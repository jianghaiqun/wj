package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDtargetInformation;
import cn.com.sinosoft.entity.ShowInsurance;
import cn.com.sinosoft.service.MyTrialService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuXin
 *
 */
@ParentPackage("shop")
public class MyTrialAction extends BaseShopAction {

	private static final long serialVersionUID = -1932931907252727020L;
	private List<SDtargetInformation> sdInformations = new ArrayList<SDtargetInformation>();
	private SDtargetInformation sdTagetInformation = new SDtargetInformation();
	private List<City> citys = new ArrayList<City>();
	private List<FDInsCom> coms = new ArrayList<FDInsCom>();
	private List<ShowInsurance> showInsurances = new ArrayList<ShowInsurance>();
	String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
	@Resource
	private MyTrialService myTrialService;
	public String list(){
		init();
		sdInformations = myTrialService.getsSDInformations(memberId,sdTagetInformation);
		return "list";
	}
	public String detail(){
		init();
		String serialNumber = getRequest().getParameter("serialNumber");
		showInsurances = myTrialService.getShowInsurances(serialNumber);
		return "detail";
	}
	public String selectShowInsurance(){
		init();
		sdInformations = myTrialService.getsSDInformations(memberId,sdTagetInformation);
		return "list";
	}
	private void init(){
		citys = myTrialService.getRegionCode();
		coms = myTrialService.getCompanys();	
	}
	public List<SDtargetInformation> getSdInformations() {
		return sdInformations;
	}
	public void setSdInformations(List<SDtargetInformation> sdInformations) {
		this.sdInformations = sdInformations;
	}
	public SDtargetInformation getSdTagetInformation() {
		return sdTagetInformation;
	}
	public void setSdTagetInformation(SDtargetInformation sdTagetInformation) {
		this.sdTagetInformation = sdTagetInformation;
	}
	public List<City> getCitys() {
		return citys;
	}
	public void setCitys(List<City> citys) {
		this.citys = citys;
	}
	public List<FDInsCom> getComs() {
		return coms;
	}
	public void setComs(List<FDInsCom> coms) {
		this.coms = coms;
	}
	public List<ShowInsurance> getShowInsurances() {
		return showInsurances;
	}
	public void setShowInsurances(List<ShowInsurance> showInsurances) {
		this.showInsurances = showInsurances;
	}

}
