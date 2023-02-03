package com.seagull.oa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.sys.entity.SysCompany;
import com.seagull.oa.sys.entity.SysRole;
import com.seagull.oa.sys.mapper.CompanyMapper;
import com.seagull.oa.sys.mapper.RoleMapper;
import com.seagull.oa.sys.mapper.UserRelateMapper;
import com.seagull.oa.sys.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = roleMapper.queryByCode(company.getCode().substring(0, 4));
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    /**
     * 新增角色
     */
    @Override
    public ResultData insert(SysRole role) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getName, role.getName())
                .eq(SysRole::getCompanyId, role.getCompanyId());
        int count = roleMapper.selectCount(queryWrapper);
        if (count == 0) {
            roleMapper.insert(role);
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "角色名称已经存在");
        }
    }

    /**
     * 更新角色
     */
    @Override
    public ResultData update(SysRole role) {
        SysRole originalRole = roleMapper.selectById(role.getId());
        if (originalRole.getName().equals(role.getName())) {
            roleMapper.updateById(role);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRole::getName, role.getName())
                    .eq(SysRole::getCompanyId, role.getCompanyId());
            int count = roleMapper.selectCount(queryWrapper);
            if (count == 0) {
                roleMapper.updateById(role);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "角色名称已经存在");
            }
        }
    }

    /**
     * 删除角色
     */
    @Override
    public ResultData delete(Long id) {
        int count = userRelateMapper.queryCountByRoleId(id);
        if (count != 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "角色已经被引用，不可删除");
        }
        roleMapper.deleteById(id);
        return ResultData.success(null);
    }

    /**
     * 查询角色
     */
    @Override
    public ResultData queryAll(Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        List<Map<String, Object>> list = roleMapper.queryByCode(company.getCode().substring(0, 4));
        return ResultData.success(list);
    }

}
