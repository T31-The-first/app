package com.t31.app.service.developer.impl;

import com.t31.app.dao.developer.DevAppInfoDAO;
import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import com.t31.app.service.developer.DevAppInfoService;
import com.t31.app.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevAppInfoServiceImpl implements DevAppInfoService {
    @Autowired
    private DevAppInfoDAO appInfoDAO;
    @Override
    public Page selectAppInfo(AppInfoDTO appInfo, Page page) {
        page.setTotalCount(appInfoDAO.selectAppCount(appInfo));
        int start = (page.getPageIndex()-1)*page.getPageSize();
        int size = page.getPageSize();
        page.setDataList(appInfoDAO.selectAppInfo(appInfo,start,size));
        return page;
    }
    public AppInfoList AppInfoView(int id){
        return appInfoDAO.selectAppInfoById(id);
    }

    @Override
    public int selByAPKName(String name) {
        return appInfoDAO.selByAPKName(name);
    }

    @Override
    public int addApp(AppInfoDTO infoDTO) {
        return appInfoDAO.addApp(infoDTO);
    }

    @Override
    public int delApp(int id) {
        return appInfoDAO.delApp(id);
    }
}
