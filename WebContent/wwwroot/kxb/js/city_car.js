var cpslink="http://www.4008000000.com/cpchexian/media/jisuanqi.shtml?WT.mc_id=wc03-kxbw-1003";
var cpsname="pingan";
var pageLink="";
var comname="中国平安";
var provicecity={
    city:[
  ["北京","天津","上海","重庆","香港","澳门"],
  ["石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水"],
  ["太原","大同","阳泉","长治","晋城","朔州","晋中","运城","忻州","临汾","吕梁"],
  ["呼和浩特","包头","乌海","赤峰","通辽","鄂尔多斯","呼伦贝尔","巴彦淖尔","乌兰察布","兴安","锡林郭勒","阿拉善"],
  ["沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛"],
  ["长春","吉林","四平","辽源","通化","白山","松原","白城","延边"],
  ["哈尔滨","齐齐哈尔","鸡西","鹤岗","双鸭山","大庆","伊春","佳木斯","七台河","牡丹江","黑河","绥化","大兴安岭"],
  ["南京","苏州","扬州","无锡","徐州","常州","南通","连云港","淮安","盐城","镇江","泰州","宿迁"],
  ["杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州","丽水"],
  ["合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安","亳州","池州","宣城"],
  ["福州","宁德","南平","厦门","莆田","三明","泉州","漳州","龙岩"],
  ["南昌","上饶","萍乡","九江","景德镇","新余","鹰潭","赣州","吉安","宜春","抚州"],
  ["济南","青岛","淄博","枣庄","东营","烟台","潍坊","威海","济宁","泰安","日照","莱芜","临沂","德州","聊城","滨州","菏泽"],
  ["郑州","开封","洛阳","平顶山","焦作","鹤壁","新乡","安阳","濮阳","漯河","许昌","三门峡","南阳","商丘","信阳","周口","驻马店"],
  ["武汉","十堰","襄樊","鄂州","黄石","荆州","宜昌","荆门","孝感","黄冈","咸宁","随州","恩施"],
  ["长沙","株洲","湘潭","岳阳","邵阳","常德","衡阳","张家界","益阳","郴州","永州","怀化","娄底","湘西"],
  ["广州","清远","潮州","东莞","珠海","深圳","汕头","韶关","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","阳江","河源","中山","揭阳","云浮"],
  ["南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林","百色","贺州","河池","来宾","崇左"],
  ["海口","三亚"],
  ["成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","宜宾","广安","达州","眉山","雅安","巴中","资阳","阿坝","甘孜","凉山"],
  ["贵阳","六盘水","遵义","安顺","铜仁","毕节","黔西南","黔南"],
  ["昆明","曲靖","玉溪","保山","昭通","丽江","普洱","临沧","文山","红河","西双版纳","楚雄","大理","德宏","怒江","迪庆"],
  ["拉萨","昌都","山南","日喀则","那曲","阿里","林芝"],
  ["西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林","安康","商洛"],
  ["兰州","白银","定西","敦煌","嘉峪关","金昌","天水","武威","张掖","平凉","酒泉","庆阳","临夏","陇南"],
  ["西宁","海东","海北","黄南","海南","果洛","玉树","海西"],
  ["银川","石嘴山","吴忠","固原","中卫"],
  ["乌鲁木齐","克拉玛依","吐鲁番","哈密","和田","阿克苏","喀什","克孜勒苏柯尔克孜","巴音郭楞蒙古","昌吉","博尔塔拉蒙古","伊犁哈萨克","阿勒泰"],
  ["台北","高雄","基隆","台中","台南","新竹","嘉义"],
  ["国外"]
  ],
  cityInit:function(proviceObj,cityObj){
    var city=eval(provicecity.city);
 
    //得到对应省份的城市数组
    var provinceCity=city[$(proviceObj).get(0).selectedIndex - 1];
    //清空城市下拉框，仅留提示选项
    $(cityObj).find('option[value!=""]').remove(); 
    
     if(provinceCity!=null)
     {
        for(var i=0;i<provinceCity.length;i++)
         {
           $(cityObj).append("<option value='"+provinceCity[i]+"'>"+provinceCity[i]+"</option>");
         }
     }  
  }
}
var citycarnoall={
    num:[
  ["京","津","沪","渝","Z","M"],
  ["冀A","冀B","冀C","冀D","冀E","冀F","冀G","冀H","冀J","冀R","冀T"],
  ["晋A","晋B","晋C","晋D","晋E","晋F","晋K","晋M","晋H","晋L","晋J"],
  ["蒙A","蒙B","蒙C","蒙D","蒙G","蒙K","蒙E","蒙L","蒙J","蒙F","蒙H","蒙M"],
  ["辽A","辽B","辽C","辽D","辽E","辽F","辽G","辽H","辽J","辽K","辽L","辽M","辽N","辽P"],
  ["吉A","吉B","吉C","吉D","吉E","吉F","吉J","吉G","吉H"],
  ["黑A","黑B","黑G","黑H","黑J","黑E","黑F","黑D","黑K","黑C","黑N","黑M","黑P"],
  ["苏A","苏B","苏C","苏D","苏E","苏F","苏G","苏H","苏J","苏K","苏L","苏M","苏N"],
  ["浙A","浙B","浙C","浙F","浙E","浙D","浙G","浙H","浙L","浙J","浙K"],
  ["皖A","皖B","皖C","皖D","皖E","皖F","皖G","皖H","皖J","皖M","皖K","皖L","皖N","皖S","皖R","皖P"],
  ["闽A","闽D","闽B","闽G","闽C","闽E","闽H","闽F","闽J"],
  ["赣A","赣H","赣J","赣G","赣K","赣L","赣B","赣D","赣C","赣F","赣E"],
  ["鲁A","鲁B","鲁C","鲁D","鲁E","鲁F","鲁G","鲁H","鲁J","鲁K","鲁L","鲁S","鲁Q","鲁N","鲁P","鲁M","鲁R"],
  ["豫A","豫B","豫C","豫D","豫E","豫F","豫G","豫H","豫J","豫K","豫L","豫M","豫R","豫N","豫S","豫P","豫Q","豫U"],
  ["鄂A","鄂B","鄂C","鄂E","鄂F","鄂G","鄂H","鄂K","鄂D","鄂J","鄂L","鄂S","鄂Q","鄂M","鄂N","鄂R","鄂P"],
  ["湘A","湘B","湘C","湘D","湘E","湘F","湘J","湘G","湘H","湘L","湘M","湘N","湘K","湘U"],
  ["粤A","粤F","粤B","粤C","粤D","粤E","粤J","粤G","粤K","粤H","粤L","粤M","粤N","粤P","粤Q","粤R","粤S","粤T","粤U","粤V","粤W"],
  ["桂A","桂B","桂C","桂D","桂E","桂P","桂N","桂R","桂K","桂L","桂J","桂M","桂G","桂F"],
  ["琼A","琼B"],
  ["川A","川C","川D","川E","川F","川B","川H","川J","川K","川L","川R","川Z","川Q","川X","川S","川T","川Y","川M","川U","川V","川W"],
  ["贵A","贵B","贵C","贵G","贵D","贵E","贵F","贵H"],
  ["云A","云D","云F","云M","云C","云P","云J","云S","云E","云G","云H","云K","云L","云N","云Q","云R"],
  ["藏A","藏B","藏C","藏D","藏E","藏F","藏G"],
  ["陕A","陕B","陕C","陕D","陕E","陕J","陕F","陕K","陕G","陕H"],
  ["甘A","甘B","甘C","甘D","甘E","甘H","甘G","甘L","甘F","甘M","甘J","甘K","甘N","甘P"],
  ["青","青","青","青","青","青","青","青",],
  ["宁A","宁B","宁C","宁D","宁E"],
  ["新A","新J","新K","新L","新B","新E","新M","新N","新P","新Q","新R","新D","新G"],
  ["台","台","台","台","台","台","台"],
  ["F"]
  ],
  cityCarnoInit:function(proviceObj,cityObj,carnoObj){
    var citycarno=eval(citycarnoall.num);
 
    //得到对应省份的城市数组
    var provinceCityCarnum=citycarno[$(proviceObj).get(0).selectedIndex - 1];
    jQuery("#carnoObj").val("");
    if(provinceCityCarnum!=null)
     {
     	jQuery("#carnum").val(provinceCityCarnum[$(cityObj).get(0).selectedIndex - 1]);
     }  
  }
}
jQuery(function () {
	// 车险计算器内车险品牌模拟ridoe切换
		jQuery("#counter_pplist>a").click(
			function(){
					jQuery(this).addClass("counter_sels").siblings(".counter_sels").removeClass("counter_sels");
				    if(jQuery(this).attr("rel")=="pa"){
						cpslink="http://www.4008000000.com/cpchexian/media/jisuanqi.shtml?WT.mc_id=wc03-kxbw-1003";
						cpsname="pingan";
						comname="中国平安";
					}
					if(jQuery(this).attr("rel")=="tpy"){
						cpslink="http://www.ecpic.com.cn/cpiccar/sale/quickQuoteCPIC/quickQuoteEntry?otherSourc=533";
						cpsname="taipingyang";
						comname="太平洋保险";
					}
					if(jQuery(this).attr("rel")=="yg"){
						cpslink="http://chexian.sinosig.com/simplePremium/territory_marketing.jsp?utm_source=P05-kaixinbao&utm_campaign=car&areaCode=W06010003";
						cpsname="yangguang";
						comname="阳光保险";
					}
					if(jQuery(this).attr("rel")=="dd"){
						cpslink="http://www.95590.cn/ebiz/view/onlinePartner/opBaseInfo.jsp?utm_source=kaixinbao";
						cpsname="dadi";
						comname="大地保险";
					}
					if(jQuery(this).attr("rel")=="astp"){
						cpslink="http://chexian.axatp.com/toPreparation.do?cityCode=310100&localProvinceCode=310000&departmentCode=5&linkResource=&selectPayChannel=&isAgent=0&isRenewal=&ecInsureId=&select_city=%C9%CF%BA%A3%CA%D0&planDefineId=3&=&rt=0&ms=";
						cpsname="anshengtianping";
						comname="安盛天平保险";
					}
			})
	 //初始化
   provicecity.cityInit($('#sltProvince'),$('#sltCity'));
   //绑定事件
   $('#sltProvince').bind("change",function(){
        provicecity.cityInit($('#sltProvince'),$('#sltCity'));
        jQuery("#carnum").val("");
   });
   $('#sltCity').bind("change",function(){
        citycarnoall.cityCarnoInit($('#sltProvince'),$('#sltCity'),$('#carnum'));
   });
})
/*车险立即报价方法*/
function offerPrice(pagelink) {
		pageLink=pagelink;
		var value = jQuery("#sltProvince").val();
		if(value==''||value==null||value=="省份"){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请选择省份");
			return;
		}
		var value = jQuery("#sltCity").val();
		if(value==''||value==null||value=="城市"){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请选择城市");
			return;
		}
		if(!CheckPlateNo()){
			return;
		}
		var value = jQuery("#CarOwner").val();
		if(value==''||value==null){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请输入车主姓名");
			return;
		}
		var value = jQuery("#ContactPhone").val();
		if(value==''||value==null){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请输入联系电话");
			return;
		}
		var patrn= /^(1[3458][0-9]{9})|(15[89][0-9]{8})$/;
		value = jQuery("#ContactPhone").val();
		if (!patrn.test(value)){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请输入正确的手机号码!");
			return;
		}
		var value = jQuery("#BuyDate").val();
		if(value==''||value==null){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请选择购车时间");
			return;
		}
		var value = jQuery("#CarValue").val();
		if(value==''||value==null){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请输入车价");
			return;
		}
		 var chrnum = /^0.*$/;
		value = jQuery("#CarValue").val();
		if(chrnum.test(value)){
			jQuery("#error_message").show();
			jQuery("#error_message").html("车价不允许以0开头!");
			return;
		}
		if(!CheckCarValue()){
			return;
		}
		var value = jQuery("#InsuranceDate").val();
		if(value==''||value==null){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请选择保险到期时间");
			return;
		}
		var value = jQuery("#ContactEmail").val();
		if(value==''||value==null){
			jQuery("#error_message").show();
			jQuery("#error_message").html("请输入邮箱地址");
			return;
		}
		var value = jQuery("#ContactEmail").val();
		var mailreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (!mailreg.test(value)) {
			jQuery("#error_message").show();
			jQuery("#error_message").html("邮箱地址格式错误");
			return;
		}
		var CarProperty = jQuery("#CarProperty").val();
		// 车牌中不能含有警、军、海、空、北、沈、兰、济、南、广、成
		var carNb = jQuery("#carnum").val();
		var str = "警,军,海,空,北,沈,兰,济,南,广,成";

		for (i = 0; i < str.length; i++) {
			if (carNb.indexOf(str.split(",")[i]) != -1) {
				jQuery("#error_message").show();
				jQuery("#error_message").html("车牌首字不能以\"警、军、海、空、北、沈、兰、济、南、广、成\" 开头");
				return;
			}
		}
		// 车主姓名不允许填写“不详”、“不祥”、“未知”、“不知道”、“公司”、“有限”、“集团”、“股份”词语
		var CarOwner = jQuery("#CarOwner").val();
		var str = "不详,不祥,未知,不知道,公司,有限,集团,股份";
		for (j = 0; j < str.length; j++) {
			if (CarOwner.indexOf(str.split(",")[j]) != -1) {
				jQuery("#error_message").show();
				jQuery("#error_message").html("姓名不允许填写\"不详、不祥、未知、不知道、公司、有限、集团、股份\" 词语");
				return;
			}
		}
		SaveOfferPriceData(pageLink);
}
/*车牌号码校验*/
function CheckPlateNo() {
	var carNb = $("#carnum").val();
	var chrnum = /^([\u4e00-\u9fa5]|[\uFE30-\uFFA0])[a-zA-Z][a-zA-Z0-9]{5,6}$/;
	var num =  /\s+/;
	if (carNb == "") {
		jQuery("#error_message").show();
		jQuery("#error_message").html("请输入车牌号码!");
		return false;
	} else if(num.test(carNb)){
		jQuery("#error_message").show();
		jQuery("#error_message").html("车牌号码不可输入空格!");
		return false;
	}else if (!chrnum.test(carNb)) {
		jQuery("#error_message").show();
		jQuery("#error_message").html("车牌号码格式错误!");
		return false;
	}else {
		jQuery("#error_message").hide();
		jQuery("#error_message").html("");
	}
	return true;
}
/*校验车价*/
function CheckCarValue() {
	var CarValue = $("#CarValue").val();
	var numAll = /^\d+(|(\.\d{2})?|(\.\d{1})?)$/;
	if (!numAll.test(CarValue)){
		jQuery("#error_message").show();
		jQuery("#error_message").html("车价格式错误!");
		return false;
		
	} else if (( parseInt(CarValue) > 9999) || ( parseInt(CarValue) < 0) ) {
		jQuery("#error_message").show();
		jQuery("#error_message").html("车价需小于10000万元!");
		return false;
	}else {
		jQuery("#error_message").hide();
		jQuery("#error_message").html("");
	}
	return true;
}
/*保存车险用户立即报价数据*/
function SaveOfferPriceData(pagelink){
	var carUser = encodeURIComponent(encodeURIComponent(jQuery("#CarOwner").val()));
	var Phone = jQuery("#ContactPhone").val();
	var carNO = encodeURIComponent(encodeURIComponent(jQuery("#carnum").val()));
	var InsuranceDate = jQuery("#InsuranceDate").val();
	var BuyDate = jQuery("#BuyDate").val();
	var carValue = jQuery("#CarValue").val();
	var carProperty= jQuery("#CarProperty").val();
	var Email = encodeURIComponent(encodeURIComponent(jQuery("#ContactEmail").val()));
	var carAddress = encodeURIComponent(encodeURIComponent(jQuery("#sltCity").val()));
	var sltProvince = encodeURIComponent(encodeURIComponent(jQuery("#sltProvince").val()));
	jQuery.ajax({
		url: sinosoft.base+"/car/transition!saveMiddle.action?carUser="+carUser+"&carPhone="+Phone+"&carNO="+carNO
		+"&InsuranceDate="+InsuranceDate+"&BuyDate="+BuyDate+"&carValue="+carValue+"&carProperty="+carProperty
		+"&Email="+Email+"&carAddress="+carAddress+"&sltProvince="+sltProvince+"&cpsname="+cpsname,
		dataType: "json",
		async: false,
		success: function(data) {
				//var str = data.jumpPath;
			 	jumpCarCompany(pageLink);
			}
		});
}
/*跳转到第三方保险公司*/
function jumpCarCompany(pagelink){
	var carUser = jQuery("#CarOwner").val();
	 var carPhone = jQuery("#ContactPhone").val();
	 var carNO = jQuery("#carnum").val();
	 if(cpslink==000 || cpslink == '#' || cpslink == 'javascript:void(0);' ){
		 //history.go(0);
	 }else{
			    	 var url = escape(comname);
			    	 var win =window.open("");
			    	 win.location.href=pagelink+'?'+cpslink+'*'+url;
		 
	 }
}
