<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>健康问卷</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>
<style type="text/css" media="screen">
  .lineform h4{ font-size: 14px; color: #555; border-left: 4px solid #FD8824; margin-bottom:20px; margin-top: 20px; padding-left: 10px; margin-left: 20px; }
  .lineform h5{ font-size: 12px; color: #555; border-left: 2px solid #FD8824; margin-bottom:20px; margin-top: 20px; padding-left: 10px; margin-left: 20px;}
  .lineform dl dt{ width:130px; }
  .sel_radio{ width:50px; display:inline-block; text-align: center; }
  .sel_radio input,.selcs0 input{ margin-right: 4px;}
  .lineform dl dd{ min-height: 40px;}
  .selcs0{display:inline-block; margin-right: 4px; display:block; margin-bottom: 8px;}
  .lineform dl.byy  dd{width:358px; }
  .line_desc_dd .error{margin-top: 0;}
  .byy2 .linett{width:350px;}
  .linett span{ display:inline-block; width:60px;}
  .byy2 .mes_tip_p{ left:67px;}
  .lineform dl{ margin-bottom: 5px;}
</style>
</head>

<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<iframe id="iframeA" name="iframeA" src="" width="0" height="0" style="display:none;" ></iframe> 
<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
</#if> 


<form id="InfoForm"  action="" method="post" enctype ="multipart/form-data"> 
  <input type="text"  id="underwritingOfflineHealthinfo.sex" name="underwritingOfflineHealthinfo.sex" style="display:none" class="linedown_s"  />
  <input type="text"  id="underwritingOfflineHealthinfo.age" name="underwritingOfflineHealthinfo.age" style="display:none" class="linedown_s"  />
  <input type="text"  id="underwritingOfflineHealthinfo.birthday" name="underwritingOfflineHealthinfo.birthday" style="display:none" class="linedown_s"  />
  <div class="weaper linedown_box">

        <div class="linedowncon">
          <h3 class="linedown_tit f_mi" style="text-align: center">健康问卷</h3>
<!--               <p class="line_kf">线下核保信息录入<span>不会填？有疑问？请联系 <a href="">在线客服</a></span></p>
 -->              <div class="lineform">
         
         <h4 >调查资料：</h4>
              <dl>
                <dt><em>*</em>姓名：</dt>
                <dd><div class="linett">
                  <input type="text"  data-verify="NOTNULL|姓名不为空&&CHANDEH|请输入正确的姓名&&LENGTH[2,20]|姓名的长度不正确" maxlength="20" class="linedown_s"   name="underwritingOfflineInfo.name" />
                  <label class="mes_tip_p" for="line_names">请输入调查对象姓名</label>
                </div></dd>
              </dl>
               <dl>
                <dt><em>*</em>身高：</dt>
                <dd><div class="linett">
                    <input type="text" id="hel_height"  data-verify="NOTNULL|身高不为空" maxlength="3" onkeyup="this.value=this.value.replace(/\D/g,'')" class="linedown_s"  name="underwritingOfflineHealthinfo.height" />
                    <label class="mes_tip_p" for="hel_height">单位CM</label>
                </div></dd>
              </dl>
              <dl>
                <dt><em>*</em>体重：</dt>
                <dd><div class="linett">
                  <input type="text"  id="hel_eg"  data-verify="NOTNULL|体重不为空" maxlength="5" onkeyup="if(isNaN(value)){this.value=''}" class="linedown_s"  name="underwritingOfflineHealthinfo.weight" />
                   <label class="mes_tip_p" for="hel_eg">单位KG</label>
                </div></dd>
              </dl>
              <dl>
                <dt><em>*</em>邮箱：</dt>
                <dd><div class="linett">
                  <input type="text"  id="hel_eg"  data-verify="NOTNULL|邮箱不为空&&EMAIL|邮箱格式不正确" class="linedown_s"   id="underwritingOfflineInfo.email" name="underwritingOfflineInfo.email"  />
                   <label class="mes_tip_p" for="hel_eg">您的邮箱</label>
                </div></dd>
              </dl>
              <dl>
                <dt><em>*</em>电话：</dt>
                <dd><div class="linett">
                  <input type="text"  id="hel_eg"  data-verify="NOTNULL|电话不为空&&LENGTH[11,11]|电话长度不正确" class="linedown_s" name="underwritingOfflineInfo.mobile" />
                   <label class="mes_tip_p" for="hel_eg">您的电话</label>
                </div></dd>
              </dl>
              <dl>
                <dt><em>*</em>身份证号：</dt>
                <dd>
                <li><input class=" card_type"  type="hidden"  readonly="readonly" value="身份证" /></li><li><div class="linett">
                  <input type="text"  id="hel_code" data-verify="NOTNULL|身份证号不为空&&CARDTYPE|请输入正确的身份证号&&LENGTH[5,50]|身份证号长度不正确" class="linedown_s" name="underwritingOfflineHealthinfo.IdNo" />
                  <label class="mes_tip_p" for="hel_code">请输入正确的身份证号码</label>
                </div></li></dd>
              </dl>
             
               <h4>健康询问具体内容：</h4>
                 <dl>
                  <dt><em>*</em>首次发病日期：</dt>
                  <dd><div class="linett">
                    <input type="text"  id="hel_data" data-verify="NOTNULL|首次发病日期不为空" onclick="WdatePicker({skin:'whyGreen',minDate:'{%y-56}-%M-%d',maxDate:'%y-%M-{%d}'})" class="linedown_s" name="underwritingOfflineHealthinfo.firsOnsetTime" />
                  </div></dd>
                </dl>
                <dl style="margin-bottom: 15px">
                <dt><em>*</em>主要症状：</dt>
                <dd class="line_desc_dd"><div class="linetcon">
                  <textarea name="line_desc_mainSymptoms" id="line_desc_mainSymptoms" data-verify="NOTNULL|健康状况不为空&&LENGTH[1,400]|输入长度1-400字" class="linedown_text" ></textarea>
                  <label class="mes_tip_p" for="line_desc_mainSymptoms">被保人健康状况描述 </label>
                </div></dd>
		<input type="text"  id="underwritingOfflineHealthinfo.mainSymptoms" name="underwritingOfflineHealthinfo.mainSymptoms" style="display:none" class="linedown_s"  />
              </dl>
              <dl>
                  <dt><em>*</em>具体诊断：</dt>
                  <dd><div class="linett">
                    <input type="text"  id="hel_name" data-verify="NOTNULL|具体诊断不为空" maxlength="200" class="linedown_s"  name="underwritingOfflineHealthinfo.diseaseName"  />
                    <label class="mes_tip_p" for="hel_name">医院诊断的疾病名称</label>
                  </div></dd>
              </dl>
              <h5>发作情况：</h5>
              <dl>
                  <dt><em>*</em>发作持续时间：</dt>
                  <dd><div class="linett">
                    <input type="text"  data-verify="NOTNULL|发作持续时间不为空" maxlength="20" id="hel_ltime" class="linedown_s"  name="underwritingOfflineHealthinfo.attackDate" />
                    <label class="mes_tip_p" for="hel_ltime">发作持续时间</label>
                  </div></dd>
              </dl>
              <dl>
                  <dt><em>*</em>发作次数或频率：</dt> 
                  <dd><div class="linett">
                    <input type="text"  data-verify="NOTNULL|发作次数或频率不为空" maxlength="20"  id="hel_stime" class="linedown_s" name="underwritingOfflineHealthinfo.attackFrequency"  />
                    <label class="mes_tip_p" for="hel_stime">（几天一次或几周一次）</label>
                  </div></dd>
              </dl>
              <dl>
                  <dt><em>*</em>最近一次发作时间：</dt>
                  <dd><div class="linett">
                    <input type="text"  data-verify="NOTNULL|最近一次发作时间不为空"  id="hel_stime" onclick="WdatePicker({skin:'whyGreen',minDate:'{%y-56}-%M-%d',maxDate:'%y-%M-{%d}'})" class="linedown_s" name="underwritingOfflineHealthinfo.attackLastDate" />
                  </div></dd>
              </dl>
               <h5>治疗情况：</h5>
               <dl class="byy">
                  <dt><em>*</em>目前是否仍接受治疗：</dt>
                  <dd style="width:120px; padding-top: 8px"><div class="linett">
                  <label class="sel_radio"><input type="radio" checked="checked" name="radio" value="1">是</label>
                  <label class="sel_radio"><input type="radio" name="radio" value="2">否</label>
		  <input type="text"  id="underwritingOfflineHealthinfo.isTreat" name="underwritingOfflineHealthinfo.isTreat" style="display:none" class="linedown_s"  />
                  </div></dd>
                   <dd style="display:none;" id="selc_time"><div class="linett">
                    <span  style="width:100px">停止治疗的时间：</span><input type="text"  id="hel_stoptime" style="width:100px" onclick="WdatePicker({skin:'whyGreen',minDate:'{%y-56}-%M-%d',maxDate:'%y-%M-{%d}'})" class="linedown_s" name="underwritingOfflineHealthinfo.stopTreat" />
                    
                  </div></dd>
		  		   
              </dl>
               <dl class="byy byy2">
                  <dt><em>*</em>治疗方法：</dt>
                  <dd><div class="linett">
                    <span>手术治疗：</span><input type="text"  id="hel_sactive1" class="linedown_s" maxlength="50" name="underwritingOfflineHealthinfo.treatSurgery" />
                    <label class="mes_tip_p" for="hel_sactive1">手术治疗，手术名称</label>
                  </div></dd>
                  <dd><div class="linett">
                    <span>药物治疗：</span><input type="text"  id="hel_sactive2" class="linedown_s" maxlength="50" name="underwritingOfflineHealthinfo.treatDrug" />
                    <label class="mes_tip_p" for="hel_sactive2">药物治疗，药物名称</label>
                  </div></dd>
                  <div class="clear"></div>
                  <dt></dt>
                  <dd><div class="linett">
                    <span>物理治疗：</span><input type="text"  id="hel_sactive3" class="linedown_s" maxlength="50" name="underwritingOfflineHealthinfo.treatPhysical" />
                    <label class="mes_tip_p" for="hel_sactive3">物理治疗(如针灸、按摩、红外线等 )</label>
                  </div></dd>
                  <dd><div class="linett">
                    <span>其他：</span><input type="text"  id="hel_sactive4" class="linedown_s" maxlength="50" name="underwritingOfflineHealthinfo.treatOther" />
                    <label class="mes_tip_p" for="hel_sactive4">其他请具体说明</label>
                  </div></dd>
              </dl>
               <dl>
                  <dt style="padding-top: 15px"><em>*</em>治疗效果：</dt>
                  <dd><div class="linett">
                     <label class="selcs0" for="r1"><input type="radio" checked="checked" id="r1" name="radios2" value="01">治愈，无不适应症状，无复发</label>
                     <label class="selcs0" for="r2"><input type="radio" id="r2" name="radios2" value="02">症状缓解</label>
                     <label class="selcs0" for="r3"><input type="radio" id="r3" name="radios2" value="03">未治愈，治疗无效果，症状无缓解或加重</label>
                  </div></dd>
		    <input type="text"  id="underwritingOfflineHealthinfo.treatEffect" name="underwritingOfflineHealthinfo.treatEffect" style="display:none" class="linedown_s" value="01" />
                </dl>
                     <h5>其他补充说明：</h5>
                <dl>
                <dt>补充说明：</dt>
                <dd class="line_desc_dd"><div class="linetcon">
                  <textarea name="line_desc_otherSupplement" id="line_desc_otherSupplement" data-verify="LENGTH[0,500]|输入长度0-500字"  class="linedown_text" ></textarea>
                  <label class="mes_tip_p" for="line_desc_otherSupplement">补充说明 </label>
                </div></dd>
	        <input type="text"  id="underwritingOfflineHealthinfo.otherSupplement" name="underwritingOfflineHealthinfo.otherSupplement" style="display:none" class="linedown_s"  />
              </dl>
              <div class="linedown_sub">
                  <a href="javascript:void(0);" class="linedownbtns" id="linebtn">确定提交</a><span class="line_btnsend" style="display:none;">提交中<em class="ani_dot">...</em></span>
		  <a href="javascript:void(0);" onclick="window.history.back(); return false;" class="linedownbtns linedown_f">取消返回</a>
              </div>
               </div>
        </div>
	    </div>
</form>





<#if (typeFlag !="02")!>
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
</#if> 
<script type="text/javascript" src="${staticPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${staticPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${staticPath}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/re_base.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/re_header.js"></script>
<script type="text/javascript" src="${staticPath}/js/artDialog.js"></script>
<script type="text/javascript" src="${staticPath}/js/login.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/re_footer.js"></script>
<script type="text/javascript" src="${staticPath}/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/validate.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/re_offline.js"></script>

<script type="text/javascript" src="${staticPath}/js/redesign/xiaoneng_CustomerService.js"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script>

<script>
    /* 录入信息校验 */
    $(document).wjDataVelify({
      tarArea : ".lineform",     //校验的DOM作用域
      tarElms : ":input[data-verify]",  //校验的DOM元素集合
      sbtBtn : "#linebtn",        //提交按钮
      callBack : function() {       //回调函数

          var mes = true;
          jQuery(".byy2 input").each(function(index, el) {
              var m = jQuery(el).val();
	      // alert("ajax 提交表单")
              if(m!=""){
                  mes = false;
              }
          });
           if(mes){
              jQuery(".byy2 dd:eq(2) .linett").after('<div class="error" style="display: block;"><em class="icon_e"></em><span>至少填写一种治疗方法</span></div>');
              return
           }else{
             jQuery(".byy2 dd .error").remove();
           }
		submitInfoForm();

      }
    });

    jQuery(".byy2 input").focus(function(){
        jQuery(".byy2 dd .error").remove();
    });

    jQuery(document).ready(function($) {
      selc();
    });

     jQuery("[name='radio']").click(function(event) {
       selc();
     });

    function selc(){
       var sel = jQuery("[name='radio']:checked").val();
      if(sel=="2"){
          jQuery("#selc_time").show();
	  jQuery("[name='underwritingOfflineHealthinfo.isTreat']").val("N");
      }else{
          jQuery("#selc_time").hide();
          jQuery("[name='underwritingOfflineHealthinfo.isTreat']").val("Y");
      }
    }

     jQuery("[name='radios2']").click(function(event) {
        var sel = jQuery("[name='radios2']:checked").val();
	 jQuery("[name='underwritingOfflineHealthinfo.treatEffect']").val(sel)
     });


	function afterUpLoad(message){
		alert(message)
	}

	function submitInfoForm() {
	setmassage();
	var turl= sinosoft.base + "/shop/underwriting_offline!saveHealthyQuestion.action";
        var options = { 
        		url:turl, 
        		async:true,
        		type:"POST", 
        		data:{productId:getParam("productId") },
        		dataType: "json",
        		resetForm:false,
	        	success: function(data){
				if(data.massage=="success"){
					window.location.href= sinosoft.base + "/shop/underwriting_offline!success.action?productId="+${productId};
				}else {
				      art.dialog({
						  icon: 'error',
						  content:data.massage
					  });
					
				}
			}
        	};  
            jQuery('#InfoForm').ajaxSubmit(options); 
	 
	}



	function setmassage() {
         jQuery("[name='underwritingOfflineHealthinfo.mainSymptoms']").val ( jQuery("#line_desc_mainSymptoms").val() ) ;//主要症状
	 jQuery("[name='underwritingOfflineHealthinfo.otherSupplement']").val ( jQuery("#line_desc_otherSupplement").val() ) ;//补充说明：
	 var UUserCard = jQuery("[name='underwritingOfflineHealthinfo.IdNo']").val();
	 var date = new Date();
	 var year = date.getFullYear();
	 var birthday_year =  UUserCard.substring(6, 10) + "-" + UUserCard.substring(10, 12) + "-" + UUserCard.substring(12, 14); 
	 var userage= year - parseInt(UUserCard.substr(6,4));
	 if (parseInt(UUserCard.substr(16, 1)) % 2 == 1) {
	   jQuery("[name='underwritingOfflineHealthinfo.sex']").val ( "M" ) ;//补充说明：
	 } else {
	   jQuery("[name='underwritingOfflineHealthinfo.sex']").val ( "F" ) ;//补充说明：
	 } 
	 jQuery("[name='underwritingOfflineHealthinfo.age']").val ( userage ) ;//年龄：
	 jQuery("[name='underwritingOfflineHealthinfo.birthday']").val ( birthday_year ) ;//生日
	}

	function getParam(paramName)
	{
		paramValue = "";
		isFound = false;
		if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=")>1)
		{
		    arrSource = unescape(this.location.search).substring(1,this.location.search.length).split("&");
		    i = 0;
		    while (i < arrSource.length && !isFound)
		    {
			if (arrSource[i].indexOf("=") > 0)
			{
			     if (arrSource[i].split("=")[0].toLowerCase()==paramName.toLowerCase())
			     {
				paramValue = arrSource[i].split("=")[1];
				isFound = true;
			     }
			}
			i++;
		    }   
		}
	   return paramValue;
	}
 
	 

</script>
 
 
</body>
</html>