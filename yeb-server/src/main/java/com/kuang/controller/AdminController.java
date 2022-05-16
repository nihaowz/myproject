package com.kuang.controller;


import com.kuang.pojo.Admin;
import com.kuang.service.IAdminService;
import com.kuang.utils.Response;
import com.kuang.vo.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author kuang
 * @since 2022-05-16
 */
@RestController
@RequestMapping("/admin")
@Api(tags = "admin-controller")
public class AdminController {

    @Autowired
    IAdminService iAdminService;

    @ApiOperation(value = "登录接口,返回jwt令牌")
    @PostMapping("/login")
    public Response login(@RequestBody AdminLoginVO adminLogin, HttpServletRequest httpRequest) {
        return iAdminService.login(adminLogin, httpRequest);
    }

    @ApiOperation(value = "退出功能")
    @PostMapping("/logout")
    public Response logout() {
        //根据前端直接把请求头删除
        //然后就达到了退出的功能
        return Response.success("退出成功");
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/getAdminInfo")
    //设置到了全局对象
    public Response getAdminInfo(Principal principal) {
        String username = principal.getName();
        if (principal == null) {
            return null;
        }
        Admin admin = iAdminService.getAdminByName(username);
        if(admin == null){
            return Response.fail("获取失败");
        }
        return Response.success("获取成功",admin);
    }

}
