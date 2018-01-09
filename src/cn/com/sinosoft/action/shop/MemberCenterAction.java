package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.BindInfoForLogin;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Order;
import cn.com.sinosoft.service.BindInfoForLoginService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.service.MessageService;
import cn.com.sinosoft.service.OrderService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.memberSchema;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
  
/**
 * 前台Action类 - 会员中心 
 * ============================================================================
 * 
 * 
 *  
 * 
 *  
 * 
 * KEY:SINOSOFT456CA553F200C7A4CFEC6788B8F51192
 * ============================================================================
 */

@ParentPackage("member")
public class MemberCenterAction extends BaseShopAction {

	private static final long serialVersionUID = -3568504222758246021L;
	private String appName;
	private String mobileNO;// 手机号
	private String serialNO;// 操作后注册传递过来的的字段值
	private String isfirstMC;//标志位：是否第一次登录会员中心
	private String isfirstMCAfterUngrade;//标志位：升级后是否第一次登录会员中心
	private String tagid;//登录/注册页面切换标记
	//windowAddr:注册前地址
    private String windowAddr = null;
    private BindInfoForLogin bindInfoForLogin = new BindInfoForLogin();
	@Resource
	MemberService memberService;
	@Resource
	MessageService messageService;
	@Resource
	private OrderService orderService;
	@Resource
	private BindInfoForLoginService bindInfoForLoginService;
	// 会员中心首页
	public String index() {

		GetDBdata dBdata = new GetDBdata();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//add by cuishigang start
		String flag = getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME).toString();
		Cookie[] cookies = getRequest().getCookies();
		String loginbindId="";
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				if ("loginBindId".equals(c.getName())){
					loginbindId = c.getValue();
					break;
				}
			}
		}
		Member member = null;
		if(!"".equals(loginbindId)&& "tencent".equals(flag)){
			BindInfoForLogin bindinfo = this.bindInfoForLoginService.load(loginbindId);
			Member mem = new Member();
			mem.setEmail(bindinfo.getKxbUserEmail());
			mem.setMobileNO(bindinfo.getKxbUserPhone());
			member = this.memberService.getUser(mem);
			if(member.getId()==null||"".equals(member.getId())){
				//跳转到信息完善页面
				if(windowAddr==null||"".equals(windowAddr)){
					windowAddr=getRequest().getHeader("Referer");
					//若windowAddr 来源页面 是用POST方式提交，那么URL中会有？,或者action结束，这样直接跳回去必然报错，还是返回首页~
					if(windowAddr==null||"".equals(windowAddr)||windowAddr.endsWith("?")||windowAddr.endsWith("action")){
						windowAddr=getRequest().getScheme() + "://" +  getRequest().getServerName() + ":"
					    + getRequest().getServerPort()+ "/";//没有找到来源 返回首页
					}
				}
				this.bindInfoForLogin = bindinfo;
				return "register";
			}
		}
		String memberId = "";
		if(getLoginMember()!=null){
			memberId = getLoginMember().getId();
		}
		if(memberId==null || "".equals(memberId)){
			if(member!=null){
				memberId = member.getId();
			}
			
		}
		//add by cuishigang end
	
		//是否第一次登录会员中心
		Member mem = getLoginMember();
		if(mem!=null){
			String 	fullDegree=mem.getFullDegree();
			if ("2".equals(mem.getRegisterType()) && mem.getIsfirstMC()==null && !"Y".equals(mem.getIsMobileNOBinding())) {
				request.setAttribute("isFirstLoginAutoReg", "Y");
				String sql = "update member set isfirstMC='Y' where id ='"+memberId+"'";
				new QueryBuilder(sql).executeNoQuery();
			}
			if(mem.getIsfirstMC()==null && "15%".equals(fullDegree)){
				isfirstMC="Y";
				String sql = "update member set isfirstMC='"+isfirstMC+"' where id ='"+memberId+"'";
				new QueryBuilder(sql).executeNoQuery();
			}else{
				int count = 0;
				if (!(mem.getBirthday() == null || "".equals(mem.getBirthday())))
					count+=15;
				if (!(mem.getEmail() == null || "".equals(mem.getEmail())))
					count+=15;
				if (!(mem.getIDType() == null || "".equals(mem.getIDType()) || "-1"
						.equals(mem.getIDType())))
					count+=4;
				if (!(mem.getIndustryType() == null || "".equals(mem.getIndustryType())))
					count+=3;
				if (!(mem.getLocation() == null || "".equals(mem.getLocation()) || "-1".equals(mem.getLocation())))
					count+=4;
				if (!(mem.getMarriageStatus() == null
						|| "".equals(mem.getMarriageStatus()) || "-1".equals(mem
						.getMarriageStatus())))
					count+=4;
				if (!(mem.getMobileNO() == null || "".equals(mem.getMobileNO())))
					count+=15;
				if (!(mem.getPosition() == null || "".equals(mem.getPosition())))
					count+=2;
				if (!(mem.getRealName() == null || "".equals(mem.getRealName())))
					count+=15;
				if (!(mem.getSex() == null || "".equals(mem.getSex())))
					count+=15;
				if (!(mem.getVIPType() == null
						|| "".equalsIgnoreCase(mem.getVIPType()) || "-1"
						.equals(mem.getVIPType())))
					count+=4;
				if (!(mem.getZipcode() == null || "".equals(mem.getZipcode())))
					count+=4;
				
				fullDegree = count + "%";
				
			}
			//将完整度放入session
			request.getSession().setAttribute("fullDegree", fullDegree);
			memberSchema tMemberSchema = new memberSchema();
			tMemberSchema.setid(mem.getId());
			if(tMemberSchema.fill()){
				tMemberSchema.setfullDegree(fullDegree);
				tMemberSchema.update();
			}
		}
		
		// 不是第一次登陆 判断是否是升级后第一次登陆
		if (!"Y".equals(isfirstMC)) {
			if(mem != null){
				String pointScalerTime = Config.getValue("PointScalerTime");
				// 升级时间不为空，当前时间大于等于升级时间时
				if (StringUtil.isNotEmpty(pointScalerTime)
						&& DateUtil.compare(DateUtil.getCurrentDateTime(),
								pointScalerTime, DateUtil.Format_DateTime) >= 0) {
					// 升级后初次登陆时间为空,记录升级后初次登录时间
					if (mem.getLoginDateAfterUngrade() == null) {
						String sql = "update member set LoginDateAfterUngrade=? where id = ?";
						new QueryBuilder(sql, new Date(), mem.getId()).executeNoQuery();
						isfirstMCAfterUngrade = "Y";
					}
				}
			}
		}
		
		String[] tempParame = {memberId};
		
		String queryMember = "select member.id,realName,registerType,email,isEmailBinding,mobileNO,isMobileNOBinding,"
				+ "currentValidatePoint,point,aboutToExpirePoints,birthday,birthYear,member.grade,vipFlag,a.gradeName"
			    + " from member left join MemberGrade a on a.gradeCode = member.grade where member.id = ?";
		
		try {
			List<HashMap<String, String>> memberResultList = dBdata
			.query(queryMember,tempParame);
			
			if (memberResultList != null && memberResultList.size() > 0) {
				Map<String, String> mapTemp = getGradeIcon(memberResultList
						.get(0).get("vipFlag"),
						memberResultList.get(0).get("grade"), memberResultList
								.get(0).get("gradeName"),
						memberResultList.get(0).get("birthday"),
						memberResultList.get(0).get("birthYear"));
				memberResultList.get(0).put("gradeClass",
						mapTemp.get("gradeClass"));
				memberResultList.get(0).put("upgradeInfo",
						mapTemp.get("gradeInfo"));
				memberResultList.get(0).put("gradeDesc",
						mapTemp.get("gradeDesc"));
				if (StringUtil.isEmpty(memberResultList.get(0).get("aboutToExpirePoints"))) {
					memberResultList.get(0).put("aboutToExpirePoints", "0");
				}
				// 暂存保单数
				QueryBuilder qb = new QueryBuilder("SELECT COUNT(1) FROM sdorders WHERE memberid=? AND orderstatus='4' AND channelsn != 'jfsc' AND channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode) AND ( dellFlag IS NULL OR dellFlag = '')", memberId);
				memberResultList.get(0).put("storeCount", qb.executeString());
				
			}
			request.setAttribute("memberIndexList", memberResultList);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		// 优惠券信息
		request.setAttribute("couponList", getCouponInfo(memberId));
		// 会员浏览记录
		request.setAttribute("memberBrowseList", getBrowseRecord(memberId));
		// 会员收藏记录
		request.setAttribute("memberStowList", getStowRecord(memberId));
		
		// 常见问题
		String innerCode = Config.getValue("PointDescInnerCode");
		List<HashMap<String, String>> info = new ArrayList<HashMap<String, String>>();
		if (StringUtil.isNotEmpty(innerCode)) {
			String sql = "select ID,Title from zcarticle where CatalogInnerCode = ? and status=30 and Attribute like '%newRecommend%' order by topflag desc,orderflag desc,publishdate desc,id desc limit 0,5;";
			DataTable dt = new QueryBuilder(sql, innerCode).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				HashMap<String, String> map = new HashMap<String, String>();
				int rowCount = dt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					map = new HashMap<String, String>();
					map.put("ID", dt.getString(i, 0));
					map.put("Title", dt.getString(i, 1));
					info.add(map);
				}
				request.setAttribute("pointsDescList", info);
			}
		}
		
		return getHeadPic();
	}
	
	/**
	 * 取得会员收藏产品记录
	 * @param memberId
	 * @return
	 */
	public List<Map<String, String>> getStowRecord(String memberId) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = "select b.ProductID,b.ProductName, b.HtmlPath, b.Remark6, s.AdaptPeopleInfoListV3 "
				+ "from ProductCollection a,SDProduct b, sdsearchrelaproduct s "
				+ "where a.MemberID=? and a.ProductID=b.ProductID and a.ProductID=s.ProductID "
				+ "and b.IsPublish='Y' order by a.CreateDate desc limit 0, 3";
		DataTable dt = new QueryBuilder(sql, memberId).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			Map<String, String> map;
			AjaxPriceAction ap = new AjaxPriceAction();
			for (int i = 0; i < rowCount; i++) {
				map = new HashMap<String, String>();
				map.put("URL", dt.getString(i, "HtmlPath"));
				map.put("ProductName", dt.getString(i, "ProductName"));
				map.put("AdaptPeopleInfoV3", dt.getString(i, "AdaptPeopleInfoListV3"));
				map.put("CompanyCode", dt.getString(i, "Remark6"));
				map.put("InitPrem", ap.queryAjaxPrice(dt.getString(i, "ProductID"), "wj", memberId));
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 取得会员浏览记录
	 * @param memberId
	 * @return
	 */
	public List<Map<String, String>> getBrowseRecord(String memberId) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = " select r.ProductID,r.ProductName,r.URL,r.AdaptPeopleInfoListV3 from MemberBrowseRecord b, sdsearchrelaproduct r "
				+ "where b.memberId = ? and b.productId=r.ProductID order by b.createDate desc limit 0, 3 ";
		DataTable dt = new QueryBuilder(sql, memberId).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			Map<String, String> map;
			AjaxPriceAction ap = new AjaxPriceAction();
			for (int i = 0; i < rowCount; i++) {
				map = new HashMap<String, String>();
				map.put("URL", dt.getString(i, "URL"));
				map.put("ProductName", dt.getString(i, "ProductName"));
				map.put("AdaptPeopleInfoV3", dt.getString(i, "AdaptPeopleInfoListV3"));
				map.put("CompanyCode", dt.getString(i, "ProductID").substring(0, 4));
				map.put("InitPrem", ap.queryAjaxPrice(dt.getString(i, "ProductID"), "wj", memberId));
				list.add(map);
			}
		}
		
		return list;
	}
	
	/**
	 * 取得会员可使用的优惠券信息
	 * @param memberId
	 * @return
	 */
	public List<Map<String, String>> getCouponInfo (String memberId) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		String sql = " select parValue,date_format(startTime,'%Y-%m-%d') as startTime, prop3, prop4,"
				   + " date_format(endTime,'%Y-%m-%d') as endTime, shortName from CouponInfo" 
				   + " where status='2' and memberId = ? and UNIX_TIMESTAMP(endTime) > UNIX_TIMESTAMP(now())" 
				   + " order by startTime asc ";
		DataTable dt = new QueryBuilder(sql, memberId).executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			Map<String, String> map;
			for (int i = 0; i < rowCount; i++) {
				map = new HashMap<String, String>();
				// 非折扣券
				if ("01".equals(dt.getString(i, "prop3")) || StringUtil.isEmpty(dt.getString(i, "prop3"))) {
					// 面值
					map.put("parValue", dt.getString(i, "parValue"));
					map.put("unit", "￥");
					map.put("couponType", "优惠券");
				} else {
					// 折扣
					map.put("parValue", dt.getString(i, "prop4"));
					map.put("unit", "折");
					map.put("couponType", "折扣券");
				}
				// 起始时间
				map.put("startDate", dt.getString(i, "startTime"));
				// 终止时间
				map.put("endDate", dt.getString(i, "endTime"));
				// 简短描述
				map.put("shortName", dt.getString(i, "shortName"));
				list.add(map);
			}
		}
		
		return list;
	}
	
	/**
	 * 判断是否是生日月
	 * 
	 * @param birthday
	 *            会员生日
	 * @param birthYear
	 *            已享受生日月最新年月
	 * @return
	 */
	public boolean isMemBirthMonth(String birthday, String birthYear) {
		boolean birMonthFlag = false;
		if (StringUtil.isNotEmpty(birthday)) {
			String currYear = DateUtil.getCurrentDate("yyyy");
			String currMonth = DateUtil.getCurrentDate("MM");
			String birMonth = birthday.substring(5, 7);
			// 当前月是生日月 则看今年时候已参加过生日月活动
			if (birMonth.equals(currMonth)) {
				if (StringUtil.isNotEmpty(birthYear)) {
					if (!birthYear.contains(currYear)) {
						birMonthFlag = true;
					} else if (birthYear.equals(currYear + "-" + birMonth)) {
						birMonthFlag = true;
					}
				} else {
					birMonthFlag = true;
				}
			}
		}
		return birMonthFlag;
	}
	
	/**
	 * 取得会员对应等级的图标
	 * 
	 * @param vipFlag
	 *            VIP标识 Y:是VIP
	 * @param grade
	 *            会员等级
	 * @param gradeName
	 *            会员等级名称
	 * @param birthday
	 *            会员生日
	 * @param birthYear
	 *            已享受生日月最新年月
	 * @return
	 */
	public Map<String, String> getGradeIcon(String vipFlag, String grade,
			String gradeName, String birthday, String birthYear) {
		Map<String, String> map = new HashMap<String, String>();

		// 判断当前时间是否是生日月
		boolean birMonthFlag = isMemBirthMonth(birthday, birthYear);

		String gradeClass = "";
		String gradeInfo = "";
		String gradeDesc = "";
		if ("Y".equals(vipFlag)) {
			if (birMonthFlag) {
				gradeClass = "vip_k vip_bkvip";
				gradeDesc = "VIP会员生日月积分加倍";
			} else {
				gradeClass = "vip_k vip_kvip";
				gradeDesc = "VIP会员积分加成";
			}
			gradeInfo = "您是尊贵的VIP会员，享受更多特权";
		} else {

			String orderCount = "";
			String sumPrem = "";
			DataTable dt = new QueryBuilder(
					"select gradeName, orderCount, sumPrem, link from MemberGrade where preGradeCode = ?", grade)
					.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				orderCount = dt.getString(0, 1);
				sumPrem = dt.getString(0, 2);
			}
			
			if ("K0".equals(grade)) {
				gradeClass = "vip_k vip_no";
				gradeInfo += "消费" + orderCount + "次做会员，享更多优惠";
				gradeDesc = "普通会员";

			} else if ("K1".equals(grade)) {
				gradeClass = "vip_k vip_k1";
				gradeDesc = "K1会员积分加成";
				if (birMonthFlag) {
					gradeClass = "vip_k vip_bk1";
					gradeDesc = "K1会员生日月积分加倍";
				}
				gradeInfo += "消费" + orderCount + "次满" + sumPrem + "元，升级K2会员";

			} else if ("K2".equals(grade)) {
				gradeClass = "vip_k vip_k2";
				gradeDesc = "K2会员积分加成";
				if (birMonthFlag) {
					gradeClass = "vip_k vip_bk2";
					gradeDesc = "K2会员生日月积分加倍";
				}
				gradeInfo = "申请VIP！享受更多特权！";
			}
		}
		map.put("gradeClass", gradeClass);
		map.put("gradeInfo", gradeInfo);
		map.put("gradeDesc", gradeDesc);

		return map;
	}
		
    public String getHeadPic(){
    	
        HttpServletRequest request = ServletActionContext.getRequest();
        Member meb = getLoginMember();
		
		if(meb!=null){
			String memberId = meb.getId();
			String[] tempParame = {memberId};
			GetDBdata dBdata = new GetDBdata();
			String queryPic = "select headPicPath,aboutToExpirePoints,aboutToExpireDate from member where id = ?";
			
			// 查询原头像
			try {
				List<HashMap<String, String>> picResult = dBdata
				.query(queryPic,tempParame);
				request.setAttribute("memberHead", picResult);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
        
    	return "index";
	}
	
	public String queryForFullDegree(){
		String memberid="";
		String result = "";
		if(getLoginMember()!=null){
			memberid = getLoginMember().getId();
		}
		String sql = "SELECT fullDegree FROM member WHERE id='"+memberid+"' ";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		if(dt!=null && dt.getRowCount()>0){
			result = dt.getString(0, 0);
		}
			
		Map<String, Object> price = new HashMap<String, Object>();
		price.put("fullDegree", result.toString());// 资料完整度
		JSONObject jsonObject = JSONObject.fromObject(price);
		
		return ajax(jsonObject.toString(), "text/html");
	}

	// 获取未读消息数量
	public Long getUnreadMessageCount() {
		return messageService.getUnreadMessageCount(getLoginMember());
	}

	public String validateData() {
		if (appName == null || appName.equals("")) {
			addActionError("投保人姓名不能为空");
			return ERROR;
		}
		if (mobileNO == null || mobileNO.equals("")) {
			addActionError("手机号不能为空");
			return ERROR;
		}
		String name;
		String phoneNO;
		String flag = "noPass";
		GetDBdata db = new GetDBdata();
		// if ("0".equals(codeType)) {// 表示非车险产品

		String sql = "select d.applicantname,d.applicantmobile from orders a , orderitem b, information c ,informationappnt d "
				+ " where a.id = b.order_id and c.orderitem_id = b.id and d.information_id = c.id " + " and ordersn=?";
		String[] sqltemp = {serialNO};   
		List<HashMap<String, String>> list;
		try {
			list = db.query(sql,sqltemp);
			Iterator<HashMap<String, String>> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				name = (String) map.get("applicantName");
				phoneNO = (String) map.get("applicantMobile");
				if (name.equals(appName) && phoneNO.equals(mobileNO)) {
					flag = "pass";
				}
			}
			String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);

			String[] serialNO2 = serialNO.split(",");
			for (int i = 0; serialNO2 != null && i < serialNO2.length; i++) {
				String ods = serialNO2[i];
				if (StringUtil.isNotEmpty(ods)) {
					Order order = orderService.getOrderByOrderSn(ods);
					if (StringUtil.isEmpty(order.getMemberId())) {
						order.setMemberId(id);
						orderService.update(order);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		if (flag.equals("pass")) {
			return "finish";
		} else {
			return "error1";
		}
	}
	
	public List<Map<String, String>> getRecommProduct(String memberId) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		AjaxPriceAction ap = new AjaxPriceAction();
		//查询最近一次订单信息中的productid
		String sql = " select s2.productID from sdorders s1, sdinformation s2 where s1.memberid =? "+ 
	            "and s1.orderSn=s2.orderSn order by s1.createdate desc LIMIT 0,1";
		String productId = new QueryBuilder(sql, memberId).executeString();
		if (StringUtil.isNotEmpty(productId)) {
			String productid = "";
			//根据productid查询此产品所有分类
			String sql1="SELECT a.* from(select d.id,d.ProductType"+
			         " from zcarticle b,ZDColumnValue c ,zccatalog d"+
					 " where b.CatalogInnerCode like '002313%' and b.id=c.relaid and c.columncode='RiskCode' and c.textvalue =? and b.catalogid=d.id"+
			         " order by d.ProductType) a order by rand() limit 0,4";
			QueryBuilder qb1 = new QueryBuilder(sql1, productId);
			DataTable dt1 = qb1.executeDataTable();
			if(dt1 != null && dt1.getRowCount() > 0){
				int cataidNu = dt1.getRowCount();
				//查询属于最新订单第一个分类下的销量最高的产品
				String sql3="select z2.*"+
						" from zcarticle z1,sdsearchrelaproduct z2"+
						" where z1.catalogid =? and z1.title = z2.productname "+
						" order by salesvolume+0 desc limit 0,1";
				
				for(int i=0;i<cataidNu;i++){
					if(i==0){
						QueryBuilder qb3 = new QueryBuilder(sql3);
						qb3.add(dt1.get(i).getString("id"));
						DataTable dt3 = qb3.executeDataTable(); 
						if(dt3!=null && dt3.getRowCount()>0){
							String newPrice = ap.queryAjaxPrice(dt3.get(0).getString("productid"), memberId);
							Map<String, String> map = new HashMap<String, String>();
							map.put("productName", dt3.get(0).getString("productName"));
							map.put("productUrl", dt3.get(0).getString("url"));
							map.put("initPrem", newPrice);
							map.put("companyCode", dt3.get(0).getString("productid").substring(0, 4));
							list.add(map);
							productid += "'"+dt3.get(0).getString("productid")+"'";
						}
					}else {
						//查询属于最新订单不同于第一个分类下的销量最高的产品
						String sql4="select z2.*"+
								" from zcarticle z1,sdsearchrelaproduct z2"+
								" where z1.catalogid =? and z1.title = z2.productname "+
								"and productid not in ("+productid+")"+
								" order by salesvolume+0 desc limit 0,1";
						QueryBuilder qb4 = new QueryBuilder(sql4);
						qb4.add(dt1.get(i).getString("id"));
						DataTable dt4 = qb4.executeDataTable();
						if(dt4!=null && dt4.getRowCount()>0){
							String newPrice = ap.queryAjaxPrice(dt4.get(0).getString("productid"), memberId);
							Map<String, String> map = new HashMap<String, String>();
							map.put("productName", dt4.get(0).getString("productName"));
							map.put("productUrl", dt4.get(0).getString("url"));
							map.put("initPrem", newPrice);
							map.put("companyCode", dt4.get(0).getString("productid").substring(0, 4));
							list.add(map);
							productid +=",'"+dt4.get(0).getString("productid")+"'";
						}
		
					}
				}
				int count = 4-list.size();
				if(count > 0){
					String sql2="SELECT * FROM sdsearchrelaproduct WHERE productid NOT IN ("+productid+") ORDER BY salesvolume+0 DESC limit 0,"+count;
					QueryBuilder qb4 = new QueryBuilder(sql2);
					DataTable dt4 = qb4.executeDataTable();
					if(dt4!=null && dt4.getRowCount()>0){
						for(int i=0;i<dt4.getRowCount();i++){
							String newPrice = ap.queryAjaxPrice(dt4.get(i).getString("productid"), memberId);
							Map<String, String> map = new HashMap<String, String>();
							map.put("productName", dt4.get(i).getString("productName"));
							map.put("productUrl", dt4.get(i).getString("url"));
							map.put("initPrem", newPrice);
							map.put("companyCode", dt4.get(i).getString("productid").substring(0, 4));
							list.add(map);
						}
					}
				}
			}
			
		} else {
			String sql5="select * from sdsearchrelaproduct order by salesvolume+0 desc limit 0,4";
			QueryBuilder qb4 = new QueryBuilder(sql5);
			DataTable dt4 = qb4.executeDataTable();
			if(dt4!=null && dt4.getRowCount()>0){
				for(int i =0;i<dt4.getRowCount();i++){
					String newPrice = ap.queryAjaxPrice(dt4.get(i).getString("productid"), memberId);
					Map<String, String> map = new HashMap<String, String>();
					map.put("productName", dt4.get(i).getString("productName"));
					map.put("productUrl", dt4.get(i).getString("url"));
					map.put("initPrem", newPrice);
					map.put("companyCode", dt4.get(i).getString("productid").substring(0, 4));
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 产品中心首页-产品推荐
	 * 
	 * @author jiaomengying
	 * */
	
	public String ajaxProductNew() {
		String memberId = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		List<Map<String, String>> list = getRecommProduct(memberId);

		StringBuffer result = new StringBuffer();
		if (list != null && list.size() > 0) {
			int size = list.size();
			result.append("<h3 class=\"member-htit\"><span class=\"member-titsc\">为您推荐</span></h3>");
			for (int i = 0; i < size; i++) {
				result.append("<dl class=\"error_list\">");
				result.append("<dt><div class=\"icon_C"+list.get(i).get("companyCode")+" m-list-logos\">");
				result.append("<a href=\""+list.get(i).get("productUrl")+"\" target=\"_blank\"></a></div></dt>");
				result.append("<dd class=\"error_tit\"><a href=\""+list.get(i).get("productUrl")+"\" target=\"_blank\">");
				result.append(list.get(i).get("productName")+"</a></dd>");
				result.append("<dd><span class=\"error_pay f_mi\">￥"+list.get(i).get("initPrem"));
				result.append("<a href=\""+list.get(i).get("productUrl")+"\" class=\"error_a\" target=\"_blank\">");
				result.append("去看看&gt;&gt;</a></span></dd></dl>"); 
			}
			result.append("<div class=\"clear\"></div>");
		}
	
		Map<String, Object> price = new HashMap<String, Object>();
		price.put("products", result.toString());
		JSONObject jsonObject = JSONObject.fromObject(price);
	
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/*public String ajaxProduct() {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.*,(select textvalue from zdcolumnvalue where columncode = 'SalesVolume' and relaid = a.id) as SalesVolume, ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'CalHTML2' and relaid = a.id) as CalHTML2,   ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'AdaptPeopleInfo' and relaid = a.id) as AdaptPeopleInfo,    ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'FEMRiskBrightSpot' and relaid = a.id) as FEMRiskBrightSpot,");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'DutyHTML' and relaid = a.id) as DutyHTML,   ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'prodcutMarkPrice' and relaid = a.id) as prodcutMarkPrice , ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'SupplierCode2' and relaid = a.id) as SupplierCode2,        ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'initPrem' and relaid = a.id) as initPrem,   ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'Popular' and relaid = a.id) as Popular,  ");
		sb.append("(select textvalue from zdcolumnvalue where ColumnCode = 'riskcode' and relaid = a.id) as RiskCode   ");
		sb.append("from zcarticle a where status=30 and Attribute like '%newRecommend%'   ");
		sb.append("and cataloginnercode like '002313%' limit 3   ");
		
		DataTable dt = new QueryBuilder(sb.toString()).executeDataTable();
		StringBuffer result = new StringBuffer();
		if (dt != null && dt.getRowCount() > 0) {
			for( int i=0;i<dt.getRowCount();i++){
				result.append( "<div class='product_title'>");
				result.append("<span class='CInsuranceCompany icon_C"+dt.get(i).getString("SupplierCode2")+"'></span><span class='productName'> <a target='_blank' href='"+Config.getFrontServerContextPath()+"/"+dt.get(i).getString("URL")+"')\" ><h2 class='ziti'>"+dt.get(i).getString("Title")+"</h2></a>");
				result.append("</span> <span class='SalesVolume'>(累计销量："+dt.get(i).getString("SalesVolume")+")</span><span id='productIntegral_"+dt.get(i).getString("RiskCode")+"' style='display: none;'></span>");
				result.append("</div>");
				result.append("<div class='product_condition'>");
				result.append(""+dt.get(i).getString("CalHTML2")+"");
				result.append("</div>");
				result.append("<div class='product_info'>");
				result.append("<div class='product_info_bor'>");
				result.append("<div class='prodcutMark'>");
				result.append("<ul class='price'>"+dt.get(i).getString("prodcutMarkPrice")+"</ul>");	
				result.append("<ul class='btn'>");	
				result.append("<li class='btn1'><span id='chakan' onClick=\"chakan('"+dt.get(i).getString("URL")+"')\">查看详情</span></li>");	
//				result.append("<li class='btn2'><span onClick=\"submitp('"+dt.get(i).getString("RiskCode")+"');\">加入收藏</span><span class='add_cp_list' style='margin-left: 6px;'");
//				result.append("onclick=\"showcp('"+dt.get(i).getString("Title")+"','"+dt.get(i).getString("Logo")+"','"+dt.get(i).getString("RiskCode")+"','"+dt.get(i).getString("URL")+"','A01');\">加入对比</span></li>");
				result.append("</ul>");	
				result.append("</div>");
				result.append("</div>"); 
				result.append("<div class='AdaptPeopleInfo'>"+dt.get(i).getString("AdaptPeopleInfo")+"</div>");
				result.append("<div class='productFeature'>"+dt.get(i).getString("FEMRiskBrightSpot")+"</div>");
				result.append(""+dt.get(i).getString("DutyHTML")+"");
				result.append("</div>");
			}
		}
		
		Map<String, Object> price = new HashMap<String, Object>();
		price.put("products", result.toString());// 折扣保费
		JSONObject jsonObject = JSONObject.fromObject(price);
		return ajax(jsonObject.toString(), "text/html");
	}*/
	
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getMobileNO() {
		return mobileNO;
	}

	public void setMobileNO(String mobileNO) {
		this.mobileNO = mobileNO;
	}

	public String getSerialNO() {
		return serialNO;
	}

	public void setSerialNO(String serialNO) {
		this.serialNO = serialNO;
	}

	public String getWindowAddr() {
		return windowAddr;
	}

	public void setWindowAddr(String windowAddr) {
		this.windowAddr = windowAddr;
	}

	public BindInfoForLogin getBindInfoForLogin() {
		return bindInfoForLogin;
	}

	public void setBindInfoForLogin(BindInfoForLogin bindInfoForLogin) {
		this.bindInfoForLogin = bindInfoForLogin;
	}

	public String getIsfirstMC() {
		return isfirstMC;
	}

	public void setIsfirstMC(String isfirstMC) {
		this.isfirstMC = isfirstMC;
	}

	public String getIsfirstMCAfterUngrade() {
		return isfirstMCAfterUngrade;
	}

	public void setIsfirstMCAfterUngrade(String isfirstMCAfterUngrade) {
		this.isfirstMCAfterUngrade = isfirstMCAfterUngrade;
	}

	public String getTagid() {
		return tagid;
	}

	public void setTagid(String tagid) {
		this.tagid = tagid;
	}

}