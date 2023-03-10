package com.seagox.oa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysCompany;
import com.seagox.oa.sys.entity.SysMessage;
import com.seagox.oa.sys.entity.SysNotice;
import com.seagox.oa.sys.mapper.CompanyMapper;
import com.seagox.oa.sys.mapper.NoticeMapper;
import com.seagox.oa.sys.mapper.SysMessageMapper;
import com.seagox.oa.sys.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private SysMessageMapper messageMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public ResultData queryUnRead(Long companyId, Long userId) {
        SysCompany company = companyMapper.selectById(companyId);
        return ResultData.success(messageMapper.queryCount(company.getCode().substring(0, 4), userId));
    }

    @Override
    public ResultData update(Long userId, Long id) {
        SysMessage message = messageMapper.selectById(id);
        message.setStatus(1);
        message.setUpdateTime(new Date());
        messageMapper.updateById(message);
        return ResultData.success(null);
    }

    @Override
    public ResultData updateAll(Long userId) {
        SysMessage message = new SysMessage();
        message.setStatus(1);
        message.setUpdateTime(new Date());
        LambdaQueryWrapper<SysMessage> updateWrapper = new LambdaQueryWrapper<>();
        updateWrapper.eq(SysMessage::getStatus, 0)
                .eq(SysMessage::getToUserId, userId);
        messageMapper.update(message, updateWrapper);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryWarn(Long companyId, Long userId) {
        LambdaQueryWrapper<SysMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysMessage::getCompanyId, companyId)
                .eq(SysMessage::getType, 3)
                .eq(SysMessage::getToUserId, userId);
        return ResultData.success(messageMapper.selectList(queryWrapper));
    }

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, Long userId, Integer status, String title) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = messageMapper.queryAll(company.getCode().substring(0, 4), userId, status, title);
        for (Map<String, Object> messageMap : list) {
            if (2 == (Integer) messageMap.get("type") && -1 == (Long) messageMap.get("businessType")) {
                SysNotice notice = noticeMapper.selectById((Long) messageMap.get("businessKey"));
                messageMap.put("classify", notice.getClassify());
            } else {
                messageMap.put("classify", "1");
            }
        }
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData meeting(Long companyId, Long userId, String url, String memberValues) {
        if (!StringUtils.isEmpty(memberValues)) {
            String[] memberArray = memberValues.split(",");
            for (int i = 0; i < memberArray.length; i++) {
                SysMessage message = new SysMessage();
                message.setCompanyId(companyId);
                message.setFromUserId(userId);
                message.setType(2);
                message.setBusinessType(0L);
                message.setBusinessKey(0L);
                message.setToUserId(Long.valueOf(memberArray[i]));
                message.setTitle("????????????: <a target=\"_blank\" href=" + url + ">" + url + "</a>");
                messageMapper.insert(message);
            }
        }
        return ResultData.success(null);
    }

}
