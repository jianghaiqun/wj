package cn.com.sinosoft.dao.impl;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.TradeInformationDao;

import cn.com.sinosoft.entity.TradeInformation;

@Repository
public class TradeInformationDaoImpl extends BaseDaoImpl<TradeInformation, String> implements TradeInformationDao {

	@Override
	public TradeInformation getTradeInformationByOrdId(String ordId) {
		// TODO Auto-generated method stub

		String hql = "from TradeInformation as tradeInformation  " + "  where  tradeInformation.ordID=? and ((tradeInformation.payStatus='1' and tradeInformation.tradeResult='0')"
				+ " or (tradeInformation.payStatus='2') OR (tradeInformation.payStatus='3' AND tradeInformation.tradeResult='1' ))";
		TradeInformation tradeInformation = null;
		if(getSession().createQuery(hql).setParameter(0, ordId).list() !=null && getSession().createQuery(hql).setParameter(0, ordId).list().size()!=0){
			tradeInformation=(TradeInformation) getSession().createQuery(hql).setParameter(0, ordId).list().get(0);
		}
		return tradeInformation;
	}

	@Override
	public TradeInformation getTradeInformationByOrdSn(String ordSn) {
		// TODO Auto-generated method stub
		String hql = "from TradeInformation as tradeInformation  " + "  where  tradeInformation.ordID=?";
		TradeInformation tradeInformation=null;
		if( getSession().createQuery(hql).setParameter(0, ordSn).list() !=null && getSession().createQuery(hql).setParameter(0, ordSn).list().size()!=0 ){
			 tradeInformation=(TradeInformation) getSession().createQuery(hql).setParameter(0, ordSn).list().get(0);
		}
		return tradeInformation;
	}

	public TradeInformation isPayFinnish(String ordSn) {

		String hql = " from TradeInformation as tradeInformation  where  tradeInformation.ordID = ?" + " and tradeInformation.payStatus = '1' and tradeInformation.tradeResult='0' ";
		TradeInformation tradeInformation = null;
		if(getSession().createQuery(hql).setParameter(0, ordSn).list() !=null && getSession().createQuery(hql).setParameter(0, ordSn).list().size()!=0){
			tradeInformation=(TradeInformation) getSession().createQuery(hql).setParameter(0, ordSn).list().get(0);
		}
		return tradeInformation;
	}

}
