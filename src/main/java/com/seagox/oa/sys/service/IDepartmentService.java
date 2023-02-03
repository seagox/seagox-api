package com.seagox.oa.sys.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysDepartment;

public interface IDepartmentService {

    /**
     * 查询全部通过公司id
     */
    public ResultData queryByCompanyId(Long companyId, String searchCompanyId);

    /**
     * 新增
     */
    public ResultData insert(SysDepartment department);

    /**
     * 更新
     */
    public ResultData update(SysDepartment department);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 查询公司部门通过公司id
     *
     * @param companyId 公司id
     */
    public ResultData queryCompanyDeptLevel(Long companyId);

}
