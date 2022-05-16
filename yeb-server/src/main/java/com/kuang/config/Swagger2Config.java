package com.kuang.config;


import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    //规定扫描那些包
    public Docket createDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kuang.controller")) //扫描哪些包
                .paths(PathSelectors.any())
                .build()
                //设置全局token用于测试
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("办公文档")
                .description("办公文档的开发")
                .contact(new Contact("xxx","http:localhost:8087//xxx.html","xxxxx.@qq.com"))
                .version("1.0")
                .build();
    }
    private List<ApiKey> securitySchemes(){
        //设置请求头
        List<ApiKey> list = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
        list.add(apiKey);
        return list;
    }
    private List<SecurityContext> securityContexts(){
        //设置需要获取权限的路径
        List<SecurityContext> list = new ArrayList<>();
        //哪些路径需要做登录认证
        list.add(getContextByPath("/test/hello/.*"));

        return list;
    }

    private SecurityContext getContextByPath(String path) {
        return SecurityContext.builder()
                //进行授权
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(path))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> list =  new ArrayList<>();

        AuthorizationScope authorizationScope = new AuthorizationScope("global", "allpass");

        AuthorizationScope[] authorizationScopes =  new AuthorizationScope[1];

        authorizationScopes[0] = authorizationScope;

        list.add(new SecurityReference("Authorization",authorizationScopes));

        return list;


    }

}

