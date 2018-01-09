<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>我的试算 </title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
</head>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/member_center.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript">
   function detail(id){
       location.href="${base}/shop/my_trial!detail.action?serialNumber="+id;
   }
   function selectShowInsurance(){
       document.getElementById("selectForm").submit();
       return true;
   }
</script>
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
					我的试算
				</div>
				<div class="middle">
					<div class="blank"></div>
					<form id="selectForm" action="${base}/shop/my_trial!selectShowInsurance.action"  method="post">
             <table class="listTable">
                <tr>
                   <th>车辆行驶区域：</th>
                   <td>
                     <select id="regionCode" name="sdTagetInformation.regionCode" >
					   <option value="-1">---请选择---</option>
					   <#if (citys != null && citys?size > 0)>
							<#list citys as list>
					            <option value="${list.placeCode}"
					               <#if (list.placeCode==sdTagetInformation.regionCode)!>
					                   selected
					            </#if>>${list.placeName}</option>
					        </#list>
					   </#if>
					 </select>
                   </td>
                   <th>车辆品牌型号</th>
                   <td>
                      <input type="text" id="carBrand" name="sdTagetInformation.carBrand"/>
                   </td>
                </tr>
                <tr>
                   <td><input type="button" id="" name="" value="查询" onclick="selectShowInsurance();"/></td>
                </tr>
             </table>
        </from>
					<div class="blank"></div>
					 <table class="listTable">
            <tr>
                <td>姓名</td>
                <td>车辆行驶区域</td>
                <td>车辆品牌型号</td>
                <td>试算时间</td>
                <td>操作</td>
            </tr>
            <#if (sdInformations != null && sdInformations?size > 0)>
				 <#list sdInformations as list>
                      <tr>
                         <td>${list.insureder}</td>
                         <td>
                             <#if (citys != null && citys?size > 0)>
							     <#list citys as list1>
					                  <#if (list1.placeCode==list.regionCode)>
					                      ${list1.placeName}
					                  </#if>
					             </#list>
					         </#if>
                         </td>
                         <td>${list.carBrand}</td>
                         <td>${list.createDate}</td>
                         <td><a onclick="detail('${list.id}');">详情</a></td>
                      </tr>
                 </#list>
            </#if>
        </table>
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