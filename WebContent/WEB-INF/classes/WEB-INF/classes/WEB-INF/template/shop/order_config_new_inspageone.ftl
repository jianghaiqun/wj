<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] /> 
<!DOCTYPE html >
<!--<%@ taglib prefix="s" uri="/struts-tags" %>-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>填写投保信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/uploadify.css"/>
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>

<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>
<script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>
<script type="text/javascript" src="${shopStaticPath}/VerifyInput.js"></script>
<script type="text/javascript" src="${shopStaticPath}/Common.js"></script>
<script type="text/javascript">
   window.history.forward(1);
</script>
<script language="javascript" type="text/javascript">
function loadRelation(){
 	 if("02"=="${typeFlag}"){
	      sethash();
	 }
	 /* zhangjinquan 11180 2012-10-11修改对保险期限的处理，以保证暂存后能够正确带出曾经保存的数据 */
	 var protectionPeriodFlag = document.getElementById("protectionPeriodFlag").value;//保障期限是否存在
	 if(("true"==protectionPeriodFlag) && ("" == "${(sdinformation.ensureLimit)!}")){
			 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		 var d = new Date();
		 d.setDate(d.getDate()+${startPeriod});
		 var eff_value=document.getElementById("effective").value
		 var ndate
		 if(eff_value==""||eff_value==null){
		 	ndate= d.getFullYear()+"-"+add_zero(d.getMonth()+1)+"-"+add_zero(d.getDate());
		 }else{
		 	ndate=eff_value;
		 }
		 //document.getElementById("effective").value = ndate;
		 if(protectionPeriodTy != "A") {
			 var temp = addDate(protectionPeriodTy,protectionPeriodLast, ndate);
			 document.getElementById("fail").value = temp;
			 document.getElementById("fail_").value = temp;
		 }
	 }
	 changeInformation("","");
	//产品次日生效的处理逻辑
	var effectiveNextDayFlag = document.getElementById("effectiveNextDayFlag").value;//保障期限是否存在
	if("true"==effectiveNextDayFlag){
		 var protectionPeriodLast = document.getElementById("protectionPeriodLast").value;//保障期限后段
		 var protectionPeriodTy = document.getElementById("protectionPeriodTy").value;//保障期限类型Y,M,D
		var ndate=document.getElementById("effective").value;
		if(protectionPeriodTy != "A") {
			var temp = addDate(protectionPeriodTy,protectionPeriodLast, ndate);
			document.getElementById("fail").value = temp;
			document.getElementById("fail_").value = temp;
		}
		var start = jQuery('#effective').val();
		var end = jQuery('#fail').val();
		if(end =="终身"){
			jQuery('#ensureDate').html(start+" 00时  至  "+end);
			jQuery('#ensureDate_em').html("( 从"+start+" 00时起到  "+end+"  )");
		}else{
		
			jQuery('#ensureDate').html(start+" 00时  至  "+end+" 24时");
			jQuery('#ensureDate_em').html("( 从"+start+" 00时起到"+end+" 24时为止 )");
		}
	}
	
}

function addValidation(){
}
function cancelVerification(){
}
var status = "${status}";
var updateOrderId = "${sdorder.id}";
var productId  ="${productId}"
var KID = "${KID}";
var companyCode = "${sdinformation.insuranceCompany}";
var typeFlag = "${typeFlag}";
var p_url = "${jrhsURL}";
var effdatedate = "${sdinformation.startDate}";
var orderSn = "${sdorder.orderSn}";
var productExcelFlag = "${(productExcelFlag)!}";
var loginFlag = "${loginFlag}";
var appStartDate = "${appStartDate}";
var appEndDate = "${appEndDate}";
var insStartDate = "${insStartDate}";
var insEndDate = "${insEndDate}";
var insuredLenFlag = false;
var risktype = "${sdinformation.riskType}";
var pointExchangeFlag = "${pointExchangeFlag}";
var memberid = "${sdorder.memberId}";
var memberActivity = "${memberActivity}";
<#if recognizeeList? has_content>
    insuredLenFlag = true;
</#if>
var premProducts = ['101401014','101401024','103601005','103601006','103601007','103601008'];
var OccupLevel = "${OccupLevel}";
var needShowOccupLevel = "${needShowOccupLevel}";
var needShowOccup = "${needShowOccup}";
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body onLoad="loadRelation();" class="up-bg-qh">
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if> 
	<div class="wrapper">
		<input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
		<#if (shopcarflag =="false")!>
		<div class="re-line-log">
            <ul class="re-line-ul">
            	<li class="re-line-s"><div class="re-linehead re-linehead1"><span></span></div><h3 class="re-line-h3">1、填写投保信息<p>我们对您填写的信息严格保密</p></h3><span class="re-line-jiao"></span></li>
            	<li><div class="re-linehead re-linehead2"><span></span></div><h3 class="re-line-h3">2、确认保单<p>确认信息填写是否正确无误</p></h3><span class="re-line-jiao"></span></li>
            	<li><div class="re-linehead re-linehead3"><span></span></div><h3 class="re-line-h3">3、在线支付<p>
            	<#if (pointExchangeFlag == "1")>积分全额兑换<#else>积分折扣、支持多种支付方式</#if></p></h3><span class="re-line-jiao"></span></li>
            </ul>
      	</div>
      	<#else>
      	<div class="re-line-log">
            <ul class="re-line-ul">
            	<li class="re-line-s"><div class="re-linehead re-linehead1"><span></span></div><h3 class="re-line-h3">1、填写投保信息<p>我们对您填写的信息严格保密</p></h3><span class="re-line-jiao"></span></li>
            	<li class="re-line-d"><div class="re-linehead re-linehead4"><span></span></div><h3 class="re-line-h3">2、查看购物车<p>加入购物车，多订单一起付款</p></h3><span class="re-line-jiao"></span></li>
            	<li><div class="re-linehead re-linehead3"><span></span></div><h3 class="re-line-h3">3、在线支付<p>
            	<#if (pointExchangeFlag == "1")>积分全额兑换<#else>积分折扣、支持多种支付方式</#if></p></h3><span class="re-line-jiao"></span></li>
            </ul>
      	</div>
      	
      	</#if>
		<div class="clear"></div>
 		<form id="orderInfoForm"  action="" method="post" enctype ="multipart/form-data"> 
   		
		<div class="clearfix">
                 <#assign index = 1 />
                 <#assign moduleType = '' />
	    	     <#list pageModuleList as abc>
	    	        <#include "${abc.moduleURL}">    
	    	        ${abc.ModuleType}
	    	        <#if (index == 1) >
	    	        	<#assign moduleType = abc.ModuleType />
	    	        	<div class="line_a">
	    	        <#elseif moduleType != abc.ModuleType >
	    	      		</div>
	    	        	<div class="line_a">
	    	        	<#assign moduleType = abc.ModuleType />
	    	        	
	    	        </#if>
	    	        <#assign index=index+1 />
	       		 </#list> 
   		 </div>
	     	
	    <div class="line_a clear_mar">
			  <div class="line_at">合计信息</div>
				<div class="shop_mes_all">
			    <table  border="0" class="cp_pricebox">
					  <tr>
					    <th>起止日期</th>
					    <th>被保人人数</th>
					    <th>购买份数</th>
					    <#if (pointExchangeFlag == "1") || (activityFlag == "0")>
					    <th>费用合计</th>
					    <#else>
					    <th>原始费用合计</th>
              			<th>优惠后价格</th>
					    </#if>
					  </tr>
					  <tr>
					    <td id="ensureDate">
					    <#if  (sdinformation.startDate)??>
					    从&nbsp;&nbsp;&nbsp;${sdinformation.startDate}&nbsp;&nbsp;&nbsp;00时起到&nbsp;&nbsp;&nbsp;<#if (sdinformation.endDate=="9999-12-31")|| (sdinformation.endDate=="终身")>&nbsp;&nbsp;&nbsp;终身<#else>${sdinformation.endDate}&nbsp;&nbsp;&nbsp;24时为止 </#if>
				        </#if>
					    </td>
					    <td><font id="insNum"><#if (mulInsuredFlag=="rid_td")>${insuredImpCount}<#elseif (mulInsuredFlag=="rid_me")>1<#else>${insuredCount}</#if></font></td>
					    <td id="insMult"><#if (mulInsuredFlag=="rid_td")>${insuredImpCount}<#elseif (mulInsuredFlag=="rid_me")>${insuredMulCount}<#else>${insuredActCount}</#if>份</td>
					    <#if (pointExchangeFlag == "1")>
					    	<td colspan="2"><font  class="up_font_s"><span id = "priceTotle" >${sdorder.offsetPoint}</span>积分</font></td>
					    <#elseif (activityFlag == "0")>
					    	<td colspan="2"><font  class="up_font_s">￥ <span id = "priceTotle" >${sdorder.payPrice}</span>
					    	</font><#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if></td>
					    <#elseif (activityFlag == "1")>
					    	<td ><font  class="up-font-money">￥ <span id = "priceTotle_1" >${sdorder.totalAmount}</span></font></td>
					    	<td><font  class="up-font-money2">￥ <span id = "priceTotle">${sdorder.payPrice}</span></font><#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if></td>
					    </#if>
					    
					  </tr>
					 <#if (isLcx !="0")>
					  <tr>
					     <#if (pointExchangeFlag == "1") || (typeFlag != null && typeFlag != "")>
					     	<td colspan="5" class="order-hd-list" id="inspageone_activityTD" style="display:none">
					     <#else>
					     	<td colspan="5" class="order-hd-list" id="inspageone_activityTD">
					     </#if>
			              
			              <#if (loginFlag =="true"&&map_pointinfo.pointsprice!="0"&&map_pointinfo.pointsprice!="0.0"&&map_pointinfo.pointsprice!="0.00")!>
							 <#if (map_pointinfo.points != null&& map_pointinfo.points != "") >
								<div class="integal_hjcon"><span  class="integral_hj">您用${map_pointinfo.points}积分可以进行抵扣费用¥${map_pointinfo.pointsprice}（继续支付可直抵）</span></div>
							 <#else>
								<div class="integal_hjcon" style="display:none;"><span  class="integral_hj"></span></div>
							 </#if>
						  <#else>
								<div class="integal_hjcon" style="display:none;"><span  class="integral_hj"></span></div>
						   </#if>
						   		<div class="at-desp">
				              	  <span class="at-des-p">继续支付满足条件可以享受以下优惠</span>
				                  <ul class="at_list">
							          <li><span class="tb_icon active_01">积分</span><span class="tb_text"><span id="result_sendPointsDesc">${memGradeDesc}</span><span id="result_sendPoints">${sendPoints}</span>个积分（可抵扣<span id="result_sendPointsValue">${sendPointsValue}</span>元）</span></li>
							          ${(activityInfo)!}
							      </ul>
						        </div>
			              </td>
            		  </tr>
            		 </#if>
			</table>
			</div>
			<div class="price_all" style="display:none">共
						<font id="insNum" color="red"><#if (mulInsuredFlag=="rid_td")>${insuredImpCount}<#elseif (mulInsuredFlag=="rid_me")>1<#else>${insuredCount}</#if></font>位被保人，保费
						<font  id = "priceTotle_1" color="red">${sdorder.productTotalPrice}</font>元，
						<font  id = "insMult_1" color="red"><#if (mulInsuredFlag=="rid_td")>${insuredImpCount}<#elseif (mulInsuredFlag=="rid_me")>${insuredMulCount}<#else>${insuredActCount}</#if></font>份保单，合计	<span >	
						<font  color="red">￥ <span id = "priceTotle" >${sdorder.payPrice}</span></font> </span>元
						<span id = "discountPrice" style="display:none;">￥ ${sdorder.discountAmount}</span>
			</div>
			 <input type="hidden" id="productTotalPrice" name="sdorder.productTotalPrice" value="${(sdorder.productTotalPrice)!}"/>
			 <input type="hidden" id="order_healthySn" name="order_healthySn" value="${(order_healthySn)!}"/>
			 <input type="hidden" id="channelsn" name="sdorder.channelsn" value="${(sdorder.channelsn)!}"/>
			 <input type="hidden" id="totalAmount" name="sdorder.totalAmount" value="${(sdorder.totalAmount)!}"/>
			 <input type="hidden" id="payPrice" name="sdorder.payPrice" value="${(sdorder.payPrice)!}"/>
			 <input type="hidden" id="discountAmount" name="sdorder.discountAmount" value="${(sdorder.discountAmount)!}"/> 
			 <input type="hidden" id="recognizeeOperate" name="recognizeeOperate" value="${recognizeeOperate?default('0')}"/>
			  <input type="hidden" id="orderStatus" name="sdorder.orderStatus" value="${(orderStatus)!}"/>
			 <input type="hidden" id="KID" name="KID" value="${KID}"/>
			 <input type="hidden" id="typeFlag" name="typeFlag" value="${typeFlag}"/>
			  <input type="hidden" id="channelCode" name="channelCode" value="${channelCode}"/>
			 <input type="hidden" id="userCode" name="UserCode" value="${userCode}"/>
			 <input type="hidden" id="textAge" name="textAge" value="${textAge}"/>
			 <input type="hidden" id="impValadate" name="impValadate" value="${impValadate}"/>
			 <input type="hidden" id="effectiveNextDayFlag" name="effectiveNextDayFlag" value="${effectiveNextDayFlag}"/>
			 <input type="hidden" id="effectiveNextDayValue" name="effectiveNextDayValue" value="${effectiveNextDayValue}"/>
			 <input type="hidden" id="orderFlag" name="orderFlag" value="${orderFlag}"/>
			 <input type="hidden" id="sourceFlag" name="sourceFlag" value="${sourceFlag}"/>
			 <input type="hidden" id="excleTempEnName" name="excleTempEnName" value="${excleTempEnName}"/>
			 <input type="hidden" id="dutyTempSerials" name="dutyTempSerials" value="${dutyTempSerials}"/>
			 <input type="hidden" id="complicatedFlag" name="complicatedFlag" value="${complicatedFlag}"/>
			 <input type="hidden" id="pointExchangeFlag" name="pointExchangeFlag" value="${pointExchangeFlag}"/>
			 <input type="hidden" id="offsetPoint" name="sdorder.offsetPoint" value="${(sdorder.offsetPoint)!}"/>
			 <input type="hidden" id="PointScalerUnit" name="PointScalerUnit" value="${PointScalerUnit}"/>
			 <input type="hidden" id="specialEffDateFlag" name="specialEffDateFlag" value="${specialEffDateFlag}"/>
			 <input type="hidden" id="isLcx" name="isLcx" value="${isLcx}"/>
			 <input type="hidden" id="UnderwritingOfflineCode" name="UnderwritingOfflineCode" value="${UnderwritingOfflineCode}"/>
			 
			<#if (shopcarflag =="false")!>
			 	<#if (loginFlag =="true")!>
					<div class="syr2"> 
						 <input type="button" id="confirm" value="下一步" class="next_btn dev_submitButton" style="height=20px;width=100px;" >
						 <span id="comfirmToPay"  onclick="temptorysave();" class="zc_btn" style="display:">暂存</span><em class="zc-textdes">(暂存保单，下次购买免填单)</em>
				<#else>
					<div class="syr2 syr33"> 
						 <input type="button" id="confirm" value="下一步" class="next_btn dev_submitButton" style="height=20px;width=100px;" >
						 <span id="comfirmToPay"  onclick="temptorysave();" class="zc_btn" style="display:none">暂存</span><em class="zc-textdes" style="display:none">(暂存保单，下次购买免填单)</em>
						 </#if> 
			<#else>
					<div class="syr2 syr33"> 
						<input type="button" id="confirm" value="保存" class="next_btn dev_submitButton" style="height=20px;width=100px;" ><br />
			</#if>
 					<font id="order_err" style="displan:none" class="order-error" color="red"></font>
 			<div class="clear"></div>
 			<div id="msg_1" 
 			 <#if (loginFlag =="true")!>
 			 class="londing_mes londing_meslog"
 			 <#else>
 			 class="londing_mes"
 			 </#if>
 			 style="display:none"><img src="${staticPath}/images/nloading.gif"  width="20px" height="20px" alt="" />正在缓存，请稍等</div>
 			<div class="clear"></div>
 			</div>
 			</div></div></div>
   </form>
   </div>
			<script type="text/javascript">
			if ((document.getElementById("showOutGoingParpose") != null) && (typeof(document.getElementById("showOutGoingParpose")) != "undefined"))
			{
				document.getElementById("showOutGoingParpose").innerHTML = "${destinationCountryStr}";
				document.getElementById("checkOutPice").value = "${destinationCountryStr}";
			}
			
			</script>
 </div>
<#if (typeFlag !="02")!>
<!-- 底部开始 -->
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!-- 底部结束 -->
</#if>
<!-- 百分点发送逻辑 begin -->
<script type="text/javascript" src="${shopStaticPath}/min-jquery.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.cookie.js"></script>
<script type="text/javascript">
/* //百分点合作结束，百分点核心代码暂存
try{
	window["_BFD"] = window["_BFD"] || {};
	_BFD.BFD_INFO = { 
		"user_id" : jQuery.cookie("loginMemberId")==null?"":jQuery.cookie("loginMemberId"), //网站当前用户id，如果未登录就为0或空字符串
		"page_type" : "others" //当前页面全称，请勿修改
	};
}catch(e){
	
}
*/
</script>
<!-- 百分点发送逻辑 end -->
<#include "/wwwroot/kxb/block/community_v1.shtml">
<script	type="text/javascript" src="${shopStaticPath}/iframeTools.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/json2.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqDnR.js"></script>
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.jqModal.js"></script>
<script type="text/javascript" src="http://localhost:8080/js/shop/js/shop.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>
<script type="text/javascript" src="${shopStaticPath}/swfobject.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.uploadify.js"></script>

 <script type="text/javascript">
 if ((document.getElementById("showOutGoingParpose") != null) && (typeof(document.getElementById("showOutGoingParpose")) != "undefined"))
 {
 	document.getElementById("showOutGoingParpose").innerHTML = "${countryText}";
 	document.getElementById("checkOutPice").value = "${countryText}";
 }
 
 var insuredCount = "${insuredCount}"; 
 var insFlag = jQuery("#mulInsuredFlag").val();
 //pubGetChildrenOccupation("${sdinformationAppnt.applicantOccupation1}","${sdinformationAppnt.applicantOccupation3}");
 pubGetChildrenArea("${sdinformationAppnt.applicantArea1}", "${sdinformationAppnt.applicantArea2}", "${sdinformationAppnt.applicantArea3}","App","");
 pubGetChildrenOccupation("${sdinformationAppnt.applicantOccupation1}","${sdinformationAppnt.applicantOccupation2}","${sdinformationAppnt.applicantOccupation3}","App","");
 
  var bankFlag = jQuery("#bankinforFlag").val();
  if(bankFlag && bankFlag == "Y" ){
  	pubGetChildrenArea("${directPayBankInfo.bankProvince}", "${directPayBankInfo.bankCity}", "","Bank","");
  }
 
var benefitOperateVal = $('input[name="benefitOperate"]:checked ').val();
if(benefitOperateVal == "1"){
	  <#assign index = 1>
		  <#list sdinformationbnfList as tList>  
				 pubGetChildrenArea("${tList.bnfArea1}", "${tList.bnfArea2}","","Bnf","${index}"); 
				 pubGetChildrenOccupation("${tList.bnfOccupation1}","${tList.bnfOccupation2}","${tList.bnfOccupation3}","Bnf","${index}");
				<#assign index = index+1>
	 </#list>
 }

 
 if(insFlag==null||insFlag=="undefined"){
	  <#assign index = 1>
	  <#list sdinformationinsuredList as tList>  
			pubGetChildrenArea("${tList.recognizeeArea1}", "${tList.recognizeeArea2}","${tList.recognizeeArea3}","Ins","${index}"); 
			pubGetChildrenArea("${tList.recognizeeOrigin1}", "${tList.recognizeeOrigin2}","${tList.recognizeeOrigin3}","Origin","${index}"); 
			pubGetChildrenArea("${tList.recognizeeDestination1}", "${tList.recognizeeDestination2}","${tList.recognizeeDestination3}","Destination","${index}"); 
			pubGetChildrenOccupation("${tList.recognizeeOccupation1}","${tList.recognizeeOccupation2}","${tList.recognizeeOccupation3}","Ins","${index}");
			<#assign index = index+1>
	  </#list>
	  <#assign index1 = 1>
	  <#list sdinformationpropertyList as tList>  
			pubGetChildrenArea("${tList.propertyArea1}", "${tList.propertyArea2}","${tList.propertyArea3}","Property","${index1}");
			<#assign index1 = index1+1>	
	 </#list>
 }else if(insFlag=="rid_me"){
		pubGetChildrenArea("${sdinformationinsured.recognizeeArea1}", "${sdinformationinsured.recognizeeArea2}","${sdinformationinsured.recognizeeArea3}","Ins","");
		pubGetChildrenArea("${sdinformationinsured.recognizeeOrigin1}", "${sdinformationinsured.recognizeeOrigin2}","${sdinformationinsured.recognizeeOrigin3}","Origin","");
		pubGetChildrenArea("${sdinformationinsured.recognizeeDestination1}", "${sdinformationinsured.recognizeeDestination2}","${sdinformationinsured.recognizeeDestination3}","Destination",""); 
		pubGetChildrenDestination("${sdinformationinsured.remark}", "${sdinformationinsured.destinationCountry}","${sdinformationinsured.destinationCountryText}","Ins","");
		pubGetChildrenOccupation("${sdinformationinsured.recognizeeOccupation1}","${sdinformationinsured.recognizeeOccupation2}","${sdinformationinsured.recognizeeOccupation3}","Ins","");
 }else{
	 <#assign index = 1>
	  <#list sdinformationinsuredList as tList> 
			pubGetChildrenArea("${tList.recognizeeArea1}", "${tList.recognizeeArea2}","${tList.recognizeeArea3}","Ins","${index}");
			pubGetChildrenArea("${tList.recognizeeOrigin1}", "${tList.recognizeeOrigin2}","${tList.recognizeeOrigin3}","Origin","${index}");
			pubGetChildrenArea("${tList.recognizeeDestination1}", "${tList.recognizeeDestination2}","${tList.recognizeeDestination3}","Destination","${index}"); 
			pubGetChildrenDestination("${tList.remark}", "${tList.destinationCountry}","${tList.destinationCountryText}","Ins","${index}");
			pubGetChildrenOccupation("${tList.recognizeeOccupation1}","${tList.recognizeeOccupation2}","${tList.recognizeeOccupation3}","Ins","${index}");
			<#assign index = index+1>
	  </#list>
	  <#assign index1 = 1>
	  <#list sdinformationpropertyList as tList>  
			pubGetChildrenArea("${tList.propertyArea1}", "${tList.propertyArea2}","${tList.applicantArea3}","Property","${index1}");
			<#assign index1 = index1+1>
	  </#list>
 }
 initPrem();
 if(loginFlag && orderSn == ""&&pointExchangeFlag!='1'){
 	ajaxAlreadySave(memberid);
 }
 // 改变窗口大小，邮箱提示框位置不变
 jQuery(window).resize(function(){ 
      var top = jQuery('#applicantMail').offset().top + jQuery('#applicantMail').height() + 6;
      var left = jQuery('#applicantMail').offset().left;
      jQuery("#mailBox").css({top:top,left:left});
});

function checkUserName() {
    var userName = jQuery("#shoploginname").val();
    var myReg = /^[-_A-Za-z0-9\.]+@([-_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,4}$/; //验证邮箱的正则
    var regu = /^[1][3-8][0-9]{9}$/;
    if (userName!=null&&userName!=""&&!myReg.test(userName) && !regu.test(userName)) {
        jQuery("#shoperror").html("正确的邮箱或手机号是登录的唯一凭证哟");
        return false;
    }

    return true;
}
//查询注册错误次数
logqueryRemarkError();
jQuery("#shoppass").keyup(function(){
 var s = jQuery(this).val().length;
 if(s>=16){
	 jQuery("#shoperror").html("您的密码长度已经达到上限了哦~");
 }else{
	 jQuery("#shoperror").html("");
 }
})
jQuery("#shoppass").focus(
function(s) {
	jQuery("#shoperror").html("");
    var regName = jQuery("#shoploginname").val();
    if(regName!=null&&regName!=""){
         if(!checkUserName()) return false;
	     jQuery.ajax({
	        url:sinosoft.base+"/shop/member!checkRegisterName.action?regName=" + regName,
	        type: "post",
	        dataType: "json",
	        success: function(data) {
	            if (data.status == "true") {
	                jQuery("#shoppass").val("");
	                if (document.getElementById('logveriCode').style.display!="") {
		                if (data.remarkFlag == "true") {
					          document.getElementById('logveriCode').style.display="";
					          loginCaptchaImageRefresh();
					            
		                }else{
					          document.getElementById('logveriCode').style.display="none";
					    }
				    }
	            }
	        }
	    });
    
    }
   
});

//根据产品是否有职业选项和是否是特殊产品初始化职业类别
if(jQuery("#lookOccuDiv").length > 0 && OccupLevel != null && OccupLevel.length > 0 && needShowOccupLevel == "1"){
	jQuery("#lookOccuDiv").show();
	//职业选项特殊产品职业类别显示(三级职业选项显隐，特殊产品显示，不是特殊产品不显示)
	if (jQuery(".br_box").find("p").length > 0 && needShowOccupLevel == "1" && needShowOccup != "1") {//标识的四家保险公司含有特殊配置产品
		jQuery(".br_box").find("p").find("span:contains('职业')").parent().remove();
	}else if(needShowOccup == "1"){
		jQuery(".br_box").find("p").find("span:contains('职业')").parent().show();
		jQuery("#lookOccuDiv").hide();
	}

}
 //保险期限为至XX岁时的产品返回修改后设置保单终止日期
 if (document.getElementById("Ensure") && 
 	 document.getElementById("Ensure").value.indexOf("A") != -1) {
 	 var recognizeeBirthday_0 = jQuery("#recognizeeBirthday_0").val();
     if(recognizeeBirthday_0 !=null && recognizeeBirthday_0 != ""){
     	changeEndDateForToAge(recognizeeBirthday_0, document.getElementById("effective").value);
     }
 }	
 
var productIntegralValue='${sendPoints}';
 whenIntegralZero(productIntegralValue);
     if(jQuery("#zhm_name") && jQuery("#applicantName").val()!=""){
	       jQuery("#zhm_name").html(jQuery("#applicantName").val());
	    }
	</script>
	

</body>
</html>