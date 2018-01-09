package com.sinosoft.cms.settlement;

import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FinancialSettlementStatisticsDataTable {

	public DataTable queryRSX(String invoiceStartDate, String invoiceEndDate, String companyName,
			String branchCode) {

		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder().append("SELECT ")
				.append("	d.`InnerCode` typeCode, ")
				.append("	d.`TreeLevel` treeLevel, ")
				.append("	d.`prop2` `level`, ")
				.append("	d.`Name` type, ")
				.append("	IFNULL(sum(f.premium1st), 0) sump1, ")
				.append("	IFNULL(sum(f.premium2nd), 0) sump2, ")
				.append("	IFNULL(sum(f.fee1st), 0) sumf1, ")
				.append("	IFNULL(sum(f.fee2nd), 0) sumf2, ")
				.append("sum(CASE f.channeltype WHEN '自营' THEN f.premium1st ELSE 0 END) sump1z, ")
				.append("sum(CASE f.channeltype WHEN '自营' THEN f.premium2nd ELSE 0 END) sump2z, ")
				.append("sum(CASE f.channeltype WHEN '自营' THEN f.fee1st ELSE 0 END) sumf1z, ")
				.append("sum(CASE f.channeltype WHEN '自营' THEN f.fee2nd ELSE 0 END) sumf2z, ")
				.append("sum(CASE f.channeltype WHEN '线下' THEN f.premium1st ELSE 0 END) sump1x, ")
				.append("sum(CASE f.channeltype WHEN '线下' THEN f.premium2nd ELSE 0 END) sump2x, ")
				.append("sum(CASE f.channeltype WHEN '线下' THEN f.fee1st ELSE 0 END) sumf1x, ")
				.append("sum(CASE f.channeltype WHEN '线下' THEN f.fee2nd ELSE 0 END) sumf2x, ")
				.append("sum(CASE f.channeltype WHEN '第三方' THEN f.premium1st ELSE 0 END) sump1d, ")
				.append("sum(CASE f.channeltype WHEN '第三方' THEN f.premium2nd ELSE 0 END) sump2d, ")
				.append("sum(CASE f.channeltype WHEN '第三方' THEN f.fee1st ELSE 0 END) sumf1d, ")
				.append("sum(CASE f.channeltype WHEN '第三方' THEN f.fee2nd ELSE 0 END) sumf2d ")
				.append("FROM ")
				.append("	datainfo d ")
				.append("LEFT JOIN (SELECT * FROM financialsettlementdetails WHERE FIND_IN_SET(branchcode,?) ");
		qb.add(branchCode);
		if (StringUtils.isNotEmpty(invoiceStartDate)) {
			sb.append(" and Date(invoicedate) >= ? ");
			qb.add(invoiceStartDate);
		}
		if (StringUtils.isNotEmpty(invoiceEndDate)) {
			sb.append(" and Date(invoicedate) <= ? ");
			qb.add(invoiceEndDate);
		}
		if (StringUtils.isNotEmpty(companyName)) {
			sb.append(" and FIND_IN_SET(insurancecompanyname,?) ");
			qb.add(companyName);
		}
		sb.append(")f ON f.insurancesubtype = d.`InnerCode` OR f.insurancetype=d.`InnerCode` ")
				.append("WHERE d.Type = 'coverage_rsx' ")
				.append("GROUP BY ")
				.append("	d.`InnerCode` ")
				.append("ORDER BY ")
				.append("	d.OrderFlag");
		qb.setSQL(sb.toString());
		DataTable dt = qb.executeDataTable();
		dt.insertRow(new Object[] { "00", "1", null, "合计",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0"
		});
		return dt;
	}

	public DataTable queryCX(String invoiceStartDate, String invoiceEndDate, String companyName,
			String branchCode) {

		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder().append("SELECT  ")
				.append("  d.`InnerCode` typeCode,  ")
				.append("  d.`TreeLevel` treeLevel,  ")
				.append("  d.`prop2` `level`,  ")
				.append("  d.`Name` type,  ")
				.append("  IFNULL(sum(f.premium1st+f.premium2nd), 0) sump,  ")
				.append("  IFNULL(sum(f.fee1st+f.fee2nd), 0) sumf,  ")
				.append("sum(CASE f.channeltype WHEN '自营' THEN f.premium1st+f.premium2nd ELSE 0 END) sumpz,  ")
				.append("sum(CASE f.channeltype WHEN '自营' THEN f.fee1st+f.fee2nd ELSE 0 END) sumfz,  ")
				.append("sum(CASE f.channeltype WHEN '第三方' THEN f.premium1st+f.premium2nd ELSE 0 END) sumpd,  ")
				.append("sum(CASE f.channeltype WHEN '第三方' THEN f.fee1st+f.fee2nd ELSE 0 END) sumfd  ")
				.append("FROM  ")
				.append("  datainfo d  ")
				.append("LEFT JOIN (SELECT * FROM financialsettlementdetails WHERE FIND_IN_SET(branchcode,?) ");
		qb.add(branchCode);
		if (StringUtils.isNotEmpty(invoiceStartDate)) {
			sb.append(" and Date(invoicedate) >= ? ");
			qb.add(invoiceStartDate);
		}
		if (StringUtils.isNotEmpty(invoiceEndDate)) {
			sb.append(" and Date(invoicedate) <= ? ");
			qb.add(invoiceEndDate);
		}
		if (StringUtils.isNotEmpty(companyName)) {
			sb.append(" and FIND_IN_SET(insurancecompanyname,?) ");
			qb.add(companyName);
		}
		sb.append(" ) f ON  f.insurancesubtype = d.`InnerCode` OR f.insurancetype=d.`InnerCode`  ")
				.append("WHERE d.Type = 'coverage_cx'  ")
				.append("GROUP BY ")
				.append("	d.`InnerCode` ")
				.append("ORDER BY ")
				.append("	d.OrderFlag");
		qb.setSQL(sb.toString());
		DataTable dt = qb.executeDataTable();
		dt.insertRow(new Object[] { "00", "1", null, "合计",
				"0",
				"0",
				"0",
				"0",
				"0",
				"0"
		});
		return dt;
	}

	DataTable calculateSumRSX(DataTable dataTable) {

		DataRow rsRow = null, sumRow = null;
		Map<String, BigDecimal> rsMap = new HashMap<String, BigDecimal>();
		rsMap.put("sump1", new BigDecimal(0));
		rsMap.put("sump1z", new BigDecimal(0));
		rsMap.put("sump1x", new BigDecimal(0));
		rsMap.put("sump1d", new BigDecimal(0));
		rsMap.put("sump2", new BigDecimal(0));
		rsMap.put("sump2z", new BigDecimal(0));
		rsMap.put("sump2x", new BigDecimal(0));
		rsMap.put("sump2d", new BigDecimal(0));
		rsMap.put("sumf1", new BigDecimal(0));
		rsMap.put("sumf1z", new BigDecimal(0));
		rsMap.put("sumf1x", new BigDecimal(0));
		rsMap.put("sumf1d", new BigDecimal(0));
		rsMap.put("sumf2", new BigDecimal(0));
		rsMap.put("sumf2z", new BigDecimal(0));
		rsMap.put("sumf2x", new BigDecimal(0));
		rsMap.put("sumf2d", new BigDecimal(0));
		Map<String, BigDecimal> totalMap = new HashMap<String, BigDecimal>();
		totalMap.putAll(rsMap);

		for (DataRow dataRow : dataTable) {
			// 新单保费
			String sump1Str = dataRow.getString("sump1");
			String sump1zStr = dataRow.getString("sump1z");
			String sump1xStr = dataRow.getString("sump1x");
			String sump1dStr = dataRow.getString("sump1d");
			BigDecimal sump1 = new BigDecimal(sump1Str);
			BigDecimal sump1z = new BigDecimal(sump1zStr);
			BigDecimal sump1x = new BigDecimal(sump1xStr);
			BigDecimal sump1d = new BigDecimal(sump1dStr);
			// 续期保费
			String sump2Str = dataRow.getString("sump2");
			String sump2zStr = dataRow.getString("sump2z");
			String sump2xStr = dataRow.getString("sump2x");
			String sump2dStr = dataRow.getString("sump2d");
			BigDecimal sump2 = new BigDecimal(sump2Str);
			BigDecimal sump2z = new BigDecimal(sump2zStr);
			BigDecimal sump2x = new BigDecimal(sump2xStr);
			BigDecimal sump2d = new BigDecimal(sump2dStr);
			// 新单佣金
			String sumf1Str = dataRow.getString("sumf1");
			String sumf1zStr = dataRow.getString("sumf1z");
			String sumf1xStr = dataRow.getString("sumf1x");
			String sumf1dStr = dataRow.getString("sumf1d");
			BigDecimal sumf1 = new BigDecimal(sumf1Str);
			BigDecimal sumf1z = new BigDecimal(sumf1zStr);
			BigDecimal sumf1x = new BigDecimal(sumf1xStr);
			BigDecimal sumf1d = new BigDecimal(sumf1dStr);
			// 续期佣金
			String sumf2Str = dataRow.getString("sumf2");
			String sumf2zStr = dataRow.getString("sumf2z");
			String sumf2xStr = dataRow.getString("sumf2x");
			String sumf2dStr = dataRow.getString("sumf2d");
			BigDecimal sumf2 = new BigDecimal(sumf2Str);
			BigDecimal sumf2z = new BigDecimal(sumf2zStr);
			BigDecimal sumf2x = new BigDecimal(sumf2xStr);
			BigDecimal sumf2d = new BigDecimal(sumf2dStr);
			// 保监险种分类
			String typeCode = dataRow.getString("typeCode");
			// 1:大类,2:小类.
			String level = dataRow.getString("level");
			if ("1".equalsIgnoreCase(level)) {
				totalMap.put("sump1", totalMap.get("sump1")
						.add(sump1));
				totalMap.put("sump2", totalMap.get("sump2")
						.add(sump2));
				totalMap.put("sumf1", totalMap.get("sumf1")
						.add(sumf1));
				totalMap.put("sumf2", totalMap.get("sumf2")
						.add(sumf2));
				totalMap.put("sump1z", totalMap.get("sump1z")
						.add(sump1z));
				totalMap.put("sump2z", totalMap.get("sump2z")
						.add(sump2z));
				totalMap.put("sumf1z", totalMap.get("sumf1z")
						.add(sumf1z));
				totalMap.put("sumf2z", totalMap.get("sumf2z")
						.add(sumf2z));
				totalMap.put("sump1x", totalMap.get("sump1x")
						.add(sump1x));
				totalMap.put("sump2x", totalMap.get("sump2x")
						.add(sump2x));
				totalMap.put("sumf1x", totalMap.get("sumf1x")
						.add(sumf1x));
				totalMap.put("sumf2x", totalMap.get("sumf2x")
						.add(sumf2x));
				totalMap.put("sump1d", totalMap.get("sump1d")
						.add(sump1d));
				totalMap.put("sump2d", totalMap.get("sump2d")
						.add(sump2d));
				totalMap.put("sumf1d", totalMap.get("sumf1d")
						.add(sumf1d));
				totalMap.put("sumf2d", totalMap.get("sumf2d")
						.add(sumf2d));
			}

			if (typeCode.trim().equalsIgnoreCase("0001")) {
				rsRow = dataRow;
			} else if (typeCode.trim().equalsIgnoreCase("00")) {
				sumRow = dataRow;
			} else if (typeCode.startsWith("0001") && "1".equalsIgnoreCase(level)) {
				rsMap.put("sump1", rsMap.get("sump1")
						.add(sump1));
				rsMap.put("sump2", rsMap.get("sump2")
						.add(sump2));
				rsMap.put("sumf1", rsMap.get("sumf1")
						.add(sumf1));
				rsMap.put("sumf2", rsMap.get("sumf2")
						.add(sumf2));
				rsMap.put("sump1z", rsMap.get("sump1z")
						.add(sump1z));
				rsMap.put("sump2z", rsMap.get("sump2z")
						.add(sump2z));
				rsMap.put("sumf1z", rsMap.get("sumf1z")
						.add(sumf1z));
				rsMap.put("sumf2z", rsMap.get("sumf2z")
						.add(sumf2z));
				rsMap.put("sump1x", rsMap.get("sump1x")
						.add(sump1x));
				rsMap.put("sump2x", rsMap.get("sump2x")
						.add(sump2x));
				rsMap.put("sumf1x", rsMap.get("sumf1x")
						.add(sumf1x));
				rsMap.put("sumf2x", rsMap.get("sumf2x")
						.add(sumf2x));
				rsMap.put("sump1d", rsMap.get("sump1d")
						.add(sump1d));
				rsMap.put("sump2d", rsMap.get("sump2d")
						.add(sump2d));
				rsMap.put("sumf1d", rsMap.get("sumf1d")
						.add(sumf1d));
				rsMap.put("sumf2d", rsMap.get("sumf2d")
						.add(sumf2d));
			}

		}
		if (rsRow != null) {
			rsRow.set("sump1", rsMap.get("sump1"));
			rsRow.set("sump1z", rsMap.get("sump1z"));
			rsRow.set("sump1x", rsMap.get("sump1x"));
			rsRow.set("sump1d", rsMap.get("sump1d"));
			rsRow.set("sump2", rsMap.get("sump2"));
			rsRow.set("sump2z", rsMap.get("sump2z"));
			rsRow.set("sump2x", rsMap.get("sump2x"));
			rsRow.set("sump2d", rsMap.get("sump2d"));
			rsRow.set("sumf1", rsMap.get("sumf1"));
			rsRow.set("sumf1z", rsMap.get("sumf1z"));
			rsRow.set("sumf1x", rsMap.get("sumf1x"));
			rsRow.set("sumf1d", rsMap.get("sumf1d"));
			rsRow.set("sumf2", rsMap.get("sumf2"));
			rsRow.set("sumf2z", rsMap.get("sumf2z"));
			rsRow.set("sumf2x", rsMap.get("sumf2x"));
			rsRow.set("sumf2d", rsMap.get("sumf2d"));
		}
		if (sumRow != null) {
			sumRow.set("sump1", totalMap.get("sump1"));
			sumRow.set("sump1z", totalMap.get("sump1z"));
			sumRow.set("sump1x", totalMap.get("sump1x"));
			sumRow.set("sump1d", totalMap.get("sump1d"));
			sumRow.set("sump2", totalMap.get("sump2"));
			sumRow.set("sump2z", totalMap.get("sump2z"));
			sumRow.set("sump2x", totalMap.get("sump2x"));
			sumRow.set("sump2d", totalMap.get("sump2d"));
			sumRow.set("sumf1", totalMap.get("sumf1"));
			sumRow.set("sumf1z", totalMap.get("sumf1z"));
			sumRow.set("sumf1x", totalMap.get("sumf1x"));
			sumRow.set("sumf1d", totalMap.get("sumf1d"));
			sumRow.set("sumf2", totalMap.get("sumf2"));
			sumRow.set("sumf2z", totalMap.get("sumf2z"));
			sumRow.set("sumf2x", totalMap.get("sumf2x"));
			sumRow.set("sumf2d", totalMap.get("sumf2d"));
		}

		return dataTable;
	}

	DataTable calculateSumCX(DataTable dataTable) {

		DataRow rsRow = null, sumRow = null;
		Map<String, BigDecimal> rsMap = new HashMap<String, BigDecimal>();
		rsMap.put("sump", new BigDecimal(0));
		rsMap.put("sumpz", new BigDecimal(0));
		rsMap.put("sumpd", new BigDecimal(0));
		rsMap.put("sumf", new BigDecimal(0));
		rsMap.put("sumfz", new BigDecimal(0));
		rsMap.put("sumfd", new BigDecimal(0));
		Map<String, BigDecimal> totalMap = new HashMap<String, BigDecimal>();
		totalMap.putAll(rsMap);

		for (DataRow dataRow : dataTable) {
			// 保费
			String sumpStr = dataRow.getString("sump");
			String sumpzStr = dataRow.getString("sumpz");
			String sumpdStr = dataRow.getString("sumpd");
			BigDecimal sump = new BigDecimal(sumpStr);
			BigDecimal sumpz = new BigDecimal(sumpzStr);
			BigDecimal sumpd = new BigDecimal(sumpdStr);
			// 佣金
			String sumfStr = dataRow.getString("sumf");
			String sumfzStr = dataRow.getString("sumfz");
			String sumfdStr = dataRow.getString("sumfd");
			BigDecimal sumf = new BigDecimal(sumfStr);
			BigDecimal sumfz = new BigDecimal(sumfzStr);
			BigDecimal sumfd = new BigDecimal(sumfdStr);
			// 保监险种分类
			String typeCode = dataRow.getString("typeCode");
			// 1:大类,2:小类.
			String level = dataRow.getString("level");
			if ("1".equalsIgnoreCase(level)) {
				totalMap.put("sump", totalMap.get("sump")
						.add(sump));
				totalMap.put("sumf", totalMap.get("sumf")
						.add(sumf));
				totalMap.put("sumpz", totalMap.get("sumpz")
						.add(sumpz));
				totalMap.put("sumpd", totalMap.get("sumpd")
						.add(sumpd));
				totalMap.put("sumfz", totalMap.get("sumfz")
						.add(sumfz));
				totalMap.put("sumfd", totalMap.get("sumfd")
						.add(sumfd));
			}

			if (typeCode.trim().equalsIgnoreCase("0001")) {
				rsRow = dataRow;
			} else if (typeCode.trim().equalsIgnoreCase("00")) {
				sumRow = dataRow;
			} else if (typeCode.startsWith("0001") && "1".equalsIgnoreCase(level)) {
				rsMap.put("sump", rsMap.get("sump")
						.add(sump));
				rsMap.put("sumf", rsMap.get("sumf")
						.add(sumf));
				rsMap.put("sumpz", rsMap.get("sumpz")
						.add(sumpz));
				rsMap.put("sumfz", rsMap.get("sumfz")
						.add(sumfz));
				rsMap.put("sumpd", rsMap.get("sumpd")
						.add(sumpd));
				rsMap.put("sumfd", rsMap.get("sumfd")
						.add(sumfd));
			}

		}
		if (rsRow != null) {
			rsRow.set("sump", rsMap.get("sump"));
			rsRow.set("sumpz", rsMap.get("sumpz"));
			rsRow.set("sumpd", rsMap.get("sumpd"));
			rsRow.set("sumf", rsMap.get("sumf"));
			rsRow.set("sumfz", rsMap.get("sumfz"));
			rsRow.set("sumfd", rsMap.get("sumfd"));
		}
		if (sumRow != null) {
			sumRow.set("sump", totalMap.get("sump"));
			sumRow.set("sumpz", totalMap.get("sumpz"));
			sumRow.set("sumpd", totalMap.get("sumpd"));
			sumRow.set("sumf", totalMap.get("sumf"));
			sumRow.set("sumfz", totalMap.get("sumfz"));
			sumRow.set("sumfd", totalMap.get("sumfd"));
		}

		return dataTable;
	}

	void createSheet(Map<String, DataTable> dataTableMap) {

	}

//	public static void main(String[] args) {
//
//		FinancialSettlementStatisticsDataTable f = new FinancialSettlementStatisticsDataTable();
//		DataTable dataTable = f.queryRSX(null, null, null, null);
//		dataTable = f.calculateSumRSX(dataTable);
//		System.out.println(dataTable);
//		dataTable = f.queryCX(null, null, null, null);
//		dataTable = f.calculateSumCX(dataTable);
//		System.out.println(dataTable);
//	}
}
