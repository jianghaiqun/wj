/*validTip 为验证提示方法，可以默认，可以自己定义。
	@obj 为input对像
	@text为提示文字 
*/
;(function(){
	//共用方法类
	window.validTip={};
	//默认配置
	validTip.defaults={
		//obj变成jq对象,防止传入的是JS对象
		right:function(obj,text){//正确提示
			text=text||'';
			$(obj).closest('li').find('.comment').addClass('right').removeClass('wrong').html("<span>&nbsp;</span>"+text).css('display','inline-block');
		},
		wrong:function(obj,text){//错误提示
			text=text||'错误';
			$(obj).closest('li').find('.comment').addClass('wrong').removeClass('right').html("<span>&nbsp;</span>"+text).css('display','inline-block');
		},
		normal:function(obj,text){//正常提示
			text=text||'';
			$(obj).closest('li').find('.comment').removeClass('right').removeClass('wrong').html('<span>&nbsp;</span>'+text).css('display','inline-block');
		}
	};
	//合并默认配置
	$( document ).ready( function(){
		for( var i in validTip.defaults ){
			if( window.validTip[i]==undefined ){
				window.validTip[i]=validTip.defaults[i];
			};
		}
	} );
})();
//验证插件
;(function(){
	/*全局对象*/
	var d=document,
		//电子邮件
		//中文 /^[\u4E00-\u9FA5]$/
		//regEmail=/^[\w.-]+?@[a-z0-9]+?\.[a-z]{2,6}$/i,
		regEmail=/^(\w)+((\.\w+)|(\-|\_))*@(\w)+((\.\w+)+)$/i;
		//身份证
		regID=/^\d{6}(?:((?:19|20)\d{2})(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2]\d|3[0-1])\d{3}(?:x|X|\d)|(?:\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2]\d|3[0-1])\d{3}))$/,
		//手机号
		regPhone=/^\d{11}$/,
		//验证非空
		regEmpty=/[^\s]{1,}/;
	/*顶级私有函数*/
	function getClass (className, node, tag) {
		node = node || document;
		tag = tag || '*';
		var i = 0,
			j = 0,
			classElements = [],
			els = node.getElementsByTagName(tag),
			elsLen = els.length,
			pattern = new RegExp("(^|\\s)" + className + "(\\s|$)");
		for (; i < elsLen; i ++) {
			if (pattern.test(els[i].className)) {
				classElements[j] = els[i];
				j ++;
			};
		};
		return classElements;
	};
	/*验证主体*/
	var valid=(function(){
		var valid=function(select){
			//彷jQuery 初始化
			return new valid.fn.init(select);
		};
		valid.fn=valid.prototype={
			constructor:valid,
			init:function(select){//初始化
				var regClass=/^\.\w+$/;//class
				var regId=/^#\w+$/;//id
				if(!select){//不传入，则返回
					return this;
				};
				if(select.nodeType==1){//传入的是节点
					this[0]=this[name]=select;
					return this;
				};
				if(regId.test(select)){//传入的是ID
					this[0]=d.getElementById(select.split('#')[1]);
					this[name]=select;
					return this;
				};
				if(regClass.test(select)){//传入的是class 只返回第一个CLASS
					var classs=getClass(select.split('.')[1]);
					var length=classs.length;
					this[0]=classs[0];
					this[name]=select;
					return this;
				}else{
					this[name]=select;
				}
			},
			bind:function(type,fn){//绑定事件
				var obj=this[0];
				
				if(window.attachEvent){
					obj.attachEvent('on'+type,function(){
						return fn.call(obj);//简单从这里处理下 IE this
					});
				}else if(window.addEventListener){
					obj.addEventListener(type,fn,false);
				}
			},
			//去空格方法 来自jQuery
			trim: String.prototype.trim ?
			function( text ) {
				return text == null ?"" :	String.prototype.trim.call( text );
			} :
			function( text ) {
				return text == null ?"" :	text.toString().replace( /^\s+|\s+$/g, "" );
			},
			//类型验证
			type:function(param){
				var cacheType=Object.prototype.toString.call(param);
    				return cacheType.substring(8,cacheType.length-1).toLowerCase();
			},
			/*ajax 验证 基于JQ---想移出
			{
				url:url//请求地址
				obj:obj//当前对像
				data:{}//数据
				wrong:wrong//重复时提示信息
				right:,
				call:'0000'/'1111'//正确时，后台返回的数据
			}*/
			ajax:function(options){
				var status=false;
				$.ajax({
					url:options.url,
					type:'post',
					async:false,
					data:options.data,
					error:function(){
						validTip.wrong(options.obj,'网络异常，请重试');
						status=false;
					},
					success:function(data){
						if(data===options.call){//未重复
							validTip.right(options.obj,options.right);
							status=true;
						}else{//重复
							validTip.wrong(options.obj,options.wrong);
							status=false;
						}
					}
				});
				return status;
			},
			/*验证规则*/
			/*config:{
				node:valid(items)[0],
				empty:item.empty,
				wrong:item.wrong,
				limit:正则,
				ajax:false/function(){return false/true},
				right:item.right
			}*/
			//核心验证规则
			core:function(config){
				var node=config.node,
					value=valid.fn.trim(node.value),
					limit=config.limit,
					status=config.status,
					ajax=config.ajax;
				if(value===''){//为空
					if ( status ){//必填项
						validTip.wrong(node,config.empty);
						return false;
					}else{//非必填项
						validTip.normal(node,'');
						return true;
					}
				}else{//不为空
					if(valid.fn.type( limit )==='regexp'){//正则
						if(limit.test(value)){
							if(valid.fn.type( ajax )==='function'){//开启ajax验证
								return ajax()
							}else{
								validTip.right(node,config.right);
								return true;
							}
						}else{
							validTip.wrong(node,config.wrong);
							return false;
						}
					}else{//其它
						if(eval(limit)){
							if(valid .fn.type( ajax )==='function'){//开启ajax验证
								return ajax()
							}else{
								validTip.right(node,config.right);
								return true;
							}
						}else{
							validTip.wrong(node,config.wrong);
							return false;
						}
					}
				}
			},
			//验证电子邮箱
			email:function(config){
				config.limit=config.limit||regEmail;
				return valid.fn.core(config);
			},
			//验证手机号码
			phone:function(config){
				config.limit=config.limit||regPhone;
				return valid.fn.core(config);
			},
			//验证身份证号
			idnumber:function(config){
				config.limit=config.limit||regID;
				return valid.fn.core(config);
			},
			//验证非空项
			empty:function(config){
				config.limit=config.limit||regEmpty;
				return valid.fn.core(config);
			},
			//验证初始化
			validator:function(config){
				var i,
					items,
					item,
					length,
					that=this,
					cache={},
					fun=[],
					param=[],
					randomFun;
				for(items in config){
					if( valid.fn.type( config[items] )==='object' ) {//如果传入的是对像
						item=config[items];
						cache={//保存数据
							node:valid(items)[0],
							limit:item.limit,
							ajax:item.ajax,
							empty:item.empty,
							wrong:item.wrong,
							right:item.right
						};
						//绑定事件
						for( var j in item.bind ){
							(function( cache,j ){
								var type=item.bind[j];
								var handler=config[items].handler;
								if(valid.fn.type(type)==='boolean'){//传入的是boolean 
									type?(cache.status=true):(cache.status=false);//改变状态码
									valid( items ).bind( j,function(){
										valid.fn[handler](cache);
									} );
								}else{
									valid( items ).bind( j,function(e){
										/*e=e||window.event;
										var target=e.target||e.srcElement;//解决IE attachEvent this;*/
										validTip.normal(this,type);
									} );
								}
							})( cache,j )
						};
						fun.push(config[items].handler);
						param.push(cache);
					}else{//传入函数
						cache=null;
						randomFun=('valid'+items).replace(/#|\./,'');//生成随机函数名
						valid.fn[randomFun]=config[items];//函数装载到valid.fn中
						fun.push(randomFun);
						param.push(cache);
					}
				};
				//建立一个全局函数，以供最后调用 validform()//return true/false
				window['valid'+this[name].replace(/#|\./ig,'')]=function(){	
					var l=fun.length;
					var status=0;
					for(var i=0 ;i <l;i++){
						if(valid.fn[fun[i]]( param[i] )){//运行生成的函数 例如 valid.fn.email
							status++
						};
					};
					return status===l;
				}
			}	
			
		};
		valid.fn.init.prototype=valid.fn;
		return valid;
	})();
	window.valid=valid;
})();
