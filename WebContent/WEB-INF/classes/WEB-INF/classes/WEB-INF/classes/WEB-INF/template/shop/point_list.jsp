<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
    <%@ taglib uri="/struts-tags" prefix="s" %>
<%
	String serverContext = Config.getServerContext();
%>
<div class="clear h20"></div>
<table cellspacing="0" cellpadding="0" border="0" align="center" class="member_nearorderTable">
	<tbody>
		<tr>
            <th width="197px">时间</th>
            <th width="130px">积分变化</th>
            <th width="397px">详细说明</th>
        </tr>
		<s:iterator id="list" value="#request.listq">
		<tr>
            <td><span class="member_intergaindateCase"><s:property value="#list.createDate"/>&nbsp;<s:property value="#list.createTime"/></span></td>
            <td>
                <s:if test="%{#list.manner==1}">
	                <span style="color:#FF0000"><s:property value="#list.point"/></span>
	            </s:if>
	            <s:else>  
	                <span style="color:#00B036"><s:property value="#list.point"/></span>
				</s:else>                   
            </td>
            <td>
	            <s:if test="%{#list.source.indexOf('元')>=0}">
	                <s:if test="%{#list.source.substring(0,#list.source.indexOf('元')).length()<5}">
						<s:bean name="org.apache.struts2.util.Counter" id="counter">  
							<s:param name="first" value="1" />  
							<s:param name="last" value="5-#list.source.substring(0,#list.source.indexOf('元')).length()" />  
							<s:iterator>&nbsp;</s:iterator>  
						</s:bean>  
					</s:if>
					<span class="mem_yhj_num"><s:property value="#list.source.substring(0,#list.source.indexOf('元'))"/></span>
	                <s:property value="#list.source.substring(#list.source.indexOf('元'),#list.source.length())"/>
	            </s:if>
	            <s:else>  
	                 <s:property value="#list.source"/>
				</s:else>
	        </td>
        </tr>
		</s:iterator>
    </tbody>               
</table>  
<div class="clear h20"></div>
<s:if test="#request.lastpage1 > 1">
<div class="plpage">
	<!--    翻页    -->
	<div  class="page_area">
		<div id="pagination">
			<ul>
				<s:if test="#request.page1==1">
					<li class="page_prev"><a href="#"><span class="default">上一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_prev"><a href="javascript:gotoPage('point!obtain.action','<s:property value="#request.page1-1"/>','<s:property value="#request.lastpage1"/>','member_commantable')"><span class="">上一页</span></a></li>
				</s:else>
				<s:iterator id="pageFootMap" status="st" value="#request.pageFootList1">
					<li class="<s:property value="#pageFootMap.class" />">
						<s:if test="#pageFootMap.index==\"...\"">
							<span><s:property value="#pageFootMap.index"/></span>
						</s:if>
						<s:else>
							<a href="javascript:gotoPage('point!obtain.action','<s:property value="#pageFootMap.index"/>','<s:property value="#request.lastpage1"/>','member_commantable')">
			        			<span><s:property value="#pageFootMap.index"/></span>
			        		</a>
						</s:else>
	        		</li>
	        	</s:iterator>
	        	<s:if test="#request.page1==#request.lastpage1 || #request.lastpage1 == 0">
					<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
				</s:if>
				<s:else>
					<li class="page_next"><a href="javascript:gotoPage('point!obtain.action','<s:property value="#request.page1+1"/>','<s:property value="#request.lastpage1"/>','member_commantable')"><span class="">下一页</span></a></li>
				</s:else>
			</ul>
		</div>
	</div>
</div>
<div class="clear h20"></div>
</s:if>