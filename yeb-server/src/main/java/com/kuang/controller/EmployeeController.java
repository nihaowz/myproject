package com.kuang.controller;


import com.kuang.pojo.Employee;
import com.kuang.service.IEmployeeService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/employee")
@Api(tags = "Employee-Controller")
public class EmployeeController {
    
    @Autowired
    IEmployeeService employeeService;
    
    
    @ApiOperation(value = "查询所有的员工信息")
    @GetMapping("/getAllEmployee")
    public Response getAllEmployee(){
        List<Employee> list = employeeService.list();
        if(!list.isEmpty()){
            return Response.success("查询成功",list);
        }else{
            return  Response.fail("查询错误");
        }
    }
    @ApiOperation(value = "通过id获取员工信息")
    @GetMapping("/getEmployeeById/{id}")
    public Response getEmployeeById(@PathVariable Integer id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return Response.success("查询成功",employee);
        }else{
            return  Response.fail("查询错误");
        }
    }
    @ApiOperation(value = "批量删除员工信息")
    @DeleteMapping("/deleteBatchEmployee")
    public Response deleteBatchEmployee(Integer[] ids){
        boolean b = employeeService.removeByIds(Arrays.asList(ids));
        if(b){
            return Response.success("批量删除成功");
        }else{
            return Response.fail("批量删除失败");
        }
    }
    @ApiOperation(value = "通过id删除某一个员工信息")
    @GetMapping("/deleteEmployeeById/{id}")
    public Response deleteEmployeeById(@PathVariable Integer id){
        boolean b = employeeService.removeById(id);
        if(b){
            return Response.success("删除成功");
        }else{
            return Response.fail("删除失败");
        }
    }
    @ApiOperation(value = "增加员工")
    @GetMapping("/addEmployee")
    public Response addEmployee(@RequestBody Employee employee){
        boolean b = employeeService.save(employee);
        if(b){
            return Response.success("增加成功");
        }else{
            return Response.fail("增加失败");
        }
    }

    @ApiOperation(value = "查询批量员工")
    @GetMapping("/addBatchEmployee")
    public Response addBatchEmployee(@RequestBody List<Employee> employees){
        boolean b = employeeService.saveBatch(employees);
        if(b){
            return Response.success("批量增加成功");
        }else{
            return Response.fail("批量增加失败");
        }
    }
    @ApiOperation(value = "查询批量员工")
    @GetMapping("/updateEmployee")
    public Response updateEmployee(@RequestBody Employee employee){
        boolean b = employeeService.updateById(employee);
        if(b){
            return Response.success("批量增加成功");
        }else{
            return Response.fail("批量增加失败");
        }
    }


    /**
     * 将所有的员工进行导出
     */

    /**
     * 将员工进行导入
     */




}
