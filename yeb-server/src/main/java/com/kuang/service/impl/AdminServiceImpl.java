package com.kuang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.config.jwtConfig.JwtTokenUtils;
import com.kuang.mapper.AdminMapper;
import com.kuang.pojo.Admin;
import com.kuang.pojo.Role;
import com.kuang.service.IAdminService;
import com.kuang.utils.Response;
import com.kuang.vo.AdminLoginVO;
import javafx.beans.value.ObservableStringValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
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

    @Autowired
    AdminMapper adminMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public Response login(AdminLoginVO adminLogin, HttpServletRequest httpRequest) {
        //可以从请求头里面拿到code
        String requestCode = (String) httpRequest.getSession().getAttribute("kaptcha");
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminLogin.getUsername());
        if(StringUtils.isEmpty(requestCode) || !requestCode.equalsIgnoreCase(adminLogin.getCode())){
            return Response.fail("登录失败，验证码输入错误");
        }
        //匹配失败 或者details获取为空
        if (!userDetails.getUsername().equals(adminLogin.getUsername()) || !passwordEncoder.matches(adminLogin.getPassword(), userDetails.getPassword()) || userDetails == null) {
            return Response.fail("密码不匹配或者账户不匹配");
        }
        //另外就是判断对其账号是否禁用
        if (!userDetails.isEnabled()) {
            return Response.fail("账号已经被禁用，请联系管理员");
        }
        //必须对security中的上下文用户信息放入全文中，不然可能会出现一些问题
        //用于更新登录用户的信息
        //第一个填写userDetils 第二个是填写密码
        //第三个是填写你所有的授权
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        //返回令牌
        String token = jwtTokenUtils.generateToken(userDetails);

        Map<String, String> map = new HashMap<>();

        map.put("token", token);

        map.put("tokenHead", tokenHead);

        //返回给前端
        return Response.success("登录成功", map);
    }

    //根据用户名查找到admin对象
    @Override
    public Admin getAdminByName(String username) {
        //运用lambda表达式的形式
        //查询出来的映射对象
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();

        if (!StringUtils.isEmpty(username)) {
            queryWrapper.eq(Admin::getUsername, username);
        }

        queryWrapper.eq(Admin::getEnabled, true);

        Admin admin = adminMapper.selectOne(queryWrapper);

        if (admin == null) {
            return null;
        } else {
            return admin;
        }
    }


}
