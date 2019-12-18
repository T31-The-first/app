package com.t31.app.controller.developer.appinfo;

import com.t31.app.entity.AppCategoryDTO;
import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.DataDictionaryDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import com.t31.app.entity.devinfo.AppVersionInfo;
import com.t31.app.service.developer.DataDictionaryService;
import com.t31.app.service.developer.DevAppCategoryService;
import com.t31.app.service.developer.DevAppInfoService;
import com.t31.app.service.developer.DevAppVersionService;
import com.t31.app.util.Page;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    @Autowired
    private DevAppVersionService appVersionService;
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
        public String appInfoView(@PathVariable("id") int id,Model model){
            AppInfoList appinfo = appInfoService.AppInfoView(id);
            List<AppVersionInfo> appVersionInfo = appVersionService.selectVersionListByAppId(id);
            model.addAttribute("appInfo",appinfo);
            model.addAttribute("appVersionList",appVersionInfo);
            return "developer/appinfoview";
        }


        /**
         * 12-17 20:20
     * 新增app基础信息yemian
     * @return
     */
    @RequestMapping("/add.html")
    public String addHtml(){
        return "developer/appinfoadd";
    }

    /**
     * 12-17 20:43
     * ajax判断apk是否存在
     */
    @RequestMapping("/apkexist")
    @ResponseBody
    public String checkApkName(String APKName){
        String rs=null;
        //noexist
        if(APKName!=null){
            if(appInfoService.selByAPKName(APKName)>0){
                rs="exist";
            }else{
                rs="noexist";
            }
        }else{
            rs="empty";
        }
        return "{\"APKName\":\""+rs+"\"}";
    }

    /**
     * 12-17 21:12
     * 新增app基础信息处理
     * @return
     */
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    public String addDo(AppInfoDTO appInfoDTO,
                        @RequestParam(value = "a_logoPicPath",required = false) MultipartFile[] files,
                      HttpServletRequest request,
                      Model model){
        String errorInfo=null;
        String filePath="/statics/uploadfiles";
        //文件存放的位置
        String path=request.getServletContext().getRealPath(filePath);
        System.out.println(path);
        String msg="";
        if(files.length>0){
            for (int i=0; i<files.length;i++) {
                MultipartFile file=files[i];
                if(!file.isEmpty()){
                    if(i==0){
                        errorInfo="fileUploadError";
                    }else if(i==1){
                        errorInfo="uploadWpError";
                    }

                    System.out.println(file.getOriginalFilename()); //原文件名
                    String prefix= FilenameUtils.getExtension(file.getOriginalFilename()); // 原文件名后缀名

                    int fileSize=500000;
                    if(file.getSize()>fileSize*1024){//上传文件大小不能大于500kb*1024
                        model.addAttribute(errorInfo,"传文件大小不能大于500kb*1024");

                    } else if(prefix.equalsIgnoreCase("jpg")
                            ||prefix.equalsIgnoreCase("peng")
                            ||prefix.equalsIgnoreCase("jpeg")
                            ||prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
                        String fileName=null;
                        if(i==0){
                            fileName=appInfoDTO.getAPKName()+".jpg";
                        }else if(i==1){
                            fileName=appInfoDTO.getAPKName()+"_2.jpg";
                        }
                        //名字加.jpg等图片路径
                        System.out.println("--------------------------");
//                File tempFile=new File(path, file.getOriginalFilename());

                        File targetFile=new File(path,fileName);/*+File.separator+appInfoDTO.getAPKName()*/
                        if(!targetFile.exists()){
                            targetFile.mkdirs();
                        }
                        try {
                            file.transferTo(targetFile);
                            if(i==0){
                                appInfoDTO.setLogoPicPath(filePath+"/"+fileName);
                            }else if(i==1){
                                appInfoDTO.setLogoPicPath(filePath+"/"+fileName);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            request.setAttribute("errorinfo","上传失败！");
                            model.addAttribute(errorInfo,"上传失败！");
                        }
                    }else{
                        System.out.println("上传图片格式不正确!");
                        model.addAttribute(errorInfo,"上传图片格式不正确");
                    }
                }
            }
        }

        if(appInfoService.addApp(appInfoDTO)>0){
            return "redirect:/developer/appinfo/applist.html";
        }else{
            return "/add.html";
        }
    }
}
