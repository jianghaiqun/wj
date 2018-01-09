/*
#@des:侧边栏跟随滚动
*@maker:dongqi.guo
*@time:2013-8-22-15:20 
*/

SidebarFollow = function() {
	this.config = {
		element: null,
		distanceToTop: 0
	};
	this.cache = {
		originalToTop: 0,
		prevElement: null,
		parentToTop: 0,
		placeholder: jQuery('<div>')
	}
};
SidebarFollow.prototype = {
	init: function(config) {
		this.config = config || this.config;
		var _self = this;
		var element = jQuery(_self.config.element);
		if (element.length <= 0) {
			return
		}
		var prevElement = element.prev();
		while (prevElement.is(':hidden')) {
			prevElement = prevElement.prev();
			if (prevElement.length <= 0) {
				break
			}
		}
		_self.cache.prevElement = prevElement;
		var parent = element.parent();
		var parentToTop = parent.offset().top;
		var parentBorderTop = parent.css('border-top');
		var parentPaddingTop = parent.css('padding-top');
		_self.cache.parentToTop = parentToTop + parentBorderTop + parentPaddingTop;
		jQuery(window).scroll(function() {
			_self._scrollScreen({
				element: element,
				_self: _self
			})
		});
		jQuery(window).resize(function() {
			_self._scrollScreen({
				element: element,
				_self: _self
			})
		})
	},
	_scrollScreen: function(args) {
		var _self = args._self;
		var element = args.element;
		var prevElement = _self.cache.prevElement;
		var toTop = _self.config.distanceToTop;
		var bodyToTop = parseInt(jQuery('body').css('top'), 10);
		if (!isNaN(bodyToTop)) {
			toTop += bodyToTop
		}
		var elementToTop = element.offset().top - toTop;
		var referenceToTop = 0;
		if (prevElement && prevElement.length === 1) {
			referenceToTop = prevElement.offset().top + prevElement.outerHeight()
		} else {
			referenceToTop = _self.cache.parentToTop - toTop
		}
		if (jQuery(document).scrollTop() > elementToTop) {
			var elementHeight = element.outerHeight();
			_self.cache.placeholder.css('height', elementHeight).insertBefore(element);
			_self.cache.originalToTop = elementToTop;
			element.css({
				top: toTop + 'px',
				position: 'fixed'
			})
		} else if (_self.cache.originalToTop > elementToTop || referenceToTop > elementToTop) {
			_self.cache.placeholder.remove();
			element.css({
				position: 'static'
			})
		}
	}
};