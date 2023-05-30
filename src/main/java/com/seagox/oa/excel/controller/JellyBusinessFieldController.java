package com.seagox.oa.excel.controller;

import com.seagox.oa.annotation.SysLogPoint;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyBusinessField;
import com.seagox.oa.excel.service.IJellyBusinessFieldService;
import com.seagox.oa.template.FieldModel;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

/**
 * 业务字段
 */
@RestController
@RequestMapping("/businessField")
public class JellyBusinessFieldController {

    @Autowired
    private IJellyBusinessFieldService businessFieldService;

    /**
     * 查询全部
     */
    @GetMapping("/queryAll")
    public ResultData queryAll(String tableName) {
        return businessFieldService.queryAll(tableName);
    }

    /**
     * 分页查询
     *
     * @param pageNo          起始页
     * @param pageSize        每页大小
     * @param businessTableId 业务表id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                  Long businessTableId, String name, String remark) {
        return businessFieldService.queryByPage(pageNo, pageSize, businessTableId, name, remark);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增业务字段")
    public ResultData insert(@Valid JellyBusinessField businessField) {
        return businessFieldService.insert(businessField);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @SysLogPoint("修改业务字段")
    public ResultData update(@Valid JellyBusinessField businessField) {
        return businessFieldService.update(businessField);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除业务字段")
    public ResultData delete(@PathVariable Long id) {
        return businessFieldService.delete(id);
    }

    /**
     * 查询全部
     */
    @GetMapping("/queryByTableId/{tableId}")
    public ResultData queryByTableId(@PathVariable("tableId") Long tableId) {
        return businessFieldService.queryByTableId(tableId);
    }
    
    /**
     * 导入
     */
    @PostMapping("/import")
    public ResultData importHandle(@RequestParam("file") MultipartFile file, Long tableId) {
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        params.setNeedVerify(true);
        try {
            ExcelImportResult<FieldModel> result = ExcelImportUtil.importExcelMore(
                    file.getInputStream(),
                    FieldModel.class,
                    params);
            Map<String, Object> repeatMap = new HashMap<>();
            List<FieldModel> resultList = result.getList();
            for (int i = 0; i < resultList.size(); i++) {
                if (repeatMap.containsKey(resultList.get(i).getName())) {
                    return ResultData.warn(ResultCode.OTHER_ERROR, "字段名重复,请检查:" + resultList.get(i).getName());
                } else {
                    repeatMap.put(resultList.get(i).getName(), 1);
                }
            }
            //判断是否有错误
            if (result.isVerifyFail()) {
                for (FieldModel entity : result.getFailList()) {
                    return ResultData.warn(ResultCode.OTHER_ERROR, "第" + entity.getRowNum() + "行的错误是：" + entity.getErrorMsg());
                }
            } else {
                //获到正确的数据
            	return businessFieldService.importHandle(tableId, resultList);
            }
            return ResultData.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultData.warn(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

}
