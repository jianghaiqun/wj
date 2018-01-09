package com.sinosoft.shop;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.template.HtmlNameParser;
import com.sinosoft.cms.template.HtmlNameRule;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCCatalogSchema;

/**
 * 显示最热/最新/本类最热/最近浏览
 * 
 */
public class HotList {
	public static void deal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("Type");
		if (StringUtil.isEmpty(type)) {
			return;
		}
		if (type.equals("Catalog")) {
			getCatalogHotList(request, response);
		}
		if (type.equals("Site")) {
			getSiteHotList(request, response);
		}
		if (type.equals("New")) {

		}
		if (type.equals("History")) {
			getHistoryHotList(request, response);
		}
	}

	public static void getCatalogHotList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String catalogID = request.getParameter("CatalogID");
		String count = request.getParameter("Count");
		String strWidth = request.getParameter("CharWidth");
		PrintWriter out = response.getWriter();
		out.println("document.write('<ul>');");
		if (StringUtil.isEmpty(count)) {
			count = "10";
		}
		if (StringUtil.isEmpty(count)) {
			strWidth = "100";
		}
		if (StringUtil.isEmpty(catalogID)) {
			out.println("document.write('CatalogID不能为空')");
		}
		String innerCode = CatalogUtil.getSchema(catalogID).getInnerCode();
		DataTable dt = new QueryBuilder("select * from zsgoods where CatalogInnerCode like ? order by SaleCount desc",
				innerCode + "%").executePagedDataTable(Integer.parseInt(count), 0);
		int width = Integer.parseInt(strWidth);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String name = dt.getString(i, "Name");
			int len = StringUtil.lengthEx(name) / 2;
			name = StringUtil.subStringEx(name, width);
			if (len > width + 1) {
				name += "...";
			}
			String link = getLink(dt.getDataRow(i));
			out.println("document.write('<li><a href=\"" + link + "\">" + name + "</a></li>');");
		}
		out.println("document.write('</ul>');");
	}

	public static void getSiteHotList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String siteID = request.getParameter("SiteID");
		String count = request.getParameter("Count");
		String strWidth = request.getParameter("CharWidth");
		PrintWriter out = response.getWriter();
		out.println("document.write('<ul>');");
		if (StringUtil.isEmpty(count)) {
			count = "10";
		}
		if (StringUtil.isEmpty(count)) {
			strWidth = "100";
		}
		if (StringUtil.isEmpty(siteID)) {
			out.println("document.write('SiteID不能为空')");
		}
		DataTable dt = new QueryBuilder("select * from zsgoods where SiteID=? order by SaleCount desc", siteID)
				.executePagedDataTable(Integer.parseInt(count), 0);
		int width = Integer.parseInt(strWidth);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String name = dt.getString(i, "Name");
			int len = StringUtil.lengthEx(name) / 2;
			name = StringUtil.subStringEx(name, width);
			if (len > width + 1) {
				name += "...";
			}
			String link = getLink(dt.getDataRow(i));
			out.println("document.write('<li><a href=\"" + link + "\">" + name + "</a></li>');");
		}
		out.println("document.write('</ul>');");
	}

	public static void getHistoryHotList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String siteID = request.getParameter("SiteID");
		String count = request.getParameter("Count");
		String strWidth = request.getParameter("CharWidth");
		PrintWriter out = response.getWriter();
		out.println("document.write('<ul>');");
		if (StringUtil.isEmpty(count)) {
			count = "10";
		}
		if (StringUtil.isEmpty(count)) {
			strWidth = "100";
		}
		if (StringUtil.isEmpty(siteID)) {
			out.println("document.write('SiteID不能为空')");
		}
		DataTable dt = new QueryBuilder("select * from zsgoods where SiteID=? order by SaleCount desc", siteID)
				.executePagedDataTable(Integer.parseInt(count), 0);
		int width = Integer.parseInt(strWidth);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String name = dt.getString(i, "Name");
			int len = StringUtil.lengthEx(name) / 2;
			name = StringUtil.subStringEx(name, width);
			if (len > width + 1) {
				name += "...";
			}
			String link = getLink(dt.getDataRow(i));
			out.println("document.write('<li><a href=\"" + link + "\">" + name + "</a></li>');");
		}
		out.println("document.write('</ul>');");
	}

	public static String getLink(DataRow goodsDr) {
		String nameRule = CatalogUtil.getSchema(goodsDr.getString("CatalogID")).getDetailNameRule();
		ZCCatalogSchema catalog = CatalogUtil.getSchema(goodsDr.getString("CatalogID"));
		if (StringUtil.isNotEmpty(nameRule)) {
			HtmlNameParser nameParser = new HtmlNameParser(SiteUtil.getSchema(goodsDr.getString("SiteID")).toDataRow(),
					null, goodsDr, nameRule);
			HtmlNameRule h = nameParser.getNameRule();
			return PubFun.getLevelStr(catalog.getTreeLevel()) + h.getFullPath();
		} else {
			return PubFun.getLevelStr(catalog.getTreeLevel()) + CatalogUtil.getPath(catalog.getID())
					+ goodsDr.get("ID") + ".shtml";
		}

	}
}
