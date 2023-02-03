package com.seagull.oa.excel.controller;

import com.seagull.oa.annotation.SysLogPoint;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyTableColumn;
import com.seagull.oa.excel.service.IJellyTableColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 表字段
 */
@RestController
@RequestMapping("/tableColumn")
public class JellyTableColumnController {

    @Autowired
    private IJellyTableColumnService tableColumnService;

    /**
     * 查询所有
     */
    @GetMapping("/queryByClassifyId")
    public ResultData queryByClassifyId(Long classifyId) {
        return tableColumnService.queryByClassifyId(classifyId);
    }

    /**
     * 分页查询
     *
     * @param pageNo     起始页
     * @param pageSize   每页大小
     * @param classifyId 业务表id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long classifyId) {
        return tableColumnService.queryByPage(pageNo, pageSize, classifyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    @SysLogPoint("新增表格表头")
    public ResultData insert(@Valid JellyTableColumn tableColumn) {
        return tableColumnService.insert(tableColumn);
    }

    /**
     * 更新
     */
    @PostMapping("/update")
    @SysLogPoint("更新表格表头")
    public ResultData update(@Valid JellyTableColumn tableColumn) {
        return tableColumnService.update(tableColumn);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    @SysLogPoint("删除表格表头")
    public ResultData delete(@PathVariable Long id) {
        return tableColumnService.delete(id);
    }

}
