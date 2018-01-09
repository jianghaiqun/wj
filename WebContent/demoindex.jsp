<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>demo index</title>
<style type="text/css">
	a{
		margin: 10px;
		padding: 10px;
		font-size: 20px;
		
	}
</style>
<script src="Framework/Main.js"></script>
<script type="text/javascript" src="template/common/js/jquery.js"></script>
<script type="text/javascript" src="wwwroot/kxb/js/template/common/js/base.js"></script>
<script type="text/javascript" src="wwwroot/kxb/js/artDialog.js"></script>

<script type="text/javascript">

function activityCoupon(activitysn) {
	var activitysn = jQuery("#activitysn").val();  // activitysn活动号 
	jQuery.ajax({
		type: 'post',
		url: sinosoft.base+'/shop/member_send_coupon!sendCouponDeal.action?callback=?&activitysn='+activitysn,
		dataType: "json",
		async: true,
		success: function(data) {
			if (data.status == 'notLogin') {
				//art.dialog.data('base', sinosoft.base);
				//art.dialog.data('front', sinosoft.front);
				window.open(sinosoft.front+'/template/art_login.html');
			}else {
				alert(data.message);
				/* art.dialog({
					content : data.message,
					id : 'error_id',
					title : '提示'
				}); */
			}
		}
	});
}
</script>
</head>
<body>
	<h3>领取入口</h3>
	<hr>
	<!-- 用户:<input id="memberid" type="text" > -->
	活动号:<input id="activitysn" type="text" >
	<button onclick="activityCoupon()">...点击领取...</button>
</body>
</html>