package com.seagox.oa.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seagox.oa.common.ResultCode;
import com.seagox.oa.common.ResultData;
import com.seagox.oa.sys.entity.SysCompany;
import com.seagox.oa.sys.entity.SysMenu;
import com.seagox.oa.sys.entity.SysRole;
import com.seagox.oa.sys.entity.SysUserRelate;
import com.seagox.oa.sys.mapper.CompanyMapper;
import com.seagox.oa.sys.mapper.MenuMapper;
import com.seagox.oa.sys.mapper.RoleMapper;
import com.seagox.oa.sys.mapper.UserRelateMapper;
import com.seagox.oa.sys.service.IMenuService;
import com.seagox.oa.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
public class MenuService implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserRelateMapper userRelateMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 查询全部通过公司id
     */
    @Override
    public ResultData queryByCompanyId(Long companyId, int status) {
        SysCompany company = companyMapper.selectById(companyId);
        List<Map<String, Object>> list = menuMapper.queryByCode(company.getCode().substring(0, 4), status);
        return ResultData.success(TreeUtils.categoryTreeHandle(list, "parentId", 0L));
    }

    @Override

    public ResultData insert(SysMenu menu) {
        menuMapper.insert(menu);
        return ResultData.success(null);
    }


    @Override
    public ResultData update(SysMenu menu) {
        menuMapper.updateById(menu);
        return ResultData.success(null);
    }


    @Override
    public ResultData delete(Long id) {
        LambdaQueryWrapper<SysMenu> qw = new LambdaQueryWrapper<>();
        qw.eq(SysMenu::getParentId, id);
        int count = menuMapper.selectCount(qw);
        if (count != 0) {
            return ResultData.warn(ResultCode.OTHER_ERROR, "存在子菜单，不可删除");
        }
        menuMapper.deleteById(id);
        return ResultData.success(null);
    }

    @Override
    public ResultData queryUserMenu(Long companyId, Long userId) {
        LambdaQueryWrapper<SysUserRelate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRelate::getUserId, userId)
                .eq(SysUserRelate::getCompanyId, companyId);
        SysUserRelate userRelate = userRelateMapper.selectOne(queryWrapper);
        if (userRelate != null) {
            String roleStr = "";
            if (!StringUtils.isEmpty(userRelate.getRoleIds())) {
                String[] roleArray = userRelate.getRoleIds().split(",");
                for (int i = 0; i < roleArray.length; i++) {
                    SysRole role = roleMapper.selectById(roleArray[i]);
                    if (StringUtils.isEmpty(roleStr)) {
                        roleStr = roleStr + role.getPath();
                    } else {
                        roleStr = roleStr + "," + role.getPath();
                    }
                }
            }
            if (!StringUtils.isEmpty(roleStr)) {
                return ResultData.success(TreeUtils.categoryTreeHandle(menuMapper.queryUserMenu(roleStr.split(",")), "parentId", 0L));
            }
        }
        return ResultData.success(null);
    }

    @Override
    public ResultData queryQuickAccess(Long companyId, Long userId) {
        LambdaQueryWrapper<SysUserRelate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRelate::getUserId, userId)
                .eq(SysUserRelate::getCompanyId, companyId);
        SysUserRelate userRelate = userRelateMapper.selectOne(queryWrapper);
        if (userRelate != null) {
            String roleStr = "";
            if (!StringUtils.isEmpty(userRelate.getRoleIds())) {
                String[] roleArray = userRelate.getRoleIds().split(",");
                for (int i = 0; i < roleArray.length; i++) {
                    SysRole role = roleMapper.selectById(roleArray[i]);
                    if (StringUtils.isEmpty(roleStr)) {
                        roleStr = roleStr + role.getPath();
                    } else {
                        roleStr = roleStr + "," + role.getPath();
                    }
                }
            }
            if (!StringUtils.isEmpty(roleStr)) {
                return ResultData.success(menuMapper.queryQuickAccess(roleStr.split(",")));
            }
        }
        return ResultData.success(null);
    }

}
