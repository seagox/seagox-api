package com.seagox.oa.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessTable;
import com.seagox.oa.excel.entity.JellyForm;
import com.seagox.oa.excel.mapper.JellyBusinessFieldMapper;
import com.seagox.oa.excel.mapper.JellyBusinessTableMapper;
import com.seagox.oa.excel.mapper.JellyFormMapper;
import com.seagox.oa.flow.entity.SeaDefinition;
import com.seagox.oa.flow.mapper.SeaDefinitionMapper;
import com.seagox.oa.flow.service.ISeaDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SeaDefinitionService implements ISeaDefinitionService {

    @Autowired
    private SeaDefinitionMapper seaDefinitionMapper;

    @Autowired
    private JellyBusinessTableMapper businessTableMapper;

    @Autowired
    private JellyBusinessFieldMapper businessFieldMapper;

    @Autowired
    private JellyFormMapper formMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<SeaDefinition> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaDefinition::getCompanyId, companyId)
        .like(!StringUtils.isEmpty(name), SeaDefinition::getName, name);
        List<SeaDefinition> list = seaDefinitionMapper.selectList(qw);
        PageInfo<SeaDefinition> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryAll(Long companyId) {
        LambdaQueryWrapper<SeaDefinition> qw = new LambdaQueryWrapper<>();
        qw.eq(SeaDefinition::getCompanyId, companyId);
        List<SeaDefinition> list = seaDefinitionMapper.selectList(qw);
        return ResultData.success(list);
    }

    @Override
    public ResultData insert(SeaDefinition seaDefinition) {
        seaDefinitionMapper.insert(seaDefinition);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData update(SeaDefinition seaDefinition) {
        seaDefinitionMapper.updateById(seaDefinition);
        return ResultData.success(null);
    }

    @Override
    public ResultData delete(Long id) {
        LambdaQueryWrapper<JellyForm> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyForm::getFlowId, id);
        Long count = formMapper.selectCount(qw);
        if (count == 0) {
            seaDefinitionMapper.deleteById(id);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "流程已绑定，不可删除");
        }
    }

    @Override
    public ResultData queryById(Long id) {
        SeaDefinition seaDefinition = seaDefinitionMapper.selectById(id);
        seaDefinition.setOperationAuthority(queryOperationAuthority(seaDefinition.getDataSource()));
        return ResultData.success(seaDefinition);
    }

    /**
     * 获取表单操作权限集合
     */
    public List<Map<String, Object>> queryOperationAuthority(String dataSource) {
        List<Map<String, Object>> operationAuthority = new ArrayList<>();
        if (!StringUtils.isEmpty(dataSource)) {
            List<Map<String, Object>> businessFieldList = businessFieldMapper.queryByTableIds(dataSource.split(","));
            for (int j = 0; j < businessFieldList.size(); j++) {
                Map<String, Object> map = new HashMap<>();
                map.put("tableName", businessFieldList.get(j).get("tableName"));
                map.put("tableComment", businessFieldList.get(j).get("tableComment"));
                map.put("authority", 2);
                map.put("field", businessFieldList.get(j).get("name"));
                map.put("comment", businessFieldList.get(j).get("remark"));
                operationAuthority.add(map);
            }
        }
        return operationAuthority;
    }

    @Override
    public ResultData queryBusinessTable(Long id) {
        SeaDefinition seaDefinition = seaDefinitionMapper.selectById(id);
        LambdaQueryWrapper<JellyBusinessTable> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(!StringUtils.isEmpty(seaDefinition.getDataSource()), JellyBusinessTable::getId, Arrays.asList(seaDefinition.getDataSource().split(",")));
        List<JellyBusinessTable> list = businessTableMapper.selectList(queryWrapper);
        return ResultData.success(list);
    }

    @Override
    public ResultData queryBusinessField(Long id) {
        SeaDefinition seaDefinition = seaDefinitionMapper.selectById(id);
        if (StringUtils.isEmpty(seaDefinition.getDataSource())) {
            return ResultData.success(null);
        } else {
            List<Map<String, Object>> businessFieldList = businessFieldMapper.queryByTableIds(seaDefinition.getDataSource().split(","));
            return ResultData.success(businessFieldList);
        }
    }

}
