<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.sinosoft.framework.Config" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html >
<head>
<title>油耗计算结果-开心保车险</title>
<meta http-equiv="Content-Type" content="text/html, charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<link rel="Stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/weixin/car.css" />
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/carweixin_weixi.js"></script>
<script type="text/javascript">
	function rest_per(){
		window.location.href = "./car_reponse!initFule.action";
	}

</script>

</head>
<body>
	<div class="wrap">
	
	   <div class="header">
	   		<a href="#"><img src="<%=Config.getValue("StaticResourcePath")%>/images/weixin/car/logo.png"  > </a>
   	   </div>
		
       <div class="content">
       		<div class="car_index_1" > 
       			<img src="<%=Config.getValue("StaticResourcePath")%>/images/weixin/car/car_index_1.png"  >
       		</div>
       		
       		<div class="car_index_2" >
				<div  class="car_index_2_result div_radius"   >
					<div  class="car_index_2_1 "  >
					</div>
					<div class="car_index_2_2 div_radius"    >
						 每公升油可以行驶
						<div   class="car_index_2_2_1 div_radius">
							<input type="text" class="div_radius" id="pkilometer" name="pkilometer" value="<s:property value="pkilometer"/>"/><label>公里</label>
						</div>
					</div>
					<div class="car_index_2_2 div_radius"    >
						 每跑一公里需用
						<div   class="car_index_2_2_1 div_radius">
							<input type="text" class="div_radius" id="pliter" name="pliter" value="<s:property value="pliter"/>"/><label>公升</label>
						</div>
					</div>
					<div style="font-size: 0.8em;color: red;padding-left: 50%;">约等同（<s:property value="pliter_cc"/>）cc的油</div>
					<div class="car_index_2_2 div_radius"    >
						 每公里的油钱是
						<div   class="car_index_2_2_1 div_radius">
							<input type="text" class="div_radius" id="pliterUSD" name="pliterUSD" value="<s:property value="pliterUSD"/>"/><label>元&nbsp;&nbsp;</label>
						</div>
					</div>
				</div>  
				     	
				<div class="calprem"  >
				    <a class="base-button Orange" href="javascript:void(0)" onclick="rest_per();">重新计算</a>
				</div>		
       		</div>
       </div> 
       
       <div class="footer"  >
       	    <%@include file="footer.jsp" %>
   	   </div>
	</div>
</body>
</html>

