package com.seagox.oa.excel.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 表单管理
 */
@KeySequence(value = "jelly_form_seq")
public class JellyForm {
	
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
     * 图标
     */
    private String icon;
    
    /**
     * 颜色
     */
    private String color;
    
    /**
     * 流程json
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long flowId;
    
    /**
     * 目标表
     */
    private Long tableId;
    
    /**
     * 表格表头
     */
    private String tableHeader;
    
    /**
     * 数据源
     */
    private String dataSource;
    
    /**
     * 搜索配置
     */
    private String searchJson;
    
    /**
     * 设计界面
     */
    private String design;

    /**
     * 其他参数json
     */
    private String options;
    
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
     * 打印json
     */
    @TableField(exist = false)
    private String printJson;
    
    /**
     * 历史审核json
     */
    @TableField(exist = false)
    private String historyJson;
    
    /**
     * 权限
     */
    @TableField(exist = false)
    private String authority;

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

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getTableHeader() {
		return tableHeader;
	}

	public void setTableHeader(String tableHeader) {
		this.tableHeader = tableHeader;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getSearchJson() {
		return searchJson;
	}

	public void setSearchJson(String searchJson) {
		this.searchJson = searchJson;
	}

	public String getDesign() {
		return design;
	}

	public void setDesign(String design) {
		this.design = design;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
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

	public String getPrintJson() {
		return printJson;
	}

	public void setPrintJson(String printJson) {
		this.printJson = printJson;
	}

	public String getHistoryJson() {
		return historyJson;
	}

	public void setHistoryJson(String historyJson) {
		this.historyJson = historyJson;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
    
}
