package com.t31.app.controller.developer;

import com.t31.app.entity.DevUserDTO;
import com.t31.app.service.developer.DevUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/developer")
public class DeveloperUserController {
    @Autowired
    private DevUserService devUserService;
        @RequestMapping("/devlogin.html")
        public String devLoginBefore(){
            return "devlogin";
        }
        @RequestMapping("/index.html")
        public String index(){
            return "developer/main";
        }
        @RequestMapping("/dologin")
        public String doLogin(String devCode, String devPassword, Model model, HttpServletRequest request){
            //开发者登录提交地址
            DevUserDTO  devUser = devUserService.userLogin(devCode,devPassword);
            if(devUser!=null){
                //登录成功，跳转至index页面
                //设置session
                request.getSession().setAttribute("devUserSession",devUser);
                return "developer/main";

            }else{
                //登录失败,跳转至登录页面，显示信息
                model.addAttribute("error","用户名或密码不正确！");
                return "devlogin";
            }
        }
}
