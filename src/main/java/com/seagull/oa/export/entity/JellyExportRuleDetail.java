package com.seagull.oa.export.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 导入规则明细
 */
public class JellyExportRuleDetail {

    /**
     * id
     */
    private Long id;

    /**
     * 导入规则id
     */
    private Long exportRuleId;

    /**
     * 对应字段
     */
    private Long field;

    /**
     * 对应列
     */
    private String col;

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


	/**
	 * 字段转换类型(1无;2字典;3用户;4部门;5唯一字段;6地址)
	 */
	private Integer type;

	/**
	 * 字段转换来源
	 */
	private Long source;

	/**
	 * sql语句
	 */
	private String sqlSource;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExportRuleId() {
		return exportRuleId;
	}

	public void setExportRuleId(Long exportRuleId) {
		this.exportRuleId = exportRuleId;
	}

	public Long getField() {
		return field;
	}

	public void setField(Long field) {
		this.field = field;
	}

	public String getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	public String getSqlSource() {
		return sqlSource;
	}

	public void setSqlSource(String sqlSource) {
		this.sqlSource = sqlSource;
	}
}
