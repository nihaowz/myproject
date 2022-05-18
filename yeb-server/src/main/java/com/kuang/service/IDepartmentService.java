package com.kuang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuang.pojo.Department;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> getAllDepartment();
}
