package com.seagox.oa.disk.controller;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.disk.param.FileChunkParam;
import com.seagox.oa.disk.service.JellyFileChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件分片
 */
@RestController
@RequestMapping("/oss")
public class OSSController {

    @Autowired
    private JellyFileChunkService jellyFileChunkService;

    /**
     * 上传分片检查
     *
     * @param companyId  单位id
     * @param identifier 文件md5
     * @return
     */
    @GetMapping("/uploadChunk")
    public ResultData register(Long companyId, String identifier) {
        return jellyFileChunkService.register(companyId, identifier);
    }

    /**
     * 上传分片
     *
     * @param companyId  单位id
     * @param param      分片参数
     * @param bucketName bucket名称
     * @return
     */
    @PostMapping("/uploadChunk")
    public ResultData uploadChunk(Long companyId, FileChunkParam param, String bucketName) {
        return jellyFileChunkService.uploadChunk(companyId, param, bucketName);
    }

    /**
     * 合并分片
     *
     * @param companyId  单位id
     * @param identifier 文件md5
     * @param bucketName bucket名称
     * @param filename   文件名称
     * @return
     */
    @GetMapping("/mergeChunks")
    public ResultData mergeChunks(Long companyId, String identifier, String bucketName, String filename) {
        return jellyFileChunkService.mergeChunks(companyId, identifier, bucketName, filename);
    }
}
