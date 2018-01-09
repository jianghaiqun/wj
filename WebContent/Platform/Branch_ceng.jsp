<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>组织机构</title>
		<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
		<script src="../Framework/Main.js"></script>
		<script>
	Page.onLoad(function() {
		$("dg1").afterEdit = function(tr, dr) {
			dr.set("Name", $V("Name"));
			dr.set("Memo", $V("Memo"));
			return true;
		}
	});

	function sortBranch(type, targetDr, sourceDr, rowIndex, oldIndex) {
		if (rowIndex == oldIndex) {
			return;
		}

		var ds = $("dg1").DataSource;
		if (ds.get(rowIndex - 1, "BranchInnerCode").length == 4) {
			alert("您选择的是总机构，总机构不需要排序！");
			DataGrid.loadData("dg1");
			return;
		}

		if (rowIndex - 1 == 0) {
			alert("任何子机构都不能排在总机构前面");
			DataGrid.loadData("dg1");
			return;
		}

		var type = "";
		var orderBranch = "";
		var nextBranch = "";
		if (ds.get(rowIndex - 1, "ParentInnerCode") == ds.get(rowIndex,
				"ParentInnerCode")) {
			type = "Before";
			orderBranch = ds.get(rowIndex - 1, "BranchInnerCode");
			nextBranch = ds.get(rowIndex, "BranchInnerCode");
		} else if (ds.get(rowIndex - 1, "ParentInnerCode") == ds.get(
				rowIndex - 2, "ParentInnerCode")) {
			type = "After";
			orderBranch = ds.get(rowIndex - 1, "BranchInnerCode");
			nextBranch = ds.get(rowIndex - 2, "BranchInnerCode");
		} else {
			alert("不在同一机构下的子机构不能排序！");
			DataGrid.loadData("dg1");
			return;
		}
		var dc = new DataCollection();
		dc.add("OrderType", type);
		dc.add("OrderBranch", orderBranch);
		dc.add("NextBranch", nextBranch);
		DataGrid.showLoading("dg1");
		Server.sendRequest("com.sinosoft.platform.Branch_ceng.sortBranch", dc,
				function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg1");
						}
					});
				});
	}
</script>
	</head>
	<body>
		<span style="display: none;" id="siteid" name="siteid">{Site.ID}</span>
		<table width="100%" border="0" cellspacing="6" cellpadding="0"
			style="border-collapse: separate; border-spacing: 6px;">
			<tr valign="top">
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="6"
						class="blockTable">
						<tr>
							<td valign="middle" class="blockTd">
								<img src="../Icons/icon042a1.gif" width="20" height="20" />
								组织机构人员树
							</td>
						</tr>
						<tr>
							<td
								style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
								<!-- <z:datagrid id="dg1" method="com.sinosoft.platform.Branch_ceng.dg1DataBind"
					page="false"> -->
					<%
						
						//Branch_ceng.dg1DataBind_ceng()
					 %>

								<table width="100%" cellpadding="2" cellspacing="0"
									class="dataTable" afterdrag="sortBranch">
									<tr ztype="head" class="dataTableHead">
										<td width="4%" ztype="RowNo" drag="true">
											<img src="../Framework/Images/icon_drag.gif" width="16"
												height="16">
										</td>
										<td width="4%" ztype="selector" field="BranchInnerCode">
											&nbsp;
										</td>
										<td width="29%" ztype="tree" level="TreeLevel">
											<b>名称</b>
										</td>
										<td width="14%">
											<b>编码</b>
										</td>
										<td width="14%">
											<strong>机构主管</strong>
										</td>
										<td width="18%">
											<strong>电话</strong>
										</td>
										<td width="17%">
											<strong>传真</strong>
										</td>
									</tr>
									<tr onDblClick=
	
onclick=
	user();;
>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											${Name}
										</td>
										<td>
											${BranchCode}
										</td>
										<td>
											${ManagerName}
										</td>
										<td>
											${Phone}
										</td>
										<td>
											${Fax}
										</td>
									</tr>
								</table>
								<!--</z:datagrid>-->
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
