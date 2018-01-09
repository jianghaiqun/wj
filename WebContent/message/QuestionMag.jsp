<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>提问管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

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
		Dialog.alert("请先选择要删除的评论！");
		return;
	}
	Dialog.confirm("您确认要删除评论吗?",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.sinosoft.message.AuditQuestionUI.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.loadData("dg1");
				}
			});
		});
	});
}

function changeVerifyStatus(){
	DataList.setParam("dg1","VerifyStatus",$V("VerifyStatus"));
	DataList.loadData("dg1");
}

function changeCatalogList(){
	if(isIE){
		$("SelectCatalogID").listURL="DataService/CommentCatalogSelector.jsp?Type="+$V("CatalogType");
	}else{
		$("SelectCatalogID").setAttribute("listURL","DataService/CommentCatalogSelector.jsp?Type="+$V("CatalogType"));
	}
	Selector.setValueEx("SelectCatalogID",'','');
	$S("CatalogID","");
/* 	DataList.setParam("dg1","CatalogID","");
	DataList.setParam("dg1","CatalogType",$V("CatalogType"));
	DataList.loadData("dg1"); */
}

function setCatalogID(){
	if($V("SelectCatalogID")!=0){
		$S("CatalogID",$V("SelectCatalogID"));
/* 		DataList.setParam("dg1","CatalogID",$V("SelectCatalogID"));
		DataList.setParam("dg1","CatalogType",$V("CatalogType"));
		DataList.loadData("dg1"); */
	}
}

function reloadList(){
	DataList.loadData("dg1");
}

function Pass(CID){//修改完毕
	if(CID==0){
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
		Dialog.alert("请先选择要审核的提问！");
		return;
	}
	if(!$V("ContentSign")||$V("ContentSign")==""){
		Dialog.alert("请先选择内容类型！");
		return;
	}
	if(!$V("CatalogID")||$V("CatalogID")==""){
		Dialog.alert("请先选择栏目归属！");
		return;
	}
	Dialog.confirm("您确认要操作吗?",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join(","));
		dc.add("Type","Pass");
		dc.add("CatalogID",$V("CatalogID"));
		dc.add("ContentSign",$V("ContentSign"));
		Server.sendRequest("com.sinosoft.message.AuditQuestionUI.save",dc,function(response){
			if(response.Status==1){
				Dialog.alert(response.Message,function(){
					DataList.loadData("dg1");
				});
			}else{
			   Dialog.alert(response.Message);
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
		Dialog.alert("请先选择要审核的评论！");
		return;
	}
	Dialog.confirm("您确认要操作吗?",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join(","));
		dc.add("Type","NoPass");
		Server.sendRequest("com.sinosoft.message.AuditQuestionUI.save",dc,function(response){
			if(response.Status==1){
				Dialog.alert(response.Message,function(){
					DataList.loadData("dg1");
				});
			}else{
			   Dialog.alert(response.Message);
			}
		});
	});
}

var selectFlag = false;
function selectAll(){
	if(selectFlag){
		selectFlag = false;
	}else{
		selectFlag = true;
	}
	var arr = $N("CommentID");
	for( i=0;i<arr.length;i++){
		arr[i].checked = selectFlag;
	}
}

function search(){
	DataList.setParam("dg1","VerifyStatus",$V("VerifyStatus"));
	DataList.setParam("dg1","QTitle",$V("QTitle"));
	DataList.setParam("dg1","QUser",$V("QUser"));
	DataList.setParam("dg1","QTime",$V("QTime"));
	DataList.loadData("dg1");
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon002a1.gif" /> 问题列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
                    <z:tbutton onClick="selectAll()"><img src="../Icons/icon018a11.gif" />全选</z:tbutton>
                    <z:tbutton onClick="Pass()"><img src="../Icons/icon018a2.gif" />审核通过</z:tbutton>
                    <z:tbutton onClick="NoPass()"><img src="../Icons/icon018a1.gif" />审核不通过</z:tbutton>
                    <z:tbutton onClick="del()"><img src="../Icons/icon018a3.gif" />删除</z:tbutton>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#999999">内容类型</font>
					<z:select id="ContentSign" code="riskType" 
		            	listWidth="80" style="width:80px" onChange="" lazy="true"></z:select>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="#999999">选择栏目</font>
                 <z:select  id="SelectCatalogID" style="width:120px" onChange="setCatalogID()" listWidth="200" listHeight="300" listURL="DataService/CommentCatalogSelectorQ.jsp?Type=1&Sign=1"></z:select>
                    <span title="${aState}" style="color:#ff0000 ">审核时请选择栏目归属来确定问题发布位置</span>
                    <input type="hidden" id="CatalogID" name="CatalogID" value=""/>
                    </td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
						&nbsp;<font color="#999999">审核状态</font>
	                    <z:select id="VerifyStatus" style="width:60px;" onChange="changeVerifyStatus();">
	                        <span value="All">全部</span>
	                        <span value="00">未审核</span>
	                        <span value="01">已通过</span>
	                        <span value="02">未通过</span>
	                    </z:select>
	                    &nbsp;<font color="#999999">提问标题：</font>
	                    <input type="text" id="QTitle" name="QTitle" value=""/>
	                    &nbsp;<font color="#999999">提问用户：</font>
	                    <input type="text" id="QTitle" name="QUser" value=""/>
	                    &nbsp;<font color="#999999">提问时间：</font>
	                    <input type="text" id="QTime" style="width:90px;" ztype='date'>
	                    <input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()">
					</td>
				</tr>
				<tr>   
					<td style="padding:0px 5px;">
					<z:datalist id="dg1" method="com.sinosoft.message.AuditQuestionUI.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
                                <td width="8%" align="right"><input type="checkbox" name="CommentID" value="${ID}" />
                                <input type="hidden" id="aState" name="aState" value="${aState}" /></td>
								<td width="65%">关联ID: ${QuestionId}</td>
                               <%--  <td width="27%" align="right" style=" font-weight:normal;">
                                <img src="../Icons/icon404a4.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="Pass(${ID})">审核通过</a>
                                <img src="../Icons/icon404a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="NoPass(${ID})">审核不通过</a>
                                <img src="../Icons/icon034a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="del(${ID})">删除</a>
                                </td> --%>
							</tr>
                            <tr>
                            	<td align="right">提问标题：</td>
                                <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal;" height="20"><span title="${Title}">${Title}</span></td>
                                <td rowspan="2" height="50">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">栏目归属：</td>
                                        <td style=" border-bottom:none;">${catalogname}</td>
                                    </tr>
                                	<tr>
                                    	<td width="41%" align="right" style=" border-bottom:none;">提问用户：</td>
                                        <td width="59%" style=" border-bottom:none;">${UserEmail}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">用户IP：</td>
                                        <td style=" border-bottom:none;">${AddUserIP}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">提问时间：</td>
                                        <td style=" border-bottom:none;">${AddDate}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">审核状态：</td>
                                        <td style=" border-bottom:none;"><span title="${sState}" style="color:${FlagColor}">${sState}</span></td>
                                    </tr>
                                </table>
                                </td>
                            </tr>
                            <tr>
                              <td align="right">内容：</td>
                              <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal" height="50">
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
