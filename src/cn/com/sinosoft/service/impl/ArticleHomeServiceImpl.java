package cn.com.sinosoft.service.impl;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import cn.com.sinosoft.dao.ArticleHomeDao;
import cn.com.sinosoft.dao.OrderDao;
import cn.com.sinosoft.entity.ArticleHome;
import cn.com.sinosoft.service.ArticleHomeService;

@Service
public class ArticleHomeServiceImpl extends BaseServiceImpl<ArticleHome, String> implements ArticleHomeService {
	@Resource
	private ArticleHomeDao articleHomeDao;
	@Resource
	public void setBaseDao(ArticleHomeDao articleHomeDao) {
		super.setBaseDao(articleHomeDao);
	}

	@Override
	public String getLastHomeSn() {
		return articleHomeDao.getLastHomeSn();
	}
} 