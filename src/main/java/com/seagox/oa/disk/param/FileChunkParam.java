package com.seagox.oa.disk.param;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 分片上传参数
 */
public class FileChunkParam {

    private Long id;

    @NotNull(message = "当前分片不能为空")
    private Integer chunkNumber;

    @NotNull(message = "分片大小不能为空")
    private Float chunkSize;

    @NotNull(message = "当前分片大小不能为空")
    private Float currentChunkSize;

    @NotNull(message = "文件总数不能为空")
    private Integer totalChunks;

    @NotBlank(message = "文件标识不能为空")
    private String identifier;

    @NotBlank(message = "文件名不能为空")
    private String filename;

    private Integer fileType;

    private String relativePath;

    @NotNull(message = "文件总大小不能为空")
    private Float totalSize;

    private MultipartFile file;

    /**
     * 分片信息
     */
    private String partETag;

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

    public Integer getTotalChunks() {
        return totalChunks;
    }

    public void setTotalChunks(Integer totalChunks) {
        this.totalChunks = totalChunks;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

    public Float getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Float totalSize) {
        this.totalSize = totalSize;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getPartETag() {
        return partETag;
    }

    public void setPartETag(String partETag) {
        this.partETag = partETag;
    }
}
