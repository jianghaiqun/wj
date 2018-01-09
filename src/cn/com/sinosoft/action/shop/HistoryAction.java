package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ScanRecord;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.inter.ActionUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ParentPackage("member")
public class HistoryAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2379953643233459997L;
	private String memberID;
	private int page = 1;
	public static final int pagesize = 10;
    private int count;
    private int lastpage;
	private String productName;
	private String insureName;
	private String htmlPath;
	private String isPublish;
	private String createDate;
	private List<ScanRecord> listq = new ArrayList<ScanRecord>();

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getInsureName() {
		return insureName;
	}

	public void setInsureName(String insureName) {
		this.insureName = insureName;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public String scan() {

		memberID = getLoginMember().getId();
		String[] temp = {memberID};
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql = "select a.sid,b.productname pname,b.InsureName iname, b.productId,"
				+ " b.HtmlPath hpath,b.IsPublish publish,a.createdate cdate from SDHistory a,SDProduct b "
				+ " where a.productId=b.productId  and memberId=? "
				+ " order by a.createdate desc,"
				+ " a.createtime desc limit  " + (page - 1) * pagesize + ","
				+ pagesize;
		String sqlcount = "select a.sid, b.productname pname,b.InsureName iname,b.productId,"
			+ " b.HtmlPath hpath,b.IsPublish publish,a.createdate cdate from SDHistory a,SDProduct b "
			+ " where a.productId=b.productId  and memberId=?";
			
		try {
			List<Map> listcount = jtd.obtainData(sqlcount,temp);    
			List<Map> list = jtd.obtainData(sql,temp);    
			count=listcount.size();
			
			if(count<=20)
			{ this.lastpage=(count+9)/10;}
			else {count=20;
			 this.lastpage=(count+9)/10;} 
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				String sid=(String)map.get("SID");
				productName = (String) map.get("ProductName");
				insureName = (String) map.get("InsureName");
				htmlPath = (String) map.get("HtmlPath");
				isPublish = (String) map.get("IsPublish");
				createDate = (String) map.get("CreateDate");
				ScanRecord srd = new ScanRecord();
				srd.setSid(sid);
				srd.setCreateDate(createDate);
				srd.setHtmlPath(htmlPath);
				srd.setInsureName(insureName);
				srd.setIsPublish(isPublish);
				srd.setProductName(productName);
				srd.setProductId((String)map.get("ProductID"));
				listq.add(srd);

			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
	        Map<String,Object> map = new HashMap<String,Object>(); 
	        try{ActionUtil.dealAction("wj00037", map, getRequest());}
	        catch(Exception e){
				logger.error(e.getMessage(), e);
	        }finally{
			return "input";}
		
	}

	
	public String delete(){
		memberID = getLoginMember().getId();
		String sql = "delete from  SDHistory where sid=? and memberid=?" ;
		String[] sqltemp = {id,memberID};
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			jtd.updateOrSaveOrDelete(sql,sqltemp);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		return scan();
	}
	
	
	
	public void setListq(List<ScanRecord> listq) {
		this.listq = listq;
	}

	public List<ScanRecord> getListq() {
		return listq;
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

}
