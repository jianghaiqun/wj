package com.sinosoft.shop;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.ChineseSpelling;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCSiteSchema;

public class Shop extends Page {
	/**
	 * @param params
	 * @return
	 */
	public static Mapx init(Mapx params) {
		long ID = Long.parseLong(params.get("CatalogID").toString());
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		return catalog.toMapx();
	}

	public static Mapx initDialog(Mapx params) {
		String sql =
				"select CodeName,CodeValue from ZDCode where ParentCode !='System' and CodeType ='PublishFlag' Order by CodeOrder";
		DataTable dt = new QueryBuilder(sql).executeDataTable();

		Object o1 = params.get("CatalogID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(ID);
			if (catalog.fill()) {
				Mapx map = catalog.toMapx();
				String imagePath = catalog.getImagePath();
				if (StringUtil.isEmpty(imagePath)) {
					map.put("PicSrc", "../Images/nopicture.gif");
				} else {
					map.put("PicSrc", imagePath);
				}
				map.put("radioPublishFlag", HtmlUtil.dataTableToRadios("PublishFlag", dt, catalog.getPublishFlag()));
				map.put("optionCatalogType", HtmlUtil.mapxToOptions(Catalog.CatalogTypeMap, catalog.getType() + ""));
				return map;
			}
			return null;
		} else {
			params.put("SiteID", Application.getCurrentSiteID() + "");
			params.put("radioPublishFlag", HtmlUtil.dataTableToRadios("PublishFlag", dt, "Y"));
			params.put("optionCatalogType", HtmlUtil.mapxToOptions(Catalog.CatalogTypeMap));
			params.put("PicSrc", "../Images/nopicture.gif");
			params.put("ListTemplate", "/template/list.html");
			params.put("DetailTemplate", "/template/detail.html");
			return params;
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		QueryBuilder qb = new QueryBuilder("select path,filename from zcimage where id=?", Long.parseLong(ID));
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			this.Response.put("picSrc", Config.getContextPath() + Config.getValue("UploadDir")
					+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + "s_"
					+ dt.get(0, "filename").toString());
		}
	}

	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		int catalogType = Catalog.SHOP_GOODS;
		String parentTreeLevel = (String) ta.getParams().get("ParentLevel");
		String parentID = (String) ta.getParams().get("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb =
					new QueryBuilder("select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ?");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(parentTreeLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb =
					new QueryBuilder("select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=?");
			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}

		String siteName = "商品库";
		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType)) {// 单选
			ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>" + siteName
					+ "</label>");
		} else if ("2".equals(inputType)) {// 多选、全选
			ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID
					+ "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
		} else {
			ta.setRootText(siteName);
		}

		ta.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		int count = dt.getRowCount();
		StringBuffer sb = new StringBuffer();
		QueryBuilder qb = new QueryBuilder();
		sb.append("update ZCCatalog set ");
		for (int i = 0; i < count; i++) {
			sb.append(dt.get(i, "InnerCode"));
			sb.append("=?");

			qb.add(dt.get(i, "Value"));
		}
		sb.append(" modifyuser = '");
		sb.append(User.getUserName());
		sb.append("',modifytime = '");
		sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		sb.append("' where ID = ?");
		qb.add(dt.get(0, "ID_key"));

		qb.setSQL(sb.toString());
		if (qb.executeNoQuery() != -1) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	/**
	 * 这里只能增加栏目,并且要注意增加一级栏目和非一级栏目的差别，更新的上级信息不一样
	 * 
	 */
	public void add() {
		Transaction trans = new Transaction();
		add(Request, trans);
		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("插入数据发生错误!");
		}
	}

	/**
	 * 增加栏目
	 * 
	 * @param dc
	 * @param trans
	 */
	public ZCCatalogSchema add(DataCollection dc, Transaction trans) {
		String parentID = dc.getString("ParentID");

		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		String type = dc.getString("Type"); // 频道,专题，期刊
		// 没有传入parentID表示增加一级栏目，否则是非一级栏目
		if (parentID != null && !"0".equals(parentID)) {
			pCatalog.setID(Integer.parseInt(parentID));
			pCatalog.fill();

			catalog.setParentID(pCatalog.getID());
			catalog.setSiteID(pCatalog.getSiteID());
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);
			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			pCatalog.setIsLeaf(0);
			trans.add(pCatalog, Transaction.UPDATE);
		} else {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(Application.getCurrentSiteID());
			site.fill();

			catalog.setParentID(0);
			catalog.setSiteID(site.getID());
			catalog.setTreeLevel(1);

			if ("1".equals(type)) {
				site.setChannelCount(site.getChannelCount() + 1);
			} else if ("2".equals(type)) {
				site.setSpecialCount(site.getSpecialCount() + 1);
			} else if ("3".equals(type)) {
				site.setMagzineCount(site.getMagzineCount() + 1);
			}
			trans.add(site, Transaction.UPDATE);
		}

		String url = dc.getString("URL");
		if (url == null || "".equals(url)) {
			url = CatalogUtil.getPath(catalog.getParentID()) + dc.getString("Alias") + "/";
		}
		long catalogID = NoUtil.getMaxID("CatalogID");
		catalog.setID(catalogID);
		
		String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
		catalog.setInnerCode(innerCode);
		
		catalog.setName(dc.getString("Name"));
		catalog.setAlias(dc.getString("Alias"));
		catalog.setURL(url);
		catalog.setType(Integer.parseInt(dc.getString("Type")));
		catalog.setListTemplate(dc.getString("ListTemplate"));
		catalog.setListNameRule(dc.getString("ListNameRule"));
		catalog.setDetailTemplate(dc.getString("DetailTemplate"));
		catalog.setDetailNameRule(dc.getString("DetailNameRule"));
		catalog.setChildCount(0);
		catalog.setIsLeaf(1);
		catalog.setTotal(0);
		catalog.setOrderFlag(Catalog.getCatalogOrderFlag(parentID, type));
		catalog.setLogo(dc.getString("Logo"));
		catalog.setListPageSize(20);

		String publishFlag = dc.getString("PublishFlag");
		if (publishFlag != null && !"".equals(publishFlag)) {
			catalog.setPublishFlag(publishFlag);
		} else {
			catalog.setPublishFlag("1");
		}
		catalog.setImagePath(dc.getString("setImagePath"));
		catalog.setHitCount(0);
		catalog.setMeta_Keywords(dc.getString("Meta_Keywords"));
		catalog.setMeta_Description(dc.getString("Meta_Description"));
		catalog.setOrderColumn(dc.getString("OrderColumn"));
		catalog.setProp1(dc.getString("Prop1"));
		catalog.setProp2(dc.getString("Prop2"));
		catalog.setProp3(dc.getString("Prop3"));
		catalog.setProp4(dc.getString("Prop4"));
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);

		return catalog;
	}

	public long add(ZCCatalogSchema parent, ZCCatalogSchema catalog, Transaction trans) {

		String type = catalog.getType() + ""; // 频道,专题，期刊
		// 没有传入parentID表示增加一级栏目，否则是非一级栏目
		if (parent != null) {
			catalog.setParentID(parent.getID());
			catalog.setSiteID(parent.getSiteID());
			catalog.setTreeLevel(parent.getTreeLevel() + 1);
			parent.setChildCount(parent.getChildCount() + 1);
			parent.setIsLeaf(0);
			trans.add((ZCCatalogSchema) parent.clone(), Transaction.UPDATE);
		} else {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(Application.getCurrentSiteID());
			site.fill();

			catalog.setParentID(0);
			catalog.setSiteID(site.getID());
			catalog.setTreeLevel(1);

			if ("1".equals(type)) {
				site.setChannelCount(site.getChannelCount() + 1);
			} else if ("2".equals(type)) {
				site.setSpecialCount(site.getSpecialCount() + 1);
			} else if ("3".equals(type)) {
				site.setMagzineCount(site.getMagzineCount() + 1);
			}
			trans.add(site, Transaction.UPDATE);
		}
		
		String innerCode = CatalogUtil.createCatalogInnerCode(parent.getInnerCode());
		catalog.setInnerCode(innerCode);

		long catalogID = NoUtil.getMaxID("CatalogID");
		catalog.setID(catalogID);

		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);

		return catalogID;
	}

	public void save() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("ID")));
		catalog.fill();
		catalog.setValue(Request);
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());

		Transaction trans = new Transaction();
		trans.add(catalog, Transaction.UPDATE);

		// 工作流沿用
		String extend = $V("Extend");
		if ("1".equals(extend)) {
			// 本栏目
			// 不用处理，catalog中已经置值了
		} else if ("2".equals(extend)) {
			// 所有子栏目
			trans.add(new QueryBuilder("update zccatalog set workflow =? where innercode like ?", catalog.getWorkflow(), catalog.getInnerCode()
					+ "%"));
		} else if ("3".equals(extend)) {
			// 站点所有同类栏目
			trans.add(new QueryBuilder("update zccatalog set workflow =? where siteID =" + Application.getCurrentSiteID()
					+ " and Type=? ", catalog.getWorkflow(), catalog.getType()));
		} else if ("4".equals(extend)) {
			// 同级栏目
			trans.add(new QueryBuilder("update zccatalog set workflow =? where siteID =" + Application.getCurrentSiteID()
					+ " and Type=? and TreeLevel=" + catalog.getTreeLevel(), catalog.getWorkflow(), catalog.getType()));
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	public void saveTemplate() {
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("ID")));
		catalog.fill();
		catalog.setValue(Request);
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());

		trans.add(catalog, Transaction.UPDATE);
		String extend = $V("Extend");
		if ("1".equals(extend)) { // 子栏目
			String innerCode = catalog.getInnerCode();
			QueryBuilder qb =
					new QueryBuilder("update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=? where inndercode like ? and TreeLevel>?");
			qb.add($V("IndexTemplate"));
			qb.add($V("ListTemplate"));
			qb.add($V("DetailTemplate"));
			qb.add(innerCode + "%");
			qb.add(catalog.getTreeLevel());

			trans.add(qb);
		} else if ("2".equals(extend)) {// 全部栏目
			QueryBuilder qb =
					new QueryBuilder("update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=? where siteID=? and  Type=?");
			qb.add($V("IndexTemplate"));
			qb.add($V("ListTemplate"));
			qb.add($V("DetailTemplate"));
			qb.add(catalog.getSiteID());
			qb.add(catalog.getType());

			trans.add(qb);
		} else if ("3".equals(extend)) {// 同级栏目
			QueryBuilder qb =
					new QueryBuilder("update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=? where siteID=? and  Type=? and TreeLevel=?");
			qb.add($V("IndexTemplate"));
			qb.add($V("ListTemplate"));
			qb.add($V("DetailTemplate"));
			qb.add(catalog.getSiteID());
			qb.add(catalog.getType());
			qb.add(catalog.getTreeLevel());

			trans.add(qb);
		}

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("保存数据发生错误!");
		}
	}

	public static void importTreeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("PrentID");

		long parentID = (obj != null) ? Long.parseLong(obj.toString()) : 0;

		ta.setRootText("商品库");
		String fileName = ta.getParams().get("FilePath").toString();
		int TreeLevel = 1;
		if (parentID != 0) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(parentID);
			catalog.fill();
			TreeLevel = (int)catalog.getTreeLevel();
		}
		DataTable dt = getTreeDataTable(fileName, parentID, TreeLevel);
		ta.bindData(dt);
	}

	private static DataTable getTreeDataTable(String fileName, long parentID, int TreeLevel) {
		String fileText = FileUtil.readText(fileName);
		String[] catalogs = fileText.split("\n");

		DataTable dt = new DataTable();
		DataColumn ID = new DataColumn("ID", DataColumn.BIGDECIMAL);
		DataColumn ParentID = new DataColumn("ParentID", DataColumn.BIGDECIMAL);
		DataColumn Level = new DataColumn("TreeLevel", DataColumn.INTEGER);
		DataColumn Name = new DataColumn("Name", DataColumn.STRING);
		dt.insertColumn(ID);
		dt.insertColumn(ParentID);
		dt.insertColumn(Level);
		dt.insertColumn(Name);

		long lastID = 1;
		int topTreeLevel = TreeLevel;
		Mapx mapx = new Mapx();
		mapx.put(TreeLevel + "", parentID + "");
		for (int i = 0; i < catalogs.length; i++) {
			String catalog = catalogs[i];
			catalog = StringUtil.toDBC(catalog);
			catalog = StringUtil.rightTrim(catalog);
			if (catalog.lastIndexOf(' ') != -1) {
				int length = 0;
				for (int k = 0; k < catalog.length(); k++) {
					length = k + 1;
					if (catalog.charAt(k) != ' ') {
						break;
					}
				}
				int currentTreeLevel = (length / 2) + 1;
				if (currentTreeLevel > TreeLevel) {
					parentID = lastID;
					TreeLevel = currentTreeLevel;
					mapx.put(TreeLevel + "", parentID + "");
				} else if (currentTreeLevel < TreeLevel) {
					// parentID = lastID;
					TreeLevel = currentTreeLevel;
					parentID = Long.parseLong(mapx.get(TreeLevel + "").toString());
				}
			} else {
				TreeLevel = topTreeLevel;
				parentID = Long.parseLong(mapx.get(TreeLevel + "").toString());
			}
			lastID = i + 1;
			Object[] catalogRow = { new Long(lastID), new Long(parentID), new Integer(TreeLevel), catalog.trim() };
			dt.insertRow(catalogRow);
		}

		return dt;
	}

	/**
	 * 
	 * 批量导入栏目清单
	 */
	public void importCatalog() {
		long parentID = Long.parseLong($V("ParentID"));

		String fileName = $V("FilePath");
		String fileText = FileUtil.readText(fileName);
		String[] catalogs = fileText.split("\n");
		String type = $V("Type"); // 频道,专题，期刊

		int TreeLevel = 1;
		Mapx catalogMap = new Mapx();
		if (parentID != 0) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(parentID);
			catalog.fill();
			TreeLevel = (int)catalog.getTreeLevel();
			catalogMap.put(catalog.getID() + "", catalog);
		}
		long lastID = 0;
		int topTreeLevel = TreeLevel;
		Mapx mapx = new Mapx();
		mapx.put(TreeLevel + "", parentID + "");

		Transaction trans = new Transaction();
		for (int i = 0; i < catalogs.length; i++) {
			String catalogName = catalogs[i];
			catalogName = StringUtil.toDBC(catalogName);
			catalogName = StringUtil.rightTrim(catalogName);
			if (catalogName.lastIndexOf(' ') != -1) {
				int length = 0;
				for (int k = 0; k < catalogName.length(); k++) {
					length = k + 1;
					if (catalogName.charAt(k) != ' ') {
						break;
					}
				}
				int currentTreeLevel = (length / 2) + topTreeLevel;
				if (currentTreeLevel > TreeLevel) {
					parentID = lastID;
					TreeLevel = currentTreeLevel;
					mapx.put(TreeLevel + "", parentID + "");
				} else if (currentTreeLevel < TreeLevel) {
					TreeLevel = currentTreeLevel;
					parentID = Long.parseLong(mapx.get(TreeLevel + "").toString());
				}
			} else {
				TreeLevel = topTreeLevel;
				parentID = Long.parseLong(mapx.get(TreeLevel + "").toString());
			}

			catalogName = catalogName.trim();

			ZCCatalogSchema pCatalog = new ZCCatalogSchema();
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			if (parentID != 0) {
				pCatalog.setID(parentID);
				boolean isNewData = false;
				if (!pCatalog.fill()) {
					pCatalog = (ZCCatalogSchema) catalogMap.get(parentID + "");
					isNewData = true;
				}

				catalog.setParentID(pCatalog.getID());
				catalog.setSiteID(pCatalog.getSiteID());
				catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);
				pCatalog.setChildCount(pCatalog.getChildCount() + 1);
				pCatalog.setIsLeaf(0);
				catalogMap.put(pCatalog.getID() + "", pCatalog);

				if (isNewData) {
					trans.add(pCatalog, Transaction.INSERT);
				} else {
					trans.add(pCatalog, Transaction.UPDATE);
				}

			} else {
				ZCSiteSchema site = new ZCSiteSchema();
				site.setID(Application.getCurrentSiteID());
				site.fill();

				catalog.setParentID(0);
				catalog.setSiteID(site.getID());
				catalog.setTreeLevel(1);

				if ("1".equals(type)) {
					site.setChannelCount(site.getChannelCount() + 1);
				} else if ("2".equals(type)) {
					site.setSpecialCount(site.getSpecialCount() + 1);
				} else if ("3".equals(type)) {
					site.setMagzineCount(site.getMagzineCount() + 1);
				}
				trans.add(site, Transaction.UPDATE);
			}

			lastID = NoUtil.getMaxID("CatalogID");
			catalog.setID(lastID);
			
			String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
			catalog.setInnerCode(innerCode);
			
			catalog.setName(catalogName);
			String alias = ChineseSpelling.getFirstAlpha(catalogName);
			catalog.setAlias(alias);
			catalog.setURL(alias + "/");
			if (StringUtil.isNotEmpty(type) && !"null".equals(type)) {
				catalog.setType(Integer.parseInt(type));
			} else {
				catalog.setType(Catalog.CATALOG);
			}

			catalog.setListTemplate($V("ListTemplate"));
			catalog.setDetailTemplate($V("DetailTemplate"));
			catalog.setChildCount(0);
			catalog.setIsLeaf(1);
			catalog.setTotal(0);
			catalog.setOrderFlag(Catalog.getCatalogOrderFlag(parentID, type));
			catalog.setLogo($V("Logo"));
			catalog.setListPageSize(1);
			catalog.setPublishFlag("1");
			catalog.setHitCount(0);
			catalog.setMeta_Keywords($V("Meta_Keywords"));
			catalog.setMeta_Description($V("Meta_Description"));
			catalog.setOrderColumn($V("OrderColumn"));
			catalog.setProp1($V("Prop1"));
			catalog.setProp2($V("Prop2"));
			catalog.setProp3($V("Prop3"));
			catalog.setProp4($V("Prop4"));
			catalog.setAddUser(User.getUserName());
			catalog.setAddTime(new Date());

			catalogMap.put(catalog.getID() + "", catalog);

			trans.add(catalog, Transaction.INSERT);
		}

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("插入数据发生错误!");
		}
	}

	/**
	 * 移动栏目到目标栏目
	 */
	public void move() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		long catalogID = Long.parseLong((String) $V("CatalogID"));
		long parentID = Long.parseLong((String) $V("ParentID"));
		catalog.setID(catalogID);
		catalog.fill();
		Transaction tran = new Transaction();

		long TreeLevel = 0;
		String innerCode, parentInnerCode = "";
		if (parentID == 0) {
			TreeLevel = 1;
			if (catalog.getTreeLevel() == 1) {
				Response.setStatus(0);
				Response.setMessage("选择的栏目和目标栏目处于同一级，不能转移。");
				return;
			}

			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(Application.getCurrentSiteID());
			site.fill();

			long type = catalog.getType(); // 频道,专题，期刊
			if (type == Catalog.CATALOG) {
				site.setChannelCount(site.getChannelCount() + 1);
			} else if (type == Catalog.SUBJECT) {
				site.setSpecialCount(site.getSpecialCount() + 1);
			} else if (type == Catalog.MAGAZINE) {
				site.setMagzineCount(site.getMagzineCount() + 1);
			}
			tran.add(site, Transaction.UPDATE);
		} else {
			ZCCatalogSchema pCatalog = new ZCCatalogSchema();
			pCatalog.setID(parentID);
			pCatalog.fill();

			parentInnerCode = pCatalog.getInnerCode();
			TreeLevel = pCatalog.getTreeLevel() + 1;

			if (catalog.getTreeLevel() == TreeLevel && catalog.getParentID() == parentID) {
				Response.setStatus(0);
				Response.setMessage("选择的栏目和目标栏目处于同一级，不能转移。");
				return;
			}

			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			pCatalog.setIsLeaf(0);
			tran.add(pCatalog, Transaction.UPDATE);
		}

		// 更新上级目录的childcount
		long oldParentID = catalog.getParentID();
		if (oldParentID == 0) {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(Application.getCurrentSiteID());
			site.fill();

			long type = catalog.getType(); // 频道,专题，期刊
			if (type == Catalog.CATALOG) {
				site.setChannelCount(site.getChannelCount() - 1);
			} else if (type == Catalog.SUBJECT) {
				site.setSpecialCount(site.getSpecialCount() - 1);
			} else if (type == Catalog.MAGAZINE) {
				site.setMagzineCount(site.getMagzineCount() - 1);
			}
			tran.add(site, Transaction.UPDATE);
		} else {
			tran.add(new QueryBuilder("update zccatalog set childcount=childcount-1 where id=?", oldParentID));
		}

		innerCode = (TreeLevel == 1) ? CatalogUtil.createCatalogInnerCode(""):
			CatalogUtil.createCatalogInnerCode(parentInnerCode);
		catalog.setInnerCode(innerCode);

		catalog.setTreeLevel(TreeLevel);

		catalog.setParentID(parentID);
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());

		tran.add(catalog, Transaction.UPDATE);
		tran.add(new QueryBuilder("update zcarticle set CatalogInnerCode=? where catalogid=?", innerCode, catalogID));
		tran.add(new QueryBuilder("update bzcarticle set CatalogInnerCode=? where catalogid=?", innerCode, catalogID));

		Mapx map = new Mapx();
		if (catalog.getChildCount() > 0) {
			ZCCatalogSet childCatalogSet = new ZCCatalogSchema().query(new QueryBuilder("where parentid=?", catalogID));
			for (int i = 0; i < childCatalogSet.size(); i++) {
				Mapx childMap = moveChild(childCatalogSet.get(i), TreeLevel, innerCode);
				map.putAll(childMap);
			}
		}

		Object[] ks = map.keyArray();
		for (int i = 0; i < map.size(); i++) {
			String sql = ks[i].toString();
			tran.add(new QueryBuilder(sql));
		}

		if (tran.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("保存数据发生错误!");
		}
	}

	/*******************************************************************************************************************
	 * 移动当前栏目及子节点
	 * 
	 * @param catalog
	 * @param TreeLevel
	 * @param parentInnerCode
	 * @return
	 */
	public Mapx moveChild(ZCCatalogSchema catalog, long TreeLevel, String parentInnerCode) {
		Mapx map = new Mapx();
		long catalogID = catalog.getID();
		String innerCode;

		TreeLevel = TreeLevel + 1;
		
		innerCode = CatalogUtil.createCatalogInnerCode(parentInnerCode);

		map.put("update zccatalog set TreeLevel=" + TreeLevel + ",innercode='" + innerCode + "',modifyuser='"
				+ User.getUserName() + "',modifytime='" + DateUtil.getCurrentDate() + "' where id=" + catalogID, new Integer(Transaction.SQL));

		map.put("update zcarticle set CatalogInnerCode='" + innerCode + "' where catalogid=" + catalogID, new Integer(Transaction.SQL));
		map.put("update bzcarticle set CatalogInnerCode='" + innerCode + "' where catalogid=" + catalogID, new Integer(Transaction.SQL));

		if (catalog.getChildCount() > 0) {
			ZCCatalogSet childCatalogSet = new ZCCatalogSchema().query(new QueryBuilder("where parentid=?", catalogID));
			for (int i = 0; i < childCatalogSet.size(); i++) {
				Mapx childMap = moveChild(childCatalogSet.get(i), TreeLevel, innerCode);
				map.putAll(childMap);
			}
		}

		return map;
	}

	public void publish() {
		long id;
		// 站点
		if ("0".equals($V("type"))) {
			id = publishAllTask(Application.getCurrentSiteID());
			Response.setStatus(1);
			$S("TaskID", "" + id);
		} else {
			long catalogID = Long.parseLong($V("CatalogID"));
			if ("1".equals($V("ChildFlag"))) {
				id = publishTask(catalogID, true, true);
			} else {
				id = publishTask(catalogID, false, true);
			}
			Response.setStatus(1);
			$S("TaskID", "" + id);
		}
	}

	private long publishTask(final long catalogID, final boolean child, final boolean detail) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(catalogID, child, detail, true, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public long publishAllTask(final long siteID) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishAll(siteID);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	/**
	 * 这里只能删除栏目,并且要注意删除一级栏目和非一级栏目的差别，更新的上级信息不一样
	 * 
	 */
	public void del() {
		Transaction trans = new Transaction();
		String ID = $V("CatalogID");

		Catalog.deleteCatalog(trans, Long.parseLong(ID));

		if (trans.commit()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("删除栏目失败");
		}
	}

	public static void deleteCatalog(Transaction trans, long ID) {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();

		if (catalog.getTreeLevel() == 1) {
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(catalog.getSiteID());
			site.fill();
			// 频道,专题，期刊
			long type = catalog.getType();
			if (type == Catalog.CATALOG) {
				site.setChannelCount(site.getChannelCount() - 1);
			} else if (type == Catalog.SUBJECT) {
				site.setSpecialCount(site.getSpecialCount() - 1);
			} else if (type == Catalog.MAGAZINE) {
				site.setMagzineCount(site.getMagzineCount() - 1);
			}
			trans.add(site, Transaction.UPDATE);
		} else {
			ZCCatalogSchema pCatalog = new ZCCatalogSchema();
			pCatalog.setID(catalog.getParentID());
			pCatalog.fill();
			pCatalog.setChildCount(pCatalog.getChildCount() - 1);
			if (pCatalog.getChildCount() == 0) {
				pCatalog.setIsLeaf(1);
			} else {
				pCatalog.setIsLeaf(0);
			}
			trans.add(pCatalog, Transaction.UPDATE);
		}
		trans.add(catalog.query(new QueryBuilder(" where InnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE_AND_BACKUP);
		trans.add(new ZCArticleSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%")), Transaction.DELETE_AND_BACKUP);

	}

	public static void createDefaultCatalog(String siteID, int type) {
		createDefaultCatalog(Long.parseLong(siteID), type);
	}

	public static void createDefaultCatalog(long siteID, int type) {
		DataTable dt =
				new QueryBuilder("select id from zccatalog where siteid =?  and type=?", siteID, type).executePagedDataTable(1, 0);
		if (dt != null && dt.getRowCount() > 0) {
			return;
		}
		Transaction trans = new Transaction();
		// 给媒体库建默认专辑
		ZCCatalogSchema defaultCatalog = new ZCCatalogSchema();
		defaultCatalog.setID(NoUtil.getMaxID("CatalogID"));
		defaultCatalog.setSiteID(siteID);
		defaultCatalog.setParentID(0);
		defaultCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
		defaultCatalog.setTreeLevel(1);
		String Name = "";
		switch (type) {
		case 4:
			Name = "默认图片";
			break;
		case 5:
			Name = "默认视频";
			break;
		case 6:
			Name = "默认音频";
			break;
		case 7:
			Name = "默认附件";
			break;
		default:
			Name = "默认图片";
			break;
		}
		defaultCatalog.setName(Name);
		defaultCatalog.setURL("");
		defaultCatalog.setAlias(StringUtil.getChineseFirstAlpha(Name));
		defaultCatalog.setType(type);
		defaultCatalog.setListTemplate("");
		defaultCatalog.setListNameRule("");
		defaultCatalog.setDetailTemplate("");
		defaultCatalog.setDetailNameRule("");
		defaultCatalog.setChildCount(0);
		defaultCatalog.setIsLeaf(1);
		defaultCatalog.setTotal(0);
		defaultCatalog.setOrderFlag(Catalog.getCatalogOrderFlag("0", type));
		defaultCatalog.setLogo("");
		defaultCatalog.setListPageSize(10);
		defaultCatalog.setPublishFlag("Y");
		defaultCatalog.setHitCount(0);
		defaultCatalog.setMeta_Keywords("");
		defaultCatalog.setMeta_Description("");
		defaultCatalog.setOrderColumn("");
		defaultCatalog.setAddUser(User.getUserName());
		defaultCatalog.setAddTime(new Date());
		trans.add(defaultCatalog, Transaction.INSERT);
		trans.commit();
	}

	public static void main(String[] args) {
		ZCCatalogSet set = new ZCCatalogSchema().query(new QueryBuilder("where siteid=14 order by id"));
		for (int k = 1; k < 30; k++) {
			for (int i = 0; i < set.size(); i++) {
				ZCCatalogSchema schema = (ZCCatalogSchema) set.get(i).clone();
				if (schema.getTreeLevel() == 1) {
					schema.setInnerCode(StringUtil.leftPad((String.valueOf(Integer.parseInt(schema.getInnerCode().substring(0, 4))
							+ set.size() * k / 3)), '0', 4));
				} else if (schema.getTreeLevel() == 2) {
					schema.setInnerCode((StringUtil.leftPad((String.valueOf(Integer.parseInt(schema.getInnerCode().substring(0, 4))
							+ set.size() * k / 3)), '0', 4))
							+ "0001");
				} else if (schema.getTreeLevel() == 3) {
					schema.setInnerCode((StringUtil.leftPad((String.valueOf(Integer.parseInt(schema.getInnerCode().substring(0, 4))
							+ set.size() * k / 3)), '0', 4))
							+ "00010001");
				}

				schema.setID(schema.getID() + set.size() * k);
				if (schema.getParentID() != 0) {
					schema.setParentID(schema.getParentID() + set.size() * k);
				}
				schema.insert();
			}
		}

	}

}
