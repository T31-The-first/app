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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
            model.addAttribute("statusList",statusData);
            model.addAttribute("appListPage",page);
            model.addAttribute("categoryLevel1List",categoryList);
            return "developer/appinfolist";
        }
        @RequestMapping("/appcategory")
        public String appCategoryList(){
            return null;
        }
}
