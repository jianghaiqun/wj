<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table cellspacing="0" cellpadding="0" width="95%" border="0" align="center" class="member_nearorderTable">
	<tbody>
		<tr>
			<th width="40%;">评测文章</th>
			<th width="60%;">回复内容</th>
		</tr>
		<s:iterator id="list" value="#request.sclist">
			<tr>
				<td>
					<a class="pc_tit" target="_blank" href="<%=Config.getFrontServerContextPath() %>/<s:property value="#list.url" />">
						<s:property value="#list.title" /></a>
					</td>
				<td><div class="pc_con"><s:property
					value="#list.replyContent" /></div></td>
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