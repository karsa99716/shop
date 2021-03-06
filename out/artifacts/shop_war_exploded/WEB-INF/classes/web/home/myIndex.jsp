﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title>首页</title>

		<link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value='/AmazeUI-2.4.2/assets/css/admin.css'/>" rel="stylesheet" type="text/css" />

		<link href="<c:url value='/basic/css/demo.css'/>" rel="stylesheet" type="text/css" />

		<link href="<c:url value='/css/hmstyle.css'/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value='/AmazeUI-2.4.2/assets/js/jquery.min.js'/>"></script>
		<script src="<c:url value='/AmazeUI-2.4.2/assets/js/amazeui.min.js'/>"></script>

	</head>

	<body>
		<div class="hmtop">
			
			
			<!--顶部导航条 -->
			<div class="am-container header">
				<ul class="message-l">
					<div class="topMessage">
						<div class="menu-hd">
							<c:choose>
								<c:when test="${user==null}">
									<a href="<c:url value='/home/login.jsp'/>" target="_top" class="h">亲，请登录</a>
									<a href="<c:url value='/home/regist.jsp'/>" target="_top">免费注册</a>
								</c:when>
								<c:otherwise>
									${user.username}，欢迎回来
								</c:otherwise>
							</c:choose>

						</div>
					</div>
				</ul>
				<ul class="message-r">
					<div class="topMessage home">
						<div class="menu-hd"><a href="#" target="_top" class="h">商城首页</a></div>
					</div>
					<div class="topMessage my-shangcheng">
						<div class="menu-hd MyShangcheng"><a href="#" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
					</div>
					<div class="topMessage mini-cart">
						<div class="menu-hd"><a id="mc-menu-hd" href="#" target="_top"><i class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span><strong id="J_MiniCartNum" class="h">0</strong></a></div>
					</div>
					<div class="topMessage favorite">
						<div class="menu-hd">
							<c:if test="${user!=null}">
								<a href="<c:url value='/UserServlet?action=exit' />" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>退出登录</span></a>
							</c:if>
						</div>
					</div>
				</ul>
			</div>

				<!--悬浮搜索框-->

				<div class="nav white">
					<div class="logo"><img src="<c:url value='/images/logo.png'/>" /></div>
					<div class="logoBig">
						<li><img src="<c:url value='/images/logobig.png'/>" /></li>
					</div>

					<div class="search-bar pr">
						<a name="index_none_header_sysc" href="#"></a>
						<form>
							<input id="searchInput" name="index_none_header_sysc" type="text" placeholder="搜索" autocomplete="off">
							<input id="ai-topsearch" class="submit am-btn" value="搜索" index="1" type="submit">
						</form>
					</div>
				</div>
			<!--悬浮搜索框结束-->
				<div class="clear"></div>
			</div>
			<!--顶部导航条结束 -->
			
			
									
			
			<div class="listMain">"
				
			        
					  
								<div class="nav-table">
					   <div class="long-title"><span class="all-goods">全部分类</span></div>
					   <div class="nav-cont">
							<ul>
								<li class="index"><a href="#">首页</a></li>
                                <li class="qc"><a href="#">购物车</a></li>
                                <li class="qc"><a href="<c:url value='/OrderServlet?action=findAll' />">我的订单</a></li>
                                <li class="qc last"><a href="#">个人中心</a></li>
							</ul>
						    
						</div>
			</div>
				
		       					
					
				
			</div>
			
			
			<div class="shopMainbg">
			

					<!--甜点-->
					
					<div class="am-container ">
						<div class="shopTitle ">
							<h4>甜品</h4>
							<h3>每一道甜品都有一个故事</h3>
							
							<span class="more ">
                    <a class="more-link " href="<c:url value='/ProductServlet?action=findAll' />">更多商品</a>
                        </span>
						</div>
					</div>
					     
					
					<div class="am-g am-g-fixed flood method3 ">
						<ul class="am-thumbnails ">
							<c:forEach var="item" items="${list}">
								<li>
									<div class="list ">
											<a href="<c:url value='/ProductServlet?action=findById&id=${item.productid}' />">
												<img src="<c:url value='/${item.photo}' />" />
												<div class="pro-title ">${item.name}</div>
												<span class="e-price ">${item.price}</span>
											</a>
									</div>
								</li>
							</c:forEach>
						</ul>
						
					</div>
					
					<div class="footer ">
						
						<div class="footer-bd ">
							<p>
								<a href="# ">关于我们</a>
								<a href="# ">合作伙伴</a>
								<a href="# ">联系我们</a>
								<a href="# ">网站地图</a>
								<em>© 2015-2025  版权所有. </em>
							</p>
						</div>
					</div>
			
			</div>
		</div>
		
		<!--引导 -->

		
		

		
		<script>
			window.jQuery || document.write('<script src="basic/js/jquery.min.js "><\/script>');
		</script>
		<script type="text/javascript " src="<c:url value='/basic/js/quick_links.js'/> "></script>
	</body>

</html>