package com.t31.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class MainController {
        @RequestMapping("index.html")
        public String index(){
            return "index";
        }
}
