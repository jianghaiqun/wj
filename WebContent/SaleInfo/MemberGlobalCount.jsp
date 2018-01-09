<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<z:init method="com.wangjin.infoseeker.GlobalCount.initStaff">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>全站统计</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/OrderTree.js"></script>
<script type="text/javascript" src="../template/common/js/jquery.js"></script>
<script type="text/javascript">
function query(type,cancelFlag){
	if(type && type == 'html'){
		 jQuery("#insure_main_wrap").html("<div style=\"margin: 10px 100px;color: red;\">请稍候，程序处理中....</div>") ;
	}  
	var year = $V("year");
	var contant = $NV("contant");
	var sessionId = "GlobalCount";
	var fromWap = $V("fromWap");
	var sendUrl="../ReportServlet.jspx?year="+year+"&contant="+contant+"&defaultFilename=GlobalCount&type="+type+"&fromWap="+fromWap
			    +"&method=com.wangjin.infoseeker.NewGlobalCount.dg1DataBind&sessionId="+sessionId+"&cancelFlag="+cancelFlag;
	if(type=="excel"){
		location.href=sendUrl;
	}else{
		jQuery("#insure_main_wrap").load(sendUrl) ;
	}
}
</script>
</head>
<body>

	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<span style="float: left;width:320px;">
						统计年份：
						<z:select id="year" name="year" style="width:80px" >${year}</z:select>
						 会员来源：
						<z:select name="fromWap" id="fromWap" style="width:50px">
										<span value="1">淘宝</span>
										<span value="2">主站</span>
									</z:select>
						</span>
						<z:tbutton onClick="query('html','0')"> <img src="../Icons/icon031a1.gif"/>统计</z:tbutton>
						<z:tbutton onClick="query('html','1')"> <img src="../Icons/icon031a1.gif"/>撤单统计</z:tbutton>
						<z:tbutton onClick="query('html','2')"> <img src="../Icons/icon031a1.gif"/>对冲统计</z:tbutton>
						<z:tbutton onClick="query('excel','')"> <img src="../Icons/icon031a7.gif" />导出EXCEL</z:tbutton>
					</td>
				</tr>
			</table>
					<table>
			  		<tr>
							<td>
								 <div  id="insure_main_wrap"   >
								          	<div style="margin: 10px 100px;color: red;">
								 				&nbsp;&nbsp;&nbsp;请选择查询条件后，点击【查询】按钮生成报表
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