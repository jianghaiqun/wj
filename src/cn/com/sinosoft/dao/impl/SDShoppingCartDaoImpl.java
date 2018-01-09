package cn.com.sinosoft.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDShoppingCartDao;
import cn.com.sinosoft.entity.SDShoppingCart;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

@Repository
public class SDShoppingCartDaoImpl extends BaseDaoImpl<SDShoppingCart,String> implements SDShoppingCartDao{

	@Override
	public DataTable getShopCartNo(String sql) {
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		return dt;
	}

	@Override
	public DataTable getInfByIDAndType(String sql, String idtype, String idNo) {
		DataTable dt = new QueryBuilder(sql,idtype,idNo).executeDataTable();
		return dt;
	}

	@Override
	public String getuwCheckFlagByComCode(String comCode) {
		String sql = "select prop1 from zdcode where CodeType='UWCheckClassName' and ParentCode='UWCheckClassName' and CodeValue=?";
		DataTable dt = new QueryBuilder(sql,comCode).executeDataTable();
		String flag = "N";
		if(dt.getRowCount()>0){
			flag = dt.getString(0, 0);
		}
		return flag;
	}

	@Override
	public DataTable getInsuredByOrderSn(String orderSn) {
		String sql = "select recognizeeName from sdinformationinsured where ordersn=? group by recognizeekey";
		return new QueryBuilder(sql,orderSn).executeDataTable();
	}

	@Override
	public void deleteShopCartBySql(String deleteSql) {
		new QueryBuilder(deleteSql).executeNoQuery();
		
	}

	@Override
	public Map<String,String> getProductInFo(String productId) {
		DataTable dt =new QueryBuilder("select LogoLink,url from SDSearchRelaProduct where productId =?",productId).executeDataTable();
		Map<String,String> map = new HashMap<String,String>();
		if(dt.getRowCount()>0){
			map.put("logo", dt.getString(0, 0));
			map.put("detailUrl", dt.getString(0, 1));
		}
		return map;
	}

	@Override
	public DataTable getInsuredNoByOrderSn(String orderSn) {
		String sql = "select count(recognizeeName) from sdinformationinsured where ordersn=? ";
		return new QueryBuilder(sql,orderSn).executeDataTable();
	}

}
