package com.kuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuang.pojo.Department;
import org.apache.ibatis.annotations.Mapper;

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
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartment(Integer id);
}
