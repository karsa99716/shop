<%--
  Created by IntelliJ IDEA.
  User: newbe
  Date: 2020/11/8
  Time: 15:02
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
    <script type="text/javascript" src="<c:url value='/admin/js/jquery.idTabs.min.js' />"></script>
    <script type="text/javascript" src="<c:url value='/admin/js/select-ui.min.js' />"></script>
    <script type="text/javascript" src="<c:url value='/admin/editor/kindeditor.js' />"></script>
    <script type="text/javascript" src="<c:url value='/admin/cal/lhgcore.js' />"></script>
    <script type="text/javascript" src="<c:url value='/admin/cal/lhgcalendar.js' />"></script>
    <script type="text/javascript" src="<c:url value='/ckeditor/ckeditor.js' />"></script>

    <script type="text/javascript">
        KE.show({
            id: 'content7',
            cssPath: './index.css'
        });
    </script>

    <script type="text/javascript">
        $(document).ready(function (e) {
            $(".select1").uedSelect({
                width: 345
            });
            $(".select2").uedSelect({
                width: 167
            });
            $(".select3").uedSelect({
                width: 100
            });
        });

        // 打开新窗口
        // f:链接地址
        // n:窗口的名称
        // w:窗口的宽度
        // h:窗口的高度
        // s:窗口是否有滚动条，1：有滚动条；0：没有滚动条
        function openWin(f, n, w, h, s) {
            sb = s == "1" ? "1" : "0";
            l = (screen.width - w) / 2;
            t = (screen.height - h) / 2;
            sFeatures = "left=" + l + ",top=" + t + ",height=" + h + ",width=" + w
                + ",center=1,scrollbars=" + sb + ",status=0,directories=0,channelmode=0";
            openwin = window.open(f, n, sFeatures);
            if (!openwin.opener)
                openwin.opener = self;
            openwin.focus();
            return openwin;
        }

    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a>
        </li>
        <li><a href="#">商品信息管理</a>
        </li>
    </ul>
</div>

<form action="<c:url value="/AdminProductServlet" /> " method="post" id="form1">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="productid" value="${item1.productid}"/>
    <div class="formbody">
        <div class="formtitle">
            <span>商品基本信息</span>
        </div>
        <ul class="forminfo">
            <li><label>商品名称<b>*</b>
            </label> <input name="name" type="text" class="dfinput" value="${item1.name}"
                            style="width:518px;"/>
            </li>

            <li><label>商品分类<b>*</b>
            </label>
                <div class="vocation">
                    <select class="select1" name="categoryid">
                        <c:forEach var="item" items="${list}">
                            <c:choose>
                                <c:when test="${item.categoryid==item1.categoryid}">
                                    <option value="${item.categoryid}" selected="selected">${item.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${item.categoryid}">${item.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </li>

            <li>
                <label>商品封面<b>*</b></label>
                <input type="hidden" id="filepath" name="photo" value=""/>
                <img id="img1" src="<c:url value='${item1.photo}' />"/>
                <a class="click" target="blank"
                   onclick='openWin("<c:url value='/admin/upload.jsp'/>","上传文件",500,300,0)'> 上传</a>
            </li>

            <li><label>商品价格<b>*</b>
            </label> <input name="price" type="text" class="dfinput" value="${item1.price}"
                            style="width:278px;"/>
            </li>

            <li><label>商品市场价格<b>*</b>
            </label> <input name="markprice" type="text" class="dfinput" value="${item1.markprice}"
                            style="width:278px;"/>
            </li>

            <li><label>商品数量<b>*</b>
            </label> <input name="quality" type="text" class="dfinput" value="${item1.quality}"
                            style="width:278px;"/>
            </li>

            <li><label>浏览量<b>*</b> </label> <input name="hit" type="text"
                                                   class="dfinput" value="${item1.hit}" style="width:278px;"/></li>

            <li><label>发布时间<b>*</b> </label> <input name="time" type="text"
                                                    class="dfinput" value="${item1.time}" style="width:278px;"
                                                    onclick="J.calendar.get();"/></li>

            <li><label>商品描述内容<b>*</b>
            </label></li>
            <li>
                 <textarea id="content7" name="content"
                           style="width:700px;height:250px;">${item1.content}</textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('content');
                </script>
            </li>
            <li><label style="color: red;width:278px" id="result">&nbsp;${msg}</label></li>
            <li><label>&nbsp;</label><input name="" type="submit"
                                            class="btn" value="修改"/>
            </li>
        </ul>

    </div>
</form>
</body>
</html>
