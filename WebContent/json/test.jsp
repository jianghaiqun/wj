<%@ page language="java" pageEncoding="utf-8"%>
<html>
<head>
<title>jQuery&json</title>
 
<script type="text/javascript" src="./jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#ajax1").click(function(){
			var now=new Date().getTime();
			var data = {param1:"参数一", param2:"参数二", param3:"参数三", param4:"参数4" , operate:"test" ,method:"test1"};
			$.getJSON('http://localhost:8080/cms/shop.jsp?now='+now, data, function(json){//now表示每次点击都会发送新的请求
				 $('#div1').html(json.test);
				 for(var i=0; i<json.map.length; i++) {
					 alert(i+"--"+json.map[i].test);
				 }
			});                          
    	});
   });
   </script>
</head>

<body>
   <input type="button" id="ajax1" value="showme">
   <div id="div1"></div>
</body>
</html>