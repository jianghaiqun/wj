/*
#@des:开具发票js
*@maker:wangwenying
*@time:2015-06-18
*/

(function() {
    var oAddCont = jQuery("#addEmailAddress");
    var oRequire = jQuery("#sendEmail").find("a.require");

	var oAddBillTitle = jQuery("#addBillTitle");
    var orequireTitleUpdate = jQuery("#invoiceHead").find("a.requireTitleUpdate");
    var oUpload = jQuery("#btn_upload");
    var oDefault = jQuery("#sendEmail").find("a.deliverAddrDefault");
    var oDelete = jQuery("#sendEmail").find("a.deliverAddrDelete");
    var orequireTitleDel = jQuery("#invoiceHead").find("a.requireTitleDel");
    var billRequireFlag = "1";
    
    /**
     * 删除抬头信息
     */
    orequireTitleDel.click(function() {
        var id = jQuery(this).attr("id").split("_")[1];
    	var billType = $("#billType").val();
        var orderSn = $("#orderSn").val();
        var billRequireFlag = "1";
    	if (confirm("确认删除该抬头信息?")) {
    		jQuery
    				.ajax({
    					url : sinosoft.base
    							+ "/shop/bill_title!deleteBillTitle.action?billType="+billType+"&orderSn="+orderSn+"&billRequireFlag=" + billRequireFlag,
    					dataType : "json",
    					type : "POST",
    					async : false,
    					data:{titleId:id},
    					success : function(data) {
    						if (data.tFlag == "Suc") {
    							art.dialog.alert("删除信息成功！",function (){
    								window.location.href = sinosoft.base + "/shop/bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSn+"&billRequireFlag=" + billRequireFlag;
    							});							
    						} else {
    							art.dialog.alert(data.Msg);
    						}
    					}
    				});
    	} else {
    		return false;
    	}
	});
    
    //删除邮件地址信息
    oDelete.click(function() {
        var id = jQuery(this).attr("id").split("_")[1];
    	if (confirm("确认删除该地址?")) {
            var billType = $("#billType").val();
            var orderSn = $("#orderSn").val();
            jQuery.ajax({
                    url: sinosoft.base + "/shop/member_info_maintain!deleteDeliverAddrInfo.action?info_id=" + id,
                    dataType: "json",
                    type: "POST",
                    async: false,
                    success: function(data) {
                        if (data.tFlag == "Suc") {
                            art.dialog.alert("删除信息成功！",function(){
                            	window.location.href = sinosoft.base + "/shop/bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSn+"&billRequireFlag=" + billRequireFlag;
                            });
                        } else {
                        	art.dialog.alert(data.Msg);
                        }
                    }
                });
        } else {
            return false;
        }
	});
	//设置默认邮寄地址
	oDefault.click(function() {
        var id = jQuery(this).attr("id").split("_")[1];
        var infoId = jQuery(this).attr("id").split("_")[2];
		var billType = $("#billType").val();
	    var orderSn = $("#orderSn").val();
	    jQuery.ajax({
	            url: sinosoft.base + "/shop/member_info_maintain!defaultDeliverAddrInfo.action?memberID=" + id + "&info_id=" + infoId,
	            dataType: "json",
	            type: "POST",
	            async: false,
	            success: function(data) {
	                if (data.tFlag == "Suc") {
	                    art.dialog.alert("设置默认信息成功！",function(){
	                    	window.location.href = sinosoft.base + "/shop/bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSn+"&billRequireFlag=" + billRequireFlag;
	                    });
	                } else {
	                    art.dialog.alert(data.Msg);
	                }
	            }
	        });
	});

    var billType = $("#billType").val();
    var orderSn = $("#orderSn").val();
    oAddBillTitle.click(function() {
        var billTitleCount = $("#billTitleCount").val();
        if (billTitleCount>=10) {
        	art.dialog.alert("最多增加10个发票抬头!");
        	return false;
        }
        var billType = $("#billType").val();
        var titleContent = '<div class="update_title_box"><form id="titleSubmitInfo" action="">'
			+ ' <div id="artPopup05">发票抬头：<input id="billTitleInput" type="text" value="普通发票抬头" onfocus="if (this.value == \'普通发票抬头\') {this.value = \'\';};jQuery(this).css(\'color\', \'#3b3b3b\');" onblur="verifyElement(\'发票抬头|NOTNULL\',this.value,this.id);if (this.value == \'\') {this.value = \'普通发票抬头\';jQuery(this).css(\'color\', \'#ccc\');};" verify="发票抬头|notnull" /><label class="requireField"></label></div>'
			+ ' </form></div>' 
		art.dialog({
			id : 'billTitleDialog',
			content : titleContent,
			title : "常用发票信息",
			drag : false,
			okVal : '保存',
			ok : function() {
				if (jQuery("#billTitleInput").val() == '普通发票抬头') {
					verifyElement("发票抬头|NOTNULL","","billTitleInput");
				} else if(verifyElement("发票抬头|NOTNULL",jQuery("#billTitleInput").val(),"billTitleInput")){
					
					jQuery.ajax({
						  url: "bill_title!saveOrUpdate.action",
						  dataType: 'json',
						  data: {
							  billTitleCount:billTitleCount,
							  titleName:jQuery("#billTitleInput").val()
						  },
						  success: function(data) {
	                          var tFlag = data.tFlag;
	                          if (tFlag == "Err") {
	                        	  art.dialog({
	                                  id: 'billTitleDialog'
	                              }).close();
	                              art.dialog.alert(data.Msg);
	                          } else {
	                        	  art.dialog.alert("增加抬头信息成功！",function(){
	                        		  art.dialog({
	                        			  id: 'billTitleDialog'
	                        		  }).close();
	                        		  window.location.href = sinosoft.base + "/shop/bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSn+"&billRequireFlag=" + billRequireFlag;
	                        	  })
	                          }
						  }
					});
				}
				return false;
			},
			cancel : true,
			cancelVal : '关闭'
		});
    });

    orequireTitleUpdate.click(function() {
    	
    	
        var title_id = jQuery(this).attr("id").split("_")[1];
        var billTitleCount = $("#billTitleCount").val();
       
        var billType = $("#billType").val();
        var titleIndex = $("#titleIndex_"+title_id).val();
        var billTitle = $("#title_name_"+titleIndex).val();
        
        var titleContent = '<div class="update_title_box"><form id="titleSubmitInfo" action="">'
			+ ' <div id="artPopup05">发票抬头：<input id="billTitleInput" style="color:#3b3b3b;" type="text" value="' +billTitle+ '" onfocus="if (this.value == \'普通发票抬头\') {this.value = \'\';};jQuery(this).css(\'color\', \'#3b3b3b\');" onblur="verifyElement(\'发票抬头|NOTNULL\',this.value,this.id);if (this.value == \'\') {this.value = \'普通发票抬头\';jQuery(this).css(\'color\', \'#ccc\');};"  verify="发票抬头|notnull" /><label class="requireField"></label></div>'
			+ ' </form></div>';
		art.dialog({
			id : 'billTitleDialog',
			content : titleContent,
			title : "常用发票信息",
			drag : false,
			okVal : '保存',
			ok : function() {
				if (jQuery("#billTitleInput").val() == '普通发票抬头') {
					verifyElement("发票抬头|NOTNULL","","billTitleInput");
				} else if(verifyElement("发票抬头|NOTNULL",jQuery("#billTitleInput").val(),"billTitleInput")){
					
					jQuery.ajax({
						  url: "bill_title!saveOrUpdate.action",
						  dataType: 'json',
						  data: {
							  titleId:title_id,
							  titleName:jQuery("#billTitleInput").val()
						  },
						  success: function(data) {
	                          var tFlag = data.tFlag;
	                          if (tFlag == "Err") {
	                        	  art.dialog({
	                                  id: 'billTitleDialog'
	                              }).close();
	                              art.dialog.alert(data.Msg);
	                          } else {
	                        	  art.dialog.alert("修改抬头信息成功！",function(){
	                        		  art.dialog({
	                        			  id: 'billTitleDialog'
	                        		  }).close();
	                        		  window.location.href = sinosoft.base + "/shop/bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSn+"&billRequireFlag=" + billRequireFlag;
	                        	  });
	                          }
						  }
					});
				}
				return false;
			},
			cancel : true,
			cancelVal : '关闭'
		});

    });
    
    oAddCont.click(function() {
        var deliverAddrCnt = $("#deliverAddrCount").val();
        if (deliverAddrCnt>=5) {
        	art.dialog.alert("只能增加5个邮寄地址!");
        	return false;
        }
        var billType = $("#billType").val();
        var orderSn = $("#orderSn").val();
        art.dialog.open(
        		"deliver_address_maintain!initAddDeliverAddr.action?count=" + deliverAddrCnt, {
                title: "常用邮寄地址填写",
                id: 'deliverAddr',
                width:"600px",
                height:"300px",
                okVal: '保存',
                ok: function() {
                    var iframeDoc = this.iframe.contentWindow.document;
                    if ($(iframeDoc).find("#deliverAddrName").val() == '中英文姓名') {
						verifyElement1("姓名|NOTNULL&UFO&LEN>2","","deliverAddrName",iframeDoc);
						return false;
					} else if (!verifyElement1("姓名|NOTNULL&UFO&LEN>2",$(iframeDoc).find("#deliverAddrName").val(),"deliverAddrName",iframeDoc)) {
						return false;
					}
					if ($(iframeDoc).find("#deliverAddrTel").val() == '手机或固话') {
						verifyElement1("手机号码|PHONE1","","deliverAddrTel",iframeDoc);
						return false;
					} else if (!verifyElement1("手机号码|PHONE1",$(iframeDoc).find("#deliverAddrTel").val(),"deliverAddrTel",iframeDoc)) {
						return false;
					}
					if ($(iframeDoc).find("#address").val() == '请准确填写，以免无法邮寄给您') {
						verifyElement1("地址|notnull&LEN>4","","address",iframeDoc);
						return false;
					} else if (!verifyElement1("地址|notnull&LEN>4",$(iframeDoc).find("#address").val(),"address",iframeDoc)) {
						return false;
					}	
					if (!verifyElement1("邮编|ZIPCODE",$(iframeDoc).find("#zipCode").val(),"zipCode",iframeDoc)) {
						return false;
					}
                    var addrName = $(iframeDoc).find("#deliverAddrName").val();
                    var addrTel = $(iframeDoc).find("#deliverAddrTel").val();
                    var addrProvince = $(iframeDoc).find("#area").val();
                    var addrProvinceName = $(iframeDoc).find("#area option:selected").text();
                    var addrCity = $(iframeDoc).find("#city").val();
                    var addrCityName = $(iframeDoc).find("#city option:selected").text();
                    var addrAddress = $(iframeDoc).find("#address").val();
                    var addrZipCode = $(iframeDoc).find("#zipCode").val();
                    if(addrProvinceName=="" || addrCityName == "" ){
						return false;
					}
                    jQuery.ajax({
					  url: "deliver_address_maintain!saveDeliverAddrInfo.action",
					  dataType: 'json',
					  data: {
						  count: deliverAddrCnt,
                          info_Name: addrName,
                          info_Tel: addrTel,
                          info_ProvinceCode: addrProvince,
                          info_ProvinceName: addrProvinceName,
                          info_CityCode: addrCity,
                          info_CityName: addrCityName,
                          info_DetailAddr: addrAddress,
                          info_ZipCode: addrZipCode
                      },
					  success: function(data) {
                          var tFlag = data.tFlag;
                          if (tFlag == "Err") {
                              dialog.alert(data.Msg);
                          } else {
                        	  art.dialog({
                                  id: 'deliverAddr'
                              }).close();
                              window.location.href = sinosoft.base + "/shop/bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSn;
                          }
					  }
					});
                    
                    return false;
                },
                cancel: function() {
                    art.dialog({
                        id: 'deliverAddr'
                    }).close();
                },
                cancelVal: '关闭'
            },false
        );
    });

    oRequire.click(function() {
        var billType = $("#billType").val();
        var orderSn = $("#orderSn").val();
        var addr_id = jQuery(this).attr("id").split("_")[1];
        art.dialog.open(
            "deliver_address_maintain!getDeliverAddr.action?addr_id=" + addr_id, {
                title: "常用邮寄地址填写",
                id: 'deliverAddr',
                width:"600px",
                height:"300px",
                okVal: '保存',
                ok: function() {

                    var iframeDoc = this.iframe.contentWindow.document;
                    if ($(iframeDoc).find("#deliverAddrName").val() == '中英文姓名') {
						verifyElement1("姓名|NOTNULL&UFO&LEN>2","","deliverAddrName",iframeDoc);
						return false;
					} else if (!verifyElement1("姓名|NOTNULL&UFO&LEN>2",$(iframeDoc).find("#deliverAddrName").val(),"deliverAddrName",iframeDoc)) {
						return false;
					}
					if ($(iframeDoc).find("#deliverAddrTel").val() == '手机或固话') {
						verifyElement1("手机号码|PHONE1","","deliverAddrTel",iframeDoc);
						return false;
					} else if (!verifyElement1("手机号码|PHONE1",$(iframeDoc).find("#deliverAddrTel").val(),"deliverAddrTel",iframeDoc)) {
						return false;
					}
					if ($(iframeDoc).find("#address").val() == '请准确填写，以免无法邮寄给您') {
						verifyElement1("地址|notnull&LEN>4","","address",iframeDoc);
						return false;
					} else if (!verifyElement1("地址|notnull&LEN>4",$(iframeDoc).find("#address").val(),"address",iframeDoc)) {
						return false;
					}	
					if (!verifyElement1("邮编|ZIPCODE",$(iframeDoc).find("#zipCode").val(),"zipCode",iframeDoc)) {
						return false;
					}
                    
                    var id = $(iframeDoc).find("#Id").val();
                    var addrName = $(iframeDoc).find("#deliverAddrName").val();
                    var addrTel = $(iframeDoc).find("#deliverAddrTel").val();
                    var addrProvince = $(iframeDoc).find("#area").val();
                    var addrProvinceName = $(iframeDoc).find("#area option:selected").text();
                    var addrCity = $(iframeDoc).find("#city").val();
                    var addrCityName = $(iframeDoc).find("#city option:selected").text();
                    var addrAddress = $(iframeDoc).find("#address").val();
                    var addrZipCode = $(iframeDoc).find("#zipCode").val();

                    if(addrProvinceName=="" || addrCityName == "" ){
						return false;
					}
                    //console.log(jQuery(iframeDoc).find("#deliverAddr_form"));
                    jQuery.ajax({
  					  url: "deliver_address_maintain!saveDeliverAddrInfo.action",
  					  dataType: 'json',
  					  data: {
  						    info_id: id,
                            info_Name: addrName,
                            info_Tel: addrTel,
                            info_ProvinceCode: addrProvince,
                            info_ProvinceName: addrProvinceName,
                            info_CityCode: addrCity,
                            info_CityName: addrCityName,
                            info_DetailAddr: addrAddress,
                            info_ZipCode: addrZipCode
                        },
  					  success: function(data) {
                            var tFlag = data.tFlag;
                            if (tFlag == "Err") {
                                dialog.alert(data.Msg);
                            } else {
                          	  art.dialog({
                                    id: 'deliverAddr'
                                }).close();
                                window.location.href = sinosoft.base + "/shop/bill_save!enterBilling.action?billType="+billType+"&orderSn="+orderSn;
                            }
  					  }
  					});

                    return false;
                },
                cancel: function() {
                    art.dialog({
                        id: 'deliverAddr'
                    }).close();
                },
                cancelVal: '关闭'
           },false
        );

    });

})();
