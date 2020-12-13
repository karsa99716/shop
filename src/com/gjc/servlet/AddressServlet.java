package com.gjc.servlet;

import com.gjc.domain.Address;
import com.gjc.domain.User;
import com.gjc.service.AddressService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddressServlet extends HttpServlet {
    private AddressService addressService = new AddressService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("add".equals(action)){
            add(request,response);
        }else if ("paybefore".equals(action)){
            payBefore(request,response);
        }
    }

    private void payBefore(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /*
        1.判断用户是否登录
        2.没有登录的话，转向登录页面
        3.登录，从session取出user对象
        4.通过userid查找该用户的所有记录，调用Service中的findAll（int userid）
        5.重定向到pay.jsp
         */
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
//        if (user==null){//没有登录
//            response.sendRedirect(request.getContextPath()+"/home/login.jsp");
//            return;
//        }
        int vipid = user.getVipid();
        request.setAttribute("addresslist",addressService.findAll(vipid));
        request.getRequestDispatcher("/person/pay.jsp").forward(request,response);


    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        /*
        0.判断用户是否登录
        1.首先取出参数
        2.验证
        3.封装到Address对象
        4.调用Service的add方法添加一条记录
        5.返回到pay.jsp页面（显示新的地址，读），防止刷新重复提交
         */
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
//        if (user==null){//用户未登录，返回登陆页面
//            response.sendRedirect(request.getContextPath()+"/home/login.jsp");
//            return;
//        }
        String receiver = request.getParameter("receiver");
        String phone = request.getParameter("phone");
        String postcode = request.getParameter("postcode");
        String province = request.getParameter("province");
        String city = request.getParameter("city");
        String area = request.getParameter("area");
        String addressname = request.getParameter("addressname");
        int vipid = user.getVipid();
        //3.封装到Address对象
        Address address = new Address();
        address.setAddressname(addressname);
        address.setProvince(province);
        address.setCity(city);
        address.setArea(area);
        address.setPostcode(postcode);
        address.setPhone(phone);
        address.setReceiver(receiver);
        address.setVipid(vipid);

        addressService.add(address);
        request.setAttribute("msg","<script>alert('添加成功！');window.location.href='"+request.getContextPath()+"/AddressServlet?action=paybefore'</script>");
        request.getRequestDispatcher("/home/msg.jsp").forward(request,response);

    }
}
