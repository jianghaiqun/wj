<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<%
	//如果有DocList.LastCatalog则重定向到CatalogBasic.jsp
	CatalogSite.onRequestBegin(request, response);
%>
<z:init method="com.sinosoft.cms.site.CatalogSite.init">
	<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>栏目</title>
<script src="../Framework/Main.js"></script>
<script>
//2012.12.06 add by 梅俊峰,于善春 <<20121108018-需求系统开发需求申请单-搜索页面>> start
Page.onLoad(function(){
	$("dg1").beforeEdit = function(tr,dr){
	    $S("Level",$V("hLevel"));
	}
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Type",$V("Level"));		
		return true;
	}
}); 
//2012.12.06 add by 梅俊峰,于善春 <<20121108018-需求系统开发需求申请单-搜索页面>> end
	//鼠标点击当前页面时，隐藏右键菜单
	var iFrame = parent.parent;

	Page.onLoad(function() {
		Application.setAllPriv("site", Application.CurrentSite);
	});

	Page.onClick(function() {
		var div = iFrame.$("_DivContextMenu")
		if (div) {
			$E.hide(div);
		}
	});

	var topFrame = window.parent;
	function add() {
		topFrame.add();
	}

	function publish() {
		topFrame.publish();
	}

	function publishIndex() {
		if (!$V("IndexTemplate")) {
			Dialog.alert("首页模板不能为空");
			return;
		}
		topFrame.publishIndex();
	}

	function del() {
		topFrame.del();
	}

	function batchAdd() {
		topFrame.batchAdd();
	}

	function batchDelete() {
		topFrame.batchDelete();
	}

	function addMagazine() {
	}

	function openEditor(path) {
		topFrame.openEditor(path);
	}

	function preview() {
		topFrame.preview();
	}

	function browse(ele) {
		var diag = new Dialog("Diag3");
		diag.Width = 700;
		diag.Height = 440;
		diag.Title = "选择首页模板";
		diag.URL = "Site/TemplateExplorer.jsp";
		goBack = function(params) {
			$S(ele, params);
		}
		diag.OKEvent = afterSelect;
		diag.show();
	}

	function afterSelect() {
		$DW.onOK();
	}

	function changeIndexTemplate() {
		var indexTemplate = $V('IndexTemplate');
		if (!indexTemplate) {
			Dialog.alert("请选择模板!");
			return;
		}
		var dc = new DataCollection();
		dc.add("IndexTemplate", indexTemplate);
		Server.sendRequest("com.sinosoft.cms.site.CatalogSite.changeTemplate",
				dc, function(response) {
					Dialog.alert(response.Message);
					if (response.Status == 1) {
						$('editLink').style.display = "block";
						$('editLink').onclick = function() {
							openEditor(indexTemplate);
						}
					}
				});
	}
	function syncAllProduct() {
		Dialog.confirm("您确认要同步全部产品信息吗，同步时间可能较长？", function() {
			Server.sendRequest(
					"com.sinosoft.product.ProductSearch.syncAllProduct",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在同步...");
						p.show();
					});
		});
	}
	function syncAllProductArea() {
		Dialog.confirm("您确认要同步全部产品销售地区信息吗，同步时间可能较长？", function() {
			Server.sendRequest(
					"com.sinosoft.product.ProductSearch.syncAllProductArea",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在同步...");
						p.show();
					});
		});
	}
	function syncAllProductHI() {
		Dialog.confirm("您确认要同步全部产品健康告知信息吗，同步时间可能较长？", function() {
			Server.sendRequest(
					"com.sinosoft.product.ProductSearch.syncAllProductHI",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在同步...");
						p.show();
					});
		});
	}
	function syncSearchCondition() {
		Dialog.confirm("您确认要重置筛选条件数据吗，重置时可能会影响用户的正常查询？", function() {
			Server.sendRequest(
					"com.sinosoft.product.ProductSearch.syncSearchCondition",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在同步...");
						p.show();
					});
		});
	}
	function publishAllProduct(){
		Dialog.confirm("您确认要发布产品中心下全部产品信息吗，发布时间可能较长？", function() {
			Server.sendRequest(
					"com.sinosoft.product.ProductSearch.publishAllProduct",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在发布...");
						p.show();
					});
		});
	}
	function publishQuickSearchProduct(){
		Dialog.confirm("您确认要生成快速查询静态页面吗，生成时间可能较长？", function() {
			Server.sendRequest(
					"com.sinosoft.product.ProductSearch.publishQuickSearchProduct",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在生成...");
						p.show();
					});
		});
	}
	
	function publishSearchProduct(){
		Dialog.confirm("您确认要生成筛选静态页面吗，生成时间可能较长？", function() {
			Server.sendRequest(
					"com.sinosoft.product.ProductSearch.publishSearchProduct",
					null, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在生成...");
						p.show();
					});
		});
	}

	//zhangjinquan 11180 req20121210002-保险公司专题 2012-12-21 start
	function publishProductSortInsureTopic()
	{
		Dialog.confirm
		(
			"您确认要生成保险公司专题静态页面吗，生成时间可能较长？",
			function()
			{
				Server.sendRequest(
					"com.sinosoft.cms.template.custom.ProductSortInsureTopicPublish.publish",
					null,
					function(response)
					{
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在生成...");
						p.show();
					}
				);
			}
		);
	}
	//zhangjinquan 11180 req20121210002-保险公司专题 2012-12-21 end

	//2012.11.19 add by 梅俊峰,于善春 <<20121108018-需求系统开发需求申请单-搜索页面>> start
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
		Dialog.confirm("您确认要手动删除关键词吗", function() {
			Server.sendRequest(
					"com.wangjin.search.DeleteKeyword.deleteKeyword",
					dk, function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在删除关键词...");
						p.show();
					});
		});
	}

	function addKeyword(){
		var diag = new Dialog("Diag1");
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
			Dialog.alert("请先选择要删除的行！");
			return;
		}
	Dialog.confirm("您确认要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("Id",arr.join());
		Server.sendRequest("com.wangjin.search.AddKeyword.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("删除关键词成功");
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
		if ($V("Keyword_input") != "请输入敏感词") {
			Word = $V("Keyword_input");
		}	
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.setParam("dg1","Word",Word);
		DataGrid.loadData("dg1");
	}
	//2012.11.19 add by 梅俊峰,于善春 <<20121108018-需求系统开发需求申请单-搜索页面>> end
	
	function preview() {
		topFrame.preview();
	}

	function exportStructure() {
		var diag = new Dialog("Diag4");
		diag.Width = 500;
		diag.Height = 350;
		diag.Title = "导出栏目结构";
		diag.URL = "Site/CatalogExportStructure.jsp?Type=${Type}&ID=";
		diag.ShowMessageRow = true;
		diag.Message = "导出的文本可复制后再使用“批量导入”功能导入的其他站点或栏目下。";
		diag.show();
		diag.OKButton.hide();
	}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div style="padding: 2px">
		<table width="100%" cellpadding='4' cellspacing='0'>
			<tr>
				<td><z:tButtonPurview>${_Site_SiteBasic_Button}</z:tButtonPurview></td>
			</tr>
		</table>
		<form id="form2">
			<table width="600" border="1" cellpadding="3" cellspacing="0" bordercolor="#eeeeee">
				<tr>
					<td>站点ID：</td>
					<td>${ID}</td>
				</tr>
				<tr>
					<td>站点名称：</td>
					<td>${Name} &nbsp; <input type="hidden" id="ID" value="${ID}"> <input type="hidden" id="Name" value="${Name}"> <input name="BtnPreview" type="button" id="BtnPreview"
						value="预览" onclick="preview();"></td>
				</tr>
				<tr>
					<td>站点目录名：</td>
					<td>${Alias}</td>
				</tr>
				<tr>
					<td>站点URL：</td>
					<td>${URL}</td>
				</tr>
				<!--tr>
	  <td>&nbsp;</td>
      <td  align="right" >站点Logo：</td>
      <td><img src="./${LogoFileName}" align="absmiddle"/>&nbsp;</td>
    </tr-->
				<tr>
					<td>首页模板：</td>
					<td><input name="IndexTemplate" type="text" class="input1" id="IndexTemplate" value="${IndexTemplate}" size="30" /> <input type="button" name="Submit" value="选择模板"
						onClick="browse('IndexTemplate')"> <input type="button" name="Submit" value="更新" onClick="changeIndexTemplate()">&nbsp;&nbsp;${edit}</td>
				</tr>
				<tr>
					<td>编辑器样式：</td>
					<td>${EditorCss}&nbsp;</td>
				</tr>
				<tr>
					<td>描述：</td>
					<td>${Info}&nbsp;</td>
				</tr>
				<tr style="display: none;">
					<td>产品筛选条件重置：</td>
					<td><z:tbutton id="BtnSyncSearchCondition" priv="site_browse" onClick="syncSearchCondition();">
							<img src="../Icons/icon002a9.gif" />重置</z:tbutton></td>
				</tr>
				<tr style="display: none;">
					<td>全部产品信息同步：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProduct();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr style="display: none;">
					<td>全部产品销售地区同步：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProductArea();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr style="display: none;">
					<td>全部产品健康告知同步：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="syncAllProductHI();">
							<img src="../Icons/icon002a9.gif" />同步</z:tbutton></td>
				</tr>
				<tr style="display: none;">
					<td>全部产品信息发布：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="publishAllProduct();">
							<img src="../Icons/icon002a9.gif" />发布</z:tbutton><span style="color:#ff0000 ">该发布不包含已下线的产品</span></td>
				</tr>
				
				<tr style="display: none;">
					<td>快速查询静态页面生成：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="publishQuickSearchProduct();">
							<img src="../Icons/icon002a9.gif" />生成</z:tbutton><span style="color:#ff0000 ">为快速查询提供静态页面</span></td>
				</tr>				
				<tr style="display: none;">
					<td>筛选静态页面生成：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="publishSearchProduct();">
							<img src="../Icons/icon002a9.gif" />生成</z:tbutton><span style="color:#ff0000 ">为筛选提供静态页面</span></td>
				</tr>
				<tr style="display: none;">
					<td>保险公司专题静态页面生成：</td>
					<td><z:tbutton id="BtnPublishZhuanTi" priv="site_browse" onClick="publishProductSortInsureTopic();">
							<img src="../Icons/icon002a9.gif" />生成</z:tbutton><span style="color:#ff0000 ">为保险公司专题提供静态页面</span></td>
				</tr>
				<!-- //2012.11.19 add by 梅俊峰,于善春 <<20121108018-需求系统开发需求申请单-搜索页面>> start -->
				<tr style="display: none;">
                    <td>关键词搜索静态页面全部更新：</td>
					<td><z:tbutton id="BtnSyncAllProduct" priv="site_browse" onClick="updateAllStaticPage();">
							<img src="../Icons/icon002a9.gif" />更新</z:tbutton></td>
				</tr>
				<tr style="display: none;">
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
				<tr style="display: none;">
					<td>手动删除关键词：</td>
					<td>
					<input name="keyWord1" type="text" class="input1" id="keyWord1"  size="30" />
					<input type="button" name="Submit" value="删除" onClick="deleteKeyword()">
					</td>
				</tr>
				<table width="600" border="1" cellpadding="3" cellspacing="0"
				class="blockTable"  style="display: none;">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon007a1.gif" /> 关键词列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
					<z:tbutton onClick="addKeyword()"><img src="../Icons/icon007a2.gif" />新建</z:tbutton> 
					<z:tbutton onClick="saveKeyword()">
								<img src="../Icons/icon007a16.gif" />保存</z:tbutton> <z:tbutton onClick="delKeyword()"><img src="../Icons/icon007a3.gif" />删除</z:tbutton> 
					<div style="float: right; white-space: nowrap;"><input
						name="Keyword_input" type="text" id="Keyword_input" value="请输入关键字"
						onFocus="del_keyword();" style="width:110px"> <input
						type="button" name="Submitbutton" value="查询" onClick="doSearch()"
						id="Submitbutton"></div>
					</td>
				</tr>
				
				<tr>
					<td
						style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			<z:datagrid id="dg1"
						method="com.wangjin.search.AddKeyword.dg1DataBind" size="12"> 
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr ztype="head" class="dataTableHead">
								<td width="5%" ztype="RowNo" align="middle"><strong>序号</strong></td>
								<td width="5%" ztype="selector" field="id" align="middle">&nbsp;</td>
								<td width="20%" align="middle"><b>关键词</b></td>
								<td width="10%"  align="middle"><b>等级</b></td>
								<td width="10%" align="middle"><b>点击量</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="5%" align="middle">&nbsp;</td>
								<td width="5%" align="middle">&nbsp;</td>
								<td>${KeyWord}</td>
								<td>
								<div ztype=select name="LevelName_${Id}" id="Level_${Id}"
									style="width:100px;display:none" value="${Type}"
									disabled="true"><span value=0>Level 0</span> <span value=1>Level 1</span>
								<span value=2>Level 2</span><span value=3>Level 3</span><span value=4>Level 4</span>
								<span value=5>Level 5</span><span value=6>Level 6</span><span value=7>Level 7</span>
								<span value=8>Level 8</span><span value=9>Level 9</span>
								</td>
								<td align="middle">${Count}</td>
							</tr>
							<tr ztype="edit" bgcolor="#E1F3FF">
								<td width="5%" bgcolor="#E1F3FF">&nbsp;</td>
								<td width="5%" bgcolor="#E1F3FF">&nbsp;</td>
								<td width="20%" bgcolor="#E1F3FF">${KeyWord}</td>
								<td><input id="hLevel" type="hidden" value="${Type}">
								<div ztype=select name="Level" id="Level" style="width:100px">
								<span value=0>Level 0</span> <span value=1>Level 1</span>
								<span value=2>Level 2</span><span value=3>Level 3</span><span value=4>Level 4</span>
								<span value=5>Level 5</span><span value=6>Level 6</span><span value=7>Level 7</span>
								<span value=8>Level 8</span><span value=9>Level 9</span>
								</div> 
								</td>
								<td width="10%" bgcolor="#E1F3FF">${Count}</td>
							</tr>
							<tr ztype="pagebar">
								<td colspan="7" align="center">${PageBar}</td>
							</tr>
						</table>
					</z:datagrid>
					</td>
				</tr>
				</table>
<!-- //2012.11.19 add by 梅俊峰,于善春 <<20121108018-需求系统开发需求申请单-搜索页面>> end -->				
				<!--tr>
	  <td>&nbsp;</td>
      <td  align="right" >频道数：</td>
      <td>${ChannelCount}</td>
    </tr>
    <tr>
	  <td>&nbsp;</td>
      <td  align="right" >期刊数：</td>
      <td>${MagzineCount}</td>
    </tr>
    <tr>
	  <td>&nbsp;</td>
      <td  align="right" >专题数：</td>
      <td>${SpecialCount}</td>
    </tr>
    <tr!-->
			</table>
		</form>
	</div>
</body>
	</html>
</z:init>
