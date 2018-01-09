<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品上线信息管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function search(){
	DataGrid.setParam("module_dg1",Constant.PageIndex,0);
	DataGrid.setParam("module_dg1","ProductName",$V("ProductName"));
	DataGrid.setParam("module_dg1","ProductID",$V("ProductID"));
	DataGrid.loadData("module_dg1");
}

function relationEdit(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "投保人与被保人关系配置" ;
	diag.URL = "Document/ProductRelationEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = relationSave;
	diag.show();
}

function bnfRelationEdit(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "受益人与被保人关系配置" ;
	diag.URL = "Document/ProductBnfRelationEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = bnfRelationSave;
	diag.show();
}

function relationSave(){
	var dc = $DW.Form.getData("form2");
	var arr = $DW.DataGrid.getSelectedValue("module_dginput");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要配置的数据！");
		return;
	}
	dc.add("Id",$DW.$("Id").value); 
	dc.add("Data", arr);
	if(Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.product.ProductInfo.addRelation",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				//暂时不做任何处理
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		});
	});
}

function bnfRelationSave(){
	var dc = $DW.Form.getData("form2");
	var arr = $DW.DataGrid.getSelectedValue("module_dginput");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要配置的数据！");
		return;
	}
	dc.add("Id",$DW.$("Id").value); 
	dc.add("Data", arr);
	if(Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.product.ProductInfo.addBnfRelation",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				//暂时不做任何处理
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		});
	});
}

function certifacateEdit(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 900;
	diag.Height = 500;
	diag.Title = "证件类型配置" ;
	diag.URL = "Document/ProductCertificateEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = certifacateSave;
	diag.show();
}
function certifacateSave(){
	var dc = $DW.Form.getData("form2");
	var arr = $DW.DataGrid.getSelectedValue("module_dginput");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要配置的数据！");
		return;
	}
	dc.add("Id",$DW.$("Id").value); 
	dc.add("Data", arr);
	if(Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.product.ProductInfo.addCertificate",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				//暂时不做任何处理
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		});
	});
}
function periodEdit(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "保险期间配置" ;
	diag.URL = "Document/ProductPeriodEditDialog.jsp?Id="+arr[0];
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = periodSave;
	diag.show();
}
function periodSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("Id",$DW.$V("Id"));
	dc.add("StartDate",$DW.$V("StartDate"));
	dc.add("EndDate",$DW.$V("EndDate"));
	Server.sendRequest("com.sinosoft.product.ProductInfo.addPeriod",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				//暂时不做任何处理
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		});
	});
}

function appntAgeEdit(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var selectData = DataGrid.getSelectedData("module_dg1");
	var appntage= selectData.get(0,"appntage");
	var appntAgeTips= selectData.get(0,"appntAgeTips");
	var minAge = "",maxAge = "";
	if(appntage && appntage != ""){
		minAge = appntage.split("-")[0];
		maxAge = appntage.split("-")[1];
	}
	if(!appntAgeTips){
		appntAgeTips = "";
	}
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 160;
	diag.Title = "投保人年龄范围配置" ;
	diag.URL = "Document/AppntAgeEditDialog.jsp?Id="+arr[0]+"&minAge="+minAge+"&maxAge="+maxAge+"&appntAgeTips="+appntAgeTips;
	diag.onLoad = function(){
		$DW.$("ProductID").focus();
	};
	diag.OKEvent = appntAgeSave;
	diag.show();
}

function appntAgeSave(){
	var dc = $DW.Form.getData("form2");
	dc.add("Id",$DW.$V("Id"));
	dc.add("minAge",$DW.$V("minAge"));
	dc.add("maxAge",$DW.$V("maxAge"));
	dc.add("appntAgeTips",$DW.$V("appntAgeTips"));
	Server.sendRequest("com.sinosoft.product.ProductInfo.addAppntAge",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		});
	});
}

</script>
</head>
<body>
<z:init method="com.sinosoft.product.ProductInfo.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:0 8px 4px;">
					    <z:tbutton onClick="relationEdit()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>投保人与被保人关系配置</z:tbutton>
					    <z:tbutton onClick="bnfRelationEdit()"><img src="../Icons/icon022a2.gif" width="20" height="20"/>受益人与被保人关系配置</z:tbutton>
              		    <z:tbutton onClick="certifacateEdit()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>证件类型配置</z:tbutton>
             		    <z:tbutton onClick="periodEdit()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>产品保险期间配置</z:tbutton>
             		    <z:tbutton onClick="appntAgeEdit()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>投保人年龄范围配置</z:tbutton>
	            	</td>
	           </tr>
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
						 产品编码：<input name="ProductID" type="text" id="ProductID"> &nbsp;
						 产品名称：<input name="ProductName" type="text" id="ProductName"> &nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="module_dg1" method="com.sinosoft.product.ProductInfo.dg1DataBind"  size="20" autoFill="false" multiSelect="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="15%"><strong>产品ID</strong></td>
                  <td width="15%"><strong>产品名称</strong></td>
                  <td width="15%"><strong>投保人与被保人关系</strong></td>
                  <td width="15%"><strong>受益人与被保人关系</strong></td>
                  <td width="15%"><strong>证件类型</strong></td>
                  <td width="15%"><strong>保险期限</strong></td>
                  <td width="15%"><strong>投保人年龄</strong></td>
                  <td width="15%"><strong>投保人年龄错误提示</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ProductID}</td>
                  <td>${ProductName} </td>
                  <td>${Relation} </td>
                  <td>${BnfRelation} </td>
                  <td>${Certificate} </td>
                  <td>${Period} </td>
                  <td>${appntAge} </td>
                  <td>${appntAgeTips} </td>
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
 </z:init>
</body>
</html>
