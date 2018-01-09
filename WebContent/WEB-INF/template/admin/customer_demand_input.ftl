<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>编辑用户需求</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function change (){
	var customername = document.getElementById("customername").value;
	var phone = document.getElementById("phone").value;
    var email = document.getElementById("email").value;
    var insuredage = document.getElementById("insuredage").value;
    
	var t =  document.getElementById("insuredname");
	var insuredname = t.options[t.selectedIndex].value;
	var t1 =  document.getElementById("insurancetype");
	var insurancetype = t1.options[t1.selectedIndex].value;
	var t2 =  document.getElementById("guaranteeperiod");
	var guaranteeperiod = t2.options[t2.selectedIndex].value;
	
    //var str = "尊敬的"+customername+"您好，通过您登记联系方式"+phone+"   "+email +" ,我们的客服人员会尽快与您联络，为您介绍符合您购买意向的保险产品 :  为了您的 "+insuredage + " 的 "+insuredname +",打算购买保障期限为 "+guaranteeperiod +" 的 "+insurancetype ；

	$("#neirong").attr("value", "尊敬的   "+customername+"    您好，通过您登记联系方式"+phone+"  "+email +"    ,我们的客服人员会尽快与您联络，为您介绍符合您购买意向的保险产品 :  为了您的  "+insuredage + "  岁的   "+insuredname +"   , 打算购买保障期限为   "+guaranteeperiod +"  的   "+insurancetype); 
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
			<h1><span class="icon">&nbsp;</span><#if isAdd??>添加户需求<#else>编辑需求</#if></h1>
		</div>
		<form id="inputForm" class="validate" action="<#if isAdd??>customer_demand!save.action<#else>customer_demand!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						客户姓名:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" id="customername" name="customerDemand.customername" class="formText {required: true, customername: true}" onblur="change()" />
							<label class="requireField">*</label>
						<#else>
							${customerDemand.customername}
							<input type="hidden" name="customerDemand.customername" value="${(customerDemand.customername)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						联系电话:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" id="phone" name="customerDemand.phone" class="formText {required: true, phone: true}" onblur="change()" />
							<label class="requireField">*</label>
						<#else>
							${customerDemand.phone}
							<input type="hidden" name="customerDemand.phone" value="${(customerDemand.phone)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						E-Mail:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" id="email" name="customerDemand.email" class="formText {required: true, email: true}" onblur="change()" />
							<label class="requireField">*</label>
						<#else>
							${customerDemand.email}
							<input type="hidden" name="customerDemand.email" value="${(customerDemand.email)!}" />
						</#if>
					</td>
				</tr>
				<tr id='insurednameTr'>
					<th>
						为谁投保:
					</th>
					<td>
						<select id="insuredname" name="customerDemand.insuredname" class="{required: true}" onchange="change();">
						<option value="">请选择...</option>
							<#list allInsuranceName as list>
								<option value="${list}"<#if (list == customerDemand.insuredname)!> selected</#if>>
									${action.getText(list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						被保人年龄:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" id="insuredage" name="customerDemand.insuredage" class="formText {required: true, insuredage: true}"  onblur="change()"/>
							<label class="requireField">*</label>
						<#else>
							${customerDemand.insuredage}
							<input type="hidden" name="customerDemand.insuredage" value="${(customerDemand.insuredage)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						险种类型:
					</th>
					<td>
						<select id="insurancetype" name="customerDemand.insurancetype" class="{required: true}" onchange="change();">
						<option value="">请选择...</option>
							<#list allInsuranceType as list>
								<option value="${list}"<#if (list == customerDemand.insurancetype)!> selected</#if>>
									${action.getText(list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						保障期限:
					</th>
					<td>
						<select id="guaranteeperiod" name="customerDemand.guaranteeperiod" class="{required: true}" onchange="change();">
						<option value="">请选择...</option>
							<#list allGuaranteePeriod as list>
								<option value="${list}"<#if (list == customerDemand.guaranteeperiod)!> selected</#if>>
									${action.getText(list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						内容:
					</th>
					<td>
						<textarea  id=neirong class="wysiwyg" rows="10" onblur="change()"></textarea>
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