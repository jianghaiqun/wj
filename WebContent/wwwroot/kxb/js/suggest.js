(function($) {

		$.suggest = function(input, options) {
	
			var $input = $(input).attr("autocomplete", "off");
			var $results;

			var timeout = false;		// hold timeout ID for suggestion results to appear	
			var prevLength = 0;			// last recorded length of $input.val()
			var cache = [];				// cache MRU list
			var cacheSize = 0;			// size of cache in chars (bytes?)
			
			if($.trim($input.val())=='' || $.trim($input.val())=='') $input.val('').css('color','#aaa');
			//if($.trim($input.val())=='' || $.trim($input.val())=='中文/拼音') $input.val('中文/拼音').css('color','#aaa');
			if( ! options.attachObject )
				options.attachObject = $(document.createElement("ul")).appendTo('body');

			$results = $(options.attachObject);
			$results.addClass(options.resultsClass);
			
			resetPosition();
			$(window)
				.load(resetPosition)		// just in case user is changing size of page while loading
				.resize(resetPosition);

			$input.blur(function() {
				setTimeout(function() { $results.hide() }, 200);
			});
			$('#travelAddress').focus( 	function(){ 	resetPosition(); } );
			$input.focus(function(){
				if($.trim($(this).val())==''){
					$(this).val('').css('color','#797979');
				}
				if($.trim($(this).val())==''){
					displayItems('');//显示热门城市列表
				}
				//alert(1);
				//alert(this.offsetLeft+"----------"+this.offsetTop);
			});
			$input.click(function(){
				var q=$.trim($(this).val());
				displayItems(q);
				$(this).select();
			});
						
			// help IE users if possible
			try {
				$results.bgiframe();
			} catch(e) { }

			$input.keyup(processKey);//
			
			function resetPosition() {
				// requires jquery.dimension plugin
				var offset = $input.offset();
				$results.css({
					top: (offset.top + input.offsetHeight) + 'px',
					left: offset.left + 'px'
				});
			}
			
			
			function processKey(e) {
				
				// handling up/down/escape requires results to be visible
				// handling enter/tab requires that AND a result to be selected
				if ((/27$|38$|40$/.test(e.keyCode) && $results.is(':visible')) ||
					(/^13$|^9$/.test(e.keyCode) && getCurrentResult())) {
		            
		            if (e.preventDefault)
		                e.preventDefault();
					if (e.stopPropagation)
		                e.stopPropagation();

	                e.cancelBubble = true;
	                e.returnValue = false;
				
					switch(e.keyCode) {
	
						case 38: // up
							prevResult();
							break;
				
						case 40: // down
							nextResult();
							break;
						case 13: // return
							selectCurrentResult();
							break;
							
						case 27: //	escape
							$results.hide();
							break;
	
					}
					
				} else if ($input.val().length != prevLength) {

					if (timeout) 
						clearTimeout(timeout);
					timeout = setTimeout(suggest, options.delay);
					prevLength = $input.val().length;
					
				}			
					
				
			}
			
			function suggest() {
			
				var q = $.trim($input.val());
				displayItems(q);
			}		
			function displayItems(items) {
				var html = '';
				if (items=='') {//热门城市遍历
					//alert(options.hot_list.length)
					/*for(h in options.hot_list){
						html+='<li rel="'+options.hot_list[h][0]+'"><a href="#'+h+'"><span>'+options.hot_list[h][1]+'</span>'+options.hot_list[h][0]+'</a></li>';
					}*/
					//html='<div class="gray ac_result_tip">请输入中文/拼音或者↑↓选择</div><ul>'+html+'</ul>';
					html='<ul>'+html+'</ul>';
				}
				else {
					/*if (!items)
					return;
					if (!items.length) {
						$results.hide();
						return;
					}*/
					for (var i = 0; i < options.source.length; i++) {//国内城市匹配
						var reg = new RegExp('^' + items + '.*$', 'im');
						if (reg.test(options.source[i][0]) || reg.test(options.source[i][0]) || reg.test(options.source[i][1]) || reg.test(options.source[i][2])) {
							html += '<li rel="' + options.source[i][0] + '"><a href="#' + i + '">' + options.source[i][0] + '</a></li>';
						}
					}
					if (html == '') {
						suggest_tip = '<div class="gray ac_result_tip">对不起，找不到：' + items + '</div>';
					}
					else {
						suggest_tip = '<div class="gray ac_result_tip">' + items + '，按拼音排序</div>';
					}
					html = suggest_tip + '<ul>' + html + '</ul>';
				}

				$results.html(html).show();
				$results.children('ul').children('li:first-child').addClass(options.selectClass);
				
				$results.children('ul')
					.children('li')
					.mouseover(function() {
						$results.children('ul').children('li').removeClass(options.selectClass);
						$(this).addClass(options.selectClass);
					})
					.click(function(e) {
						e.preventDefault(); 
						e.stopPropagation();
						selectCurrentResult();
					});
			}
						
			function getCurrentResult() {
			
				if (!$results.is(':visible'))
					return false;
			
				var $currentResult = $results.children('ul').children('li.' + options.selectClass);
				if (!$currentResult.length)
					$currentResult = false;
					
				return $currentResult;

			}
			
			function selectCurrentResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult) {
					$input.val($currentResult.children('a').html().replace(/<span>.+?<\/span>/i,''));

					if( $(options.dataContainer) ) {
						$(options.dataContainer).val($currentResult.attr('rel'));
					}
					$results.hide();
					jQuery("#suggest").hover(function(){
					    jQuery("#que_lv_sf").show();
					    jQuery(this).hide();
					});
					/*if (options.onSelect) {
						options.onSelect.apply($input[0]);
					}*/
				}
			
			}
			
			function nextResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult)
					$currentResult
						.removeClass(options.selectClass)
						.next()
							.addClass(options.selectClass);
				else
					$results.children('ul').children('li:first-child').addClass(options.selectClass);
			
			}
			
			function prevResult() {
			
				$currentResult = getCurrentResult();
			
				if ($currentResult)
					$currentResult
						.removeClass(options.selectClass)
						.prev()
							.addClass(options.selectClass);
				else
					$results.children('ul').children('li:last-child').addClass(options.selectClass);
			
			}
	
		}
		
		$.fn.suggest = function(source, options) {
		
			if (!source)
				return;
		
			options = options || {};
			options.source = source;
			options.hot_list=options.hot_list || [];
			options.delay = options.delay || 0;
			options.resultsClass = options.resultsClass || 'ac_results';
			options.selectClass = options.selectClass || 'ac_over';
			options.matchClass = options.matchClass || 'ac_match';
			options.minchars = options.minchars || 1;
			options.delimiter = options.delimiter || '\n';
			//options.onSelect = options.onSelect || false;
			options.dataDelimiter = options.dataDelimiter || '\t';
			options.dataContainer = options.dataContainer || '#SuggestResult';
			options.attachObject = options.attachObject || null;
	
			this.each(function() {
				new $.suggest(this, options);
			});
	
			return this;
			
		};
		
	})(jQuery);
	
	(function($){
	
$.dimensions = {
	version: '1.2'
};

// Create innerHeight, innerWidth, outerHeight and outerWidth methods
$.each( [ 'Height', 'Width' ], function(i, name){
	
	// innerHeight and innerWidth
	$.fn[ 'inner' + name ] = function() {
		if (!this[0]) return;
		
		var torl = name == 'Height' ? 'Top'    : 'Left',  // top or left
		    borr = name == 'Height' ? 'Bottom' : 'Right'; // bottom or right
		
		return this.is(':visible') ? this[0]['client' + name] : num( this, name.toLowerCase() ) + num(this, 'padding' + torl) + num(this, 'padding' + borr);
	};
	
	// outerHeight and outerWidth
	$.fn[ 'outer' + name ] = function(options) {
		if (!this[0]) return;
		
		var torl = name == 'Height' ? 'Top'    : 'Left',  // top or left
		    borr = name == 'Height' ? 'Bottom' : 'Right'; // bottom or right
		
		options = $.extend({ margin: false }, options || {});
		
		var val = this.is(':visible') ? 
				this[0]['offset' + name] : 
				num( this, name.toLowerCase() )
					+ num(this, 'border' + torl + 'Width') + num(this, 'border' + borr + 'Width')
					+ num(this, 'padding' + torl) + num(this, 'padding' + borr);
		
		return val + (options.margin ? (num(this, 'margin' + torl) + num(this, 'margin' + borr)) : 0);
	};
});

// Create scrollLeft and scrollTop methods
$.each( ['Left', 'Top'], function(i, name) {
	$.fn[ 'scroll' + name ] = function(val) {
		if (!this[0]) return;
		
		return val != undefined ?
		
			// Set the scroll offset
			this.each(function() {
				this == window || this == document ?
					window.scrollTo( 
						name == 'Left' ? val : $(window)[ 'scrollLeft' ](),
						name == 'Top'  ? val : $(window)[ 'scrollTop'  ]()
					) :
					this[ 'scroll' + name ] = val;
			}) :
			
			// Return the scroll offset
			this[0] == window || this[0] == document ?
				self[ (name == 'Left' ? 'pageXOffset' : 'pageYOffset') ] ||
					$.boxModel && document.documentElement[ 'scroll' + name ] ||
					document.body[ 'scroll' + name ] :
				this[0][ 'scroll' + name ];
	};
});

$.fn.extend({
	position: function() {
		var left = 0, top = 0, elem = this[0], offset, parentOffset, offsetParent, results;
		
		if (elem) {
			// Get *real* offsetParent
			offsetParent = this.offsetParent();
			
			// Get correct offsets
			offset       = this.offset();
			parentOffset = offsetParent.offset();
			
			// Subtract element margins
			offset.top  -= num(elem, 'marginTop');
			offset.left -= num(elem, 'marginLeft');
			
			// Add offsetParent borders
			parentOffset.top  += num(offsetParent, 'borderTopWidth');
			parentOffset.left += num(offsetParent, 'borderLeftWidth');
			
			// Subtract the two offsets
			results = {
				top:  offset.top  - parentOffset.top,
				left: offset.left - parentOffset.left
			};
		}
		
		return results;
	},
	
	offsetParent: function() {
		var offsetParent = this[0].offsetParent;
		while ( offsetParent && (!/^body|html$/i.test(offsetParent.tagName) && $.css(offsetParent, 'position') == 'static') )
			offsetParent = offsetParent.offsetParent;
		return $(offsetParent);
	}
});

function num(el, prop) {
	return parseInt($.curCSS(el.jquery?el[0]:el,prop,true))||0;
};

})(jQuery);

//初始化旅行地点-从数据库查询
var commoncitys=new Array();
var citys=new Array();
jQuery.ajax({
	type: 'post',
	url: sinosoft.base + "/shop/index_navigation!queryAddressList.action?callback=?",
	dataType: "json",
	async: false,
	success: function(data) {
		var tList = data.address;
		for(var i=0;i<tList.length;i++){
			citys[i]=new Array(tList[i].codeValue,tList[i].prop1,tList[i].prop3);
		}
	}
});
jQuery("#suggest").hover(function(){
    jQuery("#que_lv_sf").show();
    jQuery(this).show();
});
//初始化履行地点数据-封装到页面
jQuery("#travelAddress").suggest(citys,{hot_list:commoncitys,dataContainer:'#arrcity_3word', attachObject:'#suggest'});
jQuery("#travelAddress").click(function(){
	    jQuery("#suggest").hover(function(){
		    jQuery("#que_lv_sf").show();
		    jQuery(this).show();
		});
});
