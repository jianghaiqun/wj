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
	var dc = new DataCollection();
	//获取模板集合
	Server.sendRequest("com.wangjin.pointsMall.GiftManage.initTemplateName",dc,function(response){
		jQuery("#goodsTemplateName").val(response.get("TemplateName"));
	});
	$("dg1").afterEdit = function(tr,dr){
		dr.set("OrderFlag",$V("orderFlag"));
		return true;
	}
});

/**
 * 查询
 */
function sear() {
	DataGrid.setParam("dg1", Constant.PageIndex, 0);
	DataGrid.setParam("dg1", "giftTitle", $V("giftTitle"));
	DataGrid.setParam("dg1", "type", $V("type"));
	DataGrid.setParam("dg1", "CreateUser", $V("createUser"));
	DataGrid.setParam("dg1", "ModifyUser", $V("modifyUser"));
	DataGrid.setParam("dg1", "createStartDate", $V("createStartDate"));
	DataGrid.setParam("dg1", "createEndDate", $V("createEndDate"));
	DataGrid.setParam("dg1", "isWap", $V("isWap"));
	DataGrid.loadData("dg1");
}

/**
 * 新建礼品
 */
function addGift() {
	$S("GiftStatusFlag","add");
	var diag = new Dialog("Diag1");
	diag.Width = 970;
	diag.Height = 580;
	diag.Title = "新增礼品";
	diag.URL = "PointsMall/GiftDialog.jsp?GiftDialogStatusFlag=add";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveGift;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新增礼品";
	diag.show();
}
/**
 * 编辑礼品
 */
function editGift() {
	$S("GiftStatusFlag","edit");
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要修改的礼品！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能修改一个礼品！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 970;
	diag.Height = 580;
	diag.Title = "编辑礼品";
	diag.URL = "PointsMall/GiftDialog.jsp?ID=" + dr.get("ID")+"&GiftDialogStatusFlag=edit";
	diag.onLoad = function() {
	};
	diag.OKEvent = saveGift;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "编辑礼品";
	diag.show();
}
/**
 * 礼品的保存
 */
function saveGift() {
	if($DW.Verify.hasError()){
		return;
	}
	var dc = $DW.Form.getData("form2");
	if($DW.checkImgPath()){
		dc.add("ImgPath",$DW.$NV("ImgPath").join("^"));
	}else{
		Dialog.alert("请为礼品选择展示图片");
		return;
	}
	if (dc.get("Type") != '1') {
		if($DW.getMetaDescription()==null||$DW.getMetaDescription()==''){
			Dialog.alert("请填写温馨提示");
			return;
		}
		dc.add("MetaDescription",$DW.getMetaDescription());
	}
	
	dc.add("GiftStatusFlag", $V("GiftStatusFlag"));
	Server.sendRequest("com.wangjin.pointsMall.GiftManage.saveGift",dc,function(response){
		if(response.Status==1){
			Dialog.alert(
						response.Message,function(){
							$D.close();DataGrid.loadData("dg1");
						}
					);
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}
/**
 * 查看兑换记录
 */
function searchStockRecords() {
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	var PointsExchangeType=DataGrid.getSelectedValueByColumn("dg1","pointsExchangeType");
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要查看的礼品！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能查看一个礼品的兑换记录！");
		return;
	}
	if(PointsExchangeType == 2){
		Dialog.alert("福禄模式无库存记录！");
		return;
	}
	var dr=drs[0];
	var arr = DataGrid.getSelectedValue("dg1");
	var dc = new DataCollection();
	//获取类别TYPE
	var TYPE=DataGrid.getSelectedValueByColumn("dg1","tYPE");
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 350;
	diag.Title = "兑换记录";
	diag.URL = "PointsMall/StockRecord/PointsStockRecord0"+TYPE+".jsp?ID="+dr.get("ID");
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "兑换记录";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
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
/**
 * 暂停
 */
function PauseGift(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要暂停的礼品！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能暂停一个礼品！");
		return;
	}
	var status=DataGrid.getSelectedValueByColumn("dg1","status");
	if(status!="正常"){
		Dialog.alert("只有状态为'正常'的礼品才可以暂停！");
		return;
	}
	var dr=drs[0];
	var arr = DataGrid.getSelectedValue("dg1");
	var dc = new DataCollection();
	dc.add("ID", dr.get("ID"));
	Server.sendRequest("com.wangjin.pointsMall.GiftManage.PauseGift", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData("dg1");
						$D.close();
					}
				});
			});
}
/**
 * 恢复
 */
function RecoveryGift(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要恢复的礼品！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能恢复一个礼品！");
		return;
	}
	var status=DataGrid.getSelectedValueByColumn("dg1","status");
	if(status!="暂停"){
		Dialog.alert("只有状态为'暂停'的礼品才可以恢复！");
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	var dc = new DataCollection();
	dc.add("ID", dr.get("ID"));
	Server.sendRequest("com.wangjin.pointsMall.GiftManage.RecoveryGift", dc,
			function(response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						DataGrid.loadData("dg1");
						$D.close();
					}
				});
			});
}
/**
 * 删除
 */
function dell(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要删除的礼品！");
		return;
	}
	var dr=drs[0];
	if (drs.length > 1) {
		Dialog.alert("一次只能删除一个礼品！");
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	var dc = new DataCollection();
	dc.add("ID", dr.get("ID"));
	Dialog.confirm("该礼品有可能已关联库存信息，删除后不可恢复，确认要删除？",function() { 
		Dialog.wait("正在删除礼品......"); 
		Server.sendRequest("com.wangjin.pointsMall.GiftManage.dell", dc, function() {
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
/**
 * 上传库存
 */
function uploadRecordsData(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	var PointsExchangeType=DataGrid.getSelectedValueByColumn("dg1","pointsExchangeType");
	if (!drs || drs.length == 0) {
		Dialog.alert("请先选择要指定上传的礼品！");
		return;
	}
	if (drs.length > 1) {
		Dialog.alert("一次只能上传一个礼品对应的库存！");
		return;
	}
	//获取类别TYPE
	var TYPE=DataGrid.getSelectedValueByColumn("dg1","tYPE");
	var goodsTemplateName=eval("("+jQuery("#goodsTemplateName").val()+")");//转换为json对象 
	var flag="false";
	var TemplateName="";
    jQuery.each(goodsTemplateName, function (key, value) {
    	if(key==TYPE){
    		flag="true";
    		TemplateName=value;
    		return false;
    	}
    });  
	if(flag=="false"||PointsExchangeType == 2){
		Dialog.alert("该礼品无需上传库存信息！");
		return;
	}
	var dr=drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 150;
	diag.Title = "库存信息导入";
	diag.URL = "PointsMall/UploadRecordsDialog.jsp?GiftID="+dr.get("ID")+"&TemplateName="+TemplateName+"&TYPE="+TYPE;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
}

function sortGiftSave(){	
	DataGrid.save("dg1","com.wangjin.pointsMall.GiftManage.sortGift",function(){			
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
	});
}

function FuLuLoad() {
	//$S("GiftStatusFlag","showtree");
	var diag = new Dialog("Diag1");
	diag.Width = 970;
	diag.Height = 580;
	diag.Title = "福禄商品查询";
	diag.URL = "PointsMall/FuLuQuery.jsp?GiftDialogStatusFlag=showtree";
//	diag.onLoad = function() {
//	};
	//diag.OKEvent = saveGift;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "福禄商品查询";
	//diag.OKButton.hide();
	//diag.CancelButton.value = "关  闭";
	diag.show();
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
							<legend> <label>礼品管理</label></legend> 
							<z:init method="com.wangjin.pointsMall.GiftManage.init">
								<input value="" id="goodsTemplateName" type="hidden" >
								<form id="form1" >
									<table>
										<tr>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">礼品标题：</td>
											<td><input value="" type="text" id="giftTitle" name="giftTitle" ztype="String" class="inputText" size="20"></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">种类：</td>
											<td><z:select id="type" style="width:117px">${Type}</z:select></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">创建人&nbsp;&nbsp;：&nbsp;</td>
											<td><input value="" type="text" id="createUser" name="createUser"  ztype="String" class="inputText" size="20"></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">修改人&nbsp;&nbsp;：&nbsp;</td>
											<td><input value="" type="text" id="modifyUser" name="modifyUser"  ztype="String" class="inputText" size="20"></td>
										</tr>
									</table>
									<table>
										<tr>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">创建开始时间：</td>
											<td><input name="createStartDate" id="createStartDate" value="" type="text" size="14" ztype="Date" /></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">创建结束时间：</td>
											<td><input name="createEndDate" id="createEndDate" value="" type="text" size="14" ztype="Date" /></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">wap站是否显示：</td>
											<td><z:select style="width:60px;" id="isWap" >${YesOrNo}</z:select></td>
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
							 <z:tbutton onClick="sear()"><img src="../Icons/icon021a2.gif" width="20" height="20" />查询</z:tbutton>
							 <z:tbutton onClick="addGift()"><img src="../Icons/icon021a2.gif" width="20" height="20" />增加</z:tbutton>
							 <z:tbutton onClick="editGift()"><img src="../Icons/icon021a2.gif" width="20" height="20" />修改</z:tbutton>
							 <z:tbutton onClick="dell()"><img src="../Icons/icon021a2.gif" width="20" height="20" />删除</z:tbutton>
							 <z:tbutton onClick="uploadRecordsData()"><img src="../Icons/icon021a2.gif" width="20" height="20" />上传相应库存</z:tbutton>
							 <z:tbutton onClick="searchStockRecords()"><img src="../Icons/icon021a2.gif" width="20" height="20" />查看库存记录</z:tbutton>
							 <z:tbutton onClick="PauseGift()"><img src="../Icons/icon021a2.gif" width="20" height="20" />停用</z:tbutton>
							 <z:tbutton onClick="RecoveryGift()"><img src="../Icons/icon021a2.gif" width="20" height="20" />恢复</z:tbutton>
							 <z:tbutton onClick="sortGiftSave()"><img src="../Icons/icon021a2.gif" width="20" height="20" />排序保存</z:tbutton>
							 <z:tbutton onClick="FuLuLoad()"><img src="../Icons/icon021a2.gif" width="20" height="20" />福禄商品查询</z:tbutton>
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
									<z:datagrid id="dg1" method="com.wangjin.pointsMall.GiftManage.dg1DataBind" size="10" autoFill="true"  scroll="true" lazy="false" multiSelect="true">
										<table width="120%" cellpadding="2" cellspacing="0"
											class="dataTable" afterdrag="afterRowDragEnd" 
											style="table-layout: fixed" fixedHeight="343px">
											<tr ztype="head" class="dataTableHead">
												<td width="30" height="30" ztype="RowNo" drag="true">
												<img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
												<td width="30" height="30" ztype="Selector" field="id">&nbsp;</td>
												<td width="130"><strong>礼品标题</strong></td>
												<td width="50px;"><strong>种类</strong></td>
												<td width="230px;"><strong>归属模块</strong></td>
												<td width="50px;"><strong>是否推荐</strong></td>
												<td width="60px;"><strong>积分兑换值</strong></td>
												<td width="40px;"><strong>库存</strong></td>
												<td width="50px;"><strong>状态</strong></td>
												<td width="80px;"><strong>wap站是否显示</strong></td>
												<td width="60px;"><strong>创建人</strong></td>
												<td width="100px;"><strong>创建时间</strong></td>
												<td width="100px;"><strong>修改人</strong></td>
												<td width="100px;"><strong>修改时间</strong></td>
												<td width="100px"><strong>排序</strong></td>
											</tr>
											<tr style="background-color: #F9FBFC">
												<td onmouseover="">&nbsp;</td>
												<td>&nbsp;</td>
												<td onmouseover="showMessage(this)">${giftTitle}</td>
												<td onmouseover="showMessage(this)">${TYPENAME}</td>
												<td onmouseover="showMessage(this)">${ModelType}</td>
												<td onmouseover="showMessage(this)">${recommendname}</td>
												<td onmouseover="showMessage(this)">${points}</td>
												<td onmouseover="showMessage(this)">${lastNum}</td>
												<td onmouseover="showMessage(this)">${status}</td>
												<td onmouseover="showMessage(this)">${isWapName}</td>
												<td onmouseover="showMessage(this)">${createUser}</td>
												<td onmouseover="showMessage(this)">${createDate}</td>
												<td onmouseover="showMessage(this)">${modifyUser}</td>
												<td onmouseover="showMessage(this)">${modifyDate}</td>
												<td >${orderFlag}</td>
												<td  style="display: none;" >${tYPE}</td>
												<td  style="display: none;" >${pointsExchangeType}</td>
											</tr>
											<tr ztype="edit" bgcolor="#E1F3FF">
												<td bgcolor="#E1F3FF">&nbsp;</td>
												<td>&nbsp;</td>
												<td>${giftName}</td>
												<td>${TYPENAME}</td>
												<td>${ModelType}</td>
												<td>${recommendname}</td>
												<td>${points}</td>
												<td>${lastNum}</td>
												<td>${status}</td>
												<td>${isWapName}</td>
												<td>${createUser}</td>
												<td>${createDate}</td>
												<td>${modifyUser}</td>
												<td>${modifyDate}</td>
												<td><input name="orderFlag" type="text" id="orderFlag"
														value="${orderFlag}" size="15"></td>
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