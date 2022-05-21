package com.kuang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kuang.pojo.Admin;
import com.kuang.pojo.Role;
import com.kuang.utils.Response;
import com.kuang.vo.AdminLoginVO;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    /**
     * 根据返回的注册信息写入数据库，返回token
     * @param admin
     * @param httpServletResponse
     * @return
     */
    Response register(Admin admin, HttpServletResponse httpServletResponse);

    Response updateAdminFace(byte[] bytes, String name, Integer id, Authentication authentication);
}
