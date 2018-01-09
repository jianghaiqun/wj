package com.sinosoft.framework.controls;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.ResponseImpl;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.framework.utility.Treex;
import com.sinosoft.framework.utility.Treex.TreeIterator;
import com.sinosoft.framework.utility.Treex.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataGridAction implements IControlAction,Serializable {
	private static final Logger logger = LoggerFactory.getLogger(DataGridAction.class);

	protected String ID;

	private boolean MultiSelect = true;

	private boolean AutoFill = true;

	private boolean Scroll = false;

	private boolean Lazy = false;

	private int cacheSize;

	protected HtmlTable Template;

	protected int PageSize;

	protected int PageIndex;

	private int Total;

	private boolean PageFlag;

	protected boolean SortFlag;

	protected boolean TreeFlag;

	protected boolean WebMode = true;

	protected StringBuffer SortString = new StringBuffer();

	protected String Method;

	protected String TagBody;

	protected DataTable DataSource;

	protected HtmlTable Table;

	protected Mapx Params = new Mapx();

	public ResponseImpl Response;

	ArrayList a1 = new ArrayList();

	ArrayList a2 = new ArrayList();

	String style1;

	String style2;

	String class1;

	String class2;

	String templateHtml;

	HtmlTR headTR = null;

	HtmlTR templateTR = null;

	HtmlTR editTR = null;

	HtmlTR pageBarTR = null;

	boolean isSimplePageBar = false;

	boolean TotalFlag = false;// 是否需要重新计算记录总数

	public void parse() throws Exception {
		Table = new HtmlTable();
		Table.setAttributes(Template.getAttributes());

		for (int i = 0; i < Template.Children.size(); i++) {
			HtmlTR tr = (HtmlTR) Template.Children.get(i);

			if ("Head".equalsIgnoreCase((String) tr.getAttribute("ztype"))) {
				headTR = tr;
			} else if ("PageBar".equalsIgnoreCase((String) tr.getAttribute("ztype"))) {
				pageBarTR = tr;
			} else if ("SimplePageBar".equalsIgnoreCase((String) tr.getAttribute("ztype"))) {
				pageBarTR = tr;
				isSimplePageBar = true;
			} else if ("Edit".equalsIgnoreCase((String) tr.getAttribute("ztype"))) {
				editTR = tr;
				editTR.setAttribute("style", "display:none");
			} else {
				style1 = (String) tr.getAttribute("style1");
				style2 = (String) tr.getAttribute("style2");
				class1 = (String) tr.getAttribute("class1");
				class2 = (String) tr.getAttribute("class2");
				tr.removeAttribute("style1");
				tr.removeAttribute("style2");
				tr.removeAttribute("class1");
				tr.removeAttribute("class2");
				templateTR = tr;
			}
		}
		if (headTR == null) {
			headTR = Template.getTR(0);
		}
		Table.addTR(headTR);

		// 排序
		String str = (String) Params.get(Constant.DataGridSortString);// 点击能排序的标题栏后会传入此字符串
		boolean firstSortFieldFlag = true;
		boolean emptyFlag = true;
		Mapx sortMap = new Mapx();
		if (StringUtil.isNotEmpty(str) && StringUtil.verify(str, "String")) {
			if (Constant.Null.equals(str)) {// 空字符串
				str = "";
			}
			SortString.append(str);
			String[] arr = str.split("\\,");
			for (int i = 0; i < arr.length; i++) {
				if (arr[i].indexOf(' ') > 0) {
					String[] arr2 = arr[i].split("\\s");
					sortMap.put(arr2[0].trim().toLowerCase(), arr2[1].trim());
				} else {
					sortMap.put(arr[i].trim().toLowerCase(), "");
				}
			}
			emptyFlag = false;
		}
		for (int i = 0; i < headTR.Children.size(); i++) {
			HtmlTD td = headTR.getTD(i);
			// 排序
			String ztype = (String) td.getAttribute("ztype");
			if ("Tree".equalsIgnoreCase(ztype)) {
				TreeFlag = true;
			}
			String sortField = (String) td.getAttribute("sortField");
			String direction = (String) td.getAttribute("direction");
			if (StringUtil.isNotEmpty(sortField)) {
				this.SortFlag = true;
				if (emptyFlag) {
					if (StringUtil.isNotEmpty(direction)) {
						if (!firstSortFieldFlag) {
							SortString.append(",");
						}
						SortString.append(sortField);
						SortString.append(" ");
						SortString.append(direction);
						firstSortFieldFlag = false;
					} else {
						direction = "";
					}
				} else {
					direction = (String) sortMap.get(sortField.toLowerCase());
					if (StringUtil.isEmpty(direction)) {
						direction = "";
					}
					td.setAttribute("direction", direction);
				}
			}
			if (StringUtil.isNotEmpty(sortField)) {
				td.setAttribute("style", "cursor:pointer");
				td.setAttribute("onMouseOver", "DataGrid.onSortHeadMouseOver(this);");
				td.setAttribute("onMouseOut", "DataGrid.onSortHeadMouseOut(this);");
				td.setAttribute("onClick", "DataGrid.onSort(this);");
				StringBuffer sb = new StringBuffer();
				sb.append("<span style='float:left'>");
				sb.append(td.getInnerHTML());
				sb.append("</span>");
				sb.append("<img src='");
				sb.append(Config.getContextPath());
				sb.append("Framework/Images/icon_sort");
				sb.append(direction.toUpperCase());
				sb.append(".gif' width='12' height='12' style='float:right'>");
				td.setInnerHTML(sb.toString());
			}
		}

		Table.setAttribute("SortString", SortString.toString());

		Table.setAttribute("id", this.ID);
		Table.setAttribute("page", "" + this.PageFlag);
		Table.setAttribute("size", "" + this.PageSize);
		Table.setAttribute("method", this.Method);
		Table.setAttribute("multiSelect", "" + this.MultiSelect);
		Table.setAttribute("autoFill", "" + this.AutoFill);
		Table.setAttribute("scroll", "" + this.Scroll);
		Table.setAttribute("Lazy", "" + this.Lazy);
		Table.setAttribute("cacheSize", "" + this.cacheSize);

		templateHtml = templateTR.getOuterHtml();
		if (templateHtml == null) {
			throw new RuntimeException("DataGrid不能没有模板行");
		}

		Matcher m = Constant.PatternField.matcher(templateHtml);
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			a1.add(templateHtml.substring(lastEndIndex, m.start()));
			a2.add(m.group(1));
			lastEndIndex = m.end();
		}
		a1.add(templateHtml.substring(lastEndIndex));
	}

	private void bindData() throws Exception {
		if (!PageFlag) {
			Total = DataSource.getRowCount();
		}

		if (TreeFlag) {// 自动排序
			if (DataSource.getDataColumn("ParentID") != null && DataSource.getDataColumn("ID") != null) {
				DataSource = sortTreeDataTable(DataSource, "ID", "ParentID");
			}
		}

		if (DataSource == null) {
			throw new RuntimeException("必须在bindData方法中设定DataSource");
		}

		// DataSource.setWebMode(WebMode);
		if (DataSource.getDataColumn("_RowNo") == null) {
			DataSource.insertColumn(new DataColumn("_RowNo", DataColumn.INTEGER));
		}
		for (int j = 0; j < DataSource.getRowCount(); j++) {
			int rowNo = this.PageIndex * this.PageSize + j + 1;
			DataSource.set(j, "_RowNo", new Integer(rowNo));
		}

		for (int i = 0; i < DataSource.getRowCount(); i++) {
			DataRow dr = DataSource.getDataRow(i);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < a1.size(); j++) {
				sb.append(a1.get(j));
				if (j < a2.size()) {
					sb.append(dr.getString(a2.get(j).toString()));
				}
			}
			HtmlTR tr = new HtmlTR(Table);
			tr.parseHtml(sb.toString());
			if (i % 2 == 1) {
				if (style1 != null) {
					tr.setAttribute("style", style1);
				}
				if (class1 != null) {
					tr.setAttribute("class", class1);
				}
			} else {
				if (style2 != null) {
					tr.setAttribute("style", style2);
				}
				if (class2 != null) {
					tr.setAttribute("class", class2);
				}
			}
			String clickEvent = (String) tr.getAttribute("onclick");
			if (StringUtil.isEmpty(clickEvent)) {
				clickEvent = "";
			}
			tr.setAttribute("onclick", "DataGrid.onRowClick(this,event);" + clickEvent);// 单击选择
			String dblEvent = (String) tr.getAttribute("ondblclick");// 双击编辑
			if (StringUtil.isNotEmpty(dblEvent)) {
				tr.setAttribute("ondblclick", dblEvent);
			} else if (editTR != null) {
				tr.setAttribute("ondblclick", "DataGrid.editRow(this)");
			}
			tr.setAttribute("oncontextmenu", "DataGrid._onContextMenu(this,event)");
			// 禁止选择
			// tr.setAttribute("onselectstart", "stopEvent(event);");//
			// 仅适用于IE，firefox下在DataGrid.init()中实现
			Table.addTR(tr);
		}

		// 单选、多选
		for (int i = 0; i < headTR.Children.size(); i++) {
			// 行号
			boolean rowNoFlag = "RowNo".equalsIgnoreCase((String) headTR.getTD(i).getAttribute("ztype"));
			for (int j = 1; j < Table.Children.size(); j++) {
				int rowNo = this.PageIndex * this.PageSize + j;
				if (rowNoFlag) {
					Table.getTR(j).getTD(i).setInnerHTML("" + rowNo);
					Table.getTR(j).getTD(i).setAttribute("rowno", "" + rowNo);
				}
			}

			// 选择器
			if ("Selector".equalsIgnoreCase((String) headTR.getTD(i).getAttribute("ztype"))) {
				String field = (String) headTR.getTD(i).getAttribute("field");
				String onSelect = (String) headTR.getTD(i).getAttribute("onselect");
				if (onSelect == null) {
					onSelect = "";
				}
				if (MultiSelect) {
					headTR.getTD(i).setInnerHTML(
							"<input type='checkbox' value='*' id='" + ID
									+ "_AllCheck' onclick=\"DataGrid.onAllCheckClick('" + ID + "')\"/>");
				}
				String type = MultiSelect ? "checkbox" : "radio";
				for (int j = 1; j < Table.Children.size(); j++) {
					Table.getTR(j).getTD(i).setInnerHTML(
							"<input type='" + type + "' name='" + ID + "_RowCheck' id='" + ID + "_RowCheck" + j
									+ "' value='" + DataSource.getString(j - 1, field) + "'>");
					Table.getTR(j).getTD(i).setAttribute("onclick", "DataGrid.onSelectorClick(this,event);" + onSelect);
					Table.getTR(j).getTD(i).setAttribute("ondblclick", "stopEvent(event);" + onSelect);
				}
			}
			// 选择框列
			if ("Checkbox".equalsIgnoreCase((String) headTR.getTD(i).getAttribute("ztype"))) {
				String field = (String) headTR.getTD(i).getAttribute("field");
				String checkedvalue = (String) headTR.getTD(i).getAttribute("checkedvalue");
				String disabled = (String) headTR.getTD(i).getAttribute("disabled");
				if (checkedvalue == null) {
					checkedvalue = "Y";
				}
				if (disabled == null || disabled.equalsIgnoreCase("true")) {
					disabled = "disabled";
				} else {
					disabled = "";
				}
				for (int j = 1; j < Table.Children.size(); j++) {
					String checked = checkedvalue.equals(DataSource.getString(j - 1, field)) ? "checked" : "";
					Table.getTR(j).getTD(i).setInnerHTML(
							"<input type='checkbox' " + disabled + " name='" + ID + "_" + field + "_Checkbox' id='"
									+ ID + "_" + field + "_Checkbox" + j + "' value='" + checkedvalue + "' " + checked
									+ ">");
				}
			}
			// 下拉列表
			if ("DropDownList".equalsIgnoreCase((String) headTR.getTD(i).getAttribute("ztype"))) {
				String field = (String) headTR.getTD(i).getAttribute("field");
				String sql = (String) headTR.getTD(i).getAttribute("sql");
				String zstyle = (String) headTR.getTD(i).getAttribute("zstyle");
				if (StringUtil.isEmpty(zstyle)) {
					zstyle = "width:100px";
				}
				DataTable dt = new QueryBuilder(sql).executeDataTable();
				for (int j = 1; j < Table.Children.size(); j++) {
					StringBuffer sb = new StringBuffer();
					sb.append("<div ztype='select' disabled='true' style='display:none;" + zstyle + ";' name='" + ID
							+ "_" + field + "_DropDownList" + j + "' id='" + ID + "_" + field + "_DropDownList" + j
							+ "' >");
					for (int k = 0; k < dt.getRowCount(); k++) {
						String value = DataSource.getString(j - 1, field);
						String selected = "";
						if (value.equals(dt.getString(k, 0))) {
							selected = "selected='true'";
						}
						sb.append("<span value='" + dt.getString(k, 0) + "' " + selected + ">" + dt.getString(k, 1)
								+ "</span>");
					}
					sb.append("</div>");
					Table.getTR(j).getTD(i).setInnerHTML(sb.toString());
				}
			}
			// 树状列
			if ("Tree".equalsIgnoreCase((String) headTR.getTD(i).getAttribute("ztype"))) {
				// 树的节点字段
				String field = (String) headTR.getTD(i).getAttribute("field");
				// 树的层级，用来处理缩进
				String str_level = (String) headTR.getTD(i).getAttribute("level");

				for (int j = 1; j < Table.Children.size(); j++) {
					//
					if(StringUtil.isNotEmpty(DataSource.getString(j - 1, str_level))){
					int level = Integer.parseInt(DataSource.getString(j - 1, str_level));

					StringBuffer sb = new StringBuffer();
					for (int k = 0; k < level; k++) {
						sb.append("<q style='padding:0 10px'></q>");
					}
					int nextLevel = 0;
					if (j != Table.Children.size() - 1) {
						nextLevel = Integer.parseInt(DataSource.getString(j, str_level));
					}
					if (level < nextLevel) {
						sb.append("<img src='" + Config.getContextPath()
								+ "Framework/Images/butExpand.gif' onclick='DataGrid.treeClick(this)'/>&nbsp;");
					} else {
						sb.append("<img src='" + Config.getContextPath() + "Framework/Images/butNoChild.gif'/>&nbsp;");
					}
					if (field != null) {
						// 树的节点的值
						String treeID = DataSource.getString(j - 1, field);
						sb.append("<input type='checkbox'  name='" + ID + "_TreeRowCheck' id='" + ID + "_TreeRowCheck_"
								+ j + "' value='" + treeID + "' level='" + level
								+ "' onClick='treeCheckBoxClick(this);'>");
					}

					sb.append(Table.getTR(j).getTD(i).getInnerHTML());
					Table.getTR(j).getTD(i).setInnerHTML(sb.toString());
					Table.getTR(j).setAttribute("level", "" + level);
					}
				}
			}

			// 拖拽
			if ("true".equalsIgnoreCase((String) headTR.getTD(i).getAttribute("drag"))) {
				for (int j = 1; j < Table.Children.size(); j++) {
					HtmlTD td = Table.getTR(j).getTD(i);
					String style = (String) td.getAttribute("style");
					if (style == null) {
						style = "";
					}
					td.setAttribute("style", "cursor:move;" + style);
					td.setAttribute("dragStart", "DataGrid.dragStart");
					td.setAttribute("onMouseDown", "DragManager.onMouseDown(event,this)");

					Table.getTR(j).setAttribute("dragEnd", "DataGrid.dragEnd");
					Table.getTR(j).setAttribute("onMouseUp", "DragManager.onMouseUp(event,this)");

					String userAgent = Params.getString("Header.UserAgent");
					if (userAgent == null) {
						userAgent = "";
					} else {
						userAgent = userAgent.toLowerCase();
					}
					if (userAgent.indexOf("msie") >= 0) {
						Table.getTR(j).setAttribute("onMouseEnter", "DragManager.onMouseOver(event,this)");
					} else {
						Table.getTR(j).setAttribute("onMouseOver", "DragManager.onMouseOver(event,this)");
					}
					Table.getTR(j).setAttribute("dragOver", "DataGrid.dragOver");

					if (userAgent.indexOf("msie") >= 0) {
						Table.getTR(j).setAttribute("onMouseLeave", "DragManager.onMouseOut(event,this)");
					} else {
						Table.getTR(j).setAttribute("onMouseOut", "DragManager.onMouseOut(event,this)");
					}
					Table.getTR(j).setAttribute("dragOut", "DataGrid.dragOut");
				}
			}
		}

		// 用空白行撑起整个表格，前台JS中有类似代码，为了改善用户体验，使用用户不能看到DataGrid撑开的过程而添加此段代码
		if (this.AutoFill) {
			if (!this.PageFlag) {
				if (this.DataSource.getRowCount() < 15) {
					HtmlTR tr = new HtmlTR(Table);
					tr.setAttribute("ztype", "blank");
					for (int i = 0; i < headTR.Children.size(); i++) {
						tr.addTD(new HtmlTD());
					}
					tr.setAttribute("height", String.valueOf((15 - this.DataSource.getRowCount()) * 23));
					Table.addTR(tr);
				}
			} else {
				if (this.DataSource.getRowCount() < this.PageSize) {
					HtmlTR tr = new HtmlTR(Table);
					tr.setAttribute("ztype", "blank");
					for (int i = 0; i < headTR.Children.size(); i++) {
						tr.addTD(new HtmlTD());
					}
					tr.setAttribute("height", String.valueOf((this.PageSize - this.DataSource.getRowCount()) * 23));
					Table.addTR(tr);
				}
			}
		}

		for (int i = 0; i < Table.getChildren().size(); i++) {
			HtmlTR tr = Table.getTR(i);
			for (int j = 0; j < tr.getChildren().size(); j++) {
				HtmlTD td = tr.getTD(j);
				if (StringUtil.isEmpty(td.getInnerHTML())) {
					td.setInnerHTML("&nbsp;");
				}
			}
		}
	}

	public String getHtml() {
		HtmlScript script = new HtmlScript();
		script.setAttribute("ztype", "DataGrid");
		script.setInnerHTML(getScript());
		Table.addChild(script);
		String html = null;
		if (Scroll) {
			html = scrollWrap();
		} else {
			// 分页
			if (!TreeFlag && PageFlag && pageBarTR != null) {
				dealPageBar();
				Table.addTR(pageBarTR);
			}
			html = Table.getOuterHtml();
		}
		return html;
	}

	public String scrollWrap() {
		// 必须修正td为th
		for (int i = 0; i < Table.getTR(0).getChildren().size(); i++) {
			Table.getTR(0).getTD(i).setHead(true);
		}
		Table.removeAttribute("class");
		Table.getTR(0).removeAttribute("class");

		StringBuffer sb = new StringBuffer();
		String fw = (String) Table.getAttribute("fixedWidth");
		String fh = (String) Table.getAttribute("fixedHeight");

		// 加入固定头部
		sb.append("<div id='" + ID + "_Wrap' class='dataTable dt_scrollable dt_nobr' ztype='_DataGridWrapper'");
		if (StringUtil.isNotEmpty(fw)) {
			sb.append(" style='width:" + fw + ";'");
		}
		sb.append(">");
		sb.append("<div id='" + ID + "_Wrap_head' class='dt_head'>");
		HtmlTable tmpTable = (HtmlTable) Table.clone();
		for (int i = tmpTable.Children.size() - 1; i > 0; i--) {
			tmpTable.removeTR(i);
		}
		tmpTable.setID(null);
		tmpTable.setClassName("dt_headTable");
		tmpTable.getTR(0).setClassName("dt_headTr");
		for (int i = 0; i < tmpTable.getTR(0).getChildren().size(); i++) {
			HtmlTD td = tmpTable.getTR(0).getTD(i);
			td.InnerHTML = "<div id='dataTable0_th" + i + "' class='dt_th'>" + td.InnerHTML + "</div>";
		}
		sb.append(tmpTable.getOuterHtml());
		sb.append("</div>");

		// 加入DataGrid本身
		sb.append("<div id='" + ID + "_Wrap_body' class='dt_body' style='");
		if (StringUtil.isNotEmpty(fw) && fw.indexOf("%") < 0) {
			sb.append("width:" + fw + ";");
		}
		if (StringUtil.isNotEmpty(fh)) {
			sb.append("height:" + fh + ";");
		}
		sb.append("'>");
		sb.append(Table.getOuterHtml());
		sb.append("</div>");

		// 加入分页条
		if (!TreeFlag && PageFlag && pageBarTR != null) {
			dealPageBar();

			HtmlTable footTable = new HtmlTable();
			footTable.setAttribute("width", "100%");
			footTable.addTR(pageBarTR);

			sb.append("<div class='dt_foot'>");
			sb.append(footTable.getOuterHtml());
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	private void dealPageBar() {
		try {
			for (int i = pageBarTR.Children.size() - 1; i > 0; i--) {
				pageBarTR.removeTD(i);
			}
			String html = getPageBarHtml(ID, Params, Total, PageIndex, PageSize, isSimplePageBar);
			pageBarTR.getTD(0).setInnerHTML(html);
			pageBarTR.getTD(0).setColSpan("" + headTR.Children.size());
			pageBarTR.getTD(0).setID(ID + "_PageBar");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		pageBarTR.setAttribute("dragOver", "DataGrid.dragOver");
		pageBarTR.setAttribute("dragOut", "DataGrid.dragOut");
		pageBarTR.setAttribute("dragEnd", "DataGrid.dragEnd");
	}

	public String getScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("Page.onLoad(DataGrid_" + ID + "_Init,9);");
		sb.append("function DataGrid_" + ID + "_Init(){");
		if (DataSource != null) {
			sb.append(DataCollection.dataTableToJS(DataSource));
			sb.append("$('" + ID + "').DataSource = new DataTable();");
			sb.append("$('" + ID + "').DataSource.init(_Zving_Cols,_Zving_Values);");
		}
		sb.append("var _Zving_Arr = [];");
		HtmlTR tr = new HtmlTR(Table);
		try {
			tr.parseHtml(templateHtml);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		for (int i = 0; i < tr.getChildren().size(); i++) {
			sb.append("_Zving_Arr.push(\"" + StringUtil.javaEncode(tr.getTD(i).getInnerHTML()) + "\");");
		}
		sb.append("$('" + ID + "').TemplateArray = _Zving_Arr;");
		if (editTR != null) {
			sb.append("_Zving_Arr = [];\n");
			for (int i = 0; i < editTR.getChildren().size(); i++) {
				sb.append("_Zving_Arr.push(\"" + StringUtil.javaEncode(editTR.getTD(i).getInnerHTML()) + "\");");
			}
			sb.append("$('" + ID + "').EditArray = _Zving_Arr;");
		}

		// 加入标签本身
		sb.append("$('" + ID + "').TagBody = \"" + StringUtil.htmlEncode(getTagBody().replaceAll("\\s+", " ")) + "\";");
		Object[] ks = Params.keyArray();
		Object[] vs = Params.valueArray();
		for (int i = 0; i < Params.size(); i++) {
			Object key = ks[i];
			if (!key.equals(Constant.TagBody) && !key.toString().startsWith("Cookie.")
					&& !key.toString().startsWith("Header.") && vs[i] != null) {
				if (vs[i] instanceof DataTable || vs[i] instanceof SchemaSet) {// 处理后台传递到前台的DataTable
					DataTable dt = null;
					if (vs[i] instanceof SchemaSet) {
						dt = ((SchemaSet) vs[i]).toDataTable();
					} else {
						dt = (DataTable) vs[i];
					}
					sb.append(DataCollection.dataTableToJS(dt));
					sb.append("var _TmpDt = new DataTable();");
					sb.append("_TmpDt.init(_Zving_Cols,_Zving_Values);");
					sb.append("DataGrid.setParam('" + ID + "','" + key + "',_TmpDt);");
				} else {
					sb.append("DataGrid.setParam('" + ID + "','" + key + "',\""
							+ StringUtil.javaEncode(vs[i].toString()) + "\");");
				}
			}
		}
		if (this.PageFlag) {
			sb.append("DataGrid.setParam('" + ID + "','" + Constant.DataGridPageIndex + "'," + this.PageIndex + ");");
			sb.append("DataGrid.setParam('" + ID + "','" + Constant.DataGridPageTotal + "'," + this.Total + ");");
			sb.append("DataGrid.setParam('" + ID + "','" + Constant.Size + "'," + this.PageSize + ");");
		}
		if (this.SortFlag) {
			sb.append("DataGrid.setParam('" + ID + "','" + Constant.DataGridSortString + "','" + this.SortString
					+ "');");
		}
		sb.append("DataGrid.setParam('" + ID + "','" + Constant.DataGridMultiSelect + "','" + this.MultiSelect + "');");
		sb.append("DataGrid.setParam('" + ID + "','" + Constant.DataGridAutoFill + "','" + this.AutoFill + "');");
		sb.append("DataGrid.setParam('" + ID + "','" + Constant.DataGridScroll + "','" + this.Scroll + "');");
		sb.append("DataGrid.setParam('" + ID + "','" + "_ZVING_LAZY" + "','" + this.Lazy + "');");
		if (cacheSize > 0) {
			sb.append("DataGrid.setParam('" + ID + "','" + "_ZVING_CACHESIZE" + "','" + this.cacheSize + "');");
		}

		sb.append("DataGrid.init('" + ID + "');");
		sb.append("}");

		String content = sb.toString();
		Matcher matcher = Constant.PatternField.matcher(content);
		sb = new StringBuffer();
		int lastEndIndex = 0;
		while (matcher.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("$\\{");
			sb.append(matcher.group(1));
			sb.append("}");
			lastEndIndex = matcher.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		matcher = Constant.PatternSpeicalField.matcher(content);
		sb = new StringBuffer();
		lastEndIndex = 0;
		while (matcher.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, matcher.start()));
			sb.append("${#");// 这是因为在script中需要javaEncode，但在html中不需要，所以需要区别@与#
			sb.append(matcher.group(1));
			sb.append("}");
			lastEndIndex = matcher.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public HtmlTable getTemplate() {
		return Template;
	}

	public void setTemplate(HtmlTable table) {
		Template = table;
	}

	public DataTable getDataSource() {
		return DataSource;
	}

	public void bindData(QueryBuilder qb) {
		bindData(qb, this.PageFlag);
	}

	public void bindData(QueryBuilder qb, boolean pageFlag) {
		if (pageFlag) {
			if (!TotalFlag) {// 需要重新计算总数
				setTotal(DataAccess.getCount(DBConnPool.getDBConnConfig().DBType, qb));
			}
			bindData(qb.executePagedDataTable(this.PageSize, this.PageIndex));
		} else {
			bindData(qb.executeDataTable());
		}
	}

	public void bindData(DataTable dt) {
		// toExcelFlag 为1时表示导出为Excel
		if ("1".equals(Params.get("_ExcelFlag"))) {
			String[] columnNames = (String[]) Params.get("_ColumnNames");
			String[] widths = (String[]) Params.get("_Widths");
			String[] columnIndexes = (String[]) Params.get("_ColumnIndexes");
			DataTableUtil.dataTableToExcel(dt, (OutputStream) Params.get("_OutputStream"), columnNames, widths,
					columnIndexes);
		} else {
			this.DataSource = dt;
			try {
				this.bindData();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void bindData(SchemaSet set) {
		bindData(set.toDataTable());
		/* ${_ZVING_LICENSE_CODE_} */
	}

	public static String getPageBarHtml(String id, Mapx params, int total, int pageIndex, int pageSize) {
		return getPageBarHtml(id, params, total, pageIndex, pageSize, false);
	}

	public static String getPageBarHtml(String id, Mapx params, int total, int pageIndex, int pageSize,
			boolean simpleFlag) {
		StringBuffer sb = new StringBuffer();
		int totalPages = new Double(Math.ceil(total * 1.0 / pageSize)).intValue();

		params.put(Constant.DataGridPageTotal, "" + total);
		params.remove(Constant.DataGridPageIndex);

		sb.append("<div style='float:right;font-family:Tahoma'>");
		if (pageIndex > 0) {
			sb.append("<a href='javascript:void(0);' onclick=\"DataGrid.firstPage('" + id
					+ "');\">第一页</a>&nbsp;|&nbsp;");
			sb.append("<a href='javascript:void(0);' onclick=\"DataGrid.previousPage('" + id
					+ "');\">上一页</a>&nbsp;|&nbsp;");
		} else {
			sb.append("第一页&nbsp;|&nbsp;");
			sb.append("上一页&nbsp;|&nbsp;");
		}
		if (totalPages != 0 && pageIndex + 1 != totalPages) {
			sb
					.append("<a href='javascript:void(0);' onclick=\"DataGrid.nextPage('" + id
							+ "');\">下一页</a>&nbsp;|&nbsp;");
			sb.append("<a href='javascript:void(0);' onclick=\"DataGrid.lastPage('" + id + "');\">最末页</a>");
		} else {
			sb.append("下一页&nbsp;|&nbsp;");
			sb.append("最末页&nbsp;&nbsp;");
		}
		if (!simpleFlag) {
			sb
					.append("&nbsp;&nbsp;转到第&nbsp;<input id='_PageBar_Index' type='text' class='inputText' style='width:40px' ");
			sb.append("onKeyUp=\"value=value.replace(/\\D/g,'')\">&nbsp;页");
			sb
					.append("&nbsp;&nbsp;<input type='button' onclick=\"if(!/^\\d+$/.test($V('_PageBar_Index'))||$V('_PageBar_Index')<1||$V('_PageBar_Index')>"
							+ totalPages
							+ "){alert('错误的页码');$('_PageBar_Index').focus();}else{var pageIndex = ($V('_PageBar_Index')-1)>0?$V('_PageBar_Index')-1:0;DataGrid.setParam('"
							+ id
							+ "','"
							+ Constant.DataGridPageIndex
							+ "',pageIndex);DataGrid.discard('"
							+ id
							+ "');}\" class='inputButton' value='跳转'>");
		}
		sb.append("</div>");
		sb.append("<div style='float:left;font-family:Tahoma'>");
		sb.append("共 " + total + " 条记录，每页 " + pageSize + " 条，当前第 " + (totalPages == 0 ? 0 : (pageIndex + 1)) + " / "
				+ totalPages + " 页</div>");

		return sb.toString();
	}

	public int getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(int pageIndex) {
		PageIndex = pageIndex;
	}

	public int getPageSize() {
		return PageSize;
	}

	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}

	public String getParam(String key) {
		return (String) Params.get(key);
	}

	public Mapx getParams() {
		return Params;
	}

	public void setParams(Mapx params) {
		Params = params;
	}

	public boolean isPageFlag() {
		return PageFlag;
	}

	public void setPageFlag(boolean pageFlag) {
		PageFlag = pageFlag;
	}

	public boolean isSortFlag() {
		return SortFlag;
	}

	public void setSortFlag(boolean sortFlag) {
		SortFlag = sortFlag;
		/* ${_ZVING_LICENSE_CODE_} */
	}

	public HtmlTable getTable() {
		return Table;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public boolean isTreeFlag() {
		return TreeFlag;
	}

	protected void setTreeFlag(boolean treeFlag) {
		TreeFlag = treeFlag;
	}

	public String getMethod() {
		return Method;
	}

	public void setMethod(String method) {
		Method = method;
	}

	public int getTotal() {
		return Total;
	}

	public void setTotal(int total) {
		Total = total;
		if (PageIndex > Math.ceil(Total * 1.0 / PageSize)) {
			PageIndex = new Double(Math.floor(Total * 1.0 / PageSize)).intValue();
		}
		TotalFlag = true;
	}
	
	public void setTotal(String JndiName ,QueryBuilder qb) {
		if (this.PageIndex == 0) {
			setTotal(DataAccess.getCount(DBConnPool.getDBConnConfig().DBType, qb ,JndiName));
		}
	}
	
	public void setTotal(QueryBuilder qb) {
		if (this.PageIndex == 0) {
			setTotal(DataAccess.getCount(DBConnPool.getDBConnConfig().DBType, qb));
		}
	}

	public boolean isWebMode() {
		return WebMode;
	}

	public void setWebMode(boolean webMode) {
		WebMode = webMode;
	}

	private static Pattern sortPattern = Pattern.compile("[\\w\\,\\s]*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public String getSortString() {
		if (SortString.length() == 0) {
			return "";
		}
		String str = SortString.toString();
		if (sortPattern.matcher(str).matches()) {
			return " order by " + SortString.toString();
		}
		return "";
	}

	public String getTagBody() {
		return TagBody;
	}

	public void setTagBody(String tagBody) {
		TagBody = tagBody;
	}

	public boolean isMultiSelect() {
		return MultiSelect;
	}

	public void setMultiSelect(boolean multiSelect) {
		MultiSelect = multiSelect;
	}

	public boolean isAutoFill() {
		return AutoFill;
	}

	public void setAutoFill(boolean autoFill) {
		AutoFill = autoFill;
	}

	public boolean isScroll() {
		return Scroll;
	}

	public void setScroll(boolean scroll) {
		Scroll = scroll;
	}

	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
	}

	public boolean isLazy() {
		return Lazy;
	}

	public void setLazy(boolean lazy) {
		Lazy = lazy;
	}

	/**
	 * 将DataTable按树形结构中的上下级关系排序
	 */
	public static DataTable sortTreeDataTable(DataTable dt, String identifierColumnName,
			String parentIdentifierColumnName) {
		if (dt == null || dt.getRowCount() == 0) {
			return dt;
		}
		if (dt.getDataColumn(identifierColumnName) == null) {
			logger.warn("DataGridAction.sortTreeDataTable():数据中不存在列{}", identifierColumnName);
		}
		if (dt.getDataColumn(parentIdentifierColumnName) == null) {
			logger.warn("DataGridAction.sortTreeDataTable():数据中不存在列{}", parentIdentifierColumnName);
		}
		Treex tree = Treex.dataTableToTree(dt, identifierColumnName, parentIdentifierColumnName);
		TreeIterator ti = tree.iterator();
		DataTable dest = new DataTable(dt.getDataColumns(), null);
		while (ti.hasNext()) {
			TreeNode node = (TreeNode) ti.next();
			DataRow dr = (DataRow) node.getData();
			if (dr != null) {
				dest.insertRow(dr);
			}
		}
		return dest;
	}
}
