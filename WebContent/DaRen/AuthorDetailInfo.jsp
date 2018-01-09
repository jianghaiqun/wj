<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%> 
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<%@page import="com.sinosoft.cms.pub.PubFun"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>作者详细统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script src="../wwwroot/kxb/js/Common.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script>
//订单查询
function doSearch(){ 
	var sd = $V("cooperationTime");
	var ed = $V("endCooperationTime");
	var sc = $V("payTime");
	var se = $V("endPayTime");
	
	if(ed < sd || se < sc){
	    Dialog.alert("结束日期不能小于开始日期！");
		return;
	}
	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","cooperationTime",sd);
	DataGrid.setParam("dg1","endCooperationTime",ed);
	DataGrid.setParam("dg1","payTime",sc);
	DataGrid.setParam("dg1","endPayTime",se);
	DataGrid.setParam("dg1","isPay",$V("isPay"));
	DataGrid.setParam("dg1","productName",$V("productName"));
	DataGrid.setParam("dg1","destination",$V("destination"));
	DataGrid.setParam("dg1","authorName",$V("authorName"));
	DataGrid.setParam("dg1","source",$V("source"));
	DataGrid.setParam("dg1","contactPeople",$NV("contactPeople"));
	DataGrid.setParam("dg1","remark1",$V("remark1"));
	
	DataGrid.loadData("dg1");
}

function add() {
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 200;
	diag.Title = "添加作者详细信息";
	diag.URL = "DaRen/AuthorDetailInfoDialog.jsp?type=Add";
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加作者详细信息";
	diag.show();
}
function addSave() {

	var dc = $DW.Form.getData("form2");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.daren.AuthorDetailInfo.add", dc, function() {
		Dialog.endWait();
		var response = Server.getResponse();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});	
}

function edit() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的数据！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择1条信息修改！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 200;
	diag.Title = "修改作者详细信息";
	diag.URL = "DaRen/AuthorDetailInfoDialog.jsp?type=Modify&ID=" + dr.get("id");
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改作者详细信息";
	diag.show();
}

function editSave() {

	var dc = $DW.Form.getData("form2");
	if ($DW.Verify.hasError()) {
		return;
	}
	Server.sendRequest("com.wangjin.daren.AuthorDetailInfo.edit", dc, function() {
		Dialog.endWait();
		var response = Server.getResponse();
		Dialog.alert(response.Message, function() {
			if (response.Status == 1) {
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});	
}

function del() {
	var arr = DataGrid.getSelectedValue("dg1");
	if (!arr || arr.length == 0) {
		Dialog.alert("请先选择要删除的数据！");
		return;
	}
	
	Dialog.confirm("删除后不可恢复，关联文章的稿费明细信息也会删除，确认要删除？", function() {
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.wangjin.daren.AuthorDetailInfo.delete", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
						}
					});
				});
	});
}

function payDetail() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要查看稿费明细的数据！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("只能选择1条信息查看稿费明细！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 750;
	diag.Height = 400;
	diag.Title = "稿费明细";
	diag.URL = "DaRen/PaymemntDetailInfo.jsp?authorDetailInfo_id=" + dr.get("id");
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
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
				<z:init method="com.wangjin.daren.AuthorDetailInfo.init">
					 <table  cellspacing="0" cellpadding="3">
						<tr>
							<td>合作时间 从：</td>
		                	<td> <input name="cooperationTime" type="text" id="cooperationTime" value="" style="width:90px" ztype="date"></td>
							<td>至：</td>
							<td><input name="endCooperationTime" type="text" id="endCooperationTime" value="" style="width:90px" ztype="date"></td>
							
							<td>支付时间 从：</td>
		                	<td> <input name="payTime" type="text" id="payTime" value="" style="width:90px" ztype="date"></td>
							<td>至：</td>
							<td><input name="endPayTime" type="text" id="endPayTime" value="" style="width:90px" ztype="date"></td>
							<td>是否已支付：</td>
						    <td ><z:select id="isPay" name="isPay" style="width:50px">${YesOrNo}</z:select></td>
						</tr>
						<tr>
							<td>来源：</td>
							<td>
								<z:select style="width:80px;" name="source" id="source">${source}</z:select>
							</td>
							<td>推荐险种：</td>
							<td>
								<z:select style="width:120px;" name="productName" id="productName">${productName}</z:select>
							</td>
							<td>目的地：</td>
							<td> <input name="destination" type="text" id="destination" value="" style="width:80px" /></td>
							<td>作者：</td>
							<td> <input name="authorName" type="text" id="authorName" value="" style="width:80px" /></td>
							<td>备注：</td>
							<td> <input name="remark1" type="text" id="remark1" value="" style="width:150px" /></td>
						</tr>
						<tr>
							<td>创建者：</td>
							<td colspan="9" nowrap>${contactPeople}</td>
						</tr>
						<tr>
						   <td colspan="8" nowrap><z:tButtonPurview>${_DaRen_AuthorDetailInfo_Button}</z:tButtonPurview></td>
		            	</tr>
		             </table>
		             </z:init>
				</td>
			</tr>
			  <tr>
				<td style="padding: 0px 0px;">
				 <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;" >
				  <tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<z:datagrid id="dg1" method="com.wangjin.daren.AuthorDetailInfo.dg1DataBind" size="15" scroll="true" lazy="true">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" fixedHeight="370px">
							<tr ztype="head" class="dataTableHead">
								<td width="40" ztype="RowNo" style="text-align:center;"><strong>序号</strong></td>
								<td width="15" ztype="selector" field="id" style="text-align:center;">&nbsp;</td>
						        <td width="70" style="text-align:center;"><b>目的地</b></td>
						        <td width="70" style="text-align:center;"><b>作者</b></td>
						        <td width="40" style="text-align:center;"><b>性别</b></td>
						        <td width="70" style="text-align:center;"><b>所在城市</b></td>
						        <td width="80" style="text-align:center;"><b>代码</b></td>
						        <td width="250" style="text-align:center;"><b>帖子链接</b></td>
						        <td width="70" style="text-align:center;"><b>来源</b></td>
								<td width="100" style="text-align:center;"><b>联系方式</b></td>
								<td width="90" style="text-align:center;"><b>合作时间</b></td>
								<td width="60" style="text-align:center;"><b>支付规则</b></td>
								<td width="80" style="text-align:center;"><b>支付方式</b></td>
								<td width="100" style="text-align:center;"><b>推荐险种</b></td>
								<td width="80" style="text-align:center;"><b>稿费支付总额</b></td>
								<td width="250" style="text-align:center;"><b>备注</b></td>
								<td width="90" style="text-align:center;"><b>创建时间</b></td>
								<td width="70" style="text-align:center;"><b>创建者</b></td>
								<td width="90" style="text-align:center;"><b>修改时间</b></td>
								<td width="70" style="text-align:center;"><b>修改者</b></td>
							</tr>
							<tr  style="background-color:#F9FBFC">
								<td width="40">&nbsp;</td>
								<td width="15">&nbsp;<input name="id" type="hidden" id="id" value="${id}" /></td>
								<td style="text-align:center;" title="${destination}">${destination}</td>
								<td style="text-align:center;" title="${authorName}">${authorName}</td>
								<td style="text-align:center;" title="${authorSex}">${authorSex}</td>
								<td style="text-align:center;" title="${city}">${city}</td>
								<td style="text-align:center;" title="${code}">${code}</td>
								<td style="text-align:center;" title="${articleLink}">${articleLink}</td>
								<td style="text-align:center;" title="${source}">${source}</td>
								<td style="text-align:center;" title="${contactTool} ${contactType}">${contactTool} ${contactType}</td>
								<td style="text-align:center;" title="${cooperationTime}">${cooperationTime}</td>
								<td style="text-align:center;" title="${payRules}">${payRules}</td>
								<td style="text-align:center;" title="${payType}">${payType}</td>
								<td style="text-align:center;" title="${productName}">${productName}</td>
								<td style="text-align:center;" title="${payPrice}">${payPrice}</td>
								<td style="text-align:center;" title="${remark1}">${remark1}</td>
								<td style="text-align:center;" title="${createDate}">${createDate}</td>
								<td style="text-align:center;" title="${createUser}">${createUser}</td>
								<td style="text-align:center;" title="${modifyDate}">${modifyDate}</td>
								<td style="text-align:center;" title="${modifyUser}">${modifyUser}</td>
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
