package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.lis.pubfun.PubFun;

/** 
 * @ClassName: MemberMarketing
 * @Description: TODO(会员营销)
 * @author CongZN
 * @date 2014-12-1 下午02:23:29 
 * 
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
public class MemberAddedDetail extends Page {

	/**
	 * @Title: dg1DataBind.
	 * @Description: TODO(回购率查询).
	 * @param dga
	 *            void.
	 * @author CongZN.
	 */
	public static void dg1DataBind(DataGridAction dga) {

		String beginDate = dga.getParams().getString("BeginDate")+" 00:00:00";
		String endDate = dga.getParams().getString("EndDate")+" 23:59:59";
		String from = dga.getParams().getString("From");
		
		QueryBuilder query_number = new QueryBuilder("Select ");
		if("insured".equals(from)){
			query_number.append(" IFNULL((SELECT getParentCode(s1.channelsn,'01') FROM DUAL),'无') channalName, ");
			query_number.append(" s2.recognizeeName applicantName, ");
			query_number.append(" s2.recognizeeSexName applicantSexName, ");
			query_number.append(" s2.recognizeeBirthday applicantBirthday, ");
			query_number.append(" s2.recognizeeIdentityTypeName applicantIdentityTypeName, ");
			query_number.append(" s2.recognizeeIdentityId applicantIdentityId, ");
			query_number.append(" s2.recognizeeMobile applicantMobile, ");
			query_number.append(" IFNULL(s2.recognizeeMail,'无') applicantMail, ");
			query_number.append(" IFNULL((SELECT if (email is null or email = '',mobileNO,email) FROM member WHERE id = s1.memberid),'无') member, ");
			query_number.append(" IFNULL(DATE_FORMAT(MIN(t4.receiveDate),'%Y-%m-%d'),'无') AS modifyDate , ");
			query_number.append(" s1.orderSn orderSn  ");
			query_number.append(" FROM sdorders s1 , sdinformationinsured s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' ");
			query_number.append(" GROUP BY s2.recognizeeName,s2.recognizeeName,s2.recognizeeBirthday HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ? ORDER BY s1.channelsn,t4.receiveDate ASC ");
			query_number.add(endDate);
			query_number.add(beginDate);
		}else{
			query_number.append(" IFNULL((SELECT getParentCode(s1.channelsn,'01') FROM DUAL),'无') channalName, ");
			query_number.append(" s2.applicantName applicantName, ");
			query_number.append(" s2.applicantSexName applicantSexName, ");
			query_number.append(" s2.applicantBirthday applicantBirthday, ");
			query_number.append(" s2.applicantIdentityTypeName applicantIdentityTypeName, ");
			query_number.append(" s2.applicantIdentityId applicantIdentityId, ");
			query_number.append(" s2.applicantMobile applicantMobile, ");
			query_number.append(" IFNULL(s2.applicantMail,'无') applicantMail, ");
			query_number.append(" IFNULL((SELECT if (email is null or email = '',mobileNO,email) FROM member WHERE id = s1.memberid),'无') member, ");
			query_number.append(" IFNULL(DATE_FORMAT(MIN(t4.receiveDate),'%Y-%m-%d'),'无') AS modifyDate , ");
			query_number.append(" s1.orderSn orderSn  ");
			query_number.append(" FROM sdorders s1 , SDInformationAppnt s2,SDInformation s3,tradeinformation t4   ");
			query_number.append(" WHERE s1.ordersn = s3.ordersn AND s2.informationSn = s3.informationSn  ");
			query_number.append(" AND s1.ordersn = t4.ordid AND s1.orderStatus >= '7' AND s1.orderStatus!='8' ");
			query_number.append(" GROUP BY s2.applicantName,s2.applicantSexName,s2.applicantBirthday HAVING  MIN(t4.receiveDate) <= ?  AND MIN(t4.receiveDate) >= ? ORDER BY s1.channelsn,t4.receiveDate ASC ");
			query_number.add(endDate);
			query_number.add(beginDate);
		}
		DataTable dt_query_number = query_number.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		
		
		dga.setTotal(query_number);
		dga.bindData(dt_query_number);
	}
	
	public static Mapx init(Mapx params) {
		params.put("createDate", PubFun.getCurrentDate());
		params.put("endCreateDate", PubFun.getCurrentDate());
		return params;
	}

}