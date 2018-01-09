package com.sinosoft.cms.site;

import java.util.Date;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCKeywordSchema;
import com.sinosoft.schema.ZCKeywordTypeSchema;

/**
 * 热点词管理
 * 
 */
public class KeywordType extends Page {

	public void add() {
		String typeName = $V("TypeName").trim();
		if (new QueryBuilder("select count(*) from ZCKeyWordType where SiteID=? and TypeName=?", Application
				.getCurrentSiteID(), typeName).executeInt() == 0) {
			ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
			keywordType.setID(NoUtil.getMaxID("KeyWordTypeID"));
			keywordType.setTypeName(typeName);
			keywordType.setSiteID(Application.getCurrentSiteID());
			keywordType.setAddTime(new Date());
			keywordType.setAddUser(User.getUserName());
			if (keywordType.insert()) {
				Response.setStatus(1);
				Response.setMessage("新增成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("已经存在的分类!");
		}
	}

	public static DataTable loadType(Mapx params) {
		DataTable dt = new DataTable();
		dt = new QueryBuilder("select ID,TypeName from ZCKeywordType where SiteID = ?", Application.getCurrentSiteID())
				.executeDataTable();

		return dt;
	}

	public static void loadTypeTree(TreeAction ta) {
		String ID = ta.getParam("ID");
		String selectedCID = ta.getParam("selectedCID");
		if (StringUtil.isEmpty(selectedCID)) {
			selectedCID = null;
		}
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		keyword.setID(ID);
		DataTable dt = null;
		QueryBuilder qb = null;
		if (StringUtil.isNotEmpty(ID) && keyword.fill() && !",".equals(keyword.getKeywordType())) {
			qb = new QueryBuilder("select ID,TypeName,(select 'Checked' from ZCKeyword k where k.ID = ? "
					+ "and ZCKeywordType.ID in ("
					+ keyword.getKeywordType().substring(1, keyword.getKeywordType().length() - 1) + ")) as Checked "
					+ "from ZCKeywordType where SiteID = ? ");
			qb.add(Long.parseLong(ID));
			qb.add(Application.getCurrentSiteID());
		} else {
			qb = new QueryBuilder("select ID,TypeName,(select 'Checked' from ZCKeywordType k where "
					+ "k.ID=ZCKeywordType.ID and k.ID = ?) as Checked from ZCKeywordType " + "where SiteID = ?",
					selectedCID != null?Long.parseLong(selectedCID):null, Application.getCurrentSiteID());
		}
		dt = qb.executeDataTable();
		ta.setRootText("请选择类别");
		ta.bindData(dt);
	}

	public void del() {
		Transaction trans = new Transaction();
		String ID = $V("ID");
		ZCKeywordTypeSchema keyWordType = new ZCKeywordTypeSchema();
		keyWordType.setID(ID);
		trans.add(keyWordType, Transaction.DELETE);
		if (Config.isSybase()) {
			trans.add(new QueryBuilder("update ZCKeyword set KeywordType = stuff(KeywordType,charindex(',"
					+ ID + ",',KeywordType),?,',') where KeywordType like ?", ID.length() + 2, "%," + ID + ",%"));
		} else {
			trans.add(new QueryBuilder("update ZCKeyword set KeywordType = replace(KeywordType,'," + ID
					+ ",',',') where KeywordType like ?", "%," + ID + ",%"));
		}
		trans.add(new QueryBuilder("delete from ZCKeyword where KeywordType = ?", ID));

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void edit() {
		String ID = $V("ID");
		ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
		keywordType.setID(ID);
		String typeName = $V("TypeName").trim();
		if (keywordType.fill()
				&& new QueryBuilder("select count(*) from ZCKeyWordType where SiteID=? and TypeName=?", Application
						.getCurrentSiteID(), typeName).executeInt() == 0) {
			keywordType.setTypeName(typeName);
			keywordType.setModifyTime(new Date());
			keywordType.setModifyUser(User.getUserName());
			if (keywordType.update()) {
				Response.setStatus(1);
				Response.setMessage("修改成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("已经存在的分类!");
		}
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("id");
		params.put("ID", ID);
		ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
		if (StringUtil.isNotEmpty(ID)) {
			keywordType.setID(ID);
			keywordType.fill();
			params.put("TypeName", keywordType.getTypeName());
		}

		return params;
	}

	public static DataTable loadKeywordType(Mapx params) {
		DataTable dt = new DataTable();
		dt.insertRow(new String[] { "", "" });
		dt.union(new QueryBuilder("select ID, TypeName from ZCKeywordType where siteID = ?", Application
				.getCurrentSiteID()).executeDataTable());

		return dt;
	}
}
