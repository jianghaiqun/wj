<script type="text/javascript">
	function initRule() {
		if(Operate=="UPDATE"){
			initRuleForDetails();
			composeSQL();
			 MenuUnfolded();
			 MenuUnfoldedR();
			rowDisable = true;
		}
		if(Operate=="Details"){
			initRuleForDetails();
			hideButtons();
			hideButtonsR();
			disableSpanNodes();
			disableSpanNodesR();
			rowDisable = true;
		}
	}
	function initRuleForDetails() {
		initDisplaymentStyle();
		initLogicalAndDT();

	}
	function initRuleForModifition() {
		initLogicalAndDT();
		initLRTemplatePara();
	}
	function initRuleForDuplication() {
		initLogicalAndDT();
	}
	function initButtons() {
		initButtonForMake();
	}

	function initButtonForMake() {
		var logicToTable = document.getElementById('logicToTable');
		logicToTable.disabled = true;
		var modifyLogic = document.getElementById('modifyLogic');
		modifyLogic.disabled = true;
	}

	function initButtonForCopy() {
		var submitData = document.getElementById('submitData');
		submitData.disabled = true;
		var logicToTable = document.getElementById('logicToTable');
		logicToTable.disabled = true;
		var modifyLogic = document.getElementById('modifyLogic');
		modifyLogic.disabled = true;
	}
	function initButtonForModify() {
		var displayDisicionTable = document
				.getElementById('displayDisicionTable');
		displayDisicionTable.disabled = true;
		var submitData = document.getElementById('submitData');
		submitData.disabled = false;
		var logicToTable = document.getElementById('logicToTable');
		logicToTable.disabled = false;
		var modifyLogic = document.getElementById('modifyLogic');
		modifyLogic.disabled = false;
	}

	function initDisplaymentStyle() {

		var RuleDisplay = document.getElementById('RuleDisplay');
		RuleDisplay.style.height = "auto";
		RuleDisplay.style.overflow = "visible";
		
		var submitData = document.getElementById('submitData');
		if(Operate=="Details"){
			submitData.style.display = "none";
		}else{
			submitData.style.display = "";
		}
		
		var logicToTable = document.getElementById('logicToTable');
		logicToTable.style.display = "none";
		var modifyLogic = document.getElementById('modifyLogic');
		modifyLogic.style.display = "none";
		
	}

	function initLogicalAndDT() {
		var ViewParameter = getVewParameter();
		var fm = document.getElementById('fm');
		fm.ViewPara.value=ViewParameter;
		convertXMLToRule(ViewParameter);
		//getBaseBomItems();			
		//getJsonData();
	}
	function getVewParameter() {
		var ViewParameter = queryForViewParameter(LRTemplateName, LRTemplate_ID);
		return ViewParameter;
	}

	function initLRTemplatePara() {
		var fm = document.getElementById('fm');

		var sql = "select SQLStatement,BOMS,SQLParameter,TableName from LRTemplateT where id='"
				+ LRTemplate_ID + "'";
		var result = easyQueryVer3(sql);
		var reArray = decodeEasyQueryResult(result);

		fm.SQLStatement.value = reArray[0][0];
		fm.BOMS.value = reArray[0][1];
		fm.SQLPara.value = reArray[0][2];
	    fm.TableName.value = reArray[0][3];
	}

</script>