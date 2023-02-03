package com.seagull.oa.disk.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seagull.oa.common.OSSResultMessage;
import io.minio.*;
import io.minio.messages.Item;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;

/**
 * minio常用操作
 */
@Component
public class MinioUtil {

    //获取文件流
    public InputStream getInput(MinioClient minioClient, String bucketName, String filename) throws Exception {
        return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(filename).build());
    }

    //获取minio指定文件对象信息
    public StatObjectResponse getStatObject(MinioClient minioClient, String bucketName, String filename) throws Exception {
        StatObjectResponse statObject = null;

        statObject = minioClient.statObject(StatObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .build());
        return statObject;
    }

    //获得

    //删除桶 --(不是空桶也删)
    public boolean removeBucket(MinioClient minioClient, String bucketName) {
        try {
            List<Object> folderList = this.getFolderList(minioClient, bucketName);
            List<String> fileNames = new ArrayList<>();
            if (folderList != null && !folderList.isEmpty()) {
                for (int i = 0; i < folderList.size(); i++) {
                    Map<String, Object> o = JSONObject.parseObject(JSON.toJSONString(folderList.get(i)));
                    String name = (String) o.get("fileName");
                    fileNames.add(name);
                }
            }
            if (!fileNames.isEmpty()) {
                for (String fileName : fileNames) {
                    this.delete(minioClient, bucketName, fileName);
                }
            }
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
            return OSSResultMessage.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OSSResultMessage.FALSE;
    }

    //文件上传
    public boolean upload(MinioClient minioClient, String bucketName, MultipartFile file, String fileName) {
        //返回客户端文件系统中的原始文件名
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                    .build());
            return OSSResultMessage.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return OSSResultMessage.FALSE;
    }

    //文件删除
    public boolean delete(MinioClient minioClient, String bucketName, String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName)
                    .object(fileName).build());
            return OSSResultMessage.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OSSResultMessage.FALSE;
    }

    //桶是否存在
    public boolean bucketExists(MinioClient minioClient, String bucketName) {
        boolean b = false;
        try {
            b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (b) {
                return OSSResultMessage.TRUE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OSSResultMessage.FALSE;
    }

    //创建桶
    public boolean createBucket(MinioClient minioClient, String bucketName) {
        try {
            boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!b) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            return OSSResultMessage.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OSSResultMessage.FALSE;
    }

    //获取指定bucketName下所有文件 文件名+大小
    public List<Object> getFolderList(MinioClient minioClient, String bucketName) throws Exception {
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        Iterator<Result<Item>> iterator = results.iterator();
        List<Object> items = new ArrayList<>();
        String format = "{'fileName':'%s','fileSize':'%s'}";
        while (iterator.hasNext()) {
            Item item = iterator.next().get();
            items.add(JSON.parse((String.format(format, item.objectName(),
                    formatFileSize(item.size())))));
        }
        return items;
    }

    /**
     * 讲快文件合并到新桶   块文件必须满足 名字是 0 1  2 3 5....
     *
     * @param bucketName  存块文件的桶
     * @param bucketName1 存新文件的桶
     * @param fileName1   存到新桶中的文件名称
     * @return
     */
    public boolean merge(MinioClient minioClient, String bucketName, String bucketName1, String fileName1) {
        try {
            List<ComposeSource> sourceObjectList = new ArrayList<ComposeSource>();
            List<Object> folderList = this.getFolderList(minioClient, bucketName);
            List<String> fileNames = new ArrayList<>();
            if (folderList != null && !folderList.isEmpty()) {
                for (int i = 0; i < folderList.size(); i++) {
                    Map<String, Object> o = JSONObject.parseObject(JSON.toJSONString((folderList.get(i))));
                    String name = (String) o.get("fileName");
                    fileNames.add(name);
                }
            }
            if (!fileNames.isEmpty()) {
                Collections.sort(fileNames, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        if (Integer.parseInt(o2) > Integer.parseInt(o1)) {
                            return -1;
                        }
                        return 1;
                    }
                });
                for (String name : fileNames) {
                    sourceObjectList.add(ComposeSource.builder().bucket(bucketName).object(name).build());
                }
            }

            minioClient.composeObject(
                    ComposeObjectArgs.builder()
                            .bucket(bucketName1)
                            .object(fileName1)
                            .sources(sourceObjectList)
                            .build());
            return OSSResultMessage.TRUE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OSSResultMessage.FALSE;
    }

    private static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + " B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + " KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + " MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + " GB";
        }
        return fileSizeString;
    }


}
