package com.sinosoft.cms.memberreport;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.wangjin.infoseeker.QueryUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MemberReport extends Page {

	/**
	 * 初始化会员查询页面.
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init(Mapx params) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		params.put("standardDate", fmt.format(date));
		params.put("statisticsDate", "365");
		return params;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init_points(Mapx params) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		params.put("startDate", fmt.format(date));
		params.put("endDate", fmt.format(date));
		return params;
	}
	
	/**
	 * dg1DataBind:会员基本属性查询. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	@SuppressWarnings("rawtypes")
	public static void dg1DataBind(DataGridAction dga) {

		// 基准时间和统计时间
		String standardDate = dga.getParams().getString("standardDate");
		String statisticsDate = dga.getParams().getString("statisticsDate");

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtil.isEmpty(standardDate) || StringUtil.isEmpty(statisticsDate)) {
			standardDate = fmt.format(new Date());
			statisticsDate = "365";
		}
		
		String sql = "SELECT ci.ChannelCode, ci.ChannelName, (SELECT COUNT(1) FROM member WHERE ci.ChannelCode = fromWap) membercount, "
				+ "(SELECT SUM(ROUND(o.totalAmount, 2)) FROM PrecMember o, sdinformation i, sdproduct p, member m WHERE o.ordersn = i.ordersn AND i.productid = p.productid AND o.memberid = m.id AND m.fromWap = ci.channelcode AND p.producttype = 'A' and o.createDate BETWEEN DATE_SUB(NOWPRAM, INTERVAL " + statisticsDate + " DAY) AND '" + standardDate + " 23:59:59') am, "
				+ "(SELECT SUM(ROUND(o.totalAmount, 2)) FROM PrecMember o, sdinformation i, sdproduct p, member m WHERE o.ordersn = i.ordersn AND i.productid = p.productid AND o.memberid = m.id AND m.fromWap = ci.channelcode AND p.producttype = 'B' and o.createDate BETWEEN DATE_SUB(NOWPRAM, INTERVAL " + statisticsDate + " DAY) AND '" + standardDate + " 23:59:59') bm, "
				+ "(SELECT SUM(ROUND(o.totalAmount, 2)) FROM PrecMember o, sdinformation i, sdproduct p, member m WHERE o.ordersn = i.ordersn AND i.productid = p.productid AND o.memberid = m.id AND m.fromWap = ci.channelcode AND p.producttype = 'D' and o.createDate BETWEEN DATE_SUB(NOWPRAM, INTERVAL " + statisticsDate + " DAY) AND '" + standardDate + " 23:59:59') dm, "
				+ "rm.rm30, rm.rm90, rm.rm150, rm.rm210, rm.rm365, rm.rm366, '0' f1, '0' f2, '0' f3, '0' f45, '0' f6 FROM channelinfo ci "
				+ "LEFT JOIN (SELECT r.ChannelCode, SUM(r.m30) AS rm30, SUM(r.m90) AS rm90, SUM(r.m150) AS rm150, SUM(r.m210) AS rm210, SUM(r.m365) AS rm365, SUM(r.m366) AS rm366 FROM "
				+ "(SELECT c.ChannelCode, m.id, IF (DATEDIFF(NOWPRAM, MAX(p.createDate)) <= 30, 1, 0) AS m30, "
				+ "IF (DATEDIFF(NOWPRAM, MAX(p.createDate)) > 30 AND DATEDIFF(NOWPRAM, MAX(p.createDate)) <= 90, 1, 0) AS m90, "
				+ "IF (DATEDIFF(NOWPRAM, MAX(p.createDate)) > 90 AND DATEDIFF(NOWPRAM, MAX(p.createDate)) <= 150, 1, 0) AS m150, "
				+ "IF (DATEDIFF(NOWPRAM, MAX(p.createDate)) > 150 AND DATEDIFF(NOWPRAM, MAX(p.createDate)) <= 210, 1, 0) AS m210, "
				+ "IF (DATEDIFF(NOWPRAM, MAX(p.createDate)) > 210 AND DATEDIFF(NOWPRAM, MAX(p.createDate)) <= 365, 1, 0) AS m365, "
				+ "IF (DATEDIFF(NOWPRAM, MAX(p.createDate)) > 365, 1, 0) AS m366 "
				+ "FROM channelinfo c LEFT JOIN (PrecMember p, member m) ON p.memberid = m.id AND m.fromWap = c.ChannelCode and c.Prop1='Y' and p.createDate BETWEEN DATE_SUB(NOWPRAM, INTERVAL " + statisticsDate + " DAY) AND '" + standardDate + " 23:59:59' GROUP BY m.id) AS r "
				+ "GROUP BY r.ChannelCode) rm "
				+ "ON rm.channelcode = ci.channelcode "
				+ " where ci.Prop1='Y'";
		
		sql = sql.replaceAll("NOWPRAM", "'" + standardDate + " 00:00:00'");

		// 会员来源渠道
		String channelsn = dga.getParams().getString("channelsn");
		String channel = QueryUtil.getChannelInfo(channelsn,"");
		if(StringUtil.isNotEmpty(channel)){
			String channelinfo = " and ci.channelcode IN ("+channel+")";
			sql += channelinfo;
		}
		
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		
		String sqlFValue = "SELECT i.channelcode,SUM(IF (a.c = 1, 1, 0)) AS f1, SUM(IF (a.c = 2, 1, 0)) AS f2, SUM(IF (a.c = 3, 1, 0)) AS f3, SUM(IF (a.c = 4 OR a.c = 5, 1, 0)) AS f45, SUM(IF (a.c >= 6, 1, 0)) AS f6 "
				+ "FROM channelinfo i,member m,(SELECT SUM(p.b) c, p.memberid FROM (SELECT DISTINCT memberid, DATE_FORMAT(createdate, '%Y-%m-%d') , '1' b FROM PrecMember WHERE createDate BETWEEN DATE_SUB(NOWPRAM, INTERVAL " + statisticsDate + " DAY) AND NOWEND) p GROUP BY p.memberid) a "
						+ "WHERE a.memberid = m.id  AND m.fromWap = i.channelcode "
						+ " and i.Prop1='Y' ";
		
		sqlFValue = sqlFValue.replaceAll("NOWPRAM", "'" + standardDate + " 00:00:00" + "'");
		sqlFValue = sqlFValue.replaceAll("NOWEND", "'" + standardDate + " 23:59:59" + "'");

		// 会员来源渠道
		if(StringUtil.isNotEmpty(channel)){
			String channelinfo = " and i.channelcode IN ("+channel+")";
			sqlFValue += channelinfo;
		}
		sqlFValue += " GROUP BY i.channelcode";
		QueryBuilder fqb = new QueryBuilder(sqlFValue);
		DataTable fdt = fqb.executeDataTable();
		
		Map fValue1 = null;
		Map fValue2 = null;
		Map fValue3 = null;
		Map fValue45 = null;
		Map fValue6 = null;
		if (fdt.getRowCount() > 0) {
			fValue1 = fdt.toMapx(0, 1);
			fValue2 = fdt.toMapx(0, 2);
			fValue3 = fdt.toMapx(0, 3);
			fValue45 = fdt.toMapx(0, 4);
			fValue6 = fdt.toMapx(0, 5);
		}
		
		// 会员个数
		BigDecimal memberCount = new BigDecimal("0");
		// 旅游险贡献
		BigDecimal lyAmount = new BigDecimal("0");
		// 意外险贡献
		BigDecimal ywAmount = new BigDecimal("0");
		// 健康险贡献
		BigDecimal jkAmount = new BigDecimal("0");
		// M >= 30 会员数量
		BigDecimal m30 = new BigDecimal("0");
		// 30＜R≤90会员数量
		BigDecimal m90 = new BigDecimal("0");
		// 90＜R≤150会员数量
		BigDecimal m150 = new BigDecimal("0");
		// 150＜R≤210会员数量
		BigDecimal m210 = new BigDecimal("0");
		// 210＜R≤365会员数量
		BigDecimal m365 = new BigDecimal("0");
		// 365＜R会员数量
		BigDecimal m366 = new BigDecimal("0");

		// f1
		BigDecimal f1 = new BigDecimal("0");
		// f2
		BigDecimal f2 = new BigDecimal("0");
		// f3
		BigDecimal f3 = new BigDecimal("0");
		// f45
		BigDecimal f45 = new BigDecimal("0");
		// f6
		BigDecimal f6 = new BigDecimal("0");
		for (int i = 0; i < dt.getRowCount(); i++) {
			
			if (StringUtil.isEmpty(dt.getString(i, "am"))) {
				dt.set(i, "am", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "bm"))) {
				dt.set(i, "bm", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "dm"))) {
				dt.set(i, "dm", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "rm30"))) {
				dt.set(i, "rm30", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "rm90"))) {
				dt.set(i, "rm90", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "rm150"))) {
				dt.set(i, "rm150", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "rm210"))) {
				dt.set(i, "rm210", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "rm365"))) {
				dt.set(i, "rm365", 0);
			}
			if (StringUtil.isEmpty(dt.getString(i, "rm366"))) {
				dt.set(i, "rm366", 0);
			}
			String channelcode = dt.getString(i, "ChannelCode");
			// 设置 F值
			if (fdt.getRowCount() > 0) {
				if (null != fValue1.get(channelcode) && !"0".equals(fValue1.get(channelcode))) {
					dt.set(i, "f1", fValue1.get(channelcode));
				}
				if (null != fValue2.get(channelcode) && !"0".equals(fValue2.get(channelcode))) {
					dt.set(i, "f2", fValue2.get(channelcode));
				}
				if (null != fValue3.get(channelcode) && !"0".equals(fValue3.get(channelcode))) {
					dt.set(i, "f3", fValue3.get(channelcode));
				}
				if (null != fValue45.get(channelcode) && !"0".equals(fValue45.get(channelcode))) {
					dt.set(i, "f45", fValue45.get(channelcode));
				}
				if (null != fValue6.get(channelcode) && !"0".equals(fValue6.get(channelcode))) {
					dt.set(i, "f6", fValue6.get(channelcode));
				}
			}
			
			// 合计
			memberCount = memberCount.add(new BigDecimal(dt.getString(i, "membercount")));
			lyAmount = lyAmount.add(new BigDecimal(dt.getString(i, "am")));
			ywAmount = ywAmount.add(new BigDecimal(dt.getString(i, "bm")));
			jkAmount = jkAmount.add(new BigDecimal(dt.getString(i, "dm")));
			m30 = m30.add(new BigDecimal(dt.getString(i, "rm30")));
			m90 = m90.add(new BigDecimal(dt.getString(i, "rm90")));
			m150 = m150.add(new BigDecimal(dt.getString(i, "rm150")));
			m210 = m210.add(new BigDecimal(dt.getString(i, "rm210")));
			m365 = m365.add(new BigDecimal(dt.getString(i, "rm365")));
			m366 = m366.add(new BigDecimal(dt.getString(i, "rm366")));
			
			f1 = f1.add(new BigDecimal(dt.getString(i, "f1")));
			f2 = f2.add(new BigDecimal(dt.getString(i, "f2")));
			f3 = f3.add(new BigDecimal(dt.getString(i, "f3")));
			f45 = f45.add(new BigDecimal(dt.getString(i, "f45")));
			f6 = f6.add(new BigDecimal(dt.getString(i, "f6")));
		}
		
		Object[] rowValue = {"", "合计", memberCount.toString(), lyAmount.toString(), ywAmount.toString(), jkAmount.toString(), m30.toString(), m90.toString(),
				m150.toString(), m210.toString(), m365.toString(), m366.toString(), f1.toString(), f2.toString(), f3.toString(), f45.toString(), f6.toString()};
		
		dt.insertRow(rowValue);
		dga.bindData(dt);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx initDialog(Mapx params) {
		params.put("type", params.getString("type"));
		params.put("standardDate", params.getString("standardDate"));
		params.put("statisticsDate", params.getString("statisticsDate"));
		params.put("channelsn", params.getString("channelsn"));
		return params;
	}

	public static void dg1DataBind_dialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		// 基准时间和统计时间
		String standardDate = dga.getParams().getString("standardDate");
		String statisticsDate = dga.getParams().getString("statisticsDate");

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtil.isEmpty(standardDate) || StringUtil.isEmpty(statisticsDate)) {
			standardDate = fmt.format(new Date());
			statisticsDate = "365";
		}
		QueryBuilder qb = new QueryBuilder("SELECT m.id,m.createdate,m.realName,m.username,m.sex,m.birthday,m.address,m.email,m.IDNO,m.mobileNO, '' exchangeUsed, '' buyUsed,"
				+ "'' FValue,'' KValue,'' memberrank,'' RValue,'' MValue, '' gradeName, m.point, m.currentValidatePoint, m.recommendRegPoints, m.recommendBuyPoints, "
				+ " m.logindate,(POINT+currentValidatePoint) integral,MIN(c.createdate) mincreatedate,c.memberid,c.applicantName,c.applicantBirthday,m.buyCount,'0' groupCount,"
				+ " c.applicantSex,c.applicantMobile,c.applicantMail,(SELECT channelname FROM channelinfo WHERE channelcode = m.fromWap) as fromName,m.consumeAmount,if (m.vipFlag = 'Y', 'VIP', m.grade) gradeCode FROM member m " + " LEFT JOIN PrecMember c ON c.memberid = m.id WHERE m.id IS NOT NULL AND m.id <>''");

		// 会员来源渠道 ----------------
		String channelsn = dga.getParams().getString("channelsn");
		if(StringUtil.isNotEmpty(channelsn)){
			String channelinfo = " AND find_in_set(m.fromWap,'" + channelsn + "') ";
			qb.append(channelinfo);
		} else {
			qb.append(" AND EXISTS (SELECT 1 FROM channelinfo where m.fromWap = channelcode and prop1 = 'Y')");
		}
		// 会员来源渠道 ----------------
		
		// R 区间 -------------
		String RStart = "";
		String REnd = "";
		if (StringUtil.isEmpty(RStart)) {
			RStart = "0";
		}
		if (StringUtil.isEmpty(REnd)) {
			REnd = statisticsDate;
		}
		
		if ("am".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM PrecMember r, sdinformation i, sdproduct p WHERE m.id = r.memberid AND r.ordersn = i.ordersn AND i.productid = p.ProductID AND p.ProductType = 'A' and r.createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY))");
		}
		
		if ("bm".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM PrecMember r, sdinformation i, sdproduct p WHERE m.id = r.memberid AND r.ordersn = i.ordersn AND i.productid = p.ProductID AND p.ProductType = 'B' and r.createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY))");
		}
		
		if ("dm".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM PrecMember r, sdinformation i, sdproduct p WHERE m.id = r.memberid AND r.ordersn = i.ordersn AND i.productid = p.ProductID AND p.ProductType = 'D' and r.createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY))");
		}
		
		if ("rm30".equals(type) || "rm90".equals(type) || "rm150".equals(type) || "rm210".equals(type) || "rm365".equals(type) || "rm366".equals(type)) {
			if ("rm30".equals(type)) {
				RStart = "0";
				REnd = "30";
			} else if ("rm90".equals(type)) {
				RStart = "30";
				REnd = "90";
			} else if ("rm150".equals(type)) {
				RStart = "90";
				REnd = "150";
			} else if ("rm210".equals(type)) {
				RStart = "150";
				REnd = "210";
			} else if ("rm365".equals(type)) {
				RStart = "210";
				REnd = "365";
			} else if ("rm366".equals(type)) {
				RStart = "365";
				REnd = "100000"; // 无穷大
			}

			if (RStart == "0") {
				qb.append(" AND (SELECT DATEDIFF(str_to_date('" + standardDate.trim() + " 00:00:00', '%Y-%m-%d %H:%i:%s'),MAX(createDate)) FROM PrecMember WHERE memberid = m.id) >= '" + RStart + "'");
			} else{
				qb.append(" AND (SELECT DATEDIFF(str_to_date('" + standardDate.trim() + " 00:00:00', '%Y-%m-%d %H:%i:%s'),MAX(createDate)) FROM PrecMember WHERE memberid = m.id) > '" + RStart + "'");
			}
			
			qb.append(" AND (SELECT DATEDIFF(str_to_date('" + standardDate.trim() + " 00:00:00', '%Y-%m-%d %H:%i:%s'),MAX(createDate)) FROM PrecMember WHERE memberid = m.id) <= '" + REnd + "'");
		}	
		
		if ("f1".equals(type) || "f2".equals(type) || "f3".equals(type) || "f45".equals(type) || "f6".equals(type)) {
			
			String fsql = "";
			if ("f1".equals(type)) {
				fsql = " = 1 ";
			} else if ("f2".equals(type)) {
				fsql = " = 2 ";
			} else if ("f3".equals(type)) {
				fsql = " = 3 ";
			} else if ("f45".equals(type)) {
				fsql = " in (4, 5) ";
			} else if ("f6".equals(type)) {
				fsql = " >= 6 ";
			}

			qb.append(" AND (SELECT COUNT(DISTINCT DATE_FORMAT(createdate, '%Y-%m-%d'))  FROM PrecMember WHERE memberid = m.id AND createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY)) " + fsql);
		}
		
		// 按照登录时间逆序排序，讲最后登录的放到最上面
		qb.append(" GROUP BY m.id ORDER BY m.logindate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		String SqlRM = " SELECT memberid,SUM(totalAmount) MValue,DATEDIFF(NOW(), MAX(createDate)) RValue FROM PrecMember WHERE createDate " + "BETWEEN DATE_SUB('" + standardDate.trim()
				+ " 00:00:00', INTERVAL " + statisticsDate.trim() + " DAY) AND '" + standardDate.trim() + " 23:59:59' and memberid = ";
		if (StringUtil.isNotEmpty(RStart) || StringUtil.isNotEmpty(REnd)) {
			SqlRM = " SELECT memberid,SUM(totalAmount) MValue,DATEDIFF(str_to_date('" + standardDate.trim() + " 00:00:00', '%Y-%m-%d %H:%i:%s'), MAX(createDate)) RValue FROM PrecMember WHERE createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY)"
					+ " and memberid = ";
		}

		// 相应R,F值
		int r;
		int m;
		int days;
		double resulet;
		double R1 = getMemberConfig("member-R1");
		double R2 = getMemberConfig("member-R2");
		double R3 = getMemberConfig("member-R3");
		double R4 = getMemberConfig("member-R4");
		double R5 = getMemberConfig("member-R5");
		double M1 = getMemberConfig("member-M1");
		double M2 = getMemberConfig("member-M2");
		double M3 = getMemberConfig("member-M3");
		double M4 = getMemberConfig("member-M4");
		double M5 = getMemberConfig("member-M5");
		double F1 = getMemberConfig("member-F1");
		double F2 = getMemberConfig("member-F2");
		double F3 = getMemberConfig("member-F3");
		double F4 = getMemberConfig("member-F4");
		double F5 = getMemberConfig("member-F5");
		double K1 = getMemberConfig("member-K1");
		double K2 = getMemberConfig("member-K2");
		double K3 = getMemberConfig("member-K3");
		double K4 = getMemberConfig("member-K4");
		double K5 = getMemberConfig("member-K5");
		if (dt != null && dt.getRowCount() > 0) {
			DataTable graDt = new QueryBuilder("select gradeCode, gradeName from membergrade").executeDataTable();
			Map<String, String> graMap = new HashMap<String, String>();
			if (graDt != null && graDt.getRowCount() > 0) {
				int graCount = graDt.getRowCount();
				for (int i = 0; i < graCount; i++) {
					graMap.put(graDt.getString(i, 0), graDt.getString(i, 1));
				}
			}
			String memberIds = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				memberIds += ",'"+dt.getString(i, "id") + "'";
				DataTable dtValue = new QueryBuilder(SqlRM + "'" + dt.getString(i, "id") + "'").executeDataTable();
				// 购买时间间隔
				r = 0;
				days = 0;
				// 累计金额
				m = 0;
				resulet = 0.0;
				if (dtValue != null && dtValue.getRowCount() > 0) {
					if (StringUtil.isNotEmpty(dtValue.getString(0, "RValue"))) {
						days = Integer.parseInt(dtValue.getString(0, "RValue"));
						dt.set(i, "RValue", dtValue.getString(0, "RValue"));
					} else {
						dt.set(i, "RValue", 0);
					}

					if (StringUtil.isNotEmpty(dtValue.getString(0, "MValue"))) {
						resulet = Double.parseDouble(dtValue.getString(0, "MValue"));
						dt.set(i, "MValue", dtValue.getString(0, "MValue"));
					} else {
						dt.set(i, "MValue", 0);
					}
				}
				if (days <= R1 && days > R2) {
					r = 1;
				} else if (days > R3 && days <= R2) {
					r = 2;
				} else if (days > R4 && days <= R3) {
					r = 3;
				} else if (days > R5 && days <= R4) {
					r = 4;
				} else if (days > 0 && days <= R5) {
					r = 5;
				}

				if (resulet > M1 && resulet <= M2) {
					m = 1;
				} else if (resulet > M2 && resulet <= M3) {
					m = 2;
				} else if (resulet > M3 && resulet <= M4) {
					m = 3;
				} else if (resulet > M4 && resulet <= M5) {
					m = 4;
				} else if (resulet > M5) {
					m = 5;
				}

				String sqlAll = "SELECT * FROM PrecMember WHERE memberid='" + dt.getString(i, "id") + "' and createdate " + " BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL "
						+ statisticsDate.trim() + " DAY) AND '" + standardDate.trim() + " 23:59:59' ";
				String sqlGroup = " group by DATE_FORMAT(createdate,'%Y-%m-%d')  ORDER BY createdate ASC";
				sqlAll = sqlAll + sqlGroup;
				if (StringUtil.isNotEmpty(RStart) || StringUtil.isNotEmpty(REnd)) {
					sqlAll = "SELECT DISTINCT DATE_FORMAT(createdate, '%Y-%m-%d') FROM PrecMember WHERE memberid='" + dt.getString(i, "id") + "' and createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY) ";
				}
				
				QueryBuilder sqlqb = new QueryBuilder(sqlAll);
				DataTable dts = sqlqb.executeDataTable();
				int count = 0;
				if (dts != null && dts.getRowCount() > 0) {
					count = dts.getRowCount();
				}
				dt.set(i, "FValue", count);
				int f = 0;
				if (F1 < count && count <= F2) {
					f = 1;
				} else if (F2 < count && count <= F3) {
					f = 2;
				} else if (F3 < count && count <= F4) {
					f = 3;
				} else if (F4 < count && count <= F5) {
					f = 4;
				} else if (count > F5) {
					f = 5;
				}

				try {
					// 计算公式K=1R+3F+4M
					double kV = 1 * r + 3 * f + 4 * m;
					dt.set(i, "KValue", kV);
					String memberRank = "";
					if (kV <= K1) {
						memberRank = "一星会员";
					} else if (kV > K1 && kV <= K2) {
						memberRank = "二星会员";
					} else if (kV > K2 && kV <= K3) {
						memberRank = "三星会员";
					} else if (kV > K3 && kV <= K4) {
						memberRank = "四星会员";
					} else if (kV > K4 && kV <= K5) {
						memberRank = "五星会员";
					}
					dt.set(i, "memberrank", memberRank);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				if ("0".equals(dt.getString(i, "sex")) || "M".equals(dt.getString(i, "sex"))) {
					dt.set(i, "sex", "男");
				} else if ("1".equals(dt.getString(i, "sex")) || "F".equals(dt.getString(i, "sex"))) {
					dt.set(i, "sex", "女");
				}
				if ("0".equals(dt.getString(i, "applicantSex")) || "M".equals(dt.getString(i, "applicantSex"))) {
					dt.set(i, "applicantSex", "男");
				} else if ("1".equals(dt.getString(i, "applicantSex")) || "F".equals(dt.getString(i, "applicantSex"))) {
					dt.set(i, "applicantSex", "女");
				}
				dt.set(i, "gradeName", graMap.get(dt.getString(i, "gradeCode")));
				
			}
			
			memberIds = memberIds.substring(1);
			// 取得积分商城消耗积分
			String sql = "select MemberId,round(sum(Integral),0) from sdintcalendar where source='3' and MemberId in ("+memberIds+") group by MemberId";
			DataTable exchaDt = new QueryBuilder(sql).executeDataTable();
			Map<String, String> exchaMap = new HashMap<String, String>();
			if (exchaDt != null && exchaDt.getRowCount() > 0) {
				int rowCount = exchaDt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					exchaMap.put(exchaDt.getString(i, 0), exchaDt.getString(i, 1));
				}
			}
			// 取得购买抵值消耗积分
			sql = "select MemberId,round(sum(Integral),0) from sdintcalendar where source='2' and MemberId in ("+memberIds+") group by MemberId";
			DataTable buyDt = new QueryBuilder(sql).executeDataTable();
			Map<String, String> buyMap = new HashMap<String, String>();
			if (buyDt != null && buyDt.getRowCount() > 0) {
				int rowCount = buyDt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					buyMap.put(buyDt.getString(i, 0), buyDt.getString(i, 1));
				}
			}
			
			// 取得团单数
			sql = "select memberId,groupCount from MemberGroup where memberId in ("+memberIds+")";
			DataTable groDt = new QueryBuilder(sql).executeDataTable();
			Map<String, String> groMap = new HashMap<String, String>();
			if (groDt != null && groDt.getRowCount() > 0) {
				int rowCount = groDt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					groMap.put(groDt.getString(i, 0), groDt.getString(i, 1));
				}
			}
			
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "exchangeUsed", exchaMap.get(dt.getString(i, "id")));
				dt.set(i, "buyUsed", buyMap.get(dt.getString(i, "id")));
				dt.set(i, "groupCount", groMap.get(dt.getString(i, "id")));
			}
		}
		dga.bindData(dt);
	}
	
	/**
	 * dg2DataBind:积分情况. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg2DataBind(DataGridAction dga) {

		// 统计时间
		String startDate = dga.getParams().getString("startDate");
		String endDate = dga.getParams().getString("endDate");

		String sql = "SELECT channelcode, channelname,IF (SUM(points.s0) IS NULL, 0, SUM(points.s0)) AS sums0,IF (SUM(points.s1) IS NULL, 0, SUM(points.s1)) AS sums1"
				+ ",IF (SUM(points.s26) IS NULL, 0, SUM(points.s26)) AS sums26, IF (SUM(points.svip) IS NULL, 0, SUM(points.svip)) AS sumsvip, "
				+ "IF (SUM(points.s21) IS NULL, 0, SUM(points.s21)) AS sums21, IF (SUM(points.s2) IS NULL, 0, SUM(points.s2)) AS sums2 "
				+ "FROM channelinfo LEFT JOIN (SELECT o.channelsn, t.receiveDate, IF (s.Prop3 IS NULL AND s.Source = '0', IF (s.manner = '0', s.Integral, 0), 0) AS s0, IF(s.Source = '1', s.Integral, 0) s1, IF(s.Source = '26', IF (s.manner = '0', s.Integral, 0), 0) s26, "
				+ "IF(s.Source = '0' AND s.prop3 IS NOT NULL, IF (s.manner = '0', s.Integral, 0), 0) svip, IF(s.Source = '21', s.Integral, 0) s21, 0 s2 FROM sdintcalendar s, sdorders o, TradeInformation t "
				+ "WHERE s.Businessid = o.ordersn AND s.Source IN ('0', '1', '21', '26') AND o.ordersn = t.ordID UNION SELECT o.channelsn, t.receiveDate, 0 s0, 0 s1, 0 s26, 0 svip, 0 s21, "
				+ "s.Integral s2 FROM sdintcalendar s, sdorders o, TradeInformation t WHERE s.Businessid = o.paysn AND s.Source = '2' AND o.ordersn = t.ordID) points ON points.channelsn = channelcode and Prop1 = 'Y' ";
		
		sql += " AND points.receiveDate BETWEEN '" + startDate + " 00:00:00' AND '" + endDate + " 23:59:59'";
		
		sql += " GROUP BY channelcode ";

		// 会员来源渠道
		String channelsn = dga.getParams().getString("channelsn");
		String channel = QueryUtil.getChannelInfo(channelsn,"");
		if(StringUtil.isNotEmpty(channel)){
			String channelinfo = " HAVING channelcode IN ("+channel+")";
			sql += channelinfo;
		}
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		 
		// 合计
		BigDecimal sums0 = new BigDecimal("0");
		BigDecimal sums1 = new BigDecimal("0");
		BigDecimal sums26 = new BigDecimal("0");
		BigDecimal sumsvip = new BigDecimal("0");
		BigDecimal sums21 = new BigDecimal("0");
		BigDecimal sums2 = new BigDecimal("0");
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			sums0 = sums0.add(new BigDecimal(dt.getString(i, "sums0")));
			sums1 = sums1.add(new BigDecimal(dt.getString(i, "sums1")));
			sums26 = sums26.add(new BigDecimal(dt.getString(i, "sums26")));
			sumsvip = sumsvip.add(new BigDecimal(dt.getString(i, "sumsvip")));
			sums21 = sums21.add(new BigDecimal(dt.getString(i, "sums21")));
			sums2 = sums2.add(new BigDecimal(dt.getString(i, "sums2")));
		}
		
		Object[] rowValue = {"", "合计", sums0.toString(), sums1.toString(), sums26.toString(), sumsvip.toString(), sums21.toString(), sums2.toString()};
		
		dt.insertRow(rowValue);
		dga.bindData(dt);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init2Dialog(Mapx params) {
		params.put("type", params.getString("type"));
		params.put("startDate", params.getString("startDate"));
		params.put("endDate", params.getString("endDate"));
		params.put("channelsn", params.getString("channelsn"));
		return params;
	}
	
	public static void dg2DataBind_dialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		// 统计时间
		String startDate = dga.getParams().getString("startDate");
		String endDate = dga.getParams().getString("endDate");
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id,d.id AS rid,a.memberid,d.recognizeesn AS recognizeeSn,a.ordersn,a.orderStatus,e.policyNo,e.appstatus,DATE_FORMAT(e.svalidate,'%Y-%m-%d') AS svalidate,");
		sb.append("(SELECT ChannelName FROM channelinfo WHERE channelcode=a.channelsn) channelsn, c.ApplicantMail, ");
		sb.append("(SELECT g.name FROM AREA g WHERE  c.applicantarea1 = g.id ),f.FeeRate,a.tbTradeSeriNo,b.ProductName,b.planName,");
		sb.append("ROUND(SUM(d.recognizeePrem),2) AS totalAmount,a.CreateDate,a.ModifyDate," + " a.ActivitySn,a.orderActivity,a.PayPrice,");
		sb.append("h.codeName AS orderstatusname ,'' isoldmember ,d.RecognizeeName AS recognizeeNu,c.ApplicantName,'' remark,");
		sb.append("(SELECT concat(IFNULL(email,''),'/',IFNULL(mobileno,'')) FROM member WHERE id=a.memberid) AS MID,  ");
		sb.append(" a.couponsn as couponSn,a.orderCoupon as orderCoupon,'' parValue,a.offsetPoint as offsetPoint, a.orderIntegral as orderIntegral, a.paySn as paySn, '' as paymentReport,b.productid,b.insurancecompany ,a.diyActivityDescription as diyActivityDescription, '' KID, ");
		if ("sums0".equals(type)) {
			sb.append(" (SELECT SUM(IF(s.manner = '0', s.Integral, 0)) from sdintcalendar s where s.Businessid = a.ordersn and s.Source = '0' and s.Prop3 IS NULL) AS getPoints ");
		} else if ("sums1".equals(type)) {
			sb.append(" (SELECT SUM(s.Integral) from sdintcalendar s where s.Businessid = a.ordersn and s.Source = '1') AS getPoints ");
		} else if ("sums26".equals(type)) {
			sb.append(" (SELECT SUM(IF(s.manner = '0', s.Integral, 0)) from sdintcalendar s where s.Businessid = a.ordersn and s.Source = '26') AS getPoints ");
		} else if ("sumsvip".equals(type)) {
			sb.append(" (SELECT SUM(IF(s.manner = '0', s.Integral, 0)) from sdintcalendar s where s.Businessid = a.ordersn and s.Source = '0' and s.Prop3 IS NOT NULL) AS getPoints ");
		} else if ("sums21".equals(type)) {
			sb.append(" (SELECT SUM(s.Integral) from sdintcalendar s where s.Businessid = a.ordersn and s.Source = '21') AS getPoints ");
		} else if ("sums2".equals(type)) {
			sb.append(" (SELECT SUM(s.Integral) from sdintcalendar s where s.Businessid = a.paysn and s.Source = '2') AS getPoints ");
		} else {
			sb.append(" '' AS getPoints ");
		}
		sb.append(" FROM sdorders a,sdinformation b,sdinformationappnt c,sdinformationinsured d,sdinformationrisktype e,femrisk f,zdcode h, TradeInformation t, sdintcalendar i ");
		sb.append("WHERE a.ordersn = b.ordersn AND b.informationsn = c.informationsn AND a.ordersn = d.ordersn   AND a.ordersn = e.ordersn AND d.recognizeeSn = e.recognizeeSn AND b.productid = f.eriskid  ");
		
		sb.append("and  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus "); // and a.createdate>='" + startDate + " 00:00:00' and a.createdate <='" + endDate + " 23:59:59' ");
		
		sb.append(" and a.ordersn = t.ordID AND t.receiveDate BETWEEN '" + startDate + " 00:00:00' and '" + endDate + " 23:59:59'");
		// 来源渠道
		String channelsn = dga.getParams().getString("channelsn");
		String channel = QueryUtil.getChannelInfo(channelsn,"");
		if(StringUtil.isNotEmpty(channel)){
			String channelinfo = " and a.channelsn IN ("+channel+")";
			sb.append(channelinfo);
		}
		
		if ("sums0".equals(type)) {
			sb.append(" and a.ordersn = i.Businessid and i.source = '0' and i.Prop3 IS NULL");
			//sb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where a.ordersn = Businessid and source = '0' and Prop3 IS NULL)");
		}
		if ("sums1".equals(type)) {
			sb.append(" and a.ordersn = i.Businessid and i.source = '1'");
			//sb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where a.ordersn = Businessid and source = '1')");
		}
		if ("sums26".equals(type)) {
			sb.append(" and a.ordersn = i.Businessid and i.source = '26'");
			//sb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where a.ordersn = Businessid and source = '26')");
		}
		if ("sumsvip".equals(type)) {
			sb.append(" and a.ordersn = i.Businessid and i.source = '0' and i.Prop3 IS NOT NULL");
			//sb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where a.ordersn = Businessid and source = '0' and Prop3 IS NOT NULL)");
		}
		if ("sums21".equals(type)) {
			sb.append(" and a.ordersn = i.Businessid and i.source = '21'");
			//sb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where a.ordersn = Businessid and source = '21')");
		}
		if ("sums2".equals(type)) {
			sb.append(" and a.paysn = i.Businessid and i.source = '2'");
			//sb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where a.paysn = Businessid and source = '2')");
		}
		sb.append(" GROUP BY a.ordersn order by a.modifydate desc,a.ordersn desc");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		//dt.insertColumn("KID");
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "KID", StringUtil.md5Hex(com.sinosoft.cms.pub.PubFun.getKeyValue() + dt.getString(i, "orderSn")));
				if (dt.getString(i, "orderstatus").equals("7") || dt.getString(i, "appstatus").equals("1")) {
					dt.set(i, "totalAmount", "+" + dt.getString(i, "totalAmount"));
				}
				// 保全记录查询
				String queryRemark = "SELECT remark,OperateTime,OperateName FROM sdremark WHERE ordersn='" + dt.getString(i, "orderSn") + "' ORDER BY OperateTime DESC";
				QueryBuilder qbr = new QueryBuilder(queryRemark);
				DataTable dtr = qbr.executeDataTable();
				String remark = "";
				if (dtr != null && dtr.getRowCount() > 0) {
					String remarkTem = "";
					for (int j = 0; j < dtr.getRowCount(); j++) {
						int a = j + 1;
						remarkTem = dtr.getString(j, "remark") + "  " + dtr.getString(j, "OperateTime") + "  " + dtr.getString(j, "OperateName") + " && ";
						if (j == dtr.getRowCount() -1 && remarkTem.indexOf("变更：初始商家订单号") >= 0) {
							remark = remarkTem + " " + remark;
						} else {
							remark += a + ", " + remarkTem;
						}
					}
					dt.set(i, "remark", remark);
				}
				// 被保人个数查询
				String sqlre = "SELECT recognizeeName,recognizeeIdentityId FROM sdinformationinsured WHERE ordersn ='" + dt.getString(i, "orderSn") + "' GROUP BY recognizeekey";
				QueryBuilder qbre = new QueryBuilder(sqlre);
				DataTable dtre = qbre.executeDataTable();
				if (dtre != null && dtre.getRowCount() > 0) {
					dt.set(i, "recognizeeNu", dtre.getRowCount());
				} else {
					dt.set(i, "recognizeeNu", 0);
				}
				String parValue = dt.getString(i, "orderCoupon");
				BigDecimal totalamount = new BigDecimal("0.0");
				if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {

					String totalAmount = dt.getString(i, "totalAmount");
					if (StringUtil.isEmpty(totalAmount)) {
						totalAmount = "0.00";
					}
					totalamount = new BigDecimal(totalAmount);

					if (StringUtil.isNotEmpty(parValue) && totalamount.compareTo(new BigDecimal(parValue)) != -1) {
						totalamount = totalamount.subtract(new BigDecimal(parValue));
						dt.set(i, "PayPrice", totalamount);
					}
				}
				if (StringUtil.isNotEmpty(parValue)) {
					dt.set(i, "parValue", parValue);
				} else {
					dt.set(i, "parValue", "");
				}
				// 新老会员查询
				String sqlorder = "select ordersn,orderstatus from sdorders where memberid ='" + dt.getString(i, "memberid") + "' AND orderstatus = 7 and ordersn != '" + dt.getString(i, "ordersn")
						+ "'";
				QueryBuilder qborder = new QueryBuilder(sqlorder);
				DataTable dtorder = qborder.executeDataTable();
				if (dtorder != null && dtorder.getRowCount() > 0) {
					dt.set(i, "isoldmember", "是");
				} else {
					dt.set(i, "isoldmember", "否");
				}

				// 积分抵值

				if ("0".equals(dt.getString(i, "orderIntegral")) || dt.getString(i, "orderIntegral") == null || "".equals(dt.getString(i, "orderIntegral"))) {
					dt.set(i, "offsetPoint", "0");
				} else {
					// 积分换算规则修改时间点
					BigDecimal offsetPoint = new BigDecimal(dt.getString(i, "orderIntegral"));
					dt.set(i, "offsetPoint", offsetPoint);
					if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {
						if (totalamount.compareTo(offsetPoint) != -1) {
							dt.set(i, "PayPrice", totalamount.subtract(offsetPoint));
						}
					}
				}

				String paymentSql = "select count(1) from paymentInfo where ordersn = ?";
				QueryBuilder paymentQb = new QueryBuilder(paymentSql, dt.getString(i, "ordersn"));
				if (paymentQb.executeInt() > 0) {
					dt.set(i, "paymentReport", "是");

				}
				if (StringUtil.isEmpty(dt.getString(i, "PayPrice"))) {
					if (Integer.valueOf(dt.getString(i, "orderstatus")) < 7 || "8".equals(dt.getString(i, "orderstatus").trim())) {
						dt.set(i, "PayPrice", "");
					}
				}
			}
		}
		dga.bindData(dt);
	}
	
	/**
	 * dg3DataBind:积分情况. <br/>
	 *
	 * @author wwy
	 * @param dga
	 */
	public static void dg3DataBind(DataGridAction dga) {

		// 统计时间
		String startDate = dga.getParams().getString("startDate");
		String endDate = dga.getParams().getString("endDate");

		String sql = "SELECT channelcode,channelname, IF (SUM(points.s4) IS NULL, 0, SUM(points.s4)) AS sums4,IF (SUM(points.s20) IS NULL, 0, SUM(points.s20)) AS sums20,IF (SUM(points.s10) IS NULL, 0, SUM(points.s10)) AS sums10,"
				+ "IF (SUM(points.s6) IS NULL, 0, SUM(points.s6)) AS sums6,IF (SUM(points.s7) IS NULL, 0, SUM(points.s7)) AS sums7,IF (SUM(points.s3) IS NULL, 0, SUM(points.s3)) AS sums3  "
				+ "FROM channelinfo  "
				+ " LEFT JOIN (SELECT m.fromWap, s.createDate, IF (s.Source = '4', s.Integral, 0) AS s4, IF(s.Source = '20', s.Integral, 0) s20, "
				+ " IF(s.Source = '8' OR s.Source = '9' OR s.Source = '10' OR s.Source = '11' OR s.Source = '12' OR s.Source = '13' OR s.Source = '14' OR s.Source = '15' OR s.Source = '16' OR s.Source = '17', s.Integral, 0) s10, "
				+ " IF(s.Source = '6', s.Integral, 0) s6, IF(s.Source = '7', s.Integral, 0) s7, IF(s.Source = '3', s.Integral, 0) s3, 0 s2 FROM sdintcalendar s, member m WHERE s.Source IN ('4', '20', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '6', '7', '3')  "
				+ " AND s.memberId = m.id ) points ON points.fromWap = channelcode AND Prop1 = 'Y' ";
		
		if (StringUtil.isNotEmpty(startDate)) {
			sql += "AND points.createDate >= '" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			sql += "AND points.createDate <= '" + endDate + "'";
		}
		sql += " GROUP BY channelcode ";

		// 会员来源渠道
		String channelsn = dga.getParams().getString("channelsn");
		String channel = QueryUtil.getChannelInfo(channelsn,"");
		if(StringUtil.isNotEmpty(channel)){
			String channelinfo = " HAVING channelcode IN ("+channel+")";
			sql += channelinfo;
		}
		
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		
		// 合计
		BigDecimal sums4 = new BigDecimal("0");
		BigDecimal sums20 = new BigDecimal("0");
		BigDecimal sums10 = new BigDecimal("0");
		BigDecimal sums6 = new BigDecimal("0");
		BigDecimal sums7 = new BigDecimal("0");
		BigDecimal sums3 = new BigDecimal("0");
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			sums4 = sums4.add(new BigDecimal(dt.getString(i, "sums4")));
			sums20 = sums20.add(new BigDecimal(dt.getString(i, "sums20")));
			sums10 = sums10.add(new BigDecimal(dt.getString(i, "sums10")));
			sums6 = sums6.add(new BigDecimal(dt.getString(i, "sums6")));
			sums7 = sums7.add(new BigDecimal(dt.getString(i, "sums7")));
			sums3 = sums3.add(new BigDecimal(dt.getString(i, "sums3")));
		}
		
		Object[] rowValue = {"", "合计", sums4.toString(), sums20.toString(), sums10.toString(), sums6.toString(), sums7.toString(), sums3.toString()};
		
		dt.insertRow(rowValue);
		dga.bindData(dt);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Mapx init3Dialog(Mapx params) {
		params.put("type", params.getString("type"));
		params.put("startDate", params.getString("startDate"));
		params.put("endDate", params.getString("endDate"));
		params.put("channelsn", params.getString("channelsn"));
		return params;
	}
	
	public static void dg3DataBind_dialog(DataGridAction dga) {

		String type = dga.getParams().getString("type");
		// 统计时间
		String startDate = dga.getParams().getString("startDate");
		String endDate = dga.getParams().getString("endDate");

		QueryBuilder qb = new QueryBuilder("SELECT m.id,m.createdate,m.realName,m.username,m.sex,m.birthday,m.address,m.email,m.IDNO,m.mobileNO, '' exchangeUsed, '' buyUsed,"
				+ "'' FValue,'' KValue,'' memberrank,'' RValue,'' MValue, '' gradeName, m.point, m.currentValidatePoint, m.recommendRegPoints, m.recommendBuyPoints, "
				+ " m.logindate,(POINT+currentValidatePoint) integral,MIN(c.createdate) mincreatedate,c.memberid,c.applicantName,c.applicantBirthday,m.buyCount,'0' groupCount,"
				+ " c.applicantSex,c.applicantMobile,c.applicantMail,(SELECT channelname FROM channelinfo WHERE channelcode = m.fromWap) as fromName,m.consumeAmount,if (m.vipFlag = 'Y', 'VIP', m.grade) gradeCode FROM member m " + " LEFT JOIN PrecMember c ON c.memberid = m.id WHERE m.id IS NOT NULL AND m.id <>''");

		// 会员来源渠道 ----------------
		String channelsn = dga.getParams().getString("channelsn");
		if(StringUtil.isNotEmpty(channelsn)){
			String channelinfo = " AND m.fromWap = '" + channelsn + "'";
			qb.append(channelinfo);
		} else {
			qb.append(" AND EXISTS (SELECT 1 FROM channelinfo where m.fromWap = channelcode and prop1 = 'Y')");
		}
		// 会员来源渠道 ----------------
		// 积分类型
		if ("sums4".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where m.id = memberid and source = '4' and createDate BETWEEN '" + startDate + "' and '" + endDate + "')");
		}
		if ("sums20".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where m.id = memberid and source = '20' and createDate BETWEEN '" + startDate + "' and '" + endDate + "')");
		}
		if ("sums10".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where m.id = memberid and source in('8', '9', '10', '11', '12', '13', '14', '15', '16', '17') and createDate BETWEEN '" + startDate + "' and '" + endDate + "')");
		}
		if ("sums6".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where m.id = memberid and source = '6' and createDate BETWEEN '" + startDate + "' and '" + endDate + "')");
		}
		if ("sums7".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where m.id = memberid and source = '7' and createDate BETWEEN '" + startDate + "' and '" + endDate + "')");
		}
		if ("sums3".equals(type)) {
			qb.append(" AND EXISTS (SELECT 1 FROM sdintcalendar where m.id = memberid and source = '3' and createDate BETWEEN '" + startDate + "' and '" + endDate + "')");
		}
		
		// 按照登录时间逆序排序，讲最后登录的放到最上面
		qb.append(" GROUP BY m.id ORDER BY m.logindate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		if (dt != null && dt.getRowCount() > 0) {
			DataTable graDt = new QueryBuilder("select gradeCode, gradeName from membergrade").executeDataTable();
			Map<String, String> graMap = new HashMap<String, String>();
			if (graDt != null && graDt.getRowCount() > 0) {
				int graCount = graDt.getRowCount();
				for (int i = 0; i < graCount; i++) {
					graMap.put(graDt.getString(i, 0), graDt.getString(i, 1));
				}
			}
			String memberIds = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				memberIds += ",'"+dt.getString(i, "id") + "'";

				if ("0".equals(dt.getString(i, "sex")) || "M".equals(dt.getString(i, "sex"))) {
					dt.set(i, "sex", "男");
				} else if ("1".equals(dt.getString(i, "sex")) || "F".equals(dt.getString(i, "sex"))) {
					dt.set(i, "sex", "女");
				}
				if ("0".equals(dt.getString(i, "applicantSex")) || "M".equals(dt.getString(i, "applicantSex"))) {
					dt.set(i, "applicantSex", "男");
				} else if ("1".equals(dt.getString(i, "applicantSex")) || "F".equals(dt.getString(i, "applicantSex"))) {
					dt.set(i, "applicantSex", "女");
				}
				dt.set(i, "gradeName", graMap.get(dt.getString(i, "gradeCode")));
				
			}
			
			memberIds = memberIds.substring(1);
			// 取得积分商城消耗积分
			String sql = "select MemberId,round(sum(Integral),0) from sdintcalendar where source='3' and MemberId in ("+memberIds+") group by MemberId";
			DataTable exchaDt = new QueryBuilder(sql).executeDataTable();
			Map<String, String> exchaMap = new HashMap<String, String>();
			if (exchaDt != null && exchaDt.getRowCount() > 0) {
				int rowCount = exchaDt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					exchaMap.put(exchaDt.getString(i, 0), exchaDt.getString(i, 1));
				}
			}
			// 取得购买抵值消耗积分
			sql = "select MemberId,round(sum(Integral),0) from sdintcalendar where source='2' and MemberId in ("+memberIds+") group by MemberId";
			DataTable buyDt = new QueryBuilder(sql).executeDataTable();
			Map<String, String> buyMap = new HashMap<String, String>();
			if (buyDt != null && buyDt.getRowCount() > 0) {
				int rowCount = buyDt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					buyMap.put(buyDt.getString(i, 0), buyDt.getString(i, 1));
				}
			}
			
			// 取得团单数
			sql = "select memberId,groupCount from MemberGroup where memberId in ("+memberIds+")";
			DataTable groDt = new QueryBuilder(sql).executeDataTable();
			Map<String, String> groMap = new HashMap<String, String>();
			if (groDt != null && groDt.getRowCount() > 0) {
				int rowCount = groDt.getRowCount();
				for (int i = 0; i < rowCount; i++) {
					groMap.put(groDt.getString(i, 0), groDt.getString(i, 1));
				}
			}
			
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "exchangeUsed", exchaMap.get(dt.getString(i, "id")));
				dt.set(i, "buyUsed", buyMap.get(dt.getString(i, "id")));
				dt.set(i, "groupCount", groMap.get(dt.getString(i, "id")));
			}
		}
		dga.bindData(dt);
	}
	
	private static double getMemberConfig(String MconfigCode) {
		QueryBuilder qb = new QueryBuilder("SELECT MconfigValue FROM  " + "ZDMemberRank  WHERE  MconfigCode=?");
		qb.add(MconfigCode);
		DataTable dt = qb.executeDataTable();
		double configValue = 0;
		try {
			configValue = Double.parseDouble(dt.getString(0, "MconfigValue"));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return configValue;
	}
 }