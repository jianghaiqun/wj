package com.sinosoft.product;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.publish.PublishSearchCache;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDSearchCacheSchema;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FEMSearchRelaList;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.FEMSearchProperties;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.SearchInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PublishSearchStaticPages extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(PublishSearchStaticPages.class);
	private static String[] compose(String[] s1, String[] s2) {
		if (s1 == null || s1.length == 0) {
			return null;
		}
		if (s2 == null || s2.length == 0) {
			return null;
		}
		String[] str = new String[s1.length * s2.length];
		int index = 0;
		for (String ss1 : s1) {
			for (String ss2 : s2) {
				str[index] = ss1 + "-" + ss2;
				index++;
			}
		}
		return str;
	}

	private static List<String[]> compose(List<String[]> list) {
		if (list == null || list.size() == 0) {
			return null;
		}
		List<String[]> result = new ArrayList<String[]>();
		for (int i = 0; list != null && i < list.size(); i = i + 2) {
			if (i != list.size() - 1) {
				String[] s = compose(list.get(i), list.get(i + 1));
				if (s != null)
					result.add(s);
			} else {
				result.add(list.get(i));
			}
		}
		return result;
	}

	String productOrder;
	String OrderName;
	String parentcode;
	DataTable db1;
	int n;

	public String getProductOrder() {
		return productOrder;
	}

	public void setProductOrder(String productOrder) {
		this.productOrder = productOrder;
	}

	public String getOrderName() {
		return OrderName;
	}

	public void setOrderName(String orderName) {
		OrderName = orderName;
	}

	public String getParentcode() {
		return parentcode;
	}

	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}

	public DataTable getDb1() {
		return db1;
	}

	public void setDb1(DataTable db1) {
		this.db1 = db1;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public PublishSearchStaticPages(String productOrder, String orderName, String parentcode, DataTable db1, int n) {
		super();
		this.productOrder = productOrder;
		OrderName = orderName;
		this.parentcode = parentcode;
		this.db1 = db1;
		this.n = n;
	}

	private static void PublishSearch(String productOrder, String OrderName, String parentcode, DataTable db1, int n) {
		String eRiskType = "";
		String pageURL = "";
		String CatalogID = "";
		int pageNumber = 0;
		int productSearchFlag;
		Map<String, String> map1 = new HashMap<String, String>();

		if ("".equals(productOrder)) {
			productOrder = null;
		}
		DataTable dt = new QueryBuilder("select codevalue,codename from zdcode where codetype='ProductType' and parentcode='ProductType' order by codevalue LIMIT 0,8 ").executeDataTable();
		int iTotal = dt.getRowCount();
		int i;
		for (i = 0; i < iTotal; i++) {
			String sql5 = "select id from zccatalog where producttype='" + dt.getString(i, 0) + "'";
			DataTable db5 = new QueryBuilder(sql5).executeDataTable();
			CatalogID = db5.getString(0, "id");

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ERiskSubType", dt.getString(i, 0));
			try {
				SearchInfoResponse siInfo = null;
				try {
					siInfo = ProductWebservice.SearchInfoServiceImpl(map, null);
				} catch (Exception e) {
				}
				List<String[]> strList = new ArrayList<String[]>();
				FEMSearchProperties[] femp = siInfo.getFEMSearchProperties();

				for (int j = 0; femp != null && j < femp.length; j++) {
					FEMSearchProperties fem = femp[j];
					femp[j].getSearchCode();
					String[] str = new String[fem.getFEMSearchRela().length + 1];
					for (int k = 0; fem.getFEMSearchRela() != null && k < fem.getFEMSearchRela().length + 1; k++) {
						if (k == fem.getFEMSearchRela().length) {
							str[k] = " ";// 不限
						} else {
							str[k] = fem.getFEMSearchRela()[k].getSearchValue();
						}
					}
					strList.add(str);
				}
				while (strList.size() > 1) {
					strList = compose(strList);
				}
				for (String ss[] : strList) {
					for (String s : ss) {
						String[] strs = s.split("-");
						int iCount = 0;
						productSearchFlag = 0;
						map1 = new HashMap<String, String>();
						for (int ii = 0; ii < strs.length; ii++) { // !"SupplierCode".equals(femp[ii].getSearchCode())
							// &&
							if (!" ".equals(strs[ii])) {
								map1.put(femp[ii].getSearchCode(), strs[ii]);
								iCount++;
							}
						}
						map1.put("CatalogID", CatalogID);
						map1.put("ProductType", dt.getString(i, 0));
						map1.put("ProductsOrder", productOrder);

						String risks = "";
						try {
							FEMSearchRelaList[] list = null;
							Map<String, Object> sMap = new HashMap<String, Object>();
							sMap.put("SubRiskTypeCode", dt.getString(i, 0));
							sMap.put("BU1", "N");

							String key = "";
							// if(iCount>0){
							list = new FEMSearchRelaList[iCount];// - iCount
							Iterator<String> itr = map1.keySet().iterator();
							int y = 0;
							while (itr.hasNext()) {
								key = itr.next();
								if ("CatalogID".equals(key) || "ProductsOrder".equals(key) || "ProductType".equals(key)) {
									continue;
								}
								list[y] = new FEMSearchRelaList();
								list[y].setERiskSubType(dt.getString(i, 0));
								list[y].setSearchCode(key);
								list[y].setSearchValue(map1.get(key));
								y++;
							}
							sMap.put("FEMSearchRelaList", list); // 查询全部产品不加这个测试用
							// }

							logger.info("产品筛选.调用接口:{}", eRiskType);
							ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(sMap, productOrder);// productOrder
							if (productInfo != null) {
								FMRisk[] productList = productInfo.getFMRisk();
								if (productList != null && productList.length > 0 && productList[0] != null) {
									for (int j = 0; j < productList.length; j++) {
										risks += "," + productList[j].getRiskCode();
									}
									if (risks.startsWith(",")) {
										risks = risks.substring(1);
									}
								}
								pageNumber = productList.length / 20 + 1;
							} else {
								DataTable risk = new QueryBuilder(" select z3.textvalue from zcarticle z1,zccatalog z2 ,ZDColumnValue z3 " + " where z1.catalogid=z2.id and z2.producttype='"
										+ dt.getString(i, 0) + "'  and z1.id=z3.relaid and z3.columncode='RiskCode' order by z1.orderflag desc ").executeDataTable();
								for (int j = 0; j < risk.getRowCount(); j++) {
									risks += "," + risk.getString(j, 0);
								}
								if (risks.startsWith(",")) {
									risks = risks.substring(1);
								}
								pageNumber = risk.getRowCount() / 20 + 1;
								productSearchFlag = 1;
							}
						} catch (Exception e) {
							logger.error("产品查询失败：" + e.getMessage(), e);
						}
						logger.info("产品筛选.查询结果:{}", risks);
						pageURL = PublishSearchCache.Publish(risks, map1);

						// int fz = (db1length - n) * iTotal + iTotal - i - 1;
						// int iTotal1 = iTotal * (db1length + 1);
						// lTT.setCurrentInfo("正在发布静态页面第(" + (iTotal1 - fz) +
						// "/" + iTotal1 + ")类");
						// lTT.setPercent(Integer.valueOf((iTotal1 - fz) * 100 /
						// (iTotal1 + 1)));

						String pageURL1 = PublishSearchCache.getSearchPath(map1);

						String conditionName = "";
						for (int ii = 0; ii < strs.length; ii++) {
							if (" ".equals(strs[ii])) {
								conditionName += "";// conditionName +=
								// "-不限";
							} else {
								conditionName += "-" + strs[ii];
							}
						}
						String conditionName1 = dt.getString(i, 1) + conditionName;
						String conditionCode = db1.getString(n, "codename") + "-" + dt.getString(i, 0) + conditionName;
						for (int pages = 1; pages <= pageNumber; pages++) {
							String pageURL3 = pageURL1;
							if (pages >= 2) {
								pageURL3 = pageURL3.substring(0, pageURL3.indexOf(".shtml")) + "_" + Integer.toString(pages) + ".shtml";
							}
							String pageURL2 = pageURL3.replaceAll("/", "//");
							String wordsText = FileUtil.readText(pageURL2);
							String file1 = wordsText.substring(0, wordsText.indexOf("<title>") + 7);
							String file2 = wordsText.substring(wordsText.indexOf("<title>") + 7, wordsText.indexOf("<span class=\"CConditionName\">" + femp[0].getSearchName() + "："));
							String str2 = "";
							String a1 = "";
							int b = 0;
							for (int nu = 0; nu < femp.length; nu++) {
								if (wordsText.indexOf("<span class=\"CConditionName\">" + femp[nu].getSearchName() + "：") != -1) {
									int a = wordsText.indexOf("<span class=\"CConditionName\">" + femp[nu].getSearchName() + "：");
									a1 = wordsText.substring(a);
									b = a1.indexOf("</ul>") + 5;
									String str1 = wordsText.substring(a, a + b);
									if (!" ".equals(strs[nu])) {
										str1 = str1.replaceAll("li_selected", "不限");
										str1 = str1.replace("<li><span onclick=\"searchInfo('SearchCondition_" + femp[nu].getSearchCode() + "', this);\" name=\"" + femp[nu].getSearchCode() + "\">"
												+ strs[nu] + "</span>", "<li class=\"li_selected\"><span onclick=\"searchInfo('SearchCondition_" + femp[nu].getSearchCode() + "', this);\" name=\""
												+ femp[nu].getSearchCode() + "\">" + strs[nu] + "</span>");
									}
									str2 += str1;
								}
							}
							String file4 = a1.substring(b);
							String OrderName1 = "";
							if (!"默认排序".equals(OrderName)) {
								OrderName1 = OrderName + "-";
							}
							if (conditionName.startsWith("-")) {
								conditionName = conditionName.substring(1) + "-";
							}
							// if("".equals(conditionName)){
							//								
							// }
							String file3 = file1 + OrderName1 + conditionName + file2 + str2 + file4;

							if (!"默认排序".equals(OrderName) && productSearchFlag == 0) {
								file3 = file3.replace("<span id=\"order_default\" class=\"one\" onclick=\"doOrder(this);\">默认排序</span>",
										"<span id=\"order_default1\" class=\"one\" onclick=\"doOrder(this);\">默认排序</span>");
								if (productOrder.indexOf("desc") != -1) {
									file3 = file3.replace("class=\"two\" onclick=\"doOrder(this);\">" + parentcode + "</span>", "class=\"twodesc\" onclick=\"doOrder(this);\">" + parentcode
											+ "</span>");
								} else {
									file3 = file3
											.replace("class=\"two\" onclick=\"doOrder(this);\">" + parentcode + "</span>", "class=\"twoasc\" onclick=\"doOrder(this);\">" + parentcode + "</span>");
								}
							}
							if ("默认排序".equals(OrderName)) {
								file3 = file3.replace("<span id=\"order_default\" class=\"one\" onclick=\"doOrder(this);\">默认排序</span>",
										"<span id=\"order_default\" class=\"onesel\" onclick=\"doOrder(this);\">默认排序</span>");
							}

							if (productSearchFlag == 1) {
								String file21 = file3.substring(0, file3.indexOf("<div id=\"products\">"));
								String file22 = file3.substring(file3.indexOf("<div id=\"products\">")); // style=\"color:#000000;font-size:20px;margin-top:
								// 15px;
								// margin-left:16px;margin-bottom:15px;\"
								String file23 = "<div class=\"top_b\"><span><p class=\"searchNoData\">没有符合筛选条件的保险产品，以下是" + dt.getString(i, 1) + "下的热门产品供您选择！</p></span></div>";
								file3 = file21 + file23 + file22;
							}

							FileUtil.writeText(pageURL2, file3);
						}

						SDSearchCacheSchema tSDSearchCacheSchema = new SDSearchCacheSchema();
						String sql4 = "select id from SDSearchCache where conditionCode='" + conditionCode + "'";
						DataTable db4 = new QueryBuilder(sql4).executeDataTable();
						for (int mm = 0; mm < db4.getRowCount(); mm++) {
							String id1 = db4.getString(mm, "id");
							tSDSearchCacheSchema.setID(id1);
							tSDSearchCacheSchema.delete();
						}
						tSDSearchCacheSchema.setID(NoUtil.getSDSearchCacheID());
						tSDSearchCacheSchema.setConditionName(conditionName1);
						tSDSearchCacheSchema.setConditionCode(conditionCode);
						tSDSearchCacheSchema.setCacheURL(pageURL);
						tSDSearchCacheSchema.setCreateDate(PubFun.getCurrentDate());
						tSDSearchCacheSchema.setRealPath(pageURL1);
						tSDSearchCacheSchema.setSearchType("Search");
						tSDSearchCacheSchema.setProp1(Integer.toString(pageNumber));
						tSDSearchCacheSchema.insert();
					}
				}
			} catch (Exception e) {

				logger.error("筛选页面接口获取数据失败：{}", e.getMessage());
			}
		}

	}

	public PublishSearchStaticPages() {
		super();
	}

	@Override
	public void run() {
		PublishSearch(productOrder, OrderName, parentcode, db1, n);
	}

}
