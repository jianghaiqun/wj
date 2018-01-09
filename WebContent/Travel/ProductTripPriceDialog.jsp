<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>新增公告</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../wwwroot/kxb/js/jquery-1.4.2.min.js"></script>
<script src="../Framework/Main.js"></script>
<script src="../template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">

	function add() {
		DataGrid.insertRow("dg3");
	}
	
	function del() {
		var dg = $("dg3");
		var arr = dg.getSelectedRows();
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的数据!");
			return;
		}
		Dialog.confirm("确认删除？", function() {
			for ( var i = arr.length - 1; i >= 0; i--) {
				dg.deleteRow(arr[i].rowIndex);
			}
		});

	}
	function save() {
		var dc = Form.getData("form2");
		var dt = getDataTable();
		dc.add("productId", $("productId").value);
		dc.add("Data", dt);
		if (Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.sinosoft.cms.travel.ProductManage.saveTripPrice",
				dc, function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("dg3");
						}
					});
				});
	}

	function getDataTable() {
		var dg = $("dg3");
		var dt = dg.DataSource;
		var names = [ "id", "travelDate", "price", "storeNum"];
		var map = {};
		for ( var i = 0; i < names.length; i++) {
			var eles = $N(names[i]);
			var arr = [];
			if (eles != null) {
				for ( var j = 0; j < eles.length; j++) {
					arr.push($V(eles[j]));
				}
			}
			map[names[i]] = arr;
		}
		for ( var i = 0; i < dt.getRowCount(); i++) {
			for ( var j = 0; j < names.length; j++) {
				dt.Rows[i].set(names[j], map[names[j]][i]);
			}
		}
		return dt;
	}
	

</script>
</head>
<body class="dialogBody">
	<z:init>
		<form id="form2">
			<table width="100%"  border="0">
				<tr>
					<td valign="middle">
						<table width="100%" height="50" align="center" cellpadding="2"
							cellspacing="0" border="0">
							<tr >
								<td align="center">产品名称：<span id="productName"></span><input type="hidden"
									id="productId" value="${productId}" /></td>
							</tr>
						</table></td>
				</tr>
			</table>
			<z:datagrid id="dg3"
				method="com.sinosoft.cms.travel.ProductManage.dg3DataBind" >
				<table width="99%" cellpadding="2" cellspacing="0"
					class="dataTable" align="center">
					<tr ztype="head" class="dataTableHead">
						<td width="30px" ztype="RowNo" drag="true"><img
							src="../Framework/Images/icon_drag.gif" width="16" height="16">
						</td>
						<td width="135px"><strong>出发时间</strong>
						</td>
						<td width="100px"><strong>单价</strong>
						</td>
						<td width="100px"><strong>库存</strong>
						</td>
						<td width="120px"><strong>创建时间</strong>
						</td>
						<td width="60px"><strong>创建者</strong>
						</td>
						<td width="120px"><strong>修改时间</strong>
						</td>
						<td width="60px"><strong>修改者</strong>
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
						<td align="left"><input name="travelDate" type="text"
							id="travelDate" value="${travelDate}" verify="出发时间|NotNull" 
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 120px;"/>
						</td>
						<td align="left"><input name="price" type="text"
							id="price" value="${price}" verify="单价|NotNull&&Number"
							style="width: 80px;"/>
						</td>
						<td align="left"><input name="storeNum" type="text"
							id="EndDate" value="${storeNum}" verify="库存|NotNull&&Int"
							style="width: 80px;"/>
						</td>
						<td>${createDate}</td>
						<td>${createUser}<input name="id" id="id" type="hidden" value="${id}" /></td>
						<td>${modifyDate}</td>
						<td>${modifyUser}</td>
					</tr>
				</table>
			</z:datagrid>
			<z:tbutton onClick="add();">
				<img src="../Icons/icon005a2.gif" width="20" height="20" />添加</z:tbutton>
			<z:tbutton onClick="del();">
				<img src="../Icons/icon005a4.gif" width="20" height="20" />删除</z:tbutton>
			<z:tbutton onClick="save();">
				<img src="../Icons/icon005a4.gif" width="20" height="20" />保存</z:tbutton>
		</form>
	</z:init>
</body>
</html>
