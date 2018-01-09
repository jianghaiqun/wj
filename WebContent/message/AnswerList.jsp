<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>解答管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function del(id){
	Dialog.confirm("您确认要删除评论吗?",function(){
		var dc = new DataCollection();
		dc.add("ID",id);
		Server.sendRequest("com.sinosoft.message.AuditAnswerUI.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataList.loadData("dg1");
				}
			});
		});
	});
}
function adoption(id,questionId,state){
	if(state=='01'){
		Dialog.alert("该问题已采纳答案！");
		return;
	}
	Dialog.confirm("您确认要操作吗?",function(){
		var dc = new DataCollection();
		dc.add("ID",id);
		dc.add("QuestionId",questionId);
		Server.sendRequest("com.sinosoft.message.AuditAnswerUI.save",dc,function(response){
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

function checkList(id){
	DataList.setParam("dg1","QuestionId",id);
	DataList.loadData("dg1");
}
function changeStatus(){
	DataList.setParam("dg1","Status",$V("Status"));
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
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon002a1.gif" /> 解答列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
                   &nbsp;<font color="#999999">采纳状态</font>
                    <z:select id="Status" style="width:60px;" onChange="changeStatus();">
                        <span value="All">全部</span>
                        <span value="00">未采纳</span>
                        <span value="01">已采纳</span>
                    </z:select>
                    <%--  &nbsp;&nbsp;<font color="#999999">选择栏目类型</font>
                    <z:select id="CatalogType" style="width:60px;" onChange="changeCatalogList();">
                        <span value="1">文档</span>
                        <span value="4">图片</span>
                        <span value="5">视频</span>
                        <span value="6">音频</span>
                        <span value="7">附件</span>
                    </z:select>
                    &nbsp;<font color="#999999">选择栏目</font>
                    <z:select id="SelectCatalogID" style="width:120px" onChange="setCatalogID()" listWidth="200" listHeight="300" listURL="DataService/CommentCatalogSelector.jsp?Type=1"> </z:select>
               
                    <span title="${aState}" style="color:#ff0000 ">审核时请选择栏目归属来确定问题发布位置</span>
                    <input type="hidden" id="CatalogID" name="CatalogID" value=""/> --%>
                    </td>
				</tr>
				<tr>   
					<td style="padding:0px 5px;">
					<z:datalist id="dg1" method="com.sinosoft.message.AuditAnswerUI.dg1DataBind1" size="10">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
                                <td width="8%" align="right"><%-- <input type="checkbox" name="CommentID" value="${ID}" /> --%></td>
								<td width="65%">关联ID: ${QuestionId}</td>
                                <td width="27%" align="left" style=" font-weight:normal;">
                                <img src="../Icons/icon404a4.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="adoption(${ID},${QuestionId},${aState})">采纳</a>
                                <img src="../Icons/icon034a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="del(${ID})">删除</a>
                                </td>
							</tr>
                            <tr>
                              <td align="right">内容：</td>
                              <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal" height="100">
                              <span title="${Content}">${Content}</span></td>
                                <td rowspan="2" height="120">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr>
                                    	<td width="41%" align="right" style=" border-bottom:none;">解答用户：</td>
                                        <td width="59%" style=" border-bottom:none;">${UserEmail}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">用户IP：</td>
                                        <td style=" border-bottom:none;">${AddUserIP}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">解答时间：</td>
                                        <td style=" border-bottom:none;">${AddDate}</td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">采纳状态：</td>
                                        <td style=" border-bottom:none;"><span title="${cState}" style="color:${FlagColor}">${cState}</span></td>
                                    </tr>
                                </table>
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
