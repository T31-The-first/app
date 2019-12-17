package com.t31.app.dao.developer;

import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevAppInfoDAO {
    public List<AppInfoList> selectAppInfo(@Param("appinfo") AppInfoDTO appinfo,@Param("start") int start,@Param("size") int size);
    public int selectAppCount(AppInfoDTO appinfo);
    public AppInfoList selectAppInfoById(int id);
    //临时方法，更改图片路径，使图片加载出来

}
