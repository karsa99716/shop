package com.gjc.servlet;

import com.gjc.domain.Page;
import com.gjc.service.ProductService;
import com.gjc.utils.BaseCalculate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductServlet extends HttpServlet {

    private ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("findIndex".equals(action)){
            findIndex(request,response);
        }else if ("findAll".equals(action)){
            findAll(request,response);
        }else if ("findById".equals(action)){
            findById(request,response);
        }else if ("addcart".equals(action)){
            addCart(request,response);
        }else if ("updateBuyCount".equals(action)){
            updateBuyCount(request,response);
        }else if ("deletecart".equals(action)){
            deleteCart(request,response);
        }else if ("deletecartMore".equals(action)){
            deleteCartMore(request,response);
        }else {
            findIndex(request,response);
        }

    }

    private void deleteCartMore(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.获取要删除的ids
        2.判断ids是否为空，不为空，可以删除
            2.1.获取购物车
            2.2.循环购物车，取出每个商品，对比是否是要删除的商品，如果是，删除，更新购物车总计
        3.返回cart.jsp页面
         */
        //1.获取要删除的ids
        String ids[] = request.getParameterValues("sel");
        if (ids!=null && ids.length>0){
            //2.1.获取购物车
            HttpSession session = request.getSession();
            List<Map<String,Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
            float totalprice = (float) session.getAttribute("totalprice");
            //2.2.循环购物车，取出每个商品，对比是否是要删除的商品，如果是，删除，更新购物车总计
            for (int j=0;j<ids.length;j++){
                //取出要删除的第j个商品
                for (int i=0;i< cart.size();i++){
                    Map<String,Object> item = cart.get(i);
                    String itemproductid = item.get("productid").toString();
                    if (itemproductid.equals(ids[j])){
                        //找到要删除的商品
                        float total = (float) item.get("total");
                        totalprice = BaseCalculate.substract(totalprice,total);
                        cart.remove(i);//删除该商品
                    }
                }
            }
            session.setAttribute("cart",cart);
            session.setAttribute("totalprice",totalprice);
        }
        response.sendRedirect(request.getContextPath()+"/home/cart.jsp");
    }

    private void deleteCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.获取要删除的id
        2.获取购物车
        3.循环购物车，找到该商品，从购物车中删除，同时从总计中减去该商品的total
        4.保存购物车到session域中
        5.返回cart.jsp页面
         */
        //1.获取要删除的id
        String id = request.getParameter("id");
        //2.获取购物车
        HttpSession session = request.getSession();
        List<Map<String,Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        float totalprice = (float) session.getAttribute("totalprice");
        //3.循环购物车，找到该商品，从购物车中删除，同时从总计中减去该商品的total
        for (int i=0;i<cart.size();i++){
            Map<String,Object> map = cart.get(i);
            if (map.get("productid").toString().equals(id)){
                //找到要删除的商品
                //在删除之前，将购物车所有商品总计-该商品的总价
                float total = (float) map.get("total");
                totalprice = BaseCalculate.substract(totalprice,total);
                cart.remove(i);
                break;
            }
        }
        //4.保存购物车和totalprice到session域中
        session.setAttribute("cart",cart);
        session.setAttribute("totalprice",totalprice);
        //5.返回cart.jsp页面
        response.sendRedirect(request.getContextPath()+"/home/cart.jsp");
    }

    private void updateBuyCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.获取参数id和buycount
        2.获取购物车
        3.循环购物车，找到商品，更新购买数量以及total
          同时在循环的过程中，重新计算购物车的总价
        4.把购物车和购物车总计重新保存回session
        5.需要把total和totalprice封装成json的形式返回
         */
        //1.获取参数id和buycount
        String sid = request.getParameter("id");//要修改数量的商品id
        String sbuycount = request.getParameter("buycount");
        int id =Integer.parseInt(sid);
        int buycount = Integer.parseInt(sbuycount);
        if (buycount<1){
            response.sendRedirect(request.getContextPath()+"/home/cart.jsp");
            return;
        }
        //2.获取购物车
        HttpSession session = request.getSession();
        List<Map<String,Object>> cart = (List<Map<String, Object>>) session.getAttribute("cart");
        float idtotal = 0;//更新后该商品的总价
        float totalprice = 0;//购物车所有商品的总价
        //3.循环购物车，找到商品，更新购买数量以及total
        for (int i=0;i<cart.size();i++){
            Map<String,Object> item = cart.get(i);//购物车中的商品
            if (item.get("productid").toString().equals(sid)){
                //找到该商品
                item.put("buycount",buycount);
                float price = Float.parseFloat(item.get("price").toString());
                idtotal = BaseCalculate.multiply(buycount,price);
                item.put("total",idtotal);
                totalprice = BaseCalculate.add(totalprice,idtotal);
            }else {
                float total = Float.parseFloat(item.get("total").toString());
                totalprice = BaseCalculate.add(totalprice,total);
            }
        }
        session.setAttribute("cart",cart);
        session.setAttribute("totalprice",totalprice);
        //{"total":idtotal,totalprice":totalprice}
        String jsonstr = "{\"total\":"+idtotal+",\"totalprice\":"+totalprice+"}";
        response.getWriter().print(jsonstr);
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
        1.获取商品的id和购买数量
        2.根据id获取商品信息（Service的findById方法）
        3.从session中取出购物车
        4.不存在，创建购物车，然后把该商品放入购物车中
        5.存在，购物车中没有该商品，直接放入购物车
               购物车中有该商品，需要取出该商品，商品数量加上购买数量，更新总计
         */
        //1.获取商品的id和购买数量
        int id = Integer.parseInt(request.getParameter("productid"));
        int buycount = Integer.parseInt(request.getParameter("buycount"));

        //2.根据id获取商品信息（Service的findById方法）
        Map<String,Object> map = productService.findById(id);

        //3.从session中取出购物车
        List<Map<String,Object>> cart = null;
        HttpSession session = request.getSession();
        cart = (List<Map<String, Object>>) session.getAttribute("cart");
        float price = Float.parseFloat(map.get("price").toString());//该商品单价
        float totalprice = 0;
        //4.判断购物车是否存在
        if (cart == null){
            //不存在，创建购物车
            cart = new ArrayList<Map<String, Object>>();
            //把商品数量和该商品总计放入map
            map.put("buycount",buycount);
            map.put("total", BaseCalculate.multiply(price,buycount));
            //把map放入购物车中
            cart.add(map);
            totalprice = BaseCalculate.multiply(price,buycount);
        }else {
            //存在,通过循环查找是否有该商品
            boolean incart = false;//标记该商品是否在购物车中
            for (int i=0;i<cart.size();i++){
                Map<String,Object> item = cart.get(i);//购物车中的商品
                if (item.get("productid").equals(map.get("productid"))){
                    //购物车中有该商品，更新buycount和total
                    buycount = (int) BaseCalculate.add(buycount,Integer.parseInt(item.get("buycount").toString()));
                    item.put("buycount",buycount);
                    item.put("total",BaseCalculate.multiply(buycount,price));
                    incart = true;
                    totalprice = BaseCalculate.add(totalprice,BaseCalculate.multiply(buycount,price));
                }else {
                    totalprice = BaseCalculate.add(totalprice,Float.parseFloat(item.get("total").toString()));
                }
            }
            if (!incart){//没有在购物车中
                //把商品数量和该商品总计放入map
                map.put("buycount",buycount);
                map.put("total",BaseCalculate.multiply(price,buycount));
                //把map放入购物车中
                cart.add(map);
                totalprice = BaseCalculate.add(totalprice,BaseCalculate.multiply(price,buycount));
            }
        }
        session.setAttribute("totalprice",totalprice);
        session.setAttribute("cart",cart);//把购物车放入到session中
        response.sendRedirect(request.getContextPath()+"/home/cart.jsp");
    }

    private void findById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取id，根据id查找该条记录（ProductService）
        2.把该条记录保存到request中
        3.请求转发到productDetail.jsp页面
         */
        String sid = request.getParameter("id");
        request.setAttribute("item",productService.findById(Integer.parseInt(sid)));
        request.getRequestDispatcher("/home/productDetails.jsp").forward(request,response);
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String current = request.getParameter("current");
       int currentPage;
       try {
           currentPage = Integer.parseInt(current);
       }catch (Exception e){
           currentPage = 1;
       }
       String skey = "name";
       String svalue = request.getParameter("svalue");
       String sortkey = request.getParameter("sortkey");
       String sort = request.getParameter("sort");
       Page page = productService.findAll(currentPage,skey,svalue,sortkey,sort);
        request.setAttribute("sortkey",sortkey);
        request.setAttribute("sort",sort);
        request.setAttribute("svalue",svalue);
       request.setAttribute("page",page);
       request.getRequestDispatcher("/home/productList.jsp").forward(request,response);
    }


    private void findIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.调用Service层的方法，读取1-12条记录
        2.存入到request中
        3.请求转发到myIndex.jsp页面
         */
        request.setAttribute("list",productService.findIndex());
        request.getRequestDispatcher("/home/myIndex.jsp").forward(request,response);
    }
}
