<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="积分商城-虚拟产品兑换结果页" />
<meta name="description" content="积分商城-虚拟产品兑换结果页" />
<title>开心保_非车险_支付列表</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css"/>
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/shop/css/re_shops.css"/>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<script src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script> 
<script src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.poshytip.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/resource.kaixinbao.com//js/artDialog.js"></script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 公共头部 -->
	<div class="wrapper">
		<div class="clear h20"></div>
		<div class="w900 shop_border clear_nobor fchx-box">
			<div class="line_a shop_sptitle ">
				<div class="pay_box">
					<div class="ins-tit">查看订单</div>
					<div class="clear h10"></div>
					<table width="100%" border="1" class="jf_borders">
						<tr>
							<td width="17%" class="bor_left ins-tit-td">订单编号</td>
						    <td width="30%"><s:property value="#request.mpointxxchangeInfo.orderSn"/></td>
						    <td width="17%" class="bor_left ins-tit-td">订单状态</td>
						    <td width="30%" class="font-fsfco"><s:property value="#request.mpointxxchangeInfo.status"/></td>
						</tr>
						<tr>
							<td colspan="4" class="jf_td_sfsi">
							<s:if test="#request.mpointxxchangeInfo.status ==\"已发送\"">
								 您还有<span class="font-fsfco"><s:property value="#request.points"/></span>积分，
								 <s:if test="#request.exchangeFlag == \"Y\"">
		  							可以<a href="<%=Config.getFrontServerContextPath() %>/jifen/" target="_blank">继续进行兑换</a>
		  						</s:if>
								<s:else>
									<a href="order_query!queryOrder.action" target="_blank">评价订单</a>、<a href="member_recommend!query.action" target="_blank">推荐好友</a>还能获得积分哦！
								</s:else>
							</s:if>
							<s:else>
								提醒注意：兑换产品人数较多，有可能时间有延迟，我们会快马加鞭，请耐心等待！
							</s:else>
						</tr>
					</table>
					<div class="clear h20"></div>
					<table width="100%" border="1" class="jf_borders">
						<tr>
							<td class="bor_left ins-tit-td jf_td_textlf"><s:property value="#request.orderDesc"/></td>
						<tr>
							<td class="jf_td_sfsi">
							 <s:if test="#request.mpointxxchangeInfo.type == \"6\"">充值账号：</s:if>
							 <s:else>手机号码：</s:else>
							 <s:property value="#request.mpointxxchangeInfo.mobileNo"/></td>
						</tr>
					</table>
					<div class="clear h20"></div>
					<div class="ins-tit  un_bot_bor">订单信息</div>
					<table width="100%" border="1" class="jf_borders">
						<tr>
						    <td  width="54%" class="bor_left ins-tit-td">礼品名称</td>
						    <td width="23%" class="bor_left ins-tit-td">积分</td>
						    <td width="23%" class="bor_left ins-tit-td">数量</td>
						</tr>
     					<tr>
     						<td><span ><s:property value="#request.mgoodsstock.GoodsName"/></span></td>
     						<td><span ><s:property value="#request.mpointxxchangeInfo.points"/></span></td>
     						<td><span >1</span></td>
     					</tr>
					</table>
				</div>
				<div class = "clear"></div>
			</div>
			<div class = "clear"></div>
		</div>
		<div class="clear"></div>
	</div>
	<!-- 公共底部 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 公共底部 -->
	<!-- 公共js -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<!-- 公共js -->
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.soChange-min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/template/common/js/jquery.jqDnR.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/template/common/js/jquery.jqModal.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/artDialog.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/new_jifen.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/pointsinfo.js"></script>
</body>
<script type="text/javascript">

</script>
</html>
