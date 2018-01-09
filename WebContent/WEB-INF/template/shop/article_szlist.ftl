<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${articleCategory.name} 保险知识、帮助</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<#if (articleCategory.metaKeywords)! != ""><meta name="keywords" content="${articleCategory.metaKeywords}" /></#if>
<#if (articleCategory.metaDescription)! != ""><meta name="description" content="${articleCategory.metaDescription}" /></#if>
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
					<#if (articleCategory.id=="8a36800e36be0e150136be274e51000a")><li class="link01"><span><a href="${base}/index_sz.html"></a></span></li>
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
    		<div class="bread"><span>首页</span><span>&gt;</span><#if (articleCategory.id=="8a36800e36be0e150136be274e51000a")><span><a href="${base}/shop/article!szlist.action?id=402881e83634a900013634ab0e600002">保险知识</a></span><#else><span><a href="${base}/shop/article!szlist.action?id=8a36800e36be0e150136be2714c60009">帮助</a></span></#if></div>
			<div class="wrapb">
				<div class="left01">
				<#if (articleCategory.id=="402881e83634a900013634ab0e600002")>
					<div class="knowledgemenu gray">
						<h3>热点文章</h3>
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
				<#if (articleCategory.id=="8a36800e36be0e150136be2714c60009")>
					<div class="knowledgetit"></div>
					<#else>
					<div class="helptit"></div>
					</#if>
					<div class="helpcont">
						<ul>
							<#list pager.list as list>
                			<li>
                			  <h3 class="listtit">
                            	<a href="${base}${list.htmlFilePath}" class="title">
                            		<#if list.title?length lt 40>
										${list.title}
									<#else>
										${list.title[0..40]}...
									</#if>
								</a>
                                <span class="author">
                                	作者: <#if list.author == "">未知<#else>${list.author}</#if>
                                </span>
                                <span class="createDate">
                                                                                              发布日期：${list.createDate}
                                </span>
                                </h3>
                                <div class="contentText">
									<#if list.contentText?length lt 100>
										${list.contentText}
									<#else>
										${list.contentText[0..100]}...
									</#if>
									<span class="blue"><a href="${base}${list.htmlFilePath}">全文>></a></span>
								</div>
      		        		</li>
                		</#list>
						</ul>
						</div>
					<div class="page">
					<link href="${base}/template/shop/css/pager.css" rel="stylesheet" type="text/css" />
         			<#import "/WEB-INF/template/shop/pager.ftl" as p>
         			<#assign parameterMap = {"id": (articleCategory.id)!} />
         			<@p.pager pager = pager baseUrl = "${base}/shop/article!szlist.action?" parameterMap=parameterMap />
					</div>
					<div class="helpbot"></div>
				</div>
			</div>
			<div class="h10"></div>
			<div class="clear"></div>
    	</div>
		<div class="clear"></div>
    </div>
</body>
</html>