package com.seagull.oa.disk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 文件分片
 */
public class JellyFileChunk {

    /**
     * 主键
     */
    private Long id;

    /**
     * 当前分片，从1开始
     */
    private Integer chunkNumber;

    /**
     * 分片大小
     */
    private Float chunkSize;

    /**
     * 当前分片大小
     */
    private Float currentChunkSize;

    /**
     * 总分片数
     */
    private Integer totalChunk;

    /**
     * 文件标识
     */
    private String identifier;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型(1:minio;2:阿里云)
     */
    private Integer fileType;

    /**
     * 相对路径
     */
    private String relativePath;

    /**
     * 分片信息
     */
    private String partETag;

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

    public Integer getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(Integer chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public Float getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Float chunkSize) {
        this.chunkSize = chunkSize;
    }

    public Float getCurrentChunkSize() {
        return currentChunkSize;
    }

    public void setCurrentChunkSize(Float currentChunkSize) {
        this.currentChunkSize = currentChunkSize;
    }

    public Integer getTotalChunk() {
        return totalChunk;
    }

    public void setTotalChunk(Integer totalChunk) {
        this.totalChunk = totalChunk;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getPartETag() {
        return partETag;
    }

    public void setPartETag(String partETag) {
        this.partETag = partETag;
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
