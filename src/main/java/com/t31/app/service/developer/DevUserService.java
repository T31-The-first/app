package com.t31.app.service.developer;

import com.t31.app.entity.DevUserDTO;

public interface DevUserService {
        public DevUserDTO userLogin(String username,String password);
}
