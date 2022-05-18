package com.kuang.controller;


import com.kuang.pojo.Joblevel;
import com.kuang.service.IJoblevelService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ClientInfoStatus;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/joblevel")
@Api("JoblevelController")
public class JoblevelController {

    @Autowired
    IJoblevelService joblevelService;

    @ApiOperation(value = "添加职位级别")
    @PostMapping("/addJobLevel")
    public Response addJobLevel(@RequestBody Joblevel joblevel){
        joblevelService.save(joblevel);
        return Response.success("职位添加成功");
    }

    @ApiOperation(value = "查询所有的职位级别")
    @PostMapping("/getAllJobLevel")
    public Response getAllJobLevel(){
        List<Joblevel> joblevels = joblevelService.list();
        return Response.success("查询成功", joblevels);
    }
    @ApiOperation(value = "删除职位级别")
    @DeleteMapping("/deleteJobLevel/{id}")
    public Response deleteJobLevel(@PathVariable Integer id){
        joblevelService.removeById(id);
        return Response.success("删除成功");
    }
    @ApiOperation(value = "修改职位级别")
    @PutMapping("/updateJobLevel")
    public Response updateJobLevel(@RequestBody Joblevel joblevel){
        joblevelService.updateById(joblevel);
        return Response.success("修改成功");
    }
    @ApiOperation(value = "批量删除职位级别")
    @DeleteMapping("/deleteBatchJobLevel")
    public Response deleteBatchJobLevel(Integer[] ids){
        joblevelService.removeByIds(Arrays.asList(ids));
        return Response.success("批量删除成功");
    }

    @ApiOperation(value = "批量增加职位级别")
    @PostMapping("/addBatchJobLevel")
    public Response addBatchJobLevel(@RequestBody List<Joblevel> joblevels){
        joblevelService.saveBatch(joblevels);
        return Response.success("批量增加成功");
    }




}
