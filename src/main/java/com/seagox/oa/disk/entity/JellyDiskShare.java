package com.seagox.oa.disk.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 分享
 */
@KeySequence(value = "jelly_disk_share_seq")
public class JellyDiskShare {
    /**
     * 主键
     */
    private Long id;

    /**
     * 文件id
     */
    private Long diskId;

    /**
     * 公司id
     */
    private Long companyId;

    /**
     * 所属用户id
     */
    private Long userId;

    /**
     * 分享用户id
     */
    private Long shareUserId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date shareTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiskId() {
        return diskId;
    }

    public void setDiskId(Long diskId) {
        this.diskId = diskId;
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

    public Long getShareUserId() {
        return shareUserId;
    }

    public void setShareUserId(Long shareUserId) {
        this.shareUserId = shareUserId;
    }

    public Date getShareTime() {
        return shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }
}
