package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @Author guanyulong
 * @Date 2017-01-13
 */
public class UserActivationMonitor extends Page{
	
	public static Mapx init(Mapx params) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		params.put("createDate", fmt.format(date));
		params.put("endCreateDate", fmt.format(date));
		return params;
	}
	
	public void searchUserActivationMonitor(DataGridAction dga) {
		String category = dga.getParams().getString("category");
		String sendWay = dga.getParams().getString("sendWay");
		String sendStatus = dga.getParams().getString("sendStatus");
		String createDate = dga.getParams().getString("createDate");
		String endCreateDate = dga.getParams().getString("endCreateDate");
		// 得到查询SQL 
		StringBuffer Sql = new StringBuffer();
		Sql.append( "SELECT u.category,u.createDate ,(u.oldPrice - u.payPrice) AS cheap ,u.ProductGenera,u.sendWay,u.sendStatus,u.productName,u.memberid ,u.activitysn ");
		Sql.append( " FROM UserActivationRecord u WHERE 1=1");
		
		// 创建开始日期
		if (StringUtil.isNotEmpty(createDate)) {
			Sql.append(" AND DATE_FORMAT(u.createdate,'%Y-%m-%d') >= DATE_FORMAT('"+createDate.trim()+"','%Y-%m-%d') ");
		}
		// 创建终止日期
		if (StringUtil.isNotEmpty(endCreateDate)) {
			Sql.append(" AND DATE_FORMAT(u.createdate,'%Y-%m-%d') <= DATE_FORMAT('"+endCreateDate.trim()+"','%Y-%m-%d') " );
		}
		// 发送方式  电话/邮箱
		if (StringUtil.isNotEmpty(sendWay)) {
			if("1".equals(sendWay)){
				Sql.append(" AND  u.sendWay = 'email' " );
			}else{
				Sql.append(" AND  u.sendWay = 'mobileNo' " );
			}
		}
		
		// 类别(收藏/待支付/暂存)
		if (StringUtil.isNotEmpty(category)) {
			Sql.append(" AND u.category ='"+category+"' ");
		}
		//发送状态 （成功/失败）
		if (StringUtil.isNotEmpty(sendStatus)) {
			Sql.append(" AND (u.sendStatus='"+sendStatus+"' )");
		}
		Sql.append(" ORDER BY u.createdate DESC ");

		// 测试输出结果SQL
		QueryBuilder qb = new QueryBuilder(Sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		String sendWayUse = "",sendStatusUse = "" ;
		if (dt != null) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				sendWayUse = dt.getString(i, "sendWay");
				sendStatusUse = dt.getString(i, "sendStatus");
				if(sendWayUse.contains("email"))
				{
					dt.set(i, "sendWay",  "邮件" );
				}else if(sendWayUse.contains("mobileNo")){
					dt.set(i, "sendWay",  "短信" );
				}
				
				if(sendStatusUse.equals("Y"))
				{
					dt.set(i, "sendStatus",  "已发送" ); 
				}else{
					dt.set(i, "sendStatus",  "未发送" ); 
				}
				dt.set(i, "cheap",  String.valueOf(new DecimalFormat("######0.00").format(Double.valueOf(dt.getString(i, "cheap")))) ); 
			}
		}

		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	
	
	

}
