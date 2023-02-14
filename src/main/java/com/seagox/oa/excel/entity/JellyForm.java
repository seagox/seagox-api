package com.seagox.oa.excel.entity;

import java.util.Date;
import java.util.Map;

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
     * 设计id
     */
    private Long designId;
    
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
     * 表单设计
     */
    @TableField(exist = false)
    private JellyFormDesign formDesign;
    
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

	/**
	 * 禁用按钮权限
	 */
	@TableField(exist = false)
	private Map<String,Object> disableButtonFlag;

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

	public Long getDesignId() {
		return designId;
	}

	public void setDesignId(Long designId) {
		this.designId = designId;
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

	public JellyFormDesign getFormDesign() {
		return formDesign;
	}

	public void setFormDesign(JellyFormDesign formDesign) {
		this.formDesign = formDesign;
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

	public Map<String, Object> getDisableButtonFlag() {
		return disableButtonFlag;
	}

	public void setDisableButtonFlag(Map<String, Object> disableButtonFlag) {
		this.disableButtonFlag = disableButtonFlag;
	}
    
}
