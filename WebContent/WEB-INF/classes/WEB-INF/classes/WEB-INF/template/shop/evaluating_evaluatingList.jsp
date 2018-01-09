<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-保险评测</title>
<!-- 会员中心公共CSS -->
<link rel="stylesheet" type="text/css" 	href="<%=Config.getValue("JsResourcePath") %>/style/skins/default.css" />
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
<script type="text/javascript">
	window.onload=function(){
		jQuery("#member_ask").load("<%=Config.getValue("ServerContext")%>/shop/question!ask.action");
	}; 
	function jumpPage(pageindex) {
		var sd = jQuery("#sd").val();
		var ed = jQuery("#ed").val();
		var kw = jQuery("#kw").val();
		var action = "answer!reply.action";

		if(sd.trim() != "" && ed.trim() != ""){
			if(sd > ed){
				alert("开始日期不能大于结束日期！");
				return false;
			}
		}
		jQuery("#member_answer").load("<%=Config.getValue("ServerContext")%>/shop/evaluating!evaluating.action?page="+pageindex+"&csdate="+encodeURIComponent(encodeURIComponent(sd))+"&cedate="+ed+"&keyword="+encodeURIComponent(encodeURIComponent(kw)));
	}

	function doChangeArea(intObj){
		var allSpanTitle = document.getElementById("member_tabbuttens").getElementsByTagName("li");
		for(var i=0;i<allSpanTitle.length;i++){
			var includePage = "member_tabarea"+i;
			document.getElementById(includePage).style.display="none";
			var plus = "member_tabtitle"+i;
			document.getElementById(plus).className=plus;
		}
		var selectdPage = "member_tabarea"+intObj;
		document.getElementById(selectdPage).style.display="block";
		var selectID = "member_tabtitle"+intObj;
		var selectPlus = "member_tabtitleover0";
		var selectplusone = "member_tabtitleover0 clear_b_l";
		document.getElementById(selectID).className=selectPlus;
		if(intObj==0){
			document.getElementById(selectID).className = selectplusone;
			jQuery("#member_ask").load("<%=Config.getValue("ServerContext")%>/shop/question!ask.action");
		}
		if(intObj==1){
			jQuery("#member_answer").load("<%=Config.getValue("ServerContext")%>/shop/answer!reply.action");
		}
		if(intObj==2){
			jQuery("#member_comment").load("<%=Config.getValue("ServerContext")%>/shop/coment!mycomment.action");
		}

	}
</script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>

<body class="up-bg-qh">
		<!-- 顶部 开始 1-->
		<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
		<!-- 顶部 结束 -->
	<div class="wrapper">
		<div class="daohang">
			<input type="hidden" id="out_member" value="member"/>
			<span class="daohang_home"></span>
			<a href='<%=Config.getFrontServerContextPath()%>' target='_self' ><span class="orange">您现在的位置：首页</span></a>
			<span class="separator">></span><a href='member_center!index.action' target='_self'>会员中心</a>><span class="separator"></span><span class="separator1">保险评测</span>
		</div>
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right bor_sild">
				<!-- 保险评测开始-->
				<div class="member_boxs">
					<div class="member-htit"><span class="member-titsc">保险评测</span></div>
					<div class="member_orderlist" id="member_commantable">
						<div class="memeber_qacheck">
							<input type="text" id="sd" name="csdate" class="member_ordertimeStart" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});" value="&nbsp;&nbsp;评论时间"
							onmousemove="this.className='member_ordertimeStart_move'" onmouseout="this.className='member_ordertimeStart'" onfocus="if (this.value == '&nbsp;&nbsp;评论时间') {this.value = '';}"/>
							<span class="member_ordertimelink">至</span>
							<input type="text" id="ed" name="cedate" value="" class="member_ordertimeOver" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});"
							onblur="this.onmouseout=function(){this.className='member_ordertimeOver'};" onmousemove="this.className='member_ordertimeOver_move'" onmouseout="this.className='member_ordertimeOver'"  />
							<input type="text" id="kw" name="keyword" class="member_orderapplicant" value="&nbsp;&nbsp;关键字" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;关键字';}"
							onfocus="if (this.value == '&nbsp;&nbsp;关键字') {this.value = '';}" onmousemove="this.className='member_orderapplicant_move'" onmouseout="this.className='member_orderapplicant'" />
							<span><button class="member_ordersummit" onclick="jumpPage(1)">查询</button></span>
						</div>
						<div id="member_answer">
						</div>
						<div class="clear h20"></div>
				 	</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
</body>
</html>