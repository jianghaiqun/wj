<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table cellspacing="0" cellpadding="0" border="0" align="center" class="member_nearorderTable">
	<tbody>
		<tr>
			<th width="371px;">标题</th>
			<th width="186px;">内容</th>
			<th width="163px;">回答时间</th>
		</tr>
		<s:iterator id="list" value="#request.sclist">
			<tr>
				<td><span
					class="member_qatextCase">
				<s:if test="#list.url == null || #list.url==''">
					<span style="color:#999999;">
					 <s:property value="#list.questionTitle" />
					 </span>
				</s:if>
				<s:else>
					<a target="_blank" href="<%=Config.getFrontServerContextPath() %>/<s:property value="#list.url" />"><s:property value="#list.questionTitle" /></a>
				</s:else>
					</span></td>
				<td><span class="member_qacontentCase"><s:property
					value="#list.content" /></span></td>
				<td><s:property value="#list.addDate" /></td>
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
					<li class="page_prev"><a href="javascript:jumpPage('answer','<s:property value="#request.page-1"/>')"><span class="">上一页</span></a></li>
				</s:else>
				<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
					<li class="<s:property value="#pageFootMap.class" />">
						<s:if test="#pageFootMap.index==\"...\"">
							<span><s:property value="#pageFootMap.index"/></span>
						</s:if>
						<s:else>
							<a href="javascript:jumpPage('answer','<s:property value="#pageFootMap.index"/>')">
			        			<span><s:property value="#pageFootMap.index"/></span>
			        		</a>
						</s:else>
	        		</li>
	        	</s:iterator>
	        	<s:if test="#request.page==#request.lastpage || #request.lastpage == 0">
					<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_next"><a href="javascript:jumpPage('answer','<s:property value="#request.page+1"/>')"><span class="">下一页</span></a></li>
				</s:else>
			</ul>
		</div>
	</div>
</div>
</s:if>