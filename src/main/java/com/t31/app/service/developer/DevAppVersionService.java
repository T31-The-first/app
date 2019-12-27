package com.t31.app.service.developer;

import com.t31.app.entity.AppVersionDTO;
import com.t31.app.entity.devinfo.AppVersionInfo;

import java.util.List;

public interface DevAppVersionService {
    public List<AppVersionInfo> selectVersionListByAppId(int appId);


    public int selByAppId(int appId);
    public int delVersionInfo(int appId);
    public int addAppVersion(AppVersionDTO appVersion);
    public AppVersionDTO selectVersionById(int id);
    public int updateVersion(AppVersionDTO appVersion);
    public int delVersionAPkFile(int id);
}
