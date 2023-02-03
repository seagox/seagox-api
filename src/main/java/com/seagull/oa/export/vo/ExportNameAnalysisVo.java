package com.seagull.oa.export.vo;

/**
 * 导入excel名称分析
 */
public class ExportNameAnalysisVo {

    /**
     * 是否统一配置(1:单一处理；2统一处理)
     */
    private Integer unified;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 读取规则（1:无；2:预算单位；3:XXXX年XX月_区划编码_名称）
     */
    private Integer readRule;

    /**
     * 年份字段
     */
    private String yearField;

    /**
     * 月份字段
     */
    private String monthField;

    /**
     * 单位数据来源
     */
    private String deptSource;

    /**
     * 单位名称字段
     */

    private String deptNameField;
    /**
     * 单位编码字段
     */

    private String deptCodeField;

    /**
     * 区划来源表
     */
    private String rgSource;

    /**
     * 区划名称字段
     */
    private String rgNameField;

    /**
     * 区划编码字段
     */
    private String rgCodeField;

    public Integer getUnified() {
        return unified;
    }

    public void setUnified(Integer unified) {
        this.unified = unified;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getReadRule() {
        return readRule;
    }

    public void setReadRule(Integer readRule) {
        this.readRule = readRule;
    }

    public String getYearField() {
        return yearField;
    }

    public void setYearField(String yearField) {
        this.yearField = yearField;
    }

    public String getMonthField() {
        return monthField;
    }

    public void setMonthField(String monthField) {
        this.monthField = monthField;
    }

    public String getDeptSource() {
        return deptSource;
    }

    public void setDeptSource(String deptSource) {
        this.deptSource = deptSource;
    }

    public String getDeptNameField() {
        return deptNameField;
    }

    public void setDeptNameField(String deptNameField) {
        this.deptNameField = deptNameField;
    }

    public String getDeptCodeField() {
        return deptCodeField;
    }

    public void setDeptCodeField(String deptCodeField) {
        this.deptCodeField = deptCodeField;
    }

    public String getRgSource() {
        return rgSource;
    }

    public void setRgSource(String rgSource) {
        this.rgSource = rgSource;
    }

    public String getRgNameField() {
        return rgNameField;
    }

    public void setRgNameField(String rgNameField) {
        this.rgNameField = rgNameField;
    }

    public String getRgCodeField() {
        return rgCodeField;
    }

    public void setRgCodeField(String rgCodeField) {
        this.rgCodeField = rgCodeField;
    }
}
