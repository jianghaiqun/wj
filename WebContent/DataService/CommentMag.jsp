<% 
/* JSP程序简要描述信息 
************************************************************** 
*  程序名 :   
*  建立日期:   
*  作者   :  作者的名字和邮件地址 
*  模块   :  由哪个模块使用和维护 
*  描述   :  主要功能和使用场合 
*  备注   :   
* ------------------------------------------------------------ 
*  修改历史 
* 序号           日期                           修改人                                修改原因 
* 1  2014-07-09   jiaomengying   req20140702001-cms评论管理
* 2  2014-12-08   jiaomengying   req20141205003-cms后台评论关联订单
************************************************************** 
*/ 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%String KID=PubFun.getKeyValue();%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>评论管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
		DataList.setParam("dg1","CatalogID",$V("SelectCatalogID"));
		DataList.setParam("dg1","CatalogType",$V("CatalogType"));
		DataList.setParam("dg1","VerifyStatus",$V("VerifyStatus"));
		DataList.setParam("dg1","isBuy",$V("isBuy"));
		DataList.loadData("dg1");
	}
);

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
		dc.add("type","评论");
		Server.sendRequest("com.sinosoft.cms.dataservice.Comment.del",dc,function(response){
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
	DataList.setParam("dg1","isBuy",$V("isBuy"));
	DataList.loadData("dg1");
}
//是否通过上传
function changeUploadStatus(){
	DataList.setParam("dg1","uploadStatus",$V("uploadStatus"));
	DataList.setParam("dg1","isBuy",$V("isBuy"));
	DataList.loadData("dg1");
}
var KID = "<%=KID%>";
//显示订单详细信息
function showOrderDetail(orderSn) {
	var queryID = orderSn;
	var KKID = '';
	var dc = new DataCollection();
	dc.add("KID", KID + queryID);
	var method = "cn.com.sinosoft.util.StringUtilC.md5Hex";

	Server.sendRequest(method, dc, function(response) {
		KKID = response.get("KID");
		var win = window.open(
				'../shop/order_config_new!linkOrderDetails.action?orderSn='
						+ queryID + "&KID=" + KKID, '_blank');
		//win.focus();
	});

}

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
//产品搜索
function setProductName(){
	DataList.setParam("dg1","productName",$V("searchProduct"));
	DataList.setParam("dg1","isBuy",$V("isBuy"));
	DataList.loadData("dg1");
}

function reloadList(){
	DataList.setParam("dg1","isBuy",$V("isBuy"));
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
		Dialog.alert("请先选择要审核的评论！");
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
	});
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
		Dialog.alert("请先选择要回复的评论！");
		return;
	}
	
	if(arr.length>1){
		Dialog.alert("您选择了太多的评论，请您只选择其中的一条！");
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
			DataList.setParam("dg1","VerifyStatus",$V("VerifyStatus"));
			DataList.loadData("dg1");
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
	for(var i=0;i<arr.length;i++){
		arr[i].checked = selectFlag;
	}
}
//显示修改时间
function playTime(CID){
	document.getElementById('commentTime_'+CID).style.display="";
	document.getElementById('AddTime_'+CID).style.display="none";
}
//保存修改时间
function saveTime(CID){
	var time = $V("commentTime_"+CID);
	var dc = new DataCollection();
	dc.add("time",time);
	dc.add("CID",CID);
	Server.sendRequest("com.sinosoft.cms.dataservice.Comment.saveCommTime",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataList.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	});
}

function saveCommNum(CID){
	if(Verify.hasError()){
		return;
	}
	var sortNum = $V("sortNum_"+CID);
	var praisedNum = $V("praisedNum_"+CID);
	var dc = new DataCollection();
	dc.add("sortNum",sortNum);
	dc.add("praisedNum",praisedNum);
	dc.add("CID",CID);
	Server.sendRequest("com.sinosoft.cms.dataservice.Comment.saveCommSort",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataList.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	});
}

function lookInfo(CID){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 450;
	diag.Title = "操作记录";
	diag.URL = "DataService/CommentOperaDialog.jsp?ID="+CID;
	diag.onLoad = function() {
	};
	diag.ShowMessageRow = true;
	diag.MessageTitle = "操作记录";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value = "关  闭";
}
</script>
</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon002a1.gif" /> 评论管理</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
                    <z:tbutton onClick="selectAll()"><img src="../Icons/icon018a11.gif" />全选</z:tbutton>
                    <z:tbutton onClick="Pass()"><img src="../Icons/icon018a2.gif" />审核通过</z:tbutton>
                    <z:tbutton onClick="NoPass()"><img src="../Icons/icon018a1.gif" />审核不通过</z:tbutton>
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
                     &nbsp;<font color="#999999">审核状态</font>
                    <z:select id="VerifyStatus" style="width:60px;" onChange="changeVerifyStatus();">
                        <span value="X">未审核</span>
                        <span value="Y">已通过</span>
                        <span value="N">未通过</span>
                        <span value="All">全部</span>
                    </z:select>
                    &nbsp;<font color="#999999">产品搜索</font>
	                <input type="text" id="searchProduct" onChange="setProductName();">
                    <input type="hidden" id="CatalogID" name="CatalogID" value=""/>
                    <input type="hidden" id="isBuy" name="isBuy" value="1"/>
                    &nbsp;<font color="#999999">是否通过上传</font>
                    <z:select id="uploadStatus" onChange="changeUploadStatus();">
                    	<span value=""></span>
                        <span value="Y">是</span>
                        <span value="N">否</span>
                    </z:select>
                    </td>
				</tr>
				<tr>   
					<td style="padding:0px 5px;">
					<z:datalist id="dg1" method="com.sinosoft.cms.dataservice.Comment.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
                                <td width="5%" align="right"><input type="checkbox" name="CommentID" value="${ID}" /></td>
								<td width="40%"><a href="${PreLink}" title="${Name}" target="_blank"><b>${Name}</b></a>&nbsp;&nbsp;
                                	关联ID: ${RelaID}&nbsp;&nbsp;&nbsp;&nbsp;<span style="display:${replayStyle};color:red;">已回复</span>
                                </td>
                                <td width="18%">关联订单项 </td>
                                <td width="10%"> </td>
                                <td width="22%" align="right" style=" font-weight:normal;">
                                <img src="../Icons/icon034a1.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="Reply(${ID})">回复</a>
                                <img src="../Icons/icon404a4.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="Pass(${ID})">审核通过</a>
                                <img src="../Icons/icon404a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="NoPass(${ID})">审核不通过</a>
                                <img src="../Icons/icon034a3.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="del(${ID})">删除</a>
                                </td>
							</tr>
                            <tr>
                            	<td align="right">评论标题：</td>
                                <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal;" height="20"><span title="${Title}">${Title}</span></td>
                                <td rowspan="2" style="border-right:1px solid #DDDDDD;white-space:normal;">
                                	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr>
                                    	<td  style=" border-bottom:none;">订单号：</td>
                                        <td  style=" border-bottom:none;"><a  style="cursor: hand;" onClick="showOrderDetail('${ordersn}')">${ordersn}</a></td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;">订单支付时间：</td>
                                        <td style=" border-bottom:none;">${receiveDate}</td>
                                    </tr>
                                </table>
                                </td>
                                <td rowspan="2" style="border-right:1px solid #DDDDDD;white-space:normal;"> 
                                	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr>
                                    	<td style=" border-bottom:none;">点赞数：<input type="text" id="praisedNum_${ID}" value="${praisedNum}" size="10" verify="点赞数|Int" /></td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;">排序数：<input type="text" id="sortNum_${ID}" value="${sortNum}" size="10" verify="排序数|Int"/></td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;"><a href="#;" onClick="saveCommNum(${ID});">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#;" onClick="lookInfo(${ID});">查看操作记录</a></td>
                                    </tr>
                                </table>
                                </td>
                                <td rowspan="2" height="120">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr>
                                    	<td width="41%" align="right" style=" border-bottom:none;">评论者：</td>
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
                                    <tr ondblclick="playTime(${ID});">
                                    	<td style=" border-bottom:none;" align="right">留言时间：</td>
                                        <td style=" border-bottom:none;"><span id="AddTime_${ID}">${AddTime}</span>
	                                        <input type="text" id="commentTime_${ID}" value="${AddTime}" style="display:none">
	                                        &nbsp;&nbsp;&nbsp;&nbsp;<a href="#;" onClick="saveTime(${ID});">保存</a>
                                        </td>
                                    </tr>
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">审核状态：</td>
                                        <td style=" border-bottom:none;"><span title="${VerifyFlagName}" style="color:${FlagColor}">${VerifyFlagName}</span></td>
                                    </tr>
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
