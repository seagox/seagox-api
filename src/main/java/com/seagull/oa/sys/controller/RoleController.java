package com.seagull.oa.sys.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.sys.entity.SysRole;
import com.seagull.oa.sys.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId) {
        return roleService.queryByPage(pageNo, pageSize, companyId);
    }

    /**
     * 新增角色
     */
    @PostMapping("/insert")
    @SysLogPoint("新增角色")
    public ResultData insert(@Valid SysRole role) {
        return roleService.insert(role);
    }

    /**
     * 更新角色
     */
    @PostMapping("/update")
    @SysLogPoint("更新角色")
    public ResultData update(@Valid SysRole role) {
        return roleService.update(role);
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除角色")
    public ResultData delete(@PathVariable Long id) {
        return roleService.delete(id);
    }

    /**
     * 查询角色给下拉框
     *
     * @param companyId 公司id
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId) {
        return roleService.queryAll(companyId);
    }
}
