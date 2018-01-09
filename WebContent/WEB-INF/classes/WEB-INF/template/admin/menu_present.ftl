<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理菜单</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/menu.css" rel="stylesheet" type="text/css" />
</head>
<body class="menu">
	<div class="menuContent">
		<dl>
			<dt>
				<span>礼品管理</span>
			</dt>
			<dd>
				<a href="present!list.action" target="mainFrame">礼品列表</a>
			</dd>
			<dd>
				<a href="present!add.action" target="mainFrame">添加礼品</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>礼品分类管理</span>
			</dt>
			<dd>
				<a href="present_category!list.action" target="mainFrame">分类列表</a>
			</dd>
			<dd>
				<a href="present_category!add.action" target="mainFrame">添加分类</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>兑换记录查询</span>
			</dt>
			<dd>
				<a href="present_obtain!list.action" target="mainFrame">兑换记录</a>
			</dd>
			<dd>
				<a href="present_obtain!statisticsList.action" target="mainFrame">成本统计</a>
			</dd>
		</dl>
	</div>
</body>
</html>