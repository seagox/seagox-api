package com.seagox.oa.excel.controller;

import com.seagox.oa.common.ResultData;
import com.seagox.oa.excel.entity.JellyDoor;
import com.seagox.oa.excel.service.IJellyDoorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 门户
 */
@RestController
@RequestMapping("/door")
public class JellyDoorController {

    @Autowired
    private IJellyDoorService doorService;

    /**
     * 分页查询
     *
     * @param pageNo    起始页
     * @param pageSize  每页大小
     * @param companyId 公司id
     */
    @GetMapping("/queryByPage")
    public ResultData queryByPage(@RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, Long companyId) {
        return doorService.queryByPage(pageNo, pageSize, companyId);
    }

    /**
     * 新增
     */
    @PostMapping("/insert")
    public ResultData insert(@Valid JellyDoor door) {
        return doorService.insert(door);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultData update(@Valid JellyDoor door) {
        return doorService.update(door);
    }

    /**
     * 删除
     */
    @PostMapping("/delete/{id}")
    public ResultData delete(@PathVariable Long id) {
        return doorService.delete(id);
    }

    /**
     * 信息
     */
    @GetMapping("/queryById/{id}")
    public ResultData queryById(@PathVariable Long id, Long userId) {
        return doorService.queryById(id, userId);
    }

    /**
     * 统计分析
     */
    @GetMapping("/queryAnalysis")
    public ResultData queryAnalysis(Long companyId, String userId) {
        return doorService.queryAnalysis(companyId, userId);
    }

    /**
     * 执行sql
     */
    @PostMapping("/execute")
    public ResultData execute(HttpServletRequest request, Long userId, Long id, String key) {
        return doorService.execute(request, userId, id, key);
    }

}
