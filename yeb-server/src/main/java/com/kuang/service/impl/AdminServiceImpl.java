package com.kuang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.config.jwtUtils.JwtTokenUtils;
import com.kuang.mapper.AdminMapper;
import com.kuang.pojo.Admin;
import com.kuang.service.IAdminService;
import com.kuang.utils.Response;
import com.kuang.vo.AdminLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.x509.FreshestCRLExtension;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kuang
 * @since 2022-05-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {


    //根据userDeatilsService找到userDetails获取到相关的用户信息
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public Response login(AdminLoginVO adminLogin, HttpRequest httpRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminLogin.getUsername());
        //匹配失败 或者details获取为空
        if(userDetails.getUsername().equals(adminLogin.getUsername())||!passwordEncoder.matches(adminLogin.getPassword(),userDetails.getPassword()) || userDetails == null){
            return Response.fail("密码不匹配或者账户不匹配");
        }
        //另外就是判断对其账号是否禁用
        if(!userDetails.isEnabled()){
            return Response.fail("账号已经被禁用，请联系管理员");
        }
        //必须对security中的上下文用户信息放入全文中，不然可能会出现一些问题


        //返回令牌
        String token = jwtTokenUtils.generateToken(userDetails);

        Map<String, String> map = new HashMap<>();

        map.put("token",token);

        map.put("tokenHead",tokenHead);

        //返回给前端
        return Response.success("登录成功",map);
    }
}
