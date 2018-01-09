package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ZCComment;
import cn.com.sinosoft.service.BindInfoForLoginService;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员中心 评测
 */
@ParentPackage("member")
public class EvaluatingAction extends BaseShopAction{
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	private static final long serialVersionUID = -8755379599168716062L;
	private String keyword;
	private String csdate;
	private String cedate;
	private String memberID;
	private int page=1;
	private int count;
	private int lastpage;
	private int ispage;
	private int pagesize = 5;
	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}


	private List<ZCComment> sclist = new ArrayList<ZCComment>();


	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getCsdate() {
		return csdate;
	}

	public void setCsdate(String csdate) {
		this.csdate = csdate;
	}

	public String getCedate() {
		return cedate;
	}

	public void setCedate(String cedate) {
		this.cedate = cedate;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	
	/**
	 * 保险评测.
	 * @return
	 */
	public String evaluatingList(){

		return "evaluatingList";
	}

	/**
	 * 获得登录用户信息
	 *
	 * @return
	 */
	public Member getLoginMember() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		if(!"tencent".equals(id)){
			id = memberService.get(id).getId();
		}else{
			id = bindInfoForLoginService.get(String.valueOf(getSession("loginBindId"))).getmMember().getId();
		}
		Member loginMember = memberService.load(id);
		return loginMember;
	}

	/**
	 * 加载会员中心评测数据
	 *
	 * @return
	 */
	public String evaluating(){
		// 会员ID
		memberID = getLoginMember().getId();
		try {
			// 关键字
			if (StringUtil.isNotEmpty(keyword)) {
				keyword = java.net.URLDecoder.decode(keyword, "UTF-8").trim();
			}
			// 开始时间
			if (StringUtil.isNotEmpty(csdate)) {
				csdate = java.net.URLDecoder.decode(csdate, "UTF-8").trim();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		// SQL排序分页
		String sqlPart = " order by a.addtime desc  limit  "
				+ (page - 1) * pagesize + "," + pagesize;

		// SQL条件1：关键字
		String sqlPart2 = "";
		if (!(keyword == null || "".equals(keyword.trim()))) {
			if(keyword.indexOf("关键字")==-1){
				sqlPart2 += " and  ( a.title like '%" + keyword+"%' or a.prop2 like '%" + keyword+"%' )";
			}
		}
		// SQL条件3：评论时间
		if (!(csdate == null || "".equals(csdate.trim()))) {
			if(csdate.indexOf("评论时间")==-1){
				sqlPart2 = sqlPart2 + " and a.addtime >='" + csdate+" 00:00:00'";
			}
		}
		if (!(cedate == null || "".equals(cedate.trim()))) {
			sqlPart2 = sqlPart2 + " and a.addtime <'" + cedate+" 23:59:59'";
		}
		// 获取保险测评Id
		QueryBuilder qb1 = new QueryBuilder("select id from ZCCatalog where Name = '"+ Constant.EVALUATING_NAME + "'");
		DataTable dt1 = qb1.executeDataTable();
		String catalogId = "";
		if (dt1 != null && dt1.getRowCount() > 0) {
			catalogId = dt1.getString(0,0);
		}

		String sql = "SELECT a.title,a.ReplyContent,b.url FROM zccomment a, zcarticle b where  a.prop1 = ? and a.relaid = b.id and a.catalogid = '"+ catalogId +"' ";
        
        count = new QueryBuilder("select count(1) from zccomment a where a.prop1 = ? " + sqlPart2, memberID).executeInt();
        this.lastpage = (count+pagesize-1)/(pagesize);
        DataTable dt = new QueryBuilder(sql + sqlPart2 + sqlPart, memberID).executeDataTable();
        if (dt != null && dt.getRowCount() > 0) {
        	int rowCount = dt.getRowCount();
        	for (int i = 0; i < rowCount; i++) {
				ZCComment zcomment = new ZCComment();
				zcomment.setUrl(dt.getString(i, "url"));
				zcomment.setTitle(dt.getString(i, "title"));
				zcomment.setReplyContent(dt.getString(i, "ReplyContent"));
				sclist.add(zcomment);
        	}
        }

		page_Index = String.valueOf(page);
		page_count = String.valueOf(lastpage);
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		getPageDataList(param_map);
		
        return "input";
	}

	public void setSclist(List<ZCComment> sclist) {
		this.sclist = sclist;
	}

	public List<ZCComment> getSclist() {
		return sclist;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setIspage(int ispage) {
		this.ispage = ispage;
	}

	public int getIspage() {
		return ispage;
	}
}
