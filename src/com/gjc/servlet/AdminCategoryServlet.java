package com.gjc.servlet;

import com.gjc.domain.Category;
import com.gjc.domain.Page;
import com.gjc.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminCategoryServlet extends HttpServlet {
    private CategoryService categoryService = new CategoryService();

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException{
        doPost(request,response);
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
        /*
        1.获取action的值，根据action值调用不同的方法
         */
        String action = request.getParameter("action");
        if ("add".equals(action)){
            add(request,response);
        }else if ("findAll".equals(action)){
            findAll(request,response);
        } else if ("delete".equals(action)){
            deleteById(request,response);
        }else if ("deleteMore".equals(action)){
            deleteMore(request,response);
        }else if ("updatebefore".equals(action)){
            updateBefore(request,response);
        }else if ("update".equals(action)){
            update(request,response);
        }

    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取要修改的所有信息
        2.验证
        3.调用Service层进行修改
        4.禁止刷新重复提交，先将它转向msg.jsp
         */
        //1.获取表单数据
        String name = request.getParameter("name");
        String sort = request.getParameter("sort");
        String id = request.getParameter("id");
        //2.进行验证表单数据的正确性
        if (name==null||name.trim().isEmpty()){
            //name为空
            request.setAttribute("msg","分类名称不能为空");
            request.getRequestDispatcher("/admin/updateCategory.jsp").forward(request,response);
            return;
        }
        if (sort==null||sort.trim().isEmpty()){
            //sort为空
            request.setAttribute("msg","分类排序号不能为空");
            request.getRequestDispatcher("/admin/updateCategory.jsp").forward(request,response);
            return;
        }
        String regex="^[1-9]+[0-9]*$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(sort);
        if (!m.find()){//返回true表示符合规则，false表示不符合规则
            request.setAttribute("msg","分类排序号必须为正整数");
            request.getRequestDispatcher("/admin/updateCategory.jsp").forward(request,response);
        }
        //2.1判断name是否存在，存在返回错误信息，不存在继续添加
        //调用service中的方法validateName(name)，如果存在则返回true，不存在返回false
        if(categoryService.validateName(name)){
            request.setAttribute("msg","该分类名称已被占用");
            request.getRequestDispatcher("admin/updateCategory.jsp").forward(request,response);
            return;
        }

        //3.封装到Category对象中
        Category category = new Category();
        category.setName(name);
        category.setSort(Integer.parseInt(sort));
        category.setCategoryid(Integer.parseInt(id));
        //4.调用Service层方法进行修改
        categoryService.update(category);
        //5.转到本身页面（以后再改）
        request.setAttribute("msg","<script>alert('修改成功！');window.location.href='"+request.getContextPath()+"/AdminCategoryServlet?action=findAll'</script>");
        request.getRequestDispatcher("/admin/msg.jsp").forward(request,response);
    }

    private void updateBefore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取要修改的id
        2.通过Service层把这条记录读取出来
        3.存入到request
        4.请求转发到updateCategory.jsp
         */
        String id = request.getParameter("id");
        request.setAttribute("item",categoryService.findById(Integer.parseInt(id)));
        request.getRequestDispatcher("/admin/updateCategory.jsp").forward(request,response);

    }

    private void deleteMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取要删除的id数组
        2.判断id是否为空
        3.调用Service层进行删除
        4.调用findAll方法重新显示删除后的记录
         */
        String []ids = request.getParameterValues("sel");
        if (ids!=null){
            categoryService.deleteMore(ids);
            findAll(request,response);
        }
    }

    private void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取要删除的ID
        2.调用Service层来进行删除
        3.调用find All方法来显示新的列表
         */
        String str = request.getParameter("id");
        categoryService.delectById(Integer.parseInt(str));
        findAll(request,response);
    }

    private void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        0.获取当前页码，如果没有当前页码，默认为第一页
        1.调用Service层的findAll方法，来获取数据Page对象
        2.把数据保存到request
        3.请求转发catagorylist.jsp
         */
        //0.获取当前页码，如果没有当前页码，默认为第一页
        String current = request.getParameter("current");
        int currentPage = 1;
        try {
            currentPage = Integer.parseInt(current);
        }catch (Exception e){
            currentPage = 1;
        }
        Page page = categoryService.findAll(currentPage);
        request.setAttribute("page",page);
        request.getRequestDispatcher("/admin/categoryList.jsp").forward(request,response);
    }

    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        1.获取表单数据
        2.进行验证表单数据的正确性，判断name是否已存在
        3.封装到Category对象中
        4.调用Service层方法进行添加
        5.转到本身页面（以后再改）
         */
        //1.获取表单数据
        String name = request.getParameter("name");
        String sort = request.getParameter("sort");
        //2.进行验证表单数据的正确性
        if (name==null||name.trim().isEmpty()){
            //name为空
            request.setAttribute("msg","分类名称不能为空");
            request.getRequestDispatcher("/admin/addCategory.jsp").forward(request,response);
            return;
        }
        if (sort==null||sort.trim().isEmpty()){
            //sort为空
            request.setAttribute("msg","分类排序号不能为空");
            request.getRequestDispatcher("/admin/addCategory.jsp").forward(request,response);
            return;
        }
        String regex="^[1-9]+[0-9]*$";
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(sort);
        if (!m.find()){//返回true表示符合规则，false表示不符合规则
            request.setAttribute("msg","分类排序号必须为正整数");
            request.getRequestDispatcher("/admin/addCategory.jsp").forward(request,response);
        }
        //2.1判断name是否存在，存在返回错误信息，不存在继续添加
            //调用service中的方法validateName(name)，如果存在则返回true，不存在返回false
        if(categoryService.validateName(name)){
            request.setAttribute("msg","该分类名称已被占用");
            request.getRequestDispatcher("admin/addCategory.jsp").forward(request,response);
            return;
        }

        //3.封装到Category对象中
        Category category = new Category();
        category.setName(name);
        category.setSort(Integer.parseInt(sort));
        //4.调用Service层方法进行添加
        categoryService.add(category);
        //5.转到本身页面（以后再改）
        request.setAttribute("msg","<script>alert('添加成功！');window.location.href='"+request.getContextPath()+"/AdminCategoryServlet?action=findAll'</script>");
        request.getRequestDispatcher("/admin/msg.jsp").forward(request,response);
    }
}
