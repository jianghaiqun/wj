<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>新增公告</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../wwwroot/kxb/js/jquery-1.4.2.min.js""></script>
<script src="../Framework/Main.js"></script>
<script src="../template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
jQuery(function(){
	if (jQuery("#productID").val() == null || jQuery("#productID").val() == '') {
		jQuery("#proTr").hide();
	} else {
		jQuery("#proTr").show();
	}
	if (jQuery("#comCode").val() == null || jQuery("#comCode").val() == '') {
		jQuery("#comTr").hide();
	} else {
		jQuery("#comTr").show();
	}
});

function getcolor(ele) {
	var color = jQuery(ele).val();
	jQuery(ele).parent().next().children("input").css("color",color);
}
	function add() {
		var count = $("pubInfo_dginput").rows.length;
		if (count <= 3) {
			DataGrid.insertRow("pubInfo_dginput");
		} else {
			Dialog.alert("最多可设置三条公告!");
		}
	}
	function del() {
		var dg = $("pubInfo_dginput");
		var arr = dg.getSelectedRows();
		if (!arr || arr.length == 0) {
			Dialog.alert("请先选择要删除的列!");
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
		dc.add("productID", $("productID").value);
		dc.add("comCode", $("comCode").value);
		dc.add("Data", dt);
		if (Verify.hasError()) {
			return;
		}
		Server.sendRequest("com.sinosoft.cms.dataservice.PublicInform.save",
				dc, function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("pubInfo_dginput");
						}
					});
				});
	}

	function loadPubInfo() {
		DataGrid.setParam("pubInfo_dginput", Constant.PageIndex, 0);
		DataGrid.setParam("pubInfo_dginput", "ProductId", $V("ProductId"));
		DataGrid.loadData("pubInfo_dginput");
	}

	function getDataTable() {
		var dg = $("pubInfo_dginput");
		var dt = dg.DataSource;
		var names = [ "id", "InfoOrder", "StartDate", "EndDate", "ViewFlag",
				"Color", "Info"];
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
	<z:init method="com.sinosoft.cms.dataservice.PublicInform.init">
		<form id="form2">
			<table width="100%"  border="0">
				<tr>
					<td valign="middle">
						<table width="100%" height="50" align="center" cellpadding="2"
							cellspacing="0" border="0">
							<tr style="display: none" id="proTr">
								<td align="center">产品名称：${productName}</td>
							</tr>
							<tr style="display: none" id="comTr">
								<td align="center">保险公司名称：${comName}</td>
							</tr>
							<tr>
								<td align="center" height="10"><input type="hidden"
									id="productID" value="${productID}" /> <input type="hidden"
									id="comCode" value="${comCode}" />
								</td>
							</tr>
						</table></td>
				</tr>
			</table>
			<z:datagrid id="pubInfo_dginput"
				method="com.sinosoft.cms.dataservice.PublicInform.Infodg1DataBind" >
				<table width="1615px" cellpadding="2" cellspacing="0"
					class="dataTable" style="overflow-x:scroll">
					<tr ztype="head" class="dataTableHead">
						<td width="30px" ztype="RowNo" drag="true"><img
							src="../Framework/Images/icon_drag.gif" width="16" height="16">
						</td>
						<td width="35px"><strong>序号</strong>
						</td>
						<td width="135px"><strong>开始时间</strong>
						</td>
						<td width="135px"><strong>结束时间</strong>
						</td>
						<td width="70px"><strong>是否启用</strong>
						</td>
						<td width="70px"><strong>字体颜色</strong>
						</td>
						<td width="800px"><strong>公告内容</strong>
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
						<td align="left"><input name="InfoOrder" type="text"
							id="InfoOrder" value="${InfoOrder}" verify="公告序号|NotNull&&Int"
							style="width: 20px;"/>
						</td>
						<td align="left"><input name="StartDate" type="text"
							id="StartDate" value="${StartDate}" verify="开始时间|NotNull"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							style="width: 120px;"/>
						</td>
						<td align="left"><input name="EndDate" type="text"
							id="EndDate" value="${EndDate}" verify="结束时间|NotNull"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d'})"
							style="width: 120px;"/>
						</td>
						<td align="left"><z:select name="ViewFlag"
								style="width:48px;" value="${ViewFlag}" verify="是否启用|NotNull">${@ViewFlag}</z:select>
						</td>
						<td align="left"><z:select name="Color" onChange="getcolor(this)" style="width:48px;"
								value="${Color}" verify="字体颜色|NotNull">${@Color}</z:select>
						</td>
						<td align="left"><input name="Info" type="text" 
						     id="Info" value="${Info}" verify="公告内容|NotNull" maxlength="150" style="width:780px;color:${Color}"/>
						</td>
						<td>${CreateDate}</td>
						<td>${CreateUser}<input name="id" id="id" type="hidden" value="${id}" /></td>
						<td>${ModifyDate}</td>
						<td>${ModifyUser}</td>
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
