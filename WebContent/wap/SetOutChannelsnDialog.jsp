<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.wap.SetOutChannelsn.init">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title></title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript">
		function chclick(){
			var id = document.getElementsByName('ch');
		       var value = new Array();
		       for(var i = 0; i < id.length; i++){
		         if(id[i].checked)
		         value.push(id[i].value);
		        }
		      document.getElementById('chs').value = value;
		}
		
	</script>
	</head>
	<body class="dialogBody">
	<form id="form2">
	<table width="100%" height="100%" border="0">
		<tr>
			<td valign="middle">
			<table width="400" height="197" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td>${te}</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<input type="hidden" id="productId"  name ="productId"  value = "${productId}"/>
	<input type="hidden" id="chs"  name ="chs"  value = "${chs}"/>
	</form>
	</body>
	</html>
</z:init>
