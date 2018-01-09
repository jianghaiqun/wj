package com.sinosoft.cms.api;

import java.util.Date;
import java.util.Iterator;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCAttachmentSchema;
import com.sinosoft.schema.ZCAudioSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCPageBlockItemSchema;
import com.sinosoft.schema.ZCPageBlockItemSet;
import com.sinosoft.schema.ZCPageBlockSchema;
import com.sinosoft.schema.ZCPageBlockSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZCVideoSchema;
import com.sinosoft.schema.ZDColumnRelaSchema;
import com.sinosoft.schema.ZDColumnRelaSet;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;

public class CatalogAPI implements APIInterface {

	private Mapx params;
	
	private long mCatalogID;

	public Mapx getParams() {
		return params;
	}
	public long getMCatalogID(){
		return mCatalogID;
	}
	public void setParams(Mapx params) {
		convertParams(params);
		this.params = params;
	}
		/*	新建栏目
		 *  新建栏目成功       返回 1
		 *  	  失败        返回-1
		 *  如果栏目已经存在   弹出对话框提示已经存在，并返回0
		 */
	public long insert() {
		return insert(new Transaction());
	}

	public long insert(Transaction trans) {
		String parentID = params.getString("ParentID");
		String name = params.getString("Name");
		String type = params.getString("Type");
		String alias = params.getString("Alias");
		String siteID = params.getString("SiteID");
		String url = params.getString("URL");
		
		DataTable dt = new QueryBuilder("select id from ZCCatalog where parentID='"+parentID+"' and name='"+name+"' and siteID='"+siteID+"'").executeDataTable();
		if(dt.getRowCount()>0){
			Errorx.addError(name + "栏目已经存在!");
			return -1;
		}
		
		int aliasCount = new QueryBuilder("select count(1) from zccatalog where alias=? and siteid=?",alias,siteID).executeInt();
		if(aliasCount>0){
			Errorx.addError(alias + "别名已经存在!");
			return -1;
		}
		
		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		ZCCatalogSchema catalog = new ZCCatalogSchema();

		if (StringUtil.isEmpty(type)) {
			type = Catalog.CATALOG + "";
		}

		// 没有传入parentID表示增加一级栏目，否则是非一级栏目
		if (StringUtil.isNotEmpty(parentID)) {
			pCatalog.setID(Long.parseLong(parentID));
			if(!pCatalog.fill()){
				return -1;
			}
			catalog.setParentID(pCatalog.getID());
			catalog.setSiteID(pCatalog.getSiteID());
			
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);
			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			pCatalog.setIsLeaf(0);
			trans.add(pCatalog, Transaction.UPDATE);
		}
		else {
			// 如果没有父栏目ID传入,也没有SiteID传入,则添加失败
			if (StringUtil.isEmpty(siteID)) {
				return -1;
			}

			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(siteID);
			if (!site.fill()) {
				return -1;
			}
			catalog.setParentID(0);
			catalog.setSiteID(site.getID());
			catalog.setTreeLevel(1);
			parentID = "0";

			if ("1".equals(type)) {
				site.setChannelCount(site.getChannelCount() + 1);
			}
			else if ("2".equals(type)) {
				site.setSpecialCount(site.getSpecialCount() + 1);
			}
			else if ("3".equals(type)) {
				site.setMagzineCount(site.getMagzineCount() + 1);
			}
			trans.add(site, Transaction.UPDATE);
		}

		if (StringUtil.isEmpty(url)) {
			url="";
			if (StringUtil.isNotEmpty(parentID)) {
				url += CatalogUtil.getPath(parentID);
			}
			url += alias + "/";
		}
		
		long catalogID = NoUtil.getMaxID("CatalogID");
		catalog.setID(catalogID);
		
		String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
		catalog.setInnerCode(innerCode);
		
		catalog.setName(name.trim());
		catalog.setAlias(alias);
		catalog.setURL(url);
		catalog.setType(Integer.parseInt(type));

		catalog.setListTemplate(params.getString("Template"));
		catalog.setListNameRule(params.getString("ListNameRule"));
		catalog.setDetailTemplate(params.getString("DetailTemplate"));
		catalog.setDetailNameRule(params.getString("DetailNameRule"));
		catalog.setChildCount(0);
		catalog.setIsLeaf(1);
		catalog.setTotal(0);

		String orderFlag = getCatalogOrderFlag(parentID, type);
		catalog.setOrderFlag(Integer.parseInt(orderFlag) + 1);
		catalog.setLogo(params.getString("Logo"));
		catalog.setListPageSize(20);

		if (StringUtil.isNotEmpty(params.getString("PublishFlag"))) {
			catalog.setPublishFlag("N");
		}
		else {
			catalog.setPublishFlag("Y");
		}

		if ("Y".equals(params.getString("SingleFlag"))) {
			catalog.setSingleFlag("Y");
		}
		else {
			catalog.setSingleFlag("N");
		}

		catalog.setImagePath(params.getString("Imagepath"));
		catalog.setHitCount(0);
		catalog.setMeta_Keywords(params.getString("MetaKeyWords"));
		catalog.setMeta_Description(params.getString("MetaDescription"));
		catalog.setOrderColumn(params.getString("OrderColumn"));
		catalog.setProp1(params.getString("Prop1"));
		catalog.setProp2(params.getString("Prop2"));
		catalog.setProp3(params.getString("Prop3"));
		catalog.setProp4(params.getString("Prop4"));
		catalog.setAddUser("wsdl");
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);
		trans.add(new QueryBuilder("update zccatalog set orderflag=orderflag+1 where orderflag>" + orderFlag + " and type="
				+ type));

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			return 1;
		} 
		return -1;
	}

	public boolean delete() {
		String catalogID = params.getString("CatalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		if (!catalog.fill()) {
			return false;
		}

		Transaction trans = new Transaction();

		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		pCatalog.setID(catalog.getParentID());
		pCatalog.fill();
		pCatalog.setChildCount(pCatalog.getChildCount() - 1);
		if (pCatalog.getChildCount() == 0) {
			pCatalog.setIsLeaf(1);
		}
		else {
			pCatalog.setIsLeaf(0);
		}
		trans.add(pCatalog, Transaction.UPDATE);

		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder(" where InnerCode like ?", catalog.getInnerCode() + "%"));
		trans.add(catalogSet, Transaction.DELETE);
		trans.add(new ZCArticleSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE);
		trans.add(new ZCImageSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE);
		trans.add(new ZCVideoSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE);
		trans.add(new ZCAudioSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE);
		trans.add(new ZCAttachmentSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode()
						+ "%")), Transaction.DELETE);

		String ids = "";
		for (int i = 0; i < catalogSet.size(); i++) {
			ids += catalogSet.get(i).getID();
			if (i != catalogSet.size() - 1) {
				ids += ",";
			}
			// 删除目录
			FileUtil.delete(CatalogUtil.getAbsolutePath(catalogSet.get(i).getID()));
		}
		ZCPageBlockSet blockSet = new ZCPageBlockSchema().query(new QueryBuilder(" where catalogid in (" + ids + ")"));
		for (int i = 0; i < blockSet.size(); i++) {
			ZCPageBlockItemSet itemSet = new ZCPageBlockItemSchema().query(new QueryBuilder(" where blockID=?", blockSet.get(i)
					.getID()));
			trans.add(itemSet, Transaction.DELETE);
		}
		trans.add(blockSet, Transaction.DELETE);

		// 删除栏目及子孙栏目的自定义字段zdcolumnrela,zdcolumnvalue
		String idsStr = "'" + ids.replaceAll(",", "','") + "'";// relaID为字符串字段
		// 删除栏目关联的zdcolumnrela表数据,栏目的文档自定义字段
		ZDColumnRelaSet columnRelaSet = new ZDColumnRelaSchema().query(new QueryBuilder(" where RelaID in(" + idsStr + ")"));
		trans.add(columnRelaSet, Transaction.DELETE);
		// 删除栏目本身关联的zdcolumnvalue数据,栏目的扩展字段
		ZDColumnValueSet columnValueSet1 = new ZDColumnValueSchema().query(new QueryBuilder(" where RelaID in(" + idsStr + ")"));
		trans.add(columnValueSet1, Transaction.DELETE);
		// 删除栏目下文章关联的zdcolumnvalue数据
		String wherepart = " where exists (select ID from zcarticle where cataloginnercode like '" + catalog.getInnerCode()
				+ "%' and ID=zdcolumnvalue.relaID )";
		ZDColumnValueSet columnValueSet2 = new ZDColumnValueSchema().query(new QueryBuilder(wherepart));
		trans.add(columnValueSet2, Transaction.DELETE);

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			return true;
		}
		return false;
	}

	public boolean setSchema(Schema schema) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean update() {

		String ID = params.getString("CatalogID");
//		String name = params.getString("Name");
//		String alias = params.getString("Alias");
		String extend = params.getString("Extend");

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		if (!catalog.fill()) {
			return false;
		}

		catalog.setName(params.getString("Name"));
		catalog.setAlias(params.getString("Alias"));
		catalog.setModifyUser("wsdl");
		catalog.setModifyTime(new Date());

		Transaction trans = new Transaction();
		trans.add(catalog, Transaction.UPDATE);

		// 处理模板沿用
		if (StringUtil.isNotEmpty(extend)) {
			if ("1".equals(extend)) {
				// 本栏目
				// 不用处理，catalog中已经置值了
			}
			else if ("2".equals(extend)) { // 子栏目

				String IndexTemplate = params.getString("IndexTemplate");
				String ListTemplate = params.getString("ListTemplate");
				String DetailTemplate = params.getString("DetailTemplate");
				String RssTemplate = params.getString("RssTemplate");

				if (StringUtil.isNotEmpty(IndexTemplate) && StringUtil.isNotEmpty(ListTemplate)
						&& StringUtil.isNotEmpty(DetailTemplate) && StringUtil.isNotEmpty(RssTemplate)) {
					String innerCode = catalog.getInnerCode();
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=?,rssTemplate=?"
									+ " where innercode like ? and TreeLevel>?");
					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(innerCode + "%");
					qb.add(catalog.getTreeLevel());
					trans.add(qb);
				}

			}
			else if ("3".equals(extend)) {// 全部栏目
				String IndexTemplate = params.getString("IndexTemplate");
				String ListTemplate = params.getString("ListTemplate");
				String DetailTemplate = params.getString("DetailTemplate");
				String RssTemplate = params.getString("RssTemplate");
				if (StringUtil.isNotEmpty(IndexTemplate) && StringUtil.isNotEmpty(ListTemplate)
						&& StringUtil.isNotEmpty(DetailTemplate) && StringUtil.isNotEmpty(RssTemplate)) {
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=? ,rssTemplate=?"
									+ " where siteID=? and Type=?");
					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(catalog.getSiteID());
					qb.add(catalog.getType());
					trans.add(qb);
				}
			}
			else if ("4".equals(extend)) {// 同级栏目
				String IndexTemplate = params.getString("IndexTemplate");
				String ListTemplate = params.getString("ListTemplate");
				String DetailTemplate = params.getString("DetailTemplate");
				String RssTemplate = params.getString("RssTemplate");
				if (StringUtil.isNotEmpty(IndexTemplate) && StringUtil.isNotEmpty(ListTemplate)
						&& StringUtil.isNotEmpty(DetailTemplate) && StringUtil.isNotEmpty(RssTemplate)) {
					String part = "";
					String innerCode = catalog.getInnerCode();
					if (StringUtil.isNotEmpty(innerCode) && innerCode.length() > 4) {
						part = " and innercode like '" + innerCode.substring(0, innerCode.length() - 4) + "%'";
					}
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=?,rssTemplate=?"
									+ " where siteID=? and Type=? and TreeLevel=?" + part);
					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(catalog.getSiteID());
					qb.add(catalog.getType());
					qb.add(catalog.getTreeLevel());
					trans.add(qb);
				}
			}
		}

		// 处理工作流沿用
		String wfExtend = params.getString("WorkFlowExtend");
		if (StringUtil.isNotEmpty(wfExtend)) {
			if ("1".equals(wfExtend)) {
				// 本栏目
				// 不用处理，catalog中已经置值了
			}
			else if ("2".equals(wfExtend)) {
				// 所有子栏目
				trans.add(new QueryBuilder("update zccatalog set workflow =? where innercode like ?", catalog.getWorkflow(),
						catalog.getInnerCode() + "%"));
			}
			else if ("3".equals(wfExtend)) {
				// 站点所有同类栏目
				trans.add(new QueryBuilder("update zccatalog set workflow =? where siteID =" + Application.getCurrentSiteID()
						+ " and Type=? ", catalog.getWorkflow(), catalog.getType()));
			}
			else if ("4".equals(wfExtend)) {
				// 同级栏目
				trans.add(new QueryBuilder("update zccatalog set workflow =? where siteID =" + Application.getCurrentSiteID()
						+ " and Type=? and TreeLevel=" + catalog.getTreeLevel(), catalog.getWorkflow(), catalog.getType()));
			}
		}

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			mCatalogID = catalog.getID();
			return true;
		}
		return false;
	}

	public static String getCatalogOrderFlag(String ParentID, String CatalogType) {

		DataTable parentDT = null;

		if (StringUtil.isEmpty(ParentID) || "0".equals(ParentID)) {
			parentDT = new QueryBuilder("select * from zccatalog where siteID = " + Application.getCurrentSiteID()
					+ " and type = " + CatalogType + " order by orderflag").executeDataTable();
		}
		else {
			String innercode = CatalogUtil.getInnerCode(ParentID);
			parentDT = new QueryBuilder("select * from zccatalog where siteID = " + CatalogUtil.getSiteID(ParentID)
					+ " and type = " + CatalogType + " and innercode like '" + innercode + "%' order by orderflag")
					.executeDataTable();
		}
		if (parentDT != null && parentDT.getRowCount() > 0) {
			return parentDT.getString(parentDT.getRowCount() - 1, "OrderFlag");
		}
		else {
			return "0";
		}
	}

	public void convertParams(Mapx params) {
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			String value = params.getString(key);
			if (StringUtil.isEmpty(value) || "null".equalsIgnoreCase(value)) {
				params.put(key, "");
			}
		}
	}

}
