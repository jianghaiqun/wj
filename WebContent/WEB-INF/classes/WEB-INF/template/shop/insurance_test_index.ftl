 <!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>开心保官方网站-保险测试页</title>
<link rel="stylesheet" type="text/css"
	href="${staticPath}/style/new_test.css">
<script type="text/javascript" src="${shopStaticPath}/template/common/js/jquery.js"></script>
<script src="${shopStaticPath}/Test.js"></script>
<#include "/wwwroot/kxb/block/community_v1.shtml">
<#include "/wwwroot/kxb/block/kxb_header_new_css.shtml">
</head>
<body>
<!-- 公共头部 开始 -->
<#include "/wwwroot/kxb/block/kxb_header_index_new_v2.shtml">
<!-- 公共头部 结束 -->

<!--保险测试开始-->
<form action="" method="post" id="insurancefrom">
<div class="wrapper test_bg">
<div class="test_con2">
<h2 class="test_tit_ad ya_hei">仅仅1分钟，即可获得个人保险需求</h2>
<P class="test_des_p">本测试，开心保承诺不会询问您的个人隐私等敏感信息，请您放心填写</P>
<div class="test_time">
<div class="time1 cf" id="time1">
<ul class="time_list">
	<li class="time_sel time_zb"><span class="time_s ya_hei">60S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">40S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">20S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">0S</span></li>
</ul>
</div>
<div class="time2 cf" id="time2" style="display: none;">
<ul class="time_list">
	<li class="time_sel time_zb"><span class="time_s ya_hei">60S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">40S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">20S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">0S</span></li>
</ul>
</div>
<div class="time3 cf" id="time3" style="display: none;">
<ul class="time_list">
	<li class="time_sel time_zb"><span class="time_s ya_hei">60S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">40S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">20S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">0S</span></li>
</ul>
</div>
<div class="time4 cf" id="time4" style="display: none;">
<ul class="time_list">
	<li class="time_sel time_zb"><span class="time_s ya_hei">60S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">40S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">20S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">0S</span></li>
</ul>
</div>

<div class="time5 cf" id="time5" style="display: none;">
<ul class="time_list">
	<li class="time_sel time_zb"><span class="time_s ya_hei">60S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">30S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">0S</span></li>
</ul>
</div>
<div class="time5 cf" id="time6" style="display: none;">
<ul class="time_list">
	<li class="time_sel time_zb"><span class="time_s ya_hei">60S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">30S</span></li>
	<li class=" time_zb"><span class="time_s ya_hei">0S</span></li>

</ul>
</div>

<div class="time5 cf" id="time7" style="display: none;">
<ul class="time_list">
	<li class="time_sel time_zb"><span class="time_s ya_hei">60S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">30S</span></li>
	<li class="time_sel time_zb"><span class="time_s ya_hei">0S</span></li>

</ul>
</div>
</div>

<div class=" test_des_con cf">
<div id="text_one" class="cf">
<div class="test_middle">
<dl>
	<dt>为谁投保：</dt>
	<dd><span><label class="radioCursor"><input
		type="radio" value="1" name="insurance.who">自己</label></span> <span><label
		class="radioCursor"><input type="radio" value="2" name="insurance.who">丈夫</label></span>
	<span><label class="radioCursor"><input type="radio"
		value="3" name="insurance.who">妻子</label></span> <span><label
		class="radioCursor"><input type="radio" value="4" name="insurance.who">父亲</label></span>
	<span><label class="radioCursor"><input type="radio"
		value="5" name="insurance.who">母亲</label></span> <span><label
		class="radioCursor"><input type="radio" value="6" name="insurance.who">儿女</label></span>
	<input type="hidden" value="0" id="childrenflag"></dd>
	</dd>
</dl>

<dl id="genderDl">
	<dt>您的性别：</dt>
	<dd><span><label class="radioCursor"><input
		type="radio" value="1" name="insurance.sexs">男</label></span> <span><label
		class="radioCursor"><input type="radio" value="2"
		name="insurance.sexs">女</label></span>
		<input type="hidden" value="" name="insurance.sex" id="insurancesex">
		</dd>
</dl>
<dl style="height: 28px;">
	<dt><font size="2">出生</font>日期：</dt>
	<dd><input type="hidden" id="birthday" name="insurance.birthday"
		value="2013-2-3"> <input type="hidden" id="age"
		name="analysis.age" value="0"> <select style="width: 80px;"
		id="year"></select>&nbsp;年&nbsp; <select style="width: 80px;"
		id="month"></select>&nbsp;月&nbsp; <select style="width: 80px;"
		id="day">
		<option value="">请选择</option>
	</select>&nbsp;日&nbsp; <label style="color: gray; display: none;" id="ageSpan"></label>
	</span></dd>
</dl>
<dl id="education" style="display: none; height: 30px;">
	<dt>教育阶段：</dt>
	<dd><span><label class="radioCursor"><input
		type="radio" value="1" name="insurance.education">婴儿</label></span> <span><label
		class="radioCursor"><input type="radio" value="2" name="insurance.education">幼儿园</label></span>
	<span><label class="radioCursor"><input type="radio"
		value="3" name="insurance.education">小学/初中</label></span> <span><label
		class="radioCursor"><input type="radio" value="4" name="insurance.education">高中</label></span>
	<span><label class="radioCursor"><input type="radio"
		value="5" name="insurance.education">大学</label></span> <span><label
		class="radioCursor"><input type="radio" value="6" name="insurance.education">工作</label></span>
	</dd>
</dl>
<dl>
	<dt>健康状况：</dt>
	<dd><span><label class="radioCursor"><input
		type="radio" value="1" name="insurance.healthstatus">很好</label></span> <span><label
		class="radioCursor"><input type="radio" value="2"
		name="insurance.healthstatus">正常</label></span> <span><label
		class="radioCursor"><input type="radio" value="3"
		name="insurance.healthstatus">一般，经常感冒</label></span> <span><label
		class="radioCursor"><input type="radio" value="4"
		name="insurance.healthstatus">不好，经常生病</label></span></dd>
</dl>



</div>
<div class="text_next cf"><a href="###" target=""
	class="text_nextpage ya_hei" id="next_1" target="_blank">下一步</a> <span
	class="text_error" id="tipInfoDiv1" style="display: none;">请检查您的选项</span></div>
</div>
<div id="text_two" class="cf">
<div class="test_middle">
<dl>
	<dt>工作类型：</dt>
	<dd><select id="work_style">
		<option value="1">公务员单位</option>
		<option value="2">事业编制单位</option>
		<option value="3">国有企业</option>
		<option value="4">中外合资企业</option>
		<option value="5">普通企业</option>
		<option value="6">个体工商户</option>
		<option value="7">无业或退休</option>
		<option value="8">高危职业</option>
	</select>
	<input type="hidden" value="" name="insurance.work" id="insurancework">
	</dd>
	
</dl>

<dl>
	<dt>旅行次数/年：</br>
	(出差、旅行)</dt>
	<dd>
		<span>
			<label class="radioCursor">
			<input type="radio" value="1" name="insurance.travelnum">无</label>
		</span> 
		<span>
			<label class="radioCursor">
			<input type="radio" value="2" name="insurance.travelnum">5次以下</label>
		</span>
		<span>
			<label class="radioCursor">
			<input type="radio" value="3" name="insurance.travelnum">5-10次</label>
		</span>
		<span>
			<label class="radioCursor">
			<input type="radio" value="4" name="insurance.travelnum">10次以上</label>
		</span>
	</dd>
</dl>
<dl style="height: 28px;">
	<dt>近期出行地：</dt>
	<dd><span><label class="radioCursor"><input
		type="radio" value="1" name="insurance.destination">国内</label></span> <span><label
		class="radioCursor"><input type="radio" value="2"
		name="insurance.destination">国外</label></span> <span><label class="radioCursor"><input
		type="radio" value="3" name="insurance.destination">申根国家</label></span><span><label
		class="radioCursor"><input type="radio" value="4"
		name="insurance.destination">港澳台地区</label></span></dd>
</dl>
<dl>
	<dt>参加高风险运动：</dt>
	<dd>
		<span><label class="radioCursor"><input type="radio" value="1" name="insurance.sports">是</label></span> 
		<span><label class="radioCursor"><input type="radio" value="2" name="insurance.sports">否</label></span>
	</dd>
</dl>
<dl>
	<dt>家中是否有车：</dt>
	<dd>
		<span><label class="radioCursor"><input type="radio" value="2" name="insurance.car">有</label></span>
		<span><label class="radioCursor"><input type="radio" value="1" name="insurance.car">无</label></span>
	</dd>
</dl>

</div>
<div class="text_next cf"><a href="###" target=""
	class="text_nextpage ya_hei" target="_blank" id="next_2">下一步</a> <a
	href="###" target="" class="text_uppage ya_hei" target="_blank"
	id="next_2_back">上一步</a> <span class="text_error" id="tipInfoDiv2"
	style="display: none;">请检查您的选项</span></div>
</div>

<div id="text_three" class="cf">

<div class="test_middle">
<dl>
	<dt>医疗保险：</dt>
	<dd><span class="dl_br"><label class="radioCursor"><input
		type="radio" value="1" name="insurance.yiliaobaoxian" checked="checked">没有</label></span><br>
	<span class="dl_br"><label class="radioCursor"><input
		type="radio" value="2" name="insurance.yiliaobaoxian">有，社保医疗</label></span><br>
	<span class="dl_br"><label class="radioCursor"><input
		type="radio" value="3" name="insurance.yiliaobaoxian">有，商业医疗保险</label></span><br>
	<span class="dl_br"><label class="radioCursor"><input
		type="radio" value="4" name="insurance.yiliaobaoxian">社保&商业医疗保险</label></span><br>
	</dd>
</dl>

<dl>
	<dt>养老保险：</dt>
	<dd><span class="dl_br"><label class="radioCursor"><input
		type="radio" value="1" name="insurance.yanglaobaoxian" checked="checked">没有</label></span><br>
	<span class="dl_br"><label class="radioCursor"><input
		type="radio" value="2" name="insurance.yanglaobaoxian">有，社保养老</label></span><br>
	<span class="dl_br"><label class="radioCursor"><input
		type="radio" value="3" name="insurance.yanglaobaoxian">有，商业养老保险</label></span><br>
	<span class="dl_br"><label class="radioCursor"><input
		type="radio" value="4" name="insurance.yanglaobaoxian">社保&商业养老保险</label></span><br>
	</dd>
</dl>
<dl>
	<dt>有无贷款：</dt>
	<dd><span><label class="radioCursor"><input
		type="radio" value="1" name="insurance.loan">没有</label></span> <span><label
		class="radioCursor"><input type="radio" value="2"
		name="insurance.loan">有（如：房贷、车贷或其他贷款等）</label></span></dd>
</dl>

<dl>
	<dt>家庭年收入:</dt>
	<dd><select id="revenue">
		<option value="">请选择</option>
		<option value="1">5万以下</option>
		<option value="2">5万-10万</option>
		<option value="3">10万-20万</option>
		<option value="4">20万-50万</option>
		<option value="5">50万以上</option>
	</select>
	<input type="hidden" value="" name="insurance.salary" id="insurancesalary">
	</dd>
</dl>

</div>
<div class="text_next cf"><a href="###" target=""
	class="text_nextpage ya_hei" target="_blank" id="next_3">下一步</a> <a
	href="###" target="" class="text_uppage ya_hei" target="_blank"
	id="next_3_back">上一步</a> <span class="text_error" id="tipInfoDiv3"
	style="display: none;">请检查您的选项</span></div>
</div>


<div class="test_result cf" id="text_five">
<div class="test_result_h3">
<h3 class="ya_hei">恭喜您完成了保险需求测试</h3>
<p>现在将试卷提交给老师,便可得到各个科目,详细的教师评语！</p>
</div>
<div class="test_result_box cf">
<div class="test_res_l"><b class="test_res_l_t" id="baoxian_fenxi">保险分析:</b> 

<span class="need_name need_grade"><em class="test_need_car">车&nbsp;&nbsp;&nbsp;&nbsp;险</em>
<em class="test_car_ts">建议投保开心保车险，省钱有好礼</em></span></div>
<div class="test_res_r">
<table class="test_sub_table">

	<tr>
		<th><em class="red">*</em>电子邮箱：</th>
		<td><input type="text" class="test_sub_input" name="insurance.mail" id="email"><br>
		</td>
	</tr>
	<tr>
		<th></th>
		<td><span>请填写您的常用邮箱，接收测试结果和方案</span></td>
	</tr>
	<tr>
		<th>手机号码：</th>
		<td><input type="text" class="test_sub_input" id="phone" name="insurance.phone"><br>
		</td>
	</tr>
	<tr>
		<th></th>
		<td><span>选填，及时通知您合适的投保建议</span></td>
	</tr>
	<tr>
		<th class="test_sub_p">备 注：</th>
		<td><textarea class="test_textarea" id="memo"></textarea>
		<input type="hidden" name="star" id="star"/><br>
		</td>
	</tr>
</table>
<div class="test_tijiao cf"><input type="button"
	id="submit_button" class="test_submit ya_hei" value="确认交卷"> <span
	id="tipInfoDiv4" class="text_error tj_error" style="display:none;" >请您填写电子邮箱</span><span
	id="tipInfoDiv5" class="text_error tj_error" style="display:none;" >请您填写正确的电子邮箱</span></div>
</div>
</div>
</div>
</div>
</div>
</div>
</form>
<!--保险测试结束-->

<!--公共底部开始-->
<#include "/wwwroot/kxb/block/kxb_footer_new_common.shtml">
<!--公共底部结束-->
</body>
</html>