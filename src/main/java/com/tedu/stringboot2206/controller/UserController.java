package com.tedu.stringboot2206.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;
@Controller
public class UserController {
    @RequestMapping("/regUser")
    public void reg(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("开始处理注册！！！");
    }
}
