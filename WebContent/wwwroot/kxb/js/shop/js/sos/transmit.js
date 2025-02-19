var dataForWeixin={

   appId:"",

   MsgImg:document.getElementById("wx_msgimg").value,

   TLImg:document.getElementById("wx_titimg").value,

   url:document.URL,

   title:document.getElementById("wx_title").value,

   desc:document.getElementById("wx_description").value,

   fakeid:"",

   callback:function(){}

};

(function(){

   var onBridgeReady=function(){
	   
	   WeixinJSBridge.call('hideToolbar');
	   
	   WeixinJSBridge.on('menu:share:appmessage', function(argv){
	
	      WeixinJSBridge.invoke('sendAppMessage',{
	
	         "appid":dataForWeixin.appId,
	
	         "img_url":dataForWeixin.MsgImg,
	
	         "img_width":"120",
	
	         "img_height":"120",
	
	         "link":dataForWeixin.url,
	
	         "desc":dataForWeixin.desc,
	
	         "title":dataForWeixin.title
	
	      }, function(res){(dataForWeixin.callback)();});
	
	   });
	
	   WeixinJSBridge.on('menu:share:timeline', function(argv){
	
	      (dataForWeixin.callback)();
	
	      WeixinJSBridge.invoke('shareTimeline',{
	
	         "img_url":dataForWeixin.TLImg,
	
	         "img_width":"120",
	
	         "img_height":"120",
	
	         "link":dataForWeixin.url,
	
	         "desc":dataForWeixin.desc,
	
	         "title":dataForWeixin.title
	
	      }, function(res){});
	
	   });
	
	   WeixinJSBridge.on('menu:share:weibo', function(argv){
	
	      WeixinJSBridge.invoke('shareWeibo',{
	
	         "content":dataForWeixin.title,
	
	         "url":dataForWeixin.url
	
	      }, function(res){(dataForWeixin.callback)();});
	
	   });


};

if(document.addEventListener){

   document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   
}else if(document.attachEvent){

   document.attachEvent('WeixinJSBridgeReady'   , onBridgeReady);

   document.attachEvent('onWeixinJSBridgeReady' , onBridgeReady);

}

})();