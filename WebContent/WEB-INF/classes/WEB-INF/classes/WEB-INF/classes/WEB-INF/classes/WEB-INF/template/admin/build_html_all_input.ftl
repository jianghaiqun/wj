<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>一键网站更新</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
jQuery().ready( function() {

	// 根据更新选项显示/隐藏开始日期和结束日期
	jQuery(".buildTypeInput").click( function() {
		if (jQuery(this).val() == "date") {
			jQuery(".dateTr").show();
		} else {
			jQuery(".dateTr").hide();
		}
	})
	
	jQuery("#inputForm").submit(function() {
		jQuery("#buildType").val(jQuery(".buildTypeInput:checked").val());
		jQuery("#maxResults").val(jQuery("#maxResultsInput").val());
		jQuery("#beginDate").val(jQuery("#beginDateInput").val());
		jQuery("#endDate").val(jQuery("#endDateInput").val());
	});

	var isInitialize = false;
	var buildTotal = 0;
	jQuery("#inputForm").ajaxForm({
		dataType: "json",
		beforeSubmit: function(data) {
			if (!isInitialize) {
				isInitialize = true;
				jQuery(".buildTypeInput").attr("disabled", true);
				jQuery("#maxResultsInput").attr("disabled", true);
				jQuery("#beginDateInput").attr("disabled", true);
				jQuery("#endDateInput").attr("disabled", true);
				jQuery(":submit").attr("disabled", true);
				jQuery("#statusTr").show();
				jQuery("#status").text("正在更新BASE_JAVASCRIPT，请稍后...");
			}
		},
		success: function(data) {
			
			if (data.buildTotal) {
				buildTotal += Number(data.buildTotal);
				
			}
			
			if (data.status == "baseJavascriptFinish") {
				jQuery("#status").text("正在更新自定义错误页，请稍后...");
				jQuery("#buildContent").val("errorPage");
				jQuery("#inputForm").submit();
				
			} else if (data.status == "errorPageFinish") {
				jQuery("#status").text("正在更新登录页，请稍后...");
				jQuery("#buildContent").val("login");
				jQuery("#inputForm").submit();
				
			} else if (data.status == "loginFinish") {
					jQuery("#buildContent").val("");
					jQuery("#firstResult").val("0");
					jQuery("#statusTr").hide();
					jQuery(".buildTypeInput").attr("disabled", false);
					jQuery("#maxResultsInput").attr("disabled", false);
					jQuery("#beginDateInput").attr("disabled", false);
					jQuery("#endDateInput").attr("disabled", false);
					jQuery(":submit").attr("disabled", false);
					alert( "网站更新成功！[更新总数: " + buildTotal + "]");
					isInitialize = false;
					buildTotal = 0;
			}   
		}
	});

});
</script>
</head>
<body class="input">
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span>一键网站更新</h1>
		</div>
		<form id="inputForm" action="build_html!all.action" method="post">
			<input type="hidden" id="buildType" name="buildType" value="" />
			<input type="hidden" id="maxResults" name="maxResults" value="" />
			<input type="hidden" id="firstResult" name="firstResult" value="0" />
			<input type="hidden" id="buildContent" name="buildContent" value="" />
			<input type="hidden" id="beginDate" name="beginDate" value="" />
			<input type="hidden" id="endDate" name="endDate" value="" />
			<table class="inputTable">
				<tr>
					<th>
						更新选项:
					</th>
					<td>
						<label><input type="radio" name="buildTypeInput" class="buildTypeInput" value="date" checked />指定日期</label>&nbsp;&nbsp;
						<label><input type="radio" name="buildTypeInput" class="buildTypeInput" value="all" />更新所有</label>
					</td>
				</tr>
				<tr class="dateTr">
					<th>
						起始日期:
					</th>
					<td>
						<input type="text" id="beginDateInput" name="" class="formText datePicker" value="${(defaultBeginDate?string("yyyy-MM-dd"))!}" title="留空则从最早的内容开始更新" />
					</td>
				</tr>
				<tr class="dateTr">
					<th>
						结束日期:
					</th>
					<td>
						<input type="text" id="endDateInput" name="" class="formText datePicker" value="${(defaultEndDate?string("yyyy-MM-dd"))!}" title="留空则更新至最后的内容" />
					</td>
				</tr>
				<tr>
					<th>
						每次更新数
					</th>
					<td>
						<input type="text" id="maxResultsInput" name="" class="formText" value="50" />
					</td>
				</tr>
				<tr id="statusTr" class="hidden">
					<th>
						&nbsp;
					</th>
					<td>
						<span class="loadingBar">&nbsp;</span>
						<p id="status"></p>
					</td>
				</tr>
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	</div>
</body>
</html>