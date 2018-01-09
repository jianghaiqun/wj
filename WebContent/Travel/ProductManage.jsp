<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){

	$("dg1").afterEdit = function(tr,dr){
		dr.set("sortNum",$V("sortNum"));
		return true;
	}
});

/**
 * 查询
 */
function sear() {
	DataGrid.setParam("dg1", Constant.PageIndex, 0);
	DataGrid.setParam("dg1", "productName", $V("productName"));
	DataGrid.setParam("dg1", "productType", $V("productType"));
	DataGrid.setParam("dg1", "state", $V("state"));
	DataGrid.setParam("dg1", "moduleType", $V("moduleType"));
	DataGrid.setParam("dg1", "birthland", $V("birthland"));
	DataGrid.setParam("dg1", "destination", $V("destination"));
	DataGrid.loadData("dg1");
}

function addProduct() {
	var diag = new Dialog("Diag1");
	diag.Width = 970;
	diag.Height = 600;
	diag.Title = "新增产品";
	diag.URL = "Travel/ProductDialog.jsp?operation=add";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveProduct;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新增产品";
	diag.show();
}

function editProduct() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的产品！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能修改一个产品！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 970;
	diag.Height = 600;
	diag.Title = "编辑产品";
	diag.URL = "Travel/ProductDialog.jsp?id=" + dr.get("id")+"&operation=edit";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveProduct;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑产品";
	diag.show();
}

function saveProduct() {
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	dc.add("productDesc",$DW.getProductDesc());
	Server.sendRequest("com.sinosoft.cms.travel.ProductManage.saveProduct",dc,function(response){
		if(response.Status==1){
			Dialog.alert(
						response.Message,function(){
							$D.close();sear();
						}
					);
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function online() {
	var arr = DataGrid.getSelectedValue("dg1");
	if (arr == null || arr.length == 0) {
		Dialog.alert("请选择要操作的条目！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs", arr.join());
	Dialog.confirm("您确认要上架吗？", function() {
	Server.sendRequest("com.sinosoft.cms.travel.ProductManage.online", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						sear();
					} 
						
				});
			});
	});
}

function dell(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要删除的产品！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能删除一个产品！");
		return;
	}
	var dc = new DataCollection();
	dc.add("id", dr.get("id"));
	Dialog.confirm("该产品删除后不可恢复，确认要删除？",function() { 
		Dialog.wait("正在删除产品......"); 
		Server.sendRequest("com.sinosoft.cms.travel.ProductManage.dell", dc, function() {
			Dialog.endWait();
			var response = Server.getResponse();
			Dialog.alert(response.Message, function() {
				if (response.Status == 1) {
					sear();
					$D.close();
				}
			});
		});
	});
}

function setProductImage(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要设置图片的产品！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能设置一个产品！");
		return;
	}
	
	var diag = new Dialog("Diag2");
	diag.Width = 650;
	diag.Height = 500;
	diag.Title = "设置产品图片";
	diag.URL = "Travel/ProductImageDialog.jsp?productId=" + dr.get("id");
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "设置产品图片";
	diag.show();
	diag.OKButton.hide();
}

function setTrip() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要设置的产品！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("只选择一个产品进行设置！");
		return;
	}

	var diag = new Dialog("Diag3");
	diag.Width = 900;
	diag.Height = 400;
	diag.Title = "设置行程价格";
	diag.URL = "Travel/ProductTripPriceDialog.jsp?productId="+dr.get("id");
	diag.onLoad = function() {
		$DW.$("productName").innerHTML = dr.get("productName");
	};
	//diag.URL = encodeURI(url);
	diag.ShowMessageRow = true;
	diag.MessageTitle = "设置行程价格";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function setLabel(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要设置标签的产品！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能设置一个产品！");
		return;
	}
	
	var diag = new Dialog("Diag2");
	diag.Width = 300;
	diag.Height = 200;
	diag.Title = "设置产品标签";
	diag.URL = "Travel/ProductLabelDialog.jsp?productId=" + dr.get("id");
	diag.onLoad = function() {
	};
	diag.OKEvent = saveProductLabel;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "设置产品标签";
	diag.show();
}

function saveProductLabel() {
	var dc = $DW.Form.getData("form4");

	var index = $DW.$V("index");
	var index_array=index.split(",");
	var DetailNum = $DW.$V("DetailNum");
	for(var i=0;i<parseInt(DetailNum);i++){
		var Detail1 = $DW.$V("Detail_"+index_array[i]);
		if(Detail1==null||Detail1==''){
			Dialog.alert("第"+(i+1)+"行的标签没有填写！");
			return;
		}
	}
	
	Server.sendRequest("com.sinosoft.cms.travel.ProductManage.setProductLabel",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function downline() {
	var arr = DataGrid.getSelectedValue("dg1");
	if (arr == null || arr.length == 0) {
		Dialog.alert("请选择要操作的条目！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs", arr.join());
	Dialog.confirm("您确认要下架吗？", function() {
	Server.sendRequest("com.sinosoft.cms.travel.ProductManage.downline", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						sear();
					} 
						
				});
			});
	});
}

function sortSave(){	
	DataGrid.save("dg1","com.sinosoft.cms.travel.ProductManage.sortSave",function(){			
		sear();
	});
}
//展示浮动框
function showMessage(t){
	var innertext=t.innerHTML;
	if(innertext.indexOf("&nbsp;")!=-1){
		innertext=innertext.replace(new RegExp("&nbsp;", 'g'),"");
	}
	if(innertext==""||innertext==null){
		t.title="";
	}else{
		t.title=t.innerHTML;
	}
}
</script>
</head>
<body>
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	<tr>
		<td width="50%" valign="top">
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" >
				<tr>
					<td valign="top">
						<fieldset>
							<legend> <label>产品管理</label></legend> 
							<z:init method="com.sinosoft.cms.travel.ProductManage.init">
								<input value="" id="goodsTemplateName" type="hidden" >
								<form id="form1" >
									<table>
										<tr>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">产品名：</td>
											<td><input value="" type="text" id="productName" name="productName" ztype="String" class="inputText" size="20"></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">产品类型：</td>
											<td><z:select id="productType" style="width:117px">${productType}</z:select></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">上架状态：</td>
											<td><z:select id="state" style="width:117px">${state}</z:select></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">归属模块：</td>
											<td><z:select id="moduleType" style="width:117px">${moduleType}</z:select></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">出发地：</td>
											<td><input value="" type="text" id="birthland" name="birthland"  ztype="String" class="inputText" size="20"></td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">目的地：</td>
											<td><input value="" type="text" id="destination" name="destination"  ztype="String" class="inputText" size="20"></td>
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
							 <z:tbutton onClick="sear();"><img src="../Icons/icon021a2.gif" width="20" height="20" />查询</z:tbutton>
							 <z:tbutton onClick="addProduct();"><img src="../Icons/icon021a2.gif" width="20" height="20" />增加</z:tbutton>
							 <z:tbutton onClick="editProduct();"><img src="../Icons/icon021a2.gif" width="20" height="20" />修改</z:tbutton>
							 <z:tbutton onClick="dell();"><img src="../Icons/icon021a2.gif" width="20" height="20" />删除</z:tbutton>
							 <z:tbutton onClick="setProductImage();"><img src="../Icons/icon021a2.gif" width="20" height="20" />设置图片</z:tbutton>
							 <z:tbutton onClick="setLabel();"><img src="../Icons/icon021a2.gif" width="20" height="20" />设置标签</z:tbutton>
							 <z:tbutton onClick="setTrip();"><img src="../Icons/icon021a2.gif" width="20" height="20" />设置行程价格信息</z:tbutton>
							 <z:tbutton onClick="online();"><img src="../Icons/icon021a2.gif" width="20" height="20" />上架</z:tbutton>
							 <z:tbutton onClick="downline();"><img src="../Icons/icon021a2.gif" width="20" height="20" />下架</z:tbutton>
							 <z:tbutton onClick="sortSave();"><img src="../Icons/icon021a2.gif" width="20" height="20" />排序保存</z:tbutton>
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
									<z:datagrid id="dg1" method="com.sinosoft.cms.travel.ProductManage.dg1DataBind" size="10" autoFill="true"  scroll="true" lazy="false" multiSelect="true">
										<table width="140%" cellpadding="2" cellspacing="0"
											class="dataTable" afterdrag="afterRowDragEnd" 
											style="table-layout: fixed" fixedHeight="343px">
											<tr ztype="head" class="dataTableHead">
												<td width="30px" height="30" ztype="RowNo" drag="true">
												<img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
												<td width="30px" height="30" ztype="Selector" field="id">&nbsp;</td>
												<td width="150px"><strong>产品名</strong></td>
												<td width="100px"><strong>产品简称</strong></td>
												<td width="120px;"><strong>产品有效起期</strong></td>
												<td width="120px;"><strong>产品有效止期</strong></td>
												<td width="60px;"><strong>产品类型</strong></td>
												<td width="80px;"><strong>归属模块</strong></td>
												<td width="60px;"><strong>产品分类</strong></td>
												<td width="60px;"><strong>是否上架</strong></td>
												<td width="80px;"><strong>提前预定天数</strong></td>
												<td width="80px;"><strong>出发地</strong></td>
												<td width="80px;"><strong>目的地</strong></td>
												<td width="100px"><strong>排序</strong></td>
												<td width="130px;"><strong>温馨提示</strong></td>
												<td width="60px;"><strong>创建人</strong></td>
												<td width="120px;"><strong>创建时间</strong></td>
												<td width="60px;"><strong>修改人</strong></td>
												<td width="120px;"><strong>修改时间</strong></td>
												
											</tr>
											<tr style="background-color: #F9FBFC">
												<td onmouseover="">&nbsp;</td>
												<td>&nbsp;</td>
												<td onmouseover="showMessage(this)">${productName}</td>
												<td onmouseover="showMessage(this)">${shortName}</td>
												<td onmouseover="showMessage(this)">${startDate}</td>
												<td onmouseover="showMessage(this)">${endDate}</td>
												<td onmouseover="showMessage(this)">${productTypeName}</td>
												<td onmouseover="showMessage(this)">${moduleTypeName}</td>
												<td onmouseover="showMessage(this)">${classifyName}</td>
												<td onmouseover="showMessage(this)">${stateName}</td>
												<td onmouseover="showMessage(this)">${reserveDay}</td>
												<td onmouseover="showMessage(this)">${birthland}</td>
												<td onmouseover="showMessage(this)">${destination}</td>
												<td onmouseover="showMessage(this)">${sortNum}</td>
												<td onmouseover="showMessage(this)">${prompt}</td>
												<td onmouseover="showMessage(this)">${createUser}</td>
												<td onmouseover="showMessage(this)">${createDate}</td>
												<td onmouseover="showMessage(this)">${modifyUser}</td>
												<td onmouseover="showMessage(this)">${modifyDate}</td>
											</tr>
											<tr ztype="edit" bgcolor="#E1F3FF">
												<td bgcolor="#E1F3FF">&nbsp;</td>
												<td>&nbsp;</td>
												<td>${productName}</td>
												<td>${shortName}</td>
												<td>${startDate}</td>
												<td>${endDate}</td>
												<td>${productTypeName}</td>
												<td>${moduleTypeName}</td>
												<td>${classifyName}</td>
												<td>${stateName}</td>
												<td>${reserveDay}</td>
												<td>${birthland}</td>
												<td>${destination}</td>
												<td><input name="sortNum" type="text" id="sortNum"
														value="${sortNum}" size="15"></td>
												<td>${prompt}</td>
												<td>${createUser}</td>
												<td>${createDate}</td>
												<td>${modifyUser}</td>
												<td>${modifyDate}</td>
												
											</tr>
											<tr ztype="pagebar">
												<td colspan="19" align="left">${PageBar}</td>
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