<%@ page language="java" import="java.util.*,com.sinosoft.framework.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML>
<html>
<head>
	<title></title>
</head>
<body >
		<div class="JfShopList"> 
		<s:if test="#request.list_data.size()!=0">
				<s:iterator id="dataList_Gift" value="#request.list_data" status="status">
			<s:if test="#request.page_Index==\"0\"||#request.page_Index==\"1\"">
				<s:if test="#status.index==0">
					<div class="Shop_listj">
						<a href="<s:property value="#dataList_Gift.linkUrl"/>" target="_blank">
							<img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#dataList_Gift.logoUrl"/>" width="272px" height="215px" alt="<s:property value="#dataList_Gift.giftTitle"/>"> 
								<span class="shop_listj_t"><s:property value="#dataList_Gift.giftTitle"/></span> 
						</a>
						<div class="shop_listmed">
		                    <a target="_blank" href="<s:property value="#dataList_Gift.linkUrl"/>" class="go_dhsc rborder4"><s:property value="#dataList_Gift.points"/> 积分</a>
		                    <span class="shop_listnums">仅剩<s:property value="#dataList_Gift.lastNum"/>个</span>
		                </div> 
					</div>
				</s:if>
				<s:else>
						<div class="shop_listf"> 
						<a href="<s:property value="#dataList_Gift.linkUrl"/>" target="_blank"><img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#dataList_Gift.logoUrl"/>" width="136px" height="109px" alt="<s:property value="#dataList_Gift.giftTitle"/>"></a>
							<dl> 
								<dt> 
								<a href="<s:property value="#dataList_Gift.linkUrl"/>" target="_blank"><s:property value="#dataList_Gift.giftTitle"/></a>
								</dt>
								<dd> 
				                  <span class="jf_tip_icon">剩余<s:property value="#dataList_Gift.lastNum"/>个</span> 
				                </dd>
				                <dd> 
				                  <a class="go_dhsc rborder4" href="<s:property value="#dataList_Gift.linkUrl"/>" target="_blank"><s:property value="#dataList_Gift.points"/> 积分</a>
				                </dd>   
							</dl> 
						</div> 
				</s:else>
			</s:if>
			<s:else>
				<div class="shop_listf"> 
					<a href="<s:property value="#dataList_Gift.linkUrl"/>" target="_blank"><img src="<%=Config.getValue("StaticResourcePath")%>/<s:property value="#dataList_Gift.logoUrl"/>" width="136px" height="109px" alt="<s:property value="#dataList_Gift.giftTitle"/>"></a>
						<dl> 
							<dt> 
							<a href="<s:property value="#dataList_Gift.linkUrl"/>" target="_blank"><s:property value="#dataList_Gift.giftTitle"/></a>
							</dt>
							<dd> 
				              <span class="jf_tip_icon">剩余<s:property value="#dataList_Gift.lastNum"/>个</span> 
				            </dd>
				            <dd> 
				              <a class="go_dhsc rborder4" href="<s:property value="#dataList_Gift.linkUrl"/>" target="_blank"><s:property value="#dataList_Gift.points"/> 积分</a>
				            </dd>
						</dl> 
					</div> 
			</s:else>
			</s:iterator>
		</s:if>
		<s:else>
			<p class="no_shop_hot">暂时没有设置相关产品</p>
		</s:else>
		</div>
		<s:if test="#request.list_data.size()!=0">
			<s:include value="points_foot.jsp"/>
		</s:if>
</body>
</html>
