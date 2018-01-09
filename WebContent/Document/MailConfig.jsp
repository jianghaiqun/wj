<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
	
	Page.onLoad(function() {
		$("dg1").afterEdit = function(tr, dr) {
			dr.set("MouldName", $V("MouldName"));
			return true;
		};
	});

	// 查询
	function query() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "MouldName", $V("MouldName"));
		DataGrid.loadData("dg1");
	}

	// 新建
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 400;
		diag.Height = 200;
		diag.Title = "邮件模板-新增";
		diag.URL = "Document/MailConfigAdd.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "新增模板";
		diag.show();
	}

	// 新建保存
	function addSave() {
		var dc = $DW.Form.getData("form2");
		Dialog.confirm("确认要增加？", function() {
			Server.sendRequest("com.sinosoft.cms.document.MailConfig.saveMailConfig",
					dc, function() {
						Dialog.endWait();
						var response = Server.getResponse();
						Dialog.alert(response.Message, function() {
							if (response.Status == 1) {
								DataGrid.loadData("dg1");
								$D.close();
							}
						});
					});
		});
	}

	// 编辑
	function editor() {
		var arrs = DataGrid.getSelectedData("dg1");
		var drs = arrs.Rows;
		var dr = drs[0];
		if (!drs || drs.length == 0) {
			Dialog.alert("请选择一条进行编辑 ！");
			return;
		}
		if (!drs || drs.length >= 2) {
			Dialog.alert("只能选择一条进行编辑！");
			return;
		}

		var diag = new Dialog("Diag1");
		diag.Width = 400;
		diag.Height = 200;
		diag.Title = "邮箱模板-编辑";
		diag.URL = "Document/MailConfigEditor.jsp?emailType=" + dr.get("emailType");
		diag.OKEvent = editSave;
		diag.show();
	}

	//编辑保存
	function editSave(){
		var dc = $DW.Form.getData("MailConfigEditorForm");
		Server.sendRequest("com.sinosoft.cms.document.MailConfig.updateMailConfig",dc,function(){
			var response = Server.getResponse();
			Dialog.alert(response.Message);
			if(response.Status==1){
					$D.close();
					DataGrid.loadData('dg1');
			}
		});
	}
	

	// 删除
	function del() {
		var arr = DataGrid.getSelectedValue("dg1");
		if(arr == null){
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		var dc = new DataCollection();
		
		dc.add("emailType", arr);
		Dialog.confirm("确认要删除？", function() {
			Server.sendRequest("com.sinosoft.cms.document.MailConfig.deleteMailConfig", dc, function(
					response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData('dg1');
					}
				});
			});
		});
	}

	//产品列表
	function product(){

		var arrs = DataGrid.getSelectedData("dg1");
		var drs = arrs.Rows;
		var dr = drs[0];
		
		if (!drs || drs.length == 0) {
			Dialog.alert("请选择关联邮件模板 !");
			return;
		}
		
		if (!drs || drs.length > 1) {
			Dialog.alert("请选择一项进行操作 !");
			return;
		}

		var productID = drs[0].get("product");
		var emailType = drs[0].get("emailType");
		
		var diag = new Dialog("Diag2");
		diag.Width = 550;
		diag.Height = 370;
		diag.Title = "产品列表";
		diag.URL = "Document/MailConfigProductDialog.jsp?emailType="+emailType+"&productID="+productID;
		diag.OKEvent = saveProduct;
		diag.show();
		diag.CancelButton.value = "取消";

		var  producttimeoutID=setTimeout(function (){
			
			var riskcodeArray=productID.split(",");
			for(var i=0;i<riskcodeArray.length;i++){
				$DW.DataGrid.select($DW.$("dg2"),riskcodeArray[i]);
			}
			clearTimeout(producttimeoutID);
		},1500 );
	}

	//保存选中产品
	function saveProduct(){
		var arr = $DW.DataGrid.getSelectedValue("dg2");
		var dc = new DataCollection();
		
		var emailType = $DW.$V("emailType");
		if(!arr){
			Dialog.alert("请选择产品!");
			return;
		}
		if(arr.length > 3){
			Dialog.alert("超出上限 3 款产品!");
			return;
		}

		dc.add("emailType",emailType);
		dc.add("ProductIDs",arr);
		
		Server.sendRequest("com.sinosoft.cms.document.MailConfig.saveMailConfigProduct", dc, function(
					response) {
			Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData('dg1');
					}
				});
			});
		
	}

	//产品列表
	function activity(){

		var arrs = DataGrid.getSelectedData("dg1");
		var drs = arrs.Rows;
		var dr = drs[0];
		
		if (!drs || drs.length == 0) {
			Dialog.alert("请选择关联邮件模板 !");
			return;
		}
		
		if (!drs || drs.length > 1) {
			Dialog.alert("请选择一项进行操作 !");
			return;
		}

		var emailType = drs[0].get("emailType");
		
		var diag = new Dialog("Diag2");
		diag.Width = 800;
		diag.Height = 500;
		diag.Title = "邮件配置-活动图片";
		diag.URL = "Document/MailConfigImageShow.jsp?emailType="+emailType;
		diag.OKEvent = activityOK;
		diag.show();
		diag.CancelButton.value = "取消";
	}

	//活动 OK 按钮
	function activityOK(){
		$D.close();
	}
	
	//产品品类关联产品
	function setCategoryProduct(){
		var diag = new Dialog("Diag2");
		diag.Width = 600;
		diag.Height = 300;
		diag.Title = "邮件配置-产品品类配置";
		diag.URL = "Document/MailConfigCategoryProduct.jsp";
		diag.OKEvent = categoryProductOK;
		diag.show();
		diag.CancelButton.value = "取消";
	}
	//产品品类关联产品窗口ok事件
	function categoryProductOK(){
		$D.close();
	}
	
	
	
</script>
</head>
<body>
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td valign="top">
				<fieldset>
					<legend>
						<label>邮件模板管理</label>
					</legend>
					<form id="form1">
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">模板名称：</td>
								<td><input value="" type="text" id="MouldName" name="MouldName"
									ztype="String" class="inputText" size="20" />
									</td>
									<td><z:tbutton id="BtnAdd" onClick="query()">
								              <img src="../Icons/icon403a3.gif" width="20" height="20" />查询</z:tbutton> 
								    </td>
							</tr>
						</table>
					</form>
				</fieldset>
				<table>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table> <z:tButtonPurview>${_Document_MailConfig_Button}</z:tButtonPurview>
			</td>
		</tr>
		<tr>
			<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<z:datagrid id="dg1"
					method="com.sinosoft.cms.document.MailConfig.queryMailConfig" size="10"
					scroll="true">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16">
							</td>
							<td width="3%" ztype="Selector" field="emailType">&nbsp;</td>
							<td width="17%"><strong>模板名称</strong>
							</td>
							<td width="10%"><strong>推荐活动</strong>
							</td>
							<td width="10%"><strong>推荐产品</strong>
							</td>
							<td width="12%"><strong>产品类别</strong>
							</td>
							<td width="12%"><strong>操作人</strong>
							</td>
							<td width="12%"><strong>创建时间</strong>
							</td>
						</tr>
						<tr style="background-color: #F9FBFC">
							<td width="3%">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${CodeName}</td>
							<td>${ActivityFlag}</td>
							<td>${ProductFlag}</td>
							<td>${ProductCategoryName}</td>
							<td>${Operator}</td>
							<td>${CreateDate}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="9" align="left">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid>
			</td>
		</tr>
	</table>
	
	
</body>
</html>