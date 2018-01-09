package com.sinosoft.cms.document;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDMailMouldCategorySchema;

/**
 * 邮件模板产品种类和品类管理
 * @author guozhichao
 *
 */
public class MailConfigCategory extends Page {

	/**
	 * 种类品类关联列表
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String emailType = dga.getParam("emailType");
		StringBuffer query_sql = new StringBuffer("");
		query_sql.append("select a.ID,a.EmailType,a.ProductType,a.ProductCategory,b.CodeName as ProductTypeName"
				+ ",c.codename as ProductCategoryName");
		query_sql.append(" from ZDMailMouldCategory a,zdcode b,zdcode c");
		query_sql.append(" where a.ProductType = b.CodeValue and a.ProductCategory = c.CodeValue "
				+ "and b.CodeType = 'MailConfigRiskType' and c.CodeType = 'MailConfigRiskType'");
		query_sql.append(" and a.EmailType = '"+emailType+"'");
		query_sql.append(" order by a.CreateDate desc ");
		QueryBuilder qb = new QueryBuilder(query_sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 新增关联品类窗口初始化
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initAddDialog(Mapx params) {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT b.codename as codename,a.ProductCategory as codevalue FROM ZDMailCategoryProduct a");
		sql.append(",zdcode b");
		sql.append(" where a.ProductCategory = b.codevalue");
		sql.append(" and a.Product is not null");
		sql.append(" and b.codetype = 'MailConfigRiskType'");
		DataTable dt = new QueryBuilder(sql.toString()).executeDataTable();
		params.put("categorys", HtmlUtil.dataTableToOptions(dt, true));
		params.put("MailConfigRiskType", HtmlUtil.codeToOptions(
				"MailConfigRiskType"));
		return params;
	}

	/**
	 * 保存配置
	 */
	public void saveConfig() {
		String emailType = $V("emailType");
		String productType = $V("ProductType");
		String productCategory = $V("ProductCategory");
		String oldProductType = $V("oldProductType");
		String id = $V("ID");
		String operator = User.getUserName();
		String currentDate = DateUtil.getCurrentDate();

		if (StringUtil.isEmpty(emailType)) {
			Response.setStatus(2);
			Response.setMessage("请选择模板!");
			return;
		}
		if(StringUtil.isEmpty(productCategory)){
			Response.setStatus(2);
			Response.setMessage("请选择产品品类!");
			return;
		}
		try{
			//查询品类是否存在
			QueryBuilder queryPtype = new QueryBuilder("select count(ID) from ZDMailMouldCategory "
					+ "where ProductType = '"+productType+"' and EmailType = '"+emailType+"'");
			if(queryPtype.executeLong() > 0 && (oldProductType == null || 
					!oldProductType.equals(productType))){
				Response.setStatus(2);
				Response.setMessage("该产品种类已经配置了品类!");
				return;
			}
			Transaction ts = new Transaction();
			if(id == null || id.equals("")){
				ZDMailMouldCategorySchema cate = new ZDMailMouldCategorySchema();
				cate.setEmailType(emailType);
				cate.setProductType(productType);
				cate.setProductCategory(productCategory);
				cate.setOperator(operator);
				cate.setID(String.valueOf(NoUtil.getMaxID("NotesID")));
				cate.setCreateDate(currentDate);
				ts.add(cate, Transaction.INSERT);
			}else{
				QueryBuilder updateSql = new QueryBuilder("update zdmailmouldcategory "
						+ "set EmailType = ?,ProductType = ?,ProductCategory = ?,Operator = ?,ModifyDate = ? where ID = ?");
				updateSql.add(emailType);
				updateSql.add(productType);
				updateSql.add(productCategory);
				updateSql.add(operator);
				updateSql.add(currentDate);
				updateSql.add(id);
				ts.add(updateSql);
			}
			if (ts.commit()) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		}catch (Exception e) {
			Response.setStatus(2);
			Response.setMessage("保存失败! error:" + e.getMessage());
			logger.error("saveConfig 方法异常! error:" + e.getMessage(), e);
		}
	}
	
	/**
	 * 删除配置
	 */
	public void deleteConfig() {
		String ids = $V("ID");
		try {
			Transaction ts = new Transaction();
			String[] idArray = ids.split(",");
			for(int i = 0; i < idArray.length; i++){
				ZDMailMouldCategorySchema cate = new ZDMailMouldCategorySchema();
				cate.setID(idArray[i]);
				ts.add(cate, Transaction.DELETE);
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
			logger.error("deleteConfig 方法异常! error:" + e.getMessage(), e);
		}
	}
}
