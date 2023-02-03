package com.seagox.oa.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.sys.entity.SysAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户
 */
public interface SysAccountMapper extends BaseMapper<SysAccount> {

    /**
     * 查询全部通过公司id
     *
     * @param prefix       前缀
     * @param companyId    公司id
     * @param departmentId 部门id
     */
    public List<Map<String, Object>> queryByCompanyId(@Param("prefix") String prefix, @Param("searchCompanyId") Long searchCompanyId, @Param("departmentId") Long departmentId, @Param("name") String name);

    /**
     * 通过部门id查询用户
     *
     * @param deptId 部门id
     */
    public List<Map<String, Object>> queryByDeptId(Long deptId);

    /**
     * 查询全部通过公司id
     *
     * @param companyId 公司id
     */
    public List<Map<String, Object>> queryUserByCompanyId(Long companyId);


    /**
     * 通过用户串获取用户
     */
    public List<Map<String, Object>> queryByIds(String[] idList);

    /**
     * 查询列表
     *
     * @param companyId 公司id
     * @param userName  用户名
     * @return
     */
    public List<Map<String, Object>> queryList(@Param("companyId") Long companyId, @Param("userId") Long userId, @Param("userName") String userName);

}
