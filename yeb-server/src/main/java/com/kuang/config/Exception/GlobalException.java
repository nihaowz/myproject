package com.kuang.config.Exception;


import com.kuang.utils.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
//@ControllerAdvice
//区别在是否返回json还是对象
//用于处理controller异常
//errorController是可以处理所有的异常包括没有进入controller的异常

/**
 * 定义全局异常
 */
public class GlobalException {


    @ExceptionHandler(SQLException.class)
    public Response mysqlException(SQLException sqlException){

        if(sqlException instanceof SQLIntegrityConstraintViolationException){
            return Response.fail("该数据有关联数据");
        }
        return Response.fail("数据库异常");

    }
}
