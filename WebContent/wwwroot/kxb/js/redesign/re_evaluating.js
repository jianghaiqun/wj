/* 
#@des:保险评测
*@maker:guodongqi
*@time:2016-11-18-10:00 
*/
praise = function(opt) {

    this._opt = $.extend({
     tagid : ".praise", 
       press : "pressed",
         del : ".del",
      modmes : ".user_mes",
      mesbtn : "#useR_bts",
     mestext : "#user_text",
    more_mes : "#more_mes",
     mes_box : ".mes_box",
   user_mers : ".more_usermes",
     mtext   : "<span class='more_usermes'>点击查看更多</span>",
     linka   : "#tag_lina",
    meslidom : "#pagination1 ul li",
    mestxtFn : function(){},  //点击文本框回调函数
   moremesFn : function(){},  //点击更多评论回调函数
    mesbtnFn : function(){},  //评论按钮回调函数
     tagtnFn : function(){},  //点赞按钮回调函数
     deltnFn : function(){}   //删除按钮回调函数
  }, opt);

    this.init();
    this.monitor();

}
praise.prototype.init = function() {

  this.tagid = jQuery(this._opt.tagid);
  this.del = jQuery(this._opt.del);
  this.modmes = jQuery(this._opt.modmes);
  this.mesbtn = jQuery(this._opt.mesbtn);
  this.more_mes = jQuery(this._opt.more_mes);
  this.mestext = jQuery(this._opt.mestext);
  this.linka = jQuery(this._opt.linka);
  this.meslidom = jQuery(this._opt.meslidom);

}
praise.prototype.monitor =function() {

  var _this = this;

    _this.tagid.live("click",function(){

      jQuery(this).toggleClass(_this._opt.press);

      _this._opt.tagtnFn(this);

    })

     _this.del.live("click",function(){

         _this._opt.deltnFn(this);

    })

    _this.mestext.click(function(){

      _this._opt.mestxtFn();

    });

    _this.mesbtn.click(function(){

      _this._opt.mesbtnFn();

    });

    _this.more_mes.click(function(){

      _this._opt.moremesFn();

    });

    _this.meslidom.click(function(){
       var top = _this.linka.offset().top - 48;
       jQuery("body,html").animate({
            scrollTop:top
        }, 500);
    })

}
/* 
#@des:保险评测浮动定位
*@maker:guodongqi
*@time:2016-11-23-10:00 
*/
jQuery.fn.fixedNavigation = function(opt) {
  var _opt, jWin, jNav, nNavOffTop,nFixTop;
  
  return this.each(function() {
    _opt = jQuery.extend({
    navElm: this,
    fixName: "fixed",
    boxElm: "",
    nBtm: -48,
    space: 128,
    fixTop: 127
    }, opt);

    _init();
    _monitor();
  });
  
  /* init */
  function _init() {
    jWin = jQuery(window);    
    jNav = jQuery(_opt.navElm);
    jBox = jQuery(_opt.boxElm);
    nNavOffTop = jNav.offset().top;
    jTag = jQuery(_opt.tagElm);
    nFixTop = _opt.fixTop;
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

      if(_opt.boxElm!=""){
      var nBnrMaxTop = jBox.offset().top + jBox.height() - jNav.height()-_opt.space;
      var maxPosTop = nBnrMaxTop - nFixTop;
      if(nBnrMaxTop < 0){
        nBnrMaxTop = 0;
      }
          if(nWinTop >= maxPosTop ) {
              jNav.css({ "position": "absolute", 
                     "top": nBnrMaxTop
                   });
            } else {
              jNav.css({ "position": "fixed", 
                     "top": nFixTop,
                     "bottom": "auto",
                      "width": "320px"
                   });
            }
      }
      

    });
    

  }
};

/* 
#@des:保险评测分页tip提示
*@maker:guodongqi
*@time:2016-11-23-10:00 
*/
loadpagetip = function(opt) {

    this._opt = $.extend({
     tagpdom : "#pagination li.now", 
         tag : ".tip_cons",
      clickd : "#pagination li",
      articcon : "#ajaxad div:gt(0)"
  }, opt);

    this.init();
    this.addEvent();

}
loadpagetip.prototype.init = function() {

  this.tagpdom = jQuery(this._opt.tagpdom);
  this.tag = jQuery(this._opt.tag);
  this.clickd = jQuery(this._opt.clickd);
  this.articcon = jQuery(this._opt.articcon);

}
loadpagetip.prototype.addEvent =function() {

   var _this = this;

  this.articcon.hide();
   this.monitor = function() {

       var tipfont = jQuery(_this._opt.tagpdom).find("span").attr("datafont");
      if(tipfont!=null&&jQuery(_this._opt.tagpdom).html()!=null){
            var x = jQuery(_this._opt.tagpdom).offset().top;
            var y = jQuery(_this._opt.tagpdom).offset().left;
            jQuery(_this._opt.tag).remove();
            jQuery("body").append("<em class='tip_cons'>"+tipfont+"<i></i></em>");
         
            var tipy = y -  jQuery(_this._opt.tag).width()/2 + 10;
            var tipx = x - 34;
            jQuery(_this._opt.tag).css({
              position: "absolute",
              left: tipy,
              top: tipx
            });

      }
   }

   this.monitor();

   jQuery(window).resize(function(){
        _this.monitor();
    });
    _this.clickd.click(function(){
     _this.monitor();
    })

}

