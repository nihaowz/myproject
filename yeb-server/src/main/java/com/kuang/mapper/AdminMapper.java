package com.kuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuang.pojo.Admin;
import com.kuang.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kuang
 * @since 2022-05-16
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    List<Role> getRoles(Integer id);
}
