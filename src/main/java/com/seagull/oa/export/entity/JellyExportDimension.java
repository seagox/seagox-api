package com.seagull.oa.export.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 维度管理
 */
public class JellyExportDimension {

    /**
     * id
     */
    private Long id;

    /**
     * 维度名称
     */
    private String name;

    /**
     * ODM表
     */
    private Long odmSource;

    /**
     * ODM表字段编码
     */
    private Long odmCodeField;

    /**
     * ODM表字段名称
     */
    private Long odmNameField;

    /**
     * DIM表
     */
    private Long dimSource;

    /**
     * DIM表字段编码
     */
    private Long dimCodeField;

    /**
     * DIM表字段名称
     */
    private Long dimNameField;

    /**
     * DIM表字段年段
     */
    private Long dimYearField;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 公司id
     */
    private Long companyId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOdmSource() {
        return odmSource;
    }

    public void setOdmSource(Long odmSource) {
        this.odmSource = odmSource;
    }

    public Long getOdmCodeField() {
        return odmCodeField;
    }

    public void setOdmCodeField(Long odmCodeField) {
        this.odmCodeField = odmCodeField;
    }

    public Long getOdmNameField() {
        return odmNameField;
    }

    public void setOdmNameField(Long odmNameField) {
        this.odmNameField = odmNameField;
    }

    public Long getDimSource() {
        return dimSource;
    }

    public void setDimSource(Long dimSource) {
        this.dimSource = dimSource;
    }

    public Long getDimCodeField() {
        return dimCodeField;
    }

    public void setDimCodeField(Long dimCodeField) {
        this.dimCodeField = dimCodeField;
    }

    public Long getDimNameField() {
        return dimNameField;
    }

    public void setDimNameField(Long dimNameField) {
        this.dimNameField = dimNameField;
    }

    public Long getDimYearField() {
        return dimYearField;
    }

    public void setDimYearField(Long dimYearField) {
        this.dimYearField = dimYearField;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
