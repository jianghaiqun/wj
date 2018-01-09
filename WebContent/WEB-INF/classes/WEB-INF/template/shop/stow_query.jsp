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
<title>会员中心-我的收藏</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
function buyNow1(linkurl,isPublish){
	if(isPublish=="N"){
		alert("此产品已下架");
		return false;
	}else{
		window.open(linkurl,"_blank");
	}
}
function xiajia(orderSn){
	//提示 产品已下架
	alert("此产品已下架");
	return false;
}

function jumpPage(pageindex){
	var sd=jQuery("#sd").val();
	var ed=jQuery("#ed").val();
	var pname=jQuery("#pname").val();
	var isamewgq=jQuery("#isamewgq").val();
	if(sd != "" && ed != ""){
		if (sd.indexOf("收藏时间") < 0) {
			if(sd > ed){
				alert("开始日期不能大于结束日期！");
				return false;
			}
		}
	}
	var param = ("?page="+pageindex+"&startDate="+encodeURIComponent(encodeURIComponent(sd))+"&endDate="+ed+"&productName="+encodeURIComponent(encodeURIComponent(pname))+"&insureName="+encodeURIComponent(encodeURIComponent(isamewgq)));
	jQuery("#member_commantable").load('<%=Config.getValue("ServerContext")%>/shop/stow!scan.action'+param);
}



window.onload=function(){
	 jQuery("#member_commantable").load('<%=Config.getValue("ServerContext")%>/shop/stow!scan.action', function(){
			if (jQuery(".member_nearorderTable tr").length < 2) {
				 jQuery(".no-shop").show();
				 jQuery(".memeber_ordercheck").hide();
				 jQuery(".member_orderlist").hide();
			 }
		});
}
function delStow(id, productId) {
	jQuery.ajax({
	    url:"<%=serverContext %>/shop/stow!del.action?id="+id,
	    type:"post",
		dataType:"json",
		success:function(data) {
			if (data.status == "Y") {
				jQuery('#operateCancel_'+productId).html("收藏");
				jQuery('#operateCancel_'+productId).parent().addClass("sc_collect")
				//解绑onclick实际,并重新绑定事件
				jQuery('#operateCancel_'+productId).unbind('click').removeAttr('onclick').click(function(){
					addStow(productId);
				});
			}
		}
	});
}

function addStow(productId) {
	jQuery.ajax({
		type : 'post',
		url : "<%=serverContext %>/shop/stow!add.action?productId="+productId,
		dataType : 'json',
		success : function(data) {
			if (data.status == "Y") {
				jQuery('#operateCancel_'+productId).html("取消收藏");
				jQuery('#operateCancel_'+productId).parent().removeClass("sc_collect")
				
				//解绑onclick实际,并重新绑定事件
				jQuery('#operateCancel_'+productId).unbind('click').removeAttr('onclick').click(function(){
					delStow(data.id,productId);
				});
			}
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
			<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a><span class="separator">></span><a href='member_center!index.action' target='_self'>会员中心</a><span class="separator">></span><span class="separator1">我的收藏</span>
		</div>
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
		 	<div class="member_right bor_sild">
		 		<div class="member_boxs">
		 			<div class="member-htit"><span class="member-titsc">我的收藏</span></div>
		 			<div class="no-shop" style="display:none;">
                       <div class="no-shop-tip">暂无收藏，去逛逛吧！~</div>
                  	</div> 
               		<div class="memeber_ordercheck">
               			<input type="text" id="pname"  name="productName"  class="member_orderinput"  value="&nbsp;&nbsp;产品名称" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;产品名称';}" onfocus="if (this.value == '&nbsp;&nbsp;产品名称') {this.value = '';}" onmousemove="this.className='member_orderinput_move'" onmouseout="this.className='member_orderinput'"  /> 
                     	<input type="text" id="isamewgq"  name="insureName" class="member_orderapplicant" value="&nbsp;&nbsp;保险公司" onblur="if (this.value == '') {this.value = '&nbsp;&nbsp;保险公司';}" onfocus="if (this.value == '&nbsp;&nbsp;保险公司') {this.value = '';}"  onmousemove="this.className='member_orderapplicant_move'" onmouseout="this.className='member_orderapplicant'"   /> 
                     	<input type="text" id="sd"  name="startDate"  class="member_ordertimeStart" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});"  value="&nbsp;&nbsp;收藏时间"   onmousemove="this.className='member_ordertimeStart_move'" onmouseout="this.className='member_ordertimeStart'" 
                   	 	onfocus="if (this.value == '&nbsp;&nbsp;收藏时间') {this.value = '';}"/>
                     	<span class="member_ordertimelink">至</span>
                     	<input type="text" id="ed"  name="endDate" value="" class="member_ordertimeOver" onclick="WdatePicker({skin:'whyGreen',maxDate:'2080-03-14'});"  onmousemove="this.className='member_ordertimeOver_move'" onmouseout="this.className='member_ordertimeOver'" /> 
                      	<span ><button class="member_ordersummit" type="button" onclick="jumpPage('1')">查询</button></span>
               		</div>
                	
                    <div class="member_orderlist" id="member_commantable">
                    </div>
                    <div class="clear h20"></div>
		 		</div>
		 	</div>
		</div>
	</div>
<!-- 底部开始 -->
<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<!-- 底部结束 -->
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>

<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
</body>
</html>