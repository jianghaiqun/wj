<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.sinosoft.framework.Config" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html >
<head>
<title>城市列表-开心保车险</title>
<meta http-equiv="Content-Type" content="text/html, charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<link rel="Stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/weixin/car.css" />
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/carweixin_weixi.js"></script>
<script type="text/javascript">
	function search_map(citycode,cityname){
		//$('#checkedname').html(cityname);
		var openID = $('#openID').val();
		window.location.href = "./car_reponse!roadQuery.action?checkCityCode="+citycode+"&openID="+openID;
	}

</script>
</head>
<body>
	<div class="wrap">
	
	   <div class="header"  >
	   		    <img src="<%=Config.getValue("StaticResourcePath")%>/images/weixin/car/logo.png"  >
	   			<div class="current_pos" >
	   				当前位置：<span id="checkedname">请选择</span>
	   			</div>
   	   </div>
		
       <div  >
       		 <div class="city">
       		 	<div class="colunm_title" ><span >热门城市</span></div>
       		 	<div class="city_list" >
       		 		<ul  >
			       		  <s:iterator value="hotcitylist">
			       		 		 <li onclick="search_map('<s:property value="CityCode"/>','<s:property value="CityName"/>');"><span><s:property value="CityName"/></span></li>
					  	  </s:iterator>
       		 		</ul>
       		 	</div>
       		 </div>
       		 
       		  <div class="city">
       		 	<div class="colunm_title" ><span >其他城市</span></div>
       		 	<div class="city_list"  >
       		 		<ul  >
       		 			 <s:iterator value="othercitylist">
			       		 		 <li onclick="search_map('<s:property value="CityCode"/>','<s:property value="CityName"/>');"><span><s:property value="CityName"/></span></li>
					  	  </s:iterator>
       		 		</ul>
       		 	</div>
       		 </div>
       		 
       </div> 
       <input type="hidden" id="openID" value="<s:property value="openID"/>"/>
       <div class="footer"  style="padding-top: 5em;">
       	    <%@include file="footer.jsp" %>
   	   </div>
	</div>
</body>
</html>

