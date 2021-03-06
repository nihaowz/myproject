package com.kuang.controller;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.kuang.config.kaptchaConfig.KaptchaConfig;
import com.kuang.pojo.Admin;
import com.kuang.pojo.AdminRole;
import com.kuang.pojo.Role;
import com.kuang.service.IAdminRoleService;
import com.kuang.service.IAdminService;
import com.kuang.service.IRoleService;
import com.kuang.utils.Response;
import com.kuang.vo.AdminLoginVO;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

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
@Api(tags = "Admin-Controller")
@Slf4j
public class AdminController {


    @Autowired
    private IAdminRoleService adminRoleService;

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private PasswordEncoder passwordEncoder;





    @ApiOperation("注册接口")
    @PostMapping("/register")
    public Response register(@RequestBody Admin admin,HttpServletResponse httpServletResponse){
       return iAdminService.register(admin,httpServletResponse);

    }


    @ApiOperation(value = "登录接口,返回jwt令牌")
    @PostMapping("/login")
//    添加验证码进行登录
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
        admin.setPassword(null);
        admin.setRoles(roleService.getRoles(admin.getId()));
        return Response.success("获取成功",admin);
    }

    @ApiOperation(value = "验证码的实现")
    @GetMapping(value = "/getCode", produces = "image/jpeg")
    public void getKaptcha(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        /**
         * Cache-Control指定请求和响应遵循的缓存机制
         * no-store:用于防止重要的信息被无意的发布。在请求消息中发送将使得请求和响应消息都不使用缓存。
         * no-cache:指示请求或响应消息不能缓存
         */
        httpServletResponse.setHeader("Cache-Control","no-store,no-cache");

        httpServletResponse.setContentType("image/jpeg");


        ///生成文字代码
        String text = defaultKaptcha.createText();
        //生成图片验证码
        BufferedImage code = defaultKaptcha.createImage(text);

        //把验证码到session中
        httpServletRequest.getSession().setAttribute("kaptcha",text);

        ServletOutputStream outputStream = null;

        try {
            outputStream = httpServletResponse.getOutputStream();
            ImageIO.write(code,"jpg",outputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
        IOUtils.closeQuietly(outputStream);

    }


    @ApiOperation(value = "根据用户id查询用户角色")
    @GetMapping("/getRoles")
    public List<Role> getRoles(Integer adminId){
        return roleService.getRoles(adminId);
    }



    @ApiOperation(value = "更新用户头像")
    @PostMapping("/admin/userFace")
    public Response updateAdminFace(MultipartFile multipartFile, Integer id, Authentication authentication) throws IOException {
        log.info(multipartFile.getName());
        return iAdminService.updateAdminFace(multipartFile.getBytes(),multipartFile.getOriginalFilename(),id,authentication);
    }


}
