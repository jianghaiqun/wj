package com.sinosoft.shop.extend;

import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.extend.IExtendAction;

public class ShopMenuExtend implements IExtendAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendAction#execute(java.lang.Object[])
	 */
	public void execute(Object[] args) {
		StringBuffer sb = (StringBuffer) args[0];
		String SiteID = (String) args[1];
		if (!SiteUtil.isBBSEnable(SiteID)) {
			return;
		}
		sb.append("<ul class='sidemenu'>");
		sb.append("<li id='Menu_Fav'><a href='" + Config.getContextPath() + "Shop/Web/Favorite.jsp?cur=Menu_Fav&SiteID=" + SiteID + "'>我的收藏夹</a></li>");
		sb.append("<li id='Menu_SC'><a href='" + Config.getContextPath() + "Shop/Web/ShopCart.jsp?cur=Menu_SC&SiteID=" + SiteID + "'>我的购物车</a></li>");
		sb.append("<li id='Menu_MO'><a href='" + Config.getContextPath() + "Shop/Web/MemberOrder.jsp?cur=Menu_MO&SiteID=" + SiteID + "'>我的订单</a></li>");
		sb.append("<li id='Menu_ME'><a href='" + Config.getContextPath() + "Shop/Web/MemberEvaluate.jsp?cur=Menu_ME&SiteID=" + SiteID + "'>商品评价</a></li>");
		sb.append("<li id='Menu_ADR'><a href='" + Config.getContextPath() + "Shop/Web/MemberAddress.jsp?cur=Menu_ADR&SiteID=" + SiteID + "'>收货地址管理</a></li>");
		sb.append("</ul>");
		sb.append("<hr class='shadowline'/>");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.sinosoft.framework.extend.IExtendAction#getTarget()
	 */
	public String getTarget() {
		return "Member.Menu";
	}

	public String getName() {
		return "Shop会员中心菜单扩展";
	}

}
