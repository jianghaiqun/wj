package cn.com.sinosoft.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sinosoft.framework.utility.StringUtil;

import cn.com.sinosoft.dao.PointsDao;
import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.service.PointsService;

@Service
public class PointsServiceImpl extends BaseServiceImpl<Present, String> implements PointsService {
	@Resource
	private PointsDao pointsDao;

	@Resource
	public void setBaseDao(PointsDao pointsDao) {
		super.setBaseDao(pointsDao);
	}

	public HashMap<String, String> getURL(long articleID) {
		try {
			Map map = pointsDao.getURL(articleID);
			if (map == null || map.isEmpty()) {
				return null;
			}
			HashMap<String, String> result = new HashMap<String, String>();
			String siteurl = (String) map.get("siteurl");
			String articleurl = (String) map.get("articleurl");
			String articlelogo = (String) map.get("articlelogo");

			if (StringUtil.isEmpty(siteurl) || StringUtil.isEmpty(articleurl) || StringUtil.isEmpty(articlelogo)) {
				return null;
			}
			if (!siteurl.endsWith("/")) {
				siteurl += "/";
			}

			result.put("articleurl", siteurl + "" + articleurl);
			result.put("articlelogo", siteurl + "" + articlelogo);
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}