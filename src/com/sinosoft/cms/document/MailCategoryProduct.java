package com.sinosoft.cms.document;

import com.sinosoft.cms.pub.PubFun;
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
import com.sinosoft.schema.ZDMailCategoryProductSchema;

/**
 *  邮件模板产品品类和产品关系管理
 * @author guozhichao
 *
 */
public class MailCategoryProduct extends Page {

	/**
	 * 产品品类列表
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		StringBuffer query_sql = new StringBuffer("");
		query_sql.append("select a.ID,a.ProductCategory,a.Product,b.codename as ProductCategoryName from ZDMailCategoryProduct a");
		query_sql.append(",zdcode b where a.ProductCategory = b.codevalue and b.codetype = 'MailConfigRiskType'");
		query_sql.append(" order by a.CreateDate desc");
		QueryBuilder qb = new QueryBuilder(query_sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	/**
	 * 新增产品品类
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initAddDialog(Mapx params) {
		params.put("MailConfigRiskType", HtmlUtil.codeToOptions(
				"MailConfigRiskType"));
		return params;
	}
	
	/**
	 * 保存新增产品品类配置
	 */
	public void saveConfig() {
		String productCategory = $V("ProductCategory");
		String oldProductCategory = $V("oldProductCategory");
		String id = $V("ID");
		String operator = User.getUserName();
		String currentDate = DateUtil.getCurrentDate();
		if (StringUtil.isEmpty(productCategory)) {
			Response.setStatus(2);
			Response.setMessage("请填写品类名称!");
			return;
		}
		try{
			//查询品类是否存在
			QueryBuilder queryCategory = new QueryBuilder("select count(ID) from ZDMailCategoryProduct "
					+ "where ProductCategory = '"+productCategory+"'");
			if(queryCategory.executeLong() > 0 && (oldProductCategory == null || 
					!oldProductCategory.equals(productCategory))){
				Response.setStatus(2);
				Response.setMessage("品类已经存在!");
				return;
			}
			Transaction ts = new Transaction();
			if(id == null || id.equals("")){
				ZDMailCategoryProductSchema cate = new ZDMailCategoryProductSchema();
				cate.setProductCategory(productCategory);
				cate.setOperator(operator);
				cate.setID(String.valueOf(NoUtil.getMaxID("NotesID")));
				cate.setCreateDate(currentDate);
				ts.add(cate, Transaction.INSERT);
			}else{
				QueryBuilder updateSql = new QueryBuilder("update ZDMailCategoryProduct "
						+ "set ProductCategory = ?,Operator = ?,ModifyDate = ? where ID = ?");
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
	 * 删除品类配置
	 */
	public void deleteConfig() {
		String ids = $V("ID");
		try {
			Transaction ts = new Transaction();
			String[] idArray = ids.split(",");
			for(int i = 0; i < idArray.length; i++){
				ZDMailCategoryProductSchema cate = new ZDMailCategoryProductSchema();
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
	
	/**
	 * 保存推荐产品
	 */
	public void saveRelProduct() {
		String ProductIDs = $V("ProductIDs");
		String id = $V("ID");
		try {
			Transaction ts = new Transaction();
			ZDMailCategoryProductSchema zdm = new ZDMailCategoryProductSchema();
			zdm.setID(id);
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
			logger.error("saveRelProduct 方法异常! error:" + e.getMessage(), e);
		}
	}

}
