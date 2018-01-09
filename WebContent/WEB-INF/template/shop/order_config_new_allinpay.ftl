<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保_非车险_支付列表</title>
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>
<!--支付页面样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh cashvalue">
   <#if (typeFlag =="02")!>
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
</#if>
<#if (typeFlag !="02")!>
  <#if (typeFlag =="03")!>
  <iframe id="iframeA" name="iframeA" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" src="${CpsAgentURL}/front/include/agent_order_head.jsp" width="100%" height=100%></iframe> 
  <#else>
    <#if (sdorder.renewalId != "" && sdorder.renewalId != null)!>
      <#include "/wwwroot/kxb/block/kxb_header_evaluating.shtml">
      <div class="g-header ">
          <div class="g-weaper">
              <a href="/" class="m-logo-a">
                  <img src="${staticPath}/images/redesign/logo.gif" height="70px" class="m-logo" alt="">
              </a>
              <img src="${staticPath}/images/redesign/f_logo.gif" class="m-flogo" height="70px" alt="">
              <div class="clear"></div>
            </div>
          </div>
    <#else>
      <#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
    </#if>
  </#if>
</#if>

  <div class="wrapper">
  <input type="hidden" id="paySn" value="${paySn}"/>
  <input type="hidden" id="kid" value="${KID}"/>
  <input type="hidden" id="channelsn" value="${(sdorder.channelsn)!}"/>
  <input type="hidden" id="orderSn" value="${sdorder.orderSn}"/>
  <input type="hidden" id="sdorderId" value="${sdorder.id}"/>
  <input type="hidden" name="CouponSn"  id="CouponSn" value="0"/>
  <input type="hidden" id ="artLoginFlag" name="artLoginFlag" value="0" />
  <input type="hidden" name="typeFlag" value="${typeFlag}"/>
  <input type="hidden" name="CouponSn"  id="CouponSn" value="0"/>
  <input type="hidden" name="offsetPoint"  id="offsetPoint" value="0"/>


    <div class="re-line-log">
        <ul class="re-line-ul">
            <li class="re-line-d"><div class="re-linehead re-linehead1"><span></span></div><h3 class="re-line-h3">1、填写投保信息<p>我们对您填写的信息严格保密</p></h3><span class="re-line-jiao"></span></li>
            <li class="re-line-d"><div class="re-linehead re-linehead2"><span></span></div><h3 class="re-line-h3">2、确认保单<p>确认信息填写是否正确无误</p></h3><span class="re-line-jiao"></span></li>
            <li class="re-line-s"><div class="re-linehead re-linehead3"><span></span></div><h3 class="re-line-h3">3、在线支付<p>积分折扣、支持多种支付方式</p></h3><span class="re-line-jiao"></span></li>
        </ul>
    </div>
	<div class="clear"></div>
	
		<div class="w900 order-bg">			
		
			<div class="line_a shop_sptitle ">
        	<div class="pay_box">
	 				  <div class="ins-tit un_bot_bor">支付信息</div>
					   <table width="100%" border="1" >
        
          <tr>
              <th><span class="ins-tit-c">订单号</span></th>
              <th colspan="4" ><span class="ins-tit-ordernum"><a href="${base}/shop/order_config_new!linkOrderDetails.action?orderSn=${sdorder.orderSn}&KID=${KID}" target="_blank">${sdorder.orderSn}</a></span></th>
            </tr>
          <tr>
            <td width="15%" class="ins-tit-td">投保人</td>
            <td class="ins-tit-td">产品名称</td>
            <td width="15%" class="ins-tit-td">份数</td>
            <#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")!>
              <td width="15%" class="ins-tit-td">费用合计</td>
            <#elseif (activityFlag == "0")>
              <td width="15%" class="ins-tit-td">费用合计</td>
            <#else>
              <td width="15%" class="ins-tit-td">原始价格</td>
              <td width="15%" class="ins-tit-td">优惠后价格</td>
            </#if>
          </tr>
          <#list activityMap?keys as itemKey>
              <#assign map_activity = activityMap[itemKey]>
              <#list map_activity?keys as key_activity>
                <#assign map_info = map_activity[key_activity]>
          <tr>
            <td>${map_info.ProductInfo[0].ApplicantName}</td>
            <td >${map_info.ProductInfo[0].ProductName}</td>
            <td >${map_info.ProductInfo[0].InsuredCount}</td>
            <#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")!>
              <td ><font class="pay_jg" id="pay_price">${sdorder.offsetPoint}积分</font><span class="yhj_yhts" id="yhj_tb_des"></span></td>
            <#elseif (activityFlag == "0")>
              <td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_info.ProductInfo[0].Amount}</font>
              <#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if>
              <span class="yhj_yhts" id="yhj_tb_des"></span></td>
            <#else>
                <td class="pay_zk_money">￥${sdorder.productTotalPrice}</td>
                <#if (map_info.ActivityInfo.type =="6")>
                  <td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_info.ProductInfo[0].ActivityeAmount}</font><#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if><span class="yhj_yhts" id="yhj_tb_des"></span></td>
              <#else>
                  <td nowrap><b class="pay_jg">￥</b><font class="pay_jg" id="pay_price">${map_info.ProductInfo[0].Amount}</font><#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if><span class="yhj_yhts" id="yhj_tb_des"></span></td>
              </#if>
            </#if>
          </tr>
          <#if (isLcx !='0')>
          <#if (sdorder.channelsn != "jfsc" && sdorder.channelsn != "wj_jfsc" && sdorder.channelsn != "wap_jfsc")>
          <tr>
            <#if (map_info.ActivityInfo.type == "-1" || map_info.ActivityAmont.DiscountAmount == '0.00' )>
              <td class="clear-td-bor no-yh-td" colspan="5"></td>
            <#else>
              <td colspan="5" class="clear-td-bor">
                <div class="yh_mew_list">
                     <ul class="at_list">
                     <li>
                         <#if (map_info.ActivityInfo.type =="6")>
                            <span class="tb_icon active_02">
                         <#else>
                            <span class="tb_icon active_04">
                          </#if>
                        ${map_info.ActivityInfo.typeName}</span><span class="tb_text">${map_info.ActivityInfo.description}</span></li>
                         </ul>
                         <div class="clear"></div>
                  </div>
              </td>
            </#if>
          </tr>
          </#if>
          </#if>
          </#list>
        </#list>
        </table>
        
        <#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")!>
          <div class="jf_concs">
            <span class="js-spanf">1</span>个订单&nbsp;&nbsp;&nbsp;&nbsp;总计：<span class="zj_jf">${sdorder.offsetPoint}</span>&nbsp;积分<div class="h10"></div>
              您剩余积分<em class="js-spanf">${points}</em>个
          </div>
          <a href="javascript:void(0);" onclick="dosubmit('zerozf', '1');" class="jf_gopay">确认支付</a>
        <#else>
        
      <#if (typeFlag !="02" && typeFlag !="03")!>
        <#if (map_pointinfo.givepoints !="0"&&map_pointinfo.givepoints !="0.0"&&map_pointinfo.givepoints !="0.00")>
          <div class="yhhd-des">
            <h3 class="yhhd-span">支付成功即可参加以下优惠活动：</h3>
            <ul class="yhhd-ul">
            <#if (map_pointinfo.pointDesFlag =="true")>
              <li>${map_pointinfo.pointDesStart}<b id="givepoint">${map_pointinfo.givepoints}</b>积分（下次购买可抵扣<b id="givepointvalue">${map_pointinfo.pointValue}</b>元）</li>
            <#else>
              <li>保单生效后可获得<b id="givepoint">${map_pointinfo.givepoints}</b>积分（下次购买可抵扣<b id="givepointvalue">${map_pointinfo.pointValue}</b>元）</li>
            </#if>
            <#if (activitylist?size > 0)>
              <#list activitylist as map>
              <li>${map.index}、${map.title}</li>
              </#list>
            </#if>
            </ul>
          </div>
                    
        <#else>
          <#if (activitylist?size > 0)>
            <div class="yhhd-des">
              <h3 class="yhhd-span">支付成功即可参加以下优惠活动：</h3>
              <ul class="yhhd-ul">
                <#list activitylist as map>
                <li>${map.index}、${map.title}</li>
                </#list>
              </ul>
            </div>
          <#else>
            <div class="yhhd-des" style="display:none"></div>
          </#if>
        </#if>
      </#if>
            
            <!--优惠券和活动开始-->
             <#if (isLcx !="0")>
            <div id="yhj_form" class="pay_postion_box">
              <table class="gwc_js_tables">
                <tr>
                  <td class="gwc_js_table"></td>
                  <td class="gwc_js_table_w"><span class="gwc_dd">共<i class="" id="ord_num">1</i>个订单&nbsp;&nbsp;总计：</span></td>
                  <td class="gwc_js_table_pay">
                          <i class="gwc_dd pay-pic1">￥</i><span class="gwc_dd pay-pic2" id="ord_price">${callBackAmount}</span></td>
                </tr>
                <#list activityMap?keys as itemKey>
                  <#assign map_activity = activityMap[itemKey]>
                  <#list map_activity?keys as key_activity>
                    <#if (key_activity !="_no_activity")>
                      <#assign map_info = map_activity[key_activity]>
                      <#if (map_info.ActivityInfo.type =="3" && map_info.ActivityAmont.DiscountAmount != '0.00' )>
                        <tr id="pay_div">
                          <td  class="gwc_js_table"></td>
                          <td class="gwc_js_table_w"><span class="gwc_dd">减免：</span></td>
                          <td class="gwc_js_table_pay">
                            <span class="gwc_dd"><b>￥</b><i>-${map_info.ActivityAmont.DiscountAmount}</i></span>
                              </td>
                        </tr>
                      </#if>
                    </#if>
                  </#list>
                </#list>
                <tr id="yhq_price" style="display:none;">
                </tr>
                <tr id="yh_price" style="display:none;">
                </tr>
                <tr>
                  <td  class="gwc_js_table"></td>
                  <td class="gwc_js_table_w"><span class="gwc_dd">您需要支付：</span></td>
                  <td class="gwc_js_table_pay">
                    <span class="gwc_dd">
                    <#list activityMap?keys as itemKey>
                      <#assign map_activity = activityMap[itemKey]>
                      <#list map_activity?keys as key_activity>
                        <#if (key_activity !="_no_activity")>
                          <#assign map_info = map_activity[key_activity]>
                          <b class="pay_jg_2 ">￥</b><i class="pay_jg_3 " id="p_price">${map_info.ActivityAmont.RealAmount}</i>
                        <#else>
                          <b class="pay_jg_2 ">￥</b><i class="pay_jg_3 " id="p_price">${callBackAmount}</i>
                        </#if>
                      </#list>
                    </#list>
                    </span>
                      </td>
                </tr>
               </table>   
            
              <#if (sdorder.channelsn == "cps_dlr" || typeFlag =="03")!>
                <div class="balance_pay">
                  <h3 class="balance-tit">您可以选择以下支付方式：</h3>
                  <div class="balance_box">
                    <p class="balance_zfx">（请确保支付余额在支付限额以内，您可以到银行修改您的支付限额）</p>
                    <p>您当前的余额为：${balance}元，需要
                        <#list activityMap?keys as itemKey>
                          <#assign map_activity = activityMap[itemKey]>
                          <#list map_activity?keys as key_activity>
                            <#if (key_activity !="_no_activity")>
                              <#assign map_info = map_activity[key_activity]>
                              ${map_info.ActivityAmont.RealAmount}
                            <#else>
                              ${callBackAmount}
                            </#if>
                          </#list>
                        </#list>元才能完成该次支付。</p>
                    <p>您可以点击立即支付按钮完成支付</p>
                    <p><b>支付密码：</b><input type="password" class="balance_input" name="paypassword" id="paypassword"><span><input type="button" class="balance_btsf" value="确认支付" onclick="dosubmit('yezf', '')"></span></p>
                  </div>
                </div>
              </#if>
              <input type = "hidden" id="yhj_orderId" value="${sdorder.orderSn}"/>
              <input type = "hidden" id="loginFlag" value="${loginFlag}" name="loginFlag"/>
              <input type = "hidden" id="shopcartflag" value="0"/>
              <input type = "hidden" id="jf_zero" value="true"/>
              <input type = "hidden" id="memberpoint" value="0"/>
              <input type = "hidden" id="PointScalerUnit" value="10"/>
              <#if (fkIsShow =="Y")!>
                <div class="fx_fkid">
                 飞客茶馆用户名<input type="text" id="fkID" name="fkID" maxlength="32" class="fx_fk_txt"> (飞客茶馆用户可返“飞米”，用户兑换礼品，航空公司里程，酒店积分等)
                  </div>
                <#else>
                </#if>
              <#if (loginFlag =="true" && typeFlag !="02" && typeFlag !="03")!>
                   <div id="youhui_div" class="yhj_con">
               <#else>
                   <div style="display: none" id="youhui_div" class="yhj_con">
               </#if>
                  <h3 class="pay_make_yh" id="pay_yhj_gl">使用优惠券抵消部分总额</h3>
                  <div class="pay_quan" style="display: none" id="pay_yhj_box">
                    <span class="font-hscs">请选择使用的优惠券</span>
                    <ul class="yhj-des-link">
                      <li class="yhj-deslink1">如何得到优惠券?<span class="yhj_jts" style="display:none;">关注开心保微信公众号参与互动，或通过网站活动页面领取即可获得优惠券，优惠券可在会员中心查看</span></li>
                      <li class="yhj-deslink2">优惠券如何使用?<span class="yhj_jts" style="display:none;">输入优惠券码或勾选优惠券即可使用优惠券</span></li>
                    </ul>
                    <div class="pre_yh_txt" >
                      <ul class="pre_yh_li cf">
                        <li class="pre_yh_li1">优惠券代码</li>
                        <li class="pre_yh_li2"><input type="text" class="jhm_texts" id="jhm_text"/></li>
                        <li class="pre_yh_li3"> <input type="button"  class="pay_jf_btn" id="yhj_jh_button" value="使用"></li>
                      </ul>
                    </div>
                    <div id="yhj_list" class="pay_jf_tdjn" style="display:none">
                      <img src="${staticPath}/images/loading2.gif" alt="" />
                    </div>
                  </div>
                  <h3 class="pay_make_yh" id="pay_jf_gl">使用积分抵消部分总额</h3>
                  <div class="clear"></div>
                  <div class="pay_jifen" style="display: none" id="pay_jf_box">
                    <table class="jf_table">
                      <tr id="jf_sy" >
                        <td class="pay_jf_td pay_jf_td1">本次使用</td>
                        <td class="pay_jf_td pay_jf_td2"><input type="text"  class="pay_jf_txt" name="point_dz"/></td>
                        <td class="pay_jf_td pay_jf_td3">积分支付</td>
                        <td class="pay_jf_td pay_jf_td4" id="jf_sy_qx_botton"><input type="button" value="使用"  id="sy_botton" class="pay_jf_btn" onclick="jfsy()"/></td>
                        <td class="pay_jf_td pay_jf_td5" id="jfsy_span"></td>
                      </tr>
                      <tr id="jf_qx" style="display:none;">
                        <td class="pay_jf_td pay_jf_td1">本次使用</td>
                        <td class="pay_jf_td pay_jf_td2"><input type="text" class="pay_jf_txt" name="point_dz" readonly="readonly"/></td>
                        <td class="pay_jf_td pay_jf_td3">积分支付</td>
                        <td class="pay_jf_td pay_jf_td4"><input type="button" value="取消" class="pay_jf_btn pay_quxiao" onclick="jfqx()" /></td>
                        <td class="pay_jf_td pay_jf_td5" id="jfqx_span"></td>
                      </tr>
                    </table>
                    <div class="pay_jf_td6">您有<span id="member_jf_one"></span>积分，本次最多可使用<span id="member_jf_two" ></span>积分</div>
                  </div>
                </div>
              </div>
              <!--优惠券和活动结束-->
               </#if>   
              <div id="jfzf_botton" class="jf_zhifu" style="display:none;"><input type="button" class="jf_zf_btn" onclick="dosubmit('zerozf', '')"/></div>
          </#if>  
            </div>
     				<div class = "clear"></div>
			    </div>
   			    <div class="clear"></div>
 		    

            <div class="line_a shop_sptitle ">
                <div class="pay_box">
                    <div class="ins-tit un_bot_bor">银行卡支付<span class="tip"><em class="icon_e"></em>持卡人支付信息必须与投保人一致，否则无法完成支付</span><a id="backedit" href="${base}/shop/order_config_new!buyNowUpdate.action?orderId=${sdorder.id}&productId=${sdinformation.productId}&orderSn=${sdorder.orderSn}&orderFlag=${orderFlag}&KID=${KID}&order_healthySn=${sdorder.orderSn}">返回修改</a></div>
                    <div class="pay_cont">
                        <ul class="pay_list" id="bankinfo">
                            <li>
                                <div class="item"><span>持卡人姓名：</span></div>
                                <div class="text"><span>${applicantName}</span></div>
                                <input type="hidden" name="accountName" value="${applicantName}"/>
                            </li>
                            <li>
                                <div class="item"><span>投保人卡号：</span></div>
                                <div class="text"><span>${directPayBankInfo.bankName} ${directPayBankInfo.bankNo}</span></div>
                                <input type="hidden" name="accountNo" value="${directPayBankInfo.bankNo}"/>
                                <input type="hidden" name="bankCode" value="${directPayBankInfo.bankCode}"/>
                            </li>
                            <li>
                                <div class="item"><span>支付验证：</span></div>
                                <div class="text"><span>${applicantMobile}</span></div>
                                <input type="hidden" name="mobile" value="${applicantMobile}"/>
                            </li>
                            <li>
                                <div class="item"><span>请输入验证码：</span></div>
                                <div class="text">
                                    <p class="code_wrap"><input type="text" name="authCode" /><span class="code_again">发送验证码</span></p>
                                </div>
                            </li>
                            <li style="display:none;">
                              <input type="hidden" name="insureCode" value="${sdinformation.insuranceCompany}"/>
                              <input type="hidden" name="idType" value="${applicantIdentityType}"/>
                              <input type="hidden" name="idNo" value="${applicantIdentityid}"/>
                              <input type="hidden" id="payAmount" value="${map_info.ProductInfo[0].Amount}"/>
                            </li>
                        </ul>
                        <p class="chk_label">
                            <label for="accept_p"><input id="accept_p" type="checkbox" /><em class="icon_b"></em>同意扣款授权</label>
                            <a href="javascript:void(0);" class="check" id="showStatement" style="color:#fb5759; text-decoration: underline;">查看授权</a>
                        </p>
                        <p class="btn_pay"><a id="submitBtn" href="#">确认支付</a></p>
                        <div id="msg_2" style="display:none" class="londing_mes londing_mes_nolog"><img src="${staticPath}/images/nloading.gif" width="20px" height="20px" alt="" /><span id="msg_2_2">正在处理，请稍等</span></div>
                    </div>
                    <div class = "clear"></div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <!-- 公共底部 -->
    <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
    <!-- 公共底部 -->
<script type="text/javascript" src="${shopStaticPath}/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.cookie.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.form.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_base.js"></script>
<script type="text/javascript" src="${shopStaticPath}/artDialog.js"></script>
<script type="text/javascript" src="${shopStaticPath}/login.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_header.js"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/re_footer.js"></script>
<script type="text/javascript" src="${shopStaticPath}/mod_plan.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/pay.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>
<script type="text/javascript" src="${shopStaticPath}/wj_kxb/mod_allinpay.js"></script>
<!-- 小能在线系统star -->
<script type="text/javascript" src="${shopStaticPath}/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script>
<script type="text/javascript" src="${shopStaticPath}/redesign/xiaoneng_CustomerService.js"></script>
<!-- 小能在线系统 end -->

<section id="clm_text" style="display:none;width:700px; padding: 0 25px;">
  <ul>
    <li><span>授权人证件类型：</span>${applicantIdentityTypeName}</li>
    <li><span>授权人证件号码：</span>${applicantIdentityid}</li>
    <li><span>授权日期：</span>${.now?date}</li>
  </ul>
  <p>被授权人：网金保险销售服务有限公司（以下简称“网金保险”）就授权人同意网金保险扣款的相关事宜，授权如下：</p>
  <p>一、授权人授权网金保险根据授权人的扣款指令从本授权书第二条所述的授权人的银行账户通过银行或网金保险指定的第三方支付机构（以下统称为“第三方机构”）主动扣收本授权书第二条所述的等值于扣款金额的款项，用于授权人向网金保险账户履行支付义务（“扣款服务”）。</p>
  <p>二、授权人的银行账户及扣款金额如下：</p>
  <ul>
    <li><span>户名：</span>${applicantName}</li>
    <li><span>账号：</span>${directPayBankInfo.bankNo}</li>
    <li><span>开户银行：</span>${directPayBankInfo.bankName}</li>
    <li><span>扣款金额：</span>以实际为准（含第三方机构需收的手续费（如有））</li>
  </ul>
  <p>三、授权人知晓并确认，本授权书系使用授权人在网金保险网站用户（开心保）、以网络在线点击确认的方式向网金保险发出。本授权书自授权人在网金保险网站点击确认时生效，由网金保险通过银行或第三方机构从授权人的银行账户中操作执行相当于扣款金额的款项。授权人已经通过本授权书确认扣款项的银行账户信息，在扣款的过程中，网金保险根据本授权书提供的银行账户信息进行相关操作，无需再向授权人确认银行账户信息和密码。授权人确认并承诺，网金保险根据本授权书所采取的全部行动和措施的法律后果均由授权人承担。</p>
  <p>四、授权人知晓并同意,受授权人银行账户状态、银行、第三方支付机构及网络等原因所限, 本授权书项下扣款可能会通过多次扣款交易方可完成,网金保险不对扣款服务的资金到账时间做任何承诺。网金保险、银行或第三方机构仅根据本授权书所述的授权人的授权进行相关操作, 网金保险、银行或第三方机构无义务对其根据本授权书所采取的全部行动和措施的时效性和结果承担任何责任。</p>
  <p>五、本授权委托书有效期限为一年。</p>
  <p>特此授权。</p>
</section>

</body>
</html>
