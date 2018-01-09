<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>受益人与被保人关系配置</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	</head>
	<body class="dialogBody">
	<z:init method="com.sinosoft.product.ProductInfo.initRelaition">
	<form id="form2">
	<table width="100%" height="50%" border="0">
		<tr>
			<td valign="middle">
			<table width="600" height="30" align="center" cellpadding="2"
				cellspacing="0" border="0">
				<tr>
					<td width="80" height="25"></td>
					<td></td>
				</tr>
				<tr>
					<td align="center">产品编码：</td>
					<td  >${Id}<input name="Id" type="hidden" id="Id" value="${Id}"/><input name="ComCode" type="hidden" id="ComCode" value="${ComCode}"/></td>
					
				</tr>
				<tr>
					<td align="center">产品名称：</td>
					<td  >${ProductName}</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	
			<z:datagrid id="module_dginput" method="com.sinosoft.product.ProductInfo.dg10DataBind">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td align="center" width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td style="display:none;"></td>
                  <td align="center" width="5%"><strong>关系编码</strong></td>
                  <td align="center" width="10%"><strong>关系名称</strong></td>
                </tr>
                <tr>
                  <td>&nbsp;</td>
                  <td align="center">&nbsp;</td>
                  <td align="center" style="display:none;"><input name="infoid" type="hidden" id="infoid" value="${ID}" size="32"></td>
                  <td align="center">${codeValue}</td>
                  <td align="center">${codeName}</td>
                </tr>
              </table>
            </z:datagrid>
    </form>
    </z:init>
	</body>
	</html>
