package com.gjc.servlet;

import com.gjc.service.PCAService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PCAServlet extends HttpServlet {
    private PCAService pcaService = new PCAService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");//防止参数中的中文乱码
        response.setContentType("text/html;charset=utf-8");//防止输出中的中文乱码
        String action = request.getParameter("action");
        if ("getprovinces".equals(action)){
            getProvinces(request,response);
        }else if ("getcities".equals(action)){
            getCities(request,response);
        }else if ("getareas".equals(action)){
            getAreas(request,response);
        }

    }

    private void getAreas(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.获取市级编号
        2.根据市级编号调用Service层来获取区列表
        3.封装到JSONArray中
        4.发送回页面
         */
        String cityid = request.getParameter("cityid");
        List<Map<String,Object>> list = pcaService.getAreas(cityid);
        JSONArray jsonArray = JSONArray.fromObject(list);
        response.getWriter().print(jsonArray.toString());
    }

    private void getCities(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.获取省级编号
        2.根据省级编号调用Service层来获取市列表
        3.封装到JSONArray中
        4.发送回页面
         */
        String provinceid = request.getParameter("provinceid");
        List<Map<String,Object>> list = pcaService.getCities(provinceid);
        JSONArray jsonArray = JSONArray.fromObject(list);
        response.getWriter().print(jsonArray.toString());

    }

    private void getProvinces(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.调用Service层获取省份列表
        2.将List封装到JSONArray对象中
        3.返回给页面
         */
        //1.调用Service层获取省份列表
        List<Map<String,Object>> list = pcaService.getProvinces();
        //2.将List封装到JSONArray对象中
        JSONArray jsonArray = JSONArray.fromObject(list);
        //3.返回给页面
        response.getWriter().print(jsonArray.toString());
    }
}
