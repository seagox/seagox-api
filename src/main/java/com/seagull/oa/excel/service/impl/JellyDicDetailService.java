package com.seagull.oa.excel.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seagull.oa.common.ResultCode;
import com.seagull.oa.common.ResultData;
import com.seagull.oa.excel.entity.JellyDicDetail;
import com.seagull.oa.excel.mapper.JellyDicDetailMapper;
import com.seagull.oa.excel.mapper.JellyTableColumnMapper;
import com.seagull.oa.excel.service.IJellyDicDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JellyDicDetailService implements IJellyDicDetailService {

    @Autowired
    private JellyDicDetailMapper dicDetailMapper;

    @Autowired
    private JellyTableColumnMapper tableColumnMapper;

    /**
     * 分页查询
     */
    @Override
    public ResultData queryByPage(Integer pageNo, Integer pageSize, Long classifyId) {
        PageHelper.startPage(pageNo, pageSize);
        LambdaQueryWrapper<JellyDicDetail> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyDicDetail::getClassifyId, classifyId)
                .orderByAsc(JellyDicDetail::getSort);
        List<JellyDicDetail> list = dicDetailMapper.selectList(qw);
        PageInfo<JellyDicDetail> pageInfo = new PageInfo<JellyDicDetail>(list);
        return ResultData.success(pageInfo);
    }

    @Override
    public ResultData queryDisplay(Long classifyId) {
        LambdaQueryWrapper<JellyDicDetail> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyDicDetail::getClassifyId, classifyId)
                .orderByAsc(JellyDicDetail::getSort);
        return ResultData.success(dicDetailMapper.selectList(qw));
    }

    @Transactional
    @Override
    public ResultData insert(JellyDicDetail dicDetail) {
        LambdaQueryWrapper<JellyDicDetail> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyDicDetail::getClassifyId, dicDetail.getClassifyId())
                .eq(JellyDicDetail::getCode, dicDetail.getCode());
        int count = dicDetailMapper.selectCount(qw);
        if (count > 0) {
            return ResultData.warn(ResultCode.PARAMETER_ERROR, "字典值已经存在");
        }
        dicDetailMapper.insert(dicDetail);

        LambdaQueryWrapper<JellyDicDetail> qwAll = new LambdaQueryWrapper<>();
        qwAll.eq(JellyDicDetail::getClassifyId, dicDetail.getClassifyId());
        tableColumnMapper.updateByFormatter(dicDetail.getClassifyId(), JSON.toJSONString(dicDetailMapper.selectMaps(qwAll)));
        return ResultData.success(null);
    }

    @Transactional
    @Override
    public ResultData update(JellyDicDetail dicDetail) {
        JellyDicDetail originalDicDetail = dicDetailMapper.selectById(dicDetail.getId());
        if (originalDicDetail.getCode().equals(dicDetail.getCode())) {
            dicDetailMapper.updateById(dicDetail);

            LambdaQueryWrapper<JellyDicDetail> qwAll = new LambdaQueryWrapper<>();
            qwAll.eq(JellyDicDetail::getClassifyId, dicDetail.getClassifyId());
            tableColumnMapper.updateByFormatter(dicDetail.getClassifyId(), JSON.toJSONString(dicDetailMapper.selectMaps(qwAll)));
            return ResultData.success(null);
        } else {
            LambdaQueryWrapper<JellyDicDetail> qw = new LambdaQueryWrapper<>();
            qw.eq(JellyDicDetail::getClassifyId, dicDetail.getClassifyId())
                    .eq(JellyDicDetail::getCode, dicDetail.getCode());
            int count = dicDetailMapper.selectCount(qw);
            if (count == 0) {
                dicDetailMapper.updateById(dicDetail);

                LambdaQueryWrapper<JellyDicDetail> qwAll = new LambdaQueryWrapper<>();
                qwAll.eq(JellyDicDetail::getClassifyId, dicDetail.getClassifyId());
                tableColumnMapper.updateByFormatter(dicDetail.getClassifyId(), JSON.toJSONString(dicDetailMapper.selectMaps(qwAll)));
                return ResultData.success(null);
            } else {
                return ResultData.warn(ResultCode.PARAMETER_ERROR, "字典值已经存在");
            }
        }
    }


    @Override
    public ResultData delete(Long id) {
        dicDetailMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryByClassifyId(Long classifyId) {
        LambdaQueryWrapper<JellyDicDetail> qw = new LambdaQueryWrapper<>();
        qw.eq(JellyDicDetail::getClassifyId, classifyId)
                .orderByAsc(JellyDicDetail::getSort);
        List<JellyDicDetail> list = dicDetailMapper.selectList(qw);
        return ResultData.success(list);
    }

}
