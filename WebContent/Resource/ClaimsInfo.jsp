<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>理赔资料配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<title></title>
<script>
Page.onLoad(function(){
	$("dg2").afterEdit = function(tr,dr){
		dr.set("name",$V("name"));
		dr.set("isHot",$V("isHot"));
		
		dr.set("isHotName",$("isHot").getText());
		dr.set("orderFlag",$V("orderFlag"));
		return true;
	}
});
function doSearch(){
	DataGrid.setParam("dg2", Constant.PageIndex, 0);
	DataGrid.setParam("dg2", "comCode", $V("comCode"));
	DataGrid.setParam("dg2", "isHot", $V("isHot1"));
	DataGrid.loadData("dg2");
}

function upload(){
	var diag = new Dialog("Diag1");
	diag.Width = 530;
	diag.Height = 450;
	diag.Title = "上传附件";
	diag.URL = "Resource/ClaimsInfoDialog.jsp";
	diag.onLoad = function(){
	};
    diag.OKEvent=OK;
	diag.show();
}

function OK(){
	$DW.upload();
}

function RepeatUpload(){
	var arr = DataGrid.getSelectedValue("dg2");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要重新上传的附件！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "重新上传";
	diag.URL = "Resource/ClaimsRepeatUpload.jsp?ID="+arr[0];
	diag.OKEvent = RepeatOK;
	diag.show();
	diag.CancelButton.value="关闭";
}

function RepeatOK(){
	$DW.RepeatUpload();
}

function edit(){
	var dt = DataGrid.getSelectedData("dg2");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的理赔资料！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改1条信息！");
		return;
	}
	dr = drs[0]; 
  	editDialog(dr);
}


function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 320;
	diag.Title = "理赔资料信息修改";
	diag.URL = "Resource/ClaimsInfoDialog.jsp?id="+dr.get("id");
	diag.onLoad = function(){
		$DW.$("tr1").hide();
		$DW.$("tr2").hide();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave() {
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.sinosoft.cms.resource.ClaimsInfo.editSave",dc,function(response){
		if(response.Status==1){
			$D.close();
			doSearch();
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg2");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的理赔资料！");
		return;
	}
	Dialog.confirm("确定要删除吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.resource.ClaimsInfo.delClaimsInfo",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("删除成功");
        		DataGrid.loadData("dg2");
			}
		});
	});
} 

function sortSave() {
	if(Verify.hasError()){
		return;
	}
	DataGrid.save("dg2","com.sinosoft.cms.resource.ClaimsInfo.saveClaimsInfo",function(){			
		doSearch();
	});
}

function onFileUploadCompleted(){
	doSearch();
	if($D){
		setTimeout(function(){$D.close()},100);
	}
}

</script>
</head>
<body>
<z:init method="com.sinosoft.cms.resource.ClaimsInfo.init">
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon022a1.gif" width="20" height="20" />理赔资料配置 </td>
            </tr>
            <tr>
				<td>
					 <table  cellspacing="0" cellpadding="3">
					 
						<tr>
							<td nowrap>保险公司：</td>
							<td><z:select name="comCode" id="comCode" style="width:150px;">${supplier}</z:select></td>
							<td nowrap>热门推荐：</td>
							<td><z:select name="isHot1" id="isHot1" style="width:50px;">${YesOrNo}</z:select></td>
							<td><z:tbutton onClick="doSearch()" id="Submitbutton" >
			            	     <img src="../Icons/icon005a2.gif" width="20" height="20"/>查询
			            	   </z:tbutton></td>
						</tr>
					 
						<tr>
						   <td  colspan="8">
			            	   
			            	 <z:tbutton onClick="upload();"><img src="../Icons/icon033a16.gif" width="20" height="20"/>上传</z:tbutton>
			            	 <z:tbutton onClick="RepeatUpload();"><img src="../Icons/icon033a16.gif" width="20" height="20"/>重新上传</z:tbutton>
			            	 <z:tbutton onClick="edit()"><img src="../Icons/icon018a4.gif" width="20" height="20"/>修改</z:tbutton>
			            	 <z:tbutton onClick="del();"><img src="../Icons/icon018a3.gif" />删除</z:tbutton>
			            	 <z:tbutton onClick="sortSave()"><img src="../Icons/icon021a2.gif" width="20" height="20" />保存</z:tbutton>
			            	</td>
		            	</tr>
		             </table>
				</td>
			</tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <z:datagrid id="dg2" method="com.sinosoft.cms.resource.ClaimsInfo.dg2DataBind"  size="15" scroll="true" >
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable" >
                  <tr ztype="head" class="dataTableHead">
                    <td width="2%" ztype="rowno">&nbsp;</td>
                    <td width="3%" ztype="selector" field="id">&nbsp;</td>
                    <td width="17%">附件名称</td>
                    <td width="5%">大小</td>
                    <td width="5%">格式</td>
                    <td width="10%">排序</td>
                    <td width="10%">所属保险公司</td>
                    <td width="20%">所属二级分类</td>
                    <td width="7%">是否热门推荐</td>
                    <td width="3%"><b>下载</b></td>
                  </tr>
                  <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${name}</td>
                    <td>${fileSize}</td>
                    <td>${suffix}</td>
                    <td>${orderFlag}</td>
                    <td>${comName}</td>
                    <td title="${classifyName}">${classifyName}</td>
                    <td>${isHotName}</td>
                    <td><a href="${Link}" target="_blank"><img src="../Framework/Images/icon_down.gif" width="15" height="15" style="cursor:pointer;" title="下载次数:${Prop1}"/></a></td>
                  </tr>
                  <tr ztype="edit">
				    <td>&nbsp;</td>
					<td>&nbsp;</td>
					<td><input name="name" type="text" class="input1" id="name" size="30" value="${name}"></td>
					<td>${fileSize}</td>
                    <td>${suffix}</td>
                    <td><input name="orderFlag" type="text" class="input1" id="orderFlag" size="20" value="${orderFlag}"></td>
                    <td>${comName}</td>
                    <td>${classifyName}</td>
                    <td><z:select name="isHot" id="isHot" style="width:50px;" value="${isHot}">${@YesOrNo}</z:select></td>
					<td><a href="${Link}" target="_blank"><img src="../Framework/Images/icon_down.gif" width="15" height="15" style="cursor:pointer;" title="下载次数:${Prop1}"/></a></td>
				  </tr>
                  <tr ztype="pagebar">
					 <td height="25" colspan="11">${PageBar}</td>
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