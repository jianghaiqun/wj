var sinosoft = {
    front: "http://localhost:8080",
    base: "http://localhost:8080/wj",
    httpsbase: "http://localhost:8080/wj",
    staticPath:"http://localhost:8080/",
    jsPath:"http://localhost:8080/js",
    cssPath:"http://localhost:8080/style",
    tripPayBase:"http://temai.shuaipiao.com",
    bfdClientId: " Ckaixinbao",
    xiaoNeng_CustomerService: "kf_9401_1473649732984"
};

require.config({
    baseUrl: "",
    // urlArgs: "version=0.0.1",
    waitSeconds: 0,
    paths: {
        jquery: sinosoft.staticPath+"js/jquery-1.8.3.min",
        jquery_cookie: sinosoft.staticPath+"js/jquery.cookie",
        jquery_form: sinosoft.staticPath+"js/jquery.form",
        art_dialog: sinosoft.staticPath+"js/artDialog",
        art_iftools: sinosoft.staticPath+"js/iframeTools",
        wdate_picker: sinosoft.staticPath+"js/redesign/My97DatePicker/WdatePicker",
        calendar: sinosoft.staticPath+"js/wj_kxb/my-calendar.min",
        "header": sinosoft.staticPath+"js/redesign/re_header",
        "footer": sinosoft.staticPath+"js/redesign/re_footer",
        "login": sinosoft.staticPath+"js/login",
        "jqupdate": sinosoft.staticPath+"js/jqueryupdate",
        "productcompare": sinosoft.staticPath+"js/redesign/re_productCompare",
        "detail": sinosoft.staticPath+"js/wj_kxb/mod_intro",
        "cashValue": sinosoft.staticPath+"js/wj_kxb/cash_value",
        "jquery_SuperSlide": sinosoft.staticPath+"/js/redesign/jquery.SuperSlide.2.1.1",
        "guide":sinosoft.staticPath+"js/wj_kxb/mod_guide",
        "renewal":sinosoft.staticPath+"js/wj_kxb/mod_renewal",
        "validate":sinosoft.staticPath+"js/redesign/validate",
        "showBenefit": sinosoft.staticPath+"js/wj_kxb/show_benefit"
    },
    shim: {
        jquery: {
            exports: "jQuery"
        },
        jquery_cookie: {
            deps: ["jquery"]
        },
        jquery_form: {
            deps: ["jquery"]
        },
        art_iftools: {
            deps: ["art_dialog"]
        },
        "header" : {
            deps: ["jquery"]
        },
        "footer" : {
            deps: ["jquery", "jquery_cookie"]
        },
        "login" : {
            deps: ["jquery"]
        },
        "jqupdate": {
            deps: ["jquery"]
        },
        "productcompare": {
            deps: ["jquery", "art_dialog", "footer"]
        },
        "guide": {
            deps: ["jquery"]
        },
        "renewal": {
            deps: ["jquery"]
        },
        "validate":{
            deps: ["jquery"]
        }
    }
});