package com.sinosoft.cms.dataservice;

import java.util.Date;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ShoppingGuideLinkSchema;
public class ShoppingGuide extends Page {

	public static Mapx initDialog(Mapx params) {
		String id = params.getString("Id");
		if (StringUtil.isNotEmpty(id)) {
			ShoppingGuideLinkSchema shoppingGuideLinkSchema = new ShoppingGuideLinkSchema();
			shoppingGuideLinkSchema.setId(id);
			shoppingGuideLinkSchema.fill();
			params = shoppingGuideLinkSchema.toMapx();
			params.put("ParentName",
					new QueryBuilder("select name from ShoppingGuideLink where id =?", shoppingGuideLinkSchema.getParentId())
							.executeString());
			params.put("YesOrNoValue", StringUtil.isEmpty(shoppingGuideLinkSchema.getLink()) ? "N" : "Y");
			params.put("showLink", StringUtil.isEmpty(shoppingGuideLinkSchema.getLink()) ? "none" : "block");
		} else {
			params.put("ParentId",
					HtmlUtil.dataTableToOptions(getBranchTable(), params.getString("ParentId")));
		}
		params.put("YesOrNo", HtmlUtil.codeToOptions("YesOrNo"));
		params.put("UploadFilePath", Config.getValue("StaticResourcePath")+"/");
		return params;
	}

	public static DataTable getBranchTable() {
		DataTable dt = new QueryBuilder("select Name,Id,TreeLevel,ParentId"
				+ " from ShoppingGuideLink order by Id").executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "Id", "ParentId");
		com.sinosoft.cms.pub.PubFun.indentDataTable(dt);
		return dt;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from ShoppingGuideLink");
		DataTable dt = qb.executeDataTable();
		dt = DataGridAction.sortTreeDataTable(dt, "Id", "ParentId");
		dga.bindData(dt);
	}

	public void add() {
		String parentId = $V("ParentId");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(parentId)) {
			parentId = "0";
			ShoppingGuideLinkSchema shoppingGuideLinkSchema = new ShoppingGuideLinkSchema();
			shoppingGuideLinkSchema.setValue(Request);
			shoppingGuideLinkSchema.setId(NoUtil.getMaxNo("BranchInnerCode", 3));
			shoppingGuideLinkSchema.setParentId(parentId);
			shoppingGuideLinkSchema.setTreeLevel(1);

			shoppingGuideLinkSchema.setAddTime(new Date());
			shoppingGuideLinkSchema.setAddUser(User.getUserName());
			trans.add(shoppingGuideLinkSchema, Transaction.INSERT);

			if (trans.commit()) {
				Response.setLogInfo(1, "新建成功");
			} else {
				Response.setLogInfo(0, "新建失败");
			}
		} else {
			ShoppingGuideLinkSchema pShoppingGuideLinkSchema = new ShoppingGuideLinkSchema();
			pShoppingGuideLinkSchema.setId(parentId);
			pShoppingGuideLinkSchema.fill();
			long pTreeLevel = pShoppingGuideLinkSchema.getTreeLevel();

			ShoppingGuideLinkSchema shoppingGuideLinkSchema = new ShoppingGuideLinkSchema();
			shoppingGuideLinkSchema.setValue(Request);
			shoppingGuideLinkSchema.setId(NoUtil.getMaxNo("ShoppingGuideLinkId", pShoppingGuideLinkSchema.getId(), 3));
			shoppingGuideLinkSchema.setParentId(pShoppingGuideLinkSchema.getId());
			shoppingGuideLinkSchema.setTreeLevel(pTreeLevel + 1);
			shoppingGuideLinkSchema.setAddTime(new Date());
			shoppingGuideLinkSchema.setAddUser(User.getUserName());

			trans.add(shoppingGuideLinkSchema, Transaction.INSERT);

			if (trans.commit()) {
				Response.setLogInfo(1, "新建成功");
			} else {
				Response.setLogInfo(0, "新建失败");
			}
		}
	}

	public void save() {
		String id = $V("Id");
		Transaction trans = new Transaction();
		if (StringUtil.isEmpty(id)) {
			Response.setLogInfo(0, "传入数据错误！");
			return;
		}
		ShoppingGuideLinkSchema shoppingGuideLinkSchema = new ShoppingGuideLinkSchema();
		shoppingGuideLinkSchema.setId(id);
		if (!shoppingGuideLinkSchema.fill()) {
			Response.setLogInfo(0, id + "导购频道不存在！");
			return;
		}

		shoppingGuideLinkSchema.setValue(Request);
		shoppingGuideLinkSchema.setModifyUser(User.getUserName());
		shoppingGuideLinkSchema.setModifyTime(new Date());
		trans.add(shoppingGuideLinkSchema, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ShoppingGuideLinkSchema shoppingGuideLinkSchema = new ShoppingGuideLinkSchema();
		for (int i = 0; i < ids.length; i++) {
			shoppingGuideLinkSchema.setId(ids[i]);
			if (shoppingGuideLinkSchema.fill()) {
				if ("0".equals(shoppingGuideLinkSchema.getParentId())) {
					Response.setLogInfo(0, "删除失败：不能删除顶级导购频道");
					UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "删除导购频道:" + shoppingGuideLinkSchema.getName() + "失败",
							Request.getClientIP());
					return;
				}
				QueryBuilder qb = new QueryBuilder("where Id like ?", shoppingGuideLinkSchema.getId() + "%");
				trans.add(shoppingGuideLinkSchema.query(qb), Transaction.DELETE);
			}
		}

		if (trans.commit()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "删除导购频道成功", Request.getClientIP());
			Response.setLogInfo(1, "删除成功");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELBRANCH, "删除导购频道失败", Request.getClientIP());
			Response.setLogInfo(0, "删除失败");
		}
	}
}
