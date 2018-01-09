<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>订单结果</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link href="${shopStaticPath}/template/shop/css/PayPromptSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link href="${staticPath}/style/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/style/BaikeSty.css" rel="stylesheet" type="text/css" />
<link href="${staticPath}/style/IntegralMallSty.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<style> 
.button_2{
	border:none;
	width:129px;
	height:37px;
	line-height:38px;
	#background-image: url(${shopStaticPath}/template/shop/images/pointbtn02.gif);
	background:url(${shopStaticPath}/template/shop/images/pointbtn02.gif);
	cursor: pointer;
}
</style>
 
</head>
<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">
	
			<div class="BaikeMiddArea">
<form id="fm"  action="points!dosubmit.action" method="post"   >
<input name="TotalPoints" id="TotalPoints" value="${present.point}" type="hidden">
	<div class="BaikeMiddArea">
		<div class="wrappx">
			<div class="titex">以下是您的兑换商品清单。</div>
			<div class="table03">
					<table width="897" border="0">
					<tbody>
						<tr>
	   					  <th width="479" align="left" valign="top">商品名称</th>
	   					  <th width="114" align="center">数量</th>
						  <th width="164" align="left">兑换分值</th>
						</tr>
						<tr>
						  <td align="center"><div class="tableimg"><a href="${articleUrl}" target="_blank"><img src="${articleLogo}" width="80" height="80" align="absmiddle" /></a></div>
						   <div class="tabletit01"><a href="${articleUrl}" target="_blank" style="margin-left:30px;">${present.name}</a></div></td>
						  <td align="center">1</td>
						  <td align="left"><span class="orange">开心果值：<b>${present.point}</b></span></td>
				  </tbody>
		  		  </table>
		   </div>
			  <div class="table04">
					<table width="897" border="0">
					<tbody>
						<tr>
	   					  <th width="783" align="right" valign="top">商品合计开心果值：</th>
	   					  <th width="102" align="left"><span class="orange"><b>${present.point}</b></span></th>
						</tr>
						
					  <tr>
					    <th align="right" valign="top">您的剩余开心果值：</th>
						  <th align="left"><b>${SparePoint}</b></th>
					  </tr>
				  </tbody>
		  		</table>
	  		  </div>
	  		  
<form>
	  		  
			  <div class="btnare500">
			  		<input type="submit" class="button_2" value="">
			  </div>
		</div>
		
	   	 <div class="clear"></div>
		</div>
	</div>
	<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</div>
</body>
</html>