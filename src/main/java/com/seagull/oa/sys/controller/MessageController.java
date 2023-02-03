package com.seagull.oa.sys.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.sys.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息通知
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private IMessageService messageService;

    /**
     * 是否有未读消息
     */
    @GetMapping("/queryUnRead")
    public ResultData queryUnRead(Long companyId, Long userId) {
        return messageService.queryUnRead(companyId, userId);
    }

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param userId    用户id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, Long userId, Integer status, String title) {
        return messageService.queryByPage(pageNo, pageSize, companyId, userId, status, title);
    }

    /**
     * 处理消息
     */
    @PostMapping("/update")
    @SysLogPoint("处理消息")
    public ResultData update(Long userId, Long id) {
        return messageService.update(userId, id);
    }

    /**
     * 处理消息(所有未读)
     */
    @PostMapping("/updateAll")
    @SysLogPoint("处理未读消息")
    public ResultData updateAll(Long userId) {
        return messageService.updateAll(userId);
    }

    /**
     * 查询预警数据
     */
    @GetMapping("/queryWarn")
    public ResultData queryWarn(Long companyId, Long userId) {
        return messageService.queryWarn(companyId, userId);
    }

    /**
     * 发起会议
     */
    @PostMapping("/meeting")
    public ResultData meeting(Long companyId, Long userId, String url, String memberValues) {
        return messageService.meeting(companyId, userId, url, memberValues);
    }

}
