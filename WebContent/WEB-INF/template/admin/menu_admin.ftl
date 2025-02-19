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
				<span>管理员&nbsp;</span>
			</dt>
			<dd>
				<a href="admin!list.action" target="mainFrame">管理员列表</a>
			</dd>
			<dd>
				<a href="role!list.action" target="mainFrame">角色管理</a>
			</dd>
			<dd>
				<a href="resource!list.action" target="mainFrame">资源管理</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>站内消息&nbsp;</span>
			</dt>
			<dd>
				<a href="message!inbox.action" target="mainFrame">收件箱</a>
			</dd>
			<dd>
				<a href="message!outbox.action" target="mainFrame">发件箱</a>
			</dd>
		</dl>
		<dl>
			<dt>
				<span>操作日志管理&nbsp;</span>
			</dt>
			<dd>
				<a href="log!list.action" target="mainFrame">查看日志</a>
			</dd>
			<dd>
				<a href="log_config!list.action" target="mainFrame">日志配置</a>
			</dd>
		</dl>
		
				<dl>
			<dt>
				<span>网站设置</span>
			</dt>
			<dd>
				<a href="system_config!edit.action" target="mainFrame">系统设置</a>
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
				<a href="payment_company!list.action" target="mainFrame">支付方式管理</a>
			</dd>
			<dd>
				<a href="payment_search!list.action" target="mainFrame">支付记录管理</a>
			</dd>						
		</dl>
		<dl>
			<dt>
				<span>保单配送管理</span>
			</dt>
			<dd>
				<a href="delivery_type!list.action" target="mainFrame">保单配送方式</a>
			</dd>
			<dd>
				<a href="area!list.action" target="mainFrame">地区管理</a>
			</dd>
			<dd>
				<a href="delivery_corp!list.action" target="mainFrame">物流公司</a>
			</dd>
		</dl>
		
	</div>
</body>
</html>