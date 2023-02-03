package com.seagull.oa.disk.controller;

import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.disk.entity.JellyDisk;
import com.seagull.oa.disk.param.FileChunkParam;
import com.seagull.oa.disk.service.IJellyDiskService;
import com.seagull.oa.disk.service.JellyFileChunkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 网盘
 */
@RestController
@RequestMapping("/jellyDisk")
public class JellyDiskController {

    @Autowired
    private IJellyDiskService jellyDiskService;

    @Autowired
    private JellyFileChunkService jellyFileChunkService;

    /**
     * 根据用户id查询
     *
     * @param companyId 公司id
     * @param userId    用户id
     * @param parentId  父id
     * @param type      类型
     * @param name      名称
     * @return
     */
    @GetMapping("/queryFileByUserId")
    public ResultData queryFileByUserId(Long companyId, Long userId, Long parentId, String type, String name, Long shareUserId) {
        if (shareUserId != null) {
            return ResultData.success(jellyDiskService.queryFileByUserId(companyId, shareUserId, parentId, type, name));
        }
        return ResultData.success(jellyDiskService.queryFileByUserId(companyId, userId, parentId, type, name));
    }

    /**
     * 查询上一级数据
     *
     * @param parentId 父Id
     * @return
     */
    @GetMapping("/backToPrevious/{parentId}")
    public ResultData backToPrevious(@PathVariable Long parentId, Integer menuIndex, Long userId, Long companyId, Long shareUserId) {
        return jellyDiskService.backToPrevious(parentId, menuIndex, userId, companyId, shareUserId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    public ResultData insert(JellyDisk jellyDisk) {
        return jellyDiskService.insert(jellyDisk);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    public ResultData update(JellyDisk jellyDisk) {
        return jellyDiskService.update(jellyDisk);
    }

    /**
     * 查询
     *
     * @param id id
     * @return
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return jellyDiskService.queryById(id);
    }

    /**
     * 分享
     *
     * @param id        主键id
     * @param toUserIds 分享用户id
     * @return
     */
    @PostMapping("/share")
    public ResultData share(Long id, String toUserIds, Long userId, Long companyId) {
        return jellyDiskService.share(id, toUserIds, userId, companyId);
    }

    /**
     * 新增或修改
     */
    @PostMapping("/insertOrUpdate")
    public ResultData insertOrUpdate(JellyDisk jellyDisk) {
        return jellyDiskService.insertOrUpdate(jellyDisk);
    }

    /**
     * 文件是否存在
     *
     * @param jellyDisk 实体
     * @return
     */
    @GetMapping("/isExist")
    public ResultData isExist(JellyDisk jellyDisk) {
        jellyDisk.setStatus(1);
        return ResultData.success(jellyDiskService.isExist(jellyDisk));
    }


    /**
     * 上传分片检查
     *
     * @param companyId  单位id
     * @param identifier 文件md5
     * @return
     */
    @GetMapping("/uploadChunk")
    public ResultData register(Long companyId, String identifier, JellyDisk jellyDisk) {
        jellyDisk.setStatus(1);
        if (jellyDiskService.isExist(jellyDisk)) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "文件已存在！");
        }
        return jellyFileChunkService.register(companyId, identifier);
    }

    /**
     * 上传分片
     */
    @PostMapping("/uploadChunk")
    public ResultData uploadChunk(JellyDisk jellyDisk, FileChunkParam param) {
        return jellyDiskService.uploadChunk(jellyDisk, param);
    }

    /**
     * 合并分片
     *
     * @param identifier md5
     * @return
     */
    @GetMapping("/mergeChunks")
    public ResultData mergeChunks(JellyDisk jellyDisk, String identifier) {
        return jellyDiskService.mergeChunks(jellyDisk, identifier);
    }

    /**
     * 移到回收站
     *
     * @param id 主键id
     */
    @GetMapping("/moveToRecycle/{id}")
    public ResultData moveToRecycle(@PathVariable Long id) {
        jellyDiskService.moveToRecycle(id);
        return ResultData.success(null);
    }
}

