package com.kuang.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.config.jwtConfig.JwtTokenUtils;
import com.kuang.mapper.AdminMapper;
import com.kuang.mapper.AdminRoleMapper;
import com.kuang.mapper.MailLogMapper;
import com.kuang.pojo.Admin;
import com.kuang.pojo.AdminRole;
import com.kuang.pojo.MailLog;
import com.kuang.pojo.Role;
import com.kuang.service.IAdminService;
import com.kuang.utils.MailConstants;
import com.kuang.utils.Response;
import com.kuang.vo.AdminLoginVO;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kuang
 * @since 2022-05-16
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {


    //根据userDeatilsService找到userDetails获取到相关的用户信息
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;


    @Autowired
    private MailLogMapper mailLogMapper;

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
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
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
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(username)) {
            queryWrapper.eq("username", username);
        }

        queryWrapper.eq("enabled", true);

        Admin admin = adminMapper.selectOne(queryWrapper);

        if (admin == null) {
            return null;
        } else {
            return admin;
        }
    }

    /**
     * 根据返回的注册信息写入数据库，返回token
     * @param admin
     * @param httpServletResponse
     * @return
     */
    @Override
    public Response register(Admin admin, HttpServletResponse httpServletResponse) {
        String password = admin.getPassword();
        String enPassword = passwordEncoder.encode(password);
        admin.setPassword(enPassword);
        int rowCountAdmin = adminMapper.insert(admin);
        AdminRole adminRole = new AdminRole(admin.getId(),8);
        int rowCountAdminRole = adminRoleMapper.insert(adminRole);
        List<Role> roles = adminMapper.getRoles(admin.getId());
        admin.setRoles(roles);
        //注册成功则发送邮件
        log.info("注册成功：{}",admin.getUsername());
        if(rowCountAdminRole==1 && rowCountAdmin == 1){
//            //使用默认的交换机，通过路由key跳转到相应的queue上，然后对其消息进行消费。
//            rabbitTemplate.convertAndSend("mail",admin);
            //生成uuid
            String uuid = UUID.randomUUID().toString();

//            uuid, admin.getId(), MailConstants.DELIVERY_PREPARE, MailConstants.ROUTING_KEY, MailConstants.EXCHANGE, MailConstants.MAX_RETRY_COUNT, LocalDateTime.now().plusMinutes(MailConstants.RETRY_TIME
            MailLog mailLog = new MailLog(uuid,admin.getId(),MailConstants.DELIVERY_PREPARE,MailConstants.ROUTING_KEY,MailConstants.EXCHANGE,0
                                         ,LocalDateTime.now().plusMinutes(MailConstants.RETRY_TIME),LocalDateTime.now(),null);

            mailLogMapper.insert(mailLog);

            rabbitTemplate.convertAndSend(MailConstants.EXCHANGE,MailConstants.ROUTING_KEY,admin,new CorrelationData(uuid));

        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(admin, null, admin.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        String token = jwtTokenUtils.generateToken(admin);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("tokenHead", tokenHead);
        //返回给前端
        return Response.success("注册成功", map);
    }

    @Override
    public Response updateAdminFace(byte[] bytes, String name, Integer id, Authentication authentication) {
        //得到他的后缀
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        log.info("查看他的后缀======>{}"+suffix);
        StorePath storePath = fastFileStorageClient.uploadFile(bytes, suffix);
        //得到他的存储路径
        log.info("id为{}，他的图片的存储路径是{}",id,storePath.getFullPath());
        String path = "http://42.192.56.50:8080/"+storePath.getFullPath();
        Admin admin = adminMapper.selectOne(new QueryWrapper<Admin>().eq("id", id));
        admin.setUserFace(path);
        int rowCount = adminMapper.updateById(admin);
        if(rowCount== 1){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(admin, null, authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            return Response.success("更新成功",path);
        }
        return Response.fail("更新失败");
    }


}
