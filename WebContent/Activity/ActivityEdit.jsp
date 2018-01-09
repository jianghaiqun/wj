<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp" %>
<%@ taglib uri="controls" prefix="z" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title></title>
    <link href="../Include/Default.css" rel="stylesheet" type="text/css"/>
    <script src="../Framework/Main.js"></script>
    <script type="text/javascript" src="../template/common/js/jquery.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript">
        var num = 1;
        var vipBigest = 1;
        jQuery(document).ready(function () {
            var startDate = jQuery('#startDate').val();
            var autoCreate = jQuery("#autoCreate").val();
            var type = jQuery("#type").val();
            var remindChose_check = jQuery('#remindChose_check').val();
            var ActivityChannel = jQuery('#ActivityChannel').val();
            if (remindChose_check == '0') {
                jQuery("#remindDateTime").show();
                jQuery("#remindDateTimeChose").show()
                jQuery("#remindDate").show();
                jQuery("#remindTime").show();
            } else {
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            }
            if (autoCreate == '0') {
                jQuery("#couponnum_td").hide();
                jQuery("#couponnumtd").hide();

            } else {
                jQuery("#couponnum_td").show();
                jQuery("#couponnumtd").show();
            }
            var type = jQuery("#type").val();
            jQuery("#checkvip_table").hide();
            jQuery("#payAmount_table").hide();
            if (type == '0' || type == '4' || type == '5') {
                jQuery("#company_table").hide();
                jQuery("#riskcode_table").hide();
                jQuery("#product_table").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnumtd").hide();
                jQuery("#autoCreate_td").hide();
                jQuery("#autoCreate_other").hide();
                jQuery("#add_button_td").hide();
                jQuery("#yh_talbe").hide();
                jQuery("#accumulation_checkbox_td").hide();

                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").hide();
                jQuery("#memberChannel_input").hide();
                jQuery("#checkviptd").hide();
            } else if (type == "3") {
                jQuery("#batch_table").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#prop1_td").show();
                jQuery("#prop1_0").show();
                jQuery("#yh_talbe").show();
                jQuery("#accumulation_checkbox_td").show();
                var accumulation = jQuery("#accumulation").val();
                if (accumulation == '0') {
                    jQuery("#accumulation_checkbox").attr("checked", true);
                }
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#memberChannel_input").show();
                if (jQuery("#memberChannelHidden").val() == 'Y') {
                    jQuery("#memberChannel").attr("checked", true);
                }
                if (jQuery('#memberChannel').attr('checked')) {
                    jQuery("#checkviptd").show();
                }
            } else if (type == "8") {
                jQuery("#batch_table").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#prop1_td").show();
                jQuery("#prop1_0").show();
                jQuery("#yh_talbe").show();
                jQuery("#accumulation_checkbox_td").show();
                var accumulation = jQuery("#accumulation").val();
                if (accumulation == '0') {
                    jQuery("#accumulation_checkbox").attr("checked", true);
                }
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#prop1_td").hide();
                jQuery("#prop1_0").hide();
                jQuery("#prop1_0").attr("value", "");
                //if (ActivityChannel != 'tb') {
                // jQuery("#memberChannel_input").show();
                //}else{
                jQuery("#memberChannel_input").hide();
                //}
                if (jQuery("#memberChannelHidden").val() == 'Y') {
                    jQuery("#memberChannel").attr("checked", true);
                }
                if (jQuery('#memberChannel').attr('checked')) {
                    jQuery("#checkviptd").show();
                    jQuery("#add_button_td").show();
                } else {
                    jQuery("#checkviptd").hide();
                    jQuery("#add_button_td").hide();
                }
                jQuery("#remindChoseShow").show();
            } else if (type == "6") {
                jQuery("#batch_table").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#prop1_td").hide();
                jQuery("#prop1_0").hide();
                jQuery("#yh_talbe").show();
                jQuery("#accumulation_checkbox_td").hide();
                var accumulation = jQuery("#accumulation").val();
                if (accumulation == '0') {
                    jQuery("#accumulation_checkbox").attr("checked", true);
                }
                jQuery("#discountrate_td").show();
                jQuery("#discountrate_input").show();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                if (ActivityChannel != 'tb') {
                    jQuery("#memberChannel_input").show();
                } else {
                    jQuery("#memberChannel_input").hide();
                }
                if (jQuery("#memberChannelHidden").val() == 'Y') {
                    jQuery("#memberChannel").attr("checked", true);
                }
                if (jQuery('#memberChannel').attr('checked')) {
                    jQuery("#checkviptd").show();
                    jQuery("#add_button_td").show();
                } else {
                    jQuery("#checkviptd").hide();
                    jQuery("#add_button_td").hide();
                }
                jQuery("#remindChoseShow").show();
            } else if (type == "7") {
                jQuery("#batch_table").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#prop1_td").hide();
                jQuery("#prop1_0").hide();
                jQuery("#yh_talbe").show();
                jQuery("#accumulation_checkbox_td").hide();
                var accumulation = jQuery("#accumulation").val();
                if (accumulation == '0') {
                    jQuery("#accumulation_checkbox").attr("checked", true);
                }
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").show();
                jQuery("#multiplenum_input").show();
                jQuery("#channel_table").show();
                if (ActivityChannel != 'tb') {
                    jQuery("#memberChannel_input").show();
                } else {
                    jQuery("#memberChannel_input").hide();
                }
                if (jQuery("#memberChannelHidden").val() == 'Y') {
                    jQuery("#memberChannel").attr("checked", true);
                }
                if (jQuery('#memberChannel').attr('checked')) {
                    jQuery("#checkviptd").show();
                    jQuery("#add_button_td").show();
                } else {
                    jQuery("#checkviptd").hide();
                    jQuery("#add_button_td").hide();
                }

            } else if (type == "2") {
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#yh_talbe").hide();
                jQuery("#memberChannel_input").hide();
                jQuery("#checkviptd").hide();
            } else if (type == "10") {
                //jQuery("#GroupbuyWhether").val("0");
                if (jQuery("#GroupbuyWhether").val() == "0") {
                    document.getElementById("GroupbuyWhether").disabled = true;//不参加团购
                }
                jQuery("#batch_table").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#prop1_td").show();
                jQuery("#prop1_td").html('送话费');
                jQuery("#prop1_0").show();
                jQuery("#yh_talbe").show();
                jQuery("#accumulation_checkbox_td").show();
                var accumulation = jQuery("#accumulation").val();
                if (accumulation == '0') {
                    jQuery("#accumulation_checkbox").attr("checked", true);
                }
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#memberChannel_input").show();
                if (jQuery("#memberChannelHidden").val() == 'Y') {
                    jQuery("#memberChannel").attr("checked", true);
                }
                if (jQuery('#memberChannel').attr('checked')) {
                    jQuery("#checkviptd").show();
                }
            } else if (type == "11") {
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#channel_table").show();
                jQuery("#yh_talbe").hide();
                jQuery("#memberChannel_input").hide();
                jQuery("#add_button_td").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#payAmount_table").show();
                jQuery("#checkvip_table").show();
            } else if (type == "12") {
                jQuery("#batch_table").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#yh_talbe").hide();
                jQuery("#trActivityRole").show();
                jQuery("#trRewardType").show();
                jQuery("#recommend_batch_table").show();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
            } else {
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#yh_talbe").show();
                jQuery("#memberChannel_input").hide();
                jQuery("#checkviptd").hide();
            }
            if (type == "0" || type == "2" || type == "4" || type == '5' || type == '6' || type == '7') {
                jQuery("#payAmount_td").hide();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
            }
            //加载价格梯度
            var sb_payamount = jQuery("#sb_payamount").val();
            var sb_payamount_B = jQuery("#sb_payamount_B").val();
            var sb_prop1 = jQuery("#sb_prop1").val();
            var array_payamount;
            var array_payamount_B;
            var array_prop1;
            // 适用会员级别
            var sb_memberRule = jQuery("#sb_memberRule").val();
            var array_memberRule;
            if (sb_payamount != '' && sb_payamount != null) {
                array_payamount = sb_payamount.split(",");
                if (sb_prop1 != '' && sb_prop1 != null) {
                    array_prop1 = sb_prop1.split(",");
                }
                if (sb_payamount_B != '' && sb_payamount_B != null) {
                    array_payamount_B = sb_payamount_B.split(",");
                }

                for (var i = 0; i < array_payamount.length; i++) {
                    if (i != 0) {
                        addtable();
                    }
                    jQuery("#payAmount_" + i).val(array_payamount[i]);
                    jQuery("#payAmountB_" + i).val(array_payamount_B[i]);
                    if (sb_prop1 != '' && sb_prop1 != null) {
                        jQuery("#prop1_" + i).val(array_prop1[i]);
                    }
                }
            }


            var sb_discountrate = jQuery("#sb_discountrate").val();
            var sb_multiplenum = jQuery("#sb_multiplenum").val();
            var array_discountrate;
            var array_multiplenum;
            if (sb_discountrate != '' && sb_discountrate != null) {
                array_discountrate = sb_discountrate.split(",");
                for (var i = 0; i < array_discountrate.length; i++) {
                    if (i != 0) {
                        addtable();
                    }
                    if (i == 0) {
                        jQuery("#discountrate").val(array_discountrate[i]);
                    } else {
                        jQuery("#discountrate_" + i).val(array_discountrate[i]);
                    }
                }
            }

            if (sb_multiplenum != '' && sb_multiplenum != null) {
                array_multiplenum = sb_multiplenum.split(",");
                for (var i = 0; i < array_multiplenum.length; i++) {
                    addtable();
                }
            }

            if (sb_memberRule != '' && sb_memberRule != null) {
                array_memberRule = sb_memberRule.split(";");
            }
            // 会员级别 checkbox
            if (sb_memberRule != '' && sb_memberRule != null) {
                for (var i = 0; i < array_memberRule.length; i++) {
                    jQuery("input[type=checkbox][name^='checkvip_" + i + "']").each(function () {
                        if (null != array_memberRule[i] && "" != array_memberRule[i]) {
                            if (array_memberRule[i].indexOf(jQuery(this).val()) > -1) {
                                jQuery(this).attr("checked", "true");
                            }
                        }
                    });
                }
            }

            var ActivityChannel = jQuery('#ActivityChannel').val();
            if (ActivityChannel == 'tb') {
                jQuery("#company_table").hide();
                jQuery("#riskcode_table").hide();

            }
            //初始化团购
            var GroupbuyWhether = jQuery("#GroupbuyWhether").val();
            if (GroupbuyWhether == "0") {
                jQuery("#GroupbuyNum_td").hide();
                jQuery("#GroupbuyNum").hide();
                jQuery("#GroupbuyNum").val('');
            } else {
                jQuery("#GroupbuyNum_td").show();
                jQuery("#GroupbuyNum").show();
            }

        });

        //各个批次优惠券信息列表
        function getCouponBatchList() {
            var couponBatchother = jQuery("#batchother").val();
            var couponBatch = jQuery("#batch").val();
            var diag = new Dialog("Diag2");
            diag.Width = 650;
            diag.Height = 370;
            diag.Title = "各批次优惠券列表";
            diag.URL = "Activity/CouponBatchDialog.jsp?couponBatchother=" + couponBatchother + "&couponBatch=" + couponBatch;
            diag.OKEvent = choosecouponbatch;
            diag.show();
            diag.addButton("appendinsuranceCompany", "选择", chooseCouponBatch);
            diag.CancelButton.value = "取消";
        }
        var recommendFlag = 1;
        function getCouponBatchList(param) {
            var couponBatchother;
            var couponBatch;
            if (param == 'recommend') {
                recommendFlag = 1;
                couponBatchother = jQuery("#recommend_batch").val();
                couponBatch = jQuery("#recommendBatch").val();
            } else if (param == 'recommended') {
                recommendFlag = 2;
                couponBatchother = jQuery("#recommended_batch").val();
                couponBatch = jQuery("#recommendedBatch").val();
            }
            var diag = new Dialog("Diag2");
            diag.Width = 650;
            diag.Height = 370;
            diag.Title = "各批次优惠券列表";
            diag.URL = "Activity/CouponBatchDialog.jsp?couponBatchother=" + couponBatchother + "&couponBatch=" + couponBatch;
            diag.OKEvent = choosecouponbatch;
            diag.show();
            diag.addButton("appendinsuranceCompany", "选择", chooseCouponBatch);
            diag.CancelButton.value = "取消";
        }

        //确定优惠券批次
        function choosecouponbatch() {
            var couponBatch2other = $DW.$V("couponBatch2other");
            var couponBatch2 = $DW.$V("couponBatch2");
            var type = jQuery("#type").val();
            if (type == "12") {
                if (recommendFlag == 1) {
                    jQuery("#recommend_batch").val(couponBatch2other);
                    jQuery("#recommendBatch").val(couponBatch2other);
                } else if (recommendFlag == 2) {
                    jQuery("#recommended_batch").val(couponBatch2other);
                    jQuery("#recommendedBatch").val(couponBatch2other);
                }
            } else {
                jQuery("#batchother").val(couponBatch2other);
                jQuery("#batch").val(couponBatch2other);
            }
            $D.close();
        }

        //选择优惠券批次
        function chooseCouponBatch() {
            var arr = $DW.DataGrid.getSelectedValue("dg2");
            if (!arr || arr.length == 0) {
                //Dialog.alert("您还没有选择批次！");
                //return false;
                $DW.$S("couponBatch2other", "");
                $DW.$S("couponBatch2", "");
            } else {
                var batch = $DW.DataGrid.getSelectedValueByColumns("dg2", "batch");
                $DW.$S("couponBatch2other", batch);
                $DW.$S("couponBatch2", arr.join());
            }
        }

        //产品列表
        function getProductList() {
            var ActivityChannel = jQuery('#ActivityChannel').val();
            if (ActivityChannel == 'tb') {
                getTBProductList();
            } else {
                getOthProductList();
            }
        }

        //产品列表
        function getOthProductList() {
            var productother = jQuery("#productother").val();
            var product = jQuery("#product").val();
            var diag = new Dialog("Diag2");
            diag.Width = 550;
            diag.Height = 370;
            diag.Title = "产品列表";
            diag.URL = "Activity/ProductDialog.jsp";
            diag.OKEvent = chooseproduct;
            diag.show();
            diag.addButton("appendriskCode", "追加", appendProduct);
            diag.addButton("appendriskCode", "选择", chooseProduct);
            diag.CancelButton.value = "取消";
            var producttimeoutID = setTimeout(function () {
                $DW.$S("product2", product);
                $DW.$S("product2other", productother);
                var riskcodeArray = product.split(",");
                for (var i = 0; i < riskcodeArray.length; i++) {
                    $DW.DataGrid.select($DW.$("dg2"), riskcodeArray[i]);
                }
                clearTimeout(producttimeoutID);
            }, 1000);
        }

        //产品列表
        function getTBProductList() {
            var productother = jQuery("#productother").val();
            var product = jQuery("#product").val();
            var diag = new Dialog("Diag4");
            diag.Width = 810;
            diag.Height = 370;
            diag.Title = "产品列表";
            diag.URL = "Activity/TBProductDialog.jsp";
            diag.OKEvent = chooseproduct;
            diag.show();
            diag.addButton("appendriskCode", "追加", appendProduct);
            diag.addButton("appendriskCode", "选择", chooseProduct);
            diag.CancelButton.value = "取消";
            var producttimeoutID = setTimeout(function () {
                $DW.$S("product2", product);
                $DW.$S("product2other", productother);
                var riskcodeArray = product.split(",");
                for (var i = 0; i < riskcodeArray.length; i++) {
                    $DW.DataGrid.select($DW.$("dg4"), riskcodeArray[i]);
                }
                clearTimeout(producttimeoutID);
            }, 3000);
        }

        //选择产品
        function chooseProduct() {
            var arr;
            var ActivityChannel = jQuery('#ActivityChannel').val();
            if (ActivityChannel == 'tb') {
                arr = $DW.DataGrid.getSelectedValue("dg4");
            } else {
                arr = $DW.DataGrid.getSelectedValue("dg2");
            }
            if (!arr || arr.length == 0) {
                //Dialog.alert("您还没有选择险种！");
                //return false;
                $DW.$S("product2other", "");
                $DW.$S("product2", "");
            } else {
                if (ActivityChannel == 'tb') {
                    var CustomCode = $DW.DataGrid.getSelectedValueByColumns("dg4", "id");
                    $DW.$S("product2other", CustomCode);
                } else {
                    var productname = $DW.DataGrid.getSelectedValueByColumns("dg2", "productname");
                    $DW.$S("product2other", productname);
                }
                $DW.$S("product2", arr.join());
            }
        }

        //确定产品
        function chooseproduct() {
            var product2other = $DW.$V("product2other");
            var product2 = $DW.$V("product2");
            jQuery("#productother").val(product2other);
            jQuery("#product").val(product2);
            $D.close();
        }

        //产品追加
        function appendProduct() {
            var arr;
            var ActivityChannel = jQuery('#ActivityChannel').val();
            if (ActivityChannel == 'tb') {
                var CustomCode = $DW.DataGrid.getSelectedValueByColumns("dg4", "id");
                publicAppend("product2other", CustomCode);
                arr = $DW.DataGrid.getSelectedValue("dg4");
            } else {
                var productname = $DW.DataGrid.getSelectedValueByColumns("dg2", "productname");
                publicAppend("product2other", productname);
                arr = $DW.DataGrid.getSelectedValue("dg2");
            }
            publicAppend("product2", arr.join());
        }

        //险种列表
        function getRiskCodeList() {
            var riskCodeother = jQuery("#riskCodeother").val();
            var riskCode = jQuery("#riskCode").val();
            var diag = new Dialog("Diag3");
            diag.Width = 550;
            diag.Height = 370;
            diag.Title = "险种列表";
            diag.URL = "Activity/RiskCodeDialog.jsp";
            diag.OKEvent = chooseriskcode;
            diag.show();
            diag.addButton("appendriskCode", "追加", appendRiskCode);
            diag.addButton("appendriskCode", "选择", chooseRiskCode);
            diag.CancelButton.value = "取消";
            var producttimeoutID = setTimeout(function () {
                $DW.$S("riskCode2", riskCode);
                $DW.$S("riskCode2other", riskCodeother);
                var riskcodeArray = riskCode.split(",");
                for (var i = 0; i < riskcodeArray.length; i++) {
                    $DW.DataGrid.select($DW.$("dg2"), riskcodeArray[i]);
                }
                clearTimeout(producttimeoutID);
            }, 1000);
        }

        //保险公司列表
        function getInsuranceCompanyList() {
            var insuranceCompanyother = jQuery("#insuranceCompanyother").val();
            var insuranceCompany = jQuery("#insuranceCompany").val();
            var diag = new Dialog("Diag4");
            diag.Width = 550;
            diag.Height = 370;
            diag.Title = "保险公司列表";
            diag.URL = "Activity/InsuranceCompanyDialog.jsp";
            diag.OKEvent = chooseinsurancecompany;
            diag.show();
            diag.addButton("appendinsuranceCompany", "追加", appendInsuranceCompany);
            diag.addButton("appendinsuranceCompany", "选择", chooseInsuranceCompany);
            diag.CancelButton.value = "取消";
            var producttimeoutID = setTimeout(function () {
                $DW.$S("insuranceCompany2", insuranceCompany);
                $DW.$S("insuranceCompany2other", insuranceCompanyother);
                var riskcodeArray = insuranceCompany.split(",");
                for (var i = 0; i < riskcodeArray.length; i++) {
                    $DW.DataGrid.select($DW.$("dg2"), riskcodeArray[i]);
                }
                clearTimeout(producttimeoutID);
            }, 1000);
        }

        //选择险种
        function chooseRiskCode() {
            var arr = $DW.DataGrid.getSelectedValue("dg2");
            if (!arr || arr.length == 0) {
                //Dialog.alert("您还没有选择用户！");
                //return false;
                $DW.$S("riskCode2other", "");
                $DW.$S("riskCode2", "");
            } else {
                var codename = $DW.DataGrid.getSelectedValueByColumns("dg2", "codename");
                $DW.$S("riskCode2other", codename);
                $DW.$S("riskCode2", arr.join());
            }
        }

        //选择保险公司
        function chooseInsuranceCompany() {
            var arr = $DW.DataGrid.getSelectedValue("dg2");
            if (!arr || arr.length == 0) {
                //Dialog.alert("您还没有选择用户！");
                //return false;
                $DW.$S("insuranceCompany2other", "");
                $DW.$S("insuranceCompany2", "");
            } else {
                var codename = $DW.DataGrid.getSelectedValueByColumns("dg2", "codename");
                $DW.$S("insuranceCompany2other", codename);
                $DW.$S("insuranceCompany2", arr.join());
            }
        }

        //险种追加
        function appendRiskCode() {
            var arr = $DW.DataGrid.getSelectedValue("dg2");
            var codename = $DW.DataGrid.getSelectedValueByColumns("dg2", "codename");
            publicAppend("riskCode2", arr.join());
            publicAppend("riskCode2other", codename);
        }

        //保险公司追加
        function appendInsuranceCompany() {
            var arr = $DW.DataGrid.getSelectedValue("dg2");
            var codename = $DW.DataGrid.getSelectedValueByColumns("dg2", "codename");
            publicAppend("insuranceCompany2", arr.join());
            publicAppend("insuranceCompany2other", codename);
        }

        function publicAppend(name, str) {
            if (!str || str.length == 0) {
                Dialog.alert("您还没有选择！");
                return false;
            } else {
                var provideUser = $DW.$V(name);
                if (provideUser == "" || provideUser == null) {
                    $DW.$S(name, str);
                    //$D.close();
                } else {
                    var userarray = str.split(",");
                    var str = "";
                    for (var i = 0; i < userarray.length; i++) {
                        if ((provideUser + ",").indexOf(userarray[i] + ",") == -1) {
                            str += userarray[i] + ",";
                        }
                    }
                    if (str != "") {
                        $DW.$S(name, provideUser + "," + str.substring(0, str.length - 1));
                    }
                }
            }
        }

        //确定险种
        function chooseriskcode() {
            var riskCode2other = $DW.$V("riskCode2other");
            var riskCode2 = $DW.$V("riskCode2");
            jQuery("#riskCodeother").val(riskCode2other);
            jQuery("#riskCode").val(riskCode2);
            $D.close();
        }

        //确定保险公司
        function chooseinsurancecompany() {
            var insuranceCompany2other = $DW.$V("insuranceCompany2other");
            var insuranceCompany2 = $DW.$V("insuranceCompany2");
            jQuery("#insuranceCompanyother").val(insuranceCompany2other);
            jQuery("#insuranceCompany").val(insuranceCompany2);
            $D.close();
        }

        //根据自动获取活动方式隐藏优惠券数量
        function hidecouponnum() {
            var autoCreate = jQuery("#autoCreate").val();
            if (autoCreate == '0') {
                jQuery("#couponnum").attr("value", "");
                jQuery("#couponnum_td").hide();
                jQuery("#couponnumtd").hide();

            } else {
                jQuery("#couponnum").attr("value", "");
                jQuery("#couponnum_td").show();
                jQuery("#couponnumtd").show();
            }
        }

        /**
         * 是否可累计
         */
        function checkAccumulation() {
            var check_flag = jQuery('#accumulation_checkbox').is(':checked');
            if (check_flag == true) {
                jQuery("#accumulation").attr("value", "0");
            } else {
                jQuery("#accumulation").attr("value", "1");
            }
        }

        function addtable() {
            var type = jQuery("#type").val();
            var tablestr = "<table id=\"yh_talbe_add\">"
                + "<tr>"
                + "<td id=\"payAmount_td\" height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\">消&nbsp;&nbsp;&nbsp;费&nbsp;&nbsp;&nbsp;区&nbsp;&nbsp;&nbsp;间：</td>"
                + "<td ><input value=\"\" type=\"text\" id=\"payAmount_" + num + "\" name=\"payAmount_" + num + "\" ztype=\"String\"  class=\"inputText\" size=\"10\" verify=\"消费金额|NUM\">&nbsp;<input value=\"\" type=\"text\" id=\"payAmountB_" + num + "\" name=\"payAmountB_" + num + "\" ztype=\"String\"  class=\"inputText\" size=\"10\" verify=\"消费金额|NUM\"></td>"
                + "<td id=\"prop1_td\" height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\" >优惠金额：</td>"
                + "<td ><input value=\"\" type=\"text\" id=\"prop1_" + num + "\" name=\"prop1_" + num + "\" ztype=\"String\"  class=\"inputText\" size=\"10\" verify=\"优惠金额|NUM\" ></td>";
            if (jQuery('#memberChannel').attr('checked')) {
                tablestr = tablestr + "<td id=\"checkviptd_add" + num + "\">"
                    + "<input type=\"checkbox\" name=\"checkvip_" + num + "\" value=\"K0\"/>普通会员"
                    + "<input type=\"checkbox\" name=\"checkvip_" + num + "\" value=\"K1\"/>k1会员"
                    + "<input type=\"checkbox\" name=\"checkvip_" + num + "\" value=\"K2\"/>k2会员"
                    + "<input type=\"checkbox\" name=\"checkvip_" + num + "\" value=\"VIP\"/>vip会员"
                    + "</td>";
            }
            tablestr = tablestr + "<td ><input type=\"button\" id=\"\" value=\"删除\" onclick=\"deleterow(this);\"></td>"
                + "<td ></td>"
                + "</tr>"
                + "</table>";
            //jQuery("#time_talbe").before(tablestr);
            var prop1_td_use = "prop1_" + num;
            var del = "<td id=\"payAmount_td\" height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\">消&nbsp;&nbsp;&nbsp;费&nbsp;&nbsp;&nbsp;区&nbsp;&nbsp;&nbsp;间：</td>"
                + "<td ><input value=\"\" type=\"text\" id=\"payAmount_" + num + "\" name=\"payAmount_" + num + "\" ztype=\"String\"  class=\"inputText\" size=\"10\" verify=\"消费金额|NUM\">&nbsp;<input value=\"\" type=\"text\" id=\"payAmountB_" + num + "\" name=\"payAmountB_" + num + "\" ztype=\"String\"  class=\"inputText\" size=\"10\" verify=\"消费金额|NUM\"></td>"
                + "<td id=\"prop1_td\" height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\" >优惠金额：</td>"
                + "<td ><input value=\"\" type=\"text\" id=\"prop1_" + num + "\" name=\"prop1_" + num + "\" ztype=\"String\"  class=\"inputText\" size=\"10\" verify=\"优惠金额|NUM\" ></td>";
            var adddiscountrate = "";
            if (type == "10") {
                tablestr = tablestr.replace("优惠金额", "送话费");
            }
            if (type == '6') {
                if (vipBigest == 4) {
                    Dialog.alert("目前会员只有四个等级喔！");
                    return;
                }
                adddiscountrate = "<td id=\"discountrate_td\" height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\">折&nbsp;&nbsp;&nbsp;扣&nbsp;&nbsp;&nbsp;&nbsp;率:&nbsp;&nbsp;</td>"
                    + "<td ><input value=\"\" type=\"text\" id=\"discountrate_" + num + "\" name=\"discountrate_" + num + "\"   class=\"inputText\" verify=\"折扣率|PositiveNumber&NotNull\" >"
                    + "<span style=\"color:red;padding-left:2px;padding-top:13px;\" ztype=\"Verify\">*&nbsp;&nbsp;</span></td>";
                tablestr = tablestr.replace(del, adddiscountrate);
                vipBigest++;
            }
            if (type == '7') {
                if (vipBigest == 4) {
                    Dialog.alert("目前会员只有四个等级喔！");
                    return;
                }
                var selectOption = jQuery('#selectOption').val();
                var selectOptionStr = selectOptionToStr(selectOption);
                adddiscountrate = "<td id=\"multiplenum_td\" height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\">积&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;倍&nbsp;&nbsp;&nbsp;数：</td>"
                    //+"<td ><input value=\"\" type=\"text\" id=\"multiplenum_"+num+"\" name=\"multiplenum_"+num+"\"   class=\"inputText\" verify=\"积分倍数|NotNull\" > "
                    + "<td>" + "<select style=\"width:120px;\" id=\"multiplenum_" + num + "\" name=\"multiplenum_" + num + "\" verify=\"积分倍数|NotNull\" >" + selectOptionStr + "</select>"
                    + "<span style=\"color:red;padding-left:2px;padding-top:13px;\" ztype=\"Verify\">*&nbsp;&nbsp;</span></td>";
                tablestr = tablestr.replace(del, adddiscountrate);
                vipBigest++;
                // selected = selected"
            }
            if (type == '8') {
                if (vipBigest == 4) {
                    Dialog.alert("目前会员只有四个等级喔！");
                    return;
                }
                adddiscountrate = "<td id=\"payAmount_td\" height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\">消&nbsp;&nbsp;&nbsp;费&nbsp;&nbsp;&nbsp;区&nbsp;&nbsp;&nbsp;间：</td>"
                    + "<td ><input value=\"\" type=\"text\" id=\"payAmount_" + num + "\" name=\"payAmount_" + num + "\" ztype=\"String\"  class=\"inputText\" size=\"10\" verify=\"消费金额|NUM\">&nbsp;<input value=\"\" type=\"text\" id=\"payAmountB_" + num + "\" name=\"payAmountB_" + num + "\" ztype=\"String\"  class=\"inputText\" size=\"10\" verify=\"消费金额|NUM\"></td>";
                +"<span style=\"color:red;padding-left:2px;padding-top:13px;\" ztype=\"Verify\">*&nbsp;&nbsp;</span></td>";
                tablestr = tablestr.replace(del, adddiscountrate);
                vipBigest++;
            }
            if (jQuery("#type").val() == "10") {
                tablestr = tablestr.replace("优惠金额", "送话费");
            }
            jQuery("#add_table_div").append(tablestr);
            num++;
            jQuery("#num").val(num);
            if (parseInt(num) == 1) {
                jQuery("#accumulation_checkbox_td").show();
            } else {
                jQuery("#accumulation_checkbox_td").hide();
            }
        }

        function deleterow(t) {
            t.parentNode.parentNode.parentNode.parentNode.parentNode.removeChild(t.parentNode.parentNode.parentNode.parentNode);
            deleterow2()
        }

        function deleterow2() {
            num--;
            jQuery("#num").val(num);
            if (parseInt(num) == 1) {
                jQuery("#accumulation_checkbox_td").show();
            } else {
                jQuery("#accumulation_checkbox_td").hide();
            }
            var type = jQuery("#type").val();
            if (type == '8' || type == '7' || type == '6') {
                vipBigest--;
            }
        }

        function ChooseGroupbuy() {
            var GroupbuyWhether = jQuery("#GroupbuyWhether").val();
            if (GroupbuyWhether == "0") {
                jQuery("#GroupbuyNum_td").hide();
                jQuery("#GroupbuyNum").hide();
                jQuery("#GroupbuyNum").val('');
            } else {
                jQuery("#GroupbuyNum").val('');
                jQuery("#GroupbuyNum_td").show();
                jQuery("#GroupbuyNum").show();
            }
        }

        function memberChannelOn() {
            var type = jQuery("#type").val();
            jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
            if (jQuery('#memberChannel').attr('checked')) {
                jQuery("#checkviptd").show();
                if (type == "6" || type == "7" || type == "8") {
                    jQuery("#add_button_td").show();
                }
            } else {
                if (type == "6" || type == "7" || type == "8") {
                    jQuery("#add_button_td").hide();
                    jQuery("td[id^='checkviptd']").each(function () {
                        jQuery("#" + this.id).hide();
                        jQuery("#yh_talbe_add").remove();
                        deleterow2();
                        vipBigest = 1;
                    });
                } else {
                    jQuery("td[id^='checkviptd']").each(function () {
                        jQuery("#" + this.id).hide();
                    });
                }
            }
        }


        function selectOptionToStr(selectOptionStr) {
            var sb_multiplenumArray = jQuery("#sb_multiplenum").val().split(","); //选中
            var selectOptionToStrX = "";
            var selectOptionArray = selectOptionStr.split(",");
            var selected = "selected = \"selected\"";
            for (var i = 0; i < selectOptionArray.length; i++) {
                if (sb_multiplenumArray[0] == selectOptionArray[i]) {
                    selected = "selected = \"selected\"";
                } else {
                    selected = "";
                }
                selectOptionToStrX = selectOptionToStrX + "<option  value=\" " + selectOptionArray[i] + "\" " + selected + " >" + selectOptionArray[i + 1] + "</option>";
                i++;
            }
            sb_multiplenumArray.shift();//删除数组第一个元素
            jQuery("#sb_multiplenum").val(sb_multiplenumArray)
            return selectOptionToStrX;
        }

        //是否提醒选择提醒日期
        function remindChoseShowTime() {
            var remindChose = jQuery("#remindChose").val();
            jQuery("#remindDate").attr("value", "");
            jQuery("#remindTime").attr("value", "");
            if (remindChose == '1') {
                jQuery("#remindDateTime").hide();
                jQuery("#remindDateTimeChose").hide()
                jQuery("#remindDate").hide();
                jQuery("#remindTime").hide();
            } else {
                jQuery("#remindDateTime").show();
                jQuery("#remindDateTimeChose").show()
                jQuery("#remindDate").show();
                jQuery("#remindTime").show();
            }
        }
    </script>
</head>
<body class="dialogBody">
<z:init method="com.wangjin.activity.ActivityInfo.initDialog">
    <form id="form3">
        <input type="hidden" value="${ID}" id="ID" name="ID">
        <input type="hidden" value="${couponsn}" id="couponsn" name="couponsn">
        <input type="hidden" value="${startT}" id="startT" name="startT">
        <input type="hidden" value="${endT}" id="endT" name="endT">
        <input type="hidden" value="${createtime}" id="createtime" name="createtime">
        <input type="hidden" value="${sb_payamount}" id="sb_payamount" name="sb_payamount">
        <input type="hidden" value="${sb_payamount_B}" id="sb_payamount_B" name="sb_payamount_B">
        <input type="hidden" value="${sb_prop1}" id="sb_prop1" name="sb_prop1">
        <input type="hidden" value="${sb_discountrate}" id="sb_discountrate" name="sb_discountrate">
        <input type="hidden" value="${sb_memberRule}" id="sb_memberRule" name="sb_memberRule">
        <input type="hidden" id="num" name="num" value="1">
        <input type="hidden" value="${ActivityChannel}" id="ActivityChannel" name="ActivityChannel">
        <input type="hidden" value="${memberChannelHidden}" id="memberChannelHidden" name="memberChannelHidden">
        <input type="hidden" value="${selectOption}" id="selectOption" name="selectOption">
        <input type="hidden" value="${sb_multiplenum}" id="sb_multiplenum" name="sb_multiplenum">
        <input type="hidden" value="${remindChose}" id="remindChose_check" name="remindChose_check">
        <table width="1050px" height="600px" align="center" cellpadding="2" cellspacing="0">
            <tr>
                <td style="padding: 6px;" class="blockTd">
                    <z:tree id="${ID}" method="com.sinosoft.platform.Channel.treeDataBindForActivity" level="3"
                            lazy="true" resizeable="true">
                        <img src="../Framework/Images/icon_drag.gif" width="16" height="16">
                        <p cid='${ID}' level="${TreeLevel}"><input type="checkbox" name="channel" id='channel_${ID}'
                                                                   value='${ChannelCode}' ${Checked} ${disabled}><label
                                for="channel_${ID}"><span id="span_${ID}">${Name}</span></label></p>
                    </z:tree>
                </td>
                <td valign="top">
                    <fieldset>
                        <table>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">是&nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;团&nbsp;&nbsp;&nbsp;购：</td>
                                <td><z:select style="width:120px;" id="GroupbuyWhether" verify="是否团购|NotNull"
                                              onChange="ChooseGroupbuy()"
                                              value="${GroupbuyWhetherValue}">${GroupbuyWhether}</z:select></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td id="GroupbuyNum_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">
                                    团&nbsp;&nbsp;&nbsp;购&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;数：
                                </td>
                                <td><input value="${GroupbuyNum}" type="text" id="GroupbuyNum" name="GroupbuyNum"
                                           ztype="String" class="inputText" size="20" maxlength=30></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            </tr>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;简&nbsp;&nbsp;&nbsp;述：</td>
                                <td><input value="${prop2}" type="text" id="prop2" name="prop2" ztype="String"
                                           class="inputText" size="20" verify="活动简述|NotNull"></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;称：</td>
                                <td><input value="${title}" type="text" id="title" name="title" ztype="String"
                                           class="inputText" size="20" verify="活动名称|NotNull" maxlength=30></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;述：</td>
                                <td width="160px"><input value="${description}" type="text" id="description"
                                                         name="description" ztype="String" class="inputText" size="20"
                                                         verify="活动描述|NotNull"></td>
                            </tr>
                            <tr id="trActivityRole" style="display:none">
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;规&nbsp;&nbsp;&nbsp;则：</td>
                                <td><textarea type="textarea" name="ruleDescription" id="ruleDescription" cols="30"
                                              rows="10">${ruleDescription}</textarea></td>
                            </tr>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;&nbsp;型：</td>
                                <td><input value="${type_other}" type="text" id="type_" name="type_" ztype="String"
                                           class="inputText" size="20" disabled="disabled" readonly="readonly">
                                    <input value="${type}" type="hidden" id="type" name="type" ztype="String"
                                           class="inputText" size="20" disabled="disabled" readonly="readonly">
                                </td>
                            </tr>
                            <tr id="trRewardType" style="display:none">
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">奖&nbsp;&nbsp;&nbsp;励&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;&nbsp;型：</td>
                                <td><input value="${rewardType}" type="text" id="rewardType" name="rewardType" ztype="String"
                                           class="inputText" size="20" disabled="disabled" readonly="readonly"></td>
                            </tr>
                            <tr id="remindChoseShow" style="display:none">
                                <td height="35" align="right" id="remindChoseTxt" name="remindChoseTxt"
                                    bordercolor="#eeeeee" class="tdgrey1">是&nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;醒：
                                </td>
                                <td><z:select style="width:120px;" onChange="remindChoseShowTime()"
                                              value="${remindChose}">"${remindChose_other}"
                                    <select name="remindChose" id="remindChose">
                                        <option value=""></option>
                                        <option value="0">是</option>
                                        <option value="1">否</option>
                                    </select>
                                </z:select>
                                </td>
                                <td height="35" id="remindDateTime" name="remindDateTime" align="left"
                                    bordercolor="#eeeeee" class="tdgrey1" style="display:none">提&nbsp;&nbsp;&nbsp;醒&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;间：
                                </td>
                                <td id="remindDateTimeChose" style="display:none">
                                    <input name="remindDate" id="remindDate" value="${remindDate}" type="text" size="14"
                                           ztype="Date"/>
                                    <input name="remindTime" id="remindTime" value="${remindTime}" type="text" size="14"
                                           ztype="Time"/>
                                </td>
                            </tr>
                        </table>
                        <table id="recommend_batch_table" style="display:none">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" id="batch_td">推荐人优惠券批次：</td>
                                <td>
                                    <input name="recommend_batch" type="text" value="${recommendBatch}" style="width:150px" class="inputText"
                                           id="recommend_batch" disabled="disabled"
                                           size="25" readonly="readonly" verify="推荐人优惠券批次"/>
                                    <input name="recommendBatch" type="hidden" value="" id="recommendBatch"/>
                                    <input type="button"  value="查 找" onClick="getCouponBatchList('recommend')">
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" id="batch_td">被推荐人优惠券批次：</td>
                                <td>
                                    <input name="recommended_batch" type="text" value="${recommendedBatch}" style="width:150px" class="inputText"
                                           id="recommended_batch" disabled="disabled"
                                           size="25" readonly="readonly" verify="被推荐人优惠券批次"/>
                                    <input name="recommendedBatch" type="hidden" value="" id="recommendedBatch"/>
                                    <input type="button" value="查 找" onClick="getCouponBatchList('recommended')">
                                </td>
                            </tr>
                        </table>
                        <table id="batch_table">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">优惠券批次&nbsp;：</td>
                                <td>
                                    <input name="batchother" type="text" value="${batch}" style="width:296px"
                                           class="inputText" id="batchother" disabled="disabled"
                                           size="25" readonly="readonly"/>
                                    <input name="batch" type="hidden" value="${batch}" style="width:350px"
                                           class="inputText" id="batch" disabled="disabled"
                                           size="30" readonly="readonly"/>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td height="35" align="right" id="autoCreate_td" bordercolor="#eeeeee" class="tdgrey1">
                                    自动获取优惠劵：
                                </td>
                                <td><input value="${autocreate_other}" type="text" id="autoCreate_other"
                                           name="autoCreate_other" ztype="String" class="inputText" size="20"
                                           disabled="disabled" readonly="readonly">
                                    <input value="${autocreate}" type="hidden" id="autoCreate" name="autoCreate"
                                           ztype="String" class="inputText" size="20" disabled="disabled"
                                           readonly="readonly">
                                </td>
                            </tr>
                        </table>
                        <table>
                            <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">申&nbsp;&nbsp;&nbsp;&nbsp;请&nbsp;&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;：</td>
                            <td width="150px"><z:select id="createUser" verify="申请人|NotNull"
                                                        value="${createuser}">${createUserInit}</z:select></td>
                            <td id="memberChannel_input" style="display: none;"><input type="checkbox"
                                                                                       name="memberChannel"
                                                                                       id="memberChannel" value="Y"
                                                                                       onclick="memberChannelOn()"/>会员频道渠道
                            </td>
                        </table>
                        <table id="yh_talbe">
                            <tr>

                                <td id="discountrate_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1"
                                    style="display: none;">&nbsp;&nbsp;&nbsp;折&nbsp;&nbsp;&nbsp;扣&nbsp;&nbsp;&nbsp;&nbsp;率:&nbsp;&nbsp;
                                </td>
                                <td id="discountrate_input" style="display: none;"><input type="text" id="discountrate"
                                                                                          value="${discountrate}"/></td>
                                <td id="multiplenum_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1"
                                    style="display: none;">积&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;倍&nbsp;&nbsp;&nbsp;数：
                                </td>
                                <td id="multiplenum_input" style="display: none;"><z:select style="width:120px;"
                                                                                            id="multiplenum"
                                                                                            value="${multiplenum}">${multiplenuminit}</z:select></td>
                                <td id="payAmount_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">消&nbsp;&nbsp;&nbsp;费&nbsp;&nbsp;&nbsp;区&nbsp;&nbsp;&nbsp;间：</td>
                                <td><input value="${payamount}" type="text" id="payAmount_0" name="payAmount_0"
                                           ztype="String" class="inputText" size="10" verify="消费金额|NUM">&nbsp;<input
                                        value="" type="text" id="payAmountB_0" name="payAmountB_0" ztype="String"
                                        value="${payamount}" class="inputText" size="10" verify="消费金额|NUM"></td>
                                <td id="prop1_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1"
                                    style="display: none;">优惠金额：
                                </td>
                                <td><input value="${prop1_0}" type="text" id="prop1_0" name="prop1_0" ztype="String"
                                           class="inputText" size="10" verify="优惠金额|NUM" style="display: none;"></td>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" id="couponnum_td">优&nbsp;&nbsp;&nbsp;惠&nbsp;&nbsp;&nbsp;券&nbsp;&nbsp;&nbsp;数：</td>
                                <td id="couponnumtd"><input value="${couponnum}" type="text" id="couponnum"
                                                            name="couponnum" ztype="String" class="inputText" size="20"
                                                            readonly="readonly" disabled="disabled"></td>
                                <td id="checkviptd" style="display:none;">
                                    <input type="checkbox" name="checkvip_0" value="K0"/>普通会员
                                    <input type="checkbox" name="checkvip_0" value="K1"/>k1会员
                                    <input type="checkbox" name="checkvip_0" value="K2"/>k2会员
                                    <input type="checkbox" name="checkvip_0" value="VIP"/>vip会员
                                </td>
                                <td id="add_button_td"><input type="button" id="add_button" value="增加"
                                                              onclick="addtable();"></td>
                            </tr>
                        </table>
                        <table id="payAmount_table" style="display: none;">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">消&nbsp;&nbsp;&nbsp;费&nbsp;&nbsp;&nbsp;金&nbsp;&nbsp;&nbsp;额：</td>
                                <td><input value="${payAmount}" type="text" id="payAmount" name="payAmount"
                                           ztype="String" class="inputText" size="10" verify="消费金额|NUM"></td>
                            </tr>
                        </table>
                        <div id="add_table_div"></div>
                        <table>
                            <tr>
                                <td height="35" align="left" bordercolor="#eeeeee" class="tdgrey1">起&nbsp;&nbsp;&nbsp;始&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;间：</td>
                                <td>
                                    <input name="startDate" id="startDate" value="${startDate}" type="text" size="14"
                                           ztype="Date" verify="开始日期|NotNull"/>
                                    <input name="startTime" id="startTime" value="${startTime}" type="text" size="14"
                                           ztype="Time" verify="开始时间|NotNull"/>
                                </td>
                            </tr>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">终&nbsp;&nbsp;&nbsp;止&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;间：</td>
                                <td><input name="endDate" id="endDate" value="${endDate}" type="text" size="14"
                                           ztype="Date" verify="结束日期|NotNull"/>
                                    <input name="endTime" id="endTime" value="${endTime}" type="text" size="14"
                                           ztype="Time" verify="结束时间|NotNull"/></td>
                                <input name="accumulation" id="accumulation" value="${accumulation}"
                                       type="hidden"/></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td id="accumulation_checkbox_td"><input type="checkbox" onclick="checkAccumulation()"
                                                                         id="accumulation_checkbox"/>可累计
                            </tr>
                        </table>
                        <table id="checkvip_table" style="display:none;">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">会&nbsp;&nbsp;&nbsp;员&nbsp;&nbsp;&nbsp;等&nbsp;&nbsp;&nbsp;级：</td>
                                <td id="checkviptd">
                                    <input type="checkbox" name="checkvip" value="K0" ${checked_1}/>普通会员
                                    <input type="checkbox" name="checkvip" value="K1" ${checked_2}/>k1会员
                                    <input type="checkbox" name="checkvip" value="K2" ${checked_3}/>k2会员
                                    <input type="checkbox" name="checkvip" value="VIP" ${checked_4}/>vip会员
                                </td>
                            </tr>
                        </table>
                        <table id="company_table">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活动公司：</td>
                                <td>
                                    <input name="insuranceCompanyother" type="text" value="${insurancecompanyother}"
                                           style="width:450px" class="inputText" id="insuranceCompanyother"
                                           disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input name="insuranceCompany" type="hidden" value="${insurancecompany}"
                                           style="width:450px" class="inputText" id="insuranceCompany"
                                           disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input type="button" id="chooseInsuranceCompany" name="chooseInsuranceCompany"
                                           value="查 找" onClick="getInsuranceCompanyList()">
                                </td>
                            </tr>
                        </table>
                        <table id="riskcode_table">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活动险种：</td>
                                <td>
                                    <input name="riskCodeother" type="text" value="${riskcodeother}" style="width:450px"
                                           class="inputText" id="riskCodeother" disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input name="riskCode" type="hidden" value="${riskcode}" style="width:450px"
                                           class="inputText" id="riskCode" disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input type="button" id="chooseRiskCode" name="chooseRiskCode" value="查 找"
                                           onClick="getRiskCodeList()">
                                </td>
                            </tr>
                        </table>
                        <table id="product_table">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活动产品：</td>
                                <td>
                                    <input name="productother" type="text" value="${productother}" style="width:450px"
                                           class="inputText" id="productother" disabled="disabled"
                                           size="30" readonly="readonly" onmouseover=""/>
                                    <input name="product" type="hidden" value="${product}" style="width:450px"
                                           class="inputText" id="product" disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input type="button" id="chooseProduct" name="chooseProduct" value="查 找"
                                           onClick="getProductList()">
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
            </tr>
        </table>
    </form>
</z:init>
</body>
</html>
