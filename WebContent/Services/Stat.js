	var _zcms_d,_zcms_s,_zcms_c,_zcms_l,_zcms_t,_zcms_s;
	var _zcms_st=new Date().getTime();
	
	var _sDate=0;
	var startDate = new Date();
	var _zcms_stat = function(param){
		var p = {};
		if(param){
			var arr = param.split("&");
			for(var i=0;i<arr.length;i++){
				if(arr[i]){
					var arr2 = arr[i].split("=");
					if(arr2[0]){
						p[arr2[0]] = arr2[1];
					}
				}
			}
		}
		_zcms_d = p["Dest"];
		_zcms_s = p["SiteID"];
		_zcms_c = p["CatalogInnerCode"];
		_zcms_l = p["LeafID"];
		_zcms_t = p["Type"];
		p["ce"] = _zcms_stat.ce();	
		p["la"] = navigator.systemLanguage?navigator.systemLanguage:navigator.language;
		p["cs"] = document.charset;
		p["vq"] = _zcms_stat.vq();	
		p["Referer"] = _zcms_stat.eu(document.referrer);
		p["Title"] = _zcms_stat.eu(document.title);
		p["URL"] = _zcms_stat.eu(location.href);
		p["Host"] = location.host;
		p["Event"] = "Load";
		var dest = _zcms_d;
		p["Dest"] = false;
		
		dest = dest+"?"+_zcms_stat.mq(p);
		var s = document.createElement("script");
		s.src = dest;
		document.body.appendChild(s);
		_sDate = startDate.getTime();
	};
	
	_zcms_stat.eu =  function(str){
		return encodeURI(str).replace(/=/g,"%3D").replace(/\+/g,"%2B").replace(/\?/g,"%3F").replace(/\&/g,"%26");
	}
	
	_zcms_stat.mq = function(map){
		var sb = [];
		for(var prop in map){
			if(map[prop]){
				sb.push(prop+"="+map[prop]);
			}
		}	
		return sb.join("&");
	}
	
	_zcms_stat.trim = function(str){
		return str.replace(/(^\s*)|(\s*$)/g,"");
	}
	
	_zcms_stat.ce = function(){
		var c_en = (navigator.cookieEnabled)? 1 : 0;
		return c_en;
	}
	
	_zcms_stat.vq = function(){
	  var cs = document.cookie.split("; ");
	  var name = _zcms_s+"_vq";
	  var vq = 1;
	  for(i=0; i<cs.length; i++){
		  var arr = cs[i].split("=");
		  var n = _zcms_stat.trim(arr[0]);
		  var v = arr[1]?_zcms_stat.trim(arr[1]):"";
		  if(n==name){
		  	vq = parseInt(v)+1;
		  	break;
		  }
		}
		var expires = new Date(new Date().getTime()+365*10*24*60*60*1000).toGMTString();
		var cv = name+"="+vq+";expires="+expires+";path=/;";
		document.cookie = cv;
		return vq;
	}
	//判断页面是跳转、刷新，或者关闭，1-关闭,2-跳转/刷新(不能使用0作参数)
	_zcms_stat.jc=function(){
		var jc = 1;
		if (window.event.clientX > document.body.clientWidth && event.clientY<0 || event.altKey) {
			jc = 1;
		} else {
			jc = 2;
		}
		return jc;
	}
	
	function _zcms_bu(){
		if(_zcms_d){
			var p = {};
			p["Event"] = "Unload";
			p["LeafID"] = _zcms_l;
			p["SiteID"] = _zcms_s;
			p["CatalogInnerCode"] = _zcms_c;
			p["sDate"] = _sDate;
			p["ce"] = _zcms_stat.ce();	
			p["la"] = navigator.systemLanguage?navigator.systemLanguage:navigator.language;
			p["cs"] = document.charset;
			p["vq"] = _zcms_stat.vq();	
			p["Referer"] = _zcms_stat.eu(document.referrer);
			p["Title"] = _zcms_stat.eu(document.title);
			p["URL"] = _zcms_stat.eu(location.href);
			p["Host"] = location.host;
			
			if(_zcms_c&&!_zcms_l){
				p["Type"] = _zcms_t;
			}
			var t = new Date().getTime();
			if(t-_zcms_lt>30000){
				_zcms_nt += (t-_zcms_lt);
			}
			p["StickTime"] = (t-_zcms_st-_zcms_nt)/1000;
			p["IsJOrC"] = _zcms_stat.jc();
			var dest = _zcms_d+"?"+_zcms_stat.mq(p);
			var s = document.createElement("script");
			s.src = dest;
			document.body.appendChild(s);
		}
	}
	
	var _zcms_lt = new Date().getTime();
	var _zcms_lt_ka = new Date().getTime();
	var _zcms_nt = 0;
	function _zcms_ka(){
		var t = new Date().getTime();
		if(t-_zcms_lt_ka>60000){
			_zcms_lt_ka = t;
			var p = {};
			p["Event"] = "KeepAlive";
			p["SiteID"] = _zcms_s;
			var dest = _zcms_d+"?"+_zcms_stat.mq(p);
			var s = document.createElement("script");
			s.src = dest;
			document.body.appendChild(s);
		}
		if(t-_zcms_lt>60000){
			_zcms_nt += (t-_zcms_lt);
		}
		_zcms_lt = t;
	}
	
	var pos = [];
	function _zcms_cr(evt){
		var x = evt.clientX, y=evt.clientY;
		var src = evt.srcElement;
		if(!src){
			var node = evt.target;
	    while(node&&node.nodeType!=1)node=node.parentNode;
	    src =  node;
		}
		var win;
		if(src.ownerDocument.defaultView){
			win = src.ownerDocument.defaultView;
		}else{
			win = src.ownerDocument.parentWindow;
		}
		x += Math.max(win.document.body.scrollLeft, win.document.documentElement.scrollLeft);
		y += Math.max(win.document.body.scrollTop, win.document.documentElement.scrollTop);
		pos.push([x,y]);
	}
	
	if(window.attachEvent){
		window.attachEvent("onbeforeunload",_zcms_bu);
		window.attachEvent("onclick",_zcms_ka);
		window.attachEvent("onkeydown",_zcms_ka);
		window.attachEvent("onmousemove",_zcms_ka);
		window.attachEvent("onscroll",_zcms_ka);
	}else if(window.addEventListener){
		window.addEventListener('beforeunload',_zcms_bu,false);
		window.addEventListener("click",_zcms_ka,false);
		window.addEventListener("click",_zcms_cr,false);
		window.addEventListener("keydown",_zcms_ka,false);
		window.addEventListener("mousemove",_zcms_ka,false);
		window.addEventListener("scroll",_zcms_ka,false);
	}