package com.sinosoft.cms.settlement;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class FinancialSettlementDetailsExportNew {

	public DataTable query(String invoiceStartDate, String invoiceEndDate, String companyName,
			String branchCode, String coverage) {

		QueryBuilder qb = new QueryBuilder();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT	d.`InnerCode` typeCode, ")
				.append("d.`TreeLevel` treeLevel, ")
				.append("d.`prop2` `level`, ")
				.append("d.`Name` type, ")
				.append("c.codeValue channelTypeValue, ")
				.append("c.CodeName channelType, ")
				.append("IFNULL(sum(f.premium1st), 0) sump1, ")
				.append("IFNULL(sum(f.premium2nd), 0) sump2, ")
				.append("IFNULL(sum(f.fee1st), 0) sumf1, ")
				.append("IFNULL(sum(f.fee2nd), 0) sumf2  ")
				.append("FROM datainfo d ")
				.append("INNER JOIN zdcode c ")
				.append("LEFT JOIN financialsettlementdetails f ON c.CodeName = f.channeltype ")
				.append("AND (f.insurancesubtype = d.`InnerCode`) ")
				.append("WHERE c.CodeType = 'ChannelType' ")
				.append("AND c.ParentCode = 'ChannelType' ");
		if (StringUtils.isNotEmpty(invoiceStartDate)) {
			sb.append(" and Date(f.invoicedate) >= ? ");
			qb.add(invoiceStartDate);
		}
		if (StringUtils.isNotEmpty(invoiceEndDate)) {
			sb.append(" and Date(f.invoicedate) <= ? ");
			qb.add(invoiceEndDate);
		}
		if (StringUtils.isNotEmpty(companyName)) {
			sb.append(" and FIND_IN_SET(f.insurancecompanyname,?) ");
			qb.add(companyName);
		}
		if (StringUtils.isNotEmpty(branchCode)) {
			sb.append(" and FIND_IN_SET(f.branchcode,?) ");
			qb.add(branchCode);
		}
		if (StringUtils.isNotEmpty(coverage)) {
			sb.append(" and d.Type = ? ");
			qb.add(coverage);
		}
		sb.append("GROUP BY ")
				.append("d.`InnerCode`, ")
				.append("c.CodeName ")
				.append("ORDER BY ")
				.append("d.OrderFlag, ")
				.append("c.CodeValue");
		qb.setSQL(sb.toString());
		DataTable dt = qb.executeDataTable();
		List<Map<String, Object>> list = dt.toList();

		return dt;

	}





}
