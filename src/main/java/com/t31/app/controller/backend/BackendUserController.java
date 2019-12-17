package com.t31.app.controller.backend;

import com.t31.app.entity.AppVersionDTO;
import com.t31.app.entity.backend.BackAppInfo;
import com.t31.app.entity.backend.AppInfoList2;
import com.t31.app.service.backend.*;
import com.t31.app.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * description: BackendUserControler
 * date: 2019/12/16 13:43
 * author: 周吉涛
 * version: 1.0
 */
@Controller
@RequestMapping("/backends")
public class BackendUserController {
    @Autowired
    private BackendUserService userService;
    @Autowired
    private BackDataDictionaryService dictionaryService;
    @Autowired
    private AppCategoryService categoryService;
    @Autowired
    private AppInfoService infoService;
    @Autowired
    private AppVersionService versionService;

    @RequestMapping("/applist.html")
    public String list(@RequestParam(value = "pageIndex",required = false)Integer pageNo,
            @RequestParam(value = "querySoftwareName",required = false)String softwareName,
                       @RequestParam(value = "queryFlatformId",required = false) Integer flatformId,
                       @RequestParam(value = "queryCategoryLevel1",required = false)Integer categoryLevel1,
                       @RequestParam(value = "queryCategoryLevel2",required = false)Integer categoryLevel2,
                       @RequestParam(value = "queryCategoryLevel3",required = false)Integer categoryLevel3,
                       Model model){

        Page page=new Page();
        if(pageNo==null||pageNo==0){
            pageNo=1;
        }
        //对首页与末页进行控制
        if(pageNo<1){
            pageNo=1;
        }
        page.setPageIndex(pageNo);

        page =infoService.sel(new BackAppInfo(softwareName,flatformId,categoryLevel1,categoryLevel2,categoryLevel3),page);
        List<AppInfoList2> appInfoList=(List<AppInfoList2>)page.getDataList();

//查询条件下拉框
        model.addAttribute("categoryLevel1List",categoryService.selAll(0));
        model.addAttribute("categoryLevel2List",categoryService.selAll(categoryLevel1));
        model.addAttribute("categoryLevel3List",categoryService.selAll(categoryLevel2));
//所属平台
        model.addAttribute("flatFormList",dictionaryService.selByTypeCode("APP_FLATFORM"));
//查询appinfo数据
        model.addAttribute("appInfoList",appInfoList);
        model.addAttribute("pages",page);
        model.addAttribute("pageIndex",pageNo);
        model.addAttribute("querySoftwareName",softwareName);
        model.addAttribute("queryFlatformId",flatformId);
        model.addAttribute("queryCategoryLevel1",categoryLevel1);
        model.addAttribute("queryCategoryLevel2",categoryLevel2);
        model.addAttribute("queryCategoryLevel3",categoryLevel3);

        return "/backend/applist";
    }


    @RequestMapping("/check.html")
    public String checkHtml(@RequestParam(value = "aid",required = false)Integer id,
                            @RequestParam(value = "vid",required = false)Integer versionId,
                            Model model){
        AppInfoList2 infoList=infoService.selById(id);
        AppVersionDTO version=versionService.selById(versionId);

        model.addAttribute("appVersion",version);
        model.addAttribute("appInfo",infoList);
        return "backend/appcheck";
    }
}
