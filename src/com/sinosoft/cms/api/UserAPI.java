package com.sinosoft.cms.api;

import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Priv;
import com.sinosoft.schema.ZDBranchSchema;
import com.sinosoft.schema.ZDBranchSet;
import com.sinosoft.schema.ZDPrivilegeSchema;
import com.sinosoft.schema.ZDUserRoleSchema;
import com.sinosoft.schema.ZDUserRoleSet;
import com.sinosoft.schema.ZDUserSchema;

public class UserAPI implements APIInterface {

	private Mapx params;
	
	private static final Pattern userPattern = Pattern.compile("[\\w@\\.\\,\u4e00-\u9fa5]*", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	
	public boolean delete() {
		String username=params.getString("Username");
		username = username.toLowerCase();
		
		if (!userPattern.matcher(username).matches()) {
			return false;
		}
		
		if("administrator".equalsIgnoreCase(username)){
			return false;
		}
		
		if("admin".equalsIgnoreCase(username)){
			return false;
		}
		
		ZDUserSchema user=new ZDUserSchema();
		user.setUserName(username);
		if(!user.fill()){
			return false;
		}
		
		Transaction trans=new Transaction();
		// 删除并备份用户与机构的关系
		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		userRole.setUserName(user.getUserName());
		ZDUserRoleSet userRoleSet = userRole.query();
		trans.add(userRoleSet, Transaction.DELETE);
		// 删除用户的权限
		trans.add(new ZDPrivilegeSchema().query(new QueryBuilder("where OwnerType=? and Owner=?", Priv.OWNERTYPE_USER, user.getUserName())), Transaction.DELETE);
		trans.add(user,Transaction.DELETE);
		
		if(trans.commit()){
			return true;
		}else return false;
	}

	/*   新增加后台用户
	 * 	 新增加后台用户成功 返回 1 
	 *              失败 返回 -1
	 *              已存在 返回 0
	 */
	public long insert() {
		return insert(new Transaction());
	}
	

	public long insert(Transaction trans) {
		String username = params.getString("Username");
		String realname = params.getString("RealName");
		String password = params.getString("Password");
		String email = params.getString("Email");
		String branchCode = params.getString("BranchCode");
		String isBranchAdmin = params.getString("IsBranchAdmin");
		String status = params.getString("Status");
		
		String type = params.getString("Type");
		if(StringUtil.isEmpty(username)||StringUtil.isEmpty(password)){
			return -1;
		}
		
		username = username.toLowerCase();
		if (!userPattern.matcher(username).matches()) {
			Errorx.addError("用户名最多20位，仅限英文字母，数字，汉字，半角“.”、“@”");
			return -1;
		}
		
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (user.fill()) {
			Errorx.addError("用户"+username+"已经存在.");
			return -1;
		}
		
		user.setRealName(realname);
		if (StringUtil.isEmpty(realname)) {
			user.setRealName(username);
		}
		
		ZDBranchSchema branch = new ZDBranchSchema();
		if (StringUtil.isNotEmpty(branchCode)) {
			branch.setBranchCode(branchCode);
			ZDBranchSet set = branch.query();
			if (set == null || set.size() < 1) {
				Errorx.addError(branchCode + "机构编码有误.");
				return -1;
			}
			
			user.setBranchInnerCode(set.get(0).getBranchInnerCode());
		} else {
			user.setBranchInnerCode("0001");
		}
		
		if("Y".equals(isBranchAdmin)){
			user.setIsBranchAdmin("Y");
		}else{
			user.setIsBranchAdmin("N");
		}
		
		if("S".equals(status)){
			user.setStatus("S");
		}else{
			user.setStatus("N");
		}
		
		user.setPassword(StringUtil.md5Hex(password));
		if (StringUtil.isEmpty(type)) {
			user.setType("0");
		} else {
			user.setType(type);
		}
		user.setEmail(email);
		user.setProp1(params.getString("Prop1"));
		user.setAddTime(new Date());
		user.setAddUser("wsdl");
		//添加用户
		trans.add(user, Transaction.INSERT);
		// 角色
		String roleCodes = params.getString("RoleCode");
		if (StringUtil.isNotEmpty(roleCodes)) {
			roleCodes="'"+roleCodes.replaceAll(",", "','")+"'";
			DataTable dt=new QueryBuilder("select RoleCode from zdrole where RoleCode in ("+roleCodes+")")
							.executeDataTable();
			
			String[] RoleCodes = (String[])dt.getColumnValues(0);
			Date addTime=new Date();
			for (int i = 0; i < RoleCodes.length; i++) {
				if (StringUtil.isEmpty(RoleCodes[i])) {
					continue;
				}
				ZDUserRoleSchema userRole = new ZDUserRoleSchema();
				userRole.setUserName(user.getUserName());
				userRole.setRoleCode(RoleCodes[i]);
				userRole.setAddTime(addTime);
				userRole.setAddUser("wsdl");
				//添加用户角色关联表记录
				trans.add(userRole, Transaction.INSERT);
			}
		} else {
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode("everyone");
			userRole.setAddTime(new Date());
			userRole.setAddUser("wsdl");
			//添加用户角色关联表记录
			trans.add(userRole, Transaction.INSERT);
		}
		
		if(trans.commit()){
			return 1;
		}else{
			Errorx.addError("新建用户"+username+"失败。");
			return -1;
		}
	}

	public boolean setSchema(Schema schema) {
		return false;
	}

	public boolean update() {
		String username = params.getString("Username");
		String realname = params.getString("RealName");
		String password = params.getString("Password");
		String email = params.getString("Email");
		String branchCode = params.getString("BranchCode");
		String isBranchAdmin = params.getString("IsBranchAdmin");
		String status = params.getString("Status");
		
		String type = params.getString("Type");
		if(StringUtil.isEmpty(username)){
			return false;
		}
		
		username = username.toLowerCase();
		if (!userPattern.matcher(username).matches()) {
			Errorx.addError("用户名最多20位，仅限英文字母，数字，汉字，半角“.”、“@”");
			return false;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (!user.fill()) {
			Errorx.addError(username + "用户不存在.");
			return false;
		}
		
		if (StringUtil.isNotEmpty(branchCode)) {
			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setBranchCode(branchCode);
			ZDBranchSet set = branch.query();
			if (set == null || set.size() < 1) {
				Errorx.addError(branchCode + "机构编码有误.");
				return false;
			}
			
			user.setBranchInnerCode(set.get(0).getBranchInnerCode());
		}
		
		Transaction trans=new Transaction();
		if (StringUtil.isNotEmpty(realname)) {
			user.setRealName(realname);
		}
		
		if("Y".equals(isBranchAdmin)){
			user.setIsBranchAdmin("Y");
		}else{
			user.setIsBranchAdmin("N");
		}
		
		if("suspend".equals(params.getString("OperationType"))&&"S".equals(user.getStatus())){
			Errorx.addError("用户"+username+"已经暂停。");
			return false;
		}
		
		if("restore".equals(params.getString("OperationType")) && "N".equals(user.getStatus())){
			Errorx.addError("用户"+username+"未暂停。");
			return false;
		}
		
		if("S".equals(status)){
			user.setStatus("S");
		}else{
			user.setStatus("N");
		}
		
		if (StringUtil.isNotEmpty(password)) {
			user.setPassword(StringUtil.md5Hex(password));
		}
		if (StringUtil.isEmpty(type)) {
			user.setType("0");
		} else {
			user.setType(type);
		}
		if (StringUtil.isNotEmpty(email)) {
			user.setEmail(email);
		}
		user.setProp1(params.getString("Prop1"));
		user.setModifyTime(new Date());
		user.setModifyUser("wsdl");
		
		//修改用户
		trans.add(user, Transaction.UPDATE);
		
		//角色
		
		String roleCodes = params.getString("RoleCode");
		if (StringUtil.isNotEmpty(roleCodes)) {
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			trans.add(userRole.query(), Transaction.DELETE);
			
			roleCodes="'"+roleCodes.replaceAll(",", "','")+"'";
			DataTable dt=new QueryBuilder("select RoleCode from zdrole where RoleCode in ("+roleCodes+")")
							.executeDataTable();
			String[] RoleCodes = (String[])dt.getColumnValues(0);

			Date date=new Date();
			for (int i = 0; i < RoleCodes.length; i++) {
				if (StringUtil.isEmpty(RoleCodes[i])) {
					continue;
				}
				userRole = new ZDUserRoleSchema();
				userRole.setUserName(user.getUserName());
				userRole.setRoleCode(RoleCodes[i]);
				userRole.setAddTime(date);
				userRole.setAddUser("wsdl");
				trans.add(userRole, Transaction.INSERT);
			}
		}
		
		if(trans.commit()){
			return true;
		}else return false;
	}

	public Mapx getParams() {
		return params;
	}

	public void setParams(Mapx params) {
		convertParams(params);
		this.params = params;
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
