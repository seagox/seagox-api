package com.seagox.oa.auth.controller;

import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.service.IJellyMetaFunctionService;
import com.seagox.oa.groovy.GroovyFactory;
import com.seagox.oa.groovy.IGroovyCloud;
import com.seagox.oa.groovy.IGroovyUpload;
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
    @RequestMapping("/entrance/{path}")
    public ResultData singleEntrance(@PathVariable String path, HttpServletRequest request) {
        try {
            String rule = metaFunctionService.queryByPath(path);
            if (!StringUtils.isEmpty(rule)) {
                IGroovyCloud groovyCloud = GroovyFactory.getInstance().getICloudFromCode(rule);
                return groovyCloud.entrance(request);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "路径错误");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.warn(ResultCode.OTHER_ERROR, e.getMessage());
        }
    }

    /**
     * 开放api
     *
     * @param path 路径
     */
    @Transactional
    @RequestMapping("/entrance/{route}/{path}")
    public ResultData multiEntrance(@PathVariable String route, @PathVariable String path, HttpServletRequest request) {
        try {
            String rule = metaFunctionService.queryByPath(route + "/" + path);
            if (!StringUtils.isEmpty(rule)) {
                IGroovyCloud groovyCloud = GroovyFactory.getInstance().getICloudFromCode(rule);
                return groovyCloud.entrance(request);
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
    public ResultData singleAuthority(@PathVariable String path, HttpServletRequest request) {
        try {
            String rule = metaFunctionService.queryByPath(path);
            if (!StringUtils.isEmpty(rule)) {
                IGroovyCloud groovyCloud = GroovyFactory.getInstance().getICloudFromCode(rule);
                return groovyCloud.entrance(request);
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
    @RequestMapping("/authority/{route}/{path}")
    public ResultData multiAuthority(@PathVariable String route, @PathVariable String path, HttpServletRequest request) {
        try {
            String rule = metaFunctionService.queryByPath(route + "/" + path);
            if (!StringUtils.isEmpty(rule)) {
                IGroovyCloud groovyCloud = GroovyFactory.getInstance().getICloudFromCode(rule);
                return groovyCloud.entrance(request);
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
    public void singleDownload(@PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            String rule = metaFunctionService.queryByPath(path);
            IGroovyUpload groovyUpload = GroovyFactory.getInstance().getIUploadFromCode(rule);
            groovyUpload.download(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载api
     *
     * @param path 路径
     */
    @RequestMapping("/download/{route}/{path}")
    public void multiDownload(@PathVariable String route, @PathVariable String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            String rule = metaFunctionService.queryByPath(route + "/" + path);
            IGroovyUpload groovyUpload = GroovyFactory.getInstance().getIUploadFromCode(rule);
            groovyUpload.download(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
