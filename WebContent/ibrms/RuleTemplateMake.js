function submitData() {
	if(!comfirmLogicTemplate()){
		return;
	}
	var dc = Form.getData($F("fm"));
	
	Dialog.confirm("确认提交吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleTemplateMakeUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				document.location.href="../ibrms/RuleTemplate.jsp";
			});
		});
	});
	//fm.submit();
}

function afterSubmit(Content){
	alert(Content);
	//document.location.href="../ibrms/RuleTempalte.jsp";
}

function comfirmLogicTemplate() {
	// 审核规则是否定制完整
	//生成条件表头
	if (checkOutRule()&&checkOutRuleR()) {
		//var fm = document.getElementById('fm');
		
		if (composeSQL()) {
			initDataArray();
			var submitData = document.getElementById('submitData');
			submitData.disabled = true;
			var logicToTable = document.getElementById('logicToTable');
			logicToTable.disabled = false;
			var modifyLogic = document.getElementById('modifyLogic');
			modifyLogic.disabled = false;
			return true;
		}

		else {

			return false;
		}

	}else{
		return false;
	}
}
