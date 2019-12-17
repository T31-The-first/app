package com.t31.app.service.backend.impl;

import com.t31.app.dao.backend.BackAppInfoDao;
import com.t31.app.entity.backend.BackAppInfo;
import com.t31.app.entity.backend.AppInfoList2;
import com.t31.app.service.backend.AppInfoService;
import com.t31.app.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description: AppInfoServiceImpl
 * date: 2019/12/16 15:19
 * author: 周吉涛
 * version: 1.0
 */
@Service
@Transactional
public class AppInfoServiceImpl implements AppInfoService {
    @Autowired
    private BackAppInfoDao infoDao;


    @Override
    public Page sel(BackAppInfo info, Page page) {
        page.setTotalCount(infoDao.count(info));
//        int start=(page.getCurrPageNo()-1)*page.getPageSize();
//        List<BackAppInfo> list=infoDao.selList(backend,(page.getCurrPageNo()-1)*page.getPageSize(),page.getPageSize());
        page.setDataList(infoDao.selList(info,(page.getPageIndex()-1)*page.getPageSize(),page.getPageSize()));
        return page;
    }

    @Override
    public int upAppStatus(int id, int status) {
        return infoDao.upAppStatus(id,status);
    }

    @Override
    public AppInfoList2 selById(Integer id) {
        return infoDao.selById(id);
    }
}
