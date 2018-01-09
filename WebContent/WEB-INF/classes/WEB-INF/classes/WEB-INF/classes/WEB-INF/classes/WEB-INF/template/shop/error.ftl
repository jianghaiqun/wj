<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>提示信息</title>
<link rel="icon" href="../favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css">

<style>
  .not_found{
  width: 496px;
  height: 195px;
  margin: 100px auto 0px;
  padding-left:71px;
  padding-top: 210px;
  font-family: "Microsoft Yahei";
  background: url("${staticPath}/images/redesign/error.png") no-repeat 34px top;
}
.not_found h1{
  font-size: 18px;
  font-weight: normal;
  color: #fd8823;
  text-align: center;
}
.not_found .btn_wrap{
  margin: 40px 0 20px;
  overflow: hidden;
}
.not_found .btn_wrap a{
  float: left;
  display: block;
  width: 127px;
  height: 35px;
  margin: 0 7px;
  line-height: 35px;
  border-radius: 5px;
  font-size: 14px;
  color: #fff;
  background-color: #fd8823;
  text-align: center;
}
.not_found p{
  color: #888;
}
.not_found p em{
  margin-left: 5px;
  font-weight: normal;
  color: #ed6d00;
}
.not_found .btn_wrap a:hover{ background-color:#FE9E47; }
.bg_404{ background:#F5F5F5; }
</style>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="bg_404">
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if>
<!-- 404页面 -->
    <div class="not_found">
        <h1>
        <#if (errorMessages?size > 0)!>
			<#list errorMessages as list>${list}<br></#list>
		<#elseif (actionMessages?size > 0)!>
			<#list actionMessages as list>${list}<br></#list>
		<#elseif (fieldErrors?size > 0)!>
			<#list (fieldErrors?keys)! as key>
				${fieldErrors[key]?replace('^\\[', '', 'r')?replace('\\]$', '', 'r')}<br>
			</#list>
		<#else>
			出错啦～～人太多，系统太繁忙，我们努力加载中，请您稍等!
		</#if>
        </h1>
        <div class="btn_wrap">
            <a href="javascript:void(0);" vlpageid="xiaoneng" exturl="http://www.kaixinbao.com/xiaoneng" id="qqwap2" onclick="return(VL_FileDL(this));return false;">联系在线客服</a>
            <a href="javascript:void(0);" onclick="window.history.back(); return false;">返回上一页</a>
            <a href="${front}/">首页</a>
        </div>
        <p>或者<em>致电4009-789-789</em></p>
    </div>
<!-- 404页面 end -->
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if> 
<#include "/wwwroot/kxb/block/community_v1.shtml" />
</body>
</html>