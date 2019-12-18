package com.t31.app.controller.backend;

import com.t31.app.service.backend.AppInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description: AppInfoController
 * date: 2019/12/16 15:22
 * author: 周吉涛
 * version: 1.0
 */
@Controller
@RequestMapping("/appinfo")
public class AppInfoController2 {
    @Autowired
    private AppInfoService infoService;

    @RequestMapping("/checkdo")
    public String check(@RequestParam(value = "id",required = false)int id,
                        @RequestParam(value = "status",required = false) int status,
                        Model model){
        if(infoService.upAppStatus(id,status)>0){
            return "redirect:/backends/applist.html";
        }else{
            return "forward:/backends/check.html";
        }

    }

}
