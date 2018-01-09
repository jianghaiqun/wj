<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>咨询管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	DataList.setParam("dg1","CatalogID",$V("SelectCatalogID"));
	DataList.setParam("dg1","CatalogType",$V("CatalogType"));
	DataList.setParam("dg1","isBuy",$V("isBuy"));
	DataList.loadData("dg1");
});

function del(CID){ 
	if(CID===0){
		Dialog.alert("没有记录!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的咨询！");
		return;
	}
	Dialog.confirm("您确认要删除咨询吗?",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		dc.add("type","咨询");
		Server.sendRequest("com.sinosoft.cms.dataservice.Comment.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.loadData("dg1");
				}
			});
		});
	})
}

/* function changeVerifyStatus(){
	DataList.setParam("dg1","VerifyStatus",$V("VerifyStatus"));
	DataList.setParam("dg1","isBuy",$V("isBuy"));
	DataList.loadData("dg1");
} */

function changeCatalogList(){
	if(isIE){
		$("SelectCatalogID").listURL="DataService/CommentCatalogSelector.jsp?Type="+$V("CatalogType");
	}else{
		$("SelectCatalogID").setAttribute("listURL","DataService/CommentCatalogSelector.jsp?Type="+$V("CatalogType"));
	}
	Selector.setValueEx("SelectCatalogID",'','');
	$S("CatalogID","");
	DataList.setParam("dg1","CatalogID","");
	DataList.setParam("dg1","CatalogType",$V("CatalogType"));
	DataList.setParam("dg1","isBuy",$V("isBuy"));
	DataList.loadData("dg1");
}

function setCatalogID(){
	if($V("SelectCatalogID")!=0){
		$S("CatalogID",$V("SelectCatalogID"));
		DataList.setParam("dg1","CatalogID",$V("SelectCatalogID"));
		DataList.setParam("dg1","CatalogType",$V("CatalogType"));
		DataList.setParam("dg1","isBuy",$V("isBuy"));
		DataList.loadData("dg1");
	}
}

function reloadList(){
	DataList.loadData("dg1");
}

function Pass(CID){
	if(CID===0){
		Dialog.alert("没有记录!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要审核的咨询！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join(","));
	dc.add("Type","Pass");
	Server.sendRequest("com.sinosoft.cms.dataservice.Comment.Verify",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataList.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

function Reply(CID){
	if(CID===0){
		Dialog.alert("没有记录!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要回复的咨询！");
		return;
	}
	
	if(arr.length>1){
		Dialog.alert("您选择了太多的咨询，请您只选择其中的一条！");
		return;
	}
	var diag = new Dialog("Diag");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "回复列表";
	var url = "DataService/CustomerServiceReply.jsp?ID="+arr[0];
	diag.URL = encodeURI(url);
	diag.ShowMessageRow = true;
	diag.MessageTitle = "回复内容";
	diag.OKEvent=save;
	diag.show();
}

function save(){
	var dc = $DW.Form.getData("fm_reply");
	Server.sendRequest("com.sinosoft.cms.dataservice.Comment.save",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
		if(response.Status==1){
			$D.close();
			window.location.reload();
		}
		});
	});
}

function NoPass(CID){
	if(CID===0){
		Dialog.alert("没有记录!");
		return;
	}
	var arr = new Array();
	if(!CID||CID==""){
		arr = $NV("CommentID");
	}else{
		arr[0] = CID;	
	}
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要审核的咨询！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join(","));
	dc.add("Type","NoPass");
	Server.sendRequest("com.sinosoft.cms.dataservice.Comment.Verify",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataList.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

var selectFlag = false;
function selectAll(){
	if(selectFlag){
		selectFlag = false;
	}else{
		selectFlag = true;
	}
	var arr = $N("CommentID");
	for(var i=0;i<arr.length;i++){
		arr[i].checked = selectFlag;
	}
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon002a1.gif" /> 咨询管理</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
                    <z:tbutton onClick="selectAll()"><img src="../Icons/icon018a11.gif" />全选</z:tbutton>
                    <%-- <z:tbutton onClick="Pass()"><img src="../Icons/icon018a2.gif" />审核通过</z:tbutton>
                    <z:tbutton onClick="NoPass()"><img src="../Icons/icon018a1.gif" />审核不通过</z:tbutton> --%>
                    <z:tbutton onClick="del()"><img src="../Icons/icon018a3.gif" />删除</z:tbutton>
                    &nbsp;&nbsp;<font color="#999999">选择栏目类型</font>
                  <z:select id="CatalogType" style="width:60px;" onChange="changeCatalogList();"> 
                        <span value="1">文档</span>
                        <span value="4">图片</span>
                        <span value="5">视频</span>
                        <span value="6">音频</span>
                        <span value="7">附件</span>
                    </z:select>
                    &nbsp;<font color="#999999">选择栏目</font>
                     <z:select id="SelectCatalogID" style="width:120px;" onChange="setCatalogID();" listWidth="200" listHeight="300" listURL="DataService/CommentCatalogSelector.jsp?Type=1"></z:select>
                     &nbsp;<%-- <font color="#999999">审核状态</font>
                    <z:select id="VerifyStatus" style="width:60px;" onChange="changeVerifyStatus();">
                        <span value="X">未审核</span>
                        <span value="Y">已通过</span>
                        <span value="N">未通过</span>
                        <span value="All">全部</span>
                    </z:select> --%>
                    <input type="hidden" id="CatalogID" name="CatalogID" value=""/>
                    <input type="hidden" id="isBuy" name="isBuy" value="0"/>
                    </td>
				</tr>
				<tr>   
					<td style="padding:0px 5px;">
					<z:datalist id="dg1" method="com.sinosoft.cms.dataservice.Comment.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
                                <td width="8%" align="right"><input type="checkbox" name="CommentID" value="${ID}" /></td>
								<td width="65%"><a href="${PreLink}" title="${Name}" target="_blank"><b>${Name}</b></a>&nbsp;&nbsp;
                                关联ID: ${RelaID}&nbsp;&nbsp;&nbsp;&nbsp;<span style="display:${replayStyle};color:red;">已回复</span></td></td>
                                <td width="27%" align="right" style=" font-weight:normal;">
                                <img src="../Icons/icon034a1.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="Reply(${ID})">回复</a>
                                <%-- <img src="../Icons/icon404a4.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="Pass(${ID})">审核通过</a>
                                <img src="../Icons/icon404a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="NoPass(${ID})">审核不通过</a> --%>
                                <img src="../Icons/icon034a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="del(${ID})">删除</a>
                                </td>
							</tr>
                            <tr>
                            	<td align="right">咨询标题：</td>
                                <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal;" height="20"><span title="${Title}">${Title}</span></td>
                                <td rowspan="2" height="120">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr>
                                    	<td width="41%" align="right" style=" border-bottom:none;">咨询者：</td>
                                        <td width="59%" style=" border-bottom:none;">${AddUser}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">所属栏目：</td>
                                        <td style=" border-bottom:none;"><span title="${CatalogName}">${CatalogName}</span></td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">IP：</td>
                                        <td style=" border-bottom:none;">${AddUserIP}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">留言时间：</td>
                                        <td style=" border-bottom:none;">${AddTime}</td>
                                    </tr>
                                    <%-- <tr>
                                    	<td style=" border-bottom:none;" align="right">审核状态：</td>
                                        <td style=" border-bottom:none;"><span title="${VerifyFlagName}" style="color:${FlagColor}">${VerifyFlagName}</span></td>
                                    </tr> --%>
                                </table>
                                </td>
                            </tr>
                            <tr>
                              <td align="right">内容：</td>
                              <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal" height="100">
                              <span title="${Content}">${Content}</span>
                              </td>
                            </tr>
						</table>
				  </z:datalist>
                  <z:pagebar target="dg1" />
                  </td>
				</tr>
			</table>
		</td>
		</tr>
	</table>
	</body>
</html>
