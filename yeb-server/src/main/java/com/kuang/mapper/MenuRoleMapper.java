package com.kuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuang.pojo.MenuRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author kuang
 * @since 2022-05-17
 */
@Mapper
public interface MenuRoleMapper extends BaseMapper<MenuRole> {
    /**
     * 必须要写操作的参数，不然默认的是会报错的
     * @param roleId
     * @param menuIds
     * @return
     */
    boolean addBatchMenuRole(@Param("roleId") Integer roleId,@Param("menuIds") Integer[] menuIds);
}
