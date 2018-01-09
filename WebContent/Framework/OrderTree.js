function onCheck(ele){
	ele = $(ele);
	var checked = ele.checked;
	var newPID = ele.getParent("P").$A("parentid");
	if(!checked){
		if($("contant_"+newPID)!=null){
			$("contant_"+newPID).checked = false;
		}
	}
	if(ele.value=="-1"){
	
	}
	var p = ele.getParent("P");
	var level = p.$A("level");
	var arr = $("tree1").$T("P");
	var flag = true;
	for(var i=0;i<arr.length;i++){
		var c = arr[i];
		var cid = c.$A("cid");
		if(cid){
			if(cid!="-1"&&ele.value=="-1"){
				if(checked){
					$("contant_"+cid).disable();
				}else{
					$("contant_"+cid).enable();
				}
			}else{
				if(c!=p&&flag){
					continue;
				}
				if(c==p){
					flag = false;
					continue;
				}
				if(c.$A("level")>level){
					$("contant_"+cid).checked = checked;
				}else{
					break;
				}
			}
		}
	}
}

function onCheckForChannel(ele){
	ele = $(ele);
	var checked = ele.checked;
	var newPID = ele.getParent("P").$A("parentid");
	if(!checked){
		if($("channel_"+newPID)!=null){
			$("channel_"+newPID).checked = false;
		}
	}
	if(ele.value=="-1"){
	
	}
	var p = ele.getParent("P");
	var level = p.$A("level");
	var arr = $("tree1").$T("P");
	var flag = true;
	for(var i=0;i<arr.length;i++){
		var c = arr[i];
		var cid = c.$A("cid");
		if(cid){
			if(cid!="-1"&&ele.value=="-1"){
				if(checked){
					$("channel_"+cid).disable();
				}else{
					$("channel_"+cid).enable();
				}
			}else{
				if(c!=p&&flag){
					continue;
				}
				if(c==p){
					flag = false;
					continue;
				}
				if(c.$A("level")>level){
					$("channel_"+cid).checked = checked;
				}else{
					break;
				}
			}
		}
	}
}