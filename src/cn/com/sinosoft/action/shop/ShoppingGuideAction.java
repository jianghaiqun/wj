package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.util.CommonUtil;
import com.opensymphony.oscache.util.StringUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.schema.SDLipeiFirstClickSchema;
import com.sinosoft.schema.ZCSiteSchema;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("shop")
public class ShoppingGuideAction extends BaseShopAction {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 当前要链接ID跟数据对应
	 */
	private String id;
	
	/**
	 * 判断是否是首页标记
	 */
	private String home;
	
	/**
	 * 页面链接显示列表
	 */
	private List<Map<String,String>> result;
	
	/**
	 * 友情链接组
	 */
	private List<Map<String,String>> friendGroup;
	
	/**
	 * 友情链接
	 */
	private List<Map<String,String>> friendLink;
	
	/**
	 * 当前页
	 */
	private String pageNo = "0";
	
	/**
	 * 是否是最终页
	 */
	private String lastPageFlag = "false";
	
	/**
	 * 上一步链接地址
	 */
	private String backStep;
	
	/**
	 * 标题
	 */
	private String title = "";
	
	/**
	 * 您接下来一个月的计划是计划是？ (页面顶部描述) 
	 */
	private String descProp1;

	private String Callback;
	
	/**
	 * 页面显示
	 * 
	 * @return
	 */
	public String show() {
		// 返回页
		String page = "index";
		
		String front_path = Config.getValue("FrontServerContextPath");
		
		QueryBuilder qb = new QueryBuilder("SELECT Id,Name,"
				+ "CASE WHEN link IS NULL OR link='' THEN CONCAT('" + front_path +"/daogou/',id,'.shtml') ELSE link END AS nextLink, "
				+ "CONCAT('" +  Config.getValue("StaticResourcePath") + "/', Image) AS Image, link, Prop1 FROM ShoppingGuideLink WHERE parentid=?" ,id);
		DataTable dt = qb.executeDataTable();
		
		result = new ArrayList<Map<String, String>>();
		boolean linkNullFlag = false;
		for (int i = 0; i < dt.getRowCount(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("Id", dt.getString(i, 0));
			map.put("Name", dt.getString(i, 1));
			map.put("nextLink", dt.getString(i, 2));
			map.put("Image", dt.getString(i, 3));
			String link = dt.getString(i, 4);
			if (StringUtil.isEmpty(link)) {
				map.put("target", "_self");
				linkNullFlag = true;
			}else{
				map.put("target", "_blank");
			}
			result.add(map);
			if (StringUtil.isEmpty(descProp1)) {
				descProp1 = dt.getString(i, 5);
			}
		}
		
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(221);
		site.fill();
		
		// 如果是首页，则查询友情链接
		if("y".equals(home)){

			QueryBuilder friendTeamQB = new QueryBuilder("select ID,Name from ZCLinkTeam Where Name = '导购频道'");
			DataTable friendTeamDt = friendTeamQB.executeDataTable();
			
			if (friendTeamDt.getRowCount() > 0) {
				String linkTeamStr = friendTeamDt.getString(0, 0);
				QueryBuilder friendGroupQB = new QueryBuilder("select ID,Name from zclinkgroup Where Prop1 = ? order by OrderFlag", linkTeamStr);
				DataTable friendGroupDt = friendGroupQB.executeDataTable();

				friendGroup = new ArrayList<Map<String, String>>();
				friendLink = new ArrayList<Map<String, String>>();
				for (int i = 0; i < friendGroupDt.getRowCount(); i++) {
					Map<String, String> map = new HashMap<String, String>();
					String groupID = friendGroupDt.getString(i, 0);
					map.put("Id", "guide" + groupID);
					map.put("Name", friendGroupDt.getString(i, 1));
					friendGroup.add(map);
					
					String sql = "select l.LinkGroupID, l.Name, l.URL, "
							+ "(select name from zclinkgroup where id=l.linkgroupID) as LinkGroupName from ZCLink l ";
					QueryBuilder qbLink = new QueryBuilder(sql);
					qbLink.append(" where l.LinkGroupID=?", Long.parseLong(groupID));
					qbLink.append(" order by l.OrderFlag desc,l.id desc");
					DataTable qbLinkDt = qbLink.executeDataTable();
					if (qbLinkDt.getRowCount() > 0) {
						for (int j = 0; j < qbLinkDt.getRowCount(); j++) {
							Map<String, String> linkMap = new HashMap<String, String>();
							String LinkGroupID = qbLinkDt.getString(j, 0);
							linkMap.put("LinkGroupID", "guide" + LinkGroupID);
							linkMap.put("Name", qbLinkDt.getString(j, 1));
							linkMap.put("URL", qbLinkDt.getString(j, 2));
							friendLink.add(linkMap);
						}
					}
				}
			}
			title = "保险导购_保险怎么买-" + site.getMeta_Keywords();
		}else{
			page = "son";
			pageNo = String.valueOf(id.length() / 3);
			
			if (!linkNullFlag) {
				lastPageFlag = "true";
			}
			
			if (0 == (id.length() - 3)) {
				backStep = front_path +"/daogou/index.shtml";
			}else{
				backStep = front_path +"/daogou/" + id.substring(0, id.length() - 3) + ".shtml";
			}
			
			QueryBuilder lastQB = new QueryBuilder("SELECT Name FROM ShoppingGuideLink WHERE id=?" ,id);
			String lastName = lastQB.executeString();
			
			title = lastName + "保险导购-" + site.getMeta_Keywords();
		}
		
		return page;

	}
	
	/**
	 * ajaxClaims:获取理赔频道是否首次进入. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	public String ajaxClaims(){
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("exists", "-1");
		String memberid = (String) getRequest().getSession().getAttribute(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (!StringUtil.isEmpty(memberid)) {
			QueryBuilder qb = new QueryBuilder("select MemberId from SDLipeiFirstClick where MemberId = ?", memberid);
			
			String mid = qb.executeString();
			if (!StringUtil.isEmpty(mid)) {
				jsonMap.put("exists", "true");
			}
			else {
				jsonMap.put("exists", "false");
				
				SDLipeiFirstClickSchema schema = new SDLipeiFirstClickSchema();
				schema.setId(CommonUtil.getUUID());
				schema.setMemberId(memberid);
				schema.setCreateDate(new Date());
				schema.setModifyDate(new Date());
				schema.insert();
			}
		}
		
		String jsonStr = formatJson(jsonMap);
		return ajaxJson(Callback + "(" + jsonStr + ");");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public List<Map<String, String>> getResult() {
		return result;
	}

	public void setResult(List<Map<String, String>> result) {
		this.result = result;
	}

	public List<Map<String, String>> getFriendGroup() {
		return friendGroup;
	}

	public void setFriendGroup(List<Map<String, String>> friendGroup) {
		this.friendGroup = friendGroup;
	}

	public List<Map<String, String>> getFriendLink() {
		return friendLink;
	}

	public void setFriendLink(List<Map<String, String>> friendLink) {
		this.friendLink = friendLink;
	}

	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	public String getLastPageFlag() {
		return lastPageFlag;
	}

	public void setLastPageFlag(String lastPageFlag) {
		this.lastPageFlag = lastPageFlag;
	}

	public String getBackStep() {
		return backStep;
	}

	public void setBackStep(String backStep) {
		this.backStep = backStep;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescProp1() {
		return descProp1;
	}

	public void setDescProp1(String descProp1) {
		this.descProp1 = descProp1;
	}

	public String getCallback() {
	
		return Callback;
	}

	public void setCallback(String callback) {
	
		Callback = callback;
	}
}
