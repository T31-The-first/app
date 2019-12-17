package com.t31.app.service.backend;

import com.t31.app.entity.backend.BackAppInfo;
import com.t31.app.entity.backend.AppInfoList2;
import com.t31.app.util.Page;


/**
 * description: AppInfoService
 * date: 2019/12/16 15:18
 * author: 周吉涛
 * version: 1.0
 */
public interface AppInfoService {
    public Page sel(BackAppInfo info, Page page);

    public int upAppStatus(int id,int status);
    public AppInfoList2 selById(Integer id);
}
