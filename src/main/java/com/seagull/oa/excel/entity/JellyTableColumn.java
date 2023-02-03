package com.seagull.oa.excel.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 表格表头
 */
@KeySequence(value = "jelly_table_column_seq")
public class JellyTableColumn {

    /**
     * id
     */
    private Long id;

    /**
     * 分类id
     */
    private Long classifyId;

    /**
     * 上级id
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long parentId;

    /**
     * 字段名
     */
    private String prop;

    /**
     * 标题
     */
    private String label;

    /**
     * 宽度
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer width;

    /**
     * 锁定(1:左;2:右;3:无)
     */
    private Integer locking;

    /**
     * 汇总(1:是;2:否;)
     */
    private Integer summary;

    /**
     * 合计(1:是;2:否;)
     */
    private Integer total;

    /**
     * 格式对象
     */
    private Integer target;

    /**
     * 格式化
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Long formatter;

    /**
     * 格式化数据源
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String options;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 公式
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String formula;

    /**
     * 路由json
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String routerJson;

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
     * 子集
     */
    @TableField(exist = false)
    private List<JellyTableColumn> children;

    /**
     * 是否显示
     */
    @TableField(exist = false)
    private Integer display;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLocking() {
        return locking;
    }

    public void setLocking(Integer locking) {
        this.locking = locking;
    }

    public Integer getSummary() {
        return summary;
    }

    public void setSummary(Integer summary) {
        this.summary = summary;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Long getFormatter() {
        return formatter;
    }

    public void setFormatter(Long formatter) {
        this.formatter = formatter;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getRouterJson() {
        return routerJson;
    }

    public void setRouterJson(String routerJson) {
        this.routerJson = routerJson;
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

	public List<JellyTableColumn> getChildren() {
		return children;
	}

	public void setChildren(List<JellyTableColumn> children) {
		this.children = children;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}
}
