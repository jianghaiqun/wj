/**
* ------------------------------------------
* @make:吴波
* @version  1.0 
* @des：产品对比.
* @update:郭东奇
* ------------------------------------------
*/
function closecp() {
     document.getElementById("cpdiv").style.display = "none";
     document.getElementById("cpprodu1").style.display = "none";
     document.getElementById("cpprodu2").style.display = "none";
     document.getElementById("cpprodu3").style.display = "none";
     document.getElementById("cpprodu4").style.display = "none";
     document.getElementById("productCodeA").value = "";
     document.getElementById("productCodeB").value = "";
     document.getElementById("productCodeC").value = "";
     document.getElementById("productCodeD").value = "";
      select_num();
}
function subclosecp() {
    document.getElementById("cpdiv").style.display = "none";
    document.getElementById("cpprodu1").style.display = "none";
    document.getElementById("cpprodu2").style.display = "none";
    document.getElementById("cpprodu3").style.display = "none";
    document.getElementById("cpprodu4").style.display = "none";
}
function showcp(productName, thumbnailUrl, productCode, site, productType,logolink,prem) {

	var list = jQuery('span[name=Ajax_Prict_'+productCode+']');
	if(list.length > 0){
		prem = list.html();
		prem = prem.replace("￥","");
	}
	
    var productFlag = "";
    if (productType != null && productType != "") {
         productFlag = productType.substr(0, 1);
    }
    var flag = jQuery.cookie("productType");
    if (flag != null && flag != "" && flag != productFlag) {
         jQuery.cookie("productNameA", "");
         jQuery.cookie("productCodeA", "");
         jQuery.cookie("productPremA", "");
         jQuery.cookie("productNameB", "");
         jQuery.cookie("productCodeB", "");
         jQuery.cookie("productPremB", "");
         jQuery.cookie("productNameC", "");
         jQuery.cookie("productCodeC", "");
         jQuery.cookie("productPremC", "");
         jQuery.cookie("productNameD", "");
         jQuery.cookie("productCodeD", "");
         jQuery.cookie("productPremD", "");
         jQuery.cookie("productType", "");
    } else {
         jQuery.cookie("productType", productFlag);
    }
    document.getElementById("cpdiv").style.display = "block";
    jQuery(".db_box").show();
    var productCodeValue = jQuery.cookie(productCode);
    if(productCodeValue!=null && productCodeValue!=""){
    	 jQuery.cookie(productCode, "");
    	 //var _value = productCodeValue.substring(11);
    	 var _value = "";
    	 var productCodeA_ = jQuery.cookie("productCodeA");
    	 var productCodeB_ = jQuery.cookie("productCodeB");
    	 var productCodeC_ = jQuery.cookie("productCodeC");
    	 var productCodeD_ = jQuery.cookie("productCodeD");
    	 //alert(productCodeA_+"-"+productCodeB_+"-"+productCodeC_+"-"+productCodeD_+"-"+productCode);
    	 if(productCode==productCodeA_){
    		 _value="A";
    	 }else if(productCode==productCodeB_){
    		 _value="B";
    	 }else if(productCode==productCodeC_){
    		 _value="C";
    	 }else if(productCode==productCodeD_){
    		 _value="D";
    	 }else{
    		 if(productCodeA_==null || productCodeA_==""){
    			 _value="A";
    		 }else if(productCodeB_==null || productCodeB_==""){
    			 _value="B";
    		 }else if(productCodeC_==null || productCodeC_==""){
    			 _value="C";
    		 }else if(productCodeD_==null || productCodeD_==""){
    			 _value="D";
    		 }
    	 }
    	 if(_value==null || _value==""){
    		 //showProduct();

             jQuery.tip("最多只能对比四款产品");
    		 return;
    	 }
    	 jQuery.cookie("productName"+_value, "");
         jQuery.cookie("productCode"+_value, "");
         jQuery.cookie("productPrem"+_value, "");
         jQuery("#productname"+_value).html("");
         jQuery("#productCode"+_value).val("");
         jQuery("#productPrem"+_value).html("");
         var cpproduvalue = "cpprodu1";
         if(_value=="A"){
        	 cpproduvalue = "cpprodu1";
         }else if(_value=="B"){
        	 cpproduvalue = "cpprodu2";
         }else if(_value=="C"){
        	 cpproduvalue = "cpprodu3";
         }else if(_value=="D"){
        	 cpproduvalue = "cpprodu4";
         }
         document.getElementById(cpproduvalue).style.display = "none";
         showProduct();
         return;
    }
    if (jQuery.cookie("productCodeA") == null
              || jQuery.cookie("productCodeA") == "") {
         jQuery.cookie("productNameA", productName);
         jQuery.cookie("productCodeA", productCode);
         jQuery.cookie("productPremA", prem);
         jQuery.cookie(productCode, "productCodeA");
         showProduct();
    } else if (jQuery.cookie("productCodeB") == null
              || jQuery.cookie("productCodeB") == "") {
         jQuery.cookie("productNameB", productName);
         jQuery.cookie("productCodeB", productCode);
         jQuery.cookie("productPremB", prem);
         jQuery.cookie(productCode, "productCodeB");
         showProduct();
    } else if (jQuery.cookie("productCodeC") == null
              || jQuery.cookie("productCodeC") == "") {
         jQuery.cookie("productNameC", productName);
         jQuery.cookie("productCodeC", productCode);
         jQuery.cookie("productPremC", prem);
         jQuery.cookie(productCode, "productCodeC");
         showProduct();
    } else if (jQuery.cookie("productCodeD") == null
              || jQuery.cookie("productCodeD") == "") {
         jQuery.cookie("productNameD", productName);
         jQuery.cookie("productCodeD", productCode);
         jQuery.cookie("productPremD", prem);
         jQuery.cookie(productCode, "productCodeD");
         showProduct();
    } else if (jQuery.cookie("productCodeA") != ""
              && jQuery.cookie("productCodeB") != ""
              && jQuery.cookie("productCodeC") != ""
              && jQuery.cookie("productCodeD") != "") {
         showProduct();
         jQuery.tip("最多只能对比四款产品");
    }

      
}
function showProduct() {
    var productNameA = jQuery.cookie("productNameA");
    var productNameB = jQuery.cookie("productNameB");
    var productNameC = jQuery.cookie("productNameC");
    var productNameD = jQuery.cookie("productNameD");
    var productCodeA = jQuery.cookie("productCodeA");
    var productCodeB = jQuery.cookie("productCodeB");
    var productCodeC = jQuery.cookie("productCodeC");
    var productCodeD = jQuery.cookie("productCodeD");
    var productPremA = jQuery.cookie("productPremA");
    var productPremB = jQuery.cookie("productPremB");
    var productPremC = jQuery.cookie("productPremC");
    var productPremD = jQuery.cookie("productPremD");
    if (productCodeA != null && productCodeA != "") {
         document.getElementById("cpprodu1").style.display = "block";
         jQuery("#productnameA").html(productNameA);
         jQuery("#productCodeA").val(productCodeA);
         jQuery("#productPremA").html("￥"+productPremA);
    }
    if (productCodeB != null && productCodeB != "") {
         document.getElementById("cpprodu2").style.display = "block";
         jQuery("#productnameB").html(productNameB);
         jQuery("#productCodeB").val(productCodeB);
         jQuery("#productPremB").html("￥"+productPremB);
    }
    if (productCodeC != null && productCodeC != "") {
         document.getElementById("cpprodu3").style.display = "block";
         jQuery("#productnameC").html(productNameC);
         jQuery("#productCodeC").val(productCodeC);
         jQuery("#productPremC").html("￥"+productPremC);
    }
    if (productCodeD != null && productCodeD != "") {
         document.getElementById("cpprodu4").style.display = "block";
         jQuery("#productnameD").html(productNameD);
         jQuery("#productCodeD").val(productCodeD);
         jQuery("#productPremD").html("￥"+productPremD);
    }
      select_num();
}
function closecp1() {
    document.getElementById("cpprodu1").style.display = "none";
    jQuery.cookie(jQuery.cookie("productCodeA"), "");
    document.getElementById("productCodeA").value = "";
    jQuery.cookie("productNameA", "");
    jQuery.cookie("productCodeA", "");
    jQuery.cookie("productPremA", "");
    select_num();
}
function closecp2() {
    document.getElementById("cpprodu2").style.display = "none";
    jQuery.cookie(jQuery.cookie("productCodeB"), "");
    document.getElementById("productCodeB").value = "";
    jQuery.cookie("productNameB", "");
    jQuery.cookie("productCodeB", "");
    jQuery.cookie("productPremB", "");
     select_num();
}
function closecp3() {
    document.getElementById("cpprodu3").style.display = "none";
    jQuery.cookie(jQuery.cookie("productCodeC"), "");
    document.getElementById("productCodeC").value = "";
    jQuery.cookie("productNameC", "");
    jQuery.cookie("productCodeC", "");
    jQuery.cookie("productPremC", "");
     select_num();
}
function closecp4() {
    document.getElementById("cpprodu4").style.display = "none";
    jQuery.cookie(jQuery.cookie("productCodeD"), "");
    document.getElementById("productCodeD").value = "";
    jQuery.cookie("productNameD", "");
    jQuery.cookie("productCodeD", "");
    jQuery.cookie("productPremD", "");
     select_num();
}
/*对比清空按钮*/
function close_all(){
closecp1();
closecp2();
closecp3();
closecp4();
subclosecp();
jQuery(".db_box").hide();
}
/*侧边栏对比显隐切换*/
var db_dom = jQuery("#cpdiv");
function showcd(){
          if(db_dom.is(":visible")){
                    db_dom.hide();

          }else{
                  db_dom.show();

          }
}
// /*产品比较模块定位*/
// function sel_db(x, y) {
// 	if (jQuery(".wrapper").offset() != null && jQuery(".wrapper").width() != null) {
// 	    l = jQuery(".wrapper").offset().left;
// 	    w = jQuery(".wrapper").width();
// 	    jQuery("#cpdiv").css("right", l  + "px");
// 	    jQuery("#cpdiv").css("top", y + "px");
// 	}
// }
/*最小化对比框*/
function close_h(){
 db_dom.animate({
opacity: 'hide'
}, 500);
}
function begincompare(site) {
	var productCodeA = document.getElementById("productCodeA").value;
	var productCodeB = document.getElementById("productCodeB").value;
	var productCodeC = document.getElementById("productCodeC").value;
	var productCodeD = document.getElementById("productCodeD").value;
	var eriskType = jQuery.cookie("productType");
	
	document.getElementById("productCodeA").value = "";
	document.getElementById("productCodeB").value = "";
	document.getElementById("productCodeC").value = "";
	
	if(productCodeA != "" || productCodeB != "" || productCodeC != "" || productCodeD != ""){
		window.open(site + "/shop/non_auto!list.action?eriskType=" + eriskType
				+ "&productA=" + productCodeA + "&productB=" + productCodeB
				+ "&productC=" + productCodeC + "&productD=" + productCodeD);
	}else{
		//alert("请选择您要加入对比的商品");
		art.dialog({
			title:'提示',
		    icon: 'error',
		    content: '请选择您要加入对比的商品',
		    cancelVal: '确认',
		    cancel: true
		});
	}
	close_all();
}
function select_num(){
       var vs_num = jQuery(".comparedetail").not(':hidden').length;
       jQuery("#g-vs-num").html(vs_num);
}
// function moveR() {
//     var shop_h = jQuery(".comparediv").height();
//     var _x = document.documentElement.scrollTop + document.body.scrollTop
//               + document.documentElement.clientHeight - shop_h;

//     document.getElementById("cpdiv").style.top = _x-85 + "px";
//     setTimeout("moveR();", 1);
//     window.onresize = "moveR";
// }
// jQuery(window).resize(function() {
// sel_db(10,10);
// });

function initProduct() {
	 //显示产品对比
	var flag = jQuery.cookie("productType");
	var tFlag = "";
	if(jQuery("#ProductType").val()!=null && jQuery("#ProductType").val()!="" && jQuery("#ProductType").val()!="undefined"){
		var tFlag = jQuery("#ProductType").val().substring(0,1);
	}
	if(flag==tFlag){
		 
	    var productCodeA = jQuery.cookie("productCodeA");
	    var productCodeB = jQuery.cookie("productCodeB");
	    var productCodeC = jQuery.cookie("productCodeC");
	    var productCodeD = jQuery.cookie("productCodeD");
	    if((productCodeA!=null && productCodeA!="") || (productCodeB!=null && productCodeB!="")
	    		|| (productCodeC!=null && productCodeC!="")|| (productCodeD!=null && productCodeD!="")){
	    	jQuery("#cpdiv").show();
			jQuery("#showCompare").show();
			showProduct();
	    }
	}else{
		jQuery("#cpdiv").hide;
	}
	//moveR();
	// sel_db(10,10);
	try {
		if (searchConditoinNames.length > 0) {
			initsearchConditoins(searchConditoinNames);
		}
	} catch (e) {
		var order_default = document.getElementById("order_default");
		/* zhangjinquan 11180 2012-10-20 增加判断解决产品详细页js报错问题 */
		if ((null != order_default) && (typeof(order_default) != "undefined"))
		{
			order_default.className = "onesel";
		}
	}
}
/*
 * 产品查询
 */
function searchInfo(searchCode, ele) {
	if (ele.parentNode.className == "li_selected") {
		return;
	}
	var searchEles = document.getElementById(searchCode).getElementsByTagName(
			"LI");
	changeSearchInfoStyle(searchEles, ele);
	doSearch();
}

function changeSearchInfoStyle(DocElements, curElement) {
	for ( var i = 0; i < DocElements.length; i++) {
		DocElements[i].className = "";
	}
	curElement.parentNode.className = "li_selected";
}
function changeOrderInfoStyle(curElement) {
	var DocElements = document.getElementById("divSearchOrder")
			.getElementsByTagName("SPAN");
	for ( var i = 0; i < DocElements.length; i++) {
		if (DocElements[i].id == curElement.id) {
			continue;
		}
		if (DocElements[i].className == "one"
				|| DocElements[i].className == "onesel") {
			DocElements[i].className = "one";
		} else {
			DocElements[i].className = "two";
		}
	}
	if (curElement.className == "one") {
		curElement.className = "onesel";
	} else if (curElement.className == "two") {
		curElement.className = "twoasc";
	} else if (curElement.className == "twoasc") {
		curElement.className = "twodesc";
	} else if (curElement.className == "twodesc") {
		curElement.className = "twoasc";
	}
	
	if (curElement.className == "order-sale") {
		curElement.className = "order-sale selct_sale";
	} else if (curElement.className == "order-sale selct_sale") {
		curElement.className = "order-sale";
	}
}

function doSearch() {
	var rootEle = document.getElementById("radiolist");
	if (rootEle) {
		var dc = new DataCollection();
		var ulNodeList = rootEle.getElementsByTagName("UL");
		var tNoLimitFlag = true;
		for ( var i = 0; i < ulNodeList.length; i++) {
			for ( var j = 0; j < ulNodeList[i].childNodes.length; j++) {
				if (ulNodeList[i].childNodes[j].className == "li_selected"
						&& ulNodeList[i].childNodes[j].firstChild.innerHTML != "不限") {
					dc.add(ulNodeList[i].childNodes[j].firstChild
							.getAttribute("name"),
							ulNodeList[i].childNodes[j].firstChild.innerHTML);
					tNoLimitFlag = false;
					break;
				}
			}
		}

		if (document.getElementById("order_Popular").className == "twoasc") {
			tNoLimitFlag = false;
			dc.add("ProductsOrder", "Popular");
		} else if (document.getElementById("order_Popular").className == "twodesc") {
			tNoLimitFlag = false;
			dc.add("ProductsOrder", "Popular desc");
		} else if (document.getElementById("order_SalesVolume").className == "twoasc") {
			tNoLimitFlag = false;
			dc.add("ProductsOrder", "SalesVolume");
		} else if (document.getElementById("order_SalesVolume").className == "twodesc") {
			tNoLimitFlag = false;
			dc.add("ProductsOrder", "SalesVolume desc");
		} else if (document.getElementById("order_InitPrem").className == "twoasc") {
			tNoLimitFlag = false;
			dc.add("ProductsOrder", "InitPrem");
		} else if (document.getElementById("order_InitPrem").className == "twodesc") {
			tNoLimitFlag = false;
			dc.add("ProductsOrder", "InitPrem desc");
		}
		
		
		if (tNoLimitFlag)
		{
			var nowLink = window.location.href;
			window.location.href = nowLink.substring(0,nowLink.lastIndexOf("/"))+"/";
			return;
		}
		

		dc.add("ProductType", productType);
		dc.add("CatalogID", catID);
		Server.sendRequest("com.sinosoft.product.ProductSearch.search", dc,
				function(response) {
					
					if (response.Status == 0) {
						// 错误处理
					} else if (response.Status == 1) {
						window.location.href = response.get("resultPageURL");
						
					}
				});
	}
}

function doOrder(ele) {
	changeOrderInfoStyle(ele);
	doSearch();
}

function initsearchConditoins(arr) {
	var arr_value = searchConditoinValues;
	for ( var i = 0; i < arr.length; i++) {
		var ulEle = document.getElementById("SearchCondition_" + arr[i]);
		if (ulEle) {
			for ( var j = 0; j < ulEle.childNodes.length; j++) {
				if (ulEle.childNodes[j].tagName == "LI") {
					if (ulEle.childNodes[j].firstChild.innerHTML == arr_value[i]) {
						ulEle.childNodes[j].className = "li_selected";
						break;
					} else if (ulEle.childNodes[j].firstChild.innerHTML == "不限"
							&& arr_value[i] == "0") {
						ulEle.childNodes[j].className = "li_selected";
						break;
					} else {
						ulEle.childNodes[j].className = "";
					}
				}
			}
		}
	}
	var orderType = searchConditoinValues[searchConditoinValues.length - 1];
	if (orderType != "0") {
		if (orderType.indexOf(" desc") > 0) {
			document.getElementById("order_"
					+ orderType.substring(0, orderType.indexOf(" desc"))).className = "twodesc";
		} else {
			document.getElementById("order_" + orderType).className = "twoasc";
		}
	}
}
//产品对比模块定位
jQuery("#fix_vs").fixService();
select_num();

// 产品对比模块显示隐藏
jQuery(".fixVs").click(function(){
 var vsList =  jQuery("#cpdiv")
       if (vsList.is(':visible')==false) {
          vsList.show();
       }else{
          vsList.hide();
       }
})


