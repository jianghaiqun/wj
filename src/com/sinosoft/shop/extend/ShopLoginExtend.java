package com.sinosoft.shop.extend;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.extend.IExtendAction;
import com.sinosoft.member.Member;

public class ShopLoginExtend implements IExtendAction {
	public String getTarget() {
		return "Member.Login";
	}

	public void execute(Object[] args) {
		Member member = (Member) args[0];
		String siteID = String.valueOf(member.getSiteID());
		if (SiteUtil.isShopEnable(siteID)) {
			
		}
	}

	public String getName() {
		return "Shop会员登录逻辑扩展";
	}
}
