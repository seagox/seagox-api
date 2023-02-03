package com.seagox.oa.disk.entity;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 回收站
 */
@KeySequence(value = "jelly_disk_recycle_seq")
public class JellyDiskRecycle {

    /**
     * 主键
     */
    private Long id;

    /**
     * 文件id
     */
    private Long diskId;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
