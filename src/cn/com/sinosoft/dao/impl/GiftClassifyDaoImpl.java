package cn.com.sinosoft.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.com.sinosoft.dao.GiftClassifyDao;
import cn.com.sinosoft.entity.GiftClassify;

import com.opensymphony.oscache.util.StringUtil;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;

/**
 * Dao实现类 - 礼品
 * ============================================================================
 *  
 *
 *  
 *
 *  
 *
 * KEY:SINOSOFT8061830D6037E81F0D148994EFFC9C78
 * ============================================================================
 */

@Repository
public class GiftClassifyDaoImpl extends BaseDaoImpl<GiftClassify, String> implements GiftClassifyDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<GiftClassify> hotgiftlistby() {
		String hql = "from GiftClassify as GiftClassify  where status = '0' and UNIX_TIMESTAMP(DATE_FORMAT(GiftClassify.startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(GiftClassify.endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) order by GiftClassify.popularity desc,GiftClassify.createDate desc limit 5";
		List<GiftClassify> giftlist =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		return giftlist;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<GiftClassify> recgiftlistby() {
		String hql = "from GiftClassify as GiftClassify where GiftClassify.recommend='Y' and   status = '0' and UNIX_TIMESTAMP(DATE_FORMAT(GiftClassify.startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(GiftClassify.endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) order by GiftClassify.createDate desc limit 5";
		List<GiftClassify> giftlist =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(1).list();
		return giftlist;
	}

	/**
	 * 热门兑换查询
	 * @return
	 */
	public List<GiftClassify> hotExchange() {
		String hql = "from GiftClassify where status = '0' and (lastNum+0) > 0 and UNIX_TIMESTAMP(DATE_FORMAT(startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) order by orderFlag desc, createDate desc";
		List<GiftClassify> giftlist =  getSession().createQuery(hql).setFirstResult(0).setMaxResults(4).list();
		return giftlist;
	}
	
	/**
	 * 取得保险产品礼品库存量
	 * 
	 * @param productId
	 *            保险产品id
	 * @return 库存量
	 */
	public int getLastNum(String productId) {
		String sql="from GiftClassify where status = '0' and UNIX_TIMESTAMP(DATE_FORMAT(startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and productID = ?";
		List<GiftClassify> list = getSession().createQuery(sql).setParameter(0, productId).list();
		if (list == null || list.size() == 0 || StringUtil.isEmpty(list.get(0).getLastNum())) {
			return 0;
		} else {
			return Integer.valueOf(list.get(0).getLastNum());
		}
	}
	
	/**
	 * 取得保险产品礼品信息
	 * 
	 * @param productId
	 *            保险产品id
	 * @return 礼品信息
	 */
	@SuppressWarnings("unchecked")
	public GiftClassify getGiftClassifyByProductId(String productId) {
		String sql="from GiftClassify where status = '0' and UNIX_TIMESTAMP(DATE_FORMAT(startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and productID = ?";
		List<GiftClassify> giftlist =  getSession().createQuery(sql).setParameter(0, productId).list();
		if (giftlist != null && giftlist.size() > 0) {
			return giftlist.get(0);
		}
		return null;
	}
	
	/**
	 * 取得礼品信息
	 * 
	 * @param productId
	 *            保险产品id
	 * @return 礼品信息
	 */
	@SuppressWarnings("unchecked")
	public GiftClassify getGiftClassify(String id) {
		String sql="from GiftClassify where status = '0' and UNIX_TIMESTAMP(DATE_FORMAT(startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and id = ?";
		List<GiftClassify> giftlist =  getSession().createQuery(sql).setParameter(0, id).list();
		if (giftlist != null && giftlist.size() > 0) {
			return giftlist.get(0);
		}
		return null;
	}
	/**
	 * 
	* @Title: getGiftClassifyList 
	* @Description: TODO(获取礼品列表) 
	* @return List<GiftClassify>    返回类型 
	* @author
	 */
	@SuppressWarnings("unchecked")
	public List<GiftClassify> getGiftClassifyList(Map<String,String> map){
		String sql="from GiftClassify where status = '0' and lastNum>0  and UNIX_TIMESTAMP(DATE_FORMAT(startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S'))    ";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key=entry.getKey();
			String value=entry.getValue();
			if("startindex".equals(key)||"size".equals(key)){
				continue;
			}else if("modelType".equals(key)){
				sql=sql+" and FIND_IN_SET("+value+","+key+")!=0";
				
			}else if("startpoints".equals(key)){
				sql=sql+" and points>="+value;
			}else if("endpoints".equals(key)){
				sql=sql+" and points<"+value;;
			}else{
				sql=sql+" and "+key+"='"+value+"'";
			}
		}
		sql=sql+" order by orderFlag desc, createDate desc  ";
		Query query = getSession().createQuery(sql);
		query.setFirstResult(Integer.parseInt(map.get("startindex")));  
        query.setMaxResults(Integer.parseInt(map.get("size")));  
		return query.list();
	}
	/**
	 * 
	* @Title: getGiftClassifyList_ibatis 
	* @Description: (获取礼品列表) 
	* @return DataTable    返回类型 
	* @author
	 */
	public DataTable getGiftClassifyList_ibatis(Map<String,String> map){
		String sql= "select g.*, p.ProductName from GiftClassify g left join sdproduct p on g.productId = p.productId "
				+ " where g.status = '0' and g.lastNum>0  and UNIX_TIMESTAMP(DATE_FORMAT(g.startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(g.endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S'))  ";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key=entry.getKey();
			String value=entry.getValue();
			if("startindex".equals(key)||"size".equals(key)){
				continue;
			}else if("modelType".equals(key)){
				sql=sql+" and FIND_IN_SET("+value+",g."+key+")!=0";
				
			}else if("startpoints".equals(key)){
				sql=sql+" and g.points>="+value;
			}else if("endpoints".equals(key)){
				sql=sql+" and g.points<"+value;;
			}else{
				sql=sql+" and g."+key+"='"+value+"'";
			}
		}
		sql=sql+" order by g.orderFlag desc, g.createDate desc  ";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executePagedDataTable(Integer.parseInt(map.get("size")), Integer.parseInt(map.get("startindex")));
		return dt;
	}
	/**
	 * 
	* @Title: getGiftClassifyListNum 
	* @Description: TODO(获取礼品列表数量) 
	* @return List<GiftClassify>    返回类型 
	* @author
	 */
	public int getGiftClassifyListNum(Map<String,String> map){
		String sql="select count(*) from GiftClassify where status = '0' and lastNum>0  and UNIX_TIMESTAMP(DATE_FORMAT(startDate,'%Y-%m-%d %H:%i:%S')) <= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S')) and UNIX_TIMESTAMP(DATE_FORMAT(endDate,'%Y-%m-%d %H:%i:%S')) >= UNIX_TIMESTAMP(DATE_FORMAT(now(),'%Y-%m-%d %H:%i:%S'))  ";
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key=entry.getKey();
			String value=entry.getValue();
			if("modelType".equals(key)){
				sql=sql+" and FIND_IN_SET("+value+","+key+")!=0";
				
			}else if("startpoints".equals(key)){
				sql=sql+" and points>="+value;
			}else if("endpoints".equals(key)){
				sql=sql+" and points<"+value;;
			}else{
				sql=sql+" and "+key+"='"+value+"'";
			}
//			if("startindex".equals(key)||"endindex".equals(key)){
//				continue;
//			}
			
		}
		Query query = getSession().createQuery(sql);
		return  ((Long) query.iterate().next()).intValue();
	}
}