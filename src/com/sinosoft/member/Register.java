package com.sinosoft.member;

import java.util.Date;

import com.sinosoft.cms.dataservice.MemberField;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDMemberCompanyInfoSchema;
import com.sinosoft.schema.ZDMemberPersonInfoSchema;

public class Register extends Ajax {

	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		if (StringUtil.isEmpty(SiteID)) {
			SiteID = new QueryBuilder("select ID from ZCSite Order by AddTime desc").executeOneValue() + "";
		}
		params.put("Columns", MemberField.getColumns(Long.parseLong(SiteID)));
		return params;
	}

	public void checkUser() {
		String UserName = $V("UserName");
		Member member = new Member(UserName);
		if (member.isExists()) {
			Response.setLogInfo(0, "用户名已存在,请选择另外的用户名");
		} else {
			Response.setLogInfo(1, "用户名未注册,您可以放心使用");
		}
	}

	public void register() {
		String userName = $V("UserName");
		String passWord = $V("PassWord");
		String eMail = $V("Email");
		String Type = $V("Type");
		String VerifyCode = $V("VerifyCode");
		Object authCode = User.getValue(Constant.DefaultAuthKey);
		if (authCode != null && !authCode.equals(VerifyCode)) {
			Response.setLogInfo(0, "验证码错误");
			return;
		} else {
			Transaction trans = new Transaction();
			Member member = new Member(userName);
			member.setName(userName);
			member = MemberField.setPropValues(member, Request);
			member.setEmail(eMail);
			member.setType(Type);
			member.setSiteID($V("SiteID"));
			member.setRegIP(Request.getClientIP());
			member.setValue(Request);
			member.setPassword(passWord.trim());
			member.setStatus("X");
			member.setRegTime(new Date());
			member.setScore("0");
			member.setMemberLevel(new QueryBuilder("select ID from ZDMemberLevel where Score <= 0 order by Score desc")
					.executeOneValue()
					+ "");
			member.setLastLoginIP(Request.getClientIP());
			member.setLastLoginTime(new Date());
			if(StringUtil.isEmpty(member.getName())){
				member.setName("注册用户");
			}
			trans.add(member, Transaction.INSERT);
			if (Type.equalsIgnoreCase("Person")) {
				ZDMemberPersonInfoSchema person = new ZDMemberPersonInfoSchema();
				person.setUserName(member.getUserName());
				person.setNickName(member.getName());
				person.setMobile($V("Mobile"));
				person.setAddress($V("Address"));
				person.setZipCode($V("ZipCode"));
				trans.add(person, Transaction.INSERT);
			} else {
				ZDMemberCompanyInfoSchema company = new ZDMemberCompanyInfoSchema();
				company.setUserName(member.getUserName());
				company.setCompanyName(member.getName());
				company.setEmail(member.getEmail());
				company.setMobile($V("Mobile"));
				company.setAddress($V("Address"));
				company.setZipCode($V("ZipCode"));
				trans.add(company, Transaction.INSERT);
			}
			//ForumUtil.createBBSUser(trans, userName, member.getSiteID() + "");
			if (trans.commit()) {
				Cookie.setCookie("UserName", userName);
				User.setLogin(true);
				User.setUserName(userName);
				User.setMember(true);
				User.setValue("UserName", member.getUserName());
				User.setValue("Type", member.getType());
				User.setValue("SiteID", member.getSiteID() + "");
				// 如果不使用论坛请将此方法注释
//				ForumUtil.allowVisit(member.getSiteID() + "");
				if (StringUtil.isNotEmpty(member.getName())) {
					User.setValue("Name", member.getName());
				} else {
					User.setValue("Name", member.getUserName());
				}
				Response.setLogInfo(1, "注册成功");
			} else {
				Response.setLogInfo(0, "注册失败");
			}
		}
	}

}
