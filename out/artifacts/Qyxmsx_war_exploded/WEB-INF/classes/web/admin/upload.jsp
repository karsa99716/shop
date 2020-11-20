<%--
  Created by IntelliJ IDEA.
  User: newbe
  Date: 2020/11/8
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <link href="<c:url value='/admin/css/style.css' />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/admin/css/select.css' />" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/admin/js/jquery.js' />"></script>
    <script type="text/javascript" src="<c:url value='/admin/js/select-ui.min.js' />"></script>
    <script type="text/javascript">
        $(function () {
            $("#sure").click(function () {
                var path = $("#path").val();
                $("#filepath", window.opener.document).val(path);
                $("#img1", window.opener.document).attr("src",<c:url value='/' />  +path+"?t="+Math.random());
                window.close();
            });
        });
        //上传前判断文件是否选择，且必须为图片
        function validateName(){
            var name = $('input[name="imgpath"]');
            var result = $("#result");
            result.html("");
            if (name.val()==null || $.trim(name.val())==""){
                result.html("上传文件名称不能为空！");
                return false;
            }
            var ext = ["jpg","gif","bmp","png","JPG","GIF","BMP","PNG"];
            var start = name.val().lastIndexOf(".") + 1;
            var type = name.val().substr(start);
            var flag = false;
            for (var i=0;i<ext.length;i++){
                if (type == ext[i]){
                    flag = true;
                    break;
                }
                if (flag){
                    return true;
                }else {
                    result.html("请选择一个有效的图片文件！")
                    return false;
                }
            }
        }
    </script>
</head>

<body>


<form action="<c:url value="/ImageUploadServlet" /> " method="post" enctype="multipart/form-data">
    <input type="hidden" id="path" value="${path}"/>
    <div class="formbody">
        <div class="formtitle">
            <span>上传</span>
        </div>
        <ul class="forminfo">
            <li><label>文件</label>
                <div class="vocation">
                    <input name="imgpath" type="file" class="dfinput" style="width:200px;"/>
                    <input type="submit" class="btn" value="上传" onclick="javascript:return validateName()"/>
                </div>
            </li>
            <li><label style="color:red;width:278px;" id="result">&nbsp;</label></li>
            <li><label id="result" style="color: red;width: 278px">${msg}</label>
                &nbsp;&nbsp;
                <input type="button" id="sure" class="btn" value="确定"/>

            </li>
        </ul>

    </div>
</form>
</body>
</html>
