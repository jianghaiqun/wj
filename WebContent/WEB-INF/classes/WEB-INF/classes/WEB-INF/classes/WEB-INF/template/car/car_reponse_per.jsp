<%@ page language="java"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="com.sinosoft.framework.Config" %>
<html >
<head>
<title>油耗计算-开心保车险</title>
<meta http-equiv="Content-Type" content="text/html, charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />

<link rel="Stylesheet" href="<%=Config.getValue("StaticResourcePath")%>/style/weixin/car.css" />
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/mobile.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/carweixin_weixi.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		 $('#calPrem').click(function(){
			 
			 
				var t1 = jQuery("#kilometer").val();
				var t2 = jQuery("#liter").val();
				var t3 = jQuery("#literUSD").val();
				var re = /^[0-9]+.?[0-9]*$/;  
				if(!re.test(t1)) {
					showError("请输入有效的公里数");
					return;
				}
				if(!re.test(t2)){
					showError("请输入有效的公升数");
					return;
				}
				if(!re.test(t3)){
					showError("请输入有效的油价");
					return;
				}
				document.getElementById('caraction').submit();
		  });
	});


	
</script>

</head>
<body>
  <form id="caraction" action="./car_reponse!queryFule.action">
	<div class="wrap">
	
	   <div class="header">
	   		<img src="<%=Config.getValue("StaticResourcePath")%>/images/weixin/car/logo.png"  >
   	   </div>
		
       <div class="content">
       		<div class="car_index_1" > 
       			<img src="<%=Config.getValue("StaticResourcePath")%>/images/weixin/car/car_index_1.png"  >
       		</div>
       		
       		<div class="car_index_2" >
				<div  class="car_index_2_index div_radius"   >
					<div  class="car_index_2_1 "  >
					</div>
					<div class="car_index_2_2 div_radius"    >
						 上次加满油后行驶的公里数
						<div   class="car_index_2_2_1 div_radius">
							<input type="text" class="div_radius" id="kilometer" name="kilometer"/><label>公里</label>
						</div>
					</div>
					<div class="car_index_2_2 div_radius"    >
						 油箱加满几公升
						<div   class="car_index_2_2_1 div_radius">
							<input type="text" class="div_radius" id="liter" name="liter"/><label>公升</label>
						</div>
					</div>
					<div class="car_index_2_2 div_radius"    >
						 油价 / 公升
						<div   class="car_index_2_2_1 div_radius">
							<input type="text" class="div_radius" id="literUSD" name="literUSD"/><label>&nbsp;元&nbsp;&nbsp;</label>
						</div>
					</div>
				</div>  
				     	
				<div class="calprem"  >
				    <a class="base-button Orange" href="javascript:void(0)"  id="calPrem">计算</a>
				</div>		
       		</div>
       </div> 
       
       <div class="footer"   >
       	    <%@include file="footer.jsp" %>
   	   </div>
	</div>
	</form>
</body>
</html>

