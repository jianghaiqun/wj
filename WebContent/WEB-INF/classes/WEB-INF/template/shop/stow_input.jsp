<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<table cellspacing="0" cellpadding="0" border="0" align="center" class="member_nearorderTable">
	<tbody>
		<tr>
			<th width="217px;">产品名称</th>
            <th width="100px;">保险公司</th>
            <th width="100px;">产品类型</th>
            <th width="100px;">价格</th>
            <th width="100px">收藏时间</th>
            <th width="100px">操作</th>
		</tr>
		<s:iterator id="list" value="#request.listq">
			<tr>
				<td><span class="member_fproductCase"> 
				<s:if test="#list.isPublish==\"Y\"">
					<a href="<s:property value="#list.htmlPath"/>" target="_blank">
					<s:property value="#list.productName" />
					</a>
				</s:if> 
				<s:else>
					<a href="###" target="_blank" onclick="return xiajia();"><s:property
						value="#list.productName" /></a>
				</s:else> <em>适合人群：<s:property value="#list.adaptPeopleInfoV3"/></em>
				<s:if test="#list.CheepPrem!=\"0\"">
				<b>此产品比收藏时降价了<s:property value="#list.CheepPrem"/>元</b>
				</s:if> 
				</span></td>
				<td><s:property value="#list.insureName" /></td>
				<td><s:property value="#list.productGenera" /></td>
				<td><span class="member-sc-pay"><s:property value="#list.disPrem"/></span>
				<s:if test="#list.basePrem != null && #list.basePrem != \"\"">
				<span class="member-scxj">原价：<s:property value="#list.basePrem"/></span>
				</s:if>
				</td>
				<td><s:property value="#list.createDate" /></td>
				<td>
				<div class="member_paynow"><a href="###"
					onclick="return buyNow1('<s:property value="#list.htmlPath"/>','<s:property value="#list.isPublish"/>');">立即购买</a></div>
				<div class="member_orderoperateCancel"><a
					href="###" id="operateCancel_<s:property value="#list.productId"/>" onclick="delStow('<s:property value="#list.id"/>','<s:property value="#list.productId"/>')">取消收藏</a></div>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
<div class="clear h20"></div>
<s:if test="#request.lastpage > 1">
<div class="plpage">
	<!--    翻页    -->
	<div  class="page_area">
		<div id="pagination">
			<ul>
				<s:if test="#request.page==1">
					<li class="page_prev"><a href="#"><span class="default">上一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_prev"><a href="javascript:jumpPage('<s:property value="#request.page-1"/>')"><span class="">上一页</span></a></li>
				</s:else>
				<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
					<li class="<s:property value="#pageFootMap.class" />">
						<s:if test="#pageFootMap.index==\"...\"">
							<span><s:property value="#pageFootMap.index"/></span>
						</s:if>
						<s:else>
							<a href="javascript:jumpPage('<s:property value="#pageFootMap.index"/>')">
			        			<span><s:property value="#pageFootMap.index"/></span>
			        		</a>
						</s:else>
	        		</li>
	        	</s:iterator>
	        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
					<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_next"><a href="javascript:jumpPage('<s:property value="#request.page+1"/>')"><span class="">下一页</span></a></li>
				</s:else>
			</ul>
		</div>
	</div>
</div>
</s:if>