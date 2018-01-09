jQuery(document).ready(function() {
    var reqSnDieflag = sessionStorage.getItem("reqSnDieFlag-" + $("#paySn").val());
    sessionStorage.removeItem("reqSnDieFlag-" + $("#paySn").val());
    var kid = $("#kid").val();
    var orderSn = $("#orderSn").val();
    var paySn = $("#paySn").val();
    var channelsn = $("#channelsn").val();

    // 倒计时
    new timeCountDown({
        whichPage: "sign",
        deadLine: 60,
        startHtml: "s",
        endHtml: "点击验证",
        callBack: function() {},
        sendCode: function() {
            var params = {};
            $("#bankinfo").find("input").each(function() {
                var that = $(this);
                var name = that.attr("name");
                var val = that.val();
                if (name) {
                    params[name] = val;
                }
            });
            params["paySn"] = paySn;
            params["reqSnDieflag"] = reqSnDieflag;
            jQuery.ajax({
                url: sinosoft.base + "/shop/pay!sendAllinpayAuthCode.action",
                data: params,
                dataType: "json",
                success: function(data) {
                    if(data && data.message && data.message != ""){
                        artDialogTip(data.message);
                    }else{
                        artDialogTip("发送失败");
                    }
                }
            });
        }
    });

    // 返回修改
    $("#backedit").bind("click",
    function(e) {
        var uri = $(this).attr("href");
        e.preventDefault();
        artDialogCfm(function() {
            sessionStorage.setItem("reqSnDieFlag-" + $("#paySn").val(), "dieflag");
            window.location.href = uri;
        },
        "您确认返回修改信息？");
    });

    // 点击确认支付
    $("#submitBtn").bind("click",
    function(e) {
        e.preventDefault();
        if (!$("#accept_p").is(':checked')) {
            artDialogTip("请确认您同意了以上扣款授权！");
            return;
        }
        var authCode = $("input[name='authCode']").val();
        if (authCode == "") {
            artDialogTip("请先填写验证码！");
            return;
        }
        var params = {};
        $("#bankinfo").find("input").each(function() {
            var that = $(this);
            var name = that.attr("name");
            var val = that.val();
            params[name] = val;
        });
        params["channelsn"] = channelsn;
        params["sendSmsPaySn"] = paySn;
        params["reqSnDieflag"] = reqSnDieflag;
        params["orderSn"] = orderSn;
        jQuery.blockUI({
            overlayCSS:{backgroundColor:'#fff',opacity:0.0},
            showOverlay:true,
            message:""
        }); 
        jQuery("#msg_2").show();
        $.ajax({
            url: sinosoft.base + "/shop/pay!allinpayPay.action?CouponSn="+$("#CouponSn").val()+"&offsetPoint="+$("#offsetPoint").val()+"&artLoginFlag=1&payType=tlzf&OrdId=" + orderSn + "&sdorderId=" + $("#sdorderId").val() + "&paySn=" + paySn + "&KID=" + kid + "&channelsn=" + channelsn,
            type: "post",
            dataType: "json",
            data: params,
            timeout:60000,
            success: function(data) {
                jQuery("#msg_2").hide();
                try{
                    if (data.status == 'success') {
                        var payStatus = data.data.payStatus;
                        var newPaySn = data.data.paySn;
                        if(payStatus == "waiting"){
                            window.location.href = sinosoft.base + "/shop/pay!zjzferror.action?orderSn="+orderSn+"&wait=true&errormsg=&OrdId="+orderSn;
                        }else{
                            window.location.href = sinosoft.base + "/shop/alipay!returnUrl.action?paySn="+newPaySn;
                        }
                    } else {
                        var errorMessage = data.message;
                        if(data.resultCode == "prePayError"){
                            jQuery.unblockUI();
                            artDialogTip(errorMessage);
                        }else{
                            window.location.href = sinosoft.base + "/shop/pay!zjzferror.action?orderSn="+orderSn+"&wait=false&errormsg="+errorMessage+"&OrdId="+orderSn;
                        }
                    }
                } catch(error){
                    jQuery.unblockUI();
                }
            },
            error:function(data){
                jQuery.unblockUI();
                jQuery("#msg_2").hide();
            }
        });
    });

    // 查看授权
    $("#showStatement").bind("click", function(e) {
        e.preventDefault();
        art.dialog({
            content : document.getElementById("clm_text"),
            title : '年金险授权书',
            width:600,
            height:100,
            button: [{
                name: '确认'
            }]
        });
    });

    //artDialog 提示框
    function artDialogTip(tipTxt) {
        art.dialog({
            id: 'shaop_car',
            title: '提示',
            content: '<div style="padding:10px 40px; text-align: center;">' + tipTxt + '</div>',
            lock: true,
            background: '#fff',
            opacity: '0',
            fixed: true,
            button: [{
                name: '确认'
            }]
        });
    }

    //artDialog 确认框
    function artDialogCfm(func, tipTxt) {
        art.dialog({
            id: 'shaop_car',
            title: '信息确认',
            content: '<div style="padding:10px 40px; text-align: center;">' + tipTxt + '</div>',
            lock: true,
            background: '#fff',
            opacity: '0',
            fixed: true,
            button: [{
                name: '确认',
                callback: function() {
                    func();
                }
            },
            {
                name: '取消',
                callback: function() {}
            }]

        });
    }

});