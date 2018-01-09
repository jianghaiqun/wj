<script type="text/javascript">
	function initRule() {
		switch (flag) {
		case 0: {
			initRuleForDetails();
			rowDisable = true;
			break;
		}
		case 1: {
			if(LRTemplate_ID&&LRTemplateName){
				var ViewParameter = getVewParameter();
				var fm = document.getElementById('fm');
				fm.ViewPara.value=ViewParameter;
				convertXMLToRule(ViewParameter);
				MenuUnfolded();
				MenuUnfoldedR();
			}
			break;
		}
		case 2: {
			initRuleForDuplication();
			break;
		}
		case 4: {
			initRuleForModifition();
			composeSQL();
			MenuUnfolded();
			MenuUnfoldedR();
			break;
		}
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
		switch (flag) {
		case 1: {
			initButtonForMake();
			break;
		}
		case 2: {
			initButtonForCopy();
			break;
		}
		case 4: {
			initButtonForModify();
			break;
		}
		}
	}

	function initButtonForMake() {
		var submitData = document.getElementById('submitData');
		submitData.disabled = true;
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

		var displayDisicionTable = document
				.getElementById('displayDisicionTable');
		displayDisicionTable.style.display = "none";
		var submitData = document.getElementById('submitData');
		submitData.style.display = "none";
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
		getBaseBomItems();
		getJsonData();
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

	function getDataFromDTTable() {
		var reData = new Array();
		if (!(!!LRTemplateName)) {
			return false;
		}
		var sql = "select TableName from " + LRTemplateName + " where id='"
				+ LRTemplate_ID + "'";

		var result = easyQueryVer3(sql);
		var reArray = decodeEasyQueryResult(result);
		var tableName = reArray[0][0];

		sql = "select * from " + tableName;
		result = easyQueryVer3(sql);
		reArray = decodeEasyQueryResult(result);
		var rowLen = reArray.length;
		var colLen = reArray[0].length;
		var i = 0, j = 0;

		initTableColumnNameArray(tableName);

		for (i = 0; i < rowLen; i++) {
			reData[i] = new Array();
			for (j = 0; j < colLen - 1; j++) {
				if (ColumnDataTypeArray[j] != 'Date') {
					reData[i][j] = reArray[i][j];
				} else {
					var da = new Date(reArray[i][j]);
					reData[i][j] = da.format('Y-m-d H:i:s');
				}
			}
		}
		for ( var i = reData.length; i < 10; i++) {
			reData[i] = [];
		}

		return reData;
	}
	function initTableColumnNameArray(tableName) {
		sql = " SELECT column_name from information_schema.columns where table_name='"
				+ tableName.toLowerCase() + "' order by substr(column_name,length(column_name))";
		var result = easyQueryVer3(sql);
		var reArray = decodeEasyQueryResult(result);
		var rowLen = reArray.length - 1;

		TableColumnNameArray.length = 0;
		for (i = 0; i <= rowLen; i++) {
			TableColumnNameArray[i] = reArray[i][0];
		}
	}

	function getJsonData() {
		if (!(!!LRTemplateName)) {
			alert("<规则来源表>获取有误");
			return false;
		}
		var sql = "select TableName from " + LRTemplateName + " where id='"
				+ LRTemplate_ID + "'";

		var result = easyQueryVer3(sql);
		var reArray = decodeEasyQueryResult(result);
		var tableName = reArray[0][0];

		post = "DTTableName=" + tableName;

		initTableColumnNameArray(tableName);

		post = encodeURI(post);
		post = encodeURI(post);// 两次，很关键
		Ext.Ajax.request( {
			url :'queryForInformation.jsp',
			method :'post',

			success : function(response, options) {
				displayJsonData(Ext.decode(response.responseText));
			},
			failure : function(response, options) {
				alert("数据获取出错，请找管理员！");
			},
			headers : {
				"Content-Type" :"application/x-www-form-urlencoded",
				"cache-control" :"no-cache"
			},
			params :post
		});
	}
	function prepareJsonData(jsonData) {
		for ( var i = 0; i < ColumnDataTypeArray.length; i++) {
			if (ColumnDataTypeArray[i] == "Date") {
				for ( var j = 0; j < jsonData.rows.length; j++) {
					jsonData.rows[j][TableColumnNameArray[i]] = Date.parseDate(
							jsonData.rows[j][TableColumnNameArray[i]],
							'Y-m-d H:i:s');
				}
			}		
			
			if (BaseBOMItemSourceArray[i] != ""&&typeof(BaseBOMItemSourceArray[i]) != "undefined") {
				var baseData;
				var source = BaseBOMItemSourceArray[i];
				for ( var j = 0; j < jsonData.rows.length; j++) {

					baseData = jsonData.rows[j][TableColumnNameArray[i]];

					var result = easyQueryVer3(source);
					var reArray = decodeEasyQueryResult(result);

					codeArray = baseData.split(";");
					baseData = "";
					for ( var k = 0; k < codeArray.length; k++) {
						if (codeArray[k] == "") {
							continue;
						}
						for ( var m = 0; m < reArray.length; m++) {
							if (codeArray[k] == reArray[m][0]) {

								if (k > 1 && k < codeArray.length - 1) {
									baseData += ";";
								}
								baseData += reArray[m][0] + "-" + reArray[m][1];
								break;
							}
						}
					}
					jsonData.rows[j][TableColumnNameArray[i]] = baseData;
				}
			}
		}
		return jsonData;
	}

	function displayJsonData(jsonData) {
		jsonData = prepareJsonData(jsonData);
		displayDicTable('init', jsonData);
	}
</script>