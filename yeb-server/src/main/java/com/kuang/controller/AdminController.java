package com.kuang.controller;


import com.kuang.service.IAdminService;
import com.kuang.utils.Response;
import com.kuang.vo.AdminLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
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
    public Response login(@RequestBody AdminLoginVO adminLogin, HttpRequest httpRequest){
        return iAdminService.login(adminLogin,httpRequest);
    }

}
