package com.t31.app.service.backend;

import com.t31.app.entity.AppCategoryDTO;


import java.util.List;

/**
 * description: AppCategoryService
 * date: 2019/12/16 19:47
 * author: 周吉涛
 * version: 1.0
 */
public interface AppCategoryService {
    public List<AppCategoryDTO> selAll(Integer id);


}
