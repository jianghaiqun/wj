 <!DOCTYPE html > 
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单结果</title>
<meta name="Author" content="SINOSOFT Team" />  
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<head>
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css"/>

<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/> 
  

<script language="javascript" type="text/javascript">
var orderSn = '${sdorder.orderSn}';
var needUWCheckFlag = '${needUWCheckFlag}';
var orderId = "${sdorder.id}";
var productId = '${sdinformation.productId}';
var p_url = "${jrhsURL}";
var productExcelFlag = "${(productExcelFlag)!}";
var companyCode = "${sdinformation.insuranceCompany}";
var effdatedate = "${sdinformation.startDate}";
var loginFlag = "${loginFlag}";
var resultKID = "${KID}";
</script>
<script type="text/javascript" src="${shopStaticPath}/iframe.js"></script>
<script language="javascript" type="text/javascript">
function iframeH(){
if("02"=="${typeFlag}"){
	      sethash();
	 }
}
if("Y"=="${isAllFree}"){
	window.history.forward(1);
}
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body onLoad="iframeH();" class="up-bg-qh">
<#if (typeFlag =="02")!>
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
</#if>
<#if (typeFlag !="02")!>
	<#if (typeFlag =="03")!>
	<iframe id="iframeA" name="iframeA" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" src="${CpsAgentURL}/front/include/agent_order_head.jsp" width="100%" height=100%></iframe> 
	<#else>
		<#if (typeFlag =="04")!>
		<iframe id="iframeA" name="iframeA" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" src="${CpsUnionURL}/includeV2/admin_order_head.jsp" width="100%" height=100%></iframe> 
		<#else>
		<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
		</#if> 
	</#if> 
</#if>

<div class="wrapper">
<div class="wrapper">
	<input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
	<input type="hidden" id="orderFlag" name="orderFlag" value="${orderFlag}"/>
	<#if (typeFlag !="03"&&typeFlag !="04")!>
		<#if (canPreviewOperatorFlag != "0") >
		   <div class="re-line-log">
	          <ul class="re-line-ul">
	            <li class="re-line-d"><div class="re-linehead re-linehead1"><span></span></div><h3 class="re-line-h3">1、填写投保信息<p>我们对您填写的信息严格保密</p></h3><span class="re-line-jiao"></span></li>
	            <li class="re-line-s"><div class="re-linehead re-linehead2"><span></span></div><h3 class="re-line-h3">2、确认保单<p>确认信息填写是否正确无误</p></h3><span class="re-line-jiao"></span></li>
	            <li><div class="re-linehead re-linehead3"><span></span></div><h3 class="re-line-h3">3、在线支付<p>
	            <#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")>积分全额兑换<#else>积分折扣、支持多种支付方式</#if></p></h3><span class="re-line-jiao"></span></li>
	          </ul>
	      	</div>
		<#else>
			<div class="clear h20"></div>
		</#if>
	</#if>

	<div class="clear"></div>
</div>
	<div class="w900 order-bg">
		<input type ="hidden" id="insuranceCompanySn" name="insuranceCompanySn" value="${sdinformation.insuranceCompanySn}">
		
		<#if (needUWCheckFlag=="1") >
		<form id ="resultForm" action="${base}/shop/order_config_new!tpyCheckPay.action" method="post"> 
		<#else>
		<form id ="resultForm" action="${base}/shop/order_config_new!pay.action" method="post"> 
		</#if>
		<input type ="hidden" id="orderSn" name="orderSn" value="${sdorder.orderSn}">
		<input type="hidden" id="order_healthySn" name="order_healthySn" value="${(sdorder.orderSn)!}"/>
		<div class="lay-ins-con pro-con-info">
			<span class="small_fr f_mi">订单号：${sdorder.orderSn}</span>
			<div class="ins-tit un_bot_bor">保单明细</div>
				<div class="bdmx_table_b">
				   <#if sdinformation.endDate!="" && sdinformation.endDate?string("yyyy-MM-dd")=="9999-12-31">
				       <table class="bdmx_table">
								<tr>
									<th class="bd_t_bg" width="340px;">投保计划</th>
									<th class="bd_t_bg">保险期间</th>
								</tr>
								<tr>
									<td>
								 	<#if  (sdinformation.planName)??>
								   		${sdinformation.productName}&nbsp;-&nbsp;${sdinformation.planName}
								 	<#else>
								 		${sdinformation.productName}
							     	</#if>
									</td>
									<td>承保(扣款)次日开始生效至终身</td>
								</tr>
						 	</table>
					<#else>
						<table class="bdmx_table">
							<tr>
								<th class="bd_t_bg" width="340px;">投保计划</th>
								<th class="bd_t_bg">保险期间</th>
								<th class="bd_t_bg">保单起保日期</th>
								<th class="bd_t_bg">保单终止日期</th>
							</tr>
							<tr>
								<td>
								 <#if  (sdinformation.planName)??>
								   ${sdinformation.productName}&nbsp;-&nbsp;${sdinformation.planName}
								 <#else>
								 ${sdinformation.productName}
							     </#if>
								</td>
								<td>${sdinformation.ensureDisplay}</td>
								<td>${sdinformation.startDate}</td>
								<td>${sdinformation.endDate}</td>
							</tr>
						</table>
					</#if>
			</div>
        </div>
		 
		<div class="lay-ins-con pro-con-info">
				<div class="ins-tit un_bot_bor">投保人信息</div>
				<table class="tbr_mes_table">
										<#list showAppnts as list>  
													<#assign appindexnum = 1 />
													    <#list list as i>
														       <#if (appindexnum%3==1)>
																   <tr>
														               <td class="bd_t_bg td_t_w">${i.showName}</td> 
																       <td class="tb_t_ws">${i.showValue}</td>
														       </#if> 
														       <#if (appindexnum%3==2)>
																		<td class="bd_t_bg td_t_w">${i.showName}</td> 
																		<td class="tb_t_ws">${i.showValue}</td>
															   </#if>
														       <#if (appindexnum%3==0)>
														               <td class="bd_t_bg td_t_w">${i.showName}</td> 
																       <td class="tb_t_ws">${i.showValue}</td>
															       </tr>
														       </#if> 
														       <#if (appindexnum==list?size)>
														       		<#if (appindexnum%3==1)>
														       			<td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        <td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        </tr>
														       		</#if>
														       		<#if (appindexnum%3==2) >
														       			<td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        </tr>
														       		</#if>
															       
														       </#if>
													       <#assign appindexnum=appindexnum+1 />
													    </#list> 
										</#list>
				</table>
		</div>
		
		
		
		<div class="lay-ins-con pro-con-info">
			<div class="ins-tit  ">被保险人信息<em class="ins-tit-b">（共${(insuredResultCount)!}人）</em></div>
							<#assign index = 1 />
										<#list showInsureds as list>  
										<#assign insuredindexnum = 1 />
										<div class="tbr-box">
										  <span class="tbr-box-num">${index}</span>
											<table class="tbr_mes_table">
													    <#list list as i>
														       <#if (insuredindexnum%3==1)>
																   <tr>
														               <td class="bd_t_bg td_t_w">${i.showName}</td> 
																       <td class="tb_t_ws">${i.showValue}</td>
														               
														       </#if>
														       <#if (insuredindexnum%3==2)>
																		<td class="bd_t_bg td_t_w">${i.showName}</td> 
																		<td class="tb_t_ws">${i.showValue}</td>
															   </#if>
														       <#if (insuredindexnum%3==0)>
														               <td class="bd_t_bg td_t_w">${i.showName}</td> 
																       <td class="tb_t_ws">${i.showValue}</td>
															       </tr>
														       </#if>
														       <#if (insuredindexnum==list?size)>
														       		<#if (insuredindexnum%3==1)>
														       			<td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        <td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        </tr>
														       		</#if>
														       		<#if (insuredindexnum%3==2)>
														       			<td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        </tr>
														       		</#if>
															       
														       </#if>
													       <#assign insuredindexnum=insuredindexnum+1 />
													    </#list> 
												 <#assign index=index+1 /> 
												 </table>
											</div>
										</#list>
		</div>
		<#if showPropertys? has_content>
		<div class="lay-ins-con pro-con-info">
		<div class="ins-tit un_bot_bor">财产信息</div>
		<table class="tbr_mes_table">
							<#assign pronum = 1 />
										<#list showPropertys as list>  
													    <#list list as i>
														       <#if (pronum%3==1)>
																   <tr>
														               <td class="bd_t_bg td_t_w">${i.showName}</td> 
																       <td class="tb_t_ws">${i.showValue}</td>
														       </#if>
														       <#if (pronum%3==2)>
																		<td class="bd_t_bg td_t_w">${i.showName}</td> 
																		<td class="tb_t_ws">${i.showValue}</td>
															   </#if>
														       <#if (pronum%3==0)>
														               <td class="bd_t_bg td_t_w">${i.showName}</td> 
																       <td class="tb_t_ws">${i.showValue}</td>
															       </tr>
														       </#if>
														       <#if (pronum==list?size)>
														       		<#if (pronum%3==1)>
														       			<td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        <td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        </tr>
														       		</#if>
														       		<#if (pronum%3==2)>
														       			<td class="bd_t_bg td_t_w"></td> 
																        <td class="tb_t_ws"></td>
																        </tr>
														       		</#if>
															       
														       </#if>
													       <#assign pronum=pronum+1 />
													    </#list> 
										</#list>
						</table>
	 	</div>
	 </#if>
<#if sdinformation.riskType!="11">  
	<div class="lay-ins-con pro-con-info">
		<div class="ins-tit  ">受益人信息<em class="ins-tit-b">（共${(sdInformationBnfCount)!}人）</em></div>
		<#if showBnfs? has_content>
			<#assign index_Bnf = 1 />
				<#list showBnfs as list>
					<div class="tbr-box">
					<span class="tbr-box-num">${index_Bnf}</span>
					<table class="tbr_mes_table">  
					<#assign bnfindexnum = 1 />
						<#list list as i>
					       <#if (bnfindexnum%3==1)>
							   <tr>
					               <td class="bd_t_bg td_t_w">${i.showName}</td> 
							       <td class="tb_t_ws">${i.showValue}</td>
					               
					       </#if>
					       <#if (bnfindexnum%3==2)>
									<td class="bd_t_bg td_t_w">${i.showName}</td> 
									<td class="tb_t_ws">${i.showValue}</td>
						   </#if>
					       <#if (bnfindexnum%3==0)>
					               <td class="bd_t_bg td_t_w">${i.showName}</td> 
							       <td class="tb_t_ws">${i.showValue}</td>
						       </tr>
					       </#if>
					       <#if (bnfindexnum==list?size)>
					       		<#if (bnfindexnum%3==1)>
					       			<td class="bd_t_bg td_t_w"></td> 
							        <td class="tb_t_ws"></td>
							        <td class="bd_t_bg td_t_w"></td> 
							        <td class="tb_t_ws"></td>
							        </tr>
					       		</#if>
					       		<#if (bnfindexnum%3==2)>
					       			<td class="bd_t_bg td_t_w"></td> 
							        <td class="tb_t_ws"></td>
							        </tr>
					       		</#if>
						       
					       </#if>
				       <#assign bnfindexnum=bnfindexnum+1 />
				    </#list>
					<#assign index_Bnf=index_Bnf+1 /> 
					</table>
				</#list>
			</div>
		<#else>
			<div class="box_sbg">
				<#if (selfBnfFlag=='Y')>
			    	<div class="fdsyr">被保险人本人</div>
			    <#else>
			    	<div class="fdsyr">法定受益人</div>
			    </#if>
		    </div>
		</#if>
	</div>
</#if>
<#if (directPayBankInfo ??) && (directPayBankInfo.bankCode ??) && (directPayBankInfo.bankCode !="" )&& (directPayBankInfo.prop5 != "N")>
<div class="lay-ins-con pro-con-info">
	<div class="ins-tit un_bot_bor">续期交费信息</div> 
	<table class="tbr_mes_table">
	    <tr>
	        <td class="bd_t_bg td_t_w">开户银行：</td>
	        <td class="tb_t_ws">${directPayBankInfo.bankName}</td>
	        <td class="bd_t_bg td_t_w">账户名：</td> 
			<td class="tb_t_ws">${directPayBankInfo.bankUserName}</td>
			<td class="bd_t_bg td_t_w">卡号：</td> 
			<td class="tb_t_ws">${directPayBankInfo.bankNo}</td>
	    </tr>
            <#if (directPayBankInfo.bankProvince ?? ) && (directPayBankInfo.bankProvince !="" ) >
	    <tr>
            <td class="bd_t_bg td_t_w">开户行地区：</td>
	    <td class="tb_t_ws"> ${directPayBankInfo.bankProvince} ,<#if (directPayBankInfo.bankCity ??) && (directPayBankInfo.bankCity !="") > ${directPayBankInfo.bankCity} </#if></td>
	    </tr>
            </#if>
	</table>
</div>
</#if>
 <#if insuredToCountry? has_content>
	 <div class="lay-ins-con pro-con-info">
		<div class="ins-tit un_bot_bor">旅游目的地</div>
		<div class="box_sbg">
			<ul class="md_d">
				<li>${insuredToCountry}</li>
			</ul>
		</div>
	</div>
</#if>	
<#if complicatedFlag == "Y">  
<#if showDuty? has_content>
<div>
	<div class="lay-ins-con pro-tab">
		<div class="ins-tit">保障权益</div>
		<table class="tab-bd insurant-tab prod-proj-tab" border=0 cellspacing=0 cellpadding=0>
				<colgroup>
					<col width="20%">
					<col width="10%">
					<col width="70%">
				</colgroup>
				<tr class=prod-proj-tab-hd>
							<td style="text-align: left;">保障项目</td>
							<td style="text-align: left;">保险金额</td>
							<td>保障内容</td>
						</tr>
						<#list showDuty as list>
							<tr>
								<td>${list.dutyName}</td>
								<td>${list.showAmnt}</td>
								<td>${list.coverage}</td>
							</tr>
						</#list>
			</table>
	</div>
</div>
	</#if>
</#if>
 <div style="display:none;">
 <#if showDuty? has_content>	
	<div class="lay-ins-con pro-tab">
		<h3 class=ins-tit>保障信息</h3>
			<table class="tab-bd insurant-tab prod-proj-tab" border=0 cellspacing=0
				cellpadding=0>
				<colgroup>
					<col width="20%">
					<col width="10%">
					<col width="70%">
						<tr class=prod-proj-tab-hd>
							<td style="text-align: left;">保障项目</td>
							<td style="text-align: left;">保险金额</td>
							<td>保障内容</td>
						</tr>
						<#list showDuty as list>
							<tr>
								<td>${list.dutyName}</td>
								<td>${list.showAmnt}</td>
								<td>${list.coverage}</td>
							</tr>
						</#list>
			</table>
	</div>
</#if>
</div>
			<div class="lay-ins-con pro-con-info">
			<div class="ins-tit un_bot_bor">费用合计</div>
			<div class="bdmx_table_b">
			  <table class="bdmx_table">
			  <tr>
			    <th class="bd_t_bg" width="340px;">起止日期</th>
				<th class="bd_t_bg">被保险人数</th>
				<th class="bd_t_bg">购买份数</th>
			<#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc" || activityFlag == "0")>
				<th class="bd_t_bg">费用合计</th>
			<#else>
				<th class="bd_t_bg">原始费用合计</th>
				<th class="bd_t_bg">优惠后价格</th>
			</#if>
			  </tr>
			  <tr>
			    <td>${sdinformation.startDate} 00时  至  
					    <#if sdinformation.endDate !="9999-12-31">	   
					   			 ${sdinformation.endDate} 24时  	   
					    <#else>
				  				终身	   
				   	   </#if>
			     </td>
			    <td> 
			    ${(insuredResultCount)!}
			    </td>
			    <td>${(insuredCount)!}</td>
			<#if (sdorder.channelsn == "jfsc" || sdorder.channelsn == "wap_jfsc" || sdorder.channelsn == "wj_jfsc")>
			    <td ><font class="yh-oreder-money">${sdorder.offsetPoint}积分</font></td>
			<#elseif activityFlag == "0">
				<td ><font class="yh-oreder-money">￥${sdorder.payPrice}</font>
				<#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if>
				</td>
			   			
			<#elseif activityFlag == "1">
			   	<td><font class="ys-order-money">￥${sdorder.productTotalPrice}</font></td>
                <td ><font class="yh-oreder-money">￥${sdorder.payPrice}</font>
                <#if ((sdinformation.insuranceCompany == '0007' && sdinformation.riskType == '02') || sdinformation.insuranceCompany == '2034')><span class="gray-color">（含6%增值税）</span></#if>
                </td>
			</#if>
			  </tr>
			  <#if (isLcx !="0")>
			  <#if (canPreviewOperatorFlag != "0") && sdorder.channelsn != "jfsc" && sdorder.channelsn != "wj_jfsc" && sdorder.channelsn != "wap_jfsc" &&  typeFlag !="02" && typeFlag !="03" && typeFlag !="04">
			  <tr>
			  	<td colspan="5" class="order-hd-list" id="result_activity_info">
			  		<#if (loginFlag =="true"&&map_pointinfo.pointsprice!="0"&&map_pointinfo.pointsprice!="0.0"&&map_pointinfo.pointsprice!="0.00")!>
						<#if (map_pointinfo.points != null&& map_pointinfo.points != "") >
							<div class="integal_hjcon"><span  class="integral_hj">您用${map_pointinfo.points}积分可以进行抵扣费用¥${map_pointinfo.pointsprice}（继续支付可直抵）</span></div>
						</#if>
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
			  </#if>
			</table>
			
			</div>
			<div style="display:none">
			<span class="ins-int-con">共<span class="num red">${(insuredResultCount)!}</span>名被保险人， <span class="num red">${(insuredCount)!}</span>份保单，保费合计： 
							<span class="ui-money"><dfn>￥</dfn>${sdorder.payPrice}</span></span></div>
		</div> 
		<#if (canPreviewOperatorFlag != "0") >
		  <#if null == insuranceDec || "" == insuranceDec || "null" == insuranceDec >
			<div class="ins-tit un_bot_bor">投保声明及必读</div>
			<div class="msg-con bdmx_table_b"  >
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
		  <#else>
		   ${(insuranceDec)!}
		  </#if>
			<div class="object_btn" style="text-align: center;">
				<div class=btn-more>
				<div class="check_btn"><label><input name="agreeInsure" id="agreeInsure" type="checkbox" value="" />&nbsp;我接受以上投保声明及必读内容 </label>
				</div>
				<div class=" height_s"></div>
				<#if (isAllFree!="Y")>
                  <input type="button" id="qrgm_pay"  class="qrgm_btn" value="确认无误，去支付">
                  <#if (typeFlag !="02")! && (sdorder.channelsn != "jfsc"  && sdorder.channelsn != "wj_jfsc" && sdorder.channelsn != "wap_jfsc") >
                  	<#if (isLcx !="0")>
                  	<input type="button" id="addShopCart"  class="fhxg_gwc" value="加入购物车"  >
                  	</#if>
               	  </#if>
               	  <#else>
                  		<input type="button" id="free_pay"  class="qrgm_btn" value="获得赠险">
                  </#if>
               	  <a href="${base}/shop/order_config_new!buyNowUpdate.action?orderId=${sdorder.id}&productId=${sdinformation.productId}&orderSn=${sdorder.orderSn}&orderFlag=${orderFlag}&KID=${KID}&order_healthySn=${sdorder.orderSn}" class="qrgm_update">返回修改</a>
               </div>
			</div>
			<div id="msg_2" style="display:none" class="londing_mes londing_mes_nolog"><img src="${staticPath}/images/nloading.gif" width="20px" height="20px" alt="" /><span id="msg_2_2">正在处理，请稍等</span></div>
		</#if>
	</form>
</div>
</div>
<div class="clear"></div> 
<#if (typeFlag !="02")!>
<#if (typeFlag =="03")!>
<link rel="stylesheet" type="text/css" href="${CpsAgentURL}/style/def_web.css"/>
 <div class="footerArea">
	<ul>
		<li><a href="http://www.kaixinbao.com/images/bxdlywxkz.jpg" target="_blank" rel="nofollow">保险代理业务许可证：网金保险销售服务有限公司210017000000800未经许禁止转载</a></li>
		<li><a href="#" target="_blank" rel="nofollow">中国保险监督管理委员会互联网业务备案</a></li>
		<li class="last"><a href="http://www.miitbeian.gov.cn" target="_blank" rel="nofollow">辽ICP备12007009号-1</a></li>
	</ul>
	<address>
		<p>Copyright&copy;2013-2021 kaixinbao.com.ALL Rights Reserved</p>
	</address>
</div>
<#else>
	 <#if (typeFlag =="04")!>
		<iframe id="iframeA" name="iframeA" marginheight="0" marginwidth="0" frameborder="0" scrolling="no" src="${CpsUnionURL}/includeV2/front_order_foot.jsp" width="100%" height=100%></iframe> 
		<#else>
		<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
		</#if> 
</#if>
</#if> 
<!-- 百分点发送逻辑 begin -->
<script type="text/javascript" src="${shopStaticPath}/min-jquery.js"></script>
<script type="text/javascript" src="${shopStaticPath}/jquery.cookie.js"></script>
<script type="text/javascript">
/*  //百分点合作结束，百分点核心代码暂存
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
<script type="text/javascript" src="${shopStaticPath}/jquery.blockUI.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/shopCart.js"></script>
<script type="text/javascript" src="${shopStaticPath}/shop/js/shop.js"></script>
<script type="text/javascript">
var productIntegralValue='${sendPoints}';
whenIntegralZero(productIntegralValue);
</script>
</body>
</html>