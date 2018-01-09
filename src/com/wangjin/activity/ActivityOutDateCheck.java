package com.wangjin.activity;

import com.sinosoft.cms.mail.MailSenderInfo;
import com.sinosoft.cms.mail.SimpleMailSender;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActivityCalculate;
import com.sinosoft.platform.UserList;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;

import javax.mail.MessagingException;

public class ActivityOutDateCheck extends ConfigEanbleTaskManager {
	public static final String CODE = "com.wangjin.activity.ActivityOutDateCheck";

	public boolean isRunning(long id) {
		return false;
	}

	/**
	 * 
	 * @Title: pastDate
	 * @Description: TODO(活动过期检查)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	public boolean pastDate() {
		String sql = "SELECT id,activitysn,type,title FROM sdcouponactivityinfo WHERE UNIX_TIMESTAMP(endtime) <= UNIX_TIMESTAMP(NOW()) AND STATUS !='1' AND STATUS !='4'  ";
		QueryBuilder querybuilder = new QueryBuilder(sql);
		DataTable dt = querybuilder.executeDataTable();
		Transaction trans = new Transaction();
		if (dt.getRowCount() > 0) {
			
			StringBuffer sql_update = new StringBuffer(
					"update sdcouponactivityinfo set status='4' where 1=1 ");
			StringBuffer sql_where = new StringBuffer(" and id in (");
			StringBuffer sql_where_sn = new StringBuffer(" and activitysn in (");
			for (int i = 0; i < dt.getRowCount(); i++) {
				String activitysn =dt.getString(i, 1);
				String type =dt.getString(i, 2);
				String title =dt.getString(i, 3);
				sql_where.append("'" + dt.getString(i, 0) + "',");
				sql_where_sn.append("'" + activitysn + "',");
				
				try {
					updateProductInitPrem(activitysn, type,title,true);
				} catch (MessagingException e) {
					logger.error("活动停止定时任务,发生异常.方法 updateProductInitPrem");
				}
				
			}
			
			sql_update.append(sql_where);
			QueryBuilder querybuilder_update = new QueryBuilder(String.valueOf(
					sql_update).substring(0, sql_update.length() - 1)
					+ ")");
			int row = querybuilder_update.executeNoQuery();
			trans.add(querybuilder_update);
			String sql_delete = "delete from sdproductactivitylink where  1=1 ";
			sql_delete = sql_delete + sql_where_sn.toString().substring(0, sql_where_sn.length()-1)+ ")";
			QueryBuilder qb_delete = new QueryBuilder(sql_delete);
			trans.add(qb_delete);
			
			trans.commit();
			
			logger.info("活动过期检查更新了{}条数据", row);
			return true;
		} else {
			logger.info("活动过期检查没有过期的数据");
			return false;
		}

	}

	/**
	 * @Title: updateProductInitPrem.
	 * @Description: TODO(这里用一句话描述这个方法的作用).
	 * @param p_Activitysn
	 * @param p_Type void.
	 * @author CongZN.
	 * @throws MessagingException 
	 */
	public void updateProductInitPrem(String p_Activitysn, String p_Type,String p_ActivityTitle,boolean activityOutCheckFlag) throws MessagingException {
		MailSenderInfo mailInfo = new MailSenderInfo();
		String content = "";
		try {

			Transaction ts = new Transaction();

			String indexPriceActivity = Config.getValue("indexPriceActivityState");
			
			if (StringUtil.isEmpty(indexPriceActivity)) {
				logger.warn("定时任务：活动过期,更新首页价格 活动状态未配置!");
			}
			
			String[] indexPriceActivityArray = indexPriceActivity.split(",");

			boolean state = false;
			for (int i = 0; i < indexPriceActivityArray.length; i++) {
				if (p_Type.equals(indexPriceActivity)) {
					state = true;
				}
			}

			if (state) {

				StringBuffer query_ActivityProcutSql = new StringBuffer();

				QueryBuilder query_Prem;

				String articleID = "";
				String InitPrem = "";
				String newInitPrem = "";

				query_ActivityProcutSql
						.append("select s2.ProductId from sdcouponactivityinfo s1,sdproductactivitylink s2 where s1.activitysn = s2.activitysn and s1.activitysn = ? and ActivityChannel = 'wj'");

				String query_InitPremSql = "select z2.ID,z2.TextValue from zcarticle z1,zdcolumnvalue z2 where z1.id = z2.relaid and z1.prop4 = ? and ColumnCode = 'InitPrem' ";

				String query_BasePremV3Sql = "select z2.ID,z2.TextValue from zcarticle z1,zdcolumnvalue z2 where z1.id = z2.relaid and z1.prop4 = ? and ColumnCode = 'BasePremV3' ";

				QueryBuilder query_ActivityProcut = new QueryBuilder(
						query_ActivityProcutSql.toString());
				query_ActivityProcut.add(p_Activitysn);

				DataTable dt = query_ActivityProcut.executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					String productID = dt.getString(i, "ProductId");
					// 查询原价
					query_Prem = new QueryBuilder(query_InitPremSql);
					query_Prem.add(productID);
					DataTable result_InitPrem = query_Prem.executeDataTable();
					InitPrem = "0";
					if(result_InitPrem!=null && result_InitPrem.getRowCount()>=1){
						InitPrem = result_InitPrem.getString(0, "TextValue");
					}
					
					int reLen = result_InitPrem.getRowCount();
					query_Prem = new QueryBuilder(query_BasePremV3Sql);
					query_Prem.add(productID);
					// 首页产品不展示会员折扣活动价格
					newInitPrem = ActivityCalculate.ProductCalculate(productID,"",
							InitPrem, "wj", "");
					//针对首页产品，不进行ajax刷新，活动结束会需要更行最新活动价BasePremValue
					DataTable dt1 = query_Prem.executeDataTable();
					String basepremv3 ="0.0";
					if(dt1!=null && dt1.getRowCount()>=1){
						basepremv3 = dt1.getString(0, "TextValue").replace("￥", "");
					}
					if (reLen >= 1) {
						for(int k=0;k<reLen;k++){
							if(activityOutCheckFlag){
								//活动暂停或者过期
								articleID = result_InitPrem.getString(k, "ID");
								InitPrem = result_InitPrem.getString(k, "TextValue");
								String cArticleID = new QueryBuilder("SELECT relaid FROM zdcolumnvalue WHERE id=?",articleID).executeString();
								QueryBuilder qb2 = new QueryBuilder("UPDATE zdcolumnvalue SET textvalue=? WHERE relaid =? AND columncode='BasePremValue'");
								qb2.add(InitPrem);
								qb2.add(cArticleID);
								ts.add(qb2);
								//活动结束后更新BasePremV3 为空
								if(StringUtil.isNotEmpty(basepremv3)){
									String aPrem = new QueryBuilder(" SELECT remark4 FROM sdproduct WHERE productid=? ",productID).executeString();
									QueryBuilder qb1 = new QueryBuilder("UPDATE zdcolumnvalue SET textvalue=? WHERE relaid =? AND columncode='BasePremV3'");
									QueryBuilder qb1_1 = new QueryBuilder("UPDATE sdsearchrelaproduct SET BasePremV3=? WHERE ProductID =? ");
									if(aPrem.equals(InitPrem)){
										qb1.add("");
										qb1_1.add("");
									}else{
										qb1.add("￥"+aPrem);
										qb1_1.add("￥"+aPrem);
									}
									qb1.add(cArticleID);
									qb1_1.add(productID);
									ts.add(qb1);
									ts.add(qb1_1);
								}
							}else{//活动启用
								articleID = result_InitPrem.getString(k, "ID");
								InitPrem = result_InitPrem.getString(k, "TextValue");
								if(Double.parseDouble(newInitPrem)<Double.parseDouble(InitPrem)){
									String cArticleID = new QueryBuilder("SELECT relaid FROM zdcolumnvalue WHERE id=?",articleID).executeString();
									QueryBuilder qb2 = new QueryBuilder("UPDATE zdcolumnvalue SET textvalue=? WHERE relaid =? AND columncode='BasePremValue'");
									qb2.add(newInitPrem);
									qb2.add(cArticleID);
									ts.add(qb2);
									//活动启用后更新BasePremV3
									QueryBuilder qb1 = new QueryBuilder("UPDATE zdcolumnvalue SET textvalue=? WHERE relaid =? AND columncode='BasePremV3'");
									qb1.add("￥"+InitPrem);
									qb1.add(cArticleID);
									ts.add(qb1);
								}
							}
						}
					}
				}
				
				String toMail = Config.getValue("indexPriceActivityMail");
				String subject = "活动过期或暂停提醒!";
				if(activityOutCheckFlag){
					content = "活动过期或暂停："+p_ActivityTitle+" 发布状态：";
				}else{
					content = "活动启用："+p_ActivityTitle+" 发布状态：";
					subject="活动启用提醒!";
				}
				mailInfo.setSubject(subject);
				mailInfo.setToAddress(toMail);
				
				if (ts.commit()) {
					// 更新完成价格-发布首页-发送邮件 
					
					User.setCurrent(new UserData());
					User.setRealName("系统管理员");
					User.setUserName(UserList.ADMINISTRATOR);
					User.setLogin(true);
					User.setManager(true);
					User.setBranchInnerCode("0001");
					User.setBranchInnerCode("0001");
					// 更新完成价格-发布首页-发送邮件
					PageGenerator p = new PageGenerator();
					p.staticSiteIndex( new QueryBuilder("select id from zcsite").executeInt() );
					
					content += "成功!";
				}else{
					content += "失败!";
				}
				
				mailInfo.setContent(content);
				SimpleMailSender.sendHtmlMail(mailInfo);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			content += "失败!";
			mailInfo.setContent(content);
			SimpleMailSender.sendHtmlMail(mailInfo);
		}

	}
	
	public static void main(String[] args) {
		User.setCurrent(new UserData());
		User.setRealName("系统管理员");
		User.setUserName(UserList.ADMINISTRATOR);
		User.setLogin(true);
		User.setManager(true);
		User.setBranchInnerCode("0001");
		User.setBranchInnerCode("0001");
		// 更新完成价格-发布首页-发送邮件
		PageGenerator p = new PageGenerator();
		p.staticSiteIndex( new QueryBuilder("select id from zcsite").executeInt() );
	}
	
	/**
	 * 
	 * @Title: execute
	 * @Description: TODO(定时任务调用主方法)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public void execute(long id) {
		// 活动过期提醒
		if ("0".equals(id + "")) {
			if (pastDate()) {
				logger.info("活动过期检测成功！");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "活动过期定时任务");
		return map;
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "活动过期定时任务";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.wangjin.activity.ActivityOutDateCheck";
	}
}
