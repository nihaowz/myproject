package com.kuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuang.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kuang
 * @since 2022-05-17
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRoles(Integer id);
}
