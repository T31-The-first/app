package com.t31.app.dao.developer;

import com.t31.app.entity.DevUserDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevUserDAO {
        //用户名密码返回对象,用于登录
        public DevUserDTO userLogin(@Param("devCode") String devCode,@Param("devPassword") String devPassword);

}
