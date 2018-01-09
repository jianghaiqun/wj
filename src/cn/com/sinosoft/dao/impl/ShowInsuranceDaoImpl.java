package cn.com.sinosoft.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.ShowInsuranceDao;
import cn.com.sinosoft.entity.ShowInsurance;

/**
 * @author LiuXin
 *
 */
@Repository
public class ShowInsuranceDaoImpl extends BaseDaoImpl<ShowInsurance,String> implements ShowInsuranceDao{

	@Override
	public List<ShowInsurance> getMinPremium(String sql) {
		List<ShowInsurance> sis = new ArrayList<ShowInsurance>();
		@SuppressWarnings("unchecked")
		List<Object[]> list = this.getSession().createSQLQuery(sql).list();
		if(list!=null&&list.size()>0){
			for(Object[] o : list){
				if(o[0]!=null){
					sis.add(create(o[0]));
				}
			}
		}
		return sis;
	}

	private ShowInsurance create(Object id) {
		ShowInsurance si = new ShowInsurance();
		si.setId(id.toString());
		return si;
	}
	
}
