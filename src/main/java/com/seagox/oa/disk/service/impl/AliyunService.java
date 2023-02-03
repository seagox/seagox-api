package com.seagox.oa.disk.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.seagox.oa.common.OSSResultMessage;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.disk.param.FileChunkParam;
import com.seagox.oa.disk.service.JellyFileChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class AliyunService {

    @Autowired
    private JellyFileChunkService jellyFileChunkService;

    /**
     * 初始化OSSClient
     *
     * @param endpoint
     * @param accessKey
     * @param secretKey
     * @return
     */
    public OSSClient initOSSClient(String endpoint, String accessKey, String secretKey) {
        return new OSSClient(endpoint, accessKey, secretKey);
    }

    /**
     * 生成该文件的分片上传组标识uploadid
     *
     * @param ossClient
     * @param key
     * @param bucketName
     * @return
     */
    public String getUploadOSSId(OSSClient ossClient, String key, String bucketName) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult upResult = ossClient.initiateMultipartUpload(request);
        String uploadId = upResult.getUploadId();
        return uploadId;
    }

    public ResultData uploadChunk(OSSClient ossClient, FileChunkParam param, String bucketName) {
        MultipartFile file = param.getFile();
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String key = param.getIdentifier() + "." + suffix;
        try {
            boolean isExist = ossClient.doesBucketExist(bucketName);
            if (!isExist) {
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicReadWrite);
                ossClient.createBucket(createBucketRequest);
            }
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(key);
            uploadPartRequest.setUploadId(getUploadOSSId(ossClient, key, bucketName));
            uploadPartRequest.setInputStream(new ByteArrayInputStream(param.getFile().getBytes()));
            // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
            uploadPartRequest.setPartSize(Math.round(param.getChunkSize()));
            // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出此范围，OSS将返回InvalidArgument错误码。
            uploadPartRequest.setPartNumber(param.getChunkNumber());
            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
            PartETag partETag = uploadPartResult.getPartETag();
            param.setPartETag(JSON.toJSONString(partETag));
            // 保存记录
            param.setFileType(2);
            if (!jellyFileChunkService.saveFileChunk(param)) {
                return ResultData.warn(ResultCode.OTHER_ERROR, OSSResultMessage.CHUNK_UPLOAD_FAIL);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.warn(ResultCode.OTHER_ERROR, OSSResultMessage.CHUNK_UPLOAD_FAIL);
        } finally {
            ossClient.shutdown();
        }
        return ResultData.success("merge");
    }

    public ResultData merageFile(OSSClient ossClient, String fileMd5, String key, String bucketName) {
        List<PartETag> chunkInfoList = new ArrayList<>();
        try {
//            List<JellyFileChunk> jellyFileChunkList = jellyFileChunkService.listByFileMd5(fileMd5, OssType.aliyun.getValue());
//            for (JellyFileChunk fileChunk : jellyFileChunkList) {
//                chunkInfoList.add(JSON.parseObject(fileChunk.getPartETag(), PartETag.class));
//            }
            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, getUploadOSSId(ossClient, key, bucketName), chunkInfoList);
            ossClient.completeMultipartUpload(completeMultipartUploadRequest);
            // 删除分片记录
            jellyFileChunkService.deleteByFileMd5(fileMd5, 2);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.warn(ResultCode.OTHER_ERROR, OSSResultMessage.MERGE_FAIL);
        } finally {
            ossClient.shutdown();
        }
        return ResultData.success(OSSResultMessage.MERGE_SUCCESS);
    }
}
