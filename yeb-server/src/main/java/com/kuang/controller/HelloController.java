package com.kuang.controller;

import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试接口")
@RequestMapping("/test")
public class HelloController {
    @ApiOperation(value = "测试接口")
    @GetMapping("/hello")
    public Response hello(){
        return Response.success("hello","hello");
    }
}
