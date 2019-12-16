package com.t31.app.controller.backend;

import com.t31.app.service.backend.BackendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * description: BackendUserControler
 * date: 2019/12/16 13:43
 * author: 周吉涛
 * version: 1.0
 */
@Controller
@RequestMapping("/backends")
public class BackendUserController {
    @Autowired
    private BackendUserService userService;

    @RequestMapping("/applist.html")
    public String list(){

        return "/backend/applist";
    }
}
