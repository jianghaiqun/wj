<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>车险保费计算信息管理</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function fsure(){
	     if(document.getElementById("companyCode").value==null||document.getElementById("companyCode").value=="-1"){
	         alert("请选择区域");
	         document.getElementById("companyCode").focus();
	         return false;
	     }
	     if(document.getElementById("regionCode").value==null||document.getElementById("regionCode").value=="-1"){
	         alert("请选择区域");
	         document.getElementById("regionCode").focus();
	         return false;
	     }
	     if(document.getElementById("modelType").value==null||document.getElementById("modelType").value=="-1"){
	         alert("请选择费率类型");
	         document.getElementById("modelType").focus();
	         return false;
	     }
	     if(document.getElementById("dicount").value!=""&&(document.getElementById("dicount").value>1||document.getElementById("dicount").value<0.7)){
	         alert("优惠折扣需要大于等于0.7，小于等于1");
	         document.getElementById("dicount").focus();
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
			<h1><span class="icon">&nbsp;</span>车险保费计算信息管理</h1>
		</div>
		<form id="inputForm" class="validate" action="car_rateinformation!save.action" method="post" enctype="multipart/form-data">
			<table class="inputTable">
				<tr>
					<th>
						保险公司:
					</th>
					<td>
					   <select id="companyCode" name="carRateIF.companyCode" class="{required: true}">
						  <option value="-1">----请选择----</option>
						   <#if (coms != null && coms?size > 0)>
							<#list coms as list>
					            <option value="${list.supplierCode}">${list.shortName}</option>
					        </#list>
					      </#if>
					   </select>
					</td>
				</tr>
				<tr>
					<th>
						地区:
					</th>
					<td>
					   <select id="regionCode" name="carRateIF.regionCode" >
					   <option value="-1">---请选择---</option>
					      <#if (citys != null && citys?size > 0)>
							<#list citys as list>
					            <option value="${list.placeCode}">${list.placeName}</option>
					        </#list>
					      </#if>
					   </select>
					</td>
				</tr>
				<tr>
					<th>
						模板类型:
					</th>
					<td>
					   <select id="modelType" name="carRateIF.modelType" >
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
						折扣系数:
					</th>
					<td>
					   <input type="text" name="carRateIF.dicount" id= "dicount"/>
					</td>
				</tr>
				<tr>
					<th>
						是否激活:
					</th>
					<td>
					   <select id="flag" name ="carRateIF.flag">
					       <option value="Y">是</option>
					       <option value="N">否</option>
					   </select>
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