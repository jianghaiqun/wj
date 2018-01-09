<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.sinosoft.framework.Config" import="cn.com.sinosoft.action.shop.KqPkipair"%>
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
	//人民币网关账号，该账号为11位人民币网关商户编号+01,该参数必填。
	String merchantAcctId = (String)request.getAttribute("MerId")+"01";
	//编码方式，1代表 UTF-8; 2 代表 GBK; 3代表 GB2312 默认为1,该参数必填。
	String inputCharset = "1";
	//接收支付结果的页面地址，该参数一般置为空即可。
	String pageUrl = (String)request.getAttribute("RetUrl");
	//服务器接收支付结果的后台地址，该参数务必填写，不能为空。
	String bgUrl = (String)request.getAttribute("BgRetUrl");
	//网关版本，固定值：v2.0,该参数必填。
	String version =  (String)request.getAttribute("Version");
	//语言种类，1代表中文显示，2代表英文显示。默认为1,该参数必填。
	String language =  "1";
	//签名类型,该值为4，代表PKI加密方式,该参数必填。
	String signType =  "4";
	//支付人姓名,可以为空。
	String payerName= ""; 
	//支付人联系类型，1 代表电子邮件方式；2 代表手机联系方式。可以为空。
	String payerContactType =  "";
	//支付人联系方式，与payerContactType设置对应，payerContactType为1，则填写邮箱地址；payerContactType为2，则填写手机号码。可以为空。
	String payerContact =  "";
	//商户订单号
	String orderId = (String)request.getAttribute("paySn");
	//订单金额，金额以“分”为单位。
	String orderAmount = (String)request.getAttribute("total_fee");
	//订单提交时间，格式：yyyyMMddHHmmss，如：20071117020101，不能为空。
	String orderTime = (String)request.getAttribute("TransDate");
	//商品名称，可以为空。
	String productName= (String)request.getAttribute("riskname");
	//商品数量，可以为空。
	String productNum = "1";
	//商品代码，可以为空。
	String productId = (String)request.getAttribute("productid");
	if (productId == null) {
		productId = "";
	}
	//商品描述，可以为空。
	String productDesc = "";
	//扩展字段1，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
	String ext1 = "";
	//扩展自段2，商户可以传递自己需要的参数，支付完快钱会原值返回，可以为空。
	String ext2 = "";
	//支付方式，一般为00，代表所有的支付方式。如果是银行直连商户，该值为10，必填。
	String payType = "21";
	//银行代码，如果payType为00，该值可以为空；如果payType为10，该值必须填写，具体请参考银行列表。
	String bankId = "";
	//同一订单禁止重复提交标志，实物购物车填1，虚拟产品用0。1代表只能提交一次，0代表在支付不成功情况下可以再提交。可为空。
	String redoFlag = "";
	//快钱合作伙伴的帐户号，即商户编号，可为空。
	String pid = "";
	//指定付款人
	String payerIdType="";
	//付款人标识
	String payerId="";
	// signMsg 签名字符串 不可空，生成加密签名串
	String signMsgVal = "";
	signMsgVal = appendParam(signMsgVal, "inputCharset", inputCharset);
	signMsgVal = appendParam(signMsgVal, "pageUrl", pageUrl);
	signMsgVal = appendParam(signMsgVal, "bgUrl", bgUrl);
	signMsgVal = appendParam(signMsgVal, "version", version);
	signMsgVal = appendParam(signMsgVal, "language", language);
	signMsgVal = appendParam(signMsgVal, "signType", signType);
	signMsgVal = appendParam(signMsgVal, "merchantAcctId",merchantAcctId);
	signMsgVal = appendParam(signMsgVal, "payerName", payerName);
	signMsgVal = appendParam(signMsgVal, "payerContactType",payerContactType);
	signMsgVal = appendParam(signMsgVal, "payerContact", payerContact);
	signMsgVal = appendParam(signMsgVal, "payerIdType", payerIdType);
	signMsgVal = appendParam(signMsgVal, "payerId", payerId);
	signMsgVal = appendParam(signMsgVal, "orderId", orderId);
	signMsgVal = appendParam(signMsgVal, "orderAmount", orderAmount);
	signMsgVal = appendParam(signMsgVal, "orderTime", orderTime);
	signMsgVal = appendParam(signMsgVal, "productName", productName);
	signMsgVal = appendParam(signMsgVal, "productNum", productNum);
	signMsgVal = appendParam(signMsgVal, "productId", productId);
	signMsgVal = appendParam(signMsgVal, "productDesc", productDesc);
	signMsgVal = appendParam(signMsgVal, "ext1", ext1);
	signMsgVal = appendParam(signMsgVal, "ext2", ext2);
	signMsgVal = appendParam(signMsgVal, "payType", payType);
	signMsgVal = appendParam(signMsgVal, "bankId", bankId);
	signMsgVal = appendParam(signMsgVal, "redoFlag", redoFlag);
	signMsgVal = appendParam(signMsgVal, "pid", pid);
	KqPkipair pki = new KqPkipair();
	String signMsg = pki.signMsg(signMsgVal);
%>

<%!public String appendParam(String returns, String paramId, String paramValue) {
		if (returns != "") {
			if (paramValue != "") {

				returns += "&" + paramId + "=" + paramValue;
			}

		} else {

			if (paramValue != "") {
				returns = paramId + "=" + paramValue;
			}
		}

		return returns;
	}%>

<div style="display:none;">
<form id="payment" name="payment" action="<%=request.getAttribute("GateUrl") %>" method="post">
	<input type="hidden" name="inputCharset" value="<%=inputCharset%>" />
	<input type="hidden" name="pageUrl" value="<%=pageUrl%>" />
	<input type="hidden" name="bgUrl" value="<%=bgUrl%>" />
	<input type="hidden" name="version" value="<%=version%>" />
	<input type="hidden" name="language" value="<%=language%>" />
	<input type="hidden" name="signType" value="<%=signType%>" />
	<input type="hidden" name="signMsg" value="<%=signMsg%>" />
	<input type="hidden" name="merchantAcctId" value="<%=merchantAcctId%>" />
	<input type="hidden" name="payerName" value="<%=payerName%>" />
	<input type="hidden" name="payerContactType" value="<%=payerContactType%>" />
	<input type="hidden" name="payerContact" value="<%=payerContact%>" />
	<input type="hidden" name="payerIdType" value="<%=payerIdType%>" />
	<input type="hidden" name="payerId" value="<%=payerId%>" />
	<input type="hidden" name="orderId" value="<%=orderId%>" />
	<input type="hidden" name="orderAmount" value="<%=orderAmount%>" />
	<input type="hidden" name="orderTime" value="<%=orderTime%>" />
	<input type="hidden" name="productName" value="<%=productName%>" />
	<input type="hidden" name="productNum" value="<%=productNum%>" />
	<input type="hidden" name="productId" value="<%=productId%>" />
	<input type="hidden" name="productDesc" value="<%=productDesc%>" />
	<input type="hidden" name="ext1" value="<%=ext1%>" />
	<input type="hidden" name="ext2" value="<%=ext2%>" />
	<input type="hidden" name="payType" value="<%=payType%>" />
	<input type="hidden" name="bankId" value="<%=bankId%>" />
	<input type="hidden" name="redoFlag" value="<%=redoFlag%>" />
	<input type="hidden" name="pid" value="<%=pid%>" />
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