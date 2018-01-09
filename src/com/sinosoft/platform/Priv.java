package com.sinosoft.platform;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

/**
 * 公用权限类：用户的权限常驻内存
 * 
 */

public class Priv {
	// 菜单权限
	public final static String MENU = "menu";

	public final static String MENU_BROWSE = "menu_browse";

	public final static Mapx MENU_MAP = new Mapx();

	static {
		MENU_MAP.put(MENU_BROWSE, "菜单浏览");
	}

	// 站点权限
	public final static String SITE = "site";

	public final static String SITE_BROWSE = "site_browse";

	public final static String SITE_MANAGE = "site_manage";

	public final static Mapx SITE_MAP = new Mapx();

	static {
		SITE_MAP.put(SITE_BROWSE, "站点浏览");
		SITE_MAP.put(SITE_MANAGE, "站点管理");
	}

	// 文章权限
	public final static String ARTICLE = "article";

	public final static String ARTICLE_BROWSE = "article_browse";

	public final static String ARTICLE_MANAGE = "article_manage";

	public final static String ARTICLE_MODIFY = "article_modify";

	public final static String ARTICLE_AUDIT = "article_audit";

	public final static Mapx ARTICLE_MAP = new Mapx();

	static {
		ARTICLE_MAP.put(ARTICLE_BROWSE, "文章栏目浏览");
		ARTICLE_MAP.put(ARTICLE_MANAGE, "文章栏目管理");
		ARTICLE_MAP.put(ARTICLE_MODIFY, "文章管理");
		ARTICLE_MAP.put(ARTICLE_AUDIT, "文章审核");
	}

	// 图片权限
	public final static String IMAGE = "image";

	public final static String IMAGE_BROWSE = "image_browse";

	public final static String IMAGE_MANAGE = "image_manage";

	public final static String IMAGE_MODIFY = "image_modify";

	public final static Mapx IMAGE_MAP = new Mapx();

	static {
		IMAGE_MAP.put(IMAGE_BROWSE, "图片栏目浏览");
		IMAGE_MAP.put(IMAGE_MANAGE, "图片栏目管理");
		IMAGE_MAP.put(IMAGE_MODIFY, "图片管理");
	}

	// 视频权限
	public final static String VIDEO = "video";

	public final static String VIDEO_BROWSE = "video_browse";

	public final static String VIDEO_MANAGE = "video_manage";

	public final static String VIDEO_MODIFY = "video_modify";

	public final static Mapx VIDEO_MAP = new Mapx();

	static {
		VIDEO_MAP.put(VIDEO_BROWSE, "视频栏目浏览");
		VIDEO_MAP.put(VIDEO_MANAGE, "视频栏目管理");
		VIDEO_MAP.put(VIDEO_MODIFY, "视频管理");
	}

	// 音频权限
	public final static String AUDIO = "audio";

	public final static String AUDIO_BROWSE = "audio_browse";

	public final static String AUDIO_MANAGE = "audio_manage";

	public final static String AUDIO_MODIFY = "audio_modify";

	public final static Mapx AUDIO_MAP = new Mapx();

	static {
		AUDIO_MAP.put(AUDIO_BROWSE, "音频栏目浏览");
		AUDIO_MAP.put(AUDIO_MANAGE, "音频栏目管理");
		AUDIO_MAP.put(AUDIO_MODIFY, "音频管理");
	}
	// 附件权限
	public final static String ATTACH = "attach";

	public final static String ATTACH_BROWSE = "attach_browse";

	public final static String ATTACH_MANAGE = "attach_manage";

	public final static String ATTACH_MODIFY = "attach_modify";

	public final static Mapx ATTACH_MAP = new Mapx();

	static {
		ATTACH_MAP.put(ATTACH_BROWSE, "附件栏目浏览");
		ATTACH_MAP.put(ATTACH_MANAGE, "附件栏目管理");
		ATTACH_MAP.put(ATTACH_MODIFY, "附件管理");
	}
	
	// 按钮权限
		public final static String BUTTON = "button";
		public final static String BUTTON_BROWSE = "button_browse";
		
		public final static Mapx BUTTON_MAP = new Mapx();

		static {
			BUTTON_MAP.put(BUTTON_BROWSE, "按钮浏览");
		}

		// 所有权限分类，必须把权限分类注册到PRIV_MAP
		public final static Mapx PRIV_MAP = new Mapx();

		static {
			PRIV_MAP.put(MENU, MENU_MAP);
			PRIV_MAP.put(SITE, SITE_MAP);
			PRIV_MAP.put(ARTICLE, ARTICLE_MAP);
			PRIV_MAP.put(IMAGE, IMAGE_MAP);
			PRIV_MAP.put(VIDEO, VIDEO_MAP);
			PRIV_MAP.put(AUDIO, AUDIO_MAP);
			PRIV_MAP.put(ATTACH, ATTACH_MAP);
			PRIV_MAP.put(BUTTON, BUTTON_MAP);
		}

	/**
	 * 权限所有者类别-用户
	 */
	public static final String OWNERTYPE_USER = "U";

	private static Map UserPrivMap = new Hashtable();

	public static void updateAllPriv(String UserName) {
		Object obj = new QueryBuilder("select UserName from ZDUser where UserName=?", UserName).executeOneValue();
		if (obj == null) {
			UserPrivMap.remove(UserName);
			return;
		}
		Object[] ks = PRIV_MAP.keyArray();
		for (int i = 0; i < PRIV_MAP.size(); i++) {
			updatePriv(UserName, (String) ks[i]);
		}
	}

	public static void updatePriv(String UserName, String PrivType) {
		String sql = "select ID,Code,Value from ZDPrivilege where OwnerType=? and Owner=? and PrivType=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(Priv.OWNERTYPE_USER);
		qb.add(UserName);
		qb.add(PrivType);
		DataTable dt = qb.executeDataTable();
		Map PrivTypeMap = getPrivTypeMap(UserName, PrivType);
		RolePriv.getMapFromDataTable((Map) PrivTypeMap, dt);
	}

	private static Map getPrivTypeMap(String UserName, String PrivType) {
		Map UserNamePrivMap = (Map) UserPrivMap.get(UserName); // 一个用户下的所有权限
		if (UserNamePrivMap == null) {
			UserNamePrivMap = new Hashtable();
			UserPrivMap.put(UserName, UserNamePrivMap);
			updateAllPriv(UserName);
		}
		Map PrivTypeMap = (Map) UserNamePrivMap.get(PrivType);
		if (PrivTypeMap == null) {
			PrivTypeMap = new HashMap();
			UserNamePrivMap.put(PrivType, PrivTypeMap);
		}
		return PrivTypeMap;
	}

	public static boolean getPriv(String PrivType, String ID, String Code) {
		return getPriv(User.getUserName(), PrivType, ID, Code);
	}

	public static boolean getPriv(String UserName, String PrivType, String ID, String Code) {
		if (UserList.ADMINISTRATOR.equalsIgnoreCase(UserName)) {
			return true;
		}
		String value = getUserPriv(UserName, PrivType, ID, Code);
		if ("1".equals(value)) {
			return true;
		} else if ("-1".equals(value)) {
			return false;
		} else {
			List roleCodeList = PubFun.getRoleCodesByUserName(UserName);
			if (roleCodeList != null && roleCodeList.size() != 0) {
				return RolePriv.getRolePriv((String[]) roleCodeList.toArray(new String[roleCodeList.size()]), PrivType, ID, Code);
			} else {
				return false;
			}
		}
	}

	public static boolean getBrowsePriv(String UserName, String ID) {
		return getPriv(UserName, Priv.ARTICLE, ID, Priv.ARTICLE_BROWSE);
	}

	/**
	 * 返回用户的某种权限，当用户拥有单独的权限时直接返回0或1；如果没有单独的权限，是从角色继承的话就返回null、这样需要接着查找角色的权限
	 * 
	 * @param PrivType
	 * @param ID
	 * @param Code
	 * @return
	 */
	private static String getUserPriv(String UserName, String PrivType, String ID, String Code) {
		if (MENU.equals(PrivType)) {
			Map map = Priv.getPrivTypeMap(UserName, PrivType);
			map = (Map) map.get(ID);
			if (map != null) {
				return (String) map.get(Code);
			} else {
				return null;
			}
		} else if (SITE.equals(PrivType)) {
			Map map = Priv.getPrivTypeMap(UserName, PrivType);
			map = (Map) map.get(ID);
			if (map != null) {
				return (String) map.get(Code);
			} else {
				return null;
			}
		} else {
			Map map = Priv.getPrivTypeMap(UserName, PrivType);
			map = (Map) map.get(ID);
			if (map != null) {
				return (String) map.get(Code);
			}
			return null;
		}
	}

	public static DataTable getCatalogPrivDT(String userName, String siteID, String PrivType) {
		return getCatalogPrivDT(userName, siteID, PrivType, false);
	}

	public static DataTable getCatalogPrivDT(String userName, String siteID, String PrivType, boolean isWebMode) {
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + userName + "' as UserName");
		Object[] ks = ((Mapx) Priv.PRIV_MAP.get(PrivType)).keyArray();
		for (int i = 0; i < ((Mapx) Priv.PRIV_MAP.get(PrivType)).size(); i++) {
			sb.append(",'' as " + ks[i]);
		}

		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType" + sb.toString().replaceAll("''", "''") + ",'' as ParentInnerCode from ZCSite a where a.ID = ?";
		DataTable siteDT = new QueryBuilder(sql, Long.parseLong(siteID)).executeDataTable();
		if (siteDT.getRowCount() == 0) {
			return new DataTable();
		}

		// 用InnerCode使得查找上级权限的时候非常方便
		String catalogType = RoleTabCatalog.CatalogTypeMap.getString(PrivType);
		sql = "select InnerCode as ID,Name,TreeLevel ,'" + PrivType + "' as PrivType" + sb.toString()
				+ ", (select b.InnerCode from ZCCatalog b where a.parentid=b.id) as ParentInnerCode from ZCCatalog a where Type =" + catalogType + " and a.SiteID = ? order by orderflag,innercode ";
		DataTable catalogDT = new QueryBuilder(sql, Long.parseLong(siteID)).executeDataTable();

		DataRow dr = null;
		String value = "1";
		if (isWebMode) {
			value = "√";
		}
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, Priv.getPriv(userName, Priv.SITE, dr.getString("ID"), columnName) ? value : "");
				}
			}
		}
		for (int i = 0; i < catalogDT.getRowCount(); i++) {
			dr = catalogDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, Priv.getPriv(userName, PrivType, dr.getString("ID"), columnName) ? value : "");
				}
			}
		}
		catalogDT.insertRow(siteDT.getDataRow(0), 0);
		return catalogDT;
	}

	public static DataTable getSitePrivDT(String userName, String siteID, String PrivType) {
		String s = "";
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + userName + "' as UserName");
		Object[] ks = Priv.SITE_MAP.keyArray();
		for (int i = 0; i < Priv.SITE_MAP.size(); i++) {
			sb.append(",'" + s + "' as " + ks[i].toString());
		}
		String sql = "select ID,Name,0 as TreeLevel ,'" + Priv.SITE + "' as PrivType " + sb.toString() + " from ZCSite a where id = ? order by orderflag ,id";
		DataTable siteDT = new QueryBuilder(sql, Long.parseLong(siteID)).executeDataTable();
		DataRow dr = null;
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName().toLowerCase().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, Priv.getPriv(userName, PrivType, dr.getString("ID"), columnName) ? "1" : "");
				}
			}
		}
		return siteDT;
	}

	public static boolean isValidURL(String URL) {
		if (StringUtil.isNotEmpty(URL)) {
			URL = URL.replaceAll("/+", "/");
			if (URL.startsWith("/")) {
				URL = URL.substring(1);
			}
		}
		if (!Config.isInstalled) {
			return true;
		}
		String menuID = Menu.MenuCacheMap.getString(URL);
		if (StringUtil.isNotEmpty(menuID) && !Priv.getPriv(User.getUserName(), Priv.MENU, Application.getCurrentSiteID() + "-" + menuID, Priv.MENU_BROWSE)) {
			return false;
		}
		return true;
	}
}
