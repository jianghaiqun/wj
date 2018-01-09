<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>我的投被保人</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link href="${shopStaticPath}/template/shop/css/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/style/commonality.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/MemberCenterSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
function isself(v){
if(v=="0"){
var name=jQuery("#tname").val();
var mobileNO=jQuery("#tsjh").val();
var email=jQuery("#tyx").val();
var id=jQuery("#tzjh").val();
var brithday =jQuery("#tsr").val();
var xl=jQuery("#tzjlx").val();
jQuery("#brs").attr("value",brithday);
jQuery("#bname").attr("value",name);
jQuery("#bsjh").attr("value",mobileNO);
jQuery("#byx").attr("value",email);
jQuery("#bzjh").attr("value",id);
jQuery("#bzjlx").attr("value",xl);
}else {
jQuery("#bname").attr("value","");
jQuery("#bsjh").attr("value","");
jQuery("#byx").attr("value","");
jQuery("#bzjh").attr("value","");
jQuery("#bzjlx").attr("value","-1");
jQuery("#brs").attr("value","");
}
}
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>



<body>
<#include "/wwwroot/kxb/block/kxb_header_index_css.shtml">
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
   <div class="tit02">投保人信息</div>
   <form id="informationEidtForm" action="app_insured!updateToSave.action" method="post">	
    <div class="sheetrig2">
       <ul>
         <li><label>投保人姓名：</label><input type="text" name="appInsured.applicantName" value="${appInsured.applicantName}" id="tname" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;" /></li>
          <li><label>投保人手机号码：</label><input type="text" name="appInsured.applicantTel" value="${appInsured.applicantTel}" id="tsjh" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;"  /></li>
          <li><label>投保人生日：</label><input type="text" name="appInsured.applicantBirthday" onfocus="WdatePicker();"  value="${appInsured.applicantBirthday}" id="tsr" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;" /></li>
           <li><label>证件类型：</label><select name="appInsured.applicantIdentityType" id="tzjlx" style="width:355px;">
            <option value="-1">---请选择---</option>
              <#if (listid != null && listid?size > 0)>
             <#list listid as list>
            <option value="${list.dictSerial}" <#if (appInsured.applicantIdentityType==list.dictSerial)!> selected</#if>>${list.dictName}</option>
              </#list>
            </#if>
              </select></li>
             <li><label>证件号码：</label> <input type="text" name="appInsured.applicantIdentityId" value="${appInsured.applicantIdentityId}" id="tzjh" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;"  /></li>
            <li><label>投保人电子邮箱：</label><input type="text" name="appInsured.applicantMail" value="${appInsured.applicantMail}" id="tyx" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;" /></li>
          </ul>
     </div>
           <div class="tit02">被保人信息</div>
     <div class="sheetrig2">
           <ul>
             <li><label>与投保人关系：</label><#list listRelation as list>
             <input type="radio" name="appInsured.relation" value="${list.get("CodeValue")}" onclick="isself(this.value);" <#if (appInsured.relation==list.get("CodeValue"))!>checked</#if>/> 
            ${list.get("CodeName")}
            </#list></li>
              <li><label>被保人姓名：</label><input type="text" name="appInsured.recognizeeName" value="${appInsured.recognizeeName}"  id="bname" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;" /></li>
             <li><label>被保人生日：</label><input type="text" name="appInsured.recognizeeBirthday" onfocus="WdatePicker();"  value="${appInsured.recognizeeBirthday}" id="bsr" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;" /></li>
          
          <li><label>被保人手机号码：</label><input type="text" name="appInsured.recognizeeTel" value="${appInsured.recognizeeTel}"  id="bsjh" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;"  /></li>
           <li><label>证件类型：</label><select name="appInsured.recognizeeIdentityType" id="bzjlx" style="width:355px;">
            <option value="-1">---请选择---</option>
             <#if (listid != null && listid?size > 0)>
              <#list listid as list>
            <option value="${list.dictSerial}" <#if (appInsured.recognizeeIdentityType==list.dictSerial)!> selected</#if>>${list.dictName}</option>
             </#list>
             </#if>
                </select></li>
              <li><label>证件号码：</label> <input  type="text" name="appInsured.recognizeeIdentityId" value="${appInsured.recognizeeIdentityId}" id="bzjh" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;"  /></li>
            <li><label>电子邮箱：</label><input  type="text" name="appInsured.recognizeeMail" value="${appInsured.recognizeeMail}" id="byx" class="inputstyle02" onclick="this.className='inputstyle02b'" onblur="this.className='inputstyle02'" style="width:350px;" /></li>
          </ul>
            <ul>
          <li><label>&nbsp;</label><input type="submit" class="btnorange02" value="保 存" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" class="btnorange02" value="取消" /></li>
            </ul>
       </div>
        <input type="hidden" name="appInsured.id" value="${appInsured.id}"/>
            </form>
</div>
</div>	

</div>
<div class="clear"></div>


<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<#include "/wwwroot/kxb/block/community_v1.shtml" />
</div>	
</body>


</html>