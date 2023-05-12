package com.seagox.oa.excel.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyView;
import com.seagox.oa.excel.mapper.JellyViewMapper;
import com.seagox.oa.excel.service.IJellyViewService;

@Service
public class JellyViewService implements IJellyViewService {

	@Autowired
	private JellyViewMapper viewMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value(value = "${spring.datasource.url}")
	private String datasourceUrl;

	@Override
	public ResultData queryAll(Long companyId, String name, String remark) {
		LambdaQueryWrapper<JellyView> qw = new LambdaQueryWrapper<>();
    	qw.eq(JellyView::getCompanyId, companyId)
    	.like(!StringUtils.isEmpty(name), JellyView::getName, name)
    	.like(!StringUtils.isEmpty(remark), JellyView::getRemark, remark)
        .orderByAsc(JellyView::getCreateTime);
        List<JellyView> list = viewMapper.selectList(qw);
		return ResultData.success(list);
	}

	@Transactional
	@Override
	public ResultData insert(JellyView view) {
		LambdaQueryWrapper<JellyView> qw = new LambdaQueryWrapper<>();
		qw.eq(JellyView::getName, view.getName());
		Long count = viewMapper.selectCount(qw);
		if (count == 0) {
			viewMapper.insert(view);
			StringBuffer sql = new StringBuffer();
			if (datasourceUrl.contains("mysql")) {
				
			} else if (datasourceUrl.contains("postgresql") 
				|| datasourceUrl.contains("kingbase8")) {
				// 删除视图
				sql.append("drop view if exists ");
				sql.append(view.getName());
				sql.append(";");
				// 创建视图
				sql.append("create view ");
				sql.append(view.getName());
				sql.append(" as ");
				sql.append(view.getScript());
				sql.append(";");
				// 视图注释
				sql.append("comment on view ");
				sql.append(view.getName());
				sql.append(" is ");
				sql.append("'");
				sql.append(view.getRemark());
				sql.append("'");
				sql.append(";");
			} else if (datasourceUrl.contains("oracle")) {
				
			} else if (datasourceUrl.contains("dm")) {
				
			} else if (datasourceUrl.contains("sqlserver")) {
				
			}
			jdbcTemplate.execute(sql.toString());
			return ResultData.success(null);
		} else {
			return ResultData.warn(ResultCode.OTHER_ERROR, "视图已经存在");
		}
	}

	@Transactional
	@Override
	public ResultData update(JellyView view) {
		JellyView originalView = viewMapper.selectById(view.getId());
		StringBuffer sql = new StringBuffer();
		if (datasourceUrl.contains("mysql")) {
			
		} else if (datasourceUrl.contains("postgresql") 
			|| datasourceUrl.contains("kingbase8")) {
			// 删除注释
			sql.append("drop view if exists ");
			sql.append(originalView.getName());
			sql.append(";");
			// 创建视图
			sql.append("create view ");
			sql.append(view.getName());
			sql.append(" as ");
			sql.append(view.getScript());
			sql.append(";");
			// 视图注释
			sql.append("comment on view ");
			sql.append(view.getName());
			sql.append(" is ");
			sql.append("'");
			sql.append(view.getRemark());
			sql.append("'");
			sql.append(";");
		} else if (datasourceUrl.contains("oracle")) {
			
		} else if (datasourceUrl.contains("dm")) {
			
		} else if (datasourceUrl.contains("sqlserver")) {
			
		}
		jdbcTemplate.execute(sql.toString());
		viewMapper.updateById(view);
		return ResultData.success(null);
	}

	@Transactional
	@Override
	public ResultData delete(Long id) {
		JellyView view = viewMapper.selectById(id);
		StringBuffer sql = new StringBuffer();
		if (datasourceUrl.contains("mysql")) {
			
		} else if (datasourceUrl.contains("postgresql") 
			|| datasourceUrl.contains("kingbase8")) {
			sql.append("drop view if exists ");
			sql.append(view.getName());
		} else if (datasourceUrl.contains("oracle")) {
			
		} else if (datasourceUrl.contains("dm")) {
			
		} else if (datasourceUrl.contains("sqlserver")) {
			
		}
		jdbcTemplate.execute(sql.toString());
		viewMapper.deleteById(id);
		return ResultData.success(null);
	}

	@Override
	public ResultData queryById(Long id) {
		return ResultData.success(viewMapper.selectById(id));
	}

}
