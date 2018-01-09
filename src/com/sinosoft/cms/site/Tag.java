package com.sinosoft.cms.site;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标签管理
 * 
 */
public class Tag extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String TagWord = dga.getParam("TagWord");
		QueryBuilder qb = new QueryBuilder("select * from ZCTag where siteID=?");
		qb.add(Application.getCurrentSiteID());
		if (StringUtil.isNotEmpty(TagWord)) {
			qb.append(" and Tag like ?", "%" + TagWord.trim() + "%");
		}
		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(dga.getSortString());
		} else {
			qb.append(" order by ID desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZCTagSet set = new ZCTagSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCTagSchema tag = new ZCTagSchema();
			tag.setID(Integer.parseInt(dt.getString(i, "ID")));
			tag.fill();
			tag.setValue(dt.getDataRow(i));
			tag.setModifyTime(new Date());
			tag.setModifyUser(User.getUserName());
			if (checkTagWord(tag.getID(), tag.getTag()).size() != 0) {
				Response.setStatus(0);
				Response.setMessage("更改Tag内容不允许和其他数据的Tag内容重复!");
				return;
			}
			set.add(tag);
		}
		if (set.update()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		return null;
	}

	public static ZCTagSet checkTagWord(long SiteID, String TagWord) {
		ZCTagSchema tZCTagSchema = new ZCTagSchema();
		ZCTagSet tZCTagSet = tZCTagSchema.query(new QueryBuilder("where Tag=? and SiteID=?", TagWord, SiteID));
		return tZCTagSet;
	}

	public void add() {
		QueryBuilder qb = new QueryBuilder("select value from zdconfig where  type = 'defaultmemo' ");
		Object defaultmemo = qb.executeOneValue();
		ZCTagSchema tag = new ZCTagSchema();
		String TagWord = $V("Tag").trim();
		String f_str=" |，|；|;|,"; 
		Pattern p=Pattern.compile(f_str);
		Matcher m=p.matcher(TagWord);
		boolean rs=m.find(); 
		if(rs){
			Response.setStatus(0);
			Response.setMessage("内容中包含不合法字符:空格、分号、逗号!");
			return;
		}
		tag.setID(NoUtil.getMaxNo("TagID", 6));
		tag.setValue(Request);
		tag.setSiteID(Application.getCurrentSiteID());
		tag.setAddTime(new Date());
		tag.setAddUser(User.getUserName());
		tag.setUsedCount(0);
		String tagId = tag.getID() + "";
		if (tagId.length() < 6) {
			tagId = StringUtil.leftPad(tagId, '0', 6);
		}
		String tagHtml = "/tag/" + tagId + "000001-" + defaultmemo + ".html";
		tag.setLinkURL(tagHtml);

		if (checkTagWord(Application.getCurrentSiteID(), TagWord).size() == 0) {
			if (tag.insert()) {
				Response.setLogInfo(1, "新增成功");
			} else {
				Response.setLogInfo(0, "发生错误！");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("已经存在的Tag内容!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCTagSchema Tag = new ZCTagSchema();
		QueryBuilder qb = new QueryBuilder("where id in (" + ids + ")");
		ZCTagSet set = Tag.query(qb);
		String tagA=set.get(0).getTag();
		QueryBuilder qbA = new QueryBuilder("select tag from zcArticle where  tag like "+"'%"+tagA+"%'");
		String qbTest = qbA.executeOneValue()+"";
		if(!StringUtil.isEmpty(qbTest)){
			String[] tagwords = qbTest.split(" ");
			for (int i = 0; i < tagwords.length; i++) {
				if(tagwords[i].equals(tagA)){
				Response.setStatus(0);
				Response.setMessage("此数据存在于zcArticle表中：数据不能删除");
				return;
				}
			}
		}
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void tagToHtml() {
		try {
			QueryBuilder qb = new QueryBuilder("select value from zdconfig where  type = 'defaultmemo' ");
			Object defaultmemo = qb.executeOneValue();
			if (StringUtil.isEmpty(defaultmemo + "")) {
				Response.setStatus(0);
				Response.setMessage("基础数据错配置误!");
			}
			ZCTagSchema tZCTagSchema = new ZCTagSchema();
			ZCTagSet tZCTagSet = tZCTagSchema.query();
			String tagHtml = "";
			for (int i = 0; tZCTagSet != null && i < tZCTagSet.size(); i++) {
				ZCTagSchema tag = tZCTagSet.get(i);
				ZCTagSchema tZCTag = new ZCTagSchema();
				tZCTag.setID(tag.getID());
				String tagId = tag.getID() + "";
				if (tagId.length() < 6) {
					tagId = StringUtil.leftPad(tagId, '0', 6);
				}
				tagHtml = "/tag/" + tagId + "000001-" + defaultmemo + ".html";
				if (tZCTag.fill()) {
					tZCTag.setLinkURL(tagHtml);
					tZCTag.update();
				}
			}
			Response.setLogInfo(1, "生成成功");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!" + e.getMessage());
		}
	}
}
