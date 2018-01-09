<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${article.title}</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<#if (article.metaKeywords)! != ""><meta name="keywords" content="${article.metaKeywords}" /></#if>
<#if (article.metaDescription)! != ""><meta name="description" content="${article.metaDescription}" /></#if>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#if (article.articleCategory.id!="8a36800e36be0e150136be274e51000a")&&(article.articleCategory.id!="8a36800e36be0e150136be2714c60009")>
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/article.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/article_content.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/article.js"></script>
<script type="text/javascript">
$().ready( function() {

	$hits = $("#hits");

	// 统计文章点击数
	$.ajax({
		url: "${base}/shop/article!ajaxCounter.action?id=${article.id}",
		dataType: "json",
		async: false,
		success: function(data) {
			if (data.status == "success") {
				$hits.text(data.hits);
			}
		}
	});

});
</script>
</head>
<body class="articleContent">
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body">
		<div class="bodyLeft">
			<div class="recommendArticle">
				<div class="top">推荐文章</div>
				<div class="middle clearfix">
					<ul>
						<#list (recommendArticleList)! as list>
							<li>
								<#if (list.title?length < 15)>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a>
								<#else>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 10>
								<#break />
							</#if>
						</#list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="hotArticle">
				<div class="top">热点文章</div>
				<div class="middle clearfix">
					<ul>
						<#list (hotArticleList)! as list>
							<li class="number${list_index + 1}">
								<#if (list.title?length < 15)>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a>
								<#else>
									<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 10>
								<#break />
							</#if>
						</#list>
					</ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="listBar">
				<div class="left"></div>
				<div class="middle">
					<div class="path">
						<a href="${base}/" class="home"><span class="icon"> </span>首页</a>>
						<#list pathList as list>
							<a href="${base}/shop/article!list.action?id=${list.id}">${list.name}</a>>
						</#list>
					</div>
					<div class="articleSearch">
						<form id="articleSearchForm" action="${base}/shop/article!search.action" method="get">
							<input type="text" name="pager.keyword" id="articleSearchKeyword" class="keyword" value="请输入关键词..." />
							<input type="submit" class="searchButton" value="" />
						</form>
					</div>
				</div>
				<div class="right"></div>
			</div>
			<div class="blank"></div>
			<div class="articleContentDetail">
				<div class="articleContentTop"></div>
				<div class="articleContentMiddle">
					<div class="title">${article.title}<#if (pageCount > 1 && pageNumber > 1)>(${pageNumber})</#if></div>
                    <div class="blank"></div>
                    <div class="info">
                    	<span class="createDate">日期：${article.createDate?datetime?string.short}</span>
                    	<#if article.author==""><span class="author">作者: ${article.author}</span></#if>
                    	点击：<span id="hits"></span> 次
                    	<span class="fontSize">【<a id="changeBigFontSize" href="javascript:void(0);">大</a> <a id="changeNormalFontSize" href="javascript:void(0);">中</a> <a id="changeSmallFontSize" href="javascript:void(0);">小</a>】</span>
                    </div>
					<div id="articleContent" class="content">
             			${content}
             			<div class="blank"></div>
             			<link href="${base}/template/shop/css/pager.css" rel="stylesheet" type="text/css" />
             			<#import "/WEB-INF/template/shop/pager.ftl" as p>
             			<@p.articleContentPager article = article pageNumber = pageNumber />
                    </div>
				</div>
				<div class="articleContentBottom"></div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
<#else>
<link href="${base}/template/shop/sz/css/main.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="loginwrap">
		<div class="login">
			<div class="loginleft"><span>您好，欢迎来到12580商城联盟！</span><span><a href="#">登录</a>|<a href="#">注册</a></span></div>
			<div class="loginright"><span><a href="#">返回商城首页</a>|<a href="#">我的联盟</a href="#">|<a>使用帮助</a></span></div>
		</div>
	</div>
    <div class="headwrap">
		<div class="head">
			<div class="logo"><a href="${base}/index_sz.html"><img src="${base}/template/shop/sz/images/logo.gif" /></a></div>
			<div class="navarea">
				<div class="navbgleft"></div>
					<ul class="nav">
					<#if (article.articleCategory.id=="8a36800e36be0e150136be274e51000a")><li class="link01"><span><a href="${base}/index_sz.html"></a></span></li>
					<li class="link02"><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be2714c60009"></a></span></li>
						<li class="link03on"><span></span></li>
					<#else><li class="link01"><span><a href="${base}/index_sz.html"></a></span></li>
					<li class="link02on"><span></span></li>
					<li class="link03"><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be274e51000a"></a></span></li>
					</#if>
					</ul>
				<div class="navbgright"></div>
			</div>
			<div class="bgwhiteline"></div>
		</div>
	</div>
    <div class="wrap">
		<div class="wrapa">
    		<div class="bread"><span>首页</span><span>&gt;</span><#if (article.articleCategory.id=="8a36800e36be0e150136be274e51000a")><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be274e51000a">保险知识</a></span><#else><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be2714c60009">帮助</a></span></#if><span>&gt;</span><span>文章详细</span></div>
			<div class="wrapb">
				<div class="left01">
				<#if (article.articleCategory.id=="8a36800e36be0e150136be274e51000a")>
					<div class="knowledgemenu gray">
						<h3>热点文章</h3>
						<ul>
							<#list (hotArticleList)! as list>
							<li class="number${list_index + 1}">
								<#if (list.title?length < 15)>
								<span class="icon"> </span>	<a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a>
								<#else>
								<span class="icon"> </span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 10>
								<#break/>
							</#if>
						</#list>
						</ul>
						<#else>
					<div class="helpmenu gray">
					<h3>常见问题</h3>
					<ul>
							<#list (hotArticleList)! as list>
							<li>
								<#if (list.title?length < 15)>
									<span>·</span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title}</a>
								<#else>
									<span>·</span><a href="${base}${list.htmlFilePath}" title="${list.title}">${list.title[0..12]}...</a>
								</#if>
							</li>
							<#if list_index + 1 == 10>
								<#break/>
							</#if>
						</#list>
						</ul>
					</#if>
						<div class="colum2bgbot"></div>
					</div>
				</div>
				<div class="right01">
					<div class="helptop"></div>
					<div class="artical">
						<div class="articaltit">${article.title}<#if (pageCount > 1 && pageNumber > 1)>(${pageNumber})</#if></div>
						<div class="articaldate"><span>作者：${article.author}</span><span>发布日期：${article.createDate}</span></div>
						<div class="articalcont">
						  	${content}
						 </div>
						  <div class="articalpage">	
             				<link href="${base}/template/shop/css/pager.css" rel="stylesheet" type="text/css" />
             			<#import "/WEB-INF/template/shop/pager.ftl" as p>
             			<@p.articleContentPager article = article pageNumber = pageNumber />
             				</div>
					  	<div class="articalback"><#if (article.articleCategory.id=="8a36800e36be0e150136be274e51000a")><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be274e51000a"><#else><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be2714c60009"><img src="${base}/template/shop/sz/images/backbtn.gif" /></a></span></#if></div>
					</div>
					<div class="helpbot"></div>
				</div>
			</div>
			<div class="h10"></div>
			<div class="clear"></div>
    	</div>
		<div class="clear"></div>
    </div>
    <iframe name="toppic"  src="${base}/template/shop/sz/static/foot.html"   frameborder=no width="100%" scrolling=no  height="100px"></iframe>
</body>
</#if>
</html>