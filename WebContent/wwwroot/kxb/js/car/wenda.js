function navHeight(){
	jQuery("#E").addClass("on");
	jQuery(".orange").removeClass("orange");
	if (jQuery("#IsAnswer").val()== "solve") { 
		jQuery('#BestA').show();
	}
	jQuery("#yzm").click();
}
jQuery(document).ready(
function() {
	jQuery("#add").click(function() {
						var tContent = encodeURIComponent(encodeURIComponent(jQuery("#content").val()));
						var tQuestionId = jQuery("#QuestionId").val();
						var tQuestionTitle = encodeURIComponent(encodeURIComponent(jQuery("#QuestionTitle").val()));
						var tVerifyCode = jQuery("#VerifyCode").val();
						if (tContent.length > 1000) {
							alert("回答内容只能写1000个字以内");
							return false;
						}
						if(tContent.length<=0){
							alert("问题还没有写，不能提交哟！");
							return false;
						}
						var now = new Date().getTime();
						jQuery.ajax({
							url: sinosoft.base+"/car/car_answer!save.action?Content="+tContent+"&QuestionId="+tQuestionId+"&QuestionTitle="+tQuestionTitle+"&VerifyCode="+tVerifyCode,
							dataType: "json",
							async: false,
							success: function(data) {
								var obj = eval(data);
								if(obj.test=="noCantent"){
									alert("问题还没有写，不能提交哟！");
								}
								if(obj.test=="noyzm"){
									alert("验证码没写对哦！");
									jQuery("#yzm").click();
								}
								if(obj.test=="yes"){
									alert("提交成功！");
									window.location.href = jQuery("#hesdUrl").val()+"/chexian/club/wenda";
								}
								if(obj.test=="no"){
									alert("提交失败！");
								}
								}
							}); 
					});
});
