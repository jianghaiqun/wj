<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
	function check(){
		if($V("ZipFile")==""){
			Dialog.alert("请选择导入的文件！");
			return false;
		}
		return true;
	}
	
	function pathClear(){
	   var obj = document.getElementById("ZipFile");
	   //解决上传路径删除后不能进行上传的问题
	   obj.outerHTML=obj.outerHTML;  
	}
</script>
</head>
<body>
<iframe src="javascript:void(0);" name="targetFrame"
	width="0" height="0" frameborder="0"></iframe>
<form id="form1" enctype="multipart/form-data" target="targetFrame"
	action="ZipFileSave.jsp"
	method="POST" onSubmit="return check();">
	<div id="StaffStat">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<p>选择文件： <input name="ZipFile" type="file" id="ZipFile">
						<font color="#FF6633">*只支持zip格式文件</font></p>
					</td>
				</tr>
				<tr>
					<td style="padding:4px 8px;" class="blockTd">
						<input name="button" type="Submit" class="inputButton" id="button" value=" 上传 " />
						<input name="button2" type="button" class="inputButton" onClick=" pathClear();" value=" 取 消 " />
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</div>
	<iframe name="InputorStat" id="InputorStat" frameborder="0" scrolling="auto"
		style="width:100%;height: 100%;display: none;"></iframe>
</form>
</body>
</html>
