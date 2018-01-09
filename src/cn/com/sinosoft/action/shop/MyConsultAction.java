package cn.com.sinosoft.action.shop;
 
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.List;
import java.util.Map;
@ParentPackage("member")
public class MyConsultAction extends BaseShopAction {

	private static final long serialVersionUID = -4659619665223912186L;
	private int page = 1;
	public static final int pagesize = 5;
	private int count;
	private int lastpage;
	private int newPageSize;
	private List<Map<String, String>> cousultComment;
	
	private BindInfoForLoginService bindInfoForLoginService;
	
	public String show() {
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = null;
		if(!"tencent".equals(memberId)){
			   loginMember = memberService.load(memberId);
		}else{
			loginMember = bindInfoForLoginService.get(getSession("loginBindId").toString()).getmMember();
		}
		if(loginMember!=null){
			memberId = loginMember.getId();
		}
		String sql="SELECT a.title,c.replycontent,c.prop2,a.logo,a.url " +
				" FROM  ZCComment c  LEFT JOIN ZCArticle a ON a.ID=c.RelaID" +
				" WHERE c.prop1='"+memberId+"'  AND c.SiteID='221' AND c.isBuy='0' " +
				" AND c.CatalogType='1' AND c.VerifyFlag='X' ORDER BY c.VerifyFlag ASC, c.ID DESC";
		DataTable dt = new QueryBuilder(sql).executeDataTable(); 
		if(dt==null){
			count = 0;
		}else{
			count = dt.getRowCount();
		}
		String endSql = " limit " + (page - 1) * pagesize + "," + pagesize;
		JdbcTemplateData jtd = new JdbcTemplateData();
		this.lastpage = ((count + pagesize-1) / (pagesize));
		try {
			cousultComment = jtd.obtainData(sql+endSql);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return "list";
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

	public int getNewPageSize() {
		return newPageSize;
	}

	public void setNewPageSize(int newPageSize) {
		this.newPageSize = newPageSize;
	}


	public static int getPagesize() {
		return pagesize;
	}

	public List<Map<String, String>> getCousultComment() {
		return cousultComment;
	}

	public void setCousultComment(List<Map<String, String>> cousultComment) {
		this.cousultComment = cousultComment;
	}

}
