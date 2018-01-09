package cn.com.sinosoft.action.car;

import cn.com.sinosoft.action.shop.BaseShopAction;
import cn.com.sinosoft.bean.WxCity;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
/**
 * 车险微信
 * @author cuishigang
 *
 */
public class CarReponseAction extends BaseShopAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5984103310264464764L;

	private String kilometer;//加满油后行驶的公里数
	private String liter;//油箱加满几公升
	private String literUSD;//油价 / 公升
	
	private String pkilometer;//每公升油可以行驶
	private String pliter;// 每跑一公里需用
	private String pliter_cc;// 每跑一公里需用cc
	private String pliterUSD;// 每公里的油钱是
	
	private String checkCityCode;
	private String checkCityName;
	private String checkCityPosition_x;
	private String checkCityPosition_y;
	
	private String openID;
	
	private List<WxCity> hotcitylist = new ArrayList<WxCity>();//热门城市
	
	private List<WxCity> othercitylist = new ArrayList<WxCity>();//其他城市
	/**
	 * 油耗查询
	* @Title: queryFule
	* @return String    返回类型 
	* @author XXX
	 */
	public String queryFule(){
		
		try{
			BigDecimal tkilometer = new BigDecimal(kilometer);
			BigDecimal tliter = new BigDecimal(liter);
			BigDecimal tliterUSD = new BigDecimal(literUSD);
			pkilometer = String.valueOf(tkilometer.divide(tliter,2,BigDecimal.ROUND_HALF_UP));
			pliter = String.valueOf(tliter.divide(tkilometer,2, BigDecimal.ROUND_HALF_UP));
			pliter_cc = String.valueOf(tliter.multiply(new BigDecimal("1000")).divide(tkilometer,2, BigDecimal.ROUND_HALF_UP));
			pliterUSD = String.valueOf(tliterUSD.multiply(tliter).divide(tkilometer,2, BigDecimal.ROUND_HALF_UP));
		}catch(Exception e){
			pkilometer = "0.00";
			pliter = "0.00";
			pliter_cc = "0.00";
			pliterUSD = "0.00";
			String[] argArr = {kilometer, liter, literUSD};
			logger.error("车险微信，油耗查询失败！（kilometer：{};liter：{};literUSD：{}",argArr);
			logger.error(e.getMessage(), e);
		}
		
		return "fuleresult";
	}
	/**
	 * 油耗初始化
	* @Title: initFule 
	* @return String    返回类型 
	* @author XXX
	 */
	public String initFule(){
		
		return "per";
	}
	/**
	 * 路况初始化
	* @Title: roadQuery 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author XXX
	 */
	public String roadQueryInit(){
		
		QueryBuilder qb = null;
		DataTable dt = null;
		//如果已经浏览过，则直接跳转到路况查询页
		String flag = this.getRequest().getParameter("flag");
		try{
			
			if(!("initroad").equals(flag) && StringUtil.isNotEmpty(openID)){
				 qb = new QueryBuilder(" SELECT OpenID,CityCode FROM CarBrowseRecord WHERE OpenID = ? ");
				 qb.add(this.openID);
				 dt = qb.executeDataTable();
				 if(dt.getRowCount()>=1){
					 if(StringUtil.isNotEmpty(dt.getString(0, "CityCode"))){
						    qb = new QueryBuilder(" SELECT CodeValue,CodeName,prop1,prop2 FROM zdcode WHERE codetype='WXCITY' AND CodeValue=?");
							qb.add(dt.getString(0, "CityCode"));
						    dt = qb.executeDataTable();
							if(dt!=null && dt.getRowCount()>=1){
								checkCityName = dt.getString(0, "CodeName");
								checkCityPosition_x = dt.getString(0, "prop1");
								checkCityPosition_y = dt.getString(0, "prop2");
							}
							return "citymap";
					 }
				 }
			}
			qb = new QueryBuilder(" SELECT CodeValue,CodeName,prop1,prop2 FROM zdcode WHERE codetype='WXCITY' AND Prop3='hot' ");
			dt = qb.executeDataTable();
			if(dt!=null && dt.getRowCount()>=1){
				int tLen = dt.getRowCount();
				for(int i=0;i<tLen;i++){
					WxCity tWxCity = new WxCity();
					tWxCity.setCityCode(dt.getString(i, "CodeValue"));
					tWxCity.setCityName(dt.getString(i, "CodeName"));
					tWxCity.setPosition_x(dt.getString(i, "prop1"));
					tWxCity.setPosition_y(dt.getString(i, "prop2"));
					tWxCity.setCityType(dt.getString(i, "hot"));
					hotcitylist.add(tWxCity);
				}
			}
		    qb = new QueryBuilder(" SELECT CodeValue,CodeName,prop1,prop2 FROM zdcode WHERE codetype='WXCITY' AND Prop3='other' ");
			dt = qb.executeDataTable();
			if(dt!=null && dt.getRowCount()>=1){
				int tLen = dt.getRowCount();
				for(int i=0;i<tLen;i++){
					WxCity tWxCity = new WxCity();
					tWxCity.setCityCode(dt.getString(i, "CodeValue"));
					tWxCity.setCityName(dt.getString(i, "CodeName"));
					tWxCity.setPosition_x(dt.getString(i, "prop1"));
					tWxCity.setPosition_y(dt.getString(i, "prop2"));
					tWxCity.setCityType(dt.getString(i, "hot"));
					othercitylist.add(tWxCity);
				}
			}
		}catch(Exception e){
			logger.error("类CarReponseAction，执行roadQueryInit异常" + e.getMessage(), e);
		}
		
		return "city";
	}
	/**
	 * 路况查询
	* @Title: roadQuery 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @return String    返回类型 
	* @author XXX
	 */
	public String roadQuery(){
		
		try{
			QueryBuilder qb = new QueryBuilder(" SELECT CodeValue,CodeName,prop1,prop2 FROM zdcode WHERE codetype='WXCITY' AND CodeValue=?  ");
			qb.add(checkCityCode);
			DataTable dt = qb.executeDataTable();
			if(dt!=null && dt.getRowCount()>=1){
				checkCityName = dt.getString(0, "CodeName");
				checkCityPosition_x = dt.getString(0, "prop1");
				checkCityPosition_y = dt.getString(0, "prop2");
			}

			//统计微信访问记录
			qb = new QueryBuilder(" SELECT OpenID,CityCode FROM CarBrowseRecord WHERE OpenID = ? ");
			qb.add(this.openID);
			dt = qb.executeDataTable();
			if(dt.getRowCount()>=1){
				qb = new QueryBuilder(" UPDATE CarBrowseRecord SET CityCode = ?,ModifyDate=? WHERE OpenID = ? ");
				qb.add(checkCityCode);
				qb.add(PubFun.getCurrent());
				qb.add(this.openID);
				qb.executeNoQuery();
			}else{
				qb = new QueryBuilder(" INSERT INTO CarBrowseRecord VALUES(?,?,?,?,?) ");
				qb.add(openID);
				qb.add(checkCityCode);
				qb.add("");
				qb.add(PubFun.getCurrent());
				qb.add(PubFun.getCurrent());
				qb.executeNoQuery();
			}
		}catch(Exception e){
			logger.error("类CarReponseAction，执行roadQuery异常"+ e.getMessage(), e);
		}
		
		return "citymap";
	}
	/**
	 * 违章查询--调用第三方
	* @Title: violate 
	* @return String    返回类型 
	* @author XXX
	 */
	public String violate(){
		
		
		return "violate";
	}
	public String getKilometer() {
		return kilometer;
	}
	public void setKilometer(String kilometer) {
		this.kilometer = kilometer;
	}
	public String getLiter() {
		return liter;
	}
	public void setLiter(String liter) {
		this.liter = liter;
	}
	public String getLiterUSD() {
		return literUSD;
	}
	public void setLiterUSD(String literUSD) {
		this.literUSD = literUSD;
	}
	public String getPkilometer() {
		return pkilometer;
	}
	public void setPkilometer(String pkilometer) {
		this.pkilometer = pkilometer;
	}
	public String getPliter() {
		return pliter;
	}
	public void setPliter(String pliter) {
		this.pliter = pliter;
	}
	public String getPliterUSD() {
		return pliterUSD;
	}
	public void setPliterUSD(String pliterUSD) {
		this.pliterUSD = pliterUSD;
	}
	public String getPliter_cc() {
		return pliter_cc;
	}
	public void setPliter_cc(String pliter_cc) {
		this.pliter_cc = pliter_cc;
	}
	public List<WxCity> getHotcitylist() {
		return hotcitylist;
	}
	public void setHotcitylist(List<WxCity> hotcitylist) {
		this.hotcitylist = hotcitylist;
	}
	public List<WxCity> getOthercitylist() {
		return othercitylist;
	}
	public void setOthercitylist(List<WxCity> othercitylist) {
		this.othercitylist = othercitylist;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCheckCityName() {
		return checkCityName;
	}
	public String getCheckCityPosition_x() {
		return checkCityPosition_x;
	}
	public void setCheckCityPosition_x(String checkCityPosition_x) {
		this.checkCityPosition_x = checkCityPosition_x;
	}
	public String getCheckCityPosition_y() {
		return checkCityPosition_y;
	}
	public void setCheckCityPosition_y(String checkCityPosition_y) {
		this.checkCityPosition_y = checkCityPosition_y;
	}
	public void setCheckCityName(String checkCityName) {
		this.checkCityName = checkCityName;
	}
	public String getCheckCityCode() {
		return checkCityCode;
	}
	public void setCheckCityCode(String checkCityCode) {
		this.checkCityCode = checkCityCode;
	}
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}

}
