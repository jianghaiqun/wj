<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="ForumInit.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title><%=ForumUtil.getCurrentName(request)%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="forum.css" rel="stylesheet" type="text/css">
<!--<link href="skins/zving/default.css" rel="stylesheet" type="text/css">-->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<%@ include file="../Include/Head.jsp" %>
</head> 
<script type="text/javascript">
	Page.onLoad(function() {
		if(!$V("UName")) {
			$("table1").style.display = 'none';
		}
	});
	
	function searchUser() {
		window.location="ForbidUser.jsp?UserName="+$V("UserName");
	}
	
	function editInfoSave() {
		var dc = Form.getData($F("postform"));
		dc.add("SiteID",$V("SiteID"));
		if(Verify.hasError()){
		 	 return;
	    }
		Server.sendRequest("com.sinosoft.bbs.MasterPanel.editInfoSave",dc,function(response){
			Dialog.alert(response.Message, function(){
				if(response.Status==1){
					window.location="MasterPanel.jsp";
				}
			});
		});
	}
</script>
<body>
<%@ include file="../Include/Top.jsp" %>
<z:init method="com.sinosoft.bbs.MasterPanel.init">
	<div class="wrap">
		<div id="menu" class="forumbox"> <span class="fl"><a href="Index.jsp?SiteID=${SiteID}">${BBSName}</a>  &raquo; 版主管理面板</span><span class="fr">${Priv}</span></div>
		<br>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
<input type="hidden" id="SiteID" value="${SiteID}">
  <tr valign="top">
    <td width="200"><div class="side">
            <h4>用户管理</h4>
             <hr class="shadowline"/>
            <div class="sideinner">
              <ul class="sidemenu">
                <li >${AllowEditUser}</li>
                <li class="current">${AllowForbidUser}</li>
                <!-- li><a href="#">用户权限</a></li-->
              </ul>
            </div><br/>
            <h4>板块管理</h4>
             <hr class="shadowline"/>
            <div class="sideinner">
              <ul class="sidemenu">
                <li>${AllowEditForum}</li>
                <!--li><a href="#">版主推荐</a></li-->
              </ul>
            </div><br/>
            <h4>主题管理</h4>
             <hr class="shadowline"/>
            <div class="sideinner">
              <ul class="sidemenu">
                <li>${AllowVerfyPost}</li>
                <!--li><a href="MyArticles.jsp">审核回复</a></li-->
              </ul>
            </div><br/>
            <h4>其他</h4>
             <hr class="shadowline"/>
            <div class="sideinner">
              <ul class="sidemenu">
              	<!--li><a href="#">管理日志</a></li-->
                <li><a href="Index.jsp">返回论坛</a></li>
                <li><a href="Index.jsp">退出管理</a></li>
              </ul>

            </div>
          </div>
          </td>
    <td width="20">&nbsp;</td>
    <td><z:init method="com.sinosoft.bbs.MasterPanel.searchUserInit">
    <form method="post" id="postform">
	<div class="forumbox">
	  <span class="fr">带 <span style=" font-family:'宋体'; color:#F60">*</span> 标示为必填项</span><h4>禁止用户-搜索</h4>
   <h5 style="border-bottom:1px dotted #ccc; color:#999999; padding:3px; margin:5px;">请输入用户名进行搜索：</h5><table width="100%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
   <tr>
    <td width="50" align="right">用户名：</td>
    <td width="200"><input type="text" name="UserName" id="UserName"/></td>
    <td><input type="button" name="search" id="search" onclick="searchUser()" value="搜索"/></td>
  </tr>  

</table>
<h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;">搜索结果</h5>
<table id="table1" width="100%" border="0" cellspacing="0" cellpadding="0" class="forumTable">
  <input type="hidden" id="UName" value="${UserName}" />
   <tr>
    <td width="100" align="right">用户名：</td>
    <td width="200">${UserName}</td>
  </tr>
  <tr>
    <td width="100" align="right">当前状态：</td>
    <td><input name="NickName" id="NickName" value="${NickName}" /></td>
  </tr>
  <tr>
    <td width="100" align="right">变更为：</td>
    <td><textarea rows="30" cols="70" name="ForumSign" id="ForumSign">${ForumSign}</textarea></td>
  </tr>
  <tr>
  	<td></td>
  	<td><input type="button" name="submit" id="submit" value='提交' onclick="editInfoSave();" /></td>
  </tr>
</table>
<h5 style="border-bottom:1px dotted #ccc; color:#333; padding:3px; margin:5px;"></h5>
</div>
</form>
</z:init></td>
  </tr>
</table>
</div>
</z:init>
<%@ include file="../Include/Bottom.jsp" %>
</body>

</html>