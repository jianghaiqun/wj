package com.sinosoft.platform.pub;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.ObjectUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.service.AdminUserName;
import com.sinosoft.schema.ZDBranchSchema;
import com.sinosoft.schema.ZDRoleSchema;
import com.sinosoft.schema.ZDUserSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class PlatformUtil {
	private static final Logger logger = LoggerFactory.getLogger(PlatformUtil.class);

	public static final String INDENT = "　";
	public static final String SEPARATE = "|";
	public static DataTable District = null;

	private static Mapx<String, String> DistrictMap = null;

	private static Object mutex = new Object();

	private static long LastUpdateTime = 0L;

	private static long RefreshPeriod = 60000L;

	public static String getFileIcon(String path) {
		if (path.endsWith("/")) {
			return "Framework/Images/FileType/folder.gif";
		}
		if (path.endsWith(".template.html")) {
			return "Icons/icon_template.gif";
		}
		if (path.indexOf(".") > 0) {
			String ext = path.substring(path.lastIndexOf(".") + 1);
			if ((ext.equals("htm")) || (ext.equals("shtml"))) {
				ext = "html";
			}
			if (ext.equals("jpeg")) {
				ext = "jpg";
			}
			if (ext.equals("pptx")) {
				ext = "ppt";
			}
			if (ext.equals("xlsx")) {
				ext = "xls";
			}
			if (ext.equals("jar")) {
				ext = "zip";
			}
			if (ext.equals("rar")) {
				ext = "zip";
			}
			if (ObjectUtil.in(new Object[] { ext, "", "asp", "aspx", "avi", "bmp", "doc", "docx", "exe", "fla", "flv", "folder", "gif", "html", "jpg", "js", "jsp", "mdb", "mov", "mp3", "mp4", "pdf",
					"php", "png", "ppt", "rar", "rm", "swf", "txt", "wmp", "wmv", "xls", "zip" })) {
				return "Framework/Images/FileType/" + ext + ".gif";
			}
		}

		return "Framework/Images/FileType/unknown.gif";
	}

	public static String getUserRealName(String userName) {
		if (ObjectUtil.empty(userName)) {
			return "";
		}
		ZDUserSchema user = PlatformCache.getUser(userName);
		if (user == null) {
			return "";
		}
		return user.getRealName();
	}

	public static List<String> getRoleCodesByUserName(String userName) {
		String roles = (String) CacheManager.get("Platform", "UserRole", userName);
		if (roles == null) {
			return null;
		}
		String[] arr = roles.split(",");
		ArrayList list = new ArrayList();
		for (int i = 0; i < arr.length; i++) {
			if (StringUtil.isNotEmpty(arr[i])) {
				list.add(arr[i]);
			}
		}
		return list;
	}

	public static String getRoleName(String roleCode) {
		ZDRoleSchema role = (ZDRoleSchema) CacheManager.get("Platform", "Role", roleCode);
		if (role == null) {
			return null;
		}
		return role.getRoleName();
	}

	public static ZDBranchSchema getBranch(String innerCode) {
		return PlatformCache.getBranch(innerCode);
	}

	public static String getBranchName(String innerCode) {
		ZDBranchSchema branch = PlatformCache.getBranch(innerCode);
		if (branch == null) {
			branch = PlatformCache.getBranch("0001");
		}
		if (branch != null) {
			return branch.getName();
		}
		return null;
	}

	public static String getRoleNames(List<String> roleCodes) {
		if ((roleCodes == null) || (roleCodes.size() == 0)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		boolean first = false;
		for (int i = 0; i < roleCodes.size(); i++) {
			String roleName = getRoleName((String) roleCodes.get(i));
			if (StringUtil.isNotEmpty(roleName)) {
				if (first) {
					sb.append(",");
				}
				sb.append(roleName);
				first = true;
			}
		}
		return sb.toString();
	}

	public static void indentDataTable(DataTable dt) {
		indentDataTable(dt, 0, 2, 1);
	}

	public static void indentDataTable(DataTable dt, int n, int m, int firstLevel) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			int level = Integer.parseInt(dt.getString(i, m));
			StringBuffer sb = new StringBuffer();
			for (int j = firstLevel; j < level; j++) {
				sb.append("　");
			}
			dt.set(i, n, sb.toString() + dt.getString(i, n));
		}
	}

	public static void initDistrict() {
		District = new QueryBuilder("select Name,Code,TreeLevel,Type from zddistrict", new Object[0]).executeDataTable();
		Mapx map = new Mapx();
		for (int i = 0; i < District.getRowCount(); i++) {
			map.put(District.getString(i, 1), District.getString(i, 0));
		}
		DistrictMap = map;
	}

	public static Mapx<String, String> getDistrictMap() {
		if (DistrictMap == null) {
			synchronized (mutex) {
				if (DistrictMap == null) {
					initDistrict();
				}
			}
		}
		return DistrictMap;
	}

	public static DataTable getProvince() {
		if (District == null) {
			initDistrict();
		}
		return District.filter(new Filter<DataRow>() {
			public boolean filter(DataRow dr) {
				return "1".equals(dr.get("TreeLevel"));
			}
		});
	}

	public static void loadDBConfig() {
		synchronized (mutex) {
			LastUpdateTime = System.currentTimeMillis();
			if (Config.getMapx().containsKey("Database.Default.Type") && "true".equals(Config.getMapx().get("App.ExistPlatformDB")))
				try {
					DataTable dt = new QueryBuilder("select type code,value from zdconfig").executeDataTable();
					for (int i = 0; (dt != null) && (i < dt.getRowCount()); i++) {
						Config.getMapx().put(dt.getString(i, 0), dt.getString(i, 1));
					}
					Config.getMapx().put("AdminUserName", AdminUserName.getValue());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
		}
	}

	public static void refresh() {
		if (System.currentTimeMillis() - LastUpdateTime > RefreshPeriod)
			loadDBConfig();
	}
}