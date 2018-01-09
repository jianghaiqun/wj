<!DOCTYPE>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>开心保_会员中心_个人资料</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />


<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link href="${shopStaticPath}/template/shop/css/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/MemberCenterSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<script type="text/javascript" >
function toadd(){
fwm.action = "member!profect.action";
fwm.submit();
}
function goback(){
fwm.action = "member!goBack.action";
fwm.submit();
}
</script>





<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

<div class="MemberCenterMiddArea">
	<div class="MemberCenterNavArea">
    	<ul>
        	<li class="IcoHome"><a href="${base}/wwwroot/kxb/">首页</a></li>
            <li class="IcoHome"><a href="member_center!index.action">会员中心</a></li>
           <li class="IcoHome"><a href="#">个人资料</a></li>
           <li>修改个人资料</li>
 </ul>
    	<div class="clear"></div>
    </div>
        <div class="MemberMenuArea mr10">
    	<div class="ActiveArea" style="top:4px;">
        	<a href="profile!edit.action" class="IcoUser_1">个人资料</a>
            
        </div>

            <ul class="mcmenulist">
            <li><a href="profile!edit.action" class="IcoUser_1">个人资料</a></li>
            <li><a href="order_query!list.action" class="IcoUser_2">我的订单</a></li> 
            <li><a href="password!edit.action" class="IcoUser_1" >修改密码</a></li>
            <li><a href="point!scan.action" class="IcoUser_6">积分兑换记录</a></li>
            <li><a href="point!obtain.action" class="IcoUser_6">积分获取记录</a></li>
            <li><a href="coment!mycomment.action" class="IcoUser_8">我的评论</a></li>
            <li><a href="question!ask.action" class="IcoUser_9">我的提问</a></li>
            <li><a href="answer!reply.action" class="IcoUser_10">我的回答</a></li>
            <li><a href="stow!scan.action" class="IcoUser_12">我的收藏</a></li>
            <li><a href="my_compare!show.action" class="IcoUser_14">我的比价记录</a></li>
       
        </ul>
    </div>
    
    
    
     <div class="right01">
		<div class="wrapnormal">
			<h3 class="tit01">个人信息预览</h3>
  			<div class="sheetrig2">
    <ul>
					<li><label>资料完整度：</label>${(member.fullDegree)!}</li>
					<li><label>开心值：</label>${(member.expiricalValue)!}</li>
					<li><label>email：</label>${(member.email)!}</li>
					<li><label>手机号码：</label>${(member.mobileNO)!}</li>
					<li><label>QQ号码：</label>${(member.QQNO)!}</li>
					<li><label>真实姓名：</label>${(member.realName)!}</li>
					<li><label>证件类型：</label><#list listid as list>
					<#if (list.dictSerial==member.IDType)>${list.dictName}</#if>
					</#list></li>
					<li><label>证件号码：</label>${(member.IDNO)!}</li>
					<li><label>性别：</label> <#list listSex as list>
					<#if (list.dictSerial==member.sex)>${list.dictName}</#if>
					</#list></li>
					<li><label>生日：</label> ${(brithday)!}</li>
					<li><label>所在地：</label>  <#list listLocation?reverse as list>
				    ${list.dictName}
				    </#list></li>
					<li><label>联系地址：</label>${(member.address)!}</li>
					<li><label>邮政编码：</label>${(member.zipcode)!}</li>
					<li><label>个人网址：</label> ${(member.personalURL)!}</li>
					<li><label>电话：</label>${(member.telephoneNO)!}</li>
					<li><label>传真：</label> ${(member.faxNO)!}</li>
					<li><label>婚姻状况：</label> <#list listms as list>
					          <#if (list.dictSerial==member.marriageStatus)!>${list.dictName}</#if>
					        </#list></li>
					<li><label>会员类型：</label>
			   		<#if (member.VIPType=="Person")!>个人 </#if>
			    	<#if (member.VIPType=="Company")!>团队</#if>
		            </li>        
					<li><label>行业类别：</label>${(member.industryType)!}</li>
					<li><label>职业：</label>${(member.position)!}</li>
					<li><label>兴趣爱好：</label><#list listHobby as le ><#if !le_has_next>${le.dictName}<#else>${le.dictName},</#if></#list></li>
				<form method="post" name="fwm">				
		  <input type="hidden" name="email"  value="${(member.email)!}" />
	      <input type="hidden" name="mobileNO"  value="${(member.mobileNO)!}"/>
	      <input type="hidden" name="qqno" value="${(member.QQNO)!}"  />
	      <input type="hidden" name="realName" value="${(member.realName)!}" />
	      <input type="hidden" name="member.IDType" value="${(member.IDType)!}" />
	      <input type="hidden" name="member.IDNO" value="${(member.IDNO)!}" />
	      <input type="hidden" name="member.sex" value="${(member.sex)!}" />
	      <input type="hidden" name="brithday" value="${(brithday)!}"/>   
	      <input type="hidden" name="member.location" value="${(member.location)!}" />   
	      <input type="hidden" name="member.address" value="${(member.address)!}" />   
	      <input type="hidden" name="member.zipcode" value="${(member.zipcode)!}" />         
	      <input type="hidden" name="member.personalURL" value="${(member.personalURL)!}" />         
	      <input type="hidden" name="member.telephoneNO" value="${(member.telephoneNO)!}" />          
	      <input type="hidden" name="member.faxNO" value="${(member.faxNO)!}" />   
	      <input type="hidden" name="member.marriageStatus" value="${(member.marriageStatus)!}" />   
	      <input type="hidden" name="member.industryType" value="${(member.industryType)!}"/>   
	      <input type="hidden" name="member.position" value="${(member.position)!}" />   
	      <input type="hidden" name="member.hobby" value="${(member.hobby)!}" />   
	      <input type="hidden" name="member.id" value="${member.id}"/>
	      <input type="hidden" name="EmailBinding" value="${member.isEmailBinding}"/>
	      <input type="hidden" name="MobileNOBinding" value="${member.isMobileNOBinding}"/>
	      <input type="hidden" name="member.VIPType" value="${(member.VIPType)!}"/>
	      <input type="hidden" name="member.fullDegree" value="${(member.fullDegree)!}"/>
	      <input type="hidden" name="member.expiricalValue" value="${(member.expiricalValue)!}"/>
	      <#list listHobby as list>
	      <div Style="display:none;">
		  <input type="checkbox" name="love" value="${list.dictSerial}" checked/> 
		  </div>
		  </#list>
	      <label></label><input type="button" onclick="goback();" class="btnorange" value="返回"/>
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      <input type="button" onclick="toadd();" class="btnorange" value="保存"/></form>          
				</ul>
 </div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="clear"></div>
</div>
<div class="clear"></div>

 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
 <#include "/wwwroot/kxb/block/community_v1.shtml" />
</div>        
</body>
</html>