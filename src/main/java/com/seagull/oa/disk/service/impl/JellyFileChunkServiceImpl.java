package com.seagull.oa.disk.service.impl;

import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.disk.entity.JellyFileChunk;
import com.seagull.oa.disk.mapper.JellyFileChunkMapper;
import com.seagull.oa.disk.param.FileChunkParam;
import com.seagull.oa.disk.service.JellyFileChunkService;
import com.seagull.oa.util.UploadUtils;
import io.minio.MinioClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件分片 服务实现类
 */
@Service
public class JellyFileChunkServiceImpl extends ServiceImpl<JellyFileChunkMapper, JellyFileChunk> implements JellyFileChunkService {

    @Value("${oss.type}")
    private Integer ossType;

    @Value("${oss.endpoint}")
    private String ossEndpoint;

    @Value("${oss.access-key}")
    private String ossAccessKey;

    @Value("${oss.secret-key}")
    private String ossSecretKey;

    @Autowired
    private JellyFileChunkService jellyFileChunkService;

    @Autowired
    private MinioService minioService;

    @Autowired
    private AliyunService aliyunService;

    private static final String BUCKET_PREFIX = "sea-";

    @Override
    public List<JellyFileChunk> listByFileMd5(String md5, Integer fileType) {
        LambdaQueryWrapper<JellyFileChunk> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyFileChunk::getIdentifier, md5);
        qw.eq(JellyFileChunk::getFileType, fileType);
        return list(qw);
    }

    @Override
    public boolean saveFileChunk(FileChunkParam param) {
        JellyFileChunk fileChunk = null;
        if (param.getId() != null) {
            fileChunk = getById(param.getId());
        }

        if (fileChunk == null) {
            fileChunk = new JellyFileChunk();
        } else {
            fileChunk.setUpdateTime(LocalDateTime.now());
        }

        BeanUtils.copyProperties(param, fileChunk, "id");
        fileChunk.setTotalChunk(param.getTotalChunks());
        fileChunk.setFileName(param.getFilename());
        return saveOrUpdate(fileChunk);
    }

    @Override
    public boolean deleteByFileMd5(String md5, Integer fileType) {
        LambdaQueryWrapper<JellyFileChunk> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyFileChunk::getIdentifier, md5);
        qw.eq(JellyFileChunk::getFileType, fileType);
        return remove(qw);
    }

    @Override
    public ResultData register(Long companyId, String identifier) {
        List<JellyFileChunk> jellyFileChunkList = jellyFileChunkService.listByFileMd5(identifier, ossType);
        Map<String, Object> data = new HashMap<>(1);
        if (CollectionUtils.isEmpty(jellyFileChunkList)) {
            data.put("uploaded", false);
            return ResultData.success(data);
        }

        // 处理分片
        int[] uploadedFiles = new int[jellyFileChunkList.size()];
        int index = 0;
        for (JellyFileChunk fileChunkItem : jellyFileChunkList) {
            uploadedFiles[index] = fileChunkItem.getChunkNumber();
            index++;
        }
        data.put("uploadedChunks", uploadedFiles);
        if (jellyFileChunkList.get(0).getTotalChunk().equals(uploadedFiles.length)) {
            data.put("merge", true);
        }
        return ResultData.success(data);
    }

    @Override
    public ResultData uploadChunk(Long companyId, FileChunkParam param, String bucketName) {
        bucketName = BUCKET_PREFIX + bucketName;

        // 单文件上传
        if (param.getTotalChunks() == 1) {
            String address = null;
            if (ossType == 1) {
                address = UploadUtils.minioOss(ossEndpoint, ossAccessKey, ossSecretKey, param.getFile(), bucketName);
            } else if (ossType == 2) {
                address = UploadUtils.aliyunOss(ossEndpoint, ossAccessKey, ossSecretKey, param.getFile(), bucketName);
            } else if (ossType == 3) {
                address = UploadUtils.ecloudOss(ossEndpoint, ossAccessKey, ossSecretKey, param.getFile(), bucketName);
            }
            if (StringUtils.isEmpty(address)) {
                return ResultData.warn(ResultCode.OTHER_ERROR, "文件服务器配置有误");
            } else {
                return ResultData.success(address);
            }
        } else {
            // 分片上传
            if (ossType == 1) {
                MinioClient minioClient = minioService.getMinioClient(ossEndpoint, ossAccessKey, ossSecretKey);
                return minioService.uploadChunk(minioClient, param);
            } else if (ossType == 2) {
                OSSClient ossClient = aliyunService.initOSSClient(ossEndpoint, ossAccessKey, ossSecretKey);
                return aliyunService.uploadChunk(ossClient, param, bucketName);
            }
        }
        return ResultData.warn(ResultCode.OTHER_ERROR, "文件服务器配置有误");
    }

    @Override
    public ResultData mergeChunks(Long companyId, String identifier, String bucketName, String filename) {
        bucketName = BUCKET_PREFIX + bucketName;

        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        String newFileName = identifier + "." + suffix;

        ResultData result;
        if (ossType == 1) {
            MinioClient minioClient = minioService.getMinioClient(ossEndpoint, ossAccessKey, ossSecretKey);
            result = minioService.mergeChunks(minioClient, identifier, bucketName, newFileName);
        } else if (ossType == 2) {
            OSSClient ossClient = aliyunService.initOSSClient(ossEndpoint, ossAccessKey, ossSecretKey);
            result = aliyunService.merageFile(ossClient, identifier, newFileName, bucketName);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "文件服务器配置有误！");
        }

        if (result.getCode() == ResultCode.SUCCESS.getCode()) {
            String address = null;
            if (ossType == 1) {
                address = ossEndpoint + "/" + bucketName + "/" + newFileName;
            } else if (ossType == 2) {
                address = ossEndpoint + "/" + newFileName;
            }
            if (StringUtils.isEmpty(address)) {
                return ResultData.warn(ResultCode.OTHER_ERROR, "文件服务器配置有误");
            } else {
                return ResultData.success(address);
            }
        } else {
            return result;
        }
    }
}
