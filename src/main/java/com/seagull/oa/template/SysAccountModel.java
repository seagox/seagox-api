package com.seagull.oa.template;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.seagull.oa.util.ValidatorUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SysAccountModel extends ImportModel {

    /**
     * 所在单位
     */
    @Excel(name = "所在单位")
    @NotNull(message = "不能为空或有误")
    private String companyName;

    /**
     * 部门编号
     */
    @Excel(name = "部门编号")
    @NotNull(message = "不能为空或有误")
    private String deptCode;

    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    private String deptName;

    /**
     * 帐号
     */
    @Excel(name = "帐号")
    @NotNull(message = "不能为空或有误")
    @Size(min = 2, message = "长度至少2位")
    private String account;

    /**
     * 密码
     */
    @Excel(name = "密码")
    @NotNull(message = "不能为空或有误")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d)(?=.*[^a-z0-9])[\\S]{8,}$", message = "必须包含字母、数字和特殊字符，且长度至少8位")
    private String password;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @NotNull(message = "不能为空或有误")
    private String name;

    /**
     * 所属角色
     */
    @Excel(name = "所属角色")
    @NotNull(message = "不能为空或有误")
    private String roleName;

    /**
     * 性别(1:男;2:女;)
     */
    @Excel(name = "性别", replace = {"男_1", "女_2"})
    @NotNull(message = "不能为空或有误")
    private Integer sex;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @Pattern(regexp = ValidatorUtils.REGEX_MOBILE, message = "不规范")
    private String phone;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    @Pattern(regexp = ValidatorUtils.REGEX_EMAIL, message = "不规范")
    private String email;

    /**
     * 职位
     */
    @Excel(name = "职位")
    private String position;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
