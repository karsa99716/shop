<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <title>购物车页面</title>
    <link href="<c:url value='/AmazeUI-2.4.2/assets/css/amazeui.css'/>"
          rel="stylesheet" type="text/css"/>
    d
    <link type="text/css" href="<c:url value='/css/optstyle.css'/>"
          rel="stylesheet"/>
    <link href="<c:url value='/basic/css/demo.css'/>" rel="stylesheet"
          type="text/css"/>
    <link type="text/css" href="<c:url value='/css/cartstyle.css'/>"
          rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value='/js/jquery.js' /> "></script>

    <script>
        $(function () {
            //在页面记载的时候绑定onclick事件
            $(".increment").click(function () {
                //获取数量文本框
                var qinput = $(this).siblings(":text");
                qinput.val(eval(qinput.val()) + 1);//数量+1
                //获取productid
                var id = qinput.attr("productid");
                $.post(
                    "${pageContext.request.contextPath}/ProductServlet",
                    {
                        "id": id,
                        "buycount": qinput.val(),
                        "action": "updateBuyCount"
                    },
                    function (data) {
                        //取得当前商品的总价的span
                        $("#total" + id).text(data.total);
                        $("#J_Total").text(data.totalprice);
                    },
                    "json"
                );
            });
            $(".minus").click(function () {
                //获取数量文本框
                var qinput = $(this).siblings(":text");
                if (eval(qinput.val()) !== 1) {
                    qinput.val(eval(qinput.val()) - 1);//数量+1
                }
                //获取productid
                var id = qinput.attr("productid");
                $.post(
                    "${pageContext.request.contextPath}/ProductServlet",
                    {
                        "id": id,
                        "buycount": qinput.val(),
                        "action": "updateBuyCount"
                    },
                    function (data) {
                        //取得当前商品的总价的span
                        $("#total" + id).text(data.total);
                        $("#J_Total").text(data.totalprice);
                    },
                    "json"
                );
            });

            $("#all").click(function () {
                if (this.checked)
                    $(":checkbox").attr("checked", true);
                else
                    $(":checkbox").attr("checked", false);
            });
        });
    </script>
</head>

<body>

<!--顶部导航条 -->
<div class="am-container header">
    <ul class="message-l">
        <div class="topMessage">
            <div class="menu-hd">
                <a href="#" target="_top" class="h">亲，请登录</a>
                <a href="#" target="_top">免费注册</a>
            </div>
        </div>
    </ul>
    <ul class="message-r">
        <div class="topMessage home">
            <div class="menu-hd"><a href="#" target="_top" class="h">商城首页</a></div>
        </div>
        <div class="topMessage my-shangcheng">
            <div class="menu-hd MyShangcheng"><a href="#" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a>
            </div>
        </div>
        <div class="topMessage mini-cart">
            <div class="menu-hd"><a id="mc-menu-hd" href="#" target="_top"><i
                    class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span><strong id="J_MiniCartNum"
                                                                                          class="h">0</strong></a></div>
        </div>
        <div class="topMessage favorite">
            <div class="menu-hd"><a href="#" target="_top"><i class="am-icon-heart am-icon-fw"></i><span>收藏夹</span></a>
            </div>
        </div>
    </ul>
</div>

<!--悬浮搜索框-->

<div class="nav white">
    <div class="logo"><img src="<c:url value='/images/logo.png'/>"/></div>
    <div class="logoBig">
        <li>
            <img src="<c:url value='/images/logobig.png'/>"></img>
        </li>
    </div>

    <div class="search-bar pr">
        <a name="index_none_header_sysc" href="#"></a>
        <form>
            <input id="searchInput" name="index_none_header_sysc" type="text" placeholder="搜索" autocomplete="off">
            <input id="ai-topsearch" class="submit am-btn" value="搜索" index="1" type="submit">
        </form>
    </div>
</div>

<div class="clear"></div>

<!--购物车 -->
<div class="concent">
    <div id="cartTable">
        <div class="cart-table-th">
            <div class="wp">
                <div class="th th-chk">
                    <div id="J_SelectAll1" class="select-all J_SelectAll">

                    </div>
                </div>
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
                <div class="th th-op">
                    <div class="td-inner">操作</div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
        <form action="<c:url value='/ProductServlet' /> " method="post" id="form1">
            <input type="hidden" name="action" value="deletecartMore"/>
            <c:forEach var="item" items="${cart}">
                <tr class="item-list">
                    <div class="bundle  bundle-last ">

                        <div class="bundle-main">
                            <ul class="item-content clearfix">
                                <li class="td td-chk">
                                    <div class="cart-checkbox ">
                                        <input class="check" id="J_CheckBox_170769542747" name="sel"
                                               value="${item.productid}" type="checkbox">
                                        <label for="J_CheckBox_170769542747"></label>
                                    </div>
                                </li>
                                <li class="td td-item">
                                    <div class="item-pic">
                                        <a href="#" target="_blank" data-title="" class="J_MakePoint"
                                           data-point="tbcart.8.12">
                                            <img style="width: 100%" src="<c:url value='${item.photo}' />"
                                                 class="itempic J_ItemImg"></a>
                                    </div>
                                    <div class="item-info">
                                        <div class="item-basic-info">
                                            <a href="#" target="_blank" title="" class="item-title J_MakePoint"
                                               data-point="tbcart.8.11">${item.name}</a>
                                        </div>
                                    </div>
                                </li>

                                <li class="td td-price">
                                    <div class="item-price price-promo-promo">
                                        <div class="price-content">
                                            <div class="price-line">
                                                <em class="price-original">${item.markprice}</em>
                                            </div>
                                            <div class="price-line">
                                                <em class="J_Price price-now" tabindex="0">${item.price}</em>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li class="td td-amount">
                                    <div class="amount-wrapper ">
                                        <div class="item-amount ">
                                            <div class="sl">
                                                <input class="minus" type="button" value="-"/>
                                                <input class="text_box" name="buycount" type="text"
                                                       productid="${item.productid}" value="${item.buycount}"
                                                       style="width:30px;"/>
                                                <input class="increment" type="button" value="+"/>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                                <li class="td td-sum">
                                    <div class="td-inner">
                                        <span tabindex="0" id="total${item.productid}"
                                              class="J_ItemSum number">${item.total}</span>
                                    </div>
                                </li>
                                <li class="td td-op">
                                    <div class="td-inner">

                                        <a href="javascript:if(confirm('确定要删除吗？'))window.location.href='<c:url value='/ProductServlet?action=deletecart&id=${item.productid}' />';"
                                           data-point-url="#" class="delete">
                                            删除</a>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </tr>
            </c:forEach>
        </form>
    </div>
    <div class="clear"></div>

    <div class="float-bar-wrapper">
        <div id="J_SelectAll2" class="select-all J_SelectAll">
            <div class="cart-checkbox">
                <input class="check-all check" id="all" name="select-all" type="checkbox">
                <label for="all"></label>
            </div>
            <span>全选</span>
        </div>
        <div class="operations">
            <a href="javascript:if (confirm('确认要删除吗？'))form1.submit()" hidefocus="true" class="deleteAll">删除</a>

        </div>
        <div class="float-bar-right">
            <div class="amount-sum">
                <span class="txt">已选商品</span>
                <em id="J_SelectedItemsCount">0</em><span class="txt">件</span>
                <div class="arrow-box">
                    <span class="selected-items-arrow"></span>
                    <span class="arrow"></span>
                </div>
            </div>
            <div class="price-sum">
                <span class="txt">合计:</span>
                <strong class="price">¥<span id="J_Total">${totalprice}</span></strong>
            </div>
            <div class="btn-area">
                <a href="<c:url value='/AddressServlet?action=paybefore' />" id="J_Go" class="submit-btn submit-btn-disabled" aria-label="请注意如果没有选择宝贝，将无法结算">
                    <span>结&nbsp;算</span></a>
            </div>
        </div>

    </div>

    <div class="footer">

        <div class="footer-bd">
            <p>
                <a href="#">关于恒望</a>
                <a href="#">合作伙伴</a>
                <a href="#">联系我们</a>
                <a href="#">网站地图</a>
                <em>© 2015-2025 Hengwang.com 版权所有. 更多模板 <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a>
                    - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></em>
            </p>
        </div>
    </div>

</div>

<!--操作页面-->

<div class="theme-popover-mask"></div>

<div class="theme-popover">
    <div class="theme-span"></div>
    <div class="theme-poptit h-title">
        <a href="javascript:;" title="关闭" class="close">×</a>
    </div>
    <div class="theme-popbod dform">
        <form class="theme-signin" name="loginform" action="" method="post">

            <div class="theme-signin-left">

                <li class="theme-options">
                    <div class="cart-title">颜色：</div>
                    <ul>
                        <li class="sku-line selected">12#川南玛瑙<i></i></li>
                        <li class="sku-line">10#蜜橘色+17#樱花粉<i></i></li>
                    </ul>
                </li>
                <li class="theme-options">
                    <div class="cart-title">包装：</div>
                    <ul>
                        <li class="sku-line selected">包装：裸装<i></i></li>
                        <li class="sku-line">两支手袋装（送彩带）<i></i></li>
                    </ul>
                </li>
                <div class="theme-options">
                    <div class="cart-title number">数量</div>
                    <dd>
                        <input class="min am-btn am-btn-default" name="" type="button" value="-"/>
                        <input class="text_box" name="" type="text" value="1" style="width:30px;"/>
                        <input class="add am-btn am-btn-default" name="" type="button" value="+"/>
                        <span class="tb-hidden">库存<span class="stock">1000</span>件</span>
                    </dd>

                </div>
                <div class="clear"></div>
                <div class="btn-op">
                    <div class="btn am-btn am-btn-warning">确认</div>
                    <div class="btn close am-btn am-btn-warning">取消</div>
                </div>

            </div>
            <div class="theme-signin-right">
                <div class="img-info">
                    <img src="../images/kouhong.jpg_80x80.jpg"/>
                </div>
                <div class="text-info">
                    <span class="J_Price price-now">¥39.00</span>
                    <span id="Stock" class="tb-hidden">库存<span class="stock">1000</span>件</span>
                </div>
            </div>

        </form>
    </div>
</div>
<!--引导 -->
<div class="navCir">
    <li><a href="home2.html"><i class="am-icon-home "></i>首页</a></li>
    <li><a href="sort.html"><i class="am-icon-list"></i>分类</a></li>
    <li class="active"><a href="shopcart.html"><i class="am-icon-shopping-basket"></i>购物车</a></li>
    <li><a href="../person/index.html"><i class="am-icon-user"></i>我的</a></li>
</div>
</body>

</html>