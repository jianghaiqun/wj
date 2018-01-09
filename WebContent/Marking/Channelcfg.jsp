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
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function sear(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","TerminalCode",document.all.Markingzd.value);
	DataGrid.setParam("dg1","ProductCategory",document.all.ProductCategory.value);
	DataGrid.setParam("dg1","ChannelTypeCode",document.all.ChannelTypeCode.value);
	DataGrid.setParam("dg1","Companyname",document.all.Companyname.value);
	DataGrid.setParam("dg1","AdvTypeCode",document.all.AdvTypeCode.value);
    DataGrid.setParam("dg1","ShowFormCode",document.all.ShowFormCode.value);
	DataGrid.setParam("dg1","StartDate",document.all.StartDate.value);
	DataGrid.setParam("dg1","EndDate",document.all.EndDate.value);
	DataGrid.loadData("dg1");
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	var DT = DataGrid.getSelectedData("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除该项吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",DT);
		Server.sendRequest("com.sinosoft.platform.Marking.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==1){
				Dialog.alert(response.Message);
			}else{
				DataGrid.loadData("dg1");
			}
		});
	},function(){
		return;
	});
}
function edit(Serial){
    //var arr = DataGrid.getSelectedValue("dg1");
    var arr = DataGrid.getSelectedData("dg1");
	if(Serial&&Serial!=""){
		arr = new Array();
		arr[0] = Serial;
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要编辑的行！");
		return;
	}
	if(arr.length>=2){
		Dialog.alert("您选择了过多的条目，请只选择其中的一条 ！");
		return;
	}
	dr = arr.getDataRow(0);
	var diag = new Dialog("Diag1");
	diag.Width = 1200;
	diag.Height = 280;
	diag.Title = "渠道信息修改";
	diag.URL = "Marking/MarkingEdit.jsp?Serial="+dr.get("Serial")+"&addType="+dr.get("ConfigSerial");
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改会员信息";
	//diag.Message = "修改会员级别、姓名、分数、折扣等";
	diag.show();
}
function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.platform.Marking.edit",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message);
		if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
		}
	});
}

function addMarking(){
	var diag = new Dialog("Diag1");
	diag.Width = 1000;
	diag.Height = 300;
	diag.Title = "新建";
	diag.URL = "Marking/Markingadd.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "营销渠道配置";
	diag.Message = "请先生成渠道编码、再将信息补全后保存";
	diag.show();
}
function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	Server.sendRequest("com.sinosoft.platform.Marking.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});
}

</script>
<style>
#hotarea{width:160px; height:120px; border:#147 1px solid; background:#ca6 url(../Platform/Images/picture.jpg) no-repeat; position:relative}
#hotarea  a{ position:absolute; display:block; width:35px; height:25px; border:#fff 1px dashed; text-align:center; line-height:24px; color:#fff;}
#hotarea  a:hover{ text-decoration:none; border:#fff 1px solid; color:#fff}
</style>
</head>
<body>
<input type="hidden" id="CatalogID">
<table width="100%" border='0' cellpadding='10' cellspacing='0'>
	  <tr>
		<td width="50%" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0" id="ConfigTable">
             <tr>
				<td valign="top">
				   <fieldset >
				   <legend >
				      <label>营销渠道</label> 
				   </legend>  
				        <z:init method="com.sinosoft.platform.Marking.init">
						<form id="form1">
						<table>
						          <tr>
		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">营销终端：</td>
						          <td ><z:select id="Markingzd">${Markingzd}</z:select></td>
						          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">产品类别：</td>
						          <td ><z:select id="Markingkg">${Markingkg}</z:select></td>
						          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">产品名称：</td>
						          <td ><z:select id="ProductCategory">${ProductCategory}</z:select></td>
						          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">展现形式：</td>
						          <td ><z:select id="ShowFormCode">${ShowFormCode}</z:select></td>
						          </tr>
					    </table>
					    <table>
						          <tr>
		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">渠道类型：</td>
						          <td ><z:select id="ChannelTypeCode">${ChannelTypeCode}</z:select></td>
						          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">渠道公司：</td>
						          <td ><z:select id="Companyname">${Companyname}</z:select></td>
						           <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
   		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">广告类型：</td>
						          <td ><z:select id="AdvTypeCode">${AdvTypeCode}</z:select></td>
						          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>

						          </tr>
					    </table>
	    				 <table>
						          <tr>						          
		       				      <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活动开始时间：</td>
		       				      <td><input name="StartDate" id="StartDate" value="" type="text" size="14" ztype="Date"/></td>
						          <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						           <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活动结束时间：</td>
						          <td> <input name="EndDate" id="EndDate" value="" type="text" size="14" ztype="Date"/></td>
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
				     <z:tbutton id="b1" onClick="sear()"><img src="../Icons/icon006a7.gif" width="20" height="20"/>营销推广查询</z:tbutton>  
                	 <z:tbutton id="b2" onClick="addMarking()"><img src="../Icons/icon021a5.gif" width="20" height="20"/>营销渠道配置添加</z:tbutton>
                	 <z:tbutton id="b3" onClick="edit()"><img src="../Icons/icon021a5.gif" width="20" height="20"/>营销渠道配置修改</z:tbutton>  
                	 <z:tbutton id="b4" onClick="del()"><img src="../Icons/icon021a5.gif" width="20" height="20"/>营销渠道配置删除</z:tbutton> 
					 <tr>
		       		 <td>&nbsp;</td>
					 </tr>					 
                	 <z:datagrid id="dg1" method="com.sinosoft.platform.Marking.dg1DataBind" size="8">
					<table width="110%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="afterRowDragEnd">
						<tr ztype="head" class="dataTableHead">
							<td width="5%" height="30" ztype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="3%" height="30" ztype="Selector" field="id">&nbsp;</td>
							<td width="8%"><strong>广告编号</strong></td>
							<td width="10%"><strong>备注</strong></td>
							<td width="7%"><strong>着陆页</strong></td>
							<td width="7%"><strong>投放页</strong></td>
							<td width="3%"><strong>uv</strong></td>
							<td width="3%"><strong>pv</strong></td>
							<td width="10%"><strong>营销终端</strong></td>
							<td width="7%"><strong>保险公司</strong></td>
							<td width="7%"><strong>产品类型</strong></td>
							<td width="8%"><strong>渠道类型</strong></td>
							<td width="8%"><strong>渠道公司</strong></td>
							<td width="8%"><strong>广告类型</strong></td>
							<td width="8%"><strong>展现形式</strong></td>
						</tr>
						<tr onDblClick="edit(${Serial});">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td algin="center">${AdvSerial}</td>
							<td >${Remark}</td>
							 <td>${SendAddress}</td>			 
							<td>${LandingPage}</td>
							<td">${uv}</td>
							 <td>${pv}</td>			 
							<td>${TerminalName}</td>
							 <td>${Insurancecompany}</td>			 
							<td>${ProductMajorCategoryName}</td>
							 <td>${ChannelTypeName}</td>			 
							<td>${CompanyName}</td>
							 <td>${AdvTypeName}</td>			 
							<td>${ShowFormName}</td>
							<td>${ConfigSerial}</td>
							<td>${Serial}</td>
						</tr>
						<tr ztype="pagebar">
							<td colspan="9" align="left">${PageBar}</td>
						</tr>
					</table>
				</z:datagrid></td>               	 
				</td>				
			 </tr>			 
		</table>
		</td>			
	</tr>		
</table>
</body>
</html>