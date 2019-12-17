package com.t31.app.service.developer;

import com.t31.app.entity.AppCategoryDTO;

import java.util.List;

public interface DevAppCategoryService {
            //查询一级列表
            public List<AppCategoryDTO> selectcategoryLevelList();
            //查询二级菜单或者三级菜单
            public List<AppCategoryDTO> selectcategoryLevelList(int id);
}
