package com.seagox.oa.flow.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流程定义
 */
@KeySequence(value = "sea_definition_seq")
public class SeaDefinition {

    /**
     * 主键id
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
     * 流程文件(json)
     */
    private String resources;

    /**
     * 数据源
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String dataSource;

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
     * 表单操作权限集合
     */
    @TableField(exist = false)
    private List<Map<String, Object>> operationAuthority;

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

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
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

    public List<Map<String, Object>> getOperationAuthority() {
        return operationAuthority;
    }

    public void setOperationAuthority(List<Map<String, Object>> operationAuthority) {
        this.operationAuthority = operationAuthority;
    }

}
