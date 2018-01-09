<!DOCTYPE html> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>  
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-我的优惠券</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
<style>
/*update artDialog win*/
.aui_buttons button {display: block; margin:0 auto;}
.aui_buttons{background: none;  border-top:none;}
</style>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/artDialog.js"></script>
<script type="text/javascript">
 window.onload=function(){
	 jQuery("#member_tabarea0").load('<%=Config.getValue("ServerContext")%>/shop/coupon!notUse.action');
 }
 function jh(){
	 var couponsn=document.getElementById("couponsn").value;
	 var list = art.dialog.list;
		for (var i in list) {
		    list[i].close();
		};
	 jQuery.ajax( { 
			type : "post",
			url : sinosoft.base
					+ "/shop/coupon!couponJh.action?couponsn=" + couponsn,
			dataType : "json",
			async : false,
			success : function(data) {
				jQuery.each(data, function(index, object) {
					if ("success" == index) {
						//jQuery("#couponsn").html(object);
						art.dialog({
						    id:'yhj_log',
						    padding: '25px 50px 5px 50px',
						    title:'激活优惠券',
						    content: object,
						    button:[{name: '确认'}],
						    close:function (){
						    	window.location.reload(true);
						    }
						});
					}
					if ("false" == index) {
						//jQuery("#couponsn").append(object);
						art.dialog({
						    id:'yhj_log',
						    padding: '25px 50px 5px 50px',
						    title:'激活优惠券',
						    content: object,
						    button:[{name: '确认'}] 
						});
					}
				});
			}
		});
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
			<span class="daohang_home"></span>
			<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a><span class="separator">></span><a href='member_center!index.action' target='_self'>会员中心</a><span class="separator">></span><span class="separator1">优惠券</span>
		</div>
		<div class="member_con">
		    <jsp:include page="member_center_left.jsp"></jsp:include>
		    <div class="member_right bor_sild">
		 	    <div class="member_boxs">
		 		    <div class="member-htit"><span class="member-titsc">我的优惠券</span></div>
		 	  		<div class="yhj_active_yhj">
						<table>
							<tr>
								<td width="95px;"><b>优惠券激活码</b></td>
								<td width="240px;"><input type="text" class="member_yhq_num"
									value="请输入优惠券号码"
									onblur="if (this.value == '') {this.value = '请输入优惠券号码';}"
									onfocus="if (this.value == '请输入优惠券号码') {this.value = '';}" id="couponsn"></td>
								<td><input type="button" onclick="jh()" value="马上激活" class="member_yhq_jh_btn"></td>
							</tr>
						</table>
					</div>
		 	  	    <div class="member_orderlist" id="member_commantable">
						<div class="member_intertabtitle">
							<ul id="member_tabbuttens">
								<li><a href="javascript:void(0);"><span id="member_tabtitle0" class="member_tabtitleover0 clear_b_l" onclick="doChangeArea(0);">&nbsp;&nbsp;未使用&nbsp;&nbsp;</span></a></li>
								<li><a href="javascript:void(0);"><span id="member_tabtitle1" class="member_tabtitletitle0" onclick="doChangeArea(1);">&nbsp;&nbsp;已使用 &nbsp;&nbsp;</span></a></li>
								<li><a href="javascript:void(0);"><span id="member_tabtitle2" class="member_tabtitletitle0" onclick="doChangeArea(2);">&nbsp;&nbsp;已过期&nbsp;&nbsp;</span></a></li>
							</ul>			
						</div>
						<div id="member_tabarea0"  class="member_midonebottom">
						</div>
						<div id="member_tabarea1" class="member_midonebottomHidden">
						</div>
						<div id="member_tabarea2" class="member_midonebottomHidden">
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
								document.getElementById(selectID).className = selectPlus;
								if(intObj==0){
									document.getElementById(selectID).className = selectplusone;
									jQuery("#member_tabarea0").load("<%=Config.getValue("ServerContext")%>/shop/coupon!notUse.action");
								}
								if(intObj==1){
									jQuery("#member_tabarea1").load("<%=Config.getValue("ServerContext")%>/shop/coupon!used.action");
								}
								if(intObj==2){
									jQuery("#member_tabarea2").load("<%=Config.getValue("ServerContext")%>/shop/coupon!overTime.action");
								}
							}
						</script>
				    </div> 
				    <div class="clear h20"></div>          
		 		</div>
		 	</div>
		</div>
</div>
<!-- 底部开始 -->
<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<!-- 底部结束 -->
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
</body>
</html>