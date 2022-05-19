package com.kuang.controller;


import com.kuang.dto.DepartmentDto;
import com.kuang.pojo.Department;
import com.kuang.service.IDepartmentService;
import com.kuang.utils.Response;
import com.kuang.vo.DepartmentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
@RestController
@RequestMapping("/department")
@Api(tags = "Department-Controller")
public class DepartmentController {

    @Autowired
    IDepartmentService departmentService;
    /**
     * 查询所有的部门
     */
    @ApiOperation(value = "返回所有的部门")
    @GetMapping("/getAllDepartment")
    public Response getAllDepartment(){
      return Response.success("查询成功",departmentService.getAllDepartment());
    }

    /**
     * 添加部门
     */
    @ApiOperation(value = "添加部门")
    @PostMapping("/addDepartment")
    public Response addDepartment(@RequestBody DepartmentVO departmentVO){
        //运用存储过程进行操作
        return departmentService.addDepartment(departmentVO);
    }

    /**
     * 删除部门
     */
    @ApiOperation("删除部门")
    @PostMapping("/deleteDepartment/{id}")
    public Response deleteDepartment(@PathVariable Integer id){
        DepartmentDto departmentDto = new DepartmentDto(id, 0);
        return departmentService.deleteDepartment(departmentDto);
    }


}
