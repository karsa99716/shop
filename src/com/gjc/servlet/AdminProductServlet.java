package com.gjc.servlet;

import com.gjc.domain.Page;
import com.gjc.domain.Product;
import com.gjc.service.CategoryService;
import com.gjc.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class AdminProductServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();
    private ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //请求参数中中文乱码问题
//        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("addBefore".equals(action)){
            addBefore(request,response);
        }else if ("add".equals(action)){
            add(request,response);
        }else if ("findAll".equals(action)){
            findAll(request,response);
        }else if ("find".equals(action)){
            find(request,response);
        }else if ("updatebefore".equals(action)){
            updateBefore(request,response);
        }else if ("update".equals(action)){
            update(request,response);
        }else if ("delete".equals(action)){
            delete(request,response);
        }else if ("deletemore".equals(action)){
            deleteMore(request,response);
        }
    }

    private void deleteMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取所有的checkbox值
        2.判断是否为空，转向List页面
        3.调用ProductService层的deleteMore方法
        4.转向List页面（防止刷新）
         */
        String ids[] = request.getParameterValues("sel");
        if (ids == null || ids.length==0){
            findAll(request,response);
            return;
        }
        productService.deleteMore(ids);
        request.setAttribute("msg","<script>alert('删除成功！');window.location.href='"+request.getContextPath()+"/AdminProductServlet?action=findAll'</script>");
        request.getRequestDispatcher("/admin/msg.jsp").forward(request,response);
    }

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        /*
        1.获取id
        2.调用ProductService的delete进行删除
        3.转向msg.jsp，再转向list页面
         */
        String id = request.getParameter("id");
        try {
            int productid = Integer.parseInt(id);
            productService.delete(productid);
            request.setAttribute("msg","<script>alert('删除成功！');window.location.href='"+request.getContextPath()+"/AdminProductServlet?action=findAll'</script>");
            request.getRequestDispatcher("/admin/msg.jsp").forward(request,response);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取表单数据
        2.验证表单数据的正确性
        3.将表单数据封装到Product对象中
        4.调用ProductService进行修改update
        5.刷新重复修改的问题
         */
        //1.获取表单数据
        String productid = request.getParameter("productid");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String markprice = request.getParameter("markprice");
        String photo = request.getParameter("photo");
        String categoryid = request.getParameter("categoryid");
        String content = request.getParameter("content");
        String quality = request.getParameter("quality");
        String hit = request.getParameter("hit");
        String time = request.getParameter("time");
        //3.封装到Product对象
        Product product = new Product();
        product.setProductid(Integer.parseInt(productid));
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setMarkprice(Float.parseFloat(markprice));
        product.setPhoto(photo);
        product.setCategoryid(Integer.parseInt(categoryid));
        product.setContent(content);
        product.setQuality(Integer.parseInt(quality));
        product.setHit(Integer.parseInt(hit));
        try {
            product.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(time));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //4.调用ProductService进行修改update
        productService.update(product);
        //5.返回到添加页面（禁止重复提交）
        request.setAttribute("msg","<script>alert('修改成功！');window.location.href='"+request.getContextPath()+"/AdminProductServlet?action=findAll'</script>");
        request.getRequestDispatcher("/admin/msg.jsp").forward(request,response);

    }

    private void updateBefore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /*
    1.获取参数id
    2.根据id读取记录，调用productService的findById方法
    3.读取所有的分类记录，调用CategoryService的findAll方法
    4.（2，3）中读取的数据都存入request中
    5.请求转发到updateProduct.jsp
     */
        String id = request.getParameter("id");
        request.setAttribute("item1",productService.findById(Integer.parseInt(id)));
        request.setAttribute("list",categoryService.findAll());
        request.getRequestDispatcher("/admin/updateProduct.jsp").forward(request,response);
    }

    private void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取id
        2.调用productService的findById方法获取id这一条记录
        3.存入request
        4.转发到productDes.jsp页面
         */
        String id = request.getParameter("id");
        request.setAttribute("item",productService.findById(Integer.parseInt(id)));
        request.getRequestDispatcher("/admin/productDes.jsp").forward(request,response);

    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /*
    1.获取当前页码参数
    2.调用ProductService层来读取数据
    3.把读取的数据保存request中
    4.请求转发到productlist.jsp页面
     */
        //1.获取当前页码参数
        String current = request.getParameter("current");
        //2.接收查询参数
        String skey = request.getParameter("skey");
        String svalue = request.getParameter("svalue");
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(current);
        }catch (Exception e){
            currentPage = 1;
        }
        //2.调用ProductService层来读取数据
        Page page = productService.findAll(currentPage,skey,svalue);
        request.setAttribute("page",page);
        request.setAttribute("skey",skey);
        request.setAttribute("svalue",svalue);
        request.getRequestDispatcher("/admin/productList.jsp").forward(request,response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    /*
    1.获取表单数据
    2.验证数据的正确性
    3.封装到Product对象
    4.调用ProductService层来添加一条记录
    5.返回到添加页面（禁止重复提交）
     */
        //1.获取表单数据
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String markprice = request.getParameter("markprice");
        String photo = request.getParameter("photo");
        String categoryid = request.getParameter("categoryid");
        String content = request.getParameter("content");
        String quality = request.getParameter("quality");
        String hit = request.getParameter("hit");
        String time = request.getParameter("time");
        //3.封装到Product对象
        Product product = new Product();
        product.setName(name);
        product.setPrice(Float.parseFloat(price));
        product.setMarkprice(Float.parseFloat(markprice));
        product.setPhoto(photo);
        product.setCategoryid(Integer.parseInt(categoryid));
        product.setContent(content);
        product.setQuality(Integer.parseInt(quality));
        product.setHit(Integer.parseInt(hit));
        try {
            product.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(time));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        //4.调用ProductService层来添加一条记录
        productService.add(product);
        //5.返回到添加页面（禁止重复提交）
        request.setAttribute("msg","<script>alert('发布成功！');window.location.href='"+request.getContextPath()+"/AdminProductServlet?action=addBefore'</script>");
        request.getRequestDispatcher("/admin/msg.jsp").forward(request,response);

    }

    private void addBefore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.调用CategoryService中的find All方法读取商品分类信息
        2.将数据存入request中
        3.请求转发到addProduct.jsp页面
         */
        //1.调用CategoryService中的find All方法读取商品分类信息
        List<Map<String,Object>> list = categoryService.findAll();
        //2.将数据存入request中
        request.setAttribute("list",list);
        //3.请求转发到addProduct.jsp页面
        request.getRequestDispatcher("/admin/addProduct.jsp").forward(request,response);
    }
}
