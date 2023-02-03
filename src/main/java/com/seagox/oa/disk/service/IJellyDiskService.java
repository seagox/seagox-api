package com.seagox.oa.disk.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.disk.entity.JellyDisk;
import com.seagox.oa.disk.param.FileChunkParam;

import java.util.List;
import java.util.Map;

/**
 * 网盘 服务类
 */
public interface IJellyDiskService {

    /**
     * 根据用户id查询
     *
     * @param companyId 公司id
     * @param userId    公司id
     * @param parentId  父id
     * @param type      类型
     * @param name      名称
     * @return
     */
    List<Map<String, Object>> queryFileByUserId(Long companyId, Long userId, Long parentId, String type, String name);

    /**
     * 查询上一级数据
     *
     * @param parentId  父Id
     * @param menuIndex 菜单标识
     * @return
     */
    ResultData backToPrevious(Long parentId, Integer menuIndex, Long userId, Long companyId, Long shareUserId);

    /**
     * 新增
     *
     * @param jellyDisk 实体
     * @return
     */
    ResultData insert(JellyDisk jellyDisk);

    /**
     * 更新
     *
     * @param jellyDisk 实体
     * @return
     */
    ResultData update(JellyDisk jellyDisk);

    /**
     * 查询
     *
     * @param id id
     * @return
     */
    public ResultData queryById(Long id);

    /**
     * 分享
     *
     * @param id        主键id
     * @param toUserIds 分享用户id
     * @return
     */
    ResultData share(Long id, String toUserIds, Long userId, Long companyId);

    /**
     * 上传分片
     *
     * @param jellyDisk 实体
     * @param param     分片参数
     * @return
     */
    ResultData uploadChunk(JellyDisk jellyDisk, FileChunkParam param);

    /**
     * 合并分片
     *
     * @param jellyDisk  实体
     * @param identifier md5
     * @return
     */
    ResultData mergeChunks(JellyDisk jellyDisk, String identifier);

    /**
     * 是否存在
     *
     * @param jellyDisk 实体
     * @return
     */
    boolean isExist(JellyDisk jellyDisk);

    /**
     * 新增或修改
     *
     * @param jellyDisk 实体
     * @return
     */
    ResultData insertOrUpdate(JellyDisk jellyDisk);

    /**
     * 移到回收站
     *
     * @param id 主键id
     */
    void moveToRecycle(Long id);
}
