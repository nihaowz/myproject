package com.kuang.config.jwtConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//jwt前置拦截器
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private  String tokenHeader;
    @Value("${jwt.tokenHead}")
    private  String tokenHead;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authTokenHeader = request.getHeader(tokenHeader);
        if (authTokenHeader != null && authTokenHeader.startsWith(tokenHead)) {
            String token = authTokenHeader.substring(tokenHead.length()+1);
            // key ： value
            // authentication ： beare jwt中的token
            String username = jwtTokenUtils.getUsernameFromToken(token);
            //用户可以查询出来，但是security中并没有相关的信息
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                //验证token是否有效，重新设置用户对象
                if(jwtTokenUtils.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
