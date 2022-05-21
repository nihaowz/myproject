package com.kuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kuang.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 根据当前页和显示页数进行展现
     * @param page
     * @param employee
     * @param localDates
     * @return
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("localDates") LocalDate[] localDates);

    /**
     * 获取所有的员工
     * @return
     */
    List<Employee> getAllEmployee();

    IPage<Employee> getAllEmployeeBySalary(Page<Employee> page);
}
