package com.kuang.controller;

import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "Hello-Controller")
public class HelloController {
    @ApiOperation(value = "测试接口")
    @GetMapping("/hello")
    public Response hello(){
        return Response.success("hello","hello");
    }

    @ApiOperation(value = "测试接口1")
    @GetMapping("/employee/basic/hello")
    public Response hello1(){
        return Response.success("hello1","hello1");
    }
    @ApiOperation(value = "测试接口2")
    @GetMapping("/employee/advanced/hello")
    public Response hello2(){
        return Response.success("hello2","hello2");
    }

}
