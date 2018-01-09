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
<body class="dialogBody">
<z:init method="com.wangjin.underwriting.UnderwritingOfflineInfo.initDialogForBaiNian">
<form id="form1">
		<table width="100%"  align="center" cellpadding="2" cellspacing="0">
			<tr>
				<td align="right" height="30" width="50px"><strong>产品名称：</strong></td>
				<td colspan="9">${productName}</td>
			</tr>
			<tr>
				<td align="right" height="30" width="50px"><strong>客户资料：</strong></td>
			</tr>
			<tr>
				<td align="right" height="30" width="50px">姓名：</td>
				<td width="30px">${name}</td>
				<td align="right" height="30" width="40px">性别：</td>
				<td width="20px">${sex}</td>
				<td align="right" height="30" width="40px">年龄：</td>
				<td width="20px">${age}周岁</td>
				<td align="right" height="30" width="40px">身高：</td>
				<td width="20px">${height}cm</td>
				<td align="right" height="30" width="40px">体重：</td>
				<td width="20px">${weight}kg</td>
			</tr>
			<tr>
				<td align="right" height="30" width="50px">证件类型：</td>
				<td width="30px">${IdType}</td>
				<td align="right" height="30" width="50px">证件号：</td>
				<td colspan="3">${IdNo}</td>
				<td align="right" height="30" width="50px">出生日期：</td>
				<td colspan="3">${birthday}</td>
			</tr>
	    </table>
		<strong>健康询问具体内容：</strong>
		<table width="90%"  align="center" cellpadding="2" cellspacing="0" border="1">
			<tr>
				<td height="30" width="30%" align="left">
					1.首次发病日期？
				</td>
				<td height="30" width="70%">
					${firsOnsetTime}
				</td>
			</tr>
			<tr>
				<td height="30" width="30%" align="left">
					2.主要症状？
				</td>
				<td height="30" width="70%">
					${mainSymptoms}
				</td>
			</tr>
			<tr>
				<td height="30" width="30%" align="left">
					3.具体诊断？(医院诊断的疾病名称)
				</td>
				<td height="30" width="70%">
					${diseaseName}
				</td>
			</tr>
			<tr>
				<td height="30" width="30%" align="left">
					4.发作情况</br>
					(a)发作持续时间？</br>
					(b)发作次数或频率？(每天、每周)</br>
					(c)最近一次发作时间？</br>

				</td>
				<td height="30" width="70%">
						${attackDate}${attackFrequency}${attackLastDate}
				</td>
			</tr>
			<tr>
				<td height="30" width="30%" align="left">
					5.治疗情况</br>
					(a)目前是否仍接受治疗？</br>
					(b)治疗的方法？</br>
					(c)治疗效果？</br>

				</td>
				<td height="30" width="70%">
					${treat}${treatMathod}${treatEffect}
				</td>
			</tr>
			<tr>
				<td height="30" width="30%" align="left">
					6.其他补充说明：
				</td>
				<td height="30" width="70%">
					${otherSupplement}
				</td>
			</tr>
		</table>
		<input type="hidden" id="infoID" name="infoID" value="${infoID}" />
	</form>
</z:init>
</body>
</html>