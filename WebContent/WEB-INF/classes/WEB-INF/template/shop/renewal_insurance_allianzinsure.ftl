<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <title>续保信息填写</title>
  <meta name="Author" content="SINOSOFT Team" />
  <meta name="Copyright" content="SINOSOFT" />
  <link rel="icon" href="favicon.ico" type="image/x-icon" />
<head>
  <!--全局通用样式-->
  <link rel="stylesheet" type="text/css" href="http://resource.kaixinbao.com/style/redesign/re_header.css"/>
  <!--购买流程通用样式-->
  <link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>
  <link rel="stylesheet" type="text/css" href="${staticPath}/style/wj_kxb/mod_renewal.css"/>
  <link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>

</head>
<body  class="up-bg-qh">
	<form id="orderInfoForm"  action="" method="post" enctype ="multipart/form-data"> 

	<!-----------------------------------------begin hidden----------------------------------------->

	<!--保费试算信息-->
	<#list initInsureData.premcalFactors?keys as key> 
	   <input type="hidden" id="factor_${key}" value="${initInsureData.premcalFactors.get(key)}"/>
	</#list>

	<!--订单信息-->
	<input type="hidden" id="totalAmount" name="sdorder.totalAmount"/>
	<input type="hidden" id="payPrice" name="sdorder.payPrice"/>
	<input type="hidden" id="productTotalPrice" name="sdorder.productTotalPrice"/>
  <input type="hidden" name="sdorder.renewalId" value="${initInsureData.ID}"/>
  <input type="hidden" name="sdorder.discountAmount" id="discountAmount" value=""/> 
  <input type="hidden" name="sdorder.offsetPoint" id="offsetPoint" value=""/>
  <input type="hidden" name="sdorder.channelsn" value="wj"/>

	<!--sdinformation信息-->
	<input type="hidden" id="productId" value="${initInsureData.choseProductId}" name="sdinformation.productId"/>
	<input type="hidden" value="${initInsureData.productName}" name="sdinformation.productName" />
	<input type="hidden" value="${initInsureData.chosePlanCode}" name="sdinformation.planCode" />
	<input type="hidden" value="${initInsureData.planName}" name="sdinformation.planName" />
  <input type="hidden" value="${initInsureData.premcalFactors.get('Period')}" name="sdinformation.ensure" />
  <input type="hidden" value="${initInsureData.period}" name="sdinformation.ensureDisplay" />

	<!--被保人信息-->
	<input type="hidden" name="sdinformationinsuredList[0].recognizeeBirthday" value="${initInsureData.recognizeeBirthDate}"/>
	<input type="hidden" name="sdinformationinsuredList[0].recognizeeSex" value="${initInsureData.recognizeeSex}"/>
	<input type="hidden" name="sdinformationinsuredList[0].recognizeeAppntRelation" value="${initInsureData.withPolicyholderRelation}"/>
	<input type="hidden" id="discountPrice_0" name = "sdinformationinsuredList[0].discountPrice"/>
    <input type="hidden" id="recognizeePrem_0" name = "sdinformationinsuredList[0].recognizeePrem" value=""/>
    <input type="hidden" id="recognizeeTotalPrem_0" name = "sdinformationinsuredList[0].recognizeeTotalPrem" value=""/>
    <#if (initInsureData.recognizeeOccupation1??) && (initInsureData.recognizeeOccupation1 != "") >
    <input type="hidden" name="sdinformationinsuredList[0].recognizeeOccupation1" value="${initInsureData.recognizeeOccupation1}"/>
    </#if>
     <#if (initInsureData.recognizeeOccupation2??) && (initInsureData.recognizeeOccupation2 != "") >
    <input type="hidden" name="sdinformationinsuredList[0].recognizeeOccupation2" value="${initInsureData.recognizeeOccupation2}"/>
    </#if>
     <#if (initInsureData.recognizeeOccupation3??) && (initInsureData.recognizeeOccupation3 != "") >
    <input type="hidden" name="sdinformationinsuredList[0].recognizeeOccupation3" value="${initInsureData.recognizeeOccupation3}"/>
    </#if>


	<!--投保人信息-->
	<input type="hidden" name="sdinformationAppnt.applicantBirthday" value="${initInsureData.applicantBirthDate}"/>
    <input type="hidden" name="sdinformationAppnt.applicantSex" value="${initInsureData.applicantSex}"/>
    <#if (initInsureData.applicantOccupation1??) && (initInsureData.applicantOccupation1 != "") >
    <input type="hidden" name="sdinformationAppnt.applicantOccupation1" value="${initInsureData.applicantOccupation1}"/>
    </#if>
     <#if (initInsureData.applicantOccupation2??) && (initInsureData.applicantOccupation2 != "") >
    <input type="hidden" name="sdinformationAppnt.applicantOccupation2" value="${initInsureData.applicantOccupation2}"/>
    </#if>
     <#if (initInsureData.applicantOccupation3??) && (initInsureData.applicantOccupation3 != "") >
    <input type="hidden" name="sdinformationAppnt.applicantOccupation3" value="${initInsureData.applicantOccupation3}"/>
    </#if>

	<!--其它信息-->
	<input type="hidden" name="policyNoOld" value="${initInsureData.policyNo}" />
	<input type="hidden" name="protectionPeriodTy" id="protectionPeriodTy" />
	<input type="hidden" name="protectionPeriodLast" id="protectionPeriodLast" />
	<input type="hidden" name="m_startDate" id="m_startDate" />
	<input type="hidden" name="m_endDate" id="m_endDate" />
	<input type="hidden" id="planCode" value="${initInsureData.chosePlanCode}"/>
	<input type="hidden" id="complicatedFlag" value="${initInsureData.complicatedFlag}"/>
	<input type="hidden" id="needUWCheckFlag" value="${needUWCheckFlag}"/>

	<!--投保声明信息-->
	<div class="msg-con bdmx_table_b" id="insureStatement" style="display:none;">
		<ol class="sz_list">
			<li>本人作为投保人已经将此保险产品全部保障内容和保险金额向被保险人做了明确说明，被保险人对此已表示完全同意。</li>
			<li>本投保人声明均已如实填写上述各项投保信息，如果信息填写不真实或不准确，愿意承担一切责任。</li>
			<li>本投保人和被保险人均已详细阅读并认可该保险产品的各项保险条款，特别是对保险条款中有关责任免除部分已经详细了解并完全认可。同时阅读并已了解开心保提示、客户告知书以及关于投保人、被保险人权利和义务等相关内容，本投保人和被保险人均已认可保险合同的全部内容。</li>
			<li>根据《中华人民共和国合同法》第十一条规定，数据电文是合法有效的合同书面形式。本投保人同意保险公司提供的电子保单或其它保险信息作为本保险合同成立生效的合法有效凭证，并具有完全证据效力。</li>
			<#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")> 
				<li>使用积分兑换的保险产品不可进行变更操作，不支持退保，如有不便敬请谅解。</li>
			</#if>
		</ol>
	</div> 

	<!-----------------------------------------end hidden----------------------------------------->

  <!--top头部-->
  <div class="g-top">
    <div class="g-weaper1200">
      <div class="m-login">
        <div id="NotLogin">
          <span class="m-login-p f-ib">您好，请</span>
          <span class="m-login-a" id="NotLoginSpan">
            <a href="###" id="headerShowLoginWindow">登录</a> <em>/</em>
            <a href="http://www.kaixinbao.com/wj/shop/member!newLogin.action?tagid=regtag">注册</a>
            <span class="m-login-x" style="display:none"></span>
            <!-- 未登录状态 显示图层 -->
            <div class="m-login-no" style="display:none;">
              <div class="m-lo-p">
                <div class="m-lo-img"></div>
                <img src="http://resource.kaixinbao.com/images/redesign/photo_06.gif" alt=""></div>
              <div class="m-login-jf">
                <a href="http://www.kaixinbao.com/wj/shop/member_center!index.action?tagid=logintag">
                  会员专享，
                  <br>积分换优惠</a>
              </div>
              <div class="clear h12"></div>
              <div class="m-integral"> <em class="i-integral f-ib"><a href="http://www.kaixinbao.com/wj/shop/point!newList.action">积分</a></em> 
              </div>
              <div class="m-Coupon">
                <em class="i-Coupon f-ib"><a href="http://www.kaixinbao.com/wj/shop/coupon!queryCoupon.action">优惠券</a></em> 
              </div>
            </div>
            <!-- 未登录状态 显示图层 end --> </span>
        </div>
        <div id="YesLogin" style="display: none">
          <span class="m-login-p f-ib" id="YesLoginSpan">
            您好
            <a href="http://www.kaixinbao.com/wj/shop/member_center!index.action?tagid=logintag" id="headerLoginMemberUsername" class="m-loing-name"></a> <i id="headerLoginMemGradeIcon" class="vip_k vip_top vip_k1" title=""></i>

            <span class="m-login-x" style="display:none"></span>
            <!--登录状态显示图层 -->
            <div class="m-login-yes z-hover-shadow"  style="display:none;">
              <div class="m-login-back">
                <span class="m-login-p m-login_p2">
                  您好
                  <a href="http://www.kaixinbao.com/wj/shop/member_center!index.action?tagid=logintag" id="headerLoginMemberUsername" class="m-loing-name"></a> <i id="headerLoginMemGradeIcon"  class="vip_k vip_top vip_k1" title=""></i>
                </span>
                <a href="javascript:void(0);" id="headerLogout" class="m-go-back">退出登录</a>
              </div>
              <div class="m-lo-p">
                <div class="m-lo-img">
                  <a href="###"></a>
                </div>
                <img id="memberphoto" alt=""></div>
              <div class="m-login-user">
                <a href="http://www.kaixinbao.com/wj/shop/member_center!index.action?tagid=logintag" id="headerLoginMemberUsername" class="f_mi m-login-name2"></a>
                <i id="headerLoginMemGradeIcon" class="vip_k vip_top vip_k1" title=""></i>
                <p id="gradeinfo"></p>
              </div>
              <div class="clear h6"></div>
              <div class="m-integral2">
                <em class="i-integral f-ib">
                  <a href="http://www.kaixinbao.com/wj/shop/point!newList.action">积分</a>
                </em>
                <a id="memberPointsCount" href="http://www.kaixinbao.com/wj/shop/point!newList.action" class="m-jf-num"></a>
              </div>
              <div class="m-Coupon2">
                <em class="i-Coupon f-ib">
                  <a href="http://www.kaixinbao.com/wj/shop/coupon!queryCoupon.action">优惠券</a>
                </em>
                <a id="CouponCount" href="http://www.kaixinbao.com/wj/shop/coupon!queryCoupon.action" class="m-jf-num"></a>
              </div>
            </div>
            <!--登录状态显示图层 end --> </span>
        </div>
      </div>
      <div class="m-top-fun">
        <span class="m-fun-span f-ib m-tel">
          <em class="i-tel"></em>
          <i class="p-tel">4009-789-789</i>
        </span>
        <em class="m-bor-l f-ib"></em>
        <span class="m-fun-span f-ib m-WeChat" id="i-WeChat">
          <a href="###" class="m-fun-a hover">
            <em class="i-WeChat"></em>
          </a>
          <div class="m-WeChatImg z-hover-shadow" style="display:none;">
            <img src="http://resource.kaixinbao.com/images/redesign/wechart.gif" alt="">
            <p>
              关注微信公众号
              <br>轻松管理账单</p>
          </div>
        </span>
        <em class="m-bor-l f-ib"></em>
        <span class="m-fun-span f-ib m-service" id="i-service">
          <a href="###"  class="m-fun-a ">
            <em class="i-service"></em>
          </a>
          <div class="m-service_con z-hover-shadow" style="display:none;">
            <ul class="m-service-li">
              <li>
                <a href="http://www.kaixinbao.com/ddkscx/" target="_blank" rel="nofollow">保单服务</a>
              </li>
              <li>
                <a href="http://www.kaixinbao.com/bdyz/" target="_blank" rel="nofollow">保单验真</a>
              </li>
              <li>
                <a href="http://www.kaixinbao.com/lpzs/index.shtml" target="_blank" rel="nofollow">理赔申请</a>
              </li>
            </ul>
          </div>
        </span>
        <em class="m-bor-l f-ib"></em>
        <span class="m-fun-span f-ib m-cart" id="i-user-cart-span">
          <a href="http://www.kaixinbao.com/wj/shop/member_shopcart!getShopCartINF.action" class="m-fun-a ">
            <em class="i-cart"></em>
            <i class="m-cartNum" id="shopCartNo">0</i>
          </a>
          <!-- 用户未登录 -->
          <div class="m-user-no z-hover-shadow" id="m-cart-nologin" style="display:none;">
            <div class="m-user-noImg"></div>
            <span>请先登录</span>
            <a href="http://www.kaixinbao.com/wj/shop/member_center!index.action?tagid=logintag" class="m-btn-login">登录</a>
          </div>
          <!-- 购物车为空 -->
          <div class="m-user-no z-hover-shadow" id="m-cart-no" style="display:none;">
            <div class="m-user-noImg"></div>
            <span>购物车中没有商品</span>
            <a href="http://www.kaixinbao.com" class="m-btn-login">去选购商品</a>
          </div>
          <!-- 购物车不为空 -->
          <div class="m-cart_list z-hover-shadow" id="m-cart-yes" style="display:none;">
            <ul class="m-cart-ul" id="shopcartinfo"></ul>
            <div class="m-cart-go">
              共计
              <em id="shoptotal"></em>
              <a href="http://www.kaixinbao.com/wj/shop/member_shopcart!getShopCartINF.action" class="m-cart-to">去购物车</a>
            </div>
          </div>
        </span>
        <em class="m-bor-l f-ib"></em>
        <span class="m-fun-span f-ib m-user" id="i-user-span">
          <a href="http://www.kaixinbao.com/wj/shop/member_center!index.action?tagid=logintag" class="m-fun-a ">
            <em class="i-user"></em>
          </a>
          <!-- 用户未登录 -->
          <div class="m-user-no z-hover-shadow" id="m-user-no" style="display:none;">
            <div class="m-user-noImg"></div>
            <span>请先登录</span>
            <a href="http://www.kaixinbao.com/wj/shop/member_center!index.action?tagid=logintag" class="m-btn-login">登录</a>
          </div>
          <!-- 用户已登录状态 -->
          <div class="m-user-yes z-hover-shadow" id="m-user-yes" style="display:none;">
            <dl class="m-order_dl m-order_dl1">
              <dt>我的保单</dt>
              <dd>
                <a id="prepay" href="http://www.kaixinbao.com/wj/shop/order_query!queryNoPayOrder.action" class="f-ib" rel="nofollow"></a>
                <a id="preeffecitive" href="http://www.kaixinbao.com/wj/shop/order_query!queryNoEffectOrder.action" class="f-ib" rel="nofollow"></a>
                <a id="effecitive" href="http://www.kaixinbao.com/wj/shop/order_query!queryEffectingOrder.action" class="f-ib" rel="nofollow"></a>
              </dd>
            </dl>
            <dl class="m-order_dl">
              <dt>账户中心</dt>
              <dd>
                <a href="http://www.kaixinbao.com/wj/shop/profile!edit.action" class="f-ib" rel="nofollow">账户信息</a>
                <a href="http://www.kaixinbao.com/wj/shop/point!newList.action" class="f-ib" rel="nofollow">我的积分</a>
                <a href="http://www.kaixinbao.com/wj/shop/coupon!queryCoupon.action" class="f-ib" rel="nofollow">我的优惠券</a>
              </dd>
            </dl>
            <dl class="m-order_dl">
              <dt>客户服务</dt>
              <dd>
                <a href="http://www.kaixinbao.com/wj/shop/stow!queryScan.action" class="f-ib" rel="nofollow">我的收藏</a>
                <a href="http://www.kaixinbao.com/wj/shop/question!questionList.action" class="f-ib" rel="nofollow">保险问答</a>
                <a href="http://www.kaixinbao.com/wj/shop/member_comment!queryComment.action" class="f-ib" rel="nofollow">商品评价</a>
                <a href="http://www.kaixinbao.com/wj/shop/my_compare!show.action" class="f-ib">对比记录</a>
                <a href="http://www.kaixinbao.com/wj/shop/my_consult!show.action" class="f-ib" rel="nofollow">我的咨询</a>
              </dd>
            </dl>
          </div>
        </span>
        <em class="m-bor-l f-ib"></em>
      </div>
    </div>
  </div>
  <!--top头部end-->

  <!-- header -->
  <div class="g-header ">
    <div class="g-weaper">
      <a href="http://www.kaixinbao.com/" class="m-logo-a">
        <img src="http://resource.kaixinbao.com/images/redesign/logo.gif" height="70px" class="m-logo" alt=""></a>
      <img src="http://resource.kaixinbao.com/images/redesign/f_logo.gif" class="m-flogo" height="70px" alt="">
      <div class="clear"></div>
      <!-- 导航开始 -->
    </div>
    <!-- 导航结束 --> </div>
  <!-- header end -->
  <!-- 步骤 -->
  <div class="wrapper">
    <div class="mod_nav_con">
      <ul class="mod_nav_ul">
        <li class="sel_nav"><span class="num_icon num_icon1"></span>填写投保信息<em></em></li>
        <li ><span class="num_icon num_icon2"></span>确认支付<em></em></li>
      </ul>
    </div>
    <div class="renewal_box">
      <h3 class="renewal_tit"><span>续保信息</span></h3>
      <div class="renewal_box_con">
        <ul class="renewal_con_ul">
          <li><div class="item">保险名称:</div><div class="text">
            <span class="con">${initInsureData.productName}</span>
          </div></li>
        </ul>
        <ul class="renewal_con_ul">
          <li><div class="item">投保计划:</div><div class="text">
            <span class="con">${initInsureData.planName}</span>
          </div></li>
        </ul>
        <ul class="renewal_con_ul">
        <li><div class="item">保险期限:</div><div class="text"> <span class="con">${initInsureData.period}</span></div></li>
        </ul>
        <ul class="renewal_con_ul">
            <li><div class="item"><em>*</em>起保时间:</div><div class="text"><input readonly="readonly" id="effective"  type="text" class="item_text item_times"  data-verify="NOTNULL|起保时间不为空"  onfocus="WdatePicker({skin:'whyGreen',minDate:'${initInsureData.policyStartTime}',maxDate:'${initInsureData.policyEndTime}',onpicking:function(dp){ jQuery(this).parent().siblings('.error').remove(); }});" /><em class="time_icons"></em><span class="item_time" id="policyStartEndTime" style="display:none;">从<em id="policyStart"></em> 00时起到<em id="policyEnd"></em> 24时为止</span></div></li>
        </ul>
       <div class="renewal_left">
         <ul class="renewal_con_ul">
          <li><div class="item"><em>*</em>投保人姓名:</div><div class="text"> <span class="con">${initInsureData.applicantName}</span></div>
          <input type="hidden" value="${initInsureData.applicantName}" name="sdinformationAppnt.applicantName"/>
          </li>
        </ul>
        <ul class="renewal_con_ul">
          <li><div class="item"><em>*</em>证件号码:</div><div class="text"><span class="con">${initInsureData.applicantIdentityTypeName}:<em>${initInsureData.applicantIdentityId}</em></span></div>
           <input type="hidden" value="${initInsureData.applicantIdentityId}" name="sdinformationAppnt.applicantIdentityId"/>
           <input type="hidden" value="${initInsureData.applicantIdentityType}" name="sdinformationAppnt.applicantIdentityType"/>
           <input type="hidden" value="${initInsureData.applicantIdentityTypeName}" name="sdinformationAppnt.applicantIdentityTypeName"/>
          </li>
        </ul>
        <ul class="renewal_con_ul">
          <li><div class="item"><em>*</em>手机号码:</div><div class="text"><input type="text" class="item_text"  data-verify="NOTNULL|手机号不为空&&TELPHONE|请输入正确的手机号" value="${initInsureData.applicantMobile}" name="sdinformationAppnt.applicantMobile"/></div></li>
        </ul>
        <ul class="renewal_con_ul">
          <li><div class="item"><em>*</em>电子邮箱:</div><div class="text"><input type="text" data-verify="NOTNULL|电子邮箱不为空&&EMAIL|请输入正确的电子邮箱&&LENGTH[5,50]|电子邮箱的长度不正确" class="item_text" value="${initInsureData.applicantMail}" name="sdinformationAppnt.applicantMail"/>
          </div>
          </li>
        </ul>
       </div>
        <div class="renewal_right">
         <ul class="renewal_con_ul">
          <li><div class="item"><em>*</em>被保人姓名:</div><div class="text"> <span class="con">${initInsureData.recognizeeName}</span></div>
          <input type="hidden" value="${initInsureData.recognizeeName}" name="sdinformationinsuredList[0].recognizeeName"/>
          </li>
        </ul>
        <ul class="renewal_con_ul">
          <li><div class="item"><em>*</em>证件号码:</div><div class="text"> <span class="con">${initInsureData.recognizeeIdentityTypeName}:<em>${initInsureData.recognizeeIdentityId}</em></span></div>
          <input type="hidden" value="${initInsureData.recognizeeIdentityId}" id="recognizeeIdentityId" name="sdinformationinsuredList[0].recognizeeIdentityId"/>
          <input type="hidden" value="${initInsureData.recognizeeIdentityType}" id="recognizeeIdentityType" name="sdinformationinsuredList[0].recognizeeIdentityType"/>
          <input type="hidden" value="${initInsureData.recognizeeIdentityTypeName}" name="sdinformationinsuredList[0].recognizeeIdentityTypeName"/>
          </li>
        </ul>
        <ul class="renewal_con_ul">
          <li><div class="item"><em>*</em>手机号码:</div><div class="text"><input type="text" class="item_text"  data-verify="NOTNULL|手机号不为空&&TELPHONE|请输入正确的手机号" value="${initInsureData.recognizeeMobile}" name="sdinformationinsuredList[0].recognizeeMobile" /></div>
          </li>
        </ul>
        <ul class="renewal_con_ul">
          <li><div class="item"><em>*</em>电子邮箱:</div><div class="text"><input type="text" class="item_text" data-verify="NOTNULL|电子邮箱不为空&&EMAIL|请输入正确的电子邮箱&&LENGTH[5,50]|电子邮箱的长度不正确" value="${initInsureData.recognizeeMail}" name="sdinformationinsuredList[0].recognizeeMail" /></div>
          </li>
        </ul>
       </div>
       <div class="clear"></div>
       <ul class="at_list">
                    ${(activityInfo)!}
                  </ul>
            <div class="renewal_pay_all">
              合计:<em>￥</em><span id="amount">0</span>&nbsp;<span id="productPrem" style="color:gray;font-size:14px;text-decoration:line-through;
"></span>
            </div>
            <div class="object_btn" style="text-align: center;">
        <div class=btn-more>
        <div class="check_btn"><label><input name="agreeInsure" id="agreeInsure" type="checkbox" value="" />&nbsp;我接受以上投保声明及必读内容</label> <span class="sel_tip" id="lookInsureStatement">查看投保声明</span> 
        </div>
        <div class=" height_s"></div>
                  <input type="button" id="qrgm_pay"  class="qrgm_btn" value="确认无误 去支付">

               </div>
      </div>
      <div id="msg_2" style="display:none" class="londing_mes londing_mes_nolog"><img src="http://localhost:8080/images/nloading.gif" width="20px" height="20px" alt="" /><span id="msg_2_2">正在处理，请稍等</span></div>
      </div>
    </div>
  </div>
  <!-- 步骤 -->
  </form>

<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<script type="text/javascript">VLTrace_custom_getparam='u1=productdetail';</script>
<script type="text/javascript" src="http://resource.kaixinbao.com/js/weixitrack.js"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401"></script>
<script type="text/javascript" src="${shopStaticPath}/wj_kxb/require.js"></script>
<script type="text/javascript" src="${shopStaticPath}/wj_kxb/require.config.js"></script>
<script type="text/javascript">
  require(["renewal"], function(index) {
    if(index && index.init) {
      index.init();
    }
  });
</script>

</body>
</html>