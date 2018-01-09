<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<table  border="1" class="yhj_table">
	<tbody>
		<tr>
			<th scope="col" width="88px;">编号
				<input type="hidden" value="<s:property value="page"/>" name="pc" id="pg" /> 
				<input type="hidden" value="<s:property value="lastpage"/>" name="lpg" id="lg" /> 
			</th>
			<th scope="col" width="207px;">优惠券</th>
			<th scope="col" width="120px;">有效期</th>
			<th scope="col" width="300px;">使用说明</th>
		</tr>
		<s:iterator id="list" value="#request.colist">
			<tr>
				<td><s:property value="#list.serialno" /></td>
				<td>
					<div class="member_yhq_bg">
						<div class="member_yhq_j">
							<p class="member-yhq-tit"><em><s:property value="#list.unit" /></em><span><s:property value="#list.parValue" /></span></p>
							<p class="member-yhq-tit2"><em>
							<s:if test="#list.unit==\"折\"">
								折扣券
							</s:if>
							<s:else>
								优惠券
							</s:else>
							</em> <span><s:property value="#list.shortName" /></span></p>
						</div>
						<div class="clear"></div>
						<p class="memeber-yhq-des">有效日期 <em><s:property value="#list.startTime" /> 至 <s:property value="#list.endTime" /></em></p>
					</div>
				</td>
				<td><s:property value="#list.startTime" /><br>至 <br><s:property value="#list.endTime" /></td>
				<td style="text-align: left; padding:0  5px 0px  25px"><s:property value="#list.direction" /></td>
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
					<li class="page_prev"><a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page-1"/>','<s:property value="#request.lastpage"/>','member_tabarea2')"><span class="">上一页</span></a></li>
				</s:else>
				<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
					<li class="<s:property value="#pageFootMap.class" />">
						<s:if test="#pageFootMap.index==\"...\"">
							<span><s:property value="#pageFootMap.index"/></span>
						</s:if>
						<s:else>
							<a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#pageFootMap.index"/>','<s:property value="#request.lastpage"/>','member_tabarea2')">
			        			<span><s:property value="#pageFootMap.index"/></span>
			        		</a>
						</s:else>
	        		</li>
	        	</s:iterator>
	        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
					<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_next"><a href="javascript:gotoPage('<s:property value="#request.actionAlias"/>','<s:property value="#request.page+1"/>','<s:property value="#request.lastpage"/>','member_tabarea2')"><span class="">下一页</span></a></li>
				</s:else>
			</ul>
		</div>
	</div>
</div>
</s:if>