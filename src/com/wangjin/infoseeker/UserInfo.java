package com.wangjin.infoseeker;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.math.BigDecimal;
import java.util.Date;

public class UserInfo extends Page {

	private static String today = DateUtil.toString(new Date(), "yyyy-MM-dd");
	private static String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");

	/**
	 * 初始化查询日期
	 * 
	 * @param params
	 * @return
	 */ 
	public static Mapx initStaff(Mapx params) {
		Mapx map = new Mapx();
		map.put("yesterday", yesterday);
		map.put("today", today);
		return map;
	}

	/**
	 * 获得用户注册统计的数据
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		String startDate = dga.getParam("startDate");
		String endDate = dga.getParam("endDate");
		String registType = dga.getParam("registType");
		String parameter = "";
		if (StringUtil.isNotEmpty(startDate)) {
			parameter = " and DATE_FORMAT(createdate,'%Y-%m-%d')>='" + startDate + "'";
		}
		if (StringUtil.isNotEmpty(endDate)) {
			parameter = parameter + " and DATE_FORMAT(createdate,'%Y-%m-%d')<='" + endDate + "'";
		}
		if (StringUtil.isNotEmpty(registType) && !"0".equals(registType)) {
			if ("1".equals(registType)) {
				parameter = parameter + " and registerType!='2'";
			} else if ("2".equals(registType)) {
				parameter = parameter + " and registerType='2'";
			} else if ("3".equals(registType)) {// 淘宝注册
				parameter = parameter + " and fromWap like 'tb%'";
			}
		}
		StringBuffer sql = new StringBuffer("select DATE_FORMAT(createdate,'%Y-%m-%d') 'registDate',count(1) 'count', ");
		        sql.append(" COUNT(1)-SUM((SELECT COUNT(DISTINCT memberid) FROM sdorders WHERE memberid=member.id AND orderstatus IN ('7', '10', '12', '14'))) ncount ");
				sql.append(" from member where createdate is not null ");
				sql.append(parameter);
				sql.append(" group by DATE_FORMAT(createdate,'%Y-%m-%d')");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		// 添加总计
		BigDecimal sum1 = new BigDecimal(0);
		BigDecimal sum2 = new BigDecimal(0);
		for (DataRow dr : dt) {
			sum1 = sum1.add(new BigDecimal(dr.get("count").toString()));
			sum2 = sum2.add(new BigDecimal(dr.get("ncount").toString()));
		}
		Object[] catalogRow = {"总计", sum1, sum2};
		dt.insertRow(catalogRow);
		dga.bindData(dt);
	}

	public static void dg1DataBind_UerList(DataGridAction dga) {
		String startDate = dga.getParams().getString("startDate");
		String endDate = dga.getParams().getString("end");
		String type = dga.getParams().getString("type");
		String allDate = dga.getParams().getString("allDate");
		String orderType = dga.getParams().getString("orderType"); // 0:会员注册数 1：未投保会员数
		
		if(StringUtil.isNotEmpty(type)){
			
			String parameter = "";
			if (!"1".equals(allDate)) {
				if (StringUtil.isNotEmpty(startDate)) {
					parameter = " and DATE_FORMAT(orders.createDate,'%Y-%m-%d')>='" + startDate + "'";
				} else {
					parameter = " and DATE_FORMAT(orders.createDate,'%Y-%m-%d')>='" + yesterday + "'";
				}
				if (StringUtil.isNotEmpty(endDate)) {
					parameter = parameter + " and DATE_FORMAT(orders.createDate,'%Y-%m-%d')<='" + endDate + "'";
				} else {
					parameter = parameter + " and DATE_FORMAT(orders.createDate,'%Y-%m-%d')<='" + today + "'";
				}
			}
			
			//根据回购次数检索会员信息
			StringBuffer sql = new StringBuffer();
			if(!"= 0".equals(type)){
				sql.append("select * from member m ");
				if("= 1".equals(type)){
					sql.append("join (select IF(t.count=1 ,t.memberId,'') as 'memberId0' ");
				}else if(">= 2".equals(type)){
					sql.append("join (select IF(t.count>=2 ,t.memberId,'') as 'memberId0' ");
				}else if(">= 3".equals(type)){
					sql.append("join (select IF(t.count>=3 ,t.memberId,'') as 'memberId0' ");
				}else if(">= 5".equals(type)){
					sql.append("join (select IF(t.count>=6 ,t.memberId,'') as 'memberId0' ");
				}else if(">= 10".equals(type)){
					sql.append("join (select IF(t.count>=10 ,t.memberId,'') as 'memberId0' ");
				}
				sql.append("from (select count(1) count,orders.memberId as 'memberId' ");
				sql.append("from SDOrders orders left join SDInformation info on (orders.ordersn = info.ordersn) left join sdproduct a2 on (info.productid=a2.productid) ");
				sql.append("where orders.orderstatus='7' and a2.InsureName!='百年人寿' and orders.memberId is not null and orders.memberId <> '' ");
				sql.append(parameter);
				sql.append(" group by orders.memberId ) t) m0 on m.id = m0.memberId0 ");
				sql.append("where m0.memberId0 <> '' and m.createdate is not null");
			}else{
				if("0".equals(orderType)){
					sql.append("select * from member m where 1=1");
					if (StringUtil.isNotEmpty(startDate)) {
						sql.append(" and DATE_FORMAT(m.createdate,'%Y-%m-%d')>='" + startDate + "'");
					}
					if (StringUtil.isNotEmpty(endDate)) {
						sql.append(" and DATE_FORMAT(m.createdate,'%Y-%m-%d')<='" + endDate + "'");
					}
				}else{
					sql.append("SELECT * FROM member m WHERE  ");
					sql.append("NOT EXISTS (SELECT 1 FROM sdorders WHERE memberid=m.id AND orderstatus in('7','10','12','14')) ");
					if (StringUtil.isNotEmpty(startDate)) {
						sql.append(" and DATE_FORMAT(createdate,'%Y-%m-%d')>='" + startDate + "'");
					}
					if (StringUtil.isNotEmpty(endDate)) {
						sql.append(" and DATE_FORMAT(createdate,'%Y-%m-%d')<='" + endDate + "'");
					}
				}
			}
			QueryBuilder qb = new QueryBuilder(sql.toString());
			dga.setTotal(qb);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dt.decodeColumn("sex", HtmlUtil.codeToMapx("Gender"));
			dt.decodeColumn("VIPType", HtmlUtil.codeToMapx("Member.Type"));
			// 是否启用
			DataTable dt1 = new QueryBuilder("select CodeValue as isAccountEnabled,CodeName from zdcode where codetype =?",
					"isAccountEnabled").executeDataTable();
			dt.decodeColumn("isAccountEnabled", dt1.toMapx("isAccountEnabled", "CodeName"));
			// 等级
			DataTable dt2 = new QueryBuilder("select id as memberRank_id ,name from memberRank ").executeDataTable();
			dt.decodeColumn("memberRank_id", dt2.toMapx("memberRank_id", "name"));
			dt.decodeColumn("IDType", CacheManager.getMapx("Code", "member.IDType"));
			
			Mapx map = new Mapx();
			map.put("0", "邮箱注册");
			map.put("1", "手机注册");
			map.put("2", "自动注册");
			dt.decodeColumn("registerType", map);
			dga.bindData(dt);
			
		}else{
			String registDate = dga.getParams().getString("registDate");
			String registType = dga.getParams().getString("registType");
			QueryBuilder qb = new QueryBuilder(
					"select * from member e where id is not null and id <>'' and DATE_FORMAT(createdate,'%Y-%m-%d')='"
							+ registDate + "'");
			if (null != registType) {
				if ("1".equals(registType)) {
					qb.append(" and registerType!='2'");
				} else if ("2".equals(registType)) {
					qb.append(" and registerType='2'");
				}else if("3".equals(registType)){
					qb.append(" and fromWap like 'tb%'");
				};
			}
			if("1".equals(orderType)){
				qb.append(" AND NOT EXISTS (SELECT 1 FROM sdorders WHERE memberid=e.id AND orderstatus in('7','10','12','14')) ");
			}
			// 按照登录时间逆序排序，将最后登录的放到最上面
			qb.append("order by logindate desc");
			dga.setTotal(qb);
			DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
			dt.decodeColumn("sex", HtmlUtil.codeToMapx("Gender"));
			dt.decodeColumn("VIPType", HtmlUtil.codeToMapx("Member.Type"));
			// 是否启用
			DataTable dt1 = new QueryBuilder("select CodeValue as isAccountEnabled,CodeName from zdcode where codetype =?",
					"isAccountEnabled").executeDataTable();
			dt.decodeColumn("isAccountEnabled", dt1.toMapx("isAccountEnabled", "CodeName"));
			// 等级
			DataTable dt2 = new QueryBuilder("select id as memberRank_id ,name from memberRank ").executeDataTable();
			dt.decodeColumn("memberRank_id", dt2.toMapx("memberRank_id", "name"));
			dt.decodeColumn("IDType", CacheManager.getMapx("Code", "member.IDType"));
	
			Mapx map = new Mapx();
			map.put("0", "邮箱注册");
			map.put("1", "手机注册");
			map.put("2", "自动注册");
			dt.decodeColumn("registerType", map);
			dga.bindData(dt);
		}
	}
}
