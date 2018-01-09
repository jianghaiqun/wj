jQuery(function(){
  jQuery("#kinMaxShow").kinMaxShow({ 
      height:400,
      mouseEvent:"mouseover",
      button: {
            switchEvent: 'mouseover',
            showIndex: true,
            normal: {
                width: '18px',
                height: '18px',
                lineHeight: '18px',
                left: '16px',
                bottom: '16px',
                fontSize: '12px',
                opacity: 0.8,
                background: '#666666',
                border: '1px solid #999999',
                color: '#CCCCCC',
                marginRight: '6px'
            },
            focus: {
                background: '#CC0000',
                border: '1px solid #FF0000',
                color: '#000000'
            }
          }
  });
//左侧浮动导航初始化
jQuery("#fix_nav_f").fixService({direction:'left',maxposid:"#kinMaxShow"});
jQuery("#fix_nav_right").fixService({maxposid : "#kinMaxShow"});
jQuery(".pagination ul").each(function(){
    if(jQuery(this).find("li").length<=0){
        jQuery(this).parents(".fbx-plpage").remove();
    }
 });
 
})

function doOrder1(curElement, moduleType){
	
	var DocElements = document.getElementById(moduleType+"_orderUl").getElementsByTagName("li");
	for ( var i = 0; i < DocElements.length; i++) {
		if (DocElements[i].id == curElement.id) {
			continue;
		}
		if (DocElements[i].className == "defaultSelect" || DocElements[i].className == "default") {
			DocElements[i].className = "default";
		} else {
			DocElements[i].className = "payDesc";
		}
	}
	if (curElement.className.indexOf("default") >=0) {
		curElement.className = "defaultSelect";
	} else if (curElement.className == "payDesc") {
		curElement.className = "payDescSelect";
	} else if (curElement.className == "payDescSelect") {
		curElement.className = "pauAscSelect";
	} else if (curElement.className == "pauAscSelect") {
		curElement.className = "payDescSelect";
	}

	searchProduct(1,moduleType);
}

function doOrder2(num, moduleType){
	searchProduct(num,moduleType);
}
function searchProduct(pageIndex,moduleType){
	var param={};
	param['pageIndex']=pageIndex;
	param['moduleType']=moduleType;
	// 排序
	var ProductsOrder = "";
	if (document.getElementById(moduleType+"_order_Default").className == "defaultSelect") {
		ProductsOrder = "sortNum asc";
	} else if (document.getElementById(moduleType+"_order_shelfTime").className == "pauAscSelect") {
		ProductsOrder = "shelfTime asc";
	} else if (document.getElementById(moduleType+"_order_shelfTime").className == "payDescSelect") {
		ProductsOrder = "shelfTime desc";
	} else if (document.getElementById(moduleType+"_order_InitPrem").className == "pauAscSelect") {
		ProductsOrder = "(proPrice+0) asc";
	} else if (document.getElementById(moduleType+"_order_InitPrem").className == "payDescSelect") {
		ProductsOrder = "(proPrice+0) desc";
	}
	if (ProductsOrder == '') {
		ProductsOrder = "sortNum asc";
	}
	param['ProductsOrder']=ProductsOrder;
	
	$.ajax({
		url : $("#BasePath").val()+"/nonInsuranceIndex.shtml",
		type : 'post',
		dataType : 'json',
		data : param,
		success : function(data) {
			if(data.products != ''){
				 // 替换产品
				jQuery("#"+moduleType+"_Product").html(data.products+data.productsPageBar);
			}
		}
	});
}	