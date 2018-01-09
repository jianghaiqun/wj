<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>交强险费率导入</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
    function getFileSuff() {
		var file = document.getElementById("importRateFile").value;
		var ss = file.split(".");
		if (ss.length == 2) {
			if (ss[1] == "xls") {
			    document.getElementById("sure").disabled='';
				return true;
			} else {
				alert("请上传扩展名为xls的文件");
				document.getElementById("sure").disabled='disabled';
			}
		} else {
			alert("请上传扩展名为xls的文件");
			document.getElementById("sure").disabled='disabled';
		}
		return false;
	}
	function fsure(){
	     if(document.getElementById("importRateFile").value==null||document.getElementById("importRateFile").value==""){
	         alert("请选择费率文件");
	         document.getElementById("importRateFile").focus();
	         return false;
	     }
	     document.getElementById("inputForm").submit();
	     return true;
	}
</script>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>交强险费率导入</h1>
		</div>
		<form id="inputForm" class="validate" action="car_rate!saveJQXRate.action" method="post" enctype="multipart/form-data">
				<input type="hidden" name = "rateModelType" value="JQX" />
			<table class="inputTable">
				<tr>
					<th>
						费率导入:
					</th>
					<td>
					   <input type="file" name="importRateFile" id= "importRateFile" onchange="getFileSuff();"/>
					</td>
				</tr>
				<tr id='insurednameTr'>
					<th>
						模板下载:
					</th>
					<td>
					    <a href="${base}/template/modelRate/Template_JQX.xls">交强险模板</a>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="button" id="sure" class="formButton" value="确  定" onclick="fsure();" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>