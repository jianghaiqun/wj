package com.sinosoft.module;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ProductToTemplateSchema;
import com.sinosoft.schema.ProductToTemplateSet;
import com.sinosoft.schema.ZDCodeSchema;

import java.util.Date;

public class ModuleConfigure extends Page {

	/**
	 * 页面初始化
	 *
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initModuleElement(Mapx params) {
		// params.put("FactorTypeList", HtmlUtil.codeToOptions("FactorType",
		// true));
		params.put("ModuleTypeList", HtmlUtil.codeToOptions("ElementType", true));
		// params.put("MainTemplateList", HtmlUtil.codeToOptions("MainTemplate",
		// true));
		return params;
	}

	/**
	 * 模版配置-修改页面初始化
	 *
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initEditModuleElement(Mapx params) {
		ProductToTemplateSchema tProductToTemplate = new ProductToTemplateSchema();
		ProductToTemplateSet set = tProductToTemplate.query(new QueryBuilder("where FactorId = ? ", params.get("FactorId") + ""));
		if (set != null && set.size() > 0) {
			tProductToTemplate = set.get(0);
			params.put("FactorId", tProductToTemplate.getFactorId());
			params.put("FactorName", tProductToTemplate.getFactorName());
			// params.put("FactorCode", tProductToTemplate.getFactorCode());
			params.put("Memo", tProductToTemplate.getMemo());
			// params.put("FactorTypeList", HtmlUtil.codeToOptions("FactorType",
			// tProductToTemplate.getFactorType()));
			// params.put("MainTemplateList",
			// HtmlUtil.codeToOptions("MainTemplate",
			// tProductToTemplate.getMainTemplate()));
		}
		return params;
	}

	/**
	 * 模块信息表数据查询
	 *
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select distinct m.FactorId,m.FactorName ,m.Memo   from ProductToTemplate  m  where 1=1  ";

		// String FactorType = (String) dga.getParams().get("FactorType");
		String FactorName = (String) dga.getParams().get("FactorName");
		// String FactorCode = (String) dga.getParams().get("FactorCode");

		QueryBuilder qb = new QueryBuilder(sql);

		// if (StringUtil.isNotEmpty(FactorType)) {
		// qb.append(" and m.FactorType = ? ", FactorType);
		// }

		// if (StringUtil.isNotEmpty(FactorCode)) {
		// qb.append(" and m.FactorCode like ? ", "%" + FactorCode + "%");
		// }

		if (StringUtil.isNotEmpty(FactorName)) {
			qb.append(" and m.FactorName like ? ", "%" + FactorName + "%");
		}

		qb.append(" order by id");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 模块信息表数据查询
	 *
	 * @param dga
	 */
	public static void dg2DataBind(DataGridAction dga) {
		String sql = "select m.Id,m.ModuleName, m.ModuleType,z.CodeName ModuleTypeName, m.Memo, m.Id OrderFlag  "
				+ " from ModuleInfo m left join zdcode z on (z.codetype='ElementType' and z.codevalue=m.ModuleType)  where produceflag='Y' ";

		String ModuleType = (String) dga.getParams().get("ModuleType");
		String ModuleName = (String) dga.getParams().get("ModuleName");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(ModuleType)) {
			qb.append(" and m.ModuleType = ? ", ModuleType);
		}

		if (StringUtil.isNotEmpty(ModuleName)) {
			qb.append(" and m.ModuleName like ? ", "%" + ModuleName + "%");
		}

		qb.append(" order by id");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 模版配置-修改-模版数据查询
	 *
	 * @param dga
	 */
	public static void dg3DataBind(DataGridAction dga) {
		String sql = "select p.Id,m.ModuleName, m.ModuleType,z.CodeName ModuleTypeName, m.Memo,  p.OrderFlag, p.FactorId "
				+ " from ModuleInfo m left join zdcode z on (z.codetype='ElementType' and z.codevalue=m.ModuleType),ProductToTemplate p"
				+ "  where m.produceflag='Y' and m.id = p.templatecode  and p.FactorId = ? order by p.orderflag ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(dga.getParams().get("FactorId"));
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}

	/**
	 * 模版配置-修改-添加模版数据查询
	 *
	 * @param dga
	 */
	public static void dg4DataBind(DataGridAction dga) {
		String sql = "select  m.Id,m.ModuleName, m.ModuleType,z.CodeName ModuleTypeName, m.Memo, m.Id OrderFlag "
				+ " from ModuleInfo m  left join zdcode z on (z.codetype='ElementType' and z.codevalue=m.ModuleType) "
				+ " where m.produceflag='Y'   and  m.id not in (select templatecode  from ProductToTemplate where FactorId = ? ) ";

		String ModuleType = (String) dga.getParams().get("ModuleType");
		String ModuleName = (String) dga.getParams().get("ModuleName");

		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(dga.getParams().get("FactorId"));

		if (StringUtil.isNotEmpty(ModuleType)) {
			qb.append(" and m.ModuleType = ? ", ModuleType);
		}

		if (StringUtil.isNotEmpty(ModuleName)) {
			qb.append(" and m.ModuleName like ? ", "%" + ModuleName + "%");
		}

		qb.append(" order by m.id");
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
			DataTable dt = (DataTable) Request.get("DT");
			if (dt == null || dt.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：模块数据为空!");
			}

			Transaction trans = new Transaction();
			String FactorId = NoUtil.getMaxIDLocal("PttFactorId") + "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				ProductToTemplateSchema ProductToTemplate = new ProductToTemplateSchema();
				String ProductToTemplateID = NoUtil.getMaxIDLocal("ProductToTemplateID") + "";
				ProductToTemplate.setId(ProductToTemplateID);
				ProductToTemplate.setFactorId(FactorId);
				ProductToTemplate.setFactorName($V("FactorName"));
				// ProductToTemplate.setFactorCode($V("FactorCode"));
				// ProductToTemplate.setFactorType($V("FactorType"));
				// ProductToTemplate.setMainTemplate($V("MainTemplate"));
				// ProductToTemplate.setMainTemplatePath(getMemo("MainTemplate",
				// $V("MainTemplate")));
				ProductToTemplate.setMemo($V("Memo"));
				ProductToTemplate.setTemplateCode(dt.getString(i, "Id"));
				ProductToTemplate.setOrderFlag(dt.getString(i, "OrderFlag"));
				ProductToTemplate.setCreateDate(new Date());
				trans.add(ProductToTemplate, Transaction.INSERT);
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
	public void delModule() {
		try {
			String ids = $V("FactorId");
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

			ProductToTemplateSchema producttotemplate = new ProductToTemplateSchema();
			ProductToTemplateSet set = producttotemplate.query(new QueryBuilder("where FactorId in (" + ids + ")"));
			if (set == null || set.isEmpty()) {
				Response.setStatus(0);
				Response.setMessage("数据为空，无法删除!" + ids);
				return;
			}

			StringBuffer moduleLog = new StringBuffer("删除产品投保模板表元素：");
			for (int i = 0; i < set.size(); i++) {
				producttotemplate = set.get(i);
				moduleLog.append(producttotemplate.getFactorName() + ",");
			}

			if (set.delete()) {
				Response.setStatus(1);
				UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, moduleLog + "成功", Request.getClientIP());
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(0);
				UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELMENU, moduleLog + "失败", Request.getClientIP());
				Response.setMessage("删除失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setMessage("删除失败!" + e.getMessage());
		}
	}

	/**
	 * 模版配置-修改-顺序调整
	 */
	public void saveOrderFlag() {
		DataTable dt = (DataTable) Request.get("DT");
		if (dt == null || dt.getRowCount() == 0) {
			Response.setLogInfo(0, "保存失败，原因：顺序调整模块信息为空!");
			return;
		}

		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ProductToTemplateSchema tProductTo = new ProductToTemplateSchema();
			tProductTo.setId(dt.getString(i, "Id"));
			if (tProductTo.fill()) {
				tProductTo.setOrderFlag(dt.getString(i, "OrderFlag"));
				tProductTo.setModifyDate(new Date());
				trans.add(tProductTo, Transaction.UPDATE);
			} else {
				Response.setLogInfo(0, "查询模块信息失败!");
				return;
			}
		}

		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	/**
	 * 模版配置-修改-数据保存
	 *
	 * @param dga
	 */
	public void saveModuleConfigure() {
		try {
			String FactorId = $V("FactorId");
			ProductToTemplateSchema producttotemplate = new ProductToTemplateSchema();
			ProductToTemplateSet set = producttotemplate.query(new QueryBuilder("where FactorId = ?", FactorId));
			if (set == null || set.size() == 0) {
				Response.setLogInfo(0, "保存失败!");
				return;
			}
			Transaction trans = new Transaction();
			for (int i = 0; i < set.size(); i++) {
				producttotemplate = set.get(i);
				producttotemplate.setModifyDate(new Date());
				producttotemplate.setFactorName($V("FactorName"));
				// producttotemplate.setFactorType($V("FactorType"));
				// producttotemplate.setFactorCode($V("FactorCode"));
				// producttotemplate.setMainTemplate($V("MainTemplate"));
				// producttotemplate.setMainTemplatePath(getMemo("MainTemplate",
				// $V("MainTemplate")));
				producttotemplate.setMemo($V("Memo"));
				trans.add(producttotemplate, Transaction.UPDATE);
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
	 * 模版配置-修改-删除模块信息
	 */
	public void delModuleElement() {
		try {
			String ids = $V("IDs");
			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}
			Transaction trans = new Transaction();
			ProductToTemplateSchema ProductToTemplate = new ProductToTemplateSchema();
			ProductToTemplateSet set = ProductToTemplate.query(new QueryBuilder("where id in (" + ids + ")"));
			if (set == null || set.size() == 0) {
				Response.setLogInfo(0, "删除失败!");
				return;
			}
			trans.add(set, Transaction.DELETE);
			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("删除失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "删除失败!" + e.getMessage());
		}
	}

	/**
	 * 模版配置-修改-添加模块信息
	 *
	 */
	public void addModuleConfigure() {
		try {
			DataTable dt = (DataTable) Request.get("DT");
			if (dt == null || dt.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：模块数据为空!");
			}

			Transaction trans = new Transaction();
			String FactorId = $V("FactorId");
			for (int i = 0; i < dt.getRowCount(); i++) {
				ProductToTemplateSchema ProductToTemplate = new ProductToTemplateSchema();
				String ProductToTemplateID = NoUtil.getMaxIDLocal("ProductToTemplateID") + "";
				ProductToTemplate.setId(ProductToTemplateID);
				ProductToTemplate.setFactorId(FactorId);
				ProductToTemplate.setFactorName($V("FactorName"));
				// ProductToTemplate.setFactorCode($V("FactorCode"));
				// ProductToTemplate.setFactorType($V("FactorType"));
				// ProductToTemplate.setMainTemplate($V("MainTemplate"));
				// ProductToTemplate.setMainTemplatePath(getMemo("MainTemplate",
				// $V("MainTemplate")));
				ProductToTemplate.setMemo($V("Memo"));
				ProductToTemplate.setTemplateCode(dt.getString(i, "Id"));
				ProductToTemplate.setOrderFlag(dt.getString(i, "OrderFlag"));
				ProductToTemplate.setCreateDate(new Date());
				trans.add(ProductToTemplate, Transaction.INSERT);
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
     * 模版复制
     *
     */
    public void templateCopy () {
        String factorId = $V("factorId");
        if (StringUtil.isEmpty(factorId)) {
            Response.setStatus(0);
            Response.setMessage("传入ID不能为空!");
            return;
        }
        if (!StringUtil.checkID(factorId)) {
            Response.setStatus(0);
            Response.setMessage("传入ID时发生错误!");
            return;
        }

        ProductToTemplateSchema productToTemplateSchema = new ProductToTemplateSchema();
        ProductToTemplateSet set = productToTemplateSchema.query(new QueryBuilder("where FactorId = ?", factorId));

        if (set == null || set.size() == 0) {
            Response.setLogInfo(0, "复制失败!");
            return;
        }
        Transaction trans = new Transaction();
        String factorIdCopy = NoUtil.getMaxIDLocal("PttFactorId") + "";
        for (int i = 0; i < set.size(); i++) {
            productToTemplateSchema = set.get(i);
            ProductToTemplateSchema productToTemplateCopy = (ProductToTemplateSchema)productToTemplateSchema.clone();
            String productToTemplateID = NoUtil.getMaxIDLocal("ProductToTemplateID") + "";
            productToTemplateCopy.setId(productToTemplateID);
            productToTemplateCopy.setFactorId(factorIdCopy);
            productToTemplateCopy.setFactorName($V("ModuleName"));
            productToTemplateCopy.setCreateDate(new Date());
            productToTemplateCopy.setModifyDate(new Date());

            trans.add(productToTemplateCopy, Transaction.INSERT);

        }
        if (trans.commit()) {
            Response.setLogInfo(1, "复制成功!");
        } else {
            Response.setLogInfo(0, "复制失败!");
        }
    }

	/**
	 * 获取模版大类内容
	 *
	 * @param type
	 * @param code
	 * @return
	 */
	private String getMemo(String type, String code) {
		ZDCodeSchema tZDcode = new ZDCodeSchema();
		tZDcode.setCodeType(type);
		tZDcode.setParentCode(type);
		tZDcode.setCodeValue(code);
		if (tZDcode.fill()) {
			return tZDcode.getMemo();
		}
		return null;
	}
}
