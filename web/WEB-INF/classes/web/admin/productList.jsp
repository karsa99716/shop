<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>productList</title>
    <link href="<c:url value='/admin/css/style.css' />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/admin/css/select.css' />" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<c:url value='/admin/js/jquery.js' />"></script>
    <script type="text/javascript" src="<c:url value='/admin/js/select-ui.min.js' />"></script>
    <script type="text/javascript">
        $(function () {
            $("#all").click(function () {
                if (this.checked) {
                    $(":checkbox").attr("checked", true);
                } else {
                    $(":checkbox").attr("checked", false);
                }
            });
        });
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
    </script>

</head>


<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="#">首页</a>
        </li>
        <li><a href="#">商品列表</a>
        </li>
    </ul>
</div>

<div class="rightinfo">

    <div class="tools">
        <ul class="toolbar">
            <li><a href="<c:url value='/AdminProductServlet?action=addBefore' />"><span><img
                    src="<c:url value='/admin/images/t01.png' />"/>
					</span>添加</a>
            </li>
            <li class="click"><a href="javascript:form1.submit();"><span><img
                    src="<c:url value='/admin/images/t03.png' />"/>
				</span>删除</a>
            </li>
        </ul>

        <form action="<c:url value='/AdminProductServlet' />" method="post">
            <input type="hidden" name="action" value="findAll"/>
            <ul class="seachform">
                <li><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    按关键字&nbsp;&nbsp;</label><input name="svalue" type="text" class="scinput"/>
                </li>
                <li><label>查询&nbsp;&nbsp;按</label>
                    <div class="vocation">
                        <select class="select3" name="skey">
                            <option value="categoryid">分类</option>
                            <option value="productid">编号</option>
                            <option value="name">名称</option>
                        </select>
                    </div>
                </li>
                <li><label>&nbsp;</label><input type="submit" class="scbtn" value="查询"/>
                </li>
            </ul>
        </form>

    </div>

    <table class="tablelist">
        <thead>
        <tr>
            <th><input id="all" name="" type="checkbox" value=""/>
            </th>
            <th>编号<i class="sort"><img src="<c:url value='/admin/images/px.gif' />"/>
            </i>
            </th>
            <th>商品名称</th>
            <th>价格</th>
            <th>市场价格</th>
            <th>库存量</th>
            <th>浏览量</th>
            <th>商品种类</th>
            <th>发布时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <form action="<c:url value='/AdminProductServlet' />" method="post" id="form1">
            <input type="hidden" name="action" value="deletemore"/>
            <c:forEach var="item" items="${page.list}">
                <tr>
                    <td><input name="sel" type="checkbox" value="${item.productid}"/>
                    </td>
                    <td>${item.productid}</td>
                    <td>${item.name}</td>
                    <td>${item.price}</td>
                    <td>${item.markprice}</td>
                    <td>${item.quality}</td>
                    <td>${item.hit}</td>
                    <td>${item.cname}</td>
                    <td>${item.time}</td>
                    <td><a href="<c:url value='/AdminProductServlet?action=find&id=${item.productid}' />"
                           class="tablelink">查看</a> <a
                            href="<c:url value='/AdminProductServlet?action=updatebefore&id=${item.productid}' />"
                            class="tablelink"> 修改</a>
                        <a href='javascript:if (confirm("确定要删除吗？"))window.location.href="<c:url value='/AdminProductServlet?action=delete&id=${item.productid}'/>";'
                           class="click"> 删除</a>
                    </td>
                </tr>
            </c:forEach>
        </form>
        </tbody>
    </table>


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
                            href="<c:url value='/AdminProductServlet?action=findAll&current=${page.currentPage-1}&skey=${skey}&svalue=${svalue}' />"><span
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
                                href="<c:url value='/AdminProductServlet?action=findAll&current=${i}&skey=${skey}&svalue=${svalue}' />">${i}</a>
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
                            href="<c:url value='/AdminProductServlet?action=findAll&current=${page.currentPage+1}&skey=${skey}&svalue=${svalue}' />"><span
                            class="pagenxt"></span>
                    </a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>

</div>

<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>
</body>
</html>