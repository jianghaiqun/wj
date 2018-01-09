<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.Config"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String StaticResourcePath = Config.getValue("StaticResourcePath"); 
%>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=9">
	<title>正在连接，请稍后…</title>
	<meta name="description" content="" />
	<link href="<%=StaticResourcePath %>/style/redesign/re_header.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=StaticResourcePath %>/style/car/information.css"/>
</head>

<body class="<s:property value="#request.type"/>">

	<!--header-->
	<div class="main_nav">
		<div class="nav_box clearfix nav_logs">
			<div class="new_logo"><a href="http://www.kaixinbao.com/"><img width="447" height="72" title="开心保保险网，一站式服务，省钱更安心" alt="开心保保险网，一站式服务，省钱更安心" src="<%=StaticResourcePath %>/images/logo_03.gif"></a></div>
			<div class="car_cha"><a href="http://www.kaixinbao.com/chexian/"><img width="148" height="22" src="<%=StaticResourcePath %>/images/new_car/car_channel.gif" alt="" ></a></div>
			<div class="nav_tel"><a href="http://www.kaixinbao.com/lpzs/index.shtml"><img width="187" height="38" src="<%=StaticResourcePath %>/images/new_car/logo_phone.gif" alt="" ></a></div>
		</div>
	</div>
	<!--header end-->
	
	<div class="area_bg01"></div>
	<div class="area_bg02"></div>
	<div class="area_bg03"></div>
	<div class="area_bg04"></div>
	<div class="area_bg05"></div>
	<div class="area_bg06"></div>
	<div class="area_bg07"></div>
	<div class="area_bg08"></div>
	<div class="area_cont">
		<a class="btn_top" href="http://www.kaixinbao.com/chexian/"></a>
		<p class="txt_login">正在为您连接<span><s:property value="#request.companyName"/></span>官网</p>
		<div class="loading"><p id="scroll"><span id="img_logo"></span></p></div>
		<input type="hidden" value ="<s:property value="#request.pageLink"/>" id="pageLink">
		<input type="hidden" value ="<s:property value="#request.name"/>" id="name">
		<input type="hidden" value ="<%=StaticResourcePath %>" id="path">
	</div>

<!--	维析针对不同险种车险的跳转统计的 	-->
<script type="text/javascript">
function isIllegal(temp) {
	var regex = "script|prompt|iframe|<|>|\"|\'|javascript";
	var r = temp.match(regex); 
	if(r==null||r==""){
		return false; 
	}else{
		return true;
	}
}
			 var str=window.location.search;   //location.search是从当前URL的?号开始的字符串
			 if(isIllegal(str)){
				 window.location.href= sinosoft.base+"/error.jsp?errormsg='carjs'";
				 }else{
					 var weixi_js="<script type=\"text/javascript\">VLTrace_custom_getparam='p="+document.getElementById("name").value+"';</sc" + "ript>";
					 document.write(weixi_js);
					 document.write("<div id=\"WeixiChexian\" style=\"display:none;\"><script type=\"text/javascript\" src=\""+document.getElementById("path").value+"/js/weixitrack.js\"></sc" + "ript></div>");
				 } 
</script>
<!--	维析针对车险整体的跳转统计-->
<script type="text/javascript">VLTrace_custom_getparam='p=chexianl';</script>
<script type="text/javascript" src="<%=StaticResourcePath %>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=StaticResourcePath %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=StaticResourcePath %>/js/template/common/js/base.js"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	var _elm = jQuery("#scroll");
	var _elo = jQuery("#img_logo");
	
	_elm.animate({width: "90%"}, "slow");
	_elo.animate({left: "90%"}, "slow");
	window.onload = function() {
		_elm.animate({width: "100%"}, "slow");
		_elo.animate({left: "100%"}, "slow");
	};
});
</script>
<script type="text/javascript">
var str = document.getElementById("pageLink").value;
setTimeout("openlink('"+str+"')",1500);
function openlink(pageLink){
	 window.location.href=pageLink;
}
</script>
<script type="text/javascript" src="<%=StaticResourcePath %>/js/car/car.js"></script>
<script type="text/javascript">
(function() {
	var ga = document.createElement("script");
	ga.type = "text/javascript";
	ga.async = true;
	ga.src = ("https:" == document.location.protocol ? "https://ssl"
			: "http://www")
			+ ".google-analytics.com/ga.js";
	var s = document.getElementsByTagName("script")[0];
	s.parentNode.insertBefore(ga, s);
	var _bdhmProtocol = "https:" == document.location.protocol ? " https://"
			: " http://";
	document
			.write(unescape("%3Cscript src='"
					+ _bdhmProtocol
					+ "hm.baidu.com/h.js%3F2d7e032a5f8d4e609feb9e22c0cb83f8' type='text/javascript'%3E%3C/script%3E"));
	document
			.write('<div id="Weixi" style="display:none;"><script type="text/javascript" src="http://www.kaixinbao.com/js/weixitrack.js"><\/script></div>')
})();
</script>

</body>
</html>