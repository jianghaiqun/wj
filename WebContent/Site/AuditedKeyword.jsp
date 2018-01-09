<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="controls" prefix="z"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>关键词搜索</title>
<script src="../Framework/Main.js"></script>
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
function updateAllStaticPage(){
	Dialog.confirm("您确认要更新全部静态页面吗，更新时间可能较长？", function() {
		Server.sendRequest(
				"com.wangjin.search.UpdateAllStaticPage.doUpdateAllStaticPage",
				null, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在更新...");
					p.show();
				});
	});
}
function stickyKeyword(){
	var keyWord = $V('keyWord');
	var level = $V('level1');
	var kw = new DataCollection();
	kw.add("keyWord", keyWord);
	kw.add("level", level);
	Dialog.confirm("您确认要手动置顶/置后关键词吗", function() {
		Server.sendRequest(
				"com.wangjin.search.StickyKeyword.stickyKeyword",
			kw, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在置顶/置后关键词...");
					p.show();
				});
	});
}
function deleteKeyword(){
	
	var keyWord = $V('keyWord1');
	var dk = new DataCollection();
	dk.add("keyWord", keyWord);
	Dialog.confirm("您确认要手动取消审核通过的关键词吗", function() {
		Server.sendRequest(
				"com.wangjin.search.DeleteKeyword.deleteKeyword",
				dk, function(response) {
					var taskID = response.get("TaskID");
					var p = new Progress(taskID, "正在取消...");
					p.show();
				});
	});
}

function addKeyword(){
	var diag = new Dialog("Diag2");
	diag.Width = 400;
	diag.Height = 200;
	diag.Title = "新建关键词";
	diag.URL = "Site/AddKeyword.jsp";
	diag.onLoad = function(){
		$DW.$("Keyword_Add").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form_Add");
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
function delKeyword(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择取消审核通过的关键词！");
		return;
	}
Dialog.confirm("您确认要取消审核通过吗？",function(){
	var dc = new DataCollection();
	dc.add("Id",arr.join());
	Server.sendRequest("com.wangjin.search.AddKeyword.del",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("取消审核通过成功");
			DataGrid.loadData('dg1');
		}
	});
});
	
}

function saveKeyword(){
	DataGrid.save("dg1","com.wangjin.search.AddKeyword.dg1Edit",function(){
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
		});

}

function del_keyword() {
	var keyWord = $V("Keyword_input");
	if (keyWord == "请输入关键字") {
		$S("Keyword_input","");
	}
}

function doSearch(){
	var Word = "";
	if ($V("Keyword_input") != "请输入关键字") {
		Word = $V("Keyword_input");
	}	
	var Level = $V("Level2");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Word",Word);
	DataGrid.setParam("dg1","Level",Level);
	DataGrid.loadData("dg1");
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<form id="form">
		<table width="800" border="1" cellpadding="3" cellspacing="0" bordercolor="#eeeeee">
				<tr>
                    <td>关键词搜索静态页面全部更新：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="updateAllStaticPage();">
						<img src="../Icons/icon002a9.gif" />更新</z:tbutton></td>
				</tr>
				<tr>
					<td>手动置顶/置后关键词：</td>
					<td>
					<input name="keyWord" type="text" class="input1" id="keyWord"  size="30" />
					<select id="level1" name="level1" >
						<option value="1">Level 1</option>
						<option value="2">Level 2</option>
						<option value="3">Level 3</option>
						<option value="4">Level 4</option>
						<option value="5" selected>Level 5</option>
						<option value="6">Level 6</option>
						<option value="7">Level 7</option>
						<option value="8">Level 8</option>
						<option value="9">Level 9</option>
					</select>
					<input type="button" name="Submit" value="置顶/置后" onClick="stickyKeyword()"><br/>
					(Level 5以上的为置顶，以下的为置后)
					</td>
				</tr>
				<tr>
					<td>手动取消审核通过：</td>
					<td>
					<input name="keyWord1" type="text" class="input1" id="keyWord1"  size="30" />
					<input type="button" name="Submit" value="取消审核通过" onClick="deleteKeyword()">
					</td>
				</tr>
				<table width="800" border="1" cellpadding="3" cellspacing="0" class="blockTable" >
					<tr>
						<td valign="middle" class="blockTd"><img src="../Icons/icon007a1.gif" /> 已审核关键词列表	
							<div style="float: right; white-space: nowrap;">
								<div ztype=select name="Level2" id="Level2"  >
									<span value="">请选择</span>
									<span value=1>Level 1</span>
									<span value=2>Level 2</span>
									<span value=3>Level 3</span>
									<span value=4>Level 4</span>
									<span value=5>Level 5</span>
									<span value=6>Level 6</span>
									<span value=7>Level 7</span>
									<span value=8>Level 8</span>
									<span value=9>Level 9</span>
								</div>
								<input type="button" name="Submit" value="等级查询" onClick="doSearch()">
								<input name="Keyword_input" type="text" id="Keyword_input" value="请输入关键字" onFocus="del_keyword();" style="width:110px">
								<input type="button" name="Submitbutton" value="查询" onClick="doSearch()" id="Submitbutton">
							</div>
						</td>
					</tr>
					<tr>
						<td style="padding:0 8px 4px;">
							<z:tbutton onClick="addKeyword()"><img src="../Icons/icon007a2.gif" />新建</z:tbutton> 
							<z:tbutton onClick="saveKeyword()"><img src="../Icons/icon007a16.gif" />保存</z:tbutton> 
							<z:tbutton onClick="delKeyword()"><img src="../Icons/icon007a3.gif" />转到未审核</z:tbutton> 
						</td>
					</tr>
				
					<tr>
						<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
							<z:datagrid id="dg1" method="com.wangjin.search.AddKeyword.dg2DataBind" size="12"> 
								<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
									<tr ztype="head" class="dataTableHead">
										<td width="5%" ztype="RowNo" align="middle"><strong>序号</strong></td>
										<td width="5%" ztype="selector" field="id" align="middle">&nbsp;</td>
										<td width="20%" align="middle"><b>关键词</b></td>
										<td width="10%"  align="middle"><b>等级</b></td>
										<td width="10%" align="middle"><b>点击量</b></td>
										<td width="10%" align="middle"><b>时间</b></td>
									</tr>
									<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
										<td width="5%" align="middle">&nbsp;</td>
										<td width="5%" align="middle">&nbsp;</td>
										<td>${KeyWord}</td>
										<td>
											<div ztype=select name="LevelName_${Id}" id="Level_${Id}" style="width:100px;display:none" value="${Type}" disabled="true">
												<span value=1>Level 1</span>
												<span value=2>Level 2</span>
												<span value=3>Level 3</span>
												<span value=4>Level 4</span>
												<span value=5>Level 5</span>
												<span value=6>Level 6</span>
												<span value=7>Level 7</span>
												<span value=8>Level 8</span>
												<span value=9>Level 9</span>
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
												<span value=1>Level 1</span>
												<span value=2>Level 2</span>
												<span value=3>Level 3</span>
												<span value=4>Level 4</span>
												<span value=5>Level 5</span>
												<span value=6>Level 6</span>
												<span value=7>Level 7</span>
												<span value=8>Level 8</span>
												<span value=9>Level 9</span>
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
		</table>
	</form>
</body>
</html>