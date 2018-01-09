package cn.com.sinosoft.service;

import java.util.HashMap;

import cn.com.sinosoft.entity.Present;

/**
 * Service接口 - 礼品
 * ============================================================================
 * KEY:SINOSOFT3F6674D6C2E7DF7287EF69622E4F46B5
 * ============================================================================
 */

public interface PointsService extends BaseService<Present, String> {
	public HashMap<String, String> getURL(long articleID);
}