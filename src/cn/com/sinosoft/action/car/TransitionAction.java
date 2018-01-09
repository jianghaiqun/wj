package cn.com.sinosoft.action.car;
import cn.com.sinosoft.action.shop.BaseShopAction;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import net.sf.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransitionAction extends BaseShopAction {

	private static final long serialVersionUID = 1L;

	// 车主姓名
		private String carUser;
		// 车主电话
		private String carPhone;
		// 车牌号
		private String carNO;
		// 保险日期
		private String InsuranceDate;
		// 保险年份
		private String InsureYear;
		// 提车年月
		private String BuyDate;
		// 车价
		private String carValue;
		// 新车未上牌
		private String carProperty;
		// 车主邮箱
		private String Email;
		// 行驶城市
		private String carAddress;
		//省份
		private String sltProvince;
		// 行驶城市集合
		private String carcitystr;
		private String cpsname;

	/**
	 * @Title: saveMiddle
	 * @Description: TODO(平安车险中间页信息保存)
	 * @return String 返回类型
	 * @author jiaomengying
	 */
	
	public String saveMiddle(){
		try {
			boolean status = true;
			carUser = java.net.URLDecoder.decode(carUser, "UTF-8");
			carNO = java.net.URLDecoder.decode(carNO, "UTF-8");
			Email = java.net.URLDecoder.decode(Email, "UTF-8");
			carAddress = java.net.URLDecoder.decode(carAddress, "UTF-8");
			sltProvince = java.net.URLDecoder.decode(sltProvince, "UTF-8");
			cpsname = java.net.URLDecoder.decode(cpsname, "UTF-8");
			String sql = "INSERT INTO sdcartransition VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(NoUtil.getMaxID("CarTransitionID","SN"));
			if(StringUtil.isEmpty(carUser))
				status = false;
			if(StringUtil.isEmpty(carPhone))
				status = false;
			if(StringUtil.isEmpty(InsuranceDate))
				status = false;
			if(StringUtil.isEmpty(InsureYear))
				status = false;
			if(StringUtil.isEmpty(BuyDate))
				status = false;
			if(StringUtil.isEmpty(carValue))
				status = false;
			if(StringUtil.isEmpty(carProperty) && StringUtil.isEmpty(carNO))
				status = false;
			if(StringUtil.isEmpty(Email))
				status = false;
			if(StringUtil.isEmpty(carAddress))
				status = false;
			if(status){
				qb.add(carAddress);
				qb.add(carNO);
				qb.add(InsureYear);
				qb.add(InsuranceDate);
				qb.add(BuyDate);
				qb.add(carValue);
				qb.add(carUser);
				qb.add(carPhone);
				qb.add(Email);
				qb.add(carProperty);
				qb.add(new Date());
				//保险公司
				if(StringUtil.isEmpty(cpsname)||"undefined".equals(cpsname)){
					qb.add("pingan");
				}else{
					qb.add(cpsname);
				}
				//省份
				if(StringUtil.isEmpty(sltProvince)||"undefined".equals(sltProvince)){
					qb.add("");
				}else{
					qb.add(sltProvince);
				}
				qb.executeNoQuery();
			}
			
			//查询平安省市编码
			String areaSql = "SELECT a.license,a.areacode cityCode,a.areaname cityName,b.areaname "
					+ " provinceName,b.areacode provinceCode FROM ZDPingAnArea a,ZDPingAnArea b"
					+ " WHERE a.parentcode = b.areacode AND a.areagrade = '2'"
					+ " AND b.areagrade = '1' AND a.shortname = '"+carAddress+"'";
			DataTable dt = new QueryBuilder(areaSql).executeDataTable();
			String provinceCode = "";
			String provinceName = "";
			String cityCode = "";
			String cityName = "";
			String jumpPath = "";
			String registered = "";
			String registerDate = "&vehicle.registerDate=" + BuyDate;
			String simpleVehiclePrice = "&bizQuote.simpleVehiclePrice="
					+ carValue;
			if(dt!=null&&dt.getRowCount()>0){
				// 如果前台没有默认编码则认为是平安
				String licenseNo = dt.getString(0, "license");
				
				if ("1".equals(carProperty)) {
					// 新车未上牌内容含“*”
					licenseNo += "*";
					// 新车必传
					registered = "&vehicle.registered=newVehicle";
				} else {
					// 非北京本地旧车 必传
					if ("北京".equals(carAddress)) {
						registerDate = "";
						simpleVehiclePrice = "";
					}
					licenseNo = carNO;
				}
				
				provinceCode = dt.getString(0, "provinceCode");
				provinceName = dt.getString(0, "provinceName");
				cityCode = dt.getString(0, "cityCode");
				cityName = dt.getString(0, "cityName");
				jumpPath = Config.getValue("JumpPath_PA")
						+ "&provinceCode=" + provinceCode
						+ "&provinceName=" + provinceName
						+ "&cityCode=" + cityCode + "&cityName="
						+ cityName + "&vehicle.licenseNo=" + licenseNo
						+ registered + registerDate + simpleVehiclePrice
						+ "&applicant.mobile=" + carPhone + "&applicant.email="
						+ Email + "&register.name=" + carUser;
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("jumpPath", jumpPath.toString());
			JSONObject jsonObject = JSONObject.fromObject(map);
			return ajax(jsonObject.toString(), "text/html");
		} catch (Exception e) {
			logger.error("平安中间页信息保存异常" + e.getMessage(), e);
			return ERROR;
		}
	}

	public String queryCarcode() {
		String sql = "SELECT * FROM ZDPingAnArea WHERE areagrade = '2'";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int rw = dt.getRowCount();
				StringBuffer carbuff = new StringBuffer();
				for (int i = 0; i < rw; i++) {
					carbuff.append("@" + dt.getString(i, "SpellName") + "|"
							+ dt.getString(i, "ShortName") + "|"
							+ dt.getString(i, "PartSpellName") + "|"
							+ dt.getString(i, "AreaGrade") + "|"
							+ dt.getString(i, "License"));
				}
				carcitystr = carbuff.toString();
			} else {
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("carcitystr", carcitystr.toString());
			JSONObject jsonObject = JSONObject.fromObject(map);
			return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * 查询车辆行驶城市   -车牌前缀一样随机取第一个
	 * @Title: queryDrive
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return String 返回类型
	 * @author XXX
	 */
	public String queryDrive() {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			String sql = "SELECT shortname FROM zdpinganarea WHERE license = '"
					+ carNO.substring(0,2) + "'";
			DataTable dt = new QueryBuilder(sql).executeDataTable();
			String cityName = "";
			if (dt != null && dt.getRowCount() > 0) {
				cityName = dt.getString(0, 0);
			}
			map.put("cityName", cityName);
			
		}catch(Exception e){
			logger.error("查询车辆行驶城市异常，异常信息："+e.getMessage(), e);
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		return ajax(jsonObject.toString(), "text/html");
	}

	
	public String getCarUser() {
		return carUser;
	}

	public void setCarUser(String carUser) {
		this.carUser = carUser;
	}

	public String getCarPhone() {
		return carPhone;
	}

	public void setCarPhone(String carPhone) {
		this.carPhone = carPhone;
	}

	public String getCarNO() {
		return carNO;
	}

	public void setCarNO(String carNO) {
		this.carNO = carNO;
	}

	public String getInsuranceDate() {
		return InsuranceDate;
	}

	public void setInsuranceDate(String insuranceDate) {
		InsuranceDate = insuranceDate;
	}

	public String getBuyDate() {
		return BuyDate;
	}

	public void setBuyDate(String buyDate) {
		BuyDate = buyDate;
	}

	public String getCarValue() {
		return carValue;
	}

	public void setCarValue(String carValue) {
		this.carValue = carValue;
	}

	public String getCarProperty() {
		return carProperty;
	}

	public void setCarProperty(String carProperty) {
		this.carProperty = carProperty;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getCarAddress() {
		return carAddress;
	}

	public void setCarAddress(String carAddress) {
		this.carAddress = carAddress;
	}

	public String getCarcitystr() {
		return carcitystr;
	}

	public void setCarcitystr(String carcitystr) {
		this.carcitystr = carcitystr;
	}

	public String getSltProvince() {
		return sltProvince;
	}

	public void setSltProvince(String sltProvince) {
		this.sltProvince = sltProvince;
	}

	public String getCpsname() {
		return cpsname;
	}

	public void setCpsname(String cpsname) {
		this.cpsname = cpsname;
	}

	public String getInsureYear() {
		return InsureYear;
	}

	public void setInsureYear(String insureYear) {
		InsureYear = insureYear;
	}

}
