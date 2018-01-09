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


<input type="hidden" id="GateUrl" value="<s:property value="GateUrl"/>">
<input type="hidden" id="OrderId" value="<s:property value="OrdId"/>">
<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
</body>

<script type="text/javascript">

	var orderId = jQuery("#OrderId").val();
	
	var url = "pay!ajaxVerify.action";
	
	jQuery.post(url,{"OrdId":orderId},callBackFunction,"json");

function callBackFunction(data){
	if(data.status == "true"){
		var url = jQuery('#GateUrl').val();
		location.href = url;
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