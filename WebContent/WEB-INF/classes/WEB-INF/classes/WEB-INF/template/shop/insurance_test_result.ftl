 <!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>保险测试结果列表页</title>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_test.css">
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.js"></script>

<script src="${shopStaticPath}/Test.js"></script>
<#include "/wwwroot/kxb/block/community_v1.shtml">
<#include "/wwwroot/kxb/block/kxb_header_new_css.shtml">
</head>
<body>
<!-- 公共头部 开始 -->
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<!-- 公共头部 结束 -->

<!--保险测试开始-->
<div class="wrapper test_bg">
<div class="test_con test_con_n_b">
<h2 class="test_title_s"><span class="test_jd ya_hei">保险测试分析结果</span></h2>
<dl class="need_box cf">
	<#if (list_one?size = 0)>
		<dt class="need_titile  need_titile_none"><span class="${start_one}">${title_one}</span>
	</#if> 
	<#list list_one as map>
		<#if (map.level !=0)!>
			<dt class="need_titile "><span class="${start_one}">${title_one}</span>
			<#break>
		<#else>
			<dt class="need_titile need_titile_none"><span class="${start_one}">${title_one}</span>
			<#break>
		</#if> 
	</#list>
	<#list list_one as map>
		<#if (map.level <3)!>
			<i class="need_tit_c need_grean">
			<#break>
		<#elseif (map.level =3)!>
			<i class="need_tit_c need_orange">
			<#break>
		<#elseif (map.level >3)!>
			<i class="need_tit_c need_red">
			<#break>
		</#if> 
	</#list>
	${warn_one}</i></dt>
	<dd class="need_con">
	<p class="need_des">${content_one}</p>
	<#list list_one as map>
		<#if (map.level !=0)!>
			<div class="in_s_list"><a rel="nofollow" target="_blank" href="${map.url}">
				<input id="productid" type='hidden' value="${map.productid}"/>
				<img width="190" height="190" alt="${map.productname}" src="${map.logolink}" 
				style="display: inline;"></a> <span class="in_s_des in_s_mar"><a target="_blank" 
				href="${map.url}" title="${map.productname}">${map.productname}</a></span>
				<span class="in_s_des"><span class="in_s_pay dor_pay">￥${map.initprem}</span>
				<a rel="nofollow" target="_blank" class="in_s_go" href="${map.url}">去看看&gt;&gt;</a></span>
			</div>
		</#if>
	</#list>
	<a href="${product_list_one}" class="in_s_list_more">查看更多</a></dd>
</dl>

<dl class="need_box cf">

	<#if (list_two?size = 0)>
		<dt class="need_titile  need_titile_none"><span class="${start_two}">${title_two}</span>
	</#if>
	<#list list_two as map>
		<#if (map.level !=0)!>
			<dt class="need_titile need_active"><span class="${start_two}">${title_two}</span>
			<#break>
		<#else>
			<dt class="need_titile need_titile_none"><span class="${start_two}">${title_two}</span>
			<#break>
		</#if> 
	</#list>
	<#list list_two as map>
		<#if (map.level <3)!>
			<i class="need_tit_c need_grean">
			<#break>
		<#elseif (map.level =3)!>
			<i class="need_tit_c need_orange">
			<#break>
		<#elseif (map.level >3)!>
			<i class="need_tit_c need_red">
			<#break>
		</#if> 
	</#list>
	${warn_two}</i></dt>
	<dd class="need_con" style="display: none;">
	<p class="need_des">${content_two}</p>
	<#list list_two as map>
		<#if (map.level !=0)!>
			<div class="in_s_list"><a rel="nofollow" target="_blank" href="${map.url}">
				<input id="productid" type='hidden' value="${map.productid}"/>
				<img width="190" height="190" alt="${map.productname}" src="${map.logolink}"
				style="display: inline;"></a> <span class="in_s_des in_s_mar"><a
				target="_blank"
				href="${map.url}" title="${map.productname}">${map.productname}</a></span>
				<span class="in_s_des"><span class="in_s_pay dor_pay">￥${map.initprem}</span>
				<a rel="nofollow" target="_blank" class="in_s_go" href="${map.url}">去看看&gt;&gt;</a></span>
			</div>
		</#if>	
	</#list>
	<div class="clear"></div>
	<a href="${product_list_two}" class="in_s_list_more">查看更多</a></dd>
</dl>
<dl class="need_box cf">
	<#if (list_three?size = 0)>
		<dt class="need_titile  need_titile_none"><span class="${start_three}">${title_three}</span>
	</#if>
	<#list list_three as map>
		<#if (map.level !=0)!>
			<dt class="need_titile need_active"><span class="${start_three}">${title_three}</span>
			<#break>
		<#else>
			<dt class="need_titile need_titile_none"><span class="${start_three}">${title_three}</span>
			<#break>
		</#if> 
	</#list>
	<#list list_three as map>
		<#if (map.level <3)!>
			<i class="need_tit_c need_grean">
			<#break>
		<#elseif (map.level =3)!>
			<i class="need_tit_c need_orange">
			<#break>
		<#elseif (map.level >3)!>
			<i class="need_tit_c need_red">
			<#break>
		</#if> 
	</#list>
	${warn_three}</i></dt>
	<dd class="need_con" style="display: none;">
	<p class="need_des">${content_three}</p>
	<#list list_three as map>
		<#if (map.level !=0)!>
			<div class="in_s_list"><a rel="nofollow" target="_blank" href="${map.url}">
				<input id="productid" type='hidden' value="${map.productid}"/>
				<img width="190" height="190" alt="${map.productname}" src="${map.logolink}"
				style="display: inline;"></a> <span class="in_s_des in_s_mar"><a
				target="_blank"
				href="${map.url}" title="${map.productname}">${map.productname}</a></span>
				<span class="in_s_des"><span class="in_s_pay dor_pay">￥${map.initprem}</span>
				<a rel="nofollow" target="_blank" class="in_s_go" href="${map.url}">去看看&gt;&gt;</a></span>
			</div>
		</#if> 
	</#list>
	<div class="clear"></div>
	<a href="${product_list_three}" class="in_s_list_more">查看更多</a></dd>
</dl>
<dl class="need_box cf">
	<#if (list_four?size = 0)>
		<dt class="need_titile  need_titile_none"><span class="${start_four}">${title_four}</span>
	</#if>
	<#list list_four as map>
		<#if (map.level !=0)!>
			<dt class="need_titile need_active"><span class="${start_four}">${title_four}</span>
			<#break>
		<#else>
			<dt class="need_titile need_titile_none"><span class="${start_four}">${title_four}</span>
			<#break>
		</#if> 
	</#list>
	<#list list_four as map>
		<#if (map.level <3)!>
			<i class="need_tit_c need_grean">
			<#break>
		<#elseif (map.level =3)!>
			<i class="need_tit_c need_orange">
			<#break>
		<#elseif (map.level >3)!>
			<i class="need_tit_c need_red">
			<#break>
		</#if> 
	</#list>
	${warn_four}</i></dt>
	<dd class="need_con" style="display: none;">
	<p class="need_des">${content_four}</p>

	<#list list_four as map>
		<#if (map.level !=0)!>
			<div class="in_s_list"><a rel="nofollow" target="_blank" href="${map.url}">
				<input id="productid" type='hidden' value="${map.productid}"/>
				<img width="190" height="190" alt="${map.productname}" src="${map.logolink}"
				style="display: inline;"></a> <span class="in_s_des in_s_mar"><a
				target="_blank"
				href="${map.url}" title="${map.productname}">${map.productname}</a></span>
				<span class="in_s_des"><span class="in_s_pay dor_pay">￥${map.initprem}</span>
				<a rel="nofollow" target="_blank" class="in_s_go" href="${map.url}">去看看&gt;&gt;</a></span>
			</div>
		</#if> 
	</#list>
	<div class="clear"></div>
	<a href="${product_list_four}" class="in_s_list_more">查看更多</a></dd>
</dl>
<dl class="need_box cf">
	<#if (list_five?size = 0)>
		<dt class="need_titile  need_titile_none"><span class="${start_five}">${title_five}</span>
	</#if>
	<#list list_five as map>
		<#if (map.level !=0)!>
			<dt class="need_titile need_active"><span class="${start_five}">${title_five}</span>
			<#break>
		<#else>
			<dt class="need_titile need_titile_none"><span class="${start_five}">${title_five}</span>
			<#break>
		</#if> 
	</#list>
	<#list list_five as map>
		<#if (map.level <3)!>
			<i class="need_tit_c need_grean">
			<#break>
		<#elseif (map.level =3)!>
			<i class="need_tit_c need_orange">
			<#break>
		<#elseif (map.level >3)!>
			<i class="need_tit_c need_red">
			<#break>
		</#if> 
	</#list>
	${warn_five}</i></dt>
	<dd class="need_con" style="display: none;">
	<p class="need_des">${content_five}</p>

	<#list list_five as map>
		<#if (map.level !=0)!>
			<div class="in_s_list"><a rel="nofollow" target="_blank" href="${map.url}">
				<input id="productid" type='hidden' value="${map.productid}"/>
				<img width="190" height="190" alt="${map.productname}" src="${map.logolink}"
				style="display: inline;"></a> <span class="in_s_des in_s_mar"><a
				target="_blank"
				href="${map.url}" title="${map.productname}">${map.productname}</a></span>
				<span class="in_s_des"><span class="in_s_pay dor_pay">￥${map.initprem}</span>
				<a rel="nofollow" target="_blank" class="in_s_go" href="${map.url}">去看看&gt;&gt;</a></span>
			</div>
		</#if> 
	</#list>
	<div class="clear"></div>
	<a href="${product_list_five}" class="in_s_list_more">查看更多</a></dd>
</dl>
<#if (list_six?size != 0)>
	<dl class="need_box cf">
	    <dt class="need_titile need_titile need_active">
	    <span class="need_name need_grade">车&nbsp;&nbsp;&nbsp;&nbsp;险<i class="need_car_des">建议投保，开心保为您提供如下产品</i></span>
	    </dt>
		<dd class="need_con" style="display: none;">
		<#list list_six as map>
			<div class="in_s_list in_s_list2">
				<img width="201px" height="98px"  src="${map.logolink}">
				 <span class="in_s_des in_s_mar"><a
				target="_blank"
				href="${map.url}" title="${map.productname}">${map.productname}</a></span>
				<a rel="nofollow" target="_blank" class="in_s_go" href="${map.url}">去看看&gt;&gt;</a></span>
			</div>
		</#list>
		<div class="clear"></div>
		<a href="${product_list_six}" class="in_s_list_more">查看更多</a></dd>
	</dl>
</#if>
</div>
</div>
<!--保险测试结束-->

<!--公共底部开始-->
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!--公共底部结束-->
</body>
</html>