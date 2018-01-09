<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>库存列表</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${shopStaticPath}/template/admin/js/list.js"></script>
</head>
<body class="list">
	<div class="body">
		<div class="listBar">
			<h1><span class="icon">&nbsp;</span>礼品库存列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></h1>
		</div>
		<form id="listForm" action="gift!list.action" method="post">
			<div class="operateBar">
				<#if (needReturnBtn!)=="noneed">
					<label>礼品库存分类:&nbsp</label>
					<select name="id" id="stockName" class="{required: true}" onchange="submit();">
						<option value="">请选择...</option>
						<#list stockList as list>
							<option value="${list.id}"<#if (list.id == id)!> selected</#if>>
								${list.name}
							</option>
						</#list>
					</select>&nbsp;&nbsp;
				</#if>
				<!--<input type="button" class="formButtonL" onclick="location.href='gift!add.action'" value="添加礼品库存" />-->
				<#if (needReturnBtn!)=="need">
				<input type="button" class="formButton" onclick="location.href='stock!list.action'" value="返  回" hidefocus="true" />
				</#if>
				<label <#if (needReturnBtn!)=="noneed">style="margin-left:-20px;"</#if>>每页显示:</label>
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
			</div>
			<table class="listTable" style="table-layout:fixed;">
				<tr>
					<th>
						分类名称&nbsp;
					</th>
					<th>
						卡号&nbsp;
					</th>
					<#if false>
					<!-- zhangjinquan 11180 王恩建2012-11-15要求隐藏密码列，不进行转换  -->
					<th width="75px;">
						密码&nbsp;
					</th>
					</#if>
					<th width="75px;">
						有效期&nbsp;
					</th>
					<th width="200px;">
						说明&nbsp;
					</th>
					<th width="50px;">
						状态&nbsp;
					</th>
					<th width="150px;">
						获得帐号&nbsp;
					</th>
					<th width="140px;">
						使用时间&nbsp;
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td  >
						    ${list.stock.name}
						</td>
						
						<td>
							${list.cardNo}
						</td>
						<#if false>
						<!-- zhangjinquan 11180 王恩建2012-11-15要求隐藏密码列，不进行转换  -->
						<td width="75px;">
							<div style="width=100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" onmouseover="this.style.overflow='visible';" onmouseout="this.style.overflow='hidden';">
								${list.password}
							</div>
						</td>
						</#if>
						<td width="75px;">
							<#if (list.expireDate)??>${(list.expireDate)?string("yyyy-MM-dd")}</#if>
						</td>
						<td width="200px;">
							<div style="width=100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" onmouseover="this.style.overflow='visible';" onmouseout="this.style.overflow='hidden';">
							${list.description}
							</div>
						</td>
						
						<td width="50px;">
							${list.status}
						</td>
						
						<td width="150px;">
							<div style="width=100%;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" onmouseover="this.style.overflow='visible';" onmouseout="this.style.overflow='hidden';">
							${list.memberID}
							</div>
						</td>
						
						<td width="140px;">
							<#if (list.sendDate)??>${(list.sendDate)?string("yyyy-MM-dd HH:mm:ss")}</#if>
						</td>

						<td>
							<#if !(list.memberID)??>
								<a href="gift!edit.action?id=${list.id}" title="编辑">[编辑]</a>
								<!--<a href="gift!delete.action?id=${list.id}" title="删除" id="deleteAction" onclick="return confirm('您确定要删除该礼品库存吗？');">[删除]</a>-->
							</#if>
						</td>
					</tr>
				</#list>
			</table>
		    <#if (pager.list?size > 0)>
				<div class="pagerBar">
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