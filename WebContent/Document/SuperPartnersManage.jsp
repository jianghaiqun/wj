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
			dr.set("partnerName", $V("partnerName"));
			return true;
		};
	});

	// 查询
	function query() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "partnerName", $V("partnerName"));
		DataGrid.loadData("dg1");
	}

	// 新建
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 800;
		diag.Height = 450;
		diag.Title = "超级伙伴-新增";
		diag.URL = "Document/SuperPartnersAdd.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "超级伙伴";
		diag.show();
	}

	// 新建保存
	function addSave() {
		var dc = $DW.Form.getData("form2");
		if ($DW.Verify.hasError()) {
			return;
		}
		//Dialog.confirm("确认要增加？", function() {
			Server.sendRequest("com.sinosoft.cms.document.SuperPartnersManage.savePartnersManage",
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
		//});
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
		diag.Width = 800;
		diag.Height = 450;
		diag.Title = "超级伙伴-编辑";
		diag.URL = "Document/SuperPartnersEditor.jsp?id=" + dr.get("id");
		diag.OKEvent = editSave;
		diag.show();
	}

	//编辑保存
	function editSave(){
		var dc = $DW.Form.getData("PartnersEditorForm");
		if ($DW.Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.sinosoft.cms.document.SuperPartnersManage.updatePartnersManage",dc,function(){
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
		
		dc.add("id", arr);
		Dialog.confirm("确认要删除？", function() {
			Server.sendRequest("com.sinosoft.cms.document.SuperPartnersManage.deletePartnersManage", dc, function(
					response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData('dg1');
					}
				});
			});
		});
	}
</script>
</head>
<body>
	<table width="100%" border='0' cellpadding='10' cellspacing='0'>
		<tr>
			<td valign="top">
				<fieldset>
					<legend>
						<label>超级伙伴管理</label>
					</legend>
					<form id="form1">
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">合作商名称：</td>
								<td><input value="" type="text" id="partnerName" name="partnerName"
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
				</table> <z:tButtonPurview>${_Document_PartnersManage_Button}</z:tButtonPurview>
			</td>
		</tr>
		<tr>
			<td style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<z:datagrid id="dg1"
					method="com.sinosoft.cms.document.SuperPartnersManage.queryPartnersManage" size="10"
					scroll="true">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" ztype="RowNo"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16">
							</td>
							<td width="5%" ztype="Selector" field="id">&nbsp;</td>
							<td width="10%"><strong>合作商编码</strong>
							</td>
							<td width="15%"><strong>合作商名称</strong>
							</td>
							<td width="10%"><strong>合作商私钥</strong>
							</td>
							<td width="10%"><strong>合作商状态</strong>
							</td>
							<td width="10%"><strong>支付方式</strong>
							</td>
							<td width="10%"><strong>渠道编号</strong>
							</td>
							<td width="10%"><strong>前台成功回调URL</strong>
							</td>
							<td width="10%"><strong>前台失败回调地址</strong>
							</td>
							<td width="10%"><strong>后台回调地址</strong>
							</td>
							<td width="10%"><strong>联系电话</strong>
							</td>
							<td width="10%"><strong>创建时间</strong>
							</td>
						</tr>
						<tr style="background-color: #F9FBFC">
							<td width="3%">&nbsp;</td>
							<td>&nbsp;</td>
							<td hidden="true">${id}</td>
							<td>${partnerId}</td>
							<td>${partnerName}</td>
							<td>${partnerKey}</td> 
							<td>${partnerStatusName}</td>
							<td>${payTypeName}</td>
							<td>${channelSn}</td>
							<td title="${returnUrl}">${returnUrl}</td>
							<td title="${returnErrorUrl}">${returnErrorUrl}</td>
							<td title="${bgReturnUrl}">${bgReturnUrl}</td>
							<td>${telphone}</td>
							<td>${createTime}</td>
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