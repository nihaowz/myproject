package com.kuang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.mapper.RoleMapper;
import com.kuang.pojo.Admin;
import com.kuang.pojo.Role;
import com.kuang.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<Role> getRoles(Integer adminId) {
//        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        如果处于没有登录状态则会导致出现 查询为空而报错
        return roleMapper.getRoles(adminId);
    }
}
