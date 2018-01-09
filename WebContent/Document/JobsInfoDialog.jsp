<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
	
	<z:init method="com.sinosoft.cms.document.JobsInfo.initDialog">
		<form id="form2">
			<input type="hidden"  id="id" name="id" value="${id}">
			<input type="hidden" id="flag" name="flag" value="${flag}">
			<table width="920" height="380" align="center" cellpadding="2" cellspacing="0">
				<tr>
					<td valign="top">
			    			<fieldset >
								 <table>
								     <tr>
								         <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">岗位名称：</td>
								          <td ><input value="${jobsName}" type="text" id="jobsName" name="jobsName" size="60" verify="岗位名称|NotNull" maxlength=150></td>
										  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">类别：</td>
									      <td><input value="${jobsType}" type="text" id="jobsType" name="jobsType" size="20" verify="类别|NotNull" maxlength=150></td>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">工作性质：</td>
										  <td><input value="${jobsProperty}" type="text" id="jobsProperty" name="jobsProperty" size="20" verify="工作性质|NotNull" maxlength=150></td>
										  
									 </tr>
								     <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">工作地点：</td>
										  <td><input value="${jobsAddress}" type="text" id="jobsAddress" name="jobsAddress" size="60" verify="工作地点|NotNull" maxlength=200></td>
										  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">hr邮箱：</td>
								          <td ><input value="${hrEmail}" type="text" id="hrEmail" name="hrEmail" size="20" verify="hr邮箱|NotNull" maxlength=50></td>
										  <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是否发布：</td>
									      <td><z:select style="width:60px;" id="isPublish" name="isPublish" verify="是否发布|NotNull" value="${isPublish}">${YesOrNo}</z:select></td>
									 </tr>
									 <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">岗位职责：</td>
										  <td colspan="5"><textarea id="jobsDuty" name="jobsDuty" style="width:700px;height:120px" verify="岗位职责|NotNull">${jobsDuty}</textarea></td>
										  
									 </tr>
									 <tr>
								          <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">任职要求：</td>
										  <td colspan="5"><textarea id="jobsRequirement" name="jobsRequirement" style="width:700px;height:120px" verify="任职要求|NotNull">${jobsRequirement}</textarea></td>
									 </tr>
							     </table>
							   
			               </fieldset>
					</td>
			    </tr>
			  </table>
		</form>
	</z:init>
</body>
</html>
