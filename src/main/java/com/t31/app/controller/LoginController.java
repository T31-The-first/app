package com.t31.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
        @RequestMapping("/devlogin")
        public String devLogin(){
            return "devlogin";
        }
}
