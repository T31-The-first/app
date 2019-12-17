package com.t31.app.controller.developer.appinfo;

import com.t31.app.entity.AppCategoryDTO;
import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.DataDictionaryDTO;
import com.t31.app.service.developer.DataDictionaryService;
import com.t31.app.service.developer.DevAppCategoryService;
import com.t31.app.service.developer.DevAppInfoService;
import com.t31.app.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/developer/appinfo")
public class AppInfoController {
    @Autowired
    private DevAppInfoService appInfoService;
    @Autowired
    private DevAppCategoryService appCategoryService;
    @Autowired
    private DataDictionaryService dataDictionaryService;
        /**
         * 用于显示app列表
         * @return
         */
        @RequestMapping("/applist.html")
        public String appListBefore(AppInfoDTO appinfo,@RequestParam(defaultValue = "1",required = false) int pageIndex,Model model){
            Page page = new Page();
            page.setPageIndex(pageIndex);

            page = appInfoService.selectAppInfo(appinfo,page);
            //加载一级菜单
            List<AppCategoryDTO> categoryList = appCategoryService.selectcategoryLevelList();
            List<DataDictionaryDTO> statusData = dataDictionaryService.selectStatusList();
            List<DataDictionaryDTO> flatFormData = dataDictionaryService.selectFlatFormList();
            //渲染状态列表
            model.addAttribute("statusList",statusData);
            //平台列表
            model.addAttribute("flatFormList",flatFormData);
            //页数据
            model.addAttribute("appListPage",page);
            //条件数据
            model.addAttribute("appinfo",appinfo);
            //一级菜单
            model.addAttribute("categoryLevel1List",categoryList);
            //二级菜单
            if(appinfo.getCategoryLevel1()>0){
                model.addAttribute("categoryLevel2List",appCategoryService.selectcategoryLevelList(appinfo.getCategoryLevel1()));
            }
            //三级菜单
            if(appinfo.getCategoryLevel2()>0){
                model.addAttribute("categoryLevel3List",appCategoryService.selectcategoryLevelList(appinfo.getCategoryLevel2()));
            }

            return "developer/appinfolist";
        }
        @RequestMapping("/appcategory")
        @ResponseBody
        public List<AppCategoryDTO> appCategoryList(int pid){

            return appCategoryService.selectcategoryLevelList(pid);
        }
        //查看app详细信息
        @RequestMapping("/appview/{id}")
        public String appInfoView(@PathVariable("id") int id){

            return "developer/appinfoview";
        }
}
