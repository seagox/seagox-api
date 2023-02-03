package com.seagull.oa.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagull.oa.sys.entity.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 操作日记
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    public List<Map<String, Object>> queryList(@Param("companyId") Long companyId, @Param("account") String account, @Param("name") String name);

}
