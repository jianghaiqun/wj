package com.sinosoft.module;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ModuleElementSchema;
import com.sinosoft.schema.ModuleElementSet;
import com.sinosoft.schema.ModuleInfoSchema;
import com.sinosoft.schema.ModuleInfoSet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ModuleManage extends Page {

	/**
	 * 页面初始化
	 *
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initModuleElement(Mapx params) {
		params.put("ModuleTypeList", HtmlUtil.codeToOptions("ElementType", true));
		return params;
	}

	/**
	 * 模块元素管理生成按钮，页面初始化
	 *
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initModuleElementEdit(Mapx params) {
		String Id = params.get("Id") + "";
		if (StringUtil.isNotEmpty(Id)) {
			ModuleInfoSchema ModuleInfo = new ModuleInfoSchema();
			ModuleInfo.setId(Id);
			ModuleInfo.fill();
			params.putAll(ModuleInfo.toMapx());
			params.put("ModuleTypeList", HtmlUtil.codeToOptions("ElementType", ModuleInfo.getModuleType()));
		}
		return params;
	}

	/**
	 * 模块元素数据
	 *
	 * @param dga
	 */
	public void addSave() {
		try {
			DataTable dt = (DataTable) Request.get("DT");
			if (dt == null || dt.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：元素数据为空!");
			}

			ModuleInfoSchema ModuleInfo = new ModuleInfoSchema();
			String ModuleInfoCode = NoUtil.getMaxIDLocal("ModuleInfoID") + "";
			ModuleInfo.setId(ModuleInfoCode);
			ModuleInfo.setModuleName($V("ModuleName"));
			ModuleInfo.setModuleType($V("ModuleType"));
			ModuleInfo.setMemo($V("Memo"));
			ModuleInfo.setProduceFlag("N");
			ModuleInfo.setCreateDate(new Date());

			Transaction trans = new Transaction();
			trans.add(ModuleInfo, Transaction.INSERT);

			for (int i = 0; i < dt.getRowCount(); i++) {
				ModuleElementSchema ModuleElement = new ModuleElementSchema();
				ModuleElement.setId(NoUtil.getMaxIDLocal("ModuleElementID") + "");
				ModuleElement.setModuleCode(ModuleInfoCode);
				ModuleElement.setElementCode(dt.getString(i, "Id"));
				ModuleElement.setOrderFlag(dt.getString(i, "OrderFlag"));
				ModuleElement.setOperateDate(new Date());
				trans.add(ModuleElement, Transaction.INSERT);
			}

			if (trans.commit()) {
				Response.setLogInfo(1, "保存成功!");
			} else {
				Response.setLogInfo(0, "保存失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 模版元素数据查询
	 *
	 * @param dga
	 */
	public static void dg2DataBind(DataGridAction dga) {
		String sql = "select Id,ModuleName,ModuleType,z.CodeName ModuleTypeName,ModuleURL,ProduceFlag,CreateDate,m.Memo from ModuleInfo  m left join "
				+ " zdcode z on (z.codetype='ElementType' and z.codevalue=m.ModuleType) where 1=1 ";

		String ModuleType = (String) dga.getParams().get("ModuleType");
		String ModuleName = (String) dga.getParams().get("ModuleName");
		String ProduceFlag = (String) dga.getParams().get("ProduceFlag");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(ModuleType)) {
			qb.append(" and ModuleType = ? ", ModuleType);
		}

		if (StringUtil.isNotEmpty(ProduceFlag)) {
			qb.append(" and ProduceFlag = ? ", ProduceFlag);
		}

		if (StringUtil.isNotEmpty(ModuleName)) {
			qb.append(" and ModuleName like ? ", "%" + ModuleName + "%");
		}

		qb.append(" order by id");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 数据查询
	 *
	 * @param dga
	 */
	public static void editDataBind(DataGridAction dga) {
		String sql = "select m2.Id ,m1.ElementName,m1.ElementType,z.CodeName ElementTypeName ,m1.ElementContent,m1.Memo,m2.OrderFlag  ";
		sql += " from ModuleElementInfo m1  left join zdcode z on (z.codetype='ElementType' and z.codevalue=m1.ElementType)  , ModuleElement m2";
		sql += " where m1.id=m2.ElementCode and m2.ModuleCode = ? order by orderflag ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add(dga.getParams().get("Id"));
		DataTable dt = qb.executeDataTable();
		dga.bindData(dt);
	}

	/**
	 * 模版元素删除
	 */
	public void delModule() {
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
			Transaction trans = new Transaction();

			ModuleInfoSchema moduleInfo = new ModuleInfoSchema();
			ModuleInfoSet set = moduleInfo.query(new QueryBuilder("where id in (" + ids + ")"));
			StringBuffer moduleLog = new StringBuffer("删除配置元素：");
			for (int i = 0; i < set.size(); i++) {
				moduleInfo = set.get(i);
				moduleLog.append(moduleInfo.getModuleName() + ",");
				trans.add(new QueryBuilder("delete from ModuleElement where ModuleCode = ? ", moduleInfo.getId()));
				String file = Config.getContextRealPath() + "" + moduleInfo.getModuleURL();
				if(new File(file).exists()){
					new File(file).delete();
				}
			}
			trans.add(set, Transaction.DELETE);

			if (trans.commit()) {
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
			Response.setStatus(0);
			Response.setMessage("删除失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 添加模块元素数据
	 *
	 * @param dga
	 */
	public void addModuleElement() {
		try {
			DataTable dt = (DataTable) Request.get("DT");
			if (dt == null || dt.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：新增元素数据为空!");
				return;
			}
			// 判断数据是否已经存在，如果存在给予提示
			String IDs = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				String Id = dt.getString(i, "Id");
				IDs += Id + ",";
			}
			if (IDs.endsWith(",")) {
				IDs = IDs.substring(0, IDs.length() - 1);
			}
			ModuleElementSchema ModuleElement＿ = new ModuleElementSchema();
			ModuleElementSet set = ModuleElement＿.query(new QueryBuilder("where ElementCode in (" + IDs + ") and ModuleCode = ? " , $V("ModuleCode")));
			if (set != null && set.size() > 0) {
				Response.setLogInfo(0, "保存失败!原因：数据已经存在!");
				return;
			}
			ModuleInfoSchema ModuleInfo = new ModuleInfoSchema();
			ModuleInfo.setId($V("ModuleCode"));
			if (!ModuleInfo.fill()) {
				Response.setLogInfo(0, "保存失败!原因：模块信息数据不存在!");
				return;
			}

			Transaction trans = new Transaction();
			for (int i = 0; i < dt.getRowCount(); i++) {
				ModuleElementSchema ModuleElement = new ModuleElementSchema();
				ModuleElement.setId(NoUtil.getMaxIDLocal("ModuleElementID") + "");
				ModuleElement.setModuleCode($V("ModuleCode"));
				ModuleElement.setElementCode(dt.getString(i, "Id"));
				ModuleElement.setOrderFlag(dt.getString(i, "Id"));
				ModuleElement.setOperateDate(new Date());
				trans.add(ModuleElement, Transaction.INSERT);
			}

			ModuleInfo.setProduceFlag("N");
			ModuleInfo.setModifyDate(new Date());
			trans.add(ModuleInfo, Transaction.UPDATE);

			if (trans.commit()) {
				Response.setLogInfo(1, "保存成功!");
			} else {
				Response.setLogInfo(0, "保存失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 模版模块元素数据
	 */
	public void delModuleElement() {
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
			Transaction trans = new Transaction();
			ModuleElementSchema ModuleElement = new ModuleElementSchema();
			ModuleElementSet set = ModuleElement.query(new QueryBuilder("where id in (" + ids + ")"));
			StringBuffer moduleLog = new StringBuffer("删除模块元素：");
			for (int i = 0; i < set.size(); i++) {
				ModuleElement = set.get(i);
				moduleLog.append(ModuleElement.getElementCode() + " - " + ModuleElement.getModuleCode() + ",");

				ModuleInfoSchema ModuleInfo = new ModuleInfoSchema();
				ModuleInfo.setId(ModuleElement.getModuleCode());
				if (ModuleInfo.fill()) {
					ModuleInfo.setModifyDate(new Date());
					ModuleInfo.setProduceFlag("N");
					trans.add(ModuleInfo, Transaction.UPDATE);
				} else {
					Response.setLogInfo(0, "查询模块数据失败!");
					return;
				}
			}
			trans.add(set, Transaction.DELETE);
			if (trans.commit()) {
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
			Response.setStatus(0);
			Response.setMessage("删除失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 顺序调整
	 */
	public void saveOrderFlag() {
		try {
			DataTable dt = (DataTable) Request.get("DT");
			if (dt == null || dt.getRowCount() == 0) {
				Response.setLogInfo(0, "保存失败，原因：顺序调整元素数据为空!");
				return;
			}

			Transaction trans = new Transaction();
			for (int i = 0; i < dt.getRowCount(); i++) {
				ModuleElementSchema ModuleElement = new ModuleElementSchema();
				ModuleElement.setId(dt.getString(i, "Id"));
				if (ModuleElement.fill()) {
					ModuleElement.setOrderFlag(dt.getString(i, "OrderFlag"));
					ModuleElement.setOperateDate(new Date());
					trans.add(ModuleElement, Transaction.UPDATE);
				} else {
					Response.setLogInfo(0, "查询模块元素数据失败!");
					return;
				}

				ModuleInfoSchema ModuleInfo = new ModuleInfoSchema();
				ModuleInfo.setId(ModuleElement.getModuleCode());
				if (ModuleInfo.fill()) {
					ModuleInfo.setModifyDate(new Date());
					ModuleInfo.setProduceFlag("N");
					trans.add(ModuleInfo, Transaction.UPDATE);
				} else {
					Response.setLogInfo(0, "查询模块数据失败!");
					return;
				}
			}

			if (trans.commit()) {
				Response.setLogInfo(1, "保存成功!");
			} else {
				Response.setLogInfo(0, "保存失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 模版信息数据保存
	 *
	 * @param dga
	 */
	public void saveModuleMessage() {
		try {
			String Id = $V("Id");
			if (StringUtil.isEmpty(Id)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}

			ModuleInfoSchema ModuleInfo = new ModuleInfoSchema();
			ModuleInfo.setId(Id);
			if (ModuleInfo.fill()) {
				ModuleInfo.setModuleName($V("ModuleName"));
				ModuleInfo.setModuleType($V("ModuleType"));
				ModuleInfo.setMemo($V("Memo"));
				ModuleInfo.setModifyDate(new Date());
				ModuleInfo.setProduceFlag("N");
			} else {
				Response.setLogInfo(0, "保存失败!原因：数据不存在!");
				return;
			}

			if (ModuleInfo.update()) {
				Response.setLogInfo(1, "保存成功!");
			} else {
				Response.setLogInfo(0, "保存失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 模版模块元素数据拼装
	 */
	public void proModule() {
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
			Transaction trans = new Transaction();

			ModuleInfoSchema ModuleInfo = new ModuleInfoSchema();
			ModuleInfoSet set = ModuleInfo.query(new QueryBuilder("where id in (" + ids + ")"));
			for (int i = 0; i < set.size(); i++) {
				ModuleInfo = set.get(i);

				String sql = "select m2.ElementContent from ModuleElement m1,ModuleElementInfo m2 ";
				sql += " where m2.id=m1.ElementCode and m1.ModuleCode = ? order by m1.orderflag ";
				QueryBuilder qb = new QueryBuilder(sql);
				qb.add(ModuleInfo.getId());
				DataTable dt = qb.executeDataTable();
				if (dt == null || dt.getRowCount() == 0) {
					Response.setStatus(0);
					Response.setMessage("生成失败，数据为空!(" + ModuleInfo.getModuleName() + ")");
					return;
				}

				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < dt.getRowCount(); j++) {
					sb.append(dt.getString(j, "ElementContent"));
				}

				String ModuleType = ModuleInfo.getModuleType();
				String ModuleURL = ModuleInfo.getModuleURL();
				String path = Config.getContextRealPath();
				if (StringUtil.isEmpty(ModuleURL)) {
					String tModuleURL = "WEB-INF" + File.separator + "template" + File.separator + "shop" + File.separator + "moduleinfo" + File.separator;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
					Random r = new Random();
					String random = StringUtil.leftPad(r.nextInt(1000) + "", '0', 3);
					String fileName = ModuleType + sdf.format(new Date()) + random + ".ftl";

					tModuleURL += fileName;
					ModuleURL = tModuleURL;
				} else {
					new File(path + ModuleURL).deleteOnExit();
				}
				File f = new File(path + ModuleURL);
				if (!f.exists()) {
					f.createNewFile();
				}

				boolean proFlag = FileUtil.writeText(path + ModuleURL, sb.toString());
				if (proFlag) {
					ModuleInfo.setModuleURL(ModuleURL);
					ModuleInfo.setProduceFlag("Y");
					ModuleInfo.setProDate(new Date());
					trans.add(ModuleInfo, Transaction.UPDATE);
				} else {
					Response.setStatus(0);
					Response.setMessage("生成失败!");
					return;
				}
			}

			if (trans.commit()) {
				Response.setStatus(1);
				Response.setMessage("生成成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("生成失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("生成失败! 异常原因：" + e.getMessage());
		}
	}

    /**
     * 模块复制
     *
     */
	public void moduleCopy () {
        String id = $V("id");
        if (StringUtil.isEmpty(id)) {
            Response.setStatus(0);
            Response.setMessage("传入ID不能为空!");
            return;
        }
        if (!StringUtil.checkID(id)) {
            Response.setStatus(0);
            Response.setMessage("传入ID时发生错误!");
            return;
        }

        ModuleInfoSchema moduleInfo = new ModuleInfoSchema();
        moduleInfo.setId(id);
        if (moduleInfo.fill()) {
            ModuleInfoSchema moduleInfoCopy = (ModuleInfoSchema)moduleInfo.clone();
            String copyId = NoUtil.getMaxIDLocal("ModuleInfoID") + "";
            moduleInfoCopy.setId(copyId);
            moduleInfoCopy.setModuleName($V("ModuleName"));
            moduleInfoCopy.setCreateDate(new Date());
            moduleInfoCopy.setModifyDate(new Date());

            Transaction trans = new Transaction();
            trans.add(moduleInfoCopy, Transaction.INSERT);

            if (trans.commit()) {
                Response.setLogInfo(1, "复制成功!");
            } else {
                Response.setLogInfo(0, "复制失败!");
            }
        } else {
            Response.setLogInfo(0, "复制失败!");
        }
    }
}
