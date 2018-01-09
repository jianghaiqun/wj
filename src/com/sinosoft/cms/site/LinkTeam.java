package com.sinosoft.cms.site;

import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCLinkGroupSchema;
import com.sinosoft.schema.ZCLinkGroupSet;
import com.sinosoft.schema.ZCLinkSchema;
import com.sinosoft.schema.ZCLinkSet;
import com.sinosoft.schema.ZCLinkTeamSchema;

public class LinkTeam extends Page {
	
	public void add(){
		String teamName = $V("TeamName").trim();
		if (new QueryBuilder("select count(*) from ZCLinkTeam where SiteID=? and Name=?", Application
				.getCurrentSiteID(), teamName).executeInt() == 0) {
			ZCLinkTeamSchema linkTeam = new ZCLinkTeamSchema();
			linkTeam.setID(NoUtil.getMaxID("KeyWordTypeID"));
			linkTeam.setName(teamName);
			linkTeam.setSiteID(Application.getCurrentSiteID());
			linkTeam.setAddTime(new Date());
			linkTeam.setAddUser(User.getUserName());
			if (linkTeam.insert()) {
				Response.setStatus(1);
				Response.setMessage("新增成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("已经存在的链接包!");
		}
	}
	
	public void del(){
		Transaction trans = new Transaction();
		String ID = $V("ID");
		ZCLinkTeamSchema linkTeam = new ZCLinkTeamSchema();
		linkTeam.setID(ID);
		trans.add(linkTeam, Transaction.DELETE);
		ZCLinkGroupSchema linkGroup = new ZCLinkGroupSchema();
		ZCLinkGroupSet groupSet = new ZCLinkGroupSet();
		ZCLinkSchema link = new ZCLinkSchema();
		ZCLinkSet linkSet = new ZCLinkSet();
		groupSet = linkGroup.query(new QueryBuilder(" where prop1 = ?",ID));
		for(int i=0;i<groupSet.size();i++){
			linkGroup = groupSet.get(i);
			linkSet = link.query(new QueryBuilder(" where LinkGroupID = ?",linkGroup.getID()));
			trans.add(linkSet, Transaction.DELETE);
		}
		trans.add(new QueryBuilder("delete from ZCLinkGroup where Prop1 = ?", ID));

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}
	
	public void edit(){
		String ID = $V("ID");
		ZCLinkTeamSchema LinkTeam = new ZCLinkTeamSchema();
		LinkTeam.setID(ID);
		String teamName = $V("TeamName").trim();
		if (LinkTeam.fill()
				&& new QueryBuilder("select count(*) from ZCLinkTeam where SiteID=? and Name=?", Application
						.getCurrentSiteID(), teamName).executeInt() == 0) {
			LinkTeam.setName(teamName);
			LinkTeam.setModifyTime(new Date());
			LinkTeam.setModifyUser(User.getUserName());
			if (LinkTeam.update()) {
				Response.setStatus(1);
				Response.setMessage("修改成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("已经存在的链接包!");
		}
	}
	
	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("id");
		params.put("ID", ID);
		ZCLinkTeamSchema LinkTeam = new ZCLinkTeamSchema();
		if (StringUtil.isNotEmpty(ID)) {
			LinkTeam.setID(ID);
			LinkTeam.fill();
			params.put("TeamName", LinkTeam.getName());
		}

		return params;
	}
}
