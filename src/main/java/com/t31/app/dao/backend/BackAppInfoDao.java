package com.t31.app.dao.backend;


import com.t31.app.entity.backend.BackAppInfo;
import com.t31.app.entity.backend.AppInfoList2;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * description: BackAppInfoDao
 * date: 2019/12/16 14:14
 * author: 周吉涛
 * version: 1.0
 */
public interface BackAppInfoDao {
    public List<AppInfoList2> selList(@Param("info") BackAppInfo info, @Param("start") int start, @Param("pageSize") int pageSize); //根据软件名称，所属平台，一级，二级，三级分类查询
    public int count(BackAppInfo info);

    public int upAppStatus(int id,int status);
    public AppInfoList2 selById(@Param("id") Integer id);

}
