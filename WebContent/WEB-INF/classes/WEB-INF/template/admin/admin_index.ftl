<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>管理中心首页</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/admin/css/admin.css" rel="stylesheet" type="text/css" />
<!--
<script type="text/javascript">
$().ready( function() {

	// 在线体验只读提示
	$.message("warn", "提示：您现在访问的是SINOSOFT体验站点，您的任何修改性操作将不被执行！");
	
});
</script>
-->
</head>
<body class="admin">
	<div class="adminBar">
		<span class="icon">&nbsp;</span>欢迎使用中科软电子商务管理系统！
	</div>
	<div class="body">
		<div class="bodyLeft">
			<table class="listTable">
				<tr>
					<th colspan="4">
						软件信息
					</th>
				</tr>
				<tr>
					<td width="110">
						系统名称:
					</td>
					<td>
						<span>中科软电子商务系统</span>
					</td>
				</tr>
				<tr>
					<td>
						系统版本:
					</td>
					<td>
						${systemConfig.systemVersion}
					</td>
				</tr>
				<tr>
					<td>
						官方网站:
					</td>
					<td>
						<a href="http://www.sinosoft.com.cn">http://www.sinosoft.com.cn</a>
					</td>
				</tr>
				<tr>
					<td>
						技术论坛:
					</td>
					<td>
						<a href="http://www.sinosoft.com.cn">http://www.sinosoft.com.cn</a>
					</td>
				</tr>
				<tr>
					<td>
						授权查询:
					</td>
					<td>
						<a href="http://www.sinosoft.com.cn">在线查询</a>
					</td>
				</tr>
				<tr>
					<td>
						授权提示:
					</td>
					<td>
						<span class="red">在未取得中科软商业授权之前，您不能将本软件应用于商业用途</span>
					</td>
				</tr>
			</table>
			<div class="blank"></div>
			<div class="blank"></div>
			<table class="listTable">
				<tr>
					<th colspan="4">
						待处理事务
					</th>
				</tr>
				<tr>
					<td width="110">
						未处理投保:
					</td>
					<td>
						${unprocessedOrderCount} <a href="order!list.action">[投保列表]</a>
					</td>
				</tr>
				<tr>
					<td width="110">
						等待投送保单数:
					</td>
					<td>
						${paidUnshippedOrderCount} <a href="order!list.action">[投保列表]</a>
					</td>
				</tr>
				<tr>
					<td>
						未读消息:
					</td>
					<td>
						${unreadMessageCount} <a href="message!inbox.action">[收件箱]</a>
					</td>
				</tr>
				<tr>
					<td>
						产品库存报警:
					</td>
					<td>
						${storeAlertCount} <a href="product!list.action">[产品列表]</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="bodyRight">
			<table class="listTable">
				<tr>
					<th colspan="4">
						系统信息
					</th>
				</tr>
				<tr>
					<td width="110">
						Java版本:
					</td>
					<td>
						${statics["java.lang.System"].getProperty("java.version")}
					</td>
				</tr>
				<tr>
					<td>
						操作系统名称:
					</td>
					<td>
						${statics["java.lang.System"].getProperty("os.name")}
					</td>
				</tr>
				<tr>
					<td>
						操作系统构架:
					</td>
					<td>
						${statics["java.lang.System"].getProperty("os.arch")}
					</td>
				</tr>
				<tr>
					<td>
						操作系统版本:
					</td>
					<td>
						${statics["java.lang.System"].getProperty("os.version")}
					</td>
				</tr>
				<tr>
					<td>
						当前工作目录:
					</td>
					<td>
						${statics["java.lang.System"].getProperty("user.dir")}
					</td>
				</tr>
				<tr>
					<td>
						临时文件路径:
					</td>
					<td>
						${statics["java.lang.System"].getProperty("java.io.tmpdir")}
					</td>
				</tr>
			</table>
			<div class="blank"></div>
			<div class="blank"></div>
			<table class="listTable">
				<tr>
					<th colspan="4">
						信息统计
					</th>
				</tr>
				<tr>
					<td width="110">
						已上架产品:
					</td>
					<td>
						${marketableProductCount}
					</td>
				</tr>
				<tr>
					<td>
						已下架产品:
					</td>
					<td>
						${unMarketableProductCount}
					</td>
				</tr>
				<tr>
					<td>
						会员总数:
					</td>
					<td>
						${memberTotalCount}
					</td>
				</tr>
				<tr>
					<td>
						文章总数:
					</td>
					<td>
						${articleTotalCount}
					</td>
				</tr>
			</table>
		</div>
		<p class="copyright">中科软科技股份有限公司</p>
	</div>
</body>
</html>