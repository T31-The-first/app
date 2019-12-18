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
                //apk文件名
                String apkName = apkFile.getOriginalFilename();
                String apkSuffix = apkName.substring(apkName.lastIndexOf(".")+1,apkName.length());
                if(apkSuffix.equals("apk")){
                    String downloadLink = "/statics/uploadfiles/"+apkName;
                    //上传文件所在磁盘位置
                    String localFilePath = "D:\\bdqn学习\\Y2\\idea-worksplace\\t31-ssm-workspace\\app\\src\\main\\webapp\\statics\\uploadfiles\\";
                    //上传文件所在web项目位置
                    String webFilePath = request.getServletContext().getRealPath("/statics/uploadfiles");
                    File webFile = new File(webFilePath,apkName);
                    if(!webFile.exists()){
                        webFile.mkdirs();
                    }
                    //上传文件到web项目位置
                    apkFile.transferTo(webFile);
                    //文件复制到磁盘位置
                    File localFile = new File(localFilePath+apkName);
                    if(!localFile.exists()){
                        localFile.createNewFile();
                    }
                    InputStream inputFile = new FileInputStream(webFile);
                    OutputStream outputStreamFile = new FileOutputStream(localFile);
                    byte[] bytes = new byte[inputFile.available()];
                    inputFile.read(bytes);
                    outputStreamFile.write(bytes);
                    inputFile.close();
                    outputStreamFile.close();
                    //上传文件完成，进行数据库新增操作
                    appVersion.setDownloadLink(downloadLink);
                    appVersion.setApkLocPath(localFile.getAbsolutePath());
                    appVersion.setApkFileName(apkName);
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
            public String appVersionModifySave(Model model,AppVersionDTO appVersion,HttpServletRequest request,@Param("file") MultipartFile apkFile) throws IOException {
                if(!apkFile.isEmpty()){
                    //进行文件上传操作
                        appVersion = FileUploadUtil.apkFileUpload(appVersion,apkFile,request);

                        if(request.getSession().getAttribute("devUserSession")!=null){
                            DevUserDTO userDTO = (DevUserDTO)request.getSession().getAttribute("devUserSession");
                            int createdById = userDTO.getId();
                            appVersion.setModifyBy(createdById);
                        }
                    }else{
                        model.addAttribute("fileUploadError","上传的文件必须为apk格式");
                    }
                return null;
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
                        @RequestParam(value = "a_logoPicPath",required = false) MultipartFile file,
                      HttpServletRequest request,
                      Model model) throws IOException {
        String errorInfo=null;
        String filePath="/statics/uploadfiles";
        //上传文件所在磁盘位置
        String localFilePath = "D:\\bdqn学习\\Y2\\idea-worksplace\\t31-ssm-workspace\\app\\src\\main\\webapp\\statics\\uploadfiles\\";
        //文件存放的位置
        String path=request.getServletContext().getRealPath(filePath);
        System.out.println(path);
        String msg="";
        if(!file.isEmpty()){
            errorInfo="fileUploadError";
//            System.out.println(file.getOriginalFilename()); //原文件名
            String prefix= FilenameUtils.getExtension(file.getOriginalFilename()); // 原文件名后缀名

            int fileSize=500000;
            if(file.getSize()>fileSize*1024){//上传文件大小不能大于500kb*1024
                model.addAttribute(errorInfo,"传文件大小不能大于500kb*1024");

            } else if(prefix.equalsIgnoreCase("jpg")
                    ||prefix.equalsIgnoreCase("peng")
                    ||prefix.equalsIgnoreCase("jpeg")
                    ||prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
                String fileName=null;
                fileName=appInfoDTO.getAPKName()+".jpg";

                //名字加.jpg等图片路径
                System.out.println("--------------------------");
//                File tempFile=new File(path, file.getOriginalFilename());

                File targetFile=new File(path,fileName);/*+File.separator+appInfoDTO.getAPKName()*/
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }

                //上传文件到web项目位置
                file.transferTo(targetFile);
                //文件复制到磁盘位置
                File localFile = new File(localFilePath+fileName);
                if(!localFile.exists()){
                    localFile.createNewFile();
                }
                InputStream inputFile = new FileInputStream(targetFile);
                OutputStream outputStreamFile = new FileOutputStream(localFile);
                byte[] bytes = new byte[inputFile.available()];
                inputFile.read(bytes);
                outputStreamFile.write(bytes);
                inputFile.close();
                outputStreamFile.close();
                //上传文件完成，进行数据库新增操作
                try {
                    appInfoDTO.setLogoPicPath(filePath+"/"+fileName);
                }catch (Exception e){
                    e.printStackTrace();
                    model.addAttribute(errorInfo,"上传失败！");
                }
            }else{
                model.addAttribute(errorInfo,"上传图片格式不正确");
            }
        }
        if(appInfoService.addApp(appInfoDTO)>0){
            return "redirect:/developer/appinfo/applist.html";
        }else{
            return "/add.html";
        }
    }


    /**
     * 删除app信息的同时也要删除此app关联的历史版本信息
     * @return
     */
    @RequestMapping("/delApp")
    @ResponseBody
    public String delAppInfo(@RequestParam("id") int appId,HttpServletRequest request){
        String rs="";
        AppInfoList list=appInfoService.selAppInfoById(appId);
        if(appInfoService.selAppById(appId)>0){
            if(appVersionService.selByAppId(appId)>0){
                int num=appVersionService.delVersionInfo(appId);
                if(num>0){
                    if(appInfoService.delApp(appId)>0){
                        rs="true";
                    }else{
                        rs="false";
                    }
                }else{
                    rs="false";
                }
            }else{
                if(appInfoService.delApp(appId)>0){
                    rs="true";
                }else {
                    rs = "false";
                }
            }
        }else{
            rs="notexist";
        }
    if("true".equals(rs)){
        // 文件路径
        String localPath="D:\\bdqn学习\\Y2\\idea-worksplace\\t31-ssm-workspace\\app\\src\\main\\webapp\\";
        String path = request.getSession().getServletContext().getRealPath(list.getLogoPicPath());
        if(list.getLogoPicPath()!=null){
            String newFileName = list.getLogoPicPath();
//            File targetFile = new File(path, newFileName);
            File targetFile = new File(path);
            File localFile = new File(localPath,newFileName);
            if (targetFile.exists()) {
                targetFile.delete();
            } else {
//                System.out.println("文件不存在！");
            }
            if (localFile.exists()) {
                localFile.delete();
            } else {
//                System.out.println("磁盘文件不存在！");
            }
        }
    }


        return "{\"delResult\":\""+rs+"\"}";
    }

    @RequestMapping("/modify.html")
    public String modifyHtml(int id,Model model){
//        appInfo
        AppInfoList list=appInfoService.selAppInfoById(id);
        model.addAttribute("appInfo",list);
        return "developer/appinfomodify";
    }

    @RequestMapping("/delFile")
    @ResponseBody
    public String delFile(int id,String flag,HttpServletRequest request,Model model){
     String rs="failed";

        // 文件路径
        AppInfoList list=appInfoService.selAppInfoById(id);
        String localPath="D:\\bdqn学习\\Y2\\idea-worksplace\\t31-ssm-workspace\\app\\src\\main\\webapp\\";
        String path = request.getSession().getServletContext().getRealPath(list.getLogoPicPath());
        if(list.getLogoPicPath()!=null){
            String newFileName = list.getLogoPicPath();
//            File targetFile = new File(path, newFileName);
           //web文件路径
            File targetFile = new File(path);
            if (targetFile.exists()) {
                targetFile.delete();
                rs="success";
            } else {
//                System.out.println("文件不存在！");
            }

            //磁盘路径
            File localFile = new File(localPath,newFileName);
            if (localFile.exists()) {
                localFile.delete();
                rs="success";
            } else {
//                System.out.println("磁盘文件不存在！");

            }

            if("success".equals(rs)){
                appInfoService.upLogoById(id);
            }
        }
        return "{\"result\":\""+rs+"\"}";
    }

    @RequestMapping(value = "/modifyDo",method = RequestMethod.POST)
    public String modifyDo(AppInfoDTO appInfoDTO,
                           MultipartFile file,
                           HttpServletRequest request,
                           String statusName,
                           Model model) throws IOException {

        String errorInfo=null;
        String filePath="/statics/uploadfiles";
        //上传文件所在磁盘位置
        String localFilePath = "D:\\bdqn学习\\Y2\\idea-worksplace\\t31-ssm-workspace\\app\\src\\main\\webapp\\statics\\uploadfiles\\";
        //文件存放的位置
        String path=request.getServletContext().getRealPath(filePath);
        System.out.println(path);
        String msg="";
        if(!file.isEmpty()){
            errorInfo="fileUploadError";
//            System.out.println(file.getOriginalFilename()); //原文件名
            String prefix= FilenameUtils.getExtension(file.getOriginalFilename()); // 原文件名后缀名

            int fileSize=500000;
            if(file.getSize()>fileSize*1024){//上传文件大小不能大于500kb*1024
                model.addAttribute(errorInfo,"传文件大小不能大于500kb*1024");

            } else if(prefix.equalsIgnoreCase("jpg")
                    ||prefix.equalsIgnoreCase("peng")
                    ||prefix.equalsIgnoreCase("jpeg")
                    ||prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确
                String fileName=null;
                fileName=appInfoDTO.getAPKName()+".jpg";

                //名字加.jpg等图片路径
                System.out.println("--------------------------");
//                File tempFile=new File(path, file.getOriginalFilename());

                File targetFile=new File(path,fileName);/*+File.separator+appInfoDTO.getAPKName()*/
                if(!targetFile.exists()){
                    targetFile.mkdirs();
                }

                //上传文件到web项目位置
                file.transferTo(targetFile);
                //文件复制到磁盘位置
                File localFile = new File(localFilePath+fileName);
                if(!localFile.exists()){
                    localFile.createNewFile();
                }
                InputStream inputFile = new FileInputStream(targetFile);
                OutputStream outputStreamFile = new FileOutputStream(localFile);
                byte[] bytes = new byte[inputFile.available()];
                inputFile.read(bytes);
                outputStreamFile.write(bytes);
                inputFile.close();
                outputStreamFile.close();
                //上传文件完成，进行数据库新增操作
                try {
                    appInfoDTO.setLogoPicPath(filePath+"/"+fileName);
                }catch (Exception e){
                    e.printStackTrace();
                    model.addAttribute(errorInfo,"上传失败！");
                }
            }else{
                model.addAttribute(errorInfo,"上传图片格式不正确");
            }
        }
        appInfoDTO.setStatus(dataDictionaryService.selByCodeAndTypeName(statusName));
        appInfoDTO.setModifyDate(new Date());
        appInfoDTO.setModifyBy(1);
        if(appInfoService.upAppInfo(appInfoDTO)>0){
            return "redirect:/developer/appinfo/applist.html";
        }else{
            return "/modify.html";
        }
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
