package com.seagull.oa.excel.entity;

import java.util.Date;
import java.util.List;
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
     * 设计ids
     */
    private String designIds;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 数据源
     */
    private String dataSource;
    
    /**
     * 搜索配置
     */
    private String searchJson;
    
    /**
     * 表格表头
     */
    private Long tableHeader;
    
    /**
     * 流程json
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long flowId;
    
    /**
     * 列表导出路径
     */
    private String listExportPath;
    
    /**
     * 详情导出路径
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String detailExportPath;
    
    /**
     * 新增前规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String insertBeforeRule;
    
    /**
     * 新增后规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String insertAfterRule;
    
    /**
     * 更新前规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String updateBeforeRule;
    
    /**
     * 更新后规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String updateAfterRule;
    
    /**
     * 删除前规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String deleteBeforeRule;
    
    /**
     * 删除后规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String deleteAfterRule;
    
    /**
     * 流程结束后规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String processEndRule;
    
    /**
     * 弃审规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String abandonRule;
    
    /**
     * 导出规则
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String exportRule;
    
    /**
     * 其他参数json
     */
    private String options;

	/**
	 * 联查json
	 */
	private String relateSearchJson;
    
    /**
     * 数据标题
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String dataTitle;
    
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
     * 表单设计集合
     */
    @TableField(exist = false)
    private List<JellyFormDesign> formDesignList;
    
    /**
     * 数据表json
     */
    @TableField(exist = false)
    private String dataSheetTableJson;
    
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
     * 表头json
     */
    @TableField(exist = false)
    private String tableHeaderJson;
    
    /**
     * 权限
     */
    private String authority;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 颜色
     */
    private String color;

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

	public String getDesignIds() {
		return designIds;
	}

	public void setDesignIds(String designIds) {
		this.designIds = designIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getTableHeader() {
		return tableHeader;
	}

	public void setTableHeader(Long tableHeader) {
		this.tableHeader = tableHeader;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getInsertBeforeRule() {
		return insertBeforeRule;
	}

	public void setInsertBeforeRule(String insertBeforeRule) {
		this.insertBeforeRule = insertBeforeRule;
	}

	public String getInsertAfterRule() {
		return insertAfterRule;
	}

	public void setInsertAfterRule(String insertAfterRule) {
		this.insertAfterRule = insertAfterRule;
	}

	public String getUpdateBeforeRule() {
		return updateBeforeRule;
	}

	public void setUpdateBeforeRule(String updateBeforeRule) {
		this.updateBeforeRule = updateBeforeRule;
	}

	public String getUpdateAfterRule() {
		return updateAfterRule;
	}

	public void setUpdateAfterRule(String updateAfterRule) {
		this.updateAfterRule = updateAfterRule;
	}

	public String getDeleteBeforeRule() {
		return deleteBeforeRule;
	}

	public void setDeleteBeforeRule(String deleteBeforeRule) {
		this.deleteBeforeRule = deleteBeforeRule;
	}

	public String getDeleteAfterRule() {
		return deleteAfterRule;
	}

	public void setDeleteAfterRule(String deleteAfterRule) {
		this.deleteAfterRule = deleteAfterRule;
	}

	public String getProcessEndRule() {
		return processEndRule;
	}

	public void setProcessEndRule(String processEndRule) {
		this.processEndRule = processEndRule;
	}

	public String getAbandonRule() {
		return abandonRule;
	}

	public void setAbandonRule(String abandonRule) {
		this.abandonRule = abandonRule;
	}

	public String getExportRule() {
		return exportRule;
	}

	public void setExportRule(String exportRule) {
		this.exportRule = exportRule;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getDataTitle() {
		return dataTitle;
	}

	public void setDataTitle(String dataTitle) {
		this.dataTitle = dataTitle;
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

	public List<JellyFormDesign> getFormDesignList() {
		return formDesignList;
	}

	public void setFormDesignList(List<JellyFormDesign> formDesignList) {
		this.formDesignList = formDesignList;
	}

	public String getDataSheetTableJson() {
		return dataSheetTableJson;
	}

	public void setDataSheetTableJson(String dataSheetTableJson) {
		this.dataSheetTableJson = dataSheetTableJson;
	}

	public String getHistoryJson() {
		return historyJson;
	}

	public void setHistoryJson(String historyJson) {
		this.historyJson = historyJson;
	}

	public String getTableHeaderJson() {
		return tableHeaderJson;
	}

	public void setTableHeaderJson(String tableHeaderJson) {
		this.tableHeaderJson = tableHeaderJson;
	}

	public String getRelateSearchJson() {
		return relateSearchJson;
	}

	public void setRelateSearchJson(String relateSearchJson) {
		this.relateSearchJson = relateSearchJson;
	}

	public String getListExportPath() {
		return listExportPath;
	}

	public void setListExportPath(String listExportPath) {
		this.listExportPath = listExportPath;
	}

	public String getDetailExportPath() {
		return detailExportPath;
	}

	public void setDetailExportPath(String detailExportPath) {
		this.detailExportPath = detailExportPath;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
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

	public String getPrintJson() {
		return printJson;
	}

	public void setPrintJson(String printJson) {
		this.printJson = printJson;
	}

	public Map<String, Object> getDisableButtonFlag() {
		return disableButtonFlag;
	}

	public void setDisableButtonFlag(Map<String, Object> disableButtonFlag) {
		this.disableButtonFlag = disableButtonFlag;
	}
}
