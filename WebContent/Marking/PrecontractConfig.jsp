<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预约信息配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function searchData(){
	DataGrid.setParam("dg2",Constant.PageIndex,0);
	DataGrid.setParam("dg2","couponBatch",$V("couponBatch"));
	DataGrid.setParam("dg2","stencilUrl",$V("stencilUrl"));
	DataGrid.loadData("dg2");
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 460;
	diag.Height = 250;
	diag.Title = "新增预约优惠券配置";
	diag.URL = "Marking/PrecontractConfigAdd.jsp";
	diag.onLoad = function(){
		$DW.$("StencilUrl").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	Server.sendRequest("com.sinosoft.cms.dataservice.PrecontractInfo.addConfig",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData('dg2');
				$D.close();
			}
		});
	});
}

function del() {
	var dt = DataGrid.getSelectedData("dg2");
	if (dt.getRowCount() == 0) {
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("DT", dt);
	Dialog.confirm("确认要删除？", function() {
		Server.sendRequest("com.sinosoft.cms.dataservice.PrecontractInfo.delConfig", dc, function(response) {
			Dialog.alert(response.Message, function() {
				if (response.Status == 1) {
					DataGrid.loadData('dg2');
				}
			});
		});
	});
}
</script>
</head>
<body >
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
          <tr>
				<td style="padding:10px 10px;">
					<div style="float:left;"> 
			    		 优惠券批次号&nbsp;<input name="couponBatch" type="text" id="couponBatch"> &nbsp;
			    		 预约页面URL&nbsp;<input name="stencilUrl" type="text" id="stencilUrl"> &nbsp;
				    </div>
				</td>
			</tr>
			<tr>
				<td>
					<z:tbutton onClick="searchData();"><img src="../Icons/icon022a3.gif" width="20" height="20"/>查询</z:tbutton>
					<z:tbutton onClick="add();"><img src="../Icons/icon022a3.gif" width="20" height="20"/>新增优惠券配置</z:tbutton>
					<z:tbutton onClick="del();"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除优惠券配置</z:tbutton>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="dg2" method="com.sinosoft.cms.dataservice.PrecontractInfo.dg2DataBind"  page="true" size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="12%"><strong>预约页面URL</strong></td>
                  <td width="18%"><strong>优惠券批次号</strong></td>
                  <td width="10%"><strong>初始预约人数</strong></td>
                  <td width="30%"><strong>备注</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${StencilUrl} </td>
                  <td>${CouponBatch}</td>
                  <td>${Prop1}</td>
                  <td>${Remark}</td>
                </tr>
                <tr ztype="pagebar">
					<td colspan="6" align="left">${PageBar}</td>
				</tr>
              </table>
            </z:datagrid></td>
          </tr>
      </table></td>
    </tr>
  </table>
</body>
</html>
