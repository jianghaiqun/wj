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
			<h1><span class="icon">&nbsp;</span>车险保费计算信息管理</h1>
		</div>
		<div class="inputBar">
			<h1><span class="icon">&nbsp;</span><a href="car_rateinformation!add.action">新增</a></h1>
		</div>
			<table class="inputTable">
				<tr>
					<th>
						保险公司
					</th>
					<th>
						地区
					</th>
					<th>
						模板类型
					</th>
					<th>
						折扣系数
					</th>
					<th>
						是否激活
					</th>
					<th>
						操作
					</th>
				</tr>
				<#if (pager != null&& pager.list!=null && pager.list?size > 0)>
					<#list pager.list as list>
				        <tr>
						   <td>
						      <#if (coms != null && coms?size > 0)>
							     <#list coms as list1>
					                  <#if (list1.supplierCode==list.companyCode)>
					                      ${list1.shortName}
					                  </#if>
					             </#list>
					          </#if>
						   </td>
						   <td>
						      <#if (citys != null && citys?size > 0)>
							     <#list citys as list1>
					                  <#if (list1.placeCode==list.regionCode)>
					                      ${list1.placeName}
					                  </#if>
					             </#list>
					          </#if>
						   </td>
						   <td>${list.modelType}模板</td>
						   <td>${list.dicount}</td>
						   <td>
						      <#if (list.flag== "Y")>
								  已激活
							  <#else>
								未激活
							</#if>
						   </td>
						   <td>
						      <a href="car_rateinformation!modify.action?id=${list.id}">修改</a>
						   </td>
				        </tr>
					</#list>
			    </#if>
			</table>
	</div>
</body>
</html>