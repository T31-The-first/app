package com.t31.app.controller.developer.appinfo;

import com.t31.app.entity.*;
import com.t31.app.entity.devinfo.AppInfoList;
import com.t31.app.entity.devinfo.AppVersionInfo;
import com.t31.app.service.developer.DataDictionaryService;
import com.t31.app.service.developer.DevAppCategoryService;
import com.t31.app.service.developer.DevAppInfoService;
import com.t31.app.service.developer.DevAppVersionService;
import com.t31.app.util.FileUploadUtil;
import com.t31.app.util.Page;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
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
     * 新增页面
     * @param id
     * @param model
     * @return
     */
        @RequestMapping("/appversionadd.html")
        public String addAppVersionBefore(int id,Model model){
            List<AppVersionInfo> appVersionInfo = appVersionService.selectVersionListByAppId(id);
            model.addAttribute("appVersionList",appVersionInfo);
            model.addAttribute("appId",id);
            return "developer/appversionadd";
        }

    /**
     * 保存新增结果
     * @param appVersion
     * @param apkFile
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
        @RequestMapping("/addversionsave")
        public String addAppVersion(AppVersionDTO appVersion,@RequestParam("a_downloadLink")MultipartFile apkFile,Model model,HttpServletRequest request) throws IOException {
            if(!apkFile.isEmpty()){
//                apk文件名
                String apkName = apkFile.getOriginalFilename();
                String apkSuffix = apkName.substring(apkName.lastIndexOf(".")+1,apkName.length());
                if(apkSuffix.equals("apk")){
                    appVersion=FileUploadUtil.apkFileUpload(appVersion,apkFile,request);
                    //获取创建人id
                    if(request.getSession().getAttribute("devUserSession")!=null){
                        DevUserDTO userDTO = (DevUserDTO)request.getSession().getAttribute("devUserSession");
                        int createdById = userDTO.getId();
                        appVersion.setCreatedBy(createdById);
                    }
                    int result = appVersionService.addAppVersion(appVersion);
                    if(result>0){
                        //新增版本成功
                        //model.addAttribute("magess","新增版本成功");
                    }else{
                        //新增版本失败
                        model.addAttribute("error","新增版本失败");
                    }
                }else{
                    model.addAttribute("fileUploadError","上传的文件必须为apk格式");
                }

            }else{
                model.addAttribute("fileUploadError","未上传文件");
            }

            return "forward:/developer/appinfo/appversionadd.html?id="+appVersion.getAppId();
        }

        /**
         * 修改版本页面
         * @return
         */
        @RequestMapping("/appversionmodify.html")
            public String appVersionModify(int aid,int vid,Model model){
                //查询历史版本列表
                List<AppVersionInfo> appVersionInfo = appVersionService.selectVersionListByAppId(aid);
                //查询最新版本信息
                AppVersionDTO appVersion = appVersionService.selectVersionById(vid);
                model.addAttribute("appVersionList",appVersionInfo);
                model.addAttribute("appVersion",appVersion);
                return "developer/appversionmodify";
            }

            /**
             * 修改提交地址
             * @return
             */
            @RequestMapping("appversionmodifysave")
            public String appVersionModifySave(Model model,AppVersionDTO appVersion,HttpServletRequest request,@RequestParam("file") MultipartFile apkFile) throws IOException {
                if(!apkFile.isEmpty()){
                    //apk文件名
                    String apkName = apkFile.getOriginalFilename();
                    String apkSuffix = apkName.substring(apkName.lastIndexOf(".")+1,apkName.length());
                    if(apkSuffix.equals("apk")){
                        //文件上传操作
                        appVersion = FileUploadUtil.apkFileUpload(appVersion,apkFile,request);
                        //上传成功获取修改人id
                        if(request.getSession().getAttribute("devUserSession")!=null){
                            DevUserDTO userDTO = (DevUserDTO)request.getSession().getAttribute("devUserSession");
                            int createdById = userDTO.getId();
                            appVersion.setModifyBy(createdById);
                        }
                    }else{
                        model.addAttribute("fileUploadError","上传的文件必须为apk格式");
                    }
                }
                int result = appVersionService.updateVersion(appVersion);
                if(result>0){
                    //修改成功

                }else{
                    //修改失败
                    model.addAttribute("","修改失败");
                }
                return "forward:/developer/appinfo/appversionmodify.html?aid="+appVersion.getAppId()+"&vid="+appVersion.getId();
            }

    @RequestMapping("/add.html")
    public String addHtml(){
        return "developer/appinfoadd";
    }

     // ajax判断apk是否存在
    @RequestMapping("/apkexist")
    @ResponseBody
    public String checkApkName(String APKName){
        String rs="empty";
        //noexist
        if(APKName!=null){
            if(appInfoService.selByAPKName(APKName)>0){
                rs="exist";
            }else{
                rs="noexist";
            }
        }
        return "{\"APKName\":\""+rs+"\"}";
    }

  //新增app信息
    @RequestMapping(value = "/add.do",method = RequestMethod.POST)
    public String addDo(AppInfoDTO appInfoDTO, @RequestParam(value = "a_logoPicPath",required = false) MultipartFile file, HttpServletRequest request, Model model) throws IOException {
          return FileUploadUtil.addAndModify(appInfoDTO,file,request,"add",model);
    }

     // 删除app信息的同时也要删除此app关联的历史版本信息
    @RequestMapping("/delApp")
    @ResponseBody
    public String delAppInfo(@RequestParam("id") int appId,HttpServletRequest request){
        String rs="false";
        AppInfoList appInfo=appInfoService.selAppInfoById(appId);
        if(appInfoService.selAppById(appId)>0){
            if(appVersionService.selByAppId(appId)>0){
                int num=appVersionService.delVersionInfo(appId);
                if(num>0){
                    if(appInfoService.delApp(appId)>0){
                        rs="true";
                    }
                }
            }else{
                if(appInfoService.delApp(appId)>0){
                    rs="true";
                }
            }
        }else{
            rs="notexist";
        }
        if("true".equals(rs)){
            String delRs=FileUploadUtil.delUpload(request,appId,appInfo.getLogoPicPath(),appInfo.getLogoLocPath());
        }
    return "{\"delResult\":\""+rs+"\"}";
    }

    @RequestMapping("/modify.html")
    public String modifyHtml(int id,Model model){
        AppInfoList list=appInfoService.selAppInfoById(id);
        model.addAttribute("appInfo",list);
        return "developer/appinfomodify";
    }

    @RequestMapping("/delFile")
    @ResponseBody
    public String delFile(int id,String flag,HttpServletRequest request,Model model){
        // 文件路径
        AppInfoList list=appInfoService.selAppInfoById(id);
        String rs=FileUploadUtil.delUpload(request,id,list.getLogoPicPath(),list.getLogoLocPath());
        if("success".equals(rs)){
            appInfoService.upLogoById(id);
        }
        return "{\"result\":\""+rs+"\"}";
    }

    @RequestMapping(value = "/modifyDo",method = RequestMethod.POST)
    public String modifyDo(AppInfoDTO appInfoDTO, MultipartFile file, HttpServletRequest request, String statusName, Model model) throws IOException {
        appInfoDTO.setStatus(dataDictionaryService.selByCodeAndTypeName(statusName));
        return FileUploadUtil.addAndModify(appInfoDTO,file,request,"modify",model);
    }

    /**
     * 上架或者下架操作
     * @return
     */
    @RequestMapping("/sale/{id}")
    @ResponseBody
    public String appSale(@PathVariable int id){
        //查询这个app现在的状态
        AppInfoList list = appInfoService.selAppInfoById(id);
        int appStatus = list.getStatus();
        AppInfoDTO infoDTO = new AppInfoDTO();
        infoDTO.setId(id);
        String result = "";
        if(appStatus==2||appStatus==5){
            //审核通过或者已下架，进行上架操作
            infoDTO.setStatus(4);
            int upResult = appInfoService.upAppInfo(infoDTO);
            if(upResult>0){
                //上架成功
                result="success";
            }else{
                //上架失败
                result="failed";
            }
        }else if(appStatus==4){
            //已上架，进行下架操作
            infoDTO.setStatus(5);
            int upResult = appInfoService.upAppInfo(infoDTO);
            if(upResult>0){
                //下架成功
                result="success";
            }else{
                //下架失败
                result="failed";

            }
        }else{
            //没有需要的状态进入这里，属于非法操作
        }
        return "{\"resultMsg\":\""+result+"\"}";
    }
}
