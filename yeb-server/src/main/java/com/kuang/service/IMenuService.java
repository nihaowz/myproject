package com.kuang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuang.pojo.Menu;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kuang
 * @since 2022-05-17
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenuByAdminId();


    List<Menu> getMenusFromRole();

    List<Menu> getAllMenu();
}
