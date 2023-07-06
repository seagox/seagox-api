package com.seagox.oa.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysMessage;
import com.seagox.oa.sys.entity.SysNotice;
import com.seagox.oa.sys.mapper.NoticeMapper;
import com.seagox.oa.sys.mapper.SysMessageMapper;
import com.seagox.oa.sys.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公告 服务实现类
 */
@Service
public class NoticeService implements INoticeService {

    @Autowired
    private SysMessageMapper seaMessageMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Transactional
    @Override
    public ResultData publish(SysNotice sysNotice) {
        if (sysNotice.getId() != null) {
            noticeMapper.updateById(sysNotice);
        } else {
            noticeMapper.insert(sysNotice);
        }
        // 插入到系统消息中
        String[] toUserIds = sysNotice.getToUserIds().split(",");
        for (String toUserId : toUserIds) {
            SysMessage sysMessage = new SysMessage();
            sysMessage.setCompanyId(sysNotice.getCompanyId());
            sysMessage.setType(2);
            sysMessage.setFromUserId(sysNotice.getUserId());
            sysMessage.setToUserId(Long.parseLong(toUserId));
            sysMessage.setTitle(sysNotice.getTitle());
            sysMessage.setBusinessKey(sysNotice.getId());
            sysMessage.setBusinessType(-1L);
            seaMessageMapper.insert(sysMessage);
        }

        return ResultData.success(null);
    }

    @Override
    public ResultData queryById(long id) {
        return ResultData.success(noticeMapper.selectById(id));
    }

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, Long userId, Integer status, String title, String queryType) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = new ArrayList<>();
        if ("all".equals(queryType)) {
            list = noticeMapper.queryAll(companyId, null, status, title);
        } else {
            list = noticeMapper.queryAll(companyId, userId, status, title);
        }
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData deleteById(Long id) {
        noticeMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData staging(SysNotice sysNotice) {
        if (sysNotice.getId() != null) {
            noticeMapper.updateById(sysNotice);
        } else {
            noticeMapper.insert(sysNotice);
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData withdraw(Long id) {
        Integer relationCount = noticeMapper.selectByRelation(String.valueOf(id));
        if (relationCount > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "撤回失败，该公告已被其他公告关联");
        }
        SysNotice sysNotice = new SysNotice();
        sysNotice.setId(id);
        sysNotice.setStatus(0);
        noticeMapper.updateById(sysNotice);
        seaMessageMapper.deleteMessage(-1L, id);
        return ResultData.success(null);
    }

    @Override
    public ResultData querySendOrRec(Long companyId, Long userId) {
        return ResultData.success(noticeMapper.querySendOrRec(companyId, userId));
    }

    @Override
    public ResultData queryType() {
        return ResultData.success(noticeMapper.queryType());
    }

    @Override
    public ResultData queryShowById(long id) {
        SysNotice sysNotice = noticeMapper.selectById(id);
        Map<String, Object> result = new HashMap<>();
        result.put("title", sysNotice.getTitle());
        result.put("content", sysNotice.getContent());
        result.put("resources", sysNotice.getResources());
        return ResultData.success(result);
    }


}
