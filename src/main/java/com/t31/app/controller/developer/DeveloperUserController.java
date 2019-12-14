package com.t31.app.controller.developer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeveloperUserController {
        @RequestMapping("/devlogin.html")
        public String devLogin(){
            return "devlogin";
        }
        public String index(){
            return "main";
        }
}
