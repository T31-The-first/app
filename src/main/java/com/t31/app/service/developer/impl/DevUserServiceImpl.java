package com.t31.app.service.developer.impl;

import com.t31.app.dao.developer.DevUserDAO;
import com.t31.app.entity.DevUserDTO;
import com.t31.app.service.developer.DevUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevUserServiceImpl implements DevUserService {
    @Autowired
    private DevUserDAO devUserDAO;
    @Override
    public DevUserDTO userLogin(String username,String password) {
        return devUserDAO.userLogin(username,password);
    }
}
