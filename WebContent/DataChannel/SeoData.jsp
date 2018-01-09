<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="z"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SEO数据分析</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
	/**
	 * 清空查询条件.
	 */
	function doblank() {
		createDate.value = "";
		endCreateDate.value = "";
		unRadioChecked();
	}
	/**
	 * 反选Radio.
	 */
	function unRadioChecked() {
		var chkObjs = document.getElementsByName("seoDay");
		for ( var i = 0; i < chkObjs.length; i++) {
			if (chkObjs[i].checked) {
				chkObjs[i].checked = false;
				;
				break;
			}
		}
	}

	/**
	 * 反选Radio.
	 */
	function emptyDate() {
		createDate.value = "";
		endCreateDate.value = "";
	}
	/**
	 * 查询.
	 */
	function doSearch() {
		var chk = -1;
		var chkObjs = document.getElementsByName("seoDay");
		for ( var i = 0; i < chkObjs.length; i++) {
			if (chkObjs[i].checked) {
				chk = i;
				break;
			}
		}

		DataGrid.setParam("dg1", "seoType", $V("seoType"));
		DataGrid.setParam("dg1", "seoDay", chk);
		DataGrid.setParam("dg1", "createDate", $V("createDate"));
		DataGrid.setParam("dg1", "endCreateDate", $V("endCreateDate"));

		DataGrid.loadData("dg1");
	}

	/**
	 * 数据明细.
	 */
	function view(type, cDate) {
		var diag = new Dialog("Diag1");
		diag.Width = 1000;
		diag.Height = 600;
		diag.Title = "SEO数据明细";
		if (type == "1") {
			diag.URL = "DataChannel/SeoDataDetail_baidu.jsp?type=" + type
					+ "&createDate=" + cDate;
		} else if (type == "2") {
			diag.URL = "DataChannel/SeoDataDetail_360.jsp?type=" + type
					+ "&createDate=" + cDate;
		}
		diag.onLoad = function() {
		};
		diag.ShowMessageRow = true;
		diag.MessageTitle = "明细";
		diag.show();
		diag.OKButton.hide();
		diag.CancelButton.value = "关闭";
	}

	/**
	 * 手动执行.
	 */
	function execute() {
		Dialog.confirm("确定要执行该任务吗？", function() {
			var dc = new DataCollection();
			var type = $V("seoType");
			dc.add("type", type);
			var msg;
			if (type == "1") {
				msg = "百度";
			} else if (type == "2") {
				msg = "360";
			}

			Server.sendRequest("com.sinosoft.datachannel.SeoData.synExecute", dc,
					function(response) {
						var taskID = response.get("TaskID");
						var p = new Progress(taskID, "正在抓取...");
						p.show();
					});
		});
	}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon005a13.gif" width="20" height="20" />&nbsp;SEO数据统计</td>
			</tr>
			<tr>
				<td>
				<table cellspacing="0" cellpadding="3">
					<tr>
						<td nowrap>数据类型：</td>
						<td><z:select style="width:120px;">
							<select name="seoType" id="seoType">
								<option value="1">SEO_百度</option>
								<option value="2">SEO_360</option>
							</select>
						</z:select></td>
						<td><input type="radio" name="seoDay" onclick=
	emptyDate();
/>今日&nbsp;&nbsp;<input
							type="radio" name="seoDay" onclick=
	emptyDate();
/>昨日&nbsp;&nbsp;
						<input type="radio" name="seoDay" onclick=
	emptyDate();
/>近7天&nbsp;&nbsp;<input
							type="radio" name="seoDay" onclick=
	emptyDate();
/>近15天&nbsp;&nbsp;
						<input type="radio" name="seoDay" onclick=
	emptyDate();
/>近30天&nbsp;&nbsp;</td>
						<td nowrap>从：</td>
						<td nowrap><input name="createDate" type="text"
							id="createDate" value="" style="width: 100px" ztype="date"
							verify="Date" onclick=
	unRadioChecked();
/> 至：<input
							name="endCreateDate" type="text" id="endCreateDate" value=""
							style="width: 100px" ztype="date" verify="Date"
							onclick=
	unRadioChecked();
/></td>

					</tr>
					<tr>
						<td colspan="5"><z:tbutton onClick="doSearch()"
							id="Submitbutton">
							<img src="../Icons/icon005a2.gif" width="20" height="20" />查询
			            	   </z:tbutton> <z:tbutton onClick="execute()">
							<img src="../Icons/icon403a12.gif" width="20" height="20" />手动更新</z:tbutton>
						<z:tbutton onClick="doblank()">
							<img src="../Icons/icon401a3.gif" width="20" height="20" />清空查询条件</z:tbutton>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<!--			<tr>-->
			<!--				<td style="padding:0 8px 4px;">-->
			<!--				   预留-折线图功能-->
			<!--				</td>-->
			<!--			</tr>-->

			<tr>
				<td style="padding: 0px 5px;">
				<table width="100%" border="0" style="table-layout: fixed;">
					<tr>
						<td
							style="padding-top: 0px; padding-left: 6px; padding-right: 6px; padding-bottom: 8px;">
						<z:datagrid id="dg1"
							method="com.sinosoft.datachannel.SeoData.dg1DataBind"
							page="false" scroll="true">
							<table width="100%" cellpadding="2" cellspacing="0"
								class="dataTable"
								style="text-align: center; table-layout: fixed;"
								fixedHeight="450px">
								<tr ztype="head" class="dataTableHead">
									<td width="4%" ztype="RowNo"><b>序号</b></td>
									<td width="3%" ztype="selector" field="ID">&nbsp;</td>
									<td width="10%"><b>日期</b></td>
									<td width="12%"><b>排名第1</b></td>
									<td width="12%"><b>排名2-5</b></td>
									<td width="12%"><b>排名6-10</b></td>
									<td width="12%"><b>排名11-20</b></td>
									<td width="12%"><b>排名21-30</b></td>
									<td width="12%"><b>排名31-50</b></td>
									<td width="12%"><b>总数量</b></td>
								</tr>
								<tr onDblClick="view('${ttype}','${cDate}')"
									style1="background-color:#FFFFFF"
									style2="background-color:#F9FBFC">
									<td>&nbsp;</td>
									<td>&nbsp;</td>
									<td>${cDate}</td>
									<td>${p1}</td>
									<td>${p2}</td>
									<td>${p3}</td>
									<td>${p4}</td>
									<td>${p5}</td>
									<td>${p6}</td>
									<td>${cSum}</td>
								</tr>
							</table>
						</z:datagrid></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
