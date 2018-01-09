package cn.com.sinosoft.action.shop;
 
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.ProductCollection;
import cn.com.sinosoft.entity.Stow;
import cn.com.sinosoft.service.FavoritesService;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
public class StowAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7848950997954566541L;
	@Resource
	private FavoritesService favoritesService;
	private String memberID;
	private int page = 1;
	public static final int pagesize = 10;
	private int count;
	private int lastpage;
	private String productName;
	private String insureName;
	private String htmlPath;
	private String isPublish;
	private String startDate;
	private String endDate;
	private String createDate;
	private String productGenera;
	private List<Stow> listq = new ArrayList<Stow>();
	private String productId;
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	
	public List<Stow> getListq() {
		return listq;
	}

	public void setListq(List<Stow> listq) {
		this.listq = listq;
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

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public static int getPagesize() {
		return pagesize;
	}

	public Member getLoginMember() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		Member loginMember = memberService.load(id);
		return loginMember;
	}
	
	public String queryScan(){
		
		return "query";
	}

	public String scan() {

		memberID = getLoginMember().getId();
		
		try {
			if (StringUtil.isNotEmpty(productName)) {
				productName = java.net.URLDecoder.decode(productName, "UTF-8");
			}
			if (StringUtil.isNotEmpty(insureName)) {
				insureName = java.net.URLDecoder.decode(insureName, "UTF-8");
			}
			if (StringUtil.isNotEmpty(startDate)) {
				startDate = java.net.URLDecoder.decode(startDate, "UTF-8");
			}
			
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
		}
		
		String sqlPart2 = " where a.productId=b.productId and b.productId=s.productId and a.memberId= ? ";
		if (!(productName == null || "".equals(productName.trim()))) {
			if(productName.indexOf("产品名称")==-1){
				sqlPart2 += " and b.productName like '%" + productName+"%'";
			}
		}
		if (!(insureName == null || "".equals(insureName.trim()))) {
			if(insureName.indexOf("保险公司")==-1){
				sqlPart2 += " and b.InsureName like '%" + insureName+"%'";
			}
		}
		if (!(startDate == null || "".equals(startDate.trim()))) {
			if(startDate.indexOf("收藏时间")==-1){
				sqlPart2 = sqlPart2 + " and a.createDate>='" + startDate+" 00:00:00'";
			}
		}
		if (!(endDate == null || "".equals(endDate.trim()))) {
			sqlPart2 = sqlPart2 + " and a.createDate<='" + endDate+" 23:59:59'";
		}
		String sqlPart = " order by createDate desc,createtime desc  limit " + (page - 1) * pagesize + "," + pagesize;
		count = new QueryBuilder("select count(1) from ProductCollection a,SDProduct b, sdsearchrelaproduct s " + sqlPart2, memberID).executeInt();
		this.lastpage=(count+Stow.LIST_COUNT-1)/(Stow.LIST_COUNT);
		
		String sql = "select a.id,b.productname,b.InsureName, b.ProductGenera, b.productId,"
				+ " b.HtmlPath,b.IsPublish,date_format(a.createdate,'%Y-%m-%d') as CreateDate,"
				+ " s.AdaptPeopleInfoV3,s.BasePremValue , a.CollectionPrice  "
				+ " from ProductCollection a,SDProduct b, sdsearchrelaproduct s ";
		DataTable dt = new QueryBuilder(sql + sqlPart2 + sqlPart, memberID).executeDataTable();
        if (dt != null && dt.getRowCount() > 0) {
        	AjaxPriceAction ap = new AjaxPriceAction();
        	int rowCount = dt.getRowCount();
        	// 折后价
        	String disPrem = "";
        	// 原价
        	String basePrem = "";
        	//同比收藏价格便宜了
        	String cheapPrem = "";
        	for (int i = 0; i < rowCount; i++) {
        		Stow sw = new Stow();
				sw.setId(dt.getString(i, "id"));
				sw.setCreateDate(dt.getString(i, "CreateDate"));
				sw.setHtmlPath(dt.getString(i, "HtmlPath"));
				sw.setInsureName(dt.getString(i, "InsureName"));
				sw.setIsPublish(dt.getString(i, "IsPublish"));
				sw.setProductName(dt.getString(i, "productname"));
				sw.setProductGenera(dt.getString(i, "ProductGenera"));
				sw.setProductId(dt.getString(i, "productId"));
				sw.setAdaptPeopleInfoV3(dt.getString(i, "AdaptPeopleInfoV3"));
				basePrem = dt.getString(i, "BasePremValue");
				disPrem = ap.queryAjaxPrice(dt.getString(i, "productId"), "wj", memberID);
				sw.setDisPrem(disPrem);
				sw.setBasePrem(basePrem);
				if (StringUtil.isNotEmpty(basePrem) && StringUtil.isNotEmpty(disPrem)) {
					if (new BigDecimal(basePrem).compareTo(new BigDecimal(disPrem)) == 0) {
						sw.setBasePrem("");
					}
				}
				if(StringUtil.isNotEmpty(dt.getString(i, "CollectionPrice"))){ 
					cheapPrem = String.valueOf(new DecimalFormat("######0.00").format(Double.valueOf(dt.getString(i, "CollectionPrice")) - Double.valueOf(disPrem)) ) ;
					if(Double.valueOf(cheapPrem)<=0.00){
						cheapPrem = "0";
					}
				}else{
					cheapPrem = "0";
				}
				sw.setCheepPrem(cheapPrem);
				
				listq.add(sw);
        	}
        }
        page_Index = String.valueOf(page);
		page_count = String.valueOf(lastpage);
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		getPageDataList(param_map);
		
        Map<String,Object> map = new HashMap<String,Object>(); 
        ActionUtil.dealAction("wj00039", map, getRequest());
        return "input";
	}

	public void setProductGenera(String productGenera) {
		this.productGenera = productGenera;
	}

	public String getProductGenera() {
		return productGenera;
	}
	
	public String del(){
		Map<String, Object> tData = new HashMap<String, Object>();
		tData.put("status", "N");
		memberID = getLoginMember().getId();
		String sql = "delete from  ProductCollection where id= ?  and memberid= ? " ;
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			String sqltemp[] = new String[] { id , memberID };
			
			if (jtd.updateOrSaveOrDelete(sql , sqltemp)) {
				tData.put("status", "Y");
			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String add(){
		Map<String, Object> tData = new HashMap<String, Object>();
		tData.put("status", "N");
		Member member = getLoginMember();
		if (member == null) {
			return ajax(JSONObject.fromObject(tData).toString(), "text/html");
		}
		memberID = member.getId();
		
		ProductCollection productCollection=new ProductCollection();
		productCollection.setProductID(productId);
		productCollection.setMemberID(memberID);
		if (favoritesService.contains(productCollection)) {
			tData.put("status", "Y");
		} else {
			String flag=favoritesService.save(productCollection) ;
			if(StringUtil.isNotEmpty(flag)){
				tData.put("status", "Y");
			}
		} 
		
		if ("Y".equals(tData.get("status"))) {
			tData.put("id", new QueryBuilder("select id from ProductCollection where memberid=? and productId=?", memberID,productId).executeString());
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
}
