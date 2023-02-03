package com.seagox.oa.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.sys.entity.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 部门
 */
public interface DepartmentMapper extends BaseMapper<SysDepartment> {

    /**
     * 查询全部通过公司id
     *
     * @param companyId 公司id
     */
    public List<Map<String, Object>> queryByCompanyId(Long companyId);

    /**
     * 查询全部通过公司id
     *
     * @param prefix 前缀
     */
    public List<Map<String, Object>> queryByCode(String prefix);

    /**
     * 查询最大编码
     */
    public String queryMaxCode(@Param("companyId") Long companyId, @Param("prefix") String prefix, @Param("digit") int digit);

}
