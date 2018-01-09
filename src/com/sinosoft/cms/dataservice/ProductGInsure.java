package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;


/**
 * @Author 何洋
 * @Date 2014-6-12
 * @购买核心类
 */
public class ProductGInsure extends Page {

	private int n = 0;// 被保人数
	private int amnt = 0;// 保额

	/**
	 * 产品信息初始化
	 * 
	 * @param params
	 *            页面参数 产品编码
	 * @return Mapx
	 */
	/**
	 * 目的地初始化
	 * 
	 * @param params
	 *            页面参数集合
	 * @return Mapx
	 */
	public static Mapx initDestination(Mapx params) {

		String comCode = params.getString("comCode");
		String NDestination = null;// 数据库储存信息
		String FDestination = null;// 页面目的地展示信息
		String productid = params.getString("productid");// 产品编码
		try {
			NDestination = java.net.URLDecoder.decode(params.getString("NDestination"), "UTF-8");
			FDestination = java.net.URLDecoder.decode(params.getString("FDestination"), "UTF-8");
			params.put("NDestination", NDestination);
			params.put("FDestination", FDestination);
			String contry = destinationSelect(productid, "", NDestination);
			params.put("destination", contry);
		} catch (Exception e) {
			logger.error("复选框目的地操作异常 或 数据字典 没有配置 保险公司：" + comCode + e.getMessage(), e);
			return null;
		}
		;
		return params;
	}

	/**
	 * @Title: destinationSelect
	 * @Description: TODO(旅游目的地HTML)
	 * @param p_ProductID
	 * @param p_Type
	 *            1.单选 2.多选
	 * @return
	 * @author CongZN
	 */
	public static String destinationSelect(String p_ProductID, String p_Type, String p_SelectDestination) {

		StringBuffer sb = new StringBuffer();

		String comCode = p_ProductID.substring(0, 4);

		String sql = "select * from dictionary where productId like '%" + p_ProductID + "%' and codetype='CountryCode'";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();

		if ("1".equals(p_Type)) {
			sb.append("<select class=\"bx_select_2\" id=\"destination\" name=\"destination\">");
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "codeName").equals(p_SelectDestination.trim())) {
					sb.append("	<option selected=\"selected\" value=\"" + dt.getString(i, "codeValue") + "\">");
					sb.append(dt.getString(i, "codeName"));
					sb.append("	</option>");
				} else {
					sb.append("	<option value=\"" + dt.getString(i, "codeValue") + "\">");
					sb.append(dt.getString(i, "codeName"));
					sb.append("	</option>");
				}
			}
			sb.append("</select>");
			return sb.toString();
		}

		if (dt == null || dt.getRowCount() <= 0) {
			sql = "select * from dictionary where insurancecode=? and codetype='countrycode' and (productId is null or productId = '')";
			qb = new QueryBuilder(sql, comCode);
			dt = qb.executeDataTable();
		}

		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("Schengen".equals(dt.getString(i, "codeEnName"))) {
				dt.insertRow(dt.get(i), 0);
				dt.deleteRow(i + 1);
			}
		}
		int i = 0;
		int j = 0;
		a: while (i < dt.getRowCount()) {
			sb.append("<tr style=\"background-color:#F7FFFF\">");
			if (i < dt.getRowCount()) {
				while (true) {
					if (dt.getRowCount() == j) {
						i = j;
						continue a;
					}
					sb.append("<td>&nbsp; &nbsp;<label><input name=\"Destinations\" type=\"checkbox\" onclick=\"getChecks(this);\"value=\"");
					sb.append(dt.getString(j, "codeEnName"));
					sb.append("\"");
					if ("Y".equals(dt.getString(j, "flagType"))) {
						if ("Schengen".equals(dt.getString(j, "codeEnName"))) {
							sb.append(" class=\"css_Country_0S\"");
						} else {
							sb.append(" class=\"css_Country_1\"");
						}
					} else {
						sb.append(" class=\"css_Country_\"");
					}
					sb.append("/>");
					sb.append(dt.getString(j, "codeName"));
					sb.append("</label> </td>");
					j++;
					if (j % 8 == 0 && j != 0) {
						sb.append("</tr>");
						i += 8;
						continue a;
					}
				}
			}
		}

		// int rowCount = 8;
		//
		// int sumCount = dt.getRowCount();
		//
		// int sendNumber = (dt.getRowCount() + rowCount - 1) / rowCount;
		//
		// for(int i = 0; i < sendNumber; i++){
		//
		// int j = i * rowCount;
		//
		// int tempSumCount = 0;
		//
		// sb.append("<tr style=\"background-color:#F7FFFF\">");
		//
		// if(rowCount * (i+1) >= sumCount){
		// tempSumCount = sumCount;
		// }else{
		// tempSumCount = rowCount * (i + 1);
		// }
		//
		// for(int k = j; k < tempSumCount; k++){
		// sb.append("<td>&nbsp; &nbsp;<label><input name=\"Destinations\" type=\"checkbox\" onclick=\"getChecks(this);\"value=");
		// sb.append(dt.getString(k, "codeEnName"));
		// sb.append("/>");
		// sb.append(dt.getString(k, "codeName"));
		// sb.append("</label> </td>");
		// }
		// }
		return sb.toString();
	}

}
