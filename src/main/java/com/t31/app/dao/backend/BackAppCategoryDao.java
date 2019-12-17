package com.t31.app.dao.backend;

import com.t31.app.entity.AppCategoryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: BackAppCategoryDao
 * date: 2019/12/16 19:43
 * author: 周吉涛
 * version: 1.0
 */
public interface BackAppCategoryDao {
    public List<AppCategoryDTO> sel(@Param("parentId") Integer id);//查询全部数据
}
