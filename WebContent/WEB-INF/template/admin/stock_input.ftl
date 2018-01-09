<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑库存分类</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
    function getFileSuff() {
		var file = document.getElementById("importRateFile").value;
		var ss = file.split(".");
		var sure = document.getElementById("sure");
		if (ss.length == 2) {
			if (ss[1] == "xls") {
			    sure.disabled='';
				return true;
			} else {
				alert("请上传扩展名为xls的文件");
				sure.disabled='disabled';
			}
		} else {
			alert("请上传扩展名为xls的文件");
			sure.disabled='disabled';
		}
		return false;
	}	
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加库存分类<#else>编辑库存分类</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>stock!save.action<#else>stock!update.action</#if>" method="post"  enctype="multipart/form-data">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						分类名称:
					</th>
					<td>
						<input type="text" name="stock.name" class="formText {required: true}" value="${(stock.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
			   <tr>
					<th>
						礼品导入:
					</th>
					<td>
					   <input type="file" name="importRateFile" id= "importRateFile" onchange="getFileSuff();"/>
					</td>
				</tr>
				<tr>
					<th>
						导入模板下载:
					</th>
					<td>
					   <a href="${base}/xlstemplate/template.xls">库存导入模板.xls</a>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true"  id="sure"/>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>