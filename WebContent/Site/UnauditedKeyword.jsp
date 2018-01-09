<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="controls" prefix="z"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>未审核关键词</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
Page.onLoad(function(){
	$("dg1").beforeEdit = function(tr,dr){
	    $S("Level",$V("hLevel"));
	}
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Type",$V("Level"));		
		return true;
	}
}); 

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 200;
	diag.Titel = "新建未审核关键词";
	diag.URL = "Site/UnAddKeyword.jsp";
	diag.onLoad = function(){
		$DW.$("Keyword_Add").focus();	
	};
	diag.OKEvent = addSave;
	diag.show();	
}

function addSave(){
	var dc = $DW.Form.getData("unForm_Add");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.wangjin.search.CreatKeyword.creatKeyword",dc,function(response){
		Dialog.alert("添加成功",function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});	
	});	
}
function save(){
	DataGrid.save("dg1","com.wangjin.search.AddKeyword.dg1Edit",function(){
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
	});
}
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要审核的关键词！");
		return;
	}
	Dialog.confirm("您确认审核通过吗？",function(){
		var dc = new DataCollection();
		dc.add("Id",arr.join());
		Server.sendRequest("com.wangjin.search.AddKeyword.unDel",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("审核通过");
				DataGrid.loadData('dg1');
			}
		});
	});
}
function doSearch(){
	var Word = "";
	if($V("Keyword_input")!="请输入敏感词"){
		Word = $V("Keyword_input");
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.setParam("dg1","Word",Word);
		DataGrid.loadData("dg1");
	}
}
function del_keyword() {
	var keyWord = $V("Keyword_input");
	if (keyWord == "请输入关键字") {
		$S("Keyword_input","");
	}
}
</script>
</head>
<body>
		<form id="form">
				<table width="800" border="1" cellpadding="3" cellspacing="0" class="blockTable" >
					<tr>
						<td valign="middle" class="blockTd"><img src="../Icons/icon007a1.gif" /> 未审核关键词列表
							<div style="float: right; white-space: nowrap;">
								<input name="Keyword_input" type="text" id="Keyword_input" value="请输入关键字" onFocus="del_keyword();" style="width:110px"> 
								<input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton">
							</div>
						</td>
					</tr>
					<tr>
						<td style="padding:0 8px 4px;">
							<z:tbutton onClick="add()"><img src="../Icons/icon007a2.gif" />新建</z:tbutton> 
							<z:tbutton onClick="save()"><img src="../Icons/icon007a16.gif" />保存</z:tbutton> 
							<z:tbutton onClick="del()"><img src="../Icons/icon007a3.gif" />转到已审核</z:tbutton> 			
						</td>
					</tr>
				
					<tr>
						<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
							<z:datagrid id="dg1" method="com.wangjin.search.AddKeyword.dg1DataBind" size="12"> 
							<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
								<tr ztype="head" class="dataTableHead">
									<td width="5%" ztype="RowNo" align="middle"><strong>序号</strong></td>
									<td width="5%" ztype="selector" field="id" align="middle">&nbsp;</td>
									<td width="20%" align="middle"><b>关键词</b></td>
									<td width="10%"  align="middle"><b>状态</b></td>
									<td width="10%" align="middle"><b>点击量</b></td>
									<td width="10%" align="middle"><b>时间</b></td>
								</tr>
								<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
									<td width="5%" align="middle">&nbsp;</td>
									<td width="5%" align="middle">&nbsp;</td>
									<td>${KeyWord}</td>
									<td>
									<div ztype=select name="LevelName_${Id}" id="Level_${Id}"
										style="width:100px;display:none" value="${Type}" disabled="true">
										<span value=0>未审核</span> 
										<span value=5>已审核</span>
									</div>
									</td>
									<td align="middle">${Count}</td>
									<td align="middle">${createDate}</td>
								</tr>
								<tr ztype="edit" bgcolor="#E1F3FF">
									<td width="5%" bgcolor="#E1F3FF">&nbsp;</td>
									<td width="5%" bgcolor="#E1F3FF">&nbsp;</td>
									<td width="20%" bgcolor="#E1F3FF">${KeyWord}</td>
									<td><input id="hLevel" type="hidden" value="${Type}">
									<div ztype=select name="Level" id="Level" style="width:100px">
										<span value=0>未审核</span> 
										<span value=5>已审核</span>
									</div> 
									</td>
									<td width="10%" bgcolor="#E1F3FF">${Count}</td>
									<td align="middle">${createDate}</td>
								</tr>
								<tr ztype="pagebar">
									<td colspan="7" align="center">${PageBar}</td>
								</tr>
							</table>
							</z:datagrid>
						</td>
					</tr>
				</table>
		</form>	
</body>
</html>