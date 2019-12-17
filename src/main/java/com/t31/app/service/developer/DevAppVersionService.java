package com.t31.app.service.developer;

import com.t31.app.entity.devinfo.AppVersionInfo;

import java.util.List;

public interface DevAppVersionService {
    public List<AppVersionInfo> selectVersionListByAppId(int appId);
}
