package com.sinosoft.member;

import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDMemberSchema;

/**
 */

public class Member extends ZDMemberSchema {

	private static final long serialVersionUID = 1L;

	public Member() {

	}

	public Member(String userName) {
		this.setUserName(userName);
	}

	public Member(String userName, String siteID) {
		this.setUserName(userName);
		this.setSiteID(siteID);
	}

	public void setPassword(String passWord) {
		super.setPassword(StringUtil.md5Hex(passWord));
	}

	public void setUnMd5Password(String passWord) {
		super.setPassword(passWord);
	}

	public boolean isExists() {
		boolean flag = false;
		if (StringUtil.isNotEmpty(this.getUserName())) {
			int count = new QueryBuilder("select count(*) from ZDMember where UserName=?", this.getUserName())
					.executeInt();
			if (count > 0) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean isExistsCurrentSite() {
		boolean flag = false;
		if (StringUtil.isNotEmpty(this.getUserName())) {
			int count = new QueryBuilder("select count(*) from ZDMember where UserName=? and SiteID=?", this
					.getUserName(), this.getSiteID()).executeInt();
			if (count > 0) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean checkPassWord(String passWord) {
		boolean flag = false;
		if (StringUtil.isNotEmpty(passWord)) {
			passWord = StringUtil.md5Hex(passWord.trim());
			if (this.getPassword().equals(passWord)) {
				flag = true;
			}
		}
		return flag;
	}

}
