<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0 ,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <title>结算页面</title>

    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>" rel="stylesheet"
          type="text/css"/>

    <link href="<c:url value='/basic/css/demo.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/css/cartstyle.css'/>" rel="stylesheet" type="text/css"/>

    <link href="<c:url value='/css/jsstyle.css'/>" rel="stylesheet" type="text/css"/>

    <script type="text/javascript" src="<c:url value='/js/address.js'/>"></script>
    <script type="text/javascript">
        $(function () {
            $.get(
                "${pageContext.request.contextPath}/PCAServlet",
                {"action": "getprovinces"},
                function (data) {
                    //1.获取pro
                    //2.将省份绑定到pro对象上
                    $.each(data, function (index, content) {
                        //<option value="1000">北京市</option>
                        $("#pro").append("<option value=\"" + content.provinceid + "\">" + content.province + "</option>");
                    });
                },
                "json"
            );
            //当省份改变就根据省份编号获取市列表来绑定
            $("#pro").change(
                function () {
                    //调用ajax来获取市列表，并与市select绑定
                    $.post(
                        "${pageContext.request.contextPath}/PCAServlet",
                        {
                            "action": "getcities",
                            "provinceid": $(this).val()
                        },
                        function (data) {
                            //1.清空之前的列表
                            $("#ci").empty();
                            $("#ci").append("<option>--请选择--</option>");
                            //当省份改变，将select的text值绑定到隐藏域
                            var pro_text = $("#pro option:selected").text();
                            $("#province").val(pro_text);
                            //2.将省份绑定到pro对象上
                            $.each(data, function (index, content) {
                                //<option value="1000">北京市</option>
                                $("#ci").append("<option value=\"" + content.cityid + "\">" + content.city + "</option>");
                            });
                        },
                        "json"
                    );
                }
            );
            //当省份改变就根据省份编号获取市列表来绑定
            $("#ci").change(
                function () {
                    //调用ajax来获取市列表，并与市select绑定
                    $.post(
                        "${pageContext.request.contextPath}/PCAServlet",
                        {
                            "action": "getareas",
                            "cityid": $(this).val()
                        },
                        function (data) {
                            //1.清空之前的列表
                            $("#ar").empty();
                            $("#ar").append("<option>--请选择--</option>")
                            //当市改变，将select的text值绑定到隐藏域
                            var ci_text = $("#ci option:selected").text();
                            $("#city").val(ci_text);
                            //2.将省份绑定到pro对象上
                            $.each(data, function (index, content) {
                                //<option value="1000">北京市</option>
                                $("#ar").append("<option value=\"" + content.areaid + "\">" + content.area + "</option>");
                            });
                        },
                        "json"
                    );
                }
            );
            //区绑定选择改变
            $("#ar").change(
                function () {
                    //当区改变，将select的text值绑定到隐藏域
                    var ar_text = $("#ar option:selected").text();
                    $("#area").val(ar_text);
                });

            $("#sub_address").click(
                function () {
                    $("#form1").submit();
                });

            //选中显示编辑和删除
            $(".user-addresslist").click(function () {
                //$(".user-addresslist").find(".new-addr-btn").css("display","none");
                $(".user-addresslist").find(".new-addr-btn").css("display", "none");
                $(this).find(".new-addr-btn").css("display", "block");
                //给提交订单form中赋值
                var receiver = $(this).find(".buy-user").text();
                $(".buy-footer-address").find(".buy-user").html(receiver);

                var phone = $(this).find(".buy-phone").text();
                $(".buy-footer-address").find(".buy-phone").html(phone);

                var province = $(this).find(".province").text();
                $(".buy-footer-address").find(".province").html(province);

                var city = $(this).find(".city").text();
                $(".buy-footer-address").find(".city").html(city);

                var area = $(this).find(".area").text();
                $(".buy-footer-address").find(".area").html(area);

                var street = $(this).find(".street").text();
                $(".buy-footer-address").find(".street").html(street);
                //获取订单id
                var id = $(this).attr("addressid");
                $("#addressid").val(id);
            });
        });

        function ordersubmit(){
            //提交订单
            var id = $("#addressid").val();
            if (id){
                $("#form2").submit();
            }else {
                alert("请选择收货地址！");
            }
        }
    </script>
</head>

<body>

<!--顶部导航条 -->
<div class="am-container header">
    <ul class="message-l">
        <div class="topMessage">
            <div class="menu-hd">
                <a href="#" target="_top" class="h">亲，请登录</a> <a href="#"
                                                                 target="_top">免费注册</a>
            </div>
        </div>
    </ul>
    <ul class="message-r">
        <div class="topMessage home">
            <div class="menu-hd">
                <a href="#" target="_top" class="h">商城首页</a>
            </div>
        </div>
        <div class="topMessage my-shangcheng">
            <div class="menu-hd MyShangcheng">
                <a href="#" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a>
            </div>
        </div>
        <div class="topMessage mini-cart">
            <div class="menu-hd">
                <a id="mc-menu-hd" href="#" target="_top"><i
                        class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span><strong
                        id="J_MiniCartNum" class="h">0</strong>
                </a>
            </div>
        </div>
        <div class="topMessage favorite">
            <div class="menu-hd">
                <a href="#" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>收藏夹</span>
                </a>
            </div>
    </ul>
</div>

<!--悬浮搜索框-->

<div class="nav white">
    <div class="logo">
        <img src="<c:url value='/images/logo.png'/>"/>
    </div>
    <div class="logoBig">
        <li><img src="<c:url value='/images/logobig.png'/>"/>
        </li>
    </div>

    <div class="search-bar pr">
        <a name="index_none_header_sysc" href="#"></a>
        <form>
            <input id="searchInput" name="index_none_header_sysc" type="text"
                   placeholder="搜索" autocomplete="off"> <input
                id="ai-topsearch" class="submit am-btn" value="搜索" index="1"
                type="submit">
        </form>
    </div>
</div>

<div class="clear"></div>
<div class="concent">
    <!--地址 -->
    <div class="paycont">
        <div class="address">
            <h3>确认收货地址</h3>
            <div class="control">
                <div class="tc-btn createAddr theme-login am-btn am-btn-danger">使用新地址</div>
            </div>
            <div class="clear"></div>
            <ul>

                <div class="per-border"></div>
                <!-- 地址-->
                <c:forEach var="item" items="${addresslist}">
                    <li class="user-addresslist" addressid="${item.addressid}">
                        <div class="address-left">
                            <div class="user DefaultAddr">

								<span class="buy-address-detail"> <span class="buy-user">${item.receiver}
                                </span> <span class="buy-phone">${item.phone}</span> </span>
                            </div>
                            <div class="default-address DefaultAddr">
                                <span class="buy-line-title buy-line-title-type">收货地址：</span>
                                <span class="buy--address-detail">
                                <span class="province">${item.province}</span>
                                    <c:choose>
                                        <c:when test="${(item.city eq '市辖区') or (item.city eq '县')}">
                                            <span class="city"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="city">${item.city}</span>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${item.area eq '市辖区'}">
                                            <span class="dist"></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="dist">${item.area}</span>
                                        </c:otherwise>
                                    </c:choose>
                                <span class="street">${item.addressname}</span>
                            </div>
                            <ins class="deftip hidden">默认地址</ins>
                        </div>
                        <div class="address-right">
                            <span class="am-icon-angle-right am-icon-lg"></span>
                        </div>
                        <div class="clear"></div>

                        <div class="new-addr-btn" style="display: none">
                            <a
                                    hfref="#">编辑</a> <span class="new-addr-bar">|</span> <a
                                href="javascript:void(0);" onclick="delClick(this);">删除</a>
                        </div>
                    </li>
                </c:forEach>
                <!-- 地址end -->
            </ul>

            <div class="clear"></div>
        </div>


        <!--订单 -->
        <div class="concent">
            <div id="payTable">
                <h3>确认订单信息</h3>
                <div class="cart-table-th">
                    <div class="wp">

                        <div class="th th-item">
                            <div class="td-inner">商品信息</div>
                        </div>
                        <div class="th th-price">
                            <div class="td-inner">单价</div>
                        </div>
                        <div class="th th-amount">
                            <div class="td-inner">数量</div>
                        </div>
                        <div class="th th-sum">
                            <div class="td-inner">金额</div>
                        </div>
                        <div class="th th-oplist">
                            <div class="td-inner">配送方式</div>
                        </div>

                    </div>
                </div>
                <!-- 商品 -->
                <c:forEach var="item" items="${cart}">
                    <tr id="J_BundleList_s_1911116345_1" class="item-list">
                        <div id="J_Bundle_s_1911116345_1_0" class="bundle  bundle-last">
                            <div class="bundle-main">
                                <ul class="item-content clearfix">
                                    <div class="pay-phone">
                                        <li class="td td-item">
                                            <div class="item-pic">
                                                <a href="<c:url value='/ProductServlet?action=findById&id=${item.productid}' />"
                                                   class="J_MakePoint"> <img
                                                        src="<c:url value='/${item.photo}' />"
                                                        class="itempic J_ItemImg" style="width: 100%">
                                                </a>
                                            </div>
                                            <div class="item-info">
                                                <div class="item-basic-info" style="text-align: center">
                                                    <a href="<c:url value='/ProductServlet?action=findById&id=${item.productid}' />"
                                                       target="_blank"
                                                       class="item-title J_MakePoint" data-point="tbcart.8.11"
                                                       style="margin-left: -100px">${item.name}</a>
                                                </div>
                                            </div>
                                        </li>

                                        <li class="td td-price">
                                            <div class="item-price price-promo-promo">
                                                <div class="price-content">
                                                    <em class="J_Price price-now">${item.price}</em>
                                                </div>
                                            </div>
                                        </li>
                                    </div>


                                    <li class="td td-amount">
                                        <div class="amount-wrapper ">
                                            <div class="item-amount ">
                                                <span class="phone-title">购买数量</span>
                                                <div class="sl">
                                                        ${item.buycount}
                                                </div>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="td td-sum">
                                        <div class="td-inner">
                                            <em tabindex="0" class="J_ItemSum number">${item.total}</em>
                                        </div>
                                    </li>
                                    <li class="td td-oplist">
                                        <div class="td-inner">
                                            <span class="phone-title">配送方式</span>
                                            <div class="pay-logis">包邮</div>
                                        </div>
                                    </li>

                                </ul>
                                <div class="clear"></div>

                            </div>
                    </tr>
                </c:forEach>
                <!-- 商品end -->

                <div class="clear"></div>
            </div>
        </div>


        <div class="pay-total">

            <!--留言-->
            <div class="order-extra">
                <div class="order-user-info">
                    <form action="<c:url value='/OrderServlet' /> " method="post" id="form2">
                        <div id="holyshit257" class="memo">
                            <label>买家留言：</label> <input type="text" name="content"
                                                        title="选填,对本次交易的说明（建议填写已经和卖家达成一致的说明）"
                                                        placeholder="选填,建议填写和卖家达成一致的说明"
                                                        class="memo-input J_MakePoint c2c-text-default memo-close">
                            <div class="msg hidden J-msg">
                                <p class="error">最多输入500个字符</p>
                            </div>
                            <input type="hidden" name="action" value="add"/>
                            <input type="hidden" id="addressid" name="id"/>
                        </div>
                    </form>
                </div>

            </div>

            <div class="clear"></div>
        </div>
        <!--含运费小计 -->
        <div class="buy-point-discharge ">
            <p class="price g_price ">
                合计（含运费） <span>¥</span><em class="pay-sum">${totalprice}</em>
            </p>
        </div>

        <!--信息 -->
        <div class="order-go clearfix">
            <div class="pay-confirm clearfix">
                <div class="box">
                    <div tabindex="0" id="holyshit267" class="realPay">
                        <em class="t">实付款：</em> <span class="price g_price "> <span>¥</span>
								<em class="style-large-bold-red " id="J_ActualFee">${totalprice}</em> </span>
                    </div>

                    <div id="holyshit268" class="pay-address">

                        <p class="buy-footer-address">
                            <input type="hidden" name="address"/>
                            <span class="buy-line-title buy-line-title-type">寄送至：</span> <span
                                class="buy--address-detail"> <span class="province"></span>
									<span class="city"></span> <span class="area"></span> <span
                                    class="street"></span> </span> </span>
                        </p>
                        <p class="buy-footer-address">
                            <span class="buy-line-title">收货人：</span> <span
                                class="buy-address-detail"> <span class="buy-user">
								</span> <span class="buy-phone"></span> </span>
                        </p>
                    </div>
                </div>

                <div id="holyshit269" class="submitOrder">
                    <div class="go-btn-wrap">
                        <a id="J_Go" onclick="ordersubmit()" class="btn-go" tabindex="0"
                           title="点击此按钮，提交订单">提交订单</a>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>

    <div class="clear"></div>
</div>
</div>
<div class="footer">

    <div class="footer-bd">
        <p>
            <a href="#">关于我们</a> <a href="#">合作伙伴</a> <a href="#">联系我们</a> <a
                href="#">网站地图</a> <em>© 2015-2025 版权所有</em>
        </p>
    </div>
</div>
</div>
<!-- 增加地址 -->
<div class="theme-popover-mask"></div>

<div class="theme-popover">

    <!--标题 -->
    <div class="am-cf am-padding">
        <div class="am-fl am-cf">
            <strong class="am-text-danger am-text-lg">新增地址</strong> / <small>Add
            address</small>
        </div>
    </div>
    <hr/>

    <div class="am-u-md-12">
        <form class="am-form am-form-horizontal" action="<c:url value='/AddressServlet' />" method="post" id="form1">
            <input type="hidden" name="action" value="add"/>

            <div class="am-form-group">
                <label for="user-name" class="am-form-label">收货人</label>
                <div class="am-form-content">
                    <input type="text" id="user-name" name="receiver" placeholder="收货人">
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-phone" class="am-form-label">手机号码</label>
                <div class="am-form-content">
                    <input id="user-phone" name="phone" placeholder="手机号必填" type="text">
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-phone" class="am-form-label">邮编</label>
                <div class="am-form-content">
                    <input id="user-email" name="postcode" placeholder="邮编必填" type="email">
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-phone" class="am-form-label">所在地</label>
                <div class="am-form-content address">
                    <input type="hidden" name="province" id="province"/>
                    <input type="hidden" name="city" id="city"/>
                    <input type="hidden" name="area" id="area"/>
                    <select data-am-selected id="pro">
                        <option>--请选择--</option>
                    </select> <select data-am-selected id="ci">
                    <option>--请选择--</option>
                </select> <select data-am-selected id="ar">
                    <option>--请选择--</option>
                </select>
                </div>
            </div>

            <div class="am-form-group">
                <label for="user-intro" class="am-form-label">详细地址</label>
                <div class="am-form-content">
                    <textarea name="addressname" class="" rows="3" id="user-intro" placeholder="输入详细地址"></textarea>
                    <small>100字以内写出你的详细地址...</small>
                </div>
            </div>

            <div class="am-form-group theme-poptit">
                <div class="am-u-sm-9 am-u-sm-push-3">
                    <div class="am-btn am-btn-danger" id="sub_address">保存</div>
                    <div class="am-btn am-btn-danger close">取消</div>
                </div>
            </div>
        </form>
    </div>

</div>
<!-- 增加地址 end-->
<div class="clear"></div>
</body>

</html>