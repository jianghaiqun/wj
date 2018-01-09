<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员中心-积分兑换记录</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath") %>/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=Config.getValue("JsResourcePath") %>/js/template/common/js/base.js"></script>
<style type="text/css">
#member_commantable1 table,#member_commantable1 table td,#member_commantable1 table th
	{
	border: 1px solid #cccccc;
	border-collapse: collapse;
}
</style>
<script type="text/javascript">

function jumpone() {
	 	var pg = document.getElementById("pg").value;
	 	if(pg==1){
	 			jQuery.tip("已为首页！");
	 			return false;
		}else{
	        location.href="point!scan.action?page=1";
		}

}
function jumpbefore() {
        var pg = document.getElementById("pg").value;
        if(pg==1){
        	 jQuery.tip("已为首页！");
 		     return false;
        }else{    
        	 var bg=Number(pg) - Number(1);
     	     location.href="point!scan.action?page="+bg;  
        }
}

function jumpnext() {
        var pg = document.getElementById("pg").value;
        var lg = document.getElementById("lg").value;
        
        if(lg==pg || lg==0){
        	jQuery.tip("已为尾页！");
        	return false;
        }else{
        	var ng=Number(pg) + Number(1);
        	location.href="point!scan.action?page="+ng;  
	    }
}
function jumplast() {
    	var pg = document.getElementById("pg").value;
        var lg = document.getElementById("lg").value;
        if(pg==lg || lg==0){
	    	jQuery.tip("已为尾页！");
       	 return false;
        }else{    
         location.href="point!scan.action?page="+lg;  
        }
         
}
</script>
</head>
<body>
<input id="pg" type="hidden" value="<s:property value="page"/>" />
<input id="lg" type="hidden" value="<s:property value="lastpage"/>" />
<div id="member_commantable" style="width: 747px; padding-bottom: 20px;">
<table cellspacing="0" cellpadding="0" border="0" align="center"
	class="member_nearorderTable">
	<tbody>
		<tr>
			<td><span class="member_favoriteproduct">礼品名称</span></td>
			<td><span class="member_favoritecompany member_code">卡号</span></td>
			<td><span class="member_ordertime">密码</span></td>
			<td><span class="member_ordertime">有效期</span></td>
			<td><span class="member_ordertime">兑换时间</span></td>
		</tr>
		<s:iterator id="list" value="#request.listJFDH">
			<tr>
				<td><span class="member_fproductCase "><s:property
					value="#list.name" /></span></td>
				<td><span class="member_fcompanyCase "><s:property
					value="#list.cardNo" /></span></td>
				<td><span class="member_producttypeCase"><s:property
					value="#list.password" /></span></td>
				<td><span class="member_ordertimeCase member_heights"><s:property
					value="#list.expireDate" /></span></td>
				<td><span class="member_orderoperateCase"><s:property
					value="#list.CreateDate" /></span></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
</div>

<div class="member_pagearea"><span class="member_pagebutton"><a
	href="###" onclick="return jumpone();">首页</a></span> <span
	class="member_pagebutton"><a href="###"
	onclick="return jumpbefore();">上一页</a></span> <span class="member_pagebutton"><a
	href="###" onclick="return jumpnext();">下一页</a></span> <span
	class="member_pagebutton"><a href="###"
	onclick="return jumplast();">尾页</a></span></div>
</body>
</html>