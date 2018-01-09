<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script type="text/javascript" src="../../template/common/js/jquery.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
/**
 * 查询
 */
function search() {
	DataGrid.setParam("dg3", Constant.PageIndex, 0);
	DataGrid.setParam("dg3", "email", $V("email"));
	DataGrid.setParam("dg3", "mobileNO", $V("mobileNO"));
	DataGrid.loadData("dg3");
}
//展示浮动框
function showMessage(t){
	var innertext=t.innerHTML;
	if(innertext.indexOf("&nbsp;")!=-1){
		innertext=innertext.replace(new RegExp("&nbsp;", 'g'),"");
	}
	if(innertext==""||innertext==null){
		t.title="";
	}else{
		t.title=t.innerHTML;
	}
}
</script>
</head>
<body class="dialogBody">
		<input type="hidden"  id="ID" name="ID" value="${ID}">
		<table width="100%" border='0' cellpadding='10' cellspacing='0'>
			<tr>
				<td width="50%" valign="top">
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" id="ConfigTable">
						<tr>
							<td valign="top">
								<fieldset>
									<legend>
										<label>商品库存管理</label>
									</legend>
								</fieldset>
								<table>
									<tr>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">邮箱：</td>
										<td><input value="" type="text" id="email" name="email" ztype="String" class="inputText" size="20"></td>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
										<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">电话：</td>
										<td><input value="" type="text" id="mobileNO" name="mobileNO" ztype="String" class="inputText" size="20"></td>
										<td><input value="查 询" type="button" onclick="search()"></td>
									</tr>
								</table> 
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td style="padding: 0px 5px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
									<tr>
										<td
											style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
											<z:datagrid id="dg3" method="com.wangjin.pointsMall.GiftManage.dg1DataBindRecords1" size="10" autoFill="true" scroll="true" lazy="false" multiSelect="false">
												<table width="930" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="afterRowDragEnd" style="table-layout: fixed" fixedHeight="120px">
													<tr ztype="head" class="dataTableHead">
														<td width="30px;" height="30" ztype="RowNo" drag="true"><img src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
														<td width="20px;" height="30" ztype="Selector" field="id">&nbsp;</td>
														<td width="260px;"><strong>产品名称</strong></td>
														<td width="80px;"><strong>积分兑换值</strong></td>
														<td width="55px;"><strong>状态</strong></td>
														<td width="200px;"><strong>兑换会员邮箱</strong></td>
														<td width="200px;"><strong>兑换会员电话</strong></td>
														<td width="150px;"><strong>创建人</strong></td>
														<td width="150px;"><strong>创建时间</strong></td>
														<td width="150px;"><strong>修改人</strong></td>
														<td width="150px;"><strong>兑换时间</strong></td>
													</tr>
													<tr onDblClick="editStock(${id});" style="background-color: #F9FBFC">
														<td onmouseover="">&nbsp;</td>
														<td>&nbsp;</td>
														<td onmouseover="showMessage(this)">${GoodsName}</td>
														<td onmouseover="showMessage(this)">${PayPoints}</td>
														<td onmouseover="showMessage(this)">${status}</td>
														<td onmouseover="showMessage(this)">${email}</td>
														<td onmouseover="showMessage(this)">${mobileNO}</td>
														<td onmouseover="showMessage(this)">${CreateUser}</td>
														<td onmouseover="showMessage(this)">${CreateDate}</td>
														<td onmouseover="showMessage(this)">${ModifyUser}</td>
														<td onmouseover="showMessage(this)">${CreateDate}</td>
													</tr>
													<tr ztype="pagebar">
														<td colspan="9" align="left">${PageBar}</td>
													</tr>
												</table>
											</z:datagrid>
										</td>
									</tr>
								</table>
							</td>
					</table>
				</td>
			</tr>
		</table>
</body>
</html>
