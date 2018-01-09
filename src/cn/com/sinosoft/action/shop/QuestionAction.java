package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SCQuestion;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.SCQuestionService;
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

 
@ParentPackage("member")
public class QuestionAction extends BaseShopAction{

	/**
	 * 
	 */
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


	private List<SCQuestion> sclist = new ArrayList<SCQuestion>();


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
	 * 保险问答.
	 * @return
	 */
	public String questionList(){
		
		return "questionList";
	}

	
	@Resource
	private SCQuestionService scQuestionService;
	
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
	
	public String ask(){

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
		
		String sqlPart2 = " where b.userid = ? ";
		if (!(keyword == null || "".equals(keyword.trim()))) {
			if(keyword.indexOf("关键字")==-1){
				sqlPart2 += " and  ( b.title like '%" + keyword+"%' or b.content like '%" + keyword+"%' )";
			}
		}
		if (!(csdate == null || "".equals(csdate.trim()))) {
			if(csdate.indexOf("提问时间")==-1){
				sqlPart2 = sqlPart2 + " and b.adddate >='" + csdate+" 00:00:00'";
			}
		}
		if (!(cedate == null || "".equals(cedate.trim()))) {
			sqlPart2 = sqlPart2 + " and b.adddate <'" + cedate+" 23:59:59'";
		}
		String sql = "select date_format(b.adddate,'%Y-%m-%d %H:%i:%S') as AddDate,b.title,b.content,c.url from  " 
			       + " scquestion b Left JOIN zcarticle c  on  b.questionid = c.id ";
        
        count = new QueryBuilder("select count(1) from scquestion b Left JOIN zcarticle c  on  b.questionid = c.id " + sqlPart2, memberID).executeInt();
        this.lastpage=(count+pagesize-1)/(pagesize);
        DataTable dt = new QueryBuilder(sql + sqlPart2 + sqlPart, memberID).executeDataTable();
        if (dt != null && dt.getRowCount() > 0) {
        	int rowCount = dt.getRowCount();
        	for (int i = 0; i < rowCount; i++) {
        		String url = dt.getString(i, "url");
				SCQuestion question = new SCQuestion();
				question.setAddDate(dt.getString(i, "AddDate"));
				question.setContent(dt.getString(i, "content"));
				question.setTitle(dt.getString(i, "title"));
				if(StringUtil.isNotEmpty(url)){
					question.setUrl(url);
				}
				sclist.add(question);
        	}
        }

		page_Index = String.valueOf(page);
		page_count = String.valueOf(lastpage);
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		getPageDataList(param_map);
		
        Map<String,Object> map = new HashMap<String,Object>(); 
        ActionUtil.dealAction("wj00026", map, getRequest());
        return "input";
	}

	public void setSclist(List<SCQuestion> sclist) {
		this.sclist = sclist;
	}

	public List<SCQuestion> getSclist() {
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
