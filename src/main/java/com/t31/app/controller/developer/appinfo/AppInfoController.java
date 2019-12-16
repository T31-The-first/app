package com.t31.app.controller.developer.appinfo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/developer/appinfo")
public class AppInfoController {
        /**
         * 用于显示app列表
         * @return
         */
        @RequestMapping("/applist.html")
        public String appListBefore(){

            return "developer/appinfolist";
        }
}
