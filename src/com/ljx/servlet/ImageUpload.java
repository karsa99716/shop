package com.ljx.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class ImageUpload extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");//获取文件名时防止中文乱码
        response.setContentType("text/html;charset=utf-8");
        String action = request.getParameter("action");
        if("fileBrowse".equals(action)){
            fileBrowse(request,response);
        }else if("fileUpload".equals(action)){
            fileUpload(request,response);
        }
    }

    public void fileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

        String dir = "/uploadimages/";//文件保存根目录，保存到uploadimages目录下
        String type = "";
        if(request.getParameter("type") != null)//获取文件分类
            type = request.getParameter("type").toLowerCase() + "/";
        dir +=type;
        //获取回调JS的函数Num
        String callback = request.getParameter("CKEditorFuncNum");
        String filepath = upload(dir,request,response);
        String script ="<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("+callback+",'"+request.getContextPath() +filepath+"')</script>";
        response.getWriter().print(script);
    }

    public void fileBrowse(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getContextPath() + "/";
        String type = "";
        if(request.getParameter("type") != null)//获取文件分类
            type = request.getParameter("type").toLowerCase() + "/";
        String clientPath = "uploadimages/" + type;
        File root = new File(request.getSession().getServletContext().getRealPath(clientPath));
        if(!root.exists()){
            root.mkdirs();
        }
        String callback = request.getParameter("CKEditorFuncNum");
        File[] files = root.listFiles();
        if(files.length > 0){

            for(File file:files ) {
                String src = path + clientPath + file.getName();
                response.getWriter().print("<img width='110px' height='70px' src='" +src + "' alt='" + file.getName() + "' onclick=\"funCallback("+callback+",'"+ src +"')\">");
            }
        }else{
            response.getWriter().print("<h3>未检测到资源。</h3>");
        }
        String script ="<script type='text/javascript'>"+
                "function funCallback(funcNum,fileUrl){"+
                "var parentWindow = ( window.parent == window ) ? window.opener : window.parent;"+
                "parentWindow.CKEDITOR.tools.callFunction(funcNum, fileUrl);"+
                "window.close();}"+"</script>";
        response.getWriter().print(script);
    }


    public String upload(String dir,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * 1.创建DiskFileItemFactory对象
         * 2.使用工厂对象创建解析器
         * 3.使用解析器获取FileItem集合
         * 4.循环遍历FileItem集合，取出相应的表单项，如果是文件项，则保存到磁盘中
         */
        //1.创建DiskFileItemFactory对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2.使用工厂对象创建解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);

        String filename = "";

        try {
            //3.使用解析器获取FileItem集合
            List<FileItem> fileItemList = sfu.parseRequest(request);
            //4.循环遍历FileItem集合，取出相应的表单项，如果是文件项，则保存到磁盘中
            for(FileItem fileItem:fileItemList){
                //每次循环需要判断fileItem对象是否是表单项，分类型处理
                if(!fileItem.isFormField()){//是普通表单项
                    //是文件表单项
                    //获取文件名，获取的为在客户端上的绝对路径（带盘符）
                    filename = fileItem.getName();
                    //截取文件名
                    int index = filename.lastIndexOf("\\");
                    if(index!=-1){
                        filename=filename.substring(index+1);
                    }
                    //文件名唯一，且一个文件夹不能放置太多的文件
                    //使用年月日作为文件的放置路径，使用uuid作为文件名前缀
                    //Date date = new Date();

                    //获取年月日
// 					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
// 					dir = dir+ sdf.format(date).replace("-", "/")+"/";

                    //文件保存名称：加上uuid，保证文件名唯一
                    filename = UUID.randomUUID().toString().replace("-", "") + filename;
                    //构造一个文件对象
                    //获取项目路径下的绝对地址。
                    File file = new File(this.getServletContext().getRealPath(dir+filename));//这里是保存路径
                    if(!file.exists()){
                        file.getParentFile().mkdirs();//如果父目录不存在创建它们
                    }
                    fileItem.write(file);
                    filename = dir + filename;
                }
            }
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return filename;
    }
}

