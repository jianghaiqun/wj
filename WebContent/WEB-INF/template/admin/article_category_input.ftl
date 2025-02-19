<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑文章分类</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
//根据渠道显示文章分类	
function checkboxonchange()
{
var r=document.getElementsByTagName("input"); 
var aa='';
    for(var i=0;i<r.length-1;i++){
         if(r[i].checked){

    if(r[i].type == "checkbox"  ){
     aa += r[i].value+",";
    }
}
}

var articleCategoryName = $("#articleCategoryName");
$.post("article_category!findAllChannelarticleCategory.action", {

			"channelList.id" :aa
		}, function(data, textStatus) {
			if (data != "") {
				articleCategoryName.html(data);
			} else {
				articleCategoryName.html("");
	}
	}	)



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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加文章分类<#else>编辑文章分类</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>article_category!save.action<#else>article_category!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						分类名称:
					</th>
					<td>
						<input type="text" name="articleCategory.name" class="formText {required: true}" value="${(articleCategory.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						分类渠道:
					</th>
					<td>
						<#list articleCategoryChannelList as list>
							<label>
								<input type="checkbox" name="channelList.id"   onclick="checkboxonchange()"   class="{required: true, messages: {required: '请至少选择一个渠道!'}, messagePosition: '#channelMessagePosition'}" value="${list.id}" <#if (articleCategory.channelSet.contains(list) == true)!> checked="checked"</#if> />
								${(list.name)!}
							</label>
						</#list>
						<span id="channelMessagePosition"></span>
						<label class="requireField">*</label>
					</td>
				</tr>	
				<tr>
					<th>
						上级分类:
					</th>
					<td>
						<#if isAdd??>
						<select name="parentId"  id="articleCategoryName">
							<option value="">顶级分类</option>
							<#list articleCategoryTreeList as list>
								<option value="${list.id}">
									<#if list.level != 0>
										<#list 1..list.level as i>
											------
										</#list>
									</#if>
								${list.name}
								</option>
							</#list>
						</select>
						<#else>
							${(articleCategory.parent.name)!'顶级分类'}
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="articleCategory.orderList" class="formText {required: true, digits: true}" value="${(articleCategory.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						页面关键词:
					</th>
					<td>
						<input type="text" class="formText" name="articleCategory.metaKeywords" value="${(articleCategory.metaKeywords)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面描述:
					</th>
					<td>
						<textarea name="articleCategory.metaDescription" class="formTextarea">${(articleCategory.metaDescription)!}</textarea>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<span class="warnInfo"><span class="icon">&nbsp;</span>页面关键词、页面描述可以更好的使用户通过搜索引擎搜索到站点</span>
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