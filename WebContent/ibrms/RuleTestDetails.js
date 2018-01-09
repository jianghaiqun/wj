function testRule(){
	
	if (Verify.hasError()) {
		return;
	}
	
	var arr = document.getElementById("arrResult").value.split(";");
	for(var i=1;i<arr.length;i++){
		document.getElementById(arr[i]).value = "";
	}
	document.getElementById("labeldes").style.display = "none";
	var dc = Form.getData($F("fm"));

	Server.sendRequest("com.sinosoft.ibrms.RuleTest.submitData",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				var arr = document.getElementById("arrResult").value.split(";");
				for(var i=1;i<arr.length;i++){
					if(response.get(arr[i])){
						document.getElementById(arr[i]).value = response.get(arr[i]);
					}else{
						document.getElementById(arr[i]).value = "未获得数据";
					}
				}
			}
			if(response.get("Interval")){
				var lab = document.getElementById("labeldes");
				lab.style.display = "";
				lab.innerHTML = "规则执行的时间为:"+response.get("Interval")+"毫秒。";
			}
			
		});
	});
}

function initForm(){
	initRuleForDetails();
	hideButtons();
	hideButtonsR();
	disableSpanNodes();
	disableSpanNodesR();
	rowDisable = true;
}
function initRuleForDetails() {
	initDisplaymentStyle();
	initLogicalAndDT();

}
function initDisplaymentStyle() {

	var RuleDisplay = document.getElementById('RuleDisplay');
	RuleDisplay.style.height = "auto";
	RuleDisplay.style.overflow = "visible";
}
function initLogicalAndDT() {
	var ViewParameter = getVewParameter();
	convertXMLToRule(ViewParameter);
	
	getBaseBomItems();			
	getJsonData();
	ruleTestForm(ViewParameter);
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
function initTableColumnNameArray(tableName) {
	var sql = " SELECT column_name from information_schema.columns where table_name='"+ tableName.toLowerCase() + "' order by substr(column_name,length(column_name))";
	var result = easyQueryVer3(sql);
	var reArray = decodeEasyQueryResult(result);
	var rowLen = reArray.length - 1;

	TableColumnNameArray.length = 0;
	for (i = 0; i <= rowLen; i++) {
		TableColumnNameArray[i] = reArray[i][0];
	}
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
function getVewParameter() {
	var ViewParameter = queryForViewParameter(LRTemplateName, LRTemplate_ID);
	return ViewParameter;
}



function ruleTestForm(xmlFile) {
	try {
		var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");

		xmlDoc.async = false;
		xmlDoc.loadXML(xmlFile);
	} catch (e) {
		alert(("您的浏览器不支持xml文件读取,于是本页面禁止您的操作,推荐使用IE5.0以上可以解决此问题!"));
		return;
	}
	if (xmlDoc == null) {
		alert(("您的浏览器不支持xml文件读取,于是本页面禁止您的操作,推荐使用IE5.0以上可以解决此问题!"));
		return;
	}
	//如果录入
	var LogicalTable = document.getElementById("LogicalTable");
	
	var ruleNode = xmlDoc.getElementsByTagName("Condition");

	var arrbom = new Array();
	var arrbomitem = new Array();
	var x = 0;
	var isCF = false;
	for ( var i = 0, len = ruleNode.length; i < len; i++) {
		var ruleMetaNodes = ruleNode[i].childNodes;
		var tdname = "";
		var tdid = "";
		for ( var j = 0; j < ruleMetaNodes.length; j++) {
			ruleMetaNode = ruleMetaNodes[j];
			var tdid = "";
			if (ruleMetaNode.getAttribute('id') == "BOM") {
				for(var n=0;n<arrbom.length;n++){
					if(arrbom[n].getAttribute("EnName")==ruleMetaNode.getAttribute("EnName")&&arrbomitem[n].getAttribute("EnName")==ruleMetaNode.nextSibling.getAttribute("EnName")){
						isCF = true;
					}
				}
				if(!isCF){
					arrbom[x] = ruleMetaNode;
				}
			}
			if (ruleMetaNode.getAttribute('id') == "BOMItem") {
				if(isCF){
					isCF = false;
					continue;
				}
				arrbomitem[x] = ruleMetaNode;
				x++;
			}
		}	
	}
	
	
	
	//结果输出
	var ResultTable = document.getElementById("ResultTable");
	var resultNode = xmlDoc.getElementsByTagName("Result");
	var arrbomR = new Array();
	var arrbomitemR = new Array();
	var isCFR = false;
	var xR = 0;
	for ( var i = 0, len = resultNode.length; i < len; i++) {
		var resultMetaNodes = resultNode[i].childNodes;
		var tdname = "";
		var tdid = "";
		for ( var j = 0; j < resultMetaNodes.length; j++) {
			resultMetaNode = resultMetaNodes[j];
			var tdid = "";
			if (resultMetaNode.getAttribute('id') == "BOM") {
				for(var n=0;n<arrbomR.length;n++){
					if(arrbomR[n].getAttribute("EnName")==resultMetaNode.getAttribute("EnName")&&arrbomitemR[n].getAttribute("EnName")==resultMetaNode.nextSibling.getAttribute("EnName")){
						isCFR = true;
					}
				}
				if(!isCFR){
					arrbomR[xR] = resultMetaNode;
				}
				for(var n=0;n<arrbom.length;n++){
					if(arrbom[n].getAttribute("EnName")==resultMetaNode.getAttribute("EnName")&&arrbomitem[n].getAttribute("EnName")==resultMetaNode.nextSibling.getAttribute("EnName")){
						isCF = true;
					}
				}
				if(!isCF){
					arrbom[x] = resultMetaNode;
				}
			}
			if (resultMetaNode.getAttribute('id') == "BOMItem") {
				if(isCFR){
					isCFR = false;
				}else{
					arrbomitemR[xR] = resultMetaNode;
					xR++;
				}
				if(isCF){
					isCF = false;
				}else{
					arrbomitem[x] = resultMetaNode;
					x++;
				}
			}
		}	
	}
	
	var rownum = (x+2)/3;
	for(var rownumi=0;rownumi<rownum;rownumi++){
		var rows = LogicalTable.insertRow();
		for(var i=0;i<6;i++){
			var x1 = rows.insertCell();
			x1.width="17%";
		}
	}
	for(var i=0;i<x;i++){
		LogicalTable.rows[(i+3)/3-1].cells[2*(i%3)].innerHTML=arrbom[i].getAttribute('ChName')+arrbomitem[i].getAttribute('ChName');
		LogicalTable.rows[(i+3)/3-1].cells[2*(i%3)+1].innerHTML="<Input type=\"text\" id="+arrbom[i].getAttribute('EnName')+"."+arrbomitem[i].getAttribute('EnName')+" style=\"width: 170px\">";
	}
	
	var rownumR = (xR+2)/3;
	for(var rownumi=0;rownumi<rownumR;rownumi++){
		var rows = ResultTable.insertRow();
		for(var i=0;i<6;i++){
			var x1 = rows.insertCell();
			x1.width="17%";
		}
	}

	for(var i=0;i<xR;i++){
		ResultTable.rows[(i+3)/3-1].cells[2*(i%3)].innerHTML=arrbomR[i].getAttribute('ChName')+arrbomitemR[i].getAttribute('ChName');
		ResultTable.rows[(i+3)/3-1].cells[2*(i%3)+1].innerHTML="<Input type=\"text\" id="+arrbomR[i].getAttribute('EnName')+"-"+arrbomitemR[i].getAttribute('EnName')+" style=\"width: 170px\" readOnly=\"true\">";
		document.getElementById("arrResult").value = document.getElementById("arrResult").value + ";" + arrbomR[i].getAttribute('EnName')+"-"+arrbomitemR[i].getAttribute('EnName');
	}
	
}