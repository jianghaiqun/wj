package com.sinosoft.cms.site;

import java.util.Date;

import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCPageBlockItemSchema;
import com.sinosoft.schema.ZCPageBlockItemSet;
import com.sinosoft.schema.ZCPageBlockSchema;
import com.sinosoft.schema.ZCPageBlockSet;

/**
 * 页面区块管理
 * 
 */
public class PageBlock extends Page {

	/**
	 * 区块列表
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {// type=4的页面片段为系统自动生成
		QueryBuilder qb = new QueryBuilder("select a.* from ZCPageBlock a");
		String catalogID = dga.getParam("CatalogID");
		String template = dga.getParam("Template");//模板文件
		if (StringUtil.isNotEmpty(catalogID)) {
			qb.append(",zccatalog b where a.catalogid=b.id and b.innercode like ?", CatalogUtil.getInnerCode(catalogID)
					+ "%");
		} else {
			qb.append(" where a.siteid=?", Application.getCurrentSiteID());
		}
		if(StringUtil.isNotEmpty(template)){
			qb.append(" and a.template like CONCAT('%',?,'%')",template.trim());
		}
		if (!Config.isDebugMode()) {
			qb.append(" and a.type<>4");
		}
		qb.append(" order by a.type asc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("CatalogIDName");
		dt.insertColumn("CatalogPath");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (StringUtil.isNotEmpty(dt.getString(i, "CatalogID"))&&!"0".equals(dt.getString(i, "CatalogID"))) {
				dt.set(i, "CatalogIDName", CatalogUtil.getName(dt.getString(i, "CatalogID")));
				String str = "/";
				String id = dt.getString(i, "CatalogID");
				String parentid = "";
				do{
					if(StringUtil.isNotEmpty(parentid)){
						id = parentid;
					}
					parentid = CatalogUtil.getParentID(id);
					str = "/"+CatalogUtil.getName(id)+str;
				}while(!parentid.equals("0"));
				dt.set(i, "CatalogPath", str);
			} else {
				dt.set(i, "CatalogIDName", "全站");
				dt.set(i, "CatalogPath", "");
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);

	}

	/**
	 * 显示区块项列表的信息
	 * 
	 * @param dga
	 */
	public static void dialogDataBind(DataGridAction dga) {
		String id = (String) dga.getParams().get("ID");
		// 修改
		DataTable dt = null;
		if (StringUtil.isNotEmpty(id)) {
			dt = new QueryBuilder("select title,URL from zcpageblockItem where blockid=?", id).executeDataTable();
		} else {
			dt = new QueryBuilder("select '' as title,'' as URL from zcpageblockItem").executePagedDataTable(1, 0);
		}
		dga.bindData(dt);
	}

	public static void blockItemDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		DataTable dt = null;
		if (StringUtil.isNotEmpty(id)) {
			dt = new QueryBuilder("select id,title,URL from zcpageblockItem where blockid=?", id).executeDataTable();
		}
		if (dt == null || dt.getRowCount() == 0) {
			String sql = "select 0 as id,'' as title,'' as URL from zcpageblockItem";
			dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
		}
		dga.setTotal(dt.getRowCount());
		dga.bindData(dt);
	}

	public void edit() {
		Transaction trans = new Transaction();

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = Long.parseLong($V("ID"));
		block.setID(blockID);
		if (!block.fill()) {
			Response.setStatus(0);
			Response.setMessage("没有找到对应的区块!");
		}
		block.setName($V("Name"));
		block.setCode($V("Code"));
		block.setFileName($V("FileName"));
		;
		block.setTemplate($V("Template"));
		block.setSortField($V("SortField"));
		;

		block.setType(Integer.parseInt($V("Type")));
		block.setContent($V("Content"));
		block.setModifyTime(new Date());
		block.setModifyUser(User.getUserName());

		trans.add(block, Transaction.UPDATE);

		if (Integer.parseInt($V("Type")) == 2) {
			trans.add(new QueryBuilder("delete from ZCPageBlockItem where blockid=?", blockID));
			String[] title = $V("ItemTitle").split("\\^");
			String[] url = $V("ItemURL").split("\\^");
			for (int i = 0; i < title.length; i++) {
				ZCPageBlockItemSchema item = new ZCPageBlockItemSchema();
				item.setID(NoUtil.getMaxID("PageBlockID"));
				item.setBlockID(blockID);
				item.setTitle(title[i]);
				item.setURL(url[i]);
				item.setImage($V("Image"));
				item.setAddTime(new Date());
				item.setAddUser(User.getUserName());
				trans.add(item, Transaction.INSERT);
			}
		}

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		if (params.get("ID") != null && !"".equals(params.get("ID"))) {
			long ID = Long.parseLong(params.get("ID").toString());
			ZCPageBlockSchema block = new ZCPageBlockSchema();
			block.setID(ID);
			block.fill();
			Mapx mapx = block.toMapx();
			mapx.put("BlockType", block.getType() + "");
			return mapx;
		} else {
			return params;
		}
	}

	public static Mapx initParam(Mapx params) {
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = NoUtil.getMaxID("PageBlockID");
		block.setID(blockID);
		block.setSiteID(Application.getCurrentSiteID());
		String obj = $V("CatalogID");
		if (StringUtil.isNotEmpty(obj)) {
			block.setCatalogID(Long.parseLong(obj.toString()));
		}

		block.setName($V("Name"));
		block.setCode($V("Code"));
		block.setFileName($V("FileName"));
		block.setTemplate($V("Template"));
		block.setSortField($V("SortField"));

		block.setType(Integer.parseInt($V("Type")));
		block.setContent($V("Content"));
		block.setAddTime(new Date());
		block.setAddUser(User.getUserName());

		trans.add(block, Transaction.INSERT);

		if (Integer.parseInt($V("Type")) == 2) {
			String[] title = $V("ItemTitle").split("\\^");
			String[] url = $V("ItemURL").split("\\^");
			for (int i = 0; i < title.length; i++) {
				ZCPageBlockItemSchema item = new ZCPageBlockItemSchema();
				item.setID(NoUtil.getMaxID("PageBlockID"));// 本应为PageBlockItemID，但目前这样写了，而且不影响正常使用，所以不修改
				item.setBlockID(blockID);
				item.setTitle(title[i]);
				item.setURL(url[i]);
				item.setImage($V("Image"));
				item.setAddTime(new Date());
				item.setAddUser(User.getUserName());
				trans.add(item, Transaction.INSERT);
			}
		}

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
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
		ZCPageBlockSchema block = new ZCPageBlockSchema();
		ZCPageBlockSet set = block.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		ZCPageBlockItemSchema blockItem = new ZCPageBlockItemSchema();
		ZCPageBlockItemSet itemSet = blockItem.query(new QueryBuilder("where blockID in (" + ids + ")"));
		trans.add(itemSet, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void generate() {
		// boolean result = true;
		PageGenerator p = new PageGenerator();
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		ZCPageBlockSchema block = new ZCPageBlockSchema();
		ZCPageBlockSet set = null;
		if(StringUtil.isNotEmpty(ids)){
			set = block.query(new QueryBuilder("where id in (" + ids + ")"));
		}else{
			set = block.query();
		}
		if (p.staticPageBlock(set)) {
			Deploy d = new Deploy();
			d.addJobs(Application.getCurrentSiteID(), p.getFileList());
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("生成区块失败。" + Errorx.printString());
		}
	}
	
	public void copy() {
		String BlockID = $V("ID");
		if (!StringUtil.checkID(BlockID)) {
			Response.setStatus(0);
			Response.setMessage("传入BlockID时发生错误!");
			return;
		}
		String catalogIDs = $V("CatalogIDs");
		if (!StringUtil.checkID(catalogIDs)) {
			Response.setStatus(0);
			Response.setMessage("传入CatalogID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCPageBlockSchema site = new ZCPageBlockSchema();
		ZCPageBlockSet set = site.query(new QueryBuilder("where id in (" + BlockID + ")"));
		String copySiteID = "";
		// 复制页面片段
		// Todo: 需要注意静态化的问题
		String[] catalogArr = catalogIDs.split(",");
		for (int i = 0; i < set.size(); i++) {
			ZCPageBlockSchema block = set.get(i);
			for (int j = 0; j < catalogArr.length; j++) {
				ZCPageBlockSchema blockClone = (ZCPageBlockSchema) block.clone();
				blockClone.setID(NoUtil.getMaxID("PageBlockID"));
				blockClone.setCatalogID(catalogArr[j]);
				if ("".equals(copySiteID)) {
					copySiteID = CatalogUtil.getSiteID(catalogArr[j]);
				}
				blockClone.setSiteID(copySiteID);
				trans.add(blockClone, Transaction.INSERT);

				String sqlPageBlockCount = "update zccatalog set " + "total = total+1 where id=?";
				trans.add(new QueryBuilder(sqlPageBlockCount, catalogArr[j]));
			}
		}
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}
