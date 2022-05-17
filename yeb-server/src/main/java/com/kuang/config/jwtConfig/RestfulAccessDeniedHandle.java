package com.kuang.config.jwtConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuang.utils.Response;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

//登录之后，但是权限不够
@Component
public class RestfulAccessDeniedHandle implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        PrintWriter printWriter = response.getWriter();

        Response<Object> fail = Response.fail("权限不足，请联系管理员");

        printWriter.write(new ObjectMapper().writeValueAsString(fail));

        //强行推到浏览器
        printWriter.flush();

        printWriter.close();

    }
}
