package com.seagull.oa.excel.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyForm;
import com.seagull.oa.excel.service.IJellyFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 表单管理
 */
@RestController
@RequestMapping("/jellyForm")
public class JellyFormController {

    @Autowired
    private IJellyFormService formService;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * 分页查询
     *
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param name     名称
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId,
                                  String name) {
        return formService.queryByPage(pageNo, pageSize, companyId, name);
    }

    /**
     * 查询全部通过公司id
     */
    @GetMapping("/queryByCompanyId")
    public ResultData queryByCompanyId(Long companyId) {
        return formService.queryByCompanyId(companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增表单")
    public ResultData insert(@Valid JellyForm form) {
        return formService.insert(form);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改表单")
    public ResultData update(@Valid JellyForm form) {
        return formService.update(form);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除表单")
    public ResultData delete(@PathVariable Long id) {
        return formService.delete(id);
    }

    /**
     * 表单详情
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id, Long userId) {
        return formService.queryById(userId, id);
    }

    /**
     * 表单详情通过标识
     */
    @GetMapping("/queryByMark/{id}")
    public ResultData queryByMark(@PathVariable Long id, Long companyId, Long userId) {
        return formService.queryByMark(companyId, id, userId);
    }

    /**
     * 新增(自定义)
     */
    @PostMapping("/insertCustom")
    @SysLogPoint("表单保存")
    public ResultData insertCustom(HttpServletRequest request) {
        return formService.insertCustom(request);
    }

    /**
     * 编辑(自定义)
     */
    @PostMapping("/updateCustom")
    @SysLogPoint("表单编辑")
    public ResultData updateCustom(HttpServletRequest request) {
        return formService.updateCustom(request);
    }

    /**
     * 获取动态业务数据
     *
     * @param path 路径
     * @param type 类型(tree:属性;list:列表;)
     */
    @GetMapping("/queryDynamic")
    public ResultData queryDynamic(Long companyId, String path, String type) {
        return formService.queryDynamic(companyId, path, type);
    }

    /**
     * 查询列表数据
     *
     * @param userId   用户id
     * @param id       表单id
     * @param pageNo   起始页
     * @param pageSize 每页大小
     * @param search   搜索条件
     */
    @PostMapping("/queryListById")
    public ResultData queryListById(Long companyId, Long userId, Long id,
                                    @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, String search) {
        return formService.queryListById(companyId, userId, id, pageNo, pageSize, search);
    }

    /**
     * 验证sql
     */
    @PostMapping("/verifySql")
    public ResultData verifySql(Long userId, String sql) {
        return formService.verifySql(userId, sql);
    }

    /**
     * 表单详情(自定义)
     */
    @GetMapping("/queryDetail")
    public ResultData queryDetail(Long userId, Long formId, Long id) {
        return formService.queryDetail(userId, formId, id);
    }

    /**
     * 删除(自定义)
     */
    @PostMapping("/deleteCustom")
    @SysLogPoint("删除(自定义)")
    public ResultData deleteCustom(String businessType, String businessKey, HttpServletRequest request) {
        return formService.deleteCustom(businessType, businessKey, request);
    }

    /**
     * 查询关联应用数据(自定义)
     */
    @PostMapping("/queryRelation")
    public ResultData queryRelation(String formId, String ids) {
        return formService.queryRelation(formId, ids);
    }

    /**
     * 查询业务字段
     */
    @GetMapping("/queryBusinessField/{formId}")
    public ResultData queryBusinessField(@PathVariable Long formId) {
        return formService.queryBusinessField(formId);
    }

    /**
     * 获取单据
     */
    @PostMapping("/queryBill")
    public ResultData queryBill(Long id, String field, String prefix, String billDate, int digit) {
        return formService.queryBill(id, field, prefix, billDate, digit);
    }

    /**
     * 查询所有列表数据
     *
     * @param userId 用户id
     * @param formId 表单id
     */
    @GetMapping("/queryListAllById")
    public ResultData queryListAllById(Long companyId, Long userId, Long formId) {
        return formService.queryListAllById(companyId, userId, formId);
    }

    /**
     * 列表导出
     */
    @PostMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        formService.export(request, response);
    }

    /**
     * 详情导出
     */
    @PostMapping("/detailExport")
    public void detailExport(HttpServletRequest request, HttpServletResponse response) {
        formService.detailExport(request, response);
    }

    /**
     * 查询区域数据
     *
     * @param 1:省;2:省市;3:省市区;4:省市区-街道
     */
    @GetMapping("/queryRegions/{type}")
    public ResultData queryRegions(@PathVariable String type) {
        return formService.queryRegions(type);
    }

    /**
     * 查询区域文本
     */
    @GetMapping("/queryRegionsByCode")
    public ResultData queryRegionsByCode(String codeStr) {
        return formService.queryRegionsByCode(codeStr);
    }

    /**
     * 打印预览
     */
    @GetMapping("/printPreview")
    public void printPreview(HttpServletRequest request, HttpServletResponse response) {
        formService.printPreview(request, response);
    }

    /**
     * 获取业务应用数据
     */
    @PostMapping("/queryOptions")
    public ResultData queryOptions(String value, String source, String showField) {
        return formService.queryOptions(value, source, showField);
    }

    /**
     * 更新业务表文件数据
     */
    @PostMapping("/updateFileValue")
    public ResultData updateFileValue(String table, String field, String primaryKey, String value) {
        if (!StringUtils.isEmpty(table) && !StringUtils.isEmpty(field)
                && !StringUtils.isEmpty(primaryKey) && !StringUtils.isEmpty(value)) {
            String sql = "UPDATE " + table + "  SET " + field + " = :value WHERE id = :primaryKey";
            Map<String, Object> params = new HashMap<>();
            params.put("primaryKey", primaryKey);
            params.put("value", value);
            namedParameterJdbcTemplate.update(sql, params);
        }
        return ResultData.success(null);
    }

    /**
     * 查询流程类型
     *
     * @param companyId 单位id
     * @return
     */
    @GetMapping("/queryBusinessTypes")
    public ResultData queryBusinessTypes(Long companyId) {
        return formService.queryBusinessTypes(companyId);
    }


    /**
     * 批量审核
     */
    @PostMapping("/batchAudit")
    @SysLogPoint("批量审核")
    public ResultData batchAudit(HttpServletRequest request) {
        return formService.batchAudit(request);
    }

}
