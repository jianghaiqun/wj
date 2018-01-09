// JavaScript Document
 jQuery(function(){
	 jQuery('#car_xian > li > div').hide();
	 
	    	/*主导航下拉*/
		jQuery("#car_xian li").hover(function(){
			jQuery(this).children().first().addClass("car_hover");
		    jQuery(this).children(".p_conss").show().end();
		},function(){
			jQuery(this).children().first().removeClass("car_hover");  
			jQuery(this).children(".p_conss").hide();
		}
	); 
	 
	 //焦点图切换
	jQuery('#change_33 div.changeDiv').soChange({
		thumbObj:'#change_33 .ul_change_a2 span',
		thumbNowClass:'on',
		changeTime:4000//自定义切换时间为4000ms
	});
	
	
	
	   
	
	
	/*车险模拟select*/
	 
	     jQuery('#car_tag_con > li').hide();
	jQuery(".car_select_box").click(function(event){   
				event.stopPropagation();
				jQuery(this).find(".car_option").toggle();
				jQuery(this).parent().siblings().find(".car_option").hide();
			});
			jQuery(document).click(function(event){
				var eo=jQuery(event.target);
				if(jQuery(".car_select_box").is(":visible") && eo.attr("class")!="option" && !eo.parent(".car_option").length)
				jQuery('.car_option').hide();									  
			});
	
			jQuery(".car_option a").click(function(){
				var value=jQuery(this).text();
				jQuery(this).parent().siblings(".car_select_txt").text(value);
				
				  jQuery('#car_tag_con > li:eq('+ jQuery('#car_tag a').index(jQuery(this)) +')').show().siblings().hide();
				jQuery("#car_select_value").val(value)
			 })
			 
 })
 
 function setTab(name,cursel,n){ 
for(i=1;i<=n;i++){ 
var menu=document.getElementById(name+i); 
var con=document.getElementById("con_"+name+"_"+i); 
menu.className=i==cursel?"hover":""; 
con.style.display=i==cursel?"block":"none"; 
} 
} 
 /**
  * 循环报价方法
  * cpslink 全局变量保险公司cps链接
  * getlink 获取链接方法
  * comname 保险公司名称
  */
 var cpslink="#";
 var comname="";
 function getlink(strlink,strtitle){
	 cpslink=strlink;
	 comname=strtitle;
 }
 /**
  * 立即报价方法
  * @param strlink 保险公司cps链接
  * @param strtitle 保险公司名称
  * @param pagelink 过渡页地址
  * @return
  */
 function getNowCpslink(strlink,strtitle,pagelink){
          if(strlink==000 || strlink == '#' || strlink == 'javascript:void(0);' ){
        	 //history.go(0);
         }else{
        	 strlink = strlink.replace("?", "$wen$");
        	 var url = escape(strtitle);
        	 window.open(pagelink+'?'+strlink+'*'+url);
         }
		 
	 
	 
 }
 /**
  * 过度页面跳转
  */
 function guodu(pagelink){
	     if(cpslink==000 || cpslink == '#' || cpslink == 'javascript:void(0);' ){
	    	 //history.go(0);
	     }else{
	    	 cpslink = cpslink.replace("?", "$wen$");
	    	 var url = escape(comname);
	    	 var win =window.open("");
	    	 win.location.href=pagelink+'?'+cpslink+'*'+url;
	     }
	 
	
 }
 /**
  * 立即报价跳转页面
  * @return
  */
 function openlink(){
	 var str=window.location.search;   //location.search是从当前URL的?号开始的字符串
	 if(isIllegal(str)){
	 window.location.href= sinosoft.base+"/error.jsp?errormsg='carjs'";
	 }else{
	 str = str.substring(0, str.indexOf("*"));
	 str = str.replace("?", "");
	 str = str.replace("$wen$", "?");
	 window.location.href=str;
	 }
 }
 function isIllegal(temp) {
		var regex = "script|prompt|iframe|<|>|\"|\'|javascript";
		var r = temp.match(regex); 
		if(r==null||r==""){
			return false; 
		}else{
			return true;
		}
	}
 