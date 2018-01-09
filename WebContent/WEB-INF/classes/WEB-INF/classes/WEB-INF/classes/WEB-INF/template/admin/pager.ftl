<script type="text/javascript">
jQuery().ready( function() {

	jQuery("#pager").pager({
		pagenumber: ${pager.pageNumber},
		pagecount: ${pager.pageCount},
		buttonClickCallback: jQuery.gotoPage
	});

})
</script>
<span id="pager"></span>
<input type="hidden" name="pager.pageNumber" id="pageNumber" value="${pager.pageNumber}" />
<input type="hidden" name="pager.orderBy" id="orderBy" value="${pager.orderBy}" />
<input type="hidden" name="pager.orderType" id="order" value="${pager.orderType}" />