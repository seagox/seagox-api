package com.seagull.oa.sys.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.sys.mapper.SysLogMapper;
import com.seagull.oa.sys.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogService implements ILogService {

    @Autowired
    private SysLogMapper logMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String account, String name) {
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = logMapper.queryList(companyId, account, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

}
