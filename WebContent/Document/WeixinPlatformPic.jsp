<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 180;
	diag.Title = "新建场景二维码";
	diag.URL = "Document/WeixinPlatformPic_add.jsp";
	diag.onLoad = function(){
		$DW.$("channelName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	if(!/^(?!00)(?:[0-9]{1,3}|1000)$/.test($DW.$V("sceneId")) 
			|| "0" == $DW.$V("sceneId")){
		Dialog.alert("场景值中含有不规则字符!场景值必须是1-1000的整数值。");
		return;
	}
	Server.sendRequest("com.sinosoft.cms.document.WeixinPlatform.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除该场景吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.cms.document.WeixinPlatform.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}
			});
		});
	});
}

function doSearch(){
	var sceneId = "";
	if($V("sceneId") != "请输入场景值"){
		sceneId = $V("sceneId").trim();
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","sceneId",sceneId);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'sceneId'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	if ($V("sceneId") == "请输入场景值") {
		$S("sceneId","");
	}
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
      <tr valign="top">
        <td><table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" />微信平台二维码场景列表</td>
            </tr>
			<tr>
				<td style="padding:0 8px 4px;">
					<z:tbutton onClick="add()"><img src="../Icons/icon018a2.gif" />新建</z:tbutton>
                	<z:tbutton onClick="del()"> <img src="../Icons/icon018a3.gif" />删除</z:tbutton>
                  <div style="float: right; white-space: nowrap;"><input type="text" name="sceneId"
						id="sceneId" value="请输入场景值" onFocus="delKeyWord();" style="width:150px"> 
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="doSearch()"></div>
				</td>
            </tr>
            <tr>
              <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			  <z:datagrid id="dg1" method="com.sinosoft.cms.document.WeixinPlatform.dg1DataBind" size="15">
                <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
                  <tr ztype="head" class="dataTableHead">
                    <td  width="4%" ztype="RowNo"><b>序号</b></td>
                    <td width="3%" ztype="selector" field="id">&nbsp;</td>
                    <td width="5%"><b>场景值</b></td>
                    <td width="25%"><b>场景名称</b></td>
                    <td width="10%"><b>二维码链接</b></td>
                    <td width="20%"><b>创建时间</b></td>
                    <td width="20%"><b>创建人</b></td>
                  </tr>
                  <tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
                    <td align="center">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${sceneId}</td>
                    <td>${sceneName}</td>
                    <td><a href="${ticket}" target="_blank" alt="${ticket}">点击获取</a></td>
                    <td>${AddTime}</td>
                    <td>${AddUser}</td>
                  </tr>
                  <tr ztype="pagebar">
                    <td colspan="5">${PageBar}</td>
                  </tr>
                </table>
              </z:datagrid></td>
            </tr>
        </table></td>
      </tr>
    </table>
	</body>
</html>
