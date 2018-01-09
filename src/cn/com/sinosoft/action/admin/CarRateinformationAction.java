package cn.com.sinosoft.action.admin;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.Pager.OrderType;
import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.entity.CarRateInformation;
import cn.com.sinosoft.entity.City;
import cn.com.sinosoft.entity.FDInsCom;
import cn.com.sinosoft.service.CarRateInformationService;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LiuXin
 *
 */
@ParentPackage("admin")
public class CarRateinformationAction extends BaseAdminAction{
	private static final long serialVersionUID = 1441320379291182940L;
	private Pager pager = new Pager();
	private CarRateInformation carRateIF = new CarRateInformation();
	private List<City> citys = new ArrayList<City>();
	private List<FDInsCom> coms = new ArrayList<FDInsCom>();
	@Resource
	private CarRateInformationService carRateIFService;
	public String list(){
		init();
		Pager pl = new Pager();
		pl.setOrderBy("companyCode");
		pl.setOrderType(OrderType.asc);
		pager = carRateIFService.findByPagerQbs(pl);
		return "list";
	}
	public String add(){
		init();
		return "add";
	}
	public String modify(){
		init();
		carRateIF = carRateIFService.load(id);
		return "modify";
	}
	public String save(){
		String codeId = findCRIisExit();
		if(codeId==null){
			carRateIF.setCreateDate(new Date());
			carRateIF.setModifyDate(new Date());
			if(carRateIF.getDicount()==null||"".equals(carRateIF.getDicount())){
				carRateIF.setDicount(1.0);
			}
			carRateIFService.save(carRateIF);
		}else{
			updateCRI(codeId);
		}
		init();
		Pager pl = new Pager();
		pl.setOrderBy("companyCode");
		pl.setOrderType(OrderType.asc);
		pager = carRateIFService.findByPagerQbs(pl);
		return "list";
	}
	
	private void updateCRI(String codeId) {
		CarRateInformation crif = carRateIFService.get(codeId);
		if(carRateIF.getDicount()==null||"".equals(carRateIF.getDicount())){
			crif.setDicount(1.0);
		}else{
			crif.setDicount(carRateIF.getDicount());
		}
		crif.setFlag(carRateIF.getFlag());
		crif.setModelType(carRateIF.getModelType());
		crif.setModifyDate(new Date());
		carRateIFService.update(crif);
	}
	private String findCRIisExit() {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>(); 
		qbs.add(createQB("companyCode","=",carRateIF.getCompanyCode()));
		qbs.add(createQB("regionCode","=",carRateIF.getRegionCode()));
		List<CarRateInformation> list = carRateIFService.findByQBs(qbs, "id", "desc");
		if(list!=null&&list.size()>0){
			return list.get(0).getId();
		}else{
			return null;
		}
	}
	private void init(){
		citys = carRateIFService.getRegionCode();
        coms = carRateIFService.getCompanys();	
	}
	
	private QueryBuilder createQB(String property,String sign,String value){
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
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
	public CarRateInformation getCarRateIF() {
		return carRateIF;
	}
	public void setCarRateIF(CarRateInformation carRateIF) {
		this.carRateIF = carRateIF;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}

}
