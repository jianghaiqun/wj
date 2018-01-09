<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>会员中心-订单列表</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link href="${base}/template/shop/css/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/MemberCenterSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css" />
<script type="text/javascript" src="${base}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">


function del(Id){
    var pg=jQuery("#pg").val();
    var sd= jQuery("#sd").val();
    var ed=jQuery("#ed").val();
    var kw=jQuery("select[name=orderStatus] option[selected]").val();
    if(typeof(kw)=="undefined"){
    kw="99";
    }    
    var apt=jQuery("#apt").val();
	if(confirm("您确认要取消吗?")){
	location.href="order!cancel.action?orderSn="+Id+"&ldate="+sd+"&hdate="+ed+"&orderStatus="+kw+"&applicant="+apt+"&page="+pg;
	}else{}
}



function xiajia(orderSn){
jQuery.tip("此产品已下架");
var sd= jQuery("#sd").val();
var ed=jQuery("#ed").val();
var kw=jQuery("select[name=orderStatus] option[selected]").val();
if(typeof(kw)=="undefined"){
kw="99";
}    
var apt=jQuery("#apt").val();
location.href="order!update.action?ldate="+sd+"&hdate="+ed+"&orderStatus="+kw+"&applicant="+apt+"&orderSn2="+orderSn;
}
function jumpone() {
var sd= jQuery("#sd").val();
var ed=jQuery("#ed").val();
var kw=jQuery("select[name=orderStatus] option[selected]").val(); 
if(typeof(kw)=="undefined"){
kw="99";
} 
var apt=jQuery("#apt").val();
location.href="order!list.action?ldate="+sd+"&hdate="+ed+"&orderStatus="+kw+"&applicant="+apt+"&page=1";
}
function jumpbefore() {
var sd= jQuery("#sd").val();
var ed=jQuery("#ed").val();
var kw=document.getElementById("orderStatus").value;
if(typeof(kw)=="undefined"){
kw="99";
}
var apt=jQuery("#apt").val();
var pg=jQuery("#pg").val();
if(pg==1){
jQuery.tip("已为首页了");
return false;}
else{var bg=Number(pg) - Number(1);
location.href="order!list.action?ldate="+sd+"&hdate="+ed+"&orderStatus="+kw+"&applicant="+apt+"&page="+bg;  
}
}
function jumpnext() {
var sd=jQuery("#sd").val();
var ed=jQuery("#ed").val();
var kw=document.getElementById("orderStatus").value;
if(typeof(kw)=="undefined"){
kw="99";
}
var apt=jQuery("#apt").val();
var pg=jQuery("#pg").val();
var lg=jQuery("#lg").val();
if(pg==lg){
jQuery.tip("已为尾页了");
return false;}
else{var ng=Number(pg) + Number(1);
location.href="order!list.action?ldate="+sd+"&hdate="+ed+"&orderStatus="+kw+"&applicant="+apt+"&page="+ng;  
}
}
function jumplast() {
var sd= jQuery("#sd").val();
var ed=jQuery("#ed").val();
var kw=document.getElementById("orderStatus").value;
if(typeof(kw)=="undefined"){
kw="99";
}
var apt=jQuery("#apt").val();
var pg=jQuery("#pg").val();
var lg=jQuery("#lg").val();
location.href="order!list.action?ldate="+sd+"&hdate="+ed+"&orderStatus="+kw+"&applicant="+apt+"&page="+lg;  
}

function check99(){
var sd=jQuery("#sd").val();
var ed=jQuery("#ed").val();
if((sd!="" && sd!=null && typeof(sd)!="undefined")&&(ed!="" && ed!=null && typeof(ed)!="undefined")){
if(sd>ed){
jQuery.tip("起始日期不能大于终止日期");
return false;
}
}
}
function  validateOrder(){
var sd=jQuery("#sd").val();
var ed=jQuery("#ed").val();
if((sd!="" && sd!=null && typeof(sd)!="undefined")&&(ed!="" && ed!=null && typeof(ed)!="undefined")){
if(sd>ed){
jQuery.tip("起始日期不能大于终止日期");
return false;
}else{
return true;
}
}else{
return true;}
}
</script>
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

<div class="MemberCenterMiddArea">
        <div class="MemberCenterNavArea">
    	<ul>
        	<li class="IcoHome"><a href="${front}/">首页</a></li>
            <li class="IcoHome"><a href="member_center!index.action">会员中心</a></li>
            <li>我的订单</li>
        </ul>
    	<div class="clear"></div>
         </div>
      <div class="MemberMenuArea mr10">
       <div class="ActiveArea" style="top:52px;">
        	<a href="order!list.action" class="IcoUser_2">我的订单</a>
       </div>
    	<ul class="mcmenulist">
            <li><a href="profile!edit.action" class="IcoUser_1">个人资料</a></li>
            <li><a href="order!list.action" class="IcoUser_2">我的订单</a></li>
            <li><a href="password!edit.action" class="IcoUser_1" >修改密码</a></li>
            <li><a href="point!trade.action" class="IcoUser_6">积分兑换经验</a></li>
            <li><a href="point!scan.action" class="IcoUser_6">积分兑换记录</a></li>
            <li><a href="point!obtain.action" class="IcoUser_6">积分获取记录</a></li>
            <li><a href="coment!mycomment.action" class="IcoUser_8">我的评论</a></li>
            <li><a href="question!ask.action" class="IcoUser_9">我的提问</a></li>
            <li><a href="answer!reply.action" class="IcoUser_10">我的回答</a></li>
            <li><a href="stow!scan.action" class="IcoUser_12">我的收藏</a></li>
             <li><a href="my_compare!show.action" class="IcoUser_14">我的比价记录</a></li>
        </ul>
      </div>
    
    <div class="MemberCenterOrderArea">
    	  <h3 class="titmc02">我的订单</h3>
			<div class="mcsearch">
			<form  id="myOrder" action="order!list.action" method="post"  onsubmit="return validateOrder();">
				<ul>
					<li>
						<label>订单编号：</label><input type="text" style="width:140px;" name="orderSn" value="${orderSn}" class="inputstyle01" onclick="this.className='inputstyle01b'" onblur="this.className='inputstyle01'"/>
						<label>投保人：</label><input type="text" style="width:140px;" name="applicant"  value="${applicant}" id="apt" class="inputstyle01" onclick="this.className='inputstyle01b'" onblur="this.className='inputstyle01'"/>
						<label>订单状态：</label><select id="orderStatus" name="orderStatus">
						    <option value="99">---请选择---</option>
							<option value="3" <#if (orderStatus=="3")!>selected</#if>>已取消</option>
							<option value="4" <#if (orderStatus=="4")!>selected</#if>>暂存</option>
							<option value="5" <#if (orderStatus=="5")!>selected</#if>>待支付</option>
							<option value="6" <#if (orderStatus=="6")!>selected</#if>>处理中</option>
							<option value="7" <#if (orderStatus=="7")!>selected</#if>>已支付</option>
							<option value="8" <#if (orderStatus=="8")!>selected</#if>>自动取消</option>
							<option value="9" <#if (orderStatus=="8")!>selected</#if>>已退保</option>
						</select>
					</li>
					<li>
						<label>下单时间：</label><input type="text" style="width:90px;"  onfocus="WdatePicker();" name="ldate" value="${ldate}" id="sd" class="inputstyle01" onclick="this.className='inputstyle01b'" />
						&nbsp;&nbsp;至 
						<input type="text" style="width:90px;" onfocus="WdatePicker();" name="hdate" value="${hdate}" id="ed" class="inputstyle01" onclick="this.className='inputstyle01b'" />
					   &nbsp;&nbsp;<input type="submit" value="查找" class="btnorange"  />
					</li>
					<input type="hidden" value="${page}" name="pc" id="pg">
					<input type="hidden" value="${lastpage}" name="lpg" id="lg">
				</ul>
				</form>
              </div>
      <div class="OrderTitleArea">
          <table cellpadding="0" cellspacing="0" width="100%" >
        	<tr align="center">
                	<td width="130">订单号</td>
                    <td width="280">订单商品</td>
                    <td width="90">保费</td>
                    <td width="90">下单时间</td>
                    <td>状态</td>
                    <td>操作</td> 
                </tr>
            </table>
       </div>
		
        <div class="OrderContentArea">
        	<table cellpadding="0" cellspacing="0" width="100%" class="f14">
		          <#list listOrders as list> 
						<tr align="center" height="60">
								<td width="130">
								    <#if (list.get("configFlag")!"")=="1" >
								    	<h5><a href="order_config!linkOrderDetails.action?orderSn=${list.get("orderSn")}&KID=${list.get("KID")}" style="color:#f35925" >${list.get("orderSn")}</a></h5>
								    <#else>
								    	<h5><a href="order!linkOrderDetails.action?orderSn=${list.get("orderSn")}&KID=${list.get("KID")}" style="color:#f35925" >${list.get("orderSn")}</a></h5>
								    </#if>
								</td>
								<td  width="280"><strong>
								${list.get("ProductName")}</strong>
								</td>
								<td width="90">
								<#if (list.get("totalAmount")<1.00)!>
								￥${list.get("totalAmount")}<#else>
								￥${list.get("totalAmount")?string(',###.00')}</#if>
								</td>
								<td width="90">
								${list.get("createDate")}		
								</td>
								<td>
								<#if (list.get("orderStatus")==7)>
                               		<span>${list.get("CodeName")}</span>
							    <#else>   
									${list.get("CodeName")}
							   	</#if>
							   </td>
							   
							   <td>
							   		<#if (list.get("orderStatus")==4)>
										<#if (list.get("IsPublish")=="Y")>
											<#if (list.get("configFlag")!"")=="1" >
												<#assign href="order_config!keepInput.action?orderSn=" + list.get("orderSn") />
                                			<#else>
                                				<#assign href="order!keepInput.action?orderSn=" + list.get("orderSn") />
                                			</#if>
                                			<a href="${href}" target="_blank">继续录入</a>
                                		<#else>   
                                			<a href="#" onclick="return xiajia(${list.get("orderSn")});">继续录入</a>
                                		</#if>
                                		<br>
							   		</#if>	
									<#if (list.get("orderStatus")==5)>
										<#if (list.get("IsPublish")=="Y")> 
										    <#if (list.get("needUWCheckFlag")!"")=="1" >
										    	<a href="order!tpyCheckPay.action?orderSn=${list.get("orderSn")}&KID=${list.get("KID")}" target="_blank">继续支付</a>
										    <#else>
										    	<a href="order!pay.action?orderSn=${list.get("orderSn")}&KID=${list.get("KID")}" target="_blank">继续支付</a>
										    </#if>
                                		<#else>   
                                			<a href="#" onclick="return xiajia(${list.get("orderSn")}&KID=${list.get("KID")});">继续支付</a>
                                		</#if>
                                		<br>  
							   		</#if>
							   		<#if (list.get("orderStatus")!=7&&list.get("orderStatus")!=3)>
							   			<a href="#" onclick="del(${list.get("orderSn")});">取消订单</a>
							   		</#if>
							   </td> 
							</tr>
						</#list>
				 </table>
          </div>
        <div class="PageArea">
            <ul><li>共${count}条记录!当前第${page}页</li>
                <li><a href="#" onclick="return jumpone();" >首页</a></li>
                <li><a href="#" onclick="return jumpbefore();">&lt;上一页</a></li>
                <li><a href="#" onclick="return jumpnext();">下一页 &gt;</a></li>
                <li><a href="#" onclick="return jumplast();">尾页</a></li></ul>
            <div class="clear"></div>
        </div>
      </div>
	<div class="clear"></div>

					
</div>         			

</div>	
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</body>
</html>