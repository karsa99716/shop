package com.ljx.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ImageUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //上传路径
        String path = "/uploadimages/";
        String ext[] = {"jpg","gif","bmp","png","JPG","GIF","BMP","PNG"};
        try {
            request.setAttribute("path",upload(path,request,response,ext));
        } catch (Exception e) {
            request.setAttribute("msg",e.getMessage());
        }
        request.getRequestDispatcher("/admin/upload.jsp").forward(request,response);

    }

    //上传文件，返回上传路径
    public String upload(String path, HttpServletRequest request, HttpServletResponse response,String... ext) throws Exception {
        //1.创建工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2.通过工厂对象创建解析器
        ServletFileUpload sfu = new ServletFileUpload(factory);
        String filename = "";//获取文件名
        //3.解析request，获取fileitem的列表
        try {
            List<FileItem> items = sfu.parseRequest(request);
            //4.通过循环获取表单项
            for (FileItem item : items) {
                //判断是否是普通表单项
                if (item.isFormField()) {
                    String name = item.getFieldName();//获取表单项的name
                    String value = item.getString("utf-8");//获取表单项的值，utf-8，防止中文乱码
                    System.out.println(name + ":" + value);
                } else {
                    //文件表单项
                    filename = item.getName();//获取文件名
                    //判断文件名是否为空
                    if (filename==null || filename.trim().equals("")){
                        throw new Exception("上传文件名称不能为空");
                    }
                    //判断文件扩展名是否正确
                    String type = filename.substring(filename.lastIndexOf(".")  + 1);
                    boolean flag = false;
                    if (ext!=null && ext.length>0){
                        for (int i=0;i<ext.length;i++){
                            if (type.equals(ext[i])){
                                flag = true;
                                break;
                            }
                        }
                        if (!flag){
                            throw new Exception("请选择一个有效的图片文件！");
                        }
                    }

                    //截取文件名
                    int index = filename.indexOf("\\");
                    if (index != -1) {
                        filename = filename.substring(index + 1);
                    }

                    //防止上传重名的问题
                    filename = UUID.randomUUID().toString().replace("-","")+filename;

                    filename = path+filename;
                    File file = new File(this.getServletContext().getRealPath(filename));
                    if (!file.exists()) {
                        file.getParentFile().mkdirs();//创建上层目录
                    }
                    //保存
                    item.write(file);
                }
            }
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        }
        return filename;
    }
}
