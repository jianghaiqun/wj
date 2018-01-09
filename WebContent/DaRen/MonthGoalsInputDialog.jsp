<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">

</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.wangjin.daren.MonthGoalsInput.initDialog">
<form id="form2">
		<table width="700" height="100" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
	    			<fieldset>
	    				 <table>
						     <tr>
						          <td>年月：</td>
						          <td >
						          <z:select id="year" name="year" style="width:50px" value="${yearValue}">${year}</z:select>&nbsp;&nbsp;年<z:select id="month" name="month" style="width:50px" value="${monthValue}">${month}</z:select>&nbsp;&nbsp;月</td>
						          <td>计划完成保费：</td>
						          <td ><input value="${goalsPrem}" type="text" id="goalsPrem" name="goalsPrem" ztype="String" verify="计划完成保费|NotNull"></td>
						          
						     </tr>
					     </table>
					     <input value="${ID}" type="hidden" id="ID" name="ID" />
	               </fieldset>
			</td>
	    </tr>
	    </table>
	</form>
</z:init>
</body>
</html>
