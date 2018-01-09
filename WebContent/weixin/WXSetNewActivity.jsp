<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
	Page.onLoad(function() {
		
	});
	//根据活动类型判断需要录入的字段信息
	function ChooseGroupbuy() {
		var codevalue = jQuery("#type").val();
		if (codevalue == "001") {
			jQuery("#productIdTitle").show();
			jQuery("#productIdTd").show();
			jQuery("#productImageTitle").show();
			jQuery("#productImageTd").show();
			jQuery("#productNameTitle").show();
			jQuery("#productNameTd").show();
			jQuery("#productDescribeTitle").show();
			jQuery("#productDescribeTd").show();
			jQuery("#datagrid2").hide();
			jQuery("#addProductTitle").hide();
			jQuery("#addProductTd").hide();
			jQuery("#dg2").hide();
			jQuery("#gamePeriodTitle").show();
			jQuery("#gamePeriodTd").show();
		} else if (codevalue == "002") {
			jQuery("#productIdTitle").hide();
			jQuery("#productIdTd").hide();
			jQuery("#productImageTitle").hide();
			jQuery("#productImageTd").hide();
			jQuery("#productNameTitle").hide();
			jQuery("#productNameTd").hide();
			jQuery("#productDescribeTitle").hide();
			jQuery("#productDescribeTd").hide();
			jQuery("#datagrid2").show();
			jQuery("#addProductTitle").show();
			jQuery("#addProductTd").show();
			jQuery("#dg2").show();
			jQuery("#gamePeriodTitle").hide();
			jQuery("#gamePeriodTd").hide();
		}
	}

	function addProduct() {
		DataGrid.insertRow("dg2");
	}

	function del() {
		var dg = $("dg2");
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
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	<z:init method="com.sinosoft.cms.document.WXActivity.initDialog2">
		<form id="form2">
			<table width="780" cellpadding="2" cellspacing="0" align="center">
				<tr>
					<td height="25" align="right" class="tdgrey1">活动名称：</td>
					<td height="25"><input name="name" type="text" id="name"
						value="" style="width: 150px;" verify="活动名称|NotNull"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">活动类型：</td>
					<td height="25"><z:select style="width:150px;" name="type"
							id="type" onChange="ChooseGroupbuy()" value="${codeValue}"
							verify="活动类型|NotNull">${codeName}</z:select></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">活动logo1：</td>
					<td height="25"><input name="activityLogo1" type="text"
						id="activityLogo1" value="" style="width: 150px"
						verify="活动logo1|NotNull"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">活动logo2：</td>
					<td height="25"><input name="activityLogo2" type="text"
						id="activityLogo2" value="" style="width: 150px"
						verify="活动logo2|NotNull"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">分享标题：</td>
					<td height="25"><input name="shareTitle" type="text"
						id="shareTitle" value="" style="width: 150px"
						verify="分享标题|NotNull"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">分享描述：</td>
					<td height="25"><input name="shareDescribe" type="text"
						id="shareDescribe" value="" style="width: 150px"
						verify="分享描述|NotNull"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right" id="addProductTitle">添加产品：</td>
					<td height="25" id="addProductTd"><z:tbutton
							onClick="addProduct();">
							<img src="../Icons/icon005a2.gif" height="20" />添加</z:tbutton>
					<z:tbutton	onClick="del();">
							<img src="../Icons/icon005a4.gif" height="20" />删除</z:tbutton>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr style="display: none;" id="datagrid2">
					<td></td>
					<td colspan="3"><z:datagrid id="dg2"
							method="com.sinosoft.cms.document.WXActivity.dg2DataBind"
							size="2" lazy="false">
							<table cellpadding="2" cellspacing="0" class="dataTable"
								align="left" height="10">
								<tr ztype="head" class="dataTableHead">
									<td width="30px" ztype="RowNo" drag="true"><img
										src="../Framework/Images/icon_drag.gif" width="16" height="16">
									</td>
									<td width="300px"><strong>产品图片</strong></td>
									<td width="300px"><strong>产品链接</strong></td>
								</tr>
								<tr style1="background-color:#FFFFFF"
									style2="background-color:#F9FBFC">
									<td align="center">&nbsp;</td>
									<td><input name="productImage" type="text"
										id="productImage" value="${productImage}" size="40"></td>
									<td><input name="productUrl" type="text"
									 id="productUrl" value="${productUrl}" size="40"></td>
								</tr>
								
							</table>
						</z:datagrid>
					</td>
				</tr>
				<tr>
					<td align="right" id="productIdTitle">参与产品：</td>
					<td height="25" id="productIdTd"><input name="productId"
						type="text" id="productId" value="" style="width: 150px"></td>
					<td align="right" id="productImageTitle">产品图片：</td>
					<td height="25" id="productImageTd"><input name="productImage"
						type="text" id="productImage" value="" style="width: 150px"></td>
				</tr>
				<tr>
					<td align="right" id="productNameTitle">产品名称：</td>
					<td height="25" id="productNameTd"><input name="productName"
						type="text" id="productName" value="" style="width: 150px"></td>
					<td align="right" id="productDescribeTitle">产品描述：</td>
					<td height="25" id="productDescribeTd"><input
						name="productDescribe" type="text" id="productDescribe" value=""
						style="width: 150px"></td>
				</tr>
				<tr>
					<td align="right">活动描述：</td>
					<td><textarea id="description" name="description" value=""
							style="width: 400px"></textarea></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">活动开始时间：</td>
					<td><input name=standardDateStart type="text"
						id="standardDateStart" value="" style="width: 120px"
						class="inputText" ztype="date" verify="开始时间|NotNull"> <input
						name="standardTimeStart" type="text" id="standardTimeStart"
						value="" style="width: 120px" class="inputText" ztype="time"
						verify="结束时间|NotNull"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">活动截止时间：</td>
					<td><input name="standardDateEnd" type="text"
						id="standardDateEnd" value="" style="width: 120px"
						class="inputText" ztype="date" verify="结束日期|NotNull"> <input
						name="standardTimeEnd" type="text" id="standardTimeEnd" value=""
						style="width: 120px" class="inputText" ztype="time"
						verify="结束时间|NotNull"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right" id="gamePeriodTitle">游戏时长：</td>
					<td height="25" id="gamePeriodTd"><input name="gamePeriod" type="text"
						id="gamePeriod" value="0" style="width: 150px"></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td align="right">活动规则：</td>
					<td><textarea id="ruleText" name="ruleText" value=""
							style="width: 400px" verify="活动数据|NotNull"></textarea></td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>
	</z:init>
</body>
</html>