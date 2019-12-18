package com.t31.app.service.developer.impl;

import com.t31.app.dao.developer.DevAppVersionDAO;
import com.t31.app.entity.AppVersionDTO;
import com.t31.app.entity.devinfo.AppVersionInfo;
import com.t31.app.service.developer.DevAppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevAppVersionServiceImpl implements DevAppVersionService {
    @Autowired
    private DevAppVersionDAO appVersionDAO;
        public List<AppVersionInfo> selectVersionListByAppId(int appId){
            return appVersionDAO.selectVersionByAppId(appId);
        }

    @Override
    public int selByAppId(int appId) {
        return appVersionDAO.selByAppId(appId);
    }

    @Override
    public int delVersionInfo(int appId) {
        return appVersionDAO.delVersionByAppId(appId);
    }
}
