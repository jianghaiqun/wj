<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>产品维护管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script src="../Framework/Main.js"></script>
<script>
function downProduct(){
	var arr = DataGrid.getSelectedValue("module_dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择将要删除的产品！");
		return;
	}
	Dialog.confirm("确认要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.wap.WdProductManage.downProduct",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("module_dg1");
				}
			});
		});
	});
}

function getProductList(){
	var diag = new Dialog("Diag4");
	diag.Width = 810;
	diag.Height = 370;
	diag.Title = "产品列表";
	diag.URL = "wap/Wd_ProductDialog.jsp";
	diag.OKEvent = chooseproduct;
	diag.show();
	diag.CancelButton.value = "取消";
	
}

//确定产品
function chooseproduct(){
		var arr = $DW.DataGrid.getSelectedValue("dg2");
		if(!arr||arr.length==0){
			Dialog.alert("请选中产品！");
			return;
		}
		
		var id          = $DW.DataGrid.getSelectedValueByColumns("dg2", "id");
		var productname = $DW.DataGrid.getSelectedValueByColumns("dg2", "ProductName");
		var ids = id.split(",");
		var names = productname.split(",");
		Dialog.confirm("确认要保存吗？",function(){
			var dc = new DataCollection();
			dc.add("ids",ids);
			dc.add("names",names);
			Server.sendRequest("com.sinosoft.wap.WdProductManage.upProduct",dc,function(response){
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataGrid.loadData("module_dg1");
					}
				});
			});
		});
	$D.close();
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
	DataGrid.loadData("module_dg1");
}

</script>
</head>
<body >
<z:init method="com.sinosoft.module.Module.initModuleList">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td style="padding:0 8px 4px;">
             		    <z:tbutton onClick="getProductList()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>新增产品</z:tbutton> 
             		    <z:tbutton onClick="downProduct()"><img src="../Icons/icon022a3.gif" width="20" height="20"/>删除产品</z:tbutton>
             		    <div style="float:right;"> 
				    		 产品名称&nbsp;<input name="productName" type="text" id="productName" style="width:270px"> &nbsp;
							<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				    	</div>
             		    
	            	</td>
	           </tr>
         
          <tr>   <td style="padding:0px 5px;">
			<z:datagrid id="module_dg1" method="com.sinosoft.wap.WdProductManage.dg1DataBind"  page="true" size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="3%" ztype="selector" field="id">&nbsp;</td>
                  <td width="18%"><strong>产品名称</strong></td>
                  <td width="10%"><strong>上线状态</strong></td>
                  <td width="9%"><strong>是否热销</strong></td>
                  <td width="9%"><strong>是否推荐</strong></td>
                  <td width="9%"><strong>是否精选</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="limitProduct();">
                  <td>&nbsp;</td>
                  <td>&nbsp;</td>
                  <td>${ProductName}</td>
                  <td>${IsPublish}</td>
                  <td> ${isHot}</td>
                  <td> ${isTop}</td>
                  <td> ${isSelected}</td>
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
