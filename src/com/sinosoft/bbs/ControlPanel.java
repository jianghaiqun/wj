package com.sinosoft.bbs;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCForumGroupSchema;
import com.sinosoft.schema.ZCForumMemberSchema;
import com.sinosoft.schema.ZDMemberPersonInfoSchema;
import com.sinosoft.schema.ZDMemberSchema;

public class ControlPanel extends Ajax {
	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		if (StringUtil.isEmpty(User.getUserName())) {
			params.put("redirect", "<script>window.location='Index.jsp?SiteID=" + SiteID + "'</script>");
			return params;
		}
		ForumPriv priv = new ForumPriv(SiteID);
		if (!priv.hasPriv("AllowPanel")) {
			params.put("redirect", "<script>window.location='Index.jsp?SiteID=" + SiteID + "'</script>");
			return params;
		}
		ZCForumMemberSchema forumUser = new ZCForumMemberSchema();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		ZDMemberPersonInfoSchema personInfo = new ZDMemberPersonInfoSchema();
		ZDMemberSchema member = new ZDMemberSchema();
		forumUser.setUserName(User.getUserName());
		forumUser.fill();
		personInfo.setUserName(forumUser.getUserName());
		personInfo.fill();
		member.setUserName(User.getUserName());
		member.fill();
		group.setID(forumUser.getUserGroupID());
		group.fill();
		params.putAll(member.toMapx());
		params.putAll(forumUser.toMapx());
		Mapx map = new Mapx();
		map.put("N", "使用组头像");
		map.put("Y", "使用自定义头像");
		params.put("UserImageOption", HtmlUtil.mapxToRadios("UserImageOption", map, forumUser.getUseSelfImage()));
		params.put("NickName", forumUser.getNickName());
		params.put("UserName", User.getUserName());
		params.put("RegTime", DateUtil.toDateTimeString(member.getRegTime()));
		params.put("LastLoginTime", DateUtil.toDateTimeString(member.getLastLoginTime()));
		params.put("group", group.getName());
		String headImage = forumUser.getHeadImage();
		if (headImage.startsWith("../")) {
			headImage = StringUtil.replaceEx(headImage, "../", "");
		}
		params.put("HeadImage", headImage);
		params.put("Priv", ForumUtil.initPriv(SiteID));
		params.put("Birthday", personInfo.getBirthday());
		params.put("QQ", personInfo.getQQ());
		params.put("MSN", personInfo.getMSN());
		params.put("Tel", personInfo.getTel());
		params.put("Mobile", personInfo.getMobile());
		params.put("Address", personInfo.getAddress());
		params.put("ZipCode", personInfo.getZipCode());
		params.put("SiteID", SiteID);
		params.put("BBSName", ForumUtil.getBBSName(SiteID));

		return params;
	}

	public void perInfoSave() {
		ZCForumMemberSchema forumUser = new ZCForumMemberSchema();
		forumUser.setUserName($V("UserName"));
		forumUser.fill();
		forumUser.setNickName($V("NickName"));

		Transaction trans = new Transaction();
		trans.add(forumUser, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	public void selfSettingSave() {
		ForumPriv priv = new ForumPriv($V("SiteID"));
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName($V("UserName"));
		member.fill();
		member.setForumSign($V("ForumSign"));
		member.setNickName($V("NickName"));
		member.setUseSelfImage($V("UserImageOption"));
		if ($V("UserImageOption").equals("1")) {
			if (!priv.hasPriv("AllowHeadImage")) {
				Response.setLogInfo(0, "您所在用户组不允许使用自定义头像!");
				return;
			}
		}
		Transaction trans = new Transaction();
		trans.add(member, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	public void doSave() {
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName($V("UserName"));
		member.fill();
		member.setValue(Request);
		if (member.update()) {
			Response.setLogInfo(1, "保存成功");
		} else {
			Response.setLogInfo(0, "保存失败");
		}
	}

	public void modifyPassword() {
		ZDMemberSchema forumUser = new ZDMemberSchema();
		forumUser.setUserName($V("UserName"));
		forumUser.fill();
		if (StringUtil.md5Hex($V("Password").trim()).equals(forumUser.getPassword())) {
			String psd1 = $V("NewPwd1").trim();
			String psd2 = $V("NewPwd2").trim();
			if (psd1.equals(psd2)) {
				forumUser.setPassword(StringUtil.md5Hex($V("NewPwd1")));
			} else {
				Response.setLogInfo(1, "两次密码输入不一致!");
				return;
			}
		} else {
			Response.setLogInfo(1, "原密码输入错误!");
			return;
		}

		Transaction trans = new Transaction();
		trans.add(forumUser, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}
}
