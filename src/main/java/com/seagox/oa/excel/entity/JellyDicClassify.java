package com.seagox.oa.excel.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 字典分类
 */
@KeySequence(value = "jelly_dic_classify_seq")
public class JellyDicClassify {
	
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
