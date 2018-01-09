 <#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] /> 
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <title>续保选择</title>
  
  
  
  <script type="">
  var errorMassage ='${errorMassage}';  
  var productId = '${zhenAiRenewalSchema.productId}';
  var planCode = '${zhenAiRenewalSchema.planCode}';  
  var productIdLV = '${productIdLV}';  
  var planCodeLV = '${planCodeLV}';  
  
  
  
  var riskCode = productId;
  var complicatedFlag = 'N';// 是否是复杂产品标识

</script>
  
  
  
  
  
  
  
  <meta name="Author" content="SINOSOFT Team" />
  <meta name="Copyright" content="SINOSOFT" />
  <link rel="icon" href="favicon.ico" type="image/x-icon" />
<head>
  <!--全局通用样式-->
  <link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
  <!--购买流程通用样式-->
  <link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>
  <link rel="stylesheet" type="text/css" href="${staticPath}/style/wj_kxb/mod_renewal.css"/>
  <link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>


</head>
<body  class="up-bg-qh">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
    
  <!-- 步骤 -->
  <div class="wrapper">
    <div class="clear h20"></div>
    <div class="renewal_box">
      <h3 class="renewal_tit"><span>续保信息</span>
        <p class="vip_logins" id="integer_login"  >
          <a id="PointShowLoginWindow" class="renewal_login" href="javascript:void(0);" onclick="return(VL_FileDL(this));return false;" exturl="http://www.kaixinbao.com/dltc01" vlpageid="dltc01">登录开心保账户</a>
        </p> 
      </h3>
      <div class="clear"></div>
      <dl class="renewal_old">
        <dt>安联臻爱医疗保险2016</dt>
        <dd>意外身故及伤残    10万/20万/30万</dd>
        <dd>一般医疗保险金 100万/200万/300万 <br>
         1. 一般住院医疗<br>
         2. 特殊门诊：包括<em>3种特定重疾</em>的特殊门诊<br>
      	    注：年免赔额1万元<br/></dd>
          <dd>恶性肿瘤保险金  100万/200万/300万 </dd>
      </dl>
      <span class="renewal_pk"></span>
       <dl class="renewal_new">
        <dt>安联臻爱医疗保险2017（重疾版）<em class="icons_upcon"></em></dt>
        <dd>意外身故及伤残    10万/20万/30万</dd>
        <dd>一般医疗保险金 100万/200万/300万 <br>
          1. 一般住院医疗 <br>
          2. 特殊门诊：包括<em>6种特定重疾</em>的门诊医疗 <br>
      	      注：年免赔额1万元，<em>首次确诊6种特定重疾无免赔额</em><br/></dd>
          <dd>6种特定重大疾病  医疗保险金  100万/200万/300万 </dd>
      </dl>
      <div class="clear"></div>
      
      
      	  <input type="hidden" name="count" id="count" />
		  <input type="hidden" name="productId" id="productId" value ="${zhenAiRenewalSchema.productId}" />
      <form id ="chosePlanForm" action="" method="post" >
		  <input type="hidden" name="zhenAiChoseProductPlanFlag" id="zhenAiChoseProductPlanFlag" value ="${zhenAiChoseProductPlanFlag}" />
		  <input type="hidden" name="productIdLV" id="productIdLV" value = "${productIdLV}" />
		  <input type="hidden" name="planCodeLV" id="planCodeLV" value = "${planCodeLV}" />	
		  <input type="hidden" name="zhenAiRenewalID" id="zhenAiRenewalID" value ="${zhenAiRenewalID}" />
		  <input type="hidden" name="zhenAiRenewalKID" id="zhenAiRenewalKID" value ="${zhenAiRenewalKID}" />
		  <input type="hidden" name="choseProduct" id="choseProduct" value ="" />
		  <input type="hidden" name="isLogin" id="isLogin" value ="${isLogin}" /> 
		  <input type="hidden" name="renwealOne" id="renwealOne" value ="true" /> 
  
 
  
  
  
      <div class="renewal_from">
        <div class="renewal_from_left">
          <ul class="renewal_con_ul"> 
            <li><div class="item"><em>*</em>用户保单号:</div><div class="text"><input type="text" id="policyNo" name ="policyNo" class="item_text"  value="${zhenAiRenewalSchema.policyNo}" data-verify="NOTNULL|保单号不为空"/></div></li>
          </ul>
          <ul class="renewal_con_ul">
            <li><div class="item"><em>*</em>投保人手机号:</div><div class="text"><input type="text" id="applicantMobile" name ="applicantMobile" class="item_text"  value="${zhenAiRenewalSchema.applicantMobile}"  data-verify="NOTNULL|投保人手机号不为空&&TELPHONE|请输入正确的手机号" /></div></li>
          </ul>
          <ul class="renewal_con_ul renewal_con_yzm">
            <li><div class="item"><em>*</em>图片验证码:</div><div class="text">
            <img class="code_yzm" id="loginWindowCaptchaImage" name="loginWindowCaptchaImage"   src="" alt="换一张">
            <input type="text" class="item_text item_text_yzm"  id="subMitImage" name="subMitImage" data-verify="NOTNULL|验证码不为空"/></div></li>
          </ul>
        </div>
        
        
        
        
       </form>
         
         
         
         
        <div class="renewal_from_right">
            <span class="up_btns">升级续保计划</span>
            <span class="old_btns">原计划续保</span>
        </div>
      </div>
    </div>
  </div>
  <!-- 步骤 -->
  
  <div class="clear"></div>

  <!-- 公共底部 -->
 <#include "/wwwroot/kxb/block/kxb_footer_new_v2.shtml" />
  <!-- 公共底部 -->
  

	 
	
  


<script type="text/javascript">VLTrace_custom_getparam='u1=productdetail';</script>
<script type="text/javascript" src="${staticPath}/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${staticPath}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticPath}/js/weixitrack.js"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401"></script>
<script type="text/javascript" src="${staticPath}/js/wj_kxb/require.js"></script>
<script type="text/javascript" src="${staticPath}/js/wj_kxb/require.config.js"></script>


 

<script type="text/javascript">
  require(["renewal"], function(index) {
    if(index && index.init) {
      index.init();
    }
  });
  
  require(["detail"], function(index) {
      if(index && index.init) {
        index.init();
      }
  });
</script>

 

</body>
</html>