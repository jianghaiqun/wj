/***
 *
 *	http://www.sinosoft.com.cn
 *
 **/

jQuery().ready( function() {

	var $allCheck = jQuery(".list input.allCheck");// 全选复选框
	var $idsCheck = jQuery(".list input[name='ids']");// ID复选框
	var $deleteButton = jQuery(".list input.deleteButton");// 删除按钮
	
	var $listForm = jQuery(".list form");// 列表表单
	var $searchButton =  jQuery("#searchButton");// 查询按钮
	var $pageNumber = jQuery("#pageNumber");// 当前页码
	var $pageSize = jQuery("#pageSize");// 每页显示数
	var $sort = jQuery(".list .sort");// 排序
	var $orderBy = jQuery("#orderBy");// 排序方式
	var $order = jQuery("#order");// 排序字段
	
	// 全选
	$allCheck.click( function() {
		if (jQuery(this).attr("checked") == true) {
			$idsCheck.attr("checked", true);
			$deleteButton.attr("disabled", false);
		} else {
			$idsCheck.attr("checked", false);
			$deleteButton.attr("disabled", true);
		}
	});
	
	// 无复选框被选中时,删除按钮不可用
	$idsCheck.click( function() {
		var $idsChecked = jQuery(".list input[name='ids']:checked");
		if ($idsChecked.size() > 0) {
			$deleteButton.attr("disabled", false);
		} else {
			$deleteButton.attr("disabled", true)
		}
	});
	
	// 批量删除
	$deleteButton.click( function() {
		var url = jQuery(this).attr("url");
		var $idsCheckedCheck = jQuery(".list input[name='ids']:checked");
		if (confirm("您确定要删除吗？") == true) {
			jQuery.ajax({
				url: url,
				data: $idsCheckedCheck.serialize(),
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$deleteButton.attr("disabled", true)
				},
				success: function(data) {
					$deleteButton.attr("disabled", false)
					if (data.status == "success") {
						$idsCheckedCheck.parent().parent().remove();
					}
					jQuery.message(data.status, data.message);
				}
			});
		}
	});

	// 查找
	$searchButton.click( function() {
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 每页显示数
	$pageSize.change( function() {
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 排序
	$sort.click( function() {
		var $currentOrderBy = jQuery(this).attr("name");
		if ($orderBy.val() == $currentOrderBy) {
			if ($order.val() == "") {
				$order.val("asc")
			} else if ($order.val() == "desc") {
				$order.val("asc");
			} else if ($order.val() == "asc") {
				$order.val("desc");
			}
		} else {
			$orderBy.val($currentOrderBy);
			$order.val("asc");
		}
		$pageNumber.val("1");
		$listForm.submit();
	});

	// 排序图标效果
	sortStyle();
	function sortStyle() {
		var orderByValue = $orderBy.val();
		var orderValue = $order.val();
		if (orderByValue != "" && orderValue != "") {
			jQuery(".sort[name='" + orderByValue + "']").after('<span class="' + orderValue + 'Sort">&nbsp;</span>');
		}
	}
	
	// 页码跳转
	jQuery.gotoPage = function(id) {
		$pageNumber.val(id);
		$listForm.submit();
	}
	
});


function updateState(name,val){
	jQuery.post("template_mail!modifyState.action?mailConfig.name="+name+"&mailConfig.isEmail="+val, updatecallback,'json');
}
function updatecallback(data){
	
	var res = eval(data);
	if(res){
		Dialog.alert("修改成功");
	}else{
		Dialog.alert("修改失败");
	}
}