<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="org.dom4j.*"%>
<%@page import="org.dom4j.io.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
//根据column名来取得value值
DataGrid.getSelectedValueByColumn = function(ele, column) {
	ele = $(ele);
	var ds = ele.DataSource;
	for ( var i = 0; i < ds.Columns.length; i++) {
		if (ds.Columns[i].Name == column.toLowerCase()) {
			for ( var k = 1; k < ele.rows.length; k++) {
				if (ele.rows[k].Selected) {
					return ds.Values[k - 1][i];
				}
			}
		}
	}
}
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("OrderFlag",$V("orderFlag"));
		return true;
	}
});

/**
 * 查询
 */
function sear(index) {
	DataGrid.setParam("dg1", Constant.PageIndex, index);
	DataGrid.setParam("dg1", "jobsName", $V("jobsName"));
	DataGrid.setParam("dg1", "jobsType", $V("jobsType"));
	DataGrid.setParam("dg1", "jobsProperty", $V("jobsProperty"));
	DataGrid.setParam("dg1", "jobsAddress", $V("jobsAddress"));
	DataGrid.setParam("dg1", "isPublish", $V("isPublish"));
	DataGrid.loadData("dg1");
}

/**
 * 新建招聘岗位
 */
function addJobs() {
	var diag = new Dialog("Diag1");
	diag.Width = 970;
	diag.Height = 400;
	diag.Title = "新建招聘岗位";
	diag.URL = "Document/JobsInfoDialog.jsp?flag=add";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveJobs;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建招聘岗位";
	diag.show();
}
/**
 * 编辑礼品
 */
function editJobs() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的岗位！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能修改一个岗位！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 970;
	diag.Height = 400;
	diag.Title = "编辑招聘岗位";
	diag.URL = "Document/JobsInfoDialog.jsp?id=" + dr.get("id")+"&flag=edit";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveJobs;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑招聘岗位";
	diag.show();
}
/**
 * 礼品的保存
 */
function saveJobs() {
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	Server.sendRequest("com.sinosoft.cms.document.JobsInfo.saveJobs",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
							$D.close();sear(DataGrid.getParam("dg1",Constant.PageIndex));
						}
					);
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

/**
 * 撤销发布
 */
function pauseJobs(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要撤销发布的岗位！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能撤销一个岗位！");
		return;
	}
	var status=DataGrid.getSelectedValueByColumn("dg1","isPublish");
	if(status!="Y"){
		Dialog.alert("只有已发布的岗位才可以撤销发布！");
		return;
	}
	var dr=drs[0];
	var arr = DataGrid.getSelectedValue("dg1");
	var dc = new DataCollection();
	dc.add("id", dr.get("id"));
	Server.sendRequest("com.sinosoft.cms.document.JobsInfo.pauseJobs", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						sear(DataGrid.getParam("dg1",Constant.PageIndex));
						$D.close();
					}
				});
			});
}
/**
 * 发布
 */
function publishJobs(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要发布的岗位！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能发布一个岗位！");
		return;
	}
	var status=DataGrid.getSelectedValueByColumn("dg1","isPublish");
	if(status=="Y"){
		Dialog.alert("该岗位已发布！");
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	var dc = new DataCollection();
	dc.add("id", dr.get("id"));
	Server.sendRequest("com.sinosoft.cms.document.JobsInfo.publishJobs", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						sear(DataGrid.getParam("dg1",Constant.PageIndex));
						$D.close();
					}
				});
			});
	}
	
/**
 * 删除
 */
function delJobs(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要删除的岗位！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能删除一个岗位！");
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	var dc = new DataCollection();
	dc.add("id", dr.get("id"));
	Dialog.confirm("删除后不可恢复，确认要删除？",function() { 
		Dialog.wait("正在删除岗位......"); 
		Server.sendRequest("com.sinosoft.cms.document.JobsInfo.delJobs", dc, function() {
			Dialog.endWait();
			var response = Server.getResponse();
			Dialog.alert(response.Message, function() {
				if (response.Status == 1) {
					sear(DataGrid.getParam("dg1",Constant.PageIndex));
					$D.close();
				}
			});
		});
	});
}

function sortJobsSave(){	
	DataGrid.save("dg1","com.sinosoft.cms.document.JobsInfo.sortJobs",function(){			
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
	});
}

</script>
</head>
<body>
<input type="hidden" id="GiftStatusFlag" value="">
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
				<tr>
					<td valign="top">
						<fieldset>
							<legend> <label>招聘信息管理</label></legend> 
							<z:init method="com.sinosoft.cms.document.JobsInfo.init">
								<form id="form1" >
									<table>
										<tr>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">岗位名称：</td>
											<td><input value="" type="text" id="jobsName" name="jobsName" ztype="String" class="inputText" size="20"></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">类别：</td>
											<td><input value="" type="text" id="jobsType" name="jobsType" ztype="String" class="inputText" size="20"></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">工作性质：</td>
											<td><input value="" type="text" id="jobsProperty" name="jobsProperty" ztype="String" class="inputText" size="20"></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">工作地点：</td>
											<td><input value="" type="text" id="jobsAddress" name="jobsAddress"  ztype="String" class="inputText" size="20"></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是否发布：</td>
											<td><z:select style="width:60px;" id="isPublish" name="isPublish">${YesOrNo}</z:select></td>
										</tr>
									</table>
								</form>
							</z:init>
						</fieldset>
						<table>
							<tr>
								<td>&nbsp;</td>
							</tr>
						</table>
							 <z:tbutton onClick="sear(0)"><img src="../Icons/icon021a2.gif" width="20" height="20" />查询</z:tbutton>
							 <z:tbutton onClick="addJobs()"><img src="../Icons/icon021a2.gif" width="20" height="20" />增加</z:tbutton>
							 <z:tbutton onClick="editJobs()"><img src="../Icons/icon021a2.gif" width="20" height="20" />修改</z:tbutton>
							 <z:tbutton onClick="delJobs()"><img src="../Icons/icon021a2.gif" width="20" height="20" />删除</z:tbutton>
							 <z:tbutton onClick="publishJobs()"><img src="../Icons/icon021a2.gif" width="20" height="20" />发布</z:tbutton>
							 <z:tbutton onClick="pauseJobs()"><img src="../Icons/icon021a2.gif" width="20" height="20" />撤销发布</z:tbutton>
							 <z:tbutton onClick="sortJobsSave()"><img src="../Icons/icon021a2.gif" width="20" height="20" />排序保存</z:tbutton>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td style="padding: 0px 5px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable" style="table-layout: fixed;">
							<tr>
								<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
									<z:datagrid id="dg1" method="com.sinosoft.cms.document.JobsInfo.dg1DataBind" size="10" autoFill="true"  scroll="true" lazy="false" multiSelect="true">
										<table width="120%" cellpadding="2" cellspacing="0"
											class="dataTable" afterdrag="afterRowDragEnd" 
											style="table-layout: fixed" fixedHeight="343px">
											<tr ztype="head" class="dataTableHead">
												<td width="30" height="30" ztype="RowNo" drag="true">
												<img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
												<td width="30" height="30" ztype="Selector" field="id">&nbsp;</td>
												<td width="130"><strong>岗位名称</strong></td>
												<td width="50px;"><strong>类别</strong></td>
												<td width="60px;"><strong>工作性质</strong></td>
												<td width="80px;"><strong>工作地点</strong></td>
												<td width="80px;"><strong>岗位职责</strong></td>
												<td width="80px;"><strong>任职要求</strong></td>
												<td width="60px;"><strong>是否发布</strong></td>
												<td width="120px;"><strong>发布时间</strong></td>
												<td width="100px;"><strong>hr邮箱</strong></td>
												<td width="100px"><strong>排序</strong></td>
												<td width="60px;"><strong>创建人</strong></td>
												<td width="120px;"><strong>创建时间</strong></td>
												<td width="60px;"><strong>修改人</strong></td>
												<td width="120px;"><strong>修改时间</strong></td>
												
											</tr>
											<tr style="background-color: #F9FBFC">
												<td onmouseover="">&nbsp;</td>
												<td>&nbsp;</td>
												<td title="${jobsName}">${jobsName}</td>
												<td title="${jobsType}">${jobsType}</td>
												<td title="${jobsProperty}">${jobsProperty}</td>
												<td title="${jobsAddress}">${jobsAddress}</td>
												<td title="${jobsDuty}">${jobsDuty}</td>
												<td title="${jobsRequirement}">${jobsRequirement}</td>
												<td title="${isPublishName}">${isPublishName}</td>
												<td title="${publishDate}">${publishDate}</td>
												<td title="${hrEmail}">${hrEmail}</td>
												<td >${orderFlag}</td>
												<td title="${createUser}">${createUser}</td>
												<td title="${createDate}">${createDate}</td>
												<td title="${modifyUser}">${modifyUser}</td>
												<td title="${modifyDate}">${modifyDate}</td>
												
											</tr>
											<tr ztype="edit" bgcolor="#E1F3FF">
												<td bgcolor="#E1F3FF">&nbsp;</td>
												<td>&nbsp;</td>
												<td>${jobsName}</td>
												<td>${jobsType}</td>
												<td>${jobsProperty}</td>
												<td>${jobsAddress}</td>
												<td>${jobsDuty}</td>
												<td>${jobsRequirement}</td>
												<td>${isPublishName}</td>
												<td>${publishDate}</td>
												<td>${hrEmail}</td>
												<td><input name="orderFlag" type="text" id="orderFlag"
														value="${orderFlag}" size="15"></td>
												<td>${createUser}</td>
												<td>${createDate}</td>
												<td>${modifyUser}</td>
												<td>${modifyDate}</td>
												
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
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>