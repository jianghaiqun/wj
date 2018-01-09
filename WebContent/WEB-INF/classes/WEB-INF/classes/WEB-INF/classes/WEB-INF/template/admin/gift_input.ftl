<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>添加/编辑库存</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<script type="text/javascript">
var errEle = null;
function hidcpdinput()
{
	var oldpd = document.getElementById("oldpd");
	var newpd = document.getElementById("newpd");
	var newpd1 = document.getElementById("newpd1");
	var pdch = document.getElementById("pdchflag");
	
<#if errid??>
	document.getElementById("cpdb").value = "取消密码修改";
	document.getElementById("cpd").style.display="";
	document.getElementById("cpd1").style.display="";
	document.getElementById("cpd2").style.display="";
	oldpd.className = "formText";
	newpd.className = "formText {required: true}";
	newpd1.className = "formText {required: true}";
	
	var errEle = document.getElementById("${errid}err");
	if ((null != errEle) && ("undefined" != typeof(errEle)))
	{
		errEle.innerHTML = "<label class='validateError' style='filter: ; zoom: 1; display: inline;'>${errmessage}</label>"
		document.getElementById("${errid}").onchange=function(){errEle.innerHTML="";};		
	}
	pdch.value = "1";
<#else>
	document.getElementById("cpdb").value = "修改密码";
	document.getElementById("cpd").style.display="none";
	document.getElementById("cpd1").style.display="none";
	document.getElementById("cpd2").style.display="none";
	oldpd.removeAttributeNode(oldpd.getAttributeNode("class"));
	newpd.removeAttributeNode(newpd.getAttributeNode("class"));
	newpd1.removeAttributeNode(newpd1.getAttributeNode("class"));
	pdch.value = "0";
</#if>
}

function cpdf()
{
	var cpd = document.getElementById("cpd");
	var cpd1 = document.getElementById("cpd1");
	var cpd2 = document.getElementById("cpd2");
	var cpdb = document.getElementById("cpdb");
	var oldpd = document.getElementById("oldpd");
	var newpd = document.getElementById("newpd");
	var newpd1 = document.getElementById("newpd1");
	var pdch = document.getElementById("pdchflag");
	
	if (cpd.style.display == "none")
	{
		pdch.value = "1";
		cpdb.value = "取消密码修改";
		cpd.style.display = "";
		cpd1.style.display = "";
		cpd2.style.display = "";
		oldpd.className = "formText";
		newpd.className = "formText {required: true}";
		newpd1.className = "formText {required: true}";
	}
	else
	{
		pdch.value = "0";
		cpdb.value = "修改密码";
		cpd.style.display = "none";
		cpd1.style.display = "none";
		cpd2.style.display = "none";
		//oldpd.value = "";
		//newpd.value = "";
		//newpd1.value = "";
		oldpd.removeAttributeNode(oldpd.getAttributeNode("class"));
		newpd.removeAttributeNode(newpd.getAttributeNode("class"));
		newpd1.removeAttributeNode(newpd1.getAttributeNode("class"));
	}
}
</script>
</head>
<body class="input" <#if isEdit>onload="hidcpdinput();"</#if> >
	<div class="body">
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加库存<#else>编辑库存</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>gift!save.action<#else>gift!update.action</#if>" method="post" >
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
			    <tr>
					<th>
						库存分类:
					</th>
					<td>
						<select name="gift.stock.id" id="stockName" class="{required: true}"  >
							<#if (gift.stock.id==''||gift.stock.id==null)!>
								<option value="">请选择...</option>
							</#if>
							<#list stockList as list>
								<#if (gift.stock.id!='')!>
									<#if (list.id == gift.stock.id)!> 
										<option value="${list.id}">
											${list.name}
										</option>
									</#if>
								<#else>
									<option value="${list.id}"<#if (list.id == gift.stock.id)!> selected</#if>>
										${list.name}
									</option>
								</#if>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						卡号:
					</th>
					<td>
						<input type="text" name="gift.cardNo" class="formText {required: true}" value="${(gift.cardNo)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<#if isAdd>
					<tr>
						<th>
							密码:
						</th>
						<td>
							<input type="password" name="gift.password" class="formText {required: true}" value="${(gift.password)!}" />
							<label class="requireField">*</label>
						</td>
					</tr>
				<#else>
				<!--
					<tr id="showcpd">
						<th/>
						<td>
							<input id="cpdb" type="button" class="formButtonL" value="修改密码" onclick="cpdf();" />
						</td>
					</tr>
					<tr id="cpd" style="display:'none';">
						<th>
							旧密码:
						</th>
						<td>
							<input type="password" id="oldpd" name="oldpd" value="${oldpd}" />
							<span id="oldpderr" style="display:'none';"></span>
							<label class="requireField" style="display:'inline'">*</label>
						</td>
					</tr>
					<tr id="cpd1" style="display:'none';">
						<th>
							设置新密码:
						</th>
						<td>
							<input type="password" id="newpd" name="newpd" value="${newpd}" />
							<span id="newpderr" style="display:'none';"></span>
							<label class="requireField">*</label>
						</td>
					</tr>
					<tr id="cpd2" style="display:'none';">
						<th>
							重复新密码:
						</th>
						<td>
							<input type="password" id="newpd1" name="newpd1" value="${newpd1}" />
							<span id="newpd1err" style="display:'none';"></span>
							<label class="requireField">*</label>
							<input type="hidden" id="pdchflag" name="pdchflag" value="0" />
						</td>
					</tr>
				</#if>
				-->
				<tr>
					<th>
						有效期:
					</th>
					<td>
						<input type="text" name="gift.expireDate" class="formText {required: true}" value="<#if (gift.expireDate)??>${(gift.expireDate)?string('yyyy-MM-dd')}</#if>"
							onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'});" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						说明:
					</th>
					<td>
						<input type="text" name="gift.description" class="formText {required: false}" style="width:80%;" maxlength="127" value="${(gift.description)!}" />
					</td>
				</tr>
			   <tr>
					<th>
						状态名称:
					</th>
					<td>
						<select name="gift.status" id="giftStatus" class="{required: true}">
							<option value="Y" <#if gift.status == "Y"> selected</#if>>Y</option>
							<option value="N" <#if gift.status == "N"> selected</#if>>N</option>
						</select>
						<label class="requireField">*</label>
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