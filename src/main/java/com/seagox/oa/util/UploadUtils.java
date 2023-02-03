package com.seagox.oa.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import io.minio.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class UploadUtils {

    /**
     * minio上传
     */
    public static String minioOss(String endpoint, String accessKey, String secretKey, MultipartFile file, String bucketName) {
        try {
            MinioClient minioClient = MinioClient.builder().endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                JSONObject config = new JSONObject();
                config.put("Version", "2012-10-17");
                JSONArray statements = new JSONArray();

                JSONObject statement = new JSONObject();
                statement.put("Action", "s3:GetObject");
                statement.put("Effect", "Allow");
                statement.put("Principal", "*");
                statement.put("Resource", "arn:aws:s3:::"
                        + bucketName
                        + "/*");
                statements.add(statement);
                config.put("Statement", statements);
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(config.toJSONString()).build());
            }

            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = DigestUtils.md5Hex(file.getBytes()) + "." + suffix;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(file.getBytes());
            Map<String, String> headers = new HashMap<>();
            headers.put("x-amz-acl", "public-read-write");
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(byteArrayInputStream, byteArrayInputStream.available(), -1).contentType(file.getContentType()).headers(headers).build());

            return endpoint + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 阿里云oss上传
     */
    public static String aliyunOss(String endpoint, String accessKey, String secretKey, MultipartFile file, String bucketName) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKey, secretKey);
        try {
            boolean isExist = ossClient.doesBucketExist(bucketName);
            if (!isExist) {
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicReadWrite);
                ossClient.createBucket(createBucketRequest);
            }
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 设置上传的内容类型
            meta.setContentType(file.getContentType());
            String fileName = DigestUtils.md5Hex(file.getBytes()) + "." + suffix;
            // 上传文件流。
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(file.getBytes()), meta);
            if (endpoint.startsWith("https")) {
                return "https://" + bucketName + "." + endpoint.substring(8, endpoint.length()) + "/" + fileName;
            } else {
                return "https://" + bucketName + "." + endpoint.substring(7, endpoint.length()) + "/" + fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
    }

    /**
     * 移动云oss上传
     */
    public static String ecloudOss(String endpoint, String accessKey, String secretKey, MultipartFile file, String bucketName) {
        try {
            ClientConfiguration opts = new ClientConfiguration();
            opts.setSignerOverride("S3SignerType");
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, null))
                    .withClientConfiguration(opts)
                    .build();
            boolean isExist = s3.doesBucketExistV2(bucketName);
            if (!isExist) {
                com.amazonaws.services.s3.model.CreateBucketRequest createBucketRequest = new com.amazonaws.services.s3.model.CreateBucketRequest(bucketName);
                createBucketRequest.setCannedAcl(com.amazonaws.services.s3.model.CannedAccessControlList.PublicReadWrite);
                s3.createBucket(createBucketRequest);
            }
            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            com.amazonaws.services.s3.model.ObjectMetadata meta = new com.amazonaws.services.s3.model.ObjectMetadata();
            meta.setContentType(file.getContentType());
            meta.setContentLength(file.getSize());
            meta.setHeader("x-amz-acl", com.amazonaws.services.s3.model.CannedAccessControlList.PublicReadWrite);
            String fileName = DigestUtils.md5Hex(file.getBytes()) + "." + suffix;
            s3.putObject(bucketName, fileName, new ByteArrayInputStream(file.getBytes()), meta);
            return endpoint + "/" + bucketName + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
