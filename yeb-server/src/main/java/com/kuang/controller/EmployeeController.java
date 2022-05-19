package com.kuang.controller;


import com.kuang.pojo.Employee;
import com.kuang.service.IEmployeeService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
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


    /**
     * 分页查询
     * @param currentPage 当前页数
     * @param size 显示大小
     * @return
     */
    @ApiOperation(value = "查询所有的员工信息")
    @GetMapping("/getAllEmployeeByPage")
    public Response getAllEmployee(@RequestParam(defaultValue = "1")Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   Employee employee,
                                   LocalDate[] localDates){

        return employeeService.getEmployeeByPage(currentPage,size,employee,localDates);

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
    @PostMapping("/addEmployee")
    public Response addEmployee(@RequestBody Employee employee){
        return employeeService.addEmployee(employee);
    }

    @ApiOperation(value = "查询添加员工")
    @GetMapping("/addBatchEmployee")
    public Response addBatchEmployee(@RequestBody List<Employee> employees){
        boolean b = employeeService.saveBatch(employees);
        if(b){
            return Response.success("批量增加成功");
        }else{
            return Response.fail("批量增加失败");
        }
    }
    @ApiOperation(value = "更新批量员工")
    @GetMapping("/updateEmployee")
    public Response updateEmployee(@RequestBody Employee employee){
        boolean b = employeeService.updateById(employee);
        if(b){
            return Response.success("批量更新成功");
        }else{
            return Response.fail("批量更新失败");
        }
    }

    /**
     * 查询出最大的工号进行插入
     */

    @ApiOperation(value = "查询工号")
    @GetMapping("/getMaxWorkId")
    public Response getMaxWordId(){
         return employeeService.getMaxWorkId();
    }


//    给你一个长度为 n 的整数数组 nums ，返回使所有数组元素相等需要的最少移动数。
//    在一步操作中，你可以使数组中的一个元素加 1 或者减 1 。



    /**
     * 将所有的员工进行导出
     */

    @ApiOperation(value = "员工导出")
    @GetMapping(value = "/exportAllEmployee",produces = "application/octet-stream")
    public void exportAllEmployee(HttpServletResponse httpServletResponse){
          employeeService.exportAllEmployee(httpServletResponse);
    }

    /**
     * 将员工进行导入
     * 导入的话你需要知道存入数据库，你需要存入其外键id，如果进行查询的话，每一个都有1万的数据查询比较麻烦.
     */
//    @ApiOperation(value = "员工导入")
//    @PostMapping("/importEmployee")
//    public Response importEmploye(Mu){
//
//    }




}
