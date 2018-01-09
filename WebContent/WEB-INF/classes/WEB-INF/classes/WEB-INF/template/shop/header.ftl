<script type="text/javascript" src="${base}/template/shop/js/header.js"></script>
<link href="${base}/template/shop/css/header.css" rel="stylesheet" type="text/css" />
<div class="header png">
	<div class="headerTop png">
		<div class="headerTopContent">
			<div class="headerLoginInfo">
				您好<span id="headerLoginMemberUsername"></span>，欢迎来到${(systemConfig.shopName)!}!^_^<font color="#ff662b"> 中国最优质的网上保险超市</font>
				<a href="#" id="headerShowLoginWindow" class="showLoginWindow"> 登录</a>
				<a href="${base}/shop/member_center!index.action" id="headerMemberCenter">会员中心</a>
				| <a href="#" id="headerShowRegisterWindow" class="showRegisterWindow">免费注册</a>
				<a href="${base}/shop/member!logout.action" id="headerLogout">[退出]</a>
			</div>
			<div class="headerTopNav">
				<a href="http://weibo.com/51baibaoxiang" target="_blank"><img src="${base}/upload/image/wbbtn.gif" /></a>|
				<#list topNavigationList as list>
					<a href="<@list.url?interpret />"<#if list.isBlankTarget == true> target="_blank"</#if>>${list.name}</a>
					<#if list_has_next>|</#if>
				</#list>
			</div>
		</div>
	</div>
	<div class="headerMiddle">
		
		<div class="headerInfo">
			<!--
			7×24小时服务热线：<strong>${(systemConfig.phone)!}</strong>
			-->
		</div>
		<div>
			<a href="${base}/shop/premium_trial!show.action">车险</a>
			<a href="${base}/shop/non_auto!accidentInsurance.action">意外险</a>
			<a href="${base}/shop/non_auto!travelInsurance.action">旅游险</a>
			<a href="${base}/shop/non_auto!healthInsurance.action">健康险</a>
			<a href="${base}/shop/non_auto!lifeInsurance.action">人寿险</a>
			<a href="${base}/shop/non_auto!homeInsurance.action">家财险</a>
		</div>
		<div class="headerLogo" >
			<a href="${base}/"><img class="png" src="${base}${(systemConfig.shopLogo)!}" title="${(systemConfig.shopName)!}" /></a>
		</div>
		<div class="headerSearch png">
			<form id="productSearchForm" action="${base}/shop/product!search.action" method="get">
				<div class="headerSearchText">
					<input type="text" id="productSearchKeyword" name="pager.keyword" value="<#if (pager.keyword != null && rootProductCategoryList != null)!>${pager.keyword}<#else>请输入关键词...</#if>"}" />
				</div>
				<input type="submit" class="headerSearchButton" value="" />
				<!--
				<div class="hotKeyword">
					热门关键词: 
					<#list systemConfig.hotSearchList as list>
						<a href="${base}/shop/product!search.action?pager.keyword=${list?url}">${list}</a>
					</#list>
				</div>
				-->
			</form>
		</div>
	</div>
	<div class="headerBottom" 	style="margin-top:45px;">
		<input type="button" class="cartItemListButton showCartItemList" value="" onclick="window.open('${base}/shop/order!info.action')" />
		<ul id="cartItemListDetail" style="z-index:2"></ul>
		<input type="button" class="orderButton" value="" onclick="window.open('${base}/shop/order!info.action')" />
		<div class="headerMiddleNav" style="margin-top:1px;">
			<div class="headerMiddleNavLeft png"></div>
			<ul class="headerMiddleNavContent png">
				<!--
				<#list middleNavigationList as list>
					<li>
						<a href="<@list.url?interpret />"<#if list.isBlankTarget == true> target="_blank"</#if>>${list.name}</a>
					</li>
				</#list>
				-->
					<li class="mnav01">
						<a href="${base}/" title="首页">&nbsp;</a>
					</li>
					
					<li class="mnav02">
						<a href="${base}/shop/product!listhome.action?id=402880e7327a51d201327a539dad0001" title="产品中心">&nbsp;</a>
					</li>
					<li class="mnav03">
						<a href="${base}/shop/article!list.action?id=8a368087328b9e5f01328be4aaea0007" title="客户服务">&nbsp;</a>
					</li>
					<li class="mnav04">
						<a href="${base}/shop/article!list.action?id=8a368087328b9e5f01328be355ba0003" title="保险小课堂">&nbsp;</a>
					</li>
					<li class="mnav05">
						<a href="${base}/shop/article!list.action?id=8a368087328b9e5f01328be3cf9d0004" title="行业动态">&nbsp;</a>
					</li>
			</ul>
			<div class="headerMiddleNavRight png"></div>
		</div>
	</div>
</div>
	<div style="height:45px"></div>