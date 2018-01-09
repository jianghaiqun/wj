package com.sinosoft.wap;

import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;

public class WdProductManage extends Page {

	/**
	 * 元素数据查询
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {

		StringBuffer sql = new StringBuffer(
				"SELECT a.productId  id,a.ProductName ,a.IsPublish  ,a.isHot,a.isTop,a.isSelected"
						+ " FROM   wdproductlist  a");
		String productName = (String) dga.getParams().get("productName");
		if (StringUtil.isNotEmpty(productName)) {
			sql.append(" where a.productName like '%" + productName + "%'");
		}
		sql.append(" ORDER BY a.modifyDate DESC");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);
		dga.bindData(dt);

	}

	/**
	 * 添加显示列表页 dg2DataBindProduct:(这里用一句话描述这个方法的作用). <br/>
	 *
	 * @author chouweigao
	 * @param dga
	 */
	public static void dg2DataBindProduct(DataGridAction dga) {

		String productcode = (String) dga.getParams().get("productcode");
		String productname = (String) dga.getParams().get("productname");
		QueryBuilder qb_product = new QueryBuilder(
				"SELECT a.productID id,a.ProductName FROM sdsearchrelaproduct e,sdproduct a LEFT JOIN productrelainfo b ON (a.productid = b.productid) WHERE a.productid = e.productid AND a.Ispublish = 'Y' AND b.Ispublish = 'Y' AND a.producttype IN ('A','B','C','D','E') AND NOT EXISTS ( SELECT 1 FROM zdconfig WHERE TYPE = 'questionPaper' AND VALUE=a.productid) AND a.productID NOT IN (SELECT productId FROM wdproductlist) ");
		if (StringUtil.isNotEmpty(productcode)) {
			qb_product.append(" AND a.productID like '"+ productcode.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(productname)) {
			qb_product.append(" AND a.ProductName like '"+ productname.trim() + "%'");
		}
		qb_product.append(" ORDER BY a.productID  ");
		qb_product.append(dga.getSortString());
		dga.setTotal(qb_product);
		DataTable dt = qb_product.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 添加产品
	 */
	public void upProduct() {

		try {
			String ids = $V("ids");
			String names = $V("names");
			// DataTable dt = (DataTable) Request.get("Data");
			System.err.println("*****************ids is " + ids);
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

			String[] arr = ids.split(",");
			String[] arr1 = names.split(",");
			Transaction trans = new Transaction();
			for (int i = 0; i < arr.length; i++) {
				String cProductId = arr[i];
				String name = arr1[i];

				String sql = " SELECT 1 FROM wdproductlist WHERE productid='" + cProductId + "'";
				QueryBuilder qb = new QueryBuilder(sql);
				int selrow = qb.executeInt();
				String now = "now()";
				if (selrow == 0) {
					sql = " INSERT INTO wdproductlist (id,productId,productName,isPublish,isHot,isTop,isSelected,createUser,createDate,modifyDate,prop1,prop2,prop3,prop4,prop5) "+
							"VALUES('"+CommonUtil.getUUID()+"','"+cProductId+"','"+name+"','Y','','','','"+User.getUserName()+"', "+now+", "+now+",'','','','','')";
					qb = new QueryBuilder(sql);
					trans.add(qb);
				}
			}
			boolean flag = trans.commit();

			if (flag) {
				Response.setStatus(1);
				String message = "产品添加成功!";
				Response.setMessage(message);
			} else {
				Response.setStatus(0);
				Response.setMessage("产品添加失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("产品添加失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 删除产品
	 */
	public void downProduct() {

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

			String[] arr = ids.split(",");
			Transaction trans = new Transaction();
			for (int i = 0; i < arr.length; i++) {
				String cProductId = arr[i];
				String sql = " delete from  wdproductlist WHERE productid = '" + cProductId + "'";
				QueryBuilder qb = new QueryBuilder(sql);
				trans.add(qb);
			}
			boolean flag = trans.commit();

			if (flag) {
				Response.setStatus(1);
				String message = "产品删除成功!";
				Response.setMessage(message);
			} else {
				Response.setStatus(0);
				Response.setMessage("产品删除失败!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("删除失败! 异常原因：" + e.getMessage());
		}
	}

	public static void main(String[] args) {

		// String sql =
		// " SELECT BackUp2,RiskCode FROM femrisk  WHERE RiskCode = '1111111111';";
		// QueryBuilder qb = new QueryBuilder(sql);
		// Object value = qb.executeOneValue();
		//
		// System.out.println("**************************value is "+value);

		// Mapx mapx = new Mapx();
		// mapx.put("productId", 101501009);
		// System.err.println(initLimitProduct(mapx));

		System.err.println(true && false);

	}

}