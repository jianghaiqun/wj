<%@ page language="java" pageEncoding="utf-8"%>

<html>
<head>
<title>jQuery&json</title>
<style type="text/css">
.css_a { 
        background:#ecf6fc;  /*这行将给所有的tr加上背景色*/ 
}
.css_b { 
        background:#bcd4ec;  /*这行将给所有的tr加上背景色*/ 
}
.css_c {
		background:#0066FF;
}
</style>
<script type="text/javascript" src="./jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$("#ajax1").click(function(){
			var now=new Date().getTime();
			var data = {param1:"参数一", param2:"参数二", param3:"参数三", param4:"参数4"};
			$.getJSON('http://localhost:8080/cms/jobject?now='+now, data, function(json){//now表示每次点击都会发送新的请求
				//json = eval("(" + json + ")");  eval? 
				alert(json.param1);
				var str="";
				str = "<table id = 'table1'><tr id ='tr'><td>param1</td><td>param2</td><td>param3</td><td>param4</td>";
				
				//jsonObject
				str +="<tr class = 'tr1' ><td>"+json.param1+"</td>";     
				str +="<td>"+json.param2+"</td>";
				str +="<td>"+json.param3+"</td>";
				str +="<td>"+json.param4+"</td></tr>";
				
				//jsonArray
				/*	array4
				var len = json.length;
				for(var i=0; i<len; i++) {
					str +="<tr class = 'tr1' ><td>"+json[i].col+"</td>";     
					str +="<td>"+json[i].row+"</td>"
					str +="<td>"+json[i].value+"</td>"
					str +="<td>"+json[i]+"</td></tr>"
				}
				*/
				str +="</table>";            
	        
				$('#div1').html(str);
	
				$('.tr1').each(function(i){
					if (i%2==0) { 
						$(this).addClass("css_a");
					}      
				});                            
	        
				$('.tr1').each(function(i){ 
					if (i%2==1) {    
						$(this).addClass("css_b");   
					}        
				}); 
	           
				$('#table1 .tr1').hover(function() {     
					$(this).addClass('css_c'); 
				}, function() { 
					$(this).removeClass('css_c'); 
				}
				);         
			});                          
    	});
   });
   </script>
</head>

<body>
   <div id="div1"></div>
   <input type="button" id="ajax1" value="showme">
</body>
</html>