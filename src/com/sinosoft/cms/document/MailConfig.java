package com.sinosoft.cms.document;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDMailMouldConfigSchema;

/**
 * @ClassName: MailConfig.
 * @Description: TODO(邮件模板配置).
 * @author CongZN.
 * @date 2014-8-5 上午10:22:05
 * 
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
public class MailConfig extends Page {

	/**
	 * @Title: initAddDialog
	 * @Description: TODO(邮件模板配置-ADD 初始化选项).
	 * @return Mapx 返回类型
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initAddDialog(Mapx params) {
		DataTable dt = new QueryBuilder(
				"select CodeName,CodeValue from zdcode where codeType = 'MailTemplate' and prop1 ='Y' ")
				.executeDataTable();
		params.put("MailConfigRiskType", HtmlUtil.codeToOptions(
				"MailConfigRiskType"));
		params.put("MailTemplate", HtmlUtil.dataTableToOptions(dt, true));
		return params;
	}

	/**
	 * @Title: initAddDialogEditor.
	 * @Description: TODO(邮件模板配置-Editor 初始化选项).
	 * @return Mapx 返回类型.
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initDialogEditor(Mapx params) {
		String emailType = params.getString("emailType");
		ZDMailMouldConfigSchema zdm = new ZDMailMouldConfigSchema();
		zdm.setEmailType(emailType);
		if (zdm.fill()) {
			QueryBuilder query_MailConfigName = new QueryBuilder(
					"select CodeName from zdcode where CODETYPE = 'MailTemplate' and CodeValue = ?");
			query_MailConfigName.add(zdm.getEmailType());
			String mailConfigName = query_MailConfigName.executeString();
			params.put("MailConfigName", mailConfigName);
			params.put("EmailType", zdm.getEmailType());

			params.put("MailConfigRiskType", HtmlUtil.codeToOptions(
					"MailConfigRiskType", zdm.getProductCategory()));

			// 推荐产品
			if ("Y".equals(zdm.getProductFlag())) {
				params.put("IsVisiableYCheck", "checked");
			} else {
				params.put("IsVisiableNCheck", "checked");
			}

			// 推荐活动
			if ("Y".equals(zdm.getActivityFlag())) {
				params.put("IsNewViewYCheck", "checked");
			} else {
				params.put("IsNewViewNCheck", "checked");
			}

		}
		return params;
	}

	/**
	 * @Title: saveMailConfig.
	 * @Description: TODO(邮件模板配置-新增).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void saveMailConfig() {
		String emailType = $V("MailTemplate");
		String activityFlag = $V("productFlag");
		String productFlag = $V("activityFlag");
		String productCategory = $V("MailConfigRiskType");

		if (StringUtil.isEmpty(emailType)) {
			Response.setStatus(2);
			Response.setMessage("请选择模板!");
			return;
		}

		try {
			Transaction ts = new Transaction();
			QueryBuilder update_ZDCode = new QueryBuilder(
					"UPDATE ZDCODE SET PROP1 = 'N' WHERE CODETYPE = 'MailTemplate' AND CODEVALUE= ?");

			ZDMailMouldConfigSchema zdm = new ZDMailMouldConfigSchema();
			zdm.setEmailType(emailType);
			zdm.setActivityFlag(activityFlag);
			zdm.setProductFlag(productFlag);
			zdm.setProductCategory(productCategory);
			zdm.setCreateDate(PubFun.getCurrent());
			zdm.setOperator(User.getUserName());

			ts.add(zdm, Transaction.INSERT);

			// 更新ZDCODE MailTemplate 为 "N"
			update_ZDCode.add(emailType);
			ts.add(update_ZDCode);

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("保存失败! error:提交事务失败!");
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("saveMailConfig 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: updateMailConfig.
	 * @Description: TODO(邮件模板配置-编辑).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void updateMailConfig() {
		String emailType = $V("EmailType");
		String activityFlag = $V("activityFlag");
		String productFlag = $V("productFlag");
		String productCategory = $V("MailConfigRiskType");

		try {
			Transaction ts = new Transaction();

			ZDMailMouldConfigSchema zdm = new ZDMailMouldConfigSchema();
			zdm.setEmailType(emailType);
			if (zdm.fill()) {
				zdm.setActivityFlag(activityFlag);
				zdm.setProductFlag(productFlag);
				zdm.setProductCategory(productCategory);
				zdm.setOperator(User.getUserName());
				zdm.setModifyDate(PubFun.getCurrent());
			}

			ts.add(zdm, Transaction.UPDATE);

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("修改成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("修改失败! error:提交事务失败!");
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("修改失败! error:" + e.getMessage());
			logger.error("saveMailConfig 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: deleteMailConfig.
	 * @Description: TODO(邮件模板配置-删除).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void deleteMailConfig() {
		String emailType = $V("emailType");

		try {
			
			Transaction ts = new Transaction();
			
			String[] emailTypeArr = emailType.split(",");
			
			for(int i = 0; i < emailTypeArr.length; i++){
				emailType = emailTypeArr[i];
				
				QueryBuilder update_ZDCode = new QueryBuilder(
				"UPDATE ZDCODE SET PROP1 = 'Y' WHERE CODETYPE = 'MailTemplate' AND CODEVALUE= ?");
				ZDMailMouldConfigSchema zdm = new ZDMailMouldConfigSchema();
				zdm.setEmailType(emailType);
				update_ZDCode.add(emailType);
				ts.add(zdm, Transaction.DELETE);
				ts.add(update_ZDCode);
			}
			

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("删除失败! error:提交事务失败!");
			}
		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("删除失败! error:" + e.getMessage());
			logger.error("saveMailConfig 方法异常! error:" + e.getMessage(), e);
		}

	}

	/**
	 * @Title: queryMailConfig.
	 * @Description: TODO(邮件模板配置-查询).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void queryMailConfig(DataGridAction dga) {

		String MouldName = dga.getParams().getString("MouldName");

		StringBuffer query_Sql = new StringBuffer("");
		query_Sql
				.append("select z1.*,z2.CodeName from ZDMailMouldConfig z1,zdcode z2 where z1.EmailType = z2.CodeValue and z2.codeType = 'MailTemplate'  ");

		if (StringUtil.isNotEmpty(MouldName)) {
			query_Sql.append(" and z2.CodeName like '%" + MouldName.trim()
					+ "%'");
		}

		query_Sql.append(" order by z1.createDate desc ");
		QueryBuilder qb = new QueryBuilder(query_Sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("ProductCategory", HtmlUtil
				.codeToMapx("MailConfigRiskType"));
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * @Title: dg1DataBindProduct
	 * @Description: TODO(初始化产品)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	public static void dg1DataBindProduct(DataGridAction dga) {

		String productname = dga.getParams().getString("productname");
		String productID = dga.getParams().getString("productID");
		
		QueryBuilder qb_product = new QueryBuilder();
		
		if(StringUtil.isNotEmpty(productID)){
			String productIDs = CombinationConditions(productID.split(","));
			qb_product.append("SELECT productid as  id,productname FROM  sdproduct WHERE  IsPublish='Y' and productid in("+productIDs+") union ");
		}
		
		qb_product.append("SELECT productid as  id,productname FROM  sdproduct WHERE  IsPublish='Y' ");

		if (StringUtil.isNotEmpty(productname)) {
			qb_product.append(" and productname like " + "'%"
					+ productname.trim() + "%'");
		}

		qb_product.append(dga.getSortString());
		dga.setTotal(qb_product);
		DataTable dt = qb_product.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "id", dt.getString(i, "id"));
			dt.set(i, "productname", dt.getString(i, "productname"));
		}
		dga.bindData(dt);
	}

	/**
	 * @Title: saveMailConfig.
	 * @Description: TODO(邮件模板配置-关联产品).
	 * @return void 返回类型.
	 * @author CongZN.
	 */
	public void saveMailConfigProduct() {
		String emailType = $V("emailType");
		String ProductIDs = $V("ProductIDs");

		try {
			Transaction ts = new Transaction();
			ZDMailMouldConfigSchema zdm = new ZDMailMouldConfigSchema();
			zdm.setEmailType(emailType);
			if (zdm.fill()) {
				zdm.setProduct(ProductIDs);
				zdm.setModifyDate(PubFun.getCurrent());
				ts.add(zdm, Transaction.UPDATE);
			}

			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(2);
				Response.setMessage("保存失败! error:提交事务失败!");
			}

		} catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("saveMailConfig 方法异常! error:" + e.getMessage(), e);
		}

	}
	
	/**
	 * @Title: CombinationConditions.
	 * @Description: TODO(组合条件).
	 * @return String    返回类型.
	 * @author CongZN.
	 */
	public static String CombinationConditions(String[] StrArray) {
		StringBuffer sb = new StringBuffer("");
		if (StrArray.length > 0) {
			for (int i = 0; i < StrArray.length; i++) {
				sb.append("'"+StrArray[i] + "',");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
