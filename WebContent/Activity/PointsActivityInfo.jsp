
<%
	/* 积分设置画面
	 ************************************************************** 
	 *  程序名 : PointsSet.jsp
	 *  建立日期:2014/03/21 
	 *  作者   :  wangcaiyun
	 *  模块   :  CMS
	 *  描述   :  设置发放的积分数值
	 *  备注   :  
	 * ------------------------------------------------------------ 
	 *  修改历史 
	 *  序号   日期   修改人     修改原因 
	 * 1 
	 * 2 
	 ************************************************************** 
	 */
%>
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
<script src="../Framework/Spell.js"></script>
<script>
Page.onLoad(function(){

	$("dg1").afterEdit = function(tr,dr){
		dr.set("Points",$V("Points"));
		return true;
	}
});
	function editPoints() {
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
		var dr = drs[0];
		var dc = new DataCollection();
		dc.add("Id", dr.get("Id"));
		dc.add("Points", dr.get("Points"));
		Server.sendRequest("com.wangjin.activity.PointsActivityInfo.setPoints", dc,
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
						<td style="padding: 0px 5px;"><z:tButtonPurview>${_Activity_PointsActivityInfo_Button}</z:tButtonPurview>
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
											method="com.wangjin.activity.PointsActivityInfo.dg1DataBind"
											size="10" >
											<table width="100%" cellpadding="2" cellspacing="0"
												class="dataTable">
												<tr ztype="head" class="dataTableHead">
													<td width="30px" height="30" ztype="RowNo" ><img
														src="../Framework/Images/icon_drag.gif" width="16"
														height="16">
													</td>
													<td width="30px" height="30" ztype="Selector" field="id">&nbsp;</td>
													<td ><b>积分活动类型</b>
													</td>
													<td ><b>积分</b>
													</td>
													<td ><b>修改时间</b>
													</td>
													<td ><b>修改人</b>
													</td>
												</tr>
												<tr style="background-color: #F9FBFC">
													<td width="3%">&nbsp; 
													</td>
													<td>&nbsp;</td>
													<td>${PointsTypeName}</td>
													<td>${Points}</td>
													<td>${ModifyDate}</td>
													<td>${ModifyUser}</td>

												</tr>
												<tr ztype="edit" bgcolor="#E1F3FF">
													<td bgcolor="#E1F3FF">&nbsp;</td>
													<td>&nbsp;</td>
													<td>${PointsTypeName}</td>
													<td><input name="Points" type="text" id="Points"
														value="${Points}" size="20"></td>
													<td>${ModifyDate}</td>
													<td>${ModifyUser}</td>
													<td type="hidden">${Id}</td>
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