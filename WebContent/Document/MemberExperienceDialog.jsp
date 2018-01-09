<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<script>

</script>
<body class="dialogBody">
<z:init method="com.sinosoft.message.MemberExperience.initDialog">
<form id="form2">
	<table width="500" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td width="114" height="10"></td>
			<td width="376"><input id="ID" name="ID" type="hidden" value="${ID}"/></td>
		</tr>
		<tr>
			<td align="right">会员名：</td>
			<td height="30">${username}</td>
		</tr>
		
		<tr>
			<td align="right">经验：</td>
			<td height="30"><input id="Experience" name="Experience" type="text"
				value="${Experience}" class="input1" verify="经验|NotNull" /></td>
		</tr>
		<tr>
			<td align="right">经验来源：</td>
			<td><z:select style="width:150px" >
				<select name="Source" id="Source" ">                 
                  <option value="0">登录</option>
                  <option value="1">回复</option>
                  <option value="2">发布内容</option>
                  <option value="3">评论</option>
                  <option value="4">调查问卷</option>
                  <option value="5">订阅邮件</option>
                  <option value="6">注册</option>
                  <option value="7">完善资料</option>
                  <option value="8">关注微博</option>
                </select>
				</z:select>
		</tr>
		<tr>
			<td align="right">经验收入/支出：</td>
			<td><z:select style="width:120px;" >
				<select name="Manner" id="Manner" ">                 
                  <option value="0">收入 </option>
                  <option value="1">支出 </option>
                </select>
				</z:select>
		</tr>
		
		
	</table>
	</form>
</z:init>
</body>
</html>