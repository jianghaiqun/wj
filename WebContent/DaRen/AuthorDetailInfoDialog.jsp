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
<z:init method="com.wangjin.daren.AuthorDetailInfo.initDialog">
<form id="form2">
		<table width="750" height="200" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td valign="top">
						 <table>
						 	 <tr height="30px">
						          <td>目的地：</td>
						          <td ><input value="${destination}" type="text" id="destination" name="destination" ztype="String"  class="inputText" size="20" verify="目的地|NotNull"></td>
						          <td>作者名：</td>
						          <td ><input value="${authorName}" type="text" id="authorName" name="authorName" ztype="String"  class="inputText" size="20" verify="作者名|NotNull"></td>
						          <td>性别：</td>
						          <td ><z:select id="authorSex" name="authorSex" value="${authorSex}" verify="性别|NotNull">${sex}</z:select></td>
						     </tr>
						 	 <tr height="30px">
						          <td>所在城市：</td>
						          <td ><input value="${city}" type="text" id="city" name="city" ztype="String"  class="inputText" size="20" verify="所在城市|NotNull"></td>
						          <td>代码：</td>
						          <td ><input value="${code}" type="text" id="code" name="code" ztype="String"  class="inputText" size="20" verify="代码|NotNull"></td>
						          <td>推荐险种：</td>
						          <td ><z:select id="productName" name="productName" value="${productNameValue}" verify="推荐险种|NotNull">${productName}</z:select></td>
						     </tr>
						     <tr height="30px">
						          <td>来源：</td>
						          <td ><z:select id="source" name="source" value="${sourceValue}" verify="来源|NotNull">${source}</z:select></td>
						          <td>联系工具：</td>
						          <td ><z:select id="contactTool" name="contactTool" value="${contactToolValue}" verify="联系工具|NotNull">${contactTool}</z:select></td>
						          <td>联系方式：</td>
						          <td ><input value="${contactType}" type="text" id="contactType" name="contactType" ztype="String"  class="inputText" size="40" verify="联系方式|NotNull"></td>
						     </tr>
						     <tr height="30px">
						     	  <td>合作时间：</td>
						          <td ><input value="${cooperationTime}" type="text" id="cooperationTime" name="cooperationTime" style="width:100px" ztype="date" verify="合作时间|NotNull"></td>
						          <td>支付规则：</td>
						          <td ><z:select id="payRules" name="payRules" value="${payRulesValue}" verify="支付规则|NotNull">${payRules}</z:select></td>
						          <td>支付方式：</td>
						          <td ><input value="${payType}" type="text" id="payType" name="payType" ztype="String"  class="inputText" size="40" verify="支付方式|NotNull"></td>
						     </tr>
						     <tr>
						     	  <td>帖子链接：</td>
						          <td colspan="5"><input value="${articleLink}" type="text" id="articleLink" name="articleLink" ztype="String"  class="inputText" size="100" verify="帖子链接|NotNull"></td>
						     </tr>
						     <tr>
						     	  <td>备注：</td>
						          <td colspan="5"><input value="${remark1}" type="text" id="remark1" name="remark1" ztype="String"  class="inputText" size="100"></td>
						     </tr>
					     </table>
					     <input value="${ID}" type="hidden" id="ID" name="ID" />
	               
			</td>
	    </tr>
	    </table>
	</form>
</z:init>
</body>
</html>
