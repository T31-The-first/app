package com.t31.app.service.developer.impl;

import com.t31.app.dao.developer.DevCategoryDAO;
import com.t31.app.entity.AppCategoryDTO;
import com.t31.app.service.developer.DevAppCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DevAppCategoryServiceImpl implements DevAppCategoryService {
    @Autowired
    private DevCategoryDAO categoryDAO;
    @Override
    public List<AppCategoryDTO> selectcategoryLevelList() {
        return categoryDAO.selectCategoryLevel(0);
    }

    @Override
    public List<AppCategoryDTO> selectcategoryLevelList(int id) {
        return categoryDAO.selectCategoryLevel(id);
    }
}
