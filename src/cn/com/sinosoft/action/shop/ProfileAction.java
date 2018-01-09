package cn.com.sinosoft.action.shop;
  
import cn.com.sinosoft.bean.AreaBean;
import cn.com.sinosoft.bean.CityBean;
import cn.com.sinosoft.entity.Dict;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberAttribute;
import cn.com.sinosoft.entity.MemberAttribute.AttributeType;
import cn.com.sinosoft.service.AreaService;
import cn.com.sinosoft.service.MemberAttributeService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 前台Action类 - 个人信息
 * ============================================================================
 * 
 * 
 *  
 * 
 * 
 *  
 * KEY:SINOSOFT5816616692406BD5A6311CA738576B66
 * ============================================================================
 */

@ParentPackage("member")
public class ProfileAction extends BaseShopAction {

	private static final long serialVersionUID = -7704084885878684413L;

	private Member member;
	
	private String email;

	private List<Dict> listHobby = new ArrayList<Dict>();
	private List<Dict> listLove = new ArrayList<Dict>();

	public List<Dict> getListLove() {
		return listLove;
	}

	public void setListLove(List<Dict> listLove) {
		this.listLove = listLove;
	}

	public List<Dict> getListHobby() {
		return listHobby;
	}

	public void setListHobby(List<Dict> listHobby) {
		this.listHobby = listHobby;
	}

	private List<Dict> listms = new ArrayList<Dict>();
	private List<Dict> listid = new ArrayList<Dict>();
	
	private List<Dict> listApp = new ArrayList<Dict>();

	public List<Dict> getListid() {
		return listid;
	}

	public void setListid(List<Dict> listid) {
		this.listid = listid;
	}

	@Resource
	private MemberService memberService;
	@Resource
	private MemberAttributeService memberAttributeService;
	@Resource
	private AreaService areaService;

	// ---------吴高强添加----------
	private String memberID;

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	/**
	 * 文件.
	 */
	private List<File> uploads;
	/**
	 * 文件名称.
	 */
	private List<String> uploadsFileName;
	/**
	 * 文件类型.
	 */
	private List<String> uploadsContentType;
	/**
	 * 压缩宽度.
	 */
	private int imgWidth = 100;
	/**
	 * 压缩高度.
	 */
	private int imgHeight = 100;
	/**
	 * 头像设置结果.
	 */
	private boolean result = true;
	/**
	 * 文件目录.
	 */
	private String fileDir = "memberImg";
	/**
	 * 原图位置.
	 */
	private String oldImg = "myOldImg";
	/**
	 * 新图位置
	 */
	private String newImg = "myNewImg";
	/**
	 * 上传文件大小限制10M.
	 * 单位(字节).
	 */
	final private long FILESIZE = 10485760;
	/**
	 * 返回提示信息.
	 */
	private String message;
	
	private String applicantName;
	
	private String status;
	
	private HashMap<String, List<CityBean>> cityMap ;
	
	private ArrayList<AreaBean> areaList;
	
	private String areaId;
	// -----------吴高强添加结束---------------
	// 编辑
	public String edit() {
		member = getLoginMember();
		this.memberID = member.getId();

		email = member.getEmail();
		
		if (member.getAboutToExpirePoints() == null) {
			member.setAboutToExpirePoints(0);
		}
		MemberCenterAction memberCenterAction = new MemberCenterAction();
		Map<String, String> mapTemp = memberCenterAction.getGradeIcon(
				member.getVipFlag(), member.getGrade(), "",
				member.getBirthday(), member.getBirthYear());
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("gradeClass", mapTemp.get("gradeClass"));
		request.setAttribute("upgradeInfo", mapTemp.get("gradeInfo"));
		request.setAttribute("gradeDesc", mapTemp.get("gradeDesc"));
		
		JdbcTemplateData jtd = new JdbcTemplateData();
		String sql = "select codevalue,codename from zdcode where codetype='member.IDType' and  parentcode='IDType' order by codevalue asc";
		String sql2 = "select codevalue,codename  from  zdcode  where  codetype='member.marriageStatus' and parentCode='marriageStatus'";
		String sql3 = "select codevalue,codename from zdcode where codetype='member.Hobby' and parentcode='hobby' order by codevalue asc";
		String sql4 = "select codevalue from memberhobby where  member_id= ?  and isselected = 'Y' ";
		
				
		try {
			List<Map> list = jtd.obtainData(sql, null);
			List<Map> listsex = jtd.obtainData(sql2, null);
			List<Map> listhy = jtd.obtainData(sql3, null);
			String sqltemp[] = new String[] { memberID };
			List<Map> listhy2 = jtd.obtainData(sql4, sqltemp);
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Dict idType = new Dict();
				idType.setDictName((String) map.get("CodeName"));
				idType.setDictSerial((String) map.get("CodeValue"));
				listid.add(idType);

			}
			Iterator<Map> ithy = listhy.iterator();
			while (ithy.hasNext()) {
				Map map = ithy.next();
				Dict ms = new Dict();
				ms.setDictName((String) map.get("CodeName"));
				ms.setDictSerial((String) map.get("CodeValue"));
				listHobby.add(ms);
			}
			Iterator<Map> ithy2 = listhy2.iterator();
			while (ithy2.hasNext()) {
				Map map = ithy2.next();
				Dict ms = new Dict();
				ms.setDictSerial((String) map.get("codeValue"));
				listLove.add(ms);

			}
			Iterator<Map> itsex = listsex.iterator();
			while (itsex.hasNext()) {
				Map map = itsex.next();
				Dict ms = new Dict();
				ms.setDictName((String) map.get("CodeName"));
				ms.setDictSerial((String) map.get("CodeValue"));
				listms.add(ms);
			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			ActionUtil.dealAction("wj00025", map, getRequest());
			cascade();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			//通过城市 查询省
			queryArea(member.getLocation());
			return "input";
		}

	}
	public String queryInfo(){
		member = getLoginMember();
		this.memberID = member.getId();
		
		
		
		String sql5 = " SELECT a.*,(SELECT b.id FROM AREA a,AREA b WHERE  a.name=b.name AND a.id=a.applicantArea1 AND b.parent_id IS NULL AND b.insuranceCompany IS NULL  ) areaid" +
				"  FROM sdorders s,sdinformation i,sdinformationappnt a " +
				"  WHERE s.ordersn=i.ordersn AND i.informationsn=a.informationsn  AND memberid='"+memberID+"' " ;
		if(StringUtil.isNotEmpty(applicantName)){
			sql5+="  AND a.applicantName='"+applicantName+"' ";
		};
		sql5 +=" GROUP BY a.applicantName";
		
		DataTable dt = new QueryBuilder(sql5).executeDataTable();
		Map<String, Object> map = new HashMap<String, Object>();
		String Area2Rank = "0";
		if(dt!=null && dt.getRowCount()>0){
			String cityName="";
			String Sql ="SELECT NAME FROM AREA WHERE id='"+dt.getString(0, "applicantArea2")+"'  ";
			DataTable dtCity = new QueryBuilder(Sql).executeDataTable();
			if(dtCity!=null && dtCity.getRowCount()>0){
				cityName = dtCity.getString(0, 0).substring(0, 2);
			}
			String sql2 = " SELECT id FROM AREA WHERE parent_id='"+dt.getString(0, "areaid")+"' AND NAME LIKE '"+cityName+"%'";
			DataTable dtCity2 = new QueryBuilder(sql2).executeDataTable();
			if(dtCity2!=null && dtCity2.getRowCount()>0){
				map.put("applicantArea2Name", dtCity2.getString(0, "id"));
			}
			String sql3=" SELECT id ,NAME FROM AREA WHERE parent_id IS NULL AND insuranceCompany IS NULL ORDER BY NAME";
			DataTable dtCitys = new QueryBuilder(sql3).executeDataTable();
			for(int i=0;i<dtCitys.getRowCount();i++){
				if(dtCitys.getString(i, "id").equals(dt.getString(0, "areaid"))){
					Area2Rank = (i+1)+"";
				}
			}
			map.put("applicantArea2Rank", Area2Rank);
			map.put("applicantName", dt.getString(0, "applicantName"));
			map.put("applicantSex", dt.getString(0, "applicantSex"));
			map.put("applicantBirthday", dt.getString(0, "applicantBirthday"));
			map.put("applicantIdentityType", dt.getString(0, "applicantIdentityTypeName"));
			map.put("applicantIdentityId", dt.getString(0, "applicantIdentityId"));
			map.put("applicantArea1", dt.getString(0, "applicantArea1"));
			map.put("applicantArea1Name", dt.getString(0, "areaid"));
			map.put("applicantArea2", dt.getString(0, "applicantArea2"));
			map.put("applicantAddress", dt.getString(0, "applicantAddress"));
			map.put("applicantZipCode", dt.getString(0, "applicantZipCode"));
		}
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		return ajax(jsonObject.toString(), "text/html");
	}

	// 更新
	@Validations(requiredStrings = { @RequiredStringValidator(fieldName = "member.email", message = "E-mail不允许为空!") }, stringLengthFields = { @StringLengthFieldValidator(fieldName = "member.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") }, emails = { @EmailValidator(fieldName = "member.email", message = "E-mail格式错误!") })
	@InputConfig(resultName = "error")
	public String update() {
		Map<MemberAttribute, List<String>> memberAttributeMap = new HashMap<MemberAttribute, List<String>>();
		List<MemberAttribute> enabledMemberAttributeList = memberAttributeService
				.getEnabledMemberAttributeList();
		for (MemberAttribute memberAttribute : enabledMemberAttributeList) {
			String[] parameterValues = getParameterValues(memberAttribute
					.getId());
			if (memberAttribute.getIsRequired()
					&& (parameterValues == null || parameterValues.length == 0 || StringUtils
							.isEmpty(parameterValues[0]))) {
				addActionError(memberAttribute.getName() + "不允许为空!");
				return ERROR;
			}
			if (parameterValues != null && parameterValues.length > 0
					&& StringUtils.isNotEmpty(parameterValues[0])) {
				if (memberAttribute.getAttributeType() == AttributeType.number) {
					Pattern pattern = Pattern
							.compile("^-?(?:\\d+|\\d{1,3}(?:,\\d{3})+)(?:\\.\\d+)?");
					Matcher matcher = pattern.matcher(parameterValues[0]);
					if (!matcher.matches()) {
						addActionError(memberAttribute.getName() + "只允许输入数字!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.alphaint) {
					Pattern pattern = Pattern.compile("[a-zA-Z]+");
					Matcher matcher = pattern.matcher(parameterValues[0]);
					if (!matcher.matches()) {
						addActionError(memberAttribute.getName() + "只允许输入字母!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.email) {
					Pattern pattern = Pattern
							.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
					Matcher matcher = pattern.matcher(parameterValues[0]);
					if (!matcher.matches()) {
						addActionError(memberAttribute.getName()
								+ "E-mail格式错误!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.date) {
					Pattern pattern = Pattern
							.compile("\\d{4}[\\/-]\\d{1,2}[\\/-]\\d{1,2}");
					Matcher matcher = pattern.matcher(parameterValues[0]);
					if (!matcher.matches()) {
						addActionError(memberAttribute.getName() + "日期格式错误!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.area) {
					if (!areaService.isAreaPath(parameterValues[0])) {
						addActionError(memberAttribute.getName() + "地区错误!");
						return ERROR;
					}
				}
				if (memberAttribute.getAttributeType() == AttributeType.select
						|| memberAttribute.getAttributeType() == AttributeType.checkbox) {
					List<String> attributeOptionList = memberAttribute
							.getAttributeOptionList();
					for (String parameterValue : parameterValues) {
						if (!attributeOptionList.contains(parameterValue)) {
							addActionError("参数错误!");
							return ERROR;
						}
					}
				}
				memberAttributeMap.put(memberAttribute, Arrays
						.asList(parameterValues));
			}
		}
		member.setMemberAttributeMap(memberAttributeMap);
		Member persistent = getLoginMember();
		if (StringUtils.isNotEmpty(member.getPassword())) {
			String passwordMd5 = DigestUtils.md5Hex(member.getPassword());
			persistent.setPassword(passwordMd5);
		}
		BeanUtils.copyProperties(member, persistent, new String[] { "id",
				"createDate", "modifyDate", "username", "password",
				"safeQuestion", "safeAnswer", "point", "deposit",
				"isAccountEnabled", "isAccountLocked", "loginFailureCount",
				"lockedDate", "registerIp", "loginIp", "loginDate",
				"passwordRecoverKey", "memberRank", "receiverSet",
				"favoriteProductSet", "cartItemSet", "inboxMessageSet",
				"outboxMessageSet", "orderSet", "depositSet" });
		memberService.update(persistent);
		redirectionUrl = "profile!edit.action";
		return SUCCESS;
	}
	
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
			
			List<CityBean> tempList = new ArrayList();
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
	 * 查询省ID.
	 * @param cityId
	 */
	public void queryArea(String cityId){
		if(cityId != null){
		GetDBdata dBdata = new GetDBdata();
		String sql = "select parent_id from area where id='"+cityId+"'";
		try {
			List<HashMap<String, String>> list = dBdata.query(sql);
			areaId = list.get(0).get("parent_id");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		}
	}
	

	/**
	 * 会员中心-上传头像.
	 * 
	 * @return
	 */
	public String uploadImg() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, String> jsonMap = new HashMap<String, String>();
		
		Member member = getLoginMember();
		if(member!=null){
			String memberId = member.getId();
			String saveResultPath = null;
			
			GetDBdata dBdata = new GetDBdata();
			
			String[] tempParame = {memberId};
			
			String queryPic = "select headPicPath from member where id = ?";
		
			try {
				if (uploads != null) {
					
					// 查询原头像
					List<HashMap<String, String>> picResult = dBdata
					.query(queryPic,tempParame);
					
					//判断文件是否为图片
					if(ImgTool.isImg(uploads.get(0)) == null){
						jsonMap.put("message", "上传错误,请重新上传!");
						jsonMap.put("status", "N");
						return ajaxJson(jsonMap);
					}
					//文件大小 字节
					if(uploads.get(0).length() > FILESIZE){
						jsonMap.put("message", "上传错误,图片最大 10M!");
						jsonMap.put("status", "N");
						return ajaxJson(jsonMap);
					}
					
					// 将文件存到本地
					List<String> uploadPath = ImgTool.saveImgFile(uploads, uploadsFileName,fileDir,oldImg);
					String tempPath = uploadPath.get(0).toString();
	
					if (uploadPath != null) {
						String fileName = tempPath
								.substring(
										tempPath.lastIndexOf(File.separator) + 1,
										tempPath.length());
						// 压缩图片
						saveResultPath = ImgTool.reduce(uploadPath.get(0)
								.toString(), fileName, imgWidth, imgHeight, false,fileDir,newImg);
						// 保存路径
						String savePath = saveResultPath.substring(saveResultPath
								.indexOf(fileDir), saveResultPath
								.length());
	//					String delImgPath = tempPath.substring(tempPath
	//							.indexOf(fileDir), tempPath.length());
	
						// 删除原图
	//					ImgTool.delFile(delImgPath);
	
						
						//Mysql 替换转义字符
						savePath = savePath.replaceAll(File.separator + File.separator, "/");
						
						//上传阿里云
						InputStream inputStream = new FileInputStream(saveResultPath);
						OSSUploadFile.uploadAliyun(inputStream, savePath);
						inputStream.close();
	
						// 更新头像
						String updateMemberPic = "update member set headPicPath='"
								+ savePath + "' where id='" + memberId + "'";
						result = dBdata.execUpdateSQL(updateMemberPic, null);
	
						if (picResult != null && result) {
							// 新头像保存成功 删除原有头像
							String path = picResult.get(0).get("headPicPath");
							ImgTool.delFile(path);
							String oldPath = path.replace("myNewImg", "myOldImg");
							ImgTool.delFile(oldPath);
						}
						jsonMap.put("savePath", savePath);
					}
				}
				
			} catch (IOException e) {
				ImgTool.delFile(saveResultPath);
				logger.error(e.getMessage(), e);
				jsonMap.put("message", "上传错误,请重新上传!");
				jsonMap.put("status", "N");
				return ajaxJson(jsonMap);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				jsonMap.put("message", "上传错误,请重新上传!");
				jsonMap.put("status", "N");
				return ajaxJson(jsonMap);
			}
		}else{
			jsonMap.put("message", "请重新登录!");
			jsonMap.put("status", "N");
			return ajaxJson(jsonMap);
		}
		
		jsonMap.put("status", "Y");
		return ajaxJson(jsonMap);
	}

	public List<Dict> getListms() {
		return listms;
	}

	public void setListms(List<Dict> listms) {
		this.listms = listms;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// 获取已启用的会员注册项
	public List<MemberAttribute> getEnabledMemberAttributeList() {
		return memberAttributeService.getEnabledMemberAttributeList();
	}

	public List<File> getUploads() {
		return uploads;
	}

	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}

	public List<String> getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(List<String> uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public List<String> getUploadsContentType() {
		return uploadsContentType;
	}

	public void setUploadsContentType(List<String> uploadsContentType) {
		this.uploadsContentType = uploadsContentType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public List<Dict> getListApp() {
		return listApp;
	}

	public void setListApp(List<Dict> listApp) {
		this.listApp = listApp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}