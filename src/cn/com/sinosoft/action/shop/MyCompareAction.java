package cn.com.sinosoft.action.shop;
 
import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MyTrailNonAuto;
import cn.com.sinosoft.entity.RiskAppFactor;
import cn.com.sinosoft.entity.TrailProduct;
import cn.com.sinosoft.service.CompareService;
import cn.com.sinosoft.service.MyTrailNonAutoService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.webservice.compareInfo.CompareInfoServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ParentPackage("member")
public class MyCompareAction extends BaseShopAction {

	private static final long serialVersionUID = -4659619665223912186L;
	private List<MyTrailNonAuto> myTrails = new ArrayList<MyTrailNonAuto>();
	private String serialNumber;
	private String eriskType;
	private String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
	private CompareInfoServiceStub.FEMRiskCompareProperties[] fcps;
	private ProductInfoWebServiceStub.FMRisk[] fmr;
	private List<Map<String, Object>> compareInformation = new ArrayList<Map<String, Object>>();
	private List<RiskAppFactor> rafs = new ArrayList<RiskAppFactor>();
	private List<RiskAppFactor> dutyFactor = new ArrayList<RiskAppFactor>();
	private Pager pager = new Pager();
	private String nowPage = "";
	private String pageCount = "";
	@Resource
	private CompareService compareService;
	@Resource
	private MyTrailNonAutoService myTNAutoService;

	public String show() {
		
		Pager p = new Pager();
		String page = getRequest().getParameter("page");
		if (StringUtil.isNotEmpty(page)) {
			p.setPageNumber(Integer.valueOf(page));
		}
		p.setPageSize(5);
		p.setOrderBy("createDate");
		p.setOrderType(OrderType.desc);
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("memeberId","=", memberId));
		p.setProperties(qbs);
		pager = myTNAutoService.findByPagerQbs(p);
		List<MyTrailNonAuto> list= pager.getList();
		for(int i=0;i<list.size();i++){
			List<TrailProduct> tlist = list.get(i).getTrailProducts();
			for(int j=0;j<tlist.size();j++){
				String initPrem = tlist.get(j).getInitPrem();
				tlist.get(j).setInitPrem(initPrem);
			}
		}
		
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(pager.getTotalCount()));
		page_Index = String.valueOf(pager.getPageNumber());
		page_count = String.valueOf(pager.getPageCount());
		getPageDataList(param_map);
		
		return "list";
	}

	public String detail() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			List<TrailProduct> trailProducts = myTNAutoService
					.getTrailProductBySerialNo(serialNumber);
			String compareProducts[] = new String[trailProducts.size()];
			if (trailProducts != null && trailProducts.size() > 0) {
				for (int i = 0; i < trailProducts.size(); i++) {
					compareProducts[i] = trailProducts.get(i).getProductCode();
				}
			}
			fcps = compareService.getCompareInformation(eriskType);
			map = compareService.getNonAutoTrial(compareProducts, fcps);
			fmr = (FMRisk[]) map.get("fmr");
			compareInformation = (List<Map<String, Object>>) map
					.get("compareInformation");
			rafs = (List<RiskAppFactor>) map.get("rafs");
			dutyFactor = (List<RiskAppFactor>) map.get("dutyFactor");
			if (compareInformation != null && compareInformation.size() > 0) {
				for (Map<String, Object> mp : compareInformation) {
					mp.put("companyLog",
							Config.interfaceMap.get("companyLog").toString()
									+ compareService.getCompanyInformation(mp.get(
											"riskCode").toString()));
				}
			}
			getSession().put("rafs", rafs);
			getSession().put("dutyFactor", dutyFactor);
			return "detail";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError("查询产品失败，请联系管理员！");
			return ERROR;
		}
	}

	/**
	 * 下一页
	 */
	public String turnDownPage() {
		logger.info("===PageNumber===={}==PageCount==={}", nowPage, pageCount);
		Pager p = new Pager();
		p.setPageSize(5);
		p.setOrderBy("createDate");
		p.setOrderType(OrderType.desc);
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("memeberId","=", memberId));
		p.setProperties(qbs);
		int nowPageInt = Integer.parseInt(nowPage);
		int pageCountInt = Integer.parseInt(pageCount);
		if (pageCountInt >= 0) {
			if (pageCountInt > nowPageInt && nowPageInt > 0) {
				nowPageInt = nowPageInt + 1;
				p.setPageNumber(nowPageInt);
			} else {
				p.setPageNumber(pageCountInt);
			}
		} else {
			p.setPageNumber(0);
		}
		pager = myTNAutoService.findByPagerQbs(p);
		return "list";
	}
	/**
     * 上一页
     */
	public String turnUpPage(){
		logger.info("===PageNumber===={}==PageCount==={}", nowPage, pageCount);
		Pager p = new Pager();
		p.setPageSize(5);
		p.setOrderBy("createDate");
		p.setOrderType(OrderType.desc);
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("memeberId","=", memberId));
		p.setProperties(qbs);
		int nowPageInt = Integer.parseInt(nowPage);
		int pageCountInt = Integer.parseInt(pageCount);
		if(pageCountInt>=0){
			if(nowPageInt>=2&&nowPageInt<=pageCountInt){
				nowPageInt = nowPageInt-1;
				p.setPageNumber(nowPageInt);
			}else{
				p.setPageNumber(1);
			}
		}else{
			p.setPageNumber(0);
		}
		pager = myTNAutoService.findByPagerQbs(p);
		return "list";
	}
	/**
	 * 
	 * 跳页
	 */
	public String jumpToPage(){
		Pager p = new Pager();
		p.setPageSize(5);
		p.setOrderBy("createDate");
		p.setOrderType(OrderType.desc);
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("memeberId","=", memberId));
		p.setProperties(qbs);
		int nowPageInt = Integer.parseInt(nowPage);
		int pageCountInt = Integer.parseInt(pageCount);
		if(nowPageInt>0&&nowPageInt<=pageCountInt){
			p.setPageNumber(nowPageInt);
		}else if(nowPageInt<0){
			p.setPageNumber(0);
		}else if(nowPageInt>pageCountInt){
			p.setPageNumber(pageCountInt);
		}
		pager = myTNAutoService.findByPagerQbs(p);
		return "list";
	}
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}
	public List<MyTrailNonAuto> getMyTrails() {
		return myTrails;
	}

	public void setMyTrails(List<MyTrailNonAuto> myTrails) {
		this.myTrails = myTrails;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public CompareInfoServiceStub.FEMRiskCompareProperties[] getFcps() {
		return fcps;
	}

	public void setFcps(CompareInfoServiceStub.FEMRiskCompareProperties[] fcps) {
		this.fcps = fcps;
	}

	public ProductInfoWebServiceStub.FMRisk[] getFmr() {
		return fmr;
	}

	public void setFmr(ProductInfoWebServiceStub.FMRisk[] fmr) {
		this.fmr = fmr;
	}

	public List<RiskAppFactor> getRafs() {
		return rafs;
	}

	public void setRafs(List<RiskAppFactor> rafs) {
		this.rafs = rafs;
	}

	public List<RiskAppFactor> getDutyFactor() {
		return dutyFactor;
	}

	public void setDutyFactor(List<RiskAppFactor> dutyFactor) {
		this.dutyFactor = dutyFactor;
	}

	public List<Map<String, Object>> getCompareInformation() {
		return compareInformation;
	}

	public void setCompareInformation(
			List<Map<String, Object>> compareInformation) {
		this.compareInformation = compareInformation;
	}

	public String getEriskType() {
		return eriskType;
	}

	public void setEriskType(String eriskType) {
		this.eriskType = eriskType;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}

	public String getNowPage() {
		return nowPage;
	}

	public void setNowPage(String nowPage) {
		this.nowPage = nowPage;
	}

	public String getPageCount() {
		return pageCount;
	}

	public void setPageCount(String pageCount) {
		this.pageCount = pageCount;
	}

}
