// 弹出投保人窗口
jQuery("#artAppntInsert").click(function(){
	jQuery.ajax({
		url : sinosoft.base+ "/shop/member_info_maintain!ajaxValidateCount.action?operatorFlag=appnt",
		dataType : "json",
		type : "POST",
		async : true,
		success : function(data) {
			if (data.Flag == "Suc") {

				var mailContent = ' <div class="update_manage_box">'
				    + ' <form id="appSubmitInfo" action=""><table width="100%" border="0" class="update_table_ma">'
					+ ' <tr><th>姓名</th><td style="width:400px;"><input type="text" class="manage_sele" id="applicantName" verify="姓名|notnull&UFO&LEN>2" onblur="verifyElement(\'姓名|notnull&UFO&LEN>2\',this.value,this.id);"  name = "mSDRelationAppnt.applicantName"/><label class="requireField"></label></td></tr>'
					+ ' <tr><th>英文名</th><td style="width:400px;"><input type="text" class="manage_sele" id="applicantEnName" verify="英文名|NOTNULL&UFO&ENG&LEN>2" onblur="verifyElement(\'英文名|NOTNULL&UFO&ENG&LEN>2\',this.value,this.id);" onfocus="verifyShowInf(this.value,this.id);" name = "mSDRelationAppnt.applicantEnName"/><label class="requireField"></label><font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写与您证件相同的汉语拼音或英文名</i></font></td></tr>'
					+ ' <tr><th>证件类型</th><td><select id="applicantIDtype" class="manage_sele" name="mSDRelationAppnt.applicantIDType" ><option value ="0">身份证</option><option value ="7">护照</option><option value ="99">其他</option></select><input type="text" id="applicantID"  onblur="idcheck(\'applicantIDtype\',\'applicantID\',\'applicantBirthday\',\'applicantSex\',\'\');" verify="证件号码|NOTNULL" name = "mSDRelationAppnt.applicantID" class="manage_txts"/><label class="requireField"></label></td></tr>'
					+ ' <tr><th>性别</th><td><select id="applicantSex" class="manage_sele" name="mSDRelationAppnt.applicantSex" ><option value ="M" checked="true">男</option><option value ="F">女</option></select><label class="requireField"></label></td></tr>'
					+ ' <tr><th>手机号码</th><td><input type="text" id="applicantMobile" class="manage_txts" onblur="verifyElement(\'手机号码|PHONE\',this.value,this.id)" verify="手机号码|PHONE" maxlength=11 name = "mSDRelationAppnt.applicantMobile"/><label class="requireField"></label></td></tr>'
					+ ' <tr><th>出生日期</th><td><input id="applicantBirthday" name="mSDRelationAppnt.applicantBirthday" type="text" onclick="WdatePicker({skin:\'whyGreen\',startDate:\'1980-01-01\'})" onchange="verifyElement(\'出生日期|NOTNULL&AGE\',this.value,this.id)" class="shop_day" verify="出生日期|notnull&AGE"/><label class="requireField"></label></td></tr>'
					+ ' <tr><th>电子邮箱</th><td><input type="text" id="applicantMail" class="manage_txts" onblur="verifyElement(\'电子邮箱|NOTNULL&EMAIL\',this.value,this.id)" verify="电子邮箱|NOTNULL&EMAIL" name = "mSDRelationAppnt.applicantMail"/><label class="requireField"></label></td></tr>'
					+ ' </table></form></div>';

			var appntTitle = '新增常用投保人信息';
			var turl = "member_info_maintain!saveInfo.action?operatorFlag=appnt";
			art.dialog(
							{
								content : mailContent,
								title : appntTitle,
								drag : false,
								id : 'appntArt',
								okVal : '保存',
								ok : function() {
									if(verifyInput2()){
										if (!idcheck('applicantIDtype','applicantID','applicantBirthday','applicantSex','1')) {
											return false;
										}
										jQuery.ajax({
											url : sinosoft.base
													+ "/shop/member_info_maintain!ajaxValidateName.action?operatorFlag=appnt&info_Name=" 
													+ encodeURIComponent(encodeURIComponent(jQuery("#applicantName").val()))
													+"&info_CardID="+encodeURIComponent(encodeURIComponent(jQuery("#applicantID").val())),
											dataType : "json",
											type : "POST",
											async : false,
											success : function(data) {
												if (data.Flag == "Err") {
													art.dialog.alert(data.Msg);
													return false;
												}else{
													var options = {
															url : turl,
															async : true,
															type : "POST",
															data : {
																info_Name : jQuery("#applicantName").val(),
																info_EnName : jQuery("#applicantEnName").val(),
																info_IDtype : jQuery("#applicantIDtype").val(),
																info_IDtypeName : jQuery("#applicantIDtype").find("option:selected").text(),
																info_CardID : jQuery("#applicantID").val(),
																info_Sex : jQuery("#applicantSex").val(),
																info_Mobile : jQuery("#applicantMobile").val(),
																info_Birthday : jQuery("#applicantBirthday").val(),
																info_Email : jQuery("#applicantMail").val()
															},
															dataType : "json",
															resetForm : false,
															success : function(data) {
																var tFlag = data.Flag;
																if (tFlag == "Err") {
																	dialog.alert(data.Msg);
																} else {
																	window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
																}
															}
														};
														jQuery('#appSubmitInfo').ajaxSubmit(options);
												} 
											}
										});
									}
									return false;
									},
								cancel : function(){
									art.dialog({id: 'appntArt'}).close();
								},
								cancelVal : '关闭',
							});
			
			} else {
				alert(data.Msg);
				return false;
			}
		}
	});
	
});
jQuery(".manage_update_appnt").click(function(){ 

	var info_id = jQuery(this).attr("id").split("_")[1];
	jQuery.ajax({
		url : sinosoft.base+ "/shop/member_info_maintain!getDetailInfo.action?operatorFlag=appnt&info_id="+ info_id,
		dataType : "json",
		type : "POST",
		async : false,
		success : function(data) {
			if (data.Flag == "Suc") {
				var appnt = data.appnt;
				var appntIDTypeName = appnt.applicantIdentityTypeName;
				var mailContent = ' <div class="update_manage_box">'
							    +' <form id="appSubmitInfo" action=""><table width="100%" border="0" class="update_table_ma">'
								+ ' <tr><th>姓名</th><td><input type="text" id="applicantName" verify="姓名|notnull&UFO&LEN>2" onblur="verifyElement(\'姓名|notnull&UFO&LEN>2\',this.value,this.id);" name = "mSDRelationAppnt.applicantName" value='
								+ appnt.applicantName
								+ ' /><label class="requireField"></label></td></tr>'
								+ ' <tr><th>英文名</th><td style="width:400px;"><input type="text" class="manage_sele" id="applicantEnName" verify="英文名|NOTNULL&UFO&ENG&LEN>2" onblur="verifyElement(\'英文名|NOTNULL&UFO&ENG&LEN>2\',this.value,this.id);" onfocus="verifyShowInf(this.value,this.id);" name = "mSDRelationAppnt.applicantEnName" value="'
								+ appnt.applicantEnName
								+ '" /><label class="requireField"></label><font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写与您证件相同的汉语拼音或英文名</i></font></td></tr>'
								+ ' <tr><th>证件类型</th><td><select id="applicantIDtype" name="mSDRelationAppnt.applicantIDType" verify="证件类型|idtype" onblur="verifyElement(\'证件类型|idtype\',this.value,this.id)">'
								+ '<option value ="0">身份证</option><option value ="7">护照</option><option value ="99">其他</option>';

				mailContent	   += '</select><input type="text" id="applicantID" name = "mSDRelationAppnt.applicantID" onblur="idcheck(\'applicantIDtype\',\'applicantID\',\'applicantBirthday\',\'applicantSex\',\'\');" verify="证件号码|NOTNULL" value='+ appnt.applicantIdentityId+'>';	
				
				mailContent	   += ' <label class="requireField"></label></td></tr>'
								+ ' <tr><th>性别</th><td><select id="applicantSex" name="mSDRelationAppnt.applicantSex" ><option value ="M" checked="true">男</option><option value ="F">女</option></select><label class="requireField"></label></td></tr>'
								+ ' <tr><th>手机号码</th><td><input type="text" id="applicantMobile" name = "mSDRelationAppnt.applicantMobile" onblur="verifyElement(\'手机号码|PHONE\',this.value,this.id)" verify="手机号码|PHONE" maxlength=11 value='
								+ appnt.applicantMobile
								+ ' /><label class="requireField"></label></td></tr>'
								+ ' <tr><th>出生日期</th><td><input id="applicantBirthday" name="mSDRelationAppnt.applicantBirthday" type="text" onclick="WdatePicker({skin:\'whyGreen\',startDate:\'1980-01-01\'})" onchange="verifyElement(\'出生日期|NOTNULL&AGE\',this.value,this.id)" verify="出生日期|notnull&AGE"  class="shop_day" value='
								+ appnt.applicantBirthday
								+ ' /><label class="requireField"></td></tr>'
								+ ' <tr><th>电子邮箱</th><td><input type="text" id="applicantMail" name = "mSDRelationAppnt.applicantMail" onblur="verifyElement(\'电子邮箱|NOTNULL&EMAIL\',this.value,this.id)" verify="电子邮箱|NOTNULL&EMAIL" value='
								+ appnt.applicantMail + ' /><label class="requireField"></label></td></tr>' + ' </table></form></div>';

				var appntTitle = '修改常用投保人信息';
				var turl = "member_info_maintain!updateInfo.action?operatorFlag=appnt";
				art.dialog(
								{
									content : mailContent,
									title : appntTitle,
									drag : false,
									okVal : '保存',
									ok : function() {
										if(verifyInput2()){
											if (!idcheck('applicantIDtype','applicantID','applicantBirthday','applicantSex','1')) {
												return false;
											}
											jQuery.ajax({
												url : sinosoft.base
														+ "/shop/member_info_maintain!ajaxValidateName.action?operatorFlag=appnt&operator=update&info_id="+appnt.id+"&info_Name=" 
														+ encodeURIComponent(encodeURIComponent(jQuery("#applicantName").val()))
														+"&info_CardID="+encodeURIComponent(encodeURIComponent(jQuery("#applicantID").val())),
												dataType : "json",
												type : "POST",
												async : false,
												success : function(data) {
													if (data.Flag == "Err") {
														art.dialog.alert(data.Msg);
														return false;
													}else{
														var options = {
																url : turl,
																async : true,
																type : "POST",
																data : {
																	info_id : appnt.id,
																	info_Name : jQuery("#applicantName").val(),
																	info_EnName : jQuery("#applicantEnName").val(),
																	info_IDtype : jQuery("#applicantIDtype").val(),
																	info_IDtypeName : jQuery("#applicantIDtype").find("option:selected").text(),
																	info_CardID : jQuery("#applicantID").val(),
																	info_Sex : jQuery("#applicantSex").val(),
																	info_Mobile : jQuery("#applicantMobile")
																	.val(),
																	info_Birthday : jQuery("#applicantBirthday")
																	.val(),
																	info_Email : jQuery("#applicantMail").val()
																},
																dataType : "json",
																resetForm : false,
																success : function(data) {
																	var tFlag = data.Flag;
																	if (tFlag == "Err") {
																		dialog.alert(data.Msg);
																	} else {
																		window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
																	}
																}
														};
														jQuery('#appSubmitInfo').ajaxSubmit(options);
														art.dialog.alert("常用投保人信息修改成功！");
														return true;
													}
												}
											});
										}
										return false;
									},
									cancel : true,
									cancelVal : '关闭',
								});
			} else {
				alert(data.Msg);
				return false;
			}
		}
	});

});
jQuery("#artRecognizeeInsert").click(function(){
	jQuery.ajax({
		url : sinosoft.base+ "/shop/member_info_maintain!ajaxValidateCount.action?operatorFlag=insured",
		dataType : "json",
		type : "POST",
		async : true,
		success : function(data) {
			if (data.Flag == "Suc") {
				var mailContent = ' <div class="update_manage_box"><form id="insuredSubmitInfo" action=""><table width="100%" border="0" class="update_table_ma">'
					+ ' <tr><th>姓名</td><td><input type="text" class="manage_sele" id="insuredName" verify="姓名|notnull&UFO&LEN>2" onblur="verifyElement(\'姓名|NOTNULL&UFO&LEN>2\',this.value,this.id)" maxlength=50 name = "mSDRelationRecognizee.recognizeeName" /><label class="requireField"></label></td></tr>'
					+ ' <tr><th>英文名</th><td><input type="text" class="manage_sele" id="insuredEnName" verify="英文名|NOTNULL&UFO&ENG&LEN>2" onblur="verifyElement(\'英文名|NOTNULL&UFO&ENG&LEN>2\',this.value,this.id);" maxlength="50" onfocus="verifyShowInf(this.value,this.id);" name = "mSDRelationRecognizee.recognizeeEnName"/><label class="requireField"></label><font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写与您证件相同的汉语拼音或英文名</i></font></td></tr>'
					+ ' <tr><th>证件类型</td><td><select id="insuredIDtype" class="manage_sele" name="mSDRelationRecognizee.recognizeeIDTypeName" ><option value ="0">身份证</option><option value ="7">护照</option><option value ="99">其他</option></select><input type="text" id="insuredID" onblur="idcheck(\'insuredIDtype\',\'insuredID\',\'insuredBirthday\',\'insuredSex\',\'\');" verify="证件号码|NOTNULL" name = "mSDRelationRecognizee.recognizeeID" /><label class="requireField"></label></td></tr>'
					+ ' <tr><th>性别</td><td><select id="insuredSex"class="manage_sele"  name="mSDRelationRecognizee.recognizeetSex" ><option value ="M" checked="true">男</option><option value ="F">女</option></select></td></tr>'
					+ ' <tr><th>手机号码</td><td><input type="text" id="insuredMobile" class="manage_sele" name = "mSDRelationRecognizee.recognizeeMobile" onblur="verifyElement(\'手机号码|PHONE\',this.value,this.id)" verify="手机号码|PHONE" maxlength=11 /> <label class="requireField"></label></td></tr>'
					+ ' <tr><th>出生日期</td><td><input id="insuredBirthday" name="mSDRelationRecognizee.recognizeeBirthday" type="text" onclick="WdatePicker({skin:\'whyGreen\',startDate:\'1980-01-01\'})" verify="出生日期|NOTNULL&AGE" class="shop_day" onchange="verifyElement(\'出生日期|NOTNULL&AGE\',this.value,this.id);"/><label class="requireField"></label></td></tr>'
					+ ' <tr><th>电子邮箱</td><td><input type="text" id="insuredMail" class="manage_sele" name = "mSDRelationRecognizee.recognizeeMail" onblur="verifyElement(\'电子邮箱|NOTNULL&EMAIL\',this.value,this.id)" verify="电子邮箱|NOTNULL&EMAIL" /><label class="requireField"></label></td></tr>'
					+ ' </table></form></div>';

			var RecognizeeTitle = '<span style="text-align: center">新增常用被保人信息</span>';
			var turl = "member_info_maintain!saveInfo.action?operatorFlag=insured";

			art.dialog({
								content : mailContent,
								title : RecognizeeTitle,
								drag : false,
								okVal : '保存',
								ok : function() {
									if(verifyInput2()){
										if (!idcheck('insuredIDtype','insuredID','insuredBirthday','insuredSex','1')) {
											return false;
										}
										jQuery.ajax({
											url : sinosoft.base
													+ "/shop/member_info_maintain!ajaxValidateName.action?operatorFlag=insured&info_Name=" 
													+ encodeURIComponent(encodeURIComponent(jQuery("#insuredName").val()))
													+"&info_CardID="+encodeURIComponent(encodeURIComponent(jQuery("#insuredID").val())),
											dataType : "json",
											type : "POST",
											async : false,
											success : function(data) {
												if (data.Flag == "Err") {
													art.dialog.alert(data.Msg);
													return false;
												}else{
													var options = {
															url : turl,
															async : false,
															type : "POST",
															data : {
																info_Name : jQuery("#insuredName")
																		.val(),
																info_EnName : jQuery("#insuredEnName").val(),
																info_IDtype : jQuery("#insuredIDtype").val(),
																info_IDtypeName : jQuery("#insuredIDtype").find("option:selected").text(),
																info_CardID : jQuery("#insuredID").val(),
																info_Sex : jQuery("#insuredSex").val(),
																info_Mobile : jQuery("#insuredMobile")
																		.val(),
																info_Birthday : jQuery(
																		"#insuredBirthday").val(),
																info_Email : jQuery("#insuredMail")
																		.val()
															},
															dataType : "json",
															resetForm : false,
															success : function(data) {
																var tFlag = data.Flag;
																if (tFlag == "Err") {
																	dialog.alert(data.Msg);
																} else {
																	window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
																}
															}
														};
														jQuery('#insuredSubmitInfo').ajaxSubmit(options);
												}
											}
										});
									}
                                    return false;
								},
								cancel : true,
								cancelVal : '关闭',
							});
			} else {
				alert(data.Msg);
				return false;
			}
		}
	});
	
});
jQuery(".manage_update_insured").click(function(){ 

	var info_id = jQuery(this).attr("id").split("_")[1];
	jQuery.ajax({
		url : sinosoft.base+ "/shop/member_info_maintain!getDetailInfo.action?operatorFlag=insured&info_id="+ info_id,
		dataType : "json",
		type : "POST",
		async : false,
		success : function(data) {
			if (data.Flag == "Suc") {
				var insured = data.insured;
				var insuredIDTypeName=insured.recognizeeIdentityTypeName;
				var mailContent = ' <div class="update_manage_box"><form id="insuredSubmitInfo" action=""><table width="100%" border="0" class="update_table_ma">'
						+ ' <tr><th>姓名</td><td><input class="manage_sele" type="text" id="insuredName" name = "mSDRelationRecognizee.recognizeeName" onblur="verifyElement(\'姓名|NOTNULL&UFO&LEN>2\',this.value,this.id)" maxlength=50 verify="姓名|NOTNULL&UFO&LEN>2" value='
						+ insured.recognizeeName
						+ ' /><label class="requireField"></label></td></tr>'
						+ ' <tr><th>英文名</th><td><input type="text" class="manage_sele" id="insuredEnName" verify="英文名|NOTNULL&UFO&ENG&LEN>2" onblur="verifyElement(\'英文名|NOTNULL&UFO&ENG&LEN>2\',this.value,this.id);" maxlength="50" onfocus="verifyShowInf(this.value,this.id);" name = "mSDRelationRecognizee.recognizeeEnName" value="'
						+ insured.recognizeeEnName
						+ '" /><label class="requireField"></label><font color="#E7AC59" style="display:none"> <i class="yz_mes_des">请填写与您证件相同的汉语拼音或英文名</i></font></td></tr>'
						+ ' <tr><th>证件类型</td><td><select id="insuredIDtype" name="mSDRelationRecognizee.recognizeeIDTypeName" verify="证件类型|idtype" onblur="verifyElement(\'证件类型|idtype\',this.value,this.id)" >'
						+ '<option value ="0">身份证</option><option value ="7">护照</option><option value ="99">其他</option>';

			mailContent+= '</select><input class="manage_sele" type="text" id="insuredID" name = "mSDRelationRecognizee.recognizeeID" onblur="idcheck(\'insuredIDtype\',\'insuredID\',\'insuredBirthday\',\'insuredSex\',\'\');" verify="证件号码|NOTNULL" value='+ insured.recognizeeIdentityId+ ' />';

	        mailContent+= ' <label class="requireField"></label></td></tr>'
						+ ' <tr><th>性别</td><td><select id="insuredSex" name="mSDRelationRecognizee.recognizeeSex" ><option value ="M" checked="true">男</option><option value ="F">女</option></select></td></tr>'
						+ ' <tr><th>手机号码</td><td><input class="manage_sele" type="text" id="insuredMobile" name = "mSDRelationRecognizee.recognizeeMobile" onblur="verifyElement(\'手机号码|PHONE\',this.value,this.id)"  verify="手机号码|PHONE" value='
						+ insured.recognizeeMobile
						+ ' /> <label class="requireField"></label></td></tr>'
						+ ' <tr><th>出生日期</td><td><input  id="insuredBirthday" name="mSDRelationRecognizee.recognizeeBirthday" type="text" onclick="WdatePicker({skin:\'whyGreen\',startDate:\'1980-01-01\'})"  maxlength=11 value='
						+ insured.recognizeeBirthday
						+ ' verify="出生日期|NOTNULL&AGE" class="shop_day" onchange="verifyElement(\'出生日期|NOTNULL&AGE\',this.value,this.id);"/><label class="requireField"></label></td></tr>'
						+ ' <tr><th>电子邮箱</td><td><input class="manage_sele" type="text" id="insuredMail" name = "mSDRelationRecognizee.recognizeeMail" onblur="verifyElement(\'电子邮箱|NOTNULL&EMAIL\',this.value,this.id)" verify="电子邮箱|NOTNULL&EMAIL" value='
						+ insured.recognizeeMail + ' /><label class="requireField"></label></td></tr>' + ' </table></form></div>';

				var appntTitle = '<span style="text-align: center">修改常用投保人信息</span>';
				var turl = "member_info_maintain!updateInfo.action?operatorFlag=insured";
				art.dialog(
								{
									content : mailContent,
									title : appntTitle,
									drag : false,
									okVal : '保存',
									ok : function() {
										if(verifyInput2()){
											if (!idcheck('insuredIDtype','insuredID','insuredBirthday','insuredSex','1')) {
												return false;
											}
											jQuery.ajax({
												url : sinosoft.base
														+ "/shop/member_info_maintain!ajaxValidateName.action?operatorFlag=insured&operator=update&info_id="+insured.id+"&info_Name=" 
														+ encodeURIComponent(encodeURIComponent(jQuery("#insuredName").val()))
														+"&info_CardID="+encodeURIComponent(encodeURIComponent(jQuery("#insuredID").val())),
												dataType : "json",
												type : "POST",
												async : false,
												success : function(data) {
													if (data.Flag == "Err") {
														art.dialog.alert(data.Msg);
														return false;
													}else{
														var options = {
																url : turl,
																async : true,
																type : "POST",
																data : {
																	info_id : insured.id,
																	info_Name : jQuery("#insuredName").val(),
																	info_EnName : jQuery("#insuredEnName").val(),
																	info_IDtype : jQuery("#insuredIDtype").val(),
																	info_IDtypeName : jQuery("#insuredIDtype").find("option:selected").text(),
																	info_CardID : jQuery("#insuredID").val(),
																	info_Sex : jQuery("#insuredSex").val(),
																	info_Mobile : jQuery("#insuredMobile")
																			.val(),
																	info_Birthday : jQuery("#insuredBirthday")
																			.val(),
																	info_Email : jQuery("#insuredMail").val()
																},
																dataType : "json",
																resetForm : false,
																success : function(data) {
																	var tFlag = data.Flag;
																	if (tFlag == "Err") {
																		dialog.alert(data.Msg);
																	} else {
																		window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
																	}
																}
															};
															jQuery('#insuredSubmitInfo').ajaxSubmit(options);
															art.dialog.alert("常用被保人信息修改成功！");
															return true;
													}
												}
											});
										}
                                        return false;
									},
									cancel : true,
									cancelVal : '关闭',
								});
			} else {
				alert(data.Msg);
				return false;
			}
		}
	});

});
/**
 * 删除信息[id^=ins_]
 */
function deleteinfo(flag, id) {
	if (confirm("是否删除该条信息?")) {
		jQuery
				.ajax({
					url : sinosoft.base
							+ "/shop/member_info_maintain!deleteInfo.action?operatorFlag="
							+ flag + "&info_id=" + id,
					dataType : "json",
					type : "POST",
					async : false,
					success : function(data) {
						if (data.tFlag == "Suc") {
							art.dialog.alert("删除信息成功！");
							window.location.href = sinosoft.base + "/shop/member_info_maintain!memberInfoQuery.action";
						} else {
							art.dialog.alert(data.Msg);
						}
					}
				});
	} else {
		return false;
	}
}
function checkName(name,flag){
	jQuery.ajax({
		url : sinosoft.base
				+ "/shop/member_info_maintain!deleteInfo.action?operatorFlag="
				+ flag + "&info_Name=" + Name,
		dataType : "json",
		type : "POST",
		async : false,
		success : function(data) {
			if (data.tFlag == "Err") {
				art.dialog.alert(data.Msg);
			} 
		}
	});
}

function idcheck(idtype,idCode,birthdayId,sexId,cFlag){
    str=document.getElementById(idCode).value;
    var idType = document.getElementById(idtype);
	var idType_index = idType.selectedIndex;          
	var idType_value = idType.options[idType_index].text;   
	var idType_id = idType.options[idType_index].id; 
	var flag = true;
	str = jQuery.trim(str);
	var StrLast=str.substring(str.length-1);
	if(jQuery("#"+idCode).parent().children("i")){
		   jQuery("#"+idCode).parent().children("i").remove();
	}
	if(idType_value.indexOf("身份证") != -1){
		if(str.length==18){
			  
			if(!checkId(str,"")){  
				if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">请填写18位有效身份证号</i>"));
		 		}
				return false ;
			}else{
				if(StrLast=='x'){
					if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
						  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">身份证有误,身份证号码最后一位请输入大写:X</i>"));
			 		}
					return false ;
				}else{
					if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
					jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_yes\">&nbsp;&nbsp;</i>"));
					}
				}
			}
			var sex=''; 
			var sexName = jQuery("#"+sexId).attr("name");
			if(str.substring(16,17)%2==0||str.substring(16,17).toLowerCase()=='x'){  
				jQuery("#"+sexId).val("F");
			}else{   
				jQuery("#"+sexId).val("M"); 
			}   
			var year = str.substring(6, 10);      
			var month = str.substring(10, 12);      
			var day = str.substring(12, 14);
			sex = jQuery("input[name="+sexName+"] :checked").val();
				
			if(cFlag!="1"){
				document.getElementById(birthdayId).value = year+"-"+month+"-"+day;
			} else {
				if (document.getElementById(birthdayId).value != (year+"-"+month+"-"+day)) {
					if (jQuery("#" + idCode).parent().children("i")) {
						jQuery("#"+idCode).parent().children("i").remove();
					}
					jQuery("#"+idCode).parent().children("label").after(jQuery("<i  class=\"yz_mes_error\" >身份证与出生日期不符</i>"));
					return false;
				}
			}
		}else{
			if(!jQuery("#"+idCode).parent().children("label").next().is('i')){
				  jQuery("#"+idCode).parent().children("label").after(jQuery("<i class=\"yz_mes_error\">请填写18位有效身份证号</i>"));
		 	}
			return false;
		}
		
	}
	if(!flag){
		return false;
	}else{
		return true;
	}
	
}