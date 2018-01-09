<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.sinosoft.cms.document.WapSiteBidPageManage.initProductContent">
	<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>标题菜单设置</title>
	<script src="../Framework/Main.js"></script>
	<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		Page.onLoad(function(){
			$("con_dg1").afterEdit = function(tr,dr){
				dr.set("WapProductName",$V("WapProductName"));
				dr.set("OrderFlag",$V("OrderFlag"));
				return true;
			}
		});

		function add(){
			var diag = new Dialog("Diag3");
			diag.Width = 720;
			diag.Height = 450;
			diag.Title = "产品选择";
			diag.URL = "Document/WapBidProductContent_sel.jsp";
			diag.onLoad = function(){
				$DW.$("ProductID").focus();
			};
			diag.OKEvent = saveProduct;
			diag.show();
		}
		
		//保存选中产品
		function saveProduct(){
			
			var dc = $DW.Form.getData("form3");
			if($DW.Verify.hasError()){
				return;
			}
			
			var DT_Product = $DW.DataGrid.getSelectedData("product_dg1");
			var dc = new DataCollection();

			if(!DT_Product || DT_Product.getRowCount() == 0){
				Dialog.alert("请选择产品!");
				return;
			}
			if(DT_Product.getRowCount() + $("con_dg1").rows.length - 2 > 10){
				Dialog.alert("超出上限 10 款产品!");
				return;
			}
			dc.add("DT_Product", DT_Product);
			dc.add("WapBidMenuId", $V("WapBidMenuId"));
			dc.add("DocumentId", $V("DocumentId"));
			
			Server.sendRequest("com.sinosoft.cms.document.WapSiteBidPageManage.saveProducts", dc, function(
						response) {
				Dialog.alert(response.Message, function() {
					if (response.Status == 1) {
						$D.close();
						DataGrid.loadData('con_dg1');
					}
				});
			});
		}
		
		function saveOrderFlag() {
			DataGrid.save("con_dg1", "com.sinosoft.cms.document.WapSiteBidPageManage.saveProductOrderFlag",
				function() {
				DataGrid.loadData('con_dg1');
			});
		}
		
		function del() {
			var arr = DataGrid.getSelectedValue("con_dg1");
			if (!arr || arr.length == 0) {
				Dialog.alert("请先选择要删除的行！");
				return;
			}
			Dialog.confirm("确认要删除？", function() {
				var dc = new DataCollection();
				dc.add("IDs", arr.join());
				Server.sendRequest(
						"com.sinosoft.cms.document.WapSiteBidPageManage.delContents", dc,
					function(response) {
					Dialog.alert(response.Message, function() {
						if (response.Status == 1) {
							DataGrid.loadData("con_dg1");
						}
					});
				});
			});
		}
		
	</script>
	</head>
	<body class="dialogBody">
	<form id="form2">
	<input name="WapBidMenuId" type="hidden" value="${WapBidMenuId}" id="WapBidMenuId" />
	<input name="DocumentId" type="hidden" value="${DocumentId}" id="DocumentId" />
	<table width="100%" height="100%" border="0">
	   <tr>
			<td style="padding:3px 5px;">
				<fieldset>
	   			   <legend><label>标题菜单设置</label></legend>
						 <z:tbutton onClick="add()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>添加</z:tbutton>
					    <z:tbutton onClick="saveOrderFlag()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>保存</z:tbutton>
		         	    <z:tbutton onClick="del()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除</z:tbutton>
				  </fieldset>
			</td>
		</tr>
		<tr>
			 <td style="padding:0px 5px;">
				  <z:datagrid id="con_dg1" method="com.sinosoft.cms.document.WapSiteBidPageManage.dgConDataBind" page="false" lazy="false">
		              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
		                <tr ztype="head" class="dataTableHead">
		                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
		                  <td width="3%" ztype="selector" field="ID">&nbsp;</td>
		                  <td width="20%"><strong>产品名称</strong></td>
		                  <td width="25%"><strong>产品编号</strong></td>
		                  <td width="25%"><strong>Wap产品名称(Logo名称)</strong></td>
		                  <td width="10%"><strong>排序</strong></td>
		                </tr>
		                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" >
		                  <td>&nbsp;</td>
		                  <td>&nbsp;</td>
		                  <td>${ProductName}</td>
		                  <td>${ProductId}</td>
		                  <td>${WapProductName}</td>
		                  <td>${OrderFlag}</td>
		                </tr>
		                <tr ztype="edit">
							  <td>&nbsp;</td>
							  <td>&nbsp;</td>
			                  <td>${ProductName}</td>
		                  	  <td>${ProductId}</td>
			                  <td><input type="text" name="WapProductName" id="WapProductName" size="12" value="${WapProductName}" maxlength="6" /></td>
			                  <td><input type="text" name="OrderFlag" id="OrderFlag" size="6" value="${OrderFlag}" maxlength="10" verify="NotNull&&Number"/></td>
						</tr>
		              </table>
		            </z:datagrid>
			</td>
		</tr>
	</table>
	</form>
	</body>
	</html>
</z:init>
