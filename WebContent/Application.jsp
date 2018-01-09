<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%@page import="com.sinosoft.framework.User"%>
<%@page import="com.finance.util.JedisCommonUtil"%>
<%@page import="java.util.UUID"%>
<%@page import="com.sinosoft.framework.utility.StringUtil"%>
<html style="overflow:auto">
<% session.setAttribute("SPRING_SECURITY_LAST_USERNAME",User.getUserName());session.setAttribute("CMS_KEY_SINOSOFT","SINOSOFT8EF2677C515B19AF3363B817A1842E24");  %>
<%
	String user = User.getUserName();
	String authKey = null;
	String authCode = null;
	if(StringUtil.isNotEmpty(user)){
		authKey = user + "-" + UUID.randomUUID().toString();
		authCode = StringUtil.md5Hex(user+"-"+authKey);
		JedisCommonUtil.setString(2, authCode , authKey);
		JedisCommonUtil.expire(3, authCode, 7200);// 2*60*60=7200
	}
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=Config.getAppCode()%><%=Config.getAppName()%></title>
<link rel="shortcut icon" href="Include/favicon.ico" type="image/x-icon" />
<link href="Include/Default.css" rel="stylesheet" type="text/css">
<script src="Framework/Main.js" type="text/javascript"></script>
<script src="Framework/Check.js"></script>
<script>
var user = "<%=user%>",authCode="<%=authCode%>";
function search(){
	DataGrid.setParam("dg1","Keyword",$V("keyword"));
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.loadData("dg1");
}

var Priv = {};
var privTypes = [];

function getAllPriv() {
	var dc = new DataCollection();
	Server.sendRequest("com.sinosoft.platform.Login.getAllPriv",dc,function(response){
		parserPriv(response);
	});
}
 
function parserPriv(response){
	Priv.isBranchAdmin = response.get("isBranchAdmin")=="Y"? true:false;
	if(!Priv.isBranchAdmin){
		privTypes = response.get("privTypes").split(',');
		for(var k=0;k<privTypes.length;k++){
			Priv[privTypes[k]+"DT"] = response.get(privTypes[k]+"DT");
		}
	}
}

Priv.setBtn = function(privType,id,code,button){
	if(Priv.getPriv(privType,id,code)){
		button.enable();
	}else{
		button.disable();
	}
}

Priv.getPriv = function(privType,id,code){
	if(Priv.isBranchAdmin){
		return true;
	}
	if(privType=="site"){
		var dt = Priv.siteDT;
		if(dt){
			for(var i=0;i < dt.getRowCount(); i++){
				if(dt.get(i,"id")==id){
					return dt.get(i,code)=='1';
				}
			}
		}
		return null;
	}else {
		var dt = Priv[privType+"DT"];
		if(dt){
			for(var i=0;i < dt.getRowCount(); i++){
				if(dt.get(i,"id")==id){
					return dt.get(i,code)=='1';
				}
			}
			if(id==""||id==0||id=="0"){
				var siteid = $V("_SiteSelector");
				for(var i=0;i < dt.getRowCount()&&i < 0; i++){
					if(dt.get(i,"id")==id){
						return dt.get(i,code)=='1';
					}
				}
			}
			return false;
		}
		return false;
	}
}

Page.onLoad(function(){
	getNewMessageCount();
	countMonth();
	setInterval(getNewMessageCount, 1000*10);
});

//获取短消息
function getNewMessageCount(){
	var dc = new DataCollection();
	Server.sendRequest("com.sinosoft.cms.document.Message.getNewMessage",dc,function(response){
		var count = response.get("Count");
		$("MsgCount").innerHTML = count;
		if(response.get("PopFlag")=="1"){
			var mp = new MsgPop(response.get("Message"),null,450,null,30);
			mp.show();
		}
	});
}

//定位到短消息菜单
function getMessage(){
	var mainEle = $("_Menu_120");
	Application.onMainMenuClick(mainEle);
	var ele = $("_ChildMenuItem_132");
	Application.onChildMenuClick(ele)
}

</script>
</head>
<body style="min-width:1003px">
<z:init method="com.sinosoft.platform.Application.init">
	<table id="_TableHeader" width="100%" border="0" cellpadding="0"
		cellspacing="0" class="bluebg"
		style="background:#3388bb url(Platform/Images/vistaBlue.jpg) repeat-x left top;">
		<tr>
			<td height="70" valign="bottom">
			<table height="70" border="0" cellpadding="0" cellspacing="0"
				style="position:relative;">
				<tr>
					<td style="padding:0"><img src="Platform/Images/logo.gif"></td>
				</tr>
				<tr>
					<td valign="bottom" nowrap="nowrap">
					<div style=" float:left; background:url(Platform/Images/selectsitebg.gif) no-repeat right top; color:#666666; padding:6px 23px 0 10px; margin-bottom:-2px;"><span
						id="selectsite" style="display:inline">当前站点: <z:select
						id="_SiteSelector" style="width:130px;" listWidth="250"
						onChange="Application.onParentSiteChange();">
					 ${Sites} 
               </z:select> </span> <img src="Platform/Images/selectsite_hide.gif"
						width="13" height="13" align="absmiddle" style="cursor:pointer;"
						title="隐藏"
						onClick="a=$('selectsite').style.display=='none'?false:true;$('selectsite').style.display=a?'none':'inline';this.src=a?'Platform/Images/selectsite_show.gif':'Platform/Images/selectsite_hide.gif';this.title=a?'展开':'隐藏'"></div>
					</td>
				</tr>
			</table>
			</td>
			<td valign="bottom">
			<div style="text-align:right; margin:0 20px 15px 0;">当前用户：<b><%=User.getRealName()%></b>
			&nbsp;[&nbsp;<a href="javascript:void(0);" onClick="getMessage()">短消息(<span
				id="MsgCount">0</span>)</a> | <a href="javascript:void(0);"
				onClick="Application.logout();">退出登录</a> | <a href="<%=Config.getContextPath()+"wwwroot/"+Application.getCurrentSiteAlias()+"/index.shtml"%>">返回首页</a> | <a
				href="javascript:Application.changePassword(0);">修改密码</a> ]</div>
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center">
					<div id="_Navigation" class="navigation">
						${Menu}
					</div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td style="padding:6px 3px 3px 3px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="2" height="2"
						style="background:url(Platform/Images/jiao.gif) no-repeat right bottom;"></td>
					<td width="100%" id="_HMenutable" class="tabpageBar"></td>
				</tr>
				<tr valign="top">
					<td align="right" id="_VMenutable" class="verticalTabpageBar">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td align="right" valign="bottom">
								<div id="_ChildMenu"></div>
								</td>
							</tr>
						</table>
					</td>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0" id="maintable"
							style="border-bottom:#999999 1px solid; border-right:#999999 1px solid;">
							<tr>
								<td><iframe id='_MainArea' frameborder="0" width="100%"
									height="500" src='about:blank' scrolling="auto"></iframe></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		var privDC = new DataCollection();
		privDC.parseXML(toXMLDOM(htmlDecode("${Privileges}")));
		parserPriv(privDC);
	</script>
</z:init>
</body>
</html>
