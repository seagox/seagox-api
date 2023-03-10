package com.seagox.oa.sys.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysAccount;
import com.seagox.oa.template.SysAccountModel;

import java.util.List;
import java.util.Map;

public interface IUserService {

    /**
     * 分页查询
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, Long searchCompanyId, Long departmentId, String name);

    /**
     * 新增
     */
    public ResultData insert(SysAccount user);

    /**
     * 更新
     */
    public ResultData update(SysAccount user);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 修改密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    public ResultData updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 重置密码
     *
     * @param id       用户id
     * @param password 密码
     */
    public ResultData resetPassword(Long id, String password);

    /**
     * 通过部门id查询用户
     *
     * @param deptId 部门id
     */
    public ResultData queryByDeptId(Long deptId);

    /**
     * 通过用户串获取用户
     */
    public ResultData queryByIds(String ids);

    /**
     * 查询列表
     *
     * @param companyId 公司id
     * @param userName  用户名
     * @return
     */
    public List<Map<String, Object>> queryList(Long companyId, Long userId, String userName);


    /**
     * 查询所有用户
     *
     * @param companyId 公司id
     */
    public List<Map<String, Object>> queryAll(Long companyId);

    /**
     * 查询全部通过id
     */
    public List<Map<String, Object>> queryUserByCompanyId(Long companyId);

    /**
     * 导入处理
     */
    public void importHandle(List<SysAccountModel> resultList);

    /**
     * 更新头像
     *
     * @param userId 用户id
     * @param avatar 头像
     */
    public ResultData updateAvatar(Long userId, String avatar);

}
