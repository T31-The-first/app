package com.t31.app.service.backend.impl;

import com.t31.app.dao.backend.VersionDao;
import com.t31.app.entity.AppVersionDTO;
import com.t31.app.service.backend.AppVersionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * description: AppVersionServiceImpl
 * date: 2019/12/17 10:34
 * author: 周吉涛
 * version: 1.0
 */
@Service
@Transactional
public class AppVersionServiceImpl implements AppVersionService {
    @Autowired
    private VersionDao versionDao;


    @Override
    public AppVersionDTO selById(int id) {
        return versionDao.selById(id);
    }
}
