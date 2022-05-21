package com.kuang.controller;


import com.kuang.pojo.Nation;
import com.kuang.service.INationService;
import com.kuang.service.impl.NationServiceImpl;
import com.kuang.utils.Response;
import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-19
 */
@RestController
@RequestMapping("/nation")
@Api(tags = "Nation-Controller")
public class NationController {

    @Autowired
    private INationService nationService;

    @ApiOperation(value = "查询所有的名族")
    @GetMapping("/getAllNation")
    public Response getAllNation(){
        List<Nation> list = nationService.list();
        if(Collections.isEmpty(list)){
            return Response.fail("查询失败");
        }else{
            return Response.success("查询成功",list);
        }
    }


}
