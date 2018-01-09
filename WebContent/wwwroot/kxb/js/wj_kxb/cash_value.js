define(["jquery", "jquery_cookie", "jquery_form", "art_dialog", "art_iftools", "calendar", "header", "footer", "login", "jqupdate", "productcompare"], function() {
    return {
        init: function() {
            this.selectfDefDay();
            this.complexProductFn();
            this.selsDropDown();
            this.ruleSelectUpFn();
            this.premcalCulateFn();
            this.cashValue();
            this.bindDatePlugin();
        },

        /* 绑定日期控件 */
        bindDatePlugin: function() {

            Date.prototype.Format = function (fmt) { //author: meizz
                var o = {
                    "M+": this.getMonth() + 1, //月份
                    "d+": this.getDate(), //日
                    "h+": this.getHours(), //小时
                    "m+": this.getMinutes(), //分
                    "s+": this.getSeconds(), //秒
                    "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                    "S": this.getMilliseconds() //毫秒
                };
                if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (var k in o)
                    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                return fmt;
            }

            var GetNowDateAdd = function(addday) {
                var today = new Date();
                var day1 = parseInt(today.getDate()) + parseInt(addday);
                var end = new Date(today.getFullYear(), today.getMonth(), day1);
                return end;
            }
            var getAge = function(birth) {
                var r = birth.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
                if (r == null) return;
                var d = new Date(r[1], r[3] - 1, r[4]);
                var date = GetNowDateAdd(1);
                if(specialPremCalFlag == '1'){
                    date = GetNowDateAdd(0);
                }

                var newday = date.getDate();
                var newmonth = date.getMonth();
                var newyear = date.getFullYear();

                if ((newyear < r[1]) == true) {
                    alert('出生日期不能大于当前日期');
                    return;
                }
                if ((newyear == r[1] && newmonth < (r[3] - 1)) == true) {
                    alert('出生日期不能大于当前日期');
                    return;
                }
                if ((newyear == r[1] && (newmonth == (r[3] - 1)) && newday < r[4]) == true) {
                    alert('出生日期不能大于当前日期');
                    return;
                }

                if (date.getMonth() + 1 > r[3]) {
                    return (newyear - r[1]);
                } else if (date.getMonth() + 1 == r[3]) {
                    if (newday >= r[4]) {
                        return (newyear - r[1]);
                    } else {
                        return (newyear - r[1] - 1);
                    }
                } else {
                    return (newyear - r[1] - 1);
                }
            }
            var inputDom = document.getElementById('inpRiskAppFactor_TextAge');
            var startDom = document.getElementById('txtStartDay');
            var endDom = document.getElementById('txtEndDay');
            var bnrDom = $('.bnr_box .text_age')[0];

            if(inputDom) {
                var txtSelect = '投保年龄';
                var jBnrBox = $(".bnr_box .bnr_sel:contains(" + txtSelect + ")");
                var minDate = GetNowDateAdd($(inputDom).attr('data-min')).Format("yyyy-MM-dd");
                var maxDate = GetNowDateAdd($(inputDom).attr('data-max')).Format("yyyy-MM-dd");
                new MyCalendar({
                    el: inputDom,
                    minDate: minDate,
                    maxDate: maxDate,
                    callback:  function(data, arg) {
                        if(arg.type != 'date') return;

                        var userAge = parseInt(getAge(data)); //当前投保年龄

                        $('.bnr_box .text_age').val(data);
                        jBnrBox.find("input").val(userAge + '周岁');
                        jBnrBox.find(".select_ul span").text(userAge + '周岁');

                        if(complicatedFlag != 'Y') {
                            $('.tb_span_aga').html(userAge + '周岁').attr('name', userAge + 'Y')
                            premDocal(riskCode);
                        } else {
                            $(inputDom).change();
                        }
                    }
                });

                $(inputDom).on('click', function() {
                    $('.calendar-box ').show();
                });
            }

            if(startDom && endDom) {
                var startDate = GetNowDateAdd($(startDom).attr('data-min')).Format("yyyy-MM-dd");
                var endDate = GetNowDateAdd($(endDom).attr('data-min')).Format("yyyy-MM-dd");

                new MyCalendar({
                    el: startDom,
                    minDate: startDate
                });

                new MyCalendar({
                    el: endDom,
                    minDate: endDate
                });

                $(startDom).add(endDom).on('click', function() {
                    $('.tishi_day').empty().hide();
                });
            }

            if(bnrDom) {
                var sDate = GetNowDateAdd($(bnrDom).attr('data-min')).Format("yyyy-MM-dd");
                var eDate = GetNowDateAdd($(bnrDom).attr('data-max')).Format("yyyy-MM-dd");
                new MyCalendar({
                    el: bnrDom,
                    minDate: sDate,
                    maxDate: eDate,
                    left: -371,
                    callback:  function(data, arg) {
                        if(arg.type != 'date') return;

                        var userAge = parseInt(getAge(data)); //当前投保年龄
                        $('.tb_span_aga').html(userAge + '周岁').attr('name', userAge + 'Y');
                        $(inputDom).val(data)
                        jBnrBox.find("input").val(userAge + '周岁');
                        jBnrBox.find(".select_ul span").text(userAge + '周岁');

                        if(complicatedFlag != 'Y') {
                            premDocal(riskCode);
                        } else {
                            $(inputDom).change();
                        }
                    }
                });

                $(bnrDom).on('click', function() {
                    if($('.bnr_box').css('position') == 'absolute') {
                        $('.calendar-box').css('position', 'absolute');
                    } else {
                        $('.calendar-box').css('position', 'fixed');
                    }
                    $('.calendar-box ').show();
                });

                $(window).on('scroll', function() {
                    $('.calendar-box ').hide();
                });
            }
        },

        /* 现金价值页初始化加载 */
        cashValue: function() {
            // 加载默认值
            var def = sessionStorage.getItem('CASH_VALUE_DEF');
            if(def && def != null){
                var defArray = def.split(",");
                for(var i=0;i<defArray.length;i++){
                    var _key = defArray[i].split("-")[0];
                    var _value = defArray[i].split("-")[1];
                    var _cId = defArray[i].split("-")[2];
                    if(_key.indexOf("TextAge") > 0){
                        jQuery("#TextAge_0").text(_value.substring(0,_value.length-1)+"周岁").attr("name",_value);
                    }
                    var selected = jQuery("#"+ _cId);
                    selected.parent().parent().find(".li_selected").removeClass("li_selected");
                    selected.parent().addClass("li_selected");
                }
                var birthday = sessionStorage.getItem('CASH_VALUE_BIRTHDAY');
                jQuery("#inpRiskAppFactor_TextAge").val(birthday);
            }

            var dutyVal = sessionStorage.getItem('CASH_VALUE_DUTY');
            if(dutyVal){
                var dutyValArray = dutyVal.split(",");
                for(var i=0;i<dutyValArray.length;i++){
                    var _key = dutyValArray[i];
                    var selected = jQuery("#"+ _key);
                    selected.parent().find(".sp_selected").removeClass("sp_selected");
                    selected.addClass("sp_selected");
                }
            }

            var dutyVal1 = sessionStorage.getItem('CASH_VALUE_DUTY1');
            if(dutyVal1){
                var dutyValArray = dutyVal1.split(",");
                for(var i=0;i<dutyValArray.length;i++){
                    var _key = dutyValArray[i].split("&")[0];
                    var _value = dutyValArray[i].split("&")[1];

                    var obj = jQuery("#"+ _key);

                    if(obj.prop("tagName") == "SELECT") {
                        obj.val(_value);
                    } else if(obj.prop("tagName") == "SPAN") {
                        obj.text(_value);
                    }
                }
            }

            if (dutyVal) {
                jQuery(".bz_time .sp_selected").click();//有责任上调
            } else {
                compRecal(riskCode);//无责任上调
            }
        },

        /* 旅游险出行日期自主选择 */
        selectfDefDay: function() {
            window.selfDefineDay = function(startId, endId, riskcode) {
                var beginDate = jQuery.trim(jQuery('#' + startId).val());
                var endDate = jQuery.trim(jQuery('#' + endId).val());
                var diffDays = 0;
                if ((isDate(beginDate) != false) && (isDate(endDate) != false)) {
                    var rbegin = beginDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
                    var rend = endDate.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
                    var Date1 = new Date(rbegin[1], rbegin[3] - 1, rbegin[4]);
                    var Date2 = new Date(rend[1], rend[3] - 1, rend[4]);
                    if (Date1 > Date2) {
                        jQuery("#DayE").html("出发日期不能晚于结束日期");
                        jQuery('#DayE').show();
                    } else {
                        diffDays = parseInt(Math.abs(Date2 - Date1) / 1000 / 60 / 60 / 24) + 1;
                    }
                }
                jQuery("#hdnSelectedDay").val(diffDays);
                jQuery("#LiDayItemAuto").html(diffDays + '天');
                if (diffDays > 0) {
                    jQuery("#help_select_day").hide();
                    if(complicatedFlag!="Y"){
                        premDocal(riskcode);
                    }
                }
            }

            window.Dayclear = function() {
                jQuery("#UlDayBelongs").find(".LiDayItemAuto").parent("li").each(
                    function() {
                        jQuery(this).removeClass("li_selected");
                    });
                jQuery("#help_select_day").hide();
                jQuery('#LiDayItemAuto').html("自主选择");
                jQuery('#DayE').html("");
                jQuery('#DayE').hide();
            }

            window.DaySelectAuto = function(type, enday) {
                jQuery("#UlDayBelongs").find("ul").find("li").each(function() {
                    jQuery(this).removeClass("li_selected");
                });
                jQuery("#Li" + type + enday).parent().addClass("li_selected");
                jQuery("#hdnIsAutoSelect").val("1");
                jQuery("#help_select_day").show();
                jQuery("#hdnSelectedDay").val("0");
            }

            window.DaySelect = function(type, enday) {
                jQuery("#UlDayBelongs").find("ul").find("li").each(function() {
                    jQuery(this).removeClass("li_selected");
                });
                jQuery("#Li" + type + enday).parent().addClass("li_selected");
                jQuery("#hdnIsAutoSelect").val("0");
                jQuery("#help_select_day").hide();
                jQuery("#hdnSelectedDay").val(enday);
                jQuery('#LiDayItemAuto').html("自主选择");
                jQuery('#DayE').html("");
                jQuery('#DayE').hide();
            }

            window.isDate = function(dateString) {
                var r = dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
                if (r == null) {
                    return false;
                }
                var d = new Date(r[1], r[3] - 1, r[4]);
                var num = (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3] && d
                    .getDate() == r[4]);
                var time = d.getFullYear() == r[1] && (d.getMonth() + 1) == r[3]
                    && d.getDate() == r[4];
                if (time != true) {
                    return false;
                }
            }

            window.ErroClear = function(erroId) {
                jQuery('#' + erroId).html("");
                if (erroId == "DayE") {
                    jQuery('#' + erroId).hide();
                }
            }

            window.isBirthDate = function(id, erroId) {
                var date = jQuery.trim(jQuery('#' + id).val());
                if (isDate(date) == false) {
                    jQuery('#' + erroId).html("日期格式错误");
                    if (erroId == "DayE") {
                        jQuery('#' + erroId).show();
                    }
                    return false;
                }
            }
        },
        /**
         * @des: 复杂产品详细页优化：1. 复杂产品自选保障计划产品保额变更
         2. 复杂产品自主选择日期功能追加
         3. 复杂产品各计划选项的责任保额变更
         4. 复杂产品各计划选项的保额可设置默认值
         5. 复杂产品多责任保额可级联选择。
         6. 产品选项显隐级联
         7. 兼容产品保费试算
         * @maker: Songzairan
         * @date: 2015.6.05
         */
        complexProductFn: function() {
            var objThis = this;
            jQuery.fn.complexProduct = function(opt) {

                if(complicatedFlag != "Y") return;

                var _opts, elmBox, elmLi, tdList, jCpPriceUl, jBnrPrice, jPlanCon, jAutoSelDay, jDateBtn, jSelLi, jAgeClone, nTemp, oInputDate, oSelList, oBnrBox;

                return this.each(function() {
                    _opts = jQuery.extend({
                        boxElm: this,
                        cldElm: "ul li",
                        selElm: ".li_selected",
                        birElm: ".tb_span_aga",
                        listElm: "#gh_tables td.CDutyCol2",
                        addClsName: "CDutyCol2",
                        dateElm: "#inpRiskAppFactor_TextAge",
                        ttlElm: ".cp_title_ms",
                        groElms: ".cp_yh_mes, #applicantBirthday",
                        dateText: ".up_li_width",
                        dateSpan: ".tb_span_aga",
                        bnrBox: ".bnr_box"
                    }, opt);

                    _staticInit();
                    _showList(jQuery(_opts.selElm, elmBox));
                    _monitor();
                });

                /* init */
                function _staticInit() {
                    elmBox = jQuery(_opts.boxElm);
                    elmLi = jQuery(_opts.cldElm, elmBox);
                    tdList = jQuery(_opts.listElm);
                    jSelLi = jQuery(_opts.selElm, _opts.boxElm);
                    jCpPriceUl =  jQuery(".cp_descon ul.price");
                    jBnrPrice = jQuery("#txt_price");
                    jPlanCon = jQuery(".cp_descon .paln_con > ul");
                    jAutoSelDay = jQuery(".cp_descon #UlDayBelongs > ul > li > span:not(#LiDayItemAuto)");
                    jDateBtn = jQuery("#help_select_day .input_okday");
                    oSelList = jQuery(_opts.ttlElm, elmBox).next().not(_opts.groElms);
                    oInputDate = jQuery(_opts.dateElm, elmBox);
                    oBnrBox = jQuery(_opts.bnrBox);
                    jAgeClone = jQuery(".text_age").clone().removeAttr("id").addClass("txt_sel");

                    tdList.find("select").each(function(i, v) {
                        v.disabled = "";
                    });
                }

                /* show all list */
                function _showList(selectedLi, tempValArr) {
                    var arr = [];     // 为多数组交集使用
                    var strArr = [];  // 为保存JSON KEYNAME使用
                    var strGroup = jQuery(_opts.selElm, jPlanCon).find("span").attr("id");  // 为判断已选的计划选项使用

                    tdList.find("select, span").empty();

                    selectedLi.each(function(i, v) {
                        if(jQuery(v).find(_opts.birElm).length != 0) {

                            var strKey = jQuery(v).parent().attr("id") + "_" + jQuery(v).children().attr("name").slice(0, -1);

                        } else {

                            var strKey = jQuery(v).parent().attr("id") + "_" + jQuery(v).children().attr("id").split("_")[1];

                        }

                        strArr.push(strKey);
                    });

                    for(var i=0; i<tdList.length; i++) {
                        eval("var arrVal" + i +" =[]");

                        for(var j=0; j<strArr.length; j++) {
                            var zArr = [];  // 为保存JSON VALUE使用

                            if(strArr[j].indexOf("_TextAge") != -1 && oInputDate.length != 0 ) {

                                var _strSex = ""; //判断出生日期和性别级联数据的Key值

                                jQuery("span[id^='Sex_']").each(function(i, v) {

                                    if (jQuery(this).parent().hasClass("li_selected")) {

                                        _strSex = jQuery(this).attr("id");

                                    }

                                });

                                var _dutyArr = eval("AppFacDutyRela." + strArr[j] + "_" + _strSex + ".dutyList");

                            } else if( strArr[j].indexOf("_Sex") != -1 && oInputDate.length != 0) {
                                var _strAge = jQuery("#TextAge_0").text().slice(0, -2);


                                var _dutyArr = eval("AppFacDutyRela." + strArr[j] + "_TextAge_" + _strAge + ".dutyList");

                            } else {
                                var _dutyArr = eval("AppFacDutyRela." + strArr[j] + ".dutyList");
                            }

                            for(var k=0; k<_dutyArr.length; k++) {

                                zArr.push(_dutyArr[k]);

                            }

                            eval("arrVal" + i).push(zArr[i]);

                        }

                        var _itst = intersect(eval("arrVal" + i));

                        if(_itst != null) {
                            arr.push(_itst);
                        } else {
                            arr.push(["-"]);
                        }
                    }

                    tdList.each(function(i, v) {
                        var num = arr[i].length;
                        var _id = jQuery(v).children().attr("id");
                        var tempSel;

                        for(var j=0; j<arr[i].length; j++) {
                            if(num != 1){
                                if(jQuery(v).find("select").length == 0 ){
                                    jQuery(v).empty()
                                        .html("<select id=" + _id + "></select>");
                                }

                                jQuery(v).find("select")
                                    .addClass(_opts.addClsName)
                                    .append("<option value='" + arr[i][j] + "'>" + arr[i][j] + "</option>")
                                    .unbind("change")
                                    .bind("change", function() {

                                        nTemp = jCpPriceUl.find("li > span")
                                            .first()
                                            .text()
                                            .slice(1);

                                        var _nSelId = jQuery(this).attr("id");
                                        var _oSelect = jQuery(v).find("select");
                                        var _nSelVal = _oSelect.val();
                                        var _nSelArr = [];

                                        _oSelect.find("option").each(function(i, v) {
                                            _nSelArr.push(jQuery(v).val());
                                        });

                                        var _index = jQuery.inArray(_nSelVal, _nSelArr);

                                        /** begin 责任选项联动 **/
                                        var _casArr;

                                        if(jPlanCon.length > 0) {
                                            _casArr = AppFacDutyDef[strGroup][1];
                                        } else {
                                            _casArr = AppFacDutyDef["default"][1];
                                        }

                                        for(var x=0; x<_casArr.length; x++){

                                            if(jQuery.inArray(_nSelId, _casArr[x]) != -1){
                                                for(var i=0; i<_casArr[x].length; i++) {
                                                    jQuery("#" + _casArr[x][i]).find("option")[_index].selected = "selected";
                                                }
                                            }

                                        }

                                        /** end 责任选项联动 **/
                                        premRecal(riskCode);
                                        monitoring();

                                        tempSel = jQuery(v).find("select").val();

                                    });


                            } else {
                                jQuery(v).empty()
                                    .html("<span id=" + _id + " class=" + _opts.addClsName + ">" + arr[i][j] + "</span>");
                            }
                        }

                        var _mtr = jQuery(v).find("select, span")
                            .attr("id")
                            .slice(-2);

                        if(_mtr == "01" && arr[i][0] == "不投保") {
                            jQuery(v).find("select")
                                .children()[1]
                                .selected = true;
                        }

                        /**
                         责任选项默认值
                         **/
                        if(num != 1) {
                            if(jPlanCon.length > 0) {
                                jQuery(v).find("select").val(AppFacDutyDef[strGroup][0][i]);
                            } else {
                                jQuery(v).find("select").val(AppFacDutyDef["default"][0][i]);
                            }
                        }

                        tempSel = jQuery(v).find("select").val();

                    });

                    if(tempValArr && tempValArr.length != 0) {
                        tdList.children().each(function(i, v) {
                            if(jQuery(this).is("select")) {
                                var valArr = jQuery(this).find("option");
                                var _vArr = [];

                                valArr.each(function(j, u) {
                                    _vArr.push(u.value);
                                });

                                if(jQuery.inArray(tempValArr[i], _vArr) != -1) {
                                    jQuery(v).val(tempValArr[i]);
                                } else {
                                    jQuery(v).val(_vArr[0]);
                                }
                            }
                        });
                    }

                }

                /* monitor the price */
                function monitoring() {
                    var oPrice = jCpPriceUl.find("li > span").eq(0);

                    var outId = setTimeout(function() {
                        clearInterval(intId);
                    }, 8000);

                    var intId = setInterval(function(){
                        var oMoney = oPrice.html();
                        var nPrice = parseFloat(oPrice.text().slice(1));

                        if(nPrice != nTemp) {
                            jBnrPrice.html(oMoney);
                            nTemp = nPrice;
                            clearInterval(intId);
                            clearTimeout(outId);
                        }
                    }, 500);
                }

                /* bind event */
                function _monitor() {
                    var ofactDayLi, selLiArr, _nowNum = 0;

                    elmLi.not("#birthday_li_id").children(":not(#LiDayItemAuto)").removeAttr("onclick");

                    elmLi.bind("click", function(e) {
                        if(jQuery(this).is("#birthday_li_id")) return;
                        if(jQuery(this).children().is(".elect_plan")) return;   //点击计划对比，跳出事件
                        if(jQuery(this).find("a").length != 0) return;	//判断计划选项是否链接跳转

                        if(jQuery(this).find("span").attr("id") == "LiDayItemAuto") return; //判断是否点击“自主选择”日期按钮

                        tdList.find("select").each(function(i, v) {  //默认select选项可用
                            v.disabled = "";
                        });

                        if(jQuery.inArray(this, selLiArr) != -1) return;  //判断是否点击已选选项上，阻止重复发送请求

                        selLiArr = jQuery(_opts.selElm, elmBox);

                        if(jQuery(this).siblings().children().is("#LiDayItemAuto")) {   //点击的是保险期限栏的选项
                            ofactDayLi = null;

                            var dayScope = jQuery(this).text().slice(0, -1).split("-");

                            if(dayScope.length == 1 && _nowNum == dayScope[0]) return;
                            if(dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1]) return;
                            _nowNum = 0;
                        } else {   //点击的是非保险期限栏的选项
                            if(ofactDayLi && _nowNum == 0) {   //自主选择日期为0天时，点击其他选项
                                ofactDayLi.addClass(_opts.selElm.slice(1));
                                jQuery("#LiDayItemAuto").parent().removeClass(_opts.selElm.slice(1));
                                jQuery("#LiDayItemAuto").text("自主选择");
                                jQuery("#help_select_day").hide();
                                selLiArr = jQuery(_opts.selElm, elmBox);
                            } else if(ofactDayLi && _nowNum != 0) {   //自主选择日期不为0天时，点击其他选项
                                jQuery("#help_select_day").hide();
                                var maxDayScope = jQuery(jAutoSelDay[jAutoSelDay.length - 2]).text().slice(0, -1).split("-");
                                var selDayMax;

                                if(maxDayScope.length == 1) {
                                    selDayMax = maxDayScope[0]
                                } else if (maxDayScope.length > 1) {
                                    selDayMax = maxDayScope[1]
                                }

                                if (_nowNum > selDayMax) {
                                    jQuery("#LiDayItemAuto").parent().removeClass("li_selected");
                                    jAutoSelDay.eq(jAutoSelDay.length -1).parent().addClass("li_selected");
                                    selLiArr = jQuery(_opts.selElm, _opts.boxElm);
                                } else {
                                    jAutoSelDay.each(function(i, u) {  //遍历匹配自选日期区间范围
                                        if(i == jAutoSelDay.length - 1) return;

                                        var dayScope = jQuery(u).text().slice(0, -1).split("-");

                                        if((dayScope.length == 1 && _nowNum == dayScope[0]) || (dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1])) {
                                            var selDayLi = jQuery(u).parent();
                                            var filterLi = jQuery(_opts.selElm, elmBox).filter(function() {
                                                if(jQuery(this).children().attr("id") !="LiDayItemAuto") {
                                                    return this;
                                                }
                                            });

                                            selLiArr = jQuery.merge(selDayLi, filterLi);
                                            return false;
                                        }
                                    });
                                }
                            }
                        }

                        var tempValArr = [];

                        if(jQuery(this).find("span").attr("id").match(/^Plan/g) == null) {
                            tdList.children().each(function(i, v) {
                                if(jQuery(this).is("select")) {
                                    tempValArr.push(jQuery(v).val());
                                } else if(jQuery(this).is("span")) {
                                    tempValArr.push(jQuery(v).text());
                                }
                            });
                        }

                        _showList(selLiArr, tempValArr);

                        var selKey = jQuery(this).children().attr("id").replace(/[^0-9]/ig, "");

                        selShowList(selKey, this);
                        requireList();
                        objThis.ruleSelectUpFn();
                        premRecal(riskCode);

                    });

                    jQuery("#LiDayItemAuto").bind("click", function() {
                        tdList.find("select").each(function(i, v) {
                            v.disabled = "disabled";
                        });

                        ofactDayLi = jSelLi.filter(":not(.li_selected)");
                    })

                    jDateBtn.bind("click", function() {
                        _nowNum = parseInt(jQuery("#LiDayItemAuto").text());

                        tdList.find("select").each(function(i, v) {
                            v.disabled = "";
                        });

                        if(_nowNum == 0) {
                            tdList.find("select").each(function(i, v) {
                                v.disabled = "disabled";
                            });

                            ofactDayLi = jAutoSelDay.eq(0).parent();
                            return;
                        }

                        premRecal(riskCode);
                        jSelLi = jQuery(_opts.selElm, _opts.boxElm);
                    });

                    //jQuery 1.4版本的change事件IE兼容差，此处用原生事件
                    if (oInputDate[0]) {
                        oInputDate[0].onchange =  function() {

                            var userAge = parseInt(getAge(oInputDate.val())); //当前投保年龄
                            var oTxtAge = jQuery(this).parents("ul").next(_opts.dateText).find(_opts.dateSpan);
                            var _strSex = ""; //判断出生日期和性别级联数据的Key值

                            jQuery("span[id^='Sex_']").each(function(i, v) {

                                if (jQuery(this).parent().hasClass("li_selected")) {

                                    _strSex = jQuery(this).attr("id");

                                }

                            });

                            var ageKey = oTxtAge.attr("name", userAge + "Y").attr("name").slice(0, -1) + "_" + _strSex;
                            var tempValArr = [];
                            oTxtAge.html(userAge + "周岁"); //提示用户输入的年龄

                            selShowList(ageKey, this);
                            requireList();

                            tdList.children().each(function(i, v) {

                                if(jQuery(this).is("select")) {
                                    tempValArr.push(jQuery(v).val());
                                } else if(jQuery(this).is("span")) {
                                    tempValArr.push(jQuery(v).text());
                                }

                            });

                            _showList(jQuery(_opts.selElm, _opts.boxElm), tempValArr);
                            objThis.ruleSelectUpFn();
                            premRecal(jQuery(this).attr("data-id"), this, jQuery(this).attr("data-area"));

                        }

                    }
                }
                function selShowList(keyName, _this) {

                    var _key;
                    var _key2;  // 长期险勾选保险期限和缴费年期时，需要和年龄、性别组成的联合主键共同控制投保要素的显隐(取交集)

                    if(jQuery(_this).parent().attr("id") == "birthday_li_id") {
                        _key = jQuery(_this).attr("data-area") + "_" + keyName;
                    } else {
                        if(jQuery(_this).children().attr("id").indexOf("Sex_") != -1 && jQuery("li#birthday_li_id").length > 0) {
                            var _strAge = jQuery("#TextAge_0").text().slice(0, -2);

                            _key = jQuery(_this).parents("ul").attr("id") + "_" + keyName + "_TextAge_" + _strAge;

                        } else {
                            _key = jQuery(_this).parents("ul").attr("id") + "_" + keyName;

                            if ((jQuery(_this).children().attr("id").indexOf("Period_") != -1 || jQuery(_this).children().attr("id").indexOf("FeeYear_") != -1)
                                || jQuery(_this).children().attr("id").indexOf("Rule02_") != -1 && jQuery("li#birthday_li_id").length > 0) {

                                var _strSex;
                                jQuery("span[id^='Sex_']").each(function(i, v) {
                                    if (jQuery(this).parent().hasClass("li_selected")) {
                                        _strSex = jQuery(this).attr("id");
                                    }
                                });
                                if (jQuery("#inpRiskAppFactor_TextAge").length > 0) {
                                    _key2 = jQuery("#inpRiskAppFactor_TextAge").attr("data-area") + "_" + jQuery("#TextAge_0").text().slice(0, -2) + "_" + _strSex;
                                }
                            }

                        }
                    }

                    if(!AppFacDutyRela[_key] && _key.indexOf("TextAge")) {  // 当某年龄段只有一种性别可选时，自动切换可选的性别上。
                        if(_key.indexOf("Sex_0")) {
                            _key = _key.replace("Sex_0", "Sex_1");
                        } else if (_key.indexOf("Sex_1")) {
                            _key = _key.replace("Sex_1", "Sex_0");
                        }
                    }

                    var _selObj = AppFacDutyRela[_key].cascade;

                    // 长期险勾选保险期限和缴费年期时，需要和年龄、性别组成的联合主键共同控制投保要素的显隐(取交集)
                    if(_key2) {

                        var _selObj2 = AppFacDutyRela[_key2].cascade;
                        var _result = {};

                        for(var x in _selObj) {

                            var _tArr = intersect([_selObj[x], _selObj2[x]]);

                            _result[x] = _tArr;
                        }

                        _selObj = _result;
                    }

                    oSelList.not("#" + jQuery(_this).parents("ul").attr("id")).children().hide();

                    for(var x in _selObj) {

                        var _showArr = _selObj[x];

                        for(var i=0; i<_showArr.length; i++) {

                            jQuery("#" + _showArr[i]).parent().show();

                        }

                        var selLi = jQuery("#" + x).find(_opts.selElm);

                        if(selLi.is(":hidden")) {

                            selLi.removeClass(_opts.selElm.slice(1));
                            jQuery("#" + x).children(":visible").first().addClass(_opts.selElm.slice(1));

                        }

                    }

                    jSelLi = jQuery(_opts.selElm, _opts.boxElm);

                }

                //修改侧边栏快速筛选的值
                function requireList() {

                    var jCp = jQuery(".cp_descon");
                    var jCpDiv = jQuery(".tb_age:not(:hidden), .paln_con:not(:hidden)", jCp);
                    var _vArr = jQuery();
                    jQuery(".bnr_sel>.select_ul").empty();

                    jCpDiv.each(function(i, v) {

                        var _vEm = jQuery(v).find(".cp_title_ms").text();

                        var _vInput = jQuery(v).find("ul > .li_selected").children().text();

                        var _vList = jQuery(v).children().find("span:not(.help_selec_ss, .elect_plan,:hidden)");

                        for (var x = 0; x < _vList.length; x++) {

                            if (_vList.eq(x).children().is("a")) {
                                var _txt = _vList.eq(x).html();
                            } else {
                                var _txt = _vList.eq(x).text();
                            }

                            jQuery(".zIn0" + (i + 1) + "> .select_ul").append(
                                "<li><span>" + _txt + "</span></li>");

                        }

                        jQuery(".zIn0" + (i + 1) + "> .txt_sel").val(_vInput);
                    });

                }

                //计算年龄，格式化出生日期
                function getAge(birth) {

                    var r = birth.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
                    if (r == null) return;
                    var d = new Date(r[1], r[3] - 1, r[4]);
                    var date = GetNowDateAdd(1);
                    if(specialPremCalFlag == '1'){
                        date = GetNowDateAdd(0);
                    }

                    var newday = date.getDate();
                    var newmonth = date.getMonth();
                    var newyear = date.getFullYear();

                    if ((newyear < r[1]) == true) {
                        alert('出生日期不能大于当前日期');
                        return;
                    }
                    if ((newyear == r[1] && newmonth < (r[3] - 1)) == true) {
                        alert('出生日期不能大于当前日期');
                        return;
                    }
                    if ((newyear == r[1] && (newmonth == (r[3] - 1)) && newday < r[4]) == true) {
                        alert('出生日期不能大于当前日期');
                        return;
                    }

                    if (date.getMonth() + 1 > r[3]) {
                        return (newyear - r[1]);
                    } else if (date.getMonth() + 1 == r[3]) {
                        if (newday >= r[4]) {
                            return (newyear - r[1]);
                        } else {
                            return (newyear - r[1] - 1);
                        }
                    } else {
                        return (newyear - r[1] - 1);
                    }

                }

                //今天的日期加上 addday 之后的日期
                function GetNowDateAdd(addday) {

                    var today = new Date();
                    var day1 = today.getDate();
                    day1 = parseInt(day1) + parseInt(addday);
                    var end = new Date(today.getFullYear(), today.getMonth(), day1);
                    return end;

                }

                //多数组交集
                function intersect(arrs) {
                    var arr = arrs.shift();

                    //兼容IE8以下的版本
                    if (typeof Array.prototype.filter != "function") {
                        Array.prototype.filter = function (fn, context) {
                            var arr = [];
                            if (typeof fn === "function") {
                                for (var k = 0, length = this.length; k < length; k++) {
                                    fn.call(context, this[k], k, this) && arr.push(this[k]);
                                }
                            }
                            return arr;
                        };
                    }

                    for(var i=arrs.length; i--;) {
                        var p = {
                            "boolean": {},
                            "number": {},
                            "string": {}
                        }
                        var obj = [];

                        arr = arr.concat(arrs[i]).filter(function (x) {
                            var t = typeof x;
                            return !((t in p) ? !p[t][x] && (p[t][x] = 1) : obj.indexOf(x) < 0 && obj.push(x));
                        });

                        if(!arr.length) return null; //发现不符合便退出
                    }

                    return arr;
                }
            }
            jQuery(".bz_time").complexProduct();
        },

        /* 责任上调 */
        ruleSelectUpFn: function() {
            // 记录被选中的责任
            var indexArr = [];
            jQuery(".no_prem").each(function(i, v) {
                var nIndex = jQuery.inArray(jQuery(v).find(".sp_selected")[0], jQuery(v).find("span"));
                indexArr.push(nIndex);
            });

            jQuery(".ruleSelectUp").find("select").each(function(i, v) {
                var selArr = [];
                var selId = v.id;
                var emTxt = jQuery(v).parent().attr("data-title");
                var tipTxt = jQuery(v).parent().attr("data-tip");
                var selVal = jQuery(v).val();

                jQuery(v).children().each(function(j, w) {
                    var oKeyVal = {};
                    oKeyVal.name = jQuery(w).text();
                    oKeyVal.value = jQuery(w).val();
                    selArr.push(oKeyVal);
                });
                jQuery(v).parent().empty().append("<span id='" + selId + "' class='CDutyCol2'>" + selArr[0].name + "</span>");

                jQuery(".bz_time").find(".no_prem").remove();
                jQuery("div[class*='zRule']").remove();

                jQuery(".bz_time").append("<div class='no_prem clearfix'><em class='cp_title_ms'>" + emTxt + "</em><p class='" + selId + "'></p></div>");

                jQuery(".bnr_box .bnr_price").before(
                    "<div class='bnr_sel zRule" + i +"'>" +
                    "<em>" + emTxt + "</em>" +
                    "<input class='txt_sel' type='text' value='" + selVal + "' readonly='readonly' />" +
                    "<ul class='select_ul' data-id='" + selId + "'></ul>" +
                    "</div>"
                );

                jQuery.each(selArr, function(dIndex) {
                    var clsName = this.value == selVal? "sp_selected" : "";
                    jQuery("." + selId).append("<span class='" + clsName + "' id='"+selId+"_duty_" + dIndex + "' data-value='" + this.value + "'>" + this.name + "</span>");
                    jQuery(".zRule" + i).find(".select_ul").append("<li><span>" +  this.name + "</span></li>");
                });
                jQuery("." + selId).parent().append('<p class="ms_tip">' + tipTxt +'</p>');

                // 模拟被选中责任的试算点击事件
                var _index = indexArr[i]||0;
                jQuery(".no_prem").eq(i).find("span").eq(_index).click();
            });

            jQuery("div[class*='zRule'] .select_ul li").die("click").live("click", function() {
                var _id = jQuery(this).parent().attr("data-id");
                var _val = jQuery(this).text().trim();

                jQuery("p." + _id).find("span").removeClass("sp_selected").each(function() {
                    var sVal = jQuery(this).attr("data-value");
                    if(sVal == _val) jQuery(this).addClass("sp_selected");
                });
                jQuery("#" + _id).text(jQuery(this).text());

                premRecal(riskCode);
            });

            jQuery(".no_prem span").die("click").live("click", function() {
                jQuery(this).addClass("sp_selected")
                    .siblings()
                    .removeClass("sp_selected");

                var selSpanId = jQuery(this).parent().attr("class");
                var nIndex = jQuery.inArray(jQuery(this).parents(".no_prem")[0], jQuery(".no_prem"));

                jQuery("#" + selSpanId).text(jQuery(this).text());
                jQuery(".bnr_box").find(".zRule" + nIndex).find(".txt_sel").val(jQuery(this).text());

                premRecal(riskCode);
            });
        },

        /* 右侧投保框中，模拟select选择框 */
        selsDropDown: function() {
            jQuery.fn.selectDropDown = function(opt) {
                var _opt, jBody, jCp, jCpDiv, jCpUl, jCpPriceUl, jCpLi, jCpChild, jDaySelect, jDateAll, jDateBox, jDateStart, jDateEnd, jDateBtn, jAgeClone, liArr, jBnrBox, jSel, jUl, jBnrPrice, nTemp, oTemp;

                return this.each(function() {
                    _opt = jQuery.extend({
                        bnrElm: this,
                        selElm: ".txt_sel",
                        ulElm: ".select_ul",
                        childElm: " li span",
                        priElm: "#txt_price",
                        selectLi: ".li_selected",
                        nScroll: 120
                    }, opt);

                    _staticInit();
                    _setInit();
                    _monitor();

                });

                /* static init */
                function _staticInit() {
                    jBody = jQuery("body");
                    jCp = jQuery(".cp_descon");
                    jCpDiv = jQuery(".tb_age:not(:hidden), .paln_con:not(:hidden)", jCp);
                    jCpUl = jQuery("ul:not(.price, #applicantBirthday)", jCp);
                    jCpPriceUl =  jQuery("ul.price", jCp);
                    jCpLi =  jQuery(_opt.selectLi, jCp);
                    jCpChild = jQuery("span:not(#LiDayItemAuto, .elect_plan)", jCpUl);
                    jDaySelect = jQuery("#LiDayItemAuto", jCpUl);
                    jDateAll = jQuery("#UlDayBelongs", jCp);
                    jDateBox = jQuery("#help_select_day", jCp);
                    jDateStart = jQuery("#txtStartDay", jDateBox);
                    jDateEnd = jQuery("#txtEndDay", jDateBox);
                    jDateBtn = jQuery(".input_okday", jDateBox);
                    jBnrBox = jQuery(".bnr_box");
                    jBnrPrice = jQuery(_opt.priElm);
                    liArr = jQuery(jCpLi).toArray();
                    nTemp = jCpPriceUl.find("li > span")
                        .first()
                        .text()
                        .slice(1);

                    //jAgeClone = jQuery(".text_age").clone().removeAttr("id").addClass("txt_sel").attr({"data-min": "20452", "data-max": "28"});
                    jAgeClone = jQuery(".text_age").clone().removeAttr("id").addClass("txt_sel");
                }

                /* set the value of input */
                function _setInit() {
                    var _price = jCpPriceUl.find("li > span")
                        .first()
                        .html();
                    jBnrPrice.html(_price);

                    jCpDiv.each(function(i, v) {
                        var _vEm = jQuery(v).find(".cp_title_ms").text();

                        if(_vEm.match(/出生日期/) != null) _vEm = "投保年龄";

                        var _vInput = jQuery(v).find("ul > .li_selected")
                            .children()
                            .text();
                        var _vList = jQuery(v).children()
                            .find("span:not(.help_selec_ss, .elect_plan)");
                        var _div = jQuery("<div> " +
                            "<em>" + _vEm + "</em>" +
                            "<input class='txt_sel' type='text' value='" + _vInput + "' readonly='readonly' />" +
                            "<ul class='select_ul'></ul>" +
                            "</div>").addClass("bnr_sel zIn0" + (i+1) + "");
                        var _li = jQuery(_opt.ulElm, _div);

                        _div.insertBefore(jBnrPrice.parent());

                        for(var x=0; x<_vList.length; x++) {
                            if(_vList.eq(x).children().is("a")) {
                                var _txt = _vList.eq(x).html();
                            } else {
                                var _txt = _vList.eq(x).text();
                            }

                            _li.append("<li><span>" + _txt + "</span></li>");
                        }

                    });

                    jBnrBox.hide();
                    if(jAgeClone.length != 0) {
                        jBnrBox.find(".ttl").after("<div class='bnr_sel birth_sel'><em>出生日期</em></div>");
                        jBnrBox.find(".birth_sel").append(jAgeClone.removeClass("txt_sel")).append("<p></p>");
                    }
                    jSel = jQuery(_opt.selElm);
                    jUl = jQuery(_opt.ulElm);

                    jQuery(".text_age").focus(function() {
                        jQuery(this).addClass("f_acive");
                    });

                    jQuery(".bnr_box .text_age").blur(function() {
                        var _val = this.value;
                        if(_val == jQuery(this).attr("data-record")) return;
                        jQuery(this).attr("data-record", _val);
                        jQuery(".bz_time .text_age").val(_val);
                        setTimeout(function() {
                            jQuery(".bz_time .text_age").trigger("change");
                            monitoring();
                        }, 100);
                    });

                    jQuery(window).scroll(function() {
                        jQuery(".select_ul").hide();
                    });
                }

                /* bind event */
                function _monitor() {
                    jBody.bind("click", function(e) {
                        var _elm = e.target;

                        if(_elm.className == _opt.selElm.slice(1)) {
                            var _input = jQuery(_elm);
                            var _ul = jQuery(_elm).next();
                            var _child = _ul.find(_opt.childElm);
                            var nUlHeight = _ul.height();
                            var nUl = jQuery.inArray(_elm, jSel.toArray());
                            var _jChild = jQuery("span", jCpUl.eq(nUl));

                            if(_ul.css("display") == "none") {
                                jUl.hide();
                                if(nUlHeight >= _opt.nScroll) {
                                    _ul.css({ height: _opt.nScroll, "overflow-y":"auto" });
                                }

                                _ul.show();

                                if(!_child.data("events")) {
                                    _child.bind("click", function(e) {
                                        if(jQuery(this).children().is("a")) {
                                            return;
                                        }
                                        var nChild = jQuery.inArray(e.target, _child.toArray());
                                        var _txt = jQuery(this).text();

                                        _jChild.not(":hidden").eq(nChild).click();
                                        _input.val(_txt);
                                        _ul.hide();
                                        liArr = jQuery(_opt.selectLi, jCp).toArray();
                                    });
                                }
                            } else {
                                _ul.hide();
                            }
                        } else {
                            jUl.hide();
                        }
                    });

                    jCpChild.bind("click", function(e) {
                        var _elm = e.target;
                        var _par = jQuery(_elm).parent()[0];

                        if(complicatedFlag == "Y") {
                            if(jQuery(this).parent().attr("class") == "li_selected") return;

                            jQuery(this).parent().siblings().removeClass("li_selected");
                            jQuery(this).parent().addClass("li_selected");
                            nTemp = jCpPriceUl.find("li > span")
                                .first()
                                .text()
                                .slice(1);
                        }

                        if(jQuery.inArray(_par, liArr) != -1) {
                            return;
                        }

                        if(_elm == oTemp) {
                            return;
                        } else {
                            oTemp = _elm;
                        }

                        var _txt = jQuery(_elm).text();
                        var _ul = jQuery(_elm).parents("ul")[0];
                        var nUl = jQuery.inArray(_ul, jCpUl.toArray());

                        liArr = jQuery(_opt.selectLi, jCp).toArray();

                        jQuery(jSel[nUl]).val(_txt);

                        monitoring();
                    });

                    jDateBtn.bind("click", function(e) {
                        var vStart = jDateStart.val("");
                        var vEnd = jDateEnd.val("");
                        var _date = jDaySelect.text().slice(0, -1);

                        if(vStart == "" || vEnd == "") return;
                        if(parseInt(_date.slice(0, 1)) == 0) return;

                        liArr = jQuery(_opt.selectLi, jCp).toArray();
                        oTemp = jDaySelect[0];

                        var strSel = jDateAll.find("em").text();
                        var nowSel = jDaySelect.parent();
                        var strArr = [];

                        jSel.each(function(i, v) {
                            strArr[i] = jQuery(v).prev().text();
                        });

                        if(nowSel.attr("class") == _opt.selectLi.slice(1)) {
                            /**/
                            if(complicatedFlag == "Y") {
                                var maxTxt = jDaySelect.parent().prev().prev().children().text();
                                var maxArr = maxTxt.slice(0, -1).split("-");
                                var maxNum = maxArr[maxArr.length - 1];

                                if(parseInt(_date) > maxNum) {
                                    _date = jDaySelect.parent().prev().text();
                                }
                            }
                            /**/
                            jSel.eq(jQuery.inArray(strSel, strArr))
                                .val(_date);
                        }

                        monitoring();
                    });

                }

                /* monitor the price */
                function monitoring() {
                    var oPrice = jCpPriceUl.find("li > span").eq(0);

                    var outId = setTimeout(function() {
                        clearInterval(intId);
                    }, 8000);

                    var intId = setInterval(function(){
                        var oMoney = oPrice.html();
                        var nPrice = parseFloat(oPrice.text().slice(1));

                        if(nPrice != nTemp) {
                            jBnrPrice.html(oMoney);
                            nTemp = nPrice;
                            clearInterval(intId);
                            clearTimeout(outId);
                        }
                    }, 500);
                }
            }
            jQuery(".bnr_box").selectDropDown();
        },

        /* 保费试算 */
        premcalCulateFn: function() {
            this.tempParam = {};    //用于保存上一次的请求参数，防止重复ajax

            function DataCollection(){
                this.map = {};
                this.valuetype = {};
                this.keys = [];

                DataCollection.prototype.get = function(ID){
                    if(typeof(ID)=="number"){
                        return this.map[this.keys[ID]];
                    }
                    return this.map[ID];
                };

                DataCollection.prototype.getKey = function(index){
                    return this.keys[index];
                };

                DataCollection.prototype.size = function(){
                    return this.keys.length;
                };

                DataCollection.prototype.remove = function(ID){
                    if(typeof(ID)=="number"){
                        if(ID<this.keys.length){
                            var obj = this.map[this.keys[ID]];
                            this.map[this.keys[ID]] = null;
                            this.keys.splice(ID);
                            return obj;
                        }
                    }else{
                        for(var i=0;i<this.keys.length;i++){
                            if(this.keys[i]==ID){
                                var obj = this.map[ID];
                                this.map[ID] = null;
                                this.keys.splice(i);
                                return obj;
                                break;
                            }
                        }
                    }
                    return null;
                };

                DataCollection.prototype.toQueryString = function(){
                    var arr = [];
                    for(var i=0;i<this.keys.length;i++){
                        if(this.map[this.keys[i]]==null||this.map[this.keys[i]]==""){continue;}
                        if(i!=0){
                            arr.push("&");
                        }
                        arr.push(this.keys[i]+"="+this.map[this.keys[i]]);
                    }
                    return arr.join('');
                };

                DataCollection.prototype.parseXML = function(xmlDoc){
                    var coll = xmlDoc.documentElement;
                    if(!coll){
                        return false;
                    }
                    var nodes = coll.childNodes;
                    var len = nodes.length;
                    for(var i=0;i<len;i++){
                        var node = nodes[i];
                        var Type = node.getAttribute("Type");
                        var ID = node.getAttribute("ID");
                        this.valuetype[ID] = Type;
                        if(Type=="String"){
                            var v = node.firstChild.nodeValue;
                            if(v==Constant.Null){
                                v = null;
                            }
                            this.map[ID] = v;
                        }else if(Type=="StringArray"){
                            this.map[ID] = eval("["+node.firstChild.nodeValue+"]");
                        }else if(Type=="Map"){
                            this.map[ID] = eval("("+node.firstChild.nodeValue+")");
                        }else if(Type=="DataTable"||Type=="Schema"||Type=="SchemaSet"){
                            this.parseDataTable(node,"DataTable");
                        }else{
                            this.map[ID] = node.getAttribute("Value");
                        }
                        this.keys.push(ID);
                    }
                    return true;
                };

                DataCollection.prototype.parseDataTable = function(node,strType){
                    var cols = node.childNodes[0].childNodes[0].nodeValue;
                    cols = "var _TMP1 = "+cols+"";
                    eval(cols);
                    cols = _TMP1;
                    var values = node.childNodes[1].childNodes[0].nodeValue;
                    values = "var _TMP2 = "+values+"";
                    eval(values);
                    values = _TMP2;
                    var obj;
                    obj = new DataTable();
                    obj.init(cols,values);
                    this.add(node.getAttribute("ID"),obj);
                };

                DataCollection.prototype.toXML = function(){
                    var arr = [];
                    arr.push("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                    arr.push("<collection>");
                    for(var i=0;i<this.keys.length;i++){
                        var ID = this.keys[i];
                        try{
                            var v = this.map[ID];
                            arr.push("<element ID=\""+ID+"\" Type=\""+this.valuetype[ID]+"\">");
                            if(this.valuetype[ID]=="DataTable"){
                                arr.push(v.toString());
                            }else if(this.valuetype[ID]=="String"){
                                if(v==null||typeof(v)=="undefined"){
                                    arr.push("<![CDATA["+Constant.Null+"]]>");
                                }else{
                                    arr.push("<![CDATA["+v+"]]>");
                                }
                            }else if(this.valuetype[ID]=="Map"){
                                if(v==null||typeof(v)=="undefined"){
                                    arr.push("<![CDATA["+Constant.Null+"]]>");
                                }else{
                                    arr.push("<![CDATA["+JSON.toString(v)+"]]>");
                                }
                            }else{
                                arr.push(v);
                            }
                            arr.push("</element>");
                        }catch(ex){alert("DataCollection.toXML():"+ID+","+ex.message);}
                    }
                    arr.push("</collection>");
                    return arr.join('');
                };

                DataCollection.prototype.add = function(ID,Value,Type){
                    this.map[ID] = Value;
                    this.keys.push(ID);
                    if(Type){
                        this.valuetype[ID] = Type;
                    }else	if( Value && Value.getDataRow){//DataTable可能不是本页面中的
                        this.valuetype[ID] = "DataTable";
                    }else{
                        this.valuetype[ID] = "String";
                    }
                };

                DataCollection.prototype.addAll = function(dc){
                    if(!dc){
                        return;
                    }
                    if(!dc.valuetype){
                        alert("DataCollection.addAll()要求参数必须是一个DataCollection!");
                    }
                    var size = dc.size();
                    for(var i=0;i<size;i++){
                        var k = dc.keys[i];
                        var v = dc.map[k];
                        var t = dc.valuetype[k];
                        this.add(k,v,t);
                    }
                };
            }

            window.premRecal = function (riskcode, ele, appFactor) {
                if(complicatedFlag == "Y") {
                    compRecal(riskcode);
                    return;
                }

                if (ele.parentNode.className == "li_selected") {
                    return;
                }
                var appEele = document.getElementById(appFactor);
                var appValue = "";
                if (ele.getAttribute("name") == "inpRiskAppFactor_TextAge") {
                    if (ele.value == "") {
                        return;
                    }
                    var _insuredAge = getInsuredAge(ele.value);
                    if (_insuredAge < 0){
                        return;
                    }
                    ele = appEele.getElementsByTagName("SPAN")[0];
                    ele.setAttribute("name",_insuredAge + "Y");
                    ele.innerHTML = _insuredAge + "周岁";
                }
                if (appEele.tagName == "UL") {
                    changeAppFactorStyle(appEele.getElementsByTagName("LI"), ele);
                    appValue = ele.getAttribute("name");
                } else {
                    appValue = ele.value;
                }

                changeDutyAmnt(appValue, appFactor.substring(0, 21), riskcode);
                premDocal(riskcode);
                // 如果是计划则需要改变责任条款 add by guobin
                // 列表页改变计划后，保额联动 modify by cuishigang
                // 由于列表页与详细页的责任保额显示布局已经不同，所以增加此部分供列表页显示
                //只有列表页非推荐产品部分联动保额，推荐产品不随之联动
                if(appFactor.indexOf("_Plan") != -1)	{
                    var b = jQuery("[id=divRiskAppDuty_"+riskcode+"]")[1];
                    //var a = document.getElementById("divRiskAppDuty_"+riskcode).getElementsByTagName("div");
                    jQuery("[id=divRiskAppDuty_"+riskcode+"]:last").each(function(){
                        jQuery(this).find("div").each(function(){
                            jQuery(this).children().each(function(){
                                var spanId = jQuery(this).attr("id");
                                if(jQuery(this).attr("class")=="CDutyCol2"){
                                    //var Ids = spanId.split("_");
                                    //var sId = Ids[Ids.length-1];
                                    if(spanId!=null && spanId.substring(spanId.length-appValue.length - 1,spanId.length) == "_" + appValue){
                                        jQuery(this).show();
                                    }else{
                                        jQuery(this).hide(); //by
                                    }
                                }
                            });
                        });
                    });
                    // 详细页改变计划后，保额联动 modify by cuishigang
                    var divRiskAppDuty = document.getElementById("divRiskAppDuty_"+riskcode).getElementsByTagName("td");
                    for ( var i = 0; i < divRiskAppDuty.length; i++) {
                        if(divRiskAppDuty[i].className == 'CDutyCol2' && divRiskAppDuty[i].getElementsByTagName("span").length>0){
                            var CDutyCol2 = divRiskAppDuty[i].getElementsByTagName("span");
                            for(var j = 0; j < CDutyCol2.length; j++){
                                //转换判断方法为: 截取spanid最后appvalue.length长度的字符串与appValue比较看是否相等
                                var spanId = CDutyCol2[j].getAttribute("id");
                                //alert(spanId+"--"+spanId.substring(spanId.length-appValue.length,spanId.length)+"--"+appValue)
                                //var Ids = spanId.split("_");
                                //var sId = Ids[Ids.length-1];
                                if(spanId!=null&& spanId.substring(spanId.length-appValue.length - 1,spanId.length) == "_" + appValue ){
                                    CDutyCol2[j].style.display = "block";
                                }  else {
                                    CDutyCol2[j].style.display = "none";
                                }
                            }
                        }
                    }
                }
            }
            window.compRecal = function (riskcode) {
                var param = {};
                var selectedLi = jQuery(".bz_time .li_selected");
                var selectedTd = jQuery("#gh_tables td.CDutyCol2").find("select, span");
                var jAutoSelDay = jQuery(".cp_descon #UlDayBelongs > ul > li > span:not(#LiDayItemAuto)");
                var jDaySel = jQuery("#LiDayItemAuto");
                var plantemp = "";
                /**/
                selectedLi.each(function(i, v) {
                    var _key, _val;

                    if(jQuery(v).children().attr("id") == "LiDayItemAuto") {
                        var _nowNum = parseInt(jDaySel.text());
                        var maxDayScope = jQuery(jAutoSelDay[jAutoSelDay.length - 2]).text().slice(0, -1).split("-");
                        var selDayMax;

                        if(maxDayScope.length == 1) {
                            selDayMax = maxDayScope[0]
                        } else if (maxDayScope.length > 1) {
                            selDayMax = maxDayScope[1]
                        }

                        jAutoSelDay.each(function(i, u) {
                            if(i == jAutoSelDay.length - 1) return;

                            var dayScope = jQuery(u).text().slice(0, -1).split("-");

                            if((dayScope.length == 1 && _nowNum == dayScope[0]) || (dayScope.length > 1 && _nowNum >= dayScope[0] && _nowNum <= dayScope[1])) {
                                _val = jQuery(u).attr("name");
                            }
                        });

                        if (_nowNum > selDayMax) {
                            jDaySel.parent().removeClass("li_selected");
                            jDaySel.text("自主选择");
                            jAutoSelDay.eq(jAutoSelDay.length -1).parent().addClass("li_selected");
                            _val = jAutoSelDay.eq(jAutoSelDay.length -1).attr("name");
                        }
                    } else {
                        _val = jQuery(v).children().attr("name");
                    }

                    _key = jQuery(v).parent().attr("id");

                    param["factory_"+ _key +""] = _val;
                    if (_key.indexOf("Plan") > 0) {
                        plantemp = _val;
                    }
                });
                /**/
                param["productid"] = riskcode;
                param["complicatedFlag"] = complicatedFlag;
                var channelsn = "wj";
                var channel_sn = jQuery.cookie('channelSn');
                if(typeof(channel_sn)!="undefined" && channel_sn!=null && channel_sn!=""){
                    channelsn = channel_sn;
                }
                param["channelSn"] = channelsn;
                var duty = '';
                selectedTd.each(function(i, v) {
                    var _key = v.id.slice(0, v.id.indexOf("_"));
                    var _val;

                    if(v.nodeName == "SELECT") {
                        _val = jQuery(v).val();
                    } else if(v.nodeName == "SPAN") {
                        _val = jQuery(v).text();
                    }

                    if(_val.search(/不投保/) != -1) {
                        _val = 0;
                    } else if(_val.search(/万/) != -1) {
                        _val = parseFloat(_val)*10000;
                    } else if(_val.search(/元\/天/) != -1) {
                        _val = parseFloat(_val);
                        if (riskcode.indexOf("2100") >= 0) {
                            _val = _val * 180;
                        }

                    } else if(_val.search(/-/) != -1) {
                        return;
                    } else if(_val.search(/是/) != -1) {
                        _val = parseFloat(1);
                    } else if(_val.search(/否/) != -1) {
                        _val = parseFloat(0);
                    }else if(_val.search(/豁免保费/) != -1) {//豁免保费昆仑健康树使用：_val值！=0，判断轻症责任是否选择。
                        var temp = jQuery("#106501033001_01").text();
                        _val = parseFloat(temp)*10000;
		            }else if(_val.search(/投保/) != -1) {
                        // 百年康惠保
                        if (riskcode == "224801001") {
                            _val = parseFloat(jQuery("#224801001001_01").text())*10000;
                        }
                        // 昆仑健康保
                        else if (riskcode == "106501039") {
                            _val = parseFloat(jQuery("#106501039001_01").text())*10000;
                        }
                    }else if(isNaN(parseInt(_val.split("/")[0]))) {
                        _val = 0;
                    } else if(parseInt(_val.split("/")[0]) != -1) {
                        var var1 = parseInt(_val.split("/")[1]);
                        _val = parseFloat(_val.split("/")[0]);
                        if(_val == 500 && var1 == 6) {
                            _val = 500.0;
                        }
                    }

                    duty +=  _key + '-' + _val +',';
                });
                param["duty"] = duty.substring(0,duty.length-1) ;

		 // 屏蔽模拟责任点击时候的二次保费试算
                if(JSON.stringify(param) == JSON.stringify(this.tempParam)) return;
		
                jQuery.ajaxLoadImg.show('showid');
                $(".xj_table tbody").empty();
                jQuery.ajax({
                    type: "POST",
                    url: sinosoft.base + '/shop/aeonlife_cash_value!getAeonlifeCashValue.action',
                    data: param,
                    dataType: "json",
                    success: function(data){
                        jQuery.ajaxLoadImg.hide('showid');
                        if(data && data.status == '1'){
                            var cashValues = data.data;
                            var appendStr = "";
                            for(var i=0;i<cashValues.length;i++){
                                appendStr += "<tr>"+
                                    "<td>"+cashValues[i].policyYear+"</td>"+
                                    "<td>"+cashValues[i].age+"</td>"+
                                    "<td>"+cashValues[i].totalPremiums+"</td>"+
                                    "<td>"+cashValues[i].cashValue.val+"</td>"+
                                    "</tr>";
                            }
                            $(".xj_table tbody").append(appendStr);
                        } else if (data && data.status == '2') {
                            alert(data.msg);
                        }else{
                            alert('现金价值获取失败，请联系客服~');
                        }

                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        jQuery.ajaxLoadImg.hide('showid');
                    }
                });
                this.tempParam = param;
            }
            window.getDay = function(day) {
                if(day.length<=1){
                    return 0;
                }else{
                    return day.substring(0,day.length-1);
                }
            }
            window.getMaxDay = function (maxDay) {
                if(maxDay.indexOf("-") != -1){
                    var s=maxDay.split("-");
                    maxDay=s[1];
                }
                if(maxDay.substring(maxDay.length-1,maxDay.length)=="Y"){
                    var maxDayInt=parseInt(maxDay.replace("Y",""))*365;
                    maxDay=maxDayInt+"D";
                }
                return maxDay.substring(0,maxDay.length-1);
            }
            window.premDocal = function (riskcode) {


                var rootEle = document.getElementById("divRiskAppFactor_" + riskcode);
                var day=0;
                var maxDay=0;
                jQuery("#DayE").html('');
                var param = "prem_callback=?";
                if (rootEle != null) {
                    var dc = new DataCollection();
                    var ulNodeList = rootEle.getElementsByTagName("UL");
                    for ( var i = 0; i < ulNodeList.length; i++) {
                        for ( var j = 0; j < ulNodeList[i].childNodes.length; j++) {
                            if (ulNodeList[i].childNodes[j].className == "li_selected") {
                                if(ulNodeList[i].childNodes[j].firstChild.getAttribute("id")=="LiDayItemAuto"){
                                    day=parseInt(getDay(jQuery("#UlDayBelongs").find(".li_selected").children().html()));
                                    maxDay=parseInt(getMaxDay(jQuery("#UlDayBelongs").find(".li_selected").prev().children().attr("name")));
                                    if(day >= maxDay){
                                        jQuery("#UlDayBelongs").find(".li_selected").prev().children().click();

                                        jQuery("#LiDayItemAuto").html('自主选择');
                                        jQuery("#DayE").html('您选择的天数已超出范围，已帮您选择最大期限');
                                    }else{
                                        dc.add(ulNodeList[i].id, day+"@");
                                        jQuery("#DayE").html('');
                                        param += "&" + ulNodeList[i].id + "=" + (day+"@");
                                    }

                                }else{
                                    dc.add(ulNodeList[i].id, ulNodeList[i].childNodes[j].firstChild.getAttribute("name"));
                                    param += "&" + ulNodeList[i].id + "=" + encodeURI(ulNodeList[i].childNodes[j].firstChild.getAttribute("name"));
                                }
                                continue;
                            }
                        }
                    }
                    var selNodeList = rootEle.getElementsByTagName("SELECT");
                    for ( var i = 0; i < selNodeList.length; i++) {
                        dc.add(selNodeList[i].id, selNodeList[i].value);
                        param += "&" + selNodeList[i].id + "=" + encodeURI(selNodeList[i].value);
                    }

                    // 积分商城详细页面区分产品详细页面标记
                    if(jQuery("#pointproducttype").length!=0){
                        param += "&pointproducttype=" + jQuery("#pointproducttype").val() ;
                    }
                    if(!isNaN(day) && day <= maxDay ){
                        if (dc.size() > 0) {
                            dc.add("RiskCode", riskcode);
                            param += "&RiskCode=" + riskcode;
                            param += "&complicatedFlag=" + complicatedFlag;

                            var channelsn = "wj";
                            var channel_sn = jQuery.cookie('channelSn');
                            if(typeof(channel_sn)!="undefined" && channel_sn!=null && channel_sn!=""){
                                channelsn = channel_sn;
                            }
                            param += "&channelSn=" + channelsn;
                            jQuery.getJSON(sinosoft.base + '/shop/filter!premDoCal.action?' + param ,
                                function(data) {
                                    if(data && data.status == '1'){
                                        var prem = data.productPrem;
                                        var ratePrem = data.productRatePrem;
                                        // 现价
                                        if (prem != null && prem != "") {
                                            document.getElementById("productPrem_" + riskcode).innerHTML ="<em>￥</em>"+ prem;
                                        }
                                        // 原价
                                        if (ratePrem != null && ratePrem != "") {
                                            document.getElementById("productRatePrem_" + riskcode).innerHTML = ratePrem;
                                        }
                                        // 积分
                                        var integral = data.productIntegral;
                                        if (integral != null && integral != "") {
                                            if('true'==data.pointDesFlag&&data.pointDes!=''){
                                                jQuery("#pointdes").html(data.pointDes);
                                            }else{
                                                var desc="<span id=\"pointtitle_"+riskcode+"\"></span>您将获得&nbsp;<span id=\"productIntegral_"+riskcode+"\">"+integral+"</span>&nbsp;个积分";
                                                //document.getElementById("productIntegral_" + riskcode).innerHTML = integral;
                                                jQuery("#pointdes").html(desc);
                                            }
                                        }
                                        //最多抵扣金额
                                        var IntePointAmountgral = data.IntePointAmountgral+"";
                                        if (IntePointAmountgral != null && IntePointAmountgral != "" && parseFloat(IntePointAmountgral)!=0) {
                                            if(document.getElementById("maxIntegralPrice_" + riskcode)&&document.getElementById("maxIntegralPrice_" + riskcode)!=null){
                                                if(data.loginFlag=='false'){
                                                    document.getElementById("maxIntegralPrice_" + riskcode).innerHTML = "<i class=\"jf_icons\">&nbsp;</i><span>积分最多可抵<em>¥ "+IntePointAmountgral+"</em></span>";
                                                }else{
                                                    document.getElementById("maxIntegralPrice_" + riskcode).innerHTML = "<i class=\"jf_icons\">&nbsp;</i><span>您有积分<em>"+data.MemberAmountgral+"</em> 本次可抵<em>¥ "+IntePointAmountgral+"</em></span>";
                                                }
                                            }
                                        }
                                    } // 积分商城详细页面
                                    else if (data && data.status == '3') {
                                        var prem = data.productPrem;
                                        if (prem != null && prem != "") {
                                            document.getElementById("productPrem_" + riskcode).innerHTML =  prem + "积分";
                                            document.getElementById("productLashNum_" + riskcode).innerHTML =  data.productLashNum + "个";
                                        }
                                    }
                                });

                        }
                    }
                }
            }
            window.changeAppFactorStyle = function (DocElements, curElement) {
                for ( var i = 0; i < DocElements.length; i++) {
                    DocElements[i].className = "";
                }
                curElement.parentNode.className = "li_selected";
            }
            window.changeDutyAmnt = function (appFactorValue, appFactorCode, cRiskCode) {
                var rootNodeEle = document.getElementById("divRiskAppDuty_" + cRiskCode);
                if (rootNodeEle) {
                    var dutyEles = rootNodeEle.getElementsByTagName("span");
                    for ( var i = 1; i < dutyEles.length; i++) {
                        if (dutyEles[i].id.substring(21, 42) == appFactorCode) {
                            dutyEles[i].style.display = "none";
                            if (dutyEles[i].id.substring(42) == appFactorValue) {
                                dutyEles[i].style.display = "";
                            }
                        }
                    }
                }
            }
            window.getInsuredAge = function (_strBrithday){
                var age;
                var brith;
                if(_strBrithday.match(/^\d{4}[\-\/\s+]\d{1,2}[\-\/\s+]\d{1,2}$/)){
                    brith= new Date(_strBrithday.replace(/[\-\/\s+]/g,'/'));
                }else if(_strBrithday.match(/^\d{8}$/)){
                    brith= new Date(_strBrithday.substring(0,4)+'/'+_strBrithday.substring(4,6)+'/'+_strBrithday.substring(6));
                }else{
                    alert('生日格式错误！');
                    return -1;
                }
                var aDate=new Date();
                var thisYear=aDate.getFullYear();
                var thisMonth=aDate.getMonth()+1;
                var thisDay=aDate.getDate();

                var brithy = brith.getFullYear();
                var brithm = brith.getMonth();
                var brithd = brith.getDate();
                if(thisYear-brithy<0){
                    age= -1;
                }else{
                    if(thisMonth-brithm<0) {
                        age = thisYear-brithy-1;
                    } else {
                        if(thisDay-brithd>=0) {
                            age = thisYear-brithy;
                        } else {
                            age = thisYear-brithy-1;
                        }
                    }
                }
                return age;
            }
        }
    }
});