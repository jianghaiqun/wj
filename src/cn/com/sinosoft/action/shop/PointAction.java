package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Point;
import cn.com.sinosoft.service.MemberService;
import cn.com.sinosoft.util.JdbcTemplateData;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.security.EncryptUtil;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.points.IntegralConstant;
import com.sinosoft.points.PointsCalculate;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wugq
 * 
 */
@ParentPackage("member")
public class PointAction extends BaseShopAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8798153317777122166L;
	private String memberID;
	private int page = 1;
	public static final int pagesize = 10;
	private int count;
	private int lastpage;
	private String startDate;
	private String endDate;
	private String source;
	private Point point;
	private List<Point> listq = new ArrayList<Point>();
	/**
	 * ArrayList&lt;HashMap&lt;String, Object&gt;&gt; listJFDH 积分兑换记录
	 */
	private ArrayList<HashMap<String, Object>> listJFDH;
	private int usedPoint;
	private int currentValidatePoint;// 可用积分
	private int frozenPoint;// 冻结积分
	private String password;
	private String outInt;

	@Resource
	private MemberService memberService;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOutInt() {
		return outInt;
	}

	public void setOutInt(String outInt) {
		this.outInt = outInt;
	}

	public List<Point> getListq() {
		return listq;
	}

	public void setListq(List<Point> listq) {
		this.listq = listq;
	}

	public int getUsedPoint() {
		return usedPoint;
	}

	public void setUsedPoint(int usedPoint) {
		this.usedPoint = usedPoint;
	}

	public int getCurrentValidatePoint() {
		return currentValidatePoint;
	}

	public void setCurrentValidatePoint(int currentValidatePoint) {
		this.currentValidatePoint = currentValidatePoint;
	}

	public int getFrozenPoint() {
		return frozenPoint;
	}

	public void setFrozenPoint(int frozenPoint) {
		this.frozenPoint = frozenPoint;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLastpage() {
		return lastpage;
	}

	public void setLastpage(int lastpage) {
		this.lastpage = lastpage;
	}

	public static int getPagesize() {
		return pagesize;
	}

	public Member getLoginMember() {
		String id = (String) getSession(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		Member loginMember = memberService.load(id);
		return loginMember;
	}

	/**
	 * 
	 * @return
	 */
	public String newList() {

		HttpServletRequest request = ServletActionContext.getRequest();
		memberID = getLoginMember().getId();
		
		String queryIntegral = "select currentValidatePoint,point,realName,'' sumPoint," + "headPicPath,aboutToExpireDate,aboutToExpirePoints,birthday,birthYear,member.grade,vipFlag,a.gradeName from member left join MemberGrade a on a.gradeCode = member.grade where member.id = '" + memberID + "'";
		// 积分换算一元单位
		String pointUnit = Config.getConfigValue("PointScalerUnit");
		if (StringUtil.isEmpty(pointUnit)) {
			pointUnit = "100";
		}
		// 有效期
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
		GetDBdata dBdata = new GetDBdata();
		try {
			List<HashMap<String, String>> integralList = dBdata.query(queryIntegral);
			if (integralList != null && integralList.size() > 0) {
				integralList.get(0).put("pointPeriod", pointPeriod);
				integralList.get(0).put("pointUnit", pointUnit);
				String currentValidatePoint = integralList.get(0).get("currentValidatePoint");
				String aboutToExpirePoints = integralList.get(0).get("aboutToExpirePoints");
				if (StringUtil.isEmpty(aboutToExpirePoints)) {
					integralList.get(0).put("aboutToExpirePoints", "0");
				}
				String aboutToExpireDate = integralList.get(0).get("aboutToExpireDate");
				if (StringUtil.isNotEmpty(aboutToExpireDate)) {
					integralList.get(0).put("aboutToExpireDate", aboutToExpireDate.substring(0,4)+"年"+aboutToExpireDate.substring(5,7)+"月"+aboutToExpireDate.substring(8,10)+"日");
				}
				int sumPoint = 0;
				if (StringUtil.isEmpty(currentValidatePoint)) {
					currentValidatePoint = "0";
					integralList.get(0).put("currentValidatePoint", "0");
				} else {
					sumPoint += Integer.valueOf(currentValidatePoint);
				}
				
				String point = integralList.get(0).get("point");
				if (StringUtil.isNotEmpty(point)) {
					sumPoint += Integer.valueOf(point);
				} else {
					integralList.get(0).put("point", "0");
				}
				integralList.get(0).put("sumPoint", String.valueOf(sumPoint));
				String PointMoney = ((new BigDecimal(currentValidatePoint)).divide((new BigDecimal(pointUnit)), 1, BigDecimal.ROUND_HALF_UP)).toString();
				integralList.get(0).put("PointMoney", PointMoney);
				// 取得商品待评价个数
				String commSql = "SELECT COUNT(1) FROM sdorders a,sdinformation b, sdsearchrelaproduct c WHERE a.memberid = '" + memberID
						+ "' AND a.orderStatus IN ('7','10') and a.orderSn = b.orderSn and b.productid = c.productid AND a.commentId IS NULL";
				QueryBuilder commQb = new QueryBuilder(commSql);
				// 商品待评价个数
				int commentCount = commQb.executeInt();
				integralList.get(0).put("commentCount", commentCount + "");

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
						integralList.get(0).put("recommRegCount", String.valueOf(map1.get(IntegralConstant.ACTION_POINTS)));
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				integralList.get(0).put("commPoint", commPoint);
				integralList.get(0).put("recommRegisterCount", Config.getValue("RecommRegisterCount"));
				// 取得注册好友购买送积分比例
				String recommBuyPoints = Config.getValue("RecommBuyPoints");
				if (StringUtil.isNotEmpty(recommBuyPoints)) {
					MemberRecommendAction reco = new MemberRecommendAction();
					recommBuyPoints = reco.subZeroAndDot(new BigDecimal(recommBuyPoints).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					recommBuyPoints += "%";
				}
				integralList.get(0).put("recommBuyPoints", recommBuyPoints);
				
				// 计算评价所有待评价的商品可获得的积分
				int commentPoints = Integer.valueOf(commentCount) * Integer.valueOf(commPoint);
				integralList.get(0).put("commentPoints", commentPoints + "");
				
				MemberCenterAction memCenAction = new MemberCenterAction();
				Map<String, String> mapTemp = memCenAction.getGradeIcon(
						integralList.get(0).get("vipFlag"), integralList.get(0)
								.get("grade"),
						integralList.get(0).get("gradeName"),
						integralList.get(0).get("birthday"), integralList
								.get(0).get("birthYear"));
				integralList.get(0).put("gradeClass",
						mapTemp.get("gradeClass"));
				integralList.get(0).put("upgradeInfo",
						mapTemp.get("gradeInfo"));
			}
			request.setAttribute("integralList", integralList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		page_Index = "1";
		obtain();
		exchangeOrder();
		return "newlist";
	}

	public String scan() {
		memberID = getLoginMember().getId();
		String sql = "select a.createdate, a.createtime, a.source, a.integral, sk.name, gt.cardNo, gt.password, date_format(gt.expireDate,'%Y-%m-%d') as expireDate  "
				+ " from  SDIntCalendar a,zdcode b, stock sk, gift gt ";
		String sqlPart = " order by  a.createdate desc ,a.createtime desc  limit   " + (page - 1) * pagesize + "," + pagesize;
		String sqlPart2 = " where a.source = b.codevalue and b.codetype = 'source1' and manner = 1 and sk.id=gt.stock_id and gt.id=a.businessid and a.memberid=gt.memberid and a.memberId=?";// 表示支出
		if (!(startDate == null || "".equals(startDate))) {
			sqlPart2 = sqlPart2 + " and  a.createdate >= '" + startDate + "'";
		}
		if (!(endDate == null || "".equals(endDate))) {
			sqlPart2 = sqlPart2 + " and  a.createdate <= '" + startDate + "'";
		}
		if (!(source == null || "".equals(source))) {
			sqlPart2 = sqlPart2 + " and  a.source like '%" + source + "%'";
		}
		sql = sql + sqlPart2;
		String sql3 = sql;
		sql += sqlPart;
		String[] sql3temp = { memberID };
		String sql2 = "select usedpoint,currentvalidatepoint,point from member where id = ?";
		String[] sql2temp = { memberID };
		JdbcTemplateData jtd = new JdbcTemplateData();
		try {
			listJFDH = (ArrayList<HashMap<String, Object>>) jtd.obtainData(sql, sql2temp);
			/* zhangjinquan 11180 对积分兑换的里面记录中的密码进行解码 */
			if ((null != listJFDH) && (listJFDH.size() > 0)) {
				String key = "password";
				for (int i = 0; i < listJFDH.size(); i++) {
					HashMap<String, Object> map = listJFDH.get(i);
					String password = (String) map.get(key);
					if (StringUtil.isNotEmpty(password)) {
						map.put(key, EncryptUtil.decrypt3DES(password, EncryptUtil.DEFAULT_KEY));
					}
				}
			}
			List<Map> list3 = jtd.obtainData(sql3, sql3temp);
			List<Map> list2 = jtd.obtainData(sql2, sql2temp);
			count = list3.size();
			this.lastpage = (count + Point.LIST_COUNT - 1) / (Point.LIST_COUNT);
			Iterator<Map> it2 = list2.iterator();
			while (it2.hasNext()) {
				Map map = it2.next();
				if (map.get("usedPoint") != null && !"".equals(map.get("usedPoint"))) {
					usedPoint = (Integer) map.get("usedPoint");
				} else {
					usedPoint = 0;
				}
				if (map.get("currentValidatePoint") != null && !"".equals(map.get("currentValidatePoint"))) {
					currentValidatePoint = (Integer) map.get("currentValidatePoint");
				} else {
					currentValidatePoint = 0;
				}
				if (map.get("point") != null && !"".equals(map.get("point"))) {
					frozenPoint = (Integer) map.get("point");
				} else {
					frozenPoint = 0;
				}
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
			ActionUtil.dealAction("wj00023", map, getRequest());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			return "input";
		}
	}

	public String obtain() {
		if (StringUtil.isEmpty(page_Index)) {
			page = 1;
		} else {
			page = Integer.valueOf(page_Index);
		}
		memberID = getLoginMember().getId();
		String sql = " select   sdIC.createdate CreateDate, sdIC.createtime CreateTime, sdIC.Description Description, sdIC.integral Integral ,sdIC.Source as Source ,sdIC.Manner as Manner, sdIC.Businessid as Businessid ";
		sql += " from  SDIntCalendar sdIC ";
		sql += " where  sdic.status ='0' and (manner ='0' or manner ='1') and sdIC.integral > 0 and sdIC.memberId=? and sdIC.source != '25' ";
		sql += " order by  sdIC.createdate desc ,sdIC.createtime desc  limit " + (page - 1) * pagesize + "," + pagesize;

		String sql_count = "select count(1) pointcount ";
		sql_count += "  from  SDIntCalendar sdIC  ";
		sql_count += " where  sdic.status ='0' and (manner ='0' or manner ='1') and sdIC.integral > 0 and sdIC.memberId=? and sdIC.source != '25' ";

		JdbcTemplateData jtd = new JdbcTemplateData();
		String[] sqltemp = { memberID };

		try {
			List<Map> list = jtd.obtainData(sql, sqltemp);
			List<Map> list3 = jtd.obtainData(sql_count, sqltemp);
			if (list3 != null && list3.size() != 0) {
				count = Integer.parseInt(list3.get(0).get("pointcount") + "");

			} else {
				count = 0;

			}
			
			this.lastpage = (count + Point.LIST_COUNT - 1) / (Point.LIST_COUNT);
			Iterator<Map> it = list.iterator();
			while (it.hasNext()) {
				Map map = it.next();
				Point point = new Point();
				double Int = Double.parseDouble(map.get("Integral") + "");

				// Source 0:购买产品 1： 评价产品 2:积分抵值 3:兑换商品
				if (Int > 0) {
					point.setCreateDate((String) map.get("CreateDate"));
					point.setCreateTime(((String) map.get("CreateTime")).substring(0, 5));
					// manner 0:收入 1： 支出
					point.setManner(String.valueOf(map.get("Manner")));
					if ("0".equals(String.valueOf(map.get("Manner")))) {
						point.setPoint("+" + (String) map.get("Integral"));

					} else {
						point.setPoint("-" + (String) map.get("Integral"));

					}

					// if ("0".equals(String.valueOf(map.get("Source")))) {
					// point.setSource(String.valueOf(map.get("CodeName")) +
					// "+订单号：" + String.valueOf(map.get("Businessid")));
					//
					// } else if ("2".equals(String.valueOf(map.get("Source"))))
					// {
					// QueryBuilder qb = new
					// QueryBuilder("select ordersn from sdorders where paysn=? limit 0,1");
					// qb.add(String.valueOf(map.get("Businessid")));
					// DataTable dt = qb.executeDataTable();
					// // 积分换算一元单位
					// String PointScalerUnit = "200";
					// // 积分换算规则修改时间点
					// Date time =
					// DateUtil.parseDateTime(Config.getConfigValue("PointScalerTime"));
					// // 积分创建时间
					// Date createDate =
					// DateUtil.parseDateTime(point.getCreateDate() + " " +
					// point.getCreateTime());
					// // 积分创建时间在积分换算规则修改时间点之后 积分规则修改
					// if (createDate.compareTo(time) >= 0) {
					// // 取得积分换算单位
					// PointScalerUnit =
					// Config.getConfigValue("PointScalerUnit");
					// }
					// if (dt.getRowCount() > 0) {
					// point.setSource(new
					// BigDecimal(String.valueOf(map.get("Integral"))).divide(new
					// BigDecimal(PointScalerUnit), 2, BigDecimal.ROUND_DOWN) +
					// "元积分抵值 订单号：" + dt.getString(0, 0));
					// } else {
					// point.setSource(new
					// BigDecimal(String.valueOf(map.get("Integral"))).divide(new
					// BigDecimal(PointScalerUnit), 2, BigDecimal.ROUND_DOWN) +
					// "元积分抵值 ");
					// }
					//
					// } else {
					point.setSource(String.valueOf(map.get("Description")));

					// }
					listq.add(point);

				}

			}
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		page_Index = String.valueOf(page);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("page1", page);
		request.setAttribute("lastpage1", lastpage);
		page_count = String.valueOf(lastpage);
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		getPageDataList(param_map);
		request.setAttribute("pageFootList1", getPageFootList());
		setActionAlias("point!obtain.action");
		return "list";

	}

	public String trade() {
		String memberId = (String) getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = memberService.get(memberId);
		if (loginMember.getCurrentValidatePoint() != null && !"".equals(loginMember.getCurrentValidatePoint())) {
			currentValidatePoint = loginMember.getCurrentValidatePoint();
		} else {
			currentValidatePoint = 0;
		}
		if (loginMember.getPoint() != null && !"".equals(loginMember.getPoint())) {
			frozenPoint = loginMember.getPoint();
		} else {
			frozenPoint = 0;
		}
		return "trans";
	}

	public String toDo() {
		String memberId = (String) getSession().get(Member.LOGIN_MEMBER_ID_SESSION_NAME);
		Member loginMember = memberService.get(memberId);
		if (loginMember == null) {
			return ajaxJsonErrorMessage("您的登录已失效,请重新登录！");
		}
		if (!loginMember.getPassword().equals(DigestUtils.md5Hex(password))) {
			return ajaxJsonErrorMessage("您的密码输入有误,请重新输入！");
		}
		if (loginMember.getCurrentValidatePoint() <= Integer.parseInt(outInt)) {
			return ajaxJsonErrorMessage("您输入的积分小于拥有的积分！");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Member", loginMember);
		map.put("Integral", outInt);
		ActionUtil.dealAction("wj00045", map, getRequest());
		return ajaxJsonSuccessMessage("经验兑换成功！");
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}

	public ArrayList<HashMap<String, Object>> getListJFDH() {
		return listJFDH;
	}

	public void setListJFDH(ArrayList<HashMap<String, Object>> listJFDH) {
		this.listJFDH = listJFDH;
	}

	/**
	 * 积分说明详细页
	 * 
	 * @return
	 */
	public String queryPointsDesc() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id = getParameter("id");
		// 跳转来源
		String source = getParameter("source");
		String innerCode = Config.getValue("PointDescInnerCode");
		if (StringUtil.isNotEmpty(id)) {
			DataTable dt = new QueryBuilder("select Title,Content from zcarticle where id = ?", id).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				request.setAttribute("showTitle", dt.getString(0, 0));
				if (StringUtil.isNotEmpty(dt.getString(0, 1))) {
					request.setAttribute("showContent", dt.getString(0, 1).substring(3).substring(0, dt.getString(0, 1).length()-7).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\""));
				}
			}
		} else if ("jfsc".equals(source)) {
			DataTable dt = new QueryBuilder("select Title,Content from zcarticle where CatalogInnerCode = ? and status=30 and Title like '%如何赚取积分？%'", innerCode).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				request.setAttribute("showTitle", dt.getString(0, 0));
				if (StringUtil.isNotEmpty(dt.getString(0, 1))) {
					request.setAttribute("showContent", dt.getString(0, 1).replace("<p>", "").replace("</p>", "").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\""));
				}
			}
		}
		
		// 积分说明类表信息
		if (StringUtil.isNotEmpty(innerCode)) {
			List<HashMap<String, String>> fenlei = new ArrayList<HashMap<String, String>>();
			String fenleiSql = "select listOption from zdcolumn WHERE CODE='fenlei' ";
			String listOption = new QueryBuilder(fenleiSql).executeString();
			if (StringUtil.isNotEmpty(listOption)) {
				String[] array = listOption.split("\\n");
				HashMap<String, String> map;
				for (int i = array.length-1; i >= 0; i--) {
					map = new HashMap<String, String>();
					map.put("name", array[i]);
					fenlei.add(map);
				}
				request.setAttribute("fenlei", fenlei);
			}
			
			String countSql = "select count(1) from zcarticle where CatalogInnerCode = ? and status=30";
			int count = new QueryBuilder(countSql, innerCode).executeInt();
			if (count > 0) {
				List<HashMap<String, String>> pageList = new ArrayList<HashMap<String, String>>();
				int page = 1;
				if (count % 6 == 0) {
					page = count / 6;
				} else {
					page = count / 6 + 1;
				}

				HashMap<String, String> map;
				for (int i = 1; i <= page; i++) {
					map = new HashMap<String, String>();
					map.put("no", String.valueOf(i));
					if (i == 1) {
						// 默认选中第一页
						map.put("choose", "plage_select");
					}
					pageList.add(map);
				}
				request.setAttribute("pageList", pageList);
					
				List<HashMap<String, String>> info = new ArrayList<HashMap<String, String>>();
				String sql = "select Title,Content from zcarticle where CatalogInnerCode = ? and status=30 order by topflag desc,orderflag desc,publishdate desc,id desc limit 0,6;";
				DataTable dt = new QueryBuilder(sql, innerCode).executeDataTable();
				if (dt != null && dt.getRowCount() > 0) {
					int rowCount = dt.getRowCount();
					if (StringUtil.isEmpty(id) && !"jfsc".equals(source)) {
						request.setAttribute("showTitle", dt.getString(0, 0));
						if (StringUtil.isNotEmpty(dt.getString(0, 1))) {
							request.setAttribute("showContent", dt.getString(0, 1).substring(3).substring(0, dt.getString(0, 1).length()-7).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\""));
						}
					}
					for (int i = 0; i < rowCount; i++) {
						map = new HashMap<String, String>();
						map.put("Title", dt.getString(i, 0));
						map.put("Content", dt.getString(i, 1).replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&quot;", "\""));
						info.add(map);
					}
					request.setAttribute("info", info);
				}
			} else {
				logger.error("积分说明详细查询失败，配置项管理未配置积分说明详细栏目内部编码：PointDescInnerCode!");
			}
		}

		return "desc";
	}

	/**
	 * 按分类加载常见问题列表
	 * @return
	 */
	public String loadPointDescByLabel() {
		Map<String, String> jsonMap = new HashMap<String, String>();
		String fenlei = getParameter("fenlei");
		try {
			fenlei = java.net.URLDecoder.decode(fenlei, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		
		String innerCode = Config.getValue("PointDescInnerCode");
		DataTable dt;
		int count = 0;
		String sql = "select Title,Content from zcarticle where CatalogInnerCode = ? and status=30 order by topflag desc,orderflag desc,publishdate desc,id desc limit 0,6;";
		String countSql = "select count(1) from zcarticle where CatalogInnerCode = ? and status=30";
		if (StringUtil.isNotEmpty(fenlei) && !"全部".equals(fenlei.trim())) {
			sql = "select a.Title,a.Content from zcarticle a, zdcolumnvalue v where a.CatalogInnerCode = ? and a.status=30 and v.RelaID=a.id and v.ColumnCode='fenlei' and v.TextValue = ? order by topflag desc,orderflag desc,publishdate desc,a.id desc limit 0,6";
			dt = new QueryBuilder(sql, innerCode, fenlei).executeDataTable();
			countSql = "select count(1) from zcarticle a, zdcolumnvalue v where a.CatalogInnerCode = ? and a.status=30 and v.RelaID=a.ID and v.ColumnCode='fenlei' and v.TextValue = ?";
			count = new QueryBuilder(countSql, innerCode, fenlei).executeInt();

		} else {
			dt = new QueryBuilder(sql, innerCode).executeDataTable();
			count = new QueryBuilder(countSql, innerCode).executeInt();
		}

		String pageInfo = "";
		if (count > 0) {

			int page = 1;
			if (count % 6 == 0) {
				page = count / 6;
			} else {
				page = count / 6 + 1;
			}

			for (int i = 1; i <= page; i++) {
				if (i == 1) {
					pageInfo += "<span class='plage_select' ";
				} else {
					pageInfo += "<span class='' ";
				}
				pageInfo += "id='page"
						+ i
						+ "'> <a href='javascript:void(0);' onclick='loadPointDesc("
						+ i + ");'> " + i + "</a></span>";
			}
		}

		// 积分说明类表信息
		String html = "";
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				html += "<li class=\"member_qa_li\"><a onclick=\"setPointContent('"
						+ dt.getString(i, 0)
						+ "','"
						+ dt.getString(i, 1).replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
						+ "')\" href=\"javascript:void(0);\">" + dt.getString(i, 0) + "</a></li>";
			}
		}
		jsonMap.put("pageInfo", pageInfo);
		jsonMap.put("info", html);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	public String pageLoad() {
		String no = getParameter("no");
		String fenlei = getParameter("fenlei");
		try {
			fenlei = java.net.URLDecoder.decode(fenlei, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		int pageIndex = (Integer.valueOf(no) - 1) * 6;
		String innerCode = Config.getValue("PointDescInnerCode");
		// 积分说明类表信息
		String html = "";
		DataTable dt;
		String sql = "select Title,Content from zcarticle where CatalogInnerCode = ? and status=30 order by topflag desc,orderflag desc,publishdate desc,id desc limit ?,6;";
		if (StringUtil.isNotEmpty(fenlei) && !"全部".equals(fenlei)) {
			sql = "select a.Title,a.Content from zcarticle a, zdcolumnvalue v where a.CatalogInnerCode = ? and status=30 and v.RelaID=a.id and v.ColumnCode='fenlei' and v.TextValue = ? order by topflag desc,orderflag desc,publishdate desc,a.id desc limit ?,6";
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(innerCode);
			qb.add(fenlei);
			qb.add(pageIndex);
			dt = qb.executeDataTable();
			
		} else {
			dt = new QueryBuilder(sql, innerCode, pageIndex).executeDataTable();
		}
		
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				html += "<li class=\"member_qa_li\"><a href=\"javascript:void(0);\" onclick=\"setPointContent('" + dt.getString(i, 0) + "','" + dt.getString(i, 1).replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;") + "')\">" + dt.getString(i, 0)
						+ "</a></li>";
			}
		}
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("info", html);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}

	public String exchangeOrder() {
		HttpServletRequest request = ServletActionContext.getRequest();
		page_Index = getRequest().getParameter("page_Index");
		if (StringUtil.isEmpty(page_Index)) {
			page = 1;
		} else {
			page = Integer.valueOf(page_Index);
		}
		List<HashMap<String, String>> info = new ArrayList<HashMap<String, String>>();
		memberID = getLoginMember().getId();
		int pagesize = 4;
		String limit_sql = " limit " + (page - 1) * pagesize + "," + pagesize;
		// 取得会员兑换订单数量
		String sql = "select count(1) from PointExchangeInfo where memberid = ? and (prop1 is null or prop1 = '' or prop1 != 'Y' )  ";
		count = new QueryBuilder(sql, memberID).executeInt();

		sql = "select b.id as presentID, b.LinkUrl, b.LastNum, b.status, b.StartDate, b.EndDate, b.ProductID, b.giftName, a.orderSn, a.createDate, a.points, a.type as productType, d.CodeName as productTypeName, a.status as orderStatus, c.CodeName as orderStatusName from GiftClassify b, PointExchangeInfo a left join zdcode d on d.CodeType='PointsMall.Type' and d.CodeValue= a.type, zdcode c where a.giftClassifyID = b.id and a.memberid = ? and (a.prop1 is null or a.prop1 = '' or a.prop1 != 'Y' )  and a.status = c.CodeValue and c.CodeType='Jfsc.orderStatus' order by a.createDate desc"
				+ limit_sql;
		DataTable dt = new QueryBuilder(sql, memberID).executeDataTable();

		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			OrderQueryAction ac = new OrderQueryAction();
			String sql1 = "select IsPublish,Remark6 from sdproduct where ProductID = ?";
			for (int i = 0; i < rowCount; i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				DataColumn[] col = dt.getDataRow(i).getDataColumns();
				for (int j = 0; j < col.length; j++) {
					map.put(col[j].getColumnName(), dt.getString(i, col[j].getColumnName()));
				}
				map.put("IsPublish", "Y");
				// 判断礼品是否下架
				String currentTime = DateUtil.getCurrentDateTime();
				if (!"0".equals(map.get("status")) || StringUtil.isEmpty(map.get("LastNum")) || "0".equals(map.get("LastNum")) || DateUtil.compareDateTime(currentTime, map.get("StartDate")) < 0
						|| DateUtil.compareDateTime(currentTime, map.get("EndDate")) > 0) {
					map.put("IsPublish", "N");
				}

				if ("1".equals(map.get("productType"))) {
					DataTable dt1 = new QueryBuilder(sql1, map.get("ProductID")).executeDataTable();
					if (dt1 != null && dt1.getRowCount() > 0) {
						if (!"N".equals(map.get("IsPublish"))) {
							map.put("IsPublish", dt1.getString(0, 0));
							map.put("needUWCheckFlag", dt1.getString(0, 1));
						}
					} else {
						map.put("IsPublish", "N");
						map.put("needUWCheckFlag", ac.getneedUWCheckFlag(map.get("ProductID").substring(0, 4)));
					}
					map.put("epathFlag", ac.getPolicyFlag(map.get("orderSn"), null));
					map.put("KID", StringUtil.md5Hex(PubFun.getKeyValue() + map.get("orderSn")));
				}

				map.put("createDate", map.get("createDate").substring(0, 10) + "<br>" + map.get("createDate").substring(11, 16));
				info.add(map);
			}
		}
		request.setAttribute("listOrders", info);
		this.lastpage = ((count + pagesize - 1) / (pagesize));
		page_Index = String.valueOf(page);
		page_count = String.valueOf(lastpage);
		setActionAlias("point!exchangeOrder.action");
		Map<String, String> param_map = new HashMap<String, String>();
		param_map.put("totalCounts", String.valueOf(count));
		getPageDataList(param_map);
		return "exchangeOrder";
	}

	public String exchangePolicy() {

		return "exchangePolicy";
	}
}
