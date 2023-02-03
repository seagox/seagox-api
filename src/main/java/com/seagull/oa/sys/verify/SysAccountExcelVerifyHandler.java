package com.seagull.oa.sys.verify;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagull.oa.sys.entity.SysAccount;
import com.seagull.oa.sys.entity.SysCompany;
import com.seagull.oa.sys.entity.SysDepartment;
import com.seagull.oa.sys.entity.SysRole;
import com.seagull.oa.sys.mapper.CompanyMapper;
import com.seagull.oa.sys.mapper.DepartmentMapper;
import com.seagull.oa.sys.mapper.RoleMapper;
import com.seagull.oa.sys.mapper.SysAccountMapper;
import com.seagull.oa.template.SysAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义验证
 */
@Component
public class SysAccountExcelVerifyHandler implements IExcelVerifyHandler<SysAccountModel> {

    @Autowired
    private SysAccountMapper sysAccountMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * ExcelVerifyHandlerResult
     * suceess :代表验证成功还是失败
     * msg:失败的原因
     */
    @Override
    public ExcelVerifyHandlerResult verifyHandler(SysAccountModel obj) {
        ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult(true);

        LambdaQueryWrapper<SysAccount> qwAccount = new LambdaQueryWrapper<>();
        qwAccount.eq(SysAccount::getAccount, obj.getAccount());
        int accountCount = sysAccountMapper.selectCount(qwAccount);
        if (accountCount != 0) {
            result.setSuccess(false);
            result.setMsg("账号已存在");
        }
        LambdaQueryWrapper<SysCompany> qwCompany = new LambdaQueryWrapper<>();
        qwCompany.eq(SysCompany::getName, obj.getCompanyName());
        SysCompany company = companyMapper.selectOne(qwCompany);
        if (company == null) {
            result.setSuccess(false);
            result.setMsg("所在单位不存在");
        } else {
            LambdaQueryWrapper<SysDepartment> qwDept = new LambdaQueryWrapper<>();
            qwDept.eq(SysDepartment::getCompanyId, company.getId())
                    .eq(SysDepartment::getCode, obj.getDeptCode());
            int deptCount = departmentMapper.selectCount(qwDept);
            if (deptCount == 0) {
                result.setSuccess(false);
                result.setMsg("部门编码不存在");
            }
            LambdaQueryWrapper<SysRole> qwRole = new LambdaQueryWrapper<>();
            qwRole.eq(SysRole::getCompanyId, company.getId())
                    .eq(SysRole::getName, obj.getRoleName());
            int roleCount = roleMapper.selectCount(qwRole);
            if (roleCount == 0) {
                result.setSuccess(false);
                result.setMsg("所属角色不存在");
            }
        }
        return result;
    }

}
