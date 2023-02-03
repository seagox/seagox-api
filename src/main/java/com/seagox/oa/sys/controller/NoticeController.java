package com.seagox.oa.sys.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysNotice;
import com.seagox.oa.sys.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公告
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private INoticeService noticeService;

    /**
     * 发布
     */
    @PostMapping("/publish")
    @SysLogPoint("公告发布")
    public ResultData publish(SysNotice sysNotice) {
        return noticeService.publish(sysNotice);
    }

    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable("id") long id) {
        return noticeService.queryById(id);
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
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId,
                                  Long userId, Integer status, String title, String queryType) {
        return noticeService.queryByPage(pageNo, pageSize, companyId, userId, status, title, queryType);
    }

    @PostMapping("/deleteById")
    public ResultData deleteById(Long id) {
        return noticeService.deleteById(id);
    }

    /**
     * 暂存
     */
    @PostMapping("/staging")
    public ResultData staging(SysNotice sysNotice) {
        return noticeService.staging(sysNotice);
    }

    /**
     * 撤回
     */
    @PostMapping("/withdraw")
    public ResultData withdraw(Long id) {
        return noticeService.withdraw(id);
    }

    /**
     * 查询已发/接收通知
     */
    @GetMapping("/querySendOrRec")
    public ResultData querySendOrRec(Long companyId, Long userId) {
        return noticeService.querySendOrRec(companyId, userId);
    }

    /**
     * 查询通知类型字典
     */
    @GetMapping("/queryType")
    public ResultData queryType() {
        return noticeService.queryType();
    }

    /**
     * 查询通知展示详情
     */
    @GetMapping("/queryShowById/{id}")
    public ResultData queryShowById(@PathVariable("id") long id) {
        return noticeService.queryShowById(id);
    }


}

