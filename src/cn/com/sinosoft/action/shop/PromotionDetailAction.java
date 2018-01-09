/**
 * 
 */
package cn.com.sinosoft.action.shop;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 */
@ParentPackage("shop")
public class PromotionDetailAction extends BaseShopAction {
	/**
	 * 倒计时产品
	 */
	private List<Map<String,String>> SpecialProductList;
	/**
	 * 模块列表
	 */
	private List<Map<String,Object>> ModelList;
	/**
	 * 热卖品牌
	 */
	private List<Map<String,String>> SellingBrandList;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3822010172716941758L;
	private String siteName;
	/**
	 * 
	 * @Title: index
	 * @Description: TODO(促销频道首页)
	 * @return String 返回类型
	 * @author
	 */
	@InputConfig(resultName = "error")
	public String index() {
		//倒计时产品
		DataTable  dt_SpecialProduct=new QueryBuilder("SELECT * FROM  SDPromotionHomePageSpecialProduct WHERE endtime>=NOW() LIMIT 1 ").executeDataTable();
		Map<String,String> map=new HashMap<String,String>();
		for (int i = 0; i < dt_SpecialProduct.getRowCount(); i++) {
			for (int j = 0; j < dt_SpecialProduct.getColCount(); j++) {
				String ColumnName=dt_SpecialProduct.getColumnName(j);
				String value=dt_SpecialProduct.getString(i, ColumnName);
				map.put(ColumnName, value);
			}
		}
		if(map.size()>0){
			SpecialProductList=new ArrayList<Map<String,String>>();
			SpecialProductList.add(map);
		}
		//热卖品牌
		DataTable  dt_SellingBrand=new QueryBuilder("SELECT * FROM  SDPromotionHomePageSellingBrand WHERE isShow='Y'  order by OrderFlag  ").executeDataTable();
		SellingBrandList=new ArrayList<Map<String,String>>();
		for (int i = 0; i < dt_SellingBrand.getRowCount(); i++) {
			Map<String,String> map_SellingBrand=new HashMap<String,String>();
			for (int j = 0; j < dt_SellingBrand.getColCount(); j++) {
				String ColumnName=dt_SellingBrand.getColumnName(j);
				String value=dt_SellingBrand.getString(i, ColumnName);
				map_SellingBrand.put(ColumnName, value);
			}
			
			SellingBrandList.add(map_SellingBrand);
		}
		//模块产品
		DataTable  dt_Module=new QueryBuilder("SELECT * FROM  SDPromotionHomePageModule WHERE isShow='Y' order by OrderFlag  ").executeDataTable();
		ModelList=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < dt_Module.getRowCount(); i++) {
			Map<String,Object> map_Module=new HashMap<String,Object>();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			for (int j = 0; j < dt_Module.getColCount(); j++) {
				String ColumnName=dt_Module.getColumnName(j);
				String value=dt_Module.getString(i, ColumnName);
				map_Module.put(ColumnName, value);
				if("Id".equals(ColumnName)){
					DataTable  dt_Activity=new QueryBuilder("SELECT * FROM  SDPromotionHomePageActivity WHERE isShow='Y' and endtime>=NOW()  and ModuleID=? order by OrderFlag  ",value).executeDataTable();
					for (int k = 0; k < dt_Activity.getRowCount(); k++) {
						Map<String,Object> map_Activity=new HashMap<String,Object>();
						for (int m = 0; m < dt_Activity.getColCount(); m++) {
							String ColumnName_Activity=dt_Activity.getColumnName(m);
							String value_Activity=dt_Activity.getString(k, ColumnName_Activity);
							map_Activity.put(ColumnName_Activity, value_Activity);
						}
						list.add(map_Activity);
					}
				}
			}
			if(list.size()>0){
				map_Module.put("ActivityList", list);
			}
			//模块下无活动信息，不显示模块信息
			if(map_Module.get("ActivityList")==null||("".equals(map_Module.get("ActivityList")))){
				continue;
			}
			ModelList.add(map_Module);
		}
		siteName = new QueryBuilder("SELECT Meta_Keywords FROM zcsite").executeString();
		return "index";
	}
	public Mapx<String, String> promotionInfo (String id) {
		if (StringUtil.isNotEmpty(id)) {
			Mapx<String, String> result = new Mapx<String, String>();
			StringBuffer sb = new StringBuffer();
			sb.append("select a.Id, a.ModuleName, a.ModuleType, a.ModuleColor, a.ModuleTheme, b.ProductName, ");
			sb.append("a.ModuleNameColor, a.MoreUrl, a.MoreColor, b.ProductId, b.BuyURL, b.Id as detailId ");
			sb.append("from sdPromotionDetailModule a, SDPromotionDetailProduct b, sdproduct c ");
			sb.append("where a.DocumentId = ? and a.Id = b.ModuleId and b.ProductID = c.ProductID ");
			sb.append("and c.IsPublish = 'Y' order by a.OrderFlag asc, b.OrderFlag asc ");
			sb.append("");
			sb.append("");
			sb.append("");
			DataTable dt = new QueryBuilder(sb.toString(), id).executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				// 产品所属模块类型的产品记录  各模块类型的产品记录
				Map<String, String> proModuType = new HashMap<String, String>();
				Map<String, String> proMap = new HashMap<String, String>();
				Map<String, String> moduTypeMap = new HashMap<String, String>();
				
				int rowCount = dt.getRowCount();
				String detailIds = "";
				String moduleType = "";
				String productId = "";
				String moduleId = "";
				String productIds = "";
				for (int i = 0; i < rowCount; i++) {
					moduleType = dt.getString(i, "ModuleType");
					productId = dt.getString(i, "ProductId");
					moduleId = dt.getString(i, "Id");
					if (proModuType.containsKey(moduleId)) {
						proModuType.put(moduleId, proModuType.get(moduleId) + ",'" + productId + "'");
					} else {
						proModuType.put(moduleId, "'" + productId + "'");
					}
					proMap.put(dt.getString(i, "detailId"), productId);
					moduTypeMap.put(dt.getString(i, "detailId"), moduleType);
					detailIds += ",'" + dt.getString(i, "detailId") +"'";
					productIds += ",'" + productId +"'";
				}
				
				// 取得产品亮点
				detailIds = detailIds.substring(1);
				Map<String, String> prolightMap = getLightInfo(detailIds, moduTypeMap);
				
				// 取得产品信息-原价、折扣价、销量、图片
				productIds = productIds.substring(1);
				Map<String, Map<String, String>> articleInfo = getArticleInfo(productIds);
				if (articleInfo == null) {
					return null;
				}
				
				// 显示产品图片模块的产品展示形式
				StringBuffer proSb = new StringBuffer();
				// 产品名展示
				proSb.append("<a class=\"ct_sales_st f_mi\" href=\"BuyURL\" target=\"_blank\">ProductName</a><div class=\"clear\"></div>");
				// 活动图标
				proSb.append("<div class=\"ct_sales_cimg\"><span class=\"ct_sales_hd f-ib f_mi\" id=\"Activity_ProductId\" style=\"display:none;\">productActive<em class=\"ct_sales_em\"></em></span>");
				// 产品图片
				proSb.append("<a href=\"BuyURL\" target=\"_blank\"><img src=\"logoLink\" alt=\"ProductName\"></a>");
				// 销量
				proSb.append("<span class=\"ct_sales_numf\">salesVolume人已购买</span>");
				// 亮点
				proSb.append("</div><div class=\"ct_sales_ul\">prolight<div class=\"jg_cons ct_sales_jg\">");
				// 价格显示
				proSb.append("<span class=\"sales_xj f_mi\" name=\"Ajax_Prict_ProductId\"><i>￥</i>basePremValue</span><span class=\"salaes_yj f_mi\" name=\"Clear_Ajax_Prict_ProductId\">basePrem</span></div>");
				// 购买按钮
				proSb.append("<a href=\"BuyURL\" class=\"sales_shopc f_mi\" target=\"_blank\">立即购买</a></div><div class=\"clear h30\"></div>");
				
				// 显示保险公司图片模块的产品展示形式
				StringBuffer comSb = new StringBuffer();
				// 保险公司图片展示
				comSb.append("<div class=\"icon_CsupplierCode cp_logos2\"><a target=\"_blank\" class=\"cp_logo_a\" href=\"BuyURL\"></a></div>");
				// 产品名展示
				comSb.append("<div class=\"min_title_a f_mi\"><a target=\"_blank\" href=\"BuyURL\">ProductName</a>");
				// 销量
				//comSb.append("<span class=\"min_title_num f-ib\" id=\"SalesV_ProductId\">salesVolume人已购买</span>");
				// 亮点
				comSb.append("</div><div class=\"clear h20\"></div>prolight<div class=\"min_payh_conf\">");
				// 价格显示
				comSb.append("<div class=\"salaes_height\"><span class=\"salaes_yj f_mi\" name=\"Clear_Ajax_Prict_ProductId\">basePrem</span></div><span class=\"sales_xj f_mi\" name=\"Ajax_Prict_ProductId\"><i>￥</i>basePremValue</span>");
				// 购买按钮
				comSb.append("<a target=\"_blank\" class=\"sales_shopc f_mi\" href=\"BuyURL\">立即购买</a></div><div class=\"clear h20\"></div>");
				
				// 两个单产品模块是否相临
				boolean flag = false;
				moduleId= dt.getString(0, 0);
				moduleType = dt.getString(0, "ModuleType");
				String moduleHtml = "";
				if ("01".equals(moduleType) || "03".equals(moduleType)) {
					moduleHtml = "<div style=\"background:" + dt.getString(0, "ModuleColor") + "\" class=\"ct_sales_dom\">";
				} else {
					moduleHtml = "<div style=\"background:" + dt.getString(0, "ModuleColor") + "\" class=\"ct_sales_dom2 fleft\">";
					flag = true;
				}
				moduleHtml += "<h2 class=\"ct_sales_bt f_mi "+dt.getString(0, "ModuleTheme")+"\" style=\"color:"+dt.getString(0, "ModuleNameColor")+"\">" + dt.getString(0, "ModuleName");
				if (StringUtil.isNotEmpty(dt.getString(0, "MoreUrl"))) {
					moduleHtml += "<a href=\""+dt.getString(0, "MoreUrl")+"\" style=\"color:"+dt.getString(0, "MoreColor")+";\">更多></a>";
				}
				moduleHtml += "</h2>";
				Map<String, String> map;
				String basePrem = "";
				String basePremValue = "";
				String productActive = "";
				String salesVolume = "";
				String logoLink = "";
				String supplierCode = "";
				
				
				String proHtml = "";
				for (int i = 0; i < rowCount; i++) {
					map = articleInfo.get(dt.getString(i, "ProductId"));
					if (map != null && !map.isEmpty()) {
						basePrem = map.get("BasePrem")==null?"":map.get("BasePrem");
						basePremValue = map.get("BasePremValue")==null?"":map.get("BasePremValue");
						productActive = map.get("ProductActive")==null?"":map.get("ProductActive");
						salesVolume = map.get("SalesVolume")==null?"":map.get("SalesVolume");
						logoLink = map.get("LogoLink")==null?"":map.get("LogoLink");
						supplierCode = map.get("SupplierCode2")==null?"":map.get("SupplierCode2");
						if (StringUtil.isEmpty(salesVolume)) {
							salesVolume = "0";
						}
					} else {
						basePrem = "";
						basePremValue = "";
						productActive = "";
						salesVolume = "0";
						logoLink = "";
						supplierCode = dt.getString(0, "ProductId").substring(0, 4);
					}
					if (basePrem.equals(basePremValue)) {
						basePrem = "";
					} else {
						basePrem = "￥" + basePrem;
					}
					if (!moduleId.equals(dt.getString(i, 0))) {
						moduleHtml += "<div class=\"clear\"/></div></div>";
						result.put(moduleId, moduleHtml);
						moduleHtml = "";
						moduleId= dt.getString(i, 0);
						moduleType = dt.getString(i, "ModuleType");
						if ("01".equals(moduleType) || "03".equals(moduleType)) {
							moduleHtml += "<div class=\"clear h20\"></div><div style=\"background:" + dt.getString(i, "ModuleColor") + "\" class=\"ct_sales_dom\">";
							flag = false;
						} else {
							if (flag && moduleType.equals(dt.getString(i-1, "ModuleType"))) {
								moduleHtml += "<div style=\"background:" + dt.getString(i, "ModuleColor") + "\" class=\"ct_sales_dom2 fright\">";
								flag = false;
							} else {
								moduleHtml += "<div class=\"clear h20\"></div><div style=\"background:" + dt.getString(i, "ModuleColor") + "\" class=\"ct_sales_dom2 fleft\">";
								flag = true;
							}
							
						}
						moduleHtml += "<h2 class=\"ct_sales_bt f_mi "+dt.getString(i, "ModuleTheme")+"\" style=\"color:"+dt.getString(i, "ModuleNameColor")+"\">" + dt.getString(i, "ModuleName");
						if (StringUtil.isNotEmpty(dt.getString(i, "MoreUrl"))) {
							moduleHtml += "<a href=\""+dt.getString(i, "MoreUrl")+"\" style=\"color:"+dt.getString(i, "MoreColor")+";\">更多></a>";
						}
						moduleHtml += "</h2>";
					}
					// 产品图片-多产品
					if ("01".equals(moduleType)) {
						moduleHtml += "<div class=\"ct_sales_shop\">";
						proHtml = proSb.toString();
//						moduleHtml += "<div class=\"ct_sales_shop\"><a class=\"ct_sales_st f_mi\" href=\""+dt.getString(i, "BuyURL")+"\" target=\"_blank\">"+dt.getString(i, "ProductName")+"</a>";
//						moduleHtml += "<div class=\"clear\"></div><div class=\"ct_sales_cimg\"><span class=\"ct_sales_hd f-ib f_mi\">"+productActive+"<em class=\"ct_sales_em\"></em></span>";
//						moduleHtml += "<a href=\""+dt.getString(i, "BuyURL")+"\" target=\"_blank\"><img src=\""+logoLink+"\" alt=\""+dt.getString(i, "ProductName")+"\"></a><span class=\"ct_sales_numf\">"+salesVolume+"人已购买</span></div>";
//						moduleHtml += "<div class=\"ct_sales_ul\">"+prolightMap.get(dt.getString(i, "detailId"))+"<div class=\"jg_cons ct_sales_jg\">";
//						moduleHtml += "<span class=\"sales_xj f_mi\"><i>￥</i>"+basePrem+"</span><span class=\"salaes_yj f_mi\">"+basePremValue+"</span></div>";
//						moduleHtml += "<a href=\""+dt.getString(i, "BuyURL")+"\" class=\"sales_shopc f_mi\" target=\"_blank\">立即购买</a></div><div class=\"clear h30\"></div></div>";
//						
						// 产品图片-单产品
					} else if ("02".equals(moduleType)) {
						moduleHtml += "<div class=\"ct_sales_shop ct_sales_shop2\">";
						proHtml = proSb.toString();
						
						// 保险公司图片-多产品
					} else if ("03".equals(moduleType)) {
						moduleHtml += "<div class=\"ct_sales_shop\">";
						proHtml = comSb.toString();
						
						// 保险公司图片-单产品
					} else if ("04".equals(moduleType)) {
						moduleHtml += "<div class=\"ct_sales_bofs\"><div class=\"min_ct_sales\">";
						proHtml = comSb.toString();
					}
					
					proHtml = proHtml
							.replaceAll("BuyURL", dt.getString(i, "BuyURL"))
							.replaceAll("ProductName",
									dt.getString(i, "ProductName"))
							.replace("productActive", productActive)
							.replaceAll("ProductId", dt.getString(i, "ProductId"))
							.replace("salesVolume", salesVolume)
							.replace(
									"prolight",
									prolightMap.get(dt.getString(i, "detailId")) == null ? ""
											: prolightMap.get(dt.getString(i,
													"detailId")))
							.replace("logoLink", logoLink)
							.replace("basePremValue", basePremValue)
							.replace("basePrem", basePrem)
							.replace("supplierCode", supplierCode);
					if (StringUtil.isNotEmpty(productActive)) {
						proHtml = proHtml.replace("style=\"display:none;\"", "");
					}
					moduleHtml += proHtml;
					
					if ("04".equals(moduleType)) {
						moduleHtml += "</div>";
					}
					moduleHtml += "</div>";
				}
				moduleHtml += "<div class=\"clear\"/></div></div>";
				result.put(moduleId, moduleHtml);
				return result;
			}
		}
		
		return null;
	}
	
	private Map<String, Map<String, String>> getArticleInfo(String productIds) {
		Map<String, Map<String, String>> articleInfo = new HashMap<String, Map<String, String>>();
		String articleSql = "select a.prop4,b.ColumnCode,b.TextValue from zcarticle a, zdcolumnvalue b where a.CatalogInnerCode LIKE '002313%' AND a.type='1' AND a.status='30' and a.prop4 in ("+productIds+") and a.id=b.relaId and b.ColumnCode in ('BasePrem','BasePremValue','ProductActive','SalesVolume','LogoLink','SupplierCode2') order by a.prop4 asc";
		DataTable artiDt = new QueryBuilder(articleSql)
				.executeDataTable();
		if (artiDt != null && artiDt.getRowCount() > 0) {
			String productId = artiDt.getString(0, 0);
			Map<String, String> map = new HashMap<String, String>();
			int count = artiDt.getRowCount();
			for (int i = 0; i < count; i++) {
				if (!productId.equals(artiDt.getString(i, 0))) {
					articleInfo.put(productId, map);
					productId = artiDt.getString(i, 0);
					map = new HashMap<String, String>();
				}

				if (StringUtil.isNotEmpty(artiDt.getString(i, 2))) {
					if ("ProductActive".equals(artiDt.getString(i, 1))) {
						map.put(artiDt.getString(i, 1),
								artiDt.getString(i, 2)
										.substring(
												artiDt.getString(i, 2).indexOf(
														">") + 1,
												artiDt.getString(i, 2).indexOf(
														"</")));
					
					}else {
						map.put(artiDt.getString(i, 1), artiDt.getString(i, 2));
					}
				} else {
					map.put(artiDt.getString(i, 1), "");
				}
			}
			articleInfo.put(productId, map);
		} else {
			return null;
		}

		return articleInfo;
	}
	
	/**
	 * 取得亮点信息
	 * @param detailIds
	 * @param moduTypeMap
	 * @return
	 */
	private Map<String, String> getLightInfo (String detailIds,Map<String, String> moduTypeMap) {
		Map<String, String> prolightMap = new HashMap<String, String>();
		try{
			String prolightSql = "select DetailId, Detail1, Detail2 from SDPromotionProductHighlights where DetailId in ("
					+ detailIds + ") order by DetailId asc, OrderFlag asc";
			DataTable lightDt = new QueryBuilder(prolightSql).executeDataTable();
			if (lightDt != null && lightDt.getRowCount() > 0) {
				String detailId = lightDt.getString(0, 0);
				int lightDtCount = lightDt.getRowCount();
				String moduType = moduTypeMap.get(detailId);
				String lightHtml = "";
				if ("01".equals(moduType) || "02".equals(moduType)) {
					lightHtml = "<ul class=\"ct_sales_li\">";
				} else {
					lightHtml = "<ul class=\"min_ct_ulf\">";
				}
				for (int i = 0; i < lightDtCount; i++) {
					if (!detailId.equals(lightDt.getString(i, 0))) {
						lightHtml += "</ul>";
						prolightMap.put(detailId, lightHtml);
						detailId = lightDt.getString(i, 0);
						moduType = moduTypeMap.get(detailId);
						lightHtml = "";
						if ("01".equals(moduType) || "02".equals(moduType)) {
							lightHtml = "<ul class=\"ct_sales_li\">";
						} else {
							lightHtml = "<ul class=\"min_ct_ulf\">";
						}
					}
					
					if ("01".equals(moduType) || "02".equals(moduType)) {
						if (StringUtil.isNotEmpty(lightDt.getString(i, 1))) {
							if (lightDt.getString(i, 1).length() > 12) {
								lightHtml += ("<li>" + lightDt.getString(i, 1).substring(0, 12) + "...</li>");
							} else {
								lightHtml += ("<li>" + lightDt.getString(i, 1) + "</li>");
							}
							
						}
						
					} else {
						lightHtml += ("<li><span>"
								+ lightDt.getString(i, 1) + "</span><em>"
								+ lightDt.getString(i, 2) + "</em></li>");
					}
					
				}
				
				lightHtml += "</ul>";
				prolightMap.put(detailId, lightHtml);
			}
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return prolightMap;
		}
		return prolightMap;
	}
	
	public String getImageHeight (String imageName) {
		String height = new QueryBuilder("select Height from zcimage where concat(Path, SrcFileName) = ?", imageName).executeString();
		if (StringUtil.isNotEmpty(height)) {
			height = height + "px";
		}
		return height;
	}

	public List<Map<String, String>> getSpecialProductList() {
		return SpecialProductList;
	}

	public void setSpecialProductList(List<Map<String, String>> specialProductList) {
		SpecialProductList = specialProductList;
	}

	public List<Map<String, Object>> getModelList() {
		return ModelList;
	}

	public void setModelList(List<Map<String, Object>> modelList) {
		ModelList = modelList;
	}

	public List<Map<String, String>> getSellingBrandList() {
		return SellingBrandList;
	}

	public void setSellingBrandList(List<Map<String, String>> sellingBrandList) {
		SellingBrandList = sellingBrandList;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
}
