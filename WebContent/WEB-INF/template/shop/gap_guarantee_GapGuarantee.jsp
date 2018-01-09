<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="com.sinosoft.framework.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String serverContext = Config.getServerContext();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心-我的存档</title>
<!-- 会员中心公共CSS -->
<s:include value="/wwwroot/kxb/block/kxb_member_center_css.shtml"></s:include>
<link
	href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_header.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=Config.getValue("StaticResourcePath")%>/style/redesign/re_member.css"
	rel="stylesheet" type="text/css" />
<link
	href="<%=Config.getValue("StaticResourcePath")%>/style/new_logo.css"
	rel="stylesheet" type="text/css" />
<!--default.css artdialog样式-->
<link rel="stylesheet" type="text/css"
	href="<%=Config.getValue("StaticResourcePath")%>/style/skins/default.css" />
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/template/shop/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="<%=Config.getValue("JsResourcePath")%>/js/jquery-1.4.2.min.js"></script>
<s:include value="/wwwroot/kxb/block/kxb_custom_header.shtml"></s:include>
</head>
<body class="up-bg-qh">
	<!-- 顶部 开始 1-->
	<s:include value="/wwwroot/kxb/block/kxb_header_index_new_v2.shtml"></s:include>
	<!-- 顶部 结束 -->
	<div class="wrapper">
		<div class="daohang">
			<span class="daohang_home"></span> <a href='http://www.kaixinbao.com'
				target='_self'><span class="orange">您现在的位置：首页</span></a><span
				class="separator">> </span><a href='member_center!index.action'
				target='_self'>会员中心</a> <span class="separator">></span><span
				class="separator1">会员中心首页</span>
		</div>
		<div class="member_con">
			<!-- 会员中心左侧菜单导航 -->
			<jsp:include page="member_center_left.jsp"></jsp:include>
			<div class="member_right">
				<!-- 保障缺口评测存档 -->
				<div id="bzqk_area">
					<div class="cont_box">
						<p class="title">
							<span>我的信息</span>
						</p>
						<ul class="info_list">
							<li>
								<p class="item">家庭类型：</p>
								<p class="text"><s:property value="data.hometype"/></p>
							</li>
							<li>
								<p class="item">性别：</p>
								<p class="text"><s:property value="data.sex"/></p>
							</li>
							<li>
								<p class="item">您的年龄：</p>
								<p class="text"><s:property value="data.age"/></p>
							</li>
							<li>
								<p class="item">有无社保：</p>
								<p class="text"><s:property value="data.socialsecurity"/></p>
							</li>
							<li>
								<p class="item">年收入：</p>
								<p class="text"><s:property value="data.annualearnings"/></p>
							</li>
							<li>
								<p class="item">居住省份：</p>
								<p class="text"><s:property value="data.liveprovince1"/></p>
							</li>
							<li>
								<p class="item">工作年限：</p>
								<p class="text"><s:property value="data.workstartdate"/></p>
							</li>
							<li>
								<p class="item">赡养父母：</p>
								<p class="text"><s:property value="data.supportparents"/></p>
							</li>
							<li>
								<p class="item">赡养费：</p>
								<p class="text"><s:property value="data.alimony"/></p>
							</li>
						</ul>
					</div>
					<div class="cont_box">
						<p class="title">
							<span>配偶信息</span>
						</p>
						<ul class="info_list">
							<li>
								<p class="item">身份：</p>
								<p class="text"><s:property value="data.spousesex"/></p>
							</li>
							<li>
								<p class="item">您的年龄：</p>
								<p class="text"><s:property value="data.spouseage"/></p>
							</li>
							<li>
								<p class="item">工作年限：</p>
								<p class="text"><s:property value="data.spouseworkstartdate"/></p>
							</li>
							<li>
								<p class="item">有无社保：</p>
								<p class="text"><s:property value="data.spousesicialsecurity"/></p>
							</li>
							<li>
								<p class="item">年收入：</p>
								<p class="text"><s:property value="data.spouseannualearnings"/></p>
							</li>
						</ul>
					</div>
					<div class="cont_box">
						<p class="title">
							<span>家庭净负债</span>
						</p>
						<ul class="info_list">
							<li>
								<p class="item">负债-储蓄：</p>
								<p class="text"><s:property value="data.householdnetliabilities"/></p>
								<input type='hidden' id='001' value='<s:property value="data.GapGuasrantee1"/>'>
								<input type='hidden' id='002' value='<s:property value="data.GapGuasrantee2"/>'>
								<input type='hidden' id='003' value='<s:property value="data.GapGuasrantee3"/>'>
								<input type='hidden' id='004' value='<s:property value="data.GapGuasrantee4"/>'>
							</li>
						</ul>
					</div>
					<div class="cont_box">
						<p class="title">
							<span>保障缺口测算结果</span>
						</p>
						<p class="intro">
							<s:property value="data.copywriting"/><br />您居住在<s:property value="data.liveprovince1"/>，根据国家统计局的数据，该省份每年居民人均可支配收入为
							<s:property value="data.getin"/>元，每年居民人均消费支出为<s:property value="data.putout"/>元；<br />您的家庭年收入为<s:property value="data.homegetin"/>元，根据比例计算您的家庭年支出约为<s:property value="data.homeputout"/>元；
						</p>
						<div class="chart_area">
							<p class="ttl">保障缺口：</p>
							<div id="main" style="width: 600px; height: 320px;"></div>
							<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/echarts.js"></script>
							<script type="text/javascript">
								//图表创建
								(function() {
									var a = $("#001").val();
									var b = $("#002").val();
									var c = $("#003").val();
									var d = $("#004").val();
									var arrData = [ a, b, c, d ]; //养老金，教育金，健康险，人寿险数值
									var myChart = echarts.init(document
											.getElementById('main'));
									setTimeout(
											function() {
												myChart
														.setOption({
															xAxis : {
																type : 'value',
																name :'万元',
						                                        nameLocation: 'end',
						                                        nameTextStyle: {
						                                            color: '#fd8824',
						                                            fontSize: 12
						                                        },
																axisTick : {
																	show : false
																},
																axisLine : {
																	lineStyle : {
																		width : 2,
																		color : '#fd8722'
																	}
																},
																axisLabel : {
																	textStyle : {
																		fontSize : 12
																	}
																},
																splitLine : {
																	show : false
																},
																max : 300
															},
															yAxis : {
																type : 'category',
																axisTick : {
																	show : false
																},
																axisLine : {
																	lineStyle : {
																		width : 2,
																		color : '#fd8722'
																	}
																},
																axisLabel : {
																	textStyle : {
																		fontSize : 12
																	}
																},
																data : [ '人寿险',
																		'健康险',
																		'教育金',
																		'养老金' ]
															},
															series : [ {
																type : 'bar',
																barWidth : 25,
																itemStyle : {
																	normal : {
																		color : '#fd821c',
																		borderWidth : 1,
																		barBorderRadius : [
																				0,
																				30,
																				30,
																				0 ],
																		label : {
																			show : true,
																			position : 'right',
																			formatter : '{c}',
																			textStyle : {
																				fontSize : 12,
																				color : '#fd821c'
																			}
																		}
																	}
																},
																data : arrData
															} ],
															textStyle : {
																color : '#333'
															}
														});
											}, 1200);
								})();
							</script>
							<p class="ttl">您极度需要的保障：</p>
							<div class="need_cont">
								<p class="ttl">
									<span class="icon_01"></span><s:property value="data.name1"/>
								</p>
								<p class="num">
									需求指数：<span class="num_05"></span>
								</p>
								<div class="rlt">
									<p>推荐理由：</p>
									<ul>
										<li><s:property value="data.copywriting1"/></li>
										<li><s:property value="data.copywriting2"/></li>
										<li><s:property value="data.copywriting3"/></li>
									</ul>
								</div>
							</div>
							<div class="need_cont">
								<p class="ttl">
									<span class="icon_02"></span><s:property value="data.name2"/>
								</p>
								<p class="num">
									需求指数：<span class="num_04"></span>
								</p>
								<div class="rlt">
									<p>推荐理由：</p>
									<ul>
										<li><s:property value="data.copywriting4"/></li>
										<li><s:property value="data.copywriting5"/></li>
										<li><s:property value="data.copywriting6"/></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- /保障缺口评测存档 -->
			</div>
		</div>
	</div>
	<!-- 底部开始 -->
	<s:include value="/wwwroot/kxb/block/kxb_footer_new_common.shtml"></s:include>
	<!-- 底部结束 -->
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=Config.getValue("JsResourcePath")%>/js/new_member.js"></script>
	<s:include value="/wwwroot/kxb/block/community_v1.shtml"></s:include>
</body>
</html>