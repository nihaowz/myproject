package com.kuang.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

//前端传递登录参数
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "登录参数对象",description = "")
//链式编程
@Accessors(chain = true)
public class AdminLoginVO {
    //必须填写
    @ApiModelProperty(value = "登录账号",required = true )
    private String username;

    @ApiModelProperty(value = "登录密码",required = true)
    private String password;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}
