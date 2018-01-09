package com.sinosoft.cms.resource;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.pub.CMSCache;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.cms.site.CatalogShowConfig;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.ChineseSpelling;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.UserList;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCImageRelaSchema;
import com.sinosoft.schema.ZCImageRelaSet;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCImageSet;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZDPrivilegeSchema;
import com.sinosoft.schema.ZDPrivilegeSet;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class ImageLib extends Page {

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("ID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		return catalog.toMapx();
	}  

	public void setImageCover() {
		String ID = $V("ID");
		String imagePath = "";
		DataTable dt = new QueryBuilder("select path,filename from zcimage where id=?", ID).executeDataTable();
		if (dt.getRowCount() > 0) {
			imagePath = dt.get(0, "path").toString() + dt.get(0, "filename").toString();
		}
		ZCImageSchema image = new ZCImageSchema();
		image.setID(Long.parseLong(ID));
		image.fill();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(image.getCatalogID());
		catalog.fill();
		catalog.setImagePath(imagePath);
		if (catalog.update()) {
			this.Response.setLogInfo(1, "设置专辑封面成功！");
		} else {
			this.Response.setLogInfo(0, "设置专辑封面失败！");
		}
	}

	public void setTopper() {
		String ID = $V("ID");
		ZCImageSchema image = new ZCImageSchema();
		image.setID(Long.parseLong(ID));
		image.fill();
		QueryBuilder qb = new QueryBuilder("select max(OrderFlag) from ZCImage where CatalogID = ?", image.getCatalogID());
		image.setOrderFlag(qb.executeLong() + 1);
		if (image.update()) {
			this.Response.setLogInfo(1, "置顶成功！");
		} else {
			this.Response.setLogInfo(0, "置顶失败！");
		}
	}

	public void ImageLibEdit() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setValue(this.Request);
		catalog.fill();
		catalog.setValue(this.Request);
		catalog.setAlias(StringUtil.getChineseFirstAlpha(catalog.getName()));
		if (catalog.update()) {
			CatalogUtil.update(catalog.getID());
			this.Response.setLogInfo(1, "修改图片分类成功！");
		} else {
			this.Response.setLogInfo(0, "修改图片分类失败！");
		}
	}

	public void delLib() {
		String catalogID = $V("catalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong(catalogID));
		if (!catalog.fill()) {
			this.Response.setLogInfo(0, "没有图片分类！");
			return;
		}

		if ("N".equals(catalog.getProp4())) {
			this.Response.setLogInfo(0, "不能删除该图片分类！");
			return;
		}

		//add by liuc 20130705 如果登录的不是admin用户则删除的该截点的同时将该截点下该包含所有的not admin 用户全部删除
		if(User.getUserName()!=null && !User.getUserName().equals("") && !User.getUserName().equals(UserList.ADMINISTRATOR)) {
			Transaction transnew=new Transaction();
			
			try {
				//schema
				
				ZDPrivilegeSchema mZDPrivilegeSchema=new ZDPrivilegeSchema();
				ZDPrivilegeSet mZDPrivilegeSet=mZDPrivilegeSchema.query((new QueryBuilder(" where PrivType='"+Priv.IMAGE+"' and id like '"+catalog.getInnerCode()+"%' ")));
				transnew.add(mZDPrivilegeSet, Transaction.DELETE_AND_BACKUP);
				transnew.commit();
			} catch (Exception e) {
				logger.error("在ImageLib.java类中删除ZDPrivilege值时出现异常信息"+ e.getMessage(), e);
			}
			Priv.updateAllPriv(User.getUserName());
		}
		//add by liuc end
		
		ZCCatalogSet catalogSet = new ZCCatalogSchema().query(new QueryBuilder("where InnerCode like ?", catalog.getInnerCode() + "%"));
		Transaction trans = new Transaction();
		ZCImageSet imageSet = new ZCImageSchema().query(new QueryBuilder("where CatalogInnerCode like ?", catalog.getInnerCode() + "%"));
		for (int i = 0; i < imageSet.size(); i++) {
			ZCImageRelaSet ImageRelaSet = new ZCImageRelaSchema().query(new QueryBuilder(" where id = ?", imageSet.get(i).getID()));
			trans.add(ImageRelaSet, Transaction.DELETE_AND_BACKUP);
		}

		File file = new File(Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/upload/Image/"
				+ CatalogUtil.getPath(catalog.getID()));
		FileUtil.delete(file);
		trans.add(imageSet, Transaction.DELETE_AND_BACKUP);
		trans.add(catalogSet, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			CMSCache.removeCatalogSet(catalogSet);// 从缓存中剔除
			this.Response.setLogInfo(1, "删除图片分类成功！");

			// 删除发布文件，更新列表
			Publisher p = new Publisher();
			p.deleteFileTask(imageSet);

		} else {
			this.Response.setLogInfo(0, "删除图片分类失败！");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		long SiteID = Application.getCurrentSiteID();
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentLevel = (String) params.get("ParentLevel");
		String parentID = (String) params.get("ParentID");
		if (StringUtil.isEmpty(parentID)) {
			parentID = "0";
		}

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastImageLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, CatalogShowConfig.getImageLibShowLevel());

		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder("select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode from ZCCatalog Where Type =? and SiteID =? and TreeLevel>? and innerCode like ?");
			qb.add(Catalog.IMAGELIB);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			if (!CatalogShowConfig.isImageLibLoadAllChild()) {
				qb.append(" and TreeLevel<?", Integer.parseInt(parentLevel) + 3);
				ta.setExpand(false);
			} else {
				ta.setExpand(true);
			}
			qb.append(" order by orderflag,innercode");
			dt = qb.executeDataTable();
		} else {
			ta.setLevel(CatalogShowConfig.getImageLibShowLevel());
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type=? and SiteID=? and TreeLevel-1<=? order by orderflag,innercode");
			qb.add(Catalog.IMAGELIB);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, Catalog.IMAGELIB, SiteID, ta.getLevel());
		}
		ta.setRootText("图片库");
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.IMAGE, dr.getString("InnerCode"), Priv.IMAGE_BROWSE);
			}
		});
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	public static void dg1DataList(DataListAction dla) {
		String CatalogID = dla.getParam("CatalogID");
		if (StringUtil.isEmpty(CatalogID)) {
			dla.bindData(new DataTable());
			return;
		}
		String name = (String) dla.getParam("Name");
		String startDate = (String) dla.getParam("StartDate");
		String endDate = (String) dla.getParam("EndDate");
		QueryBuilder qb = new QueryBuilder("select * from ZCImage where 1=1");
		if ("0".equals(CatalogID)) {
			qb.append(" and SiteID=?", Application.getCurrentSiteID());
		} else {
			qb.append(" and CatalogID=?", Long.parseLong(CatalogID));
		}
		if (StringUtil.isNotEmpty(name)) {
			qb.append(" and Name like ?", "%" + name.trim() + "%");
		}
		if (StringUtil.isNotEmpty(startDate)) {
			qb.append(" and addtime>=? ", startDate);
		}
		if (StringUtil.isNotEmpty(endDate)) {
			qb.append(" and addtime<=? ", endDate);
		}
		qb.append(" order by OrderFlag desc,ID desc");
		dla.setTotal(qb);
		String alias = Config.getValue("StaticResourcePath");
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("Alias", alias);
		dla.bindData(dt);
	}

	public static void dg1DataListBrowse(DataListAction dla) {
		String search = dla.getParam("Search");
		if (search == null || "".equals(search)) {
			dla.bindData(new DataTable());
			return;
		}
		String alias = Config.getValue("StaticResourcePath");
		String catalogID = dla.getParam("_CatalogID");
		String Name = dla.getParam("Name");
		String StartDate = dla.getParam("StartDate");
		String EndDate = dla.getParam("EndDate");
		String Info = dla.getParam("Info");
		QueryBuilder qb = new QueryBuilder("select * from ZCImage");
		if (StringUtil.isNotEmpty(catalogID) && !"null".equals(catalogID)) {
			qb.append(" where CatalogID = ?", Long.parseLong(catalogID));
		} else {
			qb.append(" where SiteID = ?", Application.getCurrentSiteID());
		}
		if (StringUtil.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (StringUtil.isNotEmpty(Info)) {
			qb.append(" and Info like ?", "%" + Info.trim() + "%");
		}
		if (StringUtil.isNotEmpty(StartDate)) {
			qb.append(" and addtime >= ? ", StartDate);
		}
		if (StringUtil.isNotEmpty(EndDate)) {
			qb.append(" and addtime <= ? ", EndDate);
		}
		qb.append(" order by ID desc");
		dla.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				if (Priv.getPriv(User.getUserName(), Priv.SITE, Application.getCurrentSiteID() + "", Priv.SITE_MANAGE)) {
					return true;
				}
				DataRow dr = (DataRow) obj;
				String CatalogID = dr.getString("CatalogID");
				return Priv.getPriv(User.getUserName(), Priv.IMAGE, CatalogUtil.getInnerCode(CatalogID), Priv.IMAGE_BROWSE);
			}
		});
		DataTable newdt = new DataTable(dt.getDataColumns(), null);
		for (int i = dla.getPageIndex() * dla.getPageSize(); i < dt.getRowCount() && i < (dla.getPageIndex() + 1) * dla.getPageSize(); i++) {
			newdt.insertRow(dt.getDataRow(i));
		}
		String SelectType = dla.getParam("SelectType");
		if (SelectType == null || "".equals(SelectType)) {
			SelectType = "checkbox";
		}
		newdt.insertColumn("alias", alias);
		newdt.insertColumn("selecttype", SelectType);
		dla.bindData(newdt);
	}

	public void add() {
		String name = $V("Name");
		String parentID = $V("ParentID");
		String DT = $V("DetailTemplate");
		String LT = $V("ListTemplate");
		String IT = $V("IndexTemplate");
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSchema pCatalog = new ZCCatalogSchema();

		catalog.setID(NoUtil.getMaxID("CatalogID"));
		catalog.setSiteID(Application.getCurrentSiteID());

		// parentID 为0表示上级节点是站点
		if (parentID.equals("0") || StringUtil.isEmpty(parentID)) {
			catalog.setParentID(0);
			catalog.setTreeLevel(1);
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(catalog.getSiteID());
			site.fill();
			site.setImageLibCount(site.getImageLibCount() + 1);
			trans.add(site, Transaction.UPDATE);
		} else {
			catalog.setParentID(Long.parseLong(parentID));
			pCatalog.setID(catalog.getParentID());
			pCatalog.fill();
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1);

			pCatalog.setChildCount(pCatalog.getChildCount() + 1);
			trans.add(pCatalog, Transaction.UPDATE);
		}

		String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
		catalog.setInnerCode(innerCode);

		catalog.setName(name);
		catalog.setURL("");
		String alias = ChineseSpelling.getFirstAlpha(name).toLowerCase();
		String existsName = Catalog.checkAliasExists(alias, catalog.getParentID());
		if (StringUtil.isNotEmpty(existsName)) {
			alias += NoUtil.getMaxID("AliasNo");
		}
		catalog.setAlias(alias);
		catalog.setType(Catalog.IMAGELIB);
		catalog.setIndexTemplate(IT);
		catalog.setListTemplate(LT);
		catalog.setListNameRule("");
		catalog.setDetailTemplate(DT);
		catalog.setDetailNameRule("");
		catalog.setChildCount(0);
		catalog.setIsLeaf(1);
		catalog.setTotal(0);
		catalog.setOrderFlag(Catalog.getCatalogOrderFlag(parentID, catalog.getType() + ""));
		catalog.setLogo("");
		catalog.setListPageSize(10);
		catalog.setListPage(-1);
		catalog.setPublishFlag("Y");
		catalog.setHitCount(0);
		catalog.setMeta_Keywords("");
		catalog.setMeta_Description("");
		catalog.setOrderColumn("");
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);

		//add by liuc 20130705在【媒体库->图片库->新建分类】新建完栏目后，
		//需要程序自动在以下【系统管理->用户管理】为创建的用户添加对应图片权限，
		//分别为：图片栏目浏览、图片栏目管理、图片管理；
		if(User.getUserName()!=null && !User.getUserName().equals("") && !User.getUserName().equals(UserList.ADMINISTRATOR)) {
			Transaction transnew=new Transaction();
			
			try {
				//schema
				ZDPrivilegeSet mZDPrivilegeSet=new ZDPrivilegeSet();
				ZDPrivilegeSchema mZDPrivilegeSchema=new ZDPrivilegeSchema();
				
				Set set = Priv.IMAGE_MAP.keySet();
				for (Iterator iter = set.iterator(); iter.hasNext();) {
					String code = (String) iter.next();
					mZDPrivilegeSchema=new ZDPrivilegeSchema();
					mZDPrivilegeSchema.setOwnerType("U");
					mZDPrivilegeSchema.setOwner(User.getUserName());
					mZDPrivilegeSchema.setPrivType(Priv.IMAGE); 
					mZDPrivilegeSchema.setID(catalog.getInnerCode());   	    
					mZDPrivilegeSchema.setCode(code);   
					mZDPrivilegeSchema.setValue("1");     
					mZDPrivilegeSet.add(mZDPrivilegeSchema);
				}
				
				transnew.add(mZDPrivilegeSet, Transaction.INSERT);
				transnew.commit();
			} catch (Exception e) {
				logger.error("在ImageLib.java类中向ZDPrivilege放值时出现异常信息" + e.getMessage(), e);
			}
			Priv.updateAllPriv(User.getUserName());
		}
		
		//add by liuc 
		
		// 初始化栏目发布设置
		Catalog.initCatalogConfig(catalog, trans);

		if (trans.commit()) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEIMAGE, "新建图片分类:" + catalog.getName() + "成功", Request.getClientIP());
			this.Response.setLogInfo(1, "新建图片分类成功!");
			CatalogUtil.update(catalog.getID());
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEIMAGE, "新建图片分类:" + catalog.getName() + "失败", Request.getClientIP());
			this.Response.setLogInfo(0, "新建图片分类失败!");
		}
	}

	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEIMAGE, "发布图片失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
			return;
		}
		ZCImageSchema image = new ZCImageSchema();
		ZCImageSet set = image.query(new QueryBuilder("where id in(" + ids + ")"));
		/*
		 * StringBuffer logs = new StringBuffer("发布图片:"); for (int i = 0; i <
		 * set.size(); i++) { logs.append(set.get(i).getName()+","); }
		 * UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEIMAGE,
		 * logs+"成功", Request.getClientIP());
		 */
		Response.setStatus(1);
		Publisher p = new Publisher();
		long id = p.publishSetTask("Image", set);
		$S("TaskID", "" + id);
	}
}
