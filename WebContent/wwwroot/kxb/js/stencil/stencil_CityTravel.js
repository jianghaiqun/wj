// JavaScript Document
jQuery(function() {
			//城市专题焦点图切换
			jQuery('#change_city .a_bigImg').soChange({
				thumbObj : '#change_city .ul_change_city span',
				thumbNowClass : 'on',
				changeTime : 4000
			//自定义切换时间为4000ms
			});

			jQuery(".city_go_top").click(function() {
				window.scrollTo(0, 0);
			})

		});