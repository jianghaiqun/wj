/* 
#@des:品牌频道js
*@maker:guodongqi
*@time:2016-08-15
*/
  jQuery(document).ready(function() {

    jQuery(".about_nav_m").hover(function(){
        jQuery(this).addClass("div_hover");
    },function(){
        jQuery(this).removeClass("div_hover");
    });

// 品牌介绍-媒体报道轮播
    $('#change_33  div.changeDiv').soChange({
        thumbObj:'#change_33 .ul_change_a2 span',
        thumbNowClass:'on',//自定义导航对象当前class为on
        changeTime:4000//自定义切换时间为4000ms
    });
//修正焦点 位置
     $(".ul_change_a2").css("margin-left",-$(".ul_change_a2").width()/2);
//品牌介绍-媒体切换
     jQuery("#kxb_new_ul li").tagclickbind({con:'kxb_new_mod',addstyle:'select',mode:'div',nav:'kxb_new_ul'});
//发送简历事件
     $('.link_sk,.gw_flieinput').live('click',function(){
       var mail = $(this).attr("datemail");
        art.dialog({
          content: "欢迎您发送简历至 "+ mail +" 加入我们！",
        })
    })
//查看简历信息
      $(".link_sq").live('click',function(){
          var workid = $(this).attr('dataid');
      //两列等高处理'
        $(this).siblings(".zw_des").show();
        new EqualModH($(this));
        $(this).siblings(".zw_des").hide();
        art.dialog({
          lock: true,
          fixed: true,
          title:'职位详情',
          background: '#272727', // 背景色
          opacity: 0.55,  // 透明度
          width: 884,
          content: document.getElementById(workid)
         });
      })
  });

  function turnPage(pageIndex, flag) {
	  var urlPath = sinosoft.base+'/shop/article!'+flag+'Ajax.action?pageIndex='+pageIndex;
	  jQuery.ajax({
			type: 'post',
			url: urlPath,
			dataType: "json",
			async: true,
			success: function(data) {
				if (data.info != null && data.info != '') {
					jQuery("#"+flag+"Info").html(data.info);
				}
				if (data.page != null && data.page != '') {
					jQuery("#"+flag+"Page").html(data.page);
				}
				if (flag == 'jobs') {
					
				}
			}
		});
	  
  }


/** 
 * @des: 两列div等高
 * @maker: guodongqi
 * @date: 2016.08.17
 */
var EqualModH = function(opt) {

  this._opt = $.extend({
    ware: '.gw_cons',
    dom1: '.gw_left',
    dom2: '.gw_right',
  }, opt);

  this.setInit();
  this.addEvent();
  
}
EqualModH.prototype.setInit = function(){
  this.warep = $(this._opt).siblings();
  this.ware = $(this._opt.ware,this.warep);
  this.dom1 = $(this._opt.dom1,this.ware);
  this.dom2 = $(this._opt.dom2,this.ware);

}
EqualModH.prototype.addEvent = function(){
   var _this = this;
   var h1 =  _this.dom1.height();
   var h2 =  _this.dom2.height();
   var mh = Math.max(h1, h2);
   _this.dom1.height(mh);
   _this.dom2.height(mh);
}