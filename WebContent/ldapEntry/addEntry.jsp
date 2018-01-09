<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" import="java.util.*"
	import="java.text.SimpleDateFormat"
	contentType="text/html; charset=utf-8"%>
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>数据导入或更新</title>
		<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../scripts/jquery.js"></script>
		<script type="text/javascript" src="../scripts/ajaxfileupload.js"></script>

	</head>
	<body>
		<table width="100%" border="0" cellspacing="6" cellpadding="0"
			style="border-collapse: separate; border-spacing: 6px;">
			<tr valign="top">
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="6"
						class="blockTable">
						<tr>
							<td valign="middle" class="blockTd">
								<img src="../Icons/2.png" width="20" height="20" />
								&nbsp;数据导入或更新
							</td>
						</tr>
						<tr>
							<td style="padding: 0 8px 4px;">
								<center>
									<form action="saveOrUpdateEntry.do" method="post"
										enctype="multipart/form-data" name="FileForm" id="fileForm">
										<span id='uploadSpan'> <input type="file"
												name="fileMaps" id="fileMaps"
												onchange='checkFileType(this.value);' />&nbsp; </span>
										<input type="button" value="导入或更新" id="inp" value="disabled"
											onclick="ajaxFileUpload();" />
										<font color="#9f9f9f">(请点击"浏览..."按钮获取路径)</font>
									</form>
									<div id="loading" style="display: none">
										<img src=../ldapEntry/loading.gif>
										&nbsp;正在导入...
									</div>
								</center>
							</td>
						</tr>
						<tr>
							<td
								style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">

								<%
									String flag = (String) request.getSession().getAttribute("flag");

									if (flag == "true") {

										SimpleDateFormat formatter = new SimpleDateFormat(
												"yyyy-MM-dd HH:mm:ss");

										List errList = (List) request.getSession().getAttribute(
												"errList");

										Date oldDate = (Date) request.getSession().getAttribute(
												"oldDate");

										Date newDate = (Date) request.getSession().getAttribute(
												"newDate");

										String timeStr1 = formatter.format(oldDate);

										String timeStr2 = formatter.format(newDate);

										long date1 = formatter.parse(timeStr1).getTime();
										long date2 = formatter.parse(timeStr2).getTime();

										long time = (date2 - date1) / 1000;

										List rightList = (List) request.getSession().getAttribute(

										"rightList");

										flag = "flase";

										request.getSession().setAttribute("flag", flag);
								%>

								<table width="100%" cellpadding="2" cellspacing="0"
									class="dataTable">

									<tr ztype="head" class="dataTableHead">
										<%
											if (time == 0) {
										%>
										<td>
											数据导入或更新数据库共花费
											<font color="red"><%=1%></font>秒
										</td>
										<%
											} else {
										%>
										<td>
											数据导入或更新数据库共花费
											<font color="red"><%=time%></font>秒
										</td>
										<%
											}
										%>
									</tr>
									<tr>
										<%
											if (rightList != null) {
										%>
										<td valign="top">
											成功导入或更新<%=rightList.size()%>条数据
										</td>
										<%
											}
										%>
									</tr>
									<tr>
										<%
											if (errList == null) {
										%>
										<td>
											导入或更新数据过程中失败0条
										</td>
										<%
											}
										%>
										<td>
											<%
												if (errList != null) {
											%>

											<tr>
												<td>
													导入或更新数据过程中失败
													<%=errList.size()%>条
												</td>
											</tr>
											<tr>
												<td>
													详细如下:
												</td>
											</tr>
											<%
												for (int i = 0; i < errList.size(); i++) {
											%>
											<tr>
												<td>
													<font color="red"><%=i + 1%>:</font><%=errList.get(i)%>
												</td>
											</tr>
											<%
												}
											%>

											<%
												}
											%>
										</td>
									</tr>
								</table>
								<%
									}
								%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>

	<script type="text/javascript">
		function checkFileType(str) {
		var pos = str.lastIndexOf(".");
		var lastname = str.substring(pos, str.length);
		var resultName = lastname.toLowerCase();
		if ('.xls' != resultName.toString()) {
			alert('只能上传xls文件，您上传的文件类型为' + lastname + '，请重新上传');
			resetFile();
		}
	}
	var html = document.getElementById('uploadSpan').innerHTML;

	function resetFile() {
		document.getElementById('uploadSpan').innerHTML = html;
	}
		
     function ajaxFileUpload(){  
	       if (FileForm.fileMaps.value == "") {
			   alert("请选择文件路径");
		        return false;
		       } else {
		           $('#inp').attr('disabled',false);
			       $("#loading").css("display","block");
　　　　                             $.ajaxFileUpload({
　　　　　　　　                 url:'http://localhost:8080/sjcms/saveOrUpdateEntry.do',　
　　　　　　　　             //      url:'http://10.2.14.27:9080/cms/saveOrUpdateEntry.do',　
                      secureuri:false,
　　　　　　                             type:'POST',　
                      fileElementId:'fileMaps',
　　　　　　                        　success:function(){	
                              window.location.href = window.location.href;
                                      }
　　　　　　                                     });
                    }
　　　　　　                 　 }
</script>
</html>
