//购物车
jQuery(function(){
	jQuery("#addShopCart").click( function(){
		var agreeInsure =  document.getElementById("agreeInsure");
	 if(!agreeInsure.checked){
		 alert("请确认您同意了以上投保声明！ ");
		 return ;
	 }
		jQuery.ajax({
		    url:sinosoft.base+"/shop/shopping_cart!addOrders.action?orderSn="+orderSn,
		    type:"post",
			dataType:"json",
			success:function(data) {
				jQuery("#shopCartNo").val(data.shopCartNo);
				if(data.status == "NOLOG"){
//					var localURL=window.location.href;
//		    		var loginBackURL=encodeURIComponent(localURL);
//		    		window.location.href= sinosoft.base + "/shop/member!newLogin.action?loginBackURL="+loginBackURL; 
		    		art.dialog.data('base', sinosoft.base);//将服务
		    		art.dialog.data('front', sinosoft.front);
		    		art.dialog.data('shopcarturl', sinosoft.base + "/shop/member_shopcart!getShopCartINF.action");
		    		art.dialog.data('shopCartNo',data.shopCartNo);
		    		art.dialog.data('orderSn',orderSn);
		    		art.dialog.open(sinosoft.front+'/template/art_login.html',	 {id: 'loginArtWindow',title: '登录窗口',width:363,fixed:true}, false);
					return;
				}else{
					jQuery('#shopCartNo').html(data.shopCartNo);
					if (data.status == "Y") {
						window.location.href= sinosoft.base + "/shop/member_shopcart!getShopCartINF.action";
					}else if(data.status == "N"){
						alert("加入购物车失败，请重新添加");
						return;
					}else if(data.status == "M"){
						alert("购物车中订单渠道来源不同,不能加入购物车");
						return;
					}else{
						alert("购物车中同一被保人同一产品存在不同订单中");
						return;
					}
				} 
			}
	   });  
	});
});
