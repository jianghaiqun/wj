
/* 
*@des:小能调用js
*@maker:guanyulong
*@time:2016-09-20
*/
    var XiaoNengUseUrl_kxb=window.location.pathname;
	if(XiaoNengUseUrl_kxb.indexOf(".action")>-1&&XiaoNengUseUrl_kxb.indexOf("!")>-1){
		//alert("订单填写，订单提交，支付成功，购物车");
		if(XiaoNengUseUrl_kxb.indexOf("alipay!returnUrl.action")>-1||XiaoNengUseUrl_kxb.indexOf("order_config_new!pay.action")>-1){
			//alert("在线支付||支付成功");
			if('${callBackAmount}'!=null||'${callBackAmount}'!=""||'${callBackAmount}'!="undefind"){
				NTKF_PARAM["orderprice"]='${callBackAmount}';
			}else{
				NTKF_PARAM["orderprice"]=jQuery("#p_price").html();
			}
			NTKF_PARAM["orderid"]=GetQueryString("orderSn");
		}else if(XiaoNengUseUrl_kxb.indexOf("order_config_new!buyNow.action")>-1||
				XiaoNengUseUrl_kxb.indexOf("order_config_new!sendDirectUrl.action")>-1||
				XiaoNengUseUrl_kxb.indexOf("order_config_new!keepInput.action")>-1||
				XiaoNengUseUrl_kxb.indexOf("order_config_new!buyNowUpdate.action")>-1){
			//alert("确认保单||填写投保信息")
		}else if(XiaoNengUseUrl_kxb.indexOf("member_shopcart!getShopCartINF.action")>-1){
			//alert("购物车");
		    var XiaoNengUsePayit_KXB=0;//总价
		    var XiaoNengUseItemsArr_KXB=new Array();
		    var  XiaoNengUseItemsArrUseI_KXB=0;
		    jQuery("input[name=checkProductId]").each(function() {
		    	XiaoNengUseItemsArr_KXB[ XiaoNengUseItemsArrUseI_KXB]={id:jQuery(this).val(),count:"1"};
		          ++ XiaoNengUseItemsArrUseI_KXB;
		    }) 
		    XiaoNengUseItemsArrUseI_KXB=0;
		    jQuery("input[name=amountPlasList]").each(function() {
		    	XiaoNengUsePayit_KXB = Number(XiaoNengUsePayit_KXB) + Number(jQuery(this).val());
		    }) 
		    if(XiaoNengUseItemsArr_KXB.length<=0){
		    	XiaoNengUseItemsArr_KXB[0]={id:"",count:""}; 
		    }
			NTKF_PARAM["ntalkerparam"]={
		    		cartprice:XiaoNengUsePayit_KXB,
		    		items:XiaoNengUseItemsArr_KXB
		    };

		} 
	}else{
		//alert("首页，列表页，详细页");
		if(jQuery("#RiskCode").val()!='undefind'&&jQuery("#RiskCode").val()!=''&&jQuery("#RiskCode").val()!=null){
			NTKF_PARAM["itemid"]=jQuery("#RiskCode").val();
		}else{
		if(XiaoNengUseUrl_kxb=="/index.shtml"){
			//首页
		}else if(XiaoNengUseUrl_kxb!="/index.shtml" && (XiaoNengUseUrl_kxb.split('/')).length>2){//从1开始，首页length=2
				//alert("列表页");
        	   var XiaoNengUseBrand_KXB = jQuery(".separator1").html();
        		var XiaoNengUseCategory_KXB = jQuery(".daohang").html();
        		if(XiaoNengUseBrand_KXB==null||XiaoNengUseBrand_KXB==""||XiaoNengUseBrand_KXB=="undefind"){
        			XiaoNengUseBrand_KXB="";
        			XiaoNengUseCategory_KXB="";
        		}else{
        		XiaoNengUseBrand_KXB=XiaoNengUseBrand_KXB.replace(/[^\u4e00-\u9fa5]/gi,"")
        		XiaoNengUseCategory_KXB=XiaoNengUseCategory_KXB.replace(/[^\u4e00-\u9fa5]/gi,"");
        		XiaoNengUseCategory_KXB=XiaoNengUseCategory_KXB.replace("您所在的位置首页","");
        		XiaoNengUseCategory_KXB=XiaoNengUseCategory_KXB.replace(XiaoNengUseBrand_KXB,"");
        		XiaoNengUseCategory_KXB=XiaoNengUseCategory_KXB.replace("险","险;");
        		//XiaoNengUseCategory_KXB=XiaoNengUseCategory_KXB.Substring(0,XiaoNengUseCategory_KXB.Length-1); 
        	    }
        		NTKF_PARAM["ntalkerparam"]={
        		　　		category:XiaoNengUseCategory_KXB,   
        		　　		brand:XiaoNengUseBrand_KXB    
        		};
 
        			}
		    
		}
		} 
 
function GetQueryString(name){//获取url中某个值 如 orderid=1111  name=order 取出的就是1111
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

 


 
 


