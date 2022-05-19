package com.kuang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kuang.dto.DepartmentDto;
import com.kuang.mapper.DepartmentMapper;
import com.kuang.pojo.Department;
import com.kuang.service.IDepartmentService;
import com.kuang.utils.Response;
import com.kuang.vo.DepartmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sx-9773
 * @since 2022-05-18
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartment() {
        return departmentMapper.getAllDepartment(-1);
    }

    /**
     * 添加部门
     * @param departmentVO
     * @return
     */
    @Override
    public Response addDepartment(DepartmentVO departmentVO) {
        departmentVO.setEnabled(true);

        departmentMapper.addDepartment(departmentVO);

        Integer result = departmentVO.getResult();
        if(result.equals(1)){
            return Response.success("添加成功",departmentVO);
        }else{
            return Response.fail("添加失败");
        }
    }

    /**
     * 根据id删除部门
     * @param departmentDto
     * @return
     */
    @Override
    public Response deleteDepartment(DepartmentDto departmentDto) {
        departmentMapper.deleteDepartment(departmentDto);
        if(departmentDto.getResult()==-2){
            System.out.println(departmentDto.getResult());
            return Response.fail("删除失败，该部门下面有子部门");
        }else if(departmentDto.getResult() == -1){
            return Response.fail("删除失败，该部门下有员工");
        }else{
            return Response.success("删除成功",departmentDto);
        }
    }
}
