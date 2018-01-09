<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>小额验签管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript" src="../wwwroot/kxb/js/template/common/js/base.js"></script>
<script>
	function doSearch() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "accname", $V("accname"));
		DataGrid.setParam("dg1", "bankaccno", $V("bankaccno"));
		DataGrid.loadData("dg1");
	}

	function doclear(dom) {
		var $td = jQuery(dom).parent().parent("td");
		var $bankaccno = $td.siblings(".bankaccno");
		var bankaccno = $bankaccno.html();

		jQuery.ajax({
			type : 'post',
			url : sinosoft.base + "/shop/licai_baoxian!clearcardcheck.action?bankaccno="+bankaccno,
			dataType : "json",
			async : false,
			success : function(data) {
				alert("成功清零");
			}
		});
		DataGrid.loadData("dg1");

	}
</script>

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
							src="../Icons/icon018a6.gif" />小额验签</td>
					</tr>
					<tr>
						<table>
							<tr>
								用户名：
								<input name="accname" type="text" id="accname" value=""
									style="width: 90px"> 银行卡：
								<input name="bankaccno" type="text" id="bankaccno" value=""
									style="width: 90px"> &nbsp;
								<input type="button" name="Submit" value="查询"
									onClick="doSearch()">
							</tr>
						</table>
					</tr>
					<tr>
						<td colspan="2"
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">

							<z:datagrid id="dg1"
								method="com.sinosoft.message.CardCheck.dg1DataBind" size="18"
								lazy="true" page="false">
								<table width="100%" style="" cellpadding="2" cellspacing="0"
									class="dataTable">
									<tr ztype="head" class="dataTableHead">
										<td width="10%" ztype="RowNo"><strong>序号</strong></td>
										<td width="15%"><b>姓名</b></td>
										<td width="15%"><b>卡号</b></td>
										<td width="15%"><b>银行</b></td>
										<td width="15%"><b>功能</b></td>

									</tr>
									<tr style1="background-color:#FFFFFF"
										style2="background-color:#F9FBFC">
										<td width="10%">&nbsp;</td>
										<td width="15%" name="accname" title="${accname}">${accname}</td>
										<td width="15%" class='bankaccno' name="bankaccno"
											title="${bankaccno}">${bankaccno}</td>
										<td width="15%" name="CodeName" title="${CodeName}">${CodeName}</td>
										<td width="15%"><input type="button" name="clear"
											value="清零" onClick="doclear(this)"></td>
									</tr>
								</table>
							</z:datagrid>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>