package cn.com.sinosoft.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.SDBillTitleDao;
import cn.com.sinosoft.entity.SDBillTitle;

/**
 * 
 * Dao实现类 - 发票台头
 *
 */

@SuppressWarnings("unchecked")
@Repository
public class SDBillTitleDaoImpl extends BaseDaoImpl<SDBillTitle, String>
		implements SDBillTitleDao {

	@Override
	public List<SDBillTitle> getSDBillTitle(String memberId) {
		String hql = "from SDBillTitle as bt where bt.memberId=? order by isDefault desc, modifyDate desc ";
		List<SDBillTitle> tSDBillTitle = new ArrayList<SDBillTitle>();
		tSDBillTitle = (List<SDBillTitle>) getSession().createQuery(hql)
				.setParameter(0, memberId).list();
		return tSDBillTitle;
	}

	@Override
	public int updateDefault(String Id) {
		String hql = "update SDBillTitle title set title.isDefault = '0' where title.Id <> ?";
		// @TODO
		Query queryupdate = getSession().createQuery(hql);
		int ret = queryupdate.executeUpdate();
		return ret;
	}
}