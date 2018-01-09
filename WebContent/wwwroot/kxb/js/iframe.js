/**
* ------------------------------------------
* @make:丛芝楠
* @version  1.0    
* @des：IFRAME自适应高度
* ------------------------------------------
*/

/**
 * 购买流程自适应.
 * @return
 */
function sethash() {  
            var hashH =document.body.offsetHeight+60; //获取自身高度
            if(p_url==null||p_url==''||typeof p_url=='undefined'){
            	 jQuery(".form_p_h").hide();
            	 jQuery("#shop_login").hide();
                 jQuery(".gwc_js_con").hide();
            }else{
            	 document.getElementById("iframeA").src =p_url + "?" + hashH;
            	 jQuery(".form_p_h").hide();
            	 jQuery("#shop_login").hide();
                 jQuery(".gwc_js_con").hide();
            }
}

/**
 * 列表页面高度自适应.
 * @return 
 */
function mysethash() {
    var hashH = document.body.offsetHeight+60; //获取自身高度
    var pp_url = document.referrer;
  	var bxhz_list = window.location.search;  
  	bxhz_list = bxhz_list.replace("?", "");
	if( bxhz_list != null && bxhz_list != '' && bxhz_list.indexOf("@") != -1){
		document.getElementById("iframeA").src = bxhz_list.split("@")[1] + "?" + hashH;
	} else {
		document.getElementById("iframeA").src = pp_url.substring(0, pp_url.indexOf("/kxb/")) + "/kxb/iframe.html?" + hashH;
	}
}


/**
 * 详细页-TAB切换自适应方法.
 * @param Height
 * @return
 */
function newSethash(Height) {
    var pp_url = document.referrer;
    document.getElementById("iframeA").src = pp_url.substring(0, pp_url.indexOf("/kxb/")) + "/kxb/iframe.html?" + Height;
}

var userCode;
//赠险-产品ID
var productID;
/**
 * 车险，静态页面传参.
 * @return
 */
function gParame(){
	urlinfo=window.location.href;
	len=urlinfo.length; 
	offset=urlinfo.indexOf("?"); 
	newsidinfo=urlinfo.substr(offset,len) ;
	newsids=newsidinfo.split("="); 
	userCode = newsids[1];
}

/**
 * 回调金融会所.
 * @param path
 * @param comCode
 * @param url
 * @return
 */
function getNowCpslink(path,comCode,url){
	if(url == "000"){
		alert("保险公司未上线,请重新选择!");
		return;
	}
	gParame();
	var URL = ""+path+"?comCode="+comCode+"&UserCode="+userCode;
	window.open(URL);
}

/**
 * 赠险获取参数.
 * @return
 */
function getZXParame(){
	urlinfo=window.location.href;   
	len=urlinfo.length; 
	offset=urlinfo.indexOf("?"); 
	newsidinfo=urlinfo.substr(offset+1,len) ;
	newsids=newsidinfo.split("&"); 
	userCode = newsids[0];
	productID = newsids[1];
	return;
}

/**
 * 赠险回调.
 * @param path
 * @return
 */
function ZXlink(path){
	getZXParame();
	var URL = ""+path+"?ProductID="+productID+"&UserCode="+userCode;
	window.open(URL);
}

/**
 * 投保录入页面.
 * @return
 */
function selectIframe(){
	var tf = jQuery("#typeFlag").val();
	if("02"== tf){
		var hashH =document.body.offsetHeight+60; //获取自身高度
		document.getElementById("iframeA").src =p_url + "?" + hashH;
	 }
}


