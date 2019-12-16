package com.t31.app.service.backend;

import com.t31.app.entity.BackendUserDTO;

/**
 * description: BackendUserService
 * date: 2019/12/16 10:32
 * author: 周吉涛
 * version: 1.0
 */
public interface BackendUserService {
    public BackendUserDTO login(String code, String pwd);

}
