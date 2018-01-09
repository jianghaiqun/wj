package com.sinosoft.message;

import java.util.Date;

import com.sinosoft.cms.dataservice.ColumnUtil;
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
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SCCatalogValueSchema;
import com.sinosoft.schema.SCCatalogValueSet;

public class CatalogColumn extends Page {

	public static String Extend_Self = "1";

	public static String Extend_Children = "2";

	public static String Extend_All = "3";

	public static String Extend_SameLevel = "4";

	public static Mapx ExtendMap = new Mapx();

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ColumnID");
		String tCatalogType = params.getString("CatalogType");
		if (StringUtil.isEmpty(ID)) {
			// ////
			params.put("CatalogType", tCatalogType);
			// ////
			params.put("VerifyType",
					HtmlUtil.mapxToOptions(ColumnUtil.VerifyTypeMap));
			params.put("InputType",
					HtmlUtil.mapxToOptions(ColumnUtil.InputTypeMap));
			params.put("IsMandatory",
					HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", "N"));
			params.put("MaxLength", "100");
			params.put("Cols", "265");
			params.put("Rows", "90");
		} else {
			SCCatalogValueSchema tSCCatalogValueSchema = new SCCatalogValueSchema();
			tSCCatalogValueSchema.setId(ID);
			tSCCatalogValueSchema.fill();
			params = tSCCatalogValueSchema.toMapx();
			params.put(
					"VerifyType",
					HtmlUtil.mapxToOptions(ColumnUtil.VerifyTypeMap,
							tSCCatalogValueSchema.getVerifyType()));
			params.put(
					"InputType",
					HtmlUtil.mapxToOptions(ColumnUtil.InputTypeMap,
							tSCCatalogValueSchema.getInputType()));
			params.put(
					"IsMandatory",
					HtmlUtil.codeToRadios("IsMandatory", "YesOrNo",
							tSCCatalogValueSchema.getIsMandatory()));
			params.put("CatalogType", tSCCatalogValueSchema.getCTId());
		}
		return params;
	}
//	public static Mapx initDialog1(Mapx params) {
//	    params.put("CatalogType", PubFun.getCatTypeOptions());
//		return params;
//	}

	public static void dg1DataBind(DataGridAction dga) {
		String tCatalogType = dga.getParam("CatalogType");
		String sql = "select * from SCCatalogValue where CTId='"+tCatalogType+"'";
		QueryBuilder qb = new QueryBuilder();
//		qb.add(CatalogID);
		qb.setSQL(sql);
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("VerifyType", ColumnUtil.VerifyTypeMap);
		dt.decodeColumn("InputType", ColumnUtil.InputTypeMap);
		dga.bindData(dt);
	}

	public void add() {
		String tColumnID = $V("ColumnID");

		String tCTId = $V("CatalogType");
		// int catalogType = new
		// QueryBuilder("select type from zccatalog where id = ?",
		// Long.parseLong(catalogID)).executeInt();
		// long count = new
		// QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
		// + ColumnUtil.RELA_TYPE_CATALOG_COLUMN +
		// "' and RelaID = ? and ColumnCode =? ", catalogID, columnCode)
		// .executeLong();
		// if (count > 0) {
		// Response.setLogInfo(0, "已经存在相同的字段");
		// return;
		// }
		Transaction trans = new Transaction();
		SCCatalogValueSchema tSCCatalogValueSchema = new SCCatalogValueSchema();

		if (StringUtil.isEmpty(tColumnID)) {
			tSCCatalogValueSchema.setValue(Request);

			// 把空格，全角空格，逗号，全角逗号等都替换为英文的逗号
			String defaultValue = tSCCatalogValueSchema.getDefaultValue();
			defaultValue = defaultValue.replaceAll("　　", ",");
			defaultValue = defaultValue.replaceAll("　", ",");
			defaultValue = defaultValue.replaceAll("  ", ",");
			defaultValue = defaultValue.replaceAll(" ", ",");
			defaultValue = defaultValue.replaceAll(",,", ",");
			defaultValue = defaultValue.replaceAll("，，", ",");
			defaultValue = defaultValue.replaceAll("，", ",");
			tSCCatalogValueSchema.setDefaultValue(defaultValue);

			tSCCatalogValueSchema.setId(NoUtil.getMaxID("CVID") + "");
			tSCCatalogValueSchema.setCTId(tCTId);
			tSCCatalogValueSchema.setAddTime(new Date());
			tSCCatalogValueSchema.setAddUser(User.getUserName());

			if (ColumnUtil.Input.equals(tSCCatalogValueSchema.getInputType())) {
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setListOption("");
			} else if (ColumnUtil.Text.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setListOption("");
			} else if (ColumnUtil.Select.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.Radio.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setIsMandatory("N");
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.Checkbox.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setIsMandatory("N");
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.DateInput.equals(tSCCatalogValueSchema
					.getInputType())
					|| ColumnUtil.TimeInput.equals(tSCCatalogValueSchema
							.getInputType())) {
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setListOption("");
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.ImageInput.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setIsMandatory("N");
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setListOption("");
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.HTMLInput.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setIsMandatory("N");
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setListOption("");
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			}
			trans.add(tSCCatalogValueSchema, Transaction.INSERT);
		} else {
			tSCCatalogValueSchema.setId(tColumnID);
			tSCCatalogValueSchema.fill();
			tSCCatalogValueSchema.setValue(Request);

			// 把空格，全角空格，逗号，全角逗号等都替换为英文的逗号
			String defaultValue = tSCCatalogValueSchema.getDefaultValue();
			defaultValue = defaultValue.replaceAll("　　", ",");
			defaultValue = defaultValue.replaceAll("　", ",");
			defaultValue = defaultValue.replaceAll("  ", ",");
			defaultValue = defaultValue.replaceAll(" ", ",");
			defaultValue = defaultValue.replaceAll(",,", ",");
			defaultValue = defaultValue.replaceAll("，，", ",");
			defaultValue = defaultValue.replaceAll("，", ",");
			tSCCatalogValueSchema.setDefaultValue(defaultValue);

			tSCCatalogValueSchema.setCTId(tCTId);

			if (ColumnUtil.Input.equals(tSCCatalogValueSchema.getInputType())) {
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setListOption("");
			} else if (ColumnUtil.Text.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setListOption("");
			} else if (ColumnUtil.Select.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.Radio.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setIsMandatory("N");
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.Checkbox.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setIsMandatory("N");
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.DateInput.equals(tSCCatalogValueSchema
					.getInputType())
					|| ColumnUtil.TimeInput.equals(tSCCatalogValueSchema
							.getInputType())) {
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setListOption("");
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.ImageInput.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setIsMandatory("N");
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setListOption("");
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			} else if (ColumnUtil.HTMLInput.equals(tSCCatalogValueSchema
					.getInputType())) {
				tSCCatalogValueSchema.setIsMandatory("N");
				tSCCatalogValueSchema.setColSize(null);
				tSCCatalogValueSchema.setRowSize(null);
				tSCCatalogValueSchema.setMaxLength(null);
				tSCCatalogValueSchema.setListOption("");
				tSCCatalogValueSchema.setVerifyType(ColumnUtil.STRING);
			}

			trans.add(tSCCatalogValueSchema, Transaction.UPDATE);

		}

		if (trans.commit()) {
			Response.setLogInfo(1, "新建成功");
		} else {
			Response.setLogInfo(0, "新建失败");
		}

	}


	public void del() {// 本方法需要注意SQL注入
		String ids = $V("IDs");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setLogInfo(0, "传入ID时发生错误");
			return;
		}
		Transaction trans = new Transaction();
		SCCatalogValueSchema tSCCatalogValueSchema=new SCCatalogValueSchema();
		SCCatalogValueSet tSCCatalogValueSet = tSCCatalogValueSchema.query(new QueryBuilder("where ID in (" + ids + ")"));
		trans.add(tSCCatalogValueSet, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除提问成功");
		} else {
			Response.setLogInfo(0, "删除提问失败");
		}
	}

}
