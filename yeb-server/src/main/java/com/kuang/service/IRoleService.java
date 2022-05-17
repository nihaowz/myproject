package com.kuang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuang.pojo.Role;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kuang
 * @since 2022-05-17
 */
public interface IRoleService extends IService<Role> {

    List<Role> getRoles(Integer adminId);
}
