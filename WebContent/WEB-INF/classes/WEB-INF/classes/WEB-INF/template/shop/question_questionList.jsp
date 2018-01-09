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
<title>会员中心-保险问答</title>
<!-- 会员中心公共CSS -->
<link rel="stylesheet" type="text/css" 	href="<%=Config.getValue("JsResourcePath") %>/style/skins/default.css" />
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script>
	window.onload=function(){
		jQuery("#member_ask").load("<%=Config.getValue("ServerContext")%>/shop/question!ask.action");
	}; 
</script> 
<script type="text/javascript"> 
function jumpPage(flag, pageindex) {
	var sd;
	var ed;
	var kw;
	var action;
	var id;
	var param="";
	if (flag == 'ask') {
		sd = jQuery("#sd").val();
		ed = jQuery("#ed").val();
		kw = jQuery("#kw").val();
		action = "question!ask.action";
		id = "member_ask";
	} else if (flag == 'answer') {
		sd = jQuery("#sd1").val();
		ed = jQuery("#ed1").val();
		kw = jQuery("#kw1").val();
		action = "answer!reply.action";
		id = "member_answer";
	} else if (flag == 'comment') {
		sd = jQuery("#sd2").val();
		ed = jQuery("#ed2").val();
		kw = jQuery("#kw2").val();
		param=("&productname="+encodeURIComponent(encodeURIComponent(jQuery("#pname").val()))+"&suppliercorp="+encodeURIComponent(encodeURIComponent(jQuery("#sname").val())));
		action = "coment!mycomment.action";
		id = "member_comment";
	} else {
		return;
	}
	
	if(sd.trim() != "" && ed.trim() != ""){
		if(sd > ed){
			alert("开始日期不能大于结束日期！");
			return false;
		}
	}
	jQuery("#"+id).load("<%=Config.getValue("ServerContext")%>/shop/"+action+"?page="+pageindex+"&csdate="+encodeURIComponent(encodeURIComponent(sd))+"&cedate="+ed+"&keyword="+encodeURIComponent(encodeURIComponent(kw))+param);
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
			<span class="separator">></span><a href='member_center!index.action' target='_self'>会员中心</a>><span class="separator"></span><span class="separator1">保险问答</span>
		</div>
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right bor_sild">
				<!-- 保险问答开始-->
				<div class="member_boxs">
					<div class="member-htit"><span class="member-titsc">保险问答</span></div>
					<div class="member_orderlist" id="member_commantable">
						<div class="member_intertabtitle">
							<ul id="member_tabbuttens">
								<li><span id="member_tabtitle0"
									class="member_tabtitleover0 clear_b_l" onclick="doChangeArea(0);">&nbsp;&nbsp;我的提问&nbsp;&nbsp;</span></li>
								<li><span id="member_tabtitle1"
									class="member_tabtitletitle0" onclick="doChangeArea(1);">&nbsp;&nbsp;我的回答&nbsp;&nbsp;</span></li>
								<li><span id="member_tabtitle2"
									class="member_tabtitletitle0" onclick="doChangeArea(2);">&nbsp;&nbsp;我的评论&nbsp;&nbsp;</span></li>
							</ul>
						</div>
						<div id="member_tabarea0" class="member_midonebottom">
							<div class="memeber_qacheck">
								<input type="text" id="sd" name="csdate" class="member_ordertimeStart" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});" value="&nbsp;&nbsp;提问时间" 
								onmousemove="this.className='member_ordertimeStart_move'" onmouseout="this.className='member_ordertimeStart'" onfocus="if (this.value == '&nbsp;&nbsp;提问时间') {this.value = '';}"/> 
								<span class="member_ordertimelink">至</span>
								<input type="text" id="ed" name="cedate" class="member_ordertimeOver" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});" 
								onblur="this.onmouseout=function(){this.className='member_ordertimeOver'};" onmousemove="this.className='member_ordertimeOver_move'" onmouseout="this.className='member_ordertimeOver'" value=""/> 
								<input type="text" id="kw" name="keyword" class="member_orderapplicant" value="&nbsp;&nbsp;关键字" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;关键字';}" 
								onfocus="if (this.value == '&nbsp;&nbsp;关键字') {this.value = '';}" onmousemove="this.className='member_orderapplicant_move'" onmouseout="this.className='member_orderapplicant'" /> 
								<span><button type="button" class="member_ordersummit" onclick="jumpPage('ask','1')">查询</button></span>
							</div>
 							<div id="member_ask">
 							</div>
						</div>
						<div id="member_tabarea1" class="member_midonebottomHidden">
							<div class="memeber_qacheck">
								<input type="text" id="sd1" name="csdate" class="member_ordertimeStart" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});" value="&nbsp;&nbsp;回答时间" 
								onmousemove="this.className='member_ordertimeStart_move'" onmouseout="this.className='member_ordertimeStart'" onfocus="if (this.value == '&nbsp;&nbsp;回答时间') {this.value = '';}"/> 
								<span class="member_ordertimelink">至</span> 
								<input type="text" id="ed1" name="cedate" value="" class="member_ordertimeOver" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});" 
								onblur="this.onmouseout=function(){this.className='member_ordertimeOver'};" onmousemove="this.className='member_ordertimeOver_move'" onmouseout="this.className='member_ordertimeOver'"  /> 
								<input type="text" id="kw1" name="keyword" class="member_orderapplicant" value="&nbsp;&nbsp;关键字" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;关键字';}" 
								onfocus="if (this.value == '&nbsp;&nbsp;关键字') {this.value = '';}" onmousemove="this.className='member_orderapplicant_move'" onmouseout="this.className='member_orderapplicant'" /> 
								<span><button class="member_ordersummit" onclick="jumpPage('answer','1')">查询</button></span>
							</div>
							<div id="member_answer">
 							</div>
						</div>
						<div id="member_tabarea2" class="member_midonebottomHidden">
							<div class="memeber_qacheck">
								<input type="text" id="pname" name="productname" class="member_orderapplicant" value="&nbsp;&nbsp;产品名称" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;产品名称';}" 
								onfocus="if (this.value == '&nbsp;&nbsp;产品名称') {this.value = '';}" onmousemove="this.className='member_orderapplicant_move'" onmouseout="this.className='member_orderapplicant'" /> 
								<input type="text" id="sname" name="suppliercorp" class="member_orderapplicant" value="&nbsp;&nbsp;保险公司" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;保险公司';}" 
								onfocus="if (this.value == '&nbsp;&nbsp;保险公司') {this.value = '';}" onmousemove="this.className='member_orderapplicant_move'" onmouseout="this.className='member_orderapplicant'" /> 
								<input type="text" id="sd2" name="csdate" class="member_ordertimeStart" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});" value="&nbsp;&nbsp;评论时间" 
								onmousemove="this.className='member_ordertimeStart_move'" onmouseout="this.className='member_ordertimeStart'" onfocus="if (this.value == '&nbsp;&nbsp;评论时间') {this.value = '';}"/> 
								<span class="member_ordertimelink">至</span> 
								<input type="text" id="ed2" name="cedate" value="" class="member_ordertimeOver" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});" 
								onblur="this.onmouseout=function(){this.className='member_ordertimeOver'};" onmousemove="this.className='member_ordertimeOver_move'" onmouseout="this.className='member_ordertimeOver'" value=""/> 
								<input type="text" id="kw2" name="keyword" class="member_orderapplicant" value="&nbsp;&nbsp;关键字" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;关键字';}" 
								onfocus="if (this.value == '&nbsp;&nbsp;关键字') {this.value = '';}" onmousemove="this.className='member_orderapplicant_move'" onmouseout="this.className='member_orderapplicant'" /> 
								<span><button class="member_ordersummit" onclick="jumpPage('comment','1')">查询</button></span>
							</div>
							<div id="member_comment">
 							</div>
						</div>
						<div class="clear h20"></div>
						<script>
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
				 	</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
</body>
</html>