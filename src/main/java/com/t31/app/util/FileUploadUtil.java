package com.t31.app.util;

import com.t31.app.entity.AppVersionDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

public class FileUploadUtil {
        /**
         * apk文件上传方法
         * @param appVersion
         * @param apkFile
         * @param request
         * @return
         * @throws IOException
         */
        public static AppVersionDTO apkFileUpload(AppVersionDTO appVersion, MultipartFile apkFile, HttpServletRequest request) throws IOException {
                //apk文件名
                String apkName = apkFile.getOriginalFilename();
                String downloadLink = "/statics/uploadfiles/" + apkName;
                //上传文件所在磁盘位置
                String localFilePath = "D:\\javawork\\t31-ssm-workspace\\app\\src\\main\\webapp\\statics\\uploadfiles\\";
                //上传文件所在web项目位置
                String webFilePath = request.getServletContext().getRealPath("/statics/uploadfiles");
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
}
