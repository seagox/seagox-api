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
        Long count = companyMapper.selectCount(queryWrapper);
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
                Long markCount = companyMapper.selectCount(markQw);
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
                orgMenu.setIcon("<svg t=\"1677030380655\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2355\" width=\"16\" height=\"16\"><path d=\"M789.922409 305.422945h111.583022c30.756491 0 55.791511-24.958229 55.791512-55.649192V138.475371c0-30.690963-25.03502-55.649191-55.791512-55.649192h-111.583022c-30.756491 0-55.791511 24.958229-55.791512 55.649192v27.824083H559.31886c-15.418689 0-27.895756 12.458637-27.895755 27.825108v297.253364h-123.705825V380.079543c0-30.690963-25.03502-55.649191-55.791512-55.649191H120.191851c-30.769802 0-55.791511 24.958229-55.791512 55.649191v278.245957c0 30.690963 25.02171 55.649191 55.791512 55.649191h231.733917c30.756491 0 55.791511-24.958229 55.791512-55.649191V547.027117h123.705825v297.266674c0 15.366471 12.477067 27.825108 27.895755 27.825108h174.348217c0.155631 0 0.308189-0.009215 0.462796-0.011263v27.83637c0 30.690963 25.03502 55.649191 55.791512 55.649192h111.583023c30.756491 0 55.791511-24.958229 55.791511-55.649192V788.6446c0-30.690963-25.03502-55.649191-55.791511-55.649191h-111.583023c-30.756491 0-55.791511 24.958229-55.791512 55.649191v27.83637c-0.154607-0.003072-0.308189-0.011263-0.462796-0.011263H587.214616V547.027117h146.916281v32.933271c0 30.690963 25.03502 55.649191 55.791512 55.649192h111.583022c30.756491 0 55.791511-24.958229 55.791512-55.649192V468.662006c0-30.690963-25.03502-55.649191-55.791512-55.649191h-111.583022c-30.756491 0-55.791511 24.958229-55.791512 55.649191v22.71592H587.214616V221.94967h146.916281v27.824083c0 30.691986 25.03502 55.649191 55.791512 55.649192z m0-166.947574h111.583022l0.027645 111.298382H789.922409V138.475371zM120.191851 658.3255V380.079543h231.733917l0.027645 278.245957H120.191851z m669.730558 130.3191h111.583022l0.027645 111.298382H789.922409V788.6446z m0-319.982594h111.583022l0.027645 111.298382H789.922409V468.662006z\" fill=\"#000000\" p-id=\"2356\"></path></svg>");
                orgMenu.setSort(1);
                menuMapper.insert(orgMenu);
                pathList.add(String.valueOf(orgMenu.getId()));

                SysMenu contactMenu = new SysMenu();
                contactMenu.setCompanyId(company.getId());
                contactMenu.setParentId(orgMenu.getId());
                contactMenu.setType(4);
                contactMenu.setName("通讯录");
                contactMenu.setIcon("<svg t=\"1677046363544\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"10588\" width=\"16\" height=\"16\"><path d=\"M822.982642 62.633113h-578.423537c-35.221655 0-63.891468 28.656502-63.891468 63.891468v65.960741h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067768h36.098101v139.829972h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067768h36.098101v139.816661h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067769h36.098101v139.829971h-36.098101c-15.51391 0-28.067768 12.567168-28.067768 28.067768 0 15.5006 12.553858 28.067768 28.067768 28.067769h36.098101v59.218455c0 35.234966 28.670837 63.891468 63.891468 63.891468h578.424561c35.221655 0 63.891468-28.656502 63.891468-63.891468V126.524581c-0.001024-35.234966-28.670837-63.891468-63.892492-63.891468zM236.80215 895.719455v-59.218455h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067769 0-15.499576-12.553858-28.067768-28.067769-28.067768h-50.159118V640.536516h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.499576-12.553858-28.067768-28.067769-28.067769h-50.159118V444.585342h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.499576-12.553858-28.067768-28.067769-28.067768h-50.159118V248.620858h50.159118c15.51391 0 28.067768-12.567168 28.067769-28.067768 0-15.5006-12.553858-28.067768-28.067769-28.067768h-50.159118V126.524581c0-4.275745 3.48121-7.756955 7.756955-7.756955h434.879934v784.708785H244.559105c-4.276769 0-7.756955-3.48121-7.756955-7.756956z m593.937447 0c0 4.275745-3.48121 7.756955-7.756955 7.756956h-87.40909V118.767626h87.40909c4.275745 0 7.756955 3.48121 7.756955 7.756955v769.194874z\" fill=\"#000000\" p-id=\"10589\"></path></svg>");
                contactMenu.setPath("contact");
                contactMenu.setSort(1);
                menuMapper.insert(contactMenu);
                pathList.add(String.valueOf(contactMenu.getId()));

                SysMenu roleMenu = new SysMenu();
                roleMenu.setCompanyId(company.getId());
                roleMenu.setParentId(orgMenu.getId());
                roleMenu.setType(4);
                roleMenu.setName("角色管理");
                roleMenu.setIcon("<svg t=\"1677030493356\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2605\" width=\"16\" height=\"16\"><path d=\"M755.301774 722.134033L534.133329 611.604588l66.820804-97.226102a28.05753 28.05753 0 0 0 4.916697-15.844626V343.338442c0-101.186491-81.080455-180.464908-184.616762-180.464908-103.536308 0-184.616763 79.277393-184.616763 180.464908v155.195418a27.852752 27.852752 0 0 0 5.176764 16.199914l68.732398 96.651703L79.74838 721.914922a28.013502 28.013502 0 0 0-15.885581 25.241844v98.510055c0 15.462716 12.525189 27.973571 27.973571 27.973571h650.953526c15.462716 0 27.973571-12.511879 27.97357-27.973571v-98.510055c0.001024-10.599261-5.981538-20.296479-15.461692-25.022733z m-40.485449 95.559217H119.810965v-52.915417l244.867295-117.277873a27.920329 27.920329 0 0 0 14.997872-18.248708 27.808725 27.808725 0 0 0-4.289055-23.193051l-82.801607-116.45774v-146.262019c0-69.825907 56.521542-124.516742 128.668598-124.516742 72.148079 0 128.669621 54.690835 128.669621 124.516742v146.507751l-80.124147 116.567297a27.966404 27.966404 0 0 0-4.043323 22.837762c2.048795 7.83989 7.348425 14.424496 14.588318 18.029597l234.472811 117.168317v53.244084z\" fill=\"#000000\" p-id=\"2606\"></path><path d=\"M942.432176 653.128258L721.291375 542.597789l66.820804-97.226103a28.060601 28.060601 0 0 0 4.917722-15.844625V274.332667c0-101.186491-81.080455-180.464908-184.616763-180.464908-20.161326 0-39.912074 3.059369-58.706513 9.097221l17.100933 53.270706c13.276721-4.261411 27.264018-6.419761 41.60558-6.419761 72.147056 0 128.668597 54.690835 128.668597 124.516742v146.507751l-80.124146 116.567296a27.966404 27.966404 0 0 0-4.043323 22.837763c2.048795 7.840914 7.348425 14.424496 14.588318 18.029596l234.445166 117.168317v53.243061h-69.743997v55.948166h97.717568c15.461692 0 27.973571-12.511879 27.973571-27.973571v-98.510055c0-10.599261-5.983586-20.296479-15.462716-25.022733z\" fill=\"#000000\" p-id=\"2607\"></path></svg>");
                roleMenu.setPath("role");
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
            Long count = companyMapper.selectCount(queryWrapper);
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
        Long departmentCount = departmentMapper.selectCount(departmentQueryWrapper);
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
