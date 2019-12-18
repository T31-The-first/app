package com.t31.app.service.developer.impl;

import com.t31.app.dao.developer.DevAppInfoDAO;
import com.t31.app.dao.developer.DevAppVersionDAO;
import com.t31.app.entity.AppInfoDTO;
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
    @Autowired
    private DevAppInfoDAO appInfoDAO;
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

    @Override
    public int addAppVersion(AppVersionDTO appVersion) {
            //新增版本，先新增版本，获取新增后的版本id更改app基础信息最新版本
        int result = appVersionDAO.addAppVersion(appVersion);
        //得到最新版本
        int newVersion = appVersionDAO.selectNewVersionByAppId(appVersion.getAppId());
        //设置最新版本
        AppInfoDTO appInfo = new AppInfoDTO();
        appInfo.setId(appVersion.getAppId());
        appInfo.setVersionId(newVersion);
        //更新最新版本
        if(result>0){
            result = appInfoDAO.updateAppInfo(appInfo);
        }
        return result;
    }

    @Override
    public AppVersionDTO selectVersionById(int id) {
        return appVersionDAO.selectVersionById(id);
    }
}
