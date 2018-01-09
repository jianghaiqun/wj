package com.sinosoft.datachannel;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.utility.DataCache;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;

import java.util.HashMap;

public class InsuranceTopicsProductManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.InsuranceTopicsProductManager";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "保险公司专题-产品信息");
		return map;
	}

	public void execute(long id) {
		if ("0".equals(id + "")) {
			DataCache.cleanProductInfo();
			String strCatalogId = Config.getValue("InsureCompanyTopicCatalog");
			QueryBuilder qb = new QueryBuilder();
			String sql = "select z3.TextValue,z1.id,z1.title from zcarticle z1,zdcolumnvalue z3 where z3.ColumnCode='CompanyID' and z3.relaid=z1.id and z1.CatalogID=?";
			qb.setSQL(sql);
			qb.add(strCatalogId);
			DataTable dtComCode = qb.executeDataTable();
			sql = "select codevalue,codename from zdcode where codetype='ProductType' and parentcode='ProductType' order by codevalue LIMIT 21";
			qb.setSQL(sql);
			qb.getParams().clear();
			DataTable dtType = qb.executeDataTable();
			int iComCodeCount = dtComCode.getRowCount();
			for (int i=0; i<iComCodeCount; i++){
				int iTypeCount = dtType.getRowCount();
				HashMap<String, Object> map = null;
				for (int j=0; j<iTypeCount; j++){
					map = new HashMap<String, Object>();
					map.put("BU1", "N");
					map.put("SupplierCode", String.valueOf(dtComCode.get(i, 0)));
					map.remove("SubRiskTypeCode");
					map.put("SubRiskTypeCode", dtType.get(j, 0));
					map.put("ComCode_RiskType", "ComCode_RiskType");
					try {
						ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
						DataCache.setProductInfoByCom(String.valueOf(dtComCode.get(i, 0)), productInfo);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage(), e);
					}
				}
				map = new HashMap<String, Object>();
				map.put("BU1", "N");
				map.put("SupplierCode", String.valueOf(dtComCode.get(i, 0)));
				map.remove("SubRiskTypeCode");
				map.put("ComCode_RiskType", "ComCode_RiskType");
				try {
					ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
					DataCache.setProductInfoByCom(String.valueOf(dtComCode.get(i, 0)), productInfo);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "保险公司专题";
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
		return "com.sinosoft.datachannel.InsuranceTopicsProductManager";
	}

	/*****************************************************************************/
	public static void main(String[] args) {
		RecommendToDetailManager productArticle = new RecommendToDetailManager();
		productArticle.execute(0);
	}
}
