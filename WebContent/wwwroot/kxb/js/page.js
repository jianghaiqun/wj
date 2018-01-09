function gotoHead(alias) {
	
	if (!viladateCount()) {
		return;
	}
	var parent_iframe = jQuery("iframe", window.parent.document);
	for (var i = 0; i < parent_iframe.length; i++) {
		var parent_form = parent_iframe.eq(i).prev();
		
		if (typeof (parent_form) == "undefined" || parent_form == null || parent_form == '') {
			continue;
		}
		
		var actionurl = parent_form.attr("action") + '';
		actionurl = actionurl.substring(actionurl.lastIndexOf('/') + 1, actionurl.indexOf('.action'));
		if (actionurl == (alias + '')) {
			var now_index = jQuery(".plpage04").attr('name');
			if (now_index <= 1) {
				alert('已经是首页！');
				
			} else {
				parent_form.attr("action", parent_form.attr("action").substring(0, parent_form.attr("action").indexOf('.action') + 7));
				parent_form.submit();
			}
		}
	}

}
/**
 * 尾页
 * 
 * @param alias
 */
function gotoEnd(alias) {
	if (!viladateCount()) {
		return;
	}
	var parent_iframe = jQuery("iframe", window.parent.document);
	for (var i = 0; i < parent_iframe.length; i++) {
		var parent_form = parent_iframe.eq(i).prev();
		if (typeof (parent_form) == "undefined" || parent_form == null || parent_form == '') {
			continue;
		}
		var actionurl = parent_form.attr("action") + '';
		actionurl = actionurl.substring(actionurl.lastIndexOf('/') + 1, actionurl.indexOf('.action'));
		if (actionurl == (alias + '')) {
			var now_index = jQuery(".plpage04").attr('name');
			var pagecount = parseInt(jQuery('#pagecount').html());
			if (now_index >= pagecount) {
				alert('已经是尾页！');
				
			} else {
				parent_form.attr("action", parent_form.attr("action").substring(0, parent_form.attr("action").indexOf('.action') + 7) + '?page_Index=' + pagecount );
				parent_form.submit();
			}
		}
	}
}
/**
 * 上一页
 * 
 * @param alias
 */
function gotoLast(alias) {
	if (!viladateCount()) {
		return;
	}
	var parent_iframe = jQuery("iframe", window.parent.document);
	for (var i = 0; i < parent_iframe.length; i++) {
		var parent_form = parent_iframe.eq(i).prev();
		if (typeof (parent_form) == "undefined" || parent_form == null
				|| parent_form == '') {
			continue;
		}
		var actionurl = parent_form.attr("action") + '';
		actionurl = actionurl.substring(actionurl.lastIndexOf('/') + 1, actionurl.indexOf('.action'));
		
		if (actionurl == (alias + '')) {
			var now_index = jQuery(".plpage04").attr('name');
			var index = parseInt(now_index) - 1;
			var pagecount = parseInt(jQuery('#pagecount').html());
			if (now_index > pagecount) {
				alert('页码有误！');
				
			} else if (now_index <= 1) {
				alert('已经是第一页了');
				
			} else {
				parent_form.attr("action",parent_form.attr("action").substring(0, parent_form.attr("action").indexOf('.action') + 7) + '?page_Index=' + index );
				parent_form.submit();
			}
		}
	}
}
/**
 * 下一页
 * 
 * @param alias
 */
function gotoNext(alias) {
	if (!viladateCount()) {
		return;
	}
	var parent_iframe = jQuery("iframe", window.parent.document);
	for (var i = 0; i < parent_iframe.length; i++) {
		var parent_form = parent_iframe.eq(i).prev();
		if (typeof (parent_form) == "undefined" || parent_form == null
				|| parent_form == '') {
			continue;
		}
		var actionurl = parent_form.attr("action") + '';
		actionurl = actionurl.substring(actionurl.lastIndexOf('/') + 1,
				actionurl.indexOf('.action'));
		if (actionurl == (alias + '')) {
			var now_index = jQuery(".plpage04").attr('name');
			var index = parseInt(now_index) + 1;
			var pagecount = parseInt(jQuery('#pagecount').html());
			if (now_index >= pagecount) {
				alert('已经是最后一页了');
			} else {
				parent_form.attr("action", parent_form.attr("action").substring(0,
						parent_form.attr("action").indexOf('.action') + 7)
						+ '?page_Index=' + index );
				parent_form.submit();
			}
		}
	}
}
/**
 * 跳转页
 * 
 * @param alias
 * @param index
 */
function gotoPage(alias, index,pagecount,idalias) {
	if (!viladateCount()) {
		return;
	}
	var div=jQuery("#"+idalias);
	var id_str=div.attr("id");
	id_str=id_str.substring(id_str.lastIndexOf('_')+1,id_str.length);
	var point="";
	div.parent().find("span").each(function (){
		var style_class=jQuery(this).attr('class');
		if(style_class.indexOf('jf_sel')!=-1){
			 point=jQuery(this).find("input").eq(0).val();
		}
	})
	div.load(sinosoft.base+"/shop/"+alias+ '?page_Index=' + index+"&modeltype="+id_str+"&idalias="+idalias+"&pointvalue="+point );
//	return;
//	alert(alias);
//	var parent_page_div=jQuery("div[id^='page_model_info_']",window.parent.document)
//	//var parent_iframe = jQuery("iframe", window.parent.document);
//	for (var i = 0; i < parent_page_div.length; i++) {
////		var parent_form = parent_page_div.eq(i).prev();
////		if (typeof (parent_form) == "undefined" || parent_form == null
////				|| parent_form == '') {
////			continue;
////		}
//		// 总页码
//		//var pagecount = parseInt(jQuery('#pagecount').html());
//		var page_div=parent_page_div.eq(i);
//		if (typeof (page_div) == "undefined" || page_div == null|| page_div == '') {
//			continue;
//		}
//		var id_str=page_div.attr("id");
//		id_str=id_str.substring(id_str.lastIndexOf('_')+1,id_str.length);
//		//var actionurl=page_div.data['action'];
//		alert(id_str);
//		if (actionurl == (alias + '')) {
//			if (index > pagecount) {
//				alert('页码有误,请刷新页面！');
//			} else {
//				parent_page_div.load(actionurl+ '?page_Index=' + index+"&modeltype="+this.id );
//			}
//		}
//	}
}
/**
 * 跳转到指定页
 * 
 * @param alias
 * @param index
 */
function gotoDiyPage(alias) {
	if (!viladateCount()) {
		return;
	}
	var parent_iframe = jQuery("iframe", window.parent.document);
	for (var i = 0; i < parent_iframe.length; i++) {
		var parent_form = parent_iframe.eq(i).prev();
		if (typeof (parent_form) == "undefined" || parent_form == null
				|| parent_form == '') {
			continue;
		}
		var actionurl = parent_form.attr("action") + '';
		actionurl = actionurl.substring(actionurl.lastIndexOf('/') + 1, actionurl.indexOf('.action'));
		if (actionurl == (alias + '')) {
			// 总页码
			var pagecount = parseInt(jQuery('#pagecount').html());
			
			if(jQuery('#_PageBar_Index_0').val() == null || $.trim(jQuery('#_PageBar_Index_0').val()) == ''){
				alert('请输入有效页码');
				return ;
			}
			
			var index = parseInt(jQuery('#_PageBar_Index_0').val());
			if (index == 'NaN' || index < 1) {
				alert('请输入有效页码');
				return;
			}
			// 当前页面
			var now_index = parseInt(jQuery(".plpage04").attr('name'));
			if (index > pagecount) {
				alert('页码输入有误，最大页码为' + pagecount);
				
			} else if (now_index == index) {
				alert('当前页面已经是第' + index + '页！');
				
			} else {
				parent_form.attr("action", parent_form.attr("action").substring(0,
						parent_form.attr("action").indexOf('.action') + 7)
						+ '?page_Index=' + index );
				parent_form.submit();
			}
		}
	}
}
/**
 * 校验分页是否有数据
 */
function viladateCount() {
	var datacounts = parseInt(jQuery('#datacounts').val());
	if (datacounts < 1) {
		return false;
	} else {
		return true;
	}
}

