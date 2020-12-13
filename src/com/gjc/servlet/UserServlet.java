package com.gjc.servlet;

import com.gjc.domain.Activate;
import com.gjc.domain.User;
import com.gjc.exception.UserException;
import com.gjc.service.UserService;
import com.gjc.utils.DateUtils;
import com.gjc.utils.EmailUtils;
import com.gjc.utils.Md5Utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServlet extends HttpServlet {
    private UserService userService = new UserService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("regist".equals(action)){
            regist(request,response);
        }else if ("login".equals(action)){
            login(request,response);
        }else if ("activate".equals(action)){
            activate(request,response);
        }else if ("validateUsername".equals(action)){
            validateUsername(request,response);
        }else if ("exit".equals(action)){
            exit(request,response);
        }
    }

    private void exit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.将Session失效
        2.转向ProductServlet中,action=findIndex
         */
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath()+"/ProductServlet?action=findIndex");
    }

    private void validateUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.获取用户名
        2.调用Service层的validateUsername(name)，检查用户名是否被占用
        3.返回结果
         */
        String username = request.getParameter("username");
        boolean result = userService.validateUsername(username);
        response.getWriter().print(result);
    }

    private void activate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取验证码
        2.调用service的方法进行激活
        3.激活成功，转向登录页面
        4.激活失败，转向msg页面
         */
        String code = request.getParameter("code");
        try {
            userService.activate(code);
            //激活成功
            String msg = "<script>alert('激活成功');window.location.href='"+request.getContextPath()+"/home/login.jsp';</script>";
            request.setAttribute("msg",msg);
            request.getRequestDispatcher("/msg.jsp").forward(request,response);
            return;
        } catch (UserException e) {
            //激活失败
            request.setAttribute("msg",e.getMessage());
            request.getRequestDispatcher("/msg.jsp").forward(request,response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取参数,验证正确性
        2.调用Service层中的login方法进行登录
        3.登录成功，修改最近一次登录时间，转到index.jsp页面
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
        password = Md5Utils.md5(password);
        try {
            User user = userService.login(username,password);
            //修改最近一次登录时间，调用Service层的方法updatelastlogin方法
            userService.updateLastLoginTime(user);

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
        3.1对密码进行加密
        3.2封装到User对象中
        4.调用Service层的rigist方法进行注册
        5.调用Service层的addActivate(code)存入验证码
        6.向用户发送一封验证邮件
        7.跳转到login.jsp页面（防止重复刷新）
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
        //3.1对密码进行加密
        password = Md5Utils.md5(password);
        //3.2封装到User对象中
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setLastlogintime(new Date());
        user.setStatus(false);
        //4.调用Service层的rigist方法进行注册
        userService.regist(user);

        //5.1生成激活码
        String code = UUID.randomUUID().toString().replace("-","");

        //5.2调用Service层的addActivate(Activate)存入验证码
        //定义一个Activate对象
        //将值封装到这个对象中
        Activate activate = new Activate();
        activate.setCode(code);
        Date now = new Date();//过期时间一天之后
        now = DateUtils.checkOption("next",now);
        activate.setExpiredate(now);
        activate.setVipid(user.getVipid());
        userService.addActivate(activate);

        //6.向用户发送一封验证邮件
        EmailUtils send = new EmailUtils();
        send.sendActivateMail(email,code);
        //7.跳转到login.jsp页面（防止重复刷新）
        request.setAttribute("msg","<script>alert('注册成功！');window.location.href='"+request.getContextPath()+"/home/login.jsp'</script>");
        request.getRequestDispatcher("/home/msg.jsp").forward(request,response);
    }
}
