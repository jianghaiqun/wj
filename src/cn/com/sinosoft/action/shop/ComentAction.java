package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ZCComment;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * 
 * @author wugq
 * 
 */
@ParentPackage("member")
public class ComentAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 458674080981557053L;

	private String productname;
	private String suppliercorp;
	private String keyword;
	private String content;
	private String csdate;
	private String cedate;
	private String memberID;
	private int page = 1;
	private int count;
	private int lastpage;
	public static final int pagesize = 5;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	private List<ZCComment> zclist = new ArrayList<ZCComment>();

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getSuppliercorp() {
		return suppliercorp;
	}

	public void setSuppliercorp(String suppliercorp) {
		this.suppliercorp = suppliercorp;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getMemberID() {
		return memberID;
	}

	public Member getLoginMember() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		Member loginMember = memberService.load(id);
		return loginMember;
	}

	public String mycomment() {

		memberID = getLoginMember().getId();
		try {
			if (StringUtil.isNotEmpty(keyword)) {
				keyword = java.net.URLDecoder.decode(keyword, "UTF-8").trim();
			}
			if (StringUtil.isNotEmpty(csdate)) {
				csdate = java.net.URLDecoder.decode(csdate, "UTF-8").trim();
			}
			if (StringUtil.isNotEmpty(productname)) {
				productname = java.net.URLDecoder.decode(productname, "UTF-8").trim();
			}
			if (StringUtil.isNotEmpty(suppliercorp)) {
				suppliercorp = java.net.URLDecoder.decode(suppliercorp, "UTF-8").trim();
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		
		String sqlPart = " order by addtime desc  limit " + (page - 1)
				* pagesize + "," + pagesize;
		String sqlPart2 = " where a.id=b.RelaID and a.title = c.productname and b.Prop1 =? ";

		if (!(productname == null || "".equals(productname.trim()))) {
			if(productname.indexOf("产品名称")==-1){
				sqlPart2 = sqlPart2 + "  and  a.title like '%" + productname + "%'";
			}
		}
		if (!(suppliercorp == null || "".equals(suppliercorp.trim()))) {
			if(suppliercorp.indexOf("保险公司")==-1){
				sqlPart2 = sqlPart2 + "  and  c.InsureName like '%" + suppliercorp + "%'";
			}
		}
		if (!(keyword == null || "".equals(keyword.trim()))) {
			if(keyword.indexOf("关键字")==-1){
				sqlPart2 = sqlPart2 + "  and  b.Prop2 like '%" + keyword + "%'";
			}
		}
		if (csdate != null&&!"".equals(csdate.trim())) {
			if(csdate.indexOf("评论时间")==-1){
				sqlPart2 = sqlPart2 + "  and  b.addtime >= '" + csdate + " 00:00:00'";
			}
		}
		if (cedate != null &&!"".equals(cedate.trim())) {
			sqlPart2 = sqlPart2 + "  and  b.addtime < '" + cedate + " 23:59:59'";
		}
        String sql = "select a.url ,b.addtime ,a.title ,b.content ,c.ispublish ,c.insurename from zcarticle a ,zccomment b,SDProduct c ";
        
		count = new QueryBuilder("select count(1) from zcarticle a ,zccomment b,SDProduct c " + sqlPart2, memberID).executeInt();
	    this.lastpage=(count+pagesize-1)/(pagesize);
	    DataTable dt = new QueryBuilder(sql + sqlPart2 + sqlPart, memberID).executeDataTable();
	    if (dt != null && dt.getRowCount() > 0) {
	        int rowCount = dt.getRowCount();
	        for (int i = 0; i < rowCount; i++) {
	        	ZCComment zcomment = new ZCComment();
				zcomment.setContent(StringUtil.htmlDecode(dt.getString(i, "Content")));
				zcomment.setCreateDate(dt.getString(i, "addtime"));
				zcomment.setHtmlPath(dt.getString(i, "url"));
				zcomment.setIsPublish(dt.getString(i, "ispublish"));
				zcomment.setProductName(dt.getString(i, "title"));				
				zcomment.setInsureName(dt.getString(i, "insurename"));
				
				zclist.add(zcomment);
	        }
	    }
	    page_Index = String.valueOf(page);
		page_count = String.valueOf(lastpage);
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		getPageDataList(param_map);
		
        Map<String,Object> map = new HashMap<String,Object>(); 
        ActionUtil.dealAction("wj00036", map, getRequest());
        
		return "input";
	}

	public void setZclist(List<ZCComment> zclist) {
		this.zclist = zclist;
	}

	public List<ZCComment> getZclist() {
		return zclist;
	}
}
