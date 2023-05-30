package com.seagox.oa.excel.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 导入规则
 */
@KeySequence(value = "jelly_import_rule_seq")
public class JellyImportRule {

    /**
     * id
     */
    private Long id;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 数据源
     */
    private Long dataSource;
    
    /**
     * 模板源
     */
    private String templateSource;
    
    /**
     * 开始行
     */
    private Integer startLine;

    /**
     * 校验规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long verifyRuleId;
    
    /**
     * 处理规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long handleRuleId;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getVerifyRuleId() {
		return verifyRuleId;
	}

	public void setVerifyRuleId(Long verifyRuleId) {
		this.verifyRuleId = verifyRuleId;
	}

	public Long getHandleRuleId() {
		return handleRuleId;
	}

	public void setHandleRuleId(Long handleRuleId) {
		this.handleRuleId = handleRuleId;
	}

	public Integer getStartLine() {
		return startLine;
	}

	public void setStartLine(Integer startLine) {
		this.startLine = startLine;
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
