 <!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保_非车险_支付列表</title>
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${base}/template/shop/css/PayPromptSty.css"  />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css" />
 <style >


    body, button, input, select, textarea, table{font:12px/1.6 tahoma,arial,'Hiragino Sans GB',simsun,sans-serif}
   	table th, table td{font-weight:normal;vertical-align:top;text-align:left;font-family:'微软雅黑';}
    table{border-collapse:collapse;border-spacing:0}
     
.ColumnArea__{width:500px;float:right; background:#fff;}
.ColumnArea__ .TitleArea{background:url(${base}/template/shop/images/Bg_1.gif); height:34px; line-height:34px; font-size:16px; color:#000000;text-shadow: -1px -1px 1px #810C0C; text-indent:25px;}
.ColumnArea__ .TitleArea .TitleTxt{float:left; height:34px; line-height:34px; font-size:16px; color:#000000;font-weight:hold;}
.ColumnArea__ .TitleArea .TitleMore{float:right; background: url(${base}/template/shop/images/login_bg.gif) no-repeat -30px -64px; width:40px; height:20px;  margin-top:5px;}
.ColumnArea__ .TitleArea .TitleMore a{display:block; text-decoration:none; width:40px; height:24px;}
.ColumnArea__ .Content {font-size:14px;margin-top:10px;margin-left:10px;}

.btn {
BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP:
#7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER:
progid:DXImageTransform.Microsoft.Gradient(GradientType=0,
StartColorStr=#ffffff, EndColorStr=#cecfde); BORDER-LEFT: #7b9ebd
1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px;
BORDER-BOTTOM: #7b9ebd 1px solid
}

.tab_u1 {background:url(${base}/template/shop/images/Bg_1.gif); }
</style>
<script type="text/javascript">
	function dosubmit(tmp){
		if(tmp == null || tmp == ''){
			return false;
		}
		jQuery.blockUI({ 
			"message":jQuery("#paytippopdiv"),
			"css":{ 
		          "width": jQuery("#paytippopdiv").width(),
		          "height":jQuery("#paytippopdiv").height()
		        }
		});
		document.getElementById("payType").value = tmp;
		document.forms["payment_order"].submit();
	}
	function doclose(){
		jQuery.unblockUI();
	}
	function docallBack(){
	    window.location.href = "${base}/shop/pay!doCallBack.action?OrdId=${order.orderSn}";
	}
</script>
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">
	 


	<form action="pay!show.action" target="_blank" id="payment_order" name="payment_order">
	<input type ="hidden"  id ="artLoginFlag" name="artLoginFlag" value="1" />
	<input type="hidden" name="payType" id="payType" value="1"/>
	<input type="hidden" name="OrdId" value="${order.orderSn}"/>
	<input type="hidden" name="KID" value="${KID}"/>
	<div class="PayPromptMiddArea">
		  <div class="PayStatusArea" align="center"><!--   支付状态  begin   -->
	    	<div class="PayStatus_4" align="center">&nbsp;</div>
	    </div><!--    支付状态  end    -->
	    
		 	 <table  border=0 cellspacing=0 cellpadding=0 style="border:1px solid #dedee0;margin:0 auto 15px;width:100%;border-collapse:collapse;border-spacing:0">
		 	            <tr height="30">
		 	               <td colspan="5" style="font-size:13px;background-color:#FFC75F;color:#666;border:1px solid #dedee0;border-collapse:collapse;border-spacing:0;font-family:'微软雅黑';font-weight:bold;" ><font size="2.5px">&nbsp;&nbsp;&nbsp;支付清单</font></td>
		 	            </tr>
						<tr height="30">
							<td style="text-align:center;border:1px solid #dedee0;font-family:'微软雅黑';font-weight:bold;">订单号</td>
							<td style="text-align:center;border:1px solid #dedee0;font-family:'微软雅黑';font-weight:bold;">投保人</td>
							<td style="text-align:center;border:1px solid #dedee0;font-family:'微软雅黑';font-weight:bold;">产品名称</td>
							<td style="text-align:center;border:1px solid #dedee0;font-family:'微软雅黑';font-weight:bold;">份数</td>
							<td style="text-align:center;border:1px solid #dedee0;font-family:'微软雅黑';font-weight:bold;">价格</td>
						</tr>
						<tr height="30">
							<td style=" text-align:center;border:1px solid #dedee0">${order.orderSn}</td>
							<td style=" text-align:center;border:1px solid #dedee0">${applicantName}</td>
							<td style=" text-align:center;border:1px solid #dedee0">${order.productName}</td>
							<td style=" text-align:center;border:1px solid #dedee0">1</td>
							<td style=" text-align:center;border:1px solid #dedee0">￥${order.totalAmount}</td>
						</tr>
			</table>
		 
	     <div class="PayDetailsColumnArea_2"><!--     您需要支付  begin   -->
	        <div class="PayDetailsContArea_1">
                <table  border=0 cellspacing=0 cellpadding=0 style="border:1px solid #dedee0;margin:0 auto;width:100%;border-collapse:collapse;border-spacing:0">
					<tr height="30">
						<td  style="font-size:13px;background-color:#FFC75F;color:#666;border:1px solid #dedee0;border-collapse:collapse;border-spacing:0;font-family:'微软雅黑';font-weight:bold;"><font size="2.5px">&nbsp;&nbsp;&nbsp;请选择支付方式</font></td>
		 	        </tr>
					<tr>
						<td style="border:1px solid #dedee0;">
				           <h4 class="fl">您需要支付：</h4>
				            <div class="AllPriceArea fl">
				            	￥<strong>${order.totalAmount}</strong>元
				            </div>
				            <div class="clear"></div>
	            		</td>
	            	</tr>
				
			
			<tr>
				<td style="border:1px solid #dedee0;">
				<ul class="tab_u1 clearfix" id="min_tag_hzpp1">
            		<li class="tab_li1"><em class="select_tab_hzpp1">在线支付</em></li>
            		<li class="tab_li2"><em>信用卡支付</em></li>
            	</ul>
            	<div class="new_tab_box clearfix" id="tag_box_hzpp1"  >
            	 <ul class="hz_gs_list">
            	 	<table  border=0 cellspacing=0 cellpadding=0 style="margin:0 auto;width:80%;border-collapse:collapse;border-spacing:0">
		            	<tr>
							<td>	
					            <h4>支付平台：</h4>
					            <div class="ColumnCont">
					            	<ul class="BankPayArea">
					            	     <#list bank1 as bank>
					                			<li><img onclick="dosubmit('${bank.payType}')" src="${base}/template/shop/images/pay/${bank.image}" width="130" height="30" alt="${bank.description}"/></li>
					               		 </#list>
					                </ul>
					            </div>
					            <div class="clear"></div>
		                  	</td>
		 				</tr>
		 				<tr>
		 					<td><div  class="div_line">&nbsp;</div></tr>
		 				</tr>
						<tr>
							 <td>
					            <h4>网上银行：</h4>
					            <div class="ColumnCont">
					            	<ul class="BankPayArea">
					            	    <#list bank2 as bank>
					                			<li><img onclick="dosubmit('${bank.payType}')" src="${base}/template/shop/images/pay/${bank.image}" width="130" height="30" alt="${bank.description}"/></li>
					                	</#list>
					                </ul>
					                <div class="clear"></div>
					            </div>
							</td>
						</tr>
					</table>
				</ul>
				 <ul class="hz_gs_list" style="display:none;">
	                <div class="msg-tips">
	                	 <p class="msg-des">无需开通网银，无需随身携带信用卡。输入您的信息，即可立即付款。<br>
	                	 易宝支付提供信用卡无卡支付，支持十二家银行：中国银行、工商银行、建设银行、中信银行、民生银行、广发银行、农业银行、北京银行、上海银行、招商银行、锦州银行、光大银行
	                	 </p>
	                </div>
				 	<p class="xyk">
				 		 <span class="clew">温馨提示:</span><br/>
                    	 1、如果您选择招商银行信用卡付款，请保持手机畅通，招商银行（95555）将进行电话回访确认，请按照语音提示操作。招行电话回访时间为8:00&mdash;22:00，其他时段暂不提供无卡支付服务，建议该时段选择其他银行进行付款。<br>
 						 2、如果您选择信用卡无卡支付，将在银行确认扣款成功后1个小时之内为您处理投保事宜。
	                </p>
	                
	                  <div class="pf_buttton">
               			 <a href="javascript:void(0);" onclick="dosubmit('ybxyk')">
                   		 	<img width="213" height="40" src="${base}/wwwroot/kxb/images/payfor.gif">
                   		 </a>
                   	  </div>
                   	  <br>
                   	  <br>
                 </ul>
			</div>
			</td>
			</tr>
			</table>
			</div>
	    </div><!--    您需要支付  end    -->
			
	  	
	</form>

   <div class="clear"></div>
 </div>
</div>
   <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">


<div id="paytippopdiv" style="background-color:white;width: 500px ;height:150px;border:5px solid #dcd8b5; display: none;" class="ColumnArea__" >
      <div class="TitleArea"><div class="TitleTxt">请支付 </div>
      </div>
      <div class="Content" >
      		<p style="text-align: left; font-size: 14px;margin-bottom:10px;"> 请在新打开的页面完成支付后选择：</p>
			<button onclick="docallBack();"  >&nbsp;&nbsp;已完成支付&nbsp;&nbsp;</button> &nbsp;&nbsp;&nbsp;
			<button  onclick="doclose();">&nbsp;&nbsp;遇见问题&nbsp;&nbsp;</button>
      </div>
</div>
<script type="text/javascript" charset="utf-8" src="${base}/template/common/js/jquery.blockUI.js"></script>
 <script type="text/javascript" src="${base}/wwwroot/kxb/js/jqueryupdate.js"></script>
</body>
</html>
 