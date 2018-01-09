package com.sinosoft.datachannel;

import cn.com.sinosoft.bean.SeoBean;
import com.sinosoft.cms.seo.InitSeo;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.StringUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeoData extends Page {


	public static void dg1DataBind(DataGridAction dga) {
		
		String type = dga.getParam("seoType");
		String seoDay = dga.getParam("seoDay");
		String createDate = dga.getParam("createDate");
		String endCreateDate = dga.getParam("endCreateDate");
		if(StringUtil.isEmpty(type)){
			type = "1";
		}
		String sqlFY = "select * from seodata where type='"+type+"' group by createdate";
		QueryBuilder qb1 = new QueryBuilder(sqlFY.toString());
		
		StringBuffer sbStr = new StringBuffer("select case when (ranking = 1 ) then '1' " +
				"when (ranking >=2 and ranking <=5) then '2' " +
				"when (ranking >=6 and ranking <=10) then '3' " +
				"when (ranking >=11 and ranking <=20) then '4' " +
				"when (ranking >=21 and ranking <=30) then '5' " +
				"when (ranking >=31 and ranking <=50) then '6' else '50' end oSum ,count(*) cSum,createdate cDate,type ttype " +
				"from seodata where type='"+type+"'" );
		
		
		if(!StringUtil.isEmpty(seoDay) && !"-1".equals(seoDay)){
			sbStr.append(gDay(seoDay));
		}
		
		if(!StringUtil.isEmpty(createDate)){
			sbStr.append(" and createdate >= '"+createDate+"'");
		}
		
		if(!StringUtil.isEmpty(endCreateDate)){
			sbStr.append(" and createdate <= '"+endCreateDate+"'");
		}
		
		sbStr.append(" group by oSum,createdate  order by createdate desc,oSum");
		
		QueryBuilder qb = new QueryBuilder(sbStr.toString());
		DataTable dt = qb.executeDataTable();
		SeoBean sb = null;

		String tempDate = null;

		List<SeoBean> list = new ArrayList<SeoBean>();

		int k = 0;

		int count = 0;

		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.get(i);
			if (!dr.get("cdate").toString().equals(tempDate)) {
				if (i != 0) {
					sb.setcSum(count);
					list.add(sb);
					count = 0;
				}
				sb = new SeoBean();
				sb.setcDate(dr.get("cdate").toString());
				sb.setType(dr.get("ttype").toString());
				i--;
			} else {
				k = Integer.parseInt(dr.get("osum").toString());
				String cSum = dr.get("csum").toString();
				count += Integer.parseInt(cSum);
				switch (k) {
				case 1:
					sb.setP1(cSum);
					break;
				case 2:
					sb.setP2(cSum);
					break;
				case 3:
					sb.setP3(cSum);
					break;
				case 4:
					sb.setP4(cSum);
					break;
				case 5:
					sb.setP5(cSum);
					break;
				case 6:
					sb.setP6(cSum);
					break;
				default:
					break;
				}

			}
			tempDate = dr.get("cdate").toString();
		}
		
		if(dt.getRowCount() > 0){
			sb.setcSum(count);
			list.add(sb);
			dt = encapsulationDt(list);
		}
		
		
		dga.setTotal(qb1);
		dga.bindData(dt);
	}

	/**
	 * 将LIST封装DT对象.
	 * @param list
	 * @return
	 */
	private static DataTable encapsulationDt(List<SeoBean> list) {
		DataTable dt = new DataTable();
		dt.insertColumns(new String[] { "cDate", "p1", "p2", "p3", "p4", "p5", "p6","cSum","ttype" });
		
		for (int i = 0; i < list.size(); i++) {
			SeoBean sb = new SeoBean();
			sb = list.get(i);
			Object[] seoRow = { sb.getcDate(),sb.getP1(),sb.getP2() ,sb.getP3() ,sb.getP4() ,sb.getP5() ,sb.getP6(),sb.getcSum(),sb.getType()};
			dt.insertRow(seoRow);
		}
		
		return dt;
	}
	
	/**
	 * 通过选中radio获取对天.
	 * @param type
	 * @return
	 */
	private static String gDay(String type){
		String result = null;
		String Today = countDay(0);
		int key = Integer.parseInt(type);
		switch (key) {
		case 0:
			result = " and createdate = '"+Today+"'"; 
			break;
		case 1:
			result = " and createdate = '"+countDay(1)+"'"; 
			break;
		case 2:
			result = " and createdate between '"+countDay(7)+"' and '"+Today+"'"; 
			break;
		case 3:
			result = " and createdate between '"+countDay(15)+"' and '"+Today+"'"; 
			break;
		case 4:
			result = " and createdate between '"+countDay(30)+"' and '"+Today+"'"; 
			break;
		}
		return result;
	}
	
	/**
	 * 传入天数计算.
	 * @param day
	 * @return
	 */
	private static String countDay(int dayNumber){
		Date d=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		String resDay = df.format(new Date(d.getTime() - dayNumber * 24L * 60 * 60 * 1000));
		return resDay;
	}
	
	/**
	 * 明细.
	 * @param dga
	 */
	public static void dg2DataBind(DataGridAction dga) {
		String type = dga.getParam("type");
		String createDate = dga.getParam("createDate");
		
		
		QueryBuilder qb = new QueryBuilder("select * from seodata where type=? and  createdate=? ");
		if(!StringUtil.isEmpty(dga.getSortString())){
			qb.append(dga.getSortString());
		}else{
			qb.append(" order by ranking ");
		}
		
		qb.add(type);
		qb.add(createDate);
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public void synExecute() {
		final String type = $V("type");
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				try {
					InitSeo.execute(this, type, Response);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始抓取......");
		$S("TaskID", "" + ltt.getTaskID());

	}
	
	
}
