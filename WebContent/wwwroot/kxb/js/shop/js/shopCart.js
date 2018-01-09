//购物车
jQuery(function(){
	jQuery("#addShopCart").click( function(){
		var agreeInsure =  document.getElementById("agreeInsure");
	 if(!agreeInsure.checked){
		 jQuery('#agreeInsure').parent().addClass("red");
		 art.dialog.alert("请仔细阅读投保须知，<br>并勾选“我接受以上投保声明”");
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
		    		art.dialog.open(sinosoft.front+'/block/art_login.shtml',	 {id: 'loginArtWindow',title: '您尚未登录',width:'338px', height:'480px',fixed:true}, false);
					return;
				}else{
					jQuery('#shopCartNo').html(data.shopCartNo);
					if (data.status == "Y") {
						window.location.href= sinosoft.base + "/shop/member_shopcart!getShopCartINF.action";
					}else if(data.status == "N"){
						art.dialog({
						       lock: true,
						       icon:"error",
						       content: '加入购物车失败，请重新添加'
						});
						return;
					}else if(data.status == "M"){
						art.dialog({
							   lock: true,
						       icon:"error",
						       content: '购物车中订单渠道来源不同,不能加入购物车'
						});
						return;
					}else if (data.status == "X") {
						art.dialog({
							   lock: true,
						       icon:"error",
						       content: '该产品不能加入购物车'
						});
						return;
					}else{
						art.dialog({
							   lock: true,
						       icon:"error",
						       content: '购物车中同一被保人同一产品存在不同订单中'
						});
						return;
					}
				} 
			}
	   });  
	});
});

//将产品加入购物车
jQuery(".zd_addcart").click(function(){
	var orderSn = jQuery(this).siblings().val();
	jQuery.ajax({
	    url:sinosoft.base+"/shop/shopping_cart!addOrders.action?orderSn="+orderSn,
	    type:"post",
		dataType:"json",
		success:function(data) {
			jQuery("#shopCartNo").val(data.shopCartNo);
			if(data.status == "NOLOG"){
				art.dialog.data('base', ''); 
	    		art.dialog.data('front', sinosoft.front);
	    		art.dialog.data('shopcarturl', sinosoft.base + "/shop/member_shopcart!getShopCartINF.action");
	    		art.dialog.data('shopCartNo',data.shopCartNo);
	    		art.dialog.data('orderSn',orderSn);
	    		art.dialog.open(sinosoft.front+'/block/art_login.shtml',	 {id: 'loginArtWindow',title: '您尚未登录',width:'338px', height:'480px',fixed:true}, false);
				return;
			}else{
				jQuery('#shopCartNo').html(data.shopCartNo);
				if (data.status == "Y") {
					window.location.href= sinosoft.base + "/shop/member_shopcart!getShopCartINF.action";
				}else if(data.status == "N"){
					art.dialog({
					       lock: true,
					       icon:"error",
					       content: '加入购物车失败，请重新添加'
					});
					return;
				}else if(data.status == "M"){
					art.dialog({
						   lock: true,
					       icon:"error",
					       content: '购物车中订单渠道来源不同,不能加入购物车'
					});
					return;
				}else if (data.status == "X") {
					art.dialog({
						   lock: true,
					       icon:"error",
					       content: '该产品不能加入购物车'
					});
					return;
				}else{
					art.dialog({
						   lock: true,
					       icon:"error",
					       content: '购物车中同一被保人同一产品存在不同订单中'
					});
					return;
				}
			} 
		}
   }); 
//	art.dialog({
//	       time: 2,
//	       icon:"succeed",
//	       content: '此产品已加入购物车'
//	   });
})

function selectg(){
    jQuery("input[name=checkOrderSn]").each(function() {
        if(jQuery(this).attr("checked")==true){
            jQuery(this).parents("ul").addClass("select_shop_ul");
        }else{
            jQuery(this).parents("ul").removeClass("select_shop_ul");
        }
   })
}

//批量收藏选中商品
jQuery("#collect-shop").click(function(){
		if (jQuery("#YesLogin").is(':hidden')) {
			artLoginShopCart();
			return;
		}
        var shopid = "";
        jQuery("input[name=checkOrderSn]").each(function() {
                if(jQuery(this).attr("checked")==true){
                  shopid += (","+jQuery(this).val());
                }
              });

            if(shopid!=""){
            jQuery.ajax({
    				type : 'get',
    				url : sinosoft.base+'/shop/favorites!addOrder.action?orderSns=' + shopid,
    				dataType : 'html',
    				success : function(de) {

    					if (de == 'notlogin') {
    						artLogin();//使用弹出窗口登录
    					} else {
    						art.dialog({
    			                  time: 2,
    			                  icon:"succeed",
    			                  content: '选中商品成功移入收藏夹'
    			              }); 
    					}
    				}
    			});
               
            }else{
              art.dialog({
                  lock: true,
                  icon:'warning',
                  content: '请至少选择一件商品'
              });  
            }

});

//批量删除选中商品
jQuery("#delete-cartshop").click(function(){
		if (jQuery("#YesLogin").is(':hidden')) {
			artLoginShopCart();
			return;
		}
        var shopids ="";
          jQuery("input[name=checkOrderSn]").each(function() {
                if(jQuery(this).attr("checked")==true){
                  shopids += ","+jQuery(this).val();
                }
              });
          
       if(shopids!=""){
            art.dialog({
            			id: 'delID',
            			lock: true,
                        title:"删除商品",
                        content: '您真的要删除选中的商品吗？<br>可以把商品移入收藏夹，下次再购买',
                        button: [
                            {
                                name: '移入收藏夹',
                                callback: function () {
                                	jQuery.ajax({
                        				type : 'get',
                        				url : sinosoft.base+'/shop/favorites!addOrder.action?orderSns=' + shopids,
                        				dataType : 'html',
                        				success : function(de) {

                        					if (de == 'notlogin') {
                        						artLogin();//使用弹出窗口登录
                        					} else {
                        						art.dialog({ id: 'delID' }).close()
                        						art.dialog({
        							                  time: 2,
        							                  icon:'succeed',
        							                  content: '成功移入收藏夹并删除成功'
        							              });
                        						window.location.href=sinosoft.base + '/shop/member_shopcart!deleteInfo.action?orderSn='+ shopids;
                        					}
                        				}
                        			});
                                    return false;
                                },
                                focus: true
                            },
                            {
                                name: '删除商品',
                                callback: function () {
                                	window.location.href=sinosoft.base + '/shop/member_shopcart!deleteInfo.action?orderSn='+ shopids;
                                }
                            }
                        ]
                    });    


       }else{
    	   
           art.dialog({lock: true,icon:'warning',content: '请至少选择一件商品'});  
       }

         
});
