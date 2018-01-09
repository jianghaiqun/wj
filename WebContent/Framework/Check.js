function passVerify(ele){
	var passValue=document.getElementById(ele).value;
	var p1=/[a-z]/;
	var p2=/[A-Z]/;
	var p3=/\d/;
	var error=document.getElementById("error");
		if (passValue.length>=8 && passValue.length<=20){
			if(p1.test(passValue)&&p2.test(passValue)&&p3.test(passValue)){
			document.getElementById(ele).value=passValue;
			error.innerHTML="";
			}else{
			error.innerHTML="<span style=\"display: block; color:red; text-align: center;\">密码必须由大小写字母和数字组成!</span>";
			document.getElementById(ele).value="";
			}
		}else{
			error.innerHTML="<span style=\"display: block; color:red; text-align: center;\">密码至少为8位!</span>";
			document.getElementById(ele).value="";
		}
	
}

function countMonth(){
	Server.sendRequest("com.sinosoft.platform.Application.initTime",null,function(response){
		if(response.Status==5){
		    	showPassWarn();
		} 
	});
}

function showPassWarn(){
	var warn ="<span style=\"display: block; color:red; text-align: center;\">您已经长时间未更换密码，建议您尽快更换密码!</span>";
	var diag = new Dialog("ChangePassword1");
	diag.Widht = 500;
	diag.Height = 200;
	diag.Title = "修改密码";
	diag.URL = "Platform/ChangePasswordDialog.jsp?warn="+warn;
	diag.OKEvent = function(){
		if($DW.Verify.hasError()){
			return;
		}
		var dc = $DW.Form.getData("FChangePassword");//changePassword
		Server.sendRequest("com.sinosoft.platform.Application.changePassword",dc,function(response){
			if(response.Status==1){
				Dialog.alert(response.Message);
				$D.close();
			}else{
				Dialog.alert(response.Message);
			}
		});
	}
	diag.show();
}