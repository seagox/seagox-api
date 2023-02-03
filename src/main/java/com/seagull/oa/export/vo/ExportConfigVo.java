package com.seagull.oa.export.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据导入配置
 */
public class ExportConfigVo {

    /**
     * 序号
     */
    private Integer index;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件sheet名
     */
    private String sheetName;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 规则id
     */
    private Long ruleId;

    /**
     * 起始行号
     */
    private Integer startLine;

    /**
     * 是否设置结束行
     */
    private Boolean endSwitch;

    /**
     * 结束行号
     */
    private Integer endLine;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 部门id
     */
    private Long departId;

    /**
     * 单位
     */
    private String unit;

    /**
     * 是否读取（1：读取；2不读取）
     */
    private String bRead;

    /**
     * 导入时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date importDate;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public Boolean getEndSwitch() {
        return endSwitch;
    }

    public void setEndSwitch(Boolean endSwitch) {
        this.endSwitch = endSwitch;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getDepartId() {
        return departId;
    }

    public void setDepartId(Long departId) {
        this.departId = departId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public String getbRead() {
        return bRead;
    }

    public void setbRead(String bRead) {
        this.bRead = bRead;
    }
}
