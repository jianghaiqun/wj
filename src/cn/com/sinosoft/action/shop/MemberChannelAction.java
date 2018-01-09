package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.MemberChannelService;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.util.Constant;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台Action类 - 会员频道 
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFTD06041D9E5D00829FE51D5C8A9D10C0B
 * ============================================================================
 */

@ParentPackage("shop")
public class MemberChannelAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762732013742180256L;

	private Member member;
	private String memberGrade;
	private String gradeIcon;
	
	private String vipCon = "vipno";
	
	private String pageIndex;// 会员部落 积分产品信息（页数）
	private String emails;// 推荐会员email

	@Resource
	private MemberService memberService;

	@Resource
	private MemberChannelService memberChannelService;

	public String index() {

		Map<String, String> showData = new HashMap<String, String>();
		
		HttpServletRequest request = ServletActionContext.getRequest();
		GetDBdata dBdata = new GetDBdata();
		member = getLoginMember();
		
		// 进度条提示信息显示
		showData.put("dd1POPUP", "none");
		showData.put("dd2POPUP", "none");
		showData.put("dd3POPUP", "none");
		
		if (null != member) {
			
			// 会员等级进度条
			Map<String, String> ddPercentMap = memberChannelService.ddPercent(member);
			showData.putAll(ddPercentMap);
			memberGrade = member.getGrade();
			boolean birthdayMonth = new MemberCenterAction().isMemBirthMonth(member.getBirthday(), member.getBirthYear());
			// gradeIcon 会员级别图形
			if ("Y".equals(member.getVipFlag())) {
				if (birthdayMonth) {
					gradeIcon = "vip_bkvip";
				} else {
					gradeIcon = "vip_kvip";
				}
				vipCon = "vipvip";
			}else{
				if (Constant.MEMBER_GRADE_K0.equals(memberGrade)) {

					gradeIcon = "vip_no";
					vipCon = "vipuser";
					showData.put("dd1POPUP", "");
				} else if (Constant.MEMBER_GRADE_K1.equals(memberGrade)) {

					if (birthdayMonth) {
						gradeIcon = "vip_bk1";
					} else {
						gradeIcon = "vip_k1";
					}
					vipCon = "vipk1";
					showData.put("dd2POPUP", "");
				} else if (Constant.MEMBER_GRADE_K2.equals(memberGrade)) {

					if (birthdayMonth) {
						gradeIcon = "vip_bk2";
					} else {
						gradeIcon = "vip_k2";
					}
					vipCon = "vipk2";
					showData.put("dd3POPUP", "");
				}
				else {
					gradeIcon = "vip_no";
					vipCon = "vipuser";
				}
			}
			
			String[] tempParame = { member.getId() };
			String queryMember = "select member.id,realName,date_format(member.createDate,'%Y年%m月%d日') as createDate,"
					+ "date_format(member.modifyDate,'%Y年%m月%d日') as modifyDate,"
					+ "email,"
					+ "isEmailBinding,"
					+ "mobileNO,"
					+ "isMobileNOBinding,"
					+ "headPicPath,"
					+ "currentValidatePoint,"
					+ "registerType,"
					+ "(select count(id) from couponinfo where memberid= member.id and  (UNIX_TIMESTAMP(couponinfo.endTime) > UNIX_TIMESTAMP(now())) and couponinfo.status='2' ) as couponnum,"
					+ "(select count(id) from couponinfo where memberid= member.id and  (UNIX_TIMESTAMP(couponinfo.endTime) > UNIX_TIMESTAMP(now())) and  UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d'))>=UNIX_TIMESTAMP(DATE_FORMAT(endtime,'%Y-%m-%d'))-3600*24*7 AND STATUS ='2' ) as coupondatenum,"
					+ "(SELECT COUNT(1) FROM sdorders a,sdinformation b, sdsearchrelaproduct c WHERE a.memberid = member.id AND a.orderStatus IN ('7','10') and a.orderSn = b.orderSn and b.productid = c.productid AND a.commentId IS NULL) AS commentNum,"
					+ "point,birthday,birthYear,member.grade,vipFlag,a.gradeName,a.link from member left join MemberGrade a on a.gradeCode = member.grade where member.id = ?";

			try {
				List<HashMap<String, String>> memberResultList = dBdata.query(
						queryMember, tempParame);

				request.setAttribute("memberChannelIndex", memberResultList.get(0));

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

		}

		// 会员特权描述
		Map<String, String> privilegesMap = memberChannelService.privileges(member);
		showData.putAll(privilegesMap);
		// 广告
		Map<String, String> campaignMap = memberChannelService.campaign();
		showData.putAll(campaignMap);
		// 积分产品信息
		Map<String, String> pointsProduct = memberChannelService.pointsProduct(1);
		showData.putAll(pointsProduct);
		// add by wangej 20160127 推荐信息
		Map<String, String> recommendInfoAll = memberChannelService.recommendInfoAll();
		showData.putAll(recommendInfoAll);
		// 推荐信息url和title
		if (null != member) {
			Map<String, String> recommendInfo = memberChannelService.recommendInfo(member);
			showData.putAll(recommendInfo);
		}
		// 完善会员资料获取积分信息
		Map<String, String> givePointsInfo = memberChannelService.givePointsInfo();
		showData.putAll(givePointsInfo);

		Map<String, Object> mc = memberChannelService.memberAndzccomment();
		request.setAttribute("mc", mc);
		
		List<Map<String, String>> activityProduct = memberChannelService.activityProduct(member);
		
		if (activityProduct.size() == 1) {
			request.setAttribute("activityProduct1", activityProduct);
		} else if (activityProduct.size() == 2) {
			request.setAttribute("activityProduct2", activityProduct);
		} else {
			logger.warn("会员特价产品没有数据！");
		}

		request.setAttribute("showData", showData);

		return "index";
	}

	/**
	 * 会员部落，积分查询
	 * 
	 * @return
	 */
	public String pointsProduct(){
		
		Map<String, String> pointsProduct = memberChannelService.pointsProduct(Integer.valueOf(pageIndex));
		
		return ajaxHtml(JSONObject.fromObject(pointsProduct).toString());
	}
	
	public String privilege() {
		Map<String, Object> showData = new HashMap<String, Object>();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String K1PointReturn = "";
		String K2PointReturn = "";
		String BirthPoint = "";
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("MemberGrade", "K1");
		PointsCalculate PointsCalculate = new PointsCalculate();
		Map<String, Object> map;
		try {
			// k1会员加成
			map = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_BUY, param);
			if (IntegralConstant.SUCCESS.equals(String.valueOf(map.get(IntegralConstant.STATUS)))) {
				if (StringUtil.isNotEmpty((String) map.get(IntegralConstant.ACTION_POINTS))) {
					K1PointReturn = StringUtil.subZeroAndDot(new BigDecimal(100).multiply(
							new BigDecimal((String) map.get(IntegralConstant.ACTION_POINTS))).toString())
							+ "%";
				} else {
					K1PointReturn = "无";
				}
			} else {
				K1PointReturn = "无";
			}
			showData.put("K1PointReturn", K1PointReturn);
			// k1会员生日月
			map = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_BIRTH_MONTH, param);
			if (IntegralConstant.SUCCESS.equals(String.valueOf(map.get(IntegralConstant.STATUS)))) {
				if (StringUtil.isNotEmpty((String) map.get(IntegralConstant.ACTION_POINTS))) {
					// 取出生日月加成积分 加1换算成倍数
					BirthPoint = StringUtil.subZeroAndDot(NumberUtil.round(Double
							.valueOf((String) map
									.get(IntegralConstant.ACTION_POINTS)) + 1, 2)+"");
				} else {
					BirthPoint = "";
				}
			}
			showData.put("K1BirthPoint", BirthPoint);
			// k2会员加成
			param.put("MemberGrade", "K2");
			map = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_BUY, param);
			if (IntegralConstant.SUCCESS.equals(String.valueOf(map.get(IntegralConstant.STATUS)))) {
				if (StringUtil.isNotEmpty((String) map.get(IntegralConstant.ACTION_POINTS))) {
					K2PointReturn = StringUtil.subZeroAndDot(new BigDecimal(100).multiply(
							new BigDecimal((String) map.get(IntegralConstant.ACTION_POINTS))).toString())
							+ "%";
				} else {
					K2PointReturn = "无";
				}
			} else {
				K2PointReturn = "无";
			}
			showData.put("K2PointReturn", K2PointReturn);
			// k2会员生日月
			map = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_BIRTH_MONTH, param);
			if (IntegralConstant.SUCCESS.equals(String.valueOf(map.get(IntegralConstant.STATUS)))) {
				if (StringUtil.isNotEmpty((String) map.get(IntegralConstant.ACTION_POINTS))) {
					// 取出生日月加成积分 加1换算成倍数
					BirthPoint = StringUtil.subZeroAndDot(NumberUtil.round(Double
							.valueOf((String) map
									.get(IntegralConstant.ACTION_POINTS)) + 1, 2)+"");
				} else {
					BirthPoint = "";
				}
			}
			showData.put("K2BirthPoint", BirthPoint);
			// 评论送积分数量
			String commPoint = "0";
			try {
				// 取得评论送积分数量
				Map<String, Object> pointsMap = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_COMMENT, null);
				if (pointsMap.get(IntegralConstant.ACTION_POINTS) != null && !"0".equals(pointsMap.get(IntegralConstant.ACTION_POINTS))) {
					commPoint = String.valueOf(pointsMap.get(IntegralConstant.ACTION_POINTS));
				}
				// 取得注册好友送积分数量
				Map<String, Object> map1 = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER, null);
				if (map1.get(IntegralConstant.ACTION_POINTS) != null && !"0".equals(map1.get(IntegralConstant.ACTION_POINTS))) {
					showData.put("recommRegCount", String.valueOf(map1.get(IntegralConstant.ACTION_POINTS)));
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			showData.put("commPoint", commPoint);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		showData.put("exchangeBoundary", Config.getValue("exchangeBoundary"));
		
		// 取得VIP特权说明
		List<Map<String, String>> vipInfo= new ArrayList<Map<String, String>>();
		DataTable dt = new QueryBuilder("SELECT PrivilegesName,description FROM MemberPrivileges where MemberLevel='VIP' order by orderFlag+0 asc ").executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			Map<String, String> vipMap;
			for (int i = 0; i < rowCount; i++) {
				vipMap = new HashMap<String, String>();
				vipMap.put("title", dt.getString(i, 0));
				vipMap.put("desc", dt.getString(i, 1));
				vipInfo.add(vipMap);
			}
		}
		showData.put("vipInfo", vipInfo);
		request.setAttribute("showData", showData);

		return "privilege";
	}
	
	public String grade() {
		Map<String, String> showData = new HashMap<String, String>();
		HttpServletRequest request = ServletActionContext.getRequest();
		DataTable dt = new QueryBuilder("select gradeCode, orderCount, sumPrem from membergrade where gradeCode in ('K1','K2')").executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			String gradeCode = "";
			String orderCount = "";
			String sumPrem = "";
			for (int i = 0; i < rowCount; i++) {
				gradeCode = dt.getString(i, "gradeCode");
				orderCount = dt.getString(i, "orderCount");
				sumPrem = dt.getString(i, "sumPrem");
				if (StringUtil.isEmpty(orderCount) || "0".equals(orderCount)) {
					orderCount = "不限";
				} else {
					orderCount = orderCount+"次";
				}
				showData.put(gradeCode+"OrderNum", orderCount);
				
				if (StringUtil.isEmpty(sumPrem) || "0".equals(sumPrem)) {
					sumPrem = "不限";
				} else {
					sumPrem = sumPrem+"元";
				}
				showData.put(gradeCode+"OrderPrem", sumPrem);
			}
		}
		request.setAttribute("showData", showData);

		return "grade";
	}
	
	public String point() {
		Map<String, String> showData = new HashMap<String, String>();
		HttpServletRequest request = ServletActionContext.getRequest();
		showData.put("PointScalerUnit", Config.getValue("PointScalerUnit"));
		PointsCalculate PointsCalculate = new PointsCalculate();
		// 评论送积分数量
		String commPoint = "0";
		try {
			// 取得评论送积分数量
			Map<String, Object> pointsMap = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_COMMENT, null);
			if (pointsMap.get(IntegralConstant.ACTION_POINTS) != null && !"0".equals(pointsMap.get(IntegralConstant.ACTION_POINTS))) {
				commPoint = String.valueOf(pointsMap.get(IntegralConstant.ACTION_POINTS));
			}
			// 取得注册好友送积分数量
			Map<String, Object> map1 = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH, IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER, null);
			if (map1.get(IntegralConstant.ACTION_POINTS) != null && !"0".equals(map1.get(IntegralConstant.ACTION_POINTS))) {
				showData.put("recommRegCount", String.valueOf(map1.get(IntegralConstant.ACTION_POINTS)));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		showData.put("commPoint", commPoint);
		showData.put("recommRegisterCount", Config.getValue("RecommRegisterCount"));
		
		// 取得注册好友购买送积分比例
		String recommBuyPoints = Config.getValue("RecommBuyPoints");
		String recommBuyPointsExam = "";
		if (StringUtil.isNotEmpty(recommBuyPoints)) {
			MemberRecommendAction reco = new MemberRecommendAction();
			String resu = new BigDecimal(recommBuyPoints).multiply(new BigDecimal(200)).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
			recommBuyPoints = reco.subZeroAndDot(new BigDecimal(recommBuyPoints).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());

			recommBuyPoints += "%";
			recommBuyPointsExam = "例好友购买订单获得200积分，推荐人将获得200*"+recommBuyPoints+"="+resu+"积分。";
		}
		showData.put("recommBuyPoints", recommBuyPoints);
		showData.put("recommBuyPointsExam", recommBuyPointsExam);
		// 完善会员资料获取积分信息
		Map<String, String> givePointsInfo = memberChannelService.givePointsInfo();
		showData.putAll(givePointsInfo);
		// 积分有效期
		String pointPeriod = Config.getConfigValue("PointValidityPeriod");
		if (StringUtil.isNotEmpty(pointPeriod)) {
			String unit = pointPeriod.substring(pointPeriod.length() - 1);
			if ("Y".equalsIgnoreCase(unit)) {
				pointPeriod = pointPeriod.substring(0, pointPeriod.length() - 1) + "年";
			} else if ("M".equalsIgnoreCase(unit)) {
				pointPeriod = pointPeriod.substring(0, pointPeriod.length() - 1) + "月";
			} else if ("D".equalsIgnoreCase(unit)) {
				pointPeriod = pointPeriod.substring(0, pointPeriod.length() - 1) + "日";
			}
		}
		showData.put("pointPeriod", pointPeriod);
		showData.put("pointStaPeriod", "2015年09月01日");
		showData.put("pointEndPeriod", DateUtil.toString(PubFun.getPointEvaliDate(DateUtil.parse("2015年09月01日", "yyyy年MM月dd日")), "yyyy年MM月dd日"));
		request.setAttribute("showData", showData);

		return "point";
	}
	
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getGradeIcon() {
		return gradeIcon;
	}

	public void setGradeIcon(String gradeIcon) {
		this.gradeIcon = gradeIcon;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getVipCon() {
		return vipCon;
	}

	public void setVipCon(String vipCon) {
		this.vipCon = vipCon;
	}
	
	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}
}