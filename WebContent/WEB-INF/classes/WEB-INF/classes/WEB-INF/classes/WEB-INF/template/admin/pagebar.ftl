<div class="ExchangePage">
	<div class="plpage">
		<div class="page_area">
			<div class="pagination">
				<ul>
				<#list pageList as page>
					<li class='${page.class}'>
					<#if (page.num =="...")!>
						<span>${page.num}</span>
					<#else>
						<a href='javascript:void(0)' onClick="searchPointsProduct('${page.num}');"><span>${page.num}</span></a>
					</#if>
					</li>
				</#list>
				</ul>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>