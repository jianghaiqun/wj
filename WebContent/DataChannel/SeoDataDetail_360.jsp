<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" 
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon005a13.gif" width="20" height="20" />&nbsp;数据明细</td>
			</tr>
		<tr>
		 <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <z:datagrid id="dg1" method="com.sinosoft.datachannel.SeoData.dg2DataBind"  size="15" >
               <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
               <tr ztype="head" class="dataTableHead" >
							<td width="5%" ztype="rowno">&nbsp;</td>
							
							<td width="15%"><b>关键词</b></td>
							<td width="8%" sortfield="ranking"><b>排名</b></td>
							<td width="8%" sortfield="searches"><b>收录量</b></td>
							<td width="8%" sortfield="kr"><b>KR</b></td>
							<td width="35%" sortfield="title"><b>标题</b></td>
							<td width="15%"><b>添加时间</b></td>
						</tr>
                 <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>${keyWord}</td>
							<td>${ranking}</td>
							<td>${searches}</td>
							<td >${kr}</td>
							<td>${title}</td>
							<td>${createDate}</td>
						</tr>
						
                  <tr ztype="pagebar">
					 <td height="25" colspan="8">${PageBar}</td>
				  </tr>
                </table>
              </z:datagrid></td>
              </tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
