package com.kuang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kuang.pojo.Menu;
import io.swagger.models.auth.In;
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
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据id查询出所有的菜单
     * @param adminId
     * @return
     */
    List<Menu> getMenuByAdminId(Integer adminId);

    /**
     * 根据角色查询出相应的菜单
     * @return
     */
    List<Menu> getMenusFromRole();

}
