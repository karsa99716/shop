<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>注册</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />

<link rel="stylesheet"
	href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.min.css'/>" />
<link href="<c:url value='/css/dlstyle.css'/>" rel="stylesheet"
	type="text/css">
<script src="<c:url value='/AmazeUI-2.4.2/assets/js/jquery.min.js'/>"></script>
<script src="<c:url value='/AmazeUI-2.4.2/assets/js/amazeui.min.js'/>"></script>
<script>
	$(function (){
		$("#user").blur(function (){
			/*
			1.当用户焦点离开user的输入框
			2.取出username，提交给服务器检查
			3.占用，则显示占用，并把焦点重新定位到输入框中
			4.没有占用，则显示成功信息
			 */
			$.post(
			 	"${pageContext.request.contextPath}/UserServlet",
					{
						action:"validateUsername",
						username:$(this).val()
					},
					function (data){
			 		if (data=="true"){//可用
			 			$("#msg").html("用户名可用");
					}else {
						$("#msg").html("用户名已存在");
						$("#user").focus();
					}
					}
			)
		});
	});
</script>
</head>

<body>

	<div class="login-boxtitle">
		<a href="home/demo.html"><img alt=""
			src="<c:url value='/images/logobig.png'/>" />
		</a>
	</div>

	<div class="res-banner">
		<div class="res-main">
			<div class="login-banner-bg">
				<span></span><img src="<c:url value='/images/big.jpg'/> " />
			</div>
			<div class="login-box">

				<div class="am-tabs" id="doc-my-tabs">
					<ul class="am-tabs-nav am-nav am-nav-tabs am-nav-justify">
						<li class="am-active"><a href="">注册</a>
						</li>

					</ul>

					<div class="am-tabs-bd">
						<div class="am-tab-panel am-active">
							<form method="post" id="form1" action="<c:url value='/UserServlet' /> ">
								<input type="hidden" name="action" value="regist">
							
								<div class="user-name">
									<label for="user"><i class="am-icon-user"></i>
									</label> <input type="text" name="username" id="user" value="${username}" placeholder="用户名">
								</div>
								<div class="user-email">
									<label for="email"><i class="am-icon-envelope-o"></i>
									</label> <input type="email" name="email" id="email" value="${email}" placeholder="请输入邮箱账号">
								</div>
								<div class="user-pass">
									<label for="password"><i class="am-icon-lock"></i>
									</label> <input type="password" name="password" id="password"
										placeholder="设置密码">
								</div>
								<div class="user-pass">
									<label for="passwordRepeat"><i class="am-icon-lock"></i>
									</label> <input type="password" name="password2" id="passwordRepeat"
										placeholder="确认密码">
								</div>

							</form>

							<div class="login-links">
								<label for="reader-me"> <input id="reader-me"
									type="checkbox"> 点击表示您同意商城《服务协议》 </label>
							</div>
							<div class="am-cf">
								<input type="button" value="注册" onclick="javascript:form1.submit()"
									class="am-btn am-btn-primary am-btn-sm am-fl">
							</div>
							<div class="login-links">
								<label style="color: red" id="msg">${error}</label>
							</div>
							<div class="login-links">
								<label style="color:red"> </label>
							</div>

						</div>



					</div>
				</div>

			</div>
		</div>
		</div>

		<div class="footer ">

			<div class="footer-bd ">
				<p>
					<a href="# ">关于我们</a> <a href="# ">合作伙伴</a> <a href="# ">联系我们</a> <a
						href="# ">网站地图</a> <em>© 2015-2025 版权所有</em>
				</p>
			</div>
		</div>
</body>

</html>