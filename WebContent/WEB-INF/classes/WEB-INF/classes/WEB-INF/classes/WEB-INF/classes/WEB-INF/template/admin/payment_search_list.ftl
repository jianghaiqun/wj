<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>支付记录列表</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/admin/js/list.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/calendar.js"></script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>支付记录列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="payment_search!searchlist.action" method="post">
			<div class="operateBar">
			<tr>
			        <th>
						用户名:
					</th>
					<td>
						<input type="text" name="payment.payer" value=""  />
					</td>
					<th>
						支付类型:
					</th>
					<td>
						<select name="payment.paymentType" >
							<option value="">请选择...</option>
							<#list allPaymentType as list>
								<option value="${list}"<#if (list == payment.paymentType)!> selected</#if>>
									${action.getText("PaymentType." + list)}
								</option>
							</#list>
						</select>
					</td>
					<th>
						订单号:
					</th>
					<td>
						<input type="text" name="payment.orderSn"   />
					</td>
					<th>
						保单号:
					</th>
					<td>
						<input type="text" name="payment.cotNo"   />
					</td>
					<th>
						保险公司:
					</th>
					<td>
						<input type="text" name="payment.brand"   />
					</td>
			</tr>
			<tr>										
					<th>
						支付状态:
					</th>
					<td>
						<select name="payment.paymentStatus" >
							<option value="">请选择...</option>
							<#list allPaymentStatus as list>
								<option value="${list}"<#if (list == payment.paymentStatus)!> selected</#if>>
									${action.getText("PaymentStatus." + list)}
								</option>
							</#list>
						</select>
					</td>					
					<th>
						支付时间:
					</th>
					<td>
						<input type="text" onfocus="setday(this)" readonly="readonly" name="payment.createDate" value=""  />
					</td>
			        <th>
						状态:
					</th>
					<td>
						<input type="text" name="payment.status"   />
					</td>										
				<label ></label><input type="button" id="searchButton" class="searchButton" value="" />
				<label>每页显示:</label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10" <#if pager.pageSize == 10>selected="selected" </#if>>
						10
					</option>
					<option value="20" <#if pager.pageSize == 20>selected="selected" </#if>>
						20
					</option>
					<option value="50" <#if pager.pageSize == 50>selected="selected" </#if>>
						50
					</option>
					<option value="100" <#if pager.pageSize == 100>selected="selected" </#if>>
						100
					</option>
				</select>
	</tr>
			</div>
			<table class="listTable">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<span class="sort" name="payer">用户名</span>
					</th>
					<th>
						<span class="sort" name="paymentType">支付类型</span>
					</th>
					<th>
						<span class="sort" name="order">订单号</span>
					</th>
					<th>
						<span class="sort" name="cotNo">保单号</span>
					</th>	
					<th>
						<span class="sort" name="brand">保险公司</span>
					</th>				
					<th>
						<span class="sort" name="paymentStatus">支付状态</span>
					</th>
					<th>
						<span class="sort" name="createDate">支付时间</span>
					</th>
					<th>
						<span class="sort" name="createDate">收款日期</span>
					</th>
					<th>
						<span class="sort" name="applicantName">投保人姓名</span>
					</th>
					<th>
						<span class="sort" name="recognizeeName">被保人姓名</span>
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${(list.id)!}" />
						</td>
						<td>
							${list.payer}
						</td>
						<td>
							${action.getText("PaymentType." + list.paymentType)}
						</td>
						<td>
							${list.order.orderSn}
						</td>
						<td>
							${list.cotNo}
						</td>
						<td>
							${list.brand}
						</td>						
						<td>
							${action.getText("PaymentStatus." + list.paymentStatus)}
						</td>
						<td>
							<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
						</td>
						<td>
							<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
						</td>
						<td>
							${list.applicantName}
						</td>
						<td>
							${list.recognizeeName}
						</td>
						<td>
							<a href="payment!view.action?id=${list.id}" title="查看">[查看]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton" url="payment!delete.action" value="删 除" disabled hidefocus="true" />
					<#include "/WEB-INF/template/admin/pager.ftl" />
				</div>
			<#else>
				<div class="noRecord">
					没有找到任何记录!
				</div>
			</#if>
		</form>
	</div>
</body>
</html>