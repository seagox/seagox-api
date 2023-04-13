package com.seagox.oa.excel.service;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IJellyFormService {

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param name     名称
     */
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long companyId, String name);

    /**
     * 查询全部通过公司id
     */
    public ResultData queryByCompanyId(Long companyId);

    /**
     * 添加
     */
    public ResultData insert(JellyForm form);

    /**
     * 修改
     */
    public ResultData update(JellyForm form);

    /**
     * 表单详情
     */
    public ResultData queryById(Long userId, Long id);

    /**
     * 删除
     */
    public ResultData delete(Long id);

    /**
     * 新增(自定义)
     */
    public ResultData insertCustom(HttpServletRequest request);

    /**
     * 编辑(自定义)
     */
    public ResultData updateCustom(HttpServletRequest request);

    /**
     * 获取动态业务数据
     *
     * @param path 路径
     * @param type 类型(tree:属性;list:列表;)
     */
    public ResultData queryDynamic(Long companyId, String path, String type);

    /**
     * 查询列表数据
     *
     * @param companyId 公司id
     * @param userId    用户id
     * @param id        表单id
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param search    搜索条件
     */
    public ResultData queryListById(Long companyId, Long userId, Long id, Integer pageNo, Integer pageSize, String search);

    /**
     * 获取表单详情(自定义)
     */
    public ResultData queryDetail(Long userId, Long formId, Long id);

    /**
     * 删除(自定义)
     */
    public ResultData deleteCustom(String businessType, String businessKey, HttpServletRequest request);

    /**
     * 查询业务字段
     */
    public ResultData queryBusinessField(Long formId);

    /**
     * 获取单据
     */
    public ResultData queryBill(Long id, String field, String prefix, String billDate, int digit);

    /**
     * 查询所有列表数据
     *
     * @param companyId 公司id
     * @param userId    用户id
     * @param formId    表单id
     */
    public ResultData queryListAllById(Long companyId, Long userId, Long formId);

    /**
     * 列表导出
     */
    public void export(HttpServletRequest request, HttpServletResponse response);

    /**
     * 查询区域数据
     *
     * @param 1:省;2:省市;3:省市区;4:省市区-街道
     */
    public ResultData queryRegions(String type);

    /**
     * 查询区域文本
     */
    public ResultData queryRegionsByCode(String codeStr);

    /**
     * 获取业务应用数据
     */
    public ResultData queryOptions(String value, String source, String showField);

    /**
     * 查询流程类型
     *
     * @param companyId 单位id
     * @return
     */
    public ResultData queryBusinessTypes(Long companyId);
    
}
