package cn.com.sinosoft.dao.impl;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.sinosoft.framework.utility.StringUtil;

import cn.com.sinosoft.dao.SDRelationAppntDao;
import cn.com.sinosoft.entity.SDOrder;
import cn.com.sinosoft.entity.SDRelationAppnt;

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
public class SDRelationAppntDaoImpl extends BaseDaoImpl<SDRelationAppnt, String> implements SDRelationAppntDao { 
	@Override
	public SDRelationAppnt getSDRelationAppntInfo(String comCode,String productID,String memberId,String appntName){
		String hql = "from SDRelationAppnt as fifl where fifl.remark=? and fifl.productId=? and fifl.memberId=? and fifl.applicantName=?";
		SDRelationAppnt tSDRelationAppnt = new SDRelationAppnt();
		tSDRelationAppnt = (SDRelationAppnt) getSession().createQuery(hql).setParameter(0,comCode).setParameter(1, productID).setParameter(2, memberId).setParameter(3, appntName).setFirstResult(0).setMaxResults(1).uniqueResult();
		if(tSDRelationAppnt == null){
			hql = "from SDRelationAppnt as fifl where fifl.remark=? and fifl.memberId=? and fifl.applicantName=? " ;
			tSDRelationAppnt = (SDRelationAppnt) getSession().createQuery(hql).setParameter(0,comCode).setParameter(1, memberId).setParameter(2, appntName).setFirstResult(0).setMaxResults(1).uniqueResult();
		}
		if(tSDRelationAppnt == null){
			hql = "from SDRelationAppnt as fifl where fifl.memberId=? and fifl.applicantName=? ";
			tSDRelationAppnt = (SDRelationAppnt) getSession().createQuery(hql).setParameter(0, memberId).setParameter(1, appntName).setFirstResult(0).setMaxResults(1).uniqueResult();
		}
		return tSDRelationAppnt;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SDRelationAppnt> getSDRelationAppntInfoList(String comCode,String productID,String memberId,String appntName){
		String hql = "from SDRelationAppnt as fifl where fifl.remark=? and fifl.productId=? and fifl.memberId=? and fifl.applicantName=?";
		List<SDRelationAppnt> tSDRelationAppnt = null;
		if(StringUtil.isNotEmpty(appntName)){
			tSDRelationAppnt = ((List<SDRelationAppnt>) getSession().createQuery(hql).setParameter(0,comCode).setParameter(1, productID).setParameter(2, memberId).setParameter(3, appntName).list());
			if(tSDRelationAppnt == null || tSDRelationAppnt.size()<=0){
				hql = "from SDRelationAppnt as fifl where fifl.remark=? and fifl.memberId=? and fifl.applicantName=?";
				tSDRelationAppnt = ((List<SDRelationAppnt>) getSession().createQuery(hql).setParameter(0,comCode).setParameter(1, memberId).setParameter(2, appntName).list());
			}
			if(tSDRelationAppnt == null || tSDRelationAppnt.size()<=0){
				hql = "from SDRelationAppnt as fifl where fifl.memberId=? and fifl.applicantName=?";
				tSDRelationAppnt = (List<SDRelationAppnt>) getSession().createQuery(hql).setParameter(0, memberId).setParameter(1, appntName).list();
			}
		}else{
			if(tSDRelationAppnt == null || tSDRelationAppnt.size()<=0){
				hql = "from SDRelationAppnt as fifl where fifl.memberId=? ";
				tSDRelationAppnt = (List<SDRelationAppnt>) getSession().createQuery(hql).setParameter(0, memberId).list();
			}
		}
		return tSDRelationAppnt;
	}

}