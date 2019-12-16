package com.t31.app.controller.developer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/developer")
public class DeveloperUserController {
        @RequestMapping("/devlogin.html")
        public String devLoginBefore(){
            return "devlogin";
        }
        @RequestMapping("/index.html")
        public String index(){
            return "developer/main";
        }
        @RequestMapping("/dologin")
        public String doLogin(String devCode,String devPassword){
            //登录提交地址

            return "";
        }
}
