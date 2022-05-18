package com.kuang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuang.pojo.MenuRole;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kuang
 * @since 2022-05-17
 */
public interface IMenuRoleService extends IService<MenuRole> {

    List<MenuRole> selectDeleteByRoleId(Integer roleId);

    boolean addBatchMenuRole(Integer roleId, Integer[] menuIds);
}
