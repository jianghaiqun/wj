package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.bean.AreaBean;
import cn.com.sinosoft.bean.CityBean;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.SDDeliverAddress;
import cn.com.sinosoft.entity.SDInformationAppnt;
import cn.com.sinosoft.entity.SDRelationAppnt;
import cn.com.sinosoft.entity.SDRelationRecognizee;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.SDDeliverAddressService;
import cn.com.sinosoft.service.SDInformationAppntService;
import cn.com.sinosoft.service.SDRelationAppntService;
import cn.com.sinosoft.service.SDRelationRecognizeeService;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 前台Action类 -会员常用信息维护
 * ============================================================================
 * 
 * ============================================================================
 */

@ParentPackage("member")
public class MemberInfoMaintainAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5512764550459131106L;

	private List<SDRelationAppnt> reappntList = new ArrayList<SDRelationAppnt>();// 快速录入被保人信息表
	private List<SDRelationRecognizee> recognizeeList = new ArrayList<SDRelationRecognizee>();// 快速录入被保人信息表
	private String memberID = "";// 登录会员ID
	private SDRelationAppnt mSDRelationAppnt;// 投保人信息
	private SDRelationRecognizee mSDRelationRecognizee;// 被保人信息
	private String operatorFlag = "appnt";// 操作标志：appnt 投保人操作；insured：被保人操作
	private String operator = "insert";// 操作类型：insert 插入操作；update：修改操作
	private int appntCount = 0;// 已有常用投保人数量
	private int leftAppntCount = 0;// 可添加常用投保人数量
	private int insuredCount = 0;// 已有常用背包人个数量
	private int leftInsuredCount = 0;// 可添加常用背包人个数量
	private String info_id;// 投、被保人、信息id
	private String info_Name = "";// 姓名
	private String info_EnName = "";// 英文名
	private String info_IDtype = "";// 证件类型
	private String info_IDtypeName = "";// 证件类型名称
	private String info_CardID = "";// 证件号
	private String info_Sex = "";// 性别
	private String info_Birthday = "";// 生日
	private String info_Mobile = "";// 电话
	private String info_Email = "";// 邮箱
	private List<String> paidAppntNameList = new ArrayList<String>(); // 有支付记录的投保人姓名表
	private SDInformationAppnt mSDInformationAppnt;// 投保人信息，用于回显会员中心投保人信息
	private SDDeliverAddress mSDDeliverAddress;// 投保人信息
	private List<SDDeliverAddress> deliverAddressList = new ArrayList<SDDeliverAddress>();// 邮寄地址信息表
	private int deliverAddressCount = 0;// 已有邮寄地址数量
	private HashMap<String, List<CityBean>> cityMap ;
	private ArrayList<AreaBean> areaList;
	private String addr_id;// 邮寄地址信息id
	

	private int leftdeliverAddressCount = 0;// 可添加邮寄地址数量
	@Resource
	private SDDeliverAddressService mSDDeliverAddressService;

	@Resource
	private SDRelationAppntService mSDRelationAppntService;
	@Resource
	private SDRelationRecognizeeService mSDRelationRecognizeeService;
	@Resource
	private MemberService mMemberService;
	@Resource
	private SDInformationAppntService mSDInformationAppntService;
	
	/**
	 * 省市级联数据封装.
	 * @throws Exception 
	 */
	public void cascade() throws Exception{
		
		GetDBdata dBdata = new GetDBdata();
		
		//省集合
		areaList = new ArrayList<AreaBean>();
		//市集合
		cityMap = new HashMap<String, List<CityBean>>();
		//查询省
		String queryArea = "select id ,name from area where parent_id is null and insuranceCompany is null  order by name";
		
		List<HashMap<String, String>> list = dBdata.query(queryArea);
		
		for(int i = 0; i < list.size(); i++){
			//重新封装
			AreaBean ab = new AreaBean();
			ab.setAreaId(list.get(i).get("id"));
			ab.setAreaName(list.get(i).get("name"));
			
			//查询市
			String queryCity = "select id ,parent_id,name from area where parent_id = '"+list.get(i).get("id")+"'";
			List<HashMap<String, String>> areaCity = dBdata.query(queryCity);
			
			List<CityBean> tempList = new ArrayList<CityBean>();
			for(int j = 0; j < areaCity.size(); j++){
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
	 * 查询投、被保人常用信息
	 * 
	 * @return
	 */
	public String memberInfoQuery() {
		if (!checkLogin()) {
			addActionError("请登陆后，再进行此操作！");
			return ERROR;
		}
		Member mMember = this.getLoginMember();
		memberID = mMember.getId();

		Set<SDRelationAppnt> tSDRelationAppntSet = mMember
				.getSdrelationappntSet();
		Set<SDRelationRecognizee> tSDRelationRecognizeeSet = mMember
				.getSdrelationrecognizeeSet();
		
		if (tSDRelationAppntSet != null && tSDRelationAppntSet.size() >= 1) {
			for (SDRelationAppnt tSDRelationAppnt : tSDRelationAppntSet) {
				reappntList.add(tSDRelationAppnt);
			}
		}
		if (reappntList != null && reappntList.size() >= 1) {
			appntCount = reappntList.size();
		}
		leftAppntCount = 10 - appntCount;
		if (tSDRelationRecognizeeSet != null
				&& tSDRelationRecognizeeSet.size() >= 1) {
			for (SDRelationRecognizee tSDRelationRecognizee : tSDRelationRecognizeeSet) {
				recognizeeList.add(tSDRelationRecognizee);
			}
		}
		if (recognizeeList != null && recognizeeList.size() >= 1) {
			insuredCount = recognizeeList.size();
		}
		leftInsuredCount = 10 - insuredCount;

		deliverAddressList=  mSDDeliverAddressService.getSDDeliverAddressInfo(memberID); 
		
		if (deliverAddressList != null && deliverAddressList.size() >= 1) {
			deliverAddressCount = deliverAddressList.size();
		}
		leftdeliverAddressCount = 5 - deliverAddressCount;
		
		return "query";
	}
	
	/**
	 * 保存邮寄地址常用信息
	 * 
	 * @return
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
			if (mSDRelationAppnt == null)
				mSDRelationAppnt = new SDRelationAppnt();
			mSDRelationAppnt.setApplicantName(info_Name);
			mSDRelationAppnt.setApplicantIdentityType(info_IDtype);
			mSDRelationAppnt.setApplicantIdentityId(info_CardID);
			mSDRelationAppnt.setApplicantSexName(info_Sex);
			mSDRelationAppnt.setApplicantBirthday(info_Birthday);
			mSDRelationAppnt.setApplicantMobile(info_Mobile);
			mSDRelationAppnt.setApplicantMail(info_Email);
			mSDRelationAppnt.setMemberId(getLoginMember().getId());
			mSDRelationAppnt.setmMember(getLoginMember());
			mSDRelationAppntService.save(mSDRelationAppnt);
		} catch (Exception e) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "投保人常用信息保存失败！");
			logger.error(e.getMessage(), e);
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String initAddDeliverAddr(){
	
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("Flag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		tData.put("Flag", "Suc");// 状态
		try {
			cascade();
			tData.put("addrData", mSDDeliverAddress);
			tData.put("areaList", areaList);
			tData.put("cityMap", cityMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		
		JSONObject jsonObject = JSONObject.fromObject(tData);

		return ajax(jsonObject.toString(), "text/html");
	}

	public String getDeliverAddr(){
	
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("Flag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		tData.put("Flag", "Suc");// 状态
		if (!"".equals(addr_id) && addr_id != null) {
			mSDDeliverAddress = mSDDeliverAddressService.get(addr_id
					.toString());
			mSDRelationAppnt.setmMember(null);
			tData.put("deliverAddr", mSDRelationAppnt);
		} else {
			tData.put("Flag", "Err");// 状态
			tData.put("Msg", "请选择投保人信息，再进行修改！");// 错误信息
		}

		JSONObject jsonObject = JSONObject.fromObject(tData);

		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * 保存投、被保人常用信息
	 * 
	 * @return
	 */
	public String saveInfo() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}

		tData.put("tFlag", "Suc");
		if ("appnt".equals(this.operatorFlag)) {
			try {
				if (mSDRelationAppnt == null)
					mSDRelationAppnt = new SDRelationAppnt();
				mSDRelationAppnt.setApplicantName(info_Name);
				mSDRelationAppnt.setApplicantEnName(info_EnName);
				mSDRelationAppnt.setApplicantIdentityType(info_IDtype);
				mSDRelationAppnt.setApplicantIdentityTypeName(info_IDtypeName);
				mSDRelationAppnt.setApplicantIdentityId(info_CardID);
				mSDRelationAppnt.setApplicantSex(info_Sex);
				mSDRelationAppnt.setApplicantSexName("M".equals(info_Sex)?"男":"女");
				mSDRelationAppnt.setApplicantBirthday(info_Birthday);
				mSDRelationAppnt.setApplicantMobile(info_Mobile);
				mSDRelationAppnt.setApplicantMail(info_Email);
				mSDRelationAppnt.setMemberId(getLoginMember().getId());
				mSDRelationAppnt.setmMember(getLoginMember());
				mSDRelationAppntService.save(mSDRelationAppnt);
			} catch (Exception e) {
				tData.put("tFlag", "Err");
				tData.put("Msg", "投保人常用信息保存失败！");
				logger.error(e.getMessage(), e);
			}
		} else if ("insured".equals(this.operatorFlag)) {
			try {
				if (mSDRelationRecognizee == null)
					mSDRelationRecognizee = new SDRelationRecognizee();
				mSDRelationRecognizee.setRecognizeeName(info_Name);
				mSDRelationRecognizee.setRecognizeeEnName(info_EnName);
				mSDRelationRecognizee.setRecognizeeIdentityType(info_IDtype);
				mSDRelationRecognizee.setRecognizeeIdentityTypeName(info_IDtypeName);
				mSDRelationRecognizee.setRecognizeeIdentityId(info_CardID);
				mSDRelationRecognizee.setRecognizeeSex(info_Sex);
				mSDRelationRecognizee.setRecognizeeSexName("M".equals(info_Sex)?"男":"女");
				mSDRelationRecognizee.setRecognizeeBirthday(info_Birthday);
				mSDRelationRecognizee.setRecognizeeMobile(info_Mobile);
				mSDRelationRecognizee.setRecognizeeMail(info_Email);
				mSDRelationRecognizee.setMemberId(getLoginMember().getId());
				mSDRelationRecognizee.setmMember(getLoginMember());
				mSDRelationRecognizeeService.save(mSDRelationRecognizee);
			} catch (Exception e) {
				tData.put("tFlag", "Err");
				tData.put("Msg", "被保人常用信息保存失败！");
				logger.error(e.getMessage(), e);
			}
		} else {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请选择要操作的投、被保人信息！");
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 修改投、被保人常用信息
	 * 
	 * @return
	 */
	public String updateInfo() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		if ("appnt".equals(this.operatorFlag)) {
			try {
				if (mSDRelationAppnt == null)
					mSDRelationAppnt = new SDRelationAppnt();
				mSDRelationAppnt.setId(info_id);
				mSDRelationAppnt.setApplicantName(info_Name);
				mSDRelationAppnt.setApplicantEnName(info_EnName);
				mSDRelationAppnt.setApplicantIdentityType(info_IDtype);
				mSDRelationAppnt.setApplicantIdentityTypeName(info_IDtypeName);
				mSDRelationAppnt.setApplicantIdentityId(info_CardID);
				mSDRelationAppnt.setApplicantSex(info_Sex);
				mSDRelationAppnt.setApplicantSexName("M".equals(info_Sex)?"男":"女");
				mSDRelationAppnt.setApplicantBirthday(info_Birthday);
				mSDRelationAppnt.setApplicantMobile(info_Mobile);
				mSDRelationAppnt.setApplicantMail(info_Email);
				mSDRelationAppnt.setModifyDate(new Date());
				mSDRelationAppnt.setMemberId(getLoginMember().getId());
				mSDRelationAppnt.setmMember(getLoginMember());
				mSDRelationAppntService.update(mSDRelationAppnt);
				tData.put("tFlag", "Suc");
			} catch (Exception e) {
				tData.put("tFlag", "Err");
				tData.put("Msg", "投保人常用信息保存失败！");
				logger.error(e.getMessage(), e);
			}
		} else if ("insured".equals(this.operatorFlag)) {
			try {
				if (mSDRelationRecognizee == null)
					mSDRelationRecognizee = new SDRelationRecognizee();
				mSDRelationRecognizee.setId(info_id);
				mSDRelationRecognizee.setRecognizeeName(info_Name);
				mSDRelationRecognizee.setRecognizeeEnName(info_EnName);
				mSDRelationRecognizee.setRecognizeeIdentityType(info_IDtype);
				mSDRelationRecognizee.setRecognizeeIdentityTypeName(info_IDtypeName);
				mSDRelationRecognizee.setRecognizeeIdentityId(info_CardID);
				mSDRelationRecognizee.setRecognizeeSex(info_Sex);
				mSDRelationRecognizee.setRecognizeeSexName("M".equals(info_Sex)?"男":"女");
				mSDRelationRecognizee.setRecognizeeBirthday(info_Birthday);
				mSDRelationRecognizee.setRecognizeeMobile(info_Mobile);
				mSDRelationRecognizee.setRecognizeeMail(info_Email);
				mSDRelationRecognizee.setModifyDate(new Date());
				mSDRelationRecognizee.setMemberId(getLoginMember().getId());
				mSDRelationRecognizee.setmMember(getLoginMember());
				mSDRelationRecognizeeService.update(mSDRelationRecognizee);
				tData.put("tFlag", "Suc");
			} catch (Exception e) {
				tData.put("tFlag", "Err");
				tData.put("Msg", "被保人保人常用信息保存失败！");
				logger.error(e.getMessage(), e);
			}
		} else {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请选择要操作的投、被保人信息！");
		}
		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 删除投、被保人常用信息
	 * 
	 * @return
	 */
	public String deleteInfo() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		if ("appnt".equals(this.operatorFlag)) {
			try {
				mSDRelationAppnt = mSDRelationAppntService.load(info_id);
				mSDRelationAppntService.delete(mSDRelationAppnt);
				tData.put("tFlag", "Suc");
			} catch (Exception e) {
				tData.put("tFlag", "Err");
				tData.put("Msg", "投保人常用信息删除失败！");
				logger.error(e.getMessage(), e);
			}
		} else if ("insured".equals(this.operatorFlag)) {
			try {
				mSDRelationRecognizee = mSDRelationRecognizeeService
						.load(info_id);
				mSDRelationRecognizeeService.delete(mSDRelationRecognizee);
				tData.put("tFlag", "Suc");
			} catch (Exception e) {
				tData.put("tFlag", "Err");
				tData.put("Msg", "被保人常用信息删除失败！");
				logger.error(e.getMessage(), e);
			}
		} else {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请选择要操作的投、被保人信息！");
		}

		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	 * 设置默认邮件地址常用信息
	 * 
	 * @return
	 */
	public String defaultDeliverAddrInfo() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		deliverAddressList=  mSDDeliverAddressService.getSDDeliverAddressInfo(memberID); 
		try {

			Transaction trans = new Transaction();
			Member member = getLoginMember();
			if (deliverAddressList != null && deliverAddressList.size() >= 1) {
				for (int i = 0; i < deliverAddressList.size(); i++) {
					SDDeliverAddress updateEntity = deliverAddressList.get(i);
					if(info_id.equals(updateEntity.getId())){
						updateEntity.setIsDefault("1");
						updateEntity.setModifyUser(member.getUsername());
					}else{
						updateEntity.setIsDefault("0");
						updateEntity.setModifyUser(member.getUsername());
					}
					mSDDeliverAddressService.save(updateEntity);
				}
				trans.commit();
			}
			tData.put("tFlag", "Suc");
		} catch (Exception e) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "邮件地址常用信息设置默认失败！");
			logger.error(e.getMessage(), e);
		}

		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}
	/**
	 * 删除邮件地址常用信息
	 * 
	 * @return
	 */
	public String deleteDeliverAddrInfo() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		try {
			mSDDeliverAddress = mSDDeliverAddressService.load(info_id);
			mSDDeliverAddressService.delete(mSDDeliverAddress);
			tData.put("tFlag", "Suc");
		} catch (Exception e) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "邮件地址常用信息删除失败！");
			logger.error(e.getMessage(), e);
		}

		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 常用投、被保人数量校验
	 * 
	 * @return
	 */
	public String ajaxValidateName() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		Member member = getLoginMember();
		int tCount = 0;
		tData.put("Flag", "Suc");// 总保费
		try {
			info_Name = java.net.URLDecoder.decode(info_Name, "UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		if ("appnt".equals(this.operatorFlag)) {
			QueryBuilder qb = new QueryBuilder();
			if ("insert".equals(this.operator)) {
				// 判断证件号
				qb = new QueryBuilder(
						"SELECT count(1) FROM sdrelationappnt WHERE mMember_id= ? AND applicantIdentityId= ? ");
				qb.add(member.getId());
				qb.add(info_CardID);
				tCount = Integer.parseInt(qb.executeOneValue().toString());
				if (tCount >= 1) {
					tData.put("Flag", "Err");// 状态
					tData.put("Msg", "该投保人信息的身份证号码已经添加过，不能重复添加！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
				// 判断姓名
				tCount = Integer
						.parseInt(new QueryBuilder(
								"SELECT count(1) FROM sdrelationappnt WHERE mMember_id= ? AND applicantname= ? ",
								member.getId(), info_Name).executeOneValue()
								.toString());
				if (tCount >= 1) {
					tData.put("Flag", "Err");// 状态
					tData.put("Msg", "该投保人信息已经添加过，不能重复添加！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
			} else if ("update".equals(this.operator)) {
				// 判断证件号
				qb = new QueryBuilder(
						"SELECT count(1) FROM sdrelationappnt WHERE mMember_id= ? AND applicantIdentityId= ? and id<> ?");
				qb.add(member.getId());
				qb.add(info_CardID);
				qb.add(info_id);
				tCount = Integer.parseInt(qb.executeOneValue().toString());
				if (tCount >= 1) {
					tData.put("Flag", "Err");// 状态
					tData.put("Msg", "该投保人信息的身份证号码已经添加过，不能重复添加！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
				qb = new QueryBuilder(
						"SELECT count(1) FROM sdrelationappnt WHERE mMember_id= ? AND applicantname= ? and id<> ?");
				qb.add(member.getId());
				qb.add(info_Name);
				qb.add(info_id);
				tCount = Integer.parseInt(qb.executeOneValue().toString());
				if (tCount >= 1) {
					tData.put("Flag", "Err");// 状态
					tData.put("Msg", "该投保人信息已经添加过，不能重复添加！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
			}
			tData.put("Flag", "Suc");// 状态
		} else if ("insured".equals(this.operatorFlag)) {
			QueryBuilder qb = new QueryBuilder();
			if ("insert".equals(this.operator)) {
				// 判断证件号
				qb = new QueryBuilder(
						"SELECT COUNT(1) FROM sdrelationrecognizee WHERE mMember_id= ? AND recognizeeIdentityId=?");
				qb.add(member.getId());
				qb.add(info_CardID);
				tCount = Integer.parseInt(qb.executeOneValue().toString());
				if (tCount >= 1) {
					tData.put("Flag", "Err");// 状态
					tData.put("Msg", "该被保人信息的身份证号已经添加过，不能重复添加！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
				// 判断姓名
				tCount = Integer
						.parseInt(new QueryBuilder(
								"SELECT COUNT(1) FROM sdrelationrecognizee WHERE mMember_id= ? AND recognizeeName=? ",
								member.getId(), info_Name).executeOneValue()
								.toString());
				tCount = Integer.parseInt(qb.executeOneValue().toString());
				if (tCount >= 1) {
					tData.put("Flag", "Err");// 状态
					tData.put("Msg", "该被保人信息已经添加过，不能重复添加！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
			} else if ("update".equals(this.operator)) {
				// 判断证件号
				qb = new QueryBuilder(
						"SELECT COUNT(1) FROM sdrelationrecognizee WHERE mMember_id= ? AND recognizeeIdentityId=? and id<> ?");
				qb.add(member.getId());
				qb.add(info_CardID);
				qb.add(info_id);
				tCount = Integer.parseInt(qb.executeOneValue().toString());
				if (tCount >= 1) {
					tData.put("Flag", "Err");// 状态
					tData.put("Msg", "该被保人信息的身份证号已经添加过，不能重复添加！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
				// 判断姓名
				qb = new QueryBuilder(
						"SELECT COUNT(1) FROM sdrelationrecognizee WHERE mMember_id= ? AND recognizeeName=? and id<> ?");
				qb.add(member.getId());
				qb.add(info_Name);
				qb.add(info_id);
				tCount = Integer.parseInt(qb.executeOneValue().toString());
				if (tCount >= 1) {
					tData.put("Flag", "Err");// 状态
					tData.put("Msg", "该被保人信息已经添加过，不能重复添加！");// 错误信息
					JSONObject jsonObject = JSONObject.fromObject(tData);
					return ajax(jsonObject.toString(), "text/html");
				}
			}
			if (tCount >= 1) {
				tData.put("Flag", "Err");// 状态
				tData.put("Msg", "该被保人信息已经添加过，不能重复添加！");// 错误信息
			} else {
				tData.put("Flag", "Suc");// 状态
			}
		}

		JSONObject jsonObject = JSONObject.fromObject(tData);

		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 常用投、被保人数量校验
	 * 
	 * @return
	 */
	public String ajaxValidateCount() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("tFlag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		Member member = getLoginMember();
		int tCount = 0;
		tData.put("Flag", "Suc");// 总保费
		if ("appnt".equals(this.operatorFlag)) {
			tCount = member.getSdrelationappntSet().size();
			if (tCount >= 10) {
				tData.put("Flag", "Err");// 状态
				tData.put("Msg", "常用信息已满，请删除后再增加！");// 错误信息
			} else {
				tData.put("Flag", "Suc");// 状态
			}
		} else if ("insured".equals(this.operatorFlag)) {
			tCount = member.getSdrelationrecognizeeSet().size();
			if (tCount >= 10) {
				tData.put("Flag", "Err");// 状态
				tData.put("Msg", "常用信息已满，请删除后再增加！");// 错误信息
			} else {
				tData.put("Flag", "Suc");// 状态
			}
		}

		JSONObject jsonObject = JSONObject.fromObject(tData);

		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 获取详细信息
	 * 
	 * @param tFlag
	 *            appnt:投保人；insured：被保人
	 * @return
	 */
	public String getDetailInfo() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("Flag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		tData.put("Flag", "Suc");// 状态
		if ("appnt".equals(operatorFlag)) {
			if (!"".equals(info_id) && info_id != null) {
				mSDRelationAppnt = mSDRelationAppntService.get(info_id
						.toString());
				mSDRelationAppnt.setmMember(null);
				tData.put("appnt", mSDRelationAppnt);
			} else {
				tData.put("Flag", "Err");// 状态
				tData.put("Msg", "请选择投保人信息，再进行修改！");// 错误信息
			}
		} else if ("insured".equals(operatorFlag)) {
			if (!"".equals(info_id) && info_id != null) {
				mSDRelationRecognizee = mSDRelationRecognizeeService
						.get(info_id.toString());
				mSDRelationRecognizee.setmMember(null);
				tData.put("insured", mSDRelationRecognizee);
			} else {
				tData.put("Flag", "Err");// 状态
				tData.put("Msg", "请选择被保人信息，再进行修改！");// 错误信息
			}
		}

		JSONObject jsonObject = JSONObject.fromObject(tData);

		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 获取当前登陆用户下所有有过支付订单的投保人姓名
	 */
	public String getPaidAppntByMemberId() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("Flag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		tData.put("Flag", "Suc");// 状态
		Member mMember = this.getLoginMember();
		memberID = mMember.getId();
		paidAppntNameList = mSDInformationAppntService
				.getPaidAppntNameByMemberId(memberID);
		tData.put("paidAppnts", paidAppntNameList);

		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}

	/**
	 * 根据投保人姓名获取投保人信息
	 * 
	 * @return
	 */
	public String getAppntInfoByName() {
		Map<String, Object> tData = new HashMap<String, Object>();
		if (!checkLogin()) {
			tData.put("Flag", "Err");
			tData.put("Msg", "请登陆后，再进行此操作！");
			JSONObject jsonObject = JSONObject.fromObject(tData);
			return ajax(jsonObject.toString(), "text/html");
		}
		tData.put("Flag", "Suc");// 状态
		Member mMember = this.getLoginMember();
		memberID = mMember.getId();
		if (!"".equals(info_Name) && info_Name != null) {
			try {
				info_Name = java.net.URLDecoder.decode(info_Name, "UTF-8")
						.trim();
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
			mSDInformationAppnt = mSDInformationAppntService
					.getByAppntNameAndMemberId(info_Name, memberID);
			mSDInformationAppnt.setSdinformaiton(null);
			tData.put("appnt", mSDInformationAppnt);
		} else {
			tData.put("Flag", "Err");// 状态
			tData.put("Msg", "请选择投保人信息，再进行修改！");// 错误信息
		}

		JSONObject jsonObject = JSONObject.fromObject(tData);
		return ajax(jsonObject.toString(), "text/html");
	}

	public boolean checkLogin() {
		Member member = getLoginMember();

		if (member == null || "".equals(member)) {
			return false;
		}
		return true;
	}

	public List<SDRelationAppnt> getReappntList() {
		return reappntList;
	}

	public void setReappntList(List<SDRelationAppnt> reappntList) {
		this.reappntList = reappntList;
	}

	public List<SDRelationRecognizee> getRecognizeeList() {
		return recognizeeList;
	}

	public void setRecognizeeList(List<SDRelationRecognizee> recognizeeList) {
		this.recognizeeList = recognizeeList;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public SDRelationAppnt getmSDRelationAppnt() {
		return mSDRelationAppnt;
	}

	public void setmSDRelationAppnt(SDRelationAppnt mSDRelationAppnt) {
		this.mSDRelationAppnt = mSDRelationAppnt;
	}

	public SDRelationRecognizee getmSDRelationRecognizee() {
		return mSDRelationRecognizee;
	}

	public void setmSDRelationRecognizee(
			SDRelationRecognizee mSDRelationRecognizee) {
		this.mSDRelationRecognizee = mSDRelationRecognizee;
	}

	public String getOperatorFlag() {
		return operatorFlag;
	}

	public void setOperatorFlag(String operatorFlag) {
		this.operatorFlag = operatorFlag;
	}

	public int getAppntCount() {
		return appntCount;
	}

	public void setAppntCount(int appntCount) {
		this.appntCount = appntCount;
	}

	public int getInsuredCount() {
		return insuredCount;
	}

	public void setInsuredCount(int insuredCount) {
		this.insuredCount = insuredCount;
	}

	public int getLeftAppntCount() {
		return leftAppntCount;
	}

	public void setLeftAppntCount(int leftAppntCount) {
		this.leftAppntCount = leftAppntCount;
	}

	public int getLeftInsuredCount() {
		return leftInsuredCount;
	}

	public void setLeftInsuredCount(int leftInsuredCount) {
		this.leftInsuredCount = leftInsuredCount;
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

	public String getInfo_EnName() {
		return info_EnName;
	}

	public void setInfo_EnName(String info_EnName) {
		this.info_EnName = info_EnName;
	}

	public String getInfo_IDtype() {
		return info_IDtype;
	}

	public void setInfo_IDtype(String info_IDtype) {
		this.info_IDtype = info_IDtype;
	}

	public String getInfo_IDtypeName() {
		return info_IDtypeName;
	}

	public void setInfo_IDtypeName(String info_IDtypeName) {
		this.info_IDtypeName = info_IDtypeName;
	}

	public String getInfo_CardID() {
		return info_CardID;
	}

	public void setInfo_CardID(String info_CardID) {
		this.info_CardID = info_CardID;
	}

	public String getInfo_Sex() {
		return info_Sex;
	}

	public void setInfo_Sex(String info_Sex) {
		this.info_Sex = info_Sex;
	}

	public String getInfo_Birthday() {
		return info_Birthday;
	}

	public void setInfo_Birthday(String info_Birthday) {
		this.info_Birthday = info_Birthday;
	}

	public String getInfo_Mobile() {
		return info_Mobile;
	}

	public void setInfo_Mobile(String info_Mobile) {
		this.info_Mobile = info_Mobile;
	}

	public String getInfo_Email() {
		return info_Email;
	}

	public void setInfo_Email(String info_Email) {
		this.info_Email = info_Email;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<SDDeliverAddress> getDeliverAddressList() {
		return deliverAddressList;
	}

	public void setDeliverAddressList(List<SDDeliverAddress> deliverAddressList) {
		this.deliverAddressList = deliverAddressList;
	}

	public int getDeliverAddressCount() {
		return deliverAddressCount;
	}

	public void setDeliverAddressCount(int deliverAddressCount) {
		this.deliverAddressCount = deliverAddressCount;
	}

	public int getLeftdeliverAddressCount() {
		return leftdeliverAddressCount;
	}

	public void setLeftdeliverAddressCount(int leftdeliverAddressCount) {
		this.leftdeliverAddressCount = leftdeliverAddressCount;
	}
}