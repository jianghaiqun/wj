<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.sinosoft.framework.User"%>
<%@taglib uri="controls" prefix="z"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<z:init method="com.sinosoft.cms.dataservice.CommentService.init">
	<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>评论</title> <script src="<%=Config.getValue("JsResourcePath")%>/js/Framework/Main.js"
		type="text/javascript"></script>
	<script type="text/javascript">
var flag = 0;
  
function reply(ID,CatalogID,RelaID,CatalogType,SiteID){ 
	if(flag==0) {
		var subStr='${LoginHTML}';
		var str = "<form action='Comment.jsp' method='post' "+
				"onsubmit='return checkComment();'><input type='hidden' "+
				"id='RelaID' name='RelaID' value='"+RelaID+"' /> <input type='hidden' "+
				"id='CatalogID' name='CatalogID' value='"+ CatalogID +"' /> <input "+
				"type='hidden' id='CatalogType' name='CatalogType' value='"+CatalogType+"' /> "+
				"<input type='hidden' id='SiteID' name='SiteID' value='"+SiteID+"' /> "+
				"<input type='hidden' id='ParentID' name='ParentID' value='"+ID+"'/> "+
				"<textarea cols='78' rows='8' name='CmntContent' "+
				"id='CmntContent'></textarea><br/>";
		str+=subStr;
		str+="<input type='submit' align='right' name='CmntSubmit' "+
				"id='CmntSubmit' value='提交评论'	class='btn' /></form>";
		$(ID+"textarea").innerHTML=str;
		flag = 1;
	} else {
		$(ID+"textarea").innerHTML="";
		flag = 0;
	}
}

function updateComment(ID,AddUser,AddTime,AddUserIP) {
	var dc = new DataCollection();
	dc.add("ID",ID);
	dc.add("replyContent",$V('CmntContent'));
	dc.add("replyAddUser",AddUser);
	dc.add("replyAddTime",AddTime);
	dc.add("replyAddUserIP",AddUserIP);
	
	Server.sendRequest("com.sinosoft.cms.dataservice.Comment.updateSupporterCount",dc,function(response){
		if(response.Status==1){	
			Dialog.alert(response.Message);
			window.parent.location.reload();
		}
	});
}

function addSupporterCount(ID){
	
	var id = ID+"supporter";
	var dc = new DataCollection();
	dc.add("ID",ID);
	
	Server.sendRequest("com.sinosoft.cms.dataservice.Comment.addSupporterCount",dc,function(response){
		  if(response.Status==1){
		  	$(id).innerHTML = "(" + response.get("count") + ")";
    		// alert("您的评论提交成功！");
    		Dialog.alert(response.Message);
		  }
	});
}

function addAntiCount(ID){
	var id = ID+"anti";
	var dc = new DataCollection();
	dc.add("ID",ID);
	
	Server.sendRequest("com.sinosoft.cms.dataservice.Comment.addAntiCount",dc,function(response){
	  	if(response.Status==1){
	  		$(id).innerHTML = "(" + response.get("count") + ")";
	  		// alert("您的评论提交成功！");
	  		Dialog.alert(response.Message);
	  	}
	});
}
</script>
	<style type="text/css">
.commentContainer {
	width: 100%;
	max-width: 950px;
	margin: 10px auto;
	font: 12px "宋体";
	text-align: left;
}

.commentBox {
	border: 1px solid #c8d8f2;
	background-color: #f5f8fd;
	padding: 17px 10px;
}

.commentBox h2 {
	background: url(../Images/comment.gif) no-repeat left 2px;
	font-size: 14px;
	margin: 0;
	padding: 0 0 9px 15px;
	border-bottom: 1px dashed #c8d8f2;
}

.commentBox h2 a {
	float: right;
	_display: inline;
	margin-top: -10px;
	*margin-top: -30px;
	font-size: 12px;
	color: #000099;
	text-decoration: none;
}

.commentBox h2 a span {
	color: #cc0000;
}

.commentBox h2 a:hover,.commentBox h2 a:active {
	color: #c00;
	text-decoration: underline;
}

.commentContent {
	_padding-bottom: 1px
}

.commentContent .time {
	color: #666;
	line-height: 20px;
	padding: 4px 0 0 5px;
}

.commentContent .content {
	line-height: 20px;
	padding: 2px 0 2px 5px;
}

.commentContent .fenxiang {
	color: #0033cc;
	float: right;
	height: 21px;
	padding: 3px 3px 0;
	text-align: center;
}

.commentContent .fenxiang a {
	color: #0033cc;
	text-decoration: none;
}

.commentContent .fenxiang a:hover,.commentContent .fenxiang a:active {
	text-decoration: underline;
}

.commentContent .line {
	clear: both;
	background: url(../Images/line.gif) repeat-x left top;
	height: 1px;
	font-size: 1px;
}

.commentContent .page {
	margin: 10px 0;
	padding: 5px;
	text-align: center;
}

.commentContent .page a {
	font-weight: bold;;
	display: inline-block;
}

.commentContent .page a:link,.commentContent .page a:visited {
	color: #000;
}

.commentContent .page a:hover,.commentContent .page a:active {
	color: #cc0000;
	text-decoration: underline;
}

.commentContent .page a.pagefirst:link,.commentContent .page a.pagefirst:visited
	{
	text-decoration: underline;
	color: #cc0000;
}

.commentContent .page .pagebtn {
	display: inline-block;
	width: 53px;
	border: 1px solid #ccc;
	background-color: #fff;
	padding: 2px 3px;
	font-weight: normal;
	text-decoration: none;
}

.commentContent form {
	margin: 0;
}

.commentContent .textBox textarea {
	width: 98%;
	height: 100px;
	margin: 10px 1%;
	_margin: 10px 0.5%;
}

.commentContent input.txt {
	width: 100px;
}

.commentContent input.btn {
	width: 130px;
}

.comment {
	background-color: #f5f8fd;
	text-align: center;
	padding: 10px;
	text-align: left;
	word-wrap: break-word;
	word-break: break-all;
}

.comment div,.comment div.huifu {
	padding: 2px;
	margin: 2px;
}

.comment div.huifu {
	border: 1px solid #aaa;
	padding: 2px;
	background-color: #FEFEED;
	margin: 0 auto;
	text-align: left;
}

.comment div.time,.comment div.content {
	margin: 0;
	padding: 2px;
}

.titprdou10 {
	border-bottom: 2px solid #F98504;
	margin-top: 10px;
	padding-bottom: 10px;
}

.titprdou10 h3 {
	background: #E1762C;
	width: 79px;
	height: 29px;
	line-height: 29px;
	font-size: 12px;
	color: #fff;
	font-weight: bold;
	text-align: center;
	margin-left: 27px;
	float: left;
}

.titprdou10 h4 {
	height: 29px;
	line-height: 29px;
	float: right;
	text-align: right;
	padding-right: 14px;
	color: #1E50A0;
}

.titprdou10 h4 em {
	padding-left: 3px;
	padding-right: 3px;
	color: #B82634;
}

.titprdou10 h4 em a:link {
	color: #B82634;
	text-decoration: none;
}

.titprdou10 h4 em a:visited {
	color: #B82634;
	text-decoration: none;
}

.titprdou10 h4 em a:active {
	color: #B82634;
	text-decoration: none;
}

.titprdou10 h4 em a:hover {
	color: #B82634;
	text-decoration: underline;
}

.wypl {
	border-right: 1px solid #F6CE9A;
	border-left: 1px solid #F6CE9A;
	padding-bottom: 16px;
}

.plwrap {
	padding: 20px 40px 8px 16px;
	border-bottom: 1px dotted #ccc;
	margin-left: 10px;
	margin-right: 10px;
	width: 632px;
}

.pltx {
	width: 51px;
	float: left;
}

.pltx img {
	width: 44px;
	height: 44px;
	border: 2px solid #D9D9D9;
	padding: 2px;
	background: #fff;
}

.pltx span {
	display: block;
	word-break: break-all;
	text-align: center;
}

.plright {
	width: 560px;
	float: left;
	padding-left: 16px;
}

.plcont {
	width: 530px;
	float: left;
	border-top: 1px solid #D9D9D9;
	background: url(${front}/images/plico.gif) no-repeat 0 6px;
	padding-left: 40px;
	padding-top: 19px;
	padding-bottom: 6px;
}

.plcont p {
	font-size: 14px;
	color: #666;
	line-height: 27px;
}

.pldate {
	border-top: 2px solid #D9D9D9;
	text-align: right;
	color: #777;
	padding-right: 26px;
	padding-top: 6px;
	width: 544px;
}

.plpage {
	border-top: none;
	border-left: 1px solid #F6CE9A;
	border-right: 1px solid #F6CE9A;
	border-bottom: 1px solid #F6CE9A;
	padding-top: 12px;
	height: 50px;
	text-align: center;
	margin-top: 0px;
}

.plpagecont {
	padding-bottom: 6px;
}

.plpage01 {
	margin-right: 3px;
}

.plpage01 a:link,.plpage01 a:visited,.plpage01 a:active {
	padding: 4px 7px;
	border: 1px solid #ccc;
	background: #fff;
	color: #0066CC;
	line-height: 14px;
}

.plpage01 a:hover {
	padding: 4px 7px;
	border: 1px solid #EC6C00;
	background: #F9BE00;
	text-decoration: none;
	color: #0066CC;
	line-height: 14px;
}

.plpage01 .plpageIndex {
	padding: 4px 7px;
	border: 1px solid #ccc;
	background: #fff;
	color: #0066CC;
	line-height: 14px;
}

.plpage01 .plpageIndex2 {
	padding: 4px 7px;
	border: 1px solid #EC6C00;
	background: #F9BE00;
	text-decoration: none;
	color: #0066CC;
	line-height: 14px;
	cursor: pointer;
}

.plpage02 {
	margin-right: 3px;
	cursor: pointer;
}

.plpage02 span {
	padding: 5px 7px 4px 7px;
	border: 1px solid #ccc;
	background: #fff;
	color: #999;
	height: 22px;
	width: 100px;
}

.plpage02 a:link,.plpage02 a:visited,.plpage02 a:active {
	padding: 5px 7px 4px 7px;
	border: 1px solid #ccc;
	background: #fff;
	color: #999;
	height: 22px;
	width: 100px;
	cursor: pointer;
}

.plpage02 a:hover {
	padding: 5px 7px 4px 7px;
	border: 1px solid #EC6C00;
	background: #F9BE00;
	text-decoration: none;
	color: #999;
	height: 22px;
	cursor: pointer;
}

.plpage02 .plpageswy {
	padding: 5px 7px 4px 7px;
	border: 1px solid #ccc;
	background: #fff;
	color: #999;
	height: 22px;
	width: 100px;
}

.plpage02 .plpageswy2 {
	padding: 5px 7px 4px 7px;
	border: 1px solid #EC6C00;
	background: #F9BE00;
	text-decoration: none;
	color: #999;
	height: 22px;
	cursor: pointer;
}

.plpage03 {
	padding-left: 24px;
	color: #999;
}

.btn300 {
	background: url(${front}/images/btn300bg.jpg) repeat-x bottom;
	padding-left: 3px;
	padding-right: 3px;
	height: 23px;
	border: 1px solid #999;
	cursor: pointer;
}

.revw10 {
	border: 1px solid #ccc;
	margin-top: 3px;
}

.revw10tit {
	background: url(${front}/images/titbg300.jpg) repeat-x bottom;
	border-bottom: 1px solid #ccc;
	height: 34px;
	line-height: 34px;
}

.revw10tit h3 {
	height: 34px;
	line-height: 34px;
	text-indent: 16px;
	font-size: 14px;
	font-weight: bold;
	color: #555;
	float: left;
	width: 100px;
}

.revw10tit h4 {
	height: 34px;
	line-height: 34px;
	float: right;
	width: 300px;
	text-align: right;
	padding-right: 16px;
	color: #1E50A0;
}

.revw10tit h4 em {
	padding-left: 3px;
	padding-right: 3px;
	color: #B82634;
}

.revw10tit h4 em a:link,.revw10tit h4 em a:visited,.revw10tit h4 em a:active
	{
	color: #B82634;
	text-decoration: none;
}

.revw10tit h4 em a:hover {
	color: #B82634;
	text-decoration: underline;
}

.revw10tit h5 {
	font-family: "宋体";
	height: 34px;
	font-size: 14px;
	line-height: 34px;
	float: right;
	text-align: right;
	padding-right: 14px;
	color: #1E50A0;
	color: #000;
}

.revw10tit h5 a {
	font-family: "宋体";
	color: #3266CB;
	font-size: 14px;
	font-weight: bold;
}

.revw10cont01 {
	height: 126px;
	padding-top: 60px;
}

.revw10unlogin {
	width: 176px;
	margin: 0 auto;
	background: url(${front}/images/revwunloginico.gif) no-repeat;
	padding-left: 79px;
	height: 60px;
	color: #999;
}

.revw10unlogin p {
	line-height: 21px;
}

.revw10unlogin span {
	padding-right: 6px;
}

.revw10unlogin span a:link,.revw10unlogin span a:visited,.revw10unlogin span a:active
	{
	color: #3366CC;
	text-decoration: none;
}

.revw10unlogin span a:hover {
	color: #3366CC;
	text-decoration: underline;
}

.revw10contleft {
	width: 510px;
	width: 512px !important;
	_width: 510px;
	float: left;
	padding-left: 19px;
	padding-top: 19px;
}

*+html .revw10contleft {
	width: 512px !important;
}

.revw10contleft span {
	padding: 2px;
	background: #FFDC97;
	display: block;
	height: 136px;
	width: 506px;
}

.revw10contleft span textarea {
	width: 504px;
	height: 134px;
	border: 1px solid #EFA100;
	background: url(${front}/images/textareabg.gif) no-repeat center #fff;
	overflow: auto;
}

.revw10contright {
	width: 188px;
	float: right;
	padding-top: 13px;
	padding-left: 9px;
}

.revw10contright p {
	line-height: 22px;
	font-size: 14px;
	overflow: hidden;
	text-overflow: ellipsis;
	width: 180px;
	white-space: nowrap;
}

.revw10contright p a:link {
	color: #3366CC;
	text-decoration: none;
}

.revw10contright p a:visited {
	color: #3366CC;
	text-decoration: none;
}

.revw10contright p a:active {
	color: #3366CC;
	text-decoration: none;
}

.revw10contright p a:hover {
	color: #3366CC;
	text-decoration: underline;
}

.tjplbtnarea {
	padding-top: 4px;
	_padding-top: 16px;
}

.tjplbtn01 {
	background: url(${front}/images/pbtn.jpg) no-repeat -180px -100px;
	width: 170px;
	height: 45px;
	border: none;
	cursor: pointer;
}

.tjplbtn02 {
	background: url(${front}/images/pbtn.jpg) no-repeat 0 -100px;
	width: 170px;
	height: 45px;
	border: none;
	cursor: pointer;
}

.revw10cont02 {
	padding-bottom: 20px;
	height: 100%;
}

.revw10yzmtxt {
	font-size: 14px;
	line-height: 26px;
	height: 26px;
	color: #999;
}

.revw10yzm img {
	border: 1px solid #ccc;
	padding: 1px;
	background: #fff;
	margin-left: 6px;
}

.revw10yzm input {
	height: 24px;
	line-height: 24px;
}
</style>
</head>
<body>
<!-- comment header begin -->
<script type="text/javascript">
function getPageByPageIndex(pageIndex)
{
	
	var nowPageIndex = Number(document.getElementById("cmntPageIndex").value);
	if (nowPageIndex-pageIndex == 0) {
		return;
	}
	document.getElementById("cmntPageIndex").value = pageIndex + "";
	jQuery.ajaxLoadImg.show('commentDetail'+pageIndex);
	jQuery("#GetCommentPage").ajaxSubmit
	({
		dataType: "json",
		success: function(data)
		{
			jQuery.ajaxLoadImg.hide('commentDetail'+pageIndex);
			if (data.status == "success")
			{
				document.getElementById("commentlist").innerHTML = data.message;
				document.getElementById("pagination").innerHTML = data.pageBarHtml;
				jQuery(".zan_active").praise();
			}
			else
			{
				alert("获取第" + pageIndex + "页的评论列表失败！");
			}
		}
	});
}

function getPlPage(pageIndex) {
	
	if ((null==pageIndex) || (pageIndex == '')) {
		return;
	}
	
	if (document.getElementById("cmntPageNum").value < pageIndex) {
		return;
	}
	
	if (pageIndex < 1) {
		return;
	}
	
	getPageByPageIndex(pageIndex);
	
}

</script>
<!-- comment header end -->
	<form name="GetCommentPage" id="GetCommentPage"
		action="${ServerContext}/shop/comment!ajaxGetPage.action" method="post">
		<input type="hidden" id="ProductID" name="ProductID" value="224801001" />
		<input type="hidden" id="RelaID" name="RelaID" value="${RelaID}" />
		<input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}" />
		<input type="hidden" id="CatalogType" name="CatalogType" value="${CatalogType}" /> 
		<input type="hidden" id="SiteID" name="SiteID" value="${SiteID}" /> 
		<input type="hidden" id="cmntPageIndex" name="cmntPageIndex" value="1" /> 
		<input type="hidden" id="cmntPageSize" name="cmntPageSize" value="${pageSize}" /> 
		<input type="hidden" id="cmntPageNum" name="cmntPageNum" value="${pageCount}" />
	</form>
	<div class="area_assess">
		<p class="ttl"><span>商品评论</span></p>
		<p class="num">
			<span><em class="icon"></em>满意度</span>
			<span class="nbr">100%</span>
		</p>
		<ul>
			<!-- comment purpose begin -->
			<li>${purpose}</li>
			<!-- comment purpose end -->
		</ul>
	</div>
	<!-- comment content begin -->
	<div class="list_assess" id="commentlist">
		<!-- comment loop begin -->
		<div class="list_conts ${borderClass}">
			<div class="tj_icon" style="display:${recommflag}"></div>
			<div class="cont_show">
				<div class="list_row_03">
					<p class="user_name"><em class="level_${grade}"></em>${AddUser}</p>
					<p class="user_info">${Purpose}<span class="as_date">${AddTime} </span>
						<span class="as_stars_0${score}"></span></p>
					<p title="有用" count="0" aria-pressed="${praiseAria}" class="zan_active ${praiseClasss}">
						<i class="icon vote-arrow"></i>
						<em class="label-span">有用</em>
						<em class="count-num">（<i id="praised_${ID}">${praisedNum}</i>）</em>
					</p>
				</div>
				<div class="list_row_01">
					<p><span class="icon_p"></span>${Content}</p>
				</div>
			</div>
			<div class="cont_answer" style="display:${style}">
				<p class="ans_ttl">开心保客服 回复：</p>
				<p>${ReplyContent}</p>
			</div>
		</div>
		<!-- comment loop end -->
	</div>				
	<!-- comment content end -->
</body>
	</html>
</z:init>