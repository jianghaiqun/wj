/**
* ------------------------------------------
* @make:何洋
* @version  1.0 
* @des：专题导航js
* ------------------------------------------
*/
function queryarticle(name){
	jQuery("#catalogname").val(name) ;
	name = encodeURIComponent(encodeURIComponent(name));
	jQuery.ajax({
		url: sinosoft.base + '/shop/nav!ajaxGetArticle.action?Name='+name,
		type: "post",
		dataType: "json",
		success: function(data){
			var obj = eval(data);
			jQuery("#productsPageBar").html(obj.ZTpage) ;
			jQuery(".brand_list_top").html(obj.ZTarticle) ;
	 	}		
	});
}
//分页
function doOrder2(num){
	jQuery("#load_con").show();
    jQuery("#load_con").html("<img src='" + sinosoft.staticPath + "/images/loading.gif' />");
	searchProduct(num);
}

function newJump(){
	var JumpPage = document.getElementById('_PageBar_Index_').value;
	doOrder2(JumpPage);
}
function searchProduct(num){
	var name = jQuery("#catalogname").val() ;
	name = encodeURIComponent(encodeURIComponent(name));
	jQuery.ajax({
		url: sinosoft.base + '/shop/nav!ajaxGetArticle.action?Name='+name+'&pageIndex='+num,
		type: "post",
		dataType: "json",
		success: function(data){
			var obj = eval(data);
			jQuery("#productsPageBar").html(obj.ZTpage) ;
			jQuery(".brand_list_top").html(obj.ZTarticle) ;
	 	}		
	});
}