package com.seagox.oa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.security.JwtTokenUtils;
import com.seagox.oa.sys.entity.*;
import com.seagox.oa.sys.mapper.*;
import com.seagox.oa.sys.service.ICompanyService;
import com.seagox.oa.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class CompanyService implements ICompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Autowired
    private SysAccountMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public ResultData queryAll() {
        List<Map<String, Object>> list = companyMapper.queryAll();
        return ResultData.success(TreeUtils.categoryTreeHandle(list, "parentId", 0L));
    }

    @Transactional
    @Override
    public ResultData insert(Long userId, SysCompany company) {
        LambdaQueryWrapper<SysCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCompany::getName, company.getName());
        int count = companyMapper.selectCount(queryWrapper);
        if (count == 0) {
            SysCompany parentCompany = companyMapper.selectById(company.getParentId());
            String maxCode = "";
            if (parentCompany != null) {
                maxCode = companyMapper.queryMaxCode(parentCompany.getCode(), parentCompany.getCode().length() + 4);
                if (StringUtils.isEmpty(maxCode)) {
                    maxCode = parentCompany.getCode() + "0000";
                }
                company.setMark(parentCompany.getMark());
            } else {
                maxCode = companyMapper.queryMaxCode("", 4);
                if (StringUtils.isEmpty(maxCode)) {
                    maxCode = "1000";
                }
                LambdaQueryWrapper<SysCompany> markQw = new LambdaQueryWrapper<>();
                markQw.eq(SysCompany::getMark, company.getMark());
                int markCount = companyMapper.selectCount(markQw);
                if (markCount > 0) {
                    return ResultData.warn(ResultCode.OTHER_ERROR, "标识已经存在");
                }
            }
            company.setCode(String.valueOf(Long.valueOf(maxCode) + 1));
            companyMapper.insert(company);

            if (company.getParentId() == null) {
                List<String> pathList = new ArrayList<>();
                SysMenu orgMenu = new SysMenu();
                orgMenu.setCompanyId(company.getId());
                orgMenu.setType(5);
                orgMenu.setName("组织架构");
                orgMenu.setIcon("el-icon-office-building");
                orgMenu.setPath("organizationManager");
                orgMenu.setSort(1);
                menuMapper.insert(orgMenu);
                pathList.add(String.valueOf(orgMenu.getId()));

                SysMenu contactMenu = new SysMenu();
                contactMenu.setCompanyId(company.getId());
                contactMenu.setParentId(orgMenu.getId());
                contactMenu.setType(4);
                contactMenu.setName("通讯录");
                contactMenu.setIcon("el-icon-collection");
                contactMenu.setPath("contact");
                contactMenu.setSort(1);
                menuMapper.insert(contactMenu);
                pathList.add(String.valueOf(contactMenu.getId()));

                SysMenu roleMenu = new SysMenu();
                roleMenu.setCompanyId(company.getId());
                roleMenu.setParentId(orgMenu.getId());
                roleMenu.setType(4);
                roleMenu.setName("角色管理");
                roleMenu.setIcon("el-icon-coin");
                roleMenu.setPath("roleManager");
                roleMenu.setSort(2);
                menuMapper.insert(roleMenu);
                pathList.add(String.valueOf(roleMenu.getId()));

                SysRole role = new SysRole();
                role.setCompanyId(company.getId());
                role.setName("默认角色");
                role.setPath(org.apache.commons.lang3.StringUtils.join(pathList, ","));
                roleMapper.insert(role);
                SysUserRelate userRelate = new SysUserRelate();
                userRelate.setCompanyId(company.getId());
                userRelate.setUserId(userId);
                userRelate.setRoleIds(String.valueOf(role.getId()));
                userRelateMapper.insert(userRelate);
            }
            return ResultData.success(null);
        } else {
            return ResultData.warn(ResultCode.OTHER_ERROR, "单位名称已经存在");
        }
    }


    @Override
    public ResultData update(SysCompany company) {
        SysCompany originalCompany = companyMapper.selectById(company.getId());
        if (originalCompany.getName().equals(company.getName())) {
            if ((originalCompany.getParentId() == null && company.getParentId() != null)
                    || (company.getParentId() == null && originalCompany.getParentId() != null)
                    || (company.getParentId() != null && !originalCompany.getParentId().equals(company.getParentId()))) {
                SysCompany parentCompany = companyMapper.selectById(company.getParentId());
                String maxCode = "";
                if (parentCompany != null) {
                    maxCode = companyMapper.queryMaxCode(parentCompany.getCode(), parentCompany.getCode().length() + 4);
                    if (StringUtils.isEmpty(maxCode)) {
                        maxCode = parentCompany.getCode() + "0000";
                    }
                } else {
                    maxCode = companyMapper.queryMaxCode("", 4);
                    if (StringUtils.isEmpty(maxCode)) {
                        maxCode = "1000";
                    }
                }
                company.setCode(String.valueOf(Long.valueOf(maxCode) + 1));
            }
            companyMapper.updateById(company);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<SysCompany> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysCompany::getName, company.getName());
            int count = companyMapper.selectCount(queryWrapper);
            if (count == 0) {
                if ((originalCompany.getParentId() == null && company.getParentId() != null)
                        || (company.getParentId() == null && originalCompany.getParentId() != null)
                        || (company.getParentId() != null && !originalCompany.getParentId().equals(company.getParentId()))) {
                    SysCompany parentCompany = companyMapper.selectById(company.getParentId());
                    String maxCode = "";
                    if (parentCompany != null) {
                        maxCode = companyMapper.queryMaxCode(parentCompany.getCode(), parentCompany.getCode().length() + 4);
                        if (StringUtils.isEmpty(maxCode)) {
                            maxCode = parentCompany.getCode() + "0000";
                        }
                    } else {
                        maxCode = companyMapper.queryMaxCode("", 4);
                        if (StringUtils.isEmpty(maxCode)) {
                            maxCode = "1000";
                        }
                    }
                    company.setCode(String.valueOf(Long.valueOf(maxCode) + 1));
                }
                companyMapper.updateById(company);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "单位名称已经存在");
            }
        }
    }


    @Override
    public ResultData delete(Long id) {
        LambdaQueryWrapper<SysDepartment> departmentQueryWrapper = new LambdaQueryWrapper<>();
        departmentQueryWrapper.eq(SysDepartment::getCompanyId, id);
        int departmentCount = departmentMapper.selectCount(departmentQueryWrapper);
        if (departmentCount != 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "存在部门，不可删除");
        }

        companyMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData change(Long changeCompanyId, Long userId) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userId", userId);
        claims.put("companyId", changeCompanyId);
        claims.put("created", new Date());
        claims.put("version", JwtTokenUtils.VERSION);
        String accessToken = JwtTokenUtils.generateToken(claims, JwtTokenUtils.SECRET, System.currentTimeMillis() + JwtTokenUtils.EXPIRATION * 1000);
        claims.put("accessToken", accessToken);
        claims.put("tokenType", JwtTokenUtils.TOKENHEAD);
        claims.put("expiresIn", JwtTokenUtils.EXPIRATION);
        SysCompany company = companyMapper.selectById(changeCompanyId);
        claims.put("companyName", company.getName());
        claims.put("logo", company.getLogo());
        claims.put("alias", company.getAlias());
        claims.put("mark", company.getMark());

        SysAccount queryUser = userMapper.selectById(userId);
        LambdaQueryWrapper<SysUserRelate> qw = new LambdaQueryWrapper<>();
        qw.eq(SysUserRelate::getUserId, userId)
                .eq(SysUserRelate::getCompanyId, changeCompanyId);
        SysUserRelate userRelate = userRelateMapper.selectOne(qw);
        userRelate.setUpdateTime(new Date());
        userRelateMapper.updateById(userRelate);
        claims.put("departmentId", userRelate.getDepartmentId());
        String permissions = "";
        if (!StringUtils.isEmpty(userRelate.getRoleIds())) {
            String[] roleArray = userRelate.getRoleIds().split(",");
            for (int j = 0; j < roleArray.length; j++) {
                SysRole role = roleMapper.selectById(roleArray[j]);
                if (StringUtils.isEmpty(permissions)) {
                    permissions = permissions + role.getPath();
                } else {
                    permissions = permissions + "," + role.getPath();
                }
            }

        }
        List<SysMenu> routes = new ArrayList<>();
        if (!org.springframework.util.StringUtils.isEmpty(permissions)) {
            LambdaQueryWrapper<SysMenu> sysMenuQw = new LambdaQueryWrapper<>();
            sysMenuQw.eq(SysMenu::getType, 7).in(SysMenu::getId, Arrays.asList(permissions.split(",")));
            routes = menuMapper.selectList(sysMenuQw);
            permissions = org.apache.commons.lang3.StringUtils.join(menuMapper.queryUserMenuStr(permissions.split(",")), ",");
        }
        if (queryUser.getType() == 3) {
            //超级管理员
            permissions = permissions + ",platformApplication,sysApplication";
        } else if (queryUser.getType() == 2) {
            //管理员
            permissions = permissions + ",sysApplication";
        }
        claims.put("routes", routes);
        claims.put("permissions", permissions);
        return ResultData.success(claims);
    }

    @Override
    public ResultData queryByCompanyId(Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        List<Map<String, Object>> list = companyMapper.queryByCompanyId(company.getCode().substring(0, 4));
        return ResultData.success(TreeUtils.categoryTreeHandle(list, "parentId", 0L));
    }

    @Override
    public ResultData queryListByCompanyId(Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        List<Map<String, Object>> list = companyMapper.queryByCompanyId(company.getCode());
        return ResultData.success(list);
    }

}
