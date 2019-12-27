package com.t31.app.util;

import com.t31.app.entity.AppInfoDTO;
import com.t31.app.entity.AppVersionDTO;
import com.t31.app.entity.DevUserDTO;
import com.t31.app.entity.devinfo.AppInfoList;
import com.t31.app.service.developer.DevAppInfoService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;


public class FileUploadUtil {

        private static DevAppInfoService appInfoService;
        @Autowired
        public FileUploadUtil(DevAppInfoService infoService){
                FileUploadUtil.appInfoService=infoService;
        }
        /**
         * apk文件上传方法
         * @param appVersion
         * @param apkFile
         * @param request
         * @return
         * @throws IOException
         */
        //磁盘保存路径,创建springBean通过配置文件注入路径
        private static String localFilePath;
        private static String targetFilePath;

        public String getLocalFilePath() {
                return localFilePath;
        }
        public void setLocalFilePath(String localFilePath) {
                FileUploadUtil.localFilePath = localFilePath;
        }

        public String getTargetFilePath() {
                return targetFilePath;
        }

        public void setTargetFilePath(String targetFilePath) {
                FileUploadUtil.targetFilePath = targetFilePath;
        }

        public static AppVersionDTO apkFileUpload(AppVersionDTO appVersion, MultipartFile apkFile, HttpServletRequest request) throws IOException {
                //apk文件名
                String apkName = apkFile.getOriginalFilename();
                String downloadLink = targetFilePath + apkName;
                //上传文件所在磁盘位置
                //上传文件所在web项目位置
                String webFilePath = request.getServletContext().getRealPath(targetFilePath);
                File webFile = new File(webFilePath, apkName);
                if (!webFile.exists()) {
                    webFile.mkdirs();
                }
                //上传文件到web项目位置
                apkFile.transferTo(webFile);
                //文件复制到磁盘位置
                File localFile = new File(localFilePath + apkName);
                if (!localFile.exists()) {
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

            return appVersion;
        }

        public static String addAndModify(AppInfoDTO appInfoDTO, MultipartFile file, HttpServletRequest request,String rs,Model model) throws IOException {
                int result=0;
                String errorInfo="fileUploadError";

                if(appInfoDTO.getLogoPicPath()!=null&&!("").equals(appInfoDTO.getLogoPicPath())){
                        result=addModify(appInfoDTO,rs,request);
                }else{
                        if(!file.isEmpty()){
                                String prefix= FilenameUtils.getExtension(file.getOriginalFilename()); // 原文件名后缀名
                                int fileSize=500000;
                                if(file.getSize()>fileSize*1024){//上传文件大小不能大于500kb*1024
                                        model.addAttribute(errorInfo,"传文件大小不能大于500kb*1024");
                                } else if(prefix.equalsIgnoreCase("jpg")
                                        ||prefix.equalsIgnoreCase("peng")
                                        ||prefix.equalsIgnoreCase("jpeg")
                                        ||prefix.equalsIgnoreCase("pneg")){//上传图片格式不正确

                                        //文件存放的位置
                                        String path=request.getServletContext().getRealPath(targetFilePath);
                                        String fileName=appInfoDTO.getAPKName()+".jpg"; //名字为平APKName+.jpg
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
                                        appInfoDTO.setLogoPicPath(targetFilePath+fileName);
                                        appInfoDTO.setLogoLocPath(localFile.getAbsolutePath());

                                        //上传文件成功获取修改人id
                                         /*if(getId(request)>0){

                                         }*/
                                        //传参判断是为添加还是修改
                                        result=addModify(appInfoDTO,rs,request);
                                }else{
                                        model.addAttribute(errorInfo,"上传图片格式不正确");
                                }
                        }else{
                                model.addAttribute("appInfo",appInfoDTO);
                                model.addAttribute(errorInfo,"未上传文件");
                        }
                }

                if(result>0){
                        return "redirect:/developer/appinfo/applist.html";
                }else{
                        String falg="";
                        if("add".equals(rs)){
                                falg= "forward:/developer/appinfo/add.html";
                        }else if("modify".equals(rs)){
                                falg= "forward:/developer/appinfo/modify.html";
                        }
                        return falg;
                }
//                return appInfoDTO;
        }

        //上传成功获取修改人id
        public static int getId(HttpServletRequest request){
                int createdById=0;
                if (request.getSession().getAttribute("devUserSession")!=null) {
                        DevUserDTO userDTO = (DevUserDTO) request.getSession().getAttribute("devUserSession");
                        createdById = userDTO.getId();
                }
                return createdById;
        }

        //添加与修改
        public static int addModify(AppInfoDTO appInfoDTO,String rs,HttpServletRequest request){
                int result=0;
                if("add".equals(rs)){
                        if(getId(request)>0){
                                appInfoDTO.setCreatedBy(getId(request));
                                appInfoDTO.setCreationDate(new Date());
                        }

                        appInfoDTO.setCreatedBy(1);
                        appInfoDTO.setCreationDate(new Date());
                        result=appInfoService.addApp(appInfoDTO);
                }else if("modify".equals(rs)){
                        if(getId(request)>0){
                                appInfoDTO.setModifyBy(getId(request));
                                appInfoDTO.setCreationDate(new Date());
                        }
                        appInfoDTO.setModifyBy(1);
                        appInfoDTO.setModifyDate(new Date());
                        result=appInfoService.upAppInfo(appInfoDTO);
                }
                return result;
        }

        public static String delUpload(HttpServletRequest request,String realPath,String localPath){ // 删除文件
                String result="failed";
                // 文件路径
                String path = request.getSession().getServletContext().getRealPath(realPath);
                if(realPath!=null&&!("").equals(realPath)){
                        //删除target下的文件
                        File targetFile = new File(path);
                        if (targetFile.exists()) {
                            targetFile.delete();
                            result="success";
                        }
                        //删除磁盘文件
                        File localFile = new File(localPath);
                        if (localFile.exists()) {
                            localFile.delete();
                            result="success";
                        }
                }
                return result;
        }
}
