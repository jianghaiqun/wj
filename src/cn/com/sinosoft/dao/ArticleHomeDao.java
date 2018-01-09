package cn.com.sinosoft.dao;

import cn.com.sinosoft.entity.ArticleHome;

public interface ArticleHomeDao extends BaseDao<ArticleHome, String> {

	public String getLastHomeSn();

	public String findByHomeSn(String i);
}