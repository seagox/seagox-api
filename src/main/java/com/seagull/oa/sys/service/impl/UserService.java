package com.seagull.oa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.sys.entity.*;
import com.seagull.oa.sys.mapper.*;
import com.seagull.oa.sys.service.IUserService;
import com.seagull.oa.template.SysAccountModel;
import com.seagull.oa.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    private SysAccountMapper userMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 分页查询
     */
    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, Long searchCompanyId, Long departmentId, String name) {
        String prefix = "";
        if (searchCompanyId == null) {
            SysCompany company = companyMapper.selectById(companyId);
            prefix = company.getCode().substring(0, 4);
        }
        PageHelper.startPage(pageNo, pageSize);
        List<Map<String, Object>> list = userMapper.queryByCompanyId(prefix, searchCompanyId, departmentId, name);
        PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
        return ResultData.success(pageInfo);
    }

    @Transactional
    @Override
    public ResultData insert(SysAccount user) {
        LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAccount::getAccount, user.getAccount());
        int count = userMapper.selectCount(queryWrapper);
        if (count != 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "账号已存在");
        }
        user.setPassword(EncryptUtils.md5Encode(user.getPassword()));
        userMapper.insert(user);

        SysUserRelate userRelate = new SysUserRelate();
        userRelate.setUserId(user.getId());
        userRelate.setCompanyId(user.getCompanyId());
        userRelate.setDepartmentId(user.getDepartmentId());
        userRelate.setRoleIds(user.getRoleIds());
        userRelateMapper.insert(userRelate);

        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData update(SysAccount user) {
        SysAccount originalUser = userMapper.selectById(user.getId());
        if (originalUser.getAccount().equals(user.getAccount())) {
            userMapper.updateById(user);

            LambdaQueryWrapper<SysUserRelate> updateWrapper = new LambdaQueryWrapper<>();
            updateWrapper.eq(SysUserRelate::getUserId, user.getId());
            SysUserRelate userRelate = new SysUserRelate();
            userRelate.setCompanyId(user.getCompanyId());
            userRelate.setUserId(user.getId());
            userRelate.setDepartmentId(user.getDepartmentId());
            userRelate.setRoleIds(user.getRoleIds());
            userRelateMapper.update(userRelate, updateWrapper);

            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysAccount::getAccount, user.getAccount());
            int count = userMapper.selectCount(queryWrapper);
            if (count == 0) {
                userMapper.updateById(user);

                LambdaQueryWrapper<SysUserRelate> updateWrapper = new LambdaQueryWrapper<>();
                updateWrapper.eq(SysUserRelate::getUserId, user.getId());
                SysUserRelate userRelate = new SysUserRelate();
                userRelate.setUserId(user.getId());
                userRelate.setCompanyId(user.getCompanyId());
                userRelate.setDepartmentId(user.getDepartmentId());
                userRelate.setRoleIds(user.getRoleIds());
                userRelateMapper.update(userRelate, updateWrapper);

                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "账号已存在");
            }
        }
    }

    @Transactional
    @Override
    public ResultData delete(Long id) {
        userMapper.deleteById(id);

        LambdaQueryWrapper<SysUserRelate> deleteWrapper = new LambdaQueryWrapper<>();
        deleteWrapper.eq(SysUserRelate::getUserId, id);
        userRelateMapper.delete(deleteWrapper);

        return ResultData.success(null);
    }

    @Override
    public ResultData updatePassword(Long userId, String oldPassword, String newPassword) {
        SysAccount user = userMapper.selectById(userId);
        if (!EncryptUtils.md5Encode(oldPassword).equals(user.getPassword())) {
            return ResultData.warn(ResultCode.PARAMETER_ERROR, "旧密码输入有误");
        } else if (EncryptUtils.md5Encode(newPassword).equals(user.getPassword())) {
            return ResultData.warn(ResultCode.PARAMETER_ERROR, "新密码不可以跟旧密码一样");
        } else {
            user.setPassword(EncryptUtils.md5Encode(newPassword));
            user.setUpdateTime(new Date());
            userMapper.updateById(user);
            return ResultData.success(null);
        }
    }

    @Override
    public ResultData resetPassword(Long id, String password) {
        SysAccount user = new SysAccount();
        user.setId(id);
        user.setPassword(EncryptUtils.md5Encode(password));
        user.setUpdateTime(new Date());
        userMapper.updateById(user);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryByDeptId(Long deptId) {
        return ResultData.success(userMapper.queryByDeptId(deptId));
    }

    @Override
    public ResultData queryByIds(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return ResultData.success(null);
        } else {
            return ResultData.success(userMapper.queryByIds(ids.split(",")));
        }
    }

    @Override
    public List<Map<String, Object>> queryList(Long companyId, Long userId, String userName) {
        return userMapper.queryList(companyId, userId, userName);
    }

    @Override
    public List<Map<String, Object>> queryAll(Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        String prefix = company.getCode().substring(0, 4);
        return userMapper.queryByCompanyId(prefix, companyId, null, null);
    }

    @Override
    public List<Map<String, Object>> queryUserByCompanyId(Long companyId) {
        return userMapper.queryUserByCompanyId(companyId);
    }

    @Transactional
    @Override
    public void importHandle(List<SysAccountModel> resultList) {
        for (SysAccountModel sysAccountModel : resultList) {
            SysAccount sysAccount = new SysAccount();
            sysAccount.setAvatar("avatar");
            sysAccount.setAccount(sysAccountModel.getAccount());
            sysAccount.setEmail(sysAccountModel.getEmail());
            sysAccount.setPhone(sysAccountModel.getPhone());
            sysAccount.setName(sysAccountModel.getName());
            sysAccount.setSex(sysAccountModel.getSex());
            sysAccount.setPosition(sysAccountModel.getPosition());
            sysAccount.setPassword(EncryptUtils.md5Encode(sysAccountModel.getPassword()));
            userMapper.insert(sysAccount);

            LambdaQueryWrapper<SysCompany> qwCompany = new LambdaQueryWrapper<>();
            qwCompany.eq(SysCompany::getName, sysAccountModel.getCompanyName());
            SysCompany company = companyMapper.selectOne(qwCompany);

            LambdaQueryWrapper<SysRole> qwRole = new LambdaQueryWrapper<>();
            qwRole.eq(SysRole::getCompanyId, company.getId())
                    .eq(SysRole::getName, sysAccountModel.getRoleName());
            SysRole sysRole = roleMapper.selectOne(qwRole);


            LambdaQueryWrapper<SysDepartment> qwDept = new LambdaQueryWrapper<>();
            qwDept.eq(SysDepartment::getCompanyId, company.getId())
                    .eq(SysDepartment::getCode, sysAccountModel.getDeptCode());
            SysDepartment sysDepartment = departmentMapper.selectOne(qwDept);

            SysUserRelate userRelate = new SysUserRelate();
            userRelate.setUserId(sysAccount.getId());
            userRelate.setCompanyId(company.getId());
            userRelate.setDepartmentId(sysDepartment.getId());
            userRelate.setRoleIds(String.valueOf(sysRole.getId()));
            userRelateMapper.insert(userRelate);
        }
    }

    @Override
    public ResultData updateAvatar(Long userId, String avatar) {
        SysAccount sysAccount = new SysAccount();
        sysAccount.setId(userId);
        sysAccount.setAvatar(avatar);
        userMapper.updateById(sysAccount);
        return ResultData.success(null);
    }
}
