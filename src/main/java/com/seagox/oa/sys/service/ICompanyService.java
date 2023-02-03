package com.seagox.oa.sys.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysCompany;

public interface ICompanyService {

    /**
     * 查询全部
     */
    public ResultData queryAll();

    /**
     * 新增
     */
    public ResultData insert(Long userId, SysCompany company);

    /**
     * 更新
     */
    public ResultData update(SysCompany company);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 切换
     */
    public ResultData change(Long changeCompanyId, Long userId);

    /**
     * 查询全部通过id
     */
    public ResultData queryByCompanyId(Long companyId);

    /**
     * 查询全部通过id
     */
    public ResultData queryListByCompanyId(Long companyId);

}
