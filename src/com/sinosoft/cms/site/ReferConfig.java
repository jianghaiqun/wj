package com.sinosoft.cms.site;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * 关键字管理
 * 
 */
public class ReferConfig extends Page {

	public static Mapx init(Mapx params) {
		String CodeName = params.getString("CodeName");
		String CodeType = "ArticleRefer";
		String ParentCode = "ArticleRefer";
		if (StringUtil.isNotEmpty(CodeName)) {
			try {
				CodeName = URLDecoder.decode(params.getString("CodeName"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
			ZDCodeSchema code = new ZDCodeSchema();
			code.setCodeType(CodeType);
			code.setParentCode(ParentCode);
			code.setCodeValue(CodeName);
			code.setCodeName(CodeName);
			code.fill();
			params.putAll(code.toMapx());
			params.put("New", "");
		} else {
			params.put("New", "New");
		}
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String SearchContent = dga.getParam("SearchContent");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZDCode where CodeType = 'ArticleRefer' and ParentCode ='ArticleRefer' ");
		if (StringUtil.isNotEmpty(SearchContent)) {
			qb.append(" and CodeName like ? ", "%" + SearchContent.trim() + "%");
		}
		qb.append(" order by AddTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public void add() {
		String CodeName = $V("CodeName").trim();
		String New = $V("New");
		String hCodeName = $V("hCodeName");
		if (StringUtil.isNotEmpty(New)) {
			if (new QueryBuilder("select count(*) from ZDCode where CodeName = ? and ParentCode ='ArticleRefer'",
					CodeName).executeInt() == 0) {
				ZDCodeSchema code = new ZDCodeSchema();
				code.setCodeType("ArticleRefer");
				code.setParentCode("ArticleRefer");
				code.setCodeValue(CodeName);
				code.setCodeName(CodeName);
				code.setCodeOrder(System.currentTimeMillis());
				code.setProp4(StringUtil.getChineseFirstAlpha(CodeName));
				code.setAddTime(new Date());
				code.setAddUser(User.getUserName());
				if (code.insert()) {
					Response.setStatus(1);
					Response.setMessage("新增成功!");
				} else {
					Response.setStatus(0);
					Response.setMessage("发生错误!");
				}
			} else {
				Response.setStatus(0);
				Response.setMessage("已经存在的文章来源!");
			}
		} else {
			ZDCodeSchema code = new ZDCodeSchema();
			code.setCodeType("ArticleRefer");
			code.setParentCode("ArticleRefer");
			code.setCodeValue(hCodeName);
			code.setCodeName(hCodeName);
			code.fill();
			code.setCodeValue(CodeName);
			code.setCodeName(CodeName);				
			code.setProp4(StringUtil.getChineseFirstAlpha(CodeName));
			if (code.update()) {
				Response.setStatus(1);
				Response.setMessage("修改成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		}
	}

	public void del() {
		String CodeNames = $V("CodeNames");
		CodeNames = StringUtil.replaceEx(CodeNames, ",", "','");
		Transaction trans = new Transaction();
		ZDCodeSchema code = new ZDCodeSchema();
		ZDCodeSet set = code.query(new QueryBuilder("where CodeName in ('" + CodeNames + "')"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public static String getReferList() {
		StringBuffer sb = new StringBuffer();
		QueryBuilder qb = new QueryBuilder(
				"select CodeName,Prop4 from ZDCode where CodeType='ArticleRefer' and ParentCode<>'System' order by Prop4");
		DataTable dt = qb.executeDataTable();
		sb.append("var AllNames = [");
		char last = ' ';
		for (int i = 0; i < dt.getRowCount(); i++) {
			String abbr = dt.getString(i, "Prop4");
			if (StringUtil.isEmpty(abbr)) {
				continue;
			}
			char c = abbr.charAt(0);
			if (!Character.isLetter(c)) {
				c = '*';
			}
			if (c != last) {
				if (i != 0) {
					sb.append("],");
				}
				sb.append("[");
				sb.append("\"" + StringUtil.javaEncode(dt.getString(i, "CodeName")) + "\",\"" + abbr + "\"");
				last = c;
			} else {
				sb.append(",\"" + StringUtil.javaEncode(dt.getString(i, "CodeName")) + "\",\""
						+ dt.getString(i, "Prop4") + "\"");
			}
		}
		sb.append("]];");
		return sb.toString();
	}
}
