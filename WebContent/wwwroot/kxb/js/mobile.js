var Toast = function(config) {  
    this.context = config.context == null ? $('body') : config.context;//上下文  
    this.message = config.message;//显示内容  
    this.time = config.time == null ? 10000 : config.time;//持续时间  
    this.left = config.left;//距容器左边的距离  
    this.top = config.top;//距容器上方的距离  
    this.init();  
} 
var msgEntity;  
Toast.prototype = {  
    //初始化显示的位置内容等  
    init : function(){  
        $("#toastMessage").remove();  
        //设置消息体  
        var msgDIV = new Array();  
        msgDIV.push('<div id="toastMessage">');  
        msgDIV.push('<span>' + this.message + '</span>');  
        msgDIV.push('</div>');  
        msgEntity = $(msgDIV.join('')).appendTo(this.context);  
        //设置消息样式  
        var left = this.left == null ? this.context.width() / 2 - msgEntity.find('span').width() / 2 : this.left;  
        var top = this.top == null ? this.context.scrollTop() + this.context.height() / 2 - msgEntity.find('span').height() / 2 : this.top;  
        msgEntity.css({position:'absolute', top:top, 'z-index':'99', left:left, 'background-color':'#443A30', color:'white', 'font-size':'1.5em;', padding:'10px', margin:'0px'});  
        msgEntity.hide();  
    },  
    //显示动画  
    show :function(){  
        msgEntity.fadeIn(this.time / 10);  
        msgEntity.fadeOut(this.time / 10);  
    }          
}

//显示错误信息
function showError(error) {
    new Toast({context:$('body'), message:error}).show();
}

function uaredirect(murl , type) {
	try {
		
		if (document.getElementById("bdmark") != null) {
			return;
		}
		
		var urlhash = window.location.hash;
		if (!urlhash.match("fromapp")) {
			if ((navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i))) {
				
				var cpsUserId = getLocalCookie("cpsUserId");
				var cpstype = getLocalCookie("cpsUserSource");
				 // 11 代表代理人 12 代表合作商户
				if( typeof(cpsUserId)!='undefined' && cpsUserId!=null && cpsUserId != '' && cpsUserId!='undefined'){
					if( type == 'home' ){
						if( cpstype == 'AGENT' ){
							murl += "?cpsSource=11&channeCode=05&cpsUserCode=" + cpsUserId;
							
						} else {
							murl += "?cpsSource=12&channeCode=05&cpsUserCode=" + cpsUserId;
						}
						
					} else if( type == 'list' || type == 'detail' ) {
						if( cpstype == 'AGENT' ){
							murl += "&CPSSource=11&CPSUserID=" + cpsUserId;
							
						} else {
							murl += "&CPSSource=12&CPSUserID=" + cpsUserId;
							
						}
					}  
				}
				
				location.replace(murl);
			}
		}
	} catch (err) {
		alert(err);
	}
}

//获取指定名称的cookie的值
function getLocalCookie(objname){
	var arrstr = document.cookie.split("; ");
	for(var i = 0;i < arrstr.length;i ++){
			var temp = arrstr[i].split("=");
			if(temp[0] == objname) 
				return unescape(temp[1]);
		}
}
