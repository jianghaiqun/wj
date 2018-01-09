<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>积分兑换经验</title>
<meta name="Author" content="SINOSOFT Team" />
<meta name="Copyright" content="SINOSOFT" />


<link rel="icon" href="favicon.ico" type="image/x-icon" />

<link href="${shopStaticPath}/template/shop/css/StyCss.css" rel="stylesheet" type="text/css" />
<link href="${shopStaticPath}/template/shop/css/MemberCenterSty.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/commonality.css" />
<link rel="stylesheet" type="text/css" href="${staticPath}/style/redesign/re_header.css" />
<script type="text/javascript" src="${shopStaticPath}/template/shop/js/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<div class="wrapper">

	<div class="MemberCenterMiddArea">
	<div class="MemberCenterNavArea">
    	<ul>
        	<li class="IcoHome"><a href="${staticPath}/">首页</a></li>
            <li class="IcoHome"><a href="member_center!index.action">会员中心</a></li>
            <li>积分兑换经验</li>
          
 </ul>
    	<div class="clear"></div>
    </div>
        <div class="MemberMenuArea mr10">
    	<div class="ActiveArea" style="top:148px;*top:152px;_top:152px;">
        	<a href="point!trade.action" class="IcoUser_6">积分兑换经验</a>
            
        </div>
<ul class="mcmenulist">
            <li><a href="profile!edit.action" class="IcoUser_1">个人资料</a></li>
            <li><a href="order_query!list.action" class="IcoUser_2">我的订单</a></li> 
            <li><a href="password!edit.action" class="IcoUser_1" >修改密码</a></li>
            <li><a href="point!trade.action" class="IcoUser_6">积分兑换经验</a></li>
            <li><a href="point!scan.action" class="IcoUser_6">积分兑换记录</a></li>
            <li><a href="point!obtain.action" class="IcoUser_6">积分获取记录</a></li>
            <li><a href="coment!mycomment.action" class="IcoUser_8">我的评论</a></li>
            <li><a href="question!ask.action" class="IcoUser_9">我的提问</a></li>
            <li><a href="answer!reply.action" class="IcoUser_10">我的回答</a></li>
            
            <li><a href="stow!scan.action" class="IcoUser_12">我的收藏</a></li>
             <li><a href="my_compare!show.action" class="IcoUser_14">我的比价记录</a></li>
        </ul>
    </div>
		
		
	 <div class="MemberCenterOrderArea">
		<h3 class="titmc02">积分兑换</h3>
		<div class="poarea">
			<div class="po01"><p><span>可用积分：</span>${currentValidatePoint}</p><p><span>冻结积分：</span>${frozenPoint}</p></div>
			<div class="po02"><span class="font14"><a href="${front}/jifen/">开心果可以换什么？ 去商城逛逛</a></span>&nbsp;&nbsp;</div>
			<div class="po03"><a href="${front}/jifen/"><img src="${shopStaticPath}/template/shop/images/btnmcgoto.jpg" /></a></div>
		</div>
		
		<div class="mcsearch">
				<ul><form action="${base}/shop/point!toDo.action" method="post" name="submitForm" id="submitForm">
					<li>
						<label>密码：</label><input type="password" name="password" id="password" style="width:140px;" class="inputstyle01"/>
						<label>支出：</label><input type="text" name="outInt" id="outInt" style="width:140px;" class="inputstyle01"/>&nbsp;积分
					</li>
					<li>
						<label>兑换比率：</label>1点积分换2点经验值
					</li>
					<li>
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="提交" class="btnorange" />
					</li>
					</form>
				</ul>
	  </div>
		<div style="color:red;font-size: 13px;"><span id="status"></span></div>
    </div>
	<div class="clear"></div>
</div>
<div class="clear"></div>

  <#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
  <#include "/wwwroot/kxb/block/community_v1.shtml" />
</div>
</body>
<script type="text/javascript">
// 表单验证
jQuery().ready( function() {
	jQuery("#submitForm").validate({
		submitHandler: function(form) {
			if(jQuery("#password").val() == ''){
				alert("请输入密码!");
				return false;
			}
			if(jQuery("#outInt").val() == ''){
				alert("请输入积分!");
				return false;
			}
			var value=jQuery("#outInt").val();
			var zip = /^[0-9]+$/;
			if(value == null || value == "" || "undefined" == typeof(value)){}
			else{
		     if(!zip.test(value)){
				alert("积分输入有误!");
				jQuery("#outInt").attr("value","");
			    return false;
			}}
			
			jQuery("#submitForm").ajaxSubmit({
				dataType: "json",
				success: function(data) {
					if (data.status == "success") {
					alert(data.message);
					} else {
				alert(data.message);
					}
				}
			});
		}
	});

});
</script>
</html>