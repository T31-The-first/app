package com.t31.app.service.backend.impl;

import com.t31.app.dao.backend.BackAppCategoryDao;
import com.t31.app.entity.AppCategoryDTO;
import com.t31.app.service.backend.AppCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * description: AppCategoryServiceImpl
 * date: 2019/12/16 19:48
 * author: 周吉涛
 * version: 1.0
 */
@Service
@Transactional
public class AppCategoryServiceImpl implements AppCategoryService {
    @Autowired
    private BackAppCategoryDao categoryDao;

    @Override
    public List<AppCategoryDTO> selAll(Integer id) {
        return categoryDao.sel(id);
    }
}
