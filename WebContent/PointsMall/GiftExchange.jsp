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
function sear(index) {
	DataGrid.setParam("dg1", Constant.PageIndex, index);
	DataGrid.setParam("dg1", "giftTitle", $V("giftTitle"));
	DataGrid.setParam("dg1", "type", $V("type"));
	DataGrid.setParam("dg1", "fuLuGoodsID", $V("fuLuGoodsID"));
	DataGrid.setParam("dg1", "memberid", $V("memberid"));
	DataGrid.setParam("dg1", "createStartDate", $V("createStartDate"));
	DataGrid.setParam("dg1", "createEndDate", $V("createEndDate"));
	DataGrid.setParam("dg1", "orderSn", $V("orderSn"));
	DataGrid.setParam("dg1", "fuLuOrderStatus", $V("fuLuOrderStatus"));
	DataGrid.loadData("dg1");
}


	function update_FuLu() {
		$S("GiftStatusFlag","edit");
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请先选择要修改的订单！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("一次只能修改一个订单！");
			return;
		}
		dr = drs[0];
		if(dr.get("fuLuOrderStatus")!="失败"){
			Dialog.alert("只有福禄状态失败的订单才能修改！");
			return;
		}
		var diag = new Dialog("Diag1");
		diag.Width = 970;
		diag.Height = 280;
		diag.Title = "编辑福禄商品";
		diag.URL = "PointsMall/GiftExchangeDialog.jsp?ID=" + dr.get("ID")+"&GiftDialogStatusFlag=edit";
		diag.onLoad = function() {
		};
		diag.OKEvent = saveGift;
		diag.ShowMessageRow = true;
		diag.MessageTitle = "编辑福禄商品";
		diag.show();
	}
	
	function saveGift() {
		if($DW.Verify.hasError()){
			return;
		}
		var dc = $DW.Form.getData("form2");
		dc.add("GiftStatusFlag", $V("GiftStatusFlag"));
		Server.sendRequest("com.wangjin.pointsMall.EXGiftManage.saveExchangeGift",dc,function(response){
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
	function updateFuluOrdStatus() {
		var dt = DataGrid.getSelectedData("dg1");
		var drs = dt.Rows;
		if (!drs || drs.length == 0) {
			Dialog.alert("请选择一条数据！");
			return;
		}
		if (drs.length > 1) {
			Dialog.alert("请选择一条数据！");
			return;
		}
		var dr = dt.Rows[0];
		var dc = new DataCollection();
		dc.add("orderSn", dr.get("orderSn"));
		Dialog.wait("正在更新......"); 
		Server.sendRequest("com.wangjin.pointsMall.EXGiftManage.updateFuluOrderStatus",dc,function(response){
			Dialog.closeEx();
			if(response.Status==1){
				Dialog.alert(response.Message,function(){$D.close();});
				sear(DataGrid.getParam("dg1",Constant.PageIndex));
			}else{
				Dialog.alert(response.Message);
				return;
			}
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
							<legend> <label>礼品兑换管理</label></legend> 
							<z:init method="com.wangjin.pointsMall.EXGiftManage.init">
								<input value="" id="goodsTemplateName" type="hidden" >
								<form id="form1" >
									<table>
										<tr>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">礼品标题 </td>
											<td><input value="" type="text" id="giftTitle" name="giftTitle" ztype="String" class="inputText" size="20"></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">种类：</td>
											<td><z:select id="type" name="type" style="width:117px">${Type}</z:select></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">福禄商品编码&nbsp;&nbsp;：&nbsp;</td>
											<td><input value="" type="text" id="fuLuGoodsID" name="fuLuGoodsID"  ztype="String" class="inputText" size="20"></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">会员Id编码&nbsp;&nbsp;：&nbsp;</td>
											<td><input value="" type="text" id=memberid name="memberid"  ztype="String" class="inputText" size="20"></td>
										</tr>
									</table>
									<table>
										<tr>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">兑换日期：   从：</td>
											<td><input name="createStartDate" id="createStartDate" value="" type="text" size="14" ztype="Date" /></td>
											<td>&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">到：</td>
											<td><input name="createEndDate" id="createEndDate" value="" type="text" size="14" ztype="Date" /></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >订单号&nbsp;&nbsp;：&nbsp;</td>
											<td><input value="" type="text" id=orderSn name="orderSn" ztype="String" class="inputText" size="20" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
											<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
											<td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" >福禄订单状态&nbsp;&nbsp;：&nbsp;</td>
                       
                                            <td><z:select id="fuLuOrderStatus" name="fuLuOrderStatus"  style="width:117px">${fuLuOrderStatus}</z:select></td>
										    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
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
							 <z:tbutton onClick="sear(0)"><img src="../Icons/icon021a2.gif" width="20" height="20" />筛选</z:tbutton>
						     <z:tbutton onClick="update_FuLu()" id="update_FuLu_button"><img src="../Icons/icon021a2.gif" width="20" height="20" />二次充值</z:tbutton> 
						     <z:tbutton onClick="updateFuluOrdStatus();" id="update_FuLuOrder_button"><img src="../Icons/icon021a2.gif" width="20" height="20" />更新福禄订单状态</z:tbutton> 
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
									<z:datagrid id="dg1" method="com.wangjin.pointsMall.EXGiftManage.dg1DataBind" size="10" autoFill="true"  scroll="true" lazy="false" multiSelect="true">
										<table width="120%" cellpadding="2" cellspacing="0" 
											class="dataTable" afterdrag="afterRowDragEnd" 
											style="table-layout: fixed" fixedHeight="343px">
											<div>
											<tr ztype="head" class="dataTableHead">
												<td width="30" height="30" ztype="RowNo" drag="true">
												<img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
												<td width="30" height="30" ztype="Selector" field="id">&nbsp;</td>
												<td width="200"><strong>礼品标题</strong></td>
												<td width="200"><strong>充值号码</strong></td>
												<td width="80px;"><strong>种类</strong></td>
												<td width="130px;"><strong>订单号</strong></td>
												<td width="80px;"><strong>订单状态</strong></td>
												<td width="90px;"><strong>积分兑换值</strong></td>
												<td width="80px;"><strong>兑换状态</strong></td>
												<td width="130px;"><strong>福禄订单号</strong></td>
												<td width="130px;"><strong>福禄商品编号</strong></td>
												<td width="130px;"><strong>兑换数量</strong></td>
												<td width="130px;"><strong>卡号</strong></td>
												<td width="130px;"><strong>卡密</strong></td>
												<td width="130px;"><strong>福禄订单状态</strong></td>
												<td width="130px;"><strong>充值号码</strong></td>
												<td width="300px;"><strong>会员id</strong></td>
												<td width="230px;"><strong>创建时间</strong></td>
												<td width="230px;"><strong>修改时间</strong></td>
												<td width="230px;"><strong>渠道</strong></td>
											</tr>
											</div>
											<div>
											<tr style="background-color: #F9FBFC">
												<td onmouseover="">&nbsp;</td>
												<td>&nbsp;</td>
												<td onmouseover="showMessage(this)">${gifttitle}</td>
												<td onmouseover="showMessage(this)">${mobileNo}</td>
												<td onmouseover="showMessage(this)">${TYPE}</td>
												<td onmouseover="showMessage(this)">${orderSn}</td>
												<td onmouseover="showMessage(this)">&nbsp;${status}</td>
												<td onmouseover="showMessage(this)">&nbsp;${points}</td>
												<td onmouseover="showMessage(this)">&nbsp;${status}</td>
												<td onmouseover="showMessage(this)">&nbsp;${fuLuOrderSn}</td>
												<td onmouseover="showMessage(this)">&nbsp;${fuLuGoodsID}</td>
												<td onmouseover="showMessage(this)">&nbsp;${exchangeQuantity}</td>
												<td onmouseover="showMessage(this)">&nbsp;${cardNo}</td>
												<td onmouseover="showMessage(this)">&nbsp;${cardKey}</td>
												<td onmouseover="showMessage(this)">&nbsp;${fuLuOrderStatus}</td>
												<td onmouseover="showMessage(this)">${mobileNo}</td>
												<td onmouseover="showMessage(this)">${memberid}</td>
												<td onmouseover="showMessage(this)">${createDate}</td>
												<td onmouseover="showMessage(this)">${modifyDate}</td>
												<td onmouseover="showMessage(this)">${prop2}</td>
												<td  style="display: none;" >${tYPE}</td>
											</tr>
											</div>
											<%-- <tr ztype="edit" bgcolor="#E1F3FF">
												<td bgcolor="#E1F3FF">&nbsp;</td>
												<td>&nbsp;</td>
												<td>${giftName}</td>
												<td>${TYPENAME}</td>
												<td>${orderSn}</td>
												<td>${status}</td>
												<td>${recommendname}</td>
												<td>${points}</td>
												<td>${lastNum}</td>
												<td>${status}</td>
												<td>${createUser}</td>
												<td>${createDate}</td>
												<td>${modifyUser}</td>
												<td>${modifyDate}</td>
												<td><input name="orderFlag" type="text" id="orderFlag"
														value="${orderFlag}" size="15"></td>
											</tr> --%>
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