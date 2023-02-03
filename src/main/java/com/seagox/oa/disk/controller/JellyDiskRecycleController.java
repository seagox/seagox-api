package com.seagox.oa.disk.controller;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.disk.service.IJellyDiskRecycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站
 */
@RestController
@RequestMapping("/jellyDiskRecycle")
public class JellyDiskRecycleController {

    @Autowired
    private IJellyDiskRecycleService jellyDiskRecycleService;

    /**
     * 根据用户id查询
     *
     * @param companyId 公司id
     * @param userId    用户id
     */
    @GetMapping("queryByUserId")
    public ResultData queryByUserId(Long companyId, Long userId) {
        return ResultData.success(jellyDiskRecycleService.queryByUserId(companyId, userId));
    }

    /**
     * 还原
     *
     * @param id        主键id
     * @param companyId 公司id
     * @param userId    用户id
     */
    @GetMapping("revert/{id}")
    public ResultData revert(@PathVariable Long id, Long companyId, Long userId) {
        jellyDiskRecycleService.revert(companyId, userId, id);
        return ResultData.success(null);
    }

    /**
     * 删除
     *
     * @param id 主键id
     */
    @GetMapping("delete/{id}")
    public ResultData delete(@PathVariable Long id) {
        jellyDiskRecycleService.delete(id);
        return ResultData.success(null);
    }

}

