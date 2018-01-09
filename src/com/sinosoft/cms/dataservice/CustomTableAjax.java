package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.controls.HtmlTD;
import com.sinosoft.framework.controls.HtmlTR;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCCustomTableColumnSchema;
import com.sinosoft.schema.ZCCustomTableColumnSet;
import com.sinosoft.schema.ZCCustomTableSchema;
import com.sinosoft.schema.ZCCustomTableSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomTableAjax extends Ajax {
	public void dataBindAllColumns(DataListAction dla) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setSiteID($V("SiteID"));
		table.setCode($V("TableCode"));
		ZCCustomTableSet set = table.query();
		if (set == null || set.size() < 1) {
			logger.warn("ID为{}的站点下没有代码为{}的表!",table.getSiteID(), table.getCode());
			return;
		} else {
			table = set.get(0);
			if (!"Y".equals(table.getAllowView())) {
				logger.warn("ID为{}的站点下代码为{}的表不允许前台查看!", table.getSiteID(), table.getCode());
				return;
			}
			DataTable dt = CustomTableUtil.getData(set.get(0), null, dla.getPageSize(), dla.getPageIndex());
			ZCCustomTableColumnSet cset = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?", table.getID()));
			HtmlTR tr = new HtmlTR();
			ArrayList list = new ArrayList();
			for (int i = 0; i < cset.size(); i++) {
				HtmlTD td = new HtmlTD();
				td.setInnerHTML(cset.get(i).getName());
				tr.addTD(td);
			}
			list.add(tr);
			for (int i = 0; i < dt.getRowCount(); i++) {
				tr = new HtmlTR();
				for (int j = 0; j < dt.getColCount(); j++) {
					HtmlTD td = new HtmlTD();
					td.setInnerHTML(dt.getString(i, j));
					tr.addTD(td);
				}
				list.add(tr);
			}
			dt = new DataTable();
			dt.insertColumn("RowHTML");
			for (int i = 0; i < list.size(); i++) {
				tr = (HtmlTR) list.get(i);
				dt.insertRow(new Object[] { tr.getOuterHtml() });
			}
			dla.setTotal(CustomTableUtil.getTotal(table, "where 1=1"));
			dla.bindData(dt);
		}
	}

	public void dataBindSpecifiedColumns(DataListAction dla) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setSiteID($V("SiteID"));
		table.setCode($V("TableCode"));
		ZCCustomTableSet set = table.query();
		if (set == null || set.size() < 1) {
			logger.warn("ID为{}的站点下没有代码为{}的表!", table.getSiteID(), table.getCode());
			return;
		} else {
			table = set.get(0);
			if (!"Y".equals(table.getAllowView())) {
				logger.warn("ID为{}的站点下代码为{}的表不允许前台查看!", table.getSiteID(), table.getCode());
				return;
			}
			DataTable dt = CustomTableUtil.getData(set.get(0), null, dla.getPageSize(), dla.getPageIndex());
			dla.setTotal(CustomTableUtil.getTotal(table, "where 1=1"));
			dla.bindData(dt);
		}
	}

	public void processSubmit() {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID($V("_TableID"));
		if (!table.fill()) {
			logger.warn("没有ID为{}的表!", table.getCode());
			return;
		} else {
			if (!"Y".equals(table.getAllowModify()) && !User.isManager()) {
				logger.warn("ID为{}的表不允许前台修改!", table.getID());
				return;
			}
		}
		DataAccess da = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		try {
			da.setAutoCommit(false);
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?", table.getID()));
			StringBuffer sb = new StringBuffer("insert into " + code + "(");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append(set.get(j).getCode());
			}
			sb.append(") values (");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			QueryBuilder qb = new QueryBuilder(sb.toString());
			StringBuffer messageSB = new StringBuffer();
			for (int j = 0; j < set.size(); j++) {
				ZCCustomTableColumnSchema column = set.get(j);
				String v = $V(Form.FieldPrefix + set.get(j).getCode());
				if (StringUtil.isEmpty(v)) {
					v = null;
					if ("Y".equals(set.get(j).getIsAutoID())) {
						v = String.valueOf(OrderUtil.getDefaultOrder());
					}
				}
				if ("Y".equals(column.getIsMandatory()) || "Y".equals(column.getIsPrimaryKey())) {
					if (StringUtil.isEmpty(v)) {
						messageSB.append(column.getName() + "不能为空!\n");
					}
				}
				int dataType = Integer.parseInt(column.getDataType());
				if (v != null) {
					if (column.getMaxLength() != 0 && v.length() < column.getMaxLength()) {
						messageSB.append(column.getName() + "数据过长，最大允许" + column.getMaxLength() + "个字符!\n");
					}
					try {
						if (dataType == DataColumn.DATETIME) {
							v = DateUtil.toDateTimeString(DateUtil.parseDateTime(v));
							if (v == null) {
								throw new SQLException("日期时间错误");
							}
						}
						if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).intValue());
						}
						if (dataType == DataColumn.LONG) {
							v = String.valueOf(new Double(Double.parseDouble(v)).longValue());
						}
						if (dataType == DataColumn.FLOAT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).floatValue());
						}
						if (dataType == DataColumn.DECIMAL || dataType == DataColumn.DOUBLE || dataType == DataColumn.BIGDECIMAL) {
							v = String.valueOf(Double.parseDouble(v));
						}
					} catch (Exception e) {
						messageSB.append(column.getName() + "数据不正确!\n");
					}
				}
				if (dataType == DataColumn.LONG) {
					qb.add(Integer.parseInt(v));
				} else if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
					qb.add(Integer.parseInt(v));
				} else {
					qb.add(v);
				}
			}
			if (messageSB.length() != 0) {
				Response.setError(messageSB.toString());
			} else {
				da.executeNoQuery(qb);
				da.commit();
				Response.setMessage("提交成功!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				da.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
			Response.setMessage("提交失败:" + e.getMessage());
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public static void processSubmit(HttpServletRequest request, HttpServletResponse response) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(request.getParameter("_TableID"));
		if (!table.fill()) {
			logger.warn("没有ID为{}的表!", table.getCode());
			return;
		} else {
			if (!"Y".equals(table.getAllowModify()) && !User.isManager()) {
				logger.warn("ID为{}的表不允许前台修改!", table.getID());
				return;
			}
		}
		DataAccess da = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		try {
			da.setAutoCommit(false);
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema().query(new QueryBuilder("where TableID=?", table.getID()));
			StringBuffer insertSB = new StringBuffer("insert into " + code + "(");
			QueryBuilder deleteQB = new QueryBuilder("delete from  " + code + " where 1=1 ");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					insertSB.append(",");
				}
				insertSB.append(set.get(j).getCode());
			}
			insertSB.append(") values (");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					insertSB.append(",");
				}
				insertSB.append("?");
				if ("Y".equals(set.get(j).getIsPrimaryKey())) {
					String v = request.getParameter(Form.FieldPrefix + set.get(j).getCode() + "_Old");
					deleteQB.append(" and " + set.get(j).getCode() + "=?", v);
				}
			}
			insertSB.append(")");
			QueryBuilder qb = new QueryBuilder(insertSB.toString());
			StringBuffer messageSB = new StringBuffer();
			for (int j = 0; j < set.size(); j++) {
				ZCCustomTableColumnSchema column = set.get(j);
				String v = request.getParameter(Form.FieldPrefix + set.get(j).getCode());
				if (StringUtil.isEmpty(v)) {
					v = null;
					if ("Y".equals(set.get(j).getIsAutoID())) {
						v = String.valueOf(OrderUtil.getDefaultOrder());
					}
				}
				if ("Y".equals(column.getIsMandatory()) || "Y".equals(column.getIsPrimaryKey())) {
					if (StringUtil.isEmpty(v)) {
						messageSB.append(column.getName() + "不能为空!\\n");
					}
				}
				int dataType = Integer.parseInt(column.getDataType());
				if (v != null) {
					if (column.getMaxLength() != 0 && v.length() < column.getMaxLength()) {
						messageSB.append(column.getName() + "数据过长，最大允许" + column.getMaxLength() + "个字符!\\n");
					}
					try {
						if (dataType == DataColumn.DATETIME) {
							v = DateUtil.toDateTimeString(DateUtil.parseDateTime(v));
							if (v == null) {
								throw new SQLException("日期时间错误");
							}
						}
						if (dataType == DataColumn.INTEGER || dataType == DataColumn.SMALLINT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).intValue());
						}
						if (dataType == DataColumn.LONG) {
							v = String.valueOf(new Double(Double.parseDouble(v)).longValue());
						}
						if (dataType == DataColumn.FLOAT) {
							v = String.valueOf(new Double(Double.parseDouble(v)).floatValue());
						}
						if (dataType == DataColumn.DECIMAL || dataType == DataColumn.DOUBLE || dataType == DataColumn.BIGDECIMAL) {
							v = String.valueOf(Double.parseDouble(v));
						}
					} catch (Exception e) {
						messageSB.append(column.getName() + "数据不正确!\\n");
					}
				}
				qb.add(v);
			}
			if (messageSB.length() != 0) {
				insertSB = new StringBuffer();
				insertSB.append("<script>");
				insertSB.append("alert(\"" + messageSB + "\");");
				insertSB.append("history.go(-1);");
				insertSB.append("</script>");
				response.getWriter().print(insertSB);
			} else {
				da.executeNoQuery(deleteQB);
				da.executeNoQuery(qb);
				da.commit();
				insertSB = new StringBuffer();
				insertSB.append("<script>");
				insertSB.append("alert(\"提交成功!\");");
				insertSB.append("window.location=\"" + request.getHeader("referer") + "\";");
				insertSB.append("</script>");
				response.getWriter().print(insertSB);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				da.rollback();
			} catch (SQLException e1) {
				logger.error(e1.getMessage(), e1);
			}
			StringBuffer sb = new StringBuffer();
			sb.append("<script>");
			sb.append("alert(\"提交失败!\");");
			sb.append("history.go(-1);");
			sb.append("</script>");
			try {
				response.getWriter().print(sb);
			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
			}
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
