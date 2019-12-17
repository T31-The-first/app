package com.t31.app.dao.developer;

import com.t31.app.entity.AppVersionDTO;
import com.t31.app.entity.devinfo.AppVersionInfo;

import java.util.List;

public interface DevAppVersionDAO {
            public List<AppVersionInfo> selectVersionByAppId(int appId);
}
