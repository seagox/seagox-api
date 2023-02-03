package com.seagull.oa.export.vo;

import java.util.List;

/**
 * 配置集合
 */
public class ExportConfigListVo {

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 路径
     */
    private String url;

    /**
     * 配置集合
     */
    private List<ExportConfigVo> configList;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ExportConfigVo> getConfigList() {
        return configList;
    }

    public void setConfigList(List<ExportConfigVo> configList) {
        this.configList = configList;
    }
}
