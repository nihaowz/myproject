package com.kuang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuang.pojo.Employee;
import com.kuang.utils.Response;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
public interface IEmployeeService extends IService<Employee> {


    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    Response getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] localDates);

    /**
     * 查询最大的工号
     * @return
     */
    Response getMaxWorkId();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    Response addEmployee(Employee employee);

    void exportAllEmployee(HttpServletResponse httpServletResponse);

    List<Employee> getAllEmployee();

    Response getAllEmployeeBySalary(Integer currentPage, Integer size);
}
