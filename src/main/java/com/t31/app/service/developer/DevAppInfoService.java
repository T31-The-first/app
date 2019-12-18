package com.t31.app.service.developer;

import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import com.t31.app.util.Page;

public interface DevAppInfoService {
            public Page selectAppInfo(AppInfoDTO appInfo, Page page);
            public AppInfoList AppInfoView(int id);


            public int selByAPKName(String name);
            public int addApp(AppInfoDTO infoDTO);
            public int delApp(int id);
            public int selAppById(int id);
}
