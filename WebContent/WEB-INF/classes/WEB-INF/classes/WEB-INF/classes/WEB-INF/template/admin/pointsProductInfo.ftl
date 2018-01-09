<#list productList as productInfo>
	<dl class="twoTribeDl">
		<dt>
			<a href="${productInfo.linkUrl}" target="_blank"><img src="${productInfo.logoUrl}" width="213px" height="169px" alt=""></a>
			<span class="ellipsis"><a href="${productInfo.linkUrl}" target="_blank">${productInfo.prop7}</a></span>
		</dt>
		<dd>
			<span class="fleft"><em class="exchagneNums box_round_4">
				<a href="${productInfo.linkUrl}" target="_blank">积分${productInfo.points}</a></em>
			</span>
			<span class="f_mi fright right exchangeNum">仅剩${productInfo.lastNum}件</span>
		</dd>
	</dl>
</#list>
<div class="clear h20"></div>
