package com.kuang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.mapper.MenuRoleMapper;
import com.kuang.pojo.MenuRole;
import com.kuang.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kuang
 * @since 2022-05-17
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    MenuRoleMapper menuRoleMapper;

    @Override
    public List<MenuRole> selectDeleteByRoleId(Integer roleId) {
        QueryWrapper<MenuRole> menuRoleQueryWrapper = new QueryWrapper<>();
        menuRoleQueryWrapper.eq("rid",roleId);
        List<MenuRole> menuRoles = menuRoleMapper.selectList(menuRoleQueryWrapper);
        return menuRoles;
    }

    /**
     * 进行修改role和menu的对应关系
     * @param roleId 角色id
     * @param menuIds 菜单id
     * @return
     */
    @Override
    public boolean addBatchMenuRole(Integer roleId, Integer[] menuIds) {
        if(menuIds.length == 0){
            return true;
        }
       return menuRoleMapper.addBatchMenuRole(roleId,menuIds);
    }
}
