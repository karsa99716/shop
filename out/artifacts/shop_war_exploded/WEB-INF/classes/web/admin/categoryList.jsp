
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>right</title>
    <link href="<c:url value='/admin/css/style.css' />" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/admin/js/jquery.js' />"></script>

    <script type="text/javascript">
        $(function () {
            $("#all").click(function () {
                if (this.checked) {
                    $(":checkbox").attr("checked", true)
                } else {
                    $(":checkbox").attr("checked", false)
                }
            });
        })
        // $(document).ready(function () {
        //     $(".click").click(function () {
        //         $(".tip").fadeIn(200);
        //     });
        //
        //     $(".tiptop a").click(function () {
        //         $(".tip").fadeOut(200);
        //     });
        //
        //     $(".sure").click(function () {
        //         $(".tip").fadeOut(100);
        //     });
        //
        //     $(".cancel").click(function () {
        //         $(".tip").fadeOut(100);
        //     });
        //
        // });
    </script>
</head>

<body>

<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a>
        </li>
        <li><a href="#">分类列表</a>
        </li>
    </ul>
</div>

<div class="rightinfo">
    <form action="<c:url value='/AdminCategoryServlet' /> " method="post" name="form1">
        <input type="hidden" name="action" value="deleteMore"/>
        <div class="tools">
            <ul class="toolbar">
                <li><a href="addCategory.html"><span><img
                        src="<c:url value='/admin/images/t01.png' />"/>
					</span>添加</a>
                </li>
                <li class="click"><a href="javascript:form1.submit();"><span><img src="<c:url value='/admin/images/t03.png' />"/>
                </span>删除</a>
                </li>
            </ul>
        </div>

        <table class="tablelist">
            <thead>
            <tr>
                <th><input id="all" type="checkbox"/>
                </th>
                <th>编号<i class="sort"><img src="<c:url value='/admin/images/px.gif' />"/>
                </i>
                </th>
                <th>分类名称</th>
                <th>分类排序</th>
                <th>操作</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="item" items="${page.list}">
                <tr>
                    <td><input name="sel" type="checkbox" value="${item.categoryid}"/>
                    </td>
                    <td>${item.categoryid}</td>
                    <td>${item.name}</td>
                    <td>${item.sort}</td>
                    <td><a href="<c:url value="/AdminCategoryServlet?action=updatebefore&id=${item.categoryid}" />" class="tablelink"
                           target="rightFrame">修改</a>
                        <a href="javascript:if (confirm('确定要删除吗？')) window.location.href=
                    '<c:url value="/AdminCategoryServlet?action=delete&id=${item.categoryid}&currentPage=${page.currentPage}" />';"
                           class="click"> 删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>

        </table>
    </form>


    <div class="pagin">
        <div class="message">
            共<i class="blue">${page.totalSize}</i>条记录，当前显示第&nbsp;<i
                class="blue">${page.currentPage}&nbsp;</i>页/共&nbsp;<i class="blue">${page.totalPage}&nbsp;</i>页
        </div>
        <!-- 上一页 -->

        <ul class="paginList">
            <c:choose>
                <c:when test="${page.currentPage==1}">
                    <li class="paginItem"><a href="javascript:;"><span
                            class="pagepre"></span>
                    </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="paginItem"><a
                            href="<c:url value='/AdminCategoryServlet?action=findAll&current=${page.currentPage-1}' />"><span
                            class="pagepre"></span>
                    </a>
                    </li>
                </c:otherwise>
            </c:choose>

            <c:forEach var="i" begin="${page.start}" end="${page.end}">
                <c:choose>
                    <c:when test="${page.currentPage==i}">
                        <li class="paginItem current"><a href="javascript:;">${i}</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="paginItem"><a
                                href="<c:url value='/AdminCategoryServlet?action=findAll&current=${i}' />">${i}</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:choose>
                <c:when test="${page.currentPage==page.totalPage}">
                    <li class="paginItem"><a href=""><span
                            class="pagenxt"></span>
                    </a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="paginItem"><a
                            href="<c:url value='/AdminCategoryServlet?action=findAll&current=${page.currentPage+1}' />"><span
                            class="pagenxt"></span>
                    </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>

    <div class="tip">
        <div class="tiptop">
            <span>提示信息</span><a></a>
        </div>

        <div class="tipinfo">
				<span><img src="images/ticon.png"/>
				</span>
            <div class="tipright">
                <p>是否确认对信息的删除 ？</p>
                <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
            </div>
        </div>
        <div class="tipbtn">
            <input name="" type="button" class="sure" value="确定"/>&nbsp; <input
                name="" type="button" class="cancel" value="取消"/>
        </div>

    </div>
</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>
</body>
</html>

