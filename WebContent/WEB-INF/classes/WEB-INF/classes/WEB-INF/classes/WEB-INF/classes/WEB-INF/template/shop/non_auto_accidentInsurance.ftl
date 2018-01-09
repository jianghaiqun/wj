<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>个人信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<style>
.comparediv{background:#FD943E;height:103px;margin:0 auto;padding:0px 4px 4px 4px;width:972px; text-align:left;}
.comparediv h3{color:#fff;font-weight:bold;height:22px;line-height:22px;text-indent:6px;}
.closebtn{height:24px;margin-top:-22px;text-align:right;}
	.closebtn img{margin-right:2px;margin-top:0px;}
.comparedivcont{background:#fff; height:80px;}
.comparedetail{background:#fff;float:left;height:70px;padding-left:7px;padding-top:9px;width:170px;}
	.comparedetail img{border:1px solid #ccc;float:left; width:69px;}
	.comparedetail p{float:left;padding-left:6px;width:80px;}
	.cppic{ float:left;}
.comparebtn{background:#F2F2F2;display:block;float:right;height:79px;text-align:center;width:87px;}
	.comparebtn img{margin-top:24px;}
	span.cpdelico{background:url(../images/icodel.gif) no-repeat left;padding-left:16px;}
</style>

<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/shop/css/login.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/register.css" rel="stylesheet" type="text/css" />
<link href="${base}/template/shop/css/member_center.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/shop/js/login.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/register.js"></script>
<script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>
<script type="text/javascript">
function moveR() {
document.getElementById("cpdiv").style.top=document.body.scrollTop+document.body.clientHeight-110;
document.getElementById("cpdiv").style.left=document.body.scrollLeft+(document.body.clientWidth-980)/2;
setTimeout("moveR();",100);
window.onresize="moveR();";
}
 function closecp(){
	 document.getElementById("cpdiv").style.display="none";
	 document.getElementById("cpprodu1").style.display="none";
	 document.getElementById("cpprodu2").style.display="none";
	 document.getElementById("cpprodu3").style.display="none";
	 document.getElementById("cpprodu4").style.display="none";
	 document.getElementById("insuranceCom").value="";	
	 document.getElementById("insuranceComB").value="";	
	 document.getElementById("insuranceComC").value="";		
	 document.getElementById("insuranceComD").value="";	 
	 }
	  function subclosecp(){
	 document.getElementById("cpdiv").style.display="none";
	 document.getElementById("cpprodu1").style.display="none";
	 document.getElementById("cpprodu2").style.display="none";
	 document.getElementById("cpprodu3").style.display="none";
	  document.getElementById("cpprodu4").style.display="none";
	 }
 function showcp(productName,thumbnailUrl,productSerial,insuranceCom){
	 document.getElementById("cpdiv").style.display="block";
	 if(document.getElementById("cpprodu1").style.display!="block"&&document.getElementById("productname2").innerHTML!=productName&&document.getElementById("productname3").innerHTML!=productName&&document.getElementById("productname4").innerHTML!=productName){
		 document.getElementById("cpprodu1").style.display="block" ;
		 document.getElementById("productname1").innerHTML=productName;
		 document.getElementById("insuranceCom").value=insuranceCom; 
	 }else if(document.getElementById("cpprodu2").style.display!="block"&&document.getElementById("productname1").innerHTML!=productName&&document.getElementById("productname3").innerHTML!=productName&&document.getElementById("productname4").innerHTML!=productName){
		 document.getElementById("cpprodu2").style.display="block" ; 
		 document.getElementById("productname2").innerHTML=productName; 
		 document.getElementById("insuranceComB").value=insuranceCom; 		 
	 }else if(document.getElementById("cpprodu3").style.display!="block"&&document.getElementById("productname1").innerHTML!=productName&&document.getElementById("productname2").innerHTML!=productName&&document.getElementById("productname4").innerHTML!=productName){
		 document.getElementById("cpprodu3").style.display="block" ; 
		 document.getElementById("productname3").innerHTML=productName;
		 document.getElementById("insuranceComC").value=insuranceCom; 
	 }else if(document.getElementById("cpprodu4").style.display!="block"&&document.getElementById("productname1").innerHTML!=productName&&document.getElementById("productname2").innerHTML!=productName&&document.getElementById("productname4").innerHTML!=productName){
		 document.getElementById("cpprodu4").style.display="block" ; 
		 document.getElementById("productname4").innerHTML=productName;
		 document.getElementById("insuranceComD").value=insuranceCom; 
	 }
}
 function closecp1(){
	 document.getElementById("cpprodu1").style.display="none";
	 document.getElementById("insuranceCom").value="";	
	 }
 function closecp2(){
	 document.getElementById("cpprodu2").style.display="none";
	 document.getElementById("insuranceComB").value="";	
	 }
 function closecp3(){
	 document.getElementById("cpprodu3").style.display="none";
	 document.getElementById("insuranceComC").value="";	 
	 }
function closecp4(){
	 document.getElementById("cpprodu4").style.display="none";
	 document.getElementById("insuranceComD").value="";	 
	 }
 function begincompare(){
	 var  insuranceCom= document.getElementById("insuranceCom").value;
	 var  insuranceComB= document.getElementById("insuranceComB").value;
	 var  insuranceComC= document.getElementById("insuranceComC").value;
	 location.href="${base}/shop/non_auto!list.action?eriskType=B&productA=200731159101101&productB=106101081";
 }	 
</script>
</head>
<body class="memberCenter" onLoad="moveR();closecp();" >
	<#include "/WEB-INF/template/shop/header.ftl">
	<div class="body memberCenterIndex">
		<div class="bodyLeft">
			<div class="memberInfo">
	           <table  class="listTable">
	                <#if (products != null && products?size > 0)>
		               <#list products as list>
	                      <tr>
	                           <td><input type="text" value="${list.id}" /></td>
	                           <td><input type="text" value="${list.name}" /></td>
	                           <td><a onclick="showcp('${list.name}','${list.id}','${list.id}','${list.name}')">加入试算</a></td>
	                     </tr>
	                   </#list>
	               </#if>
	           </table>
	       </div>
	<div id="cpdiv" style="Z-INDEX: 1; LEFT:0;  POSITION: absolute; bottom: 1px; display:none; top:0;bottom:0; ">
	<div class="comparedivcont">
		<div class="comparedetail" style="display:none;" id="cpprodu1"><input type="hidden" name="insuranceCom"  id="insuranceCom"  /><p><span  id="productname1"></span><br/><span class="dblue cpdelico"><a href="#" onClick="closecp1()">移除</a></span></p><div class="clear"></div></div>
		<div class="comparedetail" style="display:none;" id="cpprodu2"><input type="hidden" name="insuranceComB"  id="insuranceComB"  /><p><span  id="productname2"></span><br/><span class="dblue cpdelico"><a href="#" onClick="closecp2()">移除</a></span></p><div class="clear"></div></div>
		<div class="comparedetail" style="display:none;" id="cpprodu3"><input type="hidden" name="insuranceComC"  id="insuranceComC"  /><p><span  id="productname3"></span><br/><span class="dblue cpdelico"><a href="#" onClick="closecp3()">移除</a></span></p><div class="clear"></div></div>
		<div class="comparedetail" style="display:none;" id="cpprodu4"><input type="hidden" name="insuranceComD"  id="insuranceComC"  /><p><span  id="productname4"></span><br/><span class="dblue cpdelico"><a href="#" onClick="closecp4()">移除</a></span></p><div class="clear"></div></div>
	  	<div class="comparebtn"><a onclick="javascript:begincompare();subclosecp();"  target="wrap"><img src="webImages/btncp.gif" width="72" height="28" /></a></div>
	  	<div class="clear"></div>
	</div>
	</div>
	<#include "/WEB-INF/template/shop/friend_link.ftl">
	</div>
	<div class="blank"></div>
	<#include "/WEB-INF/template/shop/footer.ftl">
	</div></div>
</body>
</html>