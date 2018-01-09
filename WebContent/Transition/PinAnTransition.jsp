<!DOCTYPE html >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.Config"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
	String FrontServerContextPath = Config.getFrontServerContextPath();
	String JsStaticResource = Config.getValue("JsResourcePath");
	String StaticResourcePath = Config.getValue("StaticResourcePath");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>车险怎么买_汽车保险怎么买最划算_商业车险怎么买便宜_平安车险-开心保保险网</title>
<link type="text/css" rel="stylesheet" href="<%=StaticResourcePath%>/style/new/global.css">
	<link type="text/css" rel="stylesheet" href="<%=StaticResourcePath%>/style/car/base.css">
	<link type="text/css" rel="stylesheet" href="<%=StaticResourcePath%>/style/car/information.css">
<!--default.css artdialog样式-->
<link href="<%=StaticResourcePath%>/style/skins/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="<%=StaticResourcePath%>/style/car/validator.css"
	type="text/css" />
	
</head>
<body class="zz_acr_body">
	<div class="zz_headr_bg">
		<div class="zz_header">
				<div class="new_logo zz_logo">  <a href="http://www.kaixinbao.com/"><img width="447" height="72" title="开心保保险网，一站式服务，省钱更安心" alt="开心保保险网，一站式服务，省钱更安心" src="<%=StaticResourcePath%>/images/logo_03.gif"></a></div>

				<div class="nav_tel_zz"><img width="204" height="68" src="<%=StaticResourcePath%>/images/new_car/pingan_02.gif"></div>
		</div>
	</div>
		<div class="clear"></div>
		<form id="carForm" name="carForm" action="" method="post">
		<div class="zz_acr_body clearfix">
		<div class="zz_car_bgs">
			<div class="zz_carbox">
						<div class="llcly2">

    <p>
        <em>行驶城市：</em>
        <select name="car.Prop2" id="sltProvince" class="counter_sel3">
										<option selected="selected" value="">省份</option>
										<option value="直辖市">直辖市</option>
										<option value="河北">河北</option>
										<option value="山西">山西</option>
										<option value="内蒙古">内蒙古</option>
										<option value="辽宁">辽宁</option>
										<option value="吉林">吉林</option>
										<option value="黑龙江">黑龙江</option>
										<option value="江苏">江苏</option>
										<option value="浙江">浙江</option>
										<option value="安徽">安徽</option>
										<option value="福建">福建</option>
										<option value="江西">江西</option>
										<option value="山东">山东</option>
										<option value="河南">河南</option>
										<option value="湖北">湖北</option>
										<option value="湖南">湖南</option>
										<option value="广东">广东</option>
										<option value="广西">广西</option>
										<option value="海南">海南</option>
										<option value="四川">四川</option>
										<option value="贵州">贵州</option>
										<option value="云南">云南</option>
										<option value="西藏">西藏</option>
										<option value="陕西">陕西</option>
										<option value="甘肃">甘肃</option>
										<option value="青海">青海</option>
										<option value="宁夏">宁夏</option>
										<option value="新疆">新疆</option>
										<option value="台湾">台湾</option>
								</select>
								<select name="car.Address" id="sltCity" class="counter_sel3" value="<%= request.getParameter("driveCity")==null?"":java.net.URLDecoder.decode(request.getParameter("driveCity"), "UTF-8") %>">
										<option selected="selected" value="">城市</option>
								   </select>
    </p>
    <p>
        <em>车牌号：</em>
        <input type="text" name="car.PlateNo" id="carnum" class="input-c input-c-up input-c-sele" value="<%= request.getParameter("carNO")==null?"":java.net.URLDecoder.decode(request.getParameter("carNO"), "UTF-8") %>" maxlength="8"> 
        <label for="car" class="lab-car">
        <input type="checkbox" value="<%= request.getParameter("CarProperty")==null?"":request.getParameter("CarProperty") %>" name="CarProperty" id="CarProperty" class="input-car" onclick="hidCarNo();">新车未上牌</label>
        <span id="errMsg" class="error"></span>
    </p>

    <p>
        <em>投保日期：</em>
        <select name="car.InsureYear" id="InsureYear" class="input_opti">
        <option value="">--年份--</option>
        <option value=""></option>
        <option value=""></option>
        </select>
        <select name="car.InsuranceDate" id="InsuranceDate" class="input_opti">
        <option value="">--月份--</option><option value="01">1月</option><option value="02">2月</option><option value="03">3月</option><option value="04">4月</option><option value="05">5月</option><option value="06">6月</option><option value="07">7月</option><option value="08">8月</option><option value="09">9月</option><option value="10">10月</option><option value="11">11月</option><option value="12">12月</option></select>
    	<span id="errMsg2" class="error"></span>
    </p>

<p>
				<em>提车时间：</em><input type="text" id="BuyDate" name="car.BuyDate" onclick="WdatePicker({maxDate:'%y-%M',dateFmt:'yyyy-MM',skin:'whyGreen'})" class="input-c input-c-sele input-t">（注册登记年月）
			</p>

			<p>
				<em>车价：</em><input type="text" name="car.CarValue" id="CarValue" class="input-c input-c-sele" onblur="CheckCarValue();">（单位：万元）<span id="errMsg1" class="error"></span>
			</p>
  <p>
        <em>车主姓名：</em><input type="text" name="car.CarOwner" id="CarOwner" class="input-c input-c-sele" value="<%= request.getParameter("carUser")==null?"":java.net.URLDecoder.decode(request.getParameter("carUser"), "UTF-8") %>">
    </p>

    <p>
        <em>联系电话：</em><input type="text" name="car.ContactPhone" id="ContactPhone" class="input-c input-c-sele" value="<%= request.getParameter("carPhone")==null?"":request.getParameter("carPhone").trim() %>"  maxlength="11">
    </p>

    <p>
        <em>联系邮箱：</em><input type="text" name="car.ContactEmail" id="ContactEmail" class="input-c input-c-sele ">
    </p>
    <input type="hidden" value="pingan" id="cpsname">
    <a name="" onclick="doSave('<%=FrontServerContextPath %>/cx/gd/')" class="car_gobj" value="获取报价"></a>	
</div>
		</div>
		</div>
		<div class="zz_carbg1"></div>
		<div class="zz_carbg2"></div>
		<div class="zz_carbg3"></div>
		<div class="zz_carbg4"></div>
		<div class="zz_carbg5"></div>
		
		
		</div>
	</form>
<div class="clear"></div>
<%@ include file="/wwwroot/kxb/block/car_footer.shtml"%>
<script type="text/javascript">
var y = new Date();
var pYear = y.getFullYear();
var sel = document.getElementById('InsureYear');
sel.options[1].value = pYear-1;
sel.options[1].text = pYear-1;
sel.options[2].value = pYear;
sel.options[2].text = pYear;

</script>
<script type="text/javascript"
		src="<%=JsStaticResource%>/js/jquery.validate.min.js"></script>
<script type="text/javascript"
		src="<%=JsStaticResource%>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
		src="<%=JsStaticResource%>/js/jquery.json-2.2.min.js"></script>
<script type="text/javascript"
		src="<%=JsStaticResource%>/js/template/common/js/base.js"></script>
<script type="text/javascript"
		src="<%=JsStaticResource%>/js/car/car.js"></script>
<script type="text/javascript" src="<%=JsStaticResource%>/js/city_car.js"></script>
<script type="text/javascript" src="<%=JsStaticResource%>/js/car/footer.js"></script>
<script type="text/javascript" src="<%=JsStaticResource%>/js/jquery.soChange-min.js"></script>
<script type="text/javascript" src="<%=JsStaticResource%>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=JsStaticResource%>/js/jquery.form.js"></script>
<script type="text/javascript"
		src="<%=JsStaticResource%>/js/car/PinAnTransition.js"></script>
	
	<style>
		.WdateDiv .yminput,.WdateDiv .WdayTable td{ font-family: "微软雅黑";}
.WdateDiv .MTitle { background:#F3CE93;}
.WdateDiv .menuOn { background: #F3CE93;}
	</style>


</body>
</html>
