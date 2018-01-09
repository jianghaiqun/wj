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
<title>会员中心-理财险资金记录</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
	<!-- 顶部 开始 1-->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span> <a
				href='<%=Config.getFrontServerContextPath()%>' target='_self'><span
				class="orange">您现在的位置：首页</span></a> <span class="separator">></span> <a
				href='member_center!index.action' target='_self'>会员中心</a> <span
				class="separator">></span><span class="separator1">理财险资金记录</span>
		</div>
		<div class="member_con">
			<input type="hidden" id="fullDegree"
				value="<%=session.getAttribute("fullDegree")%>" /> <input
				type="hidden" id="orderFlag" name="orderFlag"
				value="<s:property value="orderFlag"/>" />
			<!-- 会员中心左侧菜单导航 -->
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right ">
				<div class="member_boxs">
					<!-- 未支付订单列表 start -->
					<div class="member-htit">
						<span class="member-titsc">理财险资金记录</span>
					</div>
					<div class="clear h20"></div>
						<p class="member_jl_p">
							理财险投保金额总计：<span><s:property value="recordMap.total" />元</span>
						</p>
						<p class="member_jl_p">
							本金总计：
							<s:property value="recordMap.principal" />
							元 <em>历史收益：<s:property value="recordMap.historyIncome" />元
							</em>
						</p>
						<p class="member_jl_p">
							预计收益：
							<s:property value="recordMap.income" />
							元 （收益金额因结算方式延迟1-3工作日）
						</p>
						<div class="member_lc_time">
							<input id='sdate' type="text" class="member_lc_inp"
								readonly="readonly" value="<s:property value="sdate" />"
								onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});"
								date-status="" min-date="2016-01-01" max-date="2056-12-31" />&nbsp;
							至&nbsp;
							<input id='edate' type="text" class="member_lc_inp"
								readonly="readonly" value="<s:property value="edate" />"
								onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});"
								max-date="<s:property value="recordMap.nowdate"/>"
								date-status="max" min-date="2016-01-01" /> 
								<input id="filterBtn" class="member_lc_sx" type="button" value="筛选" />


							<div class="divselect lc_action_sel ">
								<cite id="txt"><s:if test="record_status_str == '' || record_status_str == null">状态</s:if><s:else><s:property value="record_status_str" /></s:else> </cite>
								<ul style="display: none;">
									<li><a selectid="0" href="javascript:;">收入</a></li>
									<li><a selectid="1" href="javascript:;">支出</a></li>
								</ul>
								<input type="hidden" id="record_status" name="record_status" value="<s:property value="record_status" />"
									class="inputselect">
								<input type="hidden" id="record_status_str" name="record_status_str" value="<s:property value="record_status_str" />"
									class="inputselect">
							</div>
						</div>
						<div class="clear h20"></div>
	                 	<div id="member_ordertable" class="lc_min_height">
							<jsp:include page="order_query_newRecord.jsp"></jsp:include>							
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<script type="text/javascript"
		src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript"
		src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
	
	<script>
		// 初始化状态
		jQuery(document).ready(function() {
			jQuery('.divselect').divselect();
			
			jQuery(".divselect li a").click(function() {
			
				console.log(jQuery(this).text());
				jQuery("#record_status_str").val(jQuery(this).text());
				
			});
			
			jQuery("#filterBtn").click(function(){
				var sd = jQuery("#sdate").val();
				var ed = jQuery("#edate").val();
				var rs = jQuery("#record_status").val();
				var rs1 = jQuery("#record_status_str").val();

				if (sd > ed) {
					alert("开始日期不能大于结束日期！");
					return false;
				}else{
					window.location.href= sinosoft.base+'/shop/order_query!financingRecord.action?sdate='+sd+'&edate='+ed+'&record_status='+rs+'&record_status_str='+rs1;
				}
			});
		});
	
	</script>
</body>
</html>