package com.seagox.oa.excel.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 数据表
 */
@KeySequence(value = "jelly_data_sheet_seq")
public class JellyDataSheet {
	
	/**
     * id
     */
    private Long id;
    
    /**
     * 表单id
     */
    private Long formId;
    
    /**
     * 数据表名
     */
    private String tableName;
    
    /**
     * 是否单条(1:是;2:否;)
     */
    private Integer singleFlag;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 关联表
     */
    private String relateTable;
    
    /**
     * 关联字段
     */
    private String relateField;
    
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
	
	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getSingleFlag() {
		return singleFlag;
	}

	public void setSingleFlag(Integer singleFlag) {
		this.singleFlag = singleFlag;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRelateTable() {
		return relateTable;
	}

	public void setRelateTable(String relateTable) {
		this.relateTable = relateTable;
	}

	public String getRelateField() {
		return relateField;
	}

	public void setRelateField(String relateField) {
		this.relateField = relateField;
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
