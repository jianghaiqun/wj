package com.sinosoft.module;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ProductTempInfoSchema;
import com.sinosoft.schema.ProductTempInfoSet;

import java.util.Date;

public class ProductTemplate extends Page {

	/**
	 * 页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx init(Mapx params) {
		params.put("CompanyList", HtmlUtil.codeToOptions("SupplierCode", true));
		return params;
	}

	/**
	 * 产品模版数据查询
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select Id,productId,ProductName,TemplateName,ExcelTemplate,ExcelRemark,MakeDate from (select p1.Id ,p1.productId, (select ProductName from sdproduct p2 where p1.productid=p2.productid) as ProductName , ";
		sql += "  (select distinct FactorName from ProductToTemplate p3 where p1.TemplateId=p3.factorid) as TemplateName,p1.MakeDate,(select templateName from productExcelTemp where id=p1.ExcelTemplate) as ExcelTemplate," +
				" p1.ExcelRemark from ProductTempInfo p1 ) u  where  1=1 ";
		QueryBuilder qb = new QueryBuilder(sql);
		
		String ProductID = (String) dga.getParams().get("ProductID");
		String ProductName = (String) dga.getParams().get("ProductName");
		String TemplateName = (String) dga.getParams().get("TemplateName");
		if (StringUtil.isNotEmpty(ProductID)) {
			qb.append(" and ProductID like ? ", "%" + ProductID + "%");
		}
		if (StringUtil.isNotEmpty(ProductName)) {
			qb.append(" and ProductName like ? ", "%" + ProductName + "%");
		}

		if (StringUtil.isNotEmpty(TemplateName)) {
			qb.append(" and TemplateName like ? ", "%" + TemplateName + "%");
		}
		qb.append(" order by MakeDate desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 产品模版数据查询
	 * 
	 * @param dga
	 */
	public static void dg4DataBind(DataGridAction dga) {
		String FactorName = (String) dga.getParams().get("FactorName");
		String sql = "select Id,TemplateName,ExcelRemark from productExcelTemp where 1=1  ";
		QueryBuilder qb = new QueryBuilder(sql); 
		if (StringUtil.isNotEmpty(FactorName)) {
			qb.append(" and templateName like ? ", "%" + FactorName + "%");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 产品查询数据查询
	 * 
	 * @param dga
	 */
	public static void dg2DataBind(DataGridAction dga) {
		String sql = "select s.ProductID,s.ProductName,s.InsureName,s.ProductGenera,s.IsPublish,s.Remark6 from sdproduct s where s.ProductID not in (select ProductId from ProductTempInfo) ";

		String ProductID = (String) dga.getParams().get("ProductID");
		String ProductName = (String) dga.getParams().get("ProductName");
		String CompanyCode = (String) dga.getParams().get("CompanyCode");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(ProductID)) {
			qb.append(" and s.ProductID like ? ", "%" + ProductID + "%");
		}

		if (StringUtil.isNotEmpty(ProductName)) {
			qb.append(" and s.ProductName like ? ", "%" + ProductName + "%");
		}

		if (StringUtil.isNotEmpty(CompanyCode)) {
			qb.append(" and s.Remark6 = ? ", CompanyCode);
		}

		qb.append(" order by s.ProductID");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 产品查询数据查询--excel
	 * 
	 * @param dga
	 */
	public static void dg3DataBind(DataGridAction dga) {
		String sql = "select s.ProductID,s.ProductName,s.InsureName,s.ProductGenera,s.IsPublish,s.Remark6 from sdproduct s where s.ProductID in (select ProductId from ProductTempInfo where excelflag IS NULL) ";

		String ProductID = (String) dga.getParams().get("ProductID");
		String ProductName = (String) dga.getParams().get("ProductName");
		String CompanyCode = (String) dga.getParams().get("CompanyCode");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(ProductID)) {
			qb.append(" and s.ProductID like ? ", "%" + ProductID + "%");
		}

		if (StringUtil.isNotEmpty(ProductName)) {
			qb.append(" and s.ProductName like ? ", "%" + ProductName + "%");
		}

		if (StringUtil.isNotEmpty(CompanyCode)) {
			qb.append(" and s.Remark6 = ? ", CompanyCode);
		}

		qb.append(" order by s.ProductID");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	/**
	 * 模版配置-添加
	 * 
	 * @param dga
	 */
	public void addSave() {
		try {
			DataTable DT_Template = (DataTable) Request.get("DT_Template");
			if (DT_Template == null || DT_Template.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：模版数据为空!");
			}

			DataTable DT_Product = (DataTable) Request.get("DT_Product");
			if (DT_Product == null || DT_Product.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：产品数据为空!");
			}

			Transaction trans = new Transaction();
			ProductTempInfoSchema tProductTempInfoSchema = null;
			for (int i = 0; i < DT_Template.getRowCount(); i++) {
				for (int j = 0; j < DT_Product.getRowCount(); j++) {
					tProductTempInfoSchema = new ProductTempInfoSchema();
					String ProductTempInfoID = NoUtil.getMaxIDLocal("ProductTempInfo") + "";
					tProductTempInfoSchema.setId(ProductTempInfoID);
					tProductTempInfoSchema.setProductId(DT_Product.getString(j, "ProductID"));
					tProductTempInfoSchema.setTemplateId(DT_Template.getString(i, "FactorId"));
					tProductTempInfoSchema.setMakeDate(new Date());
					trans.add(tProductTempInfoSchema, Transaction.INSERT);
				}
			}

			if (trans.commit()) {
				Response.setLogInfo(1, "保存成功!");
			} else {
				Response.setLogInfo(0, "保存失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "保存失败!" + e.getMessage());
		}
	}
	/**
	 * 模版配置-添加
	 * 
	 * @param dga
	 */
	public void addExcelSave() {
		try {
			DataTable DT_Template = (DataTable) Request.get("DT_Template");
			if (DT_Template == null || DT_Template.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：模版数据为空!");
			}

			DataTable DT_Product = (DataTable) Request.get("DT_Product");
			if (DT_Product == null || DT_Product.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：产品数据为空!");
			}

			Transaction trans = new Transaction();
			ProductTempInfoSchema tProductTempInfoSchema = null;
			
			for (int i = 0; i < DT_Template.getRowCount(); i++) {
				for (int j = 0; j < DT_Product.getRowCount(); j++) {
					tProductTempInfoSchema = new ProductTempInfoSchema();
					tProductTempInfoSchema.setProductId(DT_Product.getString(j, "ProductID"));
					tProductTempInfoSchema = tProductTempInfoSchema.query(new QueryBuilder("where ProductId in (" + DT_Product.getString(i, "ProductID") + ")")).get(0);
					tProductTempInfoSchema.setExcelTemplate(DT_Template.getString(i, "Id"));
					tProductTempInfoSchema.setExcelFlag("Y");
					tProductTempInfoSchema.setExcelRemark(DT_Template.getString(i, "ExcelRemark"));
					tProductTempInfoSchema.setMakeDate(new Date());
					trans.add(tProductTempInfoSchema, Transaction.UPDATE);
				}
			}

			if (trans.commit()) {
				Response.setLogInfo(1, "保存成功!");
			} else {
				Response.setLogInfo(0, "保存失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "保存失败!" + e.getMessage());
		}
	}
	/**
	 * 模版配置-删除
	 */
	public void del() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			ProductTempInfoSchema tProductTempInfoSchema = new ProductTempInfoSchema();
			ProductTempInfoSet set = tProductTempInfoSchema.query(new QueryBuilder("where Id in (" + ids + ")"));
			if (set == null || set.isEmpty()) {
				Response.setStatus(0);
				Response.setMessage("数据为空，无法删除!" + ids);
				return;
			}
			if (set.delete()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("删除失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setMessage("删除失败!" + e.getMessage());
		}
	}

}
