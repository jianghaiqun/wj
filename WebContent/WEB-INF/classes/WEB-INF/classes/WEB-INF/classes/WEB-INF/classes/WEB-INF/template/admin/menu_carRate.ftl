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
				<span>车险费率导入</span>
			</dt>
			<dd>
				<a href="car_rate!commercialRate.action" target="mainFrame">商业险费率</a>
			</dd>
			<dd>
				<a href="car_rate!importJQXRate.action" target="mainFrame">交强险费率导入</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>车险套餐定义</span>
			</dt>
			<dd>
				<a href="car_menu!modify.action" target="mainFrame">套餐定义</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>保费计算信息管理</span>
			</dt>
			<dd>
				<a href="car_rateinformation!list.action" target="mainFrame">保费计算信息管理</a>
			</dd>
		</dl>
	</div>
</body>
</html>