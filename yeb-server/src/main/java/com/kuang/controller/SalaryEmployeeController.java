package com.kuang.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kuang.pojo.Employee;
import com.kuang.service.IEmployeeService;
import com.kuang.utils.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary/sobcfg/")
public class SalaryEmployeeController {

    @Autowired
    IEmployeeService employeeService;

    @ApiOperation("获取所有员工的工资")
    @GetMapping("/")
    public Response getAllEmployeeBySalary(@RequestParam(defaultValue = "1") Integer currentPage,
                                           @RequestParam(defaultValue = "10")Integer size){

       return employeeService.getAllEmployeeBySalary(currentPage,size);

    }

    @ApiOperation(value = "更新员工账号")
    @PutMapping("/")
    public Response updateEmployeeBySalary(Integer eid,Integer sid){
        boolean update = employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid));
        if(update){
            return Response.success("更新成功");
        }else{
            return Response.fail("更新失败");
        }
    }
}
