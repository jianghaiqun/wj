package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.MemberChannelDao;
import cn.com.sinosoft.entity.GiftClassify;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.MemberChannel;
import cn.com.sinosoft.service.GiftClassifyService;
import cn.com.sinosoft.service.MemberChannelService;
import cn.com.sinosoft.util.Constant;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FreemarkerUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import com.sinosoft.schema.MemberPrivilegesSchema;
import com.sinosoft.schema.MemberPrivilegesSet;
import com.sinosoft.schema.SDPointRuleSchema;
import com.sinosoft.schema.SDPointRuleSet;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service实现类 
 * ============================================================================
 * 
 *
 * 
 *
 * 
 *
 * KEY:SINOSOFT5CCDCA53AF8463D621530B1ADA0CE130
 * ============================================================================
 */

@Service
public class MemberChannelServiceImpl extends
		BaseServiceImpl<MemberChannel, String> implements MemberChannelService {

	@Resource
	private MemberChannelDao memberChannelDao;
	@Resource
	private GiftClassifyService mGiftClassifyService;

	@Resource
	public void setBaseDao(MemberChannelDao memberChannelDao) {
		super.setBaseDao(memberChannelDao);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.service.MemberChannelService#ddPercent(cn.com.sinosoft
	 * .entity.Member)
	 */
	@Override
	public Map<String, String> ddPercent(Member member) {

		Map<String, String> result = new HashMap<String, String>();
		String grade = member.getGrade();

		// String currentDate = DateUtil.getCurrentDate();
		// 订单有效数限制
		String orderCount = "";
		// 累计保费限制
		String sumPrem = "";
		// 记录会员有效订单数
		int ordCountTemp = 0;
		// 记录会员累计保费
		BigDecimal sumPremTemp = new BigDecimal(0);
		/*
		 * QueryBuilder qb; String sql =
		 * "select sum(r.payPrice+0) from sdorders o, tradeinformation t, sdinformationrisktype r where o.memberId = ? and o.orderSn = r.orderSn and o.orderSn = t.ordID and r.appstatus = '1' and r.payPrice is not null and r.payPrice != '' and o.orderStatus in ('7', '10', '12', '14') and DATE_FORMAT(r.svaliDate,'%Y-%m-%d') < ? "
		 * ; String groSql ="group by DATE_FORMAT(t.receiveDate,'%Y-%m-%d') ";
		 * 
		 * // 取得会员有效订单数 qb = new QueryBuilder(sql); qb.add(member.getId());
		 * qb.add(currentDate); qb.append(groSql);
		 * 
		 * DataTable dt = qb.executeDataTable(); if (dt != null &&
		 * dt.getRowCount() > 0) { ordCountTemp = dt.getRowCount(); for (int j =
		 * 0; j < ordCountTemp; j++) { sumPremTemp = sumPremTemp.add( new
		 * BigDecimal(dt.getDouble(j, 0))) .setScale(2,
		 * BigDecimal.ROUND_HALF_UP); } }
		 */

		if (StringUtil.isNotEmpty(member.getConsumeAmount())) {
			sumPremTemp = new BigDecimal(member.getConsumeAmount());
		}
		if (null != member.getBuyCount()) {
			ordCountTemp = member.getBuyCount();
		}
		if ("Y".equals(member.getVipFlag())) {
			result.put("dd1percent", "100");
			result.put("dd2percent", "100");
			result.put("dd3percent", "100");
		} else {
			if (Constant.MEMBER_GRADE_K0.equals(grade)) {
				// 定时任务没有更新 会员级别的场合
				if (ordCountTemp > 0) {
					// 100%进度终点被挤换行。
					result.put("dd1percent", "95");
				} else {
					// 进图条，0% 与K0重叠。 小于 20% 取20%位置
					result.put("dd1percent", "20");
				}
				result.put("dd2percent", "0");
				result.put("dd3percent", "0");

			} else if (Constant.MEMBER_GRADE_K1.equals(grade)) {

				result.put("dd1percent", "100");

				QueryBuilder qbK1 = new QueryBuilder(
						"SELECT orderCount, sumPrem FROM MemberGrade WHERE gradeCode = '"
								+ Constant.MEMBER_GRADE_K2 + "'");
				DataTable dtK1 = qbK1.executeDataTable();
				if (dtK1.getRowCount() > 0) {
					DataRow row = dtK1.get(0);
					orderCount = row.getString(0);
					sumPrem = row.getString(1);
					if (StringUtil.isEmpty(orderCount)) {
						orderCount = "5";
					}
					if (ordCountTemp > Integer.valueOf(row.getString(0))) {
						ordCountTemp = Integer.valueOf(row.getString(0));
					}
					// 计算方式 5 + 3000/500 如果 消费大于3000按照3000计算
					BigDecimal simulationCount = sumPremTemp;
					if (simulationCount.compareTo(new BigDecimal(sumPrem)) > 0) {
						simulationCount = new BigDecimal(sumPrem);
					}
					BigDecimal decimalup = new BigDecimal(ordCountTemp)
							.add(simulationCount.divide(new BigDecimal("500")));

					BigDecimal decimaldown = new BigDecimal(orderCount)
							.add(new BigDecimal(sumPrem).divide(new BigDecimal(
									"500")));

					BigDecimal dd2percentBD = (decimalup.divide(decimaldown, 2,
							BigDecimal.ROUND_HALF_EVEN))
							.multiply(new BigDecimal(100));

					String dd2percent = "0";
					if (dd2percentBD.compareTo(new BigDecimal(95)) > 0) {
						dd2percent = "95";
					} else {
						dd2percentBD = dd2percentBD.setScale(0,
								BigDecimal.ROUND_HALF_UP);
						if (dd2percentBD.compareTo(new BigDecimal(15)) < 0) {
							dd2percent = "20";
						} else {
							dd2percent = dd2percentBD.toString();
						}
					}
					result.put("dd2percent", dd2percent);
				} else {
					result.put("dd2percent", "20");
				}

				// 距离K2次数
				int times = (Integer.valueOf(orderCount) - ordCountTemp);
				String dd2Times = String.valueOf(times > 0 ? times : 0);
				result.put("dd2Times", dd2Times);

				// 距离K2金额
				BigDecimal prem = new BigDecimal(sumPrem).subtract(sumPremTemp);
				String dd2Prem = String.valueOf(prem
						.compareTo(new BigDecimal(0)) > 0 ? prem : 0);
				result.put("dd2Prem", dd2Prem);

				result.put("dd3percent", "0");
			} else if (Constant.MEMBER_GRADE_K2.equals(grade)) {
				result.put("dd1percent", "100");
				result.put("dd2percent", "100");

				QueryBuilder qbK2 = new QueryBuilder(
						"SELECT orderCount, sumPrem FROM MemberGrade WHERE gradeCode = '"
								+ Constant.MEMBER_GRADE_VIP + "'");
				DataTable dtK2 = qbK2.executeDataTable();
				if (dtK2.getRowCount() > 0) {
					DataRow row = dtK2.get(0);

					orderCount = row.getString(0);
					sumPrem = row.getString(1);
					if (StringUtil.isEmpty(sumPrem)) {
						sumPrem = "10000";
					}

					BigDecimal decimalup = sumPremTemp.subtract(new BigDecimal(
							3000));

					BigDecimal decimaldown = new BigDecimal(sumPrem)
							.subtract(new BigDecimal(3000));

					BigDecimal dd3percentBD = (decimalup.divide(decimaldown, 2,
							BigDecimal.ROUND_HALF_EVEN))
							.multiply(new BigDecimal(100));

					String dd3percent = "0";
					if (dd3percentBD.compareTo(new BigDecimal(100)) > 0) {
						dd3percent = "100";
					} else {
						dd3percentBD = dd3percentBD.setScale(0,
								BigDecimal.ROUND_HALF_UP);
						if (dd3percentBD.compareTo(new BigDecimal(15)) < 0) {
							dd3percent = "20";
						} else {
							dd3percent = dd3percentBD.toString();
						}
					}
					result.put("dd3percent", dd3percent);

				} else {
					result.put("dd3percent", "20");
				}
				// 距离VIP金额
				BigDecimal prem = new BigDecimal(sumPrem).subtract(sumPremTemp);
				String dd3Prem = String.valueOf(prem
						.compareTo(new BigDecimal(0)) > 0 ? prem : 0);
				result.put("dd3Prem", dd3Prem);
			} else {
				result.put("dd1percent", "0");
				result.put("dd2percent", "0");
				result.put("dd3percent", "0");
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.service.MemberChannelService#privileges(cn.com.sinosoft
	 * .entity.Member)
	 */
	@Override
	public Map<String, String> privileges(Member member) {

		Map<String, String> result = new HashMap<String, String>();

		MemberPrivilegesSchema schema = new MemberPrivilegesSchema();
		MemberPrivilegesSet set = schema.query(new QueryBuilder(
				"where 1=1 order by orderflag "));
		if (set.size() > 0) {
			for (int i = 0; i < set.size(); i++) {
				schema = set.get(i);
				// 会员特权显示内容
				String showDesc = schema.getcontent();

				String MemberLevel = schema.getMemberLevel();
				if (null == member) {
					result.put("tribe" + i + "OK", "tribeOK");
				} else {
					String grade = member.getGrade();

					if ("Y".equals(member.getVipFlag())) {
						grade = "VIP";
					}
					// 会员等级匹配 特权的会员等级 特权会员等级用,好分开。 即：K0,VIP
					if (MemberLevel.indexOf(grade) < 0) {
						showDesc = showDesc + "<br />暂不享受";
					} else {
						result.put("tribe" + i + "OK", "tribeOK");
					}
				}

				result.put("tribeicon" + i + "desc", showDesc);
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.sinosoft.service.MemberChannelService#campaign()
	 */
	@Override
	public Map<String, String> campaign() {

		Map<String, String> result = new HashMap<String, String>();

		DataTable positionDT = new QueryBuilder(
				"select * from zcadposition where PositionName in ('会员部落广告1', '会员部落广告2') and siteid=?",
				"221").executeDataTable();
		if (positionDT.getRowCount() > 0) {
			for (int i = 0; i < positionDT.getRowCount(); i++) {
				String PositionName = positionDT.getString(i, "PositionName");
				if ("会员部落广告1".equals(PositionName)) {
					result.put(
							"campaign1",
							"<script language='javascript' src='"
									+ Config.getValue("StaticResourcePath")
									+ "/" + positionDT.getString(i, "jsname")
									+ "'></script>");
				} else if ("会员部落广告2".equals(PositionName)) {
					result.put(
							"campaign2",
							"<script language='javascript' src='"
									+ Config.getValue("StaticResourcePath")
									+ "/" + positionDT.getString(i, "jsname")
									+ "'></script>");
				} else {
					logger.info("位置广告！");
				}
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.sinosoft.service.MemberChannelService#pointsProduct(int)
	 */
	@Override
	public Map<String, String> pointsProduct(int pageIndex) {
		String configFilePath = ServletActionContext.getRequest().getRealPath(
				"/WEB-INF/template/");
		Map<String, String> result = new HashMap<String, String>();

		Map<String, String> map_param = new HashMap<String, String>();
		map_param.put("modelType", "4");
		//map_param.put("startindex", String.valueOf((pageIndex - 1) * 4));
		map_param.put("startindex", String.valueOf(pageIndex - 1));
		map_param.put("size", "4");
		DataTable gifgListDt = mGiftClassifyService
				.getGiftClassifyList_ibatis(map_param);
		
		List<GiftClassify> gifgList = getGiftClassifyList(gifgListDt);

		// 获得积分产品信息
		Map<String, Object> paramProduct = new HashMap<String, Object>();
		gifgList = dealGiftClassify(gifgList);
		paramProduct.put("productList", gifgList);
		result.put("products", FreemarkerUtil.templateManage(configFilePath
				+ "/admin", "pointsProductInfo", paramProduct));
		// 获得分页信息
		Map<String, Object> paramPagebar = new HashMap<String, Object>();
		List<Map<String, String>> pageList = getPageBar(pageIndex);
		paramPagebar.put("pageList", pageList);
		result.put("productsPageBar", FreemarkerUtil.templateManage(
				configFilePath + "/admin", "pagebar", paramPagebar));

		return result;
	}

	private List<GiftClassify> getGiftClassifyList(DataTable gifgListDt){
		
		List<GiftClassify> gifgList = new ArrayList<GiftClassify>();
		
		for (int i = 0; i < gifgListDt.getRowCount(); i++) {
			GiftClassify g = new GiftClassify();
			g.setId(gifgListDt.getString(i, "id"));
			g.setGiftTitle(gifgListDt.getString(i, "giftTitle"));
			g.setGiftName(gifgListDt.getString(i, "giftName"));
			g.setLastNum(gifgListDt.getString(i, "lastNum"));
			g.setType(gifgListDt.getString(i, "type"));
			g.setLogoUrl(gifgListDt.getString(i, "logoUrl"));
			g.setModelType(gifgListDt.getString(i, "modelType"));
			g.setProductID(gifgListDt.getString(i, "productID"));
			g.setPoints(gifgListDt.getString(i, "points"));
			g.setGiftPrice(gifgListDt.getString(i, "giftPrice"));
			g.setPopularity(gifgListDt.getString(i, "popularity"));
			g.setRecommend(gifgListDt.getString(i, "recommend"));
			g.setStartDate(DateUtil.parse(gifgListDt.getString(i, "startDate")));
			g.setEndDate(DateUtil.parse(gifgListDt.getString(i, "endDate")));
			g.setMetaDescription(gifgListDt.getString(i, "metaDescription"));
			g.setStatus(gifgListDt.getString(i, "status"));
			g.setLinkUrl(gifgListDt.getString(i, "linkUrl"));
			String orderFlag = gifgListDt.getString(i, "orderFlag");
			if (StringUtil.isNotEmpty(orderFlag)){
				g.setOrderFlag(Long.valueOf(orderFlag));
			}
			g.setProp1(gifgListDt.getString(i, "prop1"));
			g.setProp2(gifgListDt.getString(i, "prop2"));
			g.setProp3(gifgListDt.getString(i, "prop3"));
			g.setProp4(gifgListDt.getString(i, "prop4"));
			g.setProp5(gifgListDt.getString(i, "prop5"));
			g.setProp6(gifgListDt.getString(i, "prop6"));
			g.setProp7(gifgListDt.getString(i, "ProductName"));
			g.setCreateUser(gifgListDt.getString(i, "createUser"));
			g.setModifyUser(gifgListDt.getString(i, "modifyUser"));
			
			gifgList.add(g);
		}
		
		return gifgList;
	}
	/**
	 * 图片 全路径 处理
	 * 
	 * @param gifgList
	 * @return
	 */
	private List<GiftClassify> dealGiftClassify(List<GiftClassify> gifgList) {

		for (GiftClassify giftClassify : gifgList) {
			if (StringUtil.isEmpty(giftClassify.getLinkUrl())) {
				giftClassify.setLinkUrl("");
			}
			if (!"1".equals(giftClassify.getType())) {
				giftClassify
						.setLinkUrl(Config.getValue("ServerContext")
								+ "/shop/points!integralMallInformation.action?presentID="
								+ giftClassify.getId());
			}
			giftClassify.setLogoUrl(Config.getValue("StaticResourcePath")
					+ File.separator + giftClassify.getLogoUrl());
			if (StringUtil.isEmpty(giftClassify.getProp7())) {
				giftClassify.setProp7(giftClassify.getGiftTitle());
			}
		}

		return gifgList;
	}

	private List<Map<String, String>> getPageBar(int pageIndex) {

		Map<String, String> map_param = new HashMap<String, String>();
		map_param.put("modelType", "4");
		int count = mGiftClassifyService.getGiftClassifyListNum(map_param);
		List<Map<String, String>> pageList = new ArrayList<Map<String, String>>();
		// 分页
		Map<String, String> map;
		int pageCount = new Double(Math.ceil(count * 1.0 / 4)).intValue();
		if (pageCount == 0) {
			return pageList;
		}
		if (pageCount == 1) {
			map = new HashMap<String, String>();
			map.put("class", "now");
			map.put("num", "1");
			pageList.add(map);
			return pageList;
		}

		map = new HashMap<String, String>();
		if (pageIndex == 1) {
			map.put("class", "now");
			map.put("num", "1");
		} else {
			map.put("class", "");
			map.put("num", "1");
		}
		pageList.add(map);

		for (int i = 2; i < pageCount; i++) {
			if (pageCount > 6) {
				if (pageIndex >= pageCount - 4) {
					if (i >= pageCount - 3) {
						if (i == pageCount - 3) {
							map = new HashMap<String, String>();
							map.put("class", "omit");
							map.put("num", "...");
							pageList.add(map);
						}
						map = new HashMap<String, String>();
						if (i == pageIndex) {
							map.put("class", "now");
						} else {
							map.put("class", "");
						}
						map.put("num", "" + i);
						pageList.add(map);

					}
				} else if (pageIndex < 3) {
					if (i < 5) {
						map = new HashMap<String, String>();
						if (i == pageIndex) {
							map.put("class", "now");
						} else {
							map.put("class", "");
						}
						map.put("num", "" + i);
						pageList.add(map);
						if (i == 4) {
							map = new HashMap<String, String>();
							map.put("class", "omit");
							map.put("num", "...");
							pageList.add(map);
						}
					}
				} else {
					if (pageIndex > 2 && pageCount > (pageIndex + 1)) {
						if (i > (pageIndex - 1) && i < (pageIndex + 3)) {
							map = new HashMap<String, String>();
							if (i == pageIndex) {
								map.put("class", "now");
							} else {
								map.put("class", "");
							}
							map.put("num", "" + i);
							pageList.add(map);
						}
						if (i == (pageIndex + 2) && i < pageCount - 2) {
							map = new HashMap<String, String>();
							map.put("class", "omit");
							map.put("num", "...");
							pageList.add(map);
						}
					}
				}
			} else {
				map = new HashMap<String, String>();
				if (i == pageIndex) {
					map.put("class", "now");
				} else {
					map.put("class", "");
				}
				map.put("num", "" + i);
				pageList.add(map);
			}
		}

		map = new HashMap<String, String>();
		if (pageIndex == pageCount) {
			if (pageCount > 1) {
				map.put("class", "now");
				map.put("num", "" + pageCount);
				pageList.add(map);
			}
		} else {
			map.put("class", "");
			map.put("num", "" + pageCount);
			pageList.add(map);
		}

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		if (pageList != null && pageList.size() > 0) {
			for (int i = pageList.size() - 1; i >= 0; i--) {
				result.add(pageList.get(i));
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.service.MemberChannelService#recommendInfo(cn.com.sinosoft
	 * .entity.Member)
	 */
	public Map<String, String> recommendInfo(Member member) {

		Map<String, String> result = new HashMap<String, String>();

		String sql = "select CodeValue,Memo from zdcode where CodeType='Member.Recommend' and ParentCode='Member.Recommend'";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				// 取得微博推荐标题
				if ("title".equals(dt.getString(i, 0))) {
					result.put("recommTitle", dt.getString(i, 1));
				}
				// 取得推荐描述
				if ("desc".equals(dt.getString(i, 0))) {
					result.put("recommDesc", dt.getString(i, 1));
				}
			}
		}
		String url = Config.getFrontServerContextPath() + "/"
				+ Config.getValue("RecommendUrl") + "/" + member.getId();
		result.put("recommUrl", url);
		return result;
	}

	// add by wangej 20160127 增加获取推荐积分、人数、奖励百分比的数据 begin
	public Map<String, String> recommendInfoAll() {
		Map<String, String> result = new HashMap<String, String>();
		Map<String, Object> map1;
		PointsCalculate PointsCalculate = new PointsCalculate();
		// 取得注册好友送积分数
		String recommRegCountPonits = "";
		try {
			map1 = PointsCalculate.pointsManage(IntegralConstant.POINT_SEARCH,
					IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER, null);
			if (map1.get(IntegralConstant.ACTION_POINTS) != null
					&& !"0".equals(map1.get(IntegralConstant.ACTION_POINTS))) {
				recommRegCountPonits = String.valueOf(map1
						.get(IntegralConstant.ACTION_POINTS));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		result.put("recommRegCountPonits", recommRegCountPonits);
		// 取得推荐好友注册上限
		String recommRegisterCounts = Config.getValue("RecommRegisterCount");
		result.put("recommRegisterCounts", recommRegisterCounts);
		// 取得注册好友购买送积分比例
		String recommBuyPointsPec = Config.getValue("RecommBuyPoints");
		if (StringUtil.isNotEmpty(recommBuyPointsPec)) {
			recommBuyPointsPec = subZeroAndDot(new BigDecimal(
					recommBuyPointsPec).multiply(new BigDecimal(100))
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			recommBuyPointsPec += "%";
		}
		result.put("recommBuyPointsPec", recommBuyPointsPec);
		return result;
	}

	private String subZeroAndDot(String s) {
		if (s.indexOf(".") > 0) {
			s = s.replaceAll("0+?$", "");// 去掉多余的0
			s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
		}
		return s;
	}

	// add by wangej 20160127 增加获取推荐积分、人数、奖励百分比的数据 end
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.sinosoft.service.MemberChannelService#givePointsInfo()
	 */
	@Override
	public Map<String, String> givePointsInfo() {

		Map<String, String> result = new HashMap<String, String>();

		// 邮箱 + 手机 + 性别 + 生日
		SDPointRuleSchema schema = new SDPointRuleSchema();
		String acts = "'" + IntegralConstant.POINT_SOURCE_EMAIL + "','"
				+ IntegralConstant.POINT_SOURCE_MOBILE + "','"
				+ IntegralConstant.POINT_SOURCE_MEM_SEX + "','"
				+ IntegralConstant.POINT_SOURCE_MEM_BIRTHDAY + "','"
						+ IntegralConstant.POINT_SOURCE_REGISTER + "'";
		SDPointRuleSet tSDPointRuleSet = schema.query(new QueryBuilder(
				" where MemberAct in (" + acts + ") and pointsgive='01' "));

		int pointCount = 0;
		for (int i = 0; i < tSDPointRuleSet.size(); i++) {
			SDPointRuleSchema schemaTemp = tSDPointRuleSet.get(i);

			String act = schemaTemp.getMemberAct();
			String num = schemaTemp.getPointsNum();
			// Email认证 送积分
			if (IntegralConstant.POINT_SOURCE_EMAIL.equals(act)) {
				result.put("pointEmail", num);
				pointCount = pointCount
						+ Integer.valueOf(StringUtil.isNotEmpty(num) ? num
								: "0");
			}
			// 手机认证 送积分
			if (IntegralConstant.POINT_SOURCE_MOBILE.equals(act)) {
				result.put("pointMobile", num);
				pointCount = pointCount
						+ Integer.valueOf(StringUtil.isNotEmpty(num) ? num
								: "0");
			}
			// 性别设定送积分
			if (IntegralConstant.POINT_SOURCE_MEM_SEX.equals(act)) {
				result.put("pointSex", num);
				pointCount = pointCount
						+ Integer.valueOf(StringUtil.isNotEmpty(num) ? num
								: "0");
			}
			// 生日设定送积分
			if (IntegralConstant.POINT_SOURCE_MEM_BIRTHDAY.equals(act)) {
				result.put("pointBirthday", num);
				pointCount = pointCount
						+ Integer.valueOf(StringUtil.isNotEmpty(num) ? num
								: "0");
			}
			// 注册送积分
			if (IntegralConstant.POINT_SOURCE_REGISTER.equals(act)) {
				result.put("pointRegister", num);
			}
		}

		result.put("pointCount", String.valueOf(pointCount));

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.com.sinosoft.service.MemberChannelService#memberAndzccomment()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> memberAndzccomment() {

		Map<String, Object> result = new HashMap<String, Object>();

		List memberInfoPages = new ArrayList();
		List zccommentInfoPages = new ArrayList();
		memberSchema tmemberSchema = new memberSchema();
		memberSet tmemberSet = tmemberSchema.query(new QueryBuilder(
				" where 1=1 order by createDate desc limit 0, 45 "));

		DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm");

		// 每一页的数据
		List<Map<String, String>> memberInfo = new ArrayList<Map<String, String>>();
		for (int i = 0; i < tmemberSet.size(); i++) {

			Map<String, String> map = new HashMap<String, String>();
			memberSchema schemaTemp = tmemberSet.get(i);
			String createDate = df.format(schemaTemp.getcreateDate());
			map.put("createDate", createDate);

			// 用户名
			if (StringUtil.isNotEmpty(schemaTemp.getmobileNO()) && schemaTemp.getmobileNO().length() > 10) {
				String mobileNO = schemaTemp.getmobileNO();
				String tempMobileNO = mobileNO.substring(0, 3) + "*****"
						+ mobileNO.substring(8, mobileNO.length());
				map.put("registerNo", tempMobileNO);
			} else if (StringUtil.isNotEmpty(schemaTemp.getemail())) {
				String email = schemaTemp.getemail();
				String tempEmail = email.substring(0, 3) + "*****"
						+ email.substring(email.length() - 3, email.length());
				map.put("registerNo", tempEmail);
			} else {
				map.put("registerNo", "139*****086");
			}

			memberInfo.add(map);

		}

		String StaticResourcePath = Config.getValue("StaticResourcePath");
		String FrontServerContextPath = Config
				.getValue("FrontServerContextPath");

		// 评论数据
		String sql = " SELECT z.AddUser, z.Prop2, m.headPicPath, p.ProductName, zc.URL, z.AddTime FROM "
				+ " zccomment z, member m, zcarticle zc, sdproduct p "
				+ " WHERE z.Prop1 = m.id AND z.RelaID = zc.ID AND zc.Prop4 = p.ProductID AND zc.TYPE = '1' AND zc.STATUS = '30' "
				+ " AND z.verifyFlag = 'Y' "
				+ " AND zc.CatalogInnerCode LIKE '002313%' ORDER BY z.AddTime DESC LIMIT 0, 15 ";

		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();

		// 每一页的数据
		List<Map<String, String>> zccommentInfo = new ArrayList<Map<String, String>>();
		for (int i = 0; i < dt.getRowCount(); i++) {

			Map<String, String> map = new HashMap<String, String>();
			String username = dt.getString(i, "AddUser");

			// 评论昵称
			if (StringUtil.isMobileNO(username)) {
				String tempMobileNO = username.substring(0, 3) + "*****"
						+ username.substring(8, username.length());
				map.put("username", tempMobileNO);
			} else if (StringUtil.isMail(username)) {
				String tempEmail = username.substring(0, 3)
						+ "*****"
						+ username.substring(username.length() - 3,
								username.length());
				map.put("username", tempEmail);
			} else {
				map.put("username", username);
			}
			// 评论
			String comment = dt.getString(i, "Prop2");
			if (StringUtil.isNotEmpty(comment)) {
				if (comment.length() > 54) {
					comment = comment.substring(0, 52);
					comment = comment + "...";
				}
			}
			map.put("comment", comment);
			// 头像
			String headPicPath = dt.getString(i, "headPicPath");
			if (StringUtil.isNotEmpty(headPicPath)) {
				map.put("headPicPath",
						FrontServerContextPath + "/wj/"
								+ dt.getString(i, "headPicPath"));
			} else {
				map.put("headPicPath", StaticResourcePath
						+ "/images/redesign/photo_06.gif");
			}

			// 产品名称
			map.put("ProductName", dt.getString(i, "ProductName"));
			// 产品URL
			map.put("URL",
					FrontServerContextPath + "/" + dt.getString(i, "URL"));

			zccommentInfo.add(map);

			if (i != 0 && (i + 1) % 3 == 0) {
				zccommentInfoPages.add(zccommentInfo);
				zccommentInfo = new ArrayList<Map<String, String>>();
			}
		}


		// 整理 按照每页 设定map
		List<Map<String, Object>> pages = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < 5; i++) {
			Map<String, Object> page = new HashMap<String, Object>();
			page.put("member", memberInfo);
			if (i < zccommentInfoPages.size()) {
				page.put("comment", zccommentInfoPages.get(i));
			}
			pages.add(page);
		}

		result.put("memberInfo", memberInfo);
		result.put("pages", pages);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.com.sinosoft.service.MemberChannelService#activityProduct(cn.com.sinosoft
	 * .entity.Member)
	 */
	@Override
	public List<Map<String, String>> activityProduct(Member member) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		// 查询特价活动
		String sql = "SELECT a.ActivitySn,p.ProductID, p.ProductName, p.Remark4, s.URL, s.LogoLink, s.AdaptPeopleInfo, s.DutyHTML2, a.accumulation ,a.type "
				+ " FROM sdcouponactivityinfo a, SdProductActivityLink l, sdproduct p, zcarticle zc, sdsearchrelaproduct s "
				+ " WHERE a.ActivitySn = l.activitySn AND a.status = '3' "
				+ " AND l.productid = p.productid AND zc.Prop4 = p.productid AND p.productid = s.ProductID "
				+ " AND FIND_IN_SET('wj', l.ActivityChannel) AND (zc.logo IS NOT NULL AND zc.logo <> '') "
				+ " AND a.memberChannel = 'Y' and zc.cataloginnercode like '002313%' "
				+ " AND zc.TYPE = '1' AND zc.STATUS = '30' AND a.type in('3','6','7','8') AND a.starttime <= NOW() AND a.endtime >= NOW()  "//AND a.type in('3','6','7','8')（满减,折扣，高倍积分，自定义）
				+ " ORDER BY a.createtime DESC LIMIT 0, 3 ";

		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		String ProductID = "";
		String atype = "100";
		int rowcount =dt.getRowCount();
		if (dt.getRowCount() > 0) {
			for (int i = 0; i < rowcount; i++) {
				
				if(i==1){
					if(StringUtil.isNotEmpty(ProductID) && ProductID.equals(dt.getString(i, "ProductID"))){
						if(Integer.parseInt(atype)<Integer.parseInt(dt.getString(i, "ProductID"))){
							dt.deleteRow(i);
						}else{
							dt.deleteRow(i-1);
						}
						rowcount--;
						i--;
						ProductID="";
						atype = "";
						continue;
					}
				}else if(i>=2){
					ProductID="";
					atype = "100";
					break;
				}
				
				
				ProductID = dt.getString(i, "ProductID");
				atype = dt.getString(i, "type");
				Map<String, String> map = new HashMap<String, String>();
				map.put("ProductID", dt.getString(i, "ProductID"));
				map.put("ProductName", dt.getString(i, "ProductName"));
				map.put("Remark4", dt.getString(i, "Remark4"));
				map.put("URL", dt.getString(i, "URL"));
				map.put("LogoLink", dt.getString(i, "LogoLink"));
				map.put("AdaptPeopleInfo", dt.getString(i, "AdaptPeopleInfo"));
				map.put("DutyHTML2", dt.getString(i, "DutyHTML2"));
				//以下为原逻辑：会员部落会员特价满减显示价格
				/*
				String ActivitySn = dt.getString(i, "ActivitySn");
				String price = dt.getString(i, "Remark4");
				String sql2 = "";

				String accumulation = dt.getString(i, "accumulation");

				
				 // 判断是否登录，显示的价格不同，未登录，显示减掉最高价格
				if (null != member && StringUtil.isNotEmpty(member.getGrade())) {
					String grade = member.getGrade();
					if ("Y".equals(member.getVipFlag())) {
						grade = "VIP";
					}
					sql2 = "SELECT ActivityData,StartAmount FROM SdActivityRule WHERE ActivitySn = '"
							+ ActivitySn
							+ "'"
							+ " AND StartAmount <= '"
							+ price + "'";

					// accumulation 1为不可累计，0为可累计
					if (StringUtil.isNotEmpty(accumulation)
							&& accumulation.equals("1")) {
						sql2 = sql2 + " AND EndAmount > '" + price + "'";
					}
					sql2 = sql2 + " AND FIND_IN_SET('" + grade
							+ "', MemberRule) "
							+ " ORDER BY ActivityData DESC LIMIT 1 ";
				} else {
					sql2 = "SELECT ActivityData,StartAmount FROM SdActivityRule WHERE ActivitySn = '"
							+ ActivitySn
							+ "'"
							+ " AND StartAmount <= '"
							+ price + "'";

					// accumulation 1为不可累计，0为可累计
					if (StringUtil.isNotEmpty(accumulation)
							&& accumulation.equals("1")) {
						sql2 = sql2 + " AND EndAmount > '" + price + "'";
					}
					sql2 = sql2 + " ORDER BY ActivityData DESC LIMIT 1 ";
				}

				QueryBuilder qb2 = new QueryBuilder(sql2);
				DataTable dt2 = qb2.executeDataTable();

				String ActivityData = "";
				String StartAmount = "";
				if (dt2.getRowCount() > 0) {
					ActivityData = dt2.getString(0, "ActivityData");
					StartAmount = dt2.getString(0, "StartAmount");
				}

				if (StringUtil.isNotEmpty(ActivityData)) {
					BigDecimal originalCost = new BigDecimal(price);
					// accumulation 1为不可累计，0为可累计
					if (StringUtil.isNotEmpty(accumulation)
							&& accumulation.equals("0")) {
						// 减去倍数
						Double times = 0d;
						BigDecimal bd = originalCost.divide(new BigDecimal(
								StartAmount), 2);
						Double d = Double.valueOf(bd.toString());
						// 取整数
						d = Math.floor(d);
						if (d == 0d) {
							times = 1d;
						} else {
							times = d;
						}

						map.put("Prem",
								originalCost
										.subtract(
												new BigDecimal(ActivityData)
														.multiply(new BigDecimal(
																times)))
										.toString());
					} else {
						map.put("Prem",
								originalCost.subtract(
										new BigDecimal(ActivityData))
										.toString());
					}
				} else {
					map.put("Prem", price);
				}// 会员价逻辑 */
				
				Map<String, String> results = ProductActivityInfo(dt.getString(i, "ProductID"));
				String ActivityLabel =GetActivityLabel(results);
				map.put("ActivityLabel", ActivityLabel);
				result.add(map);
			}
		}

		return result;
	}
	
	
	public String GetActivityLabel (Map<String, String> activitMap ){
		StringBuffer activityinfo_sub = new StringBuffer();
		String type = activitMap.get("type");
		String typeName = activitMap.get("typeName");
		String title = activitMap.get("title");
		String description = activitMap.get("description");
		if ("3".equals(type)) {
			activityinfo_sub.append("<dd>");
			activityinfo_sub.append("<span class=\" vip_tag active_04\">" + typeName + "</span>");
			activityinfo_sub.append("<span class=\"vip_tag_des\">" + description + "</span>");
			activityinfo_sub.append("</dd>");
		} else if ("6".equals(type)) {
			activityinfo_sub.append("<dd>");
			activityinfo_sub.append("<span class=\" vip_tag active_02\">" + typeName + "</span>");
			activityinfo_sub.append("<span class=\"vip_tag_des\">" + description + "</span>");
			activityinfo_sub.append("</dd>");
		}  else if ("7".equals(type)) {
			activityinfo_sub.append("<dd>");
			activityinfo_sub.append("<span class=\" vip_tag active_08\">" + "多返" + "</span>");
			activityinfo_sub.append("<span class=\"vip_tag_des\">" + description + "</span>");
			activityinfo_sub.append("</dd>");

		} else if ("8".equals(type)) {
			if (title.length() > 2) {
				activityinfo_sub.append("<dd>");
				activityinfo_sub.append("<span class=\" vip_tag active_03\">" + title.substring(0, 2) + "</span>");
				activityinfo_sub.append("<span class=\"vip_tag_des\">" + description + "</span>");
				activityinfo_sub.append("</dd>");
			} else {
				activityinfo_sub.append("<dd>");
				activityinfo_sub.append("<span class=\" vip_tag active_03\">" + title + "</span>");
				activityinfo_sub.append("<span class=\"vip_tag_des\">" + description + "</span>");
				activityinfo_sub.append("</dd>");
			}
		}
	
		return activityinfo_sub.toString();
	}
	
	
	
	/**
	 * 获取产品活动信息
	 * 
	 * @param ProductID
	 * @param Channel
	 * @return
	 */
	public Map<String, String> ProductActivityInfo(String Product) {
		StringBuffer ProductActivityInfo = new StringBuffer();
		ProductActivityInfo.append(" select s1.ProductID,s2.title,s2.description, s2.type ,z1.CodeName typeName ,s2.GroupbuyWhether , s2.activitySn ");
		ProductActivityInfo.append(" from SdProductActivityLink s1,sdcouponactivityinfo s2 ,zdcode z1 ");
		ProductActivityInfo.append(" where  z1.parentcode='Activity.type' and (  s1.ProductId = '").append(Product).append("' )");
		ProductActivityInfo.append(" and  status='3' and  s1.ActivitySn=s2.activitySn and s2.type=z1.codevalue  AND s2.type IN ('3','6','7','8') ");
		ProductActivityInfo.append(" and  s2.starttime <='").append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s2.endtime >='").append(PubFun.getCurrent()).append("'");
		ProductActivityInfo.append(" and  s1.ActivityChannel = 'wj' ");
		ProductActivityInfo.append(" order by  find_in_set(s2.type,'6,3,7,8') LIMIT 0, 1 ");

		QueryBuilder qb = new QueryBuilder(ProductActivityInfo.toString());
		DataTable dt = qb.executeDataTable();

/*		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (int i = 0; i < dt.getRowCount(); i++) {
			result.add(dt.get(i).toMapx());
		}*/
		return dt.get(0).toMapx();
	}

}
