package cn.com.sinosoft.dao.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinosoft.framework.utility.StringUtil;

import cn.com.sinosoft.dao.SDRelationRecognizeeDao;
import cn.com.sinosoft.entity.SDRelationRecognizee;

/**
 * Dao实现类 - 运输信息
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT64B5A05594CB1C3B74C8A9B4F94F5991
 * ============================================================================
 */

@Repository
public class SDRelationRecognizeeDaoImpl extends BaseDaoImpl<SDRelationRecognizee, String> implements SDRelationRecognizeeDao {
	@Override
	public SDRelationRecognizee getSDRelationRecognizeeInfo(String comCode,String productID,String memberId,String recognizeeName){
		String hql = "from SDRelationRecognizee as fifl where fifl.remark=? and fifl.productId=? and fifl.memberId=? and fifl.recognizeeName=?";
		SDRelationRecognizee tSDRelationRecognizee = new SDRelationRecognizee();
		tSDRelationRecognizee = (SDRelationRecognizee) getSession().createQuery(hql).setParameter(0,comCode).setParameter(1, productID).setParameter(2, memberId).setParameter(3, recognizeeName).uniqueResult();
		if(tSDRelationRecognizee == null){
			hql = "from SDRelationRecognizee as fifl where fifl.remark=? and fifl.memberId=? and fifl.recognizeeName=?";
			tSDRelationRecognizee = (SDRelationRecognizee) getSession().createQuery(hql).setParameter(0,comCode).setParameter(1, memberId).setParameter(2, recognizeeName).uniqueResult();
		}
		if(tSDRelationRecognizee == null){
			hql = "from SDRelationRecognizee as fifl where fifl.memberId=? and fifl.recognizeeName=?";
			tSDRelationRecognizee = (SDRelationRecognizee) getSession().createQuery(hql).setParameter(0, memberId).setParameter(1, recognizeeName).uniqueResult();
		}
		return tSDRelationRecognizee;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SDRelationRecognizee> getSDRelationRecognizeeInfoList(String comCode,String productID,String memberId,String recognizeeName){
		String hql = "from SDRelationRecognizee as fifl where fifl.remark=? and fifl.productId=? and fifl.memberId=? and fifl.recognizeeName=?";
		List<SDRelationRecognizee> tSDRelationRecognizee = null;
		if(StringUtil.isNotEmpty(recognizeeName)){
			tSDRelationRecognizee = (List<SDRelationRecognizee>) getSession().createQuery(hql).setParameter(0,comCode).setParameter(1, productID).setParameter(2, memberId).setParameter(3, recognizeeName).list();
			if(tSDRelationRecognizee == null || tSDRelationRecognizee.size()<=0){
				hql = "from SDRelationRecognizee as fifl where fifl.remark=? and fifl.memberId=? and fifl.recognizeeName=?";
				tSDRelationRecognizee = (List<SDRelationRecognizee>) getSession().createQuery(hql).setParameter(0,comCode).setParameter(1, memberId).setParameter(2, recognizeeName).list();
			}
			if(tSDRelationRecognizee == null || tSDRelationRecognizee.size()<=0){
				hql = "from SDRelationRecognizee as fifl where fifl.memberId=? and fifl.recognizeeName=?";
				tSDRelationRecognizee = (List<SDRelationRecognizee>) getSession().createQuery(hql).setParameter(0, memberId).setParameter(1, recognizeeName).list();
			}
		}else{
			if(tSDRelationRecognizee == null || tSDRelationRecognizee.size()<=0){
				hql = "from SDRelationRecognizee as fifl where fifl.memberId=? ";
				tSDRelationRecognizee = (List<SDRelationRecognizee>) getSession().createQuery(hql).setParameter(0, memberId).list();
			}
		}
		return tSDRelationRecognizee;
	}
}