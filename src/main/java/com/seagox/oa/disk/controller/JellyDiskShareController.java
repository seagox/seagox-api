package com.seagox.oa.disk.controller;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.disk.service.IJellyDiskShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分享
 */
@RequestMapping("/jellyDiskShare")
@RestController
public class JellyDiskShareController {

    @Autowired
    private IJellyDiskShareService jellyDiskShareService;

    /**
     * 根据用户id查询
     *
     * @param companyId 公司id
     * @param userId    用户id
     */
    @GetMapping("queryByUserId")
    public ResultData queryByUserId(Long companyId, Long userId) {
        return ResultData.success(jellyDiskShareService.queryByUserId(companyId, userId));
    }


}
