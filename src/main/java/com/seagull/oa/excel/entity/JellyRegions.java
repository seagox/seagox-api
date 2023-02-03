package com.seagull.oa.excel.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;

/**
 * 区域数据
 */
@KeySequence(value = "jelly_regions_seq")
public class JellyRegions {
	
	/**
     * id
     */
    private Long id;
    
    /**
     * 编码
     */
    private String code;
    
    /**
     * 等级
     */
    private Integer grade;
    
    /**
     * 名称
     */
    private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
