/**
 * 查询数据
 */
function queryDataRule() {
	if (Verify.hasError()) {
		return;
	}
	DataGrid.clear('RuleQueryDataGrid');
	var dc = Form.getData($F("fm"));
	for ( var i = 0; i < dc.size(); i++) {
		DataGrid.setParam("RuleQueryDataGrid", dc.getKey(i), dc.get(i));
	}
	DataGrid.setParam("RuleQueryDataGrid", Constant.PageIndex, 0);
	DataGrid.loadData("RuleQueryDataGrid");
}

/**
 * 查看明细
 */
function detailsView(){
	if(!isSelectRule()){
		return;
	}
	
	var LRTemplate_ID = fm.LRTemplate_ID.value;
	var LRTemplateName="lrtemplatet";
	if (!!LRTemplate_ID) {
		var url = "./RuleMake.jsp?flag=0"+"&LRTemplate_ID="
				+ LRTemplate_ID+"&LRTemplateName="+LRTemplateName;
		window.open(url);
	}
	
}
/**
 * 测试
 */
function TestRule(){
	if(!isSelectRule()){
		return;
	}
	var url = "./RuleTestDetails.jsp?LRTemplate_ID="
				+ fm.LRTemplate_ID.value+"&LRTemplateName=lrtemplatet";

	window.open(url);
	
}
/**
 * 测试成功
 */
function TestSucc() {
	if(!isSelectRule()){
		return;
	}
	var dc = new DataCollection();
	dc.add("State","3");
	dc.add("UserName",$V("UserName"));
	dc.add("LRTemplate_ID",$V("LRTemplate_ID"));
	dc.add("LRTemplateName",$V("LRTemplateName"));
	Dialog.confirm("确认成功吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleStateUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRule();
				}
			});
		});		
	});
	
	//fm.State.value = "3";
	//fm.submit();
}

/**
 * 测试不通过
 */
function TestFail(){
	if(!isSelectRule()){
		return;
	}
	var dc = new DataCollection();
	dc.add("State","2");
	dc.add("UserName",$V("UserName"));
	dc.add("LRTemplate_ID",$V("LRTemplate_ID"));
	dc.add("LRTemplateName",$V("LRTemplateName"));
	Dialog.confirm("确认不通过吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleStateUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRule();
				}
			});
		});		
	});
}

/**
 * 审批通过
 */
function ExamineSucc(){
	if(!isSelectRule()){
		return;
	}
	var dc = new DataCollection();
	dc.add("State","4");
	dc.add("UserName",$V("UserName"));
	dc.add("LRTemplate_ID",$V("LRTemplate_ID"));
	dc.add("LRTemplateName",$V("LRTemplateName"));
	Dialog.confirm("确认通过吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleStateUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRule();
				}
			});
		});		
	});
}

/**
 * 审批不通过
 */
function ExamineFail(){
	var dc = new DataCollection();
	dc.add("State","2");
	dc.add("UserName",$V("UserName"));
	dc.add("LRTemplate_ID",$V("LRTemplate_ID"));
	dc.add("LRTemplateName",$V("LRTemplateName"));
	Dialog.confirm("确认不通过吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleStateUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRule();
				}
			});
		});		
	});
}

/**
 * 发布
 */
function RuleIssue(){
	if(!isSelectRule()){
		return;
	}
	var dc = new DataCollection();
	dc.add("State","7");
	dc.add("UserName",$V("UserName"));
	dc.add("LRTemplate_ID",$V("LRTemplate_ID"));
	dc.add("LRTemplateName",$V("LRTemplateName"));
	Dialog.confirm("确认发布吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleStateUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRule();
				}
			});
		});		
	});
}


/**
 * 作废
 */
function RuleNullify(){
	if(!isSelectRule()){
		return;
	}
	var dc = new DataCollection();
	dc.add("State","9");
	dc.add("UserName",$V("UserName"));
	dc.add("LRTemplate_ID",$V("LRTemplate_ID"));
	dc.add("LRTemplateName",$V("LRTemplateName"));
	Dialog.confirm("确认作废吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleStateUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRule();
				}
			});
		});		
	});
}
/**
 *  新增
 */

function AddRule(){
	
	document.location.href="../ibrms/RuleInfor.jsp";
}

/**
 * 删除
 */
function DeleteRule(){
	if(!isSelectRule()){
		return;
	}
	var dc = new DataCollection();
	dc.add("State","00");
	dc.add("UserName",$V("UserName"));
	dc.add("LRTemplate_ID",$V("LRTemplate_ID"));
	dc.add("LRTemplateName",$V("LRTemplateName"));
	Dialog.confirm("确认不通过吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleStateUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRule();
				}
			});
		});		
	});
}

/**
 * 修改
 */
function UpdateRule(){
	if(!isSelectRule()){
		return;
	}
	
	var LRTemplate_ID = fm.LRTemplate_ID.value;
	if (!!LRTemplate_ID) {
		//alert($V("RuleName")+$V("RuleStartDate")+$V("TemplateLevel")+$V("LRTemplate_ID")+$V("LRTemplateName"));
		var url="./RuleMake.jsp?"+
    	"flag=4"+
    	"&RuleName="+$V("RuleNameT")+
    	"&Creator="+$V("CreatorT")+
    	"&RuleStartDate="+$V("RuleStartDateT")+
    	"&RuleEndDate="+$V("RuleEndDateT")+
    	"&TempalteLevel="+$V("TemplateLevelT")+
    	"&Business="+$V("BusinessT")+
    	"&State=1"+
    	"&Valid="+$V("ValidT")+
    	"&LRTemplate_ID="+$V("LRTemplate_ID")+
    	"&LRTemplateName="+$V("LRTemplateName")+
    	"&RuleDes="+$V("RuleDesT")+
		"&MarketingNum="+$V("MarketingNum");
		//alert(url);
    	window.location.href=url;
	}
	
}

function ReUpdateRule(){
	
	if(!isSelectRule()){
		return;
	}
	var dc = new DataCollection();
	dc.add("State","2");
	dc.add("UserName",$V("UserName"));
	dc.add("LRTemplate_ID",$V("LRTemplate_ID"));
	dc.add("LRTemplateName",$V("LRTemplateName"));
	Dialog.confirm("确认返回修改吗？",function(){
		Server.sendRequest("com.sinosoft.ibrms.RuleStateUI.submitData",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					queryDataRule();
				}
			});
		});		
	});
	
}

/**
 * select选择事件
 * @param id
 */

function onselect(id,rulename,business,templatelevel,startdate,enddate,creator,state,description) {
	fm.LRTemplate_ID.value = id;
	fm.RuleNameT.value = rulename;
	fm.CreatorT.value = creator;
	fm.RuleStartDateT.value = startdate;
	fm.RuleEndDateT.value = enddate;
	fm.TempalateLevelT.value = templatelevel;
	fm.BusinessT.value = business;
	fm.ValidT.value = "1";
	fm.RuleDesT.value = description;
}

/**
 * 是否选择规则
 * @returns {Boolean}
 */
function isSelectRule(){
	var selMenuGrpNo = DataGrid.getSelectedData("RuleQueryDataGrid");
	if (selMenuGrpNo.Rows[0] == undefined || selMenuGrpNo.Rows[0] == null) {
		Dialog.alert("您还没有选择规则");
		return false;
	}
	return true;
	
}

function afterSubmit(Content){
	
	Dialog.alert(Content);
	
	
	queryDataRule();
}