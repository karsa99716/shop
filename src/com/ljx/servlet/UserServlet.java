package com.ljx.servlet;

import com.ljx.domain.User;
import com.ljx.exception.UserException;
import com.ljx.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("regist".equals(action)){
            regist(request,response);
        }else if ("login".equals(action)){
            login(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取参数,验证正确性
        2.调用Service层中的login方法进行登录
        3.登录成功，转到index.jsp页面
        4.登陆失败，转到login.jsp页面
         */
        //1.获取参数,验证正确性
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username==null||username.trim().isEmpty()){
            //用户名为空
            request.setAttribute("error","用户名不能为空！");
            request.getRequestDispatcher("/home/login.jsp").forward(request,response);
            return;
        }
        if (password==null||password.trim().isEmpty()){
            //密码为空
            request.setAttribute("username",username);
            request.setAttribute("error","密码不能为空！");
            request.getRequestDispatcher("/home/login.jsp").forward(request,response);
            return;
        }
        try {
            User user = userService.login(username,password);
            //登录成功，保存用户登陆成功的信息
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath()+"/home/index.jsp");
        } catch (UserException e) {
            request.setAttribute("error",e.getMessage());
            request.getRequestDispatcher("/home/login.jsp").forward(request,response);
        }
    }

    private void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取参数
        2.验证参数的正确性
        3.封装到User对象中
        4.调用Service层的rigist方法进行注册
        5.跳转到login.jsp页面（防止重复刷新）
         */
        //1.获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String email = request.getParameter("email");
        //2.验证参数的正确性，回显
        if (username==null||username.trim().isEmpty()){
            //用户名为空
            request.setAttribute("error","用户名不能为空！");
            request.setAttribute("email",email);
            request.getRequestDispatcher("/home/regist.jsp").forward(request,response);
            return;
        }
        //验证邮箱的正则表达式
        String regex="^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(email);
        if (!m.find()){//返回true表示符合规则，false表示不符合规则
            //邮箱为空
            request.setAttribute("username",username);
            request.setAttribute("error","邮箱格式不正确！");
            request.getRequestDispatcher("/home/regist.jsp").forward(request,response);
            return;
        }
        if (password==null||password.trim().isEmpty()){
            //密码为空
            request.setAttribute("username",username);
            request.setAttribute("email",email);
            request.setAttribute("error","密码不能为空！");
            request.getRequestDispatcher("/home/regist.jsp").forward(request,response);
            return;
        }if (!password.equals(password2)){
            //两次密码不一致
            request.setAttribute("username",username);
            request.setAttribute("email",email);
            request.setAttribute("error","两次密码不一致！");
            request.getRequestDispatcher("/home/regist.jsp").forward(request,response);
            return;
        }
        //3.封装到User对象中
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setLastlogintime(new Date());
        //4.调用Service层的rigist方法进行注册
        userService.regist(user);
        //5.跳转到login.jsp页面（防止重复刷新）
        request.setAttribute("msg","<script>alert('注册成功！');window.location.href='"+request.getContextPath()+"/home/login.jsp'</script>");
        request.getRequestDispatcher("/home/msg.jsp").forward(request,response);
    }
}
