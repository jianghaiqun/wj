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

function openList(id){
	var diag = new Dialog("Diag1");
	diag.Width = 850;
	diag.Height = 430;
	diag.Title = "解答列表";
	//diag.ShowMessageRow = true;
	//diag.MessageTitle = "sss";
	//diag.Message = "aa";
	diag.URL = "message/AnswerList.jsp";
	diag.onLoad = function(){
		$DW.checkList(id);
	};
	//diag.OKEvent = save;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function changeStatus(){
	DataList.setParam("dg1","VerifyStatus",$V("VerifyStatus"));
	DataList.loadData("dg1");
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
                   &nbsp;<font color="#999999">解决状态</font>
                    <z:select id="VerifyStatus" style="width:60px;" onChange="changeStatus();">
                        <span value="All">全部</span>
                        <span value="00">未解决</span>
                        <span value="01">已解决</span>
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
					<z:datalist id="dg1" method="com.sinosoft.message.AuditAnswerUI.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr ztype="head" class="dataTableHead">
                                <td width="8%" align="right"><%-- <input type="checkbox" name="CommentID" value="${ID}" /> --%></td>
								<td width="65%">关联ID: ${QuestionId}</td>
                                <td width="27%" align="left" style=" font-weight:normal;">
                                <img src="../Icons/icon404a4.gif" style=" margin-bottom:-6px;"/><a href="#;" onClick="openList(${QuestionId})">查看</a>
                                </td>
							</tr>
                            <tr>
                            	<td align="right">提问标题：</td>
                                <td style="border-left:1px solid #DDDDDD;border-right:1px solid #DDDDDD;white-space:normal;" height="20"><span title="${Title}">${Title}</span></td>
                                <td rowspan="2" height="100">
                                <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                    <!-- <tr>
                                    	<td style=" border-bottom:none;" align="right">栏目归属：</td>
                                        <td style=" border-bottom:none;"></td>
                                    </tr> -->
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
                                    <tr>
                                    	<td style=" border-bottom:none;" align="right">解决状态：</td>
                                        <td style=" border-bottom:none;"><span title="${jState}" style="color:${FlagColor}">${jState}</span></td>
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
