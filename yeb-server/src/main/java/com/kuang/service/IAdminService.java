package com.kuang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuang.pojo.Admin;
import com.kuang.utils.Response;
import com.kuang.vo.AdminLoginVO;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kuang
 * @since 2022-05-16
 */
public interface IAdminService extends IService<Admin> {

    Response login(AdminLoginVO adminLogin, HttpServletRequest httpRequest);

    Admin getAdminByName(String username);
}
