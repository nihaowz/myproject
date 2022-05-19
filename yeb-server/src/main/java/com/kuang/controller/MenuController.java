package com.kuang.controller;


import com.kuang.pojo.Menu;
import com.kuang.service.IMenuService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kuang
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "Menu-Controller")
public class MenuController {

    @Autowired
    IMenuService iMenuService;
    /**
     * 根据id查询所有的菜单
     */
    @ApiOperation(value = "根据用户id查询所有的菜单")
    @GetMapping("/getMenuById")
    public Response getMenuById(){
        //id在后端就有，不需要传入用户id
        List<Menu> menus = iMenuService.getMenuByAdminId();
        return Response.success("查询成功",menus);
    }
    /**
     * 根据角色查询所有的菜单
     *
     */
    @ApiOperation(value = "根据角色查询所有的菜单")
    @GetMapping("/getMenusFromRole")
    public Response getMenusFromRole(){
       return Response.success("查询成功",iMenuService.getMenusFromRole());
    }

    @GetMapping("/getAllMenu")
    @ApiOperation("查询出所有的菜单")
    public Response getAllMenu(){
       return Response.success("查询成功",iMenuService.getAllMenu());
    }







}
