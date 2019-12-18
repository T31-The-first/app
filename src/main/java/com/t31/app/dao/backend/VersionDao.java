package com.t31.app.dao.backend;

import com.t31.app.entity.AppVersionDTO;

import org.apache.ibatis.annotations.Param;

/**
 * description: VersionDao
 * date: 2019/12/17 10:18
 * author: 周吉涛
 * version: 1.0
 */
public interface VersionDao {
    //根据id查询版本信息
    public AppVersionDTO selById(@Param("id") int id);


}
