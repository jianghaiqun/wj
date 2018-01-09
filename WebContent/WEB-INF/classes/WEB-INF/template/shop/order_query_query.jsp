<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%> 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>会员中心-所有订单</title>
	<!-- 会员中心公共CSS --> 
	<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
	<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
	<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
	<!-- 顶部 开始 1-->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span>
			<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a>
			<span class="separator">></span>
			<a href='member_center!index.action' target='_self'>会员中心</a>
			<span class="separator">></span><span class="separator1">所有订单</span>
		</div>
		<div class="member_con">
			<input type="hidden" id="fullDegree" value="<%=session.getAttribute("fullDegree") %>" />
			<input type="hidden" id ="orderFlag" name="orderFlag" value="<s:property value="orderFlag"/>" />
			<!-- 会员中心左侧菜单导航 -->
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right ">
				<div class="member_boxs">
					<div class="member-htit">
						<span class="member-titsc">所有订单</span>
					</div>
					<form  id="myOrder">
						<div class="memeber_ordercheck">
								<input type="text" id="ordSn"  name="orderSn" class="member_orderinput"  value="&nbsp;&nbsp;订单编号" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;订单编号';}" onfocus="if (this.value == '&nbsp;&nbsp;订单编号') {this.value = '';}" onmousemove="this.className='member_orderinput_move'" onmouseout="this.className='member_orderinput'"  /> 
				                <input type="text" id="apt"  name="applicant" class="member_orderapplicant" value="&nbsp;&nbsp;投保人" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;投保人';}" onfocus="if (this.value == '&nbsp;&nbsp;投保人') {this.value = '';}"  onmousemove="this.className='member_orderapplicant_move'" onmouseout="this.className='member_orderapplicant'"   /> 
				                <select class="member_orderstates" id="orderStatus" name="orderStatus">
									<option value="99">---请选择---</option>
									<option value="3">已取消</option>
									<option value="4" >暂存</option>
									<option value="5" >待支付</option>
									<option value="6" >处理中</option>
									<option value="7" >已支付</option>
									<option value="8" >自动取消</option>
									<option value="9" >已撤销</option>
									<option value="10" >有撤单</option>
								</select>
				                <input type="text" id="sd"  name="ldate"  class="member_ordertimeStart" 
				                onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});"  value="&nbsp;&nbsp;支付时间"   
				                onmousemove="this.className='member_ordertimeStart_move'" onmouseout="this.className='member_ordertimeStart'" />
				                <span class="member_ordertimelink">至</span>
				                <input type="text" id="ed"  name="hdate" value="" class="member_ordertimeOver" 
				                onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});"  
				                onmousemove="this.className='member_ordertimeOver_move'" onmouseout="this.className='member_ordertimeOver'" /> 
				                <span ><button class="member_ordersummit" type="button" onclick="queryOrder()">查询</button></span>
						</div>
					</form>
					<div id="member_ordertable">
					<jsp:include page="order_query_newlist.jsp"></jsp:include>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
	 	<script type="text/javascript">
	 	if(jQuery("#orderFlag").val()!=null){
			var orderstatus = jQuery("#orderFlag").val();
			jQuery("#orderStatus").val(orderstatus);
		}
		function queryOrder() {
			var sd = jQuery("#sd").val();
			var ed = jQuery("#ed").val();
			if (sd == "" || ed == "") {
				gotoPage('order_query!queryOrderlist.action', '1','','member_ordertable');
			} else {

				if (sd > ed) {
					alert("开始日期不能大于结束日期！");
					return false;
				} else {
					gotoPage('order_query!queryOrderlist.action', '1','','member_ordertable');
				}
			}
		}
	</script>
	</body>
</html>