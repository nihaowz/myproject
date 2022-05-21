package com.kuang.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuang.pojo.MenuRole;
import com.kuang.pojo.Role;
import com.kuang.service.IMenuRoleService;
import com.kuang.service.IRoleService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.support.RestGatewaySupport;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kuang
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/menu-role")
@Api(tags = "Menu-Role-Controller")
public class MenuRoleController {

    @Autowired
    private IMenuRoleService menuRoleService;
    /**
     * 更改某一个角色对应的菜单或者添加某一个角色对应的菜单
     */
    @PostMapping("/addOrUpdateMenuRole")
    @ApiOperation("更改角色或者添加角色的菜单")
    public Response addOrUpdateMenuRole(Integer roleId, Integer[] menuIds){
        //找到所有的roleId对应的东西，然后进行删除
        //最后在批量增加
        List<MenuRole> menuRoles = menuRoleService.selectDeleteByRoleId(roleId);
        List<Integer> menuRoleIds = menuRoles.stream().map(MenuRole::getId).collect(Collectors.toList());
        menuRoleService.removeByIds(menuRoleIds);
        //批量添加
        boolean b = menuRoleService.addBatchMenuRole(roleId, menuIds);
        if(b){
            return Response.success("添加成功");
        }else{
            return Response.fail("添加失败");
        }
    }

}
