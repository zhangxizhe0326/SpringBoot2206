package com.tedu.stringboot2206.controller;

import com.tedu.stringboot2206.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.tedu.stringboot2206.controller.UserController.userDir;

@Controller
public class UserAuthentication {
    @RequestMapping("/loginUser")
    public void reg(HttpServletRequest req, HttpServletResponse resp) throws IOException, ClassNotFoundException {
        System.out.println("开始验证登录信息！！！");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User();
        File file = new File(userDir,username+".obj");
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object= ois.readObject();
        User users = (User) object;
        if (username == null || username.isEmpty() || password == null || password.isEmpty()){
            try{
                resp.sendRedirect("/login_info_error.html");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        if (username.equals(users.getUsername()) && password.equals(users.getPassword())){
            try{
                resp.sendRedirect("/login_success.html");
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            try{
                resp.sendRedirect("/login_fail.html");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
