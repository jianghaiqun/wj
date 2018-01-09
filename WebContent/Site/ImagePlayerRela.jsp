<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Name",$V("Name"));
		dr.set("Info",$V("Info"));
		dr.set("LinkURL",$V("LinkURL"));
		dr.set("LinkText",$V("LinkText"));
		dr.set("Prop1",$V("Prop1"));
		dr.set("Prop2",$V("Prop2"));
		dr.set("orderflag",$V("orderflag"));
		dr.set("StartDate",$V("StartDate"));
		dr.set("STime",$V("STime"));
		dr.set("EndDate",$V("EndDate"));
		dr.set("ETime",$V("ETime"));
		return true;
	};
});

function addImage(){
	var diag = new Dialog("Diag2");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "添加图片";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=checkbox";
	diag.OKEvent = OK;	
	diag.show();
}

function editImage(id){
	$S("DoImageType",'edit');
	$S("DoImageID",id);
	var diag = new Dialog("Diag2");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "更换图片";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=checkbox&showButton=0";
	diag.OKEvent = OK;	
	diag.show();
}

function OK(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	
	}else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
		
	}
}

function onUploadCompleted(returnID){
	var dc = new DataCollection();
	dc.add("ImagePlayerID",$V("ImagePlayerID"));
	dc.add("ImageIDs",returnID);
	var type=$V("DoImageType");
	var method="com.sinosoft.cms.site.ImagePlayerRela.relaImage";
	if(type=='edit'){
		dc.add("OldImageID",$V("DoImageID"));
		method="com.sinosoft.cms.site.ImagePlayerRela.editImage";
	}
	Server.sendRequest(method,dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}

function onReturnBack(returnID){
	onUploadCompleted(returnID);
}

function save(){
	DataGrid.save("dg1","com.sinosoft.cms.site.ImagePlayerRela.dg1Edit",function(){window.parent.location.reload();});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("您确认要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("ImagePlayerID",$V("ImagePlayerID"));
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.site.ImagePlayerRela.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1");
				}			
			});
		});
	});
}

function publishIndex(){
	var dc = new DataCollection();
	Server.sendRequest("com.sinosoft.cms.site.CatalogSite.publishIndex",dc,function(response){
		if(response.Status==0){
			Dialog.alert("发布播放器失败!");
		}else{
			Dialog.alert("发布播放器成功!");
		}
	});
}
function  modify(){	
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要修改的行！");
		return;
	}	
	if(arr.length>1){
		Dialog.alert("请选择一行进行修改！");
		return;
	} 
	  $('dg1').edit();
}

function afterRowDragEnd(type,targetDr,sourceDr,rowIndex,oldIndex){
	alert(11);
	if(rowIndex==oldIndex){
		return;
	}
	var order = $("dg1").DataSource.get(rowIndex-1,"OrderFlag");
	var target = "";
	var dc = new DataCollection();
	var ds = $("dg1").DataSource;
	var i = rowIndex-1;
	if(i!=0){
		target = ds.get(i-1,"OrderFlag");
		dc.add("Type","After");
	}else{
		dc.add("Type","Before");
		target = $("dg1").DataSource.get(1,"OrderFlag");
	}
	dc.add("Target",target);
	dc.add("Orders",order);
	dc.add("ImagePlayerID",$V("ImagePlayerID"));
	DataGrid.showLoading("dg1");
	Server.sendRequest("com.sinosoft.cms.site.ImagePlayerRela.sortColumn",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}
function hidEndTime(id){
	if($V(id)!="1"){
		$("hidEndTime").show();
		if($V("HidEDate") != null && $V("HidEDate") !=''){
			$S("EndDate",$V("HidEDate"));
			$S("ETime",$V("HidETime"));
		}else{
			$S("StartDate","");
			$S("STime","");
		}
	}else{
		$("hidEndTime").hide();
		$S("EndDate","");
		$S("ETime","");
		if($V('StartDate')==null || $V('StartDate')==''){
			  var d = new Date();
			  var vYear = d.getFullYear();
			  var vMon = d.getMonth() + 1;
			  var vDay = d.getDate();
			  var h = d.getHours(); 
			  var m = d.getMinutes(); 
			  var se = d.getSeconds(); 
			  var ymd=vYear+"-"+(vMon<10 ? "0" + vMon : vMon)+"-"+(vDay<10 ? "0"+ vDay : vDay);
			  var hms=(h<10 ? "0"+ h : h)+":"+(m<10 ? "0" + m : m)+":"+(se<10 ? "0" +se : se);
			  $S("StartDate",ymd);
			  $S("STime",hms);
		  };
	}
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style>
table.innerTable td{
	border-bottom:none;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<form enctype="multipart/form-data" id="form2" method="post"><input
	name="ImagePlayerID" type="hidden" id="ImagePlayerID"
	value="<%=request.getParameter("ImagePlayerID")%>" /> <input
	name="DoImageType" id="DoImageType" type="hidden" value='add' /> <input
	name="DoImageID" id="DoImageID" type="hidden" />
<table width="100%" border='0' cellpadding='0' cellspacing='0'>
	<tr>
		<td style="padding:4px 5px;"><z:tbutton onClick="addImage();"
			id="UploadButton"><img src="../Icons/icon039a2.gif" />添加</z:tbutton> 
            <z:tbutton onClick="modify();" id="SaveButton"><img src="../Icons/icon039a4.gif" width="20" height="20" />修改</z:tbutton> 
			<z:tbutton onClick="save();"><img src="../Icons/icon039a16.gif" />保存</z:tbutton> 
            <z:tbutton onClick="del();" id="DelButton"><img src="../Icons/icon039a3.gif" />删除</z:tbutton> 
            <z:tbutton onClick="publishIndex();"><img src="../Icons/icon002a1.gif" />发布</z:tbutton>
			<div style="float: right; white-space: nowrap;"><b style='color:#F00'>最多可添加6张图片&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></div></td>
	</tr>
	<tr>
		<td style="padding:0px 5px;"><z:datagrid id="dg1"
			method="com.sinosoft.cms.site.ImagePlayerRela.dg1DataBind" page="false">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable"
				afterdrag="afterRowDragEnd">
				<tr ztype="head" class="dataTableHead">
					<td width="6%" ztype="RowNo" drag="true"><img
						src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
					<td width="6%" ztype="selector" field="ID">&nbsp;</td>
					<td width="19%"><b>图片</b></td>
					<td><b>图片名称</b><b>图片链接信息</b></td>
				</tr>
				<tr height="90">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td height="90" width="19%"><a
						href="javascript:editImage(${ID});"> <img align="center"
						src="${Alias}/${Path}s_${FileName}" title="点击更换图片"></a></td>
					<td>
					<table width="100%" cellspacing="0" class="innerTable">
						<tr>
							<td width="60"><b>名称: </b></td>
							<td>${Name}</td>
						</tr>
						<tr>
							<td><b>说明:</b></td>
							<td>${Info}</td>
						</tr>
						<tr>
							<td><b>链接地址:</b></td>
							<td>${LinkURL}</td>
						</tr>
						<tr>
							<td><b>链接文本:</b></td>
							<td>${LinkText}</td>
						</tr>
						<tr>
							<td><b>过渡地址:</b></td>
							<td>${Prop1}</td>
						</tr>
						<tr>
							<td><b>过度名称:</b></td>
							<td>${Prop2}</td>
						</tr>
						<tr>
							<td><b>排序:</b></td>
							<td>${orderflag}</td>
						</tr>
						<tr>
							<td><b>上线时间:</b></td>
							<td>${StartDate} ${STime}</td>
						</tr>
						<tr>
							<td><b>下线时间:</b></td>
							<td>${EndDate} ${ETime}</td>
						</tr>
					</table>
					</td>
				</tr>
				<tr ztype="edit" height="90">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td height="90"><img align="center"
						src="${Alias}${Path}s_${FileName}" title="${LinkText}"></td>
					<td>
					<table width="100%" cellspacing="0" class="innerTable">
						<tr>
							<td width="60"><b>名称:</b></td>
							<td><input type="text" class="input1" id="Name"
								value="${Name}" size="70"></td>
						</tr>
						<tr>
							<td><b>说明:</b></td>
							<td><input type="text" class="input1" id="Info"
								value="${Info}" size="70"></td>
						</tr>
						<tr>
							<td><b>链接地址:</b></td>
							<td><input name="text" type="text" class="input1"
								id="LinkURL" value="${LinkURL}" size="70"></td>
						</tr>
						<tr>
							<td><b>链接文本:</b></td>
							<td><input type="text" class="input1" id="LinkText"
								value="${LinkText}" size="70"></td>
						</tr>
						<tr>
							<td><b>过渡地址:</b></td>
							<td><input type="text" class="input1" id="Prop1"
								value="${Prop1}" size="70"></td>
						</tr>
						<tr>
							<td><b>过度名称:</b></td>
							<td><input type="text" class="input1" id="Prop2"
								value="${Prop2}" size="70"></td>
						</tr>
						<tr>
							<td><b>排序:</b></td>
							<td><input type="text" class="input1" id="orderflag"
								value="${orderflag}" size="70"></td>
						</tr>
						<tr>
							<td><b>发布方式：</b></td>
							<td> 
								<input id="IsShowText_0" class="inputRadio" type="radio" onclick="hidEndTime('IsShowText_0');" value="1" name="IsShowText">
								<label for="IsShowText_0">立即发布</label>
								<input id="IsShowText_1" class="inputRadio" type="radio" onclick="hidEndTime('IsShowText_1');" value="2" name="IsShowText">
								<label for="IsShowText_1">定时发布</label>
							</td>
						</tr>
				        <tr>
							<td><b>上线时间：</b></td>
							<td>
								<input id="StartDate" value="${StartDate}" type="text" size="14" ztype="Date" class="input1"/>
							  	<input id="STime" value="${STime}" type="text" size="10" ztype="Time" class="input1"/></td>
						</tr>
				        <tr id="hidEndTime" style="display:none">
							<td><b>下线时间：</b></td>
							<td>
								<input id="EndDate" value="${EndDate}" type="text" size="14" ztype="Date" class="input1"/> 
						  		<input id="ETime" value="${ETime}" type="text" size="10" ztype="Time" class="input1"/>
						  		<input type="hidden" id="HidEDate" name="HidEDate" value="${EndDate}"/>
    							<input type="hidden" id="HidETime" name="HidETime" value="${ETime}"/></td>
						</tr>
					</table>
					</td>
				</tr>
			</table>
		</z:datagrid></td>
	</tr>
</table>
</form>
</body>
</html>
