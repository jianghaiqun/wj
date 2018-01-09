function AddRule(){
	
	var url="../ibrms/RuleInfor.jsp";
	
	document.location.href=url;
	
}

function DeleteRule(){
	
	fm.LRTemplateName.value="lrtemplatet";
	fm.State.value = "9";
	fm.submit();
	
}