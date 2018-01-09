<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-修改密码</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript">

function showtip1(){
//jQuery("#xmm").html("密码只能由6-16个英文、数字、及下划线任两个组合组成");
jQuery("#xmm").html("密码须在6-16位之间！");
}
function wosi1(){
var pw = jQuery("#password").val();

if(pw==""||"undefined"==typeof(pw)||pw==null){
	jQuery("#xmm").html("密码不能为空！");
	jQuery("#password").attr("value","");
    return false;
	}
if(pw.length < 6 || pw.length > 16){
	jQuery("#xmm").html("密码应在6-16位之间！");
	jQuery("#password").attr("value","");
    return false;
	}
jQuery("#xmm").html("");
}

function wosi2(){
var pw = jQuery("#password").val();
var rpw = jQuery("#repassword").val();
if(rpw==""||"undefined"==typeof(rpw)||rpw==null){
	jQuery("#qrmm").html("重复密码不能为空！");
	return false;
}
if(pw!=rpw){
jQuery("#qrmm").html("两次密码输入不一致！");
return false;
}
jQuery("#qrmm").html("");

}
function wosi(){
var opw= jQuery("#oldPassword").val();
if(opw==""||"undefined"==typeof(opw)||opw==null){
	jQuery("#ysmm").html("原始密码不能为空！");
	return false;
}
}



function vd(){
var opw= jQuery("#oldPassword").val();
var pw = jQuery("#password").val();
var rpw = jQuery("#repassword").val();
if(opw==""||"undefined"==typeof(opw)){
	jQuery("#ysmm").html("原始密码不能为空！");
	return false;
}
jQuery("#ysmm").html("");

if(pw==""||"undefined"==typeof(pw)||pw==null){
	jQuery("#xmm").html("密码不能为空！");
	jQuery("#password").attr("value","");
    jQuery("#password").focus();
	return false;
	}
	
	if(pw.length < 6 || pw.length > 16){
	jQuery("#xmm").html("密码须在6-16位之间！");
	jQuery("#password").attr("value","");
    return false;
	}
	
	jQuery("#xmm").html("");
if(rpw==""||"undefined"==typeof(rpw)){
	jQuery("#qrmm").html("重复密码不能为空！");
	return false;
}
if(pw!=rpw){
jQuery("#qrmm").html("两次密码输入不一致！");
return false;
}
else{
jQuery("#qrmm").html("");
jQuery("#inputForm").submit();
}
}
</script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>

<body class="up-bg-qh">
<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
<!--导航结束-->
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span>
			<a href='<%=Config.getFrontServerContextPath() %>' target='_self' ><span class="orange">您现在的位置：首页</span></a><span class="separator">></span><a href='member_center!index.action' target='_self'>会员中心</a><span class="separator">></span><span class="separator1">修改密码</span>
		</div>
		<div class="member_con">
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right mbr_bg">
			 	<div class="member_boxs">
	           		<!-- 修改密码开始-->
	           		<div class="member-htit">
			          <span class="member-titsc">修改密码</span>
			        </div> 
			 	  	<form id="inputForm" class="validate" action="password!update.action" method="post" name="fm">
	                <div class="member_changearea">
	                	<span class="member_passchangeNote">原密码:</span>
	             	    <input type="password" name="oldPassword" id="oldPassword" onblur="wosi();" value="" class="member_passinput" onmousemove="this.className='member_passinput_move'" onmouseout="this.className='member_passinput'"  /> 
	                    <span  class="member_passcheckNull" style="width:150px;" >*&nbsp;&nbsp;&nbsp;&nbsp;<span id="ysmm"></span> </span>                  
	                    <br/>
	                     <span class="member_passchangeNote">新密码:</span>                   
	             	    <input type="password" id="password" name="member.password" value="" class="member_passinput"  onmousemove="this.className='member_passinput_move'" onmouseout="this.className='member_passinput'" onblur="wosi1();" />
	                    <span  class="member_passcheckNull" style="width:150px;">*&nbsp;&nbsp;&nbsp;&nbsp;<span id="xmm"></span></span>
	                      <br/>
	                     <span class="member_passchangeNote">确认新密码:</span>                   
	             	    <input type="password" id="repassword" onblur="wosi2();"  name="" value="" class="member_passinput"  onmousemove="this.className='member_passinput_move'" onmouseout="this.className='member_passinput'" />    
	                    <span  class="member_passcheckNull" style="width:150px;">*&nbsp;&nbsp;&nbsp;&nbsp;<span id="qrmm"></span></span>
	                    <br/>                   
	                    <span><button class="member_passsummit" style="margin-left:10px;" onclick="return vd();">提交</button></span>
	                </div>
	                </form>           
	                <!-- 修改密码结束-->
	                 
			 	</div>
			</div>
		</div>
	</div>
<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("StaticResourcePath")%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/new_member.js"></script>
</body>
</html>