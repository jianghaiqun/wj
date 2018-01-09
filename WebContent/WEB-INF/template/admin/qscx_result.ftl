<#if orderInfo? has_content>
    <h3 class="qscx_titile"><span>查询结果</span><span class="order_num">共查询到<em>${totalNum}</em>条订单</span></h3>
    <div class="qscx_jg_con">
        <table width="100%" border="1" class="cx_jg_table">
            <tbody>
                <tr>
                    <th scope="col">订单号</th>
                    <th scope="col">投保人姓名</th>
                    <th scope="col">险种名称</th>
                    <th scope="col">支付日期</th>
                    <th scope="col">生效日期</th>
                </tr>
                <#assign indexnum = 1 />
                <#list orderInfo as list> 
					<#if (indexnum%2==0)>
						<tr class="even">
					<#else>
						<tr>
					</#if>
                        <td>${list.orderSn}</td>
                        <td>${list.applicantName}</td>
                        <td>${list.productName}</td>
                        <#if (list.payDate=="")>
                        	<td class="fail_order">未支付</td>
                        	<td class="fail_order">未生效</td>
                        <#else>
                        	<td>${list.payDate}</td>
                        	<#if (list.valiStatus=='1')>
	                        	<td class="fail_order">未生效</td>
	                        <#else>
	                        	<td>${list.svaliDate}</td>
	                        </#if>
                        </#if>
                        
                    </tr>
					<#assign indexnum=indexnum+1 />
				</#list> 
                </tbody>
            </table>
            <!-- 分页 -->
            <#if (lastpage > 1)>
            <div class="page_area">
              <div id="pagination">
                <ul>
                <#if (pageIndex == 1)>
                	<li class="page_prev"><span class="default">上一页</span></li>
                <#else>
                	<li class="page_prev"><a href="javascript:gotoPage(${pageIndex-1},${lastpage})"><span class="">上一页</span></a></li>
                </#if>
                <#list pageFootList as list>
                  <li class="${list.class}">
                  <#if (list.index == "...")>
                  	<span>${list.index}</span>
                  <#else>
                  	<a href="javascript:gotoPage(${list.index},${lastpage})"><span>${list.index}</span></a>
                  </#if>
                  </li>
                </#list>
                <#if (pageIndex == lastpage || lastpage == 0)>
                  <li class="page_next"><span class="default">下一页</span></li>
                <#else>
                	<li class="page_next"><a href="javascript:gotoPage(${pageIndex+1},${lastpage})"><span class="">下一页</span></a></li>
                </#if>
                </ul>
              </div>
            </div>
            </#if>
            <div class="qscx_tishi">
                <p>想了解更多订单信息，请登录<a href="${base}/shop/member_center!index.action">会员中心</a>查看</p>
                <p class="consult">若您有疑问，欢迎拨打开心保客服热线：<em>4009-789-789</em>或<a onclick="return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/xiaoneng" rel="nofollow" vlpageid="xiaoneng" href="javascript:void(0);" class="qq fix-onlineqq"><i class="qq_icon"></i>在线咨询</a></p>
            </div>
   
        </div>
<#else>
<div class="nosearch_mes">没有查询到任何信息。请核对输入的信息是否正确，或者直接进入<a href="${base}/shop/member_center!index.action">您的后台</a>进行查看。<br />
若您有疑问，欢迎拨打开心保客服热线：<span>4009-789-789</span>&nbsp;或&nbsp;<a class="zixun" onclick="return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/xiaoneng" vlpageid="xiaoneng"  href="javascript:void(0);">在线咨询</a>
</div>
</#if>

