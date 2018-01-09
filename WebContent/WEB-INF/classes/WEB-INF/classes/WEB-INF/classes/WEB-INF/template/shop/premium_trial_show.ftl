<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>协议</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${shopStaticPath}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/member_center.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function changeButton(a){
   if(a=='Y'){
       document.getElementById("tijiao").disabled='';
   }else{
       document.getElementById("tijiao").disabled='disabled';
   }
}
function sure(){
   location.href="${base}/shop/premium_trial!loadInformation.action";
}
</script>
</head>
<body>
    <table>
       <tr>
         <td>
            <input type="radio" id="isAccept1" name="isAccept" value="N" onclick="changeButton(this.value);"/>不同意
         <td>
       </tr>
       <tr>
         <td>
            <input type="radio" id="isAccept2" name="isAccept" value="Y" onclick="changeButton(this.value);"/>同意
         <td>
       </tr>
       <tr>
         <td>
            <input type="button" id="tijiao" name="tijiao"  disabled='disabled' value="提交" onclick="sure();"/>
         <td>
       </tr>
    </table>
</body>
</html>