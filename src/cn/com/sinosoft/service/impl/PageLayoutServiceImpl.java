package cn.com.sinosoft.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.QueryBuilder;
import cn.com.sinosoft.dao.PageLayoutDao;
import cn.com.sinosoft.entity.PageLayout;
import cn.com.sinosoft.service.PageLayoutService;

@Service
public class PageLayoutServiceImpl extends BaseServiceImpl<PageLayout, String>
		implements PageLayoutService {
	@Resource
	private PageLayoutDao pageLayoutDao;

	@Resource
	public void setPageLayoutDao(PageLayoutDao pageLayoutDao) {
		super.setBaseDao(pageLayoutDao);
	}

	@Override
	public String getReturnPage(String comCode, String riskCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("comCode", "=", comCode));
		qbs.add(createQB("riskCode", "=", riskCode));
		List<PageLayout> list = pageLayoutDao.findByQBs(qbs, "id", "asc");
		if (list != null && list.size() > 0) {
			PageLayout pageLayOut = list.get(0);
			return pageLayOut.getReturnPage();
		} else {
			return null;
		}
	}
    public String getReturnPageByProduct(String productId , String comCode){
    	List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("productId", "=", productId));
		qbs.add(createQB("comCode", "=", comCode));
		List<PageLayout> list = pageLayoutDao.findByQBs(qbs, "id", "asc");
		if (list != null && list.size() > 0) {
			PageLayout pageLayOut = list.get(0);
			return pageLayOut.getReturnPage();
		} else {
			return null;
		}
    }
    
	@Override
	public String getReturnPageByCompany(String comCode) {
		List<QueryBuilder> qbs = new ArrayList<QueryBuilder>();
		qbs.add(createQB("comCode", "=", comCode));
		List<PageLayout> list = pageLayoutDao.findByQBs(qbs, "id", "asc");
		if (list != null && list.size() > 0) {
			PageLayout pageLayOut = list.get(0);
			return pageLayOut.getReturnPage();
		} else {
			return null;
		}
	}

	private QueryBuilder createQB(String property, String sign, String value) {
		QueryBuilder qb = new QueryBuilder();
		qb.setProperty(property);
		qb.setSign(sign);
		qb.setValue(value);
		return qb;
	}

}
