package com.sinosoft.bbs.extend;

import com.sinosoft.bbs.ForumUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.member.Member;
import com.sinosoft.framework.extend.IExtendAction;

public class BBSLoginExtend implements IExtendAction {
	public String getTarget() {
		return "Member.Login";
	}

	public void execute(Object[] args) {
		Member member = (Member) args[0];
		String siteID = String.valueOf(member.getSiteID());
		if (SiteUtil.isBBSEnable(siteID)) {
			ForumUtil.allowVisit(siteID);
		}
	}

	public String getName() {
		return "BBS会员登录逻辑扩展";
	}
}
