<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sinosoft.framework.Config"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付</title>
<link href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" rel="stylesheet" type="text/css" />
<link href="<%=Config.getValue("StaticResourcePath")%>/style/pay_skip.css" rel="stylesheet" type="text/css" />
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body>
<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>

<div class="wrapper">
  <div class="guodu_box">
       <div class="guodu_logo"></div>
    <p class="meihua_p">正在调取支付页面，请稍后...</p><br>
   	 
    <img id="wait" src="<%=Config.getValue("StaticResourcePath") %>/images/loading2.gif" width="70" height="70" /><br>
		 <span id="errorMessage" style="font-size:18px;color:red;"></span><br><br>
		如长时间未响应,请点击 <a href="###" onclick="reload()" style="color: #FF6D00;">刷新</a><br>
    </div>
</div>

<div style="display: none;">
<input type="hidden" id="OrderId" value="<s:property value="OrdId"/>">
<form method="post" id="ybzfForm" action="<%=request.getAttribute("GateUrl")%>">
<table>
	<tr>
		<td><input type="text" name="p0_Cmd"
			value="<%=request.getAttribute("p0_Cmd")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p1_MerId"
			value="<%=request.getAttribute("p1_MerId")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p2_Order"
			value="<%=request.getAttribute("p2_Order")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p3_Amt"
			value="<%=request.getAttribute("p3_Amt")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p4_Cur"
			value="<%=request.getAttribute("p4_Cur")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p5_Pid"
			value="<%=request.getAttribute("p5_Pid")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p6_Pcat"
			value="<%=request.getAttribute("p6_Pcat")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p7_Pdesc"
			value="<%=request.getAttribute("p7_Pdesc")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p8_Url"
			value="<%=request.getAttribute("p8_Url")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="p9_SAF"
			value="<%=request.getAttribute("p9_SAF")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="pa_MP"
			value="<%=request.getAttribute("pa_MP")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="pd_FrpId"
			value="<%=request.getAttribute("pd_FrpId")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="pr_NeedResponse"
			value="<%=request.getAttribute("pr_NeedResponse")%>"></td>
	</tr>
	<tr>
		<td><input type="text" name="hmac"
			value="<%=request.getAttribute("hmac")%>"></td>
	</tr>
</table>
</form>
</div>

<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
</body>

<script type="text/javascript">

var orderId = jQuery("#OrderId").val();

var url = "pay!ajaxVerify.action";

jQuery.post(url,{"OrdId":orderId},callBackFunction,"json");

function callBackFunction(data){
	if(data.status == "true"){
		jQuery("#ybzfForm").submit();
	}else{
		jQuery('#wait').attr('style', 'display:none');
		jQuery("#errorMessage").html(data.error);
	}
}

function reload(){
	location.reload();
}
</script>
</html>