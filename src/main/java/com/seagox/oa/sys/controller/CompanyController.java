package com.seagox.oa.sys.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysCompany;
import com.seagox.oa.sys.service.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 组织架构
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private ICompanyService companyService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll() {
        return companyService.queryAll();
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增单位")
    public ResultData insert(@Valid SysCompany company, Long userId) {
        return companyService.insert(userId, company);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SysLogPoint("更新单位")
    public ResultData update(@Valid SysCompany company) {
        return companyService.update(company);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除单位")
    public ResultData delete(@PathVariable Long id) {
        return companyService.delete(id);
    }

    /**
     * 切换
     */
    @PostMapping("/change/{changeCompanyId}")
    @SysLogPoint("切换单位")
    public ResultData change(@PathVariable Long changeCompanyId, Long userId) {
        return companyService.change(changeCompanyId, userId);
    }

    /**
     * 查询全部通过id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return companyService.queryByCompanyId(companyId);
    }

    /**
     * 查询全部通过id
     */
    @GetMapping("/queryListByCompanyId")
    public ResultData queryListByCompanyId(Long companyId) {
        return companyService.queryListByCompanyId(companyId);
    }

}
