<#assign s=JspTaglibs["/WEB-INF/struts-tags.tld"] />
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>线下核保信息填写</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/shop/css/re_shops.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/skins/default.css"/>

 
<#include "/wwwroot/kxb/block/kxb_custom_header.shtml">
</head>
<body class="up-bg-qh">
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">

<form id="InfoForm"  action="" method="post" enctype ="multipart/form-data"> 
<input type="text"  id="underwritingOfflineInfo.productId" name="underwritingOfflineInfo.productId" style="display:none" class="linedown_s"  />
<input type="text"  id="underwritingOfflineInfo.productName" name="underwritingOfflineInfo.productName" style="display:none" class="linedown_s"  />
<input type="text"  id="underwritingOfflineInfo.situationDesc" name="underwritingOfflineInfo.situationDesc" style="display:none" class="linedown_s"  />
<input type="text"  id="imagePath" name="imagePath" style="display:none" class="linedown_s"  />
  <div class="weaper linedown_box">

        <div class="linedowncon">
          <h3 class="linedown_tit">${productName}</h3>
              <p class="line_kf">• 线下核保信息录入<span>不会填？有疑问？请联系<a class="fix-onlineqq " href="javascript:void(0);" vlpageid="xiaoneng" rel="nofollow" exturl="http://www.kaixinbao.com/xiaoneng" id="qqwap1" onclick="return(VL_FileDL(this));return false;"> 在线客服</a></span></p>
              <div class="lineform">
              <dl>
                <dt><em>*</em>姓名：</dt>
                <dd><div class="linett">
                  <input type="text" data-verify="NOTNULL|姓名不为空&&CHANDEH|请输入正确的姓名，必须和投保人相符&&LENGTH[2,20]|姓名的长度不正确" id="underwritingOfflineInfo.name" name="underwritingOfflineInfo.name" class="linedown_s"  />
                  <label class="mes_tip_p" for="underwritingOfflineInfo.name">必须与投保人证件相符的姓名</label>
                </div></dd>
              </dl>
              <dl>
                <dt><em>*</em>手机：</dt>
                <dd><div class="linett">
                  <input type="text" data-verify="NOTNULL|手机号不为空&&TELPHONE|输入错误，请输入11位手机号，必须和投保人一致"  id="underwritingOfflineInfo.mobile" name="underwritingOfflineInfo.mobile" type="tel" maxlength="11"  class="linedown_s" />
                  <label class="mes_tip_p" for="underwritingOfflineInfo.mobile">必须和投保人手机号一致</label>
                </div></dd>
              </dl>
              <dl>
                <dt><em>*</em>邮箱：</dt>
                <dd><div class="linett">
                  <input type="text" data-verify="NOTNULL|电子邮箱不为空&&EMAIL|输入错误，正确的邮箱才能接收反馈结果&&LENGTH[5,50]|电子邮箱的长度不正确" id="underwritingOfflineInfo.email" name="underwritingOfflineInfo.email" class="linedown_s"  />
                  <label class="mes_tip_p" for="underwritingOfflineInfo.email">接收反馈结果通知</label>
                </div></dd>
              </dl>
              <dl>
                <dt><em>*</em>情况说明：</dt>
                <dd class="line_desc_dd"><div class="linetcon">
 
                  <textarea  maxlength="500" data-verify="NOTNULL|情况说明不为空&&LENGTH[5,500]|请输入至少5字以上被保人健康描述" class="linedown_text" name="underwritingOfflineInfo.situationDescUse" id="underwritingOfflineInfo.situationDescUse"></textarea>
		  <em class="text_phontnum"><i id="changeNums">0</i>/500</em>
                  <label class="mes_tip_p" for="underwritingOfflineInfo.situationDescUse">被保人健康状况描述 </label>
                </div></dd>
              </dl>
              <dl>
                <dt><em>*</em>报告上传：</dt>
                <dd>
                  <p class="linedown_gip">（上传照片内容需清晰显示带有被保险人姓名及医院盖章的真实诊断病例，检查报告等）</p>
                
		  <div class="linedown_upfile">
                    <div class="cont_upd">
                      <p class="upload">
                        <span>+上传报告</span>
                        <input class="file_up" type="file" data-name="card_01" name="underwritingOfflineImagesList[0].file" value="" />
                        <input type="hidden" value="" name="underwritingOfflineImagesList[0].id" />
                        <input type="hidden" value="" name="underwritingOfflineImagesList[0].filePath" />
			<input type="hidden" value="" name="underwritingOfflineImagesList[0].imageUrl" />
                        <em></em>
                      </p>
		    <div class="clear"></div>
		    </div>    
                    <p>单张图片不超过5MB，尺寸不小于300*300px，支持jpg,png和bmp</p>
                  </div>  

                </dd>
              </dl>
              <div class="linedown_sub">
                  <p>注：本次核保结果不影响其他产品的购买核保</p>
                  <a href="javascript:void(0);" class="linedownbtns" id="linebtn" >确定提交</a><span class="line_btnsend" style="display:none;">提交中<em class="ani_dot">...</em></span>
                  <a href="javascript:void(0);" onclick="window.history.back(); return false;" class="linedownbtns linedown_f">取消返回</a>
              </div>
               </div>
        </div>
    </div>
 
</form> 
 

<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<script type="text/javascript" src="${staticPath}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${staticPath}/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticPath}/js/jquery.form.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/re_base.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/re_header.js"></script>
<script type="text/javascript" src="${staticPath}/js/artDialog.js"></script>
<script type="text/javascript" src="${staticPath}/js/login.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/re_footer.js"></script> 
<script type="text/javascript" src="${staticPath}/js/redesign/validate.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/re_offline.js"></script>
<script type="text/javascript" src="${staticPath}/js/redesign/xiaoneng_CustomerService.js"></script>
<script type="text/javascript" src="http://dl.ntalker.com/js/xn6/ntkfstat.js?siteid=kf_9401" charset="utf-8"></script> 
<script>
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

 function submitForm(){//上传图片
 	var BrowserIs = navigator.userAgent.toLowerCase() ;
 	if(BrowserIs.indexOf("firefox") > 0)
	{
		BrowserIs = "BrowserIsFireFox";
	}
	document.getElementById("underwritingOfflineInfo.productId").value = getParam("productId");
	document.getElementById("underwritingOfflineInfo.productName").value = getParam("productName");
	document.getElementById("underwritingOfflineInfo.situationDesc").value =  document.getElementById("underwritingOfflineInfo.situationDescUse").value;
        var turl= "../Editor/filemanager/upload/UploaderImgServlet.jsp";
        var options = { 
        		url:turl, 
        		async:true,
        		type:"POST", 
        		data:{productId:getParam("productId"),BrowserIs:BrowserIs },
        		dataType: "json",
        		resetForm:false,
	        	success: function(data){ 
 
				 }
        	};  
            jQuery('#InfoForm').ajaxSubmit(options); 
 
 
}


 

function afterUpLoad2(message){
	if(message=="success"){
		window.location.href= sinosoft.base + "/shop/underwriting_offline!success.action?productId="+${productId};
	}else {
	      art.dialog({
			  icon: 'error',
			  content:message
		  });
		    jQuery(".file_moddrop").remove();
    		jQuery('#linebtn').show();
        	jQuery('#linebtn').siblings('.line_btnsend').hide();
        	
	}
}

function afterUpLoad(flag, content,path) {//上传图片后
	document.getElementById("imagePath").value =  path;
	if (content == "Succ") {
		     var turl= sinosoft.base + "/shop/underwriting_offline!saveApply.action";
		     var options = { 
        		url:turl, 
        		async:true,
        		type:"POST", 
        		data:{productId:getParam("productId") },
        		dataType: "json",
        		resetForm:false,
	        	success: function(data){ 
 
				 }
        	};  
            jQuery('#InfoForm').ajaxSubmit(options); 
			} else {
			      art.dialog({
					  icon: 'error',
					  content:flag
				  });

			jQuery(".file_moddrop").remove();
    		jQuery('#linebtn').show();
        	jQuery('#linebtn').siblings('.line_btnsend').hide();
			}
}


 /* 录入信息校验 */
    jQuery(document).wjDataVelify({
      tarArea : ".lineform",     //校验的DOM作用域
      tarElms : ":input[data-verify]",  //校验的DOM元素集合
      sbtBtn : "#linebtn",        //提交按钮
      callBack : function() {       //回调函数
       if(jQuery(".cont_upd .upload").length<=1){
          art.dialog({
            icon: 'error',
            id: 'errormes',
            fixed: true,
            content: '请上传报告！'
        });
          return;
        }
        submitForm();
        jQuery(this.sbtBtn).hide();
        jQuery(this.sbtBtn).siblings('.line_btnsend').show();

        //提交后创建遮罩 禁止用户点击操作 
        var domHeight = jQuery(".lineform").height();
        jQuery(".lineform").append("<div class='file_moddrop'></div>");
        jQuery(".file_moddrop").css("height",domHeight+70);

        //提交失败，返回页面 删除遮罩 让用户继续操作。
         //jQuery(".file_moddrop").remove();
      }
    });

    new wxPhotoFile({
      contArea : ".linedown_upfile",
      callBack:function(){


      }
    })
 
</script>
</body>
</html>