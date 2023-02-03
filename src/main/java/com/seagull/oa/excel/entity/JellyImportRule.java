package com.seagull.oa.excel.entity;

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
     * 导入前规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long beforeRuleId;

    /**
     * 导入后规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long afterRuleId;

    /**
     * 数据源
     */
    private Long dataSource;
    
    /**
     * 模板源
     */
    private String templateSource;

    /**
     * 校验规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String verifyRuleId;

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

	public Long getBeforeRuleId() {
		return beforeRuleId;
	}

	public void setBeforeRuleId(Long beforeRuleId) {
		this.beforeRuleId = beforeRuleId;
	}

	public Long getAfterRuleId() {
		return afterRuleId;
	}

	public void setAfterRuleId(Long afterRuleId) {
		this.afterRuleId = afterRuleId;
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

	public String getVerifyRuleId() {
		return verifyRuleId;
	}

	public void setVerifyRuleId(String verifyRuleId) {
		this.verifyRuleId = verifyRuleId;
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
