package com.seagox.oa.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.sys.entity.SysUserRelate;

import java.util.List;

/**
 * 用户关联
 */
public interface UserRelateMapper extends BaseMapper<SysUserRelate> {

    /**
     * 查询是否存在角色
     */
    public int queryCountByRoleId(Long roleId);

    /**
     * 查询角色下用户
     */
    public List<String> queryUserByRoleId(Long roleId);

    /**
     * 查询角色下用户通过公司id
     */
    public List<String> queryUserByCompanyId(Long companyId);

    /**
     * 查询角色下用户通过部门id
     */
    public List<String> queryUserByDepartmentId(Long departmentId);

}
