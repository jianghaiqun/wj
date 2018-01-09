<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%><!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<title><s:property value="title"/></title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css" />
<link rel="stylesheet" type="text/css" href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_sales.css" />
<style type="text/css">
.g-nav-main li a.nav_2{ color:#f08225; }
</style>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>

</head>
<body class="re-bg"> 
	<!-- 顶部 开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->

<!-- 导购轮播 -->
          <div class="weaper" >
           <div class="daohang">
				<span class="daohang_home"></span> 
				<a target="_self" href="<%=Config.getValue("FrontServerContextPath")%>"><span class="orange">您的现在位置：首页</span></a>
				<span class="separator">&gt;</span>
				<span class="separator1">保险快选</span>
			</div>
            <div class="g-guide"  >
            <!-- 当前第N页 -->
            <input type="hidden" value="0" id="g-guidenum">
            <!-- 百分比 -->
            <input type="hidden" value="25" id="g-guideb">
            <!-- 是否为最后一页 -->
            <input type="hidden" value="false" id="g-guidelase">
                    <div class="g-guide-tit f_mi alphatm" >
                        <div class="g-guide-h"></div>
                        <p><s:property value='descProp1'/></p>
                        <div class="g-guide-w"></div>
                    </div>

                    <div class="g-guide_to">
                      <a href="#g-tag" exturl="http://www.kaixinbao.com/DGSkip" vlpageid="DGSkip" onclick="javascript:void(0);return(VL_FileDL(this));return false;">
                         <b>跳过测评</b>
                         <p>已经确定计划<br>直接选择保障</p>
                         <div class="guide_1"></div>
                      </a>
                    </div>
                    <div class="g-guide-img">
                        <ul class="g-guide-uls">
                        	<s:iterator id="guide" value="result">
	                        	<li style="left:-1500px;">
		                        	<a href="<s:property value='#guide.nextLink'/>" target="<s:property value='#guide.target'/>"
		                        		exturl="http://www.kaixinbao.com/DGGuide" vlpageid="DGGuide" onclick="javascript:void(0);return(VL_FileDL(this));return false;">
		                        		<img src="<s:property value='#guide.Image'/>" alt="<s:property value='#guide.Name'/>">
		                        		<span class="g-guide-name"><s:property value='#guide.Name'/><em class="infes_12"></em></span>
		                        	</a>
	                        	</li>
                        	</s:iterator>
                        </ul>
                    </div>
                  <div class="g-guide-next">
                  <div class="gg-next guides_08"></div><span class="gg-next-g select"><em class="gg-next-b" style="width:0%" ></em></span>  <div class="gg-next  guides_08"></div>
                  </div>
            </div>
            <!-- data-filter 自定义属性 对应筛选项class值 值相同则属于同一个分组，未定义分组则默认归类到全部里 -->
            <div class="g-guide-tag" id="g-tag">
                <span class="g-guide-p f_mi">您已经确定好行程，只需要一份保障~看看大家最关心的保险都是什么吧</span>
	                <ul class="g-guideli cf">
	                  <li class="hover" data-filter="all"><span>全部</span></li>
	                  <s:iterator id="linkGroup" value="friendGroup">
	                  	<li data-filter="<s:property value='#linkGroup.Id'/>"><span><s:property value='#linkGroup.Name'/></span></li>
	                  </s:iterator>
	                </ul>
	                <div class="g-guide-box">
	                    <ul class="g-guide-ul">
	                    	<s:iterator id="link" value="friendLink">
	                    		<li class="<s:property value='#link.LinkGroupID'/>">
	                    			<a href="<s:property value='#link.URL'/>" target="_blank"
	                    				exturl="http://www.kaixinbao.com/DGTag" vlpageid="DGTag" onclick="javascript:void(0);return(VL_FileDL(this));return false;">
	                    				<s:property value='#link.Name'/><em class="guides_03"></em></a>
	                    		</li>
	                    	</s:iterator>
	                    </ul>
	                    <div class="clear"></div>
	                </div>
            </div>
          </div>

	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
	
	<!-- js加载 -->
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
	
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/redesign/re_base.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/header1.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/artDialog.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/login.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/redesign/re_guide.js"></script>

</body>
</html>