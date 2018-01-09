<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已联系作者查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	var an = $V("authorName");
	var al = $V("articleLink");
	var ct = $V("contactType");
	var all=an+al+ct;
	if(all == null || all == ''){
		Dialog.alert("查询条件不能为空,至少要有一个查询条件！");
		return;
	}
	
	var dc = new DataCollection();	
	dc.add("authorName",an);
	dc.add("articleLink",al);
	dc.add("contactType",ct);
	
	Server.sendRequest("com.wangjin.daren.AuthorDetailInfo.getContactedPeople",dc,function(){
		var response = Server.getResponse();
		
		if(response.Status==0){
			Dialog.alert(response.Message);
		} else {
			$("contactedPeople").innerHTML=response.Message;
		}
	});
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="3" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon018a6.gif" width="20" height="20" />已联系作者查询</td>
				</tr>
				<tr>
					<td>
						<table  cellspacing="0" cellpadding="3">
							<tr>
					        	<td>作者名：</td>
								<td><input name="authorName" type="text" id="authorName" style="width:100px"></td>
							</tr>
							<tr>
								<td>帖子链接：</td>
								<td><input name="articleLink" type="text" id="articleLink" value="" size="80"></td>
							</tr>
							<tr>
								<td>联系方式：</td>
								<td><input name="contactType" type="text" id="contactType" value="" style="width:100px"></td>
							</tr>
							<tr>
								<td><z:tbutton onClick="doSearch()" ><img src="../Icons/icon005a2.gif" width="20" height="20"/>查询</z:tbutton></td>
							</tr>
							<tr>
								<td>查询结果</td>
							</tr>
							<tr>
								<td colspan="2"><div id="contactedPeople"></div></td>
							</tr>
						</table>		
					</td>
				</tr>
				
			</table>
		</td>
	</tr>
</table>
</body>
</html>