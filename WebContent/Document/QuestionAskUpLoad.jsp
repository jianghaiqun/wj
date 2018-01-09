<%
	/* 问答导入画面
	 ************************************************************** 
	 *  程序名 : QuestionAskUpLoad.jsp
	 *  建立日期:2013/06/25 
	 *  作者   :  wangcaiyun
	 *  模块   :  CMS
	 *  描述   :  批量导入问答
	 *  备注   :  
	 * ------------------------------------------------------------ 
	 *  修改历史 
	 *  序号   日期   修改人     修改原因 
	 * 1 
	 * 2 
	 ************************************************************** 
	 */
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>问答导入</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="QuestionAskUpLoad.js"></script>
</head>
<body>
	<iframe src="javascript:void(0);" name="targetFrame" width="0"
		height="0" frameborder="0"></iframe>
	<form name="fm" target="targetFrame" action="QuestionAskUpLoadSave.jsp"
		method="post" enctype="multipart/form-data"
		onSubmit="return ImportExcel();">
		<div id="StaffStat">
			<input type="hidden" id="treeId" name="treeId">
			<table width="100%" border="0" cellspacing="6" cellpadding="0"
				style="border-collapse: separate; border-spacing: 6px;">
				<tr valign="top">
					<td width="180">
						<table width="180" border="0" cellspacing="0" cellpadding="6"
							class="blockTable">
							<tr>
								<td style="padding: 6px;" class="blockTd"><z:tree
										id="tree1" style="height:440px;width:160px;"
										method="com.sinosoft.cms.document.QuestionAskUpLoad.treeDataBind"
										level="2" lazy="true" resizeable="true">
										<p cid='${ID}' innercode='${InnerCode}' onClick="onTreeClick(this);">${Name}</p>
									</z:tree>
								</td>
							</tr>
						</table>
					</td>
					<td>
						<table width="100%" border="0" cellspacing="0" cellpadding="0"
							class="blockTable">
							<tr height="150px">
							</tr>

							<tr valign="middle">
								<td width="30%"></td>
								<td nowrap><input type="file" style="width: 300px"
									name="importWendaFile" id="importWendaFile"
									onchange="getFileSuff();" /> <input name="importBtn"
									type="button" class="inputButton" id="importBtn"
									onClick="return ImportExcel();" value="上  传" />
								</td>
							</tr>
							<tr>
								<td></td>
								<td><A href="./template/QuestionAsk.xls">问答批量导入模板下载</A>
								</td>
							</tr>
							<tr>
								<td></td>
								<td>1，请按照模板要求填写问答相关信息</td>
							</tr>
							<tr>
								<td></td>
								<td>2，请选择左侧要导入的问答栏目</td>
							</tr>
							<tr>
								<td></td>
								<td>3，每次导入的数据最多为300条</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<iframe name="InputorStat" id="InputorStat" frameborder="0"
			scrolling="auto" style="width: 100%; height: 100%; display: none;"></iframe>
	</form>
</body>
</html>