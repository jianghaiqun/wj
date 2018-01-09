package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SCAnswer;
import cn.com.sinosoft.service.SCAnswerService;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
/**
 * auther wugq
 */
@ParentPackage("member")
public class AnswerAction extends BaseShopAction {

	private static final long serialVersionUID = -6318570970948248533L;
	private String keyword;
	private String csdate;
	private String cedate;
	private String memberID;
	private int page = 1;
	private int count;
	private int lastpage;
	private int pagesize=5;
	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	private List<SCAnswer> sclist = new ArrayList<SCAnswer>();
	@Resource
	private SCAnswerService scAnswerService;
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

	
	public List<SCAnswer> getSclist() {
		return sclist;
	}

	public void setSclist(List<SCAnswer> sclist) {
		this.sclist = sclist;
	}
	public Member getLoginMember() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		Member loginMember = memberService.load(id);
		return loginMember;
	}
	public String reply(){

		memberID = getLoginMember().getId();
		try {
			if (StringUtil.isNotEmpty(keyword)) {
				keyword = java.net.URLDecoder.decode(keyword, "UTF-8").trim();
			}
			if (StringUtil.isNotEmpty(csdate)) {
				csdate = java.net.URLDecoder.decode(csdate, "UTF-8").trim();
			}
			
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		
		String sqlPart = " order by adddate desc  limit  "
				+ (page - 1) * pagesize + "," + pagesize;
		String sqlPart2 = " where  a.questionid = b.questionid and a.userid = ?  and b.questionid = c.id ";
		if (!(keyword == null || "".equals(keyword.trim()))) {
			if(keyword.indexOf("关键字")==-1){
				sqlPart2 += " and  (a.content like '%"+keyword+"%' or a.questiontitle like  '%" + keyword+"%')";
			}
		}
		if (!(csdate == null || "".equals(csdate.trim()))) {
			if(csdate.indexOf("回答时间")==-1){
				sqlPart2 = sqlPart2 + " and a.adddate >='" + csdate+" 00:00:00'";
			}
		}
		if (!(cedate == null || "".equals(cedate.trim()))) {
			sqlPart2 = sqlPart2 + " and a.adddate <'" + cedate+" 23:59:59'";
		}
		String sql = "select date_format(a.adddate,'%Y-%m-%d %H:%i:%S') as adddate,a.questiontitle,a.content,c.url from scanswer a ," 
			       + "  scquestion b ,zcarticle c ";
		
        count = new QueryBuilder("select count(1) from scanswer a, scquestion b, zcarticle c " + sqlPart2, memberID).executeInt();
        this.lastpage=(count+pagesize-1)/(pagesize);
        DataTable dt = new QueryBuilder(sql + sqlPart2 + sqlPart, memberID).executeDataTable();
        if (dt != null && dt.getRowCount() > 0) {
        	int rowCount = dt.getRowCount();
        	for (int i = 0; i < rowCount; i++) {
        		SCAnswer answer = new SCAnswer();
				answer.setAddDate(dt.getString(i, "adddate"));
				answer.setQuestionTitle(dt.getString(i, "questiontitle")) ;
				answer.setContent(dt.getString(i, "content"));
				answer.setUrl(dt.getString(i, "url"));
				sclist.add(answer);
        	}
        }
        page_Index = String.valueOf(page);
		page_count = String.valueOf(lastpage);
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		getPageDataList(param_map);
		
        Map<String,Object> map = new HashMap<String,Object>(); 
        ActionUtil.dealAction("wj00032", map, getRequest());
        
		return "input";
	}
}
