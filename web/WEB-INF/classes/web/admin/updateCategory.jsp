<%--
  Created by IntelliJ IDEA.
  User: newbe
  Date: 2020/11/7
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
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
        $(document).ready(function (e) {

            $(".select1").uedSelect({
                width: 280
            });
            $(".select2").uedSelect({
                width: 167
            });
            $(".select3").uedSelect({
                width: 100
            });

        });

        function validateName() {
            var name = $('input[name="name"]');
            var result = $("#result");
            result.html("");
            if (name.val() == null || ($.trim(name.val())) === "") {
                result.html("分类名称不能为空！！");
                name.focus();
                return false;
            }
            return true;
        }

        function validateSort() {
            var sort = $('input[name="sort"]');
            var result = $("#result");
            result.html("");
            //验证非空
            if (sort.val() == null || ($.trim(sort.val())) === "") {
                result.html("分类排序号不能为空！！");
                sort.focus();
                return false;
            }
            //验证必须是正整数
            var regix = new RegExp("^\\+?[1-9][0-9]*$");//正整数
            // test返回true或false，符合规则返回true
            if(!regix.test($.trim(sort.val()))){
                result.html("分类排序号必须输入正整数！！");
                sort.focus();
                return false;
            }
            return true;
        }
        function validate(){
            if (validateName() && validateSort()){//所有的内容通过表单验证，才可以提交
                $("#form1").submit();
            }
        }
    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a>
        </li>
        <li><a href="#">修改分类</a>
        </li>
    </ul>
</div>
<form action="<c:url value='/AdminCategoryServlet' />" method="post" id="form1">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${item.categoryid}"/>
    <div class="formbody">
        <div class="formtitle">
            <span>修改分类</span>
        </div>

        <ul class="forminfo">
            <li><label>分类名称<b>*</b>
            </label><input name="name" type="text" class="dfinput" value="${item.name}" style="width:278px;" onblur="validateName()"/>
            </li>
            <li><label>商品分类排序<b>*</b>
            </label>
                <div class="vocation">
                    <input name="sort" type="text" class="dfinput" value="${item.sort}" style="width:278px;" onblur="validateSort()"/>
                </div>
            </li>
            <li><label style="color: red;width:278px" id="result">&nbsp;${msg}</label></li>
            <li>
                <label>&nbsp;</label>
                <input name="" type="submit" class="btn" value="修改"/>
            </li>
        </ul>

    </div>
</form>
</body>
</html>
