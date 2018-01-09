/* 
#@des:职业类别搜索
*@maker:guodongqi
*@time:2016-12-12-00:00 
*/
//搜索词匹配高亮
jQuery.extend({highlight:function(a, j, g, f) {
    if (a.nodeType === 3) {
        var d = a.data.match(j);
        if (d && d[0].length) {
            var b = document.createElement(g || "span");
            b.className = f || "highlight";
            var h = a.splitText(d.index);
            h.splitText(d[0].length);
            var e = h.cloneNode(true);
            b.appendChild(e);
            h.parentNode.replaceChild(b, h);
            return 1
        }
    } else {
        if ((a.nodeType === 1 && a.childNodes) && !/(script|style)/i.test(a.tagName) && !(a.tagName === g.toUpperCase() && a.className === f)) {
            for (var c = 0; c < a.childNodes.length; c++) {
                c += jQuery.highlight(a.childNodes[c], j, g, f)
            }
        }
    }
    return 0
}});
jQuery.fn.unhighlight = function(a) {

    var b = {className:"keyword",element:"span"};
    jQuery.extend(b, a);
    return this.find(b.element + "." + b.className).each(
    function() {
        var c = this.parentNode;
        c.replaceChild(this.firstChild, this);
        c.normalize()
    }).end()

};
jQuery.fn.highlight = function(f, b) {

    var d = {className:"keyword",element:"span",caseSensitive:false,wordsOnly:false};
    jQuery.extend(d, b);
    if (f.constructor === String) {
        f = [f]
    }
    f = jQuery.grep(f, function(h, g) {
        return h != ""
    });
    if (f.length == 0) {
        return this
    }

    var a = d.caseSensitive ? "" : "i";
    var ea = [];
    
    $.each(f, function(i, ed) {
        ed = decodeURIComponent(ed);
        var eed = ed.replace(/([!~@`<>~！，。,.；;’'\[\]\/\\\?])/g, "\\$1");
        if (eed) {
            ea.push(eed);
        }
    });
    var e = "(" + ea.join("|") + ")";
    if (d.wordsOnly) {
        e = "\\b" + e + "\\b"
    }
    var c = new RegExp(e, a);
    return this.each(function() {
        jQuery.highlight(this, c, d.element, d.className)
    })
};

//导航跟随
jQuery.fn.fixedNavigation = function(opt) {

  var _opt, jWin, jNav, nNavOffTop;

  return this.each(function() {
    _opt = jQuery.extend({
    navElm: this,
    fixName: "fixed"
    }, opt);

    _init();
    _monitor();
  });

  /* init */
  function _init() {

    jWin = jQuery(window);
    jNav = jQuery(_opt.navElm);
    nNavOffTop = jNav.offset().top;

  }

  /* bind event */
  function _monitor() {

    jWin.bind("scroll", function() {
      var nWinTop = jWin.scrollTop();

      if(nWinTop >= nNavOffTop) {
        jNav.addClass(_opt.fixName);
      } else{
        jNav.removeClass(_opt.fixName);
      }
    });

  }
};

/** 
 * @des: 根据搜索词高亮提示
 * @maker: 郭东奇
 * @date: 2016.12.12
 */
var keywordSel = function(opt) {
	
	this._opt = jQuery.extend({
		keytext: '#classSearch',
		keycon: '.classecd',
		upbtn: '.classes_up', // 单位为s
		downbtn: '.classes_down',
		keyword: '.keyword',
		desClass: 'desable_btn',
		hlightClass: 'heightlight',
		hlistdom: '.heightlight',
		texthiedp:'.classsearch_p',
		defaultval : 'defaultval'    //默认取值的属性值设置
	}, opt);

	this.setInit();
	this.addEvent();
	
}
keywordSel.prototype.setInit = function() {

	this.keytext = jQuery(this._opt.keytext);
	this.keycon = jQuery(this._opt.keycon);
	this.classes_up = jQuery(this._opt.upbtn);
	this.classes_down = jQuery(this._opt.downbtn);
	this.keyword = jQuery(this._opt.keyword);
	this.keyobj = jQuery(this._opt.keycon,this.keyword);
	this.desable_btn = this._opt.desable_btn;
	this.hlightClass = this._opt.hlightClass;
	this.desClass = this._opt.desClass;
	this.hlistdom = this._opt.hlistdom;
	this.texthiedp = this._opt.texthiedp;
	this.defaultval = this._opt.defaultval;

}
keywordSel.prototype.addEvent = function(){

	var _this = this;
	var selctint = 0;
	var tablecon = _this.keycon.html();

	if(_this.keytext.val()!=""){
           _this.keycon.html("");
    }
    // 搜索文本框绑定筛选方法 依赖搜索词匹配高亮插件
    _this.keytext.live('focus keyup blur', function(event) {
        var fonts  = _this.keytext.val();
        _this.keycon.html(tablecon);
        _this.keycon.highlight([''+fonts+'']);
        var searchcon =   _this.keytext.val();
	    var keyobj = _this.keycon.find(_this._opt.keyword);

	    if(searchcon!="" && keyobj.length>0){
	    	_this.classes_up.removeClass(_this.desClass);
	    	_this.classes_down.removeClass(_this.desClass);
	    }else{
	    	_this.classes_up.addClass(_this.desClass);
	    	_this.classes_down.addClass(_this.desClass);
	    }
    });

	//向下筛选按钮
    _this.classes_down.click(function(){
        var keyobj = _this.keycon.find(_this._opt.keyword);

        if(keyobj.length>0){
            keyobj.eq(selctint).addClass(_this.hlightClass);
            keyobj.eq(selctint-1).removeClass(_this.hlightClass);
            selctint++
	        if(selctint==keyobj.length){
	            selctint = 0;
		    }
		}

		var ifs = (jQuery(window).scrollTop() > (jQuery(_this.hlistdom).offset().top + jQuery(_this.hlistdom).outerHeight())) || ((jQuery(window).scrollTop()+jQuery(window).height()) < jQuery(_this.hlistdom).offset().top);
		if(ifs){
            var tops = jQuery(_this.hlistdom).offset().top/2;
            jQuery("body,html").animate({scrollTop:tops}, 500);
		}

	});

	//向上筛选按钮
    _this.classes_up.click(function(){
        var keyobj =  _this.keycon.find(_this._opt.keyword);

        if(keyobj.length>0){
            if(selctint==0){
              selctint = keyobj.length+1;
            }
            keyobj.eq(selctint-1).removeClass(_this.hlightClass);
            keyobj.eq(selctint-2).addClass(_this.hlightClass);
            selctint--
            if(selctint==0){
               selctint = keyobj.length;
            }
        }

    	var ifs = (jQuery(window).scrollTop() > (jQuery(_this.hlistdom).offset().top + jQuery(_this.hlistdom).outerHeight())) || ((jQuery(window).scrollTop()+jQuery(window).height()) < jQuery(_this.hlistdom).offset().top);
	  	if(ifs){
          	var tops = jQuery(_this.hlistdom).offset().top/2;
          	jQuery("body,html").animate({scrollTop:tops}, 500);
       	}
    });

    //文本框提示控制显隐
    _this.keytext.bind("focus keyup blur",function(){
		var deval = jQuery(this).attr(_this.defaultval);
		selctint = 0;

      	if(deval!=jQuery(this).val()&&jQuery(this).val()!=""){
        	jQuery(this).siblings(_this.texthiedp).hide();
      	}else{
        	jQuery(this).siblings(_this.texthiedp).show();
      	}
    })

};

