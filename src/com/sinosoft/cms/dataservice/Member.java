package com.sinosoft.cms.dataservice;

import cn.com.sinosoft.action.shop.OrderConfigNewAction;
import cn.com.sinosoft.action.shop.uw.UsersUWCheck;
import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.entity.OrderDutyFactor;
import cn.com.sinosoft.entity.OrderRiskAppFactor;
import cn.com.sinosoft.service.CpsProductBuyService;
import cn.com.sinosoft.service.impl.OrderConfigNewServiceImpl;
import cn.com.sinosoft.service.impl.SDOrderServiceImpl;
import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.Constant;

import com.activemq.ProducerMQ;
import com.alibaba.fastjson.JSON;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.orm.SchemaUtil;
import com.sinosoft.framework.servlets.TBContInsureServlet;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.jdt.CancelContService;
import com.sinosoft.jdt.InsureTransferNew;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.BuyForCustomerOldSnSchema;
import com.sinosoft.schema.InformationAppntSchema;
import com.sinosoft.schema.InformationInsuredSchema;
import com.sinosoft.schema.InsuredCompanyReturnDataSchema;
import com.sinosoft.schema.MemberGradeSchema;
import com.sinosoft.schema.MemberPrivilegesSchema;
import com.sinosoft.schema.MemberPrivilegesSet;
import com.sinosoft.schema.PolicyChangeInfoSchema;
import com.sinosoft.schema.PolicyChangeInfoSet;
import com.sinosoft.schema.SDInformationAppntSchema;
import com.sinosoft.schema.SDInformationBnfSchema;
import com.sinosoft.schema.SDInformationBnfSet;
import com.sinosoft.schema.SDInformationDutySchema;
import com.sinosoft.schema.SDInformationDutySet;
import com.sinosoft.schema.SDInformationInsuredSchema;
import com.sinosoft.schema.SDInformationInsuredSet;
import com.sinosoft.schema.SDInformationRiskTypeSchema;
import com.sinosoft.schema.SDInformationRiskTypeSet;
import com.sinosoft.schema.SDInformationSchema;
import com.sinosoft.schema.SDInsuredHealthSchema;
import com.sinosoft.schema.SDInsuredHealthSet;
import com.sinosoft.schema.SDIntCalendarSchema;
import com.sinosoft.schema.SDOrderItemOthSchema;
import com.sinosoft.schema.SDOrderItemSchema;
import com.sinosoft.schema.SDOrdersSchema;
import com.sinosoft.schema.SDRemarkSchema;
import com.sinosoft.schema.TradeInformationSchema;
import com.sinosoft.schema.TradeInformationSet;
import com.sinosoft.schema.TradeSummaryInfoSchema;
import com.sinosoft.schema.TradeSummaryInfoSet;
import com.sinosoft.schema.VIPLogSchema;
import com.sinosoft.schema.ZDMemberSchema;
import com.sinosoft.schema.memberSchema;
import com.sinosoft.schema.memberSet;
import com.sinosoft.schema.ordersSchema;
import com.sinosoft.schema.ordersSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMDutyAmntPremList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMRiskFactorList;
import com.wangjin.cms.orders.QueryOrders;
import com.wangjin.infoseeker.QueryUtil;
import com.wangjin.infoseeker.toConcealUtil;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;

import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Member extends Page {
	public static void searchUserDataBind (DataGridAction dga) {
		String searchUserName = dga.getParams().getString("SearchUserName");

		if (searchUserName == null) {
			searchUserName = "";
		}

		QueryBuilder qb = new QueryBuilder("SELECT id,username,email,mobileno,currentValidatePoint FROM member WHERE" +
				"(username LIKE '%"+ searchUserName +"%' " +
				"OR email LIKE '%"+ searchUserName +"%' " +
				"OR mobileno LIKE '%"+ searchUserName +"%')");

		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	public static void dg1DataBind(DataGridAction dga) {
		// 基准时间和统计时间
		String standardDate = dga.getParams().getString("standardDate");
		String statisticsDate = dga.getParams().getString("statisticsDate");
		
		
		
		// 会员创建开始时间
		String createDate = dga.getParams().getString("createDate");
		// 会员创建结束时间
		String endCreateDate = dga.getParams().getString("endCreateDate");


		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
		if (StringUtil.isEmpty(standardDate) || StringUtil.isEmpty(statisticsDate)) {
			standardDate = fmt.format(new Date());
			statisticsDate = "365";
		}
		String orderSn = dga.getParams().getString("orderSn");
		String wherePart = "";
		if (StringUtil.isNotEmpty(orderSn)) {
			wherePart = " LEFT JOIN sdorders s ON s.memberid=d.id ";
		}
		QueryBuilder qb = new QueryBuilder("SELECT m.id,m.createdate,m.realName,m.username,m.sex,m.birthday,m.address,m.email,m.IDNO,m.mobileNO,'' FValue,'' KValue,'' memberrank,"
				+ " c.applicantName,c.applicantBirthday,c.applicantSex,c.applicantMobile,c.applicantMail,d.integral," + " m.logindate,a.MValue,a.RValue FROM member m  "
				+ " LEFT JOIN (SELECT MIN(createdate) createdate,memberid FROM PrecMember GROUP BY memberid) b " + " ON b.memberid =m.id "
				+ " LEFT JOIN PrecMember c ON c.memberid = b.memberid AND c.createdate = b.createdate" + " LEFT JOIN PrecMember p ON  p.memberid=m.id "
				+ " LEFT JOIN (SELECT memberid,SUM(totalAmount) MValue,DATEDIFF(NOW(), MAX(createDate)) RValue FROM PrecMember WHERE createDate " + " BETWEEN DATE_SUB('" + standardDate.trim()
				+ " 00:00:00', INTERVAL " + statisticsDate.trim() + " DAY) AND '" + standardDate.trim() + " 23:59:59' GROUP BY memberid) a"
				+ " ON a.memberid=p.memberid   LEFT JOIN (SELECT (SUM(POINT)+SUM(currentValidatePoint)) integral,id FROM member  GROUP BY id) d  " + " ON d.id=p.memberid " + wherePart
				+ " where m.id is not null and m.id <>'' ");

		String applicantName = dga.getParams().getString("applicantName");
		if (StringUtil.isNotEmpty(applicantName)) {
			qb.append(" AND a.applicantName=?", applicantName.trim());
		}
		String realName = dga.getParams().getString("realName");
		if (StringUtil.isNotEmpty(realName)) {
			qb.append(" and m.realName like ?", "%" + realName.trim() + "%");
		}
		String id = dga.getParams().getString("id");
		if (StringUtil.isNotEmpty(id)) {
			qb.append("  and m.id = " + id);
		}
		String IDNO = dga.getParams().getString("IDNO");
		if (StringUtil.isNotEmpty(IDNO)) {
			qb.append(" and m.IDNO = ?", IDNO.trim());
		}
		String SEmail = dga.getParams().getString("SEmail");
		if (StringUtil.isNotEmpty(SEmail)) {
			qb.append(" and m.email = ?", SEmail.trim());
		}
		String mobileNo = dga.getParams().getString("mobileNo");
		if (StringUtil.isNotEmpty(mobileNo)) {
			qb.append(" and m.mobileNo = ?", mobileNo.trim());
		}
		String applicantMail = dga.getParams().getString("applicantMail");
		if (StringUtil.isNotEmpty(applicantMail)) {
			qb.append(" and a.applicantMail=?", applicantMail.trim());
		}
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" AND s.ordersn=?", orderSn.trim());
		}
		// 创建开始日期
		if (StringUtil.isNotEmpty(createDate)) {
			qb.append(" and m.createdate >= '" + createDate.trim() + "'");
		}

		// 创建终止日期
		if (StringUtil.isNotEmpty(endCreateDate)) {
			qb.append(" and m.createdate <= '" + endCreateDate.trim() + "'");
		}
				
						
		
		
		String rValue = dga.getParams().getString("rValue");
		if (StringUtil.isNotEmpty(rValue)) {
			String R[] = rValue.split("-");
			if (R.length > 1) {
				qb.append(" and a.RValue > " + R[0] + " and a.RValue <=" + R[1]);
			} else if (R.length == 1) {
				qb.append(" and a.RValue " + R[0]);
			}
		}
		String mValue = dga.getParams().getString("mValue");
		if (StringUtil.isNotEmpty(mValue)) {
			String M[] = mValue.split("-");
			if (M.length > 1) {
				qb.append(" and a.MValue > " + M[0] + " and a.MValue <=" + M[1]);
			} else if (M.length == 1) {
				qb.append(" and a.MValue " + M[0]);
			}
		}
		// 按照登录时间逆序排序，讲最后登录的放到最上面
		qb.append(" GROUP BY m.id ORDER BY m.logindate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		// 相应R,F值
		int r;
		int m;
		int days;
		double resulet;
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				// 购买时间间隔
				r = 0;
				days = 0;
				if (StringUtil.isNotEmpty(dt.getString(i, "RValue"))) {
					days = Integer.parseInt(dt.getString(i, "RValue"));
				} else {
					dt.set(i, "RValue", 0);
				}
				// 累计金额
				m = 0;
				resulet = 0.0;
				if (StringUtil.isNotEmpty(dt.getString(i, "MValue"))) {
					resulet = Double.parseDouble(dt.getString(i, "MValue"));
				} else {
					dt.set(i, "MValue", 0);
				}

				if (days <= getMemberConfig("member-R1") && days > getMemberConfig("member-R2")) {
					r = 1;
				} else if (days > getMemberConfig("member-R3") && days <= getMemberConfig("member-R2")) {
					r = 2;
				} else if (days > getMemberConfig("member-R4") && days <= getMemberConfig("member-R3")) {
					r = 3;
				} else if (days > getMemberConfig("member-R5") && days <= getMemberConfig("member-R4")) {
					r = 4;
				} else if (days > 0 && days <= getMemberConfig("member-R5")) {
					r = 5;
				}

				if (resulet > getMemberConfig("member-M1") && resulet <= getMemberConfig("member-M2")) {
					m = 1;
				} else if (resulet > getMemberConfig("member-M2") && resulet <= getMemberConfig("member-M3")) {
					m = 2;
				} else if (resulet > getMemberConfig("member-M3") && resulet <= getMemberConfig("member-M4")) {
					m = 3;
				} else if (resulet > getMemberConfig("member-M4") && resulet <= getMemberConfig("member-M5")) {
					m = 4;
				} else if (resulet > getMemberConfig("member-M5")) {
					m = 5;
				}

				// 计算订单次数
				String sqlPart = "";
				String sqlAll = "SELECT * FROM PrecMember WHERE memberid='" + dt.getString(i, "id") + "' and createdate " + " BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL "
						+ statisticsDate.trim() + " DAY) AND '" + standardDate.trim() + " 23:59:59' ";
				String sqlGroup = " group by DATE_FORMAT(createdate,'%Y-%m-%d')  ORDER BY createdate ASC";
				QueryBuilder sqlqb = new QueryBuilder(sqlAll + sqlGroup);
				DataTable dts = sqlqb.executeDataTable();
				int count = 0;
				if (dts != null && dts.getRowCount() > 0) {
					count = 1;
					if (dts.getRowCount() > 1) {
						for (int j = 0; j < dts.getRowCount() - 1; j++) {
							String date1 = dts.getString(j, "createdate");
							if (date1 != null && date1 != "") {
								String temp[] = date1.split(" ");
								date1 = temp[0];
							}
							try {
								QueryBuilder qbDate = new QueryBuilder("SELECT DATE_ADD(?, INTERVAL ? DAY) dateN FROM DUAL");
								qbDate.add(date1);
								qbDate.add(getMemberConfig("BuyTimes"));
								DataTable dtDate = qbDate.executeDataTable();

								if (dtDate != null) {
									sqlPart = " AND createdate like '" + dtDate.getString(0, "dateN") + "%' ";
									DataTable dtSpa = new QueryBuilder(sqlAll + sqlPart + sqlGroup).executeDataTable();
									if (dtSpa != null && dtSpa.getRowCount() > 0) {
										count++;
									}
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								logger.error(e.getMessage(), e);
							}

						}
					}

				}
				dt.set(i, "FValue", count);
				int f = 0;
				if (getMemberConfig("member-F1") < count && count <= getMemberConfig("member-F2")) {
					f = 1;
				} else if (getMemberConfig("member-F2") < count && count <= getMemberConfig("member-F3")) {
					f = 2;
				} else if (getMemberConfig("member-F3") < count && count <= getMemberConfig("member-F4")) {
					f = 3;
				} else if (getMemberConfig("member-F4") < count && count <= getMemberConfig("member-F5")) {
					f = 4;
				} else if (count > getMemberConfig("member-F5")) {
					f = 5;
				}

				try {
					// 计算公式K=1R+3F+4M
					double kV = 1 * r + 3 * f + 4 * m;
					dt.set(i, "KValue", kV);
					String memberRank = "";
					if (kV <= getMemberConfig("member-K1")) {
						memberRank = "一星会员";
					} else if (kV > getMemberConfig("member-K1") && kV <= getMemberConfig("member-K2")) {
						memberRank = "二星会员";
					} else if (kV > getMemberConfig("member-K2") && kV <= getMemberConfig("member-K3")) {
						memberRank = "三星会员";
					} else if (kV > getMemberConfig("member-K3") && kV <= getMemberConfig("member-K4")) {
						memberRank = "四星会员";
					} else if (kV > getMemberConfig("member-K4") && kV <= getMemberConfig("member-K5")) {
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
			}
		}

		dga.bindData(dt);
	}

	public static void dg2DataBind(DataGridAction dga) {

		// 基准时间和统计时间
		String standardDate = dga.getParams().getString("standardDate");
		String statisticsDate = dga.getParams().getString("statisticsDate");

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd hh:ss:mm");
		if (StringUtil.isEmpty(standardDate) || StringUtil.isEmpty(statisticsDate)) {
			standardDate = fmt.format(new Date());
			statisticsDate = "365";
		}
		String orderSn = dga.getParams().getString("orderSn");
		String wherePart = "";
		if (StringUtil.isNotEmpty(orderSn)) {
			wherePart = " LEFT JOIN sdorders s ON s.memberid=m.id ";
		}
		QueryBuilder qb = new QueryBuilder("SELECT m.id,m.createdate,m.realName,m.username,m.sex,m.birthday,m.address,m.email,m.IDNO,m.mobileNO, '' exchangeUsed, '' buyUsed,"
				+ "'' FValue,'' KValue,'' memberrank,'' RValue,'' MValue, '' gradeName, m.point, m.currentValidatePoint, m.recommendRegPoints, m.recommendBuyPoints, "
				+ " m.logindate,(POINT+currentValidatePoint) integral,MIN(c.createdate) mincreatedate,c.memberid,c.applicantName,c.applicantBirthday,m.buyCount,'0' groupCount,"
				+ " c.applicantSex,c.applicantMobile,c.applicantMail,m.fromWap,(SELECT channelname FROM channelinfo WHERE channelcode = m.fromWap) as fromName,m.consumeAmount,if (m.vipFlag = 'Y', 'VIP', m.grade) gradeCode FROM member m " + " LEFT JOIN PrecMember c ON c.memberid = m.id " + wherePart + "WHERE m.id IS NOT NULL AND m.id <>''");

		String applicantName = dga.getParams().getString("applicantName");
		if (StringUtil.isNotEmpty(applicantName)) {
			qb.append(" AND c.applicantName=?", applicantName.trim());
		}
		String realName = dga.getParams().getString("realName");
		if (StringUtil.isNotEmpty(realName)) {
			qb.append(" and m.realName like ?", "%" + realName.trim() + "%");
		}
		String id = dga.getParams().getString("id");
		if (StringUtil.isNotEmpty(id)) {
			qb.append("  and m.id = " + id);
		}
		String IDNO = dga.getParams().getString("IDNO");
		if (StringUtil.isNotEmpty(IDNO)) {
			qb.append(" and m.IDNO = ?", IDNO.trim());
		}
		String SEmail = dga.getParams().getString("SEmail");
		if (StringUtil.isNotEmpty(SEmail)) {
			qb.append(" and m.email = ?", SEmail.trim());
		}
		String mobileNo = dga.getParams().getString("mobileNo");
		if (StringUtil.isNotEmpty(mobileNo)) {
			qb.append(" and m.mobileNo = ?", mobileNo.trim());
		}
		String applicantMail = dga.getParams().getString("applicantEmail");
		if (StringUtil.isNotEmpty(applicantMail)) {
			qb.append(" and c.applicantMail=?", applicantMail.trim());
		}
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" AND s.ordersn=?", orderSn.trim());
		}
		String fromWap  = dga.getParams().getString("fromWap");
		if(StringUtil.isNotEmpty(fromWap)){
			// 来源淘宝
			if ("1".equals(fromWap)) {
				qb.append(" AND m.fromWap like 'tb%'");
			} else if ("2".equals(fromWap)) {// 主站除淘宝外
				qb.append(" AND (m.fromWap not like 'tb%' or m.fromWap is null or m.fromWap = '')");
			}
		}

		// 会员来源渠道 ----------------
		String channelsn = dga.getParams().getString("channelsn");
		String tmOtherChannelsn = "";
		if(StringUtil.isNotEmpty(channelsn)){
			if(channelsn.indexOf("tb_ht_wx") != -1){
				tmOtherChannelsn += ",'tb_ht_wx'";
			}
			if(channelsn.indexOf("tb_my_wx") != -1){
				tmOtherChannelsn += ",'tb_my_wx'";
			}
			if(channelsn.indexOf("tb_ht_other") != -1){
				tmOtherChannelsn += ",'tb_ht_other'";
			}
			if(channelsn.indexOf("tb_my_other") != -1){
				tmOtherChannelsn += ",'tb_my_other'";
			}
		}
		String channel = QueryUtil.getChannelInfo(channelsn,"");
		String channelQueryParams = "";
		if(StringUtil.isNotEmpty(channel)){
			if(StringUtil.isNotEmpty(tmOtherChannelsn)){
				channelQueryParams = " AND (EXISTS (SELECT 1 FROM ChannelInfo WHERE m.fromWap = channelcode and m.fromWap IN ("+channel+")) or m.fromWap IN ("+tmOtherChannelsn.substring(1)+"))";
			}else{
				channelQueryParams = " AND EXISTS (SELECT 1 FROM ChannelInfo WHERE m.fromWap = channelcode and m.fromWap IN ("+channel+"))";
			}
		}else if(StringUtil.isNotEmpty(tmOtherChannelsn)){
			channelQueryParams = " AND m.fromWap IN ("+tmOtherChannelsn.substring(1)+")";
		}
		qb.append(channelQueryParams);
		// 会员来源渠道 ----------------
		// 会员等级-------------------
		String vipCheckbox = dga.getParams().getString("vipCheckbox");
		String checkvip = dga.getParams().getString("checkvip");
		
		if(StringUtil.isNotEmpty(vipCheckbox) && "1".equals(vipCheckbox)){
			qb.append(" AND FIND_IN_SET(if (m.vipFlag = 'Y', 'VIP', m.grade), '" + checkvip + "') ");
		}
		// 会员等级-------------------
		
		// R 区间 -------------
		String RStart = dga.getParams().getString("RStart");
		String REnd = dga.getParams().getString("REnd");
		if (StringUtil.isNotEmpty(RStart) || StringUtil.isNotEmpty(REnd)) {
			if (StringUtil.isEmpty(RStart)) {
				RStart = "0";
			}
			if (StringUtil.isEmpty(REnd)) {
				REnd = statisticsDate;
			}

			if (StringUtil.isNotEmpty(RStart)) {
				qb.append(" AND (SELECT DATEDIFF(str_to_date('" + standardDate.trim() + " 00:00:00', '%Y-%m-%d %H:%i:%s'),MAX(createDate)) FROM PrecMember WHERE memberid = m.id) >= '" + RStart + "'");
			}
			if(StringUtil.isNotEmpty(REnd)) {
				qb.append(" AND (SELECT DATEDIFF(str_to_date('" + standardDate.trim() + " 00:00:00', '%Y-%m-%d %H:%i:%s'),MAX(createDate)) FROM PrecMember WHERE memberid = m.id) <= '" + REnd + "'");
			}
		}
		
		// R 区间 -------------
		
		// M 区间 -------------
		String MStart = dga.getParams().getString("MStart");
		String MEnd = dga.getParams().getString("MEnd");
		if (StringUtil.isNotEmpty(MStart) || StringUtil.isNotEmpty(MEnd)) {
			if (StringUtil.isEmpty(RStart)) {
				RStart = "0";
			}
			if (StringUtil.isEmpty(REnd)) {
				REnd = statisticsDate;
			}
			if (StringUtil.isNotEmpty(MStart)) {
				qb.append(" AND (SELECT SUM(totalAmount) FROM PrecMember WHERE memberid = m.id AND createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY)) >= '" + MStart + "'");
			}
			if(StringUtil.isNotEmpty(MEnd)) {
				qb.append(" AND (SELECT SUM(totalAmount) FROM PrecMember WHERE memberid = m.id AND createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY)) <= '" + MEnd + "'");
			}
		}
		// M 区间 -------------
		
		// F 区间 -------------
		String FStart = dga.getParams().getString("FStart");
		String FEnd = dga.getParams().getString("FEnd");
		if (StringUtil.isNotEmpty(FStart) || StringUtil.isNotEmpty(FEnd)) {
			if (StringUtil.isEmpty(RStart)) {
				RStart = "0";
			}
			if (StringUtil.isEmpty(REnd)) {
				REnd = statisticsDate;
			}
			if (StringUtil.isNotEmpty(FStart)) {
				qb.append(" AND (SELECT COUNT(DISTINCT DATE_FORMAT(createdate, '%Y-%m-%d'))  FROM PrecMember WHERE memberid = m.id AND createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY)) >= '" + FStart + "'");
			}
			if(StringUtil.isNotEmpty(FEnd)) {
				qb.append(" AND (SELECT COUNT(DISTINCT DATE_FORMAT(createdate, '%Y-%m-%d'))  FROM PrecMember WHERE memberid = m.id AND createDate BETWEEN DATE_SUB('" + standardDate.trim() + " 00:00:00', INTERVAL " + REnd + " DAY) AND DATE_SUB('" + standardDate.trim() + " 23:59:59', INTERVAL " + RStart + " DAY)) <= '" + FEnd + "'");
			}
		}
		
		// 会员创建开始时间
		String createDate = dga.getParams().getString("createDate")+" 00:00:00";
		// 会员创建结束时间
		String endCreateDate = dga.getParams().getString("endCreateDate")+" 23:59:59";

		// 创建开始日期
		if (StringUtil.isNotEmpty(createDate)) {
			qb.append(" and m.createdate >= '" + createDate.trim() + "'");
		}

		// 创建终止日期
		if (StringUtil.isNotEmpty(endCreateDate)) {
			qb.append(" and m.createdate <= '" + endCreateDate.trim() + "'");
		}
			
		
		
		// F 区间 -------------
		// 累计实际支付金额 ---------------
		String amountStart = dga.getParams().getString("amountStart");
		String amountEnd = dga.getParams().getString("amountEnd");
		if (StringUtil.isNotEmpty(amountStart)) {
			qb.append(" AND CONVERT(m.consumeAmount, DECIMAL) >= '" + amountStart + "'");
		}
		if (StringUtil.isNotEmpty(amountEnd)) {
			qb.append(" AND CONVERT(m.consumeAmount, DECIMAL) <= '" + amountEnd + "'");
		}
		// 累计实际支付金额 ---------------
		// 累计实际购买次数 ---------------
		String countStart = dga.getParams().getString("countStart");
		String countEnd = dga.getParams().getString("countEnd");
		if (StringUtil.isNotEmpty(countStart)) {
			qb.append(" AND m.buyCount >= '" + countStart + "'");
		}
		if (StringUtil.isNotEmpty(countEnd)) {
			qb.append(" AND m.buyCount <= '" + countEnd + "'");
		}
		// 累计实际购买次数 ---------------
		// 可用积分 ---------------
		String pointsStart = dga.getParams().getString("pointsStart");
		String pointsEnd = dga.getParams().getString("pointsEnd");
		if (StringUtil.isNotEmpty(pointsStart)) {
			qb.append(" AND m.currentValidatePoint >= '" + pointsStart + "'");
		}
		if (StringUtil.isNotEmpty(pointsEnd)) {
			qb.append(" AND m.currentValidatePoint <= '" + pointsEnd + "'");
		}
		// 可用积分 ---------------
		// 团购次数 ---------------
		String groupStart = dga.getParams().getString("groupStart");
		String groupEnd = dga.getParams().getString("groupEnd");
		if (StringUtil.isNotEmpty(groupStart) || StringUtil.isNotEmpty(groupEnd)) {
			if (StringUtil.isEmpty(groupStart)) {
				qb.append(" AND EXISTS (SELECT 1 FROM MemberGroup WHERE memberid = m.id AND groupCount <= '" + groupEnd + "')");
			} else if (StringUtil.isEmpty(groupEnd)) {
				qb.append(" AND EXISTS (SELECT 1 FROM MemberGroup WHERE memberid = m.id AND groupCount >= '" + groupStart + "')");
			} else {
				qb.append(" AND EXISTS (SELECT 1 FROM MemberGroup WHERE memberid = m.id AND groupCount >= '" + groupStart + "' AND groupCount <= '" + groupEnd + "')");
			}
		}
		
		// 团购次数 ---------------
		
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
				String fromWapCode = dt.get(i).getString("fromWap");
				if("tb_ht_wx".equals(fromWapCode)){
					dt.set(i, "fromName", "淘宝（微信已激活）");
				}else if("tb_my_wx".equals(fromWapCode)){
					dt.set(i, "fromName", "支付宝（微信已激活）");
				}else if("tb_ht_other".equals(fromWapCode)){
					dt.set(i, "fromName", "淘宝（其他已激活）");
				}else if("tb_my_other".equals(fromWapCode)){
					dt.set(i, "fromName", "支付宝（其他已激活");
				}
			}
		}
		dga.bindData(dt);

	}

	public static double getMemberConfig(String MconfigCode) {
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

	public static void dg1DataBind2(DataGridAction dga) {
		String id = dga.getParams().getString("id");
		String SEmail = dga.getParams().getString("SEmail");
		QueryBuilder qb = new QueryBuilder("select * from member e where id is not null and id <>'' ");
		String applicantName = dga.getParams().getString("applicantName");
		if (StringUtil.isNotEmpty(applicantName)) {
			qb.append(" and exists ( select 'X' from orders d where e.id =memberid and  exists " + "( select 'X' from orderitem c where d.id = order_id and exists "
					+ " ( select 'X' from information b where c.id = orderitem_id and exists " + "( select 'X' from informationappnt a where information_id=b.id and applicantName = ?)))) ",
					applicantName.trim());
		}
		String realName = dga.getParams().getString("realName");
		if (StringUtil.isNotEmpty(realName)) {
			qb.append(" and realName like ?", "%" + realName.trim() + "%");
		}
		if (StringUtil.isNotEmpty(id)) {
			// qb.append(" and UserName like ?", "%" + SearchUserName.trim()+
			// "%");
			qb.append("  and (mobileno='" + id.trim() + "' or username='" + id.trim() + "' or email='" + id.trim() + "')");
		}
		String IDNO = dga.getParams().getString("IDNO");
		if (StringUtil.isNotEmpty(IDNO)) {
			qb.append(" and IDNO = ?", IDNO.trim());
		}
		if (StringUtil.isNotEmpty(SEmail)) {
			qb.append(" and email = ?", SEmail.trim());
		}
		String mobileNo = dga.getParams().getString("mobileNo");
		if (StringUtil.isNotEmpty(mobileNo)) {
			qb.append(" and mobileNo = ?", mobileNo.trim());
		}
		String telephoneNO = dga.getParams().getString("telephoneNO");
		if (StringUtil.isNotEmpty(telephoneNO)) {
			qb.append(" and telephoneNO = ?", telephoneNO.trim());
		}
		String policyNumber = dga.getParams().getString("policyNumber");
		if (StringUtil.isNotEmpty(policyNumber)) {
			qb.append(" and id in (select memberid from orders where policyNumber = ?)", policyNumber.trim());
		}
		String orderSn = dga.getParams().getString("orderSn");
		if (StringUtil.isNotEmpty(orderSn)) {
			qb.append(" and id in (select memberid from orders where orderSn = ?)", orderSn.trim());
		}
		String startCreateDate = dga.getParams().getString("startCreateDate");
		String startCreateTime = dga.getParams().getString("startCreateTime");
		if (StringUtil.isNotEmpty(startCreateDate) && StringUtil.isEmpty(startCreateTime)) {
			qb.append(" and createDate >= ?", startCreateDate.trim());
		}
		if (StringUtil.isNotEmpty(startCreateDate) && StringUtil.isNotEmpty(startCreateTime)) {
			qb.append(" and createDate >= ?", startCreateDate.trim() + " " + startCreateTime.trim());
		}
		String endCreateDate = dga.getParams().getString("endCreateDate");
		String endCreateTime = dga.getParams().getString("endCreateTime");
		if (StringUtil.isNotEmpty(endCreateDate) && StringUtil.isEmpty(endCreateTime)) {
			qb.append(" and createDate <= ?", endCreateDate.trim());
		}
		if (StringUtil.isNotEmpty(endCreateDate) && StringUtil.isNotEmpty(endCreateTime)) {
			qb.append(" and createDate <= ?", endCreateDate.trim() + " " + endCreateTime.trim());
		}

		// 按照登录时间逆序排序，讲最后登录的放到最上面 add by fhz 20121210
		qb.append("order by logindate desc");

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("sex", HtmlUtil.codeToMapx("Gender"));
		dt.decodeColumn("VIPType", HtmlUtil.codeToMapx("Member.Type"));
		// 是否启用
		DataTable dt1 = new QueryBuilder("select CodeValue as isAccountEnabled,CodeName from zdcode where codetype =?", "isAccountEnabled").executeDataTable();
		dt.decodeColumn("isAccountEnabled", dt1.toMapx("isAccountEnabled", "CodeName"));
		/*
		 * //等级 DataTable dt2 = new QueryBuilder(
		 * "select id as memberRank_id ,name from memberRank ")
		 * .executeDataTable(); dt.decodeColumn("memberRank_id",
		 * dt2.toMapx("memberRank_id", "name"));
		 */
		dt.decodeColumn("IDType", CacheManager.getMapx("Code", "member.IDType"));

		dga.bindData(dt);
	}

	/**
	 * 初始化会员查询页面.
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx init(Mapx params) {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		params.put("standardDate", fmt.format(date));
		params.put("createDate", com.sinosoft.lis.pubfun.PubFun.getPrevMonthDay(getFormat("yyyy-MM-dd", new Date())));
		params.put("endCreateDate", getFormat("yyyy-MM-dd", new Date()));

		params.put("statisticsDate", "365");
		return params;
	}
 
	private static String getFormat(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public void integralDetail(DataGridAction dga) {
		String memberid = dga.getParam("id");
		QueryBuilder qb = new QueryBuilder("SELECT  createdate,createtime,Manner,STATUS,Source,businessid,integral,"
				+ "(SELECT codename FROM zdcode z WHERE Manner=z.codevalue AND z.codetype='Manner' ) MannerName,"
				+ "(SELECT codename FROM zdcode z WHERE STATUS=z.codevalue AND z.codetype='Status' ) StatusName,"
				+ "(SELECT codename FROM zdcode z WHERE Source=z.codevalue AND z.codetype='Source' ) SourceName " + " FROM SDIntCalendar WHERE memberid='" + memberid
				+ "' AND STATUS IN ('0','1') AND integral>0  ORDER BY ID DESC");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "SourceName").equals("购买产品")) {
					dt.set(i, "businessid", "+订单号：" + dt.getString(i, "businessid"));
				}
			}
		}
		dga.bindData(dt);
	}

	// 得到订单信息
	public void orderInquery(DataGridAction dga) {

		// 得到查询SQL
		String querySql = "select b.id, b.orderSn ,b.productName,ROUND(b.totalAmount,2) as totalAmount,b.createDate," + " if(b.orderStatus=7,ROUND(b.totalAmount,2),0) as notfee,"
				+ "(select c.CodeName from zdcode c where CodeType='orderstatus' and c.codevalue=b.orderstatus) as orderstatusname ,b.orderstatus,m.effective,"
				+ " (select case when username!='' then username when mobileno!='' then mobileno ELSE email end memberID from member where id=b.memberid) as mid "
				+ "from orders b ,information m , orderitem n where m.orderitem_id = n.id and n.order_id = b.id and b.id is not null ";

		// 查询条件 fhz
		StringBuffer wherePart = new StringBuffer();
		// 订单号
		String orderSn = dga.getParams().getString("orderSn");

		if (StringUtil.isNotEmpty(orderSn)) {
			wherePart.append(" and b.orderSn like '%" + orderSn.trim() + "%'");
		}
		// 创建开始日期

		String createDate = dga.getParams().getString("createDate");
		if (StringUtil.isNotEmpty(createDate)) {
			wherePart.append(" and date(b.createDate) >= '" + createDate.trim() + "'");
		}

		// 创建终止日期
		String endCreateDate = dga.getParams().getString("endCreateDate");
		if (StringUtil.isNotEmpty(endCreateDate)) {
			wherePart.append(" and date(b.createDate) <= '" + endCreateDate.trim() + "'");
		}
		
		
		
	
		
		
		// 订单状态
		String OrderStatus = dga.getParams().getString("OrderStatus");
		if (StringUtil.isNotEmpty(OrderStatus)) {
			wherePart.append(" and b.OrderStatus = " + OrderStatus.trim() + "");
		}
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			wherePart.append(" and b.productName like '%" + productName.trim() + "%'");
		}

		// 会员ID 如果此时是会员查询页面跳转过来，那么此时要根据memberid来查询
		String memberid = dga.getParams().getString("memberid");
		// 会员ID 可能为 用户名 手机号 邮箱
		String mid = dga.getParams().getString("mid");
		if (StringUtil.isNotEmpty(mid) || StringUtil.isNotEmpty(memberid)) {
			wherePart.append(" and exists (select 'X' from member d where d.id =b.memberid  ");
			if (StringUtil.isNotEmpty(mid)) {
				wherePart.append(" and (d.mobileno='" + mid.trim() + "' or d.username='" + mid.trim() + "' or d.email='" + mid.trim() + "')");
			}
			if (StringUtil.isNotEmpty(memberid)) {

				wherePart.append(" and d.id= '" + memberid.trim() + "'");
			}
			wherePart.append(")");

		}

		// 投保人姓名 手机号 E-mail 证件号码 关联 informationappnt表
		String applicantName = dga.getParams().getString("applicantName");
		String shipMobile = dga.getParams().getString("shipMobile");
		String email = dga.getParams().getString("email");
		String idNO = dga.getParams().getString("idNO");
		if (StringUtil.isNotEmpty(applicantName) || StringUtil.isNotEmpty(shipMobile) || StringUtil.isNotEmpty(email) || StringUtil.isNotEmpty(idNO)) {
			String condition = "";

			if (StringUtil.isNotEmpty(applicantName)) {
				condition += " and applicantName ='" + applicantName.trim() + "'";
			}
			if (StringUtil.isNotEmpty(email)) {
				condition += " and applicantMail ='" + email.trim() + "'";
			}
			if (StringUtil.isNotEmpty(shipMobile)) {
				condition += " and applicantMobile ='" + shipMobile.trim() + "'";
			}
			if (StringUtil.isNotEmpty(idNO)) {
				condition += " and applicantIdentityId ='" + idNO.trim() + "'";
			}

			wherePart.append(" and exists ( select 'X' from orderitem e where b.id = e.order_id " + "and exists ( select 'X' from information f where e.id = f.orderitem_id "
					+ "and exists ( select 'X' from informationappnt g where  f.id=g.information_id " + condition + ")))");
		}

		wherePart.append(" order by b.createdate desc,b.ordersn desc");

		// 测试输出结果SQL
		// System.out.println("fhzSQL"+querySql+wherePart.toString());
		QueryBuilder qb = new QueryBuilder(querySql + wherePart.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("KID");
		if (dt != null) {
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			// String KID = dga.getParams().getString("KID");
			for (int i = 0; i < dt.getRowCount(); i++) {

				dt.set(i, "KID", StringUtil.md5Hex(PubFun.getKeyValue() + dt.getString(i, "orderSn")));
			}
		}

		dga.setTotal(qb);
		dga.bindData(dt);
	}

	// 撤单查询订单信息
	public void orderCancel(DataGridAction dga) {

		// 得到查询SQL 
		String special_pro=Config.getValue("KL_SURRENDER_PRO");//昆仑支持未承保撤单的产品zdconfig中配置
		String querySql = "select a.channelsn,c.ordersn as orderSn, c.insuredSn as insuredSn,b.productName,ROUND(d.timePrem,2) as totalAmount,DATE_FORMAT(a.createDate,'%Y-%m-%d') as createDate ,"
				+ "(select e.CodeName from zdcode e where CodeType='AppStatus' and e.codevalue=d.appStatus) as appStatusName ,"
				+ "d.appStatus, DATE_FORMAT(b.startDate,'%Y-%m-%d %T') as startDate,DATE_FORMAT(b.endDate,'%Y-%m-%d %T') as endDate,"
				+ " IF (a.channelSn='b2b_app', a.memberid, (SELECT CONCAT(IFNULL(email,''),'/',IFNULL(mobileno,'')) FROM member WHERE id=a.memberid)) as mid , "
				+ "sa.applicantName as applicantName ,c.recognizeeName as recognizeeName, c.recognizeeIdentityId as recognizeeIdentityId,"
				+ "DATE_FORMAT(d.cancelDate,'%Y-%m-%d') as cancelDate ,d.policyNo as policyNo, b.productId, fm.RiskPeriod, fm.Hesitation "
				+ "from SDOrders a ,sdinformation b left join fmriskb fm on b.productId=fm.riskcode, sdinformationinsured c ,sdinformationRiskType d,SDInformationAppnt sa "
				+ "where (case when locate(b.productId,'"+special_pro+"')>0 then ((a.orderstatus = 4 or a.orderstatus = 5 or a.orderstatus >= 7) and c.uwCheckFlag = 'Y' ) else a.orderstatus >= 7 end) and a.ordersn=b.ordersn and b.informationSn=c.informationSn and b.informationSn=sa.informationSn "
				+ "and c.recognizeeSn = d.recognizeeSn and c.insuredSn is not null and a.channelsn != 'jfsc' "
				+ "and a.channelsn not in (SELECT cb.channelcode FROM channelinfo ca, channelinfo cb  WHERE ca.channelcode = 'jfsc' AND ca.InnerChannelCode = cb.ParentInnerChanelCode)";

		StringBuffer wherePart = new StringBuffer();
		// 订单号
		String ordersn = dga.getParams().getString("ordersn");

		if (StringUtil.isNotEmpty(ordersn)) {
			wherePart.append(" and c.ordersn = '" + ordersn.trim() + "'");
		}
		// 订单号
		String policyNo = dga.getParams().getString("policyNo");

		if (StringUtil.isNotEmpty(policyNo)) {
			wherePart.append(" and d.policyNo = '" + policyNo + "'");
		}
		// 创建开始日期

		String createDate = dga.getParams().getString("createDate");
		if (StringUtil.isNotEmpty(createDate)) {
			wherePart.append(" and date(a.createDate) >= '" + createDate.trim() + "'");
		}

		// 创建终止日期
		String endCreateDate = dga.getParams().getString("endCreateDate");
		if (StringUtil.isNotEmpty(endCreateDate)) {
			wherePart.append(" and date(a.createDate) <= '" + endCreateDate.trim() + "'");
		}
		// 订单状态
		String appStatus = dga.getParams().getString("appStatus");
		if (StringUtil.isNotEmpty(appStatus) && !"-1".equals(appStatus)) {
			wherePart.append(" and d.appStatus = " + appStatus.trim() + "");
		}
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			wherePart.append(" and b.productName like '%" + productName.trim() + "%'");
		}

		// 会员ID 如果此时是会员查询页面跳转过来，那么此时要根据memberid来查询
		String memberid = dga.getParams().getString("memberid");
		// 会员ID 可能为 用户名 手机号 邮箱
		String mid = dga.getParams().getString("mid");
		if (StringUtil.isNotEmpty(mid) || StringUtil.isNotEmpty(memberid)) {
			wherePart.append(" and exists (select 'X' from member f where f.id =a.memberid  ");
			if (StringUtil.isNotEmpty(mid)) {
				wherePart.append(" and (f.mobileno='" + mid.trim() + "' or f.username='" + mid.trim() + "' or f.email='" + mid.trim() + "')");
			}
			if (StringUtil.isNotEmpty(memberid)) {

				wherePart.append(" and f.id= '" + memberid.trim() + "'");
			}
			wherePart.append(")");

		}

		// 被保人姓名 手机号 E-mail 证件号码 关联 informationappnt表
		String insuredname = dga.getParams().getString("insuredname");
		String mobile = dga.getParams().getString("mobile");
		String email = dga.getParams().getString("email");
		String idNO = dga.getParams().getString("idNO");
		if (StringUtil.isNotEmpty(insuredname) || StringUtil.isNotEmpty(mobile) || StringUtil.isNotEmpty(email) || StringUtil.isNotEmpty(idNO)) {
			String condition = "";

			if (StringUtil.isNotEmpty(insuredname)) {
				condition += " and c.recognizeeName = '" + insuredname.trim() + "'";
			}
			if (StringUtil.isNotEmpty(email)) {
				condition += " and c.recognizeeMail = '" + email.trim() + "'";
			}
			if (StringUtil.isNotEmpty(mobile)) {
				condition += " and c.recognizeeMobile = '" + mobile.trim() + "'";
			}
			if (StringUtil.isNotEmpty(idNO)) {
				condition += " and c.recognizeeIdentityId = '" + idNO.trim() + "' ";
			}

			wherePart.append(condition);
		}

		wherePart.append(" order by a.createdate desc,c.ordersn desc");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(querySql + wherePart.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("KID");
		if (dt != null) {
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			// String KID = dga.getParams().getString("KID");
			for (int i = 0; i < dt.getRowCount(); i++) {

				dt.set(i, "KID", StringUtil.md5Hex(PubFun.getKeyValue() + dt.getString(i, "orderSn")));
			}
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			String str = (String)dt.get(i, 12);
			if (StringUtil.isEmpty(str)) {
			}else if (str.length() >=6) {
				String mStr = str.substring(0, str.length() - 6) + "******";
				dt.set(i, 12,mStr);
			}else if(str.length() >=3 && str.length() <6){
				String mStr = str.substring(0, str.length() - 3) + "***";
				dt.set(i, 12,mStr);
			}else{
				
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	// 撤单查询订单信息
	public void tbOrderCancel(DataGridAction dga) {

		// 得到查询SQL
		String querySql = "select c.ordersn as orderSn, c.insuredSn as insuredSn,b.productName,ROUND(d.timePrem,2) as totalAmount,DATE_FORMAT(a.createDate,'%Y-%m-%d') as createDate ,"
				+ "(select e.CodeName from zdcode e where CodeType='AppStatus' and e.codevalue=d.appStatus) as appStatusName ,"
				+ "d.appStatus, DATE_FORMAT(b.startDate,'%Y-%m-%d %T') as startDate,DATE_FORMAT(b.endDate,'%Y-%m-%d %T') as endDate,"
				+ " (select case when username!='' then username when mobileno!='' then mobileno ELSE email end memberID from member where id=a.memberid) as mid , "
				+ "sa.applicantName as applicantName ,c.recognizeeName as recognizeeName, c.recognizeeIdentityId as recognizeeIdentityId,"
				+ "DATE_FORMAT(d.cancelDate,'%Y-%m-%d') as cancelDate ,d.policyNo as policyNo "
				+ "from SDOrders a ,sdinformation b , sdinformationinsured c ,sdinformationRiskType d,SDInformationAppnt sa "
				+ "where (a.orderstatus=5 or a.orderstatus=9) and a.ordersn=b.ordersn and b.informationSn=c.informationSn and b.informationSn=sa.informationSn "
				+ "and c.recognizeeSn = d.recognizeeSn and c.insuredSn is not null and (d.policyNo = '' or d.policyNo is null) and c.ordersn like '%TB%' ";

		StringBuffer wherePart = new StringBuffer();
		// 订单号
		String ordersn = dga.getParams().getString("ordersn");

		if (StringUtil.isNotEmpty(ordersn)) {
			wherePart.append(" and c.ordersn like '%" + ordersn.trim() + "%'");
		}
		// 创建开始日期

		String createDate = dga.getParams().getString("createDate");
		if (StringUtil.isNotEmpty(createDate)) {
			wherePart.append(" and date(a.createDate) >= '" + createDate.trim() + "'");
		}

		// 创建终止日期
		String endCreateDate = dga.getParams().getString("endCreateDate");
		if (StringUtil.isNotEmpty(endCreateDate)) {
			wherePart.append(" and date(a.createDate) <= '" + endCreateDate.trim() + "'");
		}
		// 订单状态
		String orderStatus = dga.getParams().getString("orderStatus");
		if (StringUtil.isNotEmpty(orderStatus) && !"-1".equals(orderStatus)) {
			wherePart.append(" and a.orderStatus = " + orderStatus.trim() + "");
		}
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			wherePart.append(" and b.productName like '%" + productName.trim() + "%'");
		}

		// 会员ID 如果此时是会员查询页面跳转过来，那么此时要根据memberid来查询
		String memberid = dga.getParams().getString("memberid");
		// 会员ID 可能为 用户名 手机号 邮箱
		String mid = dga.getParams().getString("mid");
		if (StringUtil.isNotEmpty(mid) || StringUtil.isNotEmpty(memberid)) {
			wherePart.append(" and exists (select 'X' from member f where f.id =a.memberid  ");
			if (StringUtil.isNotEmpty(mid)) {
				wherePart.append(" and (f.mobileno='" + mid.trim() + "' or f.username='" + mid.trim() + "' or f.email='" + mid.trim() + "')");
			}
			if (StringUtil.isNotEmpty(memberid)) {

				wherePart.append(" and f.id= '" + memberid.trim() + "'");
			}
			wherePart.append(")");

		}

		// 被保人姓名 手机号 E-mail 证件号码 关联 informationappnt表
		String insuredname = dga.getParams().getString("insuredname");
		String mobile = dga.getParams().getString("mobile");
		String email = dga.getParams().getString("email");
		String idNO = dga.getParams().getString("idNO");
		if (StringUtil.isNotEmpty(insuredname) || StringUtil.isNotEmpty(mobile) || StringUtil.isNotEmpty(email) || StringUtil.isNotEmpty(idNO)) {
			String condition = "";

			if (StringUtil.isNotEmpty(insuredname)) {
				condition += " and c.recognizeeName = '" + insuredname.trim() + "'";
			}
			if (StringUtil.isNotEmpty(email)) {
				condition += " and c.recognizeeMail = '" + email.trim() + "'";
			}
			if (StringUtil.isNotEmpty(mobile)) {
				condition += " and c.recognizeeMobile = '" + mobile.trim() + "'";
			}
			if (StringUtil.isNotEmpty(idNO)) {
				condition += " and c.recognizeeIdentityId = '" + idNO.trim() + "' ";
			}

			wherePart.append(condition);
		}

		wherePart.append(" order by a.createdate desc,c.ordersn desc");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(querySql + wherePart.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("KID");
		if (dt != null) {
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			// String KID = dga.getParams().getString("KID");
			for (int i = 0; i < dt.getRowCount(); i++) {

				dt.set(i, "KID", StringUtil.md5Hex(PubFun.getKeyValue() + dt.getString(i, "orderSn")));
			}
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			String str = (String)dt.get(i, 12);
			if (StringUtil.isEmpty(str)) {
			}else if (str.length() >=6) {
				String mStr = str.substring(0, str.length() - 6) + "******";
				dt.set(i, 12,mStr);
			}else if(str.length() >=3 && str.length() <6){
				String mStr = str.substring(0, str.length() - 3) + "***";
				dt.set(i, 12,mStr);
			}else{
				
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	// 淘宝SD撤单查询订单信息
	public void tbsdOrderCancel(DataGridAction dga) {
		// 得到查询SQL
		String querySql = "SELECT a.tbTradeSeriNo AS tbTradeSeriNo, c.orderSn AS ordersn, c.insuredSn AS insuredSn, " + "b.productName AS productName, ROUND(d.timePrem,2) AS totalAmount, "
				+ "DATE_FORMAT(e.receiveDate,'%Y-%m-%d %T') AS receiveDate, DATE_FORMAT(b.startDate,'%Y-%m-%d %T') AS startDate, "
				+ "DATE_FORMAT(b.endDate,'%Y-%m-%d %T') AS endDate, c.recognizeeName AS recognizeeName, "
				+ "c.recognizeeIdentityTypeName AS recognizeeIdentityTypeName, c.recognizeeIdentityId AS recognizeeIdentityId, "
				+ "c.recognizeeMobile AS recognizeeMobile, c.recognizeeMail AS recognizeeMail, d.policyNo AS policyNo "
				+ "FROM sdOrders a, sdinformation b, sdinformationinsured c, sdinformationRiskType d, tradeInformation e "
				+ "WHERE a.orderstatus = 7 AND a.ordersn=b.ordersn AND b.informationSn=c.informationSn " + "AND c.recognizeeSn = d.recognizeeSn AND a.orderSn = e.ordId AND c.insuredSn IS NOT NULL "
				+ "AND d.policyNo <> '' AND d.policyNo IS NOT NULL AND c.ordersn LIKE 'TB%' ";
		StringBuffer wherePart = new StringBuffer();

		// 外部订单号
		String tbTradeSeriNo = dga.getParams().getString("tbTradeSeriNo");
		if (StringUtil.isNotEmpty(tbTradeSeriNo)) {
			wherePart.append(" and a.tbTradeSeriNo like '%" + tbTradeSeriNo.trim() + "%'");
		}

		// 系统订单号
		String ordersn = dga.getParams().getString("ordersn");
		if (StringUtil.isNotEmpty(ordersn)) {
			wherePart.append(" and c.ordersn like '%" + ordersn.trim() + "%' ");
		}

		// 创建开始日期
		String createDate = dga.getParams().getString("createDate");
		if (StringUtil.isNotEmpty(createDate)) {
			wherePart.append(" and date(a.createDate) >= '" + createDate.trim() + "'");
		}

		// 创建终止日期
		String endCreateDate = dga.getParams().getString("endCreateDate");
		if (StringUtil.isNotEmpty(endCreateDate)) {
			wherePart.append(" and date(a.createDate) <= '" + endCreateDate.trim() + "'");
		}
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			wherePart.append(" and b.productName like '%" + productName.trim() + "%'");
		}

		// 被保人姓名 手机号 E-mail 证件号码 关联 informationappnt表
		String insuredname = dga.getParams().getString("insuredname");
		String mobile = dga.getParams().getString("mobile");
		String email = dga.getParams().getString("email");
		String idNO = dga.getParams().getString("idNO");
		if (StringUtil.isNotEmpty(insuredname) || StringUtil.isNotEmpty(mobile) || StringUtil.isNotEmpty(email) || StringUtil.isNotEmpty(idNO)) {
			String condition = "";

			if (StringUtil.isNotEmpty(insuredname)) {
				condition += " and c.recognizeeName = '" + insuredname.trim() + "'";
			}
			if (StringUtil.isNotEmpty(email)) {
				condition += " and c.recognizeeMail = '" + email.trim() + "'";
			}
			if (StringUtil.isNotEmpty(mobile)) {
				condition += " and c.recognizeeMobile = '" + mobile.trim() + "'";
			}
			if (StringUtil.isNotEmpty(idNO)) {
				condition += " and c.recognizeeIdentityId = '" + idNO.trim() + "' ";
			}

			wherePart.append(condition);
		}

		wherePart.append(" order by a.createdate desc,c.ordersn desc");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(querySql + wherePart.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("KID");
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				dt.set(i, "KID", StringUtil.md5Hex(PubFun.getKeyValue() + dt.getString(i, "orderSn")));
			}
		}

		dga.setTotal(qb);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String str = (String)dt.get(i, 10);
			if (StringUtil.isEmpty(str)) {
			}else if (str.length() >=6) {
				String mStr = str.substring(0, str.length() - 6) + "******";
				dt.set(i, 10,mStr);
			}else if(str.length() >=3 && str.length() <6){
				String mStr = str.substring(0, str.length() - 3) + "***";
				dt.set(i, 10,mStr);
			}else{
				
			}
			dt.set(i, 11,toConcealUtil.toConceal((String)dt.get(i, 11),4));
		}
		dga.bindData(dt);
	}

	public void productInquery(DataGridAction dga) {
		String username = dga.getParams().getString("username");
		String productname = dga.getParams().getString("productname");
		String CreateDatefrom = dga.getParams().getString("CreateDatefrom");
		String CreateDateto = dga.getParams().getString("CreateDateto");
		QueryBuilder qb = new QueryBuilder("select * from (select b.MemberID MemberID ,b.CreateDate CreateDate,"
				+ "b.CreateTime CreateTime,b.ProductID ProductID,a.username  username,a.email,a.mobileNO," + "(select ProductName from SDproduct c where c.ProductID=b.ProductID)  productname "
				+ "from member a,SDHistory  b where a.id=b.MemberID ) d where MemberID is not null ");

		if (StringUtil.isNotEmpty(username)) {
			qb.append(" and username =? ", username.trim());
		}
		if (StringUtil.isNotEmpty(productname)) {
			qb.append(" and productname like ? ", "%" + productname.trim() + "%");
		}
		if (StringUtil.isNotEmpty(CreateDatefrom)) {
			qb.append(" and CreateDate >=? ", CreateDatefrom.trim());
		}
		if (StringUtil.isNotEmpty(CreateDateto)) {
			qb.append(" and CreateDate <=?", CreateDateto.trim());
		}
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public void doCheck() {
		String UserNames = $V("UserNames");
		String Status = $V("Status");
		if (StringUtil.isNotEmpty(UserNames) && StringUtil.isNotEmpty(Status)) {
			String[] names = UserNames.split(",");
			Transaction trans = new Transaction();
			ZDMemberSchema member = new ZDMemberSchema();
			for (int i = 0; i < names.length; i++) {
				member = new ZDMemberSchema();
				member.setUserName(names[i]);
				member.fill();
				member.setStatus(Status);
				trans.add(member, Transaction.UPDATE);
			}
			if (trans.commit()) {
				Response.setLogInfo(1, "审核成功");
			} else {
				Response.setLogInfo(0, "审核失败");
			}
		}
	}

	public void checkName() {
		String UserName = Request.getString("UserName");
		int count = new QueryBuilder("select count(*) from member where UserName=?", UserName).executeInt();
		if (count > 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	public void checkEmail() {
		String email = Request.getString("email");
		int count = new QueryBuilder("select count(*) from member where email=?", email).executeInt();
		if (count > 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	public void checkMobile() {
		String mobileNO = Request.getString("mobileNO");
		int count = new QueryBuilder("select count(*) from member where mobileNO=?", mobileNO).executeInt();
		if (count > 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	public void checkName2() { // 判断会员基本信息修改过程中登录名称是否和已有的重复
		String UserName = Request.getString("UserName");
		DataTable dt = new QueryBuilder("select * from member where UserName=?", UserName).executeDataTable();
		if (dt.getRowCount() > 1) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	public void checkName3() { // 判断会员基本信息修改过程中检测输入的会员ID号是否重名
		String id = Request.getString("id");
		DataTable dt = new QueryBuilder("select * from member where id=?", id).executeDataTable();
		if (dt.getRowCount() > 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	public void checkName4() { // 判断会员基本信息修改过程中检测输入的会员ID号是否重名
		String id = Request.getString("ID");
		DataTable dt = new QueryBuilder("select * from member where id=?", id).executeDataTable();
		if (dt.getRowCount() > 0) {
			Response.setStatus(0);
		} else {
			Response.setStatus(1);
		}
	}

	public void orderInquery2(DataGridAction dga) {
		String id = $V("id");
		// fhz round ROUND(a.productPrice,2) 保留两位小数 fhz 20121123
		QueryBuilder qb = new QueryBuilder(
				"select b.id,a.createDate,b.effective,b.fail,a.productName,ROUND(a.productPrice,2)  productPrice,(select AddTime from SDCancelReturnData c where c.ContNo=a.coutNo) as CancelDate from orderitem a,information b "
						+ " where a.id=b.orderitem_id  and order_id = ?", id);
		qb.append("order by a.createDate desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public void collectInquery(DataGridAction dga) {
		String id = dga.getParams().getString("id");
		QueryBuilder qb = new QueryBuilder("select b.productname pname,b.InsureName iname,b.ProductGenera pgenera,b.HtmlPath hpath,b.IsPublish publish,"
				+ "a.createdate cdate from ProductCollection a,SDProduct b where a.productId=b.productId ");
		if (StringUtil.isNotEmpty(id)) {
			qb.append("and a.memberId= ?", id.trim());
		}
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public void add() {
		memberSchema member = new memberSchema();
		String Password = $V("Password");
		String ConfirmPassword = $V("ConfirmPassword");
		String UserName = $V("UserName");
		String ip = $V("ip");

		if (Password.length() == 0) {
			Response.setLogInfo(0, "密码不能为空");
			return;
		}

		if (!(Password.equals(ConfirmPassword))) {
			Response.setLogInfo(0, "密码不一致");
			return;
		}
		// if (member.query(new QueryBuilder("where UserName=?",
		// UserName)).size() > 0) {
		// Response.setLogInfo(0, "登录名已经存在，请更换登录名");
		// return;
		// } else {
		member.setValue(Request);
		member.setlocation($V("location"));
		member.setregisterIp(ip);
		member.setmemberRank_id("40288083382e8a2701382e994f5f000a");
		member.setloginFailureCount(0);
		member.setpoint(0);
		long menberID = NoUtil.getMaxID("MemberID");
		member.setpassword(StringUtil.md5Hex($V("Password")));
		member.setid(menberID + "");
		BigDecimal deposit;
		double temp = 0.0000;
		deposit = new BigDecimal(temp);
		member.setdeposit(deposit);
		member.setcreateDate(new Date());
		member.setgrade("K0");
		member.setFromWap("wj");
		member.setvipFlag("N");
		member.setcurrentValidatePoint(0);
		member.setpreLoginPoints(0);
		
		String mobileNO = $V("mobileNO");
		String email = member.getemail();
		
		String Mobilesql = "select id from member where mobileNO=? ";
		String mailsql = "select id from member where email=?  ";

		String username = "";
		String memberid = "";
		boolean userisExist = false;
		
		QueryBuilder qb = new QueryBuilder();

		if (StringUtil.isNotEmpty(email)) {
			qb = new QueryBuilder(mailsql, email);
			if (qb.executeOneValue() != null) {
				userisExist = true;
				}
			}
		else if (StringUtil.isNotEmpty(mobileNO)) {
			qb = new QueryBuilder(Mobilesql, mobileNO);
			if (qb.executeOneValue() != null) {
				userisExist = true;
				}
			}
		if(userisExist){
			Response.setLogInfo(0, "正在注册");
			return;
		}
		
		member.setisEmailBinding("Y");
		Map<String, Object> map = new HashMap<String, Object>();
		HttpServletRequest request = null;
		String temp1 = "";
		if (StringUtil.isNotEmpty(member.getusername())) {
			temp1 = member.getusername();
		} else if (StringUtil.isNotEmpty(member.getemail())) {
			temp1 = member.getemail();
		} else if (StringUtil.isNotEmpty(member.getmobileNO())) {
			temp1 = member.getmobileNO();
		}
		map.put("UserName", temp1);
		map.put("PassWord", $V("Password"));
		map.put("ToMobileNO", member.getmobileNO());
		map.put("ToName", member.getrealName());

		int count = 0;
		if (!(member.getbirthday() == null || "".equals(member.getbirthday())))
			count += 15;
		if (!(member.getemail() == null || "".equals(member.getemail())))
			count += 15;
		if (!(member.getIDType() == null || "".equals(member.getIDType()) || "-1".equals(member.getIDType())))
			count += 4;
		if (!(member.getindustryType() == null || "".equals(member.getindustryType())))
			count += 3;
		if (!(member.getlocation() == null || "".equals(member.getlocation()) || "-1".equals(member.getlocation())))
			count += 4;
		if (!(member.getmarriageStatus() == null || "".equals(member.getmarriageStatus()) || "-1".equals(member.getmarriageStatus())))
			count += 4;
		if (!(member.getmobileNO() == null || "".equals(member.getmobileNO())))
			count += 15;
		if (!(member.getposition() == null || "".equals(member.getposition())))
			count += 2;
		if (!(member.getrealName() == null || "".equals(member.getrealName())))
			count += 15;
		if (!(member.getsex() == null || "".equals(member.getsex())))
			count += 15;
		if (!(member.getVIPType() == null || "".equalsIgnoreCase(member.getVIPType()) || "-1".equals(member.getVIPType())))
			count += 4;
		if (!(member.getzipcode() == null || "".equals(member.getzipcode())))
			count += 4;

		String fullDegree = count + "%";
		member.setfullDegree(fullDegree);
		if (member.insert()) {
			cn.com.sinosoft.entity.Member tMember = new cn.com.sinosoft.entity.Member();
			tMember.setEmail(member.getemail());
			tMember.setId(member.getid());
			map.put("Member", tMember);
			map.put("MemberName", member.getrealName());
			ActionUtil.dealAction("wj00028", map, request);// 发短信
			map.put("memberId", member.getid());
			ActionUtil.dealAction("wj00027", map, request);// 发邮件
			map.remove("Member");
			ActionUtil.sendMail("wj00027", member.getemail(), map);
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "添加" + member.getid() + "失败!");
		}
		//
		// }

	}

	public void dg1Edit() { // 修改会员调用的方法
		String id = $V("id");
		memberSchema member = new memberSchema();
		member.setid(id);
		if (member.fill()) {
			Transaction trans = new Transaction();
			// 修改前可用积分数
			int oldPoint = member.getcurrentValidatePoint();
			String oldVipFlag = member.getvipFlag();
			String newVipFlag = Request.getString("vipFlag");
			
			boolean vipFlag = false; 
			if (StringUtil.isEmpty(oldVipFlag)) {
				if ("Y".equals(newVipFlag)) {
					VIPLogSchema sch = new VIPLogSchema();
					sch.setid(NoUtil.getMaxNo("VIPLogID", 12));
					sch.setmemberId(id);
					sch.setoperation("升级VIP");
					sch.setoperaUser(User.getUserName());
					sch.setoperaTime(new Date());
					vipFlag = true;
					trans.add(sch, Transaction.INSERT);
				}
			} else if (!oldVipFlag.equals(newVipFlag)) {
				if ("Y".equals(newVipFlag)) {
					VIPLogSchema sch = new VIPLogSchema();
					sch.setid(NoUtil.getMaxNo("VIPLogID", 12));
					sch.setmemberId(id);
					sch.setoperation("升级VIP");
					sch.setoperaUser(User.getUserName());
					sch.setoperaTime(new Date());
					vipFlag = true;
					trans.add(sch, Transaction.INSERT);
				}
				
				if ("N".equals(newVipFlag)) {
					VIPLogSchema sch = new VIPLogSchema();
					sch.setid(NoUtil.getMaxNo("VIPLogID", 12));
					sch.setmemberId(id);
					sch.setoperation("取消VIP");
					sch.setoperaUser(User.getUserName());
					sch.setoperaTime(new Date());
					trans.add(sch, Transaction.INSERT);
					// 删除VIP会员到期提醒邮件
					trans.add(new QueryBuilder("delete from SDCacheErroerMail where paySn = ?", id));
				}
			}
			//add by wangej 20151102 cms修改会员性别，前台无法显示，原因为后台存入的为M/F，前台判断的为0/1，后台做一下转换处理 begin
			if("F".equals(Request.getString("sex"))){
				Request.put("sex", "1");
			}else if ("M".equals(Request.getString("sex"))){
				Request.put("sex", "0");
			}
			//add by wangej 20151102 cms修改会员性别，前台无法显示，原因为后台存入的为M/F，前台判断的为0/1，后台做一下转换处理 begin
			member.setFromWap("wj");
			member.setValue(Request);
			trans.add(member, Transaction.UPDATE);
			if (trans.commit()) {
				if (vipFlag) {
					String sql = "select CodeValue from zdcode where CodeType = 'VIPExpireEmail' and ParentCode='VIPExpireEmail' ";
					DataTable dt = new QueryBuilder(sql).executeDataTable();
					if (dt == null || dt.getRowCount() == 0) {
						logger.warn("VIP会员提醒邮件 发送邮箱为空！会员ID:{}", id);
						Response.setLogInfo(0, "修改成功, VIP会员到期提醒邮件数据插入异常,请联系IT人员！");
					}else{
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("memberId", id);
						String toMail = "";
						int rowcount = dt.getRowCount();
						for (int i = 0; i < rowcount; i++) {
							toMail += dt.getString(i, 0) + ",";
						}
						map.put("expireDate", PubFun.getVIPExpireDate());
						if (!ActionUtil.sendMail("wj00121", toMail.substring(0, toMail.length() - 1), map)) {
							Response.setLogInfo(0, "修改成功, VIP会员到期提醒邮件数据插入异常,请联系IT人员！");
						}
					}
				}
				memberSchema member1 = new memberSchema();
				member1.setid(id);
				if (member1.fill()) {
					// 修改后可用积分数
					int newPoint = member1.getcurrentValidatePoint();
					// 积分发生变化 记录积分流水表
					if (oldPoint != newPoint) {
						// 变化积分数
						int point = 0;
						// 0:收入 1：支出
						String manner = "0";
						if (newPoint > oldPoint) {
							point = newPoint - oldPoint;
						} else {
							point = oldPoint - newPoint;
							manner = "1";
						}
						SDIntCalendarSchema tSDIntCalendarSchema = new SDIntCalendarSchema();
						tSDIntCalendarSchema.setID(NoUtil.getMaxID("IntID") + "");
						tSDIntCalendarSchema.setMemberId(member1.getid());
						tSDIntCalendarSchema.setIntegral(point + "");
						tSDIntCalendarSchema.setSource("25");// 积分来源
						tSDIntCalendarSchema.setManner(manner);// 表示收入
						tSDIntCalendarSchema.setStatus("0");
						tSDIntCalendarSchema.setDescription("人工处理-处理人员："+User.getUserName());
						tSDIntCalendarSchema.setCreateDate(PubFun.getCurrentDate());
						tSDIntCalendarSchema.setCreateTime(PubFun.getCurrentTime());
						tSDIntCalendarSchema.setsvaliDate(PubFun.getCurrentDate());
						tSDIntCalendarSchema.insert();
					}
				}
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "修改" + member.getid() + "失败!");
			}
		}
	}

	public void dg1Edit3() { // 修改订单基本信息
		String orderSn = $V("orderSn");
		ordersSchema ordersSchemamember = new ordersSchema();
		ordersSet ordersSet = ordersSchemamember.query(new QueryBuilder("where orderSn ='" + orderSn + "'"));
		ordersSchemamember = ordersSet.get(0);
		// ordersSchemamember.fill();
		if (ordersSchemamember.fill()) {
			ordersSchemamember.setValue(Request);
			if (ordersSchemamember.update()) {
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "修改" + ordersSchemamember.getorderSn() + "失败!");
			}
		}
	}

	public void dg1Edit4() {
		String id_appnt = $V("id_appnt");
		// 投保人
		InformationAppntSchema informationAppntSchema = new InformationAppntSchema();
		// informationSchema.setValue(Request);
		informationAppntSchema.setid(id_appnt);
		informationAppntSchema.fill();
		informationAppntSchema.setValue(Request);
		// 将性别名称,证件名称同步修改 ---ysc
		GetDBdata db = new GetDBdata();
		try {
			String aResult = db
					.getOneValue("select codeName from dictionary where codeType = 'Sex' and codeValue = '"
							+ informationAppntSchema.getapplicantSex()
							+ "' and insuranceCode =(select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationAppnt infoAppnt on info.id = infoAppnt.information_id where infoAppnt.information_id = '"
							+ informationAppntSchema.getinformation_id() + "')");
			String aResult1 = db
					.getOneValue("select codeName from dictionary where codeType = 'certificate' and codeValue = '"
							+ informationAppntSchema.getapplicantIdentityType()
							+ "' and insuranceCode =(select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationAppnt infoAppnt on info.id = infoAppnt.information_id where infoAppnt.information_id = '"
							+ informationAppntSchema.getinformation_id() + "')");
			informationAppntSchema.setapplicantSexName(aResult);
			informationAppntSchema.setapplicantIdentityTypeName(aResult1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}

		informationAppntSchema.setid(id_appnt);

		// 被保人
		String id_insured = $V("id_insured");
		InformationInsuredSchema informationinsuredSchema = new InformationInsuredSchema();
		informationinsuredSchema.setid(id_insured);
		informationinsuredSchema.fill();
		informationinsuredSchema.setValue(Request);
		// 将性别名称,证件名称同步修改 ---ysc
		GetDBdata db1 = new GetDBdata();
		try {
			String rResult = db1
					.getOneValue("select codeName from dictionary where codeType = 'Sex' and codeValue = '"
							+ informationinsuredSchema.getrecognizeeSex()
							+ "' and insuranceCode =(select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationInsured infoInsured on info.id = infoInsured.information_id where infoInsured.information_id = '"
							+ informationinsuredSchema.getinformation_id() + "')");
			String rResult1 = db1
					.getOneValue("select codeName from dictionary where codeType = 'certificate' and codeValue = '"
							+ informationinsuredSchema.getrecognizeeIdentityType()
							+ "' and insuranceCode =(select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationInsured infoInsured on info.id = infoInsured.information_id where infoInsured.information_id = '"
							+ informationinsuredSchema.getinformation_id() + "')");
			informationinsuredSchema.setrecognizeeSexName(rResult);
			informationinsuredSchema.setrecognizeeIdentityTypeName(rResult1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}

		informationinsuredSchema.setid(id_insured);

		if (informationAppntSchema.update() && informationinsuredSchema.update()) {
			Response.setLogInfo(1, "修改成功");
		} else {
			Response.setLogInfo(0, "修改" + informationAppntSchema.getid() + "失败!");
		}
	}

	/**
	 * 修改订单信息
	 * */
	public void editOrders() {
		// String createDate = $V("createDate");
		String orderSn = $V("orderSn");
		String orderStatus = $V("orderStatus");
		Double paidAmount = Double.valueOf($V("paidAmount"));
		Double paymentFee = Double.valueOf($V("paymentFee"));
		Double productTotalPrice = Double.valueOf($V("productTotalPrice"));
		Double totalAmount = Double.valueOf($V("totalAmount"));
		String policyNumber = $V("policyNumber");// 加入保单号 add by fhz 20121210
		ordersSchema order = new ordersSchema();
		InsuredCompanyReturnDataSchema insured = new InsuredCompanyReturnDataSchema();
		GetDBdata db = new GetDBdata();
		String id = $V("order_id");
		order.setid(id);
		if (order.fill()) {
			order.setorderSn(orderSn);
			order.setorderStatus(orderStatus);
			order.setpaidAmount(BigDecimal.valueOf(paidAmount));
			order.setpaymentFee(BigDecimal.valueOf(paymentFee));
			order.setproductTotalPrice(BigDecimal.valueOf(productTotalPrice));
			order.settotalAmount(BigDecimal.valueOf(totalAmount));
			order.setpolicyNumber(policyNumber);
			try {
				String sql = "select id from InsuredCompanyReturnData  where orderSn = ? ";
				String[] temp = { orderSn };
				String result = db.getOneValue(sql, temp);
				if (StringUtils.isNotEmpty(result)) {
					insured.setID(result);
					if (insured.fill()) {
						insured.setpolicyNo(policyNumber);
						insured.update();
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (order.update()) {
				Response.setLogInfo(1, "修改成功");
			} else {
				Response.setLogInfo(0, "修改" + orderSn + "失败!");
			}
		} else {

		}
	}

	/**
	 * 修改订单条目
	 * */
	public void editOrderItemUpdate() {
		DataTable dt = (DataTable) Request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder(" update information set effective= ? ,fail=? where id=? ");
			qb.add(dt.getString(i, "effective"));
			qb.add(dt.getString(i, "fail"));
			qb.add(dt.getString(i, "id"));
			trans.add(qb);
			QueryBuilder qb1 = new QueryBuilder(" update orderitem set productPrice=? where id in ( " + "select orderitem_id from information  where id = ?)");
			qb1.add(dt.getDouble(i, "productPrice"));
			qb1.add(dt.getString(i, "id"));
			trans.add(qb1);

		}
		if (trans.commit()) {
			Response.setLogInfo(1, "修改成功!");
		} else {
			Response.setLogInfo(0, "修改失败!");
		}
	}

	public static Mapx initAddDialog(Mapx params) {
		params.put("sex", HtmlUtil.codeToOptions("Gender"));
		params.put("VIPType", HtmlUtil.codeToOptions("Member.Type"));
		params.put("IDType", HtmlUtil.codeToOptions("member.IDType"));
		params.put("marriageStatus", HtmlUtil.codeToOptions("member.marriageStatus"));
		params.put("isAccountEnabled", HtmlUtil.codeToOptions("isAccountEnabled"));
		params.put("isAccountLocked", HtmlUtil.codeToOptions("isAccountLocked"));
		params.put("hobby", HtmlUtil.codeToCheckboxes("hobby", "member.Hobby"));
		return params;
	}

	public static Mapx initDialog(Mapx params) {
		String id = params.getString("id");
		String flag = params.getString("flag");
		if (StringUtil.isNotEmpty(id)) {
			memberSchema member = new memberSchema();
			member.setid(id);
			memberSet memberSet = member.query(new QueryBuilder("where id ='" + id + "'"));
			member = memberSet.get(0);
			Mapx map = member.toMapx();
			String provinceId = "";
			// 初始化地区信息
			if (StringUtil.isNotEmpty(member.getlocation())) {
				QueryBuilder qb = new QueryBuilder("SELECT id,parent_id,NAME FROM AREA WHERE id=?");
				qb.add(member.getlocation());
				DataTable dt = qb.executeDataTable();
				provinceId = dt.getString(0, "parent_id");
			}
			map.put("IDType", HtmlUtil.codeToOptions("member.IDType", member.getIDType()));
			map.put("marriageStatus", HtmlUtil.codeToOptions("member.marriageStatus", member.getmarriageStatus()));
			
			//mod by wangej sex处理，表中存在两组数据（M/F和0/1）,将0/1转化  20151029 begin
			String sexValue = "";
			if("1".equals(member.getsex())){
				
				sexValue = "F";
			}else if ("0".equals(member.getsex())){
				
				sexValue = "M";
			}else
			{
				sexValue =member.getsex();
			}		
			map.put("sex", HtmlUtil.codeToOptions("Gender", sexValue));
			//mod by wangej sex处理，表中存在两组数据（M/F和0/1）,将0/1转化  20151029 end
			map.put("VIPType", HtmlUtil.codeToOptions("Member.Type", member.getVIPType()));
			map.put("flag", flag);
			map.put("provinceList", HtmlUtil.areaToOptions("province", "", provinceId));
			map.put("cityList", HtmlUtil.areaToOptions("city", provinceId, member.getlocation()));
			
			String sql = "select sum(sdIC.Integral)"
					+ " from  SDIntCalendar sdIC  "
					+ " where  sdic.status ='0' and  manner ='1' and sdIC.integral > 0 and sdIC.memberId=?";
			Object  qbvalue = new QueryBuilder(sql,id).executeOneValue();
			if(qbvalue == null||"".equals(qbvalue.toString())){
				map.put("usedpoint","0.0");
			}else{
				map.put("usedpoint",qbvalue.toString());
			}
			return map;
		} else {
			return params;
		}
	}

	/**
	 * 修改会员等级信息
	 */
	public void editMemGrade() {
		String id = $V("id");
		String gradeName = $V("gradeName");
		String orderCount = $V("orderCount");
		String sumPrem = $V("sumPrem");
		String link = $V("link");
		
		if (StringUtil.isEmpty(id)) {
			Response.setLogInfo(0, "请选择条目！");
			return;
		}
		if (StringUtil.isEmpty(gradeName)) {
			Response.setLogInfo(0, "请设置会员等级名称！");
			return;
		}
		if (StringUtil.isNotEmpty(orderCount)) {
			if (!NumberUtil.isInt(orderCount)) {
				Response.setLogInfo(0, "订单数必须是整数！");
				return;
			} else {
				int ordCount = Integer.valueOf(orderCount);
				if (ordCount < 0) {
					Response.setLogInfo(0, "订单数不能是负数！");
					return;
				}
			}
		}
		
		if (StringUtil.isNotEmpty(sumPrem)) {
			if (!NumberUtil.isNumber(sumPrem)) {
				Response.setLogInfo(0, "累计保费必须是数字！");
				return;
			} else {
				double sumPrem1 = Double.valueOf(sumPrem);
				if (sumPrem1 < 0) {
					Response.setLogInfo(0, "累计保费不能是负数！");
					return;
				}
			}
		}
		
		MemberGradeSchema grade = new MemberGradeSchema();
		grade.setid(id);
		if (grade.fill()) {
			grade.setgradeName(gradeName);
			grade.setorderCount(orderCount);
			grade.setsumPrem(sumPrem);
			grade.setlink(link);
			
			if (grade.update()) {
				Response.setLogInfo(1, "修改成功！");
			} else {
				Response.setLogInfo(1, "修改失败！");
			}
		} else {
			Response.setLogInfo(0, "未找到该等级信息！");
		}
	}
	
	/**
	 * 取得会员等级信息
	 * @param dga
	 */
	public void gradeInquery(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from MemberGrade order by grade asc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 取得会员VIP记录
	 * @param dga
	 */
	public void memberVipLogInquery(DataGridAction dga) {
		String memberId = dga.getParams().getString("memberId");
		QueryBuilder qb = new QueryBuilder("select operation,operaUser,operaTime from VIPLog where memberId = ? order by operaTime desc", memberId);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	// 得到订单信息
	public void memberOrderInquery(DataGridAction dga) {

		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT a.id,a.memberid,a.ordersn,a.orderStatus,DATE_FORMAT(e.svalidate,'%Y-%m-%d')" + " AS svalidate,DATE_FORMAT(e.evalidate,'%Y-%m-%d') AS evalidate,");
		sb.append("  b.ProductName,b.planName,a.ModifyDate,IF(a.OrderStatus=7," + "ROUND(SUM(d.recognizeePrem),2),0) AS notfee,a.payPrice,h.codeName AS orderstatusname ,");
		sb.append("d.RecognizeeName AS recognizeeNu,c.ApplicantName,c.applicantSexName" + " FROM sdorders a,sdinformation b,sdinformationappnt c,sdinformationinsured d,");
		sb.append("sdinformationrisktype e,femrisk f,zdcode h WHERE a.ordersn = b.ordersn" + " AND b.informationsn = c.informationsn AND a.ordersn = d.ordersn  ");
		sb.append("AND d.recognizeeSn = e.recognizeeSn AND b.productid = f.eriskid" + "  AND  h.CodeType='orderstatus' AND h.codevalue=a.orderstatus  ");
		// 会员ID 如果此时是会员查询页面跳转过来，那么此时要根据memberid来查询
		String memberid = dga.getParams().getString("id");

		if (StringUtil.isNotEmpty(memberid)) {
			sb.append(" AND a.memberid='" + memberid.trim() + "'");
			sb.append(" AND a.orderstatus IN ('7','9','10','11','12','13','14')");
		}

		sb.append(" GROUP BY a.ordersn ORDER BY a.modifydate DESC,a.ordersn DESC");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(sb.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("KID");
		if (dt != null && dt.getRowCount()>1) {
			// 对SessionId+OrderSn 做加密处理，保证期安全性
			// String KID = dga.getParams().getString("KID");
			String orderSns = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				orderSns += ",'" + dt.getString(i, "orderSn") + "'";
				dt.set(i, "KID", StringUtil.md5Hex(PubFun.getKeyValue() + dt.getString(i, "orderSn")));
				// 被保人个数查询
				String sqlre = "SELECT recognizeeName,recognizeeIdentityId FROM sdinformationinsured WHERE ordersn ='" + dt.getString(i, "orderSn") + "' GROUP BY recognizeekey";
				QueryBuilder qbre = new QueryBuilder(sqlre);
				DataTable dtre = qbre.executeDataTable();
				if (dtre != null && dtre.getRowCount() > 0) {
					dt.set(i, "recognizeeNu", dtre.getRowCount());
				} else {
					dt.set(i, "recognizeeNu", 0);
				}
			}
			orderSns = orderSns.substring(1);
			String sql = "select orderSn,ROUND(sum(timePrem),2),ROUND(sum(payPrice),2) from sdinformationrisktype where orderSn in ("+orderSns+") and (appstatus is null or appstatus = '' or appstatus in ('0','1')) group by orderSn";
			DataTable dt1 = new QueryBuilder(sql).executeDataTable();
			if (dt1 != null && dt1.getRowCount() > 0) {
				int rowCount = dt1.getRowCount();
				Map<String, String> notfeeMap = new HashMap<String, String>();
				Map<String, String> payPriceMap = new HashMap<String, String>();
				for (int i = 0; i < rowCount; i++) {
					notfeeMap.put(dt1.getString(i, 0), dt1.getString(i, 1));
					payPriceMap.put(dt1.getString(i, 0), dt1.getString(i, 2));
				}
				
				for (int i = 0; i < dt.getRowCount(); i++) {
					if (StringUtil.isNotEmpty(notfeeMap.get(dt.getString(i, "orderSn")))) {
						dt.set(i, "notfee", notfeeMap.get(dt.getString(i, "orderSn")));
					} else {
						dt.set(i, "notfee", "0.0");
					}
					if (StringUtil.isNotEmpty(payPriceMap.get(dt.getString(i, "orderSn")))) {
						dt.set(i, "payPrice", payPriceMap.get(dt.getString(i, "orderSn")));
					} else {
						dt.set(i, "payPrice", "0.0");
					}
				}
			}
		}

		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static Mapx initDialog2(Mapx params) { // 显示订单基本信息
		String id = params.getString("id");
		String querySql = "select orderstatus from orders where id =?";
		QueryBuilder qb = new QueryBuilder(querySql);
		qb.add(id);
		int status = qb.executeInt();
		if (StringUtil.isNotEmpty(id)) {
			ordersSchema orders = new ordersSchema();
			orders.setid(id);
			orders.fill();
			// 格式化为两位小数 fhz 20121123
			if (status == 7) {
				orders.setpaidAmount(orders.gettotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP));

			} else {
				orders.setpaidAmount(orders.getpaidAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			}
			orders.settotalAmount(orders.gettotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
			orders.setpaymentFee(orders.getpaymentFee().setScale(2, BigDecimal.ROUND_HALF_UP));
			orders.setproductTotalPrice(orders.getproductTotalPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
			// 获取 InsuredCompanyReturnData 保单号 ---ysc
			GetDBdata db = new GetDBdata();
			try {
				String result = db.getOneValue("select policyNo from InsuredCompanyReturnData i left join orders o on i.orderSn = o.orderSn where o.orderSn = '" + orders.getorderSn() + "'");
				orders.setpolicyNumber(result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage(), e);
			}

			Mapx map = orders.toMapx();
			map.put("orderStatus", HtmlUtil.codeToOptions("orderstatus", orders.getorderStatus() + ""));
			// map.put("shippingStatus",
			// HtmlUtil.codeToOptions("shippingStatus",
			// orders.getshippingStatus() + ""));
			return map;
		} else {
			return params;
		}

	}

	public static Mapx initDialog5(Mapx params) {
		String id = params.getString("id");
		if (StringUtil.isNotEmpty(id)) {
			InformationAppntSchema infappSchema = new InformationAppntSchema();
			infappSchema.setid(new ExeSQL().getOneValue("select id from InformationAppnt where information_id='" + id + "'"));
			infappSchema.fill();
			Mapx<String, Object> map = infappSchema.toMapx();
			// ----------ysc
			// 证件类型
			map.put("applicantIdentityType",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select codeName,codeValue from dictionary where codeType = 'certificate' and insuranceCode = (select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationAppnt infoAppnt on info.id = infoAppnt.information_id where infoAppnt.information_id = '"
											+ infappSchema.getinformation_id() + "')"), infappSchema.getapplicantIdentityType()));
			// 原来为 gender 此处应与前台同步为SEX

			// 性别
			map.put("applicantSex",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select codeName,codeValue from dictionary where codeType = 'Sex' and insuranceCode = (select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationAppnt infoAppnt on info.id = infoAppnt.information_id where infoAppnt.information_id = '"
											+ infappSchema.getinformation_id() + "')"), infappSchema.getapplicantSex()));
			// 地区
			map.put("applicantArea1",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select name,id from area where (parent_id = '' or parent_id is null) and  insuranceCompany =(select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationAppnt infoAppnt on info.id = infoAppnt.information_id where infoAppnt.information_id = '"
											+ infappSchema.getinformation_id() + "') order by id "), infappSchema.getapplicantArea1()));
			map.put("applicantArea2",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select name,id from area where (parent_id != '' or parent_id is not null) and insuranceCompany =(select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationAppnt infoAppnt on info.id = infoAppnt.information_id where infoAppnt.information_id = '"
											+ infappSchema.getinformation_id() + "') order by id "), infappSchema.getapplicantArea2()));
			map.put("appParentArea", infappSchema.getapplicantArea1());// 初始化前台地区的上级地区代码便于查询
																		// add
																		// by
																		// fhz
																		// 20121201
			map.put("appInformation_id", infappSchema.getinformation_id());

			InformationInsuredSchema infInsSchema = new InformationInsuredSchema();
			infInsSchema.setid(new ExeSQL().getOneValue("select id from InformationInsured where information_id='" + id + "'"));
			infInsSchema.fill();
			Mapx<String, Object> map1 = infInsSchema.toMapx();

			// ----------ysc
			// 证件类型
			map1.put(
					"recognizeeIdentityType",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select codeName,codeValue from dictionary where codeType = 'certificate' and insuranceCode = (select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationInsured infoInsured on info.id = infoInsured.information_id where infoInsured.information_id = '"
											+ infInsSchema.getinformation_id() + "')"), infInsSchema.getrecognizeeIdentityType()));
			// 原来为 gender 此处应与前台同步 SEX
			// 性别
			map1.put(
					"recognizeeSex",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select codeName,codeValue from dictionary where codeType = 'Sex' and insuranceCode = (select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join InformationInsured infoInsured on info.id = infoInsured.information_id where infoInsured.information_id = '"
											+ infInsSchema.getinformation_id() + "')"), infInsSchema.getrecognizeeSex()));
			;
			// 地区
			map1.put(
					"recognizeeArea1",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select name,id from area where (parent_id = '' or parent_id is null) and  insuranceCompany =(select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join  InformationInsured infoInsured on info.id = infoInsured.information_id  where infoInsured.information_id = '"
											+ infInsSchema.getinformation_id() + "') order by id "), infInsSchema.getrecognizeeArea1()));
			map1.put(
					"recognizeeArea2",
					HtmlUtil.queryToOptions(
							new QueryBuilder(
									"select name,id from area where (parent_id != '' or parent_id is not null) and  insuranceCompany =(select orders.insuranceCompanySn from Orders orders left join OrderItem orderItem on orders.id = orderItem.order_id left join Information info on orderItem.id = info.orderItem_id left join  InformationInsured infoInsured on info.id = infoInsured.information_id  where infoInsured.information_id = '"
											+ infInsSchema.getinformation_id() + "') order by id  "), infInsSchema.getrecognizeeArea2()));
			map1.put("recParentArea", infInsSchema.getrecognizeeArea1());// 初始化前台地区的上级地区代码便于查询
																			// add
																			// by
																			// fhz
																			// 20121201
			map1.put("recInformation_id", infInsSchema.getinformation_id());

			Mapx<Object, Object> mmap = new Mapx<Object, Object>();
			Object[] ks = map.keyArray();
			Object[] vs = map.valueArray();
			for (int i = 0; i < ks.length; i++) {
				Object v = vs[i];
				if (ks[i].equals("id")) {
					mmap.put("id_appnt", v);
				} else {
					mmap.put(ks[i], v);
				}
			}
			Object[] ks1 = map1.keyArray();
			Object[] vs1 = map1.valueArray();
			for (int i = 0; i < ks1.length; i++) {
				Object v = vs1[i];
				if (ks1[i].equals("id")) {
					mmap.put("id_insured", v);
				} else {
					mmap.put(ks1[i], v);
				}
			}
			return mmap;
		} else {
			return params;
		}
	}

	// /**
	// * 得到地区名称
	// * @author fhz 还是没用上
	// * **/
	// private static String getAreaName(String areaCode) {
	// String areaName="";
	//
	// if(areaCode==null||areaCode.equals(""))
	// {
	// return areaName;
	// }
	// ExeSQL tExeSQL=new ExeSQL();
	// String areaSql="select name from area where  id='"+areaCode+"'";
	// areaName=tExeSQL.getOneValue(areaSql);
	//
	// System.out.println(areaName);
	// return areaName;
	// }

	/*
	 * public void del() { String id = $V("id"); memberSchema member = new
	 * memberSchema(); member.setValue(Request); member.setid(id);
	 * member.fill(); member.setValue(Request);
	 * 
	 * if (member.delete()) { Response.setLogInfo(1, "删除成功"); } else {
	 * Response.setLogInfo(0, "删除" + member.getid() + "失败!"); } }
	 */
	public void del() {
		String ids = $V("ids");
		String[] names = ids.split(",");
		for (int i = 0; i < names.length; i++) {
			String checkSQL = "select 'X' from member where loginDate is not null and id='" + names[i] + "'";
			QueryBuilder mQB = new QueryBuilder(checkSQL);
			// String result = String.valueOf(mQB.executeOneValue());
			// if(result != null && "X".equalsIgnoreCase(result)){
			// Response.setLogInfo(0, "会员编号为“"+names[i]+"”是已登录用户，不能删除！");
			// return;
			// }
			String sql = "insert into memberb select * from member where id='" + names[i] + "'";
			mQB = new QueryBuilder(sql);
			mQB.executeNoQuery();
			memberSchema member = new memberSchema();
			member.setValue(Request);
			member.setid(names[i]);
			member.fill();
			// member.setValue(Request);
			member.delete();
		}
		Response.setLogInfo(1, "删除成功");
	}

	/**
	 * 发送重置密码邮件
	 * */
	public void sendPwdResetRUL() {
		Map<String, Object> map = new HashMap<String, Object>();
		String email = $V("email");
		map.put("pwdURL", Config.getValue("ServerContext") + "/shop/member!sendPasswordRecoverMail.action?mobileNoOrEmail=" + email);
		map.put("memberEmail", email);
		map.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
		if (ActionUtil.sendMail("pwd0001", email, map)) {// 注册发送验证吗
			Response.setLogInfo(1, "邮件发送成功！");
		} else {
			Response.setLogInfo(0, "邮件发送失败！");
		}
	}

	/**
	 * 校验投保人信息是否修改，修改则记录修改项
	 * 
	 * @param changeInfoList
	 *            修改记录
	 * @param appnt
	 *            投保人信息
	 * @param certificateInfo
	 *            证件信息
	 * @param sexInfo
	 *            性别星系
	 * @return true:修改 false:未修改
	 */
	private boolean checkAppntChange(List<PolicyChangeInfoSchema> changeInfoList, SDInformationAppntSchema appnt, Map<String, String> certificateInfo, Map<String, String> sexInfo) {
		boolean appChange = false;
		// 投保人姓名
		String applicantName = $V("applicantName");
		// 投保人姓名
		String applicantEnName = $V("applicantEnName");
		// 投保人证件类型
		String applicantIdentityType = $V("applicantIdentityTypeName").split("_")[0];
		// 投保人证件号
		String applicantIdentityId = $V("applicantIdentityId");
		// 投保人性别
		String applicantSex = $V("applicantSexName");
		// 投保人出生日期
		String applicantBirthday = $V("applicantBirthday");
		// 投保人手机号
		String applicantMobile = $V("applicantMobile");
		// 投保人邮箱
		String applicantMail = $V("applicantMail");
		if (applicantMail == null) {
			applicantMail = "";
		}

		// 投保人姓名
		if (!appnt.getapplicantName().equals(applicantName)) {
			// 投保人姓名变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("02");
			schema.setbeforeValue(appnt.getapplicantName());
			schema.setafterValue(applicantName);
			changeInfoList.add(schema);

			appnt.setapplicantName(applicantName);
			appChange = true;
		}
		// 投保人英文名
		if (StringUtil.isNotEmpty(appnt.getapplicantEnName())) {
			//去除英文名中的空白字符乱码
			if(StringUtil.isNotEmpty(applicantEnName)){
				applicantEnName=applicantEnName.replaceAll(" "," ");
			}
			if (!appnt.getapplicantEnName().equals(applicantEnName)) {
				// 投保人英文名变更记录
				PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
				schema.setchangeType("16");
				schema.setbeforeValue(appnt.getapplicantEnName());
				schema.setafterValue(applicantEnName);
				changeInfoList.add(schema);
				
				appnt.setapplicantEnName(applicantEnName);
				appChange = true;
			}
		}
		// 投保人证件类型
		if (!appnt.getapplicantIdentityType().equals(applicantIdentityType)) {
			String newTypeName = certificateInfo.get($V("applicantIdentityTypeName"));
			// 投保人证件类型变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("03");
			schema.setbeforeValue(appnt.getapplicantIdentityTypeName());
			schema.setafterValue(newTypeName);
			changeInfoList.add(schema);

			appnt.setapplicantIdentityType(applicantIdentityType);
			// 投保人证件类型名称
			appnt.setapplicantIdentityTypeName(newTypeName);
			appChange = true;

		}
		// 投保人证件号
		if (!appnt.getapplicantIdentityId().equals(applicantIdentityId)) {
			// 投保人证件号变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("04");
			schema.setbeforeValue(appnt.getapplicantIdentityId());
			schema.setafterValue(applicantIdentityId);
			changeInfoList.add(schema);

			appnt.setapplicantIdentityId(applicantIdentityId);
			appChange = true;
		}
		// 投保人性别
		if (!appnt.getapplicantSex().equals(applicantSex)) {
			// 投保人性别变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("05");
			schema.setbeforeValue(appnt.getapplicantSexName());
			schema.setafterValue(sexInfo.get(applicantSex));
			changeInfoList.add(schema);

			appnt.setapplicantSex(applicantSex);
			appnt.setapplicantSexName(sexInfo.get(applicantSex));
			appChange = true;
		}
		// 投保人出生日期
		if (!appnt.getapplicantBirthday().equals(applicantBirthday)) {
			// 投保人出生日期变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("06");
			schema.setbeforeValue(appnt.getapplicantBirthday());
			schema.setafterValue(applicantBirthday);
			changeInfoList.add(schema);

			appnt.setapplicantBirthday(applicantBirthday);
			appChange = true;
		}
		// 投保人手机号
		if (!appnt.getapplicantMobile().equals(applicantMobile)) {
			// 投保人手机号变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("07");
			schema.setbeforeValue(appnt.getapplicantMobile());
			schema.setafterValue(applicantMobile);
			changeInfoList.add(schema);

			appnt.setapplicantMobile(applicantMobile);
			appChange = true;
		}
		// 投保人邮箱
		String mail = appnt.getapplicantMail();
		if (mail == null) {
			mail = "";
		}
		if (!mail.equals(applicantMail)) {
			// 投保人邮箱变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("08");
			schema.setbeforeValue(appnt.getapplicantMail());
			schema.setafterValue(applicantMail);
			changeInfoList.add(schema);

			appnt.setapplicantMail(applicantMail);
			appChange = true;
		}

		return appChange;
	}

	/**
	 * 校验被保人信息是否修改，修改则记录修改项
	 * 
	 * @param insuredChangeInfoMap
	 *            修改记录
	 * @param appnt
	 *            被保人信息
	 * @param certificateInfo
	 *            证件信息
	 * @param sexInfo
	 *            性别星系
	 * @return true:修改 false:未修改
	 */
	private Map<String, Boolean> checkInsuredChange(Map<String, List<PolicyChangeInfoSchema>> insuredChangeInfoMap, SDInformationInsuredSchema insured, Map<String, String> certificateInfo,
			Map<String, String> sexInfo, Map<String, String> relationInfo, DataRow dr,boolean destionationchange,Map<String, String> destionationinfo) {
		List<PolicyChangeInfoSchema> changeInfoList = new ArrayList<PolicyChangeInfoSchema>();
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		boolean baodanChange = false;
		boolean birthdayChange = false;
		// 被保人姓名
		String recognizeeName = dr.getString("recognizeeName");
		// 被保人证件类型
		String recognizeeIdentityType = dr.getString("recognizeeIdentityType").split("_")[0];
		// 被保人证件号码
		String recognizeeIdentityId = dr.getString("recognizeeIdentityId");
		// 被保人性别
		String recognizeeSex = dr.getString("recognizeeSex");
		// 被保人出生日期
		String recognizeeBirthday = dr.getString("recognizeeBirthday");
		// 被保人手机号码
		String recognizeeMobile = dr.getString("recognizeeMobile");
		// 被保人邮箱
		String recognizeeMail = dr.getString("recognizeeMail");
		// 与投保人关系
		String recognizeeAppntRelation = dr.getString("recognizeeAppntRelation");
		String recognizeeAppntRelationName = relationInfo.get(recognizeeAppntRelation);
		// 被保人英文名
		String recognizeeEnName = dr.getString("recognizeeEnName");
        //旅游目的地
		if(destionationchange){
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("20");
			schema.setbeforeValue(insured.getdestinationCountry());
			schema.setafterValue(destionationinfo.get("destinationCountry"));
			changeInfoList.add(schema);

			insured.setdestinationCountry(destionationinfo.get("destinationCountry"));
			insured.setdestinationCountryText(destionationinfo.get("destinationCountryText"));
			baodanChange=true;
		}
		// 被保人姓名
		if (!insured.getrecognizeeName().equals(recognizeeName)) {
			// 被保人姓名变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("09");
			schema.setbeforeValue(insured.getrecognizeeName());
			schema.setafterValue(recognizeeName);
			changeInfoList.add(schema);

			insured.setrecognizeeName(recognizeeName);
			baodanChange = true;
		}
		// 被保人证件类型
		if (!insured.getrecognizeeIdentityType().equals(recognizeeIdentityType)) {
			String newTypeName = certificateInfo.get(dr.getString("recognizeeIdentityType"));
			// 被保人证件类型变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("10");
			schema.setbeforeValue(insured.getrecognizeeIdentityTypeName());
			schema.setafterValue(newTypeName);
			changeInfoList.add(schema);

			insured.setrecognizeeIdentityType(recognizeeIdentityType);
			insured.setrecognizeeIdentityTypeName(newTypeName);
			baodanChange = true;
		}
		// 被保人证件号码
		if (!insured.getrecognizeeIdentityId().equals(recognizeeIdentityId)) {
			// 被保人证件号码变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("11");
			schema.setbeforeValue(insured.getrecognizeeIdentityId());
			schema.setafterValue(recognizeeIdentityId);
			changeInfoList.add(schema);

			insured.setrecognizeeIdentityId(recognizeeIdentityId);
			baodanChange = true;
		}
		// 被保人性别
		if (!insured.getrecognizeeSex().equals(recognizeeSex)) {
			// 被保人性别变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("12");
			schema.setbeforeValue(insured.getrecognizeeSexName());
			schema.setafterValue(sexInfo.get(recognizeeSex));
			changeInfoList.add(schema);

			insured.setrecognizeeSex(recognizeeSex);
			insured.setrecognizeeSexName(sexInfo.get(recognizeeSex));
			baodanChange = true;
		}
		// 被保人出生日期
		if (!insured.getrecognizeeBirthday().equals(recognizeeBirthday)) {
			// 被保人出生日期变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("13");
			schema.setbeforeValue(insured.getrecognizeeBirthday());
			schema.setafterValue(recognizeeBirthday);
			changeInfoList.add(schema);

			insured.setrecognizeeBirthday(recognizeeBirthday);
			baodanChange = true;
			birthdayChange = true;
		}
		// 被保人手机号码
		if (!recognizeeMobile.equals(insured.getrecognizeeMobile())) {
			// 被保人手机号码变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("14");
			schema.setbeforeValue(insured.getrecognizeeMobile());
			schema.setafterValue(recognizeeMobile);
			changeInfoList.add(schema);

			insured.setrecognizeeMobile(recognizeeMobile);
			baodanChange = true;
		}
		// 被保人邮箱
		if (StringUtil.isNotEmpty(insured.getrecognizeeMail()) && !insured.getrecognizeeMail().equals(recognizeeMail)) {
			// 被保人邮箱变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("15");
			schema.setbeforeValue(insured.getrecognizeeMail());
			schema.setafterValue(recognizeeMail);
			changeInfoList.add(schema);

			insured.setrecognizeeMail(recognizeeMail);
			baodanChange = true;
		}
		// 被保人英文名
		if (StringUtil.isNotEmpty(insured.getrecognizeeEnName()) && !insured.getrecognizeeEnName().equals(recognizeeEnName)) {
			// 被保人英文名变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("17");
			schema.setbeforeValue(insured.getrecognizeeEnName());
			schema.setafterValue(recognizeeEnName);
			changeInfoList.add(schema);

			insured.setrecognizeeEnName(recognizeeEnName);
			baodanChange = true;
		}
		// 与投保人关系
		if (!insured.getrecognizeeAppntRelation().equals(recognizeeAppntRelation)) {
			// 与投保人关系变更记录
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("18");
			schema.setbeforeValue(insured.getrecognizeeAppntRelationName());
			schema.setafterValue(recognizeeAppntRelationName);
			changeInfoList.add(schema);

			insured.setrecognizeeAppntRelation(recognizeeAppntRelation);
			insured.setrecognizeeAppntRelationName(recognizeeAppntRelationName);
			baodanChange = true;
		}
		if (baodanChange) {
			insuredChangeInfoMap.put(insured.getId(), changeInfoList);
		}
		result.put("baodanChange", baodanChange);
		result.put("birthdayChange", birthdayChange);
		return result;
	}

	/**
	 * 校验保单是否承保成功
	 * 
	 * @param appChange
	 *            投保人是否变更
	 * @param changePreiod
	 *            起保期限是否变更
	 * @param dataDT
	 *            前台被保人信息
	 * @return 错误信息
	 */
	private String checkAppstatus(boolean appChange, boolean changePreiod, DataTable dataDT,boolean changedestionation) {
		int rowcount = dataDT.getRowCount();
		// 投保人或起保期限变更的情况 被保人必须全选
		if (appChange || changePreiod||changedestionation) {
			// 是否全选标识
			boolean checkflag = false;
			for (int i = 0; i < rowcount; i++) {
				if ("1".equals(dataDT.getString(i, "appFlag")) && !"1".equals(dataDT.getString(i, "checkflag"))) {
					checkflag = true;
				}
				// 承保状态为未承保的情况
				if ("0".equals(dataDT.getString(i, "appStatus")) || StringUtil.isEmpty(dataDT.getString(i, "appStatus"))) {
					return "第" + (i + 1) + "行保单未承保成功、承保状态是“" + dataDT.getString(i, "appStatus") + "”，请承保后再变更！";
				}
				// 保单号为空的情况
				if ("1".equals(dataDT.getString(i, "appStatus")) && StringUtil.isEmpty(dataDT.getString(i, "policyNo"))) {
					return "第" + (i + 1) + "行保单未承保成功、保单号为空，请承保后再变更！";
				}
			}
			// 承保状态下没全选的情况（不包括撤单、已变更）
			if (checkflag) {
				return "投保人信息或起保期限或旅游目的地变更，请选择全部保单！若保单信息分页，则请分别每页全选变更一次！";

			}
		} else {
			// 只被保人信息变更的情况 只校验选中的
			for (int i = 0; i < rowcount; i++) {
				// 对选中的保单进行校验
				if ("1".equals(dataDT.getString(i, "checkflag"))) {
					// 承保状态为未承保的情况
					if ("0".equals(dataDT.getString(i, "appStatus")) || StringUtil.isEmpty(dataDT.getString(i, "appStatus"))) {
						return "第" + (i + 1) + "行保单未承保成功、承保状态是“" + dataDT.getString(i, "appStatus") + "”，请承保后再变更！";
					}
					// 保单号为空的情况
					if ("1".equals(dataDT.getString(i, "appStatus")) && StringUtil.isEmpty(dataDT.getString(i, "policyNo"))) {
						return "第" + (i + 1) + "行保单未承保成功、保单号为空，请承保后再变更！";
					}
				}
			}
		}
		return "";
	}

	/**
	 * 保单一键变更操作
	 */
	public void policyChange() {
		SDInformationRiskTypeSet risktypeSet = new SDInformationRiskTypeSet();
		List<SDInformationRiskTypeSchema> risktypeList = new ArrayList<SDInformationRiskTypeSchema>();
		SDInformationRiskTypeSchema risktype = new SDInformationRiskTypeSchema();
		SDInformationInsuredSet insuredSet = new SDInformationInsuredSet();
		List<SDInformationInsuredSchema> insuredList = new ArrayList<SDInformationInsuredSchema>();
		SDInformationInsuredSchema insured = new SDInformationInsuredSchema();
		SDInformationDutySchema duty = new SDInformationDutySchema();
		SDInformationDutySet dutySet = new SDInformationDutySet();
		SDInformationDutySchema duty_change = new SDInformationDutySchema();
		SDInformationDutySet dutyset_change = new SDInformationDutySet();
		SDInformationBnfSet newBnfSet = new SDInformationBnfSet();
		// 变更记录
		List<PolicyChangeInfoSchema> changeInfoList = new ArrayList<PolicyChangeInfoSchema>();
		// 被保人变更记录
		Map<String, List<PolicyChangeInfoSchema>> insuredChangeInfoMap = new HashMap<String, List<PolicyChangeInfoSchema>>();
		Date now = new Date();
		// 订单表ID
		String orderId = $V("id");
		// 订单号
		String orderSn = $V("orderSn");
		// 订单信息表ID
		String informationId = $V("informationId");
		// 投保人ID
		String applicantId = $V("applicantId");
		// 保险起期
		String svalidate = $V("svalidate");
		String svalitime = $V("svalitime");
		// 保险止期
		String evaliDate = $V("evaliDate");
		
		String NDestination =  $V("NDestination");
				
		String FDestination =  $V("FDestination");
		
		String destination =  $V("destination");
		// 保单信息
		DataTable dataDT = Request.getDataTable("Data");
		// 健康告知信息
		DataTable healDT = Request.getDataTable("healData");

		/* 校验参数是否有值 */
		if (StringUtil.isEmpty(orderId)) {
			Response.setLogInfo(0, "订单表ID为空，不能变更！");
			return;
		}
		if (StringUtil.isEmpty(orderSn)) {
			Response.setLogInfo(0, "订单号为空，不能变更！");
			return;
		}
		if (StringUtil.isEmpty(informationId)) {
			Response.setLogInfo(0, "订单信息表ID为空，不能变更！");
			return;
		}
		if (StringUtil.isEmpty(applicantId)) {
			Response.setLogInfo(0, "投保信息ID为空，不能变更！");
			return;
		}
		if (dataDT == null || dataDT.getRowCount() < 1) {
			Response.setLogInfo(0, "无保单信息，不能变更！");
			return;
		}
		// 取得订单表信息
		SDOrdersSchema sdorder = new SDOrdersSchema();
		sdorder.setid(orderId);
		if (!sdorder.fill()) {
			Response.setLogInfo(0, "数据库中订单表中不存在订单信息！");
			return;
		}
		String oldPaySn = sdorder.getpaySn();
		if (StringUtil.isEmpty(oldPaySn)) {
			Response.setLogInfo(0, "数据库中订单表中交易流水号为空！");
			return;
		}
		String oldordersn = sdorder.getorderSn();
		if (StringUtil.isEmpty(oldordersn)) {
			Response.setLogInfo(0, "数据库中订单表中订单号为空！");
			return;
		}

		// 取得订单基础信息
		SDInformationSchema information = new SDInformationSchema();
	
		information.setId(informationId);
		if (!information.fill()) {
			Response.setLogInfo(0, "数据库中不存在订单基础信息！");
			return;
		}
		int tLen = healDT.getRowCount();
		if (healDT != null && tLen >= 1) {
			if (!"1015".equals(information.getinsuranceCompany())) {
				for (int l = 0; l < tLen; l++) {
					if ("Y".equals(healDT.getString(l, "selectFlag"))) {
						Response.setLogInfo(0, "健康告知第" + (l + 1) + "条选项（Y）不符合投保要求，请重新选择！");
						return;
					}
				}
			}
		}
		String company=information.getinsuranceCompany();
		String productid=information.getproductId();
		// 是否修改起保日期标识
		boolean changePreiod = false;
		Date startDate = DateUtil.parse((svalidate + " " + svalitime), "yyyy-MM-dd HH:mm:ss");
		if (startDate.compareTo(information.getstartDate()) != 0) {
			PolicyChangeInfoSchema schema = new PolicyChangeInfoSchema();
			schema.setchangeType("01");
			schema.setbeforeValue(information.getstartDate().toString());
			schema.setafterValue(svalidate + " " + svalitime);
			changeInfoList.add(schema);
			information.setstartDate(startDate);
			information.setendDate(DateUtil.parse(evaliDate, "yyyy-MM-dd HH:mm:ss"));
			changePreiod = true;
		}

		// 起保日期的校验
		String message = checkPeriod(information.getproductId(), (svalidate + " " + svalitime), evaliDate);
		if (StringUtil.isNotEmpty(message)) {
			Response.setLogInfo(0, message);
			return;
		}
		//旅游目的校验
		QueryBuilder qb_des = new QueryBuilder("SELECT destinationCountry,destinationCountryText FROM sdinformationinsured WHERE ordersn=? ");
		qb_des.add(orderSn);
		DataTable dt_des = qb_des.executeDataTable();
		String destinationCountrySelect="";
		String destinationCountryTextSelect="";
		String destinationCountry="";
		String destinationCountryText="";
		boolean changeDetionationSelect= false;
		if(dt_des.getRowCount()>0){
			destinationCountrySelect=dt_des.get(0).getString(0);
			destinationCountryTextSelect=dt_des.get(0).getString(1);
			destinationCountry=destinationCountrySelect;
			destinationCountryText=destinationCountryTextSelect;
			changeDetionationSelect=true;
		}
		
		
		if(StringUtil.isNotEmpty(destinationCountrySelect)){
			if(StringUtil.isEmpty(destination)&&StringUtil.isEmpty(NDestination)){
				Response.setLogInfo(0, "旅游目的地不能为空");
				return;
			}
			
		}	
		
		Map<String, String> destionationinfo = new HashMap<String, String>();
		boolean changeDetionation = false;
		QueryBuilder qbScope = new QueryBuilder("SELECT codevalue FROM zdcode WHERE codetype='singleDestination' AND parentcode='singleDestination'");
		DataTable dtScope = qbScope.executeDataTable();
		boolean singleFlag = false;
		if (dtScope != null && dtScope.getRowCount() > 0) {
			for (int j = 0; j < dtScope.getRowCount(); j++) {
				if (dtScope.getString(j, "codevalue").equals(company)) {
					singleFlag = true;
					break;
				}
			}
		}
		if(changeDetionationSelect){
			if(singleFlag&&StringUtil.isNotEmpty(destinationCountrySelect)){
				if(!destinationCountrySelect.equals(destination)){
					destinationCountry=destination;
					destinationCountryText=QueryOrders.getCodeNameByCodeValue(company, "CountryCode", destination, productid);
					changeDetionation=true;
				}
			}else{
				if(StringUtil.isNotEmpty(NDestination)){
					String[] destinationnameStr=NDestination.split(",");
					String destinationvalue="";
					for(int i=0;i<destinationnameStr.length;i++){
						if(i==0){
							destinationvalue+=QueryOrders.getCodeValueByCodeName(productid,company,"CountryCode",destinationnameStr[i]);
						}else{
							destinationvalue+=","+QueryOrders.getCodeValueByCodeName(productid,company,"CountryCode",destinationnameStr[i]);
						}
					}
					if(StringUtil.isNotEmpty(destinationvalue)){
					String[]  destinationvalue_temp=destinationvalue.split(",");
					String[]  destinationCountrySelect_temp=destinationCountrySelect.split(",");
					boolean  diffflagN=false;
					if(destinationvalue_temp.length!=destinationCountrySelect_temp.length){
						diffflagN=false;
					}else{
						for(int i =0;i<destinationvalue_temp.length;i++){
							diffflagN=false;
							for(int j =0;j<destinationCountrySelect_temp.length;j++){
								if(destinationvalue_temp[i].equals(destinationCountrySelect_temp[j])){
									diffflagN=true;
									break;
								}
							}
						}
					}
					if(!diffflagN){
						changeDetionation=true;
					}
					if(changeDetionation){
						destinationCountry=destinationvalue;
						destinationCountryText=QueryOrders.setDestinationCountry(company, destinationCountry);
						if ("2007".equals(company) && destinationCountry != null && !"".equals(destinationCountry)) {
							if (destinationCountry != null && !"".equals(destinationCountry)) {
								String CountryText = QueryOrders.getCountryText2007("2007", destinationCountry);
								destinationCountryText=CountryText;
							}
						} else if ("1015".equals(company) && destinationCountry != null && !"".equals(destinationCountry)) {
						
							destinationCountryText= QueryOrders.getCountryText1015("1015", destinationCountry);
						} else if (("2011".equals(company) || "2023".equals(company) || "2071".equals(company)) && destinationCountry != null
								&& !"".equals(destinationCountry)) {
							destinationCountryText=QueryOrders.getSchengenCountryText(company, destinationCountry,productid);
						}
					}
					
					
				}
			}
		}
			destionationinfo.put("destinationCountry", destinationCountry);
			destionationinfo.put("destinationCountryText", destinationCountryText);
		}

		// 取得该产品的证件类型
		Map<String, String> certificateInfo = QueryOrders.getCertificateSelect(information.getinsuranceCompany());
		// 取得该产品的性别信息
		Map<String, String> sexInfo = QueryOrders.getSexSelect(information.getinsuranceCompany());
		// 取得该产品的投被保人关系
		Map<String, String> relationInfo = QueryOrders.getRelationSelect(information.getinsuranceCompany(), information.getproductId());
		// 取得投保人信息
		SDInformationAppntSchema appnt = new SDInformationAppntSchema();
		appnt.setId(applicantId);
		if (!appnt.fill()) {
			Response.setLogInfo(0, "数据库中不存在投保信息！");
			return;
		}

		/* 对比投保人信息是否修改 */
		boolean appChange = checkAppntChange(changeInfoList, appnt, certificateInfo, sexInfo);
		// 校验保单是否承保成功
		message = checkAppstatus(appChange, changePreiod, dataDT,changeDetionation);
		// 有错误信息提示
		if (StringUtil.isNotEmpty(message)) {
			Response.setLogInfo(0, message);
			return;
		}

		// 取得渠道编码
		String channelCode = new QueryBuilder("select channelCode from sdorderitem where orderSn=?", orderSn).executeString();

		SDOrderServiceImpl service = new SDOrderServiceImpl();
		Map<String, Object> paramter1 = new HashMap<String, Object>();
		Map<String, Object> insuredPremMap = new HashMap<String, Object>();
		// 责任信息
		Map<String, String> dutyJson = new HashMap<String, String>();
		// 取得责任信息
		duty.setorderSn(orderSn);
		dutySet = duty.query();
		if (dutySet != null && dutySet.size() > 0) {
			int dutySize = dutySet.size();
			for (int i = 0; i < dutySize; i++) {
				dutyJson.put(dutySet.get(i).getdutySn(), dutySet.get(i).getamt());
			}
		}

		// 取得限购份数和产品类型
		String sql = "SELECT LimitCount,ProductType,ComplicatedFlag FROM sdproduct WHERE productid=? ";
		DataTable dt = new QueryBuilder(sql, information.getproductId()).executeDataTable();
		int limitCount = 0;
		String productType = "";
		String complicatedFlag = "";
		if (dt != null && dt.getRowCount() > 0) {
			if (StringUtil.isNotEmpty(dt.getString(0, 0))) {
				limitCount = dt.getInt(0, 0);
			}
			
			productType = dt.getString(0, 1);
			complicatedFlag = dt.getString(0, 2);
		}

		// 变更后保险公司折后订单总金额
		BigDecimal totalAmount = new BigDecimal(0);
		BigDecimal payPrice = new BigDecimal(0);//实际支付金额
		BigDecimal orderCoupon = new BigDecimal(0);//优惠券
		BigDecimal orderIntegral = new BigDecimal(0);//积分优惠
		BigDecimal orderActivity = new BigDecimal(0);//活动优惠
		// 变更后原价订单总金额
		BigDecimal sumProductPrem = new BigDecimal(0);
		Map<String, Boolean> baodanMap = new HashMap<String, Boolean>();
		SDInformationInsuredSchema insuredTemp = new SDInformationInsuredSchema();
		int rowcount = dataDT.getRowCount();
		for (int i = 0; i < rowcount; i++) {
			// 对选中的保单取得相关的保单表信息和被保人表信息
			if ("1".equals(dataDT.getString(i, "checkflag")) && "1".equals(dataDT.getString(i, "appFlag"))) {

				// 取得保单表信息
				risktype = new SDInformationRiskTypeSchema();
				risktype.setId(dataDT.getString(i, "baodanId"));
				if (!risktype.fill()) {
					Response.setLogInfo(0, "第" + (i + 1) + "行的保单信息在数据库中不存在，不能变更！");
					return;
				}
				risktypeList.add(risktype);

				if (!"0".equals(risktype.getbalanceStatus()) && "1".equals(risktype.getappStatus())) {
					Response.setLogInfo(0, "保单：" + risktype.getpolicyNo() + "未结算，不能变更！");
					return;
				}
				if ("2".equals(risktype.getappStatus()) || "4".equals(risktype.getappStatus())) {
					Response.setLogInfo(0, "保单：" + risktype.getpolicyNo() + "已撤单，不能变更！");
					return;
				}
				// 取得被保人表信息
				insured = new SDInformationInsuredSchema();
				insured.setId(risktype.getsdinformationinsured_id());

				if (!insured.fill()) {
					Response.setLogInfo(0, "第" + (i + 1) + "行的被保人信息在数据库中不存在，不能变更！");
					return;
      				}

				insuredTemp = new SDInformationInsuredSchema();
				insuredTemp.setId(risktype.getsdinformationinsured_id());
				insuredTemp.fill();

				// 投保人信息、保险期限未修改的情况，与投保人关系是本人未变更成其他的情况不能变更
				if (!appChange && !changePreiod && !changeDetionation) {
					if (insured.getrecognizeeAppntRelationName().indexOf("本人") > 0 && insured.getrecognizeeAppntRelation().equals(dataDT.get(i).getString("recognizeeAppntRelation"))) {
						Response.setLogInfo(0, "第" + (i + 1) + "行的与投保人关系是本人，投保人信息、保险期限未修改,无需变更！");
						return;
					}
				}
				// 被保人出生日期是否修改
				boolean birthdayChange = false;
				boolean baodanChange = false;
				// 当与投保人关系不是本人或未变更成本人时、校验被保人信息是否修改
				if (!"本人".equals(relationInfo.get(dataDT.getString(i, "recognizeeAppntRelation"))) || !"本人".equals(insured.getrecognizeeAppntRelationName())) {
					baodanMap = checkInsuredChange(insuredChangeInfoMap, insured, certificateInfo, sexInfo, relationInfo, dataDT.get(i),changeDetionation,destionationinfo);
					baodanChange = baodanMap.get("baodanChange");
					birthdayChange = baodanMap.get("birthdayChange");
				}
				if(changeDetionation){
					insured.setdestinationCountry(destionationinfo.get("destinationCountry"));
					insured.setdestinationCountryText(destionationinfo.get("destinationCountryText"));
					baodanChange=true;
				}

				// 若只修改被保人信息，则校验选中的被保人信息是否修改
				if (!appChange && !changePreiod) {
					// 被保人信息未修改，不能变更
					if (!baodanChange) {
						Response.setLogInfo(0, "第" + (i + 1) + "行的被保人信息未修改,无需变更！");
						return;
					}
				}

				// 投保人信息修改并关系是本人的情况 被保人复制投保人信息
				if ((appChange && "本人".equals(relationInfo.get(dataDT.getString(i, "recognizeeAppntRelation"))))
						|| (!"本人".equals(insuredTemp.getrecognizeeAppntRelationName()) && "本人".equals(relationInfo.get(dataDT.getString(i, "recognizeeAppntRelation"))))) {
					copyAppnt(appnt, insured);
				}
				// 限购份数校验
				if (limitCount > 0) {
					// 保险期限或证件类型或证件号变更 进行份数校验
					if (changePreiod || !insuredTemp.getrecognizeeIdentityType().equals(insured.getrecognizeeIdentityType())
							|| !insuredTemp.getrecognizeeIdentityId().equals(insured.getrecognizeeIdentityId())) {
						if (!checkProductLimit(information, productType, insured.getrecognizeeIdentityType(), insured.getrecognizeeIdentityId(), insuredTemp.getId(), limitCount, changePreiod)) {
							Response.setLogInfo(0, "第" + (i + 1) + "行的被保人已超过限购份数，不能变更！");
							return;
						}
					}

				}

				insuredList.add(insured);

				// 起保日期修改或投保人出生日期修改 进行保费试算
				if (changePreiod || birthdayChange) {
					if (paramter1 == null || paramter1.isEmpty()) {
						paramter1 = service.getProductInformation(information.getproductId(), "N", channelCode);
						if (paramter1 == null || paramter1.isEmpty() || paramter1.containsKey("error")) {
							Response.setLogInfo(0, "与产品中心交互未取得产品信息，请稍后再变更！");
							return;
						}
					}
					paramter1.put("complicatedFlag", complicatedFlag);
					// 取得保费试算后的新保费
					double insuredPrem = getInsuredPrem(paramter1, insuredPremMap, dutyJson, information, dataDT.get(i), svalidate, service);
					// 保费与原来不一致的情况，不能变更
					if (insuredPrem != Double.valueOf(risktype.gettimePrem())) {
						Response.setLogInfo(0, "第" + (i + 1) + "行的保单变更前后保费不一致，无法变更！");
						return;
					}
				}

				totalAmount = totalAmount.add(new BigDecimal(risktype.gettimePrem()));
				payPrice = payPrice.add(new BigDecimal(risktype.getpayPrice()));
				sumProductPrem = sumProductPrem.add(new BigDecimal(risktype.getproductPrice()));
				if(StringUtil.isNotEmpty(risktype.getcouponValue())){
					orderCoupon = orderCoupon.add(new BigDecimal(risktype.getcouponValue()));
				}
				if(StringUtil.isNotEmpty(risktype.getintegralValue())){
					orderIntegral = orderIntegral.add(new BigDecimal(risktype.getintegralValue()));
				}
				if(StringUtil.isNotEmpty(risktype.getactivityValue())){
					orderActivity = orderActivity.add(new BigDecimal(risktype.getactivityValue()));
				}
			}
		}
		
		//设置订单相关优惠和实际支付金额为保单之和
		sdorder.setorderCoupon(orderCoupon.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		sdorder.setorderIntegral(orderIntegral.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		sdorder.setorderActivity(orderActivity.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		sdorder.setpayAmount(payPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		sdorder.setpayPrice(payPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

		// 变更的保单进行先撤单再承保的操作
		int risktypeListSize = risktypeList.size();
		if (risktypeListSize > 0) {
			Transaction trans = new Transaction();
			// 新生成订单表数据
			makeSDOrder(sdorder, sumProductPrem, totalAmount, now, trans);
			// 新生成订单信息表数据
			makeInformation(sdorder, information, now, trans);
			// 新生成订单明细表
			makeOrderItemSave(orderSn, sdorder, information, trans);
			// 新生成投保人表数据
			makeSDInformationAppnt(appnt, information, now, trans);
			// 新生成责任信息表数据
			makeSDInformationDuty(duty_change,dutyset_change,dutySet, information, now, trans);
			// 新生成交易信息数据
			makeTradeInfomation(sdorder, now, trans, orderSn, oldPaySn);
			// 新生成
			makeInsured(insuredList, risktypeList, information, appnt.getapplicantSn(), now, trans, insuredSet, risktypeSet, healDT,newBnfSet);
			//银行信息表 
			directpaybankinfoinsert(trans,sdorder,oldordersn);
			if (!trans.commit()) {
				Response.setLogInfo(0, "生成数据失败！变更失败！");
				return;
			}

			String errorMessage = "";
			String failRiskTypeIds = "";
			String failInsuredIds = "";
			List<String> changedRiskType = new ArrayList<String>();
			boolean cancleFlag = false;
			for (int i = 0; i < risktypeListSize; i++) {
				// 撤单
				CancelContService tCancelContService = new CancelContService();
				String riskTypeId = risktypeList.get(i).getId();
				String flag = tCancelContService.callInsTransInterface(information.getinsuranceCompany(), orderSn, insuredList.get(i).getinsuredSn(), riskTypeId);
				// 保险公司撤单成功后 更新系统数据状态
				if ("SUCCESS".equals(flag)) {
					// 更新保单表的撤单信息
					SDInformationRiskTypeSchema sdRiskTypeSchema = new SDInformationRiskTypeSchema();
					sdRiskTypeSchema.setId(riskTypeId);
					if (sdRiskTypeSchema.fill()) {
						sdRiskTypeSchema.setappStatus("4");// 撤单成功后状态更新为2
						sdRiskTypeSchema.setcancelDate(new Date());
						sdRiskTypeSchema.update();
					}
					// 更新订单表的订单状态
					tCancelContService.changeSDOrders(orderSn);
					//订单变更调用结算中心撤单码改成04，结算中心根据04判断是订单变更撤单，此种情况结算中心不给相应人员发撤单提醒邮件
					if (!tCancelContService.callProductInterface(orderSn, "", PubFun.getCurrentDate(), "04", sdRiskTypeSchema.getpolicyNo(), riskTypeId, "", "")) {
						logger.error("保单：{} 撤单结算中心调用失败！", sdRiskTypeSchema.getpolicyNo());
					}
					// 记录变更前后的保单表ID：变更前保单表ID,变更后保单表ID
					changedRiskType.add(riskTypeId + "," + risktypeSet.get(i).getId());
					PolicyChangeInfoSchema policyChangeInfo =new PolicyChangeInfoSchema();
					String sql_Init = "select beforeValue from PolicyChangeInfo where newPaySn = ? and newOrderSn = ?  and changeType='19' order by createdate asc limit 0, 1";
					QueryBuilder qb_Init = new QueryBuilder(sql_Init, oldPaySn, orderSn);
					String	beforvalue = qb_Init.executeString();
					if (StringUtil.isNotEmpty(beforvalue)) {
					policyChangeInfo.setbeforeValue(beforvalue);
					}else{
						policyChangeInfo.setbeforeValue(riskTypeId);
					}
					policyChangeInfo.setchangeType("19");
					policyChangeInfo.setafterValue(risktypeSet.get(i).getId());
					changeInfoList.add(policyChangeInfo);
					//TODO
					//快钱渠道撤单调用接口
					if (Constant.BILL_KQ_CHANNELSN.equals(sdorder.getchannelsn())) {
						try {
							// 发送消息队列到消费者
							Map<String, String> dataSendMap = new HashMap<String, String>();
							dataSendMap.put("type", "2");
							dataSendMap.put("orderSn", orderSn);
							dataSendMap.put("operator", "dealBillCallback");
							ProducerMQ mq = new ProducerMQ();
							mq.sendToCallBackPolicy(JSON.toJSONString(dataSendMap));

						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
				} else {
					String tempMessage = (flag.indexOf("|")==-1)?"撤单调用经代通失败":flag.substring(flag.indexOf("|"));
					errorMessage += "保单：" + risktype.getpolicyNo() + "保险公司撤单失败，失败原因："+tempMessage+"，未变更！";
					// 撤单失败删除变更记录
					if (insuredChangeInfoMap.containsKey(insuredList.get(i).getId())) {
						insuredChangeInfoMap.remove(insuredList.get(i).getId());
					}
					failRiskTypeIds += risktypeSet.get(i).getId() + ",";
					failInsuredIds += insuredSet.get(i).getId() + ",";
					continue;
				}
				cancleFlag = true;
				totalAmount = totalAmount.add(new BigDecimal(risktype.gettimePrem()));
				sumProductPrem = sumProductPrem.add(new BigDecimal(risktype.getproductPrice()));
			}

			// 全部撤单操作失败的情况， 删除所有数据
			if (!cancleFlag) {
				trans = new Transaction();
				trans.add(new QueryBuilder("delete from sdorders where ordersn = ?", sdorder.getorderSn()));
				trans.add(new QueryBuilder("delete from sdinformation where ordersn = ?", sdorder.getorderSn()));
				trans.add(new QueryBuilder("delete from sdinformationappnt where informationsn = ?", information.getinformationSn()));
				trans.add(dutyset_change, Transaction.DELETE);
				trans.add(insuredSet, Transaction.DELETE);
				trans.add(risktypeSet, Transaction.DELETE);
				trans.add(new QueryBuilder("delete from TradeInformation where ordID = ?", sdorder.getorderSn()));
				trans.add(new QueryBuilder("delete from TradeSummaryInfo where OrderSns = ?", sdorder.getorderSn()));
				trans.add(new QueryBuilder("delete from SDOrderItemOth where orderSn = ?", sdorder.getorderSn()));
				trans.add(new QueryBuilder("delete from SDRemark where orderSn = ?", sdorder.getorderSn()));
				trans.add(new QueryBuilder("delete from DirectPayBankInfo where orderSn = ?", sdorder.getorderSn()));
				trans.add(newBnfSet,Transaction.DELETE);
				trans.commit();
				Response.setLogInfo(0, errorMessage);
				return;

			} else if (StringUtil.isNotEmpty(failRiskTypeIds)) {// 有撤单失败的情况，删除数据
				trans = new Transaction();
				failRiskTypeIds = failRiskTypeIds.substring(0, failRiskTypeIds.length() - 1);
				failInsuredIds = failInsuredIds.substring(0, failInsuredIds.length() - 1);
				failRiskTypeIds = failRiskTypeIds.replace(",", "','");
				failInsuredIds = failInsuredIds.replace(",", "','");
				trans.add(new QueryBuilder("delete from SDInformationRiskType where id in ('" + failRiskTypeIds + "')"));
				trans.add(new QueryBuilder("delete from SDInformationInsured where id in ('" + failInsuredIds + "')"));
				trans.add(new QueryBuilder("delete from SDOrderItemOth where sdinformationinsured_id in ('" + failInsuredIds + "')"));
				trans.add(new QueryBuilder("delete from sdinformationbnf where recognizeeSn in ("
						+ "select recognizeeSn from SDInformationInsured where id in ('" + failInsuredIds + "'))"));
				// 更新订单价格
				sdorder.setproductTotalPrice(sumProductPrem.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				sdorder.settotalAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
				sdorder.setpayAmount(sdorder.gettotalAmount());
				sdorder.setpayPrice(sdorder.gettotalAmount());
				trans.add(sdorder, Transaction.UPDATE);
				information.setproductPrice(sdorder.getproductTotalPrice());
				information.setproductDiscountPrice(sdorder.gettotalAmount());
				trans.add(information, Transaction.UPDATE);
				trans.add(new QueryBuilder("update TradeInformation set ordAmt = ? where ordID = ?", sdorder.gettotalAmount(), sdorder.getorderSn()));
				QueryBuilder qb = new QueryBuilder("update TradeSummaryInfo set TotalAmount = ?, TradeAmount = ? where OrderSns = ?");
				qb.add(sdorder.gettotalAmount());
				qb.add(sdorder.gettotalAmount());
				qb.add(sdorder.getorderSn());
				trans.add(qb);
				trans.commit();
			}

			// 保存变更记录 （变更履历由最下面挪动到这里，怀疑承保时候没有返回值走不到下面的变更记录更新）
			saveChangeInfo(orderSn, oldPaySn, sdorder.getorderSn(), sdorder.getpaySn(), changeInfoList, insuredChangeInfoMap);
			
			// 退款用（同 产品购买功能中增加的代客支付数据）
			saveBuyForCustomerOldSn(orderSn, sdorder.getorderSn());

			// 承保
			String errMessage = send(information.getinsuranceCompany(), sdorder.getorderSn());
			if (StringUtil.isNotEmpty(errMessage)) {
				Response.setLogInfo(0, "变更失败！" + errMessage);
			} else {
				// 有错误信息的情况
				if (StringUtil.isNotEmpty(errorMessage)) {
					Response.setLogInfo(0, "部分变更成功，部分失败：" + errorMessage);
				} else {
					Response.setLogInfo(1, "变更成功！");
					//TODO
					//快钱渠道撤单调用接口
					if (Constant.BILL_KQ_CHANNELSN.equals(sdorder.getchannelsn())) {
						try {
							// 发送消息队列到消费者
							Map<String, String> dataSendMap = new HashMap<String, String>();
							dataSendMap.put("type", "1");
							dataSendMap.put("orderSn", sdorder.getorderSn());
							dataSendMap.put("operator", "dealBillCallback");
							ProducerMQ mq = new ProducerMQ();
							mq.sendToCallBackPolicy(JSON.toJSONString(dataSendMap));
						} catch (Exception e) {
							logger.error(e.getMessage(), e);
						}
					}
				}
			}

			// 保存变更记录 
			//saveChangeInfo(orderSn, oldPaySn, sdorder.getorderSn(), sdorder.getpaySn(), changeInfoList, insuredChangeInfoMap);
			
			// 更新原订单状态、保单变更状态
			updateStatus(changedRiskType, orderSn);

			//合作商CPS变更后。插入CPS 表数据
			partnersCps(orderSn, sdorder.getorderSn(),sdorder.getchannelsn());
			// 结算到结算中心
			TBContInsureServlet tb = new TBContInsureServlet();
			tb.callFCContService(sdorder.getorderSn());
		} else {
			if (appChange || changePreiod||changeDetionation) {
				Response.setLogInfo(0, "没有可变更的保单！");
			} else {
				Response.setLogInfo(0, "无信息修改，无需变更！");
			}

		}
	}
	
	/**
	 * 订单变更 合作商CPS数据添加.
	 * @param p_OldOrderSn
	 * @param p_NewOrderSn
	 */
	public static void partnersCps(String p_OldOrderSn,String p_NewOrderSn,String p_Channelsn){
		
		try {
			QueryBuilder query_cps = new QueryBuilder("select * from cps c where c.on = ?",p_OldOrderSn);
			DataTable dt = query_cps.executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++) {
				String cpsUserId = dt.getString(i, "cid");
					String uid = dt.getString(i, "wi");
					String channel = dt.getString(i, "pw");
					String cpsUserource = dt.getString(i, "os");
					String productName = dt.getString(i, "pna");
					String totleprice = dt.getString(i, "pp");
					OrderConfigNewAction.recordCPS(cpsUserId, uid, channel, cpsUserource, p_NewOrderSn, productName, totleprice);
			}
			
			if (StringUtil.isEmpty(p_Channelsn)) {
				return;
			}
			
			String webServiceUrl = "";
			if (p_Channelsn.endsWith("dlr")) {
				webServiceUrl = Config.getValue("AGENTSERVERICEURL");
				dealCpsOrder(p_OldOrderSn, p_NewOrderSn, webServiceUrl);
			} else if (p_Channelsn.endsWith("swpt")) {
				webServiceUrl = Config.getValue("CPSSERVERICEURL");
				dealCpsOrder(p_OldOrderSn, p_NewOrderSn, webServiceUrl);
			}
			
		} catch (Exception e) {
			logger.error("订单变更：合作商INSERT异常! 订单号："+p_OldOrderSn + e.getMessage(), e);
		}
}

	/**
	 * saveBuyForCustomerOldSn:保存代客支付撤单重出数据. <br/>
	 *
	 * @author wwy
	 * @param p_OldOrderSn
	 * @param p_NewOrderSn
	 */
	private void saveBuyForCustomerOldSn(String p_OldOrderSn, String p_NewOrderSn){
		
		QueryBuilder qb = new QueryBuilder("select Ordersn, BuyForCustomerFlag, OldOrderSn from BuyForCustomerOldSn where ordersn = ?", p_OldOrderSn);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			BuyForCustomerOldSnSchema schema = new BuyForCustomerOldSnSchema();
			schema.setid(NoUtil.getMaxNo("buyForCustomerOldSn"));
			schema.setOrdersn(p_NewOrderSn);
			schema.setOldOrderSn(dt.getString(0, 2));
			schema.setBuyForCustomerFlag(dt.getString(0, 1));
			schema.setAddTime(new Date());
			schema.setAddUser(User.getUserName());
			schema.setProp1("订单变更");
			schema.insert();
		}
	}
	
	/**
	 * 限购份数的校验
	 * 
	 * @param info
	 *            订单信息
	 * @param type
	 *            险种类型
	 * @param identityType
	 *            证件类型
	 * @param identityId
	 *            证件号
	 * @param insuredId
	 *            被保险人Id
	 * @param limitCount
	 *            限购份数
	 * @param changePreiod
	 *            保险期限是否变更
	 * @return
	 */
	private boolean checkProductLimit(SDInformationSchema info, String type, String identityType, String identityId, String insuredId, int limitCount, boolean changePreiod) {

		StringBuffer sb = new StringBuffer();
		QueryBuilder qb;
		// 家财险的校验
		if ("C".equals(type)) {
			// 保险期限没变时 不用校验
			if (!changePreiod) {
				return true;
			}
			// 取得家庭地址
			String sql = "select propertyAdress, propertyArea1, propertyArea2,propertyArea3 from sdinformationproperty where sdinformationinsured_id = ?";
			qb = new QueryBuilder(sql, insuredId);
			DataTable dt = qb.executeDataTable();
			String propertyAdress = "";
			String propertyArea1 = "";
			String propertyArea2 = "";
			String propertyArea3 = "";
			if (dt != null && dt.getRowCount() > 0) {
				propertyAdress = dt.getString(0, 0);
				propertyArea1 = dt.getString(0, 1);
				propertyArea2 = dt.getString(0, 2);
				propertyArea3 = dt.getString(0, 3);
			} else {
				return true;
			}
			sb.append("SELECT COUNT(1) FROM SDInformation a, SDInformationInsured b, ");
			sb.append("SDInformationRiskType c,sdinformationproperty d ");
			sb.append("WHERE a.InformationSn = b.InformationSn AND b.RecognizeeSn =c.RecognizeeSn ");
			sb.append("AND b.RecognizeeSn =d.RecognizeeSn AND AppStatus ='1' ");
			sb.append("AND a.InformationSn = c.InformationSn AND a.ProductId = ? ");
			sb.append("AND d.propertyArea1 = ? AND d.propertyArea2 = ? AND d.propertyArea3 = ? AND d.propertyAdress = ? ");
			sb.append("AND ((a.StartDate <= ? AND a.StartDate >= ?) OR (a.EndDate >= ? AND a.EndDate <= ?))");
			qb = new QueryBuilder(sb.toString());
			qb.add(info.getproductId());
			qb.add(propertyArea1);
			qb.add(propertyArea2);
			qb.add(propertyArea3);
			qb.add(propertyAdress);
			qb.add(info.getendDate());
			qb.add(info.getstartDate());
			qb.add(info.getstartDate());
			qb.add(info.getendDate());

		} else {
			// 除家财险其他险种的校验
			sb.append("SELECT COUNT(1) FROM SDInformation a, SDInformationInsured b, ");
			sb.append("SDInformationRiskType c WHERE a.InformationSn = b.InformationSn ");
			sb.append("AND b.RecognizeeSn = c.RecognizeeSn AND c.AppStatus ='1' ");
			sb.append("AND a.InformationSn = c.InformationSn AND b.RecognizeeIdentityType = ? ");
			sb.append("AND b.RecognizeeIdentityId = ? AND a.productoutcode = ? ");
			sb.append("AND ((EndDate >=? AND StartDate <= ?) OR (startDate<=? AND endDate>= ?) ");
			sb.append("OR (StartDate >= ? AND EndDate <= ? )) ");
			qb = new QueryBuilder(sb.toString());
			qb.add(identityType);
			qb.add(identityId);
			qb.add(info.getproductOutCode());
			qb.add(info.getstartDate());
			qb.add(info.getstartDate());
			qb.add(info.getendDate());
			qb.add(info.getendDate());
			qb.add(info.getstartDate());
			qb.add(info.getendDate());
		}
		if (limitCount < qb.executeInt()) {
			return false;
		}

		return true;
	}

	/**
	 * 更新原订单状态、保单变更状态
	 * 
	 * @param changedRiskType
	 *            变更的保单ID
	 * @param oldOrderSn
	 *            原订单号
	 */
	private void updateStatus(List<String> changedRiskType, String oldOrderSn) {
		SDInformationRiskTypeSet oldRiskTypeSet = new SDInformationRiskTypeSet();
		SDInformationRiskTypeSchema oldRiskType = new SDInformationRiskTypeSchema();
		SDInformationRiskTypeSchema newRiskType = new SDInformationRiskTypeSchema();
		Transaction trans = new Transaction();
		if (changedRiskType != null && changedRiskType.size() > 0) {
			int size = changedRiskType.size();
			String[] ids;
			for (int i = 0; i < size; i++) {
				oldRiskType = new SDInformationRiskTypeSchema();
				newRiskType = new SDInformationRiskTypeSchema();
				ids = changedRiskType.get(i).split(",");
				oldRiskType.setId(ids[0]);
				if (!oldRiskType.fill()) {
					continue;
				}
				newRiskType.setId(ids[1]);
				if (!newRiskType.fill()) {
					continue;
				}
				// 撤单成功时
				if ("2".equals(oldRiskType.getappStatus())) {
					// 新保单承保成功的情况 原保单的变更状态更新为“1”
					if ("1".equals(newRiskType.getappStatus())) {
						oldRiskType.setchangeStatus("1");
					} else {
						// 新保单未承保成功的情况 原保单的变更状态更新成“3”
						oldRiskType.setchangeStatus("3");
					}

					// 结算中心撤单失败时
				} else if ("4".equals(oldRiskType.getappStatus())) {
					// 新保单承保成功的情况 原保单的变更状态更新为“2”
					if ("1".equals(newRiskType.getappStatus())) {
						oldRiskType.setchangeStatus("2");
					} else {
						// 新保单未承保成功的情况 原保单的变更状态更新成“4”
						oldRiskType.setchangeStatus("4");
					}

					// 其他状态不处理
				} else {
					continue;
				}
				oldRiskTypeSet.add(oldRiskType);
			}

			if (oldRiskTypeSet.size() > 0) {
				trans.add(oldRiskTypeSet, Transaction.UPDATE);
				trans.commit();
				trans.clear();
			}
			// 取得原订单的总保单数
			String sql = "select count(1) from SDInformationrisktype where orderSn = ? ";
			QueryBuilder qb = new QueryBuilder(sql, oldOrderSn);
			int sumCount = qb.executeInt();
			// 取得变更的保单数
			sql += "and changeStatus is not null and changeStatus != ''";
			qb = new QueryBuilder(sql, oldOrderSn);
			int changeCount = qb.executeInt();
			// 部分变更 更新订单状态为有作废
			if (sumCount > changeCount) {
				trans.add(new QueryBuilder("update sdorders set orderStatus = '14' where orderSn = ?", oldOrderSn));
			} else {
				// 全部变更 更新订单状态为已作废
				trans.add(new QueryBuilder("update sdorders set orderStatus = '13' where orderSn = ?", oldOrderSn));
			}
			trans.commit();
		}
	}

	/**
	 * 保存变更记录
	 * 
	 * @param oldOrderSn
	 *            旧订单号
	 * @param oldPaySn
	 *            旧交易号
	 * @param newOrderSn
	 *            新订单号
	 * @param newPaySn
	 *            新交易号
	 * @param changeInfoList
	 *            保险起期、投保人变更记录
	 * @param insuredChangeInfoMap
	 *            被保人变更记录
	 */
	private void saveChangeInfo(String oldOrderSn, String oldPaySn, String newOrderSn, String newPaySn, List<PolicyChangeInfoSchema> changeInfoList,
			Map<String, List<PolicyChangeInfoSchema>> insuredChangeInfoMap) {
		Date now = new Date();
		String user = User.getUserName();
		PolicyChangeInfoSet set = new PolicyChangeInfoSet();
		String initPaySn = oldPaySn;
		String initOrderSn = oldOrderSn;
		// 查询初始订单号和初始交易号
		String sql = "select initPaySn, initOrderSn from PolicyChangeInfo where newPaySn = ? and newOrderSn = ? order by createdate asc limit 0, 1";
		QueryBuilder qb = new QueryBuilder(sql, oldPaySn, oldOrderSn);
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			initPaySn = dt.getString(0, 0);
			initOrderSn = dt.getString(0, 1);
		}
		// 投保人或保险起期变更记录
		for (int i = 0; i < changeInfoList.size(); i++) {
			changeInfoList.get(i).setid(PubFun.GetChangeInfoID());
			changeInfoList.get(i).setcreateDate(now);
			changeInfoList.get(i).setcreateUser(user);
			changeInfoList.get(i).setnewPaySn(newPaySn);
			changeInfoList.get(i).setnewOrderSn(newOrderSn);
			changeInfoList.get(i).setoldPaySn(oldPaySn);
			changeInfoList.get(i).setoldOrderSn(oldOrderSn);
			changeInfoList.get(i).setinitPaySn(initPaySn);
			changeInfoList.get(i).setinitOrderSn(initOrderSn);
			set.add(changeInfoList.get(i));
		}

		// 被保人信息变更记录
		if (!insuredChangeInfoMap.isEmpty()) {
			List<PolicyChangeInfoSchema> list = null;
			Set<String> key = insuredChangeInfoMap.keySet();
			for (Iterator it = key.iterator(); it.hasNext();) {
				String s = (String) it.next();
				list = insuredChangeInfoMap.get(s);
				if (list != null && list.size() > 0) {
					int size = list.size();
					for (int i = 0; i < size; i++) {
						list.get(i).setid(PubFun.GetChangeInfoID());
						list.get(i).setcreateDate(now);
						list.get(i).setcreateUser(user);
						list.get(i).setnewPaySn(newPaySn);
						list.get(i).setnewOrderSn(newOrderSn);
						list.get(i).setoldPaySn(oldPaySn);
						list.get(i).setoldOrderSn(oldOrderSn);
						list.get(i).setinitPaySn(initPaySn);
						list.get(i).setinitOrderSn(initOrderSn);
						set.add(list.get(i));
					}
				}
			}
		}

		if (set.size() > 0) {
			Transaction trans = new Transaction();
			trans.add(set, Transaction.INSERT);
			trans.commit();
		}
	}

	/**
	 * 投保人基础信息复制到被保人信息中
	 * 
	 * @param appnt
	 *            投保人信息
	 * @param insured
	 *            被保人信息
	 */
	private void copyAppnt(SDInformationAppntSchema appnt, SDInformationInsuredSchema insured) {
		// 姓名
		insured.setrecognizeeName(appnt.getapplicantName());
		if (StringUtil.isNotEmpty(insured.getrecognizeeEnName())) {
			insured.setrecognizeeEnName(appnt.getapplicantEnName());
		}
		insured.setrecognizeeFirstName(appnt.getapplicantFirstName());
		insured.setrecognizeeLashName(appnt.getapplicantLastName());
		// 证件类型
		insured.setrecognizeeIdentityType(appnt.getapplicantIdentityType());
		insured.setrecognizeeIdentityTypeName(appnt.getapplicantIdentityTypeName());
		// 证件号码
		insured.setrecognizeeIdentityId(appnt.getapplicantIdentityId());
		// 性别
		insured.setrecognizeeSex(appnt.getapplicantSex());
		insured.setrecognizeeSexName(appnt.getapplicantSexName());
		// 出生日期
		insured.setrecognizeeBirthday(appnt.getapplicantBirthday());
		// 手机号码
		if (StringUtil.isNotEmpty(insured.getrecognizeeMobile())) {
			insured.setrecognizeeMobile(appnt.getapplicantMobile());
		}
		// 电子邮箱
		if (StringUtil.isNotEmpty(insured.getrecognizeeMail())) {
			insured.setrecognizeeMail(appnt.getapplicantMail());
		}
	}

	/**
	 * 发送报文承保
	 */
	private String send(String CompanyID, String OrderSn) {
		String errMessage = "";
		try {
			// 判断是否核保
			String uwSql = "select count(1) from zdcode where CodeType='UWCheckClassName' " + "and ParentCode='UWCheckClassName' and CodeValue=?";
			int uwFlag = new QueryBuilder(uwSql, CompanyID).executeInt();
			// 核保时
			if (uwFlag > 0) {
				UsersUWCheck uwCheck = new UsersUWCheck();
				Map<String, Object> tMap = uwCheck.moreUWCheck(OrderSn);
				// 核保结果标记
				String tPassFlag = tMap.get("passFlag").toString();
				// 核保不通过的情况
				if ("0".equals(tPassFlag)) {
					// 取得错误信息
					List<Map<String, String>> tList = (List<Map<String, String>>) tMap.get("result");
					for (Map<String, String> m : tList) {
						errMessage = errMessage + m.get("RecognizeeName") + "," + m.get("rtnMessage") + ";<br/>";
					}
					return errMessage;
				}
			}

			// 调用经代通
			InsureTransferNew itn = new InsureTransferNew();
			itn.callInsTransInterface(CompanyID, OrderSn, null);

			String sql1 = "select appStatus,insureMsg from SDInformationRiskType where orderSn = ? ";
			DataTable dt1 = new QueryBuilder(sql1, OrderSn).executeDataTable();
			String error = "";
			for (int i = 0; i < dt1.getRowCount(); i++) {
				String appStatus = dt1.getString(i, 0);
				if (!"1".equals(appStatus)) {
					error += dt1.getString(i, 1) + ",";
				}
			}
			if (StringUtil.isNotEmpty(error)) {
				errMessage = "承保失败，原因：" + error;

			}
			return errMessage;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMessage = "承保发送失败! 异常原因：" + e.getMessage();
			return errMessage;
		}
	}

	/**
	 * 
	 * @param insuredList
	 * @param risktypeList
	 * @param info
	 * @param applicantSn
	 * @param now
	 * @param trans
	 * @param insuredSet
	 * @param risktypeSet
	 */
	private void makeInsured(List<SDInformationInsuredSchema> insuredList, List<SDInformationRiskTypeSchema> risktypeList, SDInformationSchema info, String applicantSn, Date now, Transaction trans,
			SDInformationInsuredSet insuredSet, SDInformationRiskTypeSet risktypeSet, DataTable healDT,SDInformationBnfSet newBnfSet) {
		int count = risktypeList.size();
		SDInformationInsuredSchema insured = new SDInformationInsuredSchema();
		SDInformationRiskTypeSchema risktype = new SDInformationRiskTypeSchema();
		List<QueryBuilder> list = new ArrayList<QueryBuilder>();
		QueryBuilder qb = null;
		for (int i = 0; i < count; i++) {
			insured = new SDInformationInsuredSchema();
			SchemaUtil.copyFieldValue(insuredList.get(i), insured);
			//去除英文名中的空白字符乱码
			String RecognizeeEnName=insured.getrecognizeeEnName();
			if(StringUtil.isNotEmpty(RecognizeeEnName)){
				insured.setrecognizeeEnName(RecognizeeEnName.replaceAll(" "," "));
			}
			insured.setId(PubFun.GetInformationInsuredID(info.getorderSn()));
			insured.setcreateDate(now);
			insured.setmodifyDate(now);
			insured.setorderSn(info.getorderSn());
			insured.setinformationSn(info.getinformationSn());
			insured.setrecognizeeSn(PubFun.GetSDInsuredSn());
			int tInsuredAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(insured.getrecognizeeBirthday());
			insured.setrecognizeeAge(String.valueOf(tInsuredAge));
			insured.setinsuredSn(info.getorderSn() + insured.getinsuredSn().substring(16));
			insured.setsdinformation_id(info.getId());
			if (StringUtil.isNotEmpty(insured.getrecognizeeKey())) {
				insured.setrecognizeeKey(info.getorderSn() + insured.getrecognizeeKey().substring(16));
			}
			insured.setdiscountPrice(insured.getrecognizeePrem());
			insuredSet.add(insured);

			risktype = new SDInformationRiskTypeSchema();
			SchemaUtil.copyFieldValue(risktypeList.get(i), risktype);
			risktype.setId(PubFun.GetInformationRiskTypeID(info.getorderSn()));
			risktype.setcreateDate(now);
			risktype.setmodifyDate(now);
			risktype.setorderSn(info.getorderSn());
			risktype.setinformationSn(info.getinformationSn());
			risktype.setrecognizeeSn(insured.getrecognizeeSn());
			risktype.setapplicantSn(applicantSn);
			risktype.setpolicyNo("");
			risktype.setapplyPolicyNo("");
			risktype.setsvaliDate(info.getstartDate());
			risktype.setevaliDate(info.getendDate());
			risktype.setelectronicPath("");
			risktype.setinsurerFlag("");
			risktype.setinsureMsg("");
			risktype.setinsureDate("");
			risktype.setbalanceStatus("");
			risktype.setbalanceFlag("");
			risktype.setbalanceMsg("");
			risktype.setappStatus("0");
			risktype.setvalidateCode("");
			risktype.setsdinformationinsured_id(insured.getId());
			risktype.setsdorder_id(info.getsdorder_id());
			risktypeSet.add(risktype);
			
			//受益人
			SDInformationBnfSet oldBnfSet = new SDInformationBnfSchema().query(
					new QueryBuilder("where recognizeeSn = ?",
							insuredList.get(i).getrecognizeeSn()));
			for(int j=0;j<oldBnfSet.size();j++){
				SDInformationBnfSchema oldBnfSchema = oldBnfSet.get(j);
				SDInformationBnfSchema newBnfSchema = new SDInformationBnfSchema();
				SchemaUtil.copyFieldValue(oldBnfSchema, newBnfSchema);
				newBnfSchema.setid(PubFun.getUUID());
				newBnfSchema.setcreateDate(now);
				newBnfSchema.setmodifyDate(now);
				newBnfSchema.setorderSn(info.getorderSn());
				newBnfSchema.setinformationSn(info.getinformationSn());
				newBnfSchema.setinformationSn2(info.getinformationSn());
				newBnfSchema.setrecognizeeSn(insured.getrecognizeeSn());
				newBnfSchema.setsdinformationInsured_id(insured.getId());
				if("本人".equals(newBnfSchema.getrelationToInsuredName())){
					newBnfSchema.setbnfIDType(insured.getrecognizeeIdentityType());
					newBnfSchema.setbnfIDTypeName(insured.getrecognizeeIdentityTypeName());
					newBnfSchema.setbnfIDNo(insured.getrecognizeeIdentityId());
					newBnfSchema.setbnfName(insured.getrecognizeeName());
					newBnfSchema.setbnfSex(insured.getrecognizeeSex());
					newBnfSchema.setbnfSexName(insured.getrecognizeeSexName());
					newBnfSchema.setbnfBirthday(insured.getrecognizeeBirthday());
					newBnfSchema.setmobile(insured.getrecognizeeMobile());
					newBnfSchema.setzipCode(insured.getrecognizeeZipCode());
					newBnfSchema.setemail(insured.getrecognizeeMail());
					newBnfSchema.setbnfArea1(insured.getrecognizeeArea1());
					newBnfSchema.setbnfArea2(insured.getrecognizeeArea2());
					newBnfSchema.setbnfArea3(insured.getrecognizeeArea3());
					newBnfSchema.setbnfStartID(insured.getrecognizeeStartID());
					newBnfSchema.setbnfEndID(insured.getrecognizeeEndID());
					newBnfSchema.setbnfOccupation1(insured.getrecognizeeOccupation1());
					newBnfSchema.setbnfOccupation2(insured.getrecognizeeOccupation2());
					newBnfSchema.setbnfOccupation3(insured.getrecognizeeOccupation3());
				}
				newBnfSet.add(newBnfSchema);
			}
			
			orderItemOthSave(insured, info, trans);
			makeHealth(insuredList.get(i).getorderSn(), insuredList.get(0), healDT, insured, trans);
			
			if ("11".equals(info.getriskType())) {
				qb = makeSDInformationProperty(insuredList.get(i).getorderSn(),
						insuredList.get(i).getrecognizeeSn(),
						insured.getrecognizeeSn(), insured.getId());
				list.add(qb);
			}
		}
		trans.add(risktypeSet, Transaction.INSERT);
		trans.add(insuredSet, Transaction.INSERT);
		trans.add(newBnfSet,Transaction.INSERT);
		
		if (list != null && list.size() > 0) {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				trans.add(list.get(i));
			}
		}
	}
	
	
	private void directpaybankinfoinsert (Transaction trans , SDOrdersSchema SDOrders,String oldordersn){
		DataTable BankInfodt = new QueryBuilder("SELECT * FROM directpaybankinfo WHERE ordersn=?", oldordersn).executeDataTable();
		if(BankInfodt!=null && BankInfodt.getRowCount()>0){
			StringBuffer directpaybankinfoinsertsql = new StringBuffer();
			directpaybankinfoinsertsql.append("insert into `directpaybankinfo` ");
			directpaybankinfoinsertsql.append("(`id`, `orderSn`, `paySn`, `bankProvince`, `bankCity`, `bankCode`, `bankName`, `bankUserName`, `bankNo`, `prop1`, `prop2`, `prop3`, `prop4`, `prop5`, `prop6`, `prop7`, `CreateDate`, `ModifyDate`) ");
			directpaybankinfoinsertsql.append("values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			QueryBuilder qbBillInsert = new QueryBuilder(directpaybankinfoinsertsql.toString());
			qbBillInsert.add((NoUtil.getMaxNo("DirectPayBankInfoID").toString()+"_"+oldordersn));
			qbBillInsert.add(SDOrders.getorderSn());
			if(StringUtil.isNotEmpty(SDOrders.getpaySn())){
				qbBillInsert.add(SDOrders.getpaySn());
			}else{
				qbBillInsert.add("");
			}
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "BankProvince") )){
				qbBillInsert.add(BankInfodt.getString(0, "BankProvince") );
			}else{
				qbBillInsert.add( "");
			}		
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "BankCity"))){
				qbBillInsert.add( BankInfodt.getString(0, "BankCity"));
			}else{
				qbBillInsert.add("");
			}
			qbBillInsert.add(BankInfodt.getString(0, "BankCode"));
			qbBillInsert.add(BankInfodt.getString(0, "BankName"));
			qbBillInsert.add(BankInfodt.getString(0, "BankUserName"));
			qbBillInsert.add(BankInfodt.getString(0, "BankNo"));
			
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "Prop1"))){
				qbBillInsert.add(BankInfodt.getString(0, "Prop1"));
			}else{
				qbBillInsert.add("");
			}
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "Prop2"))){
				qbBillInsert.add(BankInfodt.getString(0, "Prop2"));
			}else{
				qbBillInsert.add("");
			}
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "Prop3"))){
				qbBillInsert.add(BankInfodt.getString(0, "Prop3"));
			}else{
				qbBillInsert.add("");
			}
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "Prop4"))){
				qbBillInsert.add(BankInfodt.getString(0, "Prop4"));
			}else{
				qbBillInsert.add("");
			}
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "Prop5"))){
				qbBillInsert.add(BankInfodt.getString(0, "Prop5"));
			}else{
				qbBillInsert.add("");
			}
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "Prop6"))){
				qbBillInsert.add(BankInfodt.getString(0, "Prop6"));
			}else{
				qbBillInsert.add("");
			}
			if(StringUtil.isNotEmpty(BankInfodt.getString(0, "Prop7"))){
				qbBillInsert.add( BankInfodt.getString(0, "Prop7"));
			}else{
				qbBillInsert.add("");
			}
			qbBillInsert.add(new Date());
			qbBillInsert.add(new Date());
			trans.add(qbBillInsert);
		}
		 
	}

	/**
	 * 
	 */
	private void makeHealth(String orderSn, SDInformationInsuredSchema insured, DataTable healDT, SDInformationInsuredSchema ninsured, Transaction trans) {

		if (healDT != null && healDT.getRowCount() >= 1) {

			SDInsuredHealthSchema hschema = new SDInsuredHealthSchema();

			SDInsuredHealthSchema newhschema = null;
			hschema.setorderSn(orderSn);
			SDInsuredHealthSet hset = hschema.query();
			SDInsuredHealthSet newhset = new SDInsuredHealthSet();
			for (int i = 0; i < hset.size(); i++) {
				newhschema = new SDInsuredHealthSchema();
				SchemaUtil.copyFieldValue(hset.get(i), newhschema);
				for (int j = 0; j < healDT.getRowCount(); j++) {
					if (newhschema.gethealthyInfoId().equals(healDT.getString(j, "healthyinfoid"))) {
						newhschema.setselectFlag(healDT.getString(j, "selectFlag"));
					}
				}
				newhschema.setorderSn(ninsured.getorderSn());
				newhschema.setid("BG" + NoUtil.getMaxNoUseLock("HealthyNo", "SN"));
				newhschema.setinformationSn(ninsured.getinformationSn());
				newhschema.setrecognizeeSn(ninsured.getrecognizeeSn());
				newhschema.setsdinformationinsured_id(ninsured.getId());
				newhset.add(newhschema);
			}
			if (newhset != null && newhset.size() >= 1) {
				trans.add(newhset, Transaction.INSERT);
			}
		}
	}

	/**
	 * 处理orderitemoht
	 * 
	 * @param t
	 */
	public void orderItemOthSave(SDInformationInsuredSchema t, SDInformationSchema sdinf, Transaction trans) {
		SDOrderItemOthSchema sdorderitemoth = new SDOrderItemOthSchema();
		sdorderitemoth.setid(PubFun.GetOrderItemOthID(sdinf.getorderSn()));
		sdorderitemoth.setcreateDate(t.getcreateDate());
		sdorderitemoth.setmodifyDate(t.getcreateDate());
		sdorderitemoth.setorderSn(sdinf.getorderSn());
		sdorderitemoth.setrecognizeeSn(t.getrecognizeeSn());
		sdorderitemoth.setorderitemSn(PubFun.GetItemOthNo());
		sdorderitemoth.setsdinformationinsured_id(t.getId());
		if ("2023".equals(sdinf.getinsuranceCompany()) || "2011".equals(sdinf.getinsuranceCompany())) {
			String outcode = PubFun.getHTSN(sdinf.getproductOutCode());
			sdorderitemoth.settpySn(outcode);// 存入华泰特殊编号
		}
		trans.add(sdorderitemoth, Transaction.INSERT);
	}

	/**
	 * 处理orderitem
	 */
	public void makeOrderItemSave(String orderSn, SDOrdersSchema sdorder, SDInformationSchema sdInformation, Transaction trans) {
		SDOrderItemSchema sdorderitem = new SDOrderItemSchema();
		QueryBuilder qb = new QueryBuilder("SELECT channelCode,channelAgentCode,typeFlag FROM sdorderitem WHERE ordersn=?", orderSn);
		DataTable dt = qb.executeDataTable();
		String channelCode = "";
		String typeFlag = "";
		String ChannelAgentCode = "";
		if (dt != null && dt.getRowCount() > 0) {
			if (StringUtil.isNotEmpty(dt.getString(0, 0))) {
				channelCode = dt.getString(0, 0);
			}
			if (StringUtil.isNotEmpty(dt.getString(0, 1))) {
				typeFlag = dt.getString(0, 1);
			}
			if (StringUtil.isNotEmpty(dt.getString(0, 2))) {
				ChannelAgentCode = dt.getString(0, 2);
			}
		}
		Date createDate = new Date();
		Date modifyDate = new Date();
		sdorderitem.setid(PubFun.GetOrderItemID(sdInformation.getorderSn()));
		sdorderitem.setcreateDate(createDate);
		sdorderitem.setmodifyDate(modifyDate);
		sdorderitem.setsdorder_id(sdorder.getid());
		sdorderitem.setorderSn(sdorder.getorderSn());
		sdorderitem.setorderPoint("0");
		sdorderitem.setpointStatus("1");
		sdorderitem.setorderitemSn(PubFun.GetItemNo());
		sdorderitem.getchannelCode();
		sdorderitem.gettypeFlag();
		sdorderitem.getchannelAgentCode();
		sdorderitem.setchannelCode(channelCode);
		sdorderitem.settypeFlag(typeFlag);
		sdorderitem.setchannelAgentCode(ChannelAgentCode);
		trans.add(sdorderitem, Transaction.INSERT);
	}

	/**
	 * 新生成订单表数据
	 * 
	 * @param sdorder
	 * @param sumProductPrem
	 *            订单原价
	 * @param totalAmount
	 *            订单折后价
	 * @param now
	 *            当前时间
	 * @param trans
	 */
	private void makeSDOrder(SDOrdersSchema sdorder, BigDecimal sumProductPrem, BigDecimal totalAmount, Date now, Transaction trans) {
		sdorder.setid(PubFun.GetOrderID(sdorder.getorderSn()));
		sdorder.setorderSn(PubFun.GetOrderSn());
		sdorder.setcreateDate(now);
		sdorder.setmodifyDate(now);
		sdorder.setorderStatus("7");
		sdorder.setpaySn(PubFun.GetBGPaySn());
		sdorder.setoffsetPoint("0"); 
		sdorder.setcommentId(null);
		sdorder.setsumActivity(sdorder.getorderActivity());
		sdorder.setproductTotalPrice(sumProductPrem.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		sdorder.settotalAmount(totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		trans.add(sdorder, Transaction.INSERT);
	}

	/**
	 * 新生成订单信息表数据
	 * 
	 * @param sdorder
	 *            订单表数据
	 * @param information
	 *            订单信息表
	 * @param now
	 *            当前时间
	 * @param trans
	 */
	private void makeInformation(SDOrdersSchema sdorder, SDInformationSchema information, Date now, Transaction trans) {
		information.setId(PubFun.GetInformationID(sdorder.getorderSn()));
		information.setinformationSn(PubFun.GetSDInformationSn());
		information.setcreateDate(now);
		information.setmodifyDate(now);
		information.setorderSn(sdorder.getorderSn());
		information.setpoint("");
		information.setpointStatus("");
		information.setsdorder_id(sdorder.getid());
		information.setproductPrice(sdorder.getproductTotalPrice());
		information.setproductDiscountPrice(sdorder.gettotalAmount());
		trans.add(information, Transaction.INSERT);
	}

	/**
	 * 新生成投保人表数据
	 * 
	 * @param appnt
	 *            投保人表
	 * @param information
	 *            订单信息
	 * @param now
	 *            当前时间
	 * @param trans
	 */
	private void makeSDInformationAppnt(SDInformationAppntSchema appnt, SDInformationSchema information, Date now, Transaction trans) {
		appnt.setId(PubFun.GetInformationAppntID(information.getorderSn()));
		appnt.setcreateDate(now);
		appnt.setmodifyDate(now);
		appnt.setinformationSn(information.getinformationSn());
		appnt.setapplicantSn(PubFun.GetSDAppntSn());
		int appAge = com.sinosoft.sms.messageinterface.pubfun.PubFun.getAge(appnt.getapplicantBirthday());
		appnt.setapplicantAge(String.valueOf(appAge));
		appnt.setsdinformaiton_id(information.getId());
		trans.add(appnt, Transaction.INSERT);
	}

	/**
	 * 新生成责任信息表数据
	 * 
	 * @param dutySet
	 *            责任信息表
	 * @param info
	 *            订单信息表
	 * @param now
	 *            当前时间
	 * @param trans
	 */
	private void makeSDInformationDuty(SDInformationDutySchema duty_change,SDInformationDutySet dutySet_change ,SDInformationDutySet dutySet, SDInformationSchema info, Date now, Transaction trans) {
		if (dutySet != null && dutySet.size() > 0) {
			int dutySize = dutySet.size();
			
			for (int i = 0; i < dutySize; i++) {
				duty_change=(SDInformationDutySchema) dutySet.get(i).clone();
				duty_change.setId(PubFun.GetInformationDutyID(info.getorderSn()));
				duty_change.setcreateDate(now);
				duty_change.setmodifyDate(now);
				duty_change.setinformationSn(info.getinformationSn());
				duty_change.setorderSn(info.getorderSn());
				duty_change.setsdinformation_id(info.getId());
				
				dutySet_change.add(duty_change);
			}
			trans.add(dutySet_change, Transaction.INSERT);
		}
	
	}

	/**
	 * 新生成交易信息数据
	 * 
	 * @param sdorder
	 *            订单信息
	 * @param now
	 *            当前时间
	 * @param trans
	 * @param oldOrderSn
	 *            旧订单号
	 * @param oldPaySn
	 *            旧交易号
	 */
	private void makeTradeInfomation(SDOrdersSchema sdorder, Date now, Transaction trans, String oldOrderSn, String oldPaySn) {
		// 交易表
		TradeInformationSchema trade = new TradeInformationSchema();
		// 交易汇总表
		TradeSummaryInfoSchema tradeSummary = new TradeSummaryInfoSchema();

		trade.setordID(oldOrderSn);
		TradeInformationSet tradesSet = trade.query();
		if (tradesSet != null && tradesSet.size() > 0) {
			trade = tradesSet.get(0);
			trade.setid(PubFun.GetTradeInformationID(sdorder.getorderSn()));
			trade.setcreateDate(now);
			trade.setmodifyDate(now);
			trade.setordAmt(sdorder.gettotalAmount());
			trade.setordID(sdorder.getorderSn());
			trade.settradeSeriNO(sdorder.getpaySn());
			trade.settradeCheckSeriNo(sdorder.getpaySn());
			trade.setweixicookie("");
			trans.add(trade, Transaction.INSERT);
		}

		tradeSummary.setPaySn(oldPaySn);
		TradeSummaryInfoSet summarySet = tradeSummary.query();
		if (summarySet != null && summarySet.size() > 0) {
			tradeSummary = summarySet.get(0);
			tradeSummary.setid(NoUtil.getMaxNo("TradeSummaryID", 11));
			tradeSummary.setPaySn(sdorder.getpaySn());
			tradeSummary.setTradeSn(sdorder.getpaySn());
			tradeSummary.setOrderSns(sdorder.getorderSn());
			tradeSummary.setCouponSumAmount(sdorder.getorderCoupon());
			tradeSummary.setActivitySumAmount(sdorder.getorderActivity());
			tradeSummary.setPointSumAmount(sdorder.getorderIntegral());
			tradeSummary.setTotalAmount(sdorder.gettotalAmount());
			tradeSummary.setTradeAmount(sdorder.getpayPrice());
			tradeSummary.setCreateDate(now);
			tradeSummary.setModifyDate(now);
			trans.add(tradeSummary, Transaction.INSERT);
		}

		String initPaySn = oldPaySn;
		if (oldPaySn.indexOf("BG") >= 0) {
			String sql = "select initPaySn from PolicyChangeInfo where newPaySn = ?";
			QueryBuilder qb = new QueryBuilder(sql, oldPaySn);
			initPaySn = qb.executeString();
		}
		// 保全记录添加
		SDRemarkSchema comment = new SDRemarkSchema();
		comment.setid(com.sinosoft.lis.pubfun.PubFun.GetNRemarkId());
		comment.setremark("变更：初始商家订单号-" + initPaySn);
		comment.setorderSn(sdorder.getorderSn());
		comment.setOperateName("system");
		comment.setOperateTime(new Date());
		comment.setOperateType("add");
		comment.setprop1("");
		comment.setprop2("");
		comment.setupperId("");
		trans.add(comment, Transaction.INSERT);
	}

	/**
	 * 保费试算取得保费
	 * 
	 * @param paramter1
	 *            产品信息
	 * @param insuredPremMap
	 * @param dutyJson
	 *            责任信息
	 * @param information
	 *            订单信息
	 * @param dr
	 *            保单信息
	 * @param svalidate
	 *            起保日期
	 * @param service
	 * @return 保费
	 */
	private double getInsuredPrem(Map<String, Object> paramter1, Map<String, Object> insuredPremMap, Map<String, String> dutyJson, SDInformationSchema information, DataRow dr, String svalidate,
			SDOrderServiceImpl service) {
		OrderConfigNewServiceImpl orderConfigNewService = new OrderConfigNewServiceImpl();

		// 产品基础数据
		String[] BaseInformation = new String[16];
		// 责任投保要素列表
		List<OrderDutyFactor> dutyFactor = new ArrayList<OrderDutyFactor>();
		// 产品投保要素列表
		List<OrderRiskAppFactor> riskAppFactior = new ArrayList<OrderRiskAppFactor>();
		// 投保要素信息
		Map<String, String> insureJson = new HashMap<String, String>();
		// 参数集合
		Map<String, Object> cMap = new HashMap<String, Object>();
		double insuredPrem = 0.0;
		String insuredSex = dr.getString("recognizeeSex");
		String recognizeeBirthday = dr.getString("recognizeeBirthday");
		// 取得产品基础数据
		BaseInformation = (String[]) paramter1.get("baseInformation");
		// 取得责任投保要素列表
		riskAppFactior = (List<OrderRiskAppFactor>) paramter1.get("riskAppFactor");
		// 取得责任投保要素列表
		dutyFactor = (List<OrderDutyFactor>) paramter1.get("dutyFactor");
		if (information.getorderSn() != null && information.getorderSn().startsWith("TB")) {
			String tbperiod = dealTBorder(information.getproductId(),information.getplanCode(), information.getensure());
			if (StringUtil.isNotEmpty(tbperiod)) {
				information.setensure(tbperiod);
			}
		}
		cMap = new HashMap<String, Object>();
		cMap.put("baseInformations", BaseInformation);
		cMap.put("riskAppFactior", riskAppFactior);
		cMap.put("dutyFactor", dutyFactor);
		cMap.put("productId", information.getproductId());
		cMap.put("sdinformation", information);
		cMap.put("dutyJson", dutyJson);
		cMap.put("effdate", svalidate);
		insureJson.put("Plan", information.getplanCode());
		insureJson.put("AppType", information.getappType());
		insureJson.put("FeeYear", information.getchargeYear());
		insureJson.put("TextAge", recognizeeBirthday);
		insureJson.put("Period", information.getensure());
		insureJson.put("Sex", insuredSex);
		cMap.put("insureJson", insureJson);
		cMap.put("insuredBirthDay", recognizeeBirthday);
		cMap.put("insuredSex", insuredSex);
		cMap.put("complicatedFlag", paramter1.get("complicatedFlag"));
		
		
		// 产品年龄限制
		String limitAge = orderConfigNewService.getSectionAge(information.getproductId());

		String realSecAge = orderConfigNewService.getRealSectionAge(recognizeeBirthday, limitAge, svalidate);
		if (!"-1".equals(realSecAge)) {
			if (String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)) != null && !"null".equals(String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)))
					&& !"".equals(String.valueOf(insuredPremMap.get(insuredSex + "-" + realSecAge)))) {
				Map<String, Object> tempMap = (Map<String, Object>) insuredPremMap.get(insuredSex + "-" + realSecAge);
				insuredPrem = insuredPrem + Double.parseDouble(String.valueOf(tempMap.get("retCountPrem")));
			} else {
				Map<String, Object> rMap = calPrem(cMap, service, orderConfigNewService);
				insuredPrem = insuredPrem + Double.parseDouble(String.valueOf(rMap.get("retCountPrem")));
				insuredPremMap.put(insuredSex + "-" + realSecAge, rMap);
			}
		} else {
			insuredPrem = insuredPrem + Double.parseDouble("0");
		}
		return insuredPrem;
	}

	/**
	 * 判断起保日期是否符合产品限制
	 * 
	 * @param productId
	 *            产品编码
	 * @param startDate
	 *            起保日期
	 * @param endDate
	 *            起保终止日期
	 * @return 错误信息
	 */
	private String checkPeriod(String productId, String startDate, String endDate) {

		QueryBuilder qb = new QueryBuilder("SELECT startPeriod,endPeriod FROM productPeriod WHERE riskCode=?", productId);
		DataTable dt = qb.executeDataTable();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date periodfail;
		Date periodeffective;
		try {
			periodfail = sfTime.parse(endDate);
			periodeffective = sfTime.parse(startDate);
		} catch (ParseException e1) {
			return "起保日期格式有误，不能变更！";
		}
		// 起保时间间隔
		int startPeriod = 1;
		int endPeriod = 700;
		if (dt != null && dt.getRowCount() > 0) {
			if (StringUtil.isNotEmpty(dt.getString(0, 0))) {
				startPeriod = Integer.parseInt(dt.getString(0, 0));
			}
			if (StringUtil.isNotEmpty(dt.getString(0, 1))) {
				endPeriod = Integer.parseInt(dt.getString(0, 1));
			}
		}
		// 默认产品无次日生效，查询zdcode表中被要求次日生效的产品记录
		qb = new QueryBuilder("SELECT memo FROM zdcode WHERE codetype='ProductPeriod' AND codevalue=?");
		qb.add(productId);
		String memo = qb.executeString();
		if (StringUtil.isNotEmpty(memo)) {
			startPeriod = Integer.parseInt(memo);
		}
		// 当前时间
		Date now = new Date();
		// 当前时间超出起保时间
		if (startPeriod != 0 && now.after(periodeffective)) {
			return "起保时间小于当前时间，不能变更！";
		} else if (startPeriod == 0) {
			if (!new SimpleDateFormat("yyyyMMdd").format(now).equals(new SimpleDateFormat("yyyyMMdd").format(periodeffective))) {
				return "该产品只能当天购买，不能变更！";
			}
		}

		String tDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(sf.format(now), startPeriod, "D");
		Date nowDate;
		try {
			nowDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.StringToDate(tDate);
			if ((periodeffective.getTime() < nowDate.getTime()) && (periodfail.getTime() >= periodeffective.getTime())) {
				return "起保时间不满足该产品起保时间间隔 " + startPeriod + " 天，不能变更！";
			}
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			return "计算最小起保期限错误！";
		}
		String maxDate = com.sinosoft.sms.messageinterface.pubfun.PubFun.calSDate(sf.format(now), endPeriod, "D");
		try {
			Date end = sf.parse(maxDate + " 00:00:00");
			if (end.getTime() < periodeffective.getTime()) {
				return "起保时间超过该产品最大的起保时间：" + maxDate + "，不能变更！";
			}
		} catch (ParseException e1) {
			logger.error(e1.getMessage(), e1);
			return "计算最大起保期限错误！";
		}

		return "";
	}

	@SuppressWarnings("finally")
	public Map<String, Object> calPrem(Map<String, Object> cMap, SDOrderServiceImpl service, OrderConfigNewServiceImpl orderConfigNewService) {
		// String [] factorValue =new String [1];
		SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> mMap = new HashMap<String, Object>();
		String[] baseInformations = (String[]) cMap.get("baseInformations");// 产品基本信息
		List<OrderRiskAppFactor> riskAppFactior = (List<OrderRiskAppFactor>) cMap.get("riskAppFactior");// 产品投保要素
		List<OrderDutyFactor> dutyFactor = (List<OrderDutyFactor>) cMap.get("dutyFactor");// 产品责任信息
		SDInformationSchema sdi = (SDInformationSchema) cMap.get("sdinformation");// 订单项信息
		Map<String, String> insureJson = (Map<String, String>) cMap.get("insureJson");// 投保要素信息
		Map<String, String> dutyJson = (Map<String, String>) cMap.get("dutyJson");// 责任信息
		String productId = (String) cMap.get("productId");// 产品ID
		String insuredBirthDay = (String) cMap.get("insuredBirthDay");// 被保人生日
		String insuredSex = (String) cMap.get("insuredSex");// 被保人生日
		String channelsn = (String) cMap.get("channelsn");// 渠道：wap、wj
		String complicatedFlag = (String) cMap.get("complicatedFlag");
		String effective = "";
		String retCountPrem = "";
		String productPrem = "";

		if (StringUtil.isEmpty(channelsn)) {
			channelsn = "wj";
		}

		List<OrderDutyFactor> dutyFactorLast = new ArrayList<OrderDutyFactor>();

		Double totlePrem = 0.0;// 总保费
		Double countPrice = 0.0;// 折扣后保费

		for (int i = 0; i < riskAppFactior.size(); i++) {
			// OrderRiskAppFactor orderRiskAppFactor =
			// riskAppFactior.get(i);
			if ("TextAge".equals(riskAppFactior.get(i).getFactorType())) {
				String textage = insuredBirthDay;
				String factorValueTemp = insuredBirthDay;
				if ("".equals(factorValueTemp) || factorValueTemp == null) {
					FEMRiskFactorList femr = riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if ("".equals(textage)) {
						if (femr != null && femr.getFactorValue() != null) {
							riskFactor.setFactorValue(orderConfigNewService.getBrithdayByFactor(effective, femr.getFactorValue()));
						} else {
							riskFactor.setFactorValue("1991-01-01");
						}
					} else {
						riskFactor.setFactorValue(factorValueTemp.toString());
					}
					riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				} else {
					riskAppFactior.get(i).getFactorValue().get(0);
					List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
					FEMRiskFactorList riskFactor = new FEMRiskFactorList();
					if (factorValueTemp != null)
						riskFactor.setFactorValue(factorValueTemp.toString());
					riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
					riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
					riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
					factorValue.add(riskFactor);
					riskAppFactior.get(i).setFactorValue(factorValue);
				}
			} else if ("Sex".equals(riskAppFactior.get(i).getFactorType())) {
				String factorValueTemp = insuredSex;
				riskAppFactior.get(i).getFactorValue().get(0);
				List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
				FEMRiskFactorList riskFactor = new FEMRiskFactorList();
				if (factorValueTemp != null)
					riskFactor.setFactorValue(factorValueTemp.toString());
				riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
				riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
				riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
				factorValue.add(riskFactor);
				riskAppFactior.get(i).setFactorValue(factorValue);
			} else {
				Object factorValueTemp = insureJson.get(riskAppFactior.get(i).getFactorType());
				riskAppFactior.get(i).getFactorValue().get(0);
				List<FEMRiskFactorList> factorValue = new ArrayList<FEMRiskFactorList>();
				FEMRiskFactorList riskFactor = new FEMRiskFactorList();
				if (factorValueTemp != null)
					riskFactor.setFactorValue(factorValueTemp.toString());
				riskFactor.setAppFactorCode(riskAppFactior.get(i).getAppFactorCode());
				riskFactor.setFactorType(riskAppFactior.get(i).getFactorType());
				riskFactor.setIsPremCalFacotor(riskAppFactior.get(i).getIsPremCalFacotor());
				factorValue.add(riskFactor);
				riskAppFactior.get(i).setFactorValue(factorValue);
			}
		}

		for (OrderDutyFactor orderDutyFactor : dutyFactor) {
			String dutyValueTemp = dutyJson.get(orderDutyFactor.getDutyCode());
			if (orderDutyFactor.getFdAmntPremList() != null) {
				// orderDutyFactor.getFdAmntPremList().clear();
			}
			List<FEMDutyAmntPremList> fdAmntPremList = new ArrayList<FEMDutyAmntPremList>();
			if ("nvalue".equals(dutyValueTemp) || StringUtil.isEmpty(dutyValueTemp)) {
				// System.out.println("dutyValueTemp=" + dutyValueTemp);
			} else {
				for (FEMDutyAmntPremList femd : orderDutyFactor.getFdAmntPremList()) {
					if (dutyValueTemp.equals(femd.getBackUp1())) {
						fdAmntPremList.add(femd);
					}
				}
				if (fdAmntPremList.size() > 0) {
					orderDutyFactor.setFdAmntPremList(fdAmntPremList);
					dutyFactorLast.add(orderDutyFactor);
				}
			}
		}
		if (dutyFactorLast.size() == 0) {
			dutyFactorLast = null;
		}
		Map<String, Object> paramter = new HashMap<String, Object>();
		paramter.put("baseInformation", baseInformations);
		paramter.put("riskAppFactor", riskAppFactior);
		paramter.put("dutyFactor", dutyFactorLast);
		paramter.put("complicatedFlag", complicatedFlag);
		
		if (sdi.getstartDate() != null) {
			/**
			 * 保费试算根据保险公司判断是起保日期还是当前日期 heyang 2013-7-19
			 */
			String startdate = sdf_1.format(sdi.getstartDate());
			if (productId.startsWith("1087") || productId.startsWith("1001") || productId.startsWith("1004") || productId.startsWith("2043")) {
				startdate = PubFun.getCurrentDate();
			}
			paramter.put("effective", startdate);
		}
		try {
			Map<String, Object> mapPrem = service.getProductPremDutyAmounts(paramter);
			retCountPrem = String.valueOf(mapPrem.get("countPrice").toString());// 折后保费
			productPrem = String.valueOf(mapPrem.get("totlePrem").toString());// 原保费

			totlePrem = totlePrem + Double.parseDouble(mapPrem.get("totlePrem").toString());// 总保费
			countPrice = countPrice + Double.parseDouble(mapPrem.get("countPrice").toString());// 折扣后保费

			mMap.put("productPrem", productPrem);
			mMap.put("retCountPrem", retCountPrem);// 折扣后保费

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			mMap.put("retCountPrem", "0");
			mMap.put("productPrem", "0");
		} finally {
			return mMap;
		}
	}

	/**
	 * @Title: dealCpsOrder
	 * @Description: 处理CPS订单
	 * @return void 返回类型
	 * @author heyang
	 */
	private static void dealCpsOrder(String orderSn, String newOrderSn, String webServiceUrl) {
		try {
				Service servicemodel = new ObjectServiceFactory().create(CpsProductBuyService.class);
				CpsProductBuyService service = (CpsProductBuyService) new XFireProxyFactory().create(servicemodel, webServiceUrl);
				service.saveOrder(orderSn);// 处理原有订单
				service.saveOrder(newOrderSn);// 处理新的订单
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	private QueryBuilder makeSDInformationProperty(String orderSn,
			String oldRecognizeeSn, String newRecognizeeSn,
			String newInsuredID) {
		DataTable dt = new QueryBuilder(
				"select * from SDInformationProperty where recognizeeSn = ?",
				oldRecognizeeSn).executeDataTable();
		QueryBuilder qb = null;
		if (dt != null && dt.getRowCount() > 0) {
			StringBuffer insertSQL = new StringBuffer();
			insertSQL.append("INSERT INTO SDInformationProperty ( id,createDate,modifyDate,carPlateNo,chassisNumber,hourseAge,hourseNo,hourseType,licenseNumber,propertyAdress,propertyArea1,propertyArea2,propertyArea3,propertyToRecognizee,propertyZip,recognizeeSn,remark1,sdinformationinsured_id) VALUES  (?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ? ,? ,?,?,?)");
			qb = new QueryBuilder(insertSQL.toString());
			qb.add(PubFun.GetInformationPropID(orderSn));
			qb.add(dt.getString(0, "createDate"));
			qb.add(dt.getString(0, "modifyDate"));
			qb.add(dt.getString(0, "carPlateNo"));
			qb.add(dt.getString(0, "chassisNumber"));
			qb.add(dt.getString(0, "hourseAge"));
			qb.add(dt.getString(0, "hourseNo"));
			qb.add(dt.getString(0, "hourseType"));
			qb.add(dt.getString(0, "licenseNumber"));
			qb.add(dt.getString(0, "propertyAdress"));
			qb.add(dt.getString(0, "propertyArea1"));
			qb.add(dt.getString(0, "propertyArea2"));
			qb.add(dt.getString(0, "propertyArea3"));
			qb.add(dt.getString(0, "propertyToRecognizee"));
			qb.add(dt.getString(0, "propertyZip"));
			qb.add(newRecognizeeSn);
			qb.add(dt.getString(0, "remark1"));
			qb.add(newInsuredID);
		}
		return qb;
	}
	
	public static String dealTBorder(String productId, String planCode, String ensure) {

	QueryBuilder db_period = new QueryBuilder("SELECT `Period`  FROM jdproductc WHERE ERiskID = ? AND PlanCode =? AND CoverageYear=? ");
	db_period.add(productId);
	db_period.add(planCode);
	db_period.add(ensure);
	DataTable dt = db_period.executeDataTable();
	String Period="";
	if (dt.getRowCount() > 0) {
		if(dt.getString(0, 0)!=null && dt.getString(0, 0)!=""){
			Period =dt.getString(0, 0).toString();
		}	
	}
	return Period;
	}
	
	/**
	 * 获取会员特权信息
	 * @param dga
	 */
	public void memberPrivileges(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select * from MemberPrivileges order by orderFlag asc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 特权增加
	 */
	public void addPrivileges() {
		MemberPrivilegesSchema schema = new MemberPrivilegesSchema();
		schema.setid(CommonUtil.getUUID());
		schema.setValue(Request);
		schema.setAddTime(new Date());
		schema.setAddUser(User.getUserName());
		
		if (schema.insert()) {
			Response.setLogInfo(1, "添加成功");
		} else {
			Response.setLogInfo(0, "添加失败!");
		}
	}
	
	/**
	 * 特权编辑 初始化
	 */
	public static Mapx editPrivilegesInit(Mapx params) {
		MemberPrivilegesSchema schema = new MemberPrivilegesSchema();
		schema.setid(params.getString("id"));
		if (schema.fill()){
			params.put("PrivilegesName", schema.getPrivilegesName());
			String MemberLevel = schema.getMemberLevel();
			
			if (StringUtil.isNotEmpty(MemberLevel)) {
				String[] level = MemberLevel.split(",");
				for (int i = 0; i < level.length; i++) {
					if (StringUtil.isNotEmpty(level[i])) {
						params.put("MemberLevel" + level[i], "checked");
					}
					
				}
			}
			params.put("id", schema.getid());
			params.put("PgLink", schema.getPgLink());
			params.put("useFlag", schema.getuseFlag());
			params.put("orderFlag", schema.getorderFlag());
			params.put("content", schema.getcontent());
			params.put("description", schema.getdescription());
		}
		return params;
	}
	
	/**
	 * 特权编辑
	 */
	public void editPrivileges() {
		MemberPrivilegesSchema schema = new MemberPrivilegesSchema();
		schema.setid(Request.getString("id"));
		if (schema.fill()){
			schema.setValue(Request);
			if (schema.update()) {
				Response.setLogInfo(1, "编辑成功");
			} else {
				Response.setLogInfo(0, "编辑失败!");
			}
		}
	}
	
	/**
	 * 特权删除 
	 */
	public void delPrivileges() {
		String ids = $V("ids");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		MemberPrivilegesSchema schema = new MemberPrivilegesSchema();
		MemberPrivilegesSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		}
		else{
			Response.setLogInfo(0, "删除失败");
		}
	}
	
 }