package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.controls.SelectTag;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDColumnSchema;
import com.sinosoft.schema.ZDColumnValueSchema;
import com.sinosoft.schema.ZDColumnValueSet;


public class ColumnUtil {
	public static final String PREFIX = "_C_"; // 自定义字段前缀

	public static final String RELA_TYPE_CATALOG_EXTEND = "0"; // 栏目扩展属性关联

	public static final String RELA_TYPE_CATALOG_COLUMN = "1"; // 栏目自定义字段关联

	public static final String RELA_TYPE_DOCID = "2"; // 文档关联

	public static final String RELA_TYPE_GoodsTypeAttr = "3"; // 商品类型关联-->商品属性

	public static final String RELA_TYPE_GoodsTypeParam = "4"; // 商品参数组关联

	// 表现形式-InputType
	public static String Input = "1";

	public static String Text = "2";

	public static String Select = "3";

	public static String Radio = "4";

	public static String Checkbox = "5";

	public static String DateInput = "6";

	public static String ImageInput = "7";

	public static String HTMLInput = "8";

	public static String TimeInput = "9";

	public static Mapx InputTypeMap = new Mapx();

	static {
		InputTypeMap.put(Input, "文本框");
		InputTypeMap.put(Text, "多行文本框");
		InputTypeMap.put(Select, "下拉框");
		InputTypeMap.put(Radio, "单选框");
		InputTypeMap.put(Checkbox, "多选框");
		InputTypeMap.put(DateInput, "日期框");
		InputTypeMap.put(TimeInput, "时间框");
		InputTypeMap.put(ImageInput, "媒体库图片框");
		InputTypeMap.put(HTMLInput, "HTML");
	}

	// 校验类型-VerifyType
	public static String STRING = "1";

	public static String NUMBER = "2";

	public static String INT = "3";

	public static String NOTNULL = "4";

	public static String EMAIL = "5";

	public static Mapx VerifyTypeMap = new Mapx();

	static {
		VerifyTypeMap.put(STRING, "无");
		VerifyTypeMap.put(NUMBER, "数字");
		VerifyTypeMap.put(INT, "整数");
		VerifyTypeMap.put(NOTNULL, "非空");
		VerifyTypeMap.put(EMAIL, "邮箱");
	}

	public static String[][] IsMandatoryArray = new String[][] { { "Y", "必填" } };

	public static DataTable getColumn(String relaType, long relaID) {
		return getColumn(relaType, relaID + "");
	}

	public static DataTable getColumn(String relaType, String relaID) {
		return new QueryBuilder(
				"select * from zdcolumn where exists (select columnid from zdcolumnrela where relatype=? and relaid=? and columnid=zdcolumn.id) order by id asc",
				relaType, relaID).executeDataTable();
	}

	public static DataTable getColumn(String relaType, String relaID, String hidden) {
		return new QueryBuilder(
				"select * from zdcolumn where Prop1 != '1' and exists (select 1 from zdcolumnrela where relatype=? and relaid=? and columnid=zdcolumn.id) order by id asc",
				relaType, relaID).executeDataTable();
	}

	public static DataTable getColumnValue(String relaType, long relaID) {
		return getColumnValue(relaType, relaID + "");
	}

	public static DataTable getColumnValue(String relaType, String relaID) {
		return new QueryBuilder("select * from zdcolumnvalue where relatype=? and relaid = ?", relaType, relaID)
				.executeDataTable();
	}

	public static SchemaSet getValueFromRequest(long catalogID, long docID, Mapx Request) {
		DataTable dt = ColumnUtil.getColumn(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, catalogID);
		ZDColumnValueSet set = new ZDColumnValueSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			
			ZDColumnValueSchema value = new ZDColumnValueSchema();
			value.setID(NoUtil.getMaxID("ColumnValueID"));
			value.setColumnID(dt.getString(i, "ID"));
			value.setColumnCode(dt.getString(i, "Code"));
			value.setRelaType(ColumnUtil.RELA_TYPE_DOCID);
			value.setRelaID(docID + "");
			ZDColumnSchema column = new ZDColumnSchema();
			column.setID(dt.getString(i, "ID"));
			column.fill();
			if (ColumnUtil.ImageInput.equals(column.getInputType().trim())) {
				String textvalue = Request.getString(ColumnUtil.PREFIX + value.getColumnCode());
				if (StringUtil.isNotEmpty(textvalue) && textvalue.indexOf("upload") > 0) {
					textvalue = textvalue.substring(textvalue.indexOf("upload"));
				}
				value.setTextValue(textvalue);
			} else {
				value.setTextValue(Request.getString(ColumnUtil.PREFIX + value.getColumnCode()));
			}
			if ("AdaptPeopleInfoV3".equals(dt.getString(i, "Code")) && "publish".equals(value.getTextValue())) {
				value.setTextValue(new QueryBuilder("select TextValue from zdcolumnvalue where relatype=? and relaid = ? and ColumnCode='AdaptPeopleInfoV3'",ColumnUtil.RELA_TYPE_DOCID, docID+"").executeString());
			}
			set.add(value);
		}
		return set;
	}

	public static void extendDocColumnData(DataTable dt, long catalogID) {
		extendDocColumnData(dt, catalogID + "");
	}

	/**
	 * 得到DataTable的自定义数据
	 * 
	 * @param dt
	 * @param catalogID
	 */
	public static void extendDocColumnData(DataTable dt, String catalogID) {
		DataTable columnDT = new QueryBuilder("select columncode from zdcolumnrela where relatype='"
				+ ColumnUtil.RELA_TYPE_CATALOG_COLUMN + "' and relaid = ?", catalogID).executeDataTable();
		String[] columnNames = null;
		if (columnDT.getRowCount() > 0) {
			columnNames = new String[columnDT.getRowCount()];
			for (int i = 0; i < columnDT.getRowCount(); i++) {
				columnNames[i] = columnDT.getString(i, 0);
			}
		} else {
			return;
		}
		int colCount = dt.getColCount();
		StringBuffer relaidsb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (i == 0) {
				relaidsb.append("'");
			} else {
				relaidsb.append(",'");
			}
			relaidsb.append(dt.getString(i, "ID"));
			relaidsb.append("'");
		}

		if (StringUtil.isEmpty(relaidsb.toString())) {
			return;
		}
		for (int i = 0; i < columnNames.length; i++) {
			if (dt.getDataColumn(columnNames[i]) == null) {
				dt.insertColumn(columnNames[i]);
			}
		}
		DataTable valueDT = new QueryBuilder("select * from zdcolumnvalue where relatype='"
				+ ColumnUtil.RELA_TYPE_DOCID + "' and relaid in (" + relaidsb + ")").executeDataTable();
		if (valueDT.getRowCount() == 0) {
			return;
		}
		for (int j = 0; j < dt.getRowCount(); j++) {
			for (int k = colCount; k < dt.getColCount(); k++) {
				for (int index = 0; index < valueDT.getRowCount(); index++) {
					if (dt.getString(j, "ID").equals(valueDT.getString(index, "RelaID"))
							&& dt.getDataColumn(k).getColumnName().equals(valueDT.getString(index, "ColumnCode"))) {
						dt.set(j, k, valueDT.getString(index, "TextValue"));
						break;
					}
				}
			}
		}
	}

	public static void extendCatalogColumnData(DataTable dt, String levelStr) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			extendCatalogColumnData(dr, levelStr);
		}
	}

	public static void extendCatalogColumnData(DataTable dt, long siteID, String levelStr) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			extendCatalogColumnData(dr, siteID, levelStr);
		}
	}

	public static void extendCatalogColumnData(DataRow dr, String levelStr) {
		extendCatalogColumnData(dr, Application.getCurrentSiteID(), levelStr);
	}

	public static void extendCatalogColumnData(DataRow dr, long siteID, String levelStr) {
		DataTable valueDT = new QueryBuilder("select a.InputType,b.ColumnCode,b.TextValue from zdcolumn a,"
				+ "zdcolumnvalue b where a.ID = b.ColumnID and b.relatype='" + ColumnUtil.RELA_TYPE_CATALOG_EXTEND
				+ "' and b.relaid ='" + dr.getString("ID") + "'").executeDataTable();
		if (valueDT.getRowCount() == 0) {
			return;
		}
		for (int j = 0; j < valueDT.getRowCount(); j++) {
			if (ImageInput.equals(valueDT.getString(j, "InputType"))) {
				dr.insertColumn(valueDT.getString(j, "ColumnCode"), levelStr + valueDT.getString(j, "TextValue"));
			} else {
				dr.insertColumn(valueDT.getString(j, "ColumnCode"), valueDT.getString(j, "TextValue"));
			}

		}
	}

	public static String getHtml(String relaType, String relaID) {
		return getHtml(getColumn(relaType, relaID));
	}

	public static String getHtml(DataTable dt) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			sb.append(getHtml(dt.getDataRow(i)));
		}
		return sb.toString();
	}

	private static String getHtml(DataRow dr) {
		return getHtml(dr, null);
	}

	/**
	 * 返回一组自定义数据的Html代码
	 * 
	 * @param relaType
	 * @param relaID
	 * @return
	 */
	public static String getHtml(String relaType, String relaID, String valueRelaType, String valueRelaID) {
		return getHtml(getColumn(relaType, relaID), getColumnValue(valueRelaType, valueRelaID));
	}

	public static String getHtml(String relaType, String relaID, String valueRelaType, String valueRelaID, String hidden) {
		return getHtml(getColumn(relaType, relaID, hidden), getColumnValue(valueRelaType, valueRelaID));
	}

	public static String getHtml(String relaType, String relaID, String hidden) {
		return getHtml(getColumn(relaType, relaID, hidden));
	}

	public static String getHtml(DataTable dt, DataTable valueDT) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			sb.append(getHtml(dt.getDataRow(i), valueDT));
		}
		return sb.toString();
	}

	private static String getHtml(DataRow dr, DataTable valueDT) {
		String columnName = dr.getString("Name");
		String columnCode = dr.getString("Code");
		String inputType = dr.getString("inputType");
		String verifyType = dr.getString("verifyType");
		String listOption = dr.getString("listOption");
		String defaultValue = dr.getString("defaultValue");
		String isMandatory = dr.getString("IsMandatory");
		String maxLength = dr.getString("maxLength");
		String HTML = dr.getString("HTML");
		String verifyStr = "verify='" + columnName + "|";
		if ("Y".equals(isMandatory)) {
			verifyStr += "NotNull";
			if (ColumnUtil.ImageInput.equals(inputType)) {
				verifyStr += "&&图片不能为空|Script=checkMandatory(\"" + ColumnUtil.PREFIX + columnCode + "\")";
			}
		}
		if (ColumnUtil.STRING.equals(verifyType)) {

		} else if (ColumnUtil.NUMBER.equals(verifyType)) {
			verifyStr += "&&Number";
		} else if (ColumnUtil.INT.equals(verifyType)) {
			verifyStr += "&&Int";
		} else if (ColumnUtil.EMAIL.equals(verifyType)) {
			verifyStr += "&&Email";
		}
		if (StringUtil.isNotEmpty(maxLength) && !"0".equals(maxLength)) {
			verifyStr += "&&Length<" + maxLength + "'";
		} else {
			verifyStr += "'";
		}

		if (valueDT != null) {
			for (int i = 0; i < valueDT.getRowCount(); i++) {
				DataRow r = valueDT.getDataRow(i);
				if (columnCode.equalsIgnoreCase(r.getString("columnCode")) && r.getString("TextValue") != null) {
					defaultValue = r.getString("TextValue");
				}
			}
		}

		columnCode = ColumnUtil.PREFIX + columnCode;
		StringBuffer sb = new StringBuffer();
		if (inputType.equals(ColumnUtil.HTMLInput)) {
			sb.append("<tr><td colspan='4' >");
		} else {
			sb.append("<tr><td height='25' align='right' >");
			sb.append(columnName);
			sb.append("：</td><td align='left' colspan='3'>");
		}
		// 1 单行文本
		if (inputType.equals(ColumnUtil.Input)) {
			sb.append("<input type='text' size='40' id='" + columnCode + "' name='" + columnCode + "' value='"
					+ defaultValue + "' " + verifyStr + " />");
		}
		// 2 多行文本
		if (inputType.equals(ColumnUtil.Text)) {
			sb.append("<textarea style='width:" + dr.getString("ColSize") + "px;height:" + dr.getString("RowSize")
					+ "px' id='" + columnCode + "' name='" + columnCode + "' " + verifyStr + ">" + defaultValue
					+ "</textarea>");
		}
		// 3 下拉列表框
		if (inputType.equals(ColumnUtil.Select)) {
			SelectTag select = new SelectTag();
			select.setId(columnCode);
			if ("Y".equals(isMandatory)) {
				select.setVerify(columnName + "|NotNull");
			}
			String[] array = listOption.split("\\n");
			sb.append(select.getHtml(HtmlUtil.arrayToOptions(array, defaultValue, true)));
		}
		// 4 单选框
		if (inputType.equals(ColumnUtil.Radio)) {
			String[] array = listOption.split("\\n");
			if (StringUtil.isEmpty(defaultValue) && array.length > 0) {
				defaultValue = array[0];
			}
			sb.append(HtmlUtil.arrayToRadios(columnCode, array, defaultValue));
		}
		// 5 多选框
		if (inputType.equals(ColumnUtil.Checkbox)) {
			String[] array = listOption.split("\\n");
			defaultValue = defaultValue.replaceAll("　　", ",");
			defaultValue = defaultValue.replaceAll("　", ",");
			defaultValue = defaultValue.replaceAll("  ", ",");
			defaultValue = defaultValue.replaceAll(" ", ",");
			defaultValue = defaultValue.replaceAll(",,", ",");
			defaultValue = defaultValue.replaceAll("，，", ",");
			defaultValue = defaultValue.replaceAll("，", ",");
			String[] checkedArray = defaultValue.split(",");
			sb.append(HtmlUtil.arrayToCheckboxes(columnCode, array, checkedArray));
		}
		// 6 日期框
		if (inputType.equals(ColumnUtil.DateInput)) {
			sb.append("<input name='" + columnCode + "' id='" + columnCode + "' value='" + defaultValue
					+ "' type='text' size='14' ztype='Date' " + verifyStr + " />");
		}
		// 
		if (inputType.equals(ColumnUtil.TimeInput)) {
			sb.append("<input name='" + columnCode + "' id='" + columnCode + "' value='" + defaultValue
					+ "' type='text' size='10' ztype='Time' " + verifyStr + " />");
		}
		// 7 图片框
		if (inputType.equals(ColumnUtil.ImageInput)) {
			defaultValue = dr.getString("defaultValue");
			if (StringUtil.isEmpty(defaultValue)) {
				defaultValue = Config.getValue("StaticResourcePath")+ "/upload/Image/nopicture.jpg";
			}
			if (valueDT != null) {
				for (int i = 0; i < valueDT.getRowCount(); i++) {
					DataRow r = valueDT.getDataRow(i);
					if (columnCode.equalsIgnoreCase(ColumnUtil.PREFIX + r.getString("columnCode"))
							&& StringUtil.isNotEmpty(r.getString("TextValue"))) {
						defaultValue = Config.getValue("StaticResourcePath") + "/" + r.getString("TextValue");
					}
				}
			}
			sb.append("<img src='" + defaultValue.replaceAll("1_", "s_") + "' name='Img" + columnCode + "' id='Img"
					+ columnCode + "' width='120' height='90' ><input name='button' type='button' onClick=\"custom_img_upload('" + columnCode
					+ "');\" value='浏览...' /> 图片路径 <input type='text' id='" + columnCode + "' name='" + columnCode
					+ "' size='40' onblur='document.getElementById(\"Img" + columnCode + "\").src=this.value;' value='"
					+ defaultValue + "' " + verifyStr + "/>");
		}
		// 8 HTML
		if (inputType.equals(ColumnUtil.HTMLInput)) {
			sb.append(HTML);
		}
		sb.append("</td></tr>");
		return sb.toString();
	}

	public static String getText(String relaType, String relaID, String valueRelaType, String valueRelaID) {
		return getText(getColumn(relaType, relaID), getColumnValue(valueRelaType, valueRelaID));
	}

	public static String getText(DataTable dt, DataTable valueDT) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt.getRowCount(); i++) {
			sb.append(getText(dt.getDataRow(i), valueDT));
		}
		return sb.toString();
	}

	private static String getText(DataRow dr, DataTable valueDT) {
		String columnName = dr.getString("Name");
		String columnCode = dr.getString("Code");
		String inputType = dr.getString("inputType");
		String defaultValue = dr.getString("defaultValue");
		for (int i = 0; i < valueDT.getRowCount(); i++) {
			DataRow r = valueDT.getDataRow(i);
			if (columnCode.equalsIgnoreCase(r.getString("columnCode")) && r.getString("TextValue") != null) {
				defaultValue = r.getString("TextValue");
			}
		}
		columnCode = ColumnUtil.PREFIX + columnCode;
		StringBuffer sb = new StringBuffer();
		sb.append("<tr><td height='25' align='right' >");
		sb.append(columnName);
		sb.append("：</td><td>");
		// 1 单行文本
		if (inputType.equals("1")) {
			sb.append(defaultValue);
		}
		// 2 多行文本
		if (inputType.equals("2")) {
			sb.append(defaultValue);
		}
		// 3 下拉列表框
		if (inputType.equals("3")) {
			sb.append(defaultValue);
		}
		// 4 单选框
		if (inputType.equals("4")) {
			sb.append(defaultValue);
		}
		// 5 多选框
		if (inputType.equals("5")) {
			sb.append(defaultValue);
		}
		// 6 图片框
		if (inputType.equals("6")) {
			// 需要进一步写
			// sb.append(defaultValue);
		}
		// 7 附件框
		if (inputType.equals("7")) {
			// 需要进一步写
			// sb.append(defaultValue);
		}
		sb.append("</td></tr>");
		return sb.toString();
	}
}
