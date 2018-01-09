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
				<span>产品管理</span>
			</dt>
			<dd>
				<a href="product!list.action" target="mainFrame">产品列表</a>
			</dd>
			<dd>
				<a href="product!add.action" target="mainFrame">添加产品</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>产品分类管理</span>
			</dt>
			<dd>
				<a href="product_category!list.action" target="mainFrame">分类列表</a>
			</dd>
			<dd>
				<a href="product_category!add.action" target="mainFrame">添加分类</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>产品类型管理</span>
			</dt>
			<dd>
				<a href="product_type!list.action" target="mainFrame">类型列表</a>
			</dd>
			<dd>
				<a href="product_attribute!list.action" target="mainFrame">属性列表</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>产品投保信息管理</span>
			</dt>
			<dd>
				<a href="product_ins_type!list.action" target="mainFrame">投保类型列表</a>
			</dd>
			<dd>
				<a href="product_ins_attribute!list.action" target="mainFrame">投保属性列表</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>保险公司管理</span>
			</dt>
			<dd>
				<a href="brand!list.action" target="mainFrame">保险公司列表</a>
			</dd>
			<dd>
				<a href="brand!add.action" target="mainFrame">添加保险公司</a>
			</dd>
		</dl>
			<dl>
			<dt>
				<span>支付管理</span>
			</dt>
			<dd>
				<a href="payment_config!list.action" target="mainFrame">支付方式</a>
			</dd>
			<dd>
				<a href="payment_company!list.action" target="mainFrame">网银支付管理</a>
			</dd>			
		</dl>
		
	</div>
</body>
</html>