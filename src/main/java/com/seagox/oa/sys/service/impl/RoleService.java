package com.seagox.oa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysRole;
import com.seagox.oa.sys.mapper.RoleMapper;
import com.seagox.oa.sys.mapper.UserRelateMapper;
import com.seagox.oa.sys.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<SysRole> qw = new LambdaQueryWrapper<>();
    	qw.eq(SysRole::getCompanyId, companyId);
        List<SysRole> list = roleMapper.selectList(qw);
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
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
        Long count = roleMapper.selectCount(queryWrapper);
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
            Long count = roleMapper.selectCount(queryWrapper);
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
    	LambdaQueryWrapper<SysRole> qw = new LambdaQueryWrapper<>();
    	qw.eq(SysRole::getCompanyId, companyId);
        List<SysRole> list = roleMapper.selectList(qw);
        return ResultData.success(list);
    }

}
