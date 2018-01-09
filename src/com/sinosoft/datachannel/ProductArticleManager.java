package com.sinosoft.datachannel;

import java.util.Date;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.schema.ZCProductarticleSchema;
import com.sinosoft.schema.ZCProductarticleSet;

public class ProductArticleManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.ProductArticleManager";
	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "产品信息转入沉淀表");
		return map;
	}

	public void execute(long id) {
		if ("0".equals(id + "")) {
			String sql = "select ID,SiteID,CatalogID,CatalogInnerCode,BranchInnerCode,Title,Type,URL,Status,TopFlag,OrderFlag,Logo,RiskType from zcarticle where cataloginnercode like '002313%'";
			DataTable dt = new QueryBuilder(sql).executeDataTable();
			DataTable dt1 = new DataTable();
			if(dt.getRowCount()>0){
				ZCProductarticleSet psSet = new ZCProductarticleSet();
				Transaction transaction = new Transaction();
				String deleteSql = "delete from ZCProductarticle";
				transaction.add(new QueryBuilder(deleteSql));
				for(int i = 0;i<dt.getRowCount();i++){
					ZCProductarticleSchema ps = new ZCProductarticleSchema();
					String articleId = dt.getString(i,0);
					if(StringUtil.isNotEmpty(articleId)){
						ps.setID(dt.getInt(i,0));
						ps.setSiteID(dt.getInt(i, 1));
						ps.setCatalogID(dt.getInt(i, 2));
						ps.setCatalogInnerCode(dt.getString(i, 3));
						ps.setBranchInnerCode(dt.getString(i, 4));
						ps.setTitle(dt.getString(i, 5));
						ps.setType(dt.getString(i, 6));
						ps.setURL(dt.getString(i, 7));
						ps.setStatus(dt.getString(i, 8));
						ps.setTopFlag(dt.getString(i, 9));
						ps.setOrderFlag(dt.getInt(i, 10));
						ps.setLogo(dt.getString(i, 11));
						ps.setRiskType(dt.getString(i, 12));
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'riskcode' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							String productId = dt1.getString(0, 0);
							ps.setproductId(productId);
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where columncode = 'SalesVolume' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setSalesVolume(dt1.getString(0, 0));
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'CalHTML2' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setCalHTML2(dt1.getString(0, 0));
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'AdaptPeopleInfo' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setAdaptPeopleInfo(dt1.getString(0, 0));
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'fEMRiskBrightSpot' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setfEMRiskBrightSpot(dt1.getString(0, 0));
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'DutyHTML' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setDutyHTML(dt1.getString(0, 0));
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'prodcutMarkPrice' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setprodcutMarkPrice(dt1.getString(0, 0));
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'SupplierCode2' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setSupplierCode2(dt1.getString(0, 0));
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'Popular' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setPopular(dt1.getString(0, 0));
						}
						dt1 = new QueryBuilder("select textvalue from zdcolumnvalue where ColumnCode = 'InitPrem' and relaid = '"+articleId+"'").executeDataTable();
						if(dt1.getRowCount()>0){
							ps.setInitPrem(dt1.getString(0, 0));		
						}
						ps.setmodifyDate(new Date());
						psSet.add(ps);
					}
				}
				transaction.add(psSet, Transaction.INSERT);
				transaction.commit();
			}
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "产品信息转移";
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
		return "com.sinosoft.datachannel.ProductArticleManager";
	}
	
	/*****************************************************************************/
	public static void main(String[] args) {
		ProductArticleManager productArticle = new ProductArticleManager();
		productArticle.execute(0);
	}
}
