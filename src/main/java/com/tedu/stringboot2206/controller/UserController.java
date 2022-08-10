package com.tedu.stringboot2206.controller;
import com.tedu.stringboot2206.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@Controller
public class UserController {
    //表示保存所有用户信息的目录users
    private static File userDir;
    static{
        userDir = new File("./users");
        if (!userDir.exists()){
            userDir.mkdirs();
        }
    }
    /**
     *
     * @param req 请求对象,封装着浏览器发送过来的所有内容
     * @param resp 相应对象,封装着我们即将给浏览器回复的内容
     */
    @RequestMapping("/regUser")
    public void reg(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("开始处理注册！！！");
        /*
            处理注册的过程
            1：获取用户在注册页面上输入的注册信息(通过请求对象获取浏览器提交的表单数据)
            2：处理注册
            3：设置相应对象,将处理结果回馈给浏览器
         */
        String username = req.getParameter("username");//获取用户名
        String password = req.getParameter("password");//获取密码
        String nickname = req.getParameter("nickname");//获取昵称
        String ageStr = req.getParameter("age");//获取年龄
        System.out.println(username+","+password+","+nickname+","+ageStr);
        /*
            String username =request.getParameter("username");
            作用:获取浏览器传递过来的参数username对应的值
            返回的字符串可能存在两种特殊情况：
            1：返回空字符串，说明用户在该输入矿上没有输入任何内容
            2：返回null，说明没有传递该参数过来
         */
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty() ||
            nickname == null || nickname.isEmpty() ||
            ageStr == null || ageStr.isEmpty() ||
            !ageStr.matches("[0-9]+")){
            try{
                resp.sendRedirect("/reg_info_error.html");
            }catch (IOException e){
                e.printStackTrace();
            }
            return;
        }

        int age = Integer.parseInt(ageStr);//将年龄转换为int值
        /*
            2、
            将该注册用户信息以User对象姓氏表示并序列化到文件中保存

            将来所有的保存用户信息的文件都统一放在users目录下,并且每隔保存用户的文件的名字格式:用户名.obj


         */
        User user = new User(username,password,nickname,age);
        /*
            File的重载构造器
            File(File paremt,String child)
            该File对象表达的是在parent表示的目录中的子项child
         */
        //在userDir(该对象表达当前项目目录下的users目录)目录中的文件xxx.obj(xxx就是当前注册用户名)
        File file = new File(userDir,username+".obj");
        //如果该文件存在则说明这是一个重复的用户
        if (file.exists()){
            try {
                resp.sendRedirect("/have_user.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try (
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ){
            oos.writeObject(user);
        }catch (IOException e){
            e.printStackTrace();
        }

        //3
        try{
            /*
                让浏览器重定向到指定路径查看处理结果页面
                这里的路径“/reg_success.html”是发送给浏览器让其理解的。
                所以浏览器还是当这里的“/”为抽象路径中第一个“/”的位置
                相当于浏览器会根据该路径请求:
                http://localhost:8080/reg_syccess.html
             */
            resp.sendRedirect("/reg_success.html");
        }catch (IOException e){
            e.printStackTrace();
        }


//        user.setUsername(username);
//        user.setPassword(password);
//        user.setNickname(nickname);
//        user.setAge(age);
    }
}
