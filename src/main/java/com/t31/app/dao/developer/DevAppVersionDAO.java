package com.t31.app.dao.developer;

import com.t31.app.entity.AppVersionDTO;
import com.t31.app.entity.devinfo.AppVersionInfo;

import java.util.List;

public interface DevAppVersionDAO {
            public List<AppVersionInfo> selectVersionByAppId(int appId);

    public int delVersionByAppId(int appId);//根据appId删除版本信息
    public int selByAppId(int appId); //先根据appId查询有么有版本信息，，然后执行删除
    public int addAppVersion(AppVersionDTO appVersion);
    //通过id查询单个版本信息
    public AppVersionDTO selectVersionById(int id);
    //通过appId查询版本信息，最终得到一个最新版本的id
    public int selectNewVersionByAppId(int appId);
    //修改最新版本信息
    public int updateVersion(AppVersionDTO appVersion);
}
