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
import com.sinosoft.framework.controls.DataGridAction;
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
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.schema.ZCVideoRelaSchema;
import com.sinosoft.schema.ZCVideoRelaSet;
import com.sinosoft.schema.ZCVideoSchema;
import com.sinosoft.schema.ZCVideoSet;
import com.sinosoft.schema.ZDPrivilegeSchema;
import com.sinosoft.schema.ZDPrivilegeSet;

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class VideoLib extends Page {

	public static Mapx initEditDialog(Mapx params) {
		long ID = Long.parseLong(params.get("ID").toString());
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		return catalog.toMapx();
	}  

	public void setVideoCover() {
		String ID = Request.get("ID").toString();
		String imagePath = "";
		DataTable dt = new QueryBuilder("select path,filename from ZCVideo where id=?", ID).executeDataTable();
		if (dt.getRowCount() > 0) {
			imagePath = dt.get(0, "path").toString() + dt.get(0, "filename").toString();
		}
		ZCVideoSchema video = new ZCVideoSchema();
		video.setID(Long.parseLong(ID));
		video.fill();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(video.getCatalogID());
		catalog.fill();
		catalog.setImagePath(imagePath);
		if (catalog.update()) {
			this.Response.setLogInfo(1, "设置专辑封面成功！");
		} else {
			this.Response.setLogInfo(0, "设置专辑封面失败！");
		}
	}

	public void setTopper() {
		String ID = Request.get("ID").toString();
		ZCVideoSchema video = new ZCVideoSchema();
		video.setID(Long.parseLong(ID));
		video.fill();
		QueryBuilder qb = new QueryBuilder("select max(OrderFlag) from ZCVideo where CatalogID = ?", video.getCatalogID());
		video.setOrderFlag(qb.executeInt() + 1);
		if (video.update()) {
			this.Response.setLogInfo(1, "置顶成功！");
		} else {
			this.Response.setLogInfo(0, "置顶失败！");
		}
	}

	public void VideoLibEdit() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setValue(this.Request);
		catalog.fill();
		catalog.setValue(this.Request);
		catalog.setAlias(StringUtil.getChineseFirstAlpha(catalog.getName()));
		if (catalog.update()) {
			CatalogUtil.update(catalog.getID());
			this.Response.setLogInfo(1, "修改视频分类成功！");
		} else {
			this.Response.setLogInfo(0, "修改视频分类失败！");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		long SiteID = Application.getCurrentSiteID();
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentLevel = params.getString("ParentLevel");
		String parentID = params.getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (StringUtil.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastVideoLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, CatalogShowConfig.getVideoLibShowLevel());

		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder("select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ?");
			qb.add(Catalog.VIDEOLIB);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			if (!CatalogShowConfig.isVideoLibLoadAllChild()) {
				qb.append(" and TreeLevel<?", Integer.parseInt(parentLevel) + 3);
				ta.setExpand(false);
			} else {
				ta.setExpand(true);
			}
			qb.append(" order by orderflag,innercode");
			dt = qb.executeDataTable();
		} else {
			ta.setLevel(CatalogShowConfig.getVideoLibShowLevel());
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");
			qb.add(Catalog.VIDEOLIB);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, Catalog.VIDEOLIB, SiteID, ta.getLevel());
		}
		ta.setRootText("视频库");
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), Priv.VIDEO, dr.getString("InnerCode"), Priv.VIDEO_BROWSE);
			}
		});
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	public static void dg1DataList(DataListAction dla) {
		String Alias = Config.getContextPath() + "/" + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
		Alias = Alias.replaceAll("///", "/");
		Alias = Alias.replaceAll("//", "/");
		String CatalogID = dla.getParams().getString("CatalogID");
		if (StringUtil.isEmpty(CatalogID)) {
			// CatalogID =
			// dla.getParams().getString("Cookie.Resource.LastVideoLib");
			// if (StringUtil.isEmpty(CatalogID) || "null".equals(CatalogID)) {
			// CatalogID = "0";
			// }
			// dla.getParams().put("CatalogID", CatalogID);
			dla.bindData(new DataTable());
			return;
		}
		String Name = dla.getParams().getString("Name");
		String StartDate = (String) dla.getParam("StartDate");
		String EndDate = (String) dla.getParam("EndDate");
		if (StringUtil.isEmpty(CatalogID) && StringUtil.isEmpty(Name)) {
			dla.setTotal(0);
			dla.bindData(new DataTable());
			return;
		}
		QueryBuilder qb = new QueryBuilder("select * from ZCVideo where 1=1");
		if (StringUtil.isNotEmpty(CatalogID)) {
			qb.append(" and CatalogID=?", Long.parseLong(CatalogID));
		} else {
			dla.setTotal(0);
			dla.bindData(new DataTable());
			return;
		}
		if (StringUtil.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (StringUtil.isNotEmpty(StartDate)) {
			qb.append(" and addtime>=? ", StartDate);
		}
		if (StringUtil.isNotEmpty(EndDate)) {
			qb.append(" and addtime<=? ", EndDate);
		}
		qb.append(" order by OrderFlag desc,ID desc");
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("Alias", Alias);
		dla.bindData(dt);
	}

	public static void dg1DataBindBrowse(DataGridAction dga) {
		String Search = dga.getParams().getString("Search");
		if (Search == null || "".equals(Search)) {
			dga.bindData(new DataTable());
			return;
		}

		String CatalogID = dga.getParams().getString("_CatalogID");
		String Name = dga.getParams().getString("Name");
		String Info = dga.getParams().getString("Info");
		QueryBuilder qb = new QueryBuilder("select * from ZCVideo");
		if (StringUtil.isEmpty(CatalogID)) {
			qb.append(" where SiteID =?", Application.getCurrentSiteID());
		} else {
			qb.append(" where CatalogID =?", Long.parseLong(CatalogID));
		}
		if (StringUtil.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (StringUtil.isNotEmpty(Info)) {
			qb.append(" and Info like ?", "%" + Info.trim() + "%");
		}
		qb.append(" order by ID desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public static void dg1DataListBrowse(DataGridAction dga) {
		String Search = dga.getParams().getString("Search");
		if (Search == null || "".equals(Search)) {
			dga.bindData(new DataTable());
			return;
		}

		String CatalogID = dga.getParams().getString("_CatalogID");
		String Name = dga.getParams().getString("Name");
		String Info = dga.getParams().getString("Info");
		QueryBuilder qb = new QueryBuilder("select * from ZCVideo");
		if (StringUtil.isEmpty(CatalogID)) {
			qb.append(" where SiteID =?", Application.getCurrentSiteID());
		} else {
			qb.append(" where CatalogID =?", CatalogID);
		}
		if (StringUtil.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (StringUtil.isNotEmpty(Info)) {
			qb.append(" and Info like ?", "%" + Info.trim() + "%");
		}
		qb.append(" order by ID desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZCVideoSet set = new ZCVideoSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCVideoSchema video = new ZCVideoSchema();
			video.setValue(dt.getDataRow(i));
			video.setModifyTime(new Date());
			video.setModifyUser(User.getUserName());
			set.add(video);
		}
		if (set.update()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	public void edit() {
		ZCVideoSchema Video = new ZCVideoSchema();
		Video.setValue(this.Request);
		Video.fill();
		Video.setValue(this.Request);
		if (Video.update()) {
			this.Response.setLogInfo(1, "修改视频成功");
		} else {
			this.Response.setLogInfo(0, "修改视频失败");
		}
	}

	public void add() {
		String name = $V("Name");
		String parentID = $V("ParentID");
		String IT = $V("IndexTemplate");
		String DT = $V("DetailTemplate");
		String LT = $V("ListTemplate");
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
		catalog.setURL(" ");
		String alias = ChineseSpelling.getFirstAlpha(name).toLowerCase();
		String existsName = Catalog.checkAliasExists(alias, catalog.getParentID());
		if (StringUtil.isNotEmpty(existsName)) {
			alias += NoUtil.getMaxID("AliasNo");
		}
		catalog.setAlias(alias);
		catalog.setType(Catalog.VIDEOLIB);
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
		catalog.setPublishFlag("Y");
		catalog.setHitCount(0);
		catalog.setMeta_Keywords("");
		catalog.setMeta_Description("");
		catalog.setOrderColumn("");
		catalog.setListPage(-1);
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, Transaction.INSERT);
		
		//add by liuc 20130705在【媒体库->视频库->新建分类】新建完栏目后，
		//需要程序自动在以下【系统管理->用户管理】为创建的用户添加对应图片权限，
		//分别为：视频栏目浏览、视频栏目管理、视频管理；
		if(User.getUserName()!=null && !User.getUserName().equals("") && !User.getUserName().equals(UserList.ADMINISTRATOR)) {
			Transaction transnew=new Transaction();
			
			try {
				//schema
				ZDPrivilegeSet mZDPrivilegeSet=new ZDPrivilegeSet();
				ZDPrivilegeSchema mZDPrivilegeSchema=new ZDPrivilegeSchema();
				
				Set set = Priv.VIDEO_MAP.keySet();
				for (Iterator iter = set.iterator(); iter.hasNext();) {
					String code = (String) iter.next();
					mZDPrivilegeSchema=new ZDPrivilegeSchema();
					mZDPrivilegeSchema.setOwnerType("U");
					mZDPrivilegeSchema.setOwner(User.getUserName());
					mZDPrivilegeSchema.setPrivType(Priv.VIDEO); 
					mZDPrivilegeSchema.setID(catalog.getInnerCode());   	    
					mZDPrivilegeSchema.setCode(code);   
					mZDPrivilegeSchema.setValue("1");     
					mZDPrivilegeSet.add(mZDPrivilegeSchema);
				}
				
				transnew.add(mZDPrivilegeSet, Transaction.INSERT);
				transnew.commit();
			} catch (Exception e) {
				logger.error("在VideoLib.java类中向ZDPrivilege放值时出现异常信息"+ e.getMessage(), e);
			}
			Priv.updateAllPriv(User.getUserName());
		}
		//add by liuc 
		
		// 初始化栏目发布设置
		Catalog.initCatalogConfig(catalog, trans);

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEVIDEO, "新建视频分类:" + catalog.getName() + " 成功", Request.getClientIP());
			this.Response.setLogInfo(1, "新建视频分类成功!");
		} else {
			UserLog.log(UserLog.RESOURCE, UserLog.RESOURCE_ADDTYPEVIDEO, "新建视频分类:" + catalog.getName() + " 失败", Request.getClientIP());
			this.Response.setLogInfo(0, "新建视频分类失败!");
		}
	}

	public void delLib() {
		String catalogID = $V("catalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong(catalogID));
		if (!catalog.fill()) {
			this.Response.setLogInfo(0, "没有视频分类！");
			return;
		}

		if ("N".equals(catalog.getProp4())) {
			this.Response.setLogInfo(0, "不能删除该视频分类！");
			return;
		}
		
		//add by liuc 20130705 如果登录的不是admin用户则删除的该截点的同时将该截点下该包含所有的not admin 用户全部删除
		if(User.getUserName()!=null && !User.getUserName().equals("") && !User.getUserName().equals(UserList.ADMINISTRATOR)) {
			Transaction transnew=new Transaction();
			
			try {
				//schema
				
				ZDPrivilegeSchema mZDPrivilegeSchema=new ZDPrivilegeSchema();
				ZDPrivilegeSet mZDPrivilegeSet=mZDPrivilegeSchema.query((new QueryBuilder(" where PrivType='"+Priv.VIDEO+"' and id like '"+catalog.getInnerCode()+"%' ")));
				transnew.add(mZDPrivilegeSet, Transaction.DELETE_AND_BACKUP);
				transnew.commit();
			} catch (Exception e) {
				logger.error("在VideoLib.java类中删除ZDPrivilege值时出现异常信息"+ e.getMessage(), e);
			}
			Priv.updateAllPriv(User.getUserName());
		}
		
		//add by liuc end

		ZCCatalogSet catalogSet = new ZCCatalogSchema().query(new QueryBuilder("where InnerCode like ?", catalog.getInnerCode() + "%"));
		Transaction trans = new Transaction();
		ZCVideoSet videoSet = new ZCVideoSchema().query(new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + "%"));
		for (int i = 0; i < videoSet.size(); i++) {
			FileUtil.delete(Config.getContextRealPath() + videoSet.get(i).getPath() + videoSet.get(i).getFileName());
			ZCVideoRelaSet VideoRelaSet = new ZCVideoRelaSchema().query(new QueryBuilder(" where id =?", videoSet.get(i).getID()));
			trans.add(VideoRelaSet, Transaction.DELETE_AND_BACKUP);
		}

		File file = new File(Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/upload/Video/"
				+ CatalogUtil.getPath(Long.parseLong(catalogID)));
		FileUtil.delete(file);

		trans.add(videoSet, Transaction.DELETE_AND_BACKUP);
		trans.add(catalogSet, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			CMSCache.removeCatalogSet(catalogSet);// 从缓存中剔除
			this.Response.setLogInfo(1, "删除视频分类成功！");

			// 删除发布文件，更新列表
			Publisher p = new Publisher();
			p.deleteFileTask(videoSet);

		} else {
			this.Response.setLogInfo(0, "删除视频频分类失败！");
		}
	}

	public void publish() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
			return;
		}
		ZCVideoSchema video = new ZCVideoSchema();
		ZCVideoSet set = video.query(new QueryBuilder("where id in(" + ids + ")"));

		Response.setStatus(1);
		Publisher p = new Publisher();
		long id = p.publishSetTask("Video", set);
		$S("TaskID", "" + id);
	}

}
