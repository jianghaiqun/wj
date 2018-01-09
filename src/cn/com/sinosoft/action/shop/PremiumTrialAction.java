package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.CarMenu;
import cn.com.sinosoft.entity.CarRateInformation;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.Dict;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.entity.Insurance;
import cn.com.sinosoft.entity.SDtargetInformation;
import cn.com.sinosoft.entity.ShowInsurance;
import cn.com.sinosoft.service.InsuranceService;
import cn.com.sinosoft.service.PremiumTrialService;
import cn.com.sinosoft.service.SDtargetInformationService;
import cn.com.sinosoft.service.ShowInsuranceService;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LiuXin
 *
 */
@ParentPackage("shop")
public class PremiumTrialAction extends BaseShopAction{

	private static final long serialVersionUID = -4006355141844738194L;
	private HttpServletRequest request = ServletActionContext.getRequest();
	private HttpSession session = ServletActionContext.getRequest().getSession();
	private SDtargetInformation sdTagetInformation= new SDtargetInformation();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private String today =sdf.format(new Date());
	private String insureEnddate = "";
	private List<City> citys = new ArrayList<City>();
	private List<Dict> carUsed = new ArrayList<Dict>();
	private List<Dict> carProperties = new ArrayList<Dict>();
	private List<Dict> models = new ArrayList<Dict>();
	private List<Dict> licenColor = new ArrayList<Dict>();
	private List<Dict> cardtype = new ArrayList<Dict>();
	private List<FDInsCom> coms = new ArrayList<FDInsCom>();
	private List<ShowInsurance> showInsurances = new ArrayList<ShowInsurance>();
	private CarMenu carMenu = new CarMenu();
	
	@Resource
	private PremiumTrialService pts;
	@Resource
	private InsuranceService insuranceService;
	@Resource
	private ShowInsuranceService siService;
	@Resource
	private SDtargetInformationService sdTargetIService;
	
	public String show(){
		return "show";
	}
	public String loadInformation(){
		init();
		return "loadInformation";
	}
	public String selectInsured(){
		init();
		SDtargetInformation sdtInformation= getSDtargetInformation(sdTagetInformation);
		session.setAttribute("sdTagetInformation", sdtInformation);
		sdTargetIService.save(sdtInformation);
		List<CarRateInformation> carRIs = pts.getCarRateInformations(sdtInformation.getRegionCode());
		List<CarMenu> carMenus = pts.getCarMenus();
		getListTrialPremium(sdtInformation,carRIs,carMenus);
		showInsurances = siService.findByQBs(sdtInformation.getId());
		return "selectInsured";
	}
	public String getChangePremmium(){
		logger.info("====改变保费===");
		init();
		SDtargetInformation sDtargetOld = (SDtargetInformation) session.getAttribute("sdTagetInformation");
		SDtargetInformation sDtargetNew  = getSDtargetInformation(sDtargetOld);
		sDtargetNew.setRegionCode(sdTagetInformation.getRegionCode());
		sDtargetNew.setLastInsureCom(sdTagetInformation.getLastInsureCom());
		sDtargetNew.setCarBuyTime(sdTagetInformation.getCarBuyTime());
		sDtargetNew.setCarPrice(sdTagetInformation.getCarPrice());
		sDtargetNew.setCreateDate(new Date());
		session.setAttribute("sdTagetInformation", sDtargetNew);
		sdTargetIService.save(sDtargetNew);
		deleteOldPremium(sDtargetOld.getId());
		List<CarRateInformation> carRIs = pts.getCarRateInformations(sDtargetNew.getRegionCode());
		List<CarMenu> carMenus = pts.getCarMenus();
		carMenu.setCode("CM004");
		carMenus.add(carMenu);
		getListTrialPremium(sDtargetNew,carRIs,carMenus);
		showInsurances = siService.findByQBs(sDtargetNew.getId());
		logger.info("========显示==={}", showInsurances.size());
		return "selectInsured";
	}
	
	public String showPremiumDetail(){
		logger.info("======保单详情=========");
		String comCode = request.getParameter("comCode");
		String cmType = request.getParameter("cmType");
		String serialNumber = request.getParameter("serialNumber");
		Insurance ins = pts.getPremiumDatail(comCode,cmType,serialNumber);
		logger.info("==============={}", ins.getCompany());
		JSONObject json = JSONObject.fromObject(ins);
		String jsonstr = json.toString();
		return ajax(jsonstr, "text/html");
	}
	private void getListTrialPremium(SDtargetInformation sdTInformation,List<CarRateInformation> carRIs,List<CarMenu> carMenus) {
		List<Insurance> insuranceList = new ArrayList<Insurance>();
		if(carRIs!=null&&carRIs.size()>0){
			for(CarRateInformation carRI : carRIs){
				if(carMenus!=null&&carMenus.size()>0){
					for(CarMenu cm : carMenus){
						Insurance ins = pts.getAllPremium(sdTInformation, carRI , cm);
						insuranceList.add(ins);
					}
				}
			}
		}
		saveInsuranceList(insuranceList);
		createShowInsurance(sdTInformation,carRIs,insuranceList);
		updateShowInsurance(sdTInformation.getId());
	}
	/**
	 * 
	 * 组装查询最小保费sql
	 */
	private void updateShowInsurance(String serialNo) {
		List<ShowInsurance> selectOrdinary = siService.getMinPremium(getSqlByPremiumType("ordinaryPremium",serialNo));
		if(selectOrdinary!=null&&selectOrdinary.size()>0){
			for(ShowInsurance so : selectOrdinary){
				ShowInsurance updateso = siService.get(so.getId());
				updateso.setOrdinaryFlag("Y");
				siService.update(updateso);
			}
		}
		List<ShowInsurance> selecteconomic = siService.getMinPremium(getSqlByPremiumType("economicPremium",serialNo));
		if(selecteconomic!=null&&selecteconomic.size()>0){
			for(ShowInsurance se : selecteconomic){
				ShowInsurance updatese = siService.get(se.getId());
				updatese.setEconomicFlag("Y");
				siService.update(updatese);
			}
		}
		List<ShowInsurance> selectluxury = siService.getMinPremium(getSqlByPremiumType("luxuryPremium",serialNo));
		if(selectluxury!=null&&selectluxury.size()>0){
			for(ShowInsurance sl : selectluxury){
				ShowInsurance updatesl = siService.get(sl.getId());
				updatesl.setLuxuryFlag("Y");
				siService.update(updatesl);
			}
		}
		List<ShowInsurance> selectZDY = siService.getMinPremium(getSqlByPremiumType("zdyPremium",serialNo));
		if(selectZDY!=null&&selectZDY.size()>0){
			for(ShowInsurance sZ : selectZDY){
				ShowInsurance updatesZ = siService.get(sZ.getId());
				updatesZ.setZdyFlag("Y");
				siService.update(updatesZ);
			}
		}
	}
	private String getSqlByPremiumType(String premiumType,String serialNo){
		String sql = "select min(id) ,min("+premiumType+") from showinsurance where serialNumber = '"+ serialNo +"' and " +premiumType+ " is not null";
		return sql;
	}
	private void createShowInsurance(SDtargetInformation sdTInformation,
			List<CarRateInformation> carRIs, List<Insurance> insuranceList) {
		List<ShowInsurance> showIns = new ArrayList<ShowInsurance>();
		if(carRIs!=null&&carRIs.size()>0){
			for(CarRateInformation crif : carRIs){
				String comCode = crif.getCompanyCode();
				ShowInsurance si = new ShowInsurance();
				if(insuranceList!=null&&insuranceList.size()>0){
					for(Insurance ins : insuranceList){
						si.setComCode(comCode);
						si.setCreateDate(new Date());
						si.setModifyDate(new Date());
						si.setSerialNumber(ins.getSerialNumber());
						if(comCode.equals(ins.getCompany())&&"CM001".equals(ins.getCarMenuCode())){
							si.setOrdinaryPremium(ins.getTotle());
						}
						if(comCode.equals(ins.getCompany())&&"CM002".equals(ins.getCarMenuCode())){
							si.setEconomicPremium(ins.getTotle());
						}
						if(comCode.equals(ins.getCompany())&&"CM003".equals(ins.getCarMenuCode())){
							si.setLuxuryPremium(ins.getTotle());
						}
						if(comCode.equals(ins.getCompany())&&"CM004".equals(ins.getCarMenuCode())){
							si.setZdyPremium(ins.getTotle());
						}
						showIns.add(si);
					}
				}
			}
		}
		saveShowInsuranceList(showIns);
	}
	private void saveShowInsuranceList(List<ShowInsurance> showIns) {
		if(showIns!=null&&showIns.size()>0){
			for(ShowInsurance si : showIns){
				siService.save(si);
			}
		}
	}
	private void saveInsuranceList(List<Insurance> insuranceList) {
		if(insuranceList!=null&&insuranceList.size()>0){
			for(Insurance ins : insuranceList){
				insuranceService.save(ins);
			}
		}
	}
	private SDtargetInformation getSDtargetInformation(SDtargetInformation sdinformation) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMddHHmmssSSS");
		String id = sdf1.format(new Date());
		SDtargetInformation sti = new SDtargetInformation();
		logger.info("=====id======={}", id);
		sti.setId(id);
		sti.setRegionCode(sdinformation.getRegionCode());
		sti.setLicenseNo(sdinformation.getLicenseNo());
		sti.setCarBuyTime(sdinformation.getCarBuyTime());
		sti.setModelsCode("C001");
		sti.setCarBrand(sdinformation.getCarBrand());
		sti.setCarPrice(sdinformation.getCarPrice());
		logger.info("===getPassenger====={}=====getApprovedLoad===={}", sdinformation.getPassenger(), sdinformation.getApprovedLoad());
		if(sdinformation.getPassenger()==null||sdinformation.getPassenger()==0){
			sti.setPassenger(5);
		}else{
			sti.setPassenger(sdinformation.getPassenger());
		}
		if(sdinformation.getApprovedLoad()==null||sdinformation.getApprovedLoad()==0.0){
			sti.setApprovedLoad(2.0);
		}else{
			sti.setApprovedLoad(sdinformation.getApprovedLoad());
		}
		sti.setUsedYear(getUsedYear(sdf.format(sdinformation.getCarBuyTime())));
		sti.setCarUsed("U002");
		sti.setCarProperties("P001");
		sti.setLastInsureCom(sdinformation.getLastInsureCom());
		sti.setInsureder(sdinformation.getInsureder());
		sti.setSex(sdinformation.getSex());
		sti.setBirthday(sdinformation.getBirthday());
		sti.setTelNo(sdinformation.getTelNo());
		sti.setEmail(sdinformation.getEmail());
		return sti;
	}
	/**
	 * 
	 * 删除第一次试算的保费、标的信息
	 */
	private void deleteOldPremium(String id) {
		sdTargetIService.delete(id);
		String sql = "delete from insurance where serialNumber = " + id;
		String sISql = "delete from showinsurance where serialNumber = " + id;
		pts.deleteOldPremium(sql);
		pts.deleteOldPremium(sISql);
	}
	public void init(){
		Date endDate = pts.addYear(new Date(), 1);
		insureEnddate = sdf.format(endDate);
		citys = pts.getRegionCode();
//		carUsed = pts.getDictsByCode("U");
//		carProperties = pts.getDictsByCode("P");
//		models = pts.getDictsByCode("C");
//		licenColor = pts.getDictsByCode("CC");
//		cardtype = pts.getDictsByCode("CT");
		coms = pts.getCompanys();	
	}
	/**
	 * 
	 * 根据购车时间得到使用年限
	 */
	private Long getUsedYear(String startDate){
		try {
			Long usedYear = pts.getYearNO(new Date(), sdf.parse(startDate));
			logger.info("====使用年限========{}", usedYear.toString());
			return usedYear;
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return 0l;
	}
	/**
	 * 算出保单失效日期
	 */
	public String getEndDate(){
		String startDate = request.getParameter("startDate");
		Date start = null;
		try {
			start = sdf.parse(startDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		Date endDate = pts.addYear(start, 1);
		logger.info("====失效年限========{}", endDate);
		return ajax(sdf.format(endDate), "text/html");
	}
	public String getToday() {
		return today;
	}
	public void setToday(String today) {
		this.today = today;
	}
	public String getInsureEnddate() {
		return insureEnddate;
	}
	public void setInsureEnddate(String insureEnddate) {
		this.insureEnddate = insureEnddate;
	}
	public List<City> getCitys() {
		return citys;
	}
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
	public List<Dict> getCarUsed() {
		return carUsed;
	}
	public void setCarUsed(List<Dict> carUsed) {
		this.carUsed = carUsed;
	}
	public List<Dict> getCarProperties() {
		return carProperties;
	}
	public void setCarProperties(List<Dict> carProperties) {
		this.carProperties = carProperties;
	}
	public List<Dict> getModels() {
		return models;
	}
	public void setModels(List<Dict> models) {
		this.models = models;
	}
	public List<Dict> getLicenColor() {
		return licenColor;
	}
	public void setLicenColor(List<Dict> licenColor) {
		this.licenColor = licenColor;
	}
	public List<Dict> getCardtype() {
		return cardtype;
	}
	public void setCardtype(List<Dict> cardtype) {
		this.cardtype = cardtype;
	}
	public List<FDInsCom> getComs() {
		return coms;
	}
	public void setComs(List<FDInsCom> coms) {
		this.coms = coms;
	}
	public SDtargetInformation getSdTagetInformation() {
		return sdTagetInformation;
	}
	public void setSdTagetInformation(SDtargetInformation sdTagetInformation) {
		this.sdTagetInformation = sdTagetInformation;
	}
	public List<ShowInsurance> getShowInsurances() {
		return showInsurances;
	}
	public void setShowInsurances(List<ShowInsurance> showInsurances) {
		this.showInsurances = showInsurances;
	}
	public CarMenu getCarMenu() {
		return carMenu;
	}
	public void setCarMenu(CarMenu carMenu) {
		this.carMenu = carMenu;
	}

}
