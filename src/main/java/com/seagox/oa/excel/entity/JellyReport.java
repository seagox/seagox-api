package com.seagox.oa.excel.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 报表管理
 */
@KeySequence(value = "jelly_report_seq")
public class JellyReport {
	
	/**
     * id
     */
    private Long id;
    
    /**
     * 公司id
     */
    private Long companyId;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 颜色
     */
    private String color;
    
    /**
     * 数据源
     */
    private Long dataSource;
    
    /**
     * 模板源
     */
    private String templateSource;
    
    /**
     * 搜索配置
     */
    private String searchJson;
    
    /**
     * 导出路径
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String exportPath;
    
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public Long getDataSource() {
		return dataSource;
	}


	public void setDataSource(Long dataSource) {
		this.dataSource = dataSource;
	}


	public String getTemplateSource() {
		return templateSource;
	}


	public void setTemplateSource(String templateSource) {
		this.templateSource = templateSource;
	}


	public String getSearchJson() {
		return searchJson;
	}


	public void setSearchJson(String searchJson) {
		this.searchJson = searchJson;
	}

	public String getExportPath() {
		return exportPath;
	}


	public void setExportPath(String exportPath) {
		this.exportPath = exportPath;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
}
