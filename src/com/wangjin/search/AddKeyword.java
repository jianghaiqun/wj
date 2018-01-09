package com.wangjin.search;


import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddKeyword extends Page {
	public static void main(String[] args) throws Exception {
	}

	/*
	 * 用户在页面每做一次关键词搜索，都会在DB里给该关键词增加搜索次数
	 */
	public static void addkeyword(String word) {
		if (StringUtil.isEmpty(word)) {
			return;
		}
		word = word.trim();
		int max = 0;
		String sql_query = "select count(*) from keyword where KeyWord=?";
		String sql_count = "select max(Id) from keyword";
		String sql_update = "update keyword A set A.Count =A.Count+1 where A.KeyWord = ?";
		try {
			int count = new QueryBuilder(sql_query, word).executeInt();
			if (count > 0) {
				new QueryBuilder(sql_update, word).executeNoQuery();
			} else {
				
				max = new QueryBuilder(sql_count).executeInt();
				String sql_insert = "insert into keyword(Id,KeyWord,Count,Type,createDate) values(?,?,'1','0',now()) ";
				new QueryBuilder(sql_insert, (max + 1), word).executeNoQuery();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	// 2013.01.14 add by 梅俊峰 <<bug0000034-热门搜索，点击更多，页面显示内容存在乱码 >> start
	public static boolean checkword(String word) {
		Pattern pattern = Pattern
				.compile("^[A-Za-z0-9\u4e00-\u9fa5()（）。.:：/]+$");
		Matcher matcher = pattern.matcher(word);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	// 2013.01.14 add by 梅俊峰 <<bug0000034-热门搜索，点击更多，页面显示内容存在乱码 >> end

	/*
	 * 封杀该关键词
	 */
	public static void deletekeyword(String word) {
		word = word.trim();
		int max = 0;
		String sql_query = "select Id from keyword where KeyWord='" + word
				+ "'";
		String sql_update = "update keyword A set A.Type ='0' where A.KeyWord ='"
				+ word + "'";
		String sql_count = "select max(Id) from keyword";

		GetDBdata db = new GetDBdata();
		try {
			String result = db.getOneValue(sql_query);
			if (StringUtils.isNotBlank(result)) {
				db.execUpdateSQL(sql_update, null);
				String sql_delete = "update keyword_index A set A.Ranking ='0' where Id='"
						+ result + "'";
				db.execUpdateSQL(sql_delete, null);
			} else {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String createDate = dateFormat.format(new Date());
				max = Integer.valueOf(db.getOneValue(sql_count));
				String sql_insert = "insert into keyword(Id,KeyWord,Count,Type,createData) values("
						+ (max + 1) + ",'" + word + "','0','0','"+createDate+"') ";
				db.execUpdateSQL(sql_insert, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/*
	 * 置顶/置后该关键词，并设置level
	 */
	public static void stickykeyword(String word, String level) {
		word = StringUtils.deleteWhitespace(word);
		int max = 0;
		String sql_query = "select * from keyword where KeyWord='" + word + "'";
		String sql_count = "select max(Id) from keyword";
		GetDBdata db = new GetDBdata();
		try {
			String result = db.getOneValue(sql_query);
			if (StringUtils.isNotBlank(result)) {
				String sql_update = "update keyword A set A.Type ='" + level
						+ "' where A.KeyWord ='" + word + "'";
				db.execUpdateSQL(sql_update, null);
			} else {
				max = Integer.valueOf(db.getOneValue(sql_count));
				//后台添加关键词
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String createDate = dateFormat.format(new Date());
				String sql_insert = "insert into keyword(Id,KeyWord,Count,Type,createDate) values("
						+ (max + 1) + ",'" + word + "','0','" + level + "','"+createDate+"') ";
				db.execUpdateSQL(sql_insert, null);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public final static Mapx TREELEVEL_MAP = new Mapx();

	static {
		TREELEVEL_MAP.put("0", "Level 0");
		TREELEVEL_MAP.put("1", "Level 1");
		TREELEVEL_MAP.put("2", "Level 2");
		TREELEVEL_MAP.put("3", "Level 3");
		TREELEVEL_MAP.put("4", "Level 4");
		TREELEVEL_MAP.put("5", "Level 5");
		TREELEVEL_MAP.put("6", "Level 6");
		TREELEVEL_MAP.put("7", "Level 7");
		TREELEVEL_MAP.put("8", "Level 8");
		TREELEVEL_MAP.put("9", "Level 9");
	}

	/*
	 * 查询所有未审核关键词
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String word = dga.getParam("Word");
		QueryBuilder qb = new QueryBuilder(
				"select A.Id,A.KeyWord,A.Type,A.Count,A.createDate from keyword A where type = '0'");
		if (StringUtil.isNotEmpty(word)) {
			qb.append(" and A.KeyWord like '%" + word.trim() + "%'");
		}
		qb.append(" order by A.createDate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dt.decodeColumn("TreeLevel", TREELEVEL_MAP);
		dga.bindData(dt);
	}
	
	/*
	 * 查询所有已审核关键词
	 */
	public static void dg2DataBind(DataGridAction dga){
		String word = dga.getParam("Word");
		String level = dga.getParam("Level");
		QueryBuilder qb = new QueryBuilder(
				"select A.Id,A.KeyWord,A.Type,A.Count,A.createDate from keyword A where type <> '0' ");
		if(StringUtil.isNotEmpty(word)){
			qb.append(" and A.KeyWord like '%" + word.trim() + "%'");
			if(StringUtil.isNotEmpty(level)){
				qb.append(" and A.Type = '"+level+"'");
			}
		}else if(StringUtil.isNotEmpty(level)){
			qb.append(" and A.Type = '"+level+"'");
		}
		qb.append("order by A.Count desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dt.decodeColumn("TreeLevel", TREELEVEL_MAP);
		dga.bindData(dt);
		
	}
	
	
	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		String update = "";
		GetDBdata db = new GetDBdata();
		try {
			for (int i = 0; i < dt.getRowCount(); i++) {
				update = "update keyword set Type='" + dt.getString(i, "type")
						+ "' where KeyWord='" + dt.getString(i, "keyword")
						+ "'";
				db.execUpdateSQL(update, null);
			}
			Response.setStatus(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}
	/*
	 * 已审核关键词变成未审核
	 */

	public void del() {
		String[] ids = $V("Id").split(",");
		String sql_update;
		String sql_delete;
		GetDBdata db = new GetDBdata();
		try {
			for (String id : ids) {
				sql_update = "update keyword A set A.Type ='0' where A.Id ='"
						+ id + "'";
				sql_delete = "update keyword_index A set A.Ranking ='-1' where Id='"
						+ id + "'";

				db.execUpdateSQL(sql_update, null);
				db.execUpdateSQL(sql_delete, null);
			}
			Response.setStatus(1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	/*
	 * 未审核关键词审核
	 */
	public void unDel(){
		String[] ids = $V("Id").split(",");
		String sql_update;
		String sql_delete;
		GetDBdata db = new GetDBdata();
		try {
			for(String id : ids){
				sql_update = "update keyword A set A.Type = '5' where A.Id = '"+ id +"'";
				String sql = "select * from keyword_index where Id = '"+ id +"'";
				String result = db.getOneValue(sql);
				if(StringUtil.isNotEmpty(result)){
					sql_delete = "update keyword_index A set A.Ranking ='0' where Id='"
						+ id + "'";
					db.execUpdateSQL(sql_delete, null);
				}
				db.execUpdateSQL(sql_update, null);
				Response.setStatus(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
		
	}
}
