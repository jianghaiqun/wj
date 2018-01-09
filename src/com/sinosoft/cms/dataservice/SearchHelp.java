package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @Author guanyulong
 * @Date 2017-01-13
 */
public class SearchHelp extends Page{
	
	public static Mapx init(Mapx params) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		params.put("createDate", fmt.format(date));
		params.put("endCreateDate", fmt.format(date));
		params.put("investigationStatus", HtmlUtil.codeToOptions("Investigation.Status", true));// 福禄订单状态
		return params;
	}
	
 
	
	
	public void searchHelpSearch(DataGridAction dga) {
		String searchLike = dga.getParams().getString("searchLike");
		String investigationStatus = dga.getParams().getString("investigationStatus");
		String createDate = dga.getParams().getString("createDate");
		String endCreateDate = dga.getParams().getString("endCreateDate");
		String userMemberName = dga.getParams().getString("userMemberName");
		// 得到查询SQL 
		StringBuffer Sql = new StringBuffer();
		Sql.append( "SELECT uco.memberId,uco.userip,uco.createTime,uco.investigationStatus,uco.reason,uco.insuranceName,uco.telephone,uco.searchName,");
		Sql.append( "IFNULL(IF(m.username='',m.email,m.username),m.email ) AS membername FROM UserSearchOpinion uco LEFT JOIN member m  ON m.id=memberid WHERE 1=1 ");

		// 创建开始日期
		if (StringUtil.isNotEmpty(createDate)) {
			Sql.append(" AND DATE_FORMAT(uco.createTime,'%Y-%m-%d') >= DATE_FORMAT('"+createDate.trim()+"','%Y-%m-%d') ");
		}

		// 创建终止日期
		if (StringUtil.isNotEmpty(endCreateDate)) {
			Sql.append(" AND DATE_FORMAT(uco.createTime,'%Y-%m-%d') <= DATE_FORMAT('"+endCreateDate.trim()+"','%Y-%m-%d') " );
		}
		// 调查分类 (满意，不满意 帮助)
		if (StringUtil.isNotEmpty(investigationStatus)) {
			Sql.append(" AND  uco.investigationStatus='" + investigationStatus + "'" );
		}
		// 用户分类  1匿名 2实名
		if (StringUtil.isNotEmpty(searchLike)) {
			if(searchLike.equals("1")){
				Sql.append(" AND uco.memberid IS  NULL AND uco.userip IS NOT NULL ");
			}else{
				Sql.append(" AND uco.memberid IS NOT NULL AND uco.userip IS NULL ");
			}
		}
		
		if (StringUtil.isNotEmpty(userMemberName)) {
			Sql.append(" AND (uco.userip='"+userMemberName+"' OR m.username='"+userMemberName+"' OR m.username='"+userMemberName+"' )");
		}
		Sql.append(" ORDER BY uco.createTime DESC ");

		// 测试输出结果SQL
		logger.info("SQL========={}", Sql.toString());
		QueryBuilder qb = new QueryBuilder(Sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.insertColumn("userName");
		dt.insertColumn("leaveMessage");
		String reason = "",insuranceName = "",telephone = "",userip = "",membername = "", investigationStatusShow = "",memberid = "" ;
		Mapx investigation_Status = CacheManager.getMapx("Code", "Investigation.Status");
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				reason = dt.getString(i, "reason");
				insuranceName = dt.getString(i, "insuranceName");
				telephone = dt.getString(i, "telephone");
				investigationStatusShow = dt.getString(i, "investigationStatus");
				memberid = dt.getString(i, "memberid");
				userip = dt.getString(i, "userip");
				membername = dt.getString(i, "membername");
				if(StringUtil.isNotEmpty(reason)){
					dt.set(i, "leaveMessage",  reason );
				}else if(StringUtil.isNotEmpty(insuranceName)){
					dt.set(i, "leaveMessage",  "保险公司:"+insuranceName + "/用户联系方式:" + telephone );
				}else{
					dt.set(i, "leaveMessage",  "" );
				}
				if(StringUtil.isNotEmpty(membername)){
					dt.set(i, "userName",  membername );
				}else{
					dt.set(i, "userName",  userip );
				}
				if (StringUtil.isNotEmpty(investigationStatusShow)) {
					dt.set(i, "investigationStatus", investigation_Status.get(investigationStatusShow));
				} else {
					dt.set(i, "investigationStatus", "");
				}
				if(StringUtil.isEmpty(memberid)){
					dt.set(i, "memberid",  userip );
				}
			}
		}

		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	
	
	

}
