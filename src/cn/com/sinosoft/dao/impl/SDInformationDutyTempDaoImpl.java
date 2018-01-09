/**
 * 
 */
package cn.com.sinosoft.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDInformationDutyTempDao;
import cn.com.sinosoft.entity.SDInformationDutyTemp;

/**
 * @author wangcaiyun
 *
 */
@Repository
public class SDInformationDutyTempDaoImpl extends BaseDaoImpl<SDInformationDutyTemp, String> implements SDInformationDutyTempDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SDInformationDutyTemp> getDutyTemp(String Serials) {
		String hql = "from SDInformationDutyTemp as duty where duty.dutySerials = ?";
		return (List<SDInformationDutyTemp>) getSession().createQuery(hql).setParameter(0, Serials).list();
	}
}
