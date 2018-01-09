<%@ taglib prefix="s" uri="/struts-tags" %>
<!-- comment js begin -->
<script type="text/javascript">
function getPageByPageIndex(pageIndex)
{
	document.getElementById("cmntPageIndex").value = pageIndex + "";
	jQuery.ajaxLoadImg.show('commentDetail'+pageIndex);
	jQuery("#GetCommentPage").ajaxSubmit
	({
		dataType: "json",
		success: function(data)
		{
			jQuery.ajaxLoadImg.hide('commentDetail'+pageIndex);
			if (data.status == "success")
			{
				document.getElementById("commentlist").innerHTML = data.message;
				document.getElementById("pagination1").innerHTML = data.pageBarHtml;
				new praise({mestxtFn:showtip,mesbtnFn:submes,tagtnFn:praisefun});
			}
			else
			{
				alert("获取第" + pageIndex + "页的评论列表失败！");
			}
		}
	});
}

function getPlPage(pageIndex) {
	
	if ((null==pageIndex) || (pageIndex == '')) {
		return;
	}
	
	if (document.getElementById("cmntPageNum").value < pageIndex) {
		return;
	}
	
	if (pageIndex < 1) {
		return;
	}
	
	getPageByPageIndex(pageIndex);
	
}

</script>
<!-- comment js end -->
<form name="GetCommentPage" id="GetCommentPage"
	action="${ServerContext}/shop/eval_comment!ajaxGetPage.action"
	method="post">
	<input type="hidden" id="RelaID" name="RelaID" value="${RelaID}" />
	<input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}" />
	<input type="hidden" id="CatalogType" name="CatalogType" value="${CatalogType}" />
	<input type="hidden" id="SiteID" name="SiteID" value="${SiteID}" />
	<input type="hidden" id="cmntPageIndex" name="cmntPageIndex" value="1" />
	<input type="hidden" id="cmntPageSize" name="cmntPageSize" value="${pageSize}" />
	<input type="hidden" id="cmntPageNum" name="cmntPageNum" value="${pageCount}" />
</form>
<div class="columntit graybordert">用户讨论区</div>
	<dl class="user_mesage">
		<dt><img src="${StaticServerContext}/images/redesign/user_headr_03.png" alt=""></dt>
		<dd>
			<textarea name="" class="user_text" id="user_text" maxlength="500"></textarea>
			<div class="okmessage"><input type="button" id="useR_bts" class="useR_bts" value="评论"/></div>
		</dd>
	</dl>
	<div id="commentlist">
<!-- comment loop begin -->
	<span id="tag_lina"></span>
	<dl class="user_mes">
		<dt><img src="${headPicPath}" alt=""></dt>
		<dd class="user_con ${hot_mes}">
			<p>${Content}</p>
			<div class="user_active">
				<span class="mes_name">${AddUser}</span>
				<span class="mes_time">${AddTime}</span>
				<span class="del" name="${ID}" style="display: ${delStyle}">删除</span>
				<span name="${ID}" class="praise ${praiseClasss}">点赞
					<!--（<i id="praised_${ID}">${praisedNum}</i>）-->
				</span>
			</div>
		</dd>
		<dd class="admin_con" style="display: ${replyStyle}">
			<img src="${StaticServerContext}/images/redesign/user_headr_03.png" class="admin_h" alt="">
			<span>回复：</span>
			<p>${ReplyContent}</p>
		</dd>
	</dl>
<!-- comment loop end -->
	</div>
<!-- comment page -->
</div>
<div style="display:none">
	<!-- comment all hidden begin -->
	{评论：${Content},
	 回复：${ReplyContent}}
	<!-- comment all hidden end -->
</div>