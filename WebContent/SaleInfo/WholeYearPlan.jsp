<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.WholeYearPlan.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>全站统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
function query(){
	
	jQuery("#insure_main_wrap").html("<div style=\"margin: 10px 100px;color: red;\">请稍候，程序处理中....</div>") ;
	var startDate = $V("startDate");
	var sessionId = "WholeYearPlan";
	var sendUrl="../ReportServlet.jspx?startDate="+startDate+"&defaultFilename=WholeYearPlan&type=html"
    +"&method=com.wangjin.infoseeker.WholeYearPlan.dg1DataBind&sessionId="+sessionId;
	jQuery("#insure_main_wrap").load(sendUrl) ;
}

</script>
</head>
<body>

	<div id="StaffStat">
	<input type="hidden" id="plan"
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:200px;">
						统计时间：
						<input value="${today}" type="text" id="startDate" 
							name="startDate" ztype="Date"  class="inputText" size="14" >
						</span>
						<z:tbutton onClick="query()"> <img src="../Icons/icon031a1.gif"/>打印报表</z:tbutton>
					</td>
				</tr>
	</table>
					<table>
			  		<tr>
							<td>
								 <div  id="insure_main_wrap"   >
								          	<div style="margin: 10px 100px;color: red;">
								 				&nbsp;&nbsp;&nbsp;请选择查询条件后，点击【打印】按钮生成报表
								 			</div>
								 </div>
							</td>
						</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0" scrolling="auto"
		style="width:100%;height: 100%;display: none;"></iframe>
</body>

</html>
</z:init>