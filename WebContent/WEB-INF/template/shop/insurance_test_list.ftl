<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>保险测试结果列表页</title>
<link rel="stylesheet" type="text/css" href="${staticPath}/style/new_test.css">
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.js"></script>
<script src="${shopStaticPath}/Test.js"></script> 
<#include "/wwwroot/kxb/block/community_v1.shtml"> 
<#include "/wwwroot/kxb/block/kxb_header_new_css.shtml">
<script type="text/javascript">
	/**
	 * 分页查询
	 */
	function pageList(page){
		var  sex= isEmpty(jQuery('input:radio[name="sex"]:checked').val());
		var  age= isEmpty(jQuery("#age").val());
		var  work= isEmpty(jQuery("#work").val());
		var  travel= isEmpty(jQuery('input:radio[name="travel"]:checked').val());
		var  medical= isEmpty(jQuery('input:radio[name="medical"]:checked').val());
		var  pension= isEmpty(jQuery('input:radio[name="pension"]:checked').val());
		var  credit= isEmpty(jQuery('input:radio[name="credit"]:checked').val());
		var  salary= isEmpty(jQuery("#salary").val());
		jQuery.ajax({
			type: 'post',
			url: '${base}/shop/insurance_test!searchInsuranceTestListBy.action?page='+page+'&param='+sex+","+age+","+work+","+travel+","+medical+","+pension+","+credit+","+salary,
			dataType: "json",
			async: false,
			success: function(data) {
				jQuery.each(data, function(index, object) {
					if("bodyhtml"==index){
						jQuery("#insurancetestbody").html(object);
					}
					if("footerhtml"==index){
						jQuery("#productsPageBar").html(object);
					}
				});
				 	if(jQuery("#insurancetestbody").text()==''){
						jQuery("#none_shop").show();
						jQuery("#productsPageBar").hide();
					}else{
						jQuery("#none_shop").hide();
						jQuery("#productsPageBar").show();
					}
			}
		});

	}
	function isEmpty(val) {
		if (("" == val) || (null == val) || ("undefined" == typeof (val))) {
			return "";
		} else {
			return val;
		}
	}
	jQuery(function() {
		jQuery.ajax({
			type: 'post',
			url: '${base}/shop/insurance_test!searchInsuranceTestListBy.action?page=1&param=',
			dataType: "json",
			async: false,
			success: function(data) {
				jQuery.each(data, function(index, object) {
					if("bodyhtml"==index){
						jQuery("#insurancetestbody").html(object);
					}
					if("footerhtml"==index){
						jQuery("#productsPageBar").html(object);
					}
				});
			}
		});
	});
</script>
</head>
<body>
<!-- 公共头部 开始 -->
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<!-- 公共头部 结束 -->

<!--保险测试结果列表开始-->
<div class="wrapper test_bg">
<div class="select_need clearfix">
<div class="select_l">
<dl class="select_con clearfix">
	<dt>人群：</dt>
	<dd><label><input type="radio" name="sex" value="" onclick="pageList(1)"
		id="sex_0">不限</label></dd>
	<dd><label><input type="radio" name="sex" value="1" onclick="pageList(1)"
		id="sex_1">男</label></dd>
	<dd><label><input type="radio" name="sex" value="2" onclick="pageList(1)"
		id="sex_2">女</label></dd>
	<dd><label><input type="radio" name="sex" value="3" onclick="pageList(1)"
		id="sex_2">子女</label></dd>
</dl>
<dl class="select_con clearfix">
	<dt>年龄：</dt>
	<dd><select class="select_sel" id="age" onchange="pageList(1)">
		<option value="">不限</option>
		<option value="1">0-10</option>
		<option value="2">10-20</option>
		<option value="3">20-30</option>
		<option value="4">30-40</option>
		<option value="5">40-50</option>
		<option value="6">50-60</option>
	</select></dd>
</dl>
<dl class="select_con clearfix">
	<dt>工作类型：</dt>
	<dd><select class="select_sel" id="work" onchange="pageList(1)">
		<option value="">请选择</option>
		<option value="1">公务员单位</option>
		<option value="2">事业编制单位</option>
		<option value="3">国有企业</option>
		<option value="4">中外合资企业</option>
		<option value="5">普通企业</option>
		<option value="6">个体无商户</option>
		<option value="7">无业或退休</option>
		<option value="8">高危职业</option>
	</select></dd>
</dl>
<dl class="select_con clearfix" >
	<dt>旅行次数/年：</dt>
	<dd><label><input type="radio" name="travel" value="1" onclick="pageList(1)"
		id="Travel">无</label></dd>
	<dd><label><input type="radio" name="travel" value="2" onclick="pageList(1)"
		id="Travel">10次以下</label></dd>
	<dd><label><input type="radio" name="travel" value="3" onclick="pageList(1)"
		id="Travel">10次以上</label></dd>
</dl>
</div>
<div class="select_r">
<dl class="select_con clearfix">
	<dt>医疗保险：</dt>
	<dd><label><input type="radio" name="medical" value="" onclick="pageList(1)"
		id="Medical">不限</label></dd>
	<dd><label><input type="radio" name="medical" value="1" onclick="pageList(1)"
		id="Medical">无</label></dd>
	<dd><label><input type="radio" name="medical" value="2" onclick="pageList(1)"
		id="Medical">社保医疗</label></dd>
	<dd><label><input type="radio" name="medical" value="3" onclick="pageList(1)"
		id="Medical">商业医疗</label></dd>
	<dd><label><input type="radio" name="medical" value="4" onclick="pageList(1)"
		id="Medical">社保&商业医疗 </label></dd>
</dl>
<dl class="select_con clearfix">
	<dt>养老保险：</dt>
	<dd><label><input type="radio" name="pension" value="" onclick="pageList(1)"
		id="Pension">不限</label></dd>
	<dd><label><input type="radio" name="pension" value="1" onclick="pageList(1)"
		id="Pension">无</label></dd>
	<dd><label><input type="radio" name="pension" value="2" onclick="pageList(1)"
		id="Pension">社保养老</label></dd>
	<dd><label><input type="radio" name="pension" value="3" onclick="pageList(1)"
		id="Pension">商业养老</label></dd>
	<dd><label><input type="radio" name="pension" value="4" onclick="pageList(1)"
		id="Pension">社保&商业养老 </label></dd>
</dl>
<dl class="select_con clearfix">
	<dt>有无贷款：</dt>
	<dd><label><input type="radio" name="credit" value="" onclick="pageList(1)"
		id="Credit">不限</label></dd>
	<dd><label><input type="radio" name="credit" value="2" onclick="pageList(1)"
		id="Credit">有</label></dd>
	<dd><label><input type="radio" name="credit" value="1" onclick="pageList(1)"
		id="Credit">无</label></dd>
</dl>
<dl class="select_con clearfix">
	<dt>家庭年收入：</dt>
	<dd><select class="select_sel" id="salary" onchange="pageList(1)">
		<option value="">请选择</option>
		<option value="1">5万以下</option>
		<option value="2">5万-10万</option>
		<option value="3">10万-20万</option>
		<option value="4">20万-50万</option>
		<option value="5">50万以上</option>
	</select></dd>
</dl>
</div>
</div>
<div class="list_sxbox">
<div class="go_to_select ya_hei">
<h3 class="go_to_title">不知道如何购买保险?</h3>
<span class="go_to_des">1分钟参与开心保测试<br> 让我们为您专业推荐
</span> <a href="${base}/shop/insurance_test!insuranceTest.action" class="go_to_s" target="_blank";>立即参加</a></div>

<div class="test_con_s"  >
<div id="none_shop" style="display:none;">
<img src="${staticPath}/images/test/test_img_03.gif" width="505" height="169" alt="保险测试" class="test_none_img" /> 
<a href="${base}/shop/insurance_test!insuranceTest.action" class="go_to_test">GO!</a>
</div>
<div id="insurancetestbody">
	
</div>
</div>
<!-- 分页 start   -->
<div id="productsPageBar">
</div>
<!-- 分页    end-->
<div class="clear"></div>
</div>
</div>
<!--保险测试结果列表开始-->

<!--公共底部开始-->
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!--公共底部结束-->
</body>
</html>