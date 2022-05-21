package com.kuang.controller;


import com.kuang.pojo.Salary;
import com.kuang.service.ISalaryService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-21
 */
@RestController
@RequestMapping("/salary/sob")
@Api(tags = "Salary-Controller")
public class SalaryController {

    @Autowired
    ISalaryService salaryService;

    @ApiOperation("获取所有的工资")
    @GetMapping("/getAllSalary")
    public Response getAllSalary(){
        List<Salary> salaryList = salaryService.list();
        return Response.success("查询成功",salaryList);
    }
    @ApiOperation("修改某一个人的工资")
    @PutMapping("/updateSalary")
    public Response getAllSalary(@RequestBody Salary salary){
        salaryService.updateById(salary);
        return Response.success("修改成功");
    }
    @ApiOperation("添加某一人的工资")
    @PostMapping("/addSalary")
    public Response addSalary(@RequestBody Salary salary){
       salaryService.save(salary);
       return Response.success("添加成功");
    }
    @ApiOperation("删除某一个的工资")
    @DeleteMapping("/deleteSalary/{id}")
    public Response deleteSalary(@PathVariable Integer id){
        salaryService.removeById(id);
        return Response.success("删除成功");
    }

}
