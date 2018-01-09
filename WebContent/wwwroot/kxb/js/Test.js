/*
 *@des:保险测试页面
 *@maker:dongqi.guo
 *@time:2013-11-23-20:20
 */
jQuery(function() {
	jQuery('.need_box dt').click(function() {
		if(!(jQuery(this).attr('class').indexOf('need_titile_none')!=-1)){
			if (jQuery(this).siblings(".need_con").is(":visible")) {
				jQuery(this).siblings(".need_con").hide();
				jQuery(this).addClass("need_active");
			} else {
				jQuery(this).siblings(".need_con").show();
				jQuery(this).removeClass("need_active");
			}
		}
	});

	var f = this;
	var test_target = 0;
	var who = jQuery("input[name='insurance.who']");
	jQuery(who).click(
			function() {
				f.test_target = parseInt(f.isIe6 ? jQuery(this).find("input")
						.val() : jQuery(this).val());
				targetObjOperate(f.test_target);
			});
	// 初始化只显示第一个浮层，其他三个浮层隐藏
	jQuery('#text_two').hide();
	jQuery('#text_three').hide();
	jQuery('#text_five').hide();

	// 根据投保人显示隐藏表单信息
	function targetObjOperate(num) {
		switch (num) {
		case 1:
			jQuery("#genderDl").slideDown(200);
			jQuery("#education").slideUp(200);
			jQuery("#education").find("input").attr("checked", false);
			jQuery("#genderDl").find("input").attr("checked", false);
			showOnlyOne(1);
			break;
		case 6:
			jQuery("#genderDl").slideUp(200);
			jQuery("#education").slideDown(200);
			showOnlyOne(5);
			break;
		default:
			var who = jQuery('input:radio[name="insurance.who"]:checked').val();
			if(who=='2'||who=='4'){
				//jQuery('input:radio[name="insurance.sexs"]').eq(0).attr("checked",'checked');
				jQuery("#insurancesex").val("1");
			}else if(who=='3'||who=='5'){
				//jQuery('input:radio[name="insurance.sexs"]').eq(1).attr("checked",'checked');
				jQuery("#insurancesex").val("2");
			}
			jQuery("#genderDl").slideUp(200);
			jQuery("#education").slideUp(200);
			jQuery("#education").find("input").attr("checked", false);
			jQuery("#genderDl").find("input").attr("checked", false);
			showOnlyOne(1);
			break;
		}
	}

	function eachYear(c, a) {

		var d = jQuery("#year");
		jQuery(d).find("option").remove();
		jQuery(d).append("<option value=''>请选择</option>");
		jQuery("#month").val("");
		jQuery("#day").val("");
		var e = new Date();
		var f = e.getFullYear();
		for ( var b = f - c; b >= f - a; b--) {
			jQuery(d).append('<option value="' + b + '">' + b + "</option>");
		}
		calcAge();

	}

	/* 选择天数判断 */
	function calcAge() {
		var i = jQuery("#year").val();
		var a = jQuery("#month").val();
		var h = jQuery("#day").val();
		jQuery("#age").val("");
		jQuery("#birthday").val("");
		jQuery("#ageSpan").html("");
		if (i != "" && a != "" && h != "") {
			jQuery("#tipInfoDiv").hide();
			var e = new Date();
			var f = e.getFullYear();
			var g = e.getMonth() + 1;
			var c = e.getDate();
			var b = f - i;
			if (a > g) {
				b -= 1;
			} else {
				if (g == a) {
					if (h > c) {
						b -= 1;
					}
				}
			}
			jQuery("#age").val(b);
			jQuery("#birthday").val(i + "-" + a + "-" + h);
			jQuery("#ageSpan").html(b + "岁");
			if (test_target == 6) {
				jQuery("#childrenAge").val(b);
				jQuery("#childrenYear").html(18 - b);
			}
		}

	}
	/**
	 * 月份载入
	 */
	function eachDayByMonth(d) {
		var b = jQuery("#year").val();
		jQuery("#day option").remove();
		jQuery("#day").append("<option value=''>请选择</option>");
		if (b != "" && d != "") {
			var c = new Date(b, d, 0);
			for ( var a = 1; a <= c.getDate(); a++) {
				jQuery("#day").append(
						"<option value='" + a + "'>" + a + "</option>");
			}
		}
		calcAge();
	}
	/**
	 * 判断是否为空
	 */
	function isEmpty(val) {
		if (("" == val) || (null == val) || ("undefined" == typeof (val))) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 转变字符类型
	 */
	function changeToEmpty(val) {
		if (("" == val) || (null == val) || ("undefined" == typeof (val))) {
			return "";
		} else {
			return val;
		}
	}
	/**
	 * 只显示一个时间轴(显示的id)
	 */
	function showOnlyOne(id) {
		var i = 1;
		for (i = 1; i < 8; i++) {
			if (i == id) {
				jQuery("#time" + i + "").show();
			} else {
				jQuery("#time" + i + "").hide();
			}
		}
	}
	/**
	 * 关闭错误提示
	 */
	function closeError() {
		jQuery('#tipInfoDiv1').hide();
		jQuery('#tipInfoDiv2').hide();
		jQuery('#tipInfoDiv3').hide();
		jQuery('#tipInfoDiv4').hide();
	}
	/**
	 * 邮箱校验
	 */
	function checkMail(mail) {
		// 对电子邮件的验证
		var mailreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (!mailreg.test(mail)) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 提交用户信息
	 */
	jQuery("#submit_button")
			.click(
					function() {
						var email = changeToEmpty(jQuery("#email").val());
						var work_style = changeToEmpty(jQuery("#work_style")
								.val());
						jQuery("#insurancework").val(work_style);
						var revenue = changeToEmpty(jQuery("#revenue").val());
						jQuery("#insurancesalary").val(revenue);
						if (isEmpty(email)) {
							if (!checkMail(email)) {
								jQuery('#tipInfoDiv4').hide();
								jQuery('#tipInfoDiv5').show();
								return false;
							} else {
								//计算星数
								//makeStar();
								jQuery('#tipInfoDiv4').hide();
								jQuery('#tipInfoDiv5').hide();
								jQuery("#insurancefrom").attr("action",sinosoft.base + '/shop/insurance_test!saveInsuranceTestResult.action');
								jQuery("#insurancefrom").submit();// 表单提交
							}
						} else {
							jQuery('#tipInfoDiv4').show();
							return false;
						}
					});
	/**
	 * 确定推荐险种星数目
	 */
	function makeStar() {
		//教育险
		var jiaoyu_num=0;
		var education = jQuery('input:radio[name="insurance.education"]:checked').val();
		if(education=='2'){
			jiaoyu_num=jiaoyu_num+5;
		}else if(education=='3'){
			jiaoyu_num=jiaoyu_num+4;
		}else if(education=='4'){
			jiaoyu_num=jiaoyu_num+3;
		}else if(education=='5'){
			jiaoyu_num=jiaoyu_num+2;
		}
		//健康险
		var jiankang_num=0;
		var healthstatus = jQuery('input:radio[name="insurance.healthstatus"]:checked').val();
		if(healthstatus=='1'){
			jiankang_num=jiankang_num+1;
		}else if(healthstatus=='2'){
			jiankang_num=jiankang_num+2;
		}else if(healthstatus=='3'){
			jiankang_num=jiankang_num+3;
		}else if(healthstatus=='4'){
			jiankang_num=jiankang_num+4;
		}
		//意外险
		var yiwai_num=0;
		var work_style = jQuery("#work_style").val();
		if(work_style=='5'||work_style=='6'){
			yiwai_num=yiwai_num+2;
		}else if(work_style=='8'){
			yiwai_num=yiwai_num+3;
		}else{
			yiwai_num=yiwai_num+1;
		}
		//旅游险
		var travle_num=0;
		var travelnum = jQuery('input:radio[name="insurance.travelnum"]:checked').val();
		if(travelnum=='1'){
			travle_num=0;
		}else if(travelnum=='2'){
			yiwai_num=yiwai_num+2;
			travle_num=3;
		}else if(travelnum=='3'){
			yiwai_num=yiwai_num+3;
			travle_num=4;
		}else if(travelnum=='4'){
			yiwai_num=yiwai_num+3;
			travle_num=5;
		}
		//高风险运动
		var sports = jQuery('input:radio[name="insurance.sports"]:checked').val();
		if(sports=='1'){
			yiwai_num=yiwai_num+2;
		}
		//健康险
		var yiliaobaoxian = jQuery('input:radio[name="insurance.yiliaobaoxian"]:checked').val();
		if(yiliaobaoxian=='1'){
			jiankang_num=jiankang_num+2;
		}else if(yiliaobaoxian=='2'||yiliaobaoxian=='3'){
			jiankang_num=jiankang_num+1;
		}
		//人寿险
		var renshou_num=0;
		var yanglaobaoxian = jQuery('input:radio[name="insurance.yanglaobaoxian"]:checked').val();
		if(yanglaobaoxian=='1'){
			renshou_num=renshou_num+2;
		}else if(yanglaobaoxian=='2'||yanglaobaoxian=='3'){
			renshou_num=renshou_num+1;
		}
		//有无贷款
		var loan = jQuery('input:radio[name="insurance.loan"]:checked').val();
		if(loan=='2'){
			renshou_num=renshou_num+1;
		}
		//旅游险、意外险、人寿险、健康险、教育险
		var s=change_to_5(travle_num)+","+change_to_5(yiwai_num)+","+change_to_5(renshou_num)+","+change_to_5(jiankang_num)+","+change_to_5(jiaoyu_num);
		jQuery("#star").val(s);
	}
	/**
	 * 大于5颗星为5
	 */
	function change_to_5(num){
		if(parseInt(num)>5){
			return 5;
		}else{
			return num;
		}
	}

	/* 出生日期事件绑定 */
	var d = new Date();
	jQuery("#year").append("<option selected='selected' value=''>请选择</option>");
	jQuery("#year").append(
			"<option value='" + d.getFullYear() + "'>" + d.getFullYear()
					+ "</option>");
	for ( var b = 1; b <= 70; b++) {
		jQuery("#year").append(
				"<option value='" + (d.getFullYear() - b) + "'>"
						+ (d.getFullYear() - b) + "</option>");
	}
	jQuery("#month").append("<option value=''>请选择</option>");
	for ( var b = 1; b <= 12; b++) {
		jQuery("#month").append("<option value='" + b + "'>" + b + "</option>");
	}
	jQuery("#day").append("<option value=''>请选择</option>");
	jQuery("#year").bind("change", function() {
		jQuery("#month option:eq(0)").attr("selected", true);
		jQuery("#day option").remove();
		jQuery("#day").append("<option value=''>请选择</option>");
		calcAge();
	});

	jQuery("#month").bind("change", function() {
		eachDayByMonth(jQuery(this).val());
	});
	jQuery("#day").bind("change", function() {
		calcAge();
	});
	/**
	 * 第一页面下一步
	 */
	jQuery("#next_1")
			.click(
					function() {
						var flag = true;
						var who = jQuery(
								'input:radio[name="insurance.who"]:checked')
								.val();
						//var gender = jQuery('input:radio[name="insurance.sexs"]:checked').val();
						if(who=="1"){
							var gender = jQuery('input:radio[name="insurance.sexs"]:checked').val();
							jQuery("#insurancesex").val(gender);
						}
						var gender=jQuery("#insurancesex").val();
						var year = jQuery("#year").val();
						var month = jQuery("#month").val();
						var day = jQuery("#day").val();
						var healthStatus = jQuery(
								'input:radio[name="insurance.healthstatus"]:checked')
								.val();
						var stu = jQuery(
								'input:radio[name="insurance.education"]:checked')
								.val();
						if (who == '1') {// 选择"自己"
							if (isEmpty(gender) && isEmpty(year)
									&& isEmpty(month) && isEmpty(day)
									&& isEmpty(healthStatus)) {
								flag = true;
							} else {
								flag = false;
							}
						} else if (who == '6') {// 选择"子女"
							if (isEmpty(stu) && isEmpty(year) && isEmpty(month)
									&& isEmpty(day) && isEmpty(healthStatus)) {
								flag = true;
							} else {
								flag = false;
							}
						} else {// 其他的方式
							if (isEmpty(year) && isEmpty(month) && isEmpty(day)
									&& isEmpty(healthStatus)) {
								flag = true;
							} else {
								flag = false;
							}
						}
						if (flag) {// 下一步
							if (who == '6') {
								var ageSpan = jQuery("#ageSpan").val();
								// 大于18周岁
								if (parseInt(ageSpan) >= 18) {
									jQuery('#text_one').hide();
									jQuery('#text_two').show();
									jQuery('#text_three').hide();
									jQuery('#text_five').hide();
									jQuery("#childrenflag").val("0");
									showOnlyOne(2);
									closeError();
								} else {// 小于18周岁
									jQuery('#text_one').hide();
									jQuery('#text_two').hide();
									jQuery('#text_three').show();
									jQuery('#text_five').hide();
									closeError();
									jQuery("#childrenflag").val("1");
									showOnlyOne(6);
								}
							} else {
								jQuery('#text_one').hide();
								jQuery('#text_two').show();
								jQuery('#text_three').hide();
								jQuery('#text_five').hide();
								jQuery("#childrenflag").val("0");
								showOnlyOne(2);
								closeError();
							}
						} else {// 给出错误提示
							jQuery('#tipInfoDiv1').show();
						}
					});
	jQuery("#next_2").click(
			function() {
				var work_style = jQuery("#work_style").val();
				var lvnum = jQuery(
						'input:radio[name="insurance.travelnum"]:checked')
						.val();
				var country = jQuery(
						'input:radio[name="insurance.destination"]:checked')
						.val();
				var sports = jQuery(
						'input:radio[name="insurance.sports"]:checked').val();
				var nocar = jQuery('input:radio[name="insurance.car"]:checked')
						.val();
				if (isEmpty(work_style) && isEmpty(lvnum) && isEmpty(country)
						&& isEmpty(sports) && isEmpty(nocar)) {
					jQuery('#text_one').hide();
					jQuery('#text_two').hide();
					jQuery('#text_three').show();
					jQuery('#text_five').hide();
					closeError();
					showOnlyOne(3);
				} else {
					jQuery('#tipInfoDiv2').show();
				}
			});
	jQuery("#next_2_back").click(function() {
		jQuery('#text_one').show();
		jQuery('#text_two').hide();
		jQuery('#text_three').hide();
		jQuery('#text_five').hide();
		closeError();
		showOnlyOne(1);
	});
	jQuery("#next_3").click(
			function() {
				var revenue = jQuery("#revenue").val();
				var medicalInsuranceStatus = jQuery(
						'input:radio[name="insurance.yiliaobaoxian"]:checked')
						.val();
				var annuityStatus = jQuery(
						'input:radio[name="insurance.yanglaobaoxian"]:checked')
						.val();
				var credit = jQuery(
						'input:radio[name="insurance.loan"]:checked').val();
				if (isEmpty(revenue) && isEmpty(medicalInsuranceStatus)
						&& isEmpty(annuityStatus) && isEmpty(credit)) {
					var  sp= new Array(' id="lvyou">旅游险</span>', ' id="yiwai">意外险</span>',' id="renshou">人寿险</span>',' id="jiankang">健康险</span>',' id="jiaoyu">教育险</span>');
					var sp_tr= new Array();
					//计算星数
					makeStar();
					var star=jQuery("#star").val();
					var cou=0;
					var strarray = new Array();
					strarray = star.split(",");
					for (x = 0; x < 6; x++) {
						for (i = 0; i < strarray.length; i++) {
							if(strarray[i]==5){
								var sp_tr_index=0;
								if(sp_tr.length!=0){
									sp_tr_index=sp_tr.length;
								}
								sp_tr[sp_tr_index]='<span class="need_name need_grade5" '+sp[i];
								strarray[i]=10;
								cou=cou+1;
							}
							if(strarray[i]==4){
								var sp_tr_index=0;
								if(sp_tr.length!=0){
									sp_tr_index=sp_tr.length;
								}
								sp_tr[sp_tr_index]='<span class="need_name need_grade4" '+sp[i];
								strarray[i]=10;
								cou=cou+1;
							}
							if(strarray[i]==3){
								var sp_tr_index=0;
								if(sp_tr.length!=0){
									sp_tr_index=sp_tr.length;
								}
								sp_tr[sp_tr_index]='<span class="need_name need_grade3" '+sp[i];
								strarray[i]=10;
								cou=cou+1;
							}
							if(strarray[i]==2){
								var sp_tr_index=0;
								if(sp_tr.length!=0){
									sp_tr_index=sp_tr.length;
								}
								sp_tr[sp_tr_index]='<span class="need_name need_grade2" '+sp[i];
								strarray[i]=10;
								cou=cou+1;
							}
							if(strarray[i]==1){
								var sp_tr_index=0;
								if(sp_tr.length!=0){
									sp_tr_index=sp_tr.length;
								}
								sp_tr[sp_tr_index]='<span class="need_name need_grade1" '+sp[i];
								strarray[i]=10;
								cou=cou+1;
							}
							if(strarray[i]==0){
								var sp_tr_index=0;
								if(sp_tr.length!=0){
									sp_tr_index=sp_tr.length;
								}
								sp_tr[sp_tr_index]='<span class="need_name need_grade0" '+sp[i];
								strarray[i]=10;
								cou=cou+1;
							}
						}
						if(cou==5){
							break;
						}
					}
					var str_span="";
					for (y = 0; y < sp_tr.length; y++) {
						str_span=str_span+sp_tr[y];
					}
					jQuery("#baoxian_fenxi").append(str_span);
//					jQuery("#lvyou").addClass("need_name need_grade5");
//					jQuery("#yiwai").addClass("need_name need_grade5");
//					jQuery("#jiankang").addClass("need_name need_grade5");
//					jQuery("#renshou").addClass("need_name need_grade5");
//					jQuery("#jiaoyu").addClass("need_name need_grade5");
					jQuery('#text_one').hide();
					jQuery('#text_two').hide();
					jQuery('#text_three').hide();
					jQuery('#text_five').show();
					if (jQuery("#childrenflag").val() == "1") {
						showOnlyOne(7);
					} else {
						showOnlyOne(4);
					}
					closeError();

				} else {
					jQuery('#tipInfoDiv3').show();
				}
			});
	jQuery("#next_3_back").click(function() {
		if (jQuery("#childrenflag").val() == "1") {
			jQuery('#text_one').show();
			jQuery('#text_two').hide();
			jQuery('#text_three').hide();
			jQuery('#text_five').hide();
			showOnlyOne(5);
		} else {
			jQuery('#text_one').hide();
			jQuery('#text_two').show();
			jQuery('#text_three').hide();
			jQuery('#text_five').hide();
			showOnlyOne(2);
		}
	});
	/*
	 * jQuery(".radioCursor").bind("click", function() { if
	 * (jQuery(this).find("input[type='radio']").attr("checked") == "checked") {
	 * jQuery(this).find("input[type='radio']").attr("checked", ""); } else {
	 * jQuery(this).find("input[type='radio']").attr("checked", "checked"); }
	 * });
	 */

});