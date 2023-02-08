package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyMetaPage;
import com.seagox.oa.excel.service.IJellyMetaPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 元页面
 */
@RestController
@RequestMapping("/metaPage")
public class JellyMetaPageController {

    @Autowired
    private IJellyMetaPageService metaPageService;

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     * @param name      名称
     * @param path      路径
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, String name, String path) {
        return metaPageService.queryByPage(pageNo, pageSize, companyId, name, path);
    }
    
    /**
     * 查询所有
     *
     * @param companyId 公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return metaPageService.queryByCompanyId(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增元页面")
    public ResultData insert(@Valid JellyMetaPage metaPage) {
        return metaPageService.insert(metaPage);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改元页面")
    public ResultData update(@Valid JellyMetaPage metaPage) {
        return metaPageService.update(metaPage);
    }
    
    /**
     * 通过id获取
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id) {
        return metaPageService.queryById(id);
    }
    
    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除元页面")
    public ResultData delete(@PathVariable Long id, String path) {
        return metaPageService.delete(id, path);
    }

    /**
     * 通过路径查询元页面信息
     */
    @GetMapping("/selectByPath/{path}")
    public ResultData selectByPath(@PathVariable String path) {
        return metaPageService.selectByPath(path);
    }

}
