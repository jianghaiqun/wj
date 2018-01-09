<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>淘宝新注册会员统计列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function sear(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","year",document.all.queryYear.value);
	DataGrid.setParam("dg1","month",document.all.queryMonth.value);
	DataGrid.loadData("dg1");
}

function look(){
	var arr = DataGrid.getSelectedValue("dg1");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要查看的数据！");
		return;
	}
	if (arr.length > 1) {
		Dialog.alert("请选择一条数据！");
		return;
	}
	var id = arr[0];
	window.open("TaobaoMemberRepurchaseDetail.jsp?analysisId="+id);
}
</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
			  <tr>
				<td>
				  <fieldset >
				   <legend >
				      <label>淘宝新注册会员统计</label> 
				   </legend>  
						<table>
						          <tr>
						          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">年份：</td>
						          <td>
						          	<select id="queryYear" style="width:100px;">
										<option value="2017">2017年</option>
										<option value="2018">2018年</option>
									</select>
						          </td>
		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">月份：</td>
						          <td >
						          	<select id="queryMonth" style="width:100px;">
						          		<option value="">全部</option>
										<option value="1">1月</option>
										<option value="2">2月</option>
										<option value="3">3月</option>
										<option value="4">4月</option>
										<option value="5">5月</option>
										<option value="6">6月</option>
										<option value="7">7月</option>
										<option value="8">8月</option>
										<option value="9">9月</option>
										<option value="10">10月</option>
										<option value="11">11月</option>
										<option value="12">12月</option>
									</select>
						          </td>
						          </tr>
					    </table>
	                 </fieldset>

	                 <div style="height:5px;"></div>

					 <z:tbutton id="b1" onClick="sear()"><img src="../Icons/icon006a7.gif" width="20" height="20"/>查询</z:tbutton>
					 <z:tbutton id="b1" onClick="look()"><img src="../Icons/icon006a7.gif" width="20" height="20"/>查看复购详情</z:tbutton>  
		
				</td>
			  </tr>
			  <tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.sinosoft.platform.TaobaoMemberAnalysis.dg1DataBind" size="15" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="40" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
                                 <td width="80" style="text-align:center;"><b>月份</b></td>
                                 <td width="80" style="text-align:center;"><b>注册会员数</b></td>
                                 <td width="80" style="text-align:center;"><b>其他渠道复购数</b></td>
                                 <td width="80" style="text-align:center;"><b>转化率</b></td>
                                 <td width="80" style="text-align:center;"><b>其他渠道产品保费</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td width="15">&nbsp;<input name="id" type="hidden" id="id" value="${id}" /></td>
								 <td style="text-align:left;" title="${month}">${month}</td>
								 <td style="text-align:left;" title="${registerCount}">${registerCount}</td>
								 <td style="text-align:left;" title="${repurchaseCount}">${repurchaseCount}</td>
								 <td style="text-align:left;" title="${convertRatio}">${convertRatio}</td>
								 <td style="text-align:left;" title="${repurchasePremium}">${repurchasePremium}</td>
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