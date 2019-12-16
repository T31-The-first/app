package com.t31.app.dao.backend;

import com.t31.app.entity.BackendUserDTO;

/**
 * description: backend
 * date: 2019/12/16 10:23
 * author: 周吉涛
 * version: 1.0
 */
public interface BackendUserDao {
    public BackendUserDTO doLogin(String code, String pwd);
}
