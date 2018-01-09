package com.sinosoft.platform;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * 公用权限类：权限常驻内存
 * 
 */

public class RolePriv {
	/**
	 * 权限所有者类型：角色
	 */
	public static final String OWNERTYPE_ROLE = "R";

	private static Map RolePrivMap = new Hashtable();

	public static void updateAllPriv(String RoleCode) {
		Object obj = new QueryBuilder("select RoleCode from ZDRole where RoleCode = ?", RoleCode).executeOneValue();
		if (obj == null) {
			RolePrivMap.remove(RoleCode);
			return;
		}
		Object[] ks = Priv.PRIV_MAP.keyArray();
		for (int i = 0; i < Priv.PRIV_MAP.size(); i++) {
			RolePriv.updatePriv(RoleCode, (String) ks[i]);
		}
	}

	public static void updatePriv(String RoleCode, String PrivType) {
		// 初始化用户在栏目上的权限
		String sql = "select ID,Code,Value from ZDPrivilege where OwnerType=? and Owner=? and PrivType=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(RolePriv.OWNERTYPE_ROLE);
		qb.add(RoleCode);
		qb.add(PrivType);
		DataTable dt = qb.executeDataTable();
		Map PrivTypeMap = getPrivTypeMap(RoleCode, PrivType);
		RolePriv.getMapFromDataTable((Map) PrivTypeMap, dt);
	}

	/**
	 * 返回一组角色的某个权限的交集或并集
	 * 
	 * @param RoleCodes
	 * @param PrivType
	 * @param ID
	 * @param Code
	 * @return
	 */
	public static boolean getRolePriv(String[] RoleCodes, String PrivType, String ID, String Code) {
		String value = null;
		if (Priv.MENU.equals(PrivType)) {
			for (int i = 0; i < RoleCodes.length; i++) {
				Map map = RolePriv.getPrivTypeMap(RoleCodes[i], PrivType);
				map = (Map) map.get(ID);
				if (map != null) {
					value = (String) map.get(Code);
					if ("1".equals(value)) {
						return true;
					}
				}
			}
			return false;
		} else if (Priv.SITE.equals(PrivType)) {
			for (int i = 0; i < RoleCodes.length; i++) {
				Map map = RolePriv.getPrivTypeMap(RoleCodes[i], PrivType);
				map = (Map) map.get(ID);
				if (map != null) {
					value = (String) map.get(Code);
					if ("1".equals(value)) {
						return true;
					}
				}
			}
			return false;
		} else {
			for (int i = 0; i < RoleCodes.length; i++) {
				Map map = RolePriv.getPrivTypeMap(RoleCodes[i], PrivType);
				map = (Map) map.get(ID);
				if (map != null) {
					value = (String) map.get(Code);
					if ("1".equals(value)) {
						return true;
					}
				}
			}
			if (value != null) {
				return "1".equals(value);
			}
			return false;
		}
	}

	public static Map getPrivTypeMap(String RoleCode, String PrivType) {
		Map RoleCodePrivMap = (Map) RolePrivMap.get(RoleCode); // 一个角色下的所有权限
		if (RoleCodePrivMap == null) {
			RoleCodePrivMap = new Hashtable();
			RolePrivMap.put(RoleCode, RoleCodePrivMap);
			updateAllPriv(RoleCode);
		}
		Map PrivTypeMap = (Map) RoleCodePrivMap.get(PrivType); // 角色下某种类型的权限
		if (PrivTypeMap == null) {
			PrivTypeMap = new HashMap();
			RoleCodePrivMap.put(PrivType, PrivTypeMap);
		}
		return PrivTypeMap;
	}

	public static void getMapFromDataTable(Map map, DataTable dt) {
		map.clear();
		if (dt == null || dt.getRowCount() <= 0) {
			return;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			Map childMap = (Map) map.get(dt.getString(i, "ID"));
			if (childMap == null) {
				childMap = new HashMap();
				map.put(dt.getString(i, "ID"), childMap);
			}
			childMap.put(dt.getString(i, "Code"), dt.getString(i, "Value"));
		}
	}
}
