function queryDataRuleTemplate(){

	DataGrid.clear('RuleTemplateQueryDataGrid');
	var dc = Form.getData($F("fm"));
	for ( var i = 0; i < dc.size(); i++) {
		DataGrid.setParam("RuleTemplateQueryDataGrid", dc.getKey(i), dc.get(i));
	}
	DataGrid.setParam("RuleTemplateQueryDataGrid", Constant.PageIndex, 0);
	DataGrid.loadData("RuleTemplateQueryDataGrid");
	
}

/**
 * 查看明细
 */
function detailsView(){
	if(!isSelectRule()){
		return;
	}
	
	var LRTemplate_ID = fm.LRTemplate_ID.value;
	var LRTemplateName=fm.LRTemplateName.value;
	if (!!LRTemplate_ID) {
		var url = "./RuleTemplateMake.jsp?Operate=Details"+"&LRTemplate_ID="
				+ LRTemplate_ID+"&LRTemplateName="+LRTemplateName;
		window.open(url);
	}
	
}

function AddRule(){
	var url="";
	url='RuleTemplateCustom.jsp';
	document.location.href = url;
	
}

function UpdateRule(){
	
	if(!isSelectRule()){
		return;
	}
	fm.Operate.value="UPATE";
	var LRTemplate_ID = fm.LRTemplate_ID.value;
	var tLRTemplateName = fm.LRTemplateName.value;
	var RuleTemplateName = fm.RuleTemplateName.vale;
	var RuleTemplateDes = fm.RuleTemplateDes.value;
	var Creator = fm.Creator.value;
	var url ="../ibrms/RuleTemplateMake.jsp?Operate=UPDATE&LRTemplate_ID="
		+ LRTemplate_ID+"&LRTemplateName="+tLRTemplateName+"&RuleTemplateName="+RuleTemplateName+"&RuleTemplateDes="+RuleTemplateDes+"&Creator="+Creator;
	//alert(url);
	document.location.href=url;
	
}

function DeleteRule(){
	if(!isSelectRule()){
		return;
	}
	fm.Operate.value="DELETE";
	
	var dc = Form.getData($F("fm"));
	Dialog.confirm("确认删除吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleTemplateMakeUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRuleTemplate();
				}
			});
		});		
	});
	
}

function afterSubmit(Content){
	Dialog.alert(Content);

	queryDataRuleTemplate();
	
	
}

function onselect(id,RuleTemplateName,RuleTemplateDes,Creator){
	fm.LRTemplate_ID.value=id;
	fm.RuleTemplateName.vale=RuleTemplateName;
	fm.RuleTemplateDes.value=RuleTemplateDes;
	fm.Creator.value=Creator;
	fm.LRTemplateName.value="lrruletemplate";
}

/**
 * 是否选择规则
 * @returns {Boolean}
 */
function isSelectRule(){
	var selMenuGrpNo = DataGrid.getSelectedData("RuleTemplateQueryDataGrid");
	if (selMenuGrpNo.Rows[0] == undefined || selMenuGrpNo.Rows[0] == null) {
		Dialog.alert("您还没有选择规则");
		return false;
	}
	return true;
	
}
