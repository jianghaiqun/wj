package cn.com.sinosoft.dao.impl;

import cn.com.sinosoft.dao.PointsDao;
import cn.com.sinosoft.entity.Present;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PointsDaoImpl extends BaseDaoImpl<Present, String> implements PointsDao {

	public Map getURL(long articleID) {
		try {
			String sql = " select a.url as siteurl,b.url as  articleurl,b.logo as articlelogo from zcsite a,zcarticle b where b.siteid=a.id  and b.id=?";
			DataTable dt = new QueryBuilder(sql, articleID).executeDataTable();
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			for (int i = 0; i < dt.getRowCount(); i++) {
				Map<String,String> map = new HashMap<String ,String>();
				map.put("siteurl", dt.getString(i, 0));
				map.put("articleurl", dt.getString(i, 1));
				map.put("articlelogo", dt.getString(i, 2));
				list.add(map);
			}

			if (list != null && list.size() == 1) {
				return list.get(0);
			}
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public static void main(String[] args) {
		long id = 250125;
		PointsDaoImpl pdi = new PointsDaoImpl();
		pdi.getURL(id);

	}
}
