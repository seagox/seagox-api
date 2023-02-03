package com.seagox.oa.sys.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysAccount;
import com.seagox.oa.sys.service.IUserService;
import com.seagox.oa.sys.verify.SysAccountExcelVerifyHandler;
import com.seagox.oa.template.SysAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private SysAccountExcelVerifyHandler sysAccountExcelVerifyHandler;

    /**
     * 分页查询
     *
     * @param pageNo       起始页
     * @param pageSize     每页大小
     * @param companyId    公司id
     * @param departmentId 部门id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId, Long searchCompanyId, Long departmentId, String name) {
        return userService.queryByPage(pageNo, pageSize, companyId, searchCompanyId, departmentId, name);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增用户")
    public ResultData insert(@Valid SysAccount user) {
        return userService.insert(user);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SysLogPoint("更新用户")
    public ResultData update(@Valid SysAccount user) {
        return userService.update(user);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除用户")
    public ResultData delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    /**
     * 修改密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @PostMapping("/updatePassword")
    @SysLogPoint("修改密码")
    public ResultData updatePassword(Long userId, String oldPassword, String newPassword) {
        return userService.updatePassword(userId, oldPassword, newPassword);
    }

    /**
     * 重置密码
     *
     * @param id       用户id
     * @param password 密码
     */
    @PostMapping("/resetPassword")
    @SysLogPoint("重置密码")
    public ResultData resetPassword(Long id, String password) {
        return userService.resetPassword(id, password);
    }

    /**
     * 通过部门id查询用户
     *
     * @param deptId 部门id
     */
    @GetMapping("/queryByDeptId")
    public ResultData queryByDeptId(Long deptId) {
        return userService.queryByDeptId(deptId);
    }

    /**
     * 通过用户串获取用户
     */
    @PostMapping("/queryByIds")
    public ResultData queryByIds(String ids) {
        return userService.queryByIds(ids);
    }

    /**
     * 查询列表
     *
     * @param companyId 公司id
     * @param userName  用户名
     * @return
     */
    @GetMapping("/queryList")
    public ResultData queryList(Long companyId, Long userId, String userName) {
        return ResultData.success(userService.queryList(companyId, userId, userName));
    }

    /**
     * 查询所有用户
     *
     * @param companyId 公司id
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(Long companyId) {
        return ResultData.success(userService.queryAll(companyId));
    }


    /**
     * 查询全部通过id
     */
    @GetMapping("/queryUserByCompanyId")
    public ResultData queryUserByCompanyId(Long companyId) {
        return ResultData.success(userService.queryUserByCompanyId(companyId));
    }

    /**
     * 导入
     */
    @PostMapping("/import")
    public ResultData importHandle(@RequestParam("file") MultipartFile file) {
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setNeedVerify(true);
        params.setVerifyHandler(sysAccountExcelVerifyHandler);//设置一个验证处理器
        try {
            ExcelImportResult<SysAccountModel> result = ExcelImportUtil.importExcelMore(
                    file.getInputStream(),
                    SysAccountModel.class,
                    params);
            Map<String, Object> repeatMap = new HashMap<>();
            List<SysAccountModel> resultList = result.getList();
            for (int i = 0; i < resultList.size(); i++) {
                if (repeatMap.containsKey(resultList.get(i).getAccount())) {
                    return ResultData.warn(ResultCode.OTHER_ERROR, "帐号重复,请检查:" + resultList.get(i).getAccount());
                } else {
                    repeatMap.put(resultList.get(i).getAccount(), 1);
                }
            }
            //判断是否有错误
            if (result.isVerifyFail()) {
                for (SysAccountModel entity : result.getFailList()) {
                    return ResultData.warn(ResultCode.OTHER_ERROR, "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg());
                }
            } else {
                //获到正确的数据
                userService.importHandle(result.getList());
            }
            return ResultData.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.warn(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    /**
     * 更新头像
     *
     * @param userId 用户id
     * @param avatar 头像
     */
    @PostMapping("/updateAvatar")
    @SysLogPoint("更新头像")
    public ResultData updateAvatar(Long userId, String avatar) {
        return userService.updateAvatar(userId, avatar);
    }

}
