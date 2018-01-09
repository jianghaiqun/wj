<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>淘宝会员复购详情列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
					<td>
						复购详情如下:
					</td>
					<td></td>
				</tr>
			  <tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.platform.TaobaoMemberRepurchaseDetail.dg1DataBind" size="15" scroll="true" lazy="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
                                 <td width="80" style="text-align:center;"><b>产品id</b></td>
                                 <td width="80" style="text-align:center;"><b>产品名称</b></td>
                                 <td width="80" style="text-align:center;"><b>保费</b></td>
                                 <td width="80" style="text-align:center;"><b>下单日期</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="15">&nbsp;<input name="id" type="hidden" id="id" value="${id}" /></td>
								 <td style="text-align:left;" title="${productId}">${productId}</td>
								 <td style="text-align:left;" title="${productName}">${productName}</td>
								 <td style="text-align:left;" title="${prem}">${prem}</td>
								 <td style="text-align:left;" title="${orderDate}">${orderDate}</td>
							</tr>
							<tr ztype="pagebar">
								<td height="25" colspan="11">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid></td>
					</tr>
				  </table>
				</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>