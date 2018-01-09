<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-会员咨询</title>
<!--redesign/re_header.css头部脚部全站样式-->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></scrip
<script type="text/javascript">
function jumpone() {
	var pg = $("#pg").val();
	if (pg == 1) {
		jQuery.tip("已为首页！");
		return false;
	} else {
		location.href = "my_consult!show.action?page=1";
	}
}
function jumpbefore() {
	var pg = $("#pg").val();
	if (pg == 1) {
		jQuery.tip("已为首页！");
		return false;
	} else {
		var bg = Number(pg) - Number(1);
		location.href = "my_consult!show.action?page=" + bg;
	}
}
function jumpnext() {
	var pg = $("#pg").val();
	var lg = $("#lg").val();

	if (pg == lg || lg == 0) {
		jQuery.tip("已为尾页！");
		return false;
	} else {
		var ng = Number(pg) + Number(1);
		location.href = "my_consult!show.action?page=" + ng;
	}
}
function jumplast() {
	var pg = $("#pg").val();
	var lg = $("#lg").val();
	if (pg == lg || lg == 0) {
		jQuery.tip("已为尾页！");
		return false;
	} else {
		location.href = "my_consult!show.action?page=" + lg;
	}

}
jQuery(document).ready(function(){
	//页码初始化
	var page = jQuery("#pg").val();
	var lastpage = jQuery("#lg").val();
	var selObj = jQuery("#pageselect");
	for(var i=1;i<parseInt(lastpage)+1;i++){
		var value=i;  
		var text=i;
		selObj.append("<option value='"+value+"'>"+text+"</option>");  
	}
	jQuery("#pageselect").attr("value",page); 
});
/*选择页码*/
function choosepage() {
	var value=jQuery("#pageselect").val();
	location.href = "my_consult!show.action?page=" + value; 
}
</script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body>
		<!-- 顶部 开始 1-->
		<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
		<!-- 顶部 结束 -->
<div class="wrapper">
<div class="daohang">
			<span class="daohang_home"></span>
			<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a><span class="separator">></span><a href='member_center!index.action' target='_self'>会员中心</a><span class="separator">></span><span class="separator1">我的咨询</span>
		</div>
		<div class="member_con">
		 <s:include value="/wwwroot/kxb/block/kxb_member_center_left.shtml"></s:include>
		 <input type="hidden" id="fullDegree" value="<%=session.getAttribute("fullDegree") %>">
		 <div class="member_right bor_sild">
		 <input type="hidden" value="<s:property value="page"/>" name="pc" id="pg" />
		  <input type="hidden" value="<s:property value="lastpage"/>" name="lpg" id="lg" />
		 	<div class="member_boxs">
           			 <!-- 咨询开始-->
		 	  	<h3 class="member_t">我的咨询</h3>   
            		<div class="member_evaluate">
            				<table width="100%" border="1">
                          <tbody><tr>
                            <th width="45%" scope="col">商品信息</th>
                            <th width="55%" scope="col">咨询内容</th>
                          </tr>
                          <s:iterator id="list" value="#request.cousultComment">
                          <tr>
                          	<td>
                          			<div class="member_img_bxo member_consult_img cf">
                                <a href="<%=Config.getFrontServerContextPath() %>/<s:property value="#list.URL" />"  target='_blank'><img width="68px;" height="68px;" class="member_cp_img" alt="" src="<%=Config.getValue("ProductResourcePath") %>/<s:property value="#list.Logo" />"></a>
                               <a class="member_links" target='_blank' href="<%=Config.getFrontServerContextPath() %>/<s:property value="#list.URL" />"><s:property value="#list.Title" /></a>
                                </div>
                          	</td>
                          	<td>
                          		<dl class="member_q clearfix">
                          			<dt class="member_q_t">咨询：</dt>
                          			<dd class="member_q_con">
                          				<s:if test="#list.Prop2.length() > 54">
                          					<s:property value="#list.Prop2.substring(0,54)" />
                          					...
                          				</s:if>
                          				<s:else>
                          					<s:property value="#list.Prop2" />
                          				</s:else>
                          			</dd>
                          		</dl>
                          		<s:if test="ReplyContent != '' && ReplyContent != null ">
                          		<dl class="member_a clearfix" >
                          			<dt class="member_a_t">回复：</dt>
                          			<dd class="member_a_con"><s:property value="#list.ReplyContent" /></dd>
                          		</dl>
                          		</s:if>
                          	</td>

                          </tr>
                          </s:iterator>
                      	  </tbody>
                  		</table>


            		</div>
            			<div class="member_pagearea">
            			<span class="member_page_dis">共<s:property value="lastpage"/>页，<s:property value="count"/>条记录</span>
                    	<span class="member_pagebutton"><a href="#" onclick="return jumpone();">首页</a></span>
                        <span class="member_pagebutton"><a href="#" onclick="return jumpbefore();">上一页</a></span>
                        <span class="member_go_sel">
            				<select name="pageselect" id="pageselect" class="member_go_sels" onchange="choosepage()">
           	 				</select>
        				</span>
                        <span class="member_pagebutton"><a href="#" onclick="return jumpnext();">下一页</a></span>
                        <span class="member_pagebutton"><a href="#" onclick="return jumplast();">尾页</a></span>
                       
                    </div>
                    <!-- 咨询结束-->
                 
		 	</div>
		 </div>
		</div>
</div>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
</body>
</html>
