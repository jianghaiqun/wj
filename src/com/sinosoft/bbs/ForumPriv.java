package com.sinosoft.bbs;

import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.utility.CaseIgnoreMapx;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserList;
import com.sinosoft.schema.ZCForumGroupSet;
import com.sinosoft.schema.ZCForumMemberSchema;

public class ForumPriv {
	public static final String NO = "N";
	public static final String YES = "Y";

	Mapx map = new CaseIgnoreMapx();// 不需要区分大小写，Oracle下字段名全是大写

	public ForumPriv(long SiteID) {
		this(User.getUserName(), SiteID, null);
	}

	public ForumPriv(String SiteID) {
		this(User.getUserName(), SiteID, null);
	}

	public ForumPriv(String SiteID, String ForumID) {
		this(User.getUserName(), SiteID, ForumID);
	}

	public ForumPriv(String UserName, String SiteID, String ForumID) {
		cleanPriv();
		initPriv(UserName, SiteID, ForumID);
	}

	public ForumPriv(String UserName, long SiteID, String ForumID) {
		cleanPriv();
		initPriv(UserName, SiteID, ForumID);
	}

	/**
	 * 根据传入值来判断用户是否具有相应权限,整个论坛的所有权限都可以通过该方法得到
	 * 
	 * @author wst
	 * @param PrivType
	 * @return
	 */
	public boolean hasPriv(String PrivType) {
		return YES.equals(map.get(PrivType));
	}

	private void initPriv(String UserName, String SiteID, String ForumID) {
		initPriv(UserName, Long.parseLong(SiteID), ForumID);
	}

	private void cleanPriv() {
		map.clear();
	}

	private void initPriv(String UserName, long SiteID, String ForumID) {
		if (!ForumUtil.isInitDB(SiteID + "")) {
			cleanPriv();
			return;
		}
		ZCForumGroupSet set = new ZCForumGroupSet();
		if (StringUtil.isNotEmpty(UserName)) {
			ZCForumMemberSchema member = new ZCForumMemberSchema();
			member.setUserName(UserName);
			member.fill();
			if (StringUtil.isNotEmpty(ForumID)) {
				String systemName = ForumCache.getGroup(member.getAdminID()).getSystemName();
				if (StringUtil.isNotEmpty(systemName) && systemName.equals("版主")) {
					String[] forumAdmins = ForumUtil.getAdmins(ForumID);
					for (int i = 0; i < forumAdmins.length; i++) {
						if (forumAdmins[i].equals(User.getUserName())) {
							break;
						}
						if (i == forumAdmins.length - 1) {
							member.setAdminID(0);
						}
					}
				}
			}
			if (member.getUserName().equals(UserList.ADMINISTRATOR)) {
				long groupID = ForumCache.getGroupBySystemName("" + SiteID, "系统管理员").getID();
				member.setAdminID(groupID);
			}
			long id = ForumCache.getGroupBySystemName("" + SiteID, "禁止访问").getID();
			if (member.getAdminID() == id) {
				member.setUserGroupID(0);
				member.setDefinedID(0);
			}
			String ids = member.getUserGroupID() + "," + member.getAdminID() + "," + member.getDefinedID();
			String[] arr = ids.split(",");
			for (int i = 0; i < arr.length; i++) {
				if (StringUtil.isNotEmpty(arr[i].trim()) && !arr[i].trim().equals("0")) {
					set.add(ForumCache.getGroup(arr[i].trim()));
				}
			}
		} else {
			set.add(ForumCache.getGroupBySystemName(String.valueOf(SiteID), "游客"));
		}
		DataTable dt = set.toDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < dt.getColCount(); j++) {
				String k = dt.getDataColumn(j).getColumnName();
				if (!hasPriv(k) && YES.equals(dt.getString(i, j))) {
					map.put(k, YES);
				}
			}
		}
		isAdmin();
		if (StringUtil.isNotEmpty(User.getUserName()) && User.getUserName().equals(UserList.ADMINISTRATOR)) {
			map.put("Admin", NO);
		}
	}

	private void isAdmin() {
		if (YES.equals(map.get("AllowEditUser")) || YES.equals(map.get("AllowForbidUser"))
				|| YES.equals(map.get("AllowEditForum")) || YES.equals(map.get("AllowVerfyPost"))) {
			map.put("Admin", YES);
		} else {
			map.put("Admin", NO);
		}
	}
}
