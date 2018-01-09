/*
 *@des:快速理赔调用js
 *@maker:dongqi.guo
 *@time:2014-3-21-10:20
 */
jQuery(document).ready(function() {

	jQuery(".jscxr").hide();
	jQuery("#add_bbr").click(function() {
		if (jQuery("#lipei_ul li").length == 9) {
			jQuery(".add_bd_bbr2").hide();
		}
		var lieq = jQuery("#lipei_ul li:eq(0)").clone();
		lieq.find(":input").val('');
		lieq.find(".mess").removeClass("db_yes");
		lieq.find(".mess").removeClass("db_error");
		lieq.find(".mess").html('');
		lieq.appendTo("#lipei_ul")
		alert
		jQuery(".jscxr").show();
	})

	jQuery("#orderSn").blur(function() {
		new ZMCommon().NotEmpty('orderSn', 'OrderSnErr', '订单号');
	}).focus(function() {
		new ZMCommon().SetHTML('OrderSnErr', '');
	});
	jQuery("#contactName").blur(function() {
		new ZMCommon().NotEmpty('contactName', 'LinkNameErr', '联系人');
	}).focus(function() {
		new ZMCommon().SetHTML('LinkNameErr', '');
	});
	jQuery("#ConEmail").blur(function() {
		new ZMCommon().NotEmpty('ConEmail', 'EmailErr', '联系人邮箱');
	}).focus(function() {
		new ZMCommon().SetHTML('EmailErr', '');
	});
	jQuery("#ConTel").blur(function() {
		new ZMCommon().NotEmpty('ConTel', 'TelErr', '联系人电话');
	}).focus(function() {
		new ZMCommon().SetHTML('TelErr', '');
	});
	jQuery("#ConTime").blur(function() {
		new ZMCommon().NotEmpty('ConTime', 'TimeErr', '事故发生时间');
	}).focus(function() {
		new ZMCommon().SetHTML('TimeErr', '');
	});
	jQuery("#ConDi").blur(function() {
		new ZMCommon().NotEmpty('ConDi', 'DiErr', '事故发生地点');
	}).focus(function() {
		new ZMCommon().SetHTML('DiErr', '');
	});
	jQuery("#sjms").blur(function() {
		new ZMCommon().NotEmpty('sjms', 'sjmsErr', '事故描述');
	}).focus(function() {
		new ZMCommon().SetHTML('sjmsErr', '');
	});


})
/*删除出险人*/
function Remove(cxr) {
	if (confirm('你确定要删除吗？')) {
		jQuery(cxr).parent().parent().remove();
		jQuery(".add_bd_bbr").show();
		if (jQuery("#lipei_ul li").length == 1) {
			jQuery(".jscxr").hide();
		}
	}
}

var Trim = function(value) {
	return value.replace(/(^\s*)|(\s*$)/g, "");
}
var ZMCommon = function() {
	this.GetObj = function(id) {
		return document.getElementById(id);
	};
	this.GetObjByName = function(name) {
		return document.getElementsByName(name);
	};
	this.SetHTML = function(id, value) {
		this.GetObj(id).innerHTML = value;
		this.RemoveError(id);
	};
	this.ClearErr = function(eid) {
		this.GetObj(eid).innerHTML = '';

	};
	this.ClearErrByObj = function(eobj) {
		eobj.innerHTML = '';
		eobj.className="mess";
	};
	this.AddError = function(id) {
		this.GetObj(id).className = 'db_error';
	};
	this.RemoveError = function(id) {
		this.GetObj(id).className = ' ';
	};
	this.NotEmpty = function(id, eid, pre) {
		if (Trim(this.GetObj(id).value).length == 0) {
			this.SetHTML(eid, pre + '不能为空');
			this.AddError(eid);
			return false;
		} else {
			return true;
		}
	};
	this.NotEmptyByObj = function(obj, eobj, pre) {
		if (Trim(obj.value).length == 0) {
			eobj.innerHTML = pre + '不能为空';
			eobj.className="mess db_error";
			return false;
		} else {
			eobj.className="mess";
			return true;
		}
	};
	this.IsEmpty = function(id) {
		if (Trim(this.GetObj(id).value).length == 0) {
			return true;
		} else {
			return false;
		}
	};
}

var valObj1=new ZMCommon();

valObj1.ValAll=function() {

    var retVal= 
	  ( this.NotEmpty('orderSn', 'OrderSnErr', '订单号') 
		&& this.NotEmpty('contactName','LinkNameErr','联系人') 
	    && this.NotEmpty('ConEmail', 'EmailErr', '联系人邮箱') 
	    && this.NotEmpty('ConTel', 'TelErr', '联系人电话') 
	    &&  this.NotEmpty('ConTime', 'TimeErr', '事故发生时间') 
	    && this.NotEmpty('ConDi', 'DiErr', '事故发生地点') 
	    && this.NotEmpty('sjms', 'sjmsErr', '事故描述'));
	    if(retVal){
	       var dangerNameObjs=this.GetObjByName("name");
	       var dangerNoObjs=this.GetObjByName("identityId");
	        if(dangerNameObjs.length!=dangerNoObjs.length){ retVal=false;}
	        if(retVal)
	           for(var i=0;i<=dangerNameObjs.length-1;i++)
	           {
	             if(!this.NotEmptyByObj( $(dangerNameObjs[i]).get(0) , $(dangerNameObjs[i]).parent().next().find('.mess').get(0),'姓名'))
	             {
	               retVal=false; break;
	             }
	             else if(!this.NotEmptyByObj( $(dangerNoObjs[i]).get(0),$(dangerNoObjs[i]).parent().next().find('.mess').get(0),'证件号'))
	             {
	                retVal=false;break;
	             }
	           } 
	        }  
	   
	    if(retVal)
	      {
	         var chkGuidTypes=this.GetObjByName('guidtype');
	           var hasGuidType=false ;
	           for(var i=0;i<=chkGuidTypes.length-1;i++)
	           { 
	             if(chkGuidTypes[i].checked)
	             {
	                 hasGuidType=true;
	                 break;
	             }
	           }
	           if(!hasGuidType)
	           {
	             this.SetHTML("AllGuidErr","保障类型必须选择一项");
	             this.AddError("AllGuidErr");
	             retVal=false;
	           }
	      }   
	        return retVal;
};
	  
function reportSubmit() {
	
	if (!valObj1.ValAll()) {
		return;
	}
    var chkGuidTypes=valObj1.GetObjByName('guidtype');
    var ensureType="" ;
    var j = 0;
    for(var i=0;i<=chkGuidTypes.length-1;i++)
    { 
      if(chkGuidTypes[i].checked)
      {
    	  if (i > 10 && j == 0) {
    		  
    		  if (ensureType != '') {
    			  ensureType = "人身保障：" + ensureType
    			  ensureType = ensureType.substring(0, ensureType.length - 1) + "；";
    		  }
    		  ensureType += " 财产及其它保障：";
    		  j++;
    		  
    	  }
    	  ensureType += jQuery("#"+chkGuidTypes[i].id).next().text() + "，";
      }
    }
    jQuery("#ensureType").val(ensureType.substring(0, ensureType.length - 1));
	$("#report_fm").ajaxSubmit({
		type:'post',
		url: sinosoft.base+'/shop/report_pay!submit.action',
		dataType:'json',
		success: function(data) {
			if (data.status == "success") {
				alert('您的报案已成功记录，我们会尽快与您取得联系，\n请耐心等待审核，谢谢！');
            	location.href=sinosoft.front+'/lpzs/index.shtml';
			} else {
				alert(data.message);
			}
        }
		
	});
}