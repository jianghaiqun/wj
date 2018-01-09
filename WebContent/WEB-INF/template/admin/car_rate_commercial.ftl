<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>商业险费率导入</title>
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
	     if(document.getElementById("regionCode").value==null||document.getElementById("regionCode").value=="-1"){
	         alert("请选择区域");
	         document.getElementById("regionCode").focus();
	         return false;
	     }
	     if(document.getElementById("rateModelType").value==null||document.getElementById("rateModelType").value=="-1"){
	         alert("请选择费率类型");
	         document.getElementById("rateModelType").focus();
	         return false;
	     }
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
			<h1><span class="icon">&nbsp;</span>商业险费率导入</h1>
		</div>
		<form id="inputForm" class="validate" action="car_rate!importRate.action" method="post" enctype="multipart/form-data">
			<table class="inputTable">
				<tr>
					<th>
						车辆行驶区域:
					</th>
					<td>
					   <select id="regionCode" name="regionCode" class="{required: true}">
						  <option value="-1">----请选择----</option>
						  <#list citys as list>
								<option value="${list.placeCode}">
									${list.placeName}
								</option>
							</#list>
					   </select>
					</td>
				</tr>
				<tr>
					<th>
						模板类型:
					</th>
					<td>
					   <select id="rateModelType" name="rateModelType" class="{required: true}">
						  <option value="-1">----请选择----</option>
                          <option value="A">商业险A条款模板</option>
                          <option value="B">商业险B条款模板</option>
                          <option value="C">商业险C条款模板</option>
                          <option value="D">商业险其他模板</option>
					   </select>
					</td>
				</tr>
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
					    <a href="${base}/template/modelRate/TemplateA.xls">商业险A条款模板</a>
					    <a href="${base}/template/modelRate/TemplateB.xls">商业险B条款模板</a>
					    <a href="${base}/template/modelRate/TemplateC.xls">商业险C条款模板</a>
					    <a href="${base}/template/modelRate/TemplateD.xls">商业险其他模板</a>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="button" id="sure" class="formButton" onclick="fsure();" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>