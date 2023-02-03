package com.seagox.oa.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seagox.oa.sys.entity.SysCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 公司
 */
public interface CompanyMapper extends BaseMapper<SysCompany> {

    /**
     * 查询全部
     */
    public List<Map<String, Object>> queryAll();

    /**
     * 查询公司
     */
    public List<Map<String, Object>> queryByIds(@Param("array") String[] array, @Param("ids") String ids);

    /**
     * 查询全部通过编码前缀
     */
    public List<Map<String, Object>> queryByCompanyId(String prefix);

    /**
     * 查询最大编码
     */
    public String queryMaxCode(@Param("prefix") String prefix, @Param("digit") int digit);

}
