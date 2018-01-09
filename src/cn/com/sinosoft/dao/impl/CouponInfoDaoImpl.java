package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

import cn.com.sinosoft.dao.CouponInfoDao;
import cn.com.sinosoft.entity.CouponInfo;


@Repository
public class CouponInfoDaoImpl extends BaseDaoImpl<CouponInfo, String> implements CouponInfoDao {

	@Override
	public CouponInfo getCouponInfoByOrderSn(String orderSn){
		String hql = " from CouponInfo where orderSn=?";
		CouponInfo couponInfo = new CouponInfo();
		couponInfo = (CouponInfo) getSession().createQuery(hql).setParameter(0, orderSn).uniqueResult();
		if(couponInfo == null){
			return null;
		}
		return couponInfo;
	}

	@Override
	public String getCompanyCodeFromProductCenter(String productId) {
		
		String companyCode = new QueryBuilder("select a.SupplierCode from fmriskb a,femriskb b where a.RiskCode=b.RiskCode and b.IsPublish='Y' and a.RiskCode=? ",productId).executeString();
		
		return companyCode;
	}

	@Override
	public DataTable getRiskCodeFromProductCenter(String productId) {
		
		DataTable dt = new QueryBuilder("select DISTINCT c.subRisktype from (" +
				"select SubRiskTypeCode as subRisktype from femrisktypeb a,femriskb b where a.RiskCode=b.RiskCode and b.IsPublish='Y' and a.RiskCode = ?" +
				" union all " + 
				"select a.BelongFlag as subRisktype from fmriskb a,femriskb b where a.RiskCode=b.RiskCode and b.IsPublish='Y' and a.RiskCode = ?) c"
				,productId,productId).executeDataTable();
		
		return dt;
	}

	@Override
	public CouponInfo getCouponInfoByCouponSn(String couponSn) {
		String hql = " from CouponInfo where couponSn=?";
		CouponInfo couponInfo = new CouponInfo();
		couponInfo = (CouponInfo) getSession().createQuery(hql).setParameter(0, couponSn).uniqueResult();
		if(couponInfo == null){
			return null;
		}
		return couponInfo;
	}

}
