<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品维护管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

function lookImage() {
	var dt = DataGrid.getSelectedData("module_dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要查看wap图片的产品！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能查看1条产品的图片！");
		return;
	}
	dr = drs[0]; 
	
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 400;
	diag.Title = "图片管理";
	diag.URL = "wap/WapProductPictureDialog.jsp?productid="+dr.get("ID");

	diag.show();
	diag.OKButton.hide();
}

function upProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要上线的产品！"); 
		return;
	}
	var dt = DataGrid.getSelectedData("module_dg1");
	Dialog.confirm("确认要上线？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		dc.add("Data",dt);
		Server.sendRequest("com.sinosoft.wap.ProductManage.upProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}
function downProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择将要下线的产品！");
		return;
	}
	Dialog.confirm("确认要下线吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.wap.ProductManage.downProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}
function recommendProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择将要推荐的产品！");
		return;
	}
	Dialog.confirm("确认要将产品置为推荐吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.wap.ProductManage.recommendProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}
function cancelRecommendProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择将要取消推荐的产品！");
		return;
	}
	Dialog.confirm("确认要取消推荐的产品吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.wap.ProductManage.cancelRecommendProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}
function hotProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择将要热销的产品！");
		return;
	}
	Dialog.confirm("确认要将产品置为热销吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.wap.ProductManage.hotProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}
function cancelHotProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择将要取消热销的产品！");
		return;
	}
	Dialog.confirm("确认要将产品置为非热销吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.wap.ProductManage.cancelHotProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}
function topProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择将要精选的产品！");
		return;
	}
	Dialog.confirm("确认要将产品置为精选吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.wap.ProductManage.topProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}
function cancelTopProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择将要取消精选的产品！");
		return;
	}
	Dialog.confirm("确认要将产品取消精选吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.wap.ProductManage.cancelTopProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}
function limitProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length!=1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.sinosoft.wap.ProductManage.isLimitProduct",dc,function(response){
		if(response.Status==1){
			edit();
		}else{
			alert("此产品不是限时优惠产品");
		}
	});
}

function edit(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的数据！");
		return;
	}
	if(!arr||arr.length>1){
		Dialog.alert("请先选择一行要编辑的数据！");
		return;
	}
	var dg = DataGrid.getSelectedData("module_dg1");
	//var productName = arr[2];
	//alert(dg);
	//alert(dg.getProductId);
	//alert(dg.getProductName);
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 200;
	diag.Title = "限时优惠编辑" ;
	//diag.URL = "wap/WapProductEditDialog.jsp?productId="+arr[0]+"&productName="+$V("productName");
	diag.URL = "wap/WapProductEditDialog.jsp?productId="+arr[0];
	diag.onLoad = function(){
		$DW.$("productName").focus();
	};
	diag.OKEvent = saveLimitProduct;
	diag.show();
}

function saveLimitProduct(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
    }
	Server.sendRequest("com.sinosoft.wap.ProductManage.saveLimitProduct",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		})
	});
}



document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if( ele.id == 'ElementType' || ele.id == 'ElementName'||  ele.id == 'ElementContent'||ele.id == 'Submitbutton'){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("module_dg1",Constant.PageIndex,0);
	DataGrid.setParam("module_dg1","productName",$V("productName"));
	DataGrid.setParam("module_dg1","IsPublish",$V("IsPublish"));
	DataGrid.setParam("module_dg1","RecommendFlag",$V("RecommendFlag"));
	DataGrid.setParam("module_dg1","HotFlag",$V("HotFlag"));
	DataGrid.setParam("module_dg1","Preferential",$V("Preferential"));
	DataGrid.setParam("module_dg1","TopFlag",$V("TopFlag"));
	DataGrid.loadData("module_dg1");
}

function setOutChannelsn(){
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
	diag.Height = 200;
	diag.Title = "对外渠道设置";
	diag.URL = "wap/SetOutChannelsnDialog.jsp?productId="+arr[0];
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.wap.SetOutChannelsn.updateOutChannel",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("module_dg1");
			}
		});
	});
}

function cleanCache(){
	var dc = new DataCollection();
	Server.sendRequest("com.sinosoft.wap.ProductManage.cleanCache",dc,function(response){
		Dialog.alert(response.Message,function(response) {
			Dialog.alert(response.Message);
		});
	});
}
function setSortNum(){
	DataGrid.save("module_dg1","com.sinosoft.wap.ProductManage.setSortNum",function(){
		DataGrid.setParam("module_dg1",Constant.PageIndex,0);
		DataGrid.loadData("module_dg1");
	});
}
Page.onLoad(function() {
	$("module_dg1").afterEdit = function (tr, dr) {
		dr.set("Prop2", $V("sortNum"));
		return true;
	}
});
</script>
</head>
<body >
<z:init method="com.sinosoft.module.Module.initModuleList">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:0 8px 4px;">
             		    <z:tbutton onClick="upProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>上线</z:tbutton>
             		    <z:tbutton onClick="downProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>下线</z:tbutton>
             		    <z:tbutton onClick="recommendProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>推荐</z:tbutton>
             		    <z:tbutton onClick="cancelRecommendProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>取消推荐</z:tbutton>
             		    <z:tbutton onClick="hotProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>热销</z:tbutton>
             		    <z:tbutton onClick="cancelHotProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>取消热销</z:tbutton>
             		    <z:tbutton onClick="topProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>精选</z:tbutton>
             		    <z:tbutton onClick="cancelTopProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>取消精选</z:tbutton>
             		    <z:tbutton onClick="limitProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>限时优惠管理</z:tbutton>
             		    <z:tbutton onClick="setOutChannelsn()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>对外渠道设置</z:tbutton>
             		    <z:tbutton onClick="cleanCache()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>缓存清理</z:tbutton>
             		    <z:tbutton onClick="setSortNum()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>调整顺序</z:tbutton>
             		     <z:tbutton onClick="lookImage()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>产品图片设置</z:tbutton>
	            	</td>
	           </tr>
          <tr>
				<td style="padding:2px 10px;">
					<div style="float:left;"> 
			    		 产品名称&nbsp;<input name="productName" type="text" id="productName"> &nbsp;
						 wap站上线标记&nbsp;
						 <z:select name='IsPublish' id='IsPublish' style="width:50px;">
						 	<option value=""></option>
						 	<option value="Y">Y</option>
							<option value="N">N</option>
						 </z:select>&nbsp;
						 推荐标记&nbsp;
						 <z:select name='RecommendFlag' id='RecommendFlag' style="width:50px;">
						 	<option value=""></option>
						 	<option value="Y">Y</option>
							<option value="N">N</option>
						 </z:select>&nbsp;
						 热销标记&nbsp;
						 <z:select name='HotFlag' id='HotFlag' style="width:50px;">
						 	<option value=""></option>
						 	<option value="Y">Y</option>
							<option value="N">N</option>
						 </z:select>&nbsp;
						  精选标记&nbsp;
						<z:select name='TopFlag' id='TopFlag' style="width:50px;">
						 	<option value=""></option>
						 	<option value="Y">Y</option>
							<option value="N">N</option>
						 </z:select>&nbsp;
						 限时标记&nbsp;
						 <z:select name='Preferential' id='Preferential' style="width:50px;">
						 	<option value=""></option>
						 	<option value="Y">Y</option>
							<option value="N">N</option>
						 </z:select>&nbsp;
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    </div>
				</td>
			</tr>
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="module_dg1" method="com.sinosoft.wap.ProductManage.dg1DataBind"  page="true" size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="18%"><strong>产品名称</strong></td>
                  <td width="20%"><strong>wap站上线状态</strong></td>
                  <td width="6%"><strong>是否推荐</strong></td>
                  <td width="6%"><strong>是否热销</strong></td>
                  <td width="6%"><strong>是否精选</strong></td>
				  <td width="6%"><strong>顺序编号</strong></td>
                  <td width="6%"><strong>优惠属性</strong></td>
                  <td width="12%"><strong>优惠起期</strong></td>
                  <td width="12%"><strong>优惠止期</strong></td>
                  <td width="12%"><strong>对外渠道</strong></td>
                </tr>
			    <tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>${ProductName}</td>
					<td>${IsPublish}</td>
					<td> ${RecommendFlag}</td>
					<td> ${HotFlag}</td>
					<td> ${TopFlag}</td>
					<td> ${Prop2}</td>
					<td> ${Preferential}</td>
					<td> ${StartDate}</td>
					<td> ${EndDate}</td>
					<td> ${Prop5}</td>
					 <td> ${wapPictureUrl}</td>
				</tr>
                <tr ztype="edit">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ProductName}</td>
                  <td>${IsPublish}</td>
                  <td> ${RecommendFlag}</td>
                  <td> ${HotFlag}</td>
                  <td> ${TopFlag}</td>
				  <td> <input name="Prop2" type="text" id="sortNum" value="${Prop2}" size="20" style="width: 100%"></td>
                  <td> ${Preferential}</td>
                  <td> ${StartDate}</td>
                  <td> ${EndDate}</td>
                  <td> ${Prop5}</td>
                  
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
