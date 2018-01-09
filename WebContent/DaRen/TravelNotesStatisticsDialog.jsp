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
<z:init method="com.wangjin.daren.TravelNotesStatistics.initDialog">
<form id="form2">
		<table width="600" height="200" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
	    			<fieldset>
						 <table>
						 	 <tr>
						          <td>统计时间：</td>
						          <td><input value="${statisticalTime}" type="text" id="statisticalTime" name="statisticalTime" style="width:100px" ztype="date" verify="时间|NotNull"></td>
						          <td>流量：</td>
						          <td><input value="${flow}" type="text" id="flow" name="flow" ztype="String"  class="inputText" style="width:100px" verify="流量|NotNull&&Int"></td>
						          <td>订单量：</td>
						          <td><input value="${orderNum}" type="text" id="orderNum" name="orderNum" ztype="String"  class="inputText" style="width:100px" verify="订单量|NotNull&&Int"></td>
						     </tr>
						     <tr>  
						          <td>总保费：</td>
						          <td><input value="${sumPrem}" type="text" id="sumPrem" name="sumPrem" ztype="String"  class="inputText" style="width:100px" verify="总保费|NotNull"></td>
						          <td>购买作者：</td>
						          <td ><input value="${buyAuthor}" type="text" id="buyAuthor" name="buyAuthor" ztype="String"  class="inputText" style="width:100px" ></td>
						          <td>作者订单量：</td>
						          <td ><input value="${authorOrderNum}" type="text" id="authorOrderNum" name="authorOrderNum" ztype="String"  class="inputText" style="width:100px" verify="作者订单量|Int"></td>
						     </tr>
						     <tr> 
						          <td>作者保费：</td>
						          <td ><input value="${authorSumPrem}" type="text" id="authorSumPrem" name="authorSumPrem" ztype="String"  class="inputText" style="width:100px"></td>
						          <td>辅助转化数：</td>
						          <td ><input value="${convertNum}" type="text" id="convertNum" name="convertNum" ztype="String"  class="inputText" style="width:100px" verify="辅助转化数|Int"></td>
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
