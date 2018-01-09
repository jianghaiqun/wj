package cn.com.sinosoft.dao;

import java.util.Map;

import cn.com.sinosoft.entity.Present;

public interface PointsDao extends BaseDao<Present, String> {
	public Map getURL(long articleID);
}
