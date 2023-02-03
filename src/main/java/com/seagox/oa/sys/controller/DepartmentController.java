package com.seagox.oa.sys.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysDepartment;
import com.seagox.oa.sys.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 部门
 */
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    /**
     * 查询全部通过公司id
     *
     * @param companyId 公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByPage(Long companyId, String searchCompanyId) {
        return departmentService.queryByCompanyId(companyId, searchCompanyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增部门")
    public ResultData insert(@Valid SysDepartment department) {
        return departmentService.insert(department);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SysLogPoint("更新部门")
    public ResultData update(@Valid SysDepartment department) {
        return departmentService.update(department);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除部门")
    public ResultData delete(@PathVariable Long id) {
        return departmentService.delete(id);
    }

    /**
     * 查询公司部门通过公司id
     *
     * @param companyId 公司id
     */
    @GetMapping("/queryCompanyDeptLevel")
    public ResultData queryCompanyDeptLevel(Long companyId) {
        return departmentService.queryCompanyDeptLevel(companyId);
    }

}
