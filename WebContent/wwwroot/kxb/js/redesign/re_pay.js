/**
 * @des: 开心保PC：支付有礼
 * @maker: GuoDongQi
 * @date: 2017.07.20
 */
Couponbox = function(data,opt) {
    this._data = data;
    this._opt = $.extend({
       CouponBox : "#yhj_box",
         YhjList : ".yhj_list_con",
          yhjBtn : "#yhj_box .yhj_btn",
          keyVal : ["name", "value","confine","classname","time","link"], 
        json_key : "coupon"
    }, opt);

    this.setInit();  //初始化DOM
    this.setFunc();  //绑定事件
    this.initData(); //初始化优惠券数据
    this.addEvent(); //事件处理

}
Couponbox.prototype.setInit = function() {

    this.CouponBox = $(this._opt.CouponBox);
    this.YhjList = $(this._opt.YhjList);
    this.yhjBtn = $(this._opt.yhjBtn);

}
Couponbox.prototype.setFunc= function() {

    //添加优惠券
    this.addYhj = function(json_data,CouponBox,YhjList,yhjBtn){
    
        if($.isEmptyObject(json_data)){
            CouponBox.hide();
        }else{
            YhjList.html("");
            CouponBox.show();
                for(var i=0; i<json_data.length; i++) {
                    var id = i;
                    var name = json_data[id][this._opt.keyVal[0]];
                    var value = json_data[id][this._opt.keyVal[1]];
                    var confine = json_data[id][this._opt.keyVal[2]];
                    var classname = json_data[id][this._opt.keyVal[3]];
                    var time = json_data[id][this._opt.keyVal[4]];
                    var link = json_data[id][this._opt.keyVal[5]];
                    YhjList.append("<a href="+link+" vlpageid='coupon"+i+"' exturl='http://www.kaixinbao.com/coupon"+i+"' rel='nofollow' onclick='return(VL_FileDL(this));return false;' target='_blank'><dl class='yhj_dl'><dt><div class='yhj_pay'><h4>"+name+"</h4><span>"+confine+"</span></div><div class='yhj_pays'>"+value+"</div></dt><dd class='yhj_xz'><span>"+classname+"</span><em>有效期：<i>"+time+"</i></em></dd></dl></a>");
                }
        }
    }

}
Couponbox.prototype.initData = function() {

    var json_data = this._data[this._opt.json_key];
    this.addYhj(json_data,this.CouponBox, this.YhjList,this.yhjBtn);

}
Couponbox.prototype.addEvent= function() {

    var _this = this;

    _this.yhjBtn.click(function(){
        _this.CouponBox.hide();
    })

}