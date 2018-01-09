 jQuery(function(){
            jQuery(".visa_nav_one").hover(
                  function(){
                      jQuery(this).children(".visa_nav_a").addClass("visa_nav_zindex");
                      jQuery(this).children(".main_nav_sbox").show();
                    
                  },function(){
                      jQuery(this).children(".visa_nav_a").removeClass("visa_nav_zindex");
                      jQuery(this).children(".main_nav_sbox").hide();
                  }
              );
            
            /*给最后一个商品添加清楚虚线的样式*/
            jQuery(".visa_shop").last().addClass("clear_bor_d");
         });
 
 jQuery(function(){
	 //焦点图切换
	jQuery('#change_33 div.changeDiv').soChange({
		thumbObj:'#change_33 .ul_change_a2 span',
		thumbNowClass:'on',
		changeTime:4000//自定义切换时间为4000ms
	});
});
 var car_box = jQuery('.car_city_select');
 jQuery('.car_select_open').click(
     function()
           {
              if(car_box.is(":visible")){  car_box.hide();}else{car_box.show();}
           });
jQuery('.car_list_lis li').click(
       function(){
           jQuery(".car_select_ps").html(jQuery(this).text()) 
       }
   )

var cpslink="#";
function getlinks(strlink){
   if(car_box.is(":visible")){  car_box.hide();}else{car_box.show();}
  cpslink=strlink;
}
/**
 * 过度页面跳转
 */
function guodus(pagelink){
      if(cpslink==000 || cpslink == '#' || cpslink == 'javascript:void(0);' ){
        //history.go(0);
      }else{
        var win =window.open("");
        win.location.href=cpslink;
      }
  
 
}