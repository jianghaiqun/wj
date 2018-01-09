<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sinosoft.framework.Config"%>
<%@ page import="com.allinpay.ets.client.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付</title>
<link href="<%=Config.getValue("StaticResourcePath") %>/style/redesign/re_header.css" rel="stylesheet" type="text/css" />
<link href="<%=Config.getValue("StaticResourcePath") %>/style/pay_skip.css" rel="stylesheet" type="text/css" />
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body>

<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>

<div class="wrapper">
  <div class="guodu_box">
       <div class="guodu_logo"></div>
    <p class="meihua_p">正在调取支付页面，请稍后...</p><br>
   	 
    <img id="wait" src="<%=Config.getValue("StaticResourcePath") %>/images/loading2.gif" width="70" height="70" /><br>
		 <span id="errorMessage" style="font-size:18px;color:red;"></span><br><br>
		如长时间未响应,请点击 <a href="###" onclick="reload()" style="color: #FF6D00;">刷新</a><br>
    </div>
</div>
<%	
	//页面编码要与参数inputCharset一致，否则服务器收到参数值中的汉字为乱码而导致验证签名失败。
	request.setCharacterEncoding("UTF-8"); 

	String key=(String)request.getAttribute("key");
	String version=(String)request.getAttribute("Version");
	String language="1";
	String inputCharset="1";
	String merchantId=(String)request.getAttribute("MerId");
	String pickupUrl=(String)request.getAttribute("RetUrl");
	String receiveUrl=(String)request.getAttribute("BgRetUrl");
	String payType="0";
	String signType="1";
	String orderNo=(String)request.getAttribute("paySn");
	String orderAmount=(String)request.getAttribute("total_fee");
	String orderDatetime=(String)request.getAttribute("TransDate");
	String orderCurrency="0";
	String orderExpireDatetime="";
	String payerTelephone="";
	String payerEmail="";
	String payerName="";
	String payerIDCard="";
	String pid="";
	String productName=(String)request.getAttribute("riskname");
	String productId=(String)request.getAttribute("productid");
	String productNum="1";
	String productPrice="";
	String productDesc="";
	String ext1="";
	String ext2="";
	String extTL="";//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
	String issuerId="";
	String pan="";
	String tradeNature = "";
	String sign="";
	
	//若直连telpshx渠道，payerTelephone、payerName、payerIDCard、pan四个字段不可为空
	//其中payerIDCard、pan需使用公钥加密（PKCS1格式）后进行Base64编码
	if(null!=payerIDCard&&!"".equals(payerIDCard)){
		try{
			payerIDCard = SecurityUtil.encryptByPublicKey(Config.getClassesPath() + "TLCert.cer", payerIDCard);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	if(null!=pan&&!"".equals(pan)){
		try{
			pan = SecurityUtil.encryptByPublicKey(Config.getClassesPath() + "TLCert.cer", pan);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//构造订单请求对象，生成signMsg。
	RequestOrder requestOrder = new RequestOrder();
	if(null!=inputCharset&&!"".equals(inputCharset)){
		requestOrder.setInputCharset(Integer.parseInt(inputCharset));
	}
	requestOrder.setPickupUrl(pickupUrl);
	requestOrder.setReceiveUrl(receiveUrl);
	requestOrder.setVersion(version);
	if(null!=language&&!"".equals(language)){
		requestOrder.setLanguage(Integer.parseInt(language));
	}
	requestOrder.setSignType(Integer.parseInt(signType));
	requestOrder.setPayType(Integer.parseInt(payType));
	requestOrder.setIssuerId(issuerId);
	requestOrder.setMerchantId(merchantId);
	requestOrder.setPayerName(payerName);
	requestOrder.setPayerEmail(payerEmail);
	requestOrder.setPayerTelephone(payerTelephone);
	requestOrder.setPayerIDCard(payerIDCard);
	requestOrder.setPid(pid);
	requestOrder.setOrderNo(orderNo);
	requestOrder.setOrderAmount(Long.parseLong(orderAmount));
	requestOrder.setOrderCurrency(orderCurrency);
	requestOrder.setOrderDatetime(orderDatetime);
	requestOrder.setOrderExpireDatetime(orderExpireDatetime);
	requestOrder.setProductName(productName);
	if(null!=productPrice&&!"".equals(productPrice)){
		requestOrder.setProductPrice(Long.parseLong(productPrice));
	}
	if(null!=productNum&&!"".equals(productNum)){
		requestOrder.setProductNum(Integer.parseInt(productNum));
	}	
	requestOrder.setProductId(productId);
	requestOrder.setProductDesc(productDesc);
	requestOrder.setExt1(ext1);
	requestOrder.setExt2(ext2);
	requestOrder.setExtTL(extTL);//通联商户拓展业务字段，在v2.2.0版本之后才使用到的，用于开通分账等业务
	requestOrder.setPan(pan);
	requestOrder.setTradeNature(tradeNature);
	requestOrder.setKey(key); //key为MD5密钥，密钥是在通联支付网关会员服务网站上设置。

	String strSrcMsg = requestOrder.getSrc(); // 此方法用于debug，测试通过后可注释。
	String strSignMsg = requestOrder.doSign(); // 签名，设为signMsg字段值。
	%>
<div style="display:none;">
<form id="payment" name="payment" action="<%=request.getAttribute("GateUrl") %>" method="post">
		<input type="hidden" name="inputCharset" value="<%=inputCharset%>"/>
		<input type="hidden" name="pickupUrl" value="<%=pickupUrl%>"/>
		<input type="hidden" name="receiveUrl" value="<%=receiveUrl%>" />
		<input type="hidden" name="version" value="<%=version %>"/>
		<input type="hidden" name="language" value="<%=language %>" />
		<input type="hidden" name="signType" value="<%=signType%>"/>
		<input type="hidden" name="merchantId" value="<%=merchantId%>" />
		<input type="hidden" name="payerName" value="<%=payerName%>"/>
		<input type="hidden" name="payerEmail" value="<%=payerEmail%>" />
		<input type="hidden" name="payerTelephone" value="<%=payerTelephone %>" />
		<input type="hidden" name="payerIDCard" value="<%=payerIDCard %>" />
		<input type="hidden" name="pid" value="<%=pid%>"/>
		<input type="hidden" name="orderNo" value="<%=orderNo%>" />
		<input type="hidden" name="orderAmount" value="<%=orderAmount %>"/>
		<input type="hidden" name="orderCurrency" value="<%=orderCurrency%>" />
		<input type="hidden" name="orderDatetime" value="<%=orderDatetime%>" />
		<input type="hidden" name="orderExpireDatetime" value="<%=orderExpireDatetime %>"/>
		<input type="hidden" name="productName" value="<%=productName%>" />
		<input type="hidden" name="productPrice" value="<%=productPrice%>" />
		<input type="hidden" name="productNum" value="<%=productNum %>"/>
		<input type="hidden" name="productId" value="<%=productId%>" />
		<input type="hidden" name="productDesc" value="<%=productDesc%>" />
		<input type="hidden" name="ext1" value="<%=ext1%>" />
		<input type="hidden" name="ext2" value="<%=ext2%>" />
		<input type="hidden" name="payType" value="<%=payType%>" />
		<input type="hidden" name="issuerId" value="<%=issuerId%>" />
		<input type="hidden" name="pan" value="<%=pan %>" />
		<input type="hidden" name="tradeNature" value="<%=tradeNature %>" />
		<input type="hidden" name="signMsg" value="<%=strSignMsg %>" />
	 </form>
</div>

<input type="hidden" id="OrderId" value="<s:property value="OrdId"/>">

<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
</body>

<script type="text/javascript">
	
	var orderId = jQuery("#OrderId").val();

    var url = "pay!ajaxVerify.action";

    jQuery.post(url,{"OrdId":orderId},callBackFunction,"json");

function callBackFunction(data){
	if(data.status == "true"){
		jQuery("#payment").submit();
	}else{
		jQuery('#wait').attr('style', 'display:none');
		jQuery("#errorMessage").html(data.error);
	}
}

function reload(){
	location.reload();
}
</script>	
</html>