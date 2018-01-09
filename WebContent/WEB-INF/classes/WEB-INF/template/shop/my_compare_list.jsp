<!DOCTYPE html> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>  
<html> 
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-我的比价记录</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css"/>
<script type="text/javascript">
    function jumppage(pageindex) {
    	location.href="my_compare!show.action?page="+pageindex; 
    }
</script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
	<!-- 顶部 开始 1-->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->

	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span>
			<a href="<%=Config.getFrontServerContextPath() %>" target='_self' ><span class="orange">您现在的位置：首页</span></a><span class="separator">>
			</span><a href='member_center!index.action' target='_self'>会员中心</a>>
			<span class="separator"></span><span class="separator1">对比记录</span>
		</div>
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
		 	<div class="member_right bor_sild">
		 		<div class="member_boxs">
           			<!-- 我的比价开始-->
		 	  		<div class="member-htit"><span class="member-titsc">对比记录</span></div>
		 	  		<div class="memeber_comparelist">
                    	<h3 class="member_t_up"><ul class="member_t_tag" >
                            <li  class="tag_hsf hover">非车险比价</li>
                    	</ul></h3>
                  	</div>
                    <div class="member_orderlist" id="member_commantable">
                        <table align="center" border="0" cellspacing="0" cellpadding="0" class="member_nearorderTable" > 
                            <tbody>
                            	<tr>
                                    <th width="234px">产品名称</th>
                                    <th width="140px">保险公司</th>
                                    <th width="86px">保费</th>
                                    <th width="100px">产品类型</th>
                                    <th width="100px">比价时间</th>
                                    <th width="60px">操作</th>
                                </tr>
                                <s:iterator id="list" value="#request.pager.list">
                                <tr>
                                   <td>
	                                   <span class="member_comprenameCase">
	                                   <s:iterator id="tp" value="#list.trailProducts">
	                                   	   <div title="<s:property value="#tp.productName" />"><s:property value="#tp.productName" /></div>
	                                   </s:iterator>
	                                   </span>
                                   </td>
                                   <td>
	                                   <span class="member_comparecompanyCase">
	                                       <p><span class="member_fcompanyCase">
	                                       <s:iterator id="tp" value="#list.trailProducts">
	                                       <s:property value="#tp.comName" />
	                                       </s:iterator>
	                                       </span></p>
	                                    </span>
                                    </td>
                                    <td>
	                                    <span class="member_orderchargeCase f_mi">
	                                    <s:iterator id="tp" value="#list.trailProducts">
	                                    	<p><s:property value="#tp.initPrem" /></p>
	                                    </s:iterator>
	                                    </span>
                                    </td>
                                    <td><span class="member_producttypeCase">
	                                    <s:if test="#list.productType==\"A\"">旅游保险</s:if>
										<s:elseif test="#list.productType==\"B\"">意外保险</s:elseif>
										<s:elseif test="#list.productType==\"C\"">家财保险</s:elseif>
										<s:elseif test="#list.productType==\"D\"">健康保险</s:elseif>
										<s:elseif test="#list.productType==\"E\"">人寿保险</s:elseif>
										<s:elseif test="#list.productType==\"F\"">车辆保险</s:elseif>
										<s:else>不详</s:else>
                                    </span></td>
                                    <td><span class="member_ordertimeCase"><s:date name="#list.createDate" format="yyyy-MM-dd" /></span></td>
                                    <td><span class="member_productsdetailCase"> <a href="<%=serverContext%>/shop/non_auto!detail.action?eriskType=<s:property value="productType" />&serialNumber=<s:property value="serialNumber" />" target="_blank">详细</a> </span></td>
                                </tr>
                                </s:iterator>
                            </tbody>               
                        </table>  
                    </div>
                    <div class="clear h20"></div>
                    <s:if test="#request.pager.pageCount > 1">
                    <div class="plpage">
						<!--    翻页    -->
						<div  class="page_area">
							<div id="pagination">
								<ul>
									<s:if test="#request.pager.pageNumber==1">
										<li class="page_prev"><a href="#"><span class="default">上一页</span></a></li>
									</s:if>
									<s:else>
										<li class="page_prev"><a href="javascript:jumppage('<s:property value="#request.pager.pageNumber-1"/>')"><span class="">上一页</span></a></li>
									</s:else>
									<s:iterator id="pageFootMap" status="st" value="#request.pageFootList">
										<li class="<s:property value="#pageFootMap.class" />">
											<s:if test="#pageFootMap.index==\"...\"">
												<span><s:property value="#pageFootMap.index"/></span>
											</s:if>
											<s:else>
												<a href="javascript:jumppage('<s:property value="#pageFootMap.index"/>')">
											        <span><s:property value="#pageFootMap.index"/></span>
											    </a>
											</s:else>
									    </li>
						        	</s:iterator>
						        	<s:if test="#request.pager.pageNumber==#request.pager.pageCount || #request.pager.pageCount == 0">
										<li class="page_next"><a href="#"><span class="default">下一页</span></a></li>
									</s:if>
									<s:else>
										<li class="page_next"><a href="javascript:jumppage('<s:property value="#request.pager.pageNumber+1"/>')"><span class="">下一页</span></a></li>
									</s:else>
								</ul>
							</div>
						</div>
					</div>
					<div class="clear h20"></div>
					</s:if>
		 		</div>
		 	</div>
		</div>
	</div>
 	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
  	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
    <script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
</body>
</html>