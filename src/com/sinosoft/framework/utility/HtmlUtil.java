package com.sinosoft.framework.utility;

import com.sinosoft.framework.Constant;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.HtmlTable;
import com.sinosoft.framework.controls.SelectTag;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * HTML工具类
 * 
 */
public class HtmlUtil {
	private static final Logger logger = LoggerFactory.getLogger(HtmlUtil.class);
	/**
	 * 把ZDCode中的基础代码转化为Mapx
	 */
	public static Mapx codeToMapx(String CodeType) {
		return CacheManager.getMapx("Code", CodeType);
	}
	
	/**
	 * 把ZDCode中的基础代码转化为下拉框
	 */
	public static String codeToOptions(String CodeType) {
		return codeToOptions(CodeType, null);
	}

	/**
	 * 把ZDCode中的基础代码转化为下拉框
	 */
	public static String codeToOptions(String CodeType, Object checkedValue) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType), checkedValue);
	}
	/**
	 * 把area中的基础代码转化为下拉框
	 */
	public static String areaToOptions(String CodeType,String parent_id,Object checkedValue) {
		QueryBuilder qb = new QueryBuilder();
		if("province".equals(CodeType)){
			qb = new QueryBuilder("select id,name from area WHERE  parent_id IS NULL AND insuranceCompany IS NULL ORDER BY NAME");
		}else if("city".equals(CodeType)){
			qb = new QueryBuilder("select id,name from area where parent_id = ? ORDER BY NAME");
			qb.add(parent_id);
		}
		DataTable dt = qb.executeDataTable();
		Mapx map = new Mapx();
		map = dt.toMapx("id", "name");
		return mapxToOptions(map,checkedValue);
	}

	/**
	 * 把ZDCode中的基础代码转化为下拉框，并自动加入一个空选项
	 */
	public static String codeToOptions(String CodeType, boolean addBlankOptionFlag) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType), null, addBlankOptionFlag);
	}
	/**
	 * 把ZDCode中的基础代码转化为下拉框，并自动加入一个全部项
	 */
	public static String codeToOptions(String CodeType, Object checkedValue,String addAllOptionFlag) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType), checkedValue, addAllOptionFlag);
	}

	/**
	 * 把ZDCode中的基础代码转化为下拉框
	 */
	public static String codeToOptions(String CodeType, Object checkedValue, boolean addBlankOptionFlag) {
		return mapxToOptions(CacheManager.getMapx("Code", CodeType), checkedValue, addBlankOptionFlag);
	}

	/**
	 * 把ZDCode中的基础代码转化为单选框
	 */
	public static String codeToRadios(String name, String CodeType) {
		return codeToRadios(name, CodeType, null, false);
	}

	/**
	 * 把ZDCode中的基础代码转化为单选框,可以置初值
	 */
	public static String codeToRadios(String name, String CodeType, Object checkedValue) {
		return codeToRadios(name, CodeType, checkedValue, false);
	}

	/**
	 * 把ZDCode中的基础代码转化为单选框
	 */
	public static String codeToRadios(String name, String CodeType, boolean direction) {
		return codeToRadios(name, CodeType, null, direction);
	}

	/**
	 * 把ZDCode中的基础代码转化为单选框
	 */
	public static String codeToRadios(String name, String CodeType, Object checkedValue, boolean direction) {
		return mapxToRadios(name, CacheManager.getMapx("Code", CodeType), checkedValue, direction,false);
	}
	
	/**
	 * 把ZDCode中的基础代码转化为单选框 不可编辑状态
	 */
	public static String codeToRadios(String name, String CodeType, Object checkedValue, boolean direction ,boolean disabled) {
		return mapxToRadios(name, CacheManager.getMapx("Code", CodeType), checkedValue, direction,disabled);
	}

	/**
	 * 把ZDCode中的基础代码转化为多选框
	 */
	public static String codeToCheckboxes(String name, String CodeType) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType));
	}

	/**
	 * 把ZDCode中的基础代码转化为多选框,可以设置显示方向
	 */
	public static String codeToCheckboxes(String name, String CodeType, boolean direction) {
		return codeToCheckboxes(name, CodeType, new String[0], direction);
	}

	/**
	 * 把ZDCode中的基础代码转化为多选框,可以置初始选中的值
	 */
	public static String codeToCheckboxes(String name, String CodeType, DataTable checkedDT) {
		return codeToCheckboxes(name, CodeType, checkedDT, false);
	}

	/**
	 * 把ZDCode中的基础代码转化为多选框
	 */
	public static String codeToCheckboxes(String name, String CodeType, DataTable checkedDT, boolean direction) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType), checkedDT, null, direction);
	}

	public static String codeToCheckboxes(String name, String CodeType, String[] checkedArray) {
		return codeToCheckboxes(name, CodeType, checkedArray, false);
	}

	/**
	 * 把ZDCode中的基础代码转化为多选框
	 */
	public static String codeToCheckboxes(String name, String CodeType, String[] checkedArray, boolean direction) {
		return mapxToCheckboxes(name, CacheManager.getMapx("Code", CodeType), checkedArray, null, direction);
	}

	public static String mapxToOptions(Map map) {
		return mapxToOptions(map, null);
	}

	public static String mapxToOptions(Map map, Object checkedValue) {
		return mapxToOptions(map, checkedValue, false);
	}

	public static String mapxToOptions(Map map, boolean addBlankOptionFlag) {
		return mapxToOptions(map, null, addBlankOptionFlag);
	}

	public static String mapxToOptions(Map map, Object checkedValue, boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append("<span value=''></span>");
		}
		if (map == null) {
			return sb.toString();
		}
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			sb.append("<span value=\"");
			Object v = keys[i];
			sb.append(v);
			if (v != null && v.equals(checkedValue)) {
				sb.append("\" selected='true' >");
			} else {
				sb.append("\">");
			}
			sb.append(map.get(v));
			sb.append("</span>");
		}
		return sb.toString();
	}
	public static String mapxToOptions(Map map, Object checkedValue, String addAllOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if ("all".equals(addAllOptionFlag)) {
			sb.append("<span value='all'");
			if("all".equals(checkedValue)){
				sb.append("\" selected='true' ");
			}
			sb.append(">全部</span>");
		}
		if (map == null) {
			return sb.toString();
		}
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			sb.append("<span value=\"");
			Object v = keys[i];
			sb.append(v);
			if (v != null && v.equals(checkedValue)) {
				sb.append("\" selected='true' >");
			} else {
				sb.append("\">");
			}
			sb.append(map.get(v));
			sb.append("</span>");
		}
		return sb.toString();
	}

	public static String mapxToRadios(String name, Map map) {
		return mapxToRadios(name, map, null, false,false);
	}

	public static String mapxToRadios(String name, Map map, Object checkedValue) {
		return mapxToRadios(name, map, checkedValue, false,false);
	}

	public static String mapxToRadios(String name, Map map, Object checkedValue, boolean direction ) {
	 
		return mapxToRadios(name,map,checkedValue,direction,false);
	}

	
	public static String mapxToRadios(String name, Map map, Object checkedValue, boolean direction,boolean disabled) {
		StringBuffer sb = new StringBuffer();
		Object[] keys = map.keySet().toArray();
		for (int i = 0; i < keys.length; i++) {
			Object value = keys[i];
			sb.append("<input type='radio' name='" + name+"'");
			if (disabled) {
				sb.append(" disabled='disabled'");
			}
			sb.append(" id='" + name + "_" + i + "' value='");
			sb.append(value);
			if (value.equals(checkedValue)) {
				sb.append("' checked >");
			} else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + i + "'>");
			sb.append(map.get(value));
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String mapxToCheckboxes(String name, Mapx map) {
		return mapxToCheckboxes(name, map, (Object[]) null, null);
	}

	public static String mapxToCheckboxes(String name, Mapx map, Object[] checkedArray) {
		return mapxToCheckboxes(name, map, checkedArray, null);
	}

	// 设置不可选的选项
	public static String mapxToCheckboxes(String name, Mapx map, DataTable checkedDT, Object[] disabledValue) {
		return mapxToCheckboxes(name, map, checkedDT, disabledValue, false);
	}

	public static String mapxToCheckboxes(String name, Mapx map, DataTable checkedDT, Object[] disabledValue,
			boolean direction) {
		String[] checkedArray = new String[checkedDT.getRowCount()];
		for (int i = 0; i < checkedDT.getRowCount(); i++) {
			checkedArray[i] = checkedDT.getString(i, 0);
		}
		return mapxToCheckboxes(name, map, checkedArray, disabledValue, direction);
	}

	public static String mapxToCheckboxes(String name, Mapx map, Object[] checkedArray, Object[] disabledValue) {
		return mapxToCheckboxes(name, map, checkedArray, disabledValue, false);
	}

	public static String mapxToCheckboxes(String name, Mapx map, Object[] checkedArray, Object[] disabledValue,
			boolean direction) {
		StringBuffer sb = new StringBuffer();
		Object[] keys = map.keyArray();
		for (int k = 0; k < keys.length; k++) {
			Object value = keys[k];
			sb.append("<input type='checkbox'");
			sb.append(" name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			sb.append("'");
			if (disabledValue != null) {
				for (int j = 0; j < disabledValue.length; j++) {
					if (value.equals(disabledValue[j])) {
						sb.append(" disabled");
						break;
					}
				}
			}

			if (checkedArray != null) {
				for (int j = 0; j < checkedArray.length; j++) {
					if (value.equals(checkedArray[j])) {
						sb.append(" checked");
						break;
					}
				}
			}

			sb.append(" >");
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(map.get(value));
			sb.append("</label>&nbsp;");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToOptions(String[] array) {
		return arrayToOptions(array, null);
	}

	public static String arrayToOptions(String[] array, Object checkedValue) {
		return arrayToOptions(array, checkedValue, false);
	}

	public static String arrayToOptions(String[] array, boolean addBlankOptionFlag) {
		return arrayToOptions(array, null, addBlankOptionFlag);
	}

	public static String arrayToOptions(String[] array, Object checkedValue, boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append("<span value=''></span>");
		}

		for (int i = 0; i < array.length; i++) {
			sb.append("<span value=\"");
			Object v = array[i];
			String value = (String) v;
			String[] arr = value.split(",");
			String name = value;
			if (arr.length > 1) {
				name = arr[0];
				value = arr[1];
			}
			sb.append(value);
			if (value != null && value.equals((String) checkedValue)) {
				sb.append("\" selected='true' >");
			} else {
				sb.append("\">");
			}
			sb.append(name);
			sb.append("</span>");
		}
		return sb.toString();
	}

	public static String queryToOptions(QueryBuilder qb) {
		return dataTableToOptions(qb.executeDataTable(), null);
	}

	public static String queryToOptions(QueryBuilder qb, Object checkedValue) {
		return dataTableToOptions(qb.executeDataTable(), checkedValue);
	}

	public static String queryToOptions(QueryBuilder qb, boolean addBlankOptionFlag) {
		return dataTableToOptions(qb.executeDataTable(), addBlankOptionFlag);
	}

	public static String queryToOptions(QueryBuilder qb, Object checkedValue, boolean addBlankOptionFlag) {
		return dataTableToOptions(qb.executeDataTable(), checkedValue, addBlankOptionFlag);
	}

	/**
	 * 把dt中的数据变成一组下拉框，没有默认选中的项
	 * 
	 * @param dt
	 * @return
	 */
	public static String dataTableToOptions(DataTable dt) {
		return dataTableToOptions(dt, null);
	}

	/**
	 * 把dt中的数据变成一组下拉框，并且初始化选中的项
	 * 
	 * @param dt
	 * @param checkedValue
	 * @return
	 */
	public static String dataTableToOptions(DataTable dt, Object checkedValue) {
		return dataTableToOptions(dt, checkedValue, false);
	}

	public static String dataTableToOptions(DataTable dt, boolean addBlankOptionFlag) {
		return dataTableToOptions(dt, null, addBlankOptionFlag);
	}

	public static String dataTableToOptions(DataTable dt, Object checkedValue, boolean addBlankOptionFlag) {
		StringBuffer sb = new StringBuffer();
		if (addBlankOptionFlag) {
			sb.append(SelectTag.getOptionHtml("", "", false));
		}
		if (dt == null) {
			return null;
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			String value = dt.getString(i, 1);
			if (value.equals(checkedValue)) {
				sb.append(SelectTag.getOptionHtml(dt.getString(i, 0), value, true));
			} else {
				sb.append(SelectTag.getOptionHtml(dt.getString(i, 0), value, false));
			}
		}
		return sb.toString();
	}

	/**
	 * 单选框
	 * 
	 * @param name
	 * @param dt
	 * @return
	 */
	public static String dataTableToRadios(String name, DataTable dt) {
		return dataTableToRadios(name, dt, null, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedValue
	 *            控制被选中的项
	 * @return
	 */
	public static String dataTableToRadios(String name, DataTable dt, String checkedValue) {
		return dataTableToRadios(name, dt, checkedValue, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param direction
	 *            控制是展现样式：false 水平，true 垂直
	 * @return
	 */
	public static String dataTableToRadios(String name, DataTable dt, boolean direction) {
		return dataTableToRadios(name, dt, null, direction);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedValue
	 * @return
	 */
	public static String dataTableToRadios(String name, DataTable dt, Object checkedValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < dt.getRowCount(); k++) {
			String value = dt.getString(k, 1);
			sb.append("<input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			if (value.equals(checkedValue)) {
				sb.append("' checked >");
			} else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(dt.getString(k, 0));
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToRadios(String name, String[] array) {
		return arrayToRadios(name, array, null, false);
	}

	public static String arrayToRadios(String name, String[] array, String checkedValue) {
		return arrayToRadios(name, array, checkedValue, false);
	}

	public static String arrayToRadios(String name, String[] array, boolean direction) {
		return arrayToRadios(name, array, null, direction);
	}

	public static String arrayToRadios(String name, String[] array, Object checkedValue, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < array.length; k++) {
			String value = array[k];
			sb.append("<input type='radio' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			if (value.equals(checkedValue)) {
				sb.append("' checked >");
			} else {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(value);
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	/**
	 * 水平复选框
	 * 
	 * @param name
	 * @param dt
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt) {
		return dataTableToCheckboxes(name, dt, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param direction
	 *            控制是展现样式：false 水平，true 垂直
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, boolean direction) {
		return dataTableToCheckboxes(name, dt, new String[0], direction);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedDT
	 *            控制被选中的项
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, DataTable checkedDT) {
		return dataTableToCheckboxes(name, dt, checkedDT, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedDT
	 *            控制被选中的项
	 * @param direction
	 *            控制是展现样式：false 水平，true 垂直
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, DataTable checkedDT, boolean direction) {
		String[] checkedArray = new String[checkedDT.getRowCount()];
		for (int i = 0; i < checkedDT.getRowCount(); i++) {
			checkedArray[i] = checkedDT.getString(i, 0);
		}
		return dataTableToCheckboxes(name, dt, checkedArray, direction);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedArray
	 *            控制被选中的项
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, String[] checkedArray) {
		return dataTableToCheckboxes(name, dt, checkedArray, false);
	}

	/**
	 * @param name
	 * @param dt
	 * @param checkedArray
	 *            控制被选中的项
	 * @param direction
	 *            控制是展现样式：false 水平，true 垂直
	 * @return
	 */
	public static String dataTableToCheckboxes(String name, DataTable dt, String[] checkedArray, boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < dt.getRowCount(); k++) {
			String value = dt.getString(k, 1);
			sb.append("<input type='checkbox' name='" + name);
			sb.append("' id='" + name + "_" + k + "' value='");
			sb.append(value);
			boolean flag = false;
			if (checkedArray != null) {
				for (int j = 0; j < checkedArray.length; j++) {
					if (value.equals(checkedArray[j])) {
						sb.append("' checked >");
						flag = true;
						break;
					}
				}
			}

			if (!flag) {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(dt.getString(k, 0));
			sb.append("</label>&nbsp;");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	public static String arrayToCheckboxes(String name, String[] array) {
		return arrayToCheckboxes(name, array, null, null, false);
	}

	public static String arrayToCheckboxes(String name, String[] array, String[] checkedArray) {
		return arrayToCheckboxes(name, array, checkedArray, null, false);
	}

	public static String arrayToCheckboxes(String name, String[] array, String onclick) {
		return arrayToCheckboxes(name, array, null, onclick, false);
	}

	public static String arrayToCheckboxes(String name, String[] array, String[] checkedArray, String onclick,
			boolean direction) {
		StringBuffer sb = new StringBuffer();
		for (int k = 0; k < array.length; k++) {
			String value = array[k];
			sb.append("<label><input type='checkbox'");
			if (StringUtil.isNotEmpty(onclick)) {
				sb.append("onclick='" + onclick + "'");
			}
			sb.append(" name='" + name);
			sb.append("' id='" + name + "_" + k + "'value='");
			sb.append(value);
			boolean flag = false;
			for (int j = 0; checkedArray != null && j < checkedArray.length; j++) {
				if (value.equals(checkedArray[j])) {
					sb.append("' checked >");
					flag = true;
					break;
				}
			}
			if (!flag) {
				sb.append("' >");
			}
			sb.append("<label for='" + name + "_" + k + "'>");
			sb.append(value);
			sb.append("</label>");
			if (direction) {
				sb.append("<br>");
			}
		}
		return sb.toString();
	}

	/**
	 * 将一段html中形如${Field}的占位符用map中对应的键值替换;<br>
	 * blankFlag为true时将未在map中有对应键值的占位符替换为空字符串,为false时不替换<br>
	 */
	public static String replacePlaceHolder(String html, HashMap map, boolean blankFlag) {
		return replacePlaceHolder(html, map, blankFlag, false);
	}

	/**
	 * 将一段html中形如${Field}的占位符用map中对应的键值替换;<br>
	 * blankFlag为true时将未在map中有对应键值的占位符替换为空字符串,为false时不替换<br>
	 * spaceFlag为true时将map中键值为null或者空字符串的替换为&nbsp;
	 */
	public static String replacePlaceHolder(String html, HashMap map, boolean blankFlag, boolean spaceFlag) {
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		map = new CaseIgnoreMapx(map);
		while (matcher.find(lastEndIndex)) {
			sb.append(html.substring(lastEndIndex, matcher.start()));
			String key = matcher.group(1).toLowerCase();
			if (map.containsKey(key)) {
				Object o = map.get(key);
				if (o == null || o.equals("")) {
					sb.append(blank);
				} else {
					sb.append(o);
				}
			} else if (blankFlag) {
				sb.append("");
			} else {
				sb.append(html.substring(matcher.start(), matcher.end()));
			}
			lastEndIndex = matcher.end();
		}
		sb.append(html.substring(lastEndIndex));
		return sb.toString();
	}

	/**
	 * 用DataTable中的行将一段HTML多次替换
	 */
	public static String replaceWithDataTable(DataTable dt, String html) {
		return replaceWithDataTable(dt, html, true);
	}

	/**
	 * 用DataTable中的行将一段HTML多次替换,spaceFlag为true表示当DataTable中未有对应列时替换成空格
	 */
	public static String replaceWithDataTable(DataTable dt, String html, boolean spaceFlag) {
		if (html == null || dt == null) {
			return "";
		}
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		ArrayList arr = new ArrayList();
		ArrayList key = new ArrayList();

		while (matcher.find(lastEndIndex)) {
			arr.add(html.substring(lastEndIndex, matcher.start()));
			String str = matcher.group(1);
			key.add(str);
			lastEndIndex = matcher.end();
		}
		arr.add(html.substring(lastEndIndex));

		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < arr.size(); j++) {
				sb.append(arr.get(j));
				if (j != key.size()) {
					String str = dt.getString(i, key.get(j).toString());
					if (StringUtil.isEmpty(str)) {
						sb.append(blank);
					} else {
						sb.append(str);
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 用DataRow将一段HTML替换,匹配形如${FieldName}的字符串
	 */
	public static String replaceWithDataRow(DataRow dr, String html) {
		return replaceWithDataRow(dr, html, true);
	}

	/**
	 * 用DataRow将一段HTML替换,spaceFlag为true表示当DataTable中未有对应列时替换成空格,匹配形如${FieldName}
	 * 的字符串
	 */
	public static String replaceWithDataRow(DataRow dr, String html, boolean spaceFlag) {
		if (html == null || dr == null) {
			return "";
		}
		Matcher matcher = Constant.PatternField.matcher(html);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		String blank = "";
		if (spaceFlag) {
			blank = "&nbsp;";
		}
		ArrayList arr = new ArrayList();
		ArrayList key = new ArrayList();

		while (matcher.find(lastEndIndex)) {
			arr.add(html.substring(lastEndIndex, matcher.start()));
			String str = matcher.group(1);
			key.add(str);
			lastEndIndex = matcher.end();
		}
		arr.add(html.substring(lastEndIndex));

		for (int j = 0; j < arr.size(); j++) {
			sb.append(arr.get(j));
			if (j != key.size()) {
				String str = dr.getString(key.get(j).toString());
				if (StringUtil.isEmpty(str)) {
					sb.append(blank);
				} else {
					sb.append(str);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将一个HTML表格转换成Excel文件，并将结果输出到指定流。<br>
	 * widths表示各列的宽，indexes表示要输出的列，rows表示要输出的行。 *
	 */
	public static void htmlTableToExcel(OutputStream os, HtmlTable table, String[] widths, String[] indexes,
			String[] rows) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("First");
		try {
			HSSFFont fontBold = wb.createFont();
			fontBold.setFontHeightInPoints((short) 10);
			fontBold.setFontName("宋体");
			fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			HSSFFont fontNormal = wb.createFont();
			fontNormal.setFontHeightInPoints((short) 10);
			fontNormal.setFontName("宋体");
			fontNormal.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

			HSSFCellStyle styleBorderBold = wb.createCellStyle();
			styleBorderBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleBorderBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleBorderBold.setWrapText(true);
			// styleBorderBold.setFillBackgroundColor((short)0);
			styleBorderBold.setFont(fontBold);

			HSSFCellStyle styleBorderNormal = wb.createCellStyle();
			styleBorderNormal.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleBorderNormal.setFont(fontNormal);

			HSSFCellStyle styleBold = wb.createCellStyle();
			styleBold.setFont(fontBold);

			HSSFCellStyle styleNormal = wb.createCellStyle();
			styleNormal.setFont(fontNormal);

			// 转换头部
			HSSFRow row = sheet.getRow(0);
			if (row == null) {
				row = sheet.createRow(0);
			}
			for (int i = 0; i < indexes.length; i++) {
				HSSFCell cell = row.getCell((short) i);
				if (cell == null) {
					cell = row.createCell((short) i);
				}
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(styleBorderBold);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

				String html = table.getTR(0).getTD(Integer.parseInt(indexes[i])).getInnerHTML();
				html = html.replaceAll("<.*?>", "");

				html = StringUtil.htmlDecode(html);
				cell.setCellValue(html.trim());
				row.setHeightInPoints(23);
				if (widths != null && widths.length > i) {
					double w = Double.parseDouble(widths[i]);
					if (w < 100) {
						w = 100;
					}
					sheet.setColumnWidth((short) i, (short) (w * 35.7));
				}
			}

			for (int i = 0; i < indexes.length; i++) {
				int j = Integer.parseInt(indexes[i]);
				if (rows != null) {
					for (int k = 0; k < rows.length; k++) {
						int n = Integer.parseInt(rows[k]);
						String ztype = table.getTR(n).getAttribute("ztype");
						if (k == table.getChildren().size() - 1) {
							if ((StringUtil.isNotEmpty(ztype) && ztype.equalsIgnoreCase("pagebar"))) {
								break;
							}
							String html = table.getTR(n).getInnerHTML();
							if (StringUtil.isEmpty(html) || html.indexOf("PageBarIndex") > 0) {
								break;
							}
						}


						row = sheet.getRow(k + 1);
						if (row == null) {
							row = sheet.createRow(k + 1);
							row.setHeightInPoints(18);
						}
						HSSFCell cell = row.getCell((short) i);
						if (cell == null) {
							cell = row.createCell((short) i);
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(styleBorderNormal);
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						String html = table.getTR(n).getTD(j).getOuterHtml();
						html = html.replaceAll("<.*?>", "");
						html = StringUtil.htmlDecode(html);
						cell.setCellValue(html.trim());
					}
				} else {
					for (int k = 1; k < table.getChildren().size(); k++) {
						String ztype = table.getTR(k).getAttribute("ztype");
						if (k == table.getChildren().size() - 1) {
							if ((StringUtil.isNotEmpty(ztype) && ztype.equalsIgnoreCase("pagebar"))) {
								break;
							}
							String html = table.getTR(k).getInnerHTML();
							if (StringUtil.isEmpty(html) || html.indexOf("PageBarIndex") > 0) {
								break;
							}
						}

						row = sheet.getRow(k);
						if (row == null) {
							row = sheet.createRow(k);
							row.setHeightInPoints(18);
						}
						HSSFCell cell = row.getCell((short) i);
						if (cell == null) {
							cell = row.createCell((short) i);
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(styleBorderNormal);
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						String html = "";
						if (table.getTR(k).getChildren().size() > j) {// 有可能单元格在JAVA中被合并了
							html = table.getTR(k).getTD(j).getOuterHtml();
							html = html.replaceAll("<.*?>", "");
							html = StringUtil.htmlDecode(html);
						}
						cell.setCellValue(html.trim());
					}
				}
			}
			wb.write(os);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	public static void GlobalHtmlTableToExcel(OutputStream os, HtmlTable table, String[] widths, String[] indexes,
			String[] rows,String[] titles) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("First");
		try {
			HSSFFont fontBold = wb.createFont();
			fontBold.setFontHeightInPoints((short) 10);
			fontBold.setFontName("宋体");
			fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

			HSSFFont fontNormal = wb.createFont();
			fontNormal.setFontHeightInPoints((short) 10);
			fontNormal.setFontName("宋体");
			fontNormal.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

			HSSFCellStyle styleBorderBold = wb.createCellStyle();
			styleBorderBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleBorderBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleBorderBold.setWrapText(true);
			// styleBorderBold.setFillBackgroundColor((short)0);
			styleBorderBold.setFont(fontBold);

			HSSFCellStyle styleBorderNormal = wb.createCellStyle();
			styleBorderNormal.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleBorderNormal.setFont(fontNormal);

			HSSFCellStyle styleBold = wb.createCellStyle();
			styleBold.setFont(fontBold);

			HSSFCellStyle styleNormal = wb.createCellStyle();
			styleNormal.setFont(fontNormal);

			// 转换头部
			HSSFRow row = sheet.getRow(0);
			if (row == null) {
				row = sheet.createRow(0);
			}
			for (int i = 0; i < indexes.length; i++) {
				HSSFCell cell = row.getCell((short) i);
				if (cell == null) {
					cell = row.createCell((short) i);
				}
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellStyle(styleBorderBold);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

				cell.setCellValue(titles[Integer.parseInt(indexes[i])-1]);
				row.setHeightInPoints(23);
				if (widths != null && widths.length > i) {
					double w = Double.parseDouble(widths[i]);
					if (w < 100) {
						w = 100;
					}
					sheet.setColumnWidth((short) i, (short) (w * 35.7));
				}
			}

			for (int i = 0; i < indexes.length; i++) {
				int j = Integer.parseInt(indexes[i]);
				if (rows != null) {
					for (int k = 0; k < rows.length; k++) {
						int n = Integer.parseInt(rows[k]);
						String ztype = table.getTR(n).getAttribute("ztype");
						if (k == table.getChildren().size() - 1) {
							if ((StringUtil.isNotEmpty(ztype) && ztype.equalsIgnoreCase("pagebar"))) {
								break;
							}
							String html = table.getTR(n).getInnerHTML();
							if (StringUtil.isEmpty(html) || html.indexOf("PageBarIndex") > 0) {
								break;
							}
						}


						row = sheet.getRow(k + 1);
						if (row == null) {
							row = sheet.createRow(k + 1);
							row.setHeightInPoints(18);
						}
						HSSFCell cell = row.getCell((short) i);
						if (cell == null) {
							cell = row.createCell((short) i);
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(styleBorderNormal);
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);

						String html = table.getTR(n).getTD(j).getOuterHtml();
						html = html.replaceAll("<.*?>", "");
						html = StringUtil.htmlDecode(html);
						cell.setCellValue(html.trim());
					}
				} else {
					for (int k = 1; k < table.getChildren().size(); k++) {
						String ztype = table.getTR(k).getAttribute("ztype");
						if (k == table.getChildren().size() - 1) {
							if ((StringUtil.isNotEmpty(ztype) && ztype.equalsIgnoreCase("pagebar"))) {
								break;
							}
							String html = table.getTR(k).getInnerHTML();
							if (StringUtil.isEmpty(html) || html.indexOf("PageBarIndex") > 0) {
								break;
							}
						}

						row = sheet.getRow(k);
						if (row == null) {
							row = sheet.createRow(k);
							row.setHeightInPoints(18);
						}
						HSSFCell cell = row.getCell((short) i);
						if (cell == null) {
							cell = row.createCell((short) i);
						}
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(styleBorderNormal);
						//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						String html = "";
						if (table.getTR(k).getChildren().size() > j) {// 有可能单元格在JAVA中被合并了
							html = table.getTR(k).getTD(j).getOuterHtml();
							html = html.replaceAll("<.*?>", "");
							html = StringUtil.htmlDecode(html);
						}
						cell.setCellValue(html.trim());
					}
				}
			}
			wb.write(os);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
