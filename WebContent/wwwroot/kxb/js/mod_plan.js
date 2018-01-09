/**
 * @des: 倒计时
 * @maker: 宋再冉
 * @date: 2016.6.7
 */
var timeCountDown = function(opt) {

	this._opt = jQuery.extend({
		wrap: '.code_again',
		elem: '.code_again em',
		deadLine: 60, // 单位为s
		startClass: 'count_down',
		startHtml: '秒后重发',
		endHtml: '点击验证',
		whichPage: '',
		sendCode: function(){},
		callBack: function(){}
	}, opt);

	this.setInit();
	this.addEvent();
	
}
timeCountDown.prototype.setInit = function() {
	this.wrap = jQuery(this._opt.wrap);
	this.elem = jQuery(this._opt.elem);
	this.startClass = this._opt.startClass;
	this.startHtml = this._opt.startHtml;
	this.endHtml = this._opt.endHtml;
	this.deadLine = this._opt.deadLine;
	this.isCounting = this.wrap.hasClass(this.startClass);
	this.sendCode = this._opt.sendCode;
	this.callBack = this._opt.callBack;
	
}
timeCountDown.prototype.addEvent = function() {
	
	var _this = this;

	if(_this.isCounting){
		_this.start();
	} else {
		switch(_this._opt.whichPage) {
			case 'sign':
				_this.wrap.removeClass(_this.startClass)
					 .html(_this.endHtml)
					 .live("click", function(e) {
							
							e.preventDefault();
							
							// 兼容注册合成页
							// 手机号校验不合适时，无法发送验证码
							jQuery('.checkinput input').blur();
							if(jQuery('.checkinput').length = 0 || !jQuery('.checkinput').find('.error').length > 0){
								jQuery(this).html('<em>' + _this._opt.deadLine + '</em>' + _this.startHtml);
								clearInterval(_this.timer);
								_this.sendCode();
								_this.start();
							}
					 });
				break;
			 case 'yzm':
				 _this.wrap.removeClass(_this.startClass)
				 	.html(_this.endHtml)
				 	.live("click", function(e) {
		            	 
		                e.preventDefault();
		  
		                // 图片验证码未输入时，无法发送验证码
		                jQuery('.ex_mod_box .ex_input').blur();
		                
		                if(!jQuery('.ex_yzm').next(".error").length > 0){
		                  jQuery(this).html('<em>' + _this._opt.deadLine + '</em>' + _this.startHtml);
		                  clearInterval(_this.timer);
		                  _this.sendCode();
		                  _this.start();
		                }
		             });
		          break;
		};
	}
	
};
timeCountDown.prototype.start = function(){
	
	var _this = this;

	_this.deadLine = this._opt.deadLine;
	_this.elem = jQuery(this._opt.elem);
	_this.wrap.addClass(_this.startClass).die("click");
	_this.timer = setInterval(function(){
		_this.elem.html(--_this.deadLine);

		if(_this.deadLine <= 0) {
			clearInterval(_this.timer);
			_this.isCounting = false;
			_this.addEvent();

			_this.callBack();
		}
	}, 1000);
	
}