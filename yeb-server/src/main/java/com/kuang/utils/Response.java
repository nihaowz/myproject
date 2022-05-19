package com.kuang.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private long code; //返回的状态码
    private String message; //返回的信息
    private T Object;

    //成功返回
    public static <T>Response<T> success(String message){
//        return new Response<T>(200,message,null);
        return  success(message,null);
    }
    //带返回对象
    public static <T>Response<T> success(String message,T object){
        return new Response<T>(200,message,object);
    }
    public static  <T>Response<T> fail(String message){
        return new Response<T>(500,message,null);
    }
    public static  <T>Response<T> fail(String message,T object){
        return new Response<T>(500,message,object);
    }

}
