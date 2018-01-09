<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>个人信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<style>
   .title {background:#fff;float:left;height:800px;padding-left:140px;padding-top:9px;width:60px;}
   .title li{height:60px;}
   .title1 {background:#fff;float:left;height:800px;padding-left:140px;padding-top:9px;width:100px;}
   .title1 li{height:60px;}
</style>

<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/member_center.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="memberCenter">
<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body memberCenterIndex">
		<div class="bodyLeft">
			<div class="memberInfo">
				<div class="top"></div>
				<div class="middle">
					<p>欢迎您！<span class="username">${loginMember.username}</span> [<a class="userLogout" href="member!logout.action"">退出</a>]</p>
					<p>会员等级:<span class="red"> ${loginMember.memberRank.name}</span></p>
				</div>
				<div class="bottom"></div>
			</div>
			<div class="blank"></div>
			<div class="memberMenu">
				<div class="top">
					<a href="member_center!index.action">会员中心首页</a>
				</div>
				<div class="middle">
					<ul>
					<li class="profile">
	                    	<ul>
	                        	<li><a href="profile!edit.action">个人信息</a></li>
	                            <li><a href="password!edit.action">修改密码</a></li>
	                           <!-- <li><a href="receiver!list.action">收货地址</a></li>-->
	                        </ul>
	                    </li>
	                     <li class="order">
	                    	<ul>
	                        	<li class="current"><a href="coment!mycomment.action">我的评论</a></li>
	                       	    <li class="current"><a href="question!ask.action">我的提问</a></li>
	                            <li class="current"><a href="answer!reply.action">我的回答</a></li>
	                        </ul>
	                    </li>
	                	<li class="order">
	                    	<ul>
	                        	<li><a href="order!list.action">我的订单</a></li>
	                        </ul>
	                    	<ul>
	                        	<li><a href="order!list.action">我的保单</a></li>
	                        </ul>
	                        <ul>
	                        	<li><a href="my_trial!list.action">我的试算</a></li>
	                        	<li><a href="my_compare!show.action">我的对比</a></li>
	                        </ul>
	                    </li>
	                    <li class="category favorite">
	                    	<ul>
	                        	<li><a href="favorite!list.action">产品收藏</a></li>
	                        </ul>
	                    </li>
	                  	<li class="message">
	                    	<ul>
	                    	    <li><a href="message!send.action">发送消息</a></li>
	                            <li><a href="message!inbox.action">收件箱</a></li>
	                            <li><a href="message!draftbox.action">草稿箱</a></li>
	                            <li><a href="message!outbox.action">发件箱</a></li>
	                        </ul>
	                    </li>
	                    <li class="profile">
	                    	<ul>
	                        	<li><a href="profile!edit.action">个人信息</a></li>
	                            <li><a href="password!edit.action">修改密码</a></li>
	                            <li><a href="receiver!list.action">收货地址</a></li>
	                        </ul>
	                    </li>
	                </ul>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="bodyRight">
			<div class="memberCenterDetail">
				<div class="top">
					我的对比
				</div>
				<div class="middle">
					<div class="title">
	                   <ul>
	                     <li>
	                                                             公司
	                     </li>
	                     <li>
	                                                              产品名称
	                     </li>
	                     <li>
	                                                             产品价格
	                     </li>
	                     <#list fcps as f>
	                          <li>
	                             ${f.propertyTypeName}
	                          </li>
	                     </#list>
	                  </ul>
	               </div>
	               <#assign index = 0 />
	               <#list compareInformation as ci>
	                  <div class="title1">
	                     <ul>
	                        <li>
	                           <image src="${ci.get("companyLog")}" height="30"/>
	                        </li>
	                        <li>
	                            ${ci.get("RiskName")}
	                        </li>
	                         <li>
	                             ${ci.get("InitPrem")}
	                         </li>
	                        <#list fcps as f>
	                           <li>
	                              <span id="${f.propertyType}${ci.get("riskCode")}">${ci.get("${f.propertyType}")}</span>
	                           </li>
	                        </#list>
	                    </ul>
	                  </div>
	                <#assign index=index+1 />
	              </#list>
					<div class="blank"></div>
				</div>
				<div class="bottom"></div>
			</div>
		</div>
		<div class="blank"></div>
		<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
</body>
</html>