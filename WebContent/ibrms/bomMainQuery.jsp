<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务对象查询</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
	function queryDataBom() {
		if (Verify.hasError()) {
			return;
		}
		DataGrid.clear('BOMQueryDataGrid');
		DataGrid.clear('BOMITEMQueryDataGrid');
		var dc = Form.getData($F("fm"));
		for ( var i = 0; i < dc.size(); i++) {
			DataGrid.setParam("BOMQueryDataGrid", dc.getKey(i), dc.get(i));
		}
		DataGrid.setParam("BOMQueryDataGrid", Constant.PageIndex, 0);
		DataGrid.loadData("BOMQueryDataGrid");
	}

	function onselect(eNameValue, cNameValue) {
		fm.eName.value = eNameValue;
		fm.cName.value = cNameValue;
		queryDataBomIten();
	}

	function queryDataBomIten() {
		DataGrid.clear('BOMITEMQueryDataGrid');
		var dc = Form.getData($F("fm"));
		for ( var i = 0; i < dc.size(); i++) {
			DataGrid.setParam("BOMITEMQueryDataGrid", dc.getKey(i), dc.get(i));
		}
		DataGrid.setParam("BOMITEMQueryDataGrid", Constant.PageIndex, 0);
		DataGrid.loadData("BOMITEMQueryDataGrid");
	}

	function init() {
		fm.eName.value = "";
		fm.cName.value = "";
	}
</script>
<LINK href="../Include/Default.css" rel=stylesheet type=text/css>
<LINK href="../common/css/tab.css" rel=stylesheet type=text/css>
</head>
<body style="margin: 10px" onload="init();">
	<form name="fm" method="post">
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable" style="table-layout: fixed;">
			<tr>
				<td><fieldset>
						<legend>
							<label>BO对象查询</label>
						</legend>
						<table width="100%">
							<tr>
								<td width="180">&nbsp;&nbsp;BO英文名</td>
								<td width="311"><input id="eName" style="width: 150px"
									value=""></td>
								<td width="180">BO中文名</td>
								<td width="311"><input id="cName" style="width: 150px"
									value=""> <!--<z:select id="OutManageCom" style="width:200px" listWidth="260" input="true" code="ManageComDataSwitch" showValue="true" lazy="true"></z:select></td>-->
							</tr>
						</table>

					</fieldset></td>
			</tr>
			<tr>
				<td>
					<table border="0">
						<tr>
							<td><z:tbutton onClick="queryDataBom();">
									<img src="../Icons/icon403a5.gif" />查询BO</z:tbutton>
							</td>
							
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%" border="0" cellspacing="0" cellpadding="6"
						class="blockTable" style="table-layout: fixed;">
						<tr>
							<td>BO列表</td>
						</tr>
						<tr>
							<td style="padding: 0px 5px;"><z:datagrid
									id="BOMQueryDataGrid" size="200"
									method="com.sinosoft.ibrms.BOMDataQueryUI.BODataBind"
									autoFill="false" scroll="true" lazy="true" size="4"
									multiSelect="false">
									<table cellpadding="1" cellspacing="1" fixedHeight="98px"
										class="dataTable" afterdrag="afterRowDragEnd">
										<tr ztype="head" class="dataTableHead">
											<td width="3%" ztype="selector" field="id">&nbsp;</td>
											<td width="8%" ztype="RowNo" drag="true">序号</td>
											<td width="15%">BOM英文名</td>
											<td width="15%">BOM中文名</td>
											<td style="display: none;">父BOM</td>
											<td style="display: none;">本BOM字段</td>
											<td style="display: none;">父BOM字段</td>
											<td style="display: none;">BOM层次</td>
											<td style="display: none;">业务模块</td>
											<td width="30%">BOM描述信息</td>
											<td width="29%">BOM对应类</td>
											<td style="display: none;">有效性</td>

										</tr>
										<tr onClick="onselect('${Name}','${CNName}')">
											<td width="3%" ztype="selector" field="id">&nbsp;</td>
											<td width="8%">&nbsp;</td>
											<td width="15%">${Name}</td>
											<td width="15%">${CNName}</td>
											<td style="display: none;">${FBom}</td>
											<td style="display: none;">${BomLocalItem}</td>
											<td style="display: none;">${FatherItem}</td>
											<td style="display: none;">${BomLevel}</td>
											<td style="display: none;">${Business}</td>
											<td width="30%">${Discription}</td>
											<td width="29%">${Source}</td>
											<td style="display: none;">${Valid}</td>

										</tr>
										<tr ztype="PageBar">
											<td colspan="22">${PageBar}</td>
										</tr>
									</table>
								</z:datagrid>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>



		<div>

			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">

				<tr>
					<fieldset>
					<td><legend>
							<label>词条信息</label>
						</legend>
					</td>
					</fieldset>
				</tr>

				<tr>
					<td><table width="100%" border="0" cellspacing="0"
							cellpadding="6" class="blockTable" style="table-layout: fixed;">

							<tr>
								<td>词条列表</td>
							</tr>
							<tr>
								<td style="padding: 0px 5px;"><z:datagrid
										id="BOMITEMQueryDataGrid" size="200"
										method="com.sinosoft.ibrms.BOMDataQueryUI.BIDataBind"
										autoFill="false" scroll="true" lazy="true" size="4"
										multiSelect="false">
										<table cellpadding="1" cellspacing="1" fixedHeight="98px"
											class="dataTable" afterdrag="afterRowDragEnd">
											<tr ztype="head" class="dataTableHead">
												<td width="3%" ztype="selector" field="id">&nbsp;</td>
												<td width="8%" ztype="RowNo" drag="true">序号</td>
												<td width="15%">词条英文名</td>
												<td style="display: none;">BOM</td>
												<td width="15%">词条中文名</td>
												<td width="10%">连接词</td>
												<td style="display: none;">层次型数据</td>
												<td style="display: none;">基础词条</td>
												<td style="display: none;">基础数据取值类型</td>
												<td style="display: none;">基础数据取值</td>
												<td width="13%">词条数据类型</td>
												<td style="display: none;">词条预校验</td>
												<td style="display: none;">有效性</td>
												<td width="36%">词条描述</td>


											</tr>
											<tr>
												<td width="3%" ztype="selector" field="id">&nbsp;</td>
												<td width="8%">&nbsp;</td>
												<td width="15%">${Name}</td>
												<td style="display: none;">${BomName}</td>
												<td width="15%">${CnName}</td>
												<td width="10%">${Connectoer}</td>
												<td style="display: none;">${IsHierarchical}</td>
												<td style="display: none;">${IsBase}</td>
												<td style="display: none;">${SourceType}</td>
												<td style="display: none;">${Source}</td>
												<td width="13%">${CommandType}</td>
												<td style="display: none;">${PreCheck}</td>
												<td style="display: none;">${Valid}</td>
												<td width="36%">${Description}</td>
											</tr>
											<tr ztype="PageBar">
												<td colspan="22">${PageBar}</td>
											</tr>

										</table>
									</z:datagrid>
								</td>
							</tr>
						</table>
					<td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
