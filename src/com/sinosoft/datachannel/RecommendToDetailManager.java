package com.sinosoft.datachannel;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.RecommendToDetailSchema;
import com.sinosoft.schema.RecommendToDetailSet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RecommendToDetailManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.RecommendToDetailManager";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "详细页-推荐产品");
		return map;
	}

	public void execute(long id) {
		if ("0".equals(id + "")) {

			String productSQL = " SELECT ProductID,Remark1,ProductType FROM SDProduct WHERE IsPublish='Y' ";
			QueryBuilder productQB = new QueryBuilder(productSQL);
			DataTable productDT = productQB.executeDataTable();
			RecommendToDetailSet tRecommendToDetailSet = new RecommendToDetailSet();
			RecommendToDetailSchema tRecommendToDetailSchema = null;
			Transaction trans = new Transaction();
			trans.add(new QueryBuilder("delete from RecommendToDetail where prop1 is null or prop1 = ''"));
			if (productDT.getRowCount() >= 1) {
				Map<String, String> map = new HashMap<String, String>();
				String sql = "select DISTINCT r.Prop1, a.ProductID from RecommendToDetail r, sdsearchrelaproduct a where r.Prop1 in (SELECT CodeValue FROM zdcode WHERE CodeType = 'MainProductType' and ParentCode='MainProductType') and r.RelaArticleID = a.Prop1 ";
				DataTable proTypeDT = new QueryBuilder(sql).executeDataTable();
				if (proTypeDT != null && proTypeDT.getRowCount() > 0) {
					int rowCount = proTypeDT.getRowCount();
					for (int j = 0; j < rowCount; j++) {
						if (map.containsKey(proTypeDT.getString(j, 0))) {
							map.put(proTypeDT.getString(j, 0), map.get(proTypeDT.get(j, 0)) + "," + proTypeDT.getString(j, 1));
						} else {
							map.put(proTypeDT.getString(j, 0), proTypeDT.getString(j, 1));
						}
					}
				}
				for (int n = 0; n < productDT.getRowCount(); n++) {
					String productid = productDT.getString(n, "ProductID");
					String wheProId = productid;
					if (map.containsKey(productDT.getString(n, "ProductType"))) {
						wheProId += ("," + map.get(productDT.getString(n, "ProductType")));
					}
					wheProId = "'"+wheProId.replace(",", "','")+"'";
					String exSql = "";
					try {
						// 查询产品所属分类下销量排名前三的其他产品
						String sql1 = " SELECT z3.parentid,z1.id as aid"
								+ " FROM zcarticle z1,sdsearchrelaproduct z2,zccatalog z3"
								+ " WHERE  z1.title = z2.productname AND z1.catalogid=z3.id AND z1.ID = z2.Prop1 ";

						//String productId = "''";
						String sqlpart1 = " AND z2.productid NOT IN ("
								+ wheProId + ") AND z1.catalogid =?"
								+ " ORDER BY salesvolume+0 DESC LIMIT 0,3";
						QueryBuilder qb1 = new QueryBuilder(sql1 + sqlpart1);
						String catalogID = String.valueOf(productDT.getString(
								n, "Remark1"));
						qb1.add(catalogID);
						DataTable dt1 = qb1.executeDataTable();
						// 如果同分类下有产品
						int rowCount = 0;
						String parentid = "''";
						if (dt1 != null && dt1.getRowCount() > 0) {
							rowCount = dt1.getRowCount();
							parentid = "'" + dt1.get(0, "parentid") + "'";
							for (int i = 0; i < rowCount; i++) {
								tRecommendToDetailSchema = new RecommendToDetailSchema();
								tRecommendToDetailSchema.setid(NoUtil
										.getMaxIDLocal("RecommendToDetail")
										+ "");
								tRecommendToDetailSchema
										.setMakeDate(new Date());
								tRecommendToDetailSchema
										.setModifyDate(new Date());
								tRecommendToDetailSchema
										.setProductID(productid);
								tRecommendToDetailSchema
										.setCatalogID(catalogID);
								tRecommendToDetailSchema.setArticleID("");
								tRecommendToDetailSchema
										.setRelaArticleID(String.valueOf(dt1
												.get(i, "aid")));
								tRecommendToDetailSet
										.add(tRecommendToDetailSchema);
							}
							// 如果同分类下没有其他产品或者不足3个
							for (int i = 0; i < 3 - rowCount; i++) {
								String sqlpart2 = " AND z2.productid NOT IN ("
										+ wheProId
										+ ") and z3.parentid="
										+ parentid
										+ " ORDER BY salesvolume+0 DESC LIMIT 0,3";
								exSql = sql1 + sqlpart2;
								QueryBuilder qb2 = new QueryBuilder(exSql);
								DataTable dt2 = qb2.executeDataTable();
								if (dt2 != null && dt2.getRowCount() > 0) {
									tRecommendToDetailSchema = new RecommendToDetailSchema();
									tRecommendToDetailSchema.setid(NoUtil
											.getMaxIDLocal("RecommendToDetail")
											+ "");
									tRecommendToDetailSchema
											.setMakeDate(new Date());
									tRecommendToDetailSchema
											.setModifyDate(new Date());
									tRecommendToDetailSchema
											.setProductID(productid);
									tRecommendToDetailSchema
											.setCatalogID(catalogID);
									tRecommendToDetailSchema.setArticleID("");
									tRecommendToDetailSchema
											.setRelaArticleID(String
													.valueOf(dt2.get(i, "aid")));
									tRecommendToDetailSet
											.add(tRecommendToDetailSchema);
								}
							}
						}
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}

				}
			}
			if (tRecommendToDetailSet != null
					&& tRecommendToDetailSet.size() >= 1) {
				trans.add(tRecommendToDetailSet, Transaction.INSERT);
				if (!trans.commit()) {
					logger.error("同步详细页-推荐产品错误");
				}
			}
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "详细页推荐产品";
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.sinosoft.datachannel.RecommendToDetailManager";
	}

	/*****************************************************************************/
	public static void main(String[] args) {
		RecommendToDetailManager productArticle = new RecommendToDetailManager();
		productArticle.execute(0);
	}
}
