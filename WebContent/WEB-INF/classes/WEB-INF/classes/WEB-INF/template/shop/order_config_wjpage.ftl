<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>填写投保信息</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<!--全局通用样式-->
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/new_logo.css">
<!--购买流程通用样式-->
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/shop/css/new_shops.css"/>
<link href="${base}/template/shop/css/PayPromptSty2.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${base}/wwwroot/kxb/style/redesign/re_header.css" />
<style type="text/css">
#supinformation {width:875px;;height:145px;;overflow-y:hidden;}
.fhxg_btn, .qrgm_btn {
    border: medium none;
    color: #FFFFFF;
    cursor: pointer;
    font-size: 24px;
    font-weight: bold;
    height: 50px;
    line-height: 50px;
    text-align: center;
    width: 139px;
}      

.fhxg_btn {
    background: url("${base}/wwwroot/kxb/style/shop/images/fhxg_11.gif") repeat scroll 0 0 transparent;
    color: #868686;
    cursor: pointer;
}
</style>

<script language="javascript" type="text/javascript">

	
function  sure(){
		doSum1();
		doSum2();
		doSum3();
		init();
}
function questionDuty(){
	
		 return true;
}

function doSum1(){
	var value1 = new Number(document.getElementById("cannualincome").value);
	var value2 = new Number(document.getElementById("cmisrevenues").value);
	var sum = value1+value2;
	document.getElementById("ctotalincome1").value = sum;
	document.getElementById("ctotalincome").value = sum;
}

function doSum2(){
	init();
	var value1 = new Number(document.getElementById("lannualincome").value);
	var value2 = new Number(document.getElementById("lmisrevenues").value);
	var sum = value1+value2;
	document.getElementById("ltotalincome1").value = sum;
	document.getElementById("ltotalincome").value = sum;
}

function doSum3(){
	var value1 = new Number(document.getElementById("ltannualincome").value);
	var value2 = new Number(document.getElementById("ltmisrevenues").value);
	var sum = value1+value2;
	document.getElementById("lttotalincome1").value = sum;
	document.getElementById("lttotalincome").value = sum;
}
function notCheck1(){
	document.getElementById("live_detail1").className = "";
	document.getElementById("live_detail2").className = "required";
}
function notCheck2(){
	document.getElementById("live_detail1").className = "required";
	document.getElementById("live_detail2").className = "";
}
function init(){
		var mainAmnt = document.getElementById("mainAmnt").value;
		var ltotalincome = document.getElementById("ltotalincome").value;
		if(mainAmnt!=0){
		var pass = ltotalincome/mainAmnt;
		if(pass>=0.1){
		displayOthers();
		}
	}
}
function init2(){
		var mainAmnt = document.getElementById("mainAmnt").value;
		var ltotalincome = document.getElementById("ltotalincome").value;
		if(mainAmnt!=0){
		var pass = ltotalincome/mainAmnt;
		if(pass>=0.1){
		displayOthers();
		}else{
			document.getElementById("supInf").style.display="block";
			document.getElementById("nextPath").disabled="disabled";
		}
	}
}
function displayOthers(){
	document.getElementById("otherInf").style.display = "block";
		document.getElementById("familyNum").className = "required";
		document.getElementById("carNum").className = "required";
		var check = false;
		for(var i=0;i<=4;i++){
			check = document.getElementsByName("questionPaper.insurePurpose")[i].checked;
			if(check){
				break;
			}
		}
		if(!check){
			document.getElementsByName("questionPaper.insurePurpose")[0].checked = true;
			document.getElementsByName("questionPaper.liveStatus")[0].checked = true;
			document.getElementsByName("questionPaper.livedTime")[0].checked = true;
			document.getElementsByName("questionPaper.familySituation")[0].checked = true;
			document.getElementsByName("questionPaper.serviceYear")[0].checked = true;
			}
			var check1 = document.getElementsByName("questionPaper.insurePurpose")[4].checked;
			if(check1){
				document.getElementById("obg_detail").className = "required";
			}	
			var check2 = document.getElementsByName("questionPaper.liveStatus")[0].checked;
			if(check2){
			document.getElementById("live_detail1").className = "required";
			}
			var check3 = document.getElementsByName("questionPaper.liveStatus")[1].checked;
			if(check3){
			document.getElementById("live_detail2").className = "required";
			}
			var check4 = document.getElementsByName("questionPaper.familySituation")[1].checked;
			if(check4){
			document.getElementById("live_detail").className = "required";
			}
			document.getElementById("nextPath").disabled="";
			document.getElementById("supInf").style.display="none";
}
function doSum4(){
	doSum2();
	init2();
}
function check1(){
	document.getElementById("obg_detail").className = "required";
}
function check2(){
	document.getElementById("obg_detail").className = "";
}
function check3(){
	document.getElementById("live_detail").className = "required";
}
function check4(){
	document.getElementById("live_detail").className = "";
}
</script>
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body onLoad="sure();">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">


<div class="wrapper">
<div class="line_log">
<div class="sdong"></div>
</div>

 </div>
 <form id="orderInfoForm" name="orderInfoForm" action="order_config!questionPaperSave.action?" method="post" enctype ="multipart/form-data"  onsubmit="return questionDuty();" >
   <input value="${informationInsureds.id}" name="informationInsureds.id" type="hidden" />
   <input value="${order.orderSn}" name="questionPaper.ordID" type="hidden" />
   <input value="${order.id}" name="order.id" type="hidden" /> 
   <input value="${questionPaper.id}" name="questionPaper.id" type="hidden" />
   <input value="${mainAmnt}" name="mainAmnt" id="mainAmnt" type="hidden" />
   <input value="${informationInsureds.recognizeeName}" name="questionPaper.applicantName" type="hidden" />
   <input value="${informationInsureds.recognizeeMobile}" name="questionPaper.applicantMobile" type="hidden" />
   
    <input value="" name="questionPaper.ctotalincome" id="ctotalincome" type="hidden" />
    <input value="" name="questionPaper.ltotalincome" id="ltotalincome" type="hidden" />
    <input value="" name="questionPaper.LTtotalincome" id="lttotalincome" type="hidden" />
      
                            
   <div class="line_a shop_sptitle">
   <div class="pri ">
   <span class="CInsuranceCompany icon_C${supplierCode2}"></span>
   <div class="shop_titile">${order.productName}</div> <font size="4" color="red">
	 ￥	<span id="priceTotle_">${order.productTotalPrice}</span>
	 </font>
   </div>
</div>

<div class="shop_border">
       <div class="line_a">
            <div  class="line_at">高额问卷<img width="18" height="16" title="请详细回答以下问题。上述信息将作为您此次投保申请的补充材料。您的信息将由本公司严格为您保管，不会外泄。" src="${base}/wwwroot/kxb/style/shop/images/wen_a.png" /><font style="color:#F00;font-size:12px;font-weight:normal;margin-left:15px;">请详细回答以下问题。上述信息将作为您此次投保申请的补充材料。您的信息将由本公司严格为您保管，不会外泄。</font></div>
            <div class="form">
	           <p>
               <span style="width:100px;"><font style="color:#F00;">*</font><b>A</b>&nbsp;被保险人姓名:</span> 
               <input type="text" id="" name="questionPaper.applicantName" value="${informationInsureds.recognizeeName}" disabled="disabled" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')" class="required"  />
			</p>
             <p>
        	 <span><font style="color:#F00">*</font><b>B</b>&nbsp;地址:</span>
             <select id="applicantArea1" name="questionPaper.recognizeeArea1" onchange="getChildrenArea(this.value,'applicantArea2','-1',this);" style="width:125px;">
                    <option value="-1">--请选择--</option>
                    <#list areaList as list>
                    	<option value="${list.id}" <#if (questionPaper.recognizeeArea1==list.id)>selected="selected"</#if>>${list.name}</option>
                    </#list>
             </select>
             <select id="applicantArea2" name="questionPaper.recognizeeArea2"  style="width:125px;">
                   <option value="-1" >--请选择--</option>                  
			 </select>
             <input type="text" id="Finaladdress" name="questionPaper.recognizeeArea3" value="${questionPaper.recognizeeArea3}"  class="required" />                   
    		 </p>
    
  
	     <p>
               <span><font style="color:#F00">*</font>电话号码:</span>
               <input type="text" id="applicantMobile" name="questionPaper.applicantMobile" disabled="disabled" value="${informationInsureds.recognizeeMobile}"  class="required mobile" />           
			</p>
             <p >
               <span><font style="color:#F00">*</font><b>C</b>&nbsp;收入情况:</span>
              </p>
              
         	 <div style="diaplay:block;background:#f7f7f7;height:140px;width:705px;border:1px #CCC solid;margin-bottom:20px;"">
                	<table style="margin-top:10px;">
                    	<tr>
                        	<td style="width:105px;color:#666;"></td>
                            <td style="width:180px;color:#666;margin-right:5px;text-align:center;">预计今年</td>
                            <td style="width:180px;color:#666;margin-right:5px;text-align:center;">去年</td>
                            <td style="width:180px;color:#666;margin-right:5px;text-align:center;">两年前</td>
                        </tr>
                        <tr>
                        	<td style="height:35px;width:73px;color:#666;padding-left:32px;">年收入</td>
                            <td> <input type="text" id="cannualincome" name="questionPaper.cannualincome" onblur="doSum1();" value="${questionPaper.cannualincome}"  class="required " />              </td>
                            <td> <input type="text" id="lannualincome" name="questionPaper.lannualincome" onblur="doSum4();" value="${questionPaper.lannualincome}"  class="required " />              </td>
                            <td> <input type="text" id="ltannualincome" name="questionPaper.LTannualincome" onblur="doSum3();"  value="${questionPaper.LTannualincome}"  class="required " />              </td>
                        </tr>
                        <tr>
                        	<td style="height:35px;width:73px;color:#666;padding-left:32px;">其他收入</td>
                            <td> <input type="text" id="cmisrevenues" name="questionPaper.cmisrevenues" onblur="doSum1();" value="${questionPaper.cmisrevenues}"  class="required " />              </td>
                            <td> <input type="text" id="lmisrevenues" name="questionPaper.lmisrevenues" onblur="doSum4();" value="${questionPaper.lmisrevenues}"  class="required " />              </td>
                            <td> <input type="text" id="ltmisrevenues" name="questionPaper.LTmisrevenues" onblur="doSum3();"  value="${questionPaper.LTmisrevenues}"  class="required " />              </td>
                        </tr>
                        <tr>
                        	<td style="height:35px;width:73px;color:#666;padding-left:32px;">收入总和</td>
                          	<td> <input type="text" id="ctotalincome1" disabled="disabled" name="ctotalincome1" value=""   />              </td>
                            <td> <input type="text" id="ltotalincome1" disabled="disabled"  name="ltotalincome1" value=""   />              </td>
                            <td> <input type="text" id="lttotalincome1" disabled="disabled" name="LTtotalincome1" value=""  />              </td>
                        </tr>
                       
                    </table>
                
               </div>
              <div style="display:none;" id="otherInf">
           	   <p>
	                 <span><font style="color:#F00">*</font><b>D</b>&nbsp;保险目的:</span>
               </p>
               <p>    
                       <input type="radio" value="0" id="obj_1" class="sex_radios" onclick="check2();" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="0")> checked </#if>>&nbsp;<label class="no-border" >还款保障</label>&nbsp;&nbsp;&nbsp;
                       <input type="radio" value="1" id="obj_2" class="sex_radios" onclick="check2();" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="1")> checked </#if>>&nbsp;<label class="no-border" >家庭保障</label>&nbsp;&nbsp;&nbsp;
                       <input type="radio" value="2" id="obj_3" class="sex_radios" onclick="check2();" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="2")> checked </#if>>&nbsp;<label class="no-border" >商业捐献</label>&nbsp;&nbsp;&nbsp;
                       <input type="radio" value="3" id="obj_4" class="sex_radios" onclick="check2();" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="3")> checked </#if>>&nbsp;<label class="no-border" >抵押偿还</label>&nbsp;&nbsp;&nbsp;
                       <input type="radio" value="4" id="obj_5" class="sex_radios" onclick="check1();" name="questionPaper.insurePurpose" <#if (questionPaper.insurePurpose=="4")> checked </#if>>&nbsp;<label class="no-border" >如有其它，请列明  <input type="text" id="obg_detail" name="questionPaper.otherInsurePurpose" value="${questionPaper.otherInsurePurpose}"  class="" />        </label>&nbsp;&nbsp;&nbsp;
	                
                </p>
                 <p>
	                 <span><font style="color:#F00">*</font><b>E</b>&nbsp;居住情况:</span>
               </p>
               <p>    
                 
                <input type="radio" value="0" onclick="notCheck2();"  id="live_1" class="sex_radios" name="questionPaper.liveStatus" <#if (questionPaper.liveStatus=="0")> checked </#if>>&nbsp;<label class="no-border" >自有居所 购买价格(RMB)  <input type="text" id="live_detail1" name="questionPaper.buyPrice" value="${questionPaper.buyPrice}" class="" />        </label>&nbsp;&nbsp;&nbsp;
                 <input type="radio" value="1" onclick="notCheck1();" id="live_2" class="sex_radios" name="questionPaper.liveStatus" <#if (questionPaper.liveStatus=="1")> checked </#if>>&nbsp;<label class="no-border" >租借居所 月租金(RMB) <input type="text" id="live_detail2" name="questionPaper.rentPrice" value="${questionPaper.rentPrice}"  class="" />        </label>&nbsp;&nbsp;&nbsp;
	                
                </p>
                <p >
	                 <span style="width:225px;"><font style="color:#F00;">*</font>您在现居住地址已居住多久？</span>
               </p>
               <p>    
                <input type="radio" value="0" id="time_1" class="sex_radios" name="questionPaper.livedTime" <#if (questionPaper.livedTime=="0")> checked </#if>>&nbsp;<label class="no-border" >一年以下</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" value="1" id="time_2" class="sex_radios" name="questionPaper.livedTime" <#if (questionPaper.livedTime=="1")> checked </#if>>&nbsp;<label class="no-border" >1-3年</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" value="2" id="time_3" class="sex_radios" name="questionPaper.livedTime" <#if (questionPaper.livedTime=="2")> checked </#if>>&nbsp;<label class="no-border" >有3-6年</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" value="3" id="time_4" class="sex_radios" name="questionPaper.livedTime" <#if (questionPaper.livedTime=="3")> checked </#if>>&nbsp;<label class="no-border" >更长时间</label>&nbsp;&nbsp;&nbsp;             	                
                </p>
                  <p >
	                 <span style="width:225px;"><font style="color:#F00;">*</font><b>F</b>&nbsp;家庭情况:</span>
               </p>
               <p>    
                <input type="radio" value="0" id="family_1" class="sex_radios" onclick="check4();" name="questionPaper.familySituation" <#if (questionPaper.familySituation=="0")> checked </#if>>&nbsp;<label class="no-border" >独身</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" value="1" id="family_2" class="sex_radios" onclick="check3();" name="questionPaper.familySituation" <#if (questionPaper.familySituation=="1")> checked </#if>>&nbsp;<label class="no-border" >已婚 21岁以下孩子人数：) <input type="text" id="live_detail" name="questionPaper.childrenCount" value="${questionPaper.childrenCount}"  class="" />        </label>&nbsp;&nbsp;&nbsp;
                      	                
                </p>
         
			              <p>
               <span style="width:100px;"><font style="color:#F00">*</font>住所地居住人数:</span> 
               <input type="text" id="familyNum" name="questionPaper.peopleCount" value="${questionPaper.peopleCount}"  class=""  maxlength=50/>
  </p>
                 <p>
               <span><font style="color:#F00">*</font>拥有汽车数量:</span> 
               <input type="text" id="carNum" name="questionPaper.carNum" value="${questionPaper.carNum}"  class=""  maxlength=50/>
  </p>
  <p>
               <span>汽车品牌:</span> 
               <input type="text" id="carNum" name="questionPaper.carBrand1" value="${questionPaper.carBrand1}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  maxlength=50/>
                <input type="text" id="carNum" name="questionPaper.carBrand2" value="${questionPaper.carBrand2}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"   maxlength=50/>
                 <input type="text" id="carNum" name="questionPaper.carBrand3" value="${questionPaper.carBrand3}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"   maxlength=50/>
  </p>
   				<p >
	                 <span style="width:225px;"><b>G</b>&nbsp;其它拥有财产(如 房屋，公司，土地):</span>
               </p>
                <p>
                     <span>财产名称:</span> 
               <input type="text" id="propertyName" name="questionPaper.propertyName1" value="${questionPaper.propertyName1}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class=""  maxlength=50/>
                <input type="text" id="propertyName1" name="questionPaper.propertyName2" value="${questionPaper.propertyName2}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class=""  maxlength=50/>                
  				</p>
                 <p>
                     <span>地址:</span> 
               <input type="text" id="propertyAdress" name="questionPaper.propertyAdress1" value="${questionPaper.propertyAdress1}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class=""  maxlength=50/>
                <input type="text" id="propertyAdress1" name="questionPaper.propertyAdress2" value="${questionPaper.propertyAdress2}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class=""  maxlength=50/>                
  				</p>
                 <p>
                     <span>市值:</span>
                   <input type="text" id="propertyValue" name="questionPaper.propertyValue1" value="${questionPaper.propertyValue1}"  class=""  maxlength=50/>
                   <input type="text" id="propertyValue1" name="questionPaper.propertyValue2" value="${questionPaper.propertyValue2}"  class=""  maxlength=50/>                    
  				</p>
                  <p>
                     <span>估计价值:</span> 
                    <input type="text" id="gpropertyValue" name="questionPaper.GpropertyValue1" value="${questionPaper.gpropertyValue1}"  class=""  maxlength=50/>
                    <input type="text" id="gpropertyValue1" name="questionPaper.GpropertyValue2" value="${questionPaper.gpropertyValue2}"  class=""  maxlength=50/>
                         
  				</p>
                 <p >
	                 <span style="width:225px;"><font style="color:#F00;">*</font><b>H</b>&nbsp;您在现工作场所服务年龄？</span>
              </p>
               <p>    
                <input type="radio" value="0" id="work_1" class="sex_radios" name="questionPaper.serviceYear" <#if (questionPaper.familySituation=="0")> checked </#if>>&nbsp;<label class="no-border" >一年以下</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" value="1" id="work_2" class="sex_radios" name="questionPaper.serviceYear" <#if (questionPaper.familySituation=="1")> checked </#if>>&nbsp;<label class="no-border" >1-3年</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" value="2" id="work_3" class="sex_radios" name="questionPaper.serviceYear" <#if (questionPaper.familySituation=="2")> checked </#if>>&nbsp;<label class="no-border" >有3-6年</label>&nbsp;&nbsp;&nbsp;
                <input type="radio" value="3" id="work_4" class="sex_radios" name="questionPaper.serviceYear" <#if (questionPaper.familySituation=="3")> checked </#if>>&nbsp;<label class="no-border" >更长时间</label>&nbsp;&nbsp;&nbsp;             	                
                </p>
             <p  style="diaplay:block;height:175px;">
               <span><b>I</b>&nbsp;补充信息:</span> 
               <textarea type="textarea" id="supinformation" name="questionPaper.supInformation" value="${questionPaper.supInformation}" onkeyup="value=value.replace(/[^\a-\z\A-\Z\u4E00-\u9FA5]/g,'')"  class="" >${questionPaper.supInformation}</textarea>
  			</p>
  			 <p style="margin-bottom:40px;">
	            <span><font style="color:#F00;">*</font>日期:</span> 
	            <input id="effective1" name="effective1" type="text" disabled="disabled"  value="${questionPaper.effective}"   />
	            <input id="effective" name="questionPaper.effective" type="hidden" onclick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'})" value="${questionPaper.effective}"  />
			</p>   
  			</div>
         </div>
       </div>
    
</div>
 <div class="syr2">
  <input type="button"  class="btn300" value="  返回修改  " onclick="location.href='${base}/shop/order_config!buyNowUpdate.action?orderId=${order.id}&productId=${productId}'">  
 <input type="submit"   class="btn300" value="  下一步  " id="nextPath" disabled="disabled" ><br />
 <font color="red">请把必填信息填写完整</font>
 <font color="red" style="display:none;" id="supInf">您的去年总收入未通过校验，不能购买此产品！</font>
 </div>
 <div class="clear"></div>
   </form>
 </div>
 <script type="text/javascript" src="${base}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
 <script type="text/javascript" src="${base}/template/shop/js/calendar.js"></script>
 <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
 <#include "/wwwroot/kxb/block/community_v1.shtml">
 <script type="text/javascript" src="${base}/wwwroot/kxb/js/productDutyNew.js"></script>
<script type="text/javascript">
  pubGetChildrenArea("${questionPaper.recognizeeArea1}", "${questionPaper.recognizeeArea2}", "", ""); 
</script>
</body>
</html>