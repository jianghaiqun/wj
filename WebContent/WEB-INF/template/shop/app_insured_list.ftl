<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>我的投被保人</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/shop/css/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/style/commonality.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/MemberCenterSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<script type="text/javascript">

function zj(){
location.href="app_insured!add.action";
}
function jumpone() {
location.href="app_insured!scan.action?page=1";
}
function jumpbefore() {
     var pg=jQuery("#pg").val();
     if(pg==1){
        jQuery.tip("已为首页了");
        return false;}
        else{
        var bg=Number(pg) - Number(1);
       location.href="app_insured!scan.action?page="+bg;  
         }
   }
    function jumpnext() {
    
        var sd= jQuery("#sd").val();
        var ed=jQuery("#ed").val();
        var kw=jQuery("#kw").val();
        var pg=jQuery("#pg").val();
        var lg=jQuery("#lg").val();
        if(pg==lg){
        jQuery.tip("已为尾页了");
        return false;}
        else{
        var ng=Number(pg) + Number(1);
        location.href="app_insured!scan.action?page="+ng;  
         }
   }
   function jumplast() {
   
        var sd= jQuery("#sd").val();
        var ed=jQuery("#ed").val();
        var kw=jQuery("#kw").val();
        var pg=jQuery("#pg").val();
        var lg=jQuery("#lg").val();
       location.href="app_insured!scan.action?page="+lg;  
         
   }
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">


<div class="MemberCenterMiddArea">
	<div class="MemberCenterNavArea">
    	<ul>
        	<li class="IcoHome"><a href="/index.html">首页</a></li>
            <li class="IcoHome"><a href="member_center!index.action">会员中心</a></li>
           <li>我的投被保人</li>
 </ul>
    	<div class="clear"></div>
    </div>
        <div class="MemberMenuArea mr10">
    	<div class="ActiveArea" style="top:256px;">
        	<a href="app_insured!scan.action" class="IcoUser_7">我的投被保人</a>
            
        </div>
<ul class="mcmenulist">
           <li><a href="profile!edit.action" class="IcoUser_1">个人资料</a></li>
            <li><a href="order_query!list.action" class="IcoUser_2">我的订单</a></li>
            <li><a href="password!edit.action" class="IcoUser_1" >修改密码</a></li>
            <li><a href="point!scan.action" class="IcoUser_6">积分兑换记录</a></li>
            <li><a href="point!obtain.action" class="IcoUser_6">积分获取记录</a></li>
            <li><a href="app_insured!scan.action" class="IcoUser_7">我的投被保人</a></li>
            <li><a href="coment!mycomment.action" class="IcoUser_8">我的评论</a></li>
            <li><a href="question!ask.action" class="IcoUser_9">我的提问</a></li>
            <li><a href="answer!reply.action" class="IcoUser_10">我的回答</a></li>
            <li><a href="stow!scan.action" class="IcoUser_12">我的收藏</a></li>
            <li><a href="history!scan.action" class="IcoUser_13">我的浏览记录</a></li>
            <li><a href="my_compare!show.action" class="IcoUser_14">我的比价记录</a></li>
        </ul> 
    </div>
		
		
		
		
		
 <div class="right01">
		<div class="wrapnormal">
			<h3 class="tit01">我的投被保人</h3>
			<input type="button" value="增 加" onclick="return zj();" class="btnorange"/>
			<div class="tablewrap01">
				<table width="100%" class="table01">
					<tbody>
  					<tr>
    					<th width="30%" align="left">投保人姓名</th>
    					<th width="30%" align="left">被保人姓名</th>
    					<th width="21%" align="left">创建日期</th>
    					<th width="14%" align="left">&nbsp;</th>
    					<input type="hidden" value="${page}" name="pc" id="pg">
					    <input type="hidden" value="${lastpage}" name="lpg" id="lg">
  					</tr>
		
		<#list listq as list>
		<tr class="trbg01" onmouseover="this.style.backgroundColor='#FDFDCE'" onmouseout="this.style.backgroundColor='#ffffff'">   
						<td>${list.applicantName}</td>
    					<td>${list.recognizeeName}</td>
    					<td>${list.createDate}</td>
   						<td><span class="blue"><img src="${shopStaticPath}/template/shop/images/mcico06.gif"/><a href="app_insured!update.action?appInsuredId=${list.id}">编辑</a></span><span class="blue pad1"><img src="${shopStaticPath}/template/shop/images/mcico02.gif"/><a href="app_insured!delete.action?appInsuredId=${list.id}">删除</a></span></td>
  					</tr>
		</#list>
		</tbody>
		</table>
		
		<div class="PageArea">
            <ul>
                <li>共${count}条记录!</li>	
                <li><a href="#" onclick="return jumpone();">首页</a></li>
                <li><a href="#" onclick="return jumpbefore();">&lt;上一页</a></li>
                
                <li><a href="#" onclick="return jumpnext();">下一页&gt;</a></li>
                <li><a href="#" onclick="return jumplast();">尾页</a></li>
            </ul>
            <div class="clear"></div>
                 </div>
						
<div class="clear"></div>
        </div>
		</div>
	<div class="clear"></div>
	</div>
	<div class="clear"></div>
</div>
<div class="clear"></div>
						
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<#include "/wwwroot/kxb/block/community_v1.shtml" />
</div>							
						
	
</body>
</html>