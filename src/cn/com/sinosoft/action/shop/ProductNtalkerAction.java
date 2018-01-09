/**
 * Project Name:wj_sstry
 * File Name:ProductDetailNtalkerAction.java
 * Package Name:cn.com.sinosoft.action.shop
 * Date:2016年9月18日上午8:59:16
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package cn.com.sinosoft.action.shop;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:ProductDetailNtalkerAction <br/>
 * Function:TODO 描述类是干什么的. <br/>
 * Date:2016年9月18日 上午8:59:16 <br/>
 *
 * @author:"liang.sl"
 */
@SuppressWarnings("serial")
@ParentPackage("shop")
public class ProductNtalkerAction extends BaseShopAction {

	private String itemid;

	public String ntalker() {

		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		try {// 防止网页sql查询注入
			Integer.parseInt(itemid);
		} catch (NumberFormatException e) {
			result.put("massage", "请录入正确的产品编号格式！");
			return ajaxMap2Json(result);
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.productId, a.productName ,b.logo ,a.htmlPath ,a.remark4,"
				+ "b.InitPrem,b.BasePremValue,a.productGenera,a.InsureName ");
		sql.append("FROM sdproduct a,sdsearchrelaproduct b  ");
		sql.append("WHERE a.productId=b.productId AND a.productId='" + itemid
				+ "'");
		sql.append("order by a.productId desc ");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executeDataTable();
		if (dt == null || dt.getRowCount() <= 0) {
			result.put("massage", "产品编号录入不正确！查找不到产品信息");
			return ajaxMap2Json(result);
		} else {
			String imageurl = Config.getValue("StaticResourcePath")
					+ (String) dt.get(0).get("logo");
			jsonResult.put("id", dt.get(0).get("productId"));// 商品ID
			jsonResult.put("name", (String) dt.get(0).get("productName"));// 商品名称
			jsonResult.put("imageurl", imageurl); // 商品图片地址(全路径)
			jsonResult.put("url", (String) dt.get(0).get("htmlPath"));// 商品详情页地址
			jsonResult.put("currency", "￥");// 货币符号
			jsonResult.put("siteprice", (String) dt.get(0).get("InitPrem"));// 商品网站价格(和currency合并显示，显示效果￥：180.00)
			jsonResult.put("marketprice",(String) dt.get(0).get("BasePremValue"));// 商品市场价格
			jsonResult.put("category", (String) dt.get(0).get("productGenera"));// 商品分类名称
			jsonResult.put("brand", (String) dt.get(0).get("InsureName"));// 商品品牌名称
			// jsonResult.put("custom1", (String) dt.get(0).get(""));//自定义字段1
			// jsonResult.put("custom2", (String) dt.get(0).get(""));//自定义字段2
			result.put("item", jsonResult);
			return ajaxMap2Json(result);
		}

	}

	public String getItemid() {

		return itemid;
	}

	public void setItemid(String itemid) {

		this.itemid = itemid;
	}

}
