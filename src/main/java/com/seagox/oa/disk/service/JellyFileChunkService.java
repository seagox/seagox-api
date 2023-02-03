package com.seagox.oa.disk.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.disk.entity.JellyFileChunk;
import com.seagox.oa.disk.param.FileChunkParam;

import java.util.List;

/**
 * 文件分片 服务类
 */
public interface JellyFileChunkService extends IService<JellyFileChunk> {

    /**
     * 根据文件 md5 查询
     *
     * @param md5      md5
     * @param fileType 文件类型
     * @return
     */
    List<JellyFileChunk> listByFileMd5(String md5, Integer fileType);

    /**
     * 保存记录
     *
     * @param param 记录参数
     */
    boolean saveFileChunk(FileChunkParam param);

    /**
     * 根据MD5删除
     *
     * @param md5      MD5
     * @param fileType 文件类型
     * @return
     */
    boolean deleteByFileMd5(String md5, Integer fileType);

    /**
     * 上传分片检查
     *
     * @param companyId  单位id
     * @param identifier 文件md5
     * @return
     */
    ResultData register(Long companyId, String identifier);

    /**
     * 上传分片
     *
     * @param companyId  单位id
     * @param param      分片参数
     * @param bucketName bucketName
     * @return
     */
    ResultData uploadChunk(Long companyId, FileChunkParam param, String bucketName);

    /**
     * 合并分片
     *
     * @param companyId  单位id
     * @param identifier 文件md5
     * @param bucketName bucket名称
     * @param filename   文件名称
     * @return
     */
    ResultData mergeChunks(Long companyId, String identifier, String bucketName, String filename);
}
