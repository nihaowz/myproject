package com.kuang.config.securityConfig;

import com.kuang.config.jwtConfig.*;
import com.kuang.pojo.Admin;
import com.kuang.service.IAdminService;
import com.kuang.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IAdminService adminService;

    @Autowired
    IRoleService roleService;

    @Autowired
    RestfulAccessDeniedHandle restfulAccessDeniedHandle;

    @Autowired
    RestAuthorizationEnrtyPoint restAuthorizationEnrtyPoint;

    @Autowired
    CustomFilter customFilter;
    @Autowired
    CustomUrlManager customUrlManager;

    //认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(getPasswordEncoder());
        //密码匹配，走自己的loadUserByName
    }

    //哪些路径可以开启用
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/admin/login",
                "/admin/logout",
                "/admin/getCode",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",   //swagger路径
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/admin/register",
                "/ws/chat"
        );
    }

    //授权


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //关闭crsf
                //不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //其他都要拦截
                .anyRequest()
                .authenticated()
                //动态拦截
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlManager);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                })
                .and()
                //缓存都用不到
                .headers()
                .cacheControl();
        //添加拦截器
        http.addFilterBefore(getJwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权未定义结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandle)
                .authenticationEntryPoint(restAuthorizationEnrtyPoint);
    }

    //重写loadUserByUsername方法
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Admin admin = adminService.getAdminByName(username);
            if (admin == null) {
                return null;
            } else {
                admin.setRoles(roleService.getRoles(admin.getId()));
                return admin;
            }
        };
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter getJwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

}
