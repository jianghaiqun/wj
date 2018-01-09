package cn.com.sinosoft.service;

import cn.com.sinosoft.entity.ArticleHome;

public interface ArticleHomeService extends BaseService<ArticleHome, String> {

	public String getLastHomeSn();
}