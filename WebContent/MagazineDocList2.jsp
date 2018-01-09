<%@ page language="java" import="java.util.*" pageEncoding="utf8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>

	<body>
		<script ztype="DataGrid">
	Page.onLoad(DataGrid_dg1_Init, 9);
	function DataGrid_dg1_Init() {
		var _Zving_Cols = [ [ "ID", 7 ], [ "Attribute", 1 ], [ "Title", 1 ],
				[ "AddUser", 1 ], [ "PublishDate", 0 ], [ "Addtime", 0 ],
				[ "Status", 7 ], [ "WorkFlowID", 7 ], [ "Type", 1 ],
				[ "TopFlag", 1 ], [ "OrderFlag", 7 ], [ "TitleStyle", 1 ],
				[ "TopDate", 0 ], [ "ReferTarget", 1 ], [ "ReferType", 7 ],
				[ "ReferSourceID", 7 ], [ "Icon", 1 ], [ "_RowNo", 8 ] ]
		var _Zving_Values = [];
		$('dg1').DataSource = new DataTable();
		$('dg1').DataSource.init(_Zving_Cols, _Zving_Values);
		var _Zving_Arr = [];
		_Zving_Arr.push("&nbsp;");
		_Zving_Arr.push("&nbsp;");
		_Zving_Arr.push("$\{Icon}");
		_Zving_Arr.push("<span title=\"$\{Title}\">$\{Title}</span>");
		_Zving_Arr.push("$\{AddUser}");
		_Zving_Arr.push("$\{StatusName}");
		_Zving_Arr.push("$\{PublishDate}");
		$('dg1').TemplateArray = _Zving_Arr;
		$('dg1').TagBody = "&lt;table&nbsp;width=&quot;100%&quot;&nbsp;cellpadding=&quot;2&quot;&nbsp;cellspacing=&quot;0&quot;&nbsp;class=&quot;dataTable&quot;&nbsp;afterdrag=&quot;afterRowDragEnd&quot;&gt;&nbsp;&lt;tr&nbsp;ztype=&quot;head&quot;&nbsp;class=&quot;dataTableHead&quot;&gt;&nbsp;&lt;td&nbsp;width=&quot;5%&quot;&nbsp;height=&quot;30&quot;&nbsp;ztype=&quot;RowNo&quot;&nbsp;drag=&quot;true&quot;&gt;&lt;img&nbsp;src=&quot;../Framework/Images/icon_drag.gif&quot;&nbsp;width=&quot;16&quot;&nbsp;height=&quot;16&quot;&gt;&lt;/td&gt;&nbsp;&lt;td&nbsp;width=&quot;3%&quot;&nbsp;height=&quot;30&quot;&nbsp;ztype=&quot;Selector&quot;&nbsp;field=&quot;id&quot;&gt;&amp;nbsp;&lt;/td&gt;&nbsp;&lt;td&nbsp;width=&quot;5%&quot;&gt;&lt;strong&gt;&#23646;&#24615;&lt;/strong&gt;&lt;/td&gt;&nbsp;&lt;td&nbsp;width=&quot;48%&quot;&nbsp;sortfield=&quot;title&quot;&nbsp;direction=&quot;&quot;&gt;&lt;b&gt;&#26631;&#39064;&lt;/b&gt;&lt;/td&gt;&nbsp;&lt;td&nbsp;width=&quot;7%&quot;&gt;&lt;strong&gt;&#21019;&#24314;&#32773;&lt;/strong&gt;&lt;/td&gt;&nbsp;&lt;td&nbsp;width=&quot;17%&quot;&gt;&lt;strong&gt;&#29366;&#24577;&lt;/strong&gt;&lt;/td&gt;&nbsp;&lt;td&nbsp;width=&quot;15%&quot;&nbsp;sortfield=&quot;publishdate&quot;&nbsp;direction=&quot;&quot;&gt;&lt;strong&gt;&#21457;&#24067;&#26102;&#38388;&lt;/strong&gt;&lt;/td&gt;&nbsp;&lt;/tr&gt;&nbsp;&lt;tr&nbsp;onDblClick=&quot;edit($\{ID});&quot;&gt;&nbsp;&lt;td&gt;&amp;nbsp;&lt;/td&gt;&nbsp;&lt;td&gt;&amp;nbsp;&lt;/td&gt;&nbsp;&lt;td&nbsp;algin=&quot;center&quot;&gt;$\{Icon}&lt;/td&gt;&nbsp;&lt;td&nbsp;style=&quot;$\{TitleStyle}&quot;&gt;&lt;span&nbsp;title=&quot;$\{Title}&quot;&gt;$\{Title}&lt;/span&gt;&lt;/td&gt;&nbsp;&lt;td&gt;$\{AddUser}&lt;/td&gt;&nbsp;&lt;td&gt;$\{StatusName}&lt;/td&gt;&nbsp;&lt;td&nbsp;title=&quot;$\{PublishDate}&quot;&gt;$\{PublishDate}&lt;/td&gt;&nbsp;&lt;/tr&gt;&nbsp;&lt;tr&nbsp;ztype=&quot;pagebar&quot;&gt;&nbsp;&lt;td&nbsp;colspan=&quot;9&quot;&nbsp;align=&quot;left&quot;&gt;$\{PageBar}&lt;/td&gt;&nbsp;&lt;/tr&gt;&nbsp;&lt;/table&gt;";
		DataGrid.setParam('dg1', 'CatalogID', "0");
		DataGrid.setParam('dg1', '_ZVING_PAGEINDEX', 0);
		DataGrid.setParam('dg1', '_ZVING_PAGETOTAL', 0);
		DataGrid.setParam('dg1', '_ZVING_SIZE', 15);
		DataGrid.setParam('dg1', '_ZVING_SORTSTRING', '');
		DataGrid.setParam('dg1', '_ZVING_MULTISELECT', 'true');
		DataGrid.setParam('dg1', '_ZVING_AUTOFILL', 'true');
		DataGrid.setParam('dg1', '_ZVING_SCROLL', 'false');
		DataGrid.setParam('dg1', '_ZVING_LAZY', 'false');
		DataGrid.init('dg1');
	}
		</script>
	</body>
</html>
