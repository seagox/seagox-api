package com.seagox.oa.excel.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 表头设置
 */
@KeySequence(value = "jelly_table_column_config_seq")
public class JellyTableColumnConfig {
	
	/**
     * id
     */
    private Long id;
    
    /**
     * 用户id
     */
    private Long userId;
    
    /**
     * 表头id
     */
    private Long tableColumnId;
    
    /**
     * 显示(1:显示;2:隐藏;)
     */
    private Integer display;
    
    /**
     * 排序
     */
    private Integer sort;
    
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTableColumnId() {
		return tableColumnId;
	}

	public void setTableColumnId(Long tableColumnId) {
		this.tableColumnId = tableColumnId;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
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
