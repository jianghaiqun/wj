package com.sinosoft.platform;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.PlatformCache;
import com.sinosoft.schema.ZDPrivilegeSchema;
import com.sinosoft.schema.ZDRoleSchema;
import com.sinosoft.schema.ZDUserRoleSchema;
import com.sinosoft.schema.ZDUserRoleSet;

import java.util.Date;

public class RoleTabBasic extends Page {

	/**
	 * 初始化获取角色信息
	 */
	public static Mapx init(Mapx params) {
		String RoleCode = params.getString("RoleCode");
		if (RoleCode == null || "".equals(RoleCode)) {
			RoleCode = params.getString("Cookie.Role.LastRoleCode");
			if (RoleCode == null || "".equals(RoleCode)) {
				return params;
			}
		}
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(RoleCode);
		if (!role.fill()) {
			logger.warn("查询不到该角色！！！");
			return params;
		}
		Mapx map = role.toMapx();
		map.put("BranchName", new QueryBuilder("select name from zdbranch where BranchInnerCode=? Order by OrderFlag",
				role.getBranchInnerCode()).executeString());
		return map;
	}

	/**
	 * 初始化修改对话框信息
	 */
	public static Mapx initEditDialog(Mapx params) {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(params.get("RoleCode").toString());
		if (!role.fill()) {
			logger.info("没有查询到该角色！！！");
			return params;
		}
		Mapx map = role.toMapx();
		return map;
	}

	/**
	 * 初始化添加角色对话框信息
	 */
	public static Mapx initDialog(Mapx params) {
		params.put("BranchInnerCode", PubFun.getBranchOptions());
		return params;
	}

	/**
	 * 添加角色
	 */
	public void add() {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setValue(Request);
		role.setRoleCode(role.getRoleCode().toLowerCase());
		if (role.fill()) {
			Response.setLogInfo(0, "角色编码" + role.getRoleCode() + "已经存在了，请选择另外的角色编码！");
			return;
		}
		Date currentDate = new Date();
		String currentUserName = User.getUserName();
		role.setAddTime(currentDate);
		role.setAddUser(currentUserName);

		Transaction trans = new Transaction();
		trans.add(role, Transaction.INSERT);

		if (trans.commit()) {
			RolePriv.updateAllPriv(role.getRoleCode());
			CacheManager.set(PlatformCache.ProviderName, "Role", role.getRoleCode(), role);
			Response.setLogInfo(1, "新建成功");
		} else {
			Response.setLogInfo(0, "新建失败");
		}
	}

	/**
	 * 保存修改信息
	 */
	public void save() {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode($V("RoleCode"));
		role.fill();

		role.setValue(Request);
		role.setModifyTime(new Date());
		role.setModifyUser(User.getUserName());

		if (role.update()) {
			CacheManager.set(PlatformCache.ProviderName, "Role", role.getRoleCode(), role);
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改失败");
		}
	}

	/**
	 * 删除角色时需要做的步骤 更新机构中的角色数 删除用户与角色的关系 删除这个角色的所有权限记录 更新这个角色下的所有用户的权限记录
	 */
	public void del() {
		String RoleCode = Request.getString("RoleCode");
		Transaction trans = new Transaction();
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(RoleCode);
		role.fill();
		if (Role.EVERYONE.equalsIgnoreCase(RoleCode)) {
			Response.setLogInfo(0, Role.EVERYONE + "为系统自带的角色，不能删除！");
			UserLog.log(UserLog.USER, UserLog.USER_DELROLE, "删除角色:" + role.getRoleName() + "失败", Request.getClientIP());
			return;
		}
		if ("admin".equalsIgnoreCase(RoleCode)) {
			Response.setLogInfo(0, "admin" + "为系统自带的角色，不能删除！");
			UserLog.log(UserLog.USER, UserLog.USER_DELROLE, "删除角色:" + role.getRoleName() + "失败", Request.getClientIP());
			return;
		}
		// 删除角色
		trans.add(role, Transaction.DELETE_AND_BACKUP);

		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		ZDUserRoleSet userRoleSet = userRole.query(new QueryBuilder("where RoleCode =?", RoleCode));
		// 删除角色与用户的关系
		trans.add(userRoleSet, Transaction.DELETE_AND_BACKUP);

		// 删除角色的权限
		trans.add(new ZDPrivilegeSchema().query(new QueryBuilder("where OwnerType=? and Owner=?",
				RolePriv.OWNERTYPE_ROLE, RoleCode)), Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			PlatformCache.removeRole(role.getRoleCode());

			RolePriv.updateAllPriv(RoleCode);
			UserLog.log(UserLog.USER, UserLog.USER_DELROLE, "删除角色:" + role.getRoleName() + "成功", Request.getClientIP());
			Response.setLogInfo(1, "删除成功!");
		} else {
			UserLog.log(UserLog.USER, UserLog.USER_DELROLE, "删除角色:" + role.getRoleName() + "失败", Request.getClientIP());
			Response.setLogInfo(0, "删除失败!");
		}
	}

	/**
	 * 显示选中角色下的所有用户信息
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String RoleCode = dga.getParam("RoleCode");
		if (RoleCode == null || "".equals(RoleCode)) {
			RoleCode = dga.getParams().getString("Cookie.Role.LastRoleCode");
			if (RoleCode == null || "".equals(RoleCode)) {
				dga.bindData(new DataTable());
				return;
			}
		}
		QueryBuilder qb = new QueryBuilder(
				"select * from ZDUser where exists (select UserName from ZDUserRole b where b.UserName = ZDUser.UserName and b.RoleCode=?)",
				RoleCode);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("RoleNames");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "RoleNames", PubFun.getRoleNames(PubFun.getRoleCodesByUserName(dt.getString(i, "UserName"))));
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 获取该角色下的所有用户
	 */
	public static void bindUserList(DataGridAction dga) {
		String roleCode = dga.getParam("RoleCode");
		String searchUserName = dga.getParam("SearchUserName");
		String branchinnercode = User.getBranchInnerCode();
		if(!"admin".equals(User.getUserName())){
			if(branchinnercode.length()>18){
				branchinnercode=branchinnercode.substring(0,18);
			}
		}
	
		
		QueryBuilder qb = new QueryBuilder("select * from ZDUser");
		qb.append(" where BranchInnerCode like ?", branchinnercode + "%");
		qb.append(" and not exists (select '' from zduserrole where zduserrole.roleCode=?"
				+ " and zduserrole.userName=zduser.userName)", roleCode);
		if (StringUtil.isNotEmpty(searchUserName)) {
			qb.append(" and (UserName like ?", "%" + searchUserName.trim() + "%");
			// 查询真实姓名
			qb.append(" or realname like ?)", "%" + searchUserName.trim() + "%");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Status", UserList.STATUS_MAP);
		dga.bindData(dt);
	}

	/**
	 * 添加用户到一个角色中
	 * 
	 */
	public void addUserToRole() {
		String RoleCode = $V("RoleCode");
		if (StringUtil.isEmpty(RoleCode)) {
			Response.setLogInfo(0, "角色不能为空");
			return;
		}
		String UserNameStr = $V("UserNames");
		String[] UserNames = UserNameStr.split(",");
		Date currentDate = new Date();
		String currentUserName = User.getUserName();
		Transaction trans = new Transaction();

		ZDUserRoleSet set = new ZDUserRoleSet();
		for (int i = 0; i < UserNames.length; i++) {
			if (StringUtil.isEmpty(UserNames[i])) {
				continue;
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(UserNames[i]);
			userRole.setRoleCode(RoleCode);
			userRole.setAddTime(currentDate);
			userRole.setAddUser(currentUserName);
			set.add(userRole);
		}
		trans.add(set, Transaction.INSERT);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				PlatformCache.addUserRole(set.get(i).getUserName(), set.get(i).getRoleCode());
			}
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "添加失败");
		}
	}

	/**
	 * 从角色中删除用户
	 * 
	 */
	public void delUserFromRole() {
		String RoleCode = $V("RoleCode");
		String UserNameStr = $V("UserNames");
		String[] UserNames = UserNameStr.split(",");
		Transaction trans = new Transaction();

		ZDUserRoleSet set = new ZDUserRoleSet();
		for (int i = 0; i < UserNames.length; i++) {
			DataTable dt = new QueryBuilder("select RoleCode from ZDUserRole where UserName=? and RoleCode!=?",
					UserNames[i], RoleCode).executeDataTable();
			String[] RoleCodes = new String[dt.getRowCount()];
			for (int j = 0; j < dt.getRowCount(); j++) {
				RoleCodes[j] = dt.getString(j, 0);
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(UserNames[i]);
			userRole.setRoleCode(RoleCode);
			userRole.fill();
			set.add(userRole);
		}
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				PlatformCache.removeUserRole(set.get(i).getUserName(), set.get(i).getRoleCode());
			}
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

}
