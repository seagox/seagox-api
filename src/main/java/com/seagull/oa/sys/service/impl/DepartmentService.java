package com.seagull.oa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.sys.entity.SysCompany;
import com.seagull.oa.sys.entity.SysDepartment;
import com.seagull.oa.sys.entity.SysUserRelate;
import com.seagull.oa.sys.mapper.CompanyMapper;
import com.seagull.oa.sys.mapper.DepartmentMapper;
import com.seagull.oa.sys.mapper.UserRelateMapper;
import com.seagull.oa.sys.service.IDepartmentService;
import com.seagull.oa.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DepartmentService implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 分页查询
     */
    @Override
    public ResultData queryByCompanyId(Long companyId, String searchCompanyId) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (StringUtils.isEmpty(searchCompanyId)) {
            SysCompany company = companyMapper.selectById(companyId);
            list = departmentMapper.queryByCode(company.getCode().substring(0, 4));
        } else {
            list = departmentMapper.queryByCompanyId(Long.valueOf(searchCompanyId));
        }
        return ResultData.success(TreeUtils.categoryTreeHandle(list, "parentId", 0L));
    }

    @Override
    public ResultData insert(SysDepartment department) {
        LambdaQueryWrapper<SysDepartment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDepartment::getCompanyId, department.getCompanyId())
                .eq(!StringUtils.isEmpty(department.getParentId()), SysDepartment::getParentId, department.getParentId())
                .eq(SysDepartment::getName, department.getName());
        int count = departmentMapper.selectCount(queryWrapper);
        if (count != 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "部门已经存在");
        }
        SysDepartment parentDepartment = departmentMapper.selectById(department.getParentId());
        String maxCode = "";
        if (parentDepartment != null) {
            maxCode = departmentMapper.queryMaxCode(department.getCompanyId(), parentDepartment.getCode(), parentDepartment.getCode().length() + 4);
            if (StringUtils.isEmpty(maxCode)) {
                maxCode = parentDepartment.getCode() + "0000";
            }
        } else {
            maxCode = departmentMapper.queryMaxCode(department.getCompanyId(), "", 4);
            if (StringUtils.isEmpty(maxCode)) {
                maxCode = "1000";
            }
        }
        department.setCode(String.valueOf(Long.valueOf(maxCode) + 1));
        departmentMapper.insert(department);
        return ResultData.success(null);
    }


    @Override
    public ResultData update(SysDepartment department) {
        SysDepartment originalDepartment = departmentMapper.selectById(department.getId());
        if (originalDepartment.getName().equals(department.getName())) {
            if ((originalDepartment.getParentId() == null && department.getParentId() != null)
                    || (department.getParentId() == null && originalDepartment.getParentId() != null)
                    || (department.getParentId() != null && !originalDepartment.getParentId().equals(department.getParentId()))) {
                SysDepartment parentDepartment = departmentMapper.selectById(department.getParentId());
                String maxCode = "";
                if (parentDepartment != null) {
                    maxCode = departmentMapper.queryMaxCode(department.getCompanyId(), parentDepartment.getCode(), parentDepartment.getCode().length() + 4);
                    if (StringUtils.isEmpty(maxCode)) {
                        maxCode = parentDepartment.getCode() + "0000";
                    }
                } else {
                    maxCode = departmentMapper.queryMaxCode(department.getCompanyId(), "", 4);
                    if (StringUtils.isEmpty(maxCode)) {
                        maxCode = "1000";
                    }
                }
                department.setCode(String.valueOf(Long.valueOf(maxCode) + 1));
            }
            departmentMapper.updateById(department);
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<SysDepartment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysDepartment::getCompanyId, department.getCompanyId())
                    .eq(!StringUtils.isEmpty(department.getParentId()), SysDepartment::getParentId, department.getParentId())
                    .eq(SysDepartment::getName, department.getName());
            int count = departmentMapper.selectCount(queryWrapper);
            if (count == 0) {
                if ((originalDepartment.getParentId() == null && department.getParentId() != null)
                        || (department.getParentId() == null && originalDepartment.getParentId() != null)
                        || (department.getParentId() != null && !originalDepartment.getParentId().equals(department.getParentId()))) {
                    SysDepartment parentDepartment = departmentMapper.selectById(department.getParentId());
                    String maxCode = "";
                    if (parentDepartment != null) {
                        maxCode = departmentMapper.queryMaxCode(department.getCompanyId(), parentDepartment.getCode(), parentDepartment.getCode().length() + 4);
                        if (StringUtils.isEmpty(maxCode)) {
                            maxCode = parentDepartment.getCode() + "0000";
                        }
                    } else {
                        maxCode = departmentMapper.queryMaxCode(department.getCompanyId(), "", 4);
                        if (StringUtils.isEmpty(maxCode)) {
                            maxCode = "1000";
                        }
                    }
                    department.setCode(String.valueOf(Long.valueOf(maxCode) + 1));
                }
                departmentMapper.updateById(department);
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.OTHER_ERROR, "部门已经存在");
            }
        }
    }


    @Override
    public ResultData delete(Long id) {
        LambdaQueryWrapper<SysDepartment> departmentQueryWrapper = new LambdaQueryWrapper<>();
        departmentQueryWrapper.eq(SysDepartment::getParentId, id);
        int departmentCount = departmentMapper.selectCount(departmentQueryWrapper);
        if (departmentCount != 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "存在子部门，不可删除");
        }

        LambdaQueryWrapper<SysUserRelate> userRelateQueryWrapper = new LambdaQueryWrapper<>();
        userRelateQueryWrapper.eq(SysUserRelate::getDepartmentId, id);
        int userRelateCount = userRelateMapper.selectCount(userRelateQueryWrapper);
        if (userRelateCount != 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "部门存在人员，不可删除");
        }
        departmentMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryCompanyDeptLevel(Long companyId) {
        SysCompany company = companyMapper.selectById(companyId);
        List<Map<String, Object>> companyList = companyMapper.queryByCompanyId(company.getCode().substring(0, 4));
        List<Map<String, Object>> deptList = departmentMapper.queryByCode(company.getCode().substring(0, 4));
        List<Map<String, Object>> deptTree = TreeUtils.categoryTreeHandle(deptList, "parentId", 0L);
        List<Map<String, Object>> companyTree = TreeUtils.companyDeptLevel(companyList, "parentId", 0L, deptTree);
        return ResultData.success(companyTree);
    }

}
