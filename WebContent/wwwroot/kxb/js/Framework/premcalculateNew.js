var CONTEXTPATH = '/wj/';
var scripts = document.getElementsByTagName("script");
for(var i=0;i<scripts.length;i++){
	if(/.*Framework\/premcalculateNew\.js$/g.test(scripts[i].getAttribute("src"))){
		var jsPath = scripts[i].getAttribute("src").replace(/Framework\/premcalculateNew\.js$/g,'');
		if(jsPath.indexOf("/")==0||jsPath.indexOf("://")>0){
			CONTEXTPATH = jsPath;
			break;
		}
		var arr1 = jsPath.split("/");
		var path = window.location.href;
		if(path.indexOf("?")!=-1){
			path = path.substring(0,path.indexOf("?"));
		}
		var arr2 = path.split("/");
		arr2.splice(arr2.length-1,1);
		for(var i=0;i<arr1.length;i++){
			if(arr1[i]==".."){
				arr2.splice(arr2.length-1,1);
			}
		}
		CONTEXTPATH = arr2.join('/')+'/';
		break;
	}
}

var isIE = navigator.userAgent.toLowerCase().indexOf("msie") != -1;
var isIE8 = !!window.XDomainRequest&&!!document.documentMode;
var isIE7 = navigator.userAgent.toLowerCase().indexOf("msie 7.0") != -1 && !isIE8;
var isIE6 = navigator.userAgent.toLowerCase().indexOf("msie 6.0") != -1;
var isGecko = navigator.userAgent.toLowerCase().indexOf("gecko") != -1;
var isOpera = navigator.userAgent.toLowerCase().indexOf("opera") != -1;
var isQuirks = document.compatMode == "BackCompat";
var isStrict = document.compatMode == "CSS1Compat";
var isBorderBox = isIE && isQuirks;

if(isGecko){
	var p = HTMLElement.prototype;
	p.__defineSetter__("innerText",function(txt){this.textContent = txt;});
	p.__defineGetter__("innerText",function(){return this.textContent;});
	p.insertAdjacentElement = function(where,parsedNode){
		switch(where){
		  case "beforeBegin":
		    this.parentNode.insertBefore(parsedNode,this);
		    break;
		  case "afterBegin":
		    this.insertBefore(parsedNode,this.firstChild);
		    break;
		  case "beforeEnd":
		    this.appendChild(parsedNode);
		    break;
		  case "afterEnd":
		    if(this.nextSibling)
		      this.parentNode.insertBefore(parsedNode,this.nextSibling);
		    else
		      this.parentNode.appendChild(parsedNode);
		    break;
		  }
	};
	p.insertAdjacentHTML = function(where,htmlStr){
		var r=this.ownerDocument.createRange();
		r.setStartBefore(this);
		var parsedHTML=r.createContextualFragment(htmlStr);
		this.insertAdjacentElement(where,parsedHTML);
	};
	p.attachEvent = function(evtName,func){
		evtName = evtName.substring(2);
		this.addEventListener(evtName,func,false);
	};
	p.detachEvent = function(evtName,func){
		evtName = evtName.substring(2);
		this.removeEventListener(evtName,func,false);
	};
	window.attachEvent = p.attachEvent;
	window.detachEvent = p.detachEvent;
	document.attachEvent = p.attachEvent;
	document.detachEvent = p.detachEvent;
	p.__defineGetter__("currentStyle", function(){
		return this.ownerDocument.defaultView.getComputedStyle(this,null);
  });
	p.__defineGetter__("children",function(){
    var tmp=[];
    for(var i=0;i<this.childNodes.length;i++){
    	var n=this.childNodes[i];
      if(n.nodeType==1){
      	tmp.push(n);
      }
    }
    return tmp;
  });
	p.__defineSetter__("outerHTML",function(sHTML){
    var r=this.ownerDocument.createRange();
    r.setStartBefore(this);
    var df=r.createContextualFragment(sHTML);
    this.parentNode.replaceChild(df,this);
    return sHTML;
  });
  p.__defineGetter__("outerHTML",function(){
    var attr;
		var attrs=this.attributes;
		var str="<"+this.tagName.toLowerCase();
		for(var i=0;i<attrs.length;i++){
		    attr=attrs[i];
		    if(attr.specified){
		        str+=" "+attr.name+'="'+attr.value+'"';
		    }
		}
		if(!this.hasChildNodes){
		    return str+">";
		}
		return str+">"+this.innerHTML+"</"+this.tagName.toLowerCase()+">";
  });
	p.__defineGetter__("canHaveChildren",function(){
	  switch(this.tagName.toLowerCase()){
			case "area":
			case "base":
			case "basefont":
			case "col":
			case "frame":
			case "hr":
			case "img":
			case "br":
			case "input":
			case "isindex":
			case "link":
			case "meta":
			case "param":
			return false;
    }
    return true;
  });
  Event.prototype.__defineGetter__("srcElement",function(){
    var node=this.target;
    while(node&&node.nodeType!=1)node=node.parentNode;
    return node;
  });
  p.__defineGetter__("parentElement",function(){
		if(this.parentNode==this.ownerDocument){
			return null;
		}
		return this.parentNode;
	});
}else{
	try {
		document.documentElement.addBehavior("#default#userdata");
		document.execCommand('BackgroundImageCache', false, true);
	} catch(e) {
		alert(e);
	}
}

var _setTimeout = window.setTimeout;
window.setTimeout = function(callback,timeout,param){
	if(typeof callback == 'function'){
		var args = Array.prototype.slice.call(arguments,2);
		var _callback = function(){
			callback.apply(null,args);
		};
		return _setTimeout(_callback,timeout);
	}
	return _setTimeout(callback,timeout);
};

var Core = {};
Core.attachMethod = function(ele){
	if(!ele||ele["$A"]){
		return;
	}
	if(ele.nodeType==9){
		return;
	}
	var win;
	try{
		if(isGecko){
			win = ele.ownerDocument.defaultView;
		}else{
			win = ele.ownerDocument.parentWindow;
		}
		for(var prop in win.$E){
			ele[prop] = win.$E[prop];
		}
	}catch(ex){
		//alert("Core.attachMethod:"+ele)//有些对象不能附加属性，如flash
	}
};

var Constant = {};

Constant.Null = "_ZVING_NULL";

function $(ele) {
  if (typeof(ele) == 'string'){
    ele = document.getElementById(ele);
    if(!ele){
  		return null;
    }
  }
  if(ele){
  	Core.attachMethod(ele);
	}
  return ele;
}

function $V(ele){
	var eleId = ele;
	ele = $(ele);
	if(!ele){
		alert("表单元素不存在:"+eleId);
		return null;
	}
  switch (ele.type.toLowerCase()) {
    case 'submit':
    case 'hidden':
    case 'password':
    case 'textarea':
    case 'file':
    case 'image':
    case 'select-one':
    case 'text':
      return ele.value;
    case 'checkbox':
    case 'radio':
      if (ele.checked){
    		return ele.value;
    	}else{
    		return null;
    	}
    default:
    		return "";
  }
}

function $S(ele,v){
	var eleId = ele;
	ele = $(ele);
	if(!v&&v!=0){
		v = "";
	}
	if(!ele){
		alert("表单元素不存在:"+eleId);
		return;
	}
  switch (ele.type.toLowerCase()) {
    case 'submit':
    case 'hidden':
    case 'password':
    case 'textarea':
    case 'button':
    case 'file':
    case 'image':
    case 'select-one':
    case 'text':
      ele.value = v;
      break;
    case 'checkbox':
    case 'radio':
      if(ele.value==""+v){
      	ele.checked = true;
      }else{
      	ele.checked=false;
      }
      break;
   }
}
function $T(tagName,ele){
	ele = $(ele);
	ele = ele || document;
	var ts = ele.getElementsByTagName(tagName);//此处返回的不是数组
	var arr = [];
	var len = ts.length;
	for(var i=0;i<len;i++){
		arr.push($(ts[i]));
	}
	return arr;
}

function $N(ele){
    if (typeof(ele) == 'string'){
      ele = document.getElementsByName(ele);
      if(!ele||ele.length==0){
    		return null;
      }
      var arr = [];
      for(var i=0;i<ele.length;i++){
      	var e = ele[i];
      	if(e.getAttribute("ztype")=="select"){
      	e = e.parentNode;
      	}
      	Core.attachMethod(e);
      	arr.push(e);
      }
      ele = arr;
    }
    return ele;
}

function $NV(ele){
	ele = $N(ele);
	if(!ele){
		return null;
	}
	var arr = [];
	for(var i=0;i<ele.length;i++){
		var v = $V(ele[i]);
		if(v!=null){
			arr.push(v);
		}
	}
	return arr.length==0? null:arr;
}

function $NS(ele,value){
	ele = $N(ele);
	if(!ele){
		return;
	}
	if(!ele[0]){
		return $S(ele,value);
	}
	if(ele[0].type=="checkbox"){
		if(value==null){
			value = new Array(4);
		}
		var arr = value;
		if(!isArray(value)){
			arr = value.split(",");
		}
		for(var i=0;i<ele.length;i++){
			for(var j=0;j<arr.length;j++){
				if(ele[i].value==arr[j]){
					$S(ele[i],arr[j]);
					break;
				}
				$S(ele[i],arr[j]);
			}
		}
		return;
	}
	for(var i=0;i<ele.length;i++){
		$S(ele[i],value);
	}
}

function $F(ele){
	if(!ele)
		return document.forms[0];
	else{
		ele = $(ele);
		if(ele&&ele.tagName.toLowerCase()!="form")
			return null;
		return ele;
	}
}

//多选框全选
function selectAll(ele,eles){
	var flag = $V(ele);
	var arr = $N(eles);
	if(arr){
		for(var i=0;i< arr.length;i++){
			arr[i].checked = flag;
	  }
	}
}

function encodeURL(str){
	return encodeURI(str).replace(/=/g,"%3D").replace(/\+/g,"%2B").replace(/\?/g,"%3F").replace(/\&/g,"%26");
}

function htmlEncode(str) {
	return str.replace(/&/g,"&amp;").replace(/\"/g,"&quot;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/ /g,"&nbsp;");
}

function htmlDecode(str) {
	return str.replace(/\&quot;/g,"\"").replace(/\&lt;/g,"<").replace(/\&gt;/g,">").replace(/\&nbsp;/g," ").replace(/\&amp;/g,"&");
}

function javaEncode(txt) {
	if (!txt) {
		return txt;
	}
	return txt.replace("\\", "\\\\").replace("\r\n", "\n").replace("\n", "\\n").replace("\"", "\\\"").replace("\'", "\\\'");
}

function javaDecode(txt) {
	if (!txt) {
		return txt;
	}
	return txt.replace("\\\\", "\\").replace( "\\n", "\n").replace("\\r", "\r").replace("\\\"", "\"").replace("\\\'", "\'");
}

function isInt(str){
	return /^\-?\d+$/.test(""+str);
}

function isNumber(str){
	var t = ""+str;
	var dotCount = 0;
	for(var i=0;i<str.length;i++){
		var c = str.charAt(i);
		if(c=="."){
			if(i==0||i==str.length-1||dotCount>0){
				return false;
			}else{
				dotCount++;
			}
		}else if(c=='-'){
			if(i!=0){
				return false;
			}
		}else	if(isNaN(parseInt(c))){
			return false;
		}
	}
	return true;
}

function isTime(str){
	if(!str){
		return false;
	}
	var arr = str.split(":");
	if(arr.length!=3){
		return false;
	}
	if(!isNumber(arr[0])||!isNumber(arr[1])||!isNumber(arr[2])){
		return false;
	}
	var date = new Date();
	date.setHours(arr[0]);
	date.setMinutes(arr[1]);
	date.setSeconds(arr[2]);
	return date.toString().indexOf("Invalid")<0;
}

function isDate(str){
	if(!str){
		return false;
	}
	var arr = str.split("-");
	if(arr.length!=3){
		return false;
	}
	if(!isNumber(arr[0])||!isNumber(arr[1])||!isNumber(arr[2])){
		return false;
	}
	var date = new Date();
	date.setFullYear(arr[0]);
	date.setMonth(arr[1]);
	date.setDate(arr[2]);
	return date.toString().indexOf("Invalid")<0;
}

function isDateTime(str){
	if(!str){
		return false;
	}
	if(str.indexOf(" ")<0){
		return isDate(str);
	}
	var arr = str.split(" ");
	if(arr.length<2){
		return false;
	}
	return isDate(arr[0])&&isTime(arr[1]);
}

function isArray(obj) {
	 if(!obj){
	 	 return false;
	 }
   if (obj.constructor.toString().indexOf("Array") == -1){
      return false;
   } else{
      return true;
   }
}

function isNull(v){
	return v===null||typeof(v)=="undefined";
}

function isNotNull(v){
	return !isNull(v);
}

function getEvent(evt){
	if(document.all) return window.event;
	if (evt) {
		if((evt.constructor  == Event || evt.constructor == MouseEvent) || (typeof(evt) == "object" && evt.preventDefault && evt.stopPropagation)) {
			return evt;
		}
	}
	func = getEvent.caller;
	while(func != null) {
		var arg0 = func.arguments[0];
		if (arg0) {
			if((arg0.constructor  == Event || arg0.constructor == MouseEvent) || (typeof(arg0) == "object" && arg0.preventDefault && arg0.stopPropagation)) {
				return arg0;
			}
		}
		func=func.caller;
	}
	return null;
}

function stopEvent(evt){//阻止一切事件执行,包括浏览器默认的事件
	evt = getEvent(evt);
	if(!evt){
		return;
	}
	if(isGecko){
		evt.preventDefault();
		evt.stopPropagation();
	}
	evt.cancelBubble = true;
	evt.returnValue = false;
}

function cancelEvent(evt){//仅阻止用户定义的事件
	evt = getEvent(evt);
	evt.cancelBubble = true;
}
function DataCollection(){
	this.map = {};
	this.valuetype = {};
	this.keys = [];

	DataCollection.prototype.get = function(ID){
		if(typeof(ID)=="number"){
			return this.map[this.keys[ID]];
		}
		return this.map[ID];
	};

	DataCollection.prototype.getKey = function(index){
		return this.keys[index];
	};

	DataCollection.prototype.size = function(){
		return this.keys.length;
	};

	DataCollection.prototype.remove = function(ID){
		if(typeof(ID)=="number"){
			if(ID<this.keys.length){
				var obj = this.map[this.keys[ID]];
				this.map[this.keys[ID]] = null;
				this.keys.splice(ID);
				return obj;
			}
		}else{
			for(var i=0;i<this.keys.length;i++){
				if(this.keys[i]==ID){
					var obj = this.map[ID];
					this.map[ID] = null;
					this.keys.splice(i);
					return obj;
					break;
				}
			}
		}
		return null;
	};

	DataCollection.prototype.toQueryString = function(){
		var arr = [];
		for(var i=0;i<this.keys.length;i++){
			if(this.map[this.keys[i]]==null||this.map[this.keys[i]]==""){continue;}
			if(i!=0){
				arr.push("&");
			}
			arr.push(this.keys[i]+"="+this.map[this.keys[i]]);
		}
		return arr.join('');
	};

	DataCollection.prototype.parseXML = function(xmlDoc){
		var coll = xmlDoc.documentElement;
		if(!coll){
			return false;
		}
		var nodes = coll.childNodes;
		var len = nodes.length;
		for(var i=0;i<len;i++){
			var node = nodes[i];
			var Type = node.getAttribute("Type");
			var ID = node.getAttribute("ID");
			this.valuetype[ID] = Type;
			if(Type=="String"){
				var v = node.firstChild.nodeValue;
				if(v==Constant.Null){
					v = null;
				}
				this.map[ID] = v;
			}else if(Type=="StringArray"){
				this.map[ID] = eval("["+node.firstChild.nodeValue+"]");
			}else if(Type=="Map"){
				this.map[ID] = eval("("+node.firstChild.nodeValue+")");
			}else if(Type=="DataTable"||Type=="Schema"||Type=="SchemaSet"){
				this.parseDataTable(node,"DataTable");
			}else{
				this.map[ID] = node.getAttribute("Value");
			}
			this.keys.push(ID);
		}
		return true;
	};

	DataCollection.prototype.parseDataTable = function(node,strType){
		var cols = node.childNodes[0].childNodes[0].nodeValue;
		cols = "var _TMP1 = "+cols+"";
		eval(cols);
		cols = _TMP1;
		var values = node.childNodes[1].childNodes[0].nodeValue;
		values = "var _TMP2 = "+values+"";
		eval(values);
		values = _TMP2;
		var obj;
		obj = new DataTable();
		obj.init(cols,values);
		this.add(node.getAttribute("ID"),obj);
	};

	DataCollection.prototype.toXML = function(){
		var arr = [];
		arr.push("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		arr.push("<collection>");
		for(var i=0;i<this.keys.length;i++){
			var ID = this.keys[i];
			try{
				var v = this.map[ID];
				arr.push("<element ID=\""+ID+"\" Type=\""+this.valuetype[ID]+"\">");
				if(this.valuetype[ID]=="DataTable"){
					arr.push(v.toString());
				}else if(this.valuetype[ID]=="String"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						arr.push("<![CDATA["+v+"]]>");
					}
				}else if(this.valuetype[ID]=="Map"){
					if(v==null||typeof(v)=="undefined"){
						arr.push("<![CDATA["+Constant.Null+"]]>");
					}else{
						arr.push("<![CDATA["+JSON.toString(v)+"]]>");
					}
				}else{
					arr.push(v);
				}
				arr.push("</element>");
			}catch(ex){alert("DataCollection.toXML():"+ID+","+ex.message);}
		}
		arr.push("</collection>");
		return arr.join('');
	};

	DataCollection.prototype.add = function(ID,Value,Type){
		this.map[ID] = Value;
		this.keys.push(ID);
		if(Type){
			this.valuetype[ID] = Type;
		}else	if( Value && Value.getDataRow){//DataTable可能不是本页面中的
			this.valuetype[ID] = "DataTable";
		}else{
			this.valuetype[ID] = "String";
		}
	};

	DataCollection.prototype.addAll = function(dc){
		if(!dc){
			return;
		}
		if(!dc.valuetype){
			alert("DataCollection.addAll()要求参数必须是一个DataCollection!");
		}
		var size = dc.size();
		for(var i=0;i<size;i++){
			var k = dc.keys[i];
			var v = dc.map[k];
			var t = dc.valuetype[k];
			this.add(k,v,t);
		}
	};
}
/*---------------------------Server,Page-------------------------*/
var Server = {};
Server.RequestMap = {};
Server.MainServletURL = "MainServlet.jsp";
Server.ContextPath = CONTEXTPATH;
Server.Pool = [];

Server.getXMLHttpRequest = function(){
	for(var i=0;i<Server.Pool.length;i++){
		if(Server.Pool[i][1]=="0"){
			Server.Pool[i][1] = "1";
			return Server.Pool[i][0];
		}
	}
	var request;
	if (window.XMLHttpRequest){
		request = new XMLHttpRequest();
	}else if(window.ActiveXObject){
		for(var i =5;i>1;i--){
      try{
        if(i==2){
					request = new ActiveXObject( "Microsoft.XMLHTTP" );
        }else{
					request = new ActiveXObject( "Msxml2.XMLHTTP." + i + ".0" );
        }
      }catch(ex){}
    }
	}
	Server.Pool.push([request,"1"]);
	return request;
};
Server.sendRequest = function(methodName,dataCollection,func,id,waitMsg){//参数id用来限定id相同的请求同一时间只能有一个
	if(!Server.executeRegisteredEvent(methodName,dataCollection)){
		Console.log(methodName+"的调用被注册事件阻止!");
		return;
	}
	var Request;
	if(id!=null && Server.RequestMap[id]){
		Request = Server.RequestMap[id];
		Request.abort();
	}else{
		Request = Server.getXMLHttpRequest();
	}
	Request.open("POST", Server.ContextPath+Server.MainServletURL, true);
	Request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	var url = "_ZVING_METHOD="+methodName+"&_ZVING_DATA=";
	if(dataCollection){
		url += encodeURL(htmlEncode(dataCollection.toXML()));
	}
	url += "&_ZVING_URL="+encodeURL(window.location.pathname);
	Server._ResponseDC = null;
	Request.onreadystatechange = function(){Server.onRequestComplete(Request,func);};
	Request.send(url);
};
Server.executeRegisteredEvent = function(methodName,dc){
	var arr = Server.Events[methodName];
	if(!arr){
		return true;
	}
	for(var i=0;i<arr.length;i++){
		if(!arr[i].apply(null,[dc,methodName])){
			return false;
		}
	}
	return true;
};
Server.Events = {};

Server.onRequestComplete = function(Request,func){
	if (Request.readyState==4&&Request.status==200) {
		try{
			var xmlDoc = Request.responseXML;
			var dc = new DataCollection();
			if(xmlDoc){
				if(dc.parseXML(xmlDoc)){
					dc["Status"] = dc.get("_ZVING_STATUS");
					dc["Message"] = dc.get("_ZVING_MESSAGE");
					if(dc.get("_ZVING_SCRIPT")){
						eval(dc.get("_ZVING_SCRIPT"));
					}
				}
				Server._ResponseDC = dc;
				xmlDoc = null;
			}else{
					dc["Status"] = 0;
					dc["Message"] = "服务器发生异常,未获取到数据!";
			}
			if(func){
				try{
					func(dc);
				}catch(ex){
					alert("Server.onRequestComplete:"+ex.message+"\t"+ex.lineNumber);
				}
			}
		}finally{
			for(var i=0;i<Server.Pool.length;i++){
				if(Server.Pool[i][0]==Request){
					Server.Pool[i][1] = "0";
					break;
				}
			}
			Request = null;
			func = null;
		}
	}else if((Request.readyState==4&&Request.status==12029)||(Request.readyState==4&&Request.status==12007)){
		var dc = new DataCollection();
		dc["Status"] = 0;
		dc["Message"] = "网络原因,未获取到数据!";
		if(func){
			try{
				func(dc);
			}catch(ex){
				alert("Server.onRequestComplete:"+ex.message+"\t"+ex.lineNumber);
			}
		}
	}
};