package com.kuang.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.mapper.EmployeeMapper;
import com.kuang.pojo.Employee;
import com.kuang.service.IEmployeeService;
import com.kuang.utils.PageUtils;
import com.kuang.utils.Response;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {


    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public Response getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] localDates) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);

        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, localDates);

        PageUtils pageUtils = new PageUtils((int) employeeByPage.getTotal(), employeeByPage.getRecords());

        return Response.success("查询成功",pageUtils);


    }

    /**
     * 获取最大的工号
     * @return
     */
    @Override
    public Response getMaxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workID)"));
        int maxWorkId = Integer.parseInt((String) maps.get(0).get("max(workID)"));
        String maxWord = String.format("%08d", maxWorkId + 1);
        System.out.println(maxWord);
        return Response.success(null,maxWord);
    }

    /**
     * 添加员工
     * 第一步开始需要计算出最大的工号，然后进行递增
     * 第二步进行计算工龄通过String.format转换你需要的格式 比如你想要00000或者000，000，000，000，000
     * 最后调用mapper进行插入操作
     * @param employee
     * @return
     */
    @Override
    public Response addEmployee(Employee employee) {
        //计算合同期限
        LocalDate beginContract = employee.getBeginContract();

        LocalDate endContract = employee.getEndContract();

        //计算相差多少天
        long days = beginContract.until(endContract, ChronoUnit.DAYS);

        double contractTerm = Double.parseDouble(String.format("%.2f", days / 365));

        employee.setContractTerm(contractTerm);

        int row = employeeMapper.insert(employee);

        //返回的是影响的行数
        if(row == 1){
            return Response.success("添加成功");
        }else{
            return Response.fail("添加失败");
        }

    }

    @Override
    public void exportAllEmployee(HttpServletResponse httpServletResponse) {
        //返回所有的员工
        List<Employee> allEmployee = getAllEmployee();
        //导出数据
        ExportParams exportParams = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook workbook =  ExcelExportUtil.exportExcel(exportParams,Employee.class,allEmployee);
        ServletOutputStream outputStream = null;
        try {
            httpServletResponse.setContentType("application/vnd.ms-excel");
            httpServletResponse.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
            outputStream = httpServletResponse.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 导出所有的员工
     */
    @Override
    public List<Employee> getAllEmployee() {
       return employeeMapper.getAllEmployee();
    }

    @Override
    public Response getAllEmployeeBySalary(Integer currentPage, Integer size) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> allEmployeeBySalary = employeeMapper.getAllEmployeeBySalary(page);
        PageUtils pageUtils = new PageUtils((int) allEmployeeBySalary.getTotal(),allEmployeeBySalary.getRecords());
        return Response.success("查询成功",pageUtils);
    }


}
