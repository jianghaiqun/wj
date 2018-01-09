package com.wangjin.search;

import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.search.ArticleSearcher;
import com.sinosoft.search.SearchResult;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * 发布静态页面和关键词列表静态页面
 */
public class UpdateStaticPage {
	private static final Logger logger = LoggerFactory.getLogger(UpdateStaticPage.class);

	public static void doUpdate() {
		List<HashMap<String, String>> maplist = setKeyWord();
//		List<HashMap<String, String>> maplist_detail = setKeyWord_detail();
//		ZCSiteSchema site = SiteUtil.getSchema("221");
//		String locahosturl = site.getURL();
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		staticDir = staticDir.replaceAll("/+", "/");
//		CreateHTML chtml = new CreateHTML();
//		String header = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\kxb_header_index_new.shtml"));
//		String footer = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\kxb_footer_new.shtml"));
//		String community = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\community.shtml"));
//		for (HashMap<String, String> map : maplist_detail) {
//			updateKeywordPage(map.get("keyWord"), getPageID(Integer.valueOf(map.get("WordId"))), locahosturl, header, footer, community);
//		}
		updateIndexPage(maplist);
		logger.info("全部关键词静态页面更新完成:.");
	}

	public static void main(String[] args) throws Exception {
		// doUpdate();
		ZCSiteSchema site = SiteUtil.getSchema("221");
		String locahosturl = site.getProp4();
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		staticDir = staticDir.replaceAll("/+", "/");
		CreateHTML chtml = new CreateHTML();
		String header = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\kxb_header_index_new.shtml"));
		String footer = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\kxb_footer_new.shtml"));
		String community = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\community.shtml"));
		updateKeywordPage("保险", getPageID(Integer.valueOf("124")), locahosturl, header, footer, community);
	}

	/*
	 * 只发布关键词列表静态页面
	 */
	public static void doUpdateIndexPage() {
		updateIndexPage(setKeyWord());
		logger.info("关键词列表页面更新完成:.");
	}

	/*
	 * 只发布关键词列表静态页面
	 */
	public static void updateIndexPage(List<HashMap<String, String>> maplist) {
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		String templatefilepath = FilenameUtils.concat(staticDir, "kxb\\template");
		CreateHTML chtml = new CreateHTML();
		Map<String, Object> root = new HashMap<String, Object>();
		String staticpagepath = FilenameUtils.concat(staticDir, "kxb\\keylist");
		String header = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\kxb_header_index_new.shtml"));
		String footer = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\kxb_footer_new.shtml"));
		String community = chtml.readShtml(FilenameUtils.concat(staticDir, "kxb\\block\\community.shtml"));
		ZCSiteSchema site = SiteUtil.getSchema("221");
		String locahosturl = site.getProp4();
		String url  = site.getURL();
		root.clear();
		root.put("StaticResourcePath", site.getStaticResourcePath());
		root.put("header", header);
		root.put("footer", footer);
		root.put("community", community);
		root.put("URL", url);
		root.put("title", "热门搜索 - 开心保官方网站");
		StringBuilder sb = new StringBuilder();
		int line = 0;
		for (HashMap<String, String> map : maplist) {
			if (line == 0) {
				sb.append("<tr align='center' style='width:800px';>");
			} else {
				sb.append("<td></td>");

			}
			sb.append("<th align='left' style='width:160px';>");
			sb.append(" <div style='width:170px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;' title='" + map.get("KeyWord") + "'>");
			sb.append("<a href='");
			sb.append(locahosturl);
			sb.append("/keylist/");
			sb.append(getPageID(Integer.valueOf(map.get("WordId"))));
			sb.append("000001.shtml'>");
			sb.append(map.get("KeyWord"));
			sb.append("</a></div></th>");
			line++;
			if (line == 5) {
				sb.append("</tr>");
				line = 0;
			}

		}
		root.put("wordlist", sb.toString());
		chtml.geneHtmlFile("searchKeyIndex.html", root, staticpagepath, "index.shtml", templatefilepath);
	}

	/*
	 * 获取所有keyword_index中Ranking = 0的关键词
	 */
	public static List<HashMap<String, String>> setKeyWord_detail() {
		List<HashMap<String, String>> wordlist_detail = new ArrayList<HashMap<String, String>>();
		GetDBdata db = new GetDBdata();
		String sql_query = "select A.WordId,A.keyWord from keyword_index A where A.Ranking <> -1";
		try {
			wordlist_detail = db.query(sql_query);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return wordlist_detail;
	}

	/*
	 * 获取搜索排名前一百的关键词list
	 */
	public static List<HashMap<String, String>> setKeyWord() {
		List<HashMap<String, String>> wordlist = new ArrayList<HashMap<String, String>>();
		GetDBdata db = new GetDBdata();
		String sql_update = "update keyword_index A set A.Ranking=0 where A.Ranking <> -1";
		String sql_query = "select A.Id,A.KeyWord from keyword A where A.Type!='0' and A.KeyWord!='' and LENGTH(TRIM(A.KeyWord))>0 order by A.Type,A.Count desc limit 100";
		String sql_query_keyword;
		String sql_update_keyword;
		String sql_insert;
		String sql_count = "select max(WordId) from keyword_index";
		String id;
		int max = 0;
		try {
			List<HashMap<String, String>> list = db.query(sql_query);
			if (list.size() != 0) {
				db.execUpdateSQL(sql_update, null);
				int Ranking = 0;
				for (HashMap<String, String> keyword : list) {
					Ranking++;
					id = keyword.get("Id");
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("KeyWord", keyword.get("KeyWord"));

					sql_query_keyword = "select Id,WordId from keyword_index where Id='" + id + "'";
					List<HashMap<String, String>> result = db.query(sql_query_keyword);
					if (result.size() != 0) {
						map.put("WordId", result.get(0).get("WordId"));
						sql_update_keyword = "update keyword_index A set A.Ranking='" + Ranking + "' where Id='" + id + "'";
						try {
							db.execUpdateSQL(sql_update_keyword, null);
						} catch (MySQLNonTransientConnectionException me) {
						}
					} else {
						max = Integer.valueOf(db.getOneValue(sql_count));
						sql_insert = "insert into keyword_index(Id,keyWord,Ranking,WordId) values(" + id + ",'" + keyword.get("KeyWord") + "','" + Ranking + "','" + (max + 1) + "') ";
						map.put("WordId", String.valueOf(max + 1));
						db.execUpdateSQL(sql_insert, null);
					}
					wordlist.add(map);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return wordlist;
	}

	/*
	 * 发布单个关键词的所有静态页面
	 */
	public static void updateKeywordPage(String query, String wordID, String locahosturl, String header, String footer , String community) {
		String contextRealPath = Config.getContextRealPath();
		String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
		String templatefilepath = FilenameUtils.concat(staticDir, "kxb\\template");
		String staticpagepath = FilenameUtils.concat(staticDir, "kxb\\keylist");
		CreateHTML chtml = new CreateHTML();
		int pagesize = 10;
		Map<String, Object> root = new HashMap<String, Object>();
		DataTable dt = null;
		DataRow dr = null;
		root.clear();
		root.put("query", query);
		root.put("header", header);
		root.put("footer", footer);
		root.put("community", community);
		root.put("URL", locahosturl);
		Mapx<String, Object> map = new Mapx<String, Object>();
		map.put("query", query);
		map.put("site", "221");
		map.put("id", "20");
		// 2013.01.14 add by 梅俊峰 <<bug0000034-热门搜索，点击更多，页面显示内容存在乱码 ,按时间来排序>>
		// start
		map.put("order", "time");
		// 2013.01.14 add by 梅俊峰 <<bug0000034-热门搜索，点击更多，页面显示内容存在乱码，按时间来排序 >> end
		map.put("time", System.currentTimeMillis());
		SearchResult r = ArticleSearcher.search(map);
		if (r == null) {
			root.put("dtflag", "0");
			root.put("dt", "<br><br><br><b><font color='red'>全文检索索引未建立。</font></b><br><br>请在“站点管理”-“站点列表”中选择相应站点，开启“自动建立索引”选项。");
		} else {
			root.put("dtflag", "1");
		}
		dt = r.Data;
		int total = r.Total;
		double usedTime = r.UsedTime;
		int page = 0;
		if (total % pagesize == 0) {
			page = total / pagesize;
		} else {
			page = total / pagesize + 1;
		}

		for (int i = 1; i <= 10; i++) {
			root.put("dtflag" + i, "0");
		}
		if (total == 0) {
			root.put("from", 0);
			root.put("to", 0);
		} else if (total < pagesize) {
			root.put("from", 1);
			root.put("to", total);
		} else {
			root.put("from", 1);
			root.put("to", pagesize);
		}
		root.put("page", 1);
		root.put("total", total);
		root.put("usedTime", usedTime);
		root.put("pageinfo", pageinfo(1, page, wordID, locahosturl));
		for (int i = 0; i < dt.getRowCount(); i++) {
			dr = dt.getDataRow(i);
			root.put("dtflag" + (i + 1), "1");
			root.put("item" + (i + 1), dr);
		}
		chtml.geneHtmlFile("searchResult.html", root, staticpagepath, wordID + getPageID(1) + ".shtml", templatefilepath);

		for (int n = 2; n <= total / pagesize; n++) {
			root.put("from", (n - 1) * pagesize + 1);
			root.put("page", n);
			root.put("to", n * pagesize);
			map.put("time", System.currentTimeMillis());
			map.put("page", n);
			r = ArticleSearcher.search(map);
			dt = r.Data;

			root.put("usedTime", r.UsedTime);
			root.put("pageinfo", pageinfo(n, page, wordID, locahosturl));
			for (int i = 0; i < dt.getRowCount(); i++) {
				dr = dt.getDataRow(i);
				root.put("dtflag" + (i + 1), "1");
				root.put("item" + (i + 1), dr);
			}
			chtml.geneHtmlFile("searchResult.html", root, staticpagepath, wordID + getPageID(n) + ".shtml", templatefilepath);
		}

		int last = total % pagesize;
		if (last != 0) {
			root.clear();
			for (int i = 1; i <= 10; i++) {
				root.put("dtflag" + i, "0");
			}
			root.put("query", query);
			root.put("header", header);
			root.put("footer", footer);
			root.put("community", community);
			root.put("URL", locahosturl);
			root.put("total", total);
			int page_last = total / pagesize;
			root.put("from", (page_last) * pagesize + 1);
			root.put("to", total);
			root.put("page", page_last + 1);
			map.put("time", System.currentTimeMillis());
			map.put("page", page_last + 1);
			r = ArticleSearcher.search(map);
			dt = r.Data;

			root.put("usedTime", r.UsedTime);
			root.put("pageinfo", pageinfo(page_last + 1, page, wordID, locahosturl));
			for (int i = 0; i < dt.getRowCount(); i++) {
				dr = dt.getDataRow(i);
				root.put("dtflag" + (i + 1), "1");
				root.put("item" + (i + 1), dr);
			}
			chtml.geneHtmlFile("searchResult.html", root, staticpagepath, wordID + getPageID(page_last + 1) + ".shtml", templatefilepath);
		}
	}

	/*
	 * 编辑分页信息
	 */
	private static String pageinfo(int index, int page, String wordID, String locahosturl) {
		StringBuilder sb = new StringBuilder();
		if (index != 1) {
			sb.append("<td nowrap><a href='");
			sb.append(locahosturl);
			sb.append("/keylist/");
			sb.append(wordID);
			sb.append(getPageID(index - 1));
			sb.append(".shtml'>上一页</a></td>");
		}
		if (index < 11) {
			for (int i = 1; i < index; i++) {
				sb.append("<td nowrap><a href='");
				sb.append(locahosturl);
				sb.append("/keylist/");
				sb.append(wordID);
				sb.append(getPageID(i));
				sb.append(".shtml'>[");
				sb.append(i);
				sb.append("]</a></td>");
			}

		} else {
			for (int i = index - 10; i < index; i++) {
				sb.append("<td nowrap><a href='");
				sb.append(locahosturl);
				sb.append("/keylist/");
				sb.append(wordID);
				sb.append(getPageID(i));
				sb.append(".shtml'>[");
				sb.append(i);
				sb.append("]</a></td>");
			}
		}
		sb.append("<td nowrap><a href='");
		sb.append(locahosturl);
		sb.append("/keylist/");
		sb.append(wordID);
		sb.append(getPageID(index));
		sb.append(".shtml'>");
		sb.append(index);
		sb.append("</a></td>");

		if (page - index > 8) {
			for (int i = index + 1; i < index + 9; i++) {
				sb.append("<td nowrap><a href='");
				sb.append(locahosturl);
				sb.append("/keylist/");
				sb.append(wordID);
				sb.append(getPageID(i));
				sb.append(".shtml'>[");
				sb.append(i);
				sb.append("]</a></td>");
			}
		} else {
			for (int i = index + 1; i < page + 1; i++) {
				sb.append("<td nowrap><a href='");
				sb.append(locahosturl);
				sb.append("/keylist/");
				sb.append(wordID);
				sb.append(getPageID(i));
				sb.append(".shtml'>[");
				sb.append(i);
				sb.append("]</a></td>");
			}
		}

		if (index != page && 0 != page) {
			sb.append("<td nowrap><a href='");
			sb.append(locahosturl);
			sb.append("/keylist/");
			sb.append(wordID);
			sb.append(getPageID(index + 1));
			sb.append(".shtml'>下一页</a></td>");
		}
		return sb.toString();

	}

	public static String getPageID(int index) {
		return String.format("%06d", index);
	}
}
