package com.kuang.config.jwtConfig;

import com.kuang.pojo.Menu;
import com.kuang.pojo.Role;
import com.kuang.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 根据请求的url拿到其应该拥有的权限
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {


    @Autowired
    IMenuService menuService;

   AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        String url = ((FilterInvocation) o).getRequestUrl();

        List<Menu> menus = menuService.getMenusFromRole();

        for (Menu menu : menus) {
            //判断其是否相等
            if(antPathMatcher.match(menu.getUrl(),url)){
                 //将对象转换为str
                String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                return SecurityConfig.createList(roles);
            }
        }
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
