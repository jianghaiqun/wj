<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.sinosoft.framework.Config" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html >
<head>
<title>实时路况-开心保车险</title>
<meta http-equiv="Content-Type" content="text/html, charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<link rel="Stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/weixin/car.css?" />
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/carweixin_weixi.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?type=quick&ak=XZ9Pe5FaoN6aLbCNddPQhpFh&v=1.0"></script>
<script type="text/javascript">
	$(document).ready(function(){
		 $('#search_city').click(function(){
				window.location.href = "./car_reponse!roadQueryInit.action?flag=initroad&openID="+$('#openID').val();
		  });

	});

</script>
</head>
<body>
	<div class="wrap">
	
	   <div class="header"  >
	   		<img src="<%=Config.getValue("StaticResourcePath")%>/images/weixin/car/logo.png?version=1"  > 
	   		<div class="current_pos" id="search_city">当前位置：<span  ><s:property value="checkCityName"/></span></div>
   	   </div>
		
       <div class="content allmap" id="allmap" >
       		  
       		 
       </div> 
       <input type="hidden" id="position_x" value="<s:property value="checkCityPosition_x"/>"/>
       <input type="hidden" id="position_y" value="<s:property value="checkCityPosition_y"/>"/>
       <input type="hidden" id="openID" value="<s:property value="openID"/>"/>
       <div class="footer"  style="padding-top: 2em;">
       	    <%@include file="footer.jsp" %>
   	   </div>
	</div>
	
<script type="text/javascript">
// 百度地图API功能
var map = new BMap.Map("allmap");            // 创建Map实例
var point = new BMap.Point($('#position_x').val(), $('#position_y').val());    // 创建点坐标
map.centerAndZoom(point,13);                     // 初始化地图,设置中心点坐标和地图级别。
var traffic = new BMap.TrafficLayer();        // 创建交通流量图层实例    
map.addTileLayer(traffic);                    // 将图层添加到地图上  
</script>
</body>
</html>

