package com.sinosoft.framework.controls;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Current;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

public class DataGridPage extends Ajax {
	public void doWork() {
		try {
			DataGridAction dga = new DataGridAction();

			dga.setTagBody(StringUtil.htmlDecode($V(Constant.TagBody)));
			String method = $V(Constant.Method);
			dga.setMethod(method);

			dga.setID($V(Constant.ID));
			dga.setPageFlag("true".equalsIgnoreCase($V(Constant.Page)));
			dga.setMultiSelect(!"false".equalsIgnoreCase($V(Constant.DataGridMultiSelect)));
			dga.setAutoFill(!"false".equalsIgnoreCase($V(Constant.DataGridAutoFill)));
			dga.setScroll("true".equalsIgnoreCase($V(Constant.DataGridScroll)));
			dga.setLazy("true".equalsIgnoreCase($V("_ZVING_LAZY")));
			if (StringUtil.isNotEmpty($V("_ZVING_CACHESIZE"))) {
				dga.setCacheSize(Integer.parseInt($V("_ZVING_CACHESIZE")));
			}
			dga.setParams(Current.getRequest());
			dga.Response = Current.getResponse();

			if (dga.isPageFlag()) {
				dga.setPageIndex(0);
				if (Request.get(Constant.DataGridPageIndex) != null
						&& !Request.get(Constant.DataGridPageIndex).equals("")) {
					dga.setPageIndex(Integer.parseInt(Request.getString(Constant.DataGridPageIndex)));
				}
				if (dga.getPageIndex() < 0) {
					dga.setPageIndex(0);
				}
				if (dga.getPageIndex() != 0) {
					dga.setTotal(Integer.parseInt(Request.getString(Constant.DataGridPageTotal)));
				}
				dga.setPageSize(Integer.parseInt($V(Constant.Size)));
			}

			HtmlTable table = new HtmlTable();
			table.parseHtml(dga.getTagBody());
			dga.setTemplate(table);
			dga.parse();

			// 响应DataGrid.insertRow
			String strInsertRowIndex = Request.getString(Constant.DataGridInsertRow);
			if (StringUtil.isNotEmpty(strInsertRowIndex)) {
				DataTable dt = (DataTable) Request.get(Constant.DataTable);
				Request.remove(Constant.DataTable);
				Request.remove(Constant.DataGridInsertRow);
				dga.bindData(dt);

				HtmlTR tr = dga.getTable().getTR(1);
				$S("TRAttr", tr.getAttributes());
				for (int i = 0; i < tr.Children.size(); i++) {
					$S("TDAttr" + i, tr.getTD(i).getAttributes());
					$S("TDHtml" + i, tr.getTD(i).getInnerHTML());
				}
			} else {
				Current.invokeMethod(method, new Object[] { dga });
				$S("HTML", dga.getHtml());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static void toExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constant.GlobalCharset);
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=Excel_"
				+ DateUtil.getCurrentDateTime("yyyyMMddhhmmss") + ".xls");

		try {
			String xls = "_Excel_";
			Mapx params = ServletUtil.getParameterMap(request);
			String ID = params.getString(xls + Constant.ID);
			String tagBody = params.getString(xls + Constant.TagBody);
			String pageIndex = params.getString(xls + Constant.DataGridPageIndex);
			String pageSize = params.getString(xls + Constant.Size);
			String pageTotal = params.getString(xls + Constant.DataGridPageTotal);
			String method = params.getString(xls + Constant.Method);
			String pageFlag = params.getString(xls + Constant.Page);
			String excelPageFlag = params.getString(xls + "_ZVING_ToExcelPageFlag");
			String strWidths = params.getString(xls + "_ZVING_Widths");
			String strIndexes = params.getString(xls + "_ZVING_Indexes");
			String strRows = params.getString(xls + "_ZVING_Rows");

			if (tagBody != null && !tagBody.equals("")) {
				tagBody = StringUtil.htmlDecode(tagBody);
			}
			DataGridAction dga = new DataGridAction();
			HtmlTable table = new HtmlTable();
			dga.setMethod(method);
			dga.setID(ID);
			dga.setTagBody(tagBody);
			if ("1".equals(excelPageFlag)) {
				// 导出全部
				if ("true".equals(pageFlag)) {
					dga.setPageFlag(true);
					dga.setPageIndex(0);
					dga.setPageSize(Integer.parseInt(pageTotal));
				}
			} else {
				// 按分页导出
				if ("true".equals(pageFlag)) {
					dga.setPageFlag(true);
					dga.setPageIndex(StringUtil.isEmpty(pageIndex) ? 0 : Integer.parseInt(pageIndex));
					dga.setPageSize(StringUtil.isEmpty(pageSize) ? 0 : Integer.parseInt(pageSize));
				}
			}
			table.parseHtml(dga.getTagBody());
			dga.setTemplate(table);
			dga.parse();

			OutputStream os = response.getOutputStream();

			Current.init(request, response, method);
			Mapx map = Current.getRequest();
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				String k = ks[i].toString();
				if (k.startsWith(xls)) {
					Object v = map.get(k);
					map.remove(k);
					map.put(k.substring(xls.length()), v);
				}
			}
			dga.setParams(map);
			dga.Response = Current.getResponse();
			Current.invokeMethod(method, new Object[] { dga });

			String[] rows = null;
			if (StringUtil.isNotEmpty(strRows)) {
				rows = strRows.split(",");
			}

			// 有可能加入了一个空白行，需要去掉
			HtmlTable ht = dga.getTable();
			if (ht.getChildren().size() > 0
					&& "blank".equalsIgnoreCase(ht.getTR(ht.getChildren().size() - 1).getAttribute("ztype"))) {
				ht.removeTR(ht.getChildren().size() - 1);
			}
			HtmlUtil.htmlTableToExcel(os, ht, strWidths.split(","), strIndexes.split(","), rows);
			os.flush();
			os.close();

			os = null;
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	public static void GlobaltoExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding(Constant.GlobalCharset);
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=Excel_"
				+ DateUtil.getCurrentDateTime("yyyyMMddhhmmss") + ".xls");
		try {
			String xls = "_Excel_";
			Mapx params = ServletUtil.getParameterMap(request);
			String ID = params.getString(xls + Constant.ID);
			String tagBody = params.getString(xls + Constant.TagBody);
			String pageIndex = params.getString(xls + Constant.DataGridPageIndex);
			String pageSize = params.getString(xls + Constant.Size);
			String pageTotal = params.getString(xls + Constant.DataGridPageTotal);
			String method = params.getString(xls + Constant.Method);
			String pageFlag = params.getString(xls + Constant.Page);
			String excelPageFlag = params.getString(xls + "_ZVING_ToExcelPageFlag");
			String strWidths = params.getString(xls + "_ZVING_Widths");
			String strIndexes = params.getString(xls + "_ZVING_Indexes");
			String strTitles = params.getString(xls + "_ZVING_Titles");
			String strRows = params.getString(xls + "_ZVING_Rows");
			if (tagBody != null && !tagBody.equals("")) {
				tagBody = StringUtil.htmlDecode(tagBody);
			}
			DataGridAction dga = new DataGridAction();
			HtmlTable table = new HtmlTable();
			dga.setMethod(method);
			dga.setID(ID);
			dga.setTagBody(tagBody);
			if ("1".equals(excelPageFlag)) {
				// 导出全部
				if ("true".equals(pageFlag)) {
					dga.setPageFlag(true);
					dga.setPageIndex(0);
					dga.setPageSize(Integer.parseInt(pageTotal));
				}
			} else {
				// 按分页导出
				if ("true".equals(pageFlag)) {
					dga.setPageFlag(true);
					dga.setPageIndex(StringUtil.isEmpty(pageIndex) ? 0 : Integer.parseInt(pageIndex));
					dga.setPageSize(StringUtil.isEmpty(pageSize) ? 0 : Integer.parseInt(pageSize));
				}
			}
			table.parseHtml(dga.getTagBody());
			dga.setTemplate(table);
			dga.parse();
			OutputStream os = response.getOutputStream();
			Current.init(request, response, method);
			Mapx map = Current.getRequest();
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++) {
				String k = ks[i].toString();
				if (k.startsWith(xls)) {
					Object v = map.get(k);
					map.remove(k);
					map.put(k.substring(xls.length()), v);
				}
			}
			dga.setParams(map); 
			dga.Response = Current.getResponse();
			Current.invokeMethod(method, new Object[] { dga });
			String[] rows = null;
			if (StringUtil.isNotEmpty(strRows)) {
				rows = strRows.split(",");
			}
			// 有可能加入了一个空白行，需要去掉
			HtmlTable ht = dga.getTable();
			if (ht.getChildren().size() > 0
					&& "blank".equalsIgnoreCase(ht.getTR(ht.getChildren().size() - 1).getAttribute("ztype"))) {
				ht.removeTR(ht.getChildren().size() - 1);
			}
			HtmlUtil.GlobalHtmlTableToExcel(os, ht, strWidths.split(","), strIndexes.split(","), rows, strTitles.split(","));
			os.flush();
			os.close();
			os = null;
			response.flushBuffer();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	public static void sqlBind(DataGridAction dgp) {
		dgp.bindData(new QueryBuilder((String) dgp.getParams().get(Constant.DataGridSQL)));
	}
}
