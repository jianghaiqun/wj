package com.sinosoft.cms.dataservice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;

public class TeamReservation extends Page {

	// 得到团险预约信息
	public void orderInquery(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder(
				"select customerTel as companyname,customerName as peoplenum,customerAreas1 as connecttime,"
						+
						"customerAreas2 as connectname,productName as connectiphone,createDate as submitDate from reservation "
						+
						"where isTeam='Y' order by createDate desc");
		// DataTable dt = qb.executeDataTable();
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.bindData(dt);
	}

	// 得到普通预约信息
	public void CommonFind(DataGridAction dga) {

		QueryBuilder qb = new QueryBuilder(
				"select customerName,customerTel,createDate,areaShow,productName from reservation " +
						"where isTeam!='Y'or isTeam is null order by createDate desc");
		// DataTable dt = qb.executeDataTable();
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.bindData(dt);

	}

	// 新版非团险预约信息
	public void reservationDGA(DataGridAction dga) {

		Mapx params = dga.getParams();
		String reservationStartDate = params.getString("reservationStartDate");
		String reservationEndDate = params.getString("reservationEndDate");
		String reservationCode = params.getString("reservationCode");
		String productName = params.getString("productName");
		String customerTel = params.getString("customerTel");

		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder().append("SELECT ").append("  r.customerName, ").append("  r.customerTel, ")
				.append("  r.areaShow, ").append("  r.customerAreas1, ").append("  r.customerAreas2, ")
				.append("  r.createDate, ").append("  r.productName, ").append("  r.productId, ")
				.append("  r.reservationCode, ").append("  r.isOldMember, ").append("  m.email, ").append("  m.username ")
				.append("FROM reservation r ").append("  LEFT JOIN member m ON r.memberId = m.id WHERE 1=1 ")
				.append(" AND (isTeam!='Y'or isTeam is null) ");

		if (StringUtils.isNotEmpty(reservationStartDate)) {
			sb.append(" and Date(r.createDate) >= ? ");
			qb.add(reservationStartDate);
		}
		if (StringUtils.isNotEmpty(reservationEndDate)) {
			sb.append(" and Date(r.createDate) <= ? ");
			qb.add(reservationEndDate);
		}
		if (StringUtils.isNotEmpty(reservationCode)) {
			sb.append(" AND r.reservationCode =? ");
			qb.add(reservationCode);
		}
		if (StringUtils.isNotEmpty(productName)) {
			sb.append(" AND r.productName =? ");
			qb.add(productName);
		}
		if (StringUtils.isNotEmpty(customerTel)) {
			sb.append(" AND r.customerTel =? ");
			qb.add(customerTel);
		}

		sb.append("order by r.createDate desc ");
		qb.setSQL(sb.toString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		Map<String, String> isOldMemberMap = new HashMap<String, String>();
		isOldMemberMap.put("1", "老用户");
		isOldMemberMap.put("0", "新用户");
		dt.decodeColumn("isOldMember", isOldMemberMap);

		dga.bindData(dt);


	}

	public static Mapx<String, String> init(Mapx<String, String> params) {

		if (params == null) {
			params = new Mapx<String, String>();
		}
		Date now = new Date();
		params.put("reservationStartDate", DateUtil.getCurrentDate());
		params.put("reservationEndDate", DateUtil.getCurrentDate());
		// params.put("isTeam", HtmlUtil.codeToOptions("YesOrNo", true));

		return params;
	}

}