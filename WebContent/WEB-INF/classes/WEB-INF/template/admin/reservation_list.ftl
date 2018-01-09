<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>投保列表</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/admin/js/list.js"></script>
</head>
<body class="list">
	<div class="body">
			<table class="listTable">
				<tr>
					<th>
						客户姓名
					</th>
					<th>
						联系方式
					</th>
					<th>
						创建日期
					</th>
					<th>
						所在地区
					</th>
					<th>
						预约产品
					</th>
				</tr>
				<#if (reservations != null && reservations?size > 0)>
					<#list reservations as list>
				        <tr>
						   <td>
						   		${list.customerName}
						   </td>
						   <td>
						   		${list.customerTel}
						   </td>
						   <td>${list.createDate}</td>
						   <td>${list.areaShow}</td>
						   <td>${list.productName}</td>
				        </tr>
					</#list>
			    </#if>
			</table>
	</div>
</body>
</html>