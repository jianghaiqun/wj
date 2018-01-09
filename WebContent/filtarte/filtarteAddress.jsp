<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>筛选地址生成</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

	function ProAllAddress() {
		Dialog.confirm("您确认要生成全部筛选地址吗，生成时间可能较长？", function() {
			Server.sendRequest("com.sinosoft.product.Filtrate.ProAllAddress",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在生成...");
						p.show();
						if(response.Status==1){
							DataGrid.loadData("filtrate_dg1");
						}
					});
		});
	}

	document.onkeydown = function(event) {
		event = getEvent(event);
		if (event.keyCode == 13) {
			var ele = event.srcElement || event.target;
			if (ele.id == 'SearchID' || ele.id == 'SearchName'
					|| ele.id == 'ERiskSubType' || ele.id == 'Submitbutton') {
				searchAddress();
			}
		}
	}

	function search() {
		DataGrid.setParam("filtrate_dg1", Constant.PageIndex, 0);
		DataGrid.setParam("filtrate_dg1", "Id", $V("Id"));
//		DataGrid.setParam("filtrate_dg1", "SearchName", $V("SearchName"));
		DataGrid.setParam("filtrate_dg1", "ERiskType", $V("ERiskType"));
		DataGrid.loadData("filtrate_dg1");
	}

	function synProductBySelect(){
		var arr = DataGrid.getSelectedValue("filtrate_dg1");
		if(!arr||arr.length==0){
			Dialog.alert("先选择要生成的数据!");
			return;
		}
		Dialog.confirm("确认要生成？",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Dialog.wait("正在生成...");
			Server.sendRequest("com.sinosoft.product.Filtrate.synAddressByOne",dc,function(response){
				Dialog.closeEx();
				Dialog.alert(response.Message,function(){
					if(response.Status==1){
						DataGrid.loadData("filtrate_dg1");
					}
				});
			});
		});
	}
	
</script>
</head>
<body>
<z:init method="com.sinosoft.product.Filtrate.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
    <tr valign="top">
      <td>
	      <table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
	          <tr>
	            <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />筛选地址生成</td>
	          </tr>
					<tr>
						<td style="padding:0 8px 4px;">
	              		    <z:tbutton onClick="ProAllAddress()"><img src="../Icons/icon022a4.gif" width="20" height="20"/>生成全部筛选地址</z:tbutton>
		            	</td>
		           </tr>
	      </table>
      </td>
    </tr>
    
   <tr>
		<td style="padding:2px 10px;">
			<div style="float:left;"> 
				 筛选地址ID&nbsp;<input name="Id" type="text" id="Id"> &nbsp;
<!--  			 筛选组合名称&nbsp;<input name="SearchName" type="text" id="SearchName"> &nbsp; -->
				 所属分类&nbsp;<z:select id='ERiskType'>${ERiskSubTypeList}</z:select>&nbsp;
				<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
				
		    </div>
		</td>
	</tr>
			
     <tr>   
        <td style="padding:0px 5px;">
			<z:datagrid id="filtrate_dg1" method="com.sinosoft.product.Filtrate.dg1DataBind1"  size="15" autoFill="false">
              <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                <tr ztype="head" class="dataTableHead">
                  <td width="3%" ztype="RowNo" drag="true"><img src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
                  <td width="7%"><strong> 筛选地址ID</strong></td>
                  <td width="30%"><strong>筛选地址</strong></td>
                  <td width="30%"><strong>筛选组合名称</strong></td>
                  <td width="6%"><strong>所属分类</strong></td>
                  <td width="12%"><strong>创建时间</strong></td>
                  <td width="15%"><strong>修改时间</strong></td>
                </tr>
                <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" >
                  <td>&nbsp;</td>
                  <td>${Id}</td>
                  <td>${SearchAddress}</td>
                  <td>${SearchName}</td>
                  <td>${ERiskSubTypeName}</td>
                  <td>${CreateDate}</td>
                  <td>${ModifyDate}</td>
                </tr>
                <tr ztype="pagebar">
					<td colspan="6" align="left">${PageBar}</td>
				</tr>
              </table>
            </z:datagrid>
           </td>
       </tr>
  </table>
 </z:init>
</body>
</html>
