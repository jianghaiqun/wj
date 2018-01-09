package com.sinosoft.platform;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataCollection;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.PlatformCache;
import com.sinosoft.schema.ZDPrivilegeSchema;
import com.sinosoft.schema.ZDUserRoleSchema;
import com.sinosoft.schema.ZDUserRoleSet;
import com.sinosoft.schema.ZDUserSchema;
import com.sinosoft.schema.ZDUserSet;

import java.util.Date;
import java.util.regex.Pattern;

public class UserList extends Page {

	public final static String ADMINISTRATOR = "admin";

	public final static String STATUS_NORMAL = "N";

	public final static String STATUS_STOP = "S";

	public final static Mapx STATUS_MAP = new Mapx();

	static {
		STATUS_MAP.put(STATUS_NORMAL, "正常");
		STATUS_MAP.put(STATUS_STOP, "停用");
	}

	public static Mapx init(Mapx params) {
		String newPassword = new QueryBuilder("select value from zdconfig where type='ResetPassword'").executeOneValue()  + "";
		params.put("IsBranchAdmin", HtmlUtil.codeToRadios("IsBranchAdmin", "YesOrNo", "N"));
		params.put("Status", HtmlUtil.mapxToRadios("Status", STATUS_MAP, "N"));
		params.put("BranchInnerCode", PubFun.getBranchOptions());
		params.put("NewPassword", newPassword);
		return params;
	}

	static String Password = "zvingzving";

	public static Mapx initEditDialog(Mapx params) {
		String userName = params.getString("UserName");
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(userName);
		user.fill();
		params = user.toMapx();
		params.put("IsBranchAdmin", HtmlUtil.codeToRadios("IsBranchAdmin", "YesOrNo", user.getIsBranchAdmin()));
		params.put("Status", HtmlUtil.mapxToRadios("Status", STATUS_MAP, user.getStatus()));
		params.put("BranchInnerCode", PubFun.getBranchOptions(user.getBranchInnerCode()));
		params.put("Password", Password);
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String searchUserName = (String) dga.getParam("SearchUserName");
		QueryBuilder qb = new QueryBuilder("select * from ZDUser ");
		// String useBranchCode=User.getBranchInnerCode();
		String useBranchCode = "0001";
		if (useBranchCode.length() >= 27) {
			useBranchCode = useBranchCode.substring(0, 27);
			if (useBranchCode.indexOf("120000095120000001") >= 0) {

			} else {
				useBranchCode = useBranchCode.substring(0, 18);
			}

		}
		qb.append(" where BranchInnerCode like ? ", useBranchCode + "%");
		if (StringUtil.isNotEmpty(searchUserName)) {
			qb.append(" and (UserName like ? ", "%" + searchUserName.trim() + "%");

			// 查询真实姓名
			qb.append(" or realname like ? )", "%" + searchUserName.trim() + "%");
		}
		qb.append(" order by AddTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("BranchInnerCode", new QueryBuilder("select BranchInnerCode,Name from ZDBranch").executeDataTable().toMapx(0, 1));
		dt.decodeColumn("Status", STATUS_MAP);
		dt.insertColumn("RoleNames");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "RoleNames", PubFun.getRoleNames(PubFun.getRoleCodesByUserName(dt.getString(i, "UserName"))));
		}
		dga.bindData(dt);
	}

	public static void initRoleTree(TreeAction ta) {
		/**
		 * DataTable dt = new QueryBuilder("select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName,'' as Checked from zdrole "
		 * ) .executeDataTable();
		 */
		// 2010/09/19 将上面注释的修改为下面四句，实现用户管理中新建用户时选择所属角色时只显示与当前用户同一机构的用户
		DataTable dt = null;
		String branchinnercode = User.getBranchInnerCode();
		if (!"admin".equals(User.getUserName())) {
			if (branchinnercode.length() >= 27) {
				branchinnercode = branchinnercode.substring(0, 27);
				if (branchinnercode.indexOf("120000095120000001") >= 0) {

				} else {
					branchinnercode = branchinnercode.substring(0, 18);
				}

			}
			dt = new QueryBuilder("select RoleCode,'' as ParentID,'1' " + "as TreeLevel,RoleName,'' as Checked from zdrole where branchinnercode=?", branchinnercode).executeDataTable();

		} else {
			dt = new QueryBuilder("select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName,'' as Checked from zdrole ").executeDataTable();
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Role.EVERYONE.equalsIgnoreCase(dt.getString(i, "RoleCode"))) {
				dt.set(i, "Checked", "Checked");
			}
		}
		ta.setRootText("角色");
		ta.setIdentifierColumnName("RoleCode");
		ta.bindData(dt);
	}

	public static void initEditRoleTree(TreeAction ta) {
		String userName = ta.getParam("UserName");
		// String branchinnercode = User.getBranchInnerCode();
		DataTable dt = null;
		if (!"admin".equals(User.getUserName())) {

			String branchinnercode = User.getBranchInnerCode();
			if (branchinnercode.length() >= 27) {
				branchinnercode = branchinnercode.substring(0, 27);
				if (branchinnercode.indexOf("120000095120000001") >= 0) {

				} else {
					branchinnercode = branchinnercode.substring(0, 18);
				}

			}
			dt = new QueryBuilder("select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName," + "(select 'Checked' from ZDUserRole b where b.RoleCode=ZDRole.RoleCode "
					+ "and UserName=?) as Checked from zdrole where branchinnercode='" + branchinnercode + "' and RoleCode not like '%_admin'  and RoleCode not like '%_everyone' ", userName)
					.executeDataTable();
		} else {
			dt = new QueryBuilder("select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName," + "(select 'Checked' from ZDUserRole b where b.RoleCode=ZDRole.RoleCode "
					+ "and UserName=?) as Checked from zdrole", userName).executeDataTable();
		}

		ta.setRootText("角色");
		ta.setIdentifierColumnName("RoleCode");
		ta.bindData(dt);
	}

	/**
	 * 添加用户
	 */
	public void add() {
		Transaction trans = new Transaction();
		if (!add(trans, Request)) {
			Response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "新建用户成功!");
			Priv.updateAllPriv($V("UserName"));
		} else {
			Response.setLogInfo(0, "新建用户失败!");
		}
	}

	private static Pattern userPattern = Pattern.compile("[\\w@\\.\u4e00-\u9fa5]{1,20}", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	/**
	 * 添加用户，供外部调用
	 * 
	 * @param trans
	 * @param dc
	 * @return
	 */
	public static boolean add(Transaction trans, DataCollection dc) {
		String userName = dc.getString("UserName");
		if (!userPattern.matcher(userName).matches()) {
			Errorx.addError("用户名最多20位，仅限英文字母，数字，汉字，半角“.”、“@”");
			return false;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setValue(dc);
		user.setUserName(user.getUserName().toLowerCase());
		if (user.fill()) {
			Errorx.addError(dc.getString("UserName") + "用户已经存在!");
			return false;
		}

		user.setPassword(StringUtil.md5Hex(dc.getString("Password")));
		if (dc.getString("Type") == null || "".equals(dc.getString("Type"))) {
			user.setType("0");
		} else {
			user.setType(dc.getString("Type"));
		}
		user.setProp1(dc.getString("Prop1"));
		user.setAddTime(new Date());
		user.setAddUser(User.getUserName());
		trans.add(user, Transaction.INSERT);

		// 角色
		String roleCodes = dc.getString("RoleCode");
		if (StringUtil.isEmpty(roleCodes)) {
			return true;
		}
		String[] RoleCodes = roleCodes.split(",");
		String currentUserName = User.getUserName();

		for (int i = 0; i < RoleCodes.length; i++) {
			if (StringUtil.isEmpty(RoleCodes[i]) || StringUtil.isEmpty(user.getUserName())) {
				continue;
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode(RoleCodes[i]);
			userRole.setAddTime(new Date());
			userRole.setAddUser(currentUserName);
			trans.add(userRole, Transaction.INSERT);
		}
		return true;
	}

	public void save() {
		Transaction trans = new Transaction();
		if (!save(trans, Request)) {
			return;
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功");
			Priv.updateAllPriv($V("UserName"));
		} else {
			Response.setLogInfo(0, "修改失败");
		}
	}

	public boolean save(Transaction trans, DataCollection dc) {
		ZDUserSchema user = new ZDUserSchema();
		String newPassword = dc.getString("NewPassword");
		String newConfirmPassword = dc.getString("NewConfirmPassword");
		if (!newPassword.equals(newConfirmPassword)) {
			Response.setLogInfo(0, "密码不一致!");
			return false;
		}
		user.setUserName(dc.getString("UserName"));
		if (!user.fill()) {
			return false;
		}
		user.setValue(dc);
		if (ADMINISTRATOR.equalsIgnoreCase(user.getUserName()) && STATUS_STOP.equalsIgnoreCase(user.getStatus())) {
			Response.setLogInfo(0, ADMINISTRATOR + "为系统自带的用户，拥有最高管理权限，不能停用!");
			return false;
		}
		user.setModifyTime(new Date());
		user.setModifyUser(User.getUserName());
		if (StringUtil.isNotEmpty(newPassword) && !Password.equals(newPassword)) {
			user.setPassword(StringUtil.md5Hex(newPassword));
		}
		trans.add(user, Transaction.UPDATE);

		// 角色
		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		userRole.setUserName(user.getUserName());
		trans.add(userRole.query(), Transaction.DELETE_AND_BACKUP);

		String roleCodes = dc.getString("RoleCode");
		if (StringUtil.isEmpty(roleCodes)) {
			return true;
		}
		String[] RoleCodes = roleCodes.split(",");
		String currentUserName = User.getUserName();

		CacheManager.set(PlatformCache.ProviderName, "User", user.getUserName(), user);
		CacheManager.set(PlatformCache.ProviderName, "UserRole", user.getUserName(), roleCodes);

		for (int i = 0; i < RoleCodes.length; i++) {
			if (StringUtil.isEmpty(RoleCodes[i]) || StringUtil.isEmpty(user.getUserName())) {
				continue;
			}
			userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode(RoleCodes[i]);
			userRole.setAddTime(new Date());
			userRole.setAddUser(currentUserName);
			trans.add(userRole, Transaction.INSERT);

		}
		return true;
	}

	private static Pattern idPattern = Pattern.compile("[\\w@\\.\\,\u4e00-\u9fa5]*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	public void del() {
		String UserNames = $V("UserNames");
		if (!idPattern.matcher(UserNames).matches()) {
			Response.setLogInfo(0, "传入用户名称时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		if (!del(trans, Request)) {
			Response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			UserLog.log("User", "DelUser", "删除用户:" + UserNames + "成功", Request.getClientIP());
			Response.setLogInfo(1, "删除用户成功!");
		} else {
			UserLog.log("User", "DelUser", "删除用户:" + UserNames + "失败", Request.getClientIP());
			Response.setLogInfo(0, "删除用户失败!");
		}
	}

	//重置密码
	public void resetPassword(){
		try{
			String UserNames = $V("UserNames");
			QueryBuilder qb = new QueryBuilder("update ZDUser set Password=? where UserName =? ");
			String ResetPassword = new QueryBuilder("select value from zdconfig where type='ResetPassword'").executeOneValue()  + "";
			if(StringUtil.isEmpty(ResetPassword)){
				ResetPassword = "Aa123456";
			}
			qb.add(StringUtil.md5Hex(ResetPassword));
			qb.add(UserNames);
			if (qb.executeNoQuery() > 0) {
				UserLog.log(UserLog.USER, UserLog.USER_EDITPASSWORD, "重置密码成功", Request.getClientIP());
				Response.setMessage("重置密码成功");
				Response.setStatus(1);
				QueryBuilder q = new QueryBuilder("update ZDUser set status='N' where UserName ='"+UserNames+"'");
				q.executeNoQuery();
			} else {
				UserLog.log(UserLog.USER, UserLog.USER_EDITPASSWORD, "重置密码失败", Request.getClientIP());
				Response.setStatus(0);
				Response.setMessage("重置密码失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("重置密码异常(" + e.getMessage() + ")");
		}
	}

	public void stopUser() {
		String UserNames = $V("UserNames");
		if (!idPattern.matcher(UserNames).matches()) {
			Response.setLogInfo(0, "传入用户名称时发生错误!");
			return;
		}
		ZDUserSchema user = new ZDUserSchema();
		ZDUserSet userSet = user.query(new QueryBuilder(" where UserName in ('" + UserNames.replaceAll(",", "','") + "')"));
		for (int i = 0; i < userSet.size(); i++) {
			if (ADMINISTRATOR.equalsIgnoreCase(userSet.get(i).getUserName())) {
				Response.setLogInfo(0, ADMINISTRATOR + "为系统自带的用户，拥有最高管理权限，不能停用!");
				return;
			}
			userSet.get(i).setStatus(STATUS_STOP);
		}
		if (userSet.update()) {
			Response.setLogInfo(1, "停用用户成功!");
		} else {
			Response.setLogInfo(0, "停用用户失败!");
		}
	}

	/**
	 * 删除一个用户需要做的步骤： 更新机构中的用户数 删除用户与角色的关系 更新角色中的用户数 删除这个用户的所有权限记录
	 */
	public static boolean del(Transaction trans, DataCollection dc) {
		String UserNames = dc.getString("UserNames");
		ZDUserSchema user = new ZDUserSchema();
		ZDUserSet userSet = user.query(new QueryBuilder(" where UserName in ('" + UserNames.replaceAll(",", "','")
				+ "')"));
		trans.add(userSet, Transaction.DELETE_AND_BACKUP);

		for (int i = 0; i < userSet.size(); i++) {
			user = userSet.get(i);
			if (User.getUserName().equals(user.getUserName())) {
				Errorx.addError("当前用户为：" + User.getUserName() + ",不能删除自身用户!");
				return false;
			}
			if (ADMINISTRATOR.equalsIgnoreCase(user.getUserName())) {
				Errorx.addError(ADMINISTRATOR + "为系统自带的用户，拥有最高管理权限，不能删除!");
				return false;
			}
			// 删除并备份用户与机构的关系
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			ZDUserRoleSet userRoleSet = userRole.query();
			trans.add(userRoleSet, Transaction.DELETE_AND_BACKUP);
			// 删除用户的权限
			trans.add(new ZDPrivilegeSchema().query(new QueryBuilder("where OwnerType=? and Owner=?",
					Priv.OWNERTYPE_USER, user.getUserName())), Transaction.DELETE_AND_BACKUP);
		}
		return true;
	}
}
