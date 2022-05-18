package com.kuang.controller;


import com.kuang.service.IDepartmentService;
import com.kuang.utils.Response;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Api(tags = "department-Controller")
public class DepartmentController {

    @Autowired
    IDepartmentService departmentService;
    /**
     * 查询所有的部门
     */
    @GetMapping("/getAllDepartment")
    public Response getAllDepartment(){
      return Response.success("查询成功",departmentService.getAllDepartment());
    }

}
