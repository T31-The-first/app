package com.t31.app.controller.backend;

import org.springframework.web.bind.annotation.RequestMapping;

public class BackendUserController {
        @RequestMapping("/backendlogin.html")
        public String backendLogin(){
            return "backendlogin";
        }
}

