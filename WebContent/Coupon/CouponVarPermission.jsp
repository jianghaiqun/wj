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
			dr.set("CodeName", $V("CodeName"));
			dr.set("Memo", $V("Memo"));
			return true;
		}
	});

	// 查询
	function sear() {
		DataGrid.setParam("dg1", Constant.PageIndex, 0);
		DataGrid.setParam("dg1", "name", $V("name"));
		DataGrid.loadData("dg1");
	}

	// 新建
	function add() {
		var diag = new Dialog("Diag1");
		diag.Width = 850;
		diag.Height = 350;
		diag.Title = "增加";
		diag.URL = "Coupon/CouponPermAdd.jsp";
		diag.onLoad = function() {
		};
		diag.OKEvent = addSave;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "增加";
		diag.show();
	}

	// 新建保存
	function addSave() {
		if ($DW.Verify.hasError()) {
			return;
		}

		var username = $DW.$V('username');
		var couponVarPerm = $DW.$V('couponVarPerm');
		if (couponVarPerm.indexOf("%") != couponVarPerm.length - 1) {
			Dialog.alert("优惠券面值折扣请填百分数（例：60%）！");
			return;
		}
		if (isNaN(couponVarPerm.substring(0, couponVarPerm.length - 1))) {
			Dialog.alert("优惠券面值折扣请填百分数（例：60%）！");
			return;
		}
		
		if (couponVarPerm.substring(0, couponVarPerm.length - 1) > 100) {
			Dialog.alert("优惠券面值折扣上限100%！");
			return;
		}

		var dc = $DW.Form.getData("form2");
		Dialog.confirm("确认要增加？", function() {
			Server.sendRequest("com.wangjin.coupon.CouponInfo.addCouponPerm",
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
	function save() {
		DataGrid.save("dg1", "com.wangjin.coupon.CouponInfo.dg1Edit",
				function() {
					var response = Server.getResponse();
					
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
						}
				});
	}

	// 删除
	function del() {
		var dt = DataGrid.getSelectedData("dg1");
		if (dt.getRowCount() == 0) {
			Dialog.alert("请先选择要删除的行！");
			return;
		}
		var dc = new DataCollection();
		dc.add("DT", dt);
		Dialog.confirm("确认要删除？", function() {
			Server.sendRequest("com.wangjin.coupon.CouponInfo.del", dc, function(
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
						<label>优惠劵面值折扣权限</label>
					</legend>
					<form id="form1">
						<table>
							<tr>
								<td height="35" align="right" bordercolor="#eeeeee"
									class="tdgrey1">用户名：</td>
								<td><input value="" type="text" id="name" name="name"
									ztype="String" class="inputText" size="20" /></td>
							</tr>
						</table>
					</form>
				</fieldset>
				<table>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table> <z:tButtonPurview>${_Coupon_CouponVarPermission_Button}</z:tButtonPurview>
			</td>
		</tr>
		<tr>
			<td
				style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
				<z:datagrid id="dg1"
					method="com.wangjin.coupon.CouponInfo.dgPermDataBind" size="10"
					scroll="true">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr ztype="head" class="dataTableHead">
							<td width="3%" ztype="RowNo"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16">
							</td>
							<td width="3%" ztype="Selector" field="id">&nbsp;</td>
							<td width="12%"><strong>用户名</strong>
							</td>
							<td width="10%"><strong>优惠券折扣</strong>
							</td>
							<td width="15%"><strong>备注</strong>
							</td>
							<td width="12%"><strong>创建时间</strong>
							</td>
							<td width="12%"><strong>创建者</strong>
							</td>
							<td width="12%"><strong>修改时间</strong>
							</td>
							<td width="12%"><strong>修改者</strong>
							</td>
						</tr>
						<tr style="background-color: #F9FBFC">
							<td width="3%">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${CodeValue}</td>
							<td>${CodeName}</td>
							<td>${Memo}</td>
							<td>${AddTime}</td>
							<td>${AddUser}</td>
							<td>${ModifyTime}</td>
							<td>${ModifyUser}</td>
						</tr>
						<tr ztype="edit" bgcolor="#E1F3FF">
							<td bgcolor="#E1F3FF">&nbsp;</td>
							<td>&nbsp;</td>
							<td>${CodeValue}</td>
							<td><input name="CodeName" type="text" id="CodeName"
								value="${CodeName}" size="15" />
							</td>
							<td><input name="Memo" type="text" id="Memo" value="${Memo}"
								size="20" />
							</td>
							<td>${AddTime}</td>
							<td>${AddUser}</td>
							<td>${ModifyTime}</td>
							<td>${ModifyUser}</td>
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