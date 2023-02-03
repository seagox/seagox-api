package com.seagox.oa.sys.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 公告
 */
@KeySequence(value = "sys_notice_seq")
public class SysNotice {

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 附件json
     */
    private String resources;

    /**
     * 发送公告人id
     */
    private String toUserIds;

    /**
     * 状态:(0-暂存, 1-已发布)
     */
    private Integer status;

    /**
     * 关联通知
     */
    private String relation;

    /**
     * 通知类型(1:普通;2:重要;3:非常重要)
     */
    private String classify;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

}
