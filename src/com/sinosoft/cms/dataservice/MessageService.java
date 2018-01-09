package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;


public class MessageService extends Ajax {

	public static Mapx init(Mapx params) {
		String BoardID = params.getString("BoardID");
		if (StringUtil.isNotEmpty(BoardID)) {
			params.put("BoardName", new QueryBuilder("select Name from ZCMessageBoard where ID = ?", Long.parseLong(BoardID)).executeString());
		}
		params.put("ServicesContext", Config.getValue("ServicesContext"));
		params.put("MessageActionURL", Config.getValue("MessageActionURL"));
		return params;
	}

	public static void dg1DataBind(DataListAction dla) {
		String BoardID = dla.getParam("BoardID");
		QueryBuilder qb = new QueryBuilder("select * from ZCBoardMessage where BoardID = ? and PublishFlag = 'Y' order by ID desc", Long.parseLong(BoardID));
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("Reply");
		dt.insertColumn("Prefix");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "ReplyFlag").equals("Y")) {
				dt.set(i,"Prefix","<font color='#9B0D17'>[回复]：</font>");
				dt.set(i, "Reply", "<font color='#9B0D17'>"+ dt.getString(i, "ReplyContent") + "</font>");
			}
		}
		dla.bindData(dt);
	}
}