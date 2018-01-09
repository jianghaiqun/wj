package com.sinosoft.cms.plan.activity.qixi;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;

/**
 * 七夕购买安心恋爱保险的保单数据.<br>
 * Created by dongsheng on 2017/7/12.
 */
public class QiXiDataTable implements DataTableHandler {

	@Override
	public DataTable findDataList() {

		StringBuilder sb = new StringBuilder(
				"select policyNo,recognizeeName,applicantName,applicantMobile,applicantIdentityId,svaliDate ")
				.append("from sdinformationrisktype r LEFT JOIN sdinformationappnt a ON r.applicantSn=a.applicantSn ")
				.append("LEFT JOIN sdinformationinsured i ON r.recognizeeSn=i.recognizeeSn ")
				.append("where 1=1 ")
				.append("AND DATE(svaliDate) = ? ")
				.append("AND riskCode IN ('223902001', '223902002') ")
				.append("AND DATE(svaliDate) >= ? ")
				.append("AND DATE(svaliDate) <= ? ")
				.append("AND appStatus = '1' ");
		QueryBuilder qb = new QueryBuilder(sb.toString());
		qb.add(DateUtil.getCurrentDate());
		Mapx mapx = new QueryBuilder(
				"select CodeValue,Memo from zdcode where CodeType = 'QiXiActivityPeriod' and ParentCode = 'QiXiActivityPeriod'")
				.executeDataTable().toMapx(0, 1);
		qb.add(mapx.getString("startDate"));
		qb.add(mapx.getString("endDate"));
		return qb.executeDataTable();
	}
}
