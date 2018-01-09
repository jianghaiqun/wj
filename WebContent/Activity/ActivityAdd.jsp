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
    <script type="text/javascript">
        jQuery(document).ready(function () {
            jQuery("#add_button_td").hide();
        });

        Page.onLoad(function () {
            var ActivityChannel = jQuery('#ActivityChannel').val();
            if (ActivityChannel == 'tb') {
                hideRiskcodeCompany();
                jQuery("#company_table").hide();
                jQuery("#riskcode_table").hide();

            }
        });
        var num = 1;
        var vipBigest = 1;
        var batch_num = 1;
        var batch_param;

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
            }else {
                batch_param = param;
                couponBatchother = jQuery("#batchother").val();
                couponBatch = jQuery("#batch").val();
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
            if (type == "11") {
                jQuery("#batchother_" + batch_param).val(couponBatch2other);
                jQuery("#batch_" + batch_param).val(couponBatch2other);
            } else if (type == "12") {
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
            var type = jQuery("#type").val();
            if (ActivityChannel == 'tb') {
                if (type == "10") {
                    getOthProductList();
                } else {
                    getTBProductList();
                }
            } else {
                getOthProductList();
            }
        }

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

        //选择产品
        function chooseProduct() {
            var arr;
            var ActivityChannel = jQuery('#ActivityChannel').val();
            var type = jQuery("#type").val();
            if (ActivityChannel == 'tb') {
                if (type == "10") {
                    arr = $DW.DataGrid.getSelectedValue("dg2");
                } else {
                    arr = $DW.DataGrid.getSelectedValue("dg4");
                }
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
                    if (type == "10") {
                        var productname = $DW.DataGrid.getSelectedValueByColumns("dg2", "productname");
                        $DW.$S("product2other", productname);
                    } else {
                        var CustomCode = $DW.DataGrid.getSelectedValueByColumns("dg4", "id");
                        $DW.$S("product2other", CustomCode);
                    }
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
            var type = jQuery("#type").val();
            if (ActivityChannel == 'tb' && type != "10") {
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
            var diag = new Dialog("Diag2");
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
                $DW.$S("riskCode2other", riskCodeother);
                $DW.$S("riskCode2", riskCode);
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
            var diag = new Dialog("Diag2");
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
                $DW.$S("insuranceCompany2other", insuranceCompanyother);
                $DW.$S("insuranceCompany2", insuranceCompany);
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
                //Dialog.alert("您还没有选择险种！");
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
                $DW.$S("insuranceCompany2other", "");
                $DW.$S("insuranceCompany2", "");
                //Dialog.alert("您还没有选择保险公司！");
                //return false;
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

        //当活动类型为“注册送”时，隐藏公司与险种及金额
        function hideRiskcodeCompany() {
            var ActivityChannel = jQuery('#ActivityChannel').val();
            jQuery("#memberChannel").attr("checked", false);
            jQuery("td[id^='checkviptd']").each(function () {
                jQuery("#" + this.id).hide();
            });
            var type = jQuery("#type").val();
            jQuery("#Groupbuy_table").show();
            jQuery("#more_batch_table").hide();
            jQuery("#checkvip_table").hide();
            jQuery("#payAmount_table").hide();
            jQuery("#add_batch_div").children('table').remove();
            jQuery("#recommend_batch_table").hide();
            jQuery("#trActivityRole").hide();
            jQuery("#trRewardType").hide();
            jQuery("#ruleDescription").attr("value", "");
            if (type == '0' || type == '4' || type == '5') {
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "0");
                jQuery("#batchother").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").hide();
                jQuery("#company_table").hide();
                jQuery("#riskcode_table").hide();
                jQuery("#product_table").hide();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
                jQuery("#payAmount_td").hide();
                jQuery("#autoCreate_td").hide();
                jQuery("#autoCreate").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#batch_table").show();

                jQuery("#prop1_0").hide();
                jQuery("#prop1_td").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").hide();
                jQuery("#memberChannel_input").attr("value", "");
                jQuery("#memberChannel_input").hide();
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();
                jQuery("#remindChoseShow").hide();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            } else if (type == '2') {
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#batchother").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").show();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
                jQuery("#payAmount_td").hide();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#autoCreate_td").show();
                jQuery("#autoCreate").show();
                jQuery("#couponnum_td").show();
                jQuery("#couponnum").show();
                jQuery("#batch_table").show();
                jQuery("#prop1_0").hide();
                jQuery("#prop1_td").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#memberChannel_input").attr("value", "");
                jQuery("#memberChannel_input").hide();
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();
                jQuery("#remindChoseShow").hide();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            } else if (type == '3') {
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#batchother").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").show();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#payAmount_0").show();
                jQuery("#payAmountB_0").show();
                jQuery("#payAmount_td").show();
                jQuery("#autoCreate_td").show();
                jQuery("#autoCreate").show();
                jQuery("#payAmount_0").show();
                jQuery("#payAmountB_0").show();
                jQuery("#payAmount_td").show();
                jQuery("#batch_table").hide();
                jQuery("#prop1_0").show();
                jQuery("#prop1_td").show();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#accumulation_checkbox_td").show();
                jQuery("#add_button_td").show();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#memberChannel_input").show();
                jQuery("#prop1_td").html('优惠金额：');
                jQuery("#remindChoseShow").hide();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            } else if (type == '6') {
                vipBigest = 1;
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#batchother").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").show();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
                jQuery("#payAmount_td").hide();
                jQuery("#autoCreate_td").show();
                jQuery("#autoCreate").show();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
                jQuery("#payAmount_td").hide();
                jQuery("#batch_table").hide();
                jQuery("#prop1_0").hide();
                jQuery("#prop1_td").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").hide();
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
                jQuery("#memberChannel_input").attr("value", "");
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();
                jQuery("#remindChoseShow").show();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            } else if (type == '7') {
                vipBigest = 1;
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#batchother").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").show();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
                jQuery("#payAmount_td").hide();
                jQuery("#autoCreate_td").show();
                jQuery("#autoCreate").show();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
                jQuery("#payAmount_td").hide();
                jQuery("#batch_table").hide();
                jQuery("#prop1_0").hide();
                jQuery("#prop1_td").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").show();
                jQuery("#multiplenum_input").show();
                jQuery("#channel_table").show();
                jQuery("#memberChannel_input").attr("value", "");
                if (ActivityChannel != 'tb') {
                    jQuery("#memberChannel_input").show();
                } else {
                    jQuery("#memberChannel_input").hide();
                }
                jQuery("#memberChannel_input").attr("value", "");
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();
                jQuery("#remindChoseShow").hide();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            } else if (type == '8') {
                vipBigest = 1;
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#batchother").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").show();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#payAmount_0").show();
                jQuery("#payAmountB_0").show();
                jQuery("#payAmount_td").show();
                jQuery("#autoCreate_td").show();
                jQuery("#autoCreate").show();
                jQuery("#payAmount_0").show();
                jQuery("#payAmountB_0").show();
                jQuery("#payAmount_td").show();
                jQuery("#batch_table").hide();
                jQuery("#prop1_0").show();
                jQuery("#prop1_td").show();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").show();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();

                jQuery("#add_button_td").hide();
                jQuery("#prop1_td").hide();
                jQuery("#prop1_0").hide();
                jQuery("#prop1_0").attr("value", "");

                jQuery("#memberChannel_input").attr("value", "");
                //if (ActivityChannel != 'tb') {
                //  jQuery("#memberChannel_input").show();
                //}else{
                jQuery("#memberChannel_input").hide();
                //}
                jQuery("#memberChannel_input").attr("value", "");
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();
                jQuery("#remindChoseShow").show();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            } else if (type == '9') {

                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "0");
                jQuery("#batchother").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").hide();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
                jQuery("#payAmount_td").hide();
                jQuery("#autoCreate_td").hide();
                jQuery("#autoCreate").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#batch_table").show();
                jQuery("#channel_table").show();

                jQuery("#prop1_0").hide();
                jQuery("#prop1_td").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();

                jQuery("#memberChannel_input").attr("value", "");
                jQuery("#memberChannel_input").hide();
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();
                jQuery("#remindChoseShow").hide();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            } else if (type == '10') {
                jQuery("#GroupbuyWhether").val("0");
                document.getElementById("GroupbuyWhether").disabled = true;//不参加团购
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();//折扣率
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");//消费区间 prop1_0
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#batchother").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").show();
                jQuery("#company_table").hide();//活动公司
                jQuery("#riskcode_table").hide();//活动险种
                jQuery("#product_table").show();
                jQuery("#payAmount_0").show();//消费区间
                jQuery("#payAmountB_0").show();
                jQuery("#payAmount_td").show();//消费区间
                jQuery("#autoCreate_td").show();
                jQuery("#autoCreate").show();
                jQuery("#payAmountB_0").show();
                jQuery("#batch_table").hide();
                jQuery("#prop1_0").show();
                jQuery("#prop1_td").show();
                jQuery("#prop1_td").html('送话费：');
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#accumulation_checkbox_td").show();
                jQuery("#add_button_td").show();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#memberChannel_input").attr("value", "");
                jQuery("#memberChannel_input").hide();
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();//会员等级
                jQuery("#remindChoseShow").hide();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            } else if (type == '11') {
                batch_num = 1;
                jQuery("#riskCodeother").val("");//活动险种
                jQuery("#riskCode").val("");
                jQuery("#insuranceCompanyother").val("");//活动公司
                jQuery("#insuranceCompany").val("");
                jQuery("#productother").val("");//活动产品
                jQuery("#product").val("");
                jQuery("#autoCreate").val("");// 自动获取优惠劵
                jQuery("#couponnum").val("");//优惠券数
                jQuery("#batchother").val("");//优惠券批次
                jQuery("#batch").val("");
                jQuery("#prop1_0").val("");//优惠金额
                jQuery("#more_batch_table").show();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();//折扣率
                jQuery("#yh_talbe").hide();
                jQuery("#company_table").show();//活动公司
                jQuery("#riskcode_table").show();//活动险种
                jQuery("#product_table").show();//活动产品
                jQuery("#batch_table").hide();
                jQuery("#prop1_0").hide();
                jQuery("#prop1_td").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").hide();//增加按钮
                jQuery("#checkvip_table").show();
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
                jQuery("#payAmount_table").show();
            } else if (type == '12') {
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "0");
                jQuery("#batchother").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").hide();
                jQuery("#payAmount_0").hide();
                jQuery("#payAmountB_0").hide();
                jQuery("#payAmount_td").hide();
                jQuery("#autoCreate_td").hide();
                jQuery("#autoCreate").hide();
                jQuery("#couponnum_td").hide();
                jQuery("#couponnum").hide();
                jQuery("#batch_table").hide();
                jQuery("#recommend_batch_table").show();
                jQuery("#trActivityRole").show();
                jQuery("#trRewardType").show();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();

                jQuery("#prop1_0").hide();
                jQuery("#prop1_td").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").hide();
                jQuery("#memberChannel_input").attr("value", "");
                jQuery("#memberChannel_input").hide();
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();
                jQuery("#remindChoseShow").hide();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
                jQuery("#recommend_batch").attr("value", "");
                jQuery("#recommendBatch").attr("value", "");
                jQuery("#recommended_batch").attr("value", "");
                jQuery("#recommendedBatch").attr("value", "");
            } else {
                jQuery("#riskCodeother").attr("value", "");
                jQuery("#riskCode").attr("value", "");
                jQuery("#insuranceCompanyother").attr("value", "");
                jQuery("#insuranceCompany").attr("value", "");
                jQuery("#productother").attr("value", "");
                jQuery("#product").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#payAmount_0").attr("value", "");
                jQuery("#payAmountB_0").attr("value", "");
                jQuery("#couponnum").attr("value", "");
                jQuery("#batchother").attr("value", "");
                jQuery("#batch").attr("value", "");
                jQuery("#autoCreate").attr("value", "");
                jQuery("#prop1_0").attr("value", "");
                jQuery("#yh_talbe").show();
                jQuery("#company_table").show();
                jQuery("#riskcode_table").show();
                jQuery("#product_table").show();
                jQuery("#payAmount_0").show();
                jQuery("#payAmountB_0").show();
                jQuery("#payAmount_td").show();
                jQuery("#autoCreate_td").show();
                jQuery("#autoCreate").show();
                jQuery("#payAmount_0").show();
                jQuery("#payAmountB_0").show();
                jQuery("#payAmount_td").show();
                jQuery("#couponnum_td").show();
                jQuery("#couponnum").show();
                jQuery("#batch_table").show();
                jQuery("#prop1_0").hide();
                jQuery("#prop1_td").hide();
                jQuery("#accumulation_checkbox_td").hide();
                jQuery("#add_button_td").hide();
                jQuery("#discountrate_td").hide();
                jQuery("#discountrate_input").hide();
                jQuery("#multiplenum_td").hide();
                jQuery("#multiplenum_input").hide();
                jQuery("#channel_table").show();
                jQuery("#memberChannel_input").attr("value", "");
                jQuery("#memberChannel_input").hide();
                jQuery("input[type=checkbox][name^='checkvip_']").removeAttr("checked");
                jQuery("#checkviptd").hide();
                jQuery("#remindChoseShow").hide();
                jQuery("#remindDate").attr("value", "");
                jQuery("#remindTime").attr("value", "");
            }
            jQuery("#add_table_div").html('');
            //var yh_talbe_adds=jQuery("#yh_talbe_add");
            //alert(yh_talbe_adds.length);
            //for(var i=0;i<yh_talbe_adds.length;i++){
            //yh_talbe_adds.eq(i).remove();
            //}
        }

        /**
         * 是否可累计
         */
        function checkAccumulation() {
            var check_flag = jQuery('#accumulation_checkbox').is(':checked');
            if (check_flag == true) {
                jQuery("#accumulation").attr("value", "0");
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

            jQuery("#add_table_div").append(tablestr);
            num++;
            jQuery("#num").val(num);
            if (parseInt(num) == 1) {
                jQuery("#accumulation_checkbox_td").show();
            } else {
                jQuery("#accumulation_checkbox_td").hide();
            }

        }

        function selectOptionToStr(selectOptionStr) {
            var selectOptionToStrX = "";
            var selectOptionArray = selectOptionStr.split(",");
            for (var i = 0; i < selectOptionArray.length; i++) {
                selectOptionToStrX = selectOptionToStrX + "<option  value=\" " + selectOptionArray[i] + "\" >" + selectOptionArray[i + 1] + "</option>";
                i++;
            }
            return selectOptionToStrX;
        }

        function deleterow(t) {
            t.parentNode.parentNode.parentNode.parentNode.parentNode.removeChild(t.parentNode.parentNode.parentNode.parentNode);
            deleterow2();
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
                    });
                } else {
                    jQuery("td[id^='checkviptd']").each(function () {
                        jQuery("#" + this.id).hide();
                    });
                }
            }
        }

        function addBatchTable() {
            batch_num++;
            if (batch_num > 3) {
                batch_num--;
                Dialog.alert("最多只能发放三个优惠券！");
                return;
            }
            var tableStr = "<table id=\"more_batch_table_" + batch_num + "\""
                + "<tr>"
                + "<td height=\"35\" align=\"right\" bordercolor=\"#eeeeee\" class=\"tdgrey1\">优&nbsp;惠&nbsp;券&nbsp;批&nbsp;次&nbsp;&nbsp;：</td>"
                + "<td>"
                + "<input name=\"batchother_" + batch_num + "\" type=\"text\" value=\"\" style=\"width:200px\" class=\"inputText\" id=\"batchother_" + batch_num + "\" disabled=\"disabled\" size=\"25\" readonly = \"readonly\" verify=\"优惠券批次\"/>"
                + "<input name=\"batch_" + batch_num + "\" type=\"hidden\" id=\"batch_" + batch_num + "\"/>"
                + "&nbsp;&nbsp;<input type=\"button\" id=\"chooseCouponBatch\" name=\"chooseCouponBatch\" value=\"查 找\" onClick=\"getCouponBatchList(" + batch_num + ")\">"
                + "</td>"
                + "<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>"
                + "<td ><input type=\"button\" id=\"\" value=\"删除\" onclick=\"deleteBatchRow(this);\"></td>"
                + "</tr></table>";
            jQuery("#add_batch_div").append(tableStr);
            jQuery("#batch_num").val(batch_num);
        }

        function deleteBatchRow(t) {
            t.parentNode.parentNode.parentNode.parentNode.parentNode.removeChild(t.parentNode.parentNode.parentNode.parentNode);
            batch_num--;
            jQuery("#batch_num").val(batch_num);
        }

        function onCheck(ele) {

            ele = $(ele);
            var checked = ele.checked;
            var newPID = ele.getParent("P").$A("parentid");
            if (!checked) {
                if ($("channel_" + newPID) != null) {
                    $("channel_" + newPID).checked = false;
                }
            }
            if (ele.value == "-1") {

            }
            var p = ele.getParent("P");

            var level = p.$A("level");
            var arr = $("channel1").$T("P");

            var flag = true;
            for (var i = 0; i < arr.length; i++) {
                var c = arr[i];
                var cid = c.$A("cid");
                if (cid) {
                    if (cid != "-1" && ele.value == "-1") {
                        if (checked) {
                            $("channel_" + cid).disable();
                        } else {
                            $("channel_" + cid).enable();
                        }
                    } else {
                        if (c != p && flag) {
                            continue;
                        }
                        if (c == p) {
                            flag = false;
                            continue;
                        }
                        if (c.$A("level") > level) {
                            $("channel_" + cid).checked = checked;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<z:init method="com.wangjin.activity.ActivityInfo.init">
    <form id="form2">
        <input type="hidden" id="num" name="num" value="1">
        <input type="hidden" id="batch_num" name="batch_num" value="1">
        <input type="hidden" value="${ActivityChannel}" id="ActivityChannel" name="ActivityChannel">
        <input type="hidden" value="${selectOption}" id="selectOption" name="selectOption">
        <table width="1050px" height="600px" align="center" cellpadding="2" cellspacing="0">
            <tr>
                <td style="padding: 6px;" class="blockTd">
                    <z:tree id="channel1" method="com.sinosoft.platform.Channel.treeDataBindForActivity" level="3"
                            lazy="true" resizeable="true">
                        <img src="../Framework/Images/icon_drag.gif">
                        <%-- <p cid='${ID}' level="${TreeLevel}" ><input type="checkbox" name="channel" id='channel_${ID}' value='${ChannelCode}' onClick="onCheck(this);" ><label for="channel_${ID}"><span id="span_${ID}">${Name}</span></label></p> --%>

                        <p cid='${ID}' level="${TreeLevel}"><input type="checkbox"
                                                                   name="channel" id='channel_${ID}'
                                                                   value='${ChannelCode}'
                                                                   onClick="onCheck(this);"><label
                                for="channel_${ID}"><span id="span_${ID}">${Name}</span></label></p>
                    </z:tree>
                </td>
                <td valign="top">
                    <fieldset>
                        <table id="Groupbuy_table">
                            <tr>
                                <td id="GroupbuyWhether_td" height="35" align="right" bordercolor="#eeeeee"
                                    class="tdgrey1">是&nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;团&nbsp;&nbsp;&nbsp;购：
                                </td>
                                <td><z:select style="width:120px;" id="GroupbuyWhether" verify="是否团购|NotNull"
                                              onChange="ChooseGroupbuy()">${GroupbuyWhether}</z:select></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td id="GroupbuyNum_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">
                                    团&nbsp;&nbsp;&nbsp;购&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;数：
                                </td>
                                <td><input value="" type="text" id="GroupbuyNum" name="GroupbuyNum" ztype="String"
                                           class="inputText" size="20" maxlength=30></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            </tr>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;简&nbsp;&nbsp;&nbsp;述：</td>
                                <td><input value="" type="text" id="prop2" name="prop2" ztype="String" class="inputText"
                                           size="20" verify="活动简述|NotNull"></td>
                            </tr>
                        </table>
                        <table>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;称：</td>
                                <td><input value="" type="text" id="title" name="title" ztype="String" class="inputText"
                                           size="20" verify="活动名称|NotNull" maxlength=30></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;描&nbsp;&nbsp;&nbsp;述：</td>
                                <td><input value="" type="text" id="description" name="description" ztype="String"
                                           class="inputText" size="20" verify="活动描述|NotNull"></td>
                            </tr>
                            <tr id="trActivityRole" style="display:none">
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;规&nbsp;&nbsp;&nbsp;则：</td>
                                <td><textarea type="textarea" name="ruleDescription" id="ruleDescription" cols="30"
                                              rows="10" verify="活动规则|NotNull"></textarea></td>
                            </tr>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活&nbsp;&nbsp;&nbsp;动&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;&nbsp;型：</td>
                                <td><z:select style="width:120px;" id="type" verify="活动类型|NotNull"
                                              onChange="hideRiskcodeCompany()">${type}</z:select></td>
                            </tr>
                            <tr id="trRewardType" style="display:none">
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">奖&nbsp;&nbsp;&nbsp;励&nbsp;&nbsp;&nbsp;类&nbsp;&nbsp;&nbsp;型：</td>
                                <td><z:select style="width:120px;" id="rewardType" verify="奖励类型|NotNull">${rewardType}</z:select></td>
                            </tr>
                            <tr id="remindChoseShow" style="display:none">
                                <td height="35" align="right" id="remindChoseTxt" name="remindChoseTxt"
                                    bordercolor="#eeeeee" class="tdgrey1">是&nbsp;&nbsp;&nbsp;否&nbsp;&nbsp;&nbsp;提&nbsp;&nbsp;&nbsp;醒：
                                </td>
                                <td><z:select style="width:120px;" onChange="remindChoseShowTime()">
                                    <select name="remindChose" id="remindChose">
                                        <option value="1">否</option>
                                        <option value="0">是</option>
                                    </select>
                                </z:select>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td height="35" id="remindDateTime" name="remindDateTime" align="left"
                                    bordercolor="#eeeeee" class="tdgrey1" style="display:none">提&nbsp;&nbsp;&nbsp;醒&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;间：
                                </td>
                                <td id="remindDateTimeChose" style="display:none">
                                    <input name="remindDate" id="remindDate" value="" type="text" size="14"
                                           ztype="Date"/>
                                    <input name="remindTime" id="remindTime" value="" type="text" size="14"
                                           ztype="Time"/>
                                </td>
                            </tr>
                        </table>
                        <table id="recommend_batch_table" style="display:none">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" id="batch_td">推荐人优惠券批次：</td>
                                <td>
                                    <input name="recommend_batch" type="text" value="" style="width:150px" class="inputText"
                                           id="recommend_batch" disabled="disabled"
                                           size="25" readonly="readonly" verify="推荐人优惠券批次"/>
                                    <input name="recommendBatch" type="hidden" value="" id="recommendBatch"/>
                                    <input type="button"  value="查 找" onClick="getCouponBatchList('recommend')">
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" id="batch_td">被推荐人优惠券批次：</td>
                                <td>
                                    <input name="recommended_batch" type="text" value="" style="width:150px" class="inputText"
                                           id="recommended_batch" disabled="disabled"
                                           size="25" readonly="readonly" verify="被推荐人优惠券批次"/>
                                    <input name="recommendedBatch" type="hidden" value="" id="recommendedBatch"/>
                                    <input type="button" value="查 找" onClick="getCouponBatchList('recommended')">
                                </td>
                            </tr>
                        </table>
                        <table id="batch_table">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" id="batch_td">优&nbsp;惠&nbsp;券&nbsp;批&nbsp;次&nbsp;&nbsp;：</td>
                                <td>
                                    <input name="batchother" type="text" value="" style="width:296px" class="inputText"
                                           id="batchother" disabled="disabled"
                                           size="25" readonly="readonly" verify="优惠券批次"/>
                                    <input name="batch" type="hidden" value="" style="width:350px" class="inputText"
                                           id="batch" disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input type="button" id="chooseCouponBatch" name="chooseCouponBatch" value="查 找"
                                           onClick="getCouponBatchList()">
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td height="35" id="autoCreate_td" align="right" bordercolor="#eeeeee" class="tdgrey1">
                                    &nbsp;自动获取优惠劵：
                                </td>
                                <td><z:select style="width:120px;" onChange="hidecouponnum()"><select name="autoCreate"
                                                                                                      id="autoCreate">
                                    <option value=""></option>
                                    <option value="0">是</option>
                                    <option value="1">否</option>
                                </select>
                                </z:select>
                                </td>
                            </tr>
                        </table>
                        <table id="more_batch_table" style="display: none">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" id="batch_td">优&nbsp;惠&nbsp;券&nbsp;批&nbsp;次&nbsp;&nbsp;：</td>
                                <td>
                                    <input name="batchother_1" type="text" value="" style="width:200px"
                                           class="inputText" id="batchother_1" disabled="disabled"
                                           size="25" readonly="readonly" verify="优惠券批次"/>
                                    <input name="batch_1" type="hidden" value="" id="batch_1"/>
                                    <input type="button" id="chooseCouponBatch" name="chooseCouponBatch" value="查 找"
                                           onClick="getCouponBatchList(1)">
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td><input type="button" value="增加" onclick="addBatchTable();"></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td height="35" id="autoCreate_td" align="right" bordercolor="#eeeeee" class="tdgrey1">
                                    &nbsp;自动获取优惠劵：
                                </td>
                                <td><z:select style="width:120px;"><select name="autoCreate1" id="autoCreate1">
                                    <option value=""></option>
                                    <option value="0">是</option>
                                    <option value="1">否</option>
                                </select>
                                </z:select>
                                </td>
                            </tr>
                        </table>
                        <div id="add_batch_div"></div>
                        <table id="sqr_table">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">申&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请&nbsp;&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;&nbsp;：</td>
                                <td width="150px"><z:select id="createUser"
                                                            verify="申请人|NotNull">${createUserInit}</z:select></td>
                                <td id="memberChannel_input" style="display: none;"><input type="checkbox"
                                                                                           name="memberChannel"
                                                                                           id="memberChannel" value="Y"
                                                                                           onclick="memberChannelOn()"/>会员频道渠道
                                </td>
                            </tr>
                        </table>
                        <table id="yh_talbe">
                            <tr>
                                <td id="multiplenum_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1"
                                    style="display: none;">积&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;&nbsp;倍&nbsp;&nbsp;&nbsp;数：
                                </td>
                                <td id="multiplenum_input" style="display: none;"><z:select style="width:120px;"
                                                                                            id="multiplenum"
                                                                                            verify="积分倍数|NotNull">${multiplenum}</z:select></td>
                                <td id="discountrate_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1"
                                    style="display: none;">折&nbsp;&nbsp;&nbsp;扣&nbsp;&nbsp;&nbsp;&nbsp;率:&nbsp;&nbsp;
                                </td>
                                <td id="discountrate_input" style="display: none;"><input type="text" id="discountrate"
                                                                                          verify="折扣率|PositiveNumber&NotNull"/>
                                </td>
                                <td id="payAmount_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">消&nbsp;&nbsp;&nbsp;费&nbsp;&nbsp;&nbsp;区&nbsp;&nbsp;&nbsp;间：</td>
                                <td><input value="" type="text" id="payAmount_0" name="payAmount_0" ztype="String"
                                           class="inputText" size="10" verify="消费金额|NUM">&nbsp;<input value=""
                                                                                                      type="text"
                                                                                                      id="payAmountB_0"
                                                                                                      name="payAmountB_0"
                                                                                                      ztype="String"
                                                                                                      class="inputText"
                                                                                                      size="10"
                                                                                                      verify="消费金额|NUM">
                                </td>
                                <td id="prop1_td" height="35" align="right" bordercolor="#eeeeee" class="tdgrey1"
                                    style="display: none;">优惠金额：
                                </td>
                                <td><input value="" type="text" id="prop1_0" name="prop1_0" ztype="String"
                                           class="inputText" size="10" verify="优惠金额|NUM" style="display: none;"></td>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1" id="couponnum_td">优&nbsp;&nbsp;&nbsp;惠&nbsp;&nbsp;&nbsp;券&nbsp;&nbsp;&nbsp;数：</td>
                                <td id="couponnumtd"><input value="" type="text" id="couponnum" name="couponnum"
                                                            ztype="String" class="inputText" size="20"></td>
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
                                <td><input value="" type="text" id="payAmount" name="payAmount" ztype="String"
                                           class="inputText" size="10" verify="消费金额|NUM"></td>
                            </tr>
                        </table>
                        <div id="add_table_div"></div>
                        <table id="time_talbe">
                            <tr>
                                <td height="35" align="left" bordercolor="#eeeeee" class="tdgrey1">起&nbsp;&nbsp;&nbsp;始&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;间：</td>
                                <td>
                                    <input name="startDate" id="startDate" value="" type="text" size="14" ztype="Date"
                                           verify="开始日期|NotNull"/>
                                    <input name="startTime" id="startTime" value="" type="text" size="14" ztype="Time"
                                           verify="开始时间|NotNull"/>
                                </td>
                            </tr>
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">终&nbsp;&nbsp;&nbsp;止&nbsp;&nbsp;&nbsp;时&nbsp;&nbsp;&nbsp;间：</td>
                                <td><input name="endDate" id="endDate" value="" type="text" size="14" ztype="Date"
                                           verify="结束日期|NotNull"/>
                                    <input name="endTime" id="endTime" value="" type="text" size="14" ztype="Time"
                                           verify="结束时间|NotNull"/></td>
                                <input name="accumulation" id="accumulation" value="1" type="hidden"/></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td id="accumulation_checkbox_td"><input type="checkbox" onclick="checkAccumulation()"
                                                                         id="accumulation_checkbox"/>可累计
                                </td>
                            </tr>
                        </table>
                        <table id="checkvip_table" style="display:none;">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">会&nbsp;&nbsp;&nbsp;员&nbsp;&nbsp;&nbsp;等&nbsp;&nbsp;&nbsp;级：</td>
                                <td id="checkviptd">
                                    <input type="checkbox" name="checkvip" value="K0"/>普通会员
                                    <input type="checkbox" name="checkvip" value="K1"/>k1会员
                                    <input type="checkbox" name="checkvip" value="K2"/>k2会员
                                    <input type="checkbox" name="checkvip" value="VIP"/>vip会员
                                </td>
                            </tr>
                        </table>
                        <table id="company_table">
                            <tr>
                                <td height="35" align="right" bordercolor="#eeeeee" class="tdgrey1">活动公司：</td>
                                <td>
                                    <input name="insuranceCompanyother" type="text" value="" style="width:450px"
                                           class="inputText" id="insuranceCompanyother" disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input name="insuranceCompany" type="hidden" value="" style="width:450px"
                                           class="inputText" id="insuranceCompany" disabled="disabled"
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
                                    <input name="riskCodeother" type="text" value="" style="width:450px"
                                           class="inputText" id="riskCodeother" disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input name="riskCode" type="hidden" value="" style="width:450px" class="inputText"
                                           id="riskCode" disabled="disabled"
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
                                    <input name="productother" type="text" value="" style="width:450px"
                                           class="inputText" id="productother" disabled="disabled"
                                           size="30" readonly="readonly" onmouseover=""/>
                                    <input name="product" type="hidden" value="" style="width:450px" class="inputText"
                                           id="product" disabled="disabled"
                                           size="30" readonly="readonly"/>
                                    <input type="button" id="chooseProduct" name="chooseProduct" value="查 找"
                                           onClick="getProductList()">
                                </td>
                            </tr>
                        </table>
                        <input value="1" type="hidden" id="useTimes" name="useTimes" ztype="String" class="inputText"
                               size="20">
                    </fieldset>
                </td>
            </tr>
        </table>
    </form>
</z:init>
</body>
</html>
