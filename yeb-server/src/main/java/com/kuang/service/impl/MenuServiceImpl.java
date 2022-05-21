package com.kuang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.mapper.MenuMapper;
import com.kuang.pojo.Admin;
import com.kuang.pojo.Menu;
import com.kuang.service.IMenuService;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {


    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    /**
     * 根据用户id查询出所有的菜单
     * @return
     */
    @Override
    public List<Menu> getMenuByAdminId() {
        //从security中过去到admin对象
         Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Menu> menus = (List<Menu>)redisTemplate.opsForValue().get("menu_" + admin.getId());


        if(!Collections.isEmpty(menus)){
            return menus;
        }else{
            menus = menuMapper.getMenuByAdminId(admin.getId());
            redisTemplate.opsForValue().set("menu_"+admin.getId(),menus);
        }

        return menus;
    }

    @Override
    public List<Menu> getMenusFromRole() {
       return menuMapper.getMenusFromRole();
    }

    /**
     * 查询出所有的菜单
     * @return
     */
    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }


}
