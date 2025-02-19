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
	var url = window.location.search;
	
	function orderBy(){
		var dc = Form.getData($F("form3"));
		DataList.setParam("list1",Constant.PageIndex,0);
		DataList.setParam("list1","SiteID",$V("SiteID"));
		DataList.setParam("list1","addtime",dc.get("time"));
		DataList.setParam("list1","orderby",dc.get("orderby"));
		DataList.setParam("list1","ascdesc",dc.get("ascdesc"));
		DataList.loadData("list1");
	}
	
	function perInfoSave() {
		var dc = Form.getData($F("postform"));
		dc.add("SiteID",$V("SiteID"));
		Server.sendRequest("com.sinosoft.bbs.ControlPanel.perInfoSave",dc,function(response){
			Dialog.alert(response.Message);
			if(response.Status==1){
				$D.close();
			}
		});
	}
	
	function del(ID){
		Dialog.confirm("确定删除选中的帖子吗？",function(){
			var dc = new DataCollection();
			dc.add("ID",ID);
			dc.add("SiteID",$V("SiteID"));
	   		Server.sendRequest("com.sinosoft.bbs.MySubject.del",dc,function(response){
				if(response.Status==1){
					DataList.loadData("list1");
				}
			});
		});
	}
	
	function applyDel(ID) {
		var dc = new DataCollection();
			dc.add("ID",ID);
			dc.add("SiteID",$V("SiteID"));
	   		Server.sendRequest("com.sinosoft.bbs.PostAudit.applyDel",dc,function(response){
	   			Dialog.alert(response.Message);
				if(response.Status==1 || response.Status==2){
					DataList.loadData("list1");
				}
		});
	}
	
	function editTheme(ID,ForumID,ThemeID){
	var diag = new Dialog("Diag1"); 
		diag.Width = 700;
		diag.Height =400;
		diag.Title = "添加板块";
		diag.URL = "BBS/EditPanelPost.jsp?ForumID="+ForumID+"&ThemeID="+ThemeID+"&ID="+ID+"&SiteID="+$V("SiteID");
		//diag.OKEvent = editSave;
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.hide();
	}
</script>
<body>
<%@ include file="../Include/Top.jsp" %>
	<z:init method="com.sinosoft.bbs.ControlPanel.init">
	<div class="wrap">
	${redirect}
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=${SiteID}">会员中心</a>  &raquo; 我发表的帖子
</div>
	
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<input type="hidden" value="${SiteID}" id="SiteID">
  <tr valign="top">
    <td width="200"><%@ include file="../Include/Menu.jsp" %></td>
    <td width="20">&nbsp;</td>
    <td>  <div class="mainbox forumbox">
    <h4>我发表的文章</h4>
    <ul class="tabs">
	<li class="current"><a href="MyArticles.jsp?SiteID=${SiteID}">我的主题</a></li>

	<li><a href="MyPosts.jsp?SiteID=${SiteID}">我的回复</a></li>
	</ul>
    <form method="post" name="moderate">
      <table width="100%" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable">
        <thead class="category">
          <tr>
            <th width="100">板块</th>
            <th>标题</th>
            <th width="140">发表时间</th>
            <th width="50">状态</th>
            <th width="70">操作</th>
          </tr>
        </thead>
        <z:datalist id="list1" size="10" method="com.sinosoft.bbs.MySubject.getList">
        <tbody>
          <tr>
            <td>${Name}</td>
            <td ><span id="thread_6417"><a href="./Post.jsp?ThemeID=${ID}&ForumID=${ForumID}&SiteID=${SiteID}&User=current" >${Subject}</a></span></td>
            <td><em>${AddTime}</em></td>
            <td>${AuditStatus}</td>
            <td>${Operation}</td>
          </tr>
        </tbody>
        </z:datalist>
      </table>
    </form>
  </div>
  <div style="padding:5px;">
    <z:pagebar target="list1"></z:pagebar>
  </div>
  <div id="footfilter">
    <form method="get" id="form3"><input type="hidden" name="fid" value="5"> 查看 <select name="filter" id="time">
	<option value="0">全部主题</option>
	<option value="86400000">1 天以来主题</option>
	<option value="172800000">2 天以来主题</option>
	<option value="604800000">1 周以来主题</option>
	<option value="2592000000">1 个月以来主题</option>
	<option value="7948800000">3 个月以来主题</option>
	<option value="15897600000">6 个月以来主题</option>
	<option value="31536000000">1 年以来主题</option>
</select> 排序方式 <select name="orderby" id="orderby">
	<option value="LastPostTime" >回复时间</option>
	<option value="AddTime">发布时间</option>
	<option value="ReplyCount">回复数量</option>
	<option value="ViewCount">浏览次数</option>
</select> <select name="ascdesc" id="ascdesc">
	<option value="DESC"  >按降序排列</option>
	<option value="ASC">按升序排列</option>
</select> &nbsp;
<button type="button" onclick="orderBy()">提交</button>
</form>

  </div>
</td>
  </tr>
</table>

</div>
</z:init>
<%@ include file="../Include/Bottom.jsp" %>

</body>
</html>