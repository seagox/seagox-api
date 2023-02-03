package com.seagox.oa.disk.service.impl;

import com.seagox.oa.common.OSSResultMessage;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.disk.param.FileChunkParam;
import com.seagox.oa.disk.service.JellyFileChunkService;
import com.seagox.oa.disk.util.MinioUtil;
import io.minio.MinioClient;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioService {

    @Autowired
    private MinioUtil minioUtil;

    @Autowired
    private JellyFileChunkService jellyFileChunkService;

    // 获取minioClient
    public MinioClient getMinioClient(String endpoint, String accessKey, String secretKey) {
        MinioClient minioClient = MinioClient.builder().endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }

    //上传分块
    public ResultData uploadChunk(MinioClient minioClient, FileChunkParam param) {
        // 创建临时桶
        if (!minioUtil.bucketExists(minioClient, param.getIdentifier())) {
            minioUtil.createBucket(minioClient, param.getIdentifier());
        }

        boolean uploadFlag = minioUtil.upload(minioClient, param.getIdentifier(), param.getFile(), param.getChunkNumber() + "");
        if (!uploadFlag) {
            return ResultData.warn(ResultCode.OTHER_ERROR, OSSResultMessage.CHUNK_UPLOAD_FAIL);
        }

        param.setFileType(1);
        // 保存记录
        if (!jellyFileChunkService.saveFileChunk(param)) {
            return ResultData.success(OSSResultMessage.CHUNK_UPLOAD_FAIL);
        }
        return ResultData.success("merge");
    }

    //合并分块
    public ResultData mergeChunks(MinioClient minioClient, String fileMd5, String bucketName, String fileName) {
        if (!minioUtil.bucketExists(minioClient, fileMd5)) {
            return ResultData.success(OSSResultMessage.MERGE_SUCCESS);
        }

        if (!minioUtil.bucketExists(minioClient, bucketName)) {
            minioUtil.createBucket(minioClient, bucketName);
        }
        boolean b = minioUtil.merge(minioClient, fileMd5, bucketName, fileName);
        // 删除分片记录
        jellyFileChunkService.deleteByFileMd5(fileMd5, 1);
        if (!b) {
            //合并失败
            //删除临时桶
            minioUtil.removeBucket(minioClient, fileMd5);
            return ResultData.warn(ResultCode.OTHER_ERROR, OSSResultMessage.MERGE_FAIL);
        }
        //校验md5
        boolean checkMd5 = checkMd5(minioClient, bucketName, fileName, fileMd5);
        if (!checkMd5) {
            //删除合并的文件
            minioUtil.delete(minioClient, bucketName, fileName);
            return ResultData.warn(ResultCode.OTHER_ERROR, OSSResultMessage.MD5_CHECK_FAIL);
        }

        //删除临时桶
        boolean removeBucket = minioUtil.removeBucket(minioClient, fileMd5);
        if (!removeBucket) {
            return ResultData.warn(ResultCode.OTHER_ERROR, OSSResultMessage.DELETE_BUCKET_FAIL);
        }
        return ResultData.success(OSSResultMessage.MERGE_SUCCESS);
    }

    //删除
    public ResultData delete(MinioClient minioClient, String bucketName, String filename) {
        boolean delete = minioUtil.delete(minioClient, bucketName, filename);
        if (!delete) {
            return ResultData.warn(ResultCode.OTHER_ERROR, OSSResultMessage.DELETE_FAIL);
        }
        return ResultData.success(OSSResultMessage.DELETE_SUCCESS);
    }

    private boolean checkMd5(MinioClient minioClient, String bucketName, String fileName, String md5) {
        try {
            //利用apache工具类获取文件md5值
            InputStream inputStream = minioUtil.getInput(minioClient, bucketName, fileName);
            String md5Hex = DigestUtils.md5Hex(inputStream);
            if (md5.equalsIgnoreCase(md5Hex)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
