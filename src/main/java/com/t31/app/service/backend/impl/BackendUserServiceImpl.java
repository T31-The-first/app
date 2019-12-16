package com.t31.app.service.backend.impl;


import com.t31.app.dao.backend.BackendUserDao;
import com.t31.app.entity.BackendUserDTO;
import com.t31.app.service.backend.BackendUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description: BackendUserServiceImpl
 * date: 2019/12/16 10:34
 * author: 周吉涛
 * version: 1.0
 */
@Service
@Transactional
public class BackendUserServiceImpl implements BackendUserService {
    @Autowired
    private BackendUserDao userDao;

    @Override
    public BackendUserDTO login(String code, String pwd) {
        return userDao.doLogin(code,pwd);
    }
}
