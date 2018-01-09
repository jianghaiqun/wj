package com.sinosoft.cms.site;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.SCCatalogValueSchema;
import com.sinosoft.schema.SCCatalogValueSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZDColumnRelaSchema;
import com.sinosoft.schema.ZDColumnSchema;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CatalogColumn extends Page {

	public static String Extend_Self = "1";

	public static String Extend_Children = "2";

	public static String Extend_All = "3";

	public static String Extend_SameLevel = "4";

	public static Mapx ExtendMap = new Mapx();

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ColumnID");
		if (StringUtil.isEmpty(ID)) {
			params.put("VerifyType", HtmlUtil.mapxToOptions(ColumnUtil.VerifyTypeMap));
			params.put("InputType", HtmlUtil.mapxToOptions(ColumnUtil.InputTypeMap));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", "N"));
			params.put("MaxLength", "100");
			params.put("Cols", "265");
			params.put("Rows", "90");
		} else {
			ZDColumnSchema column = new ZDColumnSchema();
			column.setID(ID);
			column.fill();
			params = column.toMapx();
			params.put("VerifyType", HtmlUtil.mapxToOptions(ColumnUtil.VerifyTypeMap, column.getVerifyType()));
			params.put("InputType", HtmlUtil.mapxToOptions(ColumnUtil.InputTypeMap, column.getInputType()));
			params.put("IsMandatory", HtmlUtil.codeToRadios("IsMandatory", "YesOrNo", column.getIsMandatory()));
		}
		return params;
	}
	
	public static Mapx initDialog1(Mapx params) {
		
		params.put("CatalogType", PubFun.getCatTypeOptions());
		
		return params;
	}
	
	
	public void getCatalogValue() {
		String tCTId = $V("CatalogType");
		SCCatalogValueSchema tSCCatalogValueSchema=new SCCatalogValueSchema();
		SCCatalogValueSet tSCCatalogValueSet = tSCCatalogValueSchema.query(new QueryBuilder("where CTId ='"+tCTId+"'"));
		String temp="";
		if(tSCCatalogValueSet.size()>0){
			for(int i=0;i<tSCCatalogValueSet.size();i++){
				tSCCatalogValueSchema=tSCCatalogValueSet.get(i);
				String nameStr=tSCCatalogValueSchema.getValueName()+"\r\n";
				temp+=nameStr;
				
			}
		}
		$S("NameList", temp);
	}
	
	
	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID = dga.getParam("CatalogID");
		String sql = "select * from ZDColumn where  exists (select ColumnID from ZDColumnRela where ZDColumnRela.ColumnID=ZDColumn.ID and ZDColumnRela.RelaType='"
				+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and RelaID =?) order by ID asc";
		QueryBuilder qb = new QueryBuilder();
		qb.add(CatalogID);
		qb.setSQL(sql);
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("VerifyType", ColumnUtil.VerifyTypeMap);
		dt.decodeColumn("InputType", ColumnUtil.InputTypeMap);
		dga.bindData(dt);
	}

	public void add() {
		String catalogID = $V("CatalogID");
		
		String columnCode = $V("Code");
		int catalogType = new QueryBuilder("select type from zccatalog where id = ?", Long.parseLong(catalogID)).executeInt();
		long count = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
				+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and RelaID = ? and ColumnCode =? ", catalogID, columnCode)
				.executeLong();
		if (count > 0) {
			Response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
		Transaction trans = new Transaction();
		ZDColumnSchema column = new ZDColumnSchema();
		column.setValue(Request);

		// 把空格，全角空格，逗号，全角逗号等都替换为英文的逗号
		String defaultValue = column.getDefaultValue();
		defaultValue = defaultValue.replaceAll("　　", ",");
		defaultValue = defaultValue.replaceAll("　", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("，，", ",");
		defaultValue = defaultValue.replaceAll("，", ",");
		column.setDefaultValue(defaultValue);

		column.setID(NoUtil.getMaxID("ColumnID"));
		column.setSiteID(Application.getCurrentSiteID());
		column.setOrderFlag(OrderUtil.getDefaultOrder());
		column.setAddTime(new Date());
		column.setAddUser(User.getUserName());

		if (ColumnUtil.Input.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setListOption("");
		} else if (ColumnUtil.Text.equals(column.getInputType())) {
			column.setListOption("");
		} else if (ColumnUtil.Select.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Radio.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Checkbox.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.DateInput.equals(column.getInputType())
				|| ColumnUtil.TimeInput.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.ImageInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.HTMLInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}

		String extend = $V("Extend");
		if (Extend_Self.equals(extend)) {
			ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
			rela.setID(NoUtil.getMaxID("ColumnRelaID"));
			rela.setColumnID(column.getID());
			rela.setColumnCode(column.getCode());
			rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
			rela.setRelaID(catalogID);
			rela.setAddTime(column.getAddTime());
			rela.setAddUser(column.getAddUser());
			trans.add(rela, Transaction.INSERT);
		} else if (Extend_Children.equals(extend)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			DataTable childCatalogDT = new QueryBuilder("select id from zccatalog where innercode like '" + innerCode
					+ "%'").executeDataTable();
			for (int i = 0; i < childCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(childCatalogDT.getString(i, 0));
				if(rela.query().size()>0){
					continue;
				}
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(childCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		} else if (Extend_All.equals(extend)) {
			DataTable allCatalogDT = new QueryBuilder("select id from zccatalog where Type=" + catalogType
					+ " and siteID =" + Application.getCurrentSiteID()).executeDataTable();
			for (int i = 0; i < allCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(allCatalogDT.getString(i, 0));
				if(rela.query().size()>0){
					continue;
				}
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(allCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		} else if (Extend_SameLevel.equals(extend)) {
			int level = new QueryBuilder("select treelevel from zccatalog where id =" + catalogID).executeInt();
			DataTable levelCatalogDT = new QueryBuilder("select id from zccatalog where siteID ="
					+ Application.getCurrentSiteID() + " and Type=" + catalogType + " and treelevel=" + level)
					.executeDataTable();
			for (int i = 0; i < levelCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(levelCatalogDT.getString(i, 0));
				if(rela.query().size()>0){
					continue;
				}
				
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(levelCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		}
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		trans.add(column, Transaction.INSERT);
		if (trans.commit()) {
			Response.setLogInfo(1, "新建成功");
		} else {
			Response.setLogInfo(0, "新建失败");
		}

	}
	
	public void uploadExcel() {
		String fileaddress = $V("fileaddress");
		fileaddress = fileaddress.replace("//", "/");
		DataTable dt = null;
		try {
			dt = DataTableUtil.xlsToDataTable(fileaddress);
		} catch (Exception e) {
			logger.error("读取数据导入文件时发生错误:" + e.getMessage(), e);
			this.Response.setError("导入失败！请选择正确模板");
		}
		String catalogID = $V("CatalogID");
		Map<String, String> map = new HashMap<String, String>();
		try {
			for (int x = 0; x < dt.getRowCount(); x++) {
				if (dt.getString(x, 0) != null && dt.getString(x, 0) != "") {
					String columnCode = dt.getString(x, "代码").trim();
					long count = new QueryBuilder(
							"select count(*) from ZDColumnRela where RelaType='"
									+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN
									+ "' and RelaID = ? and ColumnCode =? ",
							catalogID, columnCode).executeLong();
					if (count > 0) {
						Response.setLogInfo(0, "已经存在" + columnCode + "字段");
						return;
					}
					if (map.get(dt.getString(x, "代码").trim()) != null) {
						Response.setLogInfo(0,
								"模板中有重复字段,重复字段为" + dt.getString(x, "代码")
										+ ",请仔细检查，并重新上传！");
						return;
					}
					map.put(dt.getString(x, "代码"), x + "");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "导入失败,模板中存在错误,请仔细检查.");
			return;
		}
		Transaction trans = new Transaction();
		ZDColumnSchema column = new ZDColumnSchema();
		String InputType="";
		String VerifyType="";
		String IsMandatory="";
		String extend="";
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, 0) != null && dt.getString(i, 0) != "") {
				if("文本框".equals(dt.getString(i, "表现形式").trim())){
					InputType="1";
				}else if("多行文本框".equals(dt.getString(i, "表现形式"))){
					InputType="2";
				}else if("下拉框".equals(dt.getString(i, "表现形式").trim())){
					InputType="3";
				}else if("单选框".equals(dt.getString(i, "表现形式").trim())){
					InputType="4";
				}else if("多选框".equals(dt.getString(i, "表现形式").trim())){
					InputType="5";
				}else if("日期框".equals(dt.getString(i, "表现形式").trim())){
					InputType="6";
				}else if("媒体库图片框".equals(dt.getString(i, "表现形式").trim())){
					InputType="7";
				}else if("HTML".equals(dt.getString(i, "表现形式").trim())){
					InputType="8";
				}else if("时间框".equals(dt.getString(i, "表现形式").trim())){
					InputType="9";
				}
				if("无".equals(dt.getString(i, "校验类型").trim())){
					VerifyType="1";
				}else if("数字".equals(dt.getString(i, "校验类型"))){
					VerifyType="2";
				}else if("整数".equals(dt.getString(i, "校验类型").trim())){
					VerifyType="3";
				}else if("非空".equals(dt.getString(i, "校验类型").trim())){
					VerifyType="4";
				}else if("邮箱".equals(dt.getString(i, "校验类型").trim())){
					VerifyType="5";
				}
				if("否".equals(dt.getString(i, "是否必填").trim())){
					IsMandatory="N";
				}else if("是".equals(dt.getString(i, "是否必填"))){
					IsMandatory="Y";
				}
				String columnCode = dt.getString(i, "代码").trim();
				int catalogType = new QueryBuilder(
						"select type from zccatalog where id = ?",
						Long.parseLong(catalogID)).executeInt();

				column = new ZDColumnSchema();
				try {
					// 把空格，全角空格，逗号，全角逗号等都替换为英文的逗号
					String defaultValue = dt.getString(i, "默认值");
					defaultValue = defaultValue.replaceAll("　　", ",");
					defaultValue = defaultValue.replaceAll("　", ",");
					defaultValue = defaultValue.replaceAll("  ", ",");
					defaultValue = defaultValue.replaceAll(" ", ",");
					defaultValue = defaultValue.replaceAll(",,", ",");
					defaultValue = defaultValue.replaceAll("，，", ",");
					defaultValue = defaultValue.replaceAll("，", ",");
					column.setDefaultValue(defaultValue);

					column.setID(NoUtil.getMaxID("ColumnID"));
					column.setSiteID(Application.getCurrentSiteID());
					column.setOrderFlag(OrderUtil.getDefaultOrder());
					column.setAddTime(new Date());
					column.setAddUser(User.getUserName());
					column.setCode(columnCode);
					column.setName(dt.getString(i, "名称").trim());
					column.setInputType(InputType);
					column.setVerifyType(VerifyType);
					column.setIsMandatory(IsMandatory);
					String ListOption = dt.getString(i, "列表选项").trim().replace(",",
							"\n");
					String maxLength = null;
					try{
						String regEx = "[^0-9]";       
						Pattern p = Pattern.compile(regEx);        
						Matcher m = p.matcher(dt.getString(i, "最大字数"));       
						String str =  m.replaceAll("").trim();
						 
						if ("".equals(str)) {
							maxLength = null;
						} else {
							maxLength = str;
						}
						column.setMaxLength(maxLength);
					}catch(Exception e){
						logger.error(e.getMessage(), e);
						Response.setLogInfo(0, "导入失败,最大字数列存在错误,请仔细检查是否有输入空格.");
						return;
					}
					

					if (ColumnUtil.Input.equals(InputType)) {
						column.setColSize(null);
						column.setRowSize(null);
						column.setListOption("");
					} else if (ColumnUtil.Text.equals(InputType)) {
						column.setColSize("450");
						column.setRowSize("180");
						column.setListOption("");
					} else if (ColumnUtil.Select
							.equals(InputType)) {
						column.setColSize(null);
						column.setRowSize(null);
						column.setMaxLength(null);
						column.setVerifyType(ColumnUtil.STRING);
						column.setListOption(ListOption);
					} else if (ColumnUtil.Radio.equals(InputType)) {
						column.setIsMandatory("N");
						column.setColSize(null);
						column.setRowSize(null);
						column.setMaxLength(null);
						column.setVerifyType(ColumnUtil.STRING);
						column.setListOption(ListOption);
					} else if (ColumnUtil.Checkbox.equals(InputType)) {
						column.setIsMandatory("N");
						column.setColSize(null);
						column.setRowSize(null);
						column.setMaxLength(null);
						column.setVerifyType(ColumnUtil.STRING);
						column.setListOption(ListOption);
					} else if (ColumnUtil.DateInput.equals(InputType)
							|| ColumnUtil.TimeInput.equals(InputType)) {
						column.setColSize(null);
						column.setRowSize(null);
						column.setMaxLength(null);
						column.setListOption("");
						column.setVerifyType(ColumnUtil.STRING);
					} else if (ColumnUtil.ImageInput.equals(InputType)) {
						column.setIsMandatory("N");
						column.setColSize(null);
						column.setRowSize(null);
						column.setMaxLength(null);
						column.setListOption("");
						column.setVerifyType(ColumnUtil.STRING);
					} else if (ColumnUtil.HTMLInput.equals(InputType)) {
						column.setIsMandatory("N");
						column.setColSize(null);
						column.setRowSize(null);
						column.setMaxLength(null);
						column.setListOption("");
						column.setVerifyType(ColumnUtil.STRING);
						column.setHTML(dt.getString(i, "HTML内容"));
					}

					if("仅本栏目".equals(dt.getString(i, "字段沿用").trim())){
						extend="1";
					}else if("所有子栏目沿用相同设置".equals(dt.getString(i, "字段沿用"))){
						extend="2";
					}else if("所有栏目沿用相同设置".equals(dt.getString(i, "字段沿用").trim())){
						extend="3";
					}
					if (Extend_Self.equals(extend)) {
						ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
						rela.setID(NoUtil.getMaxID("ColumnRelaID"));
						rela.setColumnID(column.getID());
						rela.setColumnCode(column.getCode());
						rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
						rela.setRelaID(catalogID);
						rela.setAddTime(column.getAddTime());
						rela.setAddUser(column.getAddUser());
						trans.add(rela, Transaction.INSERT);
					} else if (Extend_Children.equals(extend)) {
						String innerCode = CatalogUtil.getInnerCode(catalogID);
						DataTable childCatalogDT = new QueryBuilder(
								"select id from zccatalog where innercode like '"
										+ innerCode + "%'").executeDataTable();
						for (int k = 0; k < childCatalogDT.getRowCount(); k++) {
							ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
							rela.setColumnCode(column.getCode());
							rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
							rela.setRelaID(childCatalogDT.getString(k, 0));
							if (rela.query().size() > 0) {
								continue;
							}
							rela.setID(NoUtil.getMaxID("ColumnRelaID"));
							rela.setColumnID(column.getID());
							rela.setColumnCode(column.getCode());
							rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
							rela.setRelaID(childCatalogDT.getString(k, 0));
							rela.setAddTime(column.getAddTime());
							rela.setAddUser(column.getAddUser());
							trans.add(rela, Transaction.INSERT);
						}
					} else if (Extend_All.equals(extend)) {
						DataTable allCatalogDT = new QueryBuilder(
								"select id from zccatalog where Type="
										+ catalogType + " and siteID ="
										+ Application.getCurrentSiteID())
								.executeDataTable();
						for (int j = 0; j < allCatalogDT.getRowCount(); j++) {
							ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
							rela.setColumnCode(column.getCode());
							rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
							rela.setRelaID(allCatalogDT.getString(j, 0));
							if (rela.query().size() > 0) {
								continue;
							}
							rela.setID(NoUtil.getMaxID("ColumnRelaID"));
							rela.setColumnID(column.getID());
							rela.setColumnCode(column.getCode());
							rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
							rela.setRelaID(allCatalogDT.getString(j, 0));
							rela.setAddTime(column.getAddTime());
							rela.setAddUser(column.getAddUser());
							trans.add(rela, Transaction.INSERT);
						}
					} else if (Extend_SameLevel.equals(extend)) {
						int level = new QueryBuilder(
								"select treelevel from zccatalog where id ="
										+ catalogID).executeInt();
						DataTable levelCatalogDT = new QueryBuilder(
								"select id from zccatalog where siteID ="
										+ Application.getCurrentSiteID()
										+ " and Type=" + catalogType
										+ " and treelevel=" + level)
								.executeDataTable();
						for (int m = 0; m < levelCatalogDT.getRowCount(); m++) {
							ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
							rela.setColumnCode(column.getCode());
							rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
							rela.setRelaID(levelCatalogDT.getString(m, 0));
							if (rela.query().size() > 0) {
								continue;
							}

							rela.setID(NoUtil.getMaxID("ColumnRelaID"));
							rela.setColumnID(column.getID());
							rela.setColumnCode(column.getCode());
							rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
							rela.setRelaID(levelCatalogDT.getString(m, 0));
							rela.setAddTime(column.getAddTime());
							rela.setAddUser(column.getAddUser());
							trans.add(rela, Transaction.INSERT);
						}
					}
					trans.add(column, Transaction.INSERT);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					Response.setLogInfo(0, "导入失败");
					return;
				}
			}
		}
		try {
			if (trans.commit()) {
				Response.setLogInfo(1, "导入成功");
			} else {
				Response.setLogInfo(0, "导入失败，插入数据时出现错误。");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setLogInfo(0, "导入失败，插入数据时出现错误。");
			return;
		}
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		Transaction transa = new Transaction();
		transa.add(catalog, Transaction.UPDATE);
	}

	public void save() {
		String catalogID = $V("CatalogID");
		String columnCode = $V("Code");
		long count = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
				+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and RelaID = ? and ColumnCode =? and ColumnID!="
				+ $V("ColumnID"), catalogID, columnCode).executeLong();
		if (count > 0) {
			Response.setLogInfo(0, "已经存在相同的字段");
			return;
		}
		ZDColumnSchema column = new ZDColumnSchema();
		column.setID($V("ColumnID"));
		column.fill();
		column.setValue(this.Request);
		// 把空格，全角空格，逗号，全角逗号等都替换为英文的逗号
		String defaultValue = column.getDefaultValue();
		defaultValue = defaultValue.replaceAll("　　", ",");
		defaultValue = defaultValue.replaceAll("　", ",");
		defaultValue = defaultValue.replaceAll("  ", ",");
		defaultValue = defaultValue.replaceAll(" ", ",");
		defaultValue = defaultValue.replaceAll(",,", ",");
		defaultValue = defaultValue.replaceAll("，，", ",");
		defaultValue = defaultValue.replaceAll("，", ",");
		column.setDefaultValue(defaultValue);

		if (ColumnUtil.Input.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setListOption("");
		} else if (ColumnUtil.Text.equals(column.getInputType())) {
			column.setListOption("");
		} else if (ColumnUtil.Select.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Radio.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.Checkbox.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.DateInput.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.ImageInput.equals(column.getInputType())) {
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		} else if (ColumnUtil.HTMLInput.equals(column.getInputType())) {
			column.setIsMandatory("N");
			column.setColSize(null);
			column.setRowSize(null);
			column.setMaxLength(null);
			column.setListOption("");
			column.setVerifyType(ColumnUtil.STRING);
		}
		Transaction trans = new Transaction();
		trans.add(column, Transaction.UPDATE);
		trans.add(new QueryBuilder("update zdcolumnrela set ColumnCode=? where ColumnID=?", column.getCode(), column
				.getID()));
		trans.add(new QueryBuilder("update zdcolumnvalue set ColumnCode=? where ColumnID=?", column.getCode(), column
				.getID()));

		int catalogType = new QueryBuilder("select type from zccatalog where id = ?", Long.parseLong(catalogID)).executeInt();

		String extend = $V("Extend");
		if (Extend_Self.equals(extend)) {

		} else if (Extend_Children.equals(extend)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			DataTable childCatalogDT = new QueryBuilder("select id from zccatalog where innercode like '" + innerCode
					+ "%' and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='" + column.getCode()
					+ "' and b.RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and b.RelaID = zccatalog.ID)")
					.executeDataTable();
			for (int i = 0; i < childCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(childCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		} else if (Extend_All.equals(extend)) {
			DataTable allCatalogDT = new QueryBuilder("select id from zccatalog where Type=" + catalogType
					+ " and siteID =" + Application.getCurrentSiteID()
					+ " and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='" + column.getCode()
					+ "' and b.RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and b.RelaID = zccatalog.ID)")
					.executeDataTable();
			for (int i = 0; i < allCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(allCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		} else if (Extend_SameLevel.equals(extend)) {
			int level = new QueryBuilder("select treelevel from zccatalog where id =" + catalogID).executeInt();
			DataTable levelCatalogDT = new QueryBuilder("select id from zccatalog where siteID ="
					+ Application.getCurrentSiteID() + " and Type=" + catalogType + " and treelevel=" + level
					+ " and not exists (select 'x' from zdcolumnrela b where b.ColumnCode='" + column.getCode()
					+ "' and b.RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and b.RelaID = zccatalog.ID)")
					.executeDataTable();
			for (int i = 0; i < levelCatalogDT.getRowCount(); i++) {
				ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
				rela.setID(NoUtil.getMaxID("ColumnRelaID"));
				rela.setColumnID(column.getID());
				rela.setColumnCode(column.getCode());
				rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
				rela.setRelaID(levelCatalogDT.getString(i, 0));
				rela.setAddTime(column.getAddTime());
				rela.setAddUser(column.getAddUser());
				trans.add(rela, Transaction.INSERT);
			}
		}
		
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		
		if (trans.commit()) {
			Response.setLogInfo(1, "保存成功!");
		} else {
			Response.setLogInfo(0, "保存失败!");
		}
	}

	public void del() {// 本方法需要注意SQL注入
		String IDs = $V("IDs");
		long catalogID = Long.parseLong($V("CatalogID"));
		Transaction trans = new Transaction();
		String extend = $V("Extend");
		String wherePart = "";
		if (Extend_Self.equals(extend)) {
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN
					+ "' and RelaID='" + catalogID + "'";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_DOCID
					+ "' and RelaID='" + catalogID + "'";
			deleteTableData("ZDColumnValue", wherePart, trans);
		} else if (Extend_Children.equals(extend)) {
			String innerCode = CatalogUtil.getInnerCode(catalogID);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.InnerCode like '" + innerCode
					+ "%')";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_DOCID
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.InnerCode like '" + innerCode
					+ "%')";
			deleteTableData("ZDColumnValue", wherePart, trans);
		} else if (Extend_All.equals(extend)) {
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_CATALOG_COLUMN
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.SiteID = "
					+ Application.getCurrentSiteID() + ")";
			deleteTableData("ZDColumnRela", wherePart, trans);
			wherePart = " where columnID in (" + IDs + ") and RelaType='" + ColumnUtil.RELA_TYPE_DOCID
					+ "' and exists (select '' from ZCCatalog b where b.ID=RelaID and b.SiteID = "
					+ Application.getCurrentSiteID() + ")";
			deleteTableData("ZDColumnValue", wherePart, trans);
		}
		// 如果关联表内已经不存在自定义字段的关联关系，删除ZDColumn表中的数据，彻底清除所有垃圾数据
		wherePart = " where not exists (select 1 from ZDColumnRela where ZDColumn.ID=ColumnID)";
		deleteTableData("ZDColumn", wherePart, trans);
		
		// 需要更新栏目修改时间
		ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
		catalog.setModifyTime(new Date());
		catalog.setModifyUser(User.getUserName());
		trans.add(catalog, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	/**
	 * 针对大数据量删除备份的时候用Set会出现内存溢出的问题，可以用以下方法解决。
	 */
	public void deleteTableData(String tableName, String wherePart, Transaction trans) {
		String backupNO = String.valueOf(System.currentTimeMillis()).substring(1, 11);
		String backupOperator = User.getUserName();
		String backupTime = DateUtil.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
		String backup = "insert into b" + tableName + " select " + tableName + ".*,'" + backupNO + "','"
				+ backupOperator + "','" + backupTime + "',null from " + tableName + wherePart;
		String delete = "delete from " + tableName + wherePart;
		trans.add(new QueryBuilder(backup));
		trans.add(new QueryBuilder(delete));
	}

	// 拷贝至其他栏目
	public static Mapx initCopyTo(Mapx params) {
		Mapx map = new Mapx();
		String tIDs = params.get("IDs").toString();
		map.put("IDs", tIDs);
		String tCatalogID = params.get("CatalogID").toString();
		map.put("CatalogID", tCatalogID);
		String tSiteID = new QueryBuilder("select siteid from ZCCatalog where id=?", tCatalogID).executeString();
		map.put("SiteID", tSiteID);
		DataTable dt = new QueryBuilder("select name,id from ZCCatalog where siteid=? and id<>? order by id", tSiteID,
				tCatalogID).executeDataTable();
		map.put("optCatalog", HtmlUtil.dataTableToOptions(dt));
		return map;
	}
	//系统字段添加
	public void sysAdd() {
		String catalogID = $V("CatalogID");
		
		Transaction trans = new Transaction();
		String tCTId = $V("CatalogType");
		SCCatalogValueSchema tSCCatalogValueSchema=new SCCatalogValueSchema();
		SCCatalogValueSet tSCCatalogValueSet=tSCCatalogValueSchema.query(new QueryBuilder("where CTId ='"+tCTId+"'"));
		if(tSCCatalogValueSet.size()>0){
			for(int j=0;j<tSCCatalogValueSet.size();j++){
				tSCCatalogValueSchema=tSCCatalogValueSet.get(j);
				
				
				int catalogType = new QueryBuilder("select type from zccatalog where id = ?", Long.parseLong(catalogID)).executeInt();
				long count = new QueryBuilder("select count(*) from ZDColumnRela where RelaType='"
						+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and RelaID = ? and ColumnCode =? ", catalogID, tSCCatalogValueSchema.getValueCode())
				.executeLong();
				if (count > 0) {
					Response.setLogInfo(0, "已经存在相同的字段");
					return;
				}
				
				ZDColumnSchema column = new ZDColumnSchema();
				column.setID(NoUtil.getMaxID("ColumnID"));
				column.setSiteID(Application.getCurrentSiteID());
				column.setOrderFlag(OrderUtil.getDefaultOrder());
				column.setAddTime(new Date());
				column.setAddUser(User.getUserName());
				column.setDefaultValue(tSCCatalogValueSchema.getDefaultValue());
				column.setIsMandatory(tSCCatalogValueSchema.getIsMandatory());
				column.setColSize(tSCCatalogValueSchema.getColSize());
				column.setRowSize(tSCCatalogValueSchema.getRowSize());
				column.setMaxLength(tSCCatalogValueSchema.getMaxLength());
				column.setListOption(tSCCatalogValueSchema.getListOption());
				column.setVerifyType(tSCCatalogValueSchema.getVerifyType());
				column.setHTML(tSCCatalogValueSchema.getHTML());
				column.setInputType(tSCCatalogValueSchema.getInputType());
				column.setCode(tSCCatalogValueSchema.getValueCode());
				column.setName(tSCCatalogValueSchema.getValueName());
				
				
				
				String extend = $V("Extend");
				if (Extend_Self.equals(extend)) {
					ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
					rela.setID(NoUtil.getMaxID("ColumnRelaID"));
					rela.setColumnID(column.getID());
					rela.setColumnCode(column.getCode());
					rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
					rela.setRelaID(catalogID);
					rela.setAddTime(column.getAddTime());
					rela.setAddUser(column.getAddUser());
					trans.add(rela, Transaction.INSERT);
				} else if (Extend_Children.equals(extend)) {
					String innerCode = CatalogUtil.getInnerCode(catalogID);
					DataTable childCatalogDT = new QueryBuilder("select id from zccatalog where innercode like '" + innerCode
							+ "%'").executeDataTable();
					for (int i = 0; i < childCatalogDT.getRowCount(); i++) {
						ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
						rela.setColumnCode(column.getCode());
						rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
						rela.setRelaID(childCatalogDT.getString(i, 0));
						if(rela.query().size()>0){
							continue;
						}
						rela.setID(NoUtil.getMaxID("ColumnRelaID"));
						rela.setColumnID(column.getID());
						rela.setColumnCode(column.getCode());
						rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
						rela.setRelaID(childCatalogDT.getString(i, 0));
						rela.setAddTime(column.getAddTime());
						rela.setAddUser(column.getAddUser());
						trans.add(rela, Transaction.INSERT);
					}
				} else if (Extend_All.equals(extend)) {
					DataTable allCatalogDT = new QueryBuilder("select id from zccatalog where uzType=" + catalogType
							+ " and siteID =" + Application.getCurrentSiteID()).executeDataTable();
					for (int i = 0; i < allCatalogDT.getRowCount(); i++) {
						ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
						rela.setColumnCode(column.getCode());
						rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
						rela.setRelaID(allCatalogDT.getString(i, 0));
						if(rela.query().size()>0){
							continue;
						}
						rela.setID(NoUtil.getMaxID("ColumnRelaID"));
						rela.setColumnID(column.getID());
						rela.setColumnCode(column.getCode());
						rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
						rela.setRelaID(allCatalogDT.getString(i, 0));
						rela.setAddTime(column.getAddTime());
						rela.setAddUser(column.getAddUser());
						trans.add(rela, Transaction.INSERT);
					}
				} else if (Extend_SameLevel.equals(extend)) {
					int level = new QueryBuilder("select treelevel from zccatalog where id =" + catalogID).executeInt();
					DataTable levelCatalogDT = new QueryBuilder("select id from zccatalog where siteID ="
							+ Application.getCurrentSiteID() + " and Type=" + catalogType + " and treelevel=" + level)
					.executeDataTable();
					for (int i = 0; i < levelCatalogDT.getRowCount(); i++) {
						ZDColumnRelaSchema rela = new ZDColumnRelaSchema();
						rela.setColumnCode(column.getCode());
						rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
						rela.setRelaID(levelCatalogDT.getString(i, 0));
						if(rela.query().size()>0){
							continue;
						}
						
						rela.setID(NoUtil.getMaxID("ColumnRelaID"));
						rela.setColumnID(column.getID());
						rela.setColumnCode(column.getCode());
						rela.setRelaType(ColumnUtil.RELA_TYPE_CATALOG_COLUMN);
						rela.setRelaID(levelCatalogDT.getString(i, 0));
						rela.setAddTime(column.getAddTime());
						rela.setAddUser(column.getAddUser());
						trans.add(rela, Transaction.INSERT);
					}
				}
				
				
				// 需要更新栏目修改时间
				ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
				catalog.setModifyTime(new Date());
				catalog.setModifyUser(User.getUserName());
				trans.add(catalog, Transaction.UPDATE);
				
				trans.add(column, Transaction.INSERT);
				
				
				
			}
		}

		if (trans.commit()) {
			Response.setLogInfo(1, "新建成功");
		} else {
			Response.setLogInfo(0, "新建失败");
		}

	}

}
