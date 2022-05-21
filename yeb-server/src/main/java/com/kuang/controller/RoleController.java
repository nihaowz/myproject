package com.kuang.controller;


import com.kuang.pojo.Role;
import com.kuang.service.IRoleService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
@RequestMapping("/role")
@Api(tags = "Role-Controller")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("/addRole")
    @ApiOperation("添加角色")
    public Response addRole(@RequestBody Role role){
        if(!"ROLE_".startsWith(role.getName())){
           role.setName("ROLE_"+role.getName());
           //符合设计规范
        }
        roleService.save(role);
        return Response.success("添加成功");
    }
    @PostMapping("/addBatchRole")
    @ApiOperation("批量添加角色")
    public Response addBatchRole(@RequestBody List<Role> roles){
        for (Role role : roles) {
            if(!"ROLE_".startsWith(role.getName())){
                role.setName("ROLE_"+role.getName());
                //符合设计规范
            }
        }
        roleService.saveBatch(roles);
        return Response.success("批量添加成功");
    }
    @DeleteMapping("/deleteRole/{id}")
    @ApiOperation("删除角色")
    public Response deleteRole(@PathVariable Integer id){
        roleService.removeById(id);
        return Response.success("删除成功");
    }
    @DeleteMapping("/deleteBatchRole")
    @ApiOperation("批量删除角色")
    public Response deleteBatchRole(Integer[] ids){
        roleService.removeByIds(Arrays.asList(ids));
        return Response.success("批量删除成功");
    }
    @PostMapping("/queryAllRole")
    @ApiOperation("查询所有角色")
    public Response queryAllRole(){
        List<Role> roles = roleService.list();
        return Response.success("查询成功",roles);
    }




}
