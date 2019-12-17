package com.t31.app.dao.developer;

import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DevAppInfoDAO {
    public List<AppInfoList> selectAppInfo(@Param("appinfo") AppInfoDTO appinfo,@Param("start") int start,@Param("size") int size);
    public int selectAppCount(AppInfoDTO appinfo);
    
}
