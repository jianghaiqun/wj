package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.bean.AreaBean;
import cn.com.sinosoft.bean.CityBean;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDDeliverAddress;
import cn.com.sinosoft.service.SDDeliverAddressService;
import com.opensymphony.oscache.util.StringUtil;
import com.sinosoft.framework.GetDBdata;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Results({ @Result(name = "deliverAddress", location = "/WEB-INF/template/shop/deliver_address.jsp") })
/**
 * 前台Action类 -会员常用信息维护
 * ============================================================================
 * 
 * ============================================================================
 */
public class DeliverAddressMaintainAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 342898468379874206L;

	private SDDeliverAddress mSDDeliverAddress;// 邮寄信息

	private HashMap<String, List<CityBean>> cityMap;//市列表
	private ArrayList<AreaBean> areaList;//省列表
	private String areaId;//地区id
	private int count = 0;//地址总个数
	private String info_id;//ID
	private String info_Name = "";//姓名
	private String info_Tel = "";//电话
	private String info_ProvinceCode = "";//省code
	private String info_ProvinceName = "";//省名称
	private String info_CityCode = "";//市code
	private String info_CityName = "";//市名称
	private String info_DetailAddr = "";//邮寄地址
	private String info_ZipCode = "";//邮编
	private String addr_id;//地址id
	private String info_IsDefault = "";//是否默认
	private String addressColor = "";

	@Resource
	private SDDeliverAddressService mSDDeliverAddressService;

	/**
	 * 新增时初期显示
	 * 
	 * @return String
	 */
	public String initAddDeliverAddr() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("Flag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		try {
			mSDDeliverAddress = new SDDeliverAddress();
			mSDDeliverAddress.setName("中英文姓名");
			mSDDeliverAddress.setTel("手机或固话");
			mSDDeliverAddress.setDetailAddr("请准确填写，以免无法邮寄给您");
			 cascade();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}

		return ("deliverAddress");
	}

	/**
	 * 更新时取得对应信息
	 * 
	 * @return String
	 */
	public String getDeliverAddr() {

		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("Flag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		tData.put("Flag", "Suc");// 状态
		if (!"".equals(addr_id) && addr_id != null) {
			mSDDeliverAddress = mSDDeliverAddressService.load(addr_id);
			
		} else {
			tData.put("Flag", "Err");// 状态
			tData.put("Msg", "请选择邮寄地址信息，再进行修改！");// 错误信息
		}

		try {
			addressColor = "#3b3b3b";
			cascade();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			addActionError(e.getMessage());
			return ERROR;
		}finally{
			//通过城市 查询省
			queryArea(mSDDeliverAddress.getCityCode());
		}
		
		return ("deliverAddress");
	}

	/**
	 * 查询省ID.
	 * 
	 * @param cityId
	 */
	public void queryArea(String cityId) {
		if (cityId != null) {
			GetDBdata dBdata = new GetDBdata();
			String sql = "select parent_id from area where id='" + cityId + "'";
			try {
				List<HashMap<String, String>> list = dBdata.query(sql);
				areaId = list.get(0).get("parent_id");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				addActionError(e.getMessage());
			}
		}
	}

	/**
	 * 省市级联数据封装.
	 * 
	 * @throws Exception
	 */
	public void cascade() throws Exception {

		GetDBdata dBdata = new GetDBdata();

		// 省集合
		areaList = new ArrayList<AreaBean>();
		// 市集合
		cityMap = new HashMap<String, List<CityBean>>();
		// 查询省
		String queryArea = "select id ,name from area where parent_id is null and insuranceCompany is null and name not in ('中国钓鱼岛','香港','台湾') order by name";

		List<HashMap<String, String>> list = dBdata.query(queryArea);

		for (int i = 0; i < list.size(); i++) {
			// 重新封装
			AreaBean ab = new AreaBean();
			ab.setAreaId(list.get(i).get("id"));
			ab.setAreaName(list.get(i).get("name"));

			// 查询市
			String queryCity = "select id ,parent_id,name from area where parent_id = '"
					+ list.get(i).get("id") + "'";
			List<HashMap<String, String>> areaCity = dBdata.query(queryCity);

			List<CityBean> tempList = new ArrayList<CityBean>();
			for (int j = 0; j < areaCity.size(); j++) {
				CityBean cb = new CityBean();
				cb.setCityId(areaCity.get(j).get("id"));
				cb.setCityName(areaCity.get(j).get("name"));
				cb.setAreaId(areaCity.get(j).get("parent_id"));
				tempList.add(cb);
			}
			areaList.add(ab);
			cityMap.put(list.get(i).get("id"), tempList);
		}
	}

	/**
	 * 保存邮寄地址常用信息
	 * 
	 * @return String
	 */
	public String saveDeliverAddrInfo() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}

		tData.put("tFlag", "Suc");
		try {
			if (!StringUtil.isEmpty(info_id)) {
				mSDDeliverAddress = mSDDeliverAddressService.get(info_id);
			}
			
			if (mSDDeliverAddress == null)
				mSDDeliverAddress = new SDDeliverAddress();
			mSDDeliverAddress.setName(info_Name);
			mSDDeliverAddress.setTel(info_Tel);
			mSDDeliverAddress.setProvinceCode(info_ProvinceCode);
			mSDDeliverAddress.setProvinceName(info_ProvinceName);
			mSDDeliverAddress.setCityCode(info_CityCode);
			mSDDeliverAddress.setCityName(info_CityName);
			mSDDeliverAddress.setSection(info_ProvinceName + info_CityName);
			mSDDeliverAddress.setDetailAddr(info_DetailAddr);
			mSDDeliverAddress.setZipCode(info_ZipCode);
			Member member = getLoginMember();
			if (StringUtil.isEmpty(info_id)) {
				mSDDeliverAddress.setCreateUser(member.getUsername());
				mSDDeliverAddress.setModifyUser(member.getUsername());
			}else{
				mSDDeliverAddress.setModifyUser(member.getUsername());
			}
			mSDDeliverAddress.setMemberId(member.getId());
				
			if (!StringUtil.isEmpty(info_id)) {
				mSDDeliverAddress.setId(info_id);
				mSDDeliverAddressService.update(mSDDeliverAddress);
			}else{
				mSDDeliverAddress.setIsDefault("0");
				mSDDeliverAddressService.save(mSDDeliverAddress);
			}
		} catch (Exception e) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "邮寄地址信息保存失败！");
			logger.error(e.getMessage(), e);
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 检查当前用户是否登录成功
	 * 
	 * @return 判断是否登录
	 */
	public boolean checkLogin() {
		Member member = getLoginMember();

		if (member == null || "".equals(member)) {
			return false;
		}
		return true;
	}

	

	public SDDeliverAddress getMSDDeliverAddress() {
		return mSDDeliverAddress;
	}

	public void setMSDDeliverAddress(SDDeliverAddress mSDDeliverAddress) {
		this.mSDDeliverAddress = mSDDeliverAddress;
	}

	public String getInfo_id() {
		return info_id;
	}

	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}

	public String getInfo_Name() {
		return info_Name;
	}

	public void setInfo_Name(String info_Name) {
		this.info_Name = info_Name;
	}

	public String getInfo_Tel() {
		return info_Tel;
	}

	public void setInfo_Tel(String info_Tel) {
		this.info_Tel = info_Tel;
	}

	public String getInfo_ProvinceCode() {
		return info_ProvinceCode;
	}

	public void setInfo_ProvinceCode(String info_ProvinceCode) {
		this.info_ProvinceCode = info_ProvinceCode;
	}

	public String getInfo_ProvinceName() {
		return info_ProvinceName;
	}

	public void setInfo_ProvinceName(String info_ProvinceName) {
		this.info_ProvinceName = info_ProvinceName;
	}

	public String getInfo_CityCode() {
		return info_CityCode;
	}

	public void setInfo_CityCode(String info_CityCode) {
		this.info_CityCode = info_CityCode;
	}

	public String getInfo_CityName() {
		return info_CityName;
	}

	public void setInfo_CityName(String info_CityName) {
		this.info_CityName = info_CityName;
	}

	public String getInfo_DetailAddr() {
		return info_DetailAddr;
	}

	public void setInfo_DetailAddr(String info_DetailAddr) {
		this.info_DetailAddr = info_DetailAddr;
	}

	public String getInfo_ZipCode() {
		return info_ZipCode;
	}

	public void setInfo_ZipCode(String info_ZipCode) {
		this.info_ZipCode = info_ZipCode;
	}

	public HashMap<String, List<CityBean>> getCityMap() {
		return cityMap;
	}

	public void setCityMap(HashMap<String, List<CityBean>> cityMap) {
		this.cityMap = cityMap;
	}

	public ArrayList<AreaBean> getAreaList() {
		return areaList;
	}

	public void setAreaList(ArrayList<AreaBean> areaList) {
		this.areaList = areaList;
	}

	public String getAddr_id() {
		return addr_id;
	}

	public void setAddr_id(String addr_id) {
		this.addr_id = addr_id;
	}

	public SDDeliverAddressService getmSDDeliverAddressService() {
		return mSDDeliverAddressService;
	}

	public void setmSDDeliverAddressService(
			SDDeliverAddressService mSDDeliverAddressService) {
		this.mSDDeliverAddressService = mSDDeliverAddressService;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public SDDeliverAddress getmSDDeliverAddress() {
		return mSDDeliverAddress;
	}

	public void setmSDDeliverAddress(SDDeliverAddress mSDDeliverAddress) {
		this.mSDDeliverAddress = mSDDeliverAddress;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getInfo_IsDefault() {
		return info_IsDefault;
	}

	public void setInfo_IsDefault(String info_IsDefault) {
		this.info_IsDefault = info_IsDefault;
	}

	public String getAddressColor() {
		return addressColor;
	}

	public void setAddressColor(String addressColor) {
		this.addressColor = addressColor;
	}
	
}