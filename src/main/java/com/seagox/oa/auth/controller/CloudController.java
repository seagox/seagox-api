package com.seagox.oa.auth.controller;

import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.service.IJellyMetaFunctionService;
import com.seagox.oa.groovy.GroovyFactory;
import com.seagox.oa.groovy.IGroovyCloud;
import com.seagox.oa.groovy.IGroovyDownload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 元函数
 */
@RestController
@RequestMapping("/cloud")
public class CloudController {

    @Autowired
    private IJellyMetaFunctionService metaFunctionService;

    /**
     * 开放api
     *
     * @param path 路径
     */
    @Transactional
    @RequestMapping("/openApi/{path}")
    public ResultData openApi(@PathVariable String path, HttpServletRequest request) {
        try {
            String script = metaFunctionService.queryByPath(path);
            if (!StringUtils.isEmpty(script)) {
                IGroovyCloud groovyCloud = GroovyFactory.getInstance().getICloudFromCode(script);
                return groovyCloud.execute(request);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "路径错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.warn(ResultCode.OTHER_ERROR, e.getMessage());
        }
    }

    /**
     * 权限api
     *
     * @param path 路径
     */
    @Transactional
    @RequestMapping("/authority/{path}")
    public ResultData authority(@PathVariable String path, HttpServletRequest request) {
        try {
            String script = metaFunctionService.queryByPath(path);
            if (!StringUtils.isEmpty(script)) {
                IGroovyCloud groovyCloud = GroovyFactory.getInstance().getICloudFromCode(script);
                return groovyCloud.execute(request);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "路径错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.warn(ResultCode.OTHER_ERROR, e.getMessage());
        }
    }

    /**
     * 下载api
     *
     * @param path 路径
     */
    @RequestMapping("/download/{path}")
    public void download(@PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            String script = metaFunctionService.queryByPath(path);
            IGroovyDownload groovyDownload = GroovyFactory.getInstance().getIDownloadFromCode(script);
            groovyDownload.download(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
