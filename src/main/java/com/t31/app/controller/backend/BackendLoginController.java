package com.t31.app.controller.backend;

import com.t31.app.entity.BackendUserDTO;
import com.t31.app.service.backend.BackendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * description: BackendUserController
 * date: 2019/12/16 10:46
 * author: 周吉涛
 * version: 1.0
 */
@Controller
@RequestMapping("/back")
public class BackendLoginController {
    @Autowired
    private BackendUserService userService;

    @RequestMapping("/login.html")
    public String loginBefore(){
        return "/backendlogin";
    }

    @RequestMapping("/doLogin")
    public String doLogin(@RequestParam("userCode") String userCode, @RequestParam("userPassword") String pwd, HttpSession session){


        if(userService.login(userCode,pwd)!=null){
            BackendUserDTO user=userService.login(userCode,pwd);
            session.setAttribute("userSession",user);
            session.setMaxInactiveInterval(1800);
            return "redirect:/backends/applist.html";
        }else{
            return "/login/login.html";
        }

    }

}
