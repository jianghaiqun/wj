/**
 * 
 */
package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangcaiyun
 *
 */
public class OrderAutoInsure extends Page {

	public static Mapx<String, String> initDialog(Mapx<String, String> params) {
		OrderImport.initDialog(params);
		// 产品ID
		String productID = params.getString("productID");
		if (StringUtil.isEmpty(productID)) {
			return params;
		}
		//获取产品名称和产品类型,保险公司编号
		String querySql = "select ProductName,ProductType,Remark6 from sdproduct where ProductID = ?";
		DataTable dt = new QueryBuilder(querySql, productID).executeDataTable();
		String productType = null;
		String productName = null;
		String comCode = null;
		if (dt != null && dt.getRowCount() > 0) {
			productName = dt.getString(0, 0);
			productType = dt.getString(0, 1);
			comCode = dt.getString(0, 2);
		} else {
			return params;
		}
		params.put("productName", productName);

		// 取得产品导入模版
		if ("B".equals(productType)) {
			params.put("templateXls", "traveltemp1.xls");
			params.put("disStyle", "");

			// 装载职业一级下拉框数值
			Map<String, String> map = new HashMap<String, String>();
			querySql = "select id,name from Occupation o where o.insuranceCompany= ? and (productId = ?) and" +
					" (o.parent_id is null or o.parent_id = '') order by id asc";
			dt = new QueryBuilder(querySql, comCode ,productID).executeDataTable();
			if (dt == null || dt.getRowCount() == 0) {
				querySql = "select id,name from Occupation o where o.insuranceCompany= ? " +
						"and (productId is null or productId = '') and (o.parent_id is null or o.parent_id = '') order by id asc";
				dt = new QueryBuilder(querySql, comCode).executeDataTable();
			}

			if (dt != null && dt.getRowCount() > 0) {
				for (int i = 0; i < dt.getRowCount(); i++) {
					map.put(dt.getString(i,"id"),dt.getString(i,"name"));
				}
			}
			params.put("occupationLevelOne", HtmlUtil.mapxToOptions(map,true).replaceAll("span","option"));
		} else {
			querySql = "select b.templateURL from producttempinfo p, sdproduct a, productExcelTemp b where p.ProductId=a.ProductID and b.Id = p.ExcelTemplate and a.ProductID=? ";
			dt = new QueryBuilder(querySql, productID).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				params.put("templateXls", dt.getString(0, 0));
			}
			params.put("disStyle", "none");
		}
		// 取得证件类型
		OrderAutoInsure OrderAutoInsure = new OrderAutoInsure();
		params.put("IdentityType", HtmlUtil.mapxToOptions(OrderAutoInsure
				.getDictionaryInfo("certificate", productID)));
		// 取得性别
		params.put("Sex", HtmlUtil.mapxToOptions(OrderAutoInsure
				.getDictionaryInfo("Sex", productID)));

		Map<String, String> selectFlagMap = new HashMap<String, String>();
		selectFlagMap.put("Y", "Y");
		selectFlagMap.put("N", "N");
		params.put("healthyFlag",
				HtmlUtil.mapxToOptions(selectFlagMap, null, false));
		
		params.put("YesOrNo",HtmlUtil.codeToOptions("YesOrNo", "N", false));

		params.put("channelSn", HtmlUtil.queryToOptions(new QueryBuilder(
				"SELECT ChannelName,ChannelCode FROM channelinfo"), false));
		return params;
	}

	public Mapx getDictionaryInfo(String codeType, String productId) {
		String querySql = "SELECT codeValue, codeName FROM dictionary WHERE codeType=? ";
		Mapx map = new QueryBuilder(querySql + " AND productId=?", codeType,
				productId).executeDataTable().toMapx("codeValue", "codeName");
		if (map == null || map.isEmpty()) {
			map = new QueryBuilder(querySql + " AND insuranceCode=?", codeType,
					productId.substring(0, 4)).executeDataTable().toMapx(
					"codeValue", "codeName");
		}

		return map;
	}

	public void healthInquery(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"SELECT Id as healthid,showinfo FROM HealthyInfo where productId=?",
				dga.getParams().getString("productID"));
		DataTable dt = qb.executeDataTable();
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 得到产品信息
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {

		// 得到查询SQL
		String querySql = "SELECT (SELECT codename FROM zdcode WHERE codetype='SupplierCode' AND codevalue=a.Remark6) supplierName," +
				"a.ProductName, a.ProductID,a.Remark6 FROM producttempinfo p, sdproduct a WHERE p.ProductId=a.ProductID AND " +
				"( EXISTS(SELECT 1 FROM productexceltemp b WHERE b.Id = p.ExcelTemplate) " +
				"OR a.producttype='B' )" +
				"AND a.complicatedFlag!='Y'";

		// 查询条件
		StringBuffer wherePart = new StringBuffer();
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			wherePart.append(" and a.ProductName like '%" + productName.trim()
					+ "%'");
		}
		// 产品编码
		String productCode = dga.getParams().getString("productCode");
		if (StringUtil.isNotEmpty(productCode)) {
			wherePart.append(" and a.ProductID like '%" + productCode.trim()
					+ "%'");
		}

		// 保险公司
		String supplierCode = dga.getParams().getString("supplierCode");
		if (StringUtil.isNotEmpty(supplierCode)) {
			wherePart.append(" and a.Remark6 = '" + supplierCode.trim() + "'");
		}

		if (StringUtil.isNotEmpty(wherePart.toString())) {
			querySql += wherePart.toString();
		}
		querySql += " order by a.ProductID asc ";
		// 取得结果
		QueryBuilder qb = new QueryBuilder(querySql);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);
	}

}
