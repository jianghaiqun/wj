<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>积分设置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){

	$("dg1").afterEdit = function(tr,dr){
		dr.set("gradeName",$V("gradeName"));
		dr.set("orderCount",$V("orderCount"));
		dr.set("sumPrem",$V("sumPrem"));
		dr.set("link",$V("link"));
		return true;
	}
});
	function editGrade() {
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要修改的选项！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("只能选择1条信息修改！");
			return;
		}
		if(Verify.hasError()){
			return;
		}
		var dr = drs[0];
		var dc = new DataCollection();
		dc.add("id", dr.get("id"));
		dc.add("gradeName", dr.get("gradeName"));
		dc.add("orderCount", dr.get("orderCount"));
		dc.add("sumPrem", dr.get("sumPrem"));
		dc.add("link", dr.get("link"));
		Server.sendRequest("com.sinosoft.cms.dataservice.Member.editMemGrade", dc,
				function(response) {
					Dialog.alert(response.Message);
					if(response.Status==1){
						DataGrid.loadData('dg1');
					}
				});
	}
</script>
</head>
<body>
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td width="50%" valign="top">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" id="ConfigTable">
					<tr>
						<td style="padding: 0px 5px;"><z:tbutton onClick="editGrade()"><img src="../Icons/icon021a4.gif" />保存</z:tbutton> 
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					<tr>
						<td style="padding: 0px 5px;">
							<table width="100%" border="0" cellspacing="0" cellpadding="6"
								class="blockTable">
								<tr>
									<td
										style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
										<z:datagrid id="dg1"
											method="com.sinosoft.cms.dataservice.Member.gradeInquery"
											size="10" >
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable">
												<tr ztype="head" class="dataTableHead">
													<td width="20px" height="30" ztype="RowNo" ><img
														src="../Framework/Images/icon_drag.gif" width="16"
														height="16">
													</td>
													<td width="20px" height="30" ztype="Selector" field="id">&nbsp;</td>
													<td width="80px"><b>会员等级编码</b>
													</td>
													<td width="80px"><b>会员等级名称</b>
													</td>
													<td width="50px"><b>级别</b>
													</td>
													<td width="60px"><b>前一等级</b>
													</td>
													<td width="170px"><b>升级到此等级所需有效订单数</b>
													</td>
													<td width="160px"><b>升级到此等级所需累计保费</b>
													</td>
													<td width="90px"><b>查看特权链接</b>
													</td>
													<td width="60px"><b>修改人</b>
													</td>
													<td width="90px"><b>修改时间</b>
													</td>
												</tr>
												<tr style="background-color: #F9FBFC">
													<td width="3%">&nbsp; 
													</td>
													<td>&nbsp;</td>
													<td>${gradeCode}</td>
													<td>${gradeName}</td>
													<td>${grade}</td>
													<td>${preGradeCode}</td>
													<td>${orderCount}</td>
													<td>${sumPrem}</td>
													<td>${link}</td>
													<td>${modifyUser}</td>
													<td>${modifyDate}</td>
												</tr>
												<tr ztype="edit" bgcolor="#E1F3FF">
													<td bgcolor="#E1F3FF">&nbsp;</td>
													<td>&nbsp;</td>
													<td>${gradeCode}</td>
													<td><input name="gradeName" type="text" id="gradeName"
														value="${gradeName}" size="20" verify="会员等级名称|NotNull"></td>
													<td>${grade}</td>
													<td>${preGradeCode}</td>
													<td><input name="orderCount" type="text" id="orderCount"
														value="${orderCount}" size="20" verify="订单数|Int"></td>
													<td><input name="sumPrem" type="text" id="sumPrem"
														value="${sumPrem}" size="20" verify="累计保费|PositiveNumber"></td>
													<td><input name="link" type="text" id="link"
														value="${link}" size="20"></td>
													<td>${modifyUser}</td>
													<td>${modifyDate}</td>
													<td type="hidden">${id}</td>
												</tr>
												<tr ztype="pagebar">
													<td colspan="6" align="left">${PageBar}</td>
												</tr>
											</table>
										</z:datagrid>
									</td>
								</tr>
							</table></td>
					</tr>
				</table></td>
		</tr>
	</table>
</body>
</html>