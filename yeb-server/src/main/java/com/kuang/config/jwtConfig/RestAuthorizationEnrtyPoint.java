package com.kuang.config.jwtConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuang.utils.Response;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@Component
//未登录或者未授权的访问接口，返回自定义的结果
public class RestAuthorizationEnrtyPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();

        Response<Object> fail = Response.fail("登录失效，请重新登录");

        fail.setCode(401);

        writer.write(new ObjectMapper().writeValueAsString(fail));  //写到浏览器

        writer.flush(); //强行推到浏览器

        writer.close();


    }
}
