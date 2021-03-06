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


    public int selByAPKName(String name);

    public int addApp(AppInfoDTO infoDTO);
    public int delApp(int id);//根据appid删除app，相应的版本信息也要删除
    public int selById(int id);//根据id查找有没有相关的app信息
    public int upAppInfo(AppInfoDTO infoDTO); //修改app基础信息

    public AppInfoList selAppInfoById(int id); //根据id查询info信息
    public int upLogoById(int id);//根据id修改图片路径

    //修改app信息
    public int updateAppInfo(AppInfoDTO appInfo);


}
