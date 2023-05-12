package com.seagox.oa.excel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyView;
import com.seagox.oa.excel.entity.JellyViewField;
import com.seagox.oa.excel.mapper.JellyViewFieldMapper;
import com.seagox.oa.excel.mapper.JellyViewMapper;
import com.seagox.oa.excel.service.IJellyViewFieldService;
import com.seagox.oa.template.FieldModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class JellyViewFieldService implements IJellyViewFieldService {

	@Autowired
	private JellyViewMapper viewMapper;

	@Autowired
	private JellyViewFieldMapper viewFieldMapper;

    @Override
    public ResultData queryAll(String viewName) {
        LambdaQueryWrapper<JellyView> viewQw = new LambdaQueryWrapper<>();
        viewQw.eq(JellyView::getName, viewName);
        JellyView view = viewMapper.selectOne(viewQw);
        if (view != null) {
            LambdaQueryWrapper<JellyViewField> viewFieldQw = new LambdaQueryWrapper<>();
            viewFieldQw.eq(JellyViewField::getViewId, view.getId());
            List<JellyViewField> list = viewFieldMapper.selectList(viewFieldQw);
            return ResultData.success(list);
        } else {
            return ResultData.success(null);
        }
    }

    /**
     * 分页查询
     */
    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long viewId, String name, String remark) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyViewField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyViewField::getViewId, viewId)
                .like(!StringUtils.isEmpty(name), JellyViewField::getName, name)
                .like(!StringUtils.isEmpty(remark), JellyViewField::getRemark, remark)
                .orderByDesc(JellyViewField::getCreateTime);
        List<JellyViewField> list = viewFieldMapper.selectList(qw);
        PageInfo<JellyViewField> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Transactional
    @Override
    public ResultData insert(JellyViewField viewField) {
        LambdaQueryWrapper<JellyViewField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyViewField::getViewId, viewField.getViewId())
                .eq(JellyViewField::getName, viewField.getName());

        Long count = viewFieldMapper.selectCount(qw);
        if (count > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "字段已经存在");
        }
        viewFieldMapper.insert(viewField);
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData update(JellyViewField viewField) {
        LambdaQueryWrapper<JellyViewField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyViewField::getViewId, viewField.getViewId())
                .eq(JellyViewField::getName, viewField.getName());

        Long count = viewFieldMapper.selectCount(qw);
        if (count > 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "字段已经存在");
        } else {
        	viewFieldMapper.updateById(viewField);
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData delete(Long id) {
    	viewFieldMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryByViewId(Long viewId) {
        LambdaQueryWrapper<JellyViewField> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyViewField::getViewId, viewId);
        List<JellyViewField> list = viewFieldMapper.selectList(qw);
        return ResultData.success(list);
    }

	@Override
	public ResultData importHandle(Long viewId, List<FieldModel> resultList) {
        for(int i=0;i<resultList.size();i++) {
        	FieldModel fieldModel = resultList.get(i);
        	LambdaQueryWrapper<JellyViewField> qw = new LambdaQueryWrapper<>();
        	qw.eq(JellyViewField::getViewId, viewId)
                    .eq(JellyViewField::getName, fieldModel.getName());
            Long count = viewFieldMapper.selectCount(qw);
            if (count > 0) {
                return ResultData.warn(ResultCode.OTHER_ERROR, "第" + (i+2) + "行的错误是：" + "字段名" + fieldModel.getName() +"已经存在");
            }
        }
        for(int i=0;i<resultList.size();i++) {
        	FieldModel fieldModel = resultList.get(i);
        	JellyViewField viewField = new JellyViewField();
        	viewField.setViewId(viewId);
        	viewField.setName(fieldModel.getName());
        	viewField.setRemark(fieldModel.getRemark());
        	viewField.setType(fieldModel.getType());
        	viewFieldMapper.insert(viewField);
        }
        return ResultData.success(null);
	}

}
