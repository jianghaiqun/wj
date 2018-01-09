<!DOCTYPE HTML >
<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test pay interface</title>
<script type="text/javascript">
	function doTest(){
		document.forms[0].submit();
	}
</script>
</head>
<body>
	   <button onclick="doTest();"> 测试支付  </button>
	   <table>
	   		<tr>
	   			<td>支付流水号</td>
	   			<td><%=request.getAttribute("paySn") %></td>
	   		</tr>
	   		<tr>
	   			<td>支付金额</td>
	   			<td><%=request.getAttribute("total_fee") %></td>
	   		</tr>
	   		<tr>
	   			<td>测试支付地址</td>
	   			<td><%=request.getAttribute("BgRetUrl") %></td>
	   		</tr>
	   </table>
	   
<form  method="post" action="<%=request.getAttribute("BgRetUrl") %>" >
	  <input type="hidden" name="paySn" id="paySn"  value="<%=request.getAttribute("paySn") %>"> 
	  <input type="hidden" name=OrdAmt id="OrdAmt"  value="<%=request.getAttribute("total_fee") %>"> 
	  <input type="hidden" name=Btype id="Btype"  value="1"> 
	  
</form>
</body>
</html>