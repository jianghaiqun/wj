package com.sinosoft.datachannel;

import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZDTBItemInfoSchema;
import com.sinosoft.schema.ZDTBItemInfoSet;
import com.sinosoft.schema.ZDTBShopDataSchema;
import com.sinosoft.schema.ZDTBShopInfoSchema;
import com.sinosoft.schema.ZDTBShopInfoSet;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TBDataCountManager extends ConfigEanbleTaskManager {
	public static final String CODE = "com.sinosoft.datachannel.TBDataCountManager";

	public boolean isRunning(long id) {
		return false;
	}

	public Mapx getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("1", "淘宝同业店铺URL抓取");
		map.put("2", "淘宝同业店铺宝贝ID抓取");
		map.put("3", "淘宝同业数据监控");
		map.put("4", "重新监控失败的淘宝同业数据");
		return map;
	}

	public void execute(long id) {
		if ("1".equals(id + "")) {
			countTBShopUrl();
		} else if ("2".equals(id + "")) {
			countTBItemId();
		} else if ("3".equals(id + "")) {
			countTBSellData();
		} else if ("4".equals(id + "")) {
			recountFailedData();
		}
	}
	
	/**
	 * 淘宝同业店铺URL抓取
	 */
	private void countTBShopUrl() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String indexUrl = "http://baoxian.taobao.com/";
		HttpGet httpGet = new HttpGet(indexUrl);
		try {
			// 访问淘宝保险首页
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			String strResult = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
			
			// 截取品牌商家部分的源代码，通过字符串处理，截取其中的店铺域名和店铺简称，并按天猫分类进行存储
			strResult = strResult.substring(strResult.indexOf("<div class=\"floor floor-main clearfix\">"),
					strResult.indexOf("<div class=\"nav-item item-car item-even\">"));
			
			// 按天猫店铺大类对店铺进行分组
			String[] subStrs = strResult.split("<dt>");
			for (int i = 1; i < subStrs.length; i++) {
				String subStr = subStrs[i];
				
				// 存储店铺大类信息
				String companyType = subStr.substring(0, subStr.indexOf("</dt>"));
				ZDTBShopInfoSchema shopInfo = new ZDTBShopInfoSchema();
				ZDTBShopInfoSet infoSet = shopInfo.query(new QueryBuilder(" WHERE ShopName = ?", companyType));
				
				// 店铺大类Id需要作为子店铺的ParentId
				String parentId = NoUtil.getMaxNo("TBShopUrl", 6);
				if (infoSet.size() == 0) {
					shopInfo.setId(parentId);
					shopInfo.setParentId("0");
					shopInfo.setShopName(companyType);
					shopInfo.setShopUrl("");
					shopInfo.setLevel("0");
					shopInfo.setCreateDate(new Date());
					if (!shopInfo.fill()) {
						shopInfo.insert();
					} else {
						logger.error("淘宝同业店铺数据插入有误！店铺类别信息：{}", companyType);
					}
				}
				
				// 存储店铺信息
				int start = 0, end = 0;
				while (true) {
					start = subStr.indexOf("<a href=\"//", start);
					end = subStr.indexOf("\" target=\"_blank\">", start);
					if (start == -1) {
						break;
					} else {
						start += 11;
						String shopUrl = "http://" + subStr.substring(start, end).trim();
						
						String shopName = subStr.substring(subStr.indexOf("\" target=\"_blank\">", start) + 18, 
								subStr.indexOf("</a>", start)).trim();
						
						// 判断ZDTBShopInfo表中是否已存储当前店铺信息，若没有，则将新数据插入
						infoSet = shopInfo.query(new QueryBuilder(" WHERE ShopName = ? AND ShopUrl = ?", shopName, shopUrl));
						if (infoSet.size() == 0) {
							shopInfo.setId(NoUtil.getMaxNo("TBShopUrl", 6));
							shopInfo.setParentId(parentId);
							shopInfo.setShopName(shopName);
							shopInfo.setShopUrl(shopUrl);
							shopInfo.setLevel("1");
							shopInfo.setCreateDate(new Date());
							if (!shopInfo.fill()) {
								shopInfo.insert();
							} else {
								logger.error("淘宝同业店铺数据插入有误！店铺信息：{}", shopName + ":" + shopUrl);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 淘宝同业店铺宝贝ID抓取
	 */
	private void countTBItemId() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 存储宝贝ID和对应的店铺ID
		Map<String, String> itemIds = new HashMap<String, String>();
		// 获取所有店铺的ID和域名
		QueryBuilder qb = new QueryBuilder("SELECT Id,ShopUrl FROM ZDTBShopInfo WHERE Level = '1' ORDER BY Id");
		DataTable dt = qb.executeDataTable();
		
		if (dt.getRowCount() == 0) {
			return;
		}
		// 通过流水号求模运算，获取本次抓取的店铺信息
		int index = Integer.parseInt(NoUtil.getMaxNo("TBItemGet")) % dt.getRowCount() - 1;

		// 遍历每家店铺的所有搜索页，获取店铺的所有宝贝ID集合
		String shopId = dt.getString(index, "Id");
		String shopUrl = dt.getString(index, "ShopUrl");
		
		logger.info("-------开始淘宝宝贝ID抓取，店铺URL：{}", shopUrl);
		for (int i = 1;; i++) {
			// 拼装店铺搜索页URL并访问，获取源代码
			String searchUrl = shopUrl + "/search.htm?pageNo=" + i;
			HttpGet httpGet = new HttpGet(searchUrl);
			boolean isEnd = true; // 判断当前搜索页是否为最后一页的标识
			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				String strResult = EntityUtils.toString(entity);
				EntityUtils.consume(entity);

				// 通过字符串处理，截取页面上所有宝贝ID
				int start = 0;
				while (true) {
					start = strResult.indexOf("detail.tmall.com/item.htm?id=", start);
					if (start == -1) {
						break;
					} else {
						start += 29;
						String itemId = strResult.substring(start, start + 11);
						// 将不重复的宝贝ID放入集合，若页面有不重复的宝贝ID，则证明不是最后一页搜索页
						if (!itemIds.containsKey(itemId)) {
							itemIds.put(itemId, shopId);
							isEnd = false;
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (isEnd) {
				break;
			}
		}
		
		if (itemIds.size() == 0) {
			logger.error("-------淘宝宝贝ID抓取失败，访问店铺宝贝搜索页时被屏蔽，店铺URL：{}", shopUrl);
		} else {
			logger.info("-------淘宝宝贝ID抓取成功，店铺URL：{}", shopUrl);
		}

		// 遍历宝贝ID集合，判断ZDTBItemInfo表中是否已存储当前宝贝信息，若没有，则将新数据插入
		for (String itemId : itemIds.keySet()) {
			ZDTBItemInfoSchema itemInfo = new ZDTBItemInfoSchema();
			ZDTBItemInfoSet infoSet = itemInfo.query(new QueryBuilder(" WHERE ItemId = ?", itemId));
			if (infoSet.size() == 0) {
				itemInfo.setItemId(itemId);
				itemInfo.setShopId(itemIds.get(itemId));
				itemInfo.setIsNew("1");
				itemInfo.setCreateDate(new Date());
				itemInfo.setModifyDate(new Date());
				if (!itemInfo.fill()) {
					itemInfo.insert();
				} else {
					logger.error("淘宝同业店铺宝贝ID数据插入有误！宝贝ID：{}", itemId);
				}
			}
		}
	}
	
	/**
	 * 淘宝同业数据监控
	 */
	private void countTBSellData() {
		logger.info("-------淘宝同业数据统计开始------");
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		// 获取所有宝贝ID集合
		QueryBuilder qb = new QueryBuilder("SELECT ItemId FROM ZDTBItemInfo WHERE IsNew = '1'");
		DataTable dt = qb.executeDataTable();
		
		// 遍历宝贝ID集合，拼装宝贝信息查询URL并访问
		for (int i = 0; i < dt.getRowCount(); i++) {
			String itemId = dt.getString(i, "ItemId").trim();
			String queryUrl = "http://baoxian.taobao.com/json/item/info.do?item_id=" + itemId;
			HttpGet httpGet = new HttpGet(queryUrl);
			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				String strResult = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
				
				// 将相应结果的json字符串转换为map并获取需要的键值对
				Map resultMap = null;
				try {
					resultMap = new ObjectMapper().readValue(strResult, Map.class);
				} catch (Exception e) {
					logger.error("获取淘宝商品信息被屏蔽！宝贝ID：" + itemId + e.getMessage(), e);
				}
				
				// 获取商品信息失败时，插入空数据
				if (resultMap == null) {
					ZDTBShopDataSchema shopData = new ZDTBShopDataSchema();
					shopData.setId(NoUtil.getMaxNo("TBShopData"));
					shopData.setItemName("获取失败");
					shopData.setItemID(itemId);
					shopData.setSellCount("获取失败");
					
					String sql2= "SELECT b.Id AS ShopId,ShopName FROM ZDTBItemInfo a, ZDTBShopInfo b WHERE b.Id = a.ShopId AND a.ItemId = ?";
					QueryBuilder qb2 = new QueryBuilder(sql2, itemId);
					DataTable dt2 = qb2.executeDataTable();
					if (dt2.getRowCount() > 0) {
						shopData.setShopId(dt2.getString(0, "ShopId"));
						shopData.setShopName(dt2.getString(0, "ShopName"));
					}
					shopData.setCreateDate(new Date());
					shopData.setModifyDate(new Date());
					if (!shopData.fill()) {
						shopData.insert();
					} else {
						logger.error("淘宝同业统计数据插入有误！商品ID：{}", itemId);
					}
				} else {
					// 若能获取到销量值，则说明是新接口产品，将相应信息存入ZDTBShopData表
					if (resultMap.get("sellCount") != null) {
						String sellCount = resultMap.get("sellCount").toString();
						String price = resultMap.get("price").toString();
						String insType = resultMap.get("insType").toString();
						String title = resultMap.get("title").toString();
						String shopName = resultMap.get("shopName").toString();
						
						ZDTBShopDataSchema shopData = new ZDTBShopDataSchema();
						shopData.setId(NoUtil.getMaxNo("TBShopData"));
						shopData.setItemName(title);
						shopData.setItemID(itemId);
						
						// 联表查询获取店铺ID
						String sql2 = "SELECT b.Id AS ShopId FROM ZDTBItemInfo a, ZDTBShopInfo b WHERE b.Id = a.ShopId AND a.ItemId = ?";
						QueryBuilder qb2 = new QueryBuilder(sql2, itemId);
						DataTable dt2 = qb2.executeDataTable();
						if (dt2.getRowCount() > 0) {
							shopData.setShopId(dt2.getString(0, "ShopId"));
						}
						shopData.setShopName(shopName);
						shopData.setInsType(insType);
						shopData.setInsTypeName(getTBInsTypeName(insType));
						
						// 获取当日销量
						String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
						String tminutes = " 23:59:59";
						String sql3 = "SELECT SellCount FROM ZDTBShopData WHERE ItemId = ? AND ModifyDate <= ? ORDER BY ModifyDate DESC";
						DataTable dt3 = new QueryBuilder(sql3, itemId, yesterday + tminutes).executeDataTable();
						if (dt3.getRowCount() > 0 && StringUtil.isNotEmpty(dt3.getString(0, "SellCount"))) {
							Integer tSellCount = Integer.parseInt(sellCount) - Integer.parseInt(dt3.getString(0, "SellCount"));
							shopData.setTSellCount(tSellCount.toString());
						}
						
						shopData.setSellCount(sellCount);
						shopData.setPrice(price);
						shopData.setCreateDate(new Date());
						shopData.setModifyDate(new Date());
						
						if (!shopData.fill()) {
							shopData.insert();
						} else {
							logger.error("淘宝同业统计数据插入有误！商品信息：{}", title + ":" + shopName);
						}
					} else {
						// 更新ZDTBItemInfo表中老接口商品的数据
						try {
							String sql1 = "UPDATE ZDTBItemInfo SET IsNew = '0',ModifyDate = NOW() WHERE ItemId = ?";
							QueryBuilder qb1 = new QueryBuilder(sql1, itemId);
							qb1.executeNoQuery();
						} catch (Exception e) {
							logger.error("更新ZDTBItemInfo表中老接口商品异常，itemId：" + itemId + e.getMessage(), e);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		logger.info("-------淘宝同业数据统计完成------");
	}
	
	/**
	 * 重新监控失败的淘宝同业数据（昨日、今日）
	 */
	private void recountFailedData() {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		String yesterday = DateUtil.toString(DateUtil.addDay(new Date(), -1), "yyyy-MM-dd");
		String sql = "SELECT Id,ItemID FROM ZDTBShopData WHERE SellCount = '获取失败' AND ModifyDate >= ?";
		DataTable dt = new QueryBuilder(sql, yesterday).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String id = dt.getString(i, "Id");
			String itemId = dt.getString(i, "ItemId").trim();
			String queryUrl = "http://baoxian.taobao.com/json/item/info.do?item_id=" + itemId;
			HttpGet httpGet = new HttpGet(queryUrl);
			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				String strResult = EntityUtils.toString(entity);
				EntityUtils.consume(entity);
				
				// 将相应结果的json字符串转换为map并获取需要的键值对
				Map resultMap = null;
				try {
					resultMap = new ObjectMapper().readValue(strResult, Map.class);
				} catch (Exception e) {
					logger.error("获取淘宝商品信息被屏蔽！宝贝ID：" + itemId + e.getMessage(), e);
				}
				
				if (resultMap != null) {
					// 若能获取到销量值，则说明是新接口产品，将相应信息更新到ZDTBShopData表
					if (resultMap.get("sellCount") != null) {
						String sellCount = resultMap.get("sellCount").toString();
						String price = resultMap.get("price").toString();
						String insType = resultMap.get("insType").toString();
						String title = resultMap.get("title").toString();
						String shopName = resultMap.get("shopName").toString();
						
						// 联表查询获取店铺ID
						String shopId = "";						
						String sql2= "SELECT b.Id AS ShopId FROM ZDTBItemInfo a, ZDTBShopInfo b WHERE b.Id = a.ShopId AND a.ItemId = ?";
						QueryBuilder qb2 = new QueryBuilder(sql2, itemId);
						DataTable dt2 = qb2.executeDataTable();
						if (dt2.getRowCount() > 0) {
							shopId = dt2.getString(0, "ShopId");
						}
						
						// 获取当日销量
						String tSellCount = "";
						String tminutes = " 23:59:59";
						String sql3 = "SELECT SellCount FROM ZDTBShopData WHERE ItemId = ? AND ModifyDate <= ? ORDER BY ModifyDate DESC";
						DataTable dt3 = new QueryBuilder(sql3, itemId, yesterday + tminutes).executeDataTable();
						if (dt3.getRowCount() > 0 && StringUtil.isNotEmpty(dt3.getString(0, "SellCount"))) {
							Integer tSellCountNum = Integer.parseInt(sellCount) - Integer.parseInt(dt3.getString(0, "SellCount"));
							tSellCount = tSellCountNum.toString();
						}

						String updateSql ="UPDATE ZDTBShopData SET ItemName=?, ShopId=?, InsType=?, InsTypeName=?, "
								+ " TSellCount=?, SellCount=?, Price=?, ModifyDate = NOW() WHERE Id = ?";
						QueryBuilder updateQB = new QueryBuilder(updateSql);
						updateQB.add(title);
						updateQB.add(shopId);
						updateQB.add(insType);
						updateQB.add(getTBInsTypeName(insType));
						updateQB.add(tSellCount);
						updateQB.add(sellCount);
						updateQB.add(price);
						updateQB.add(id);
						
						try {
							updateQB.executeNoQuery();
						} catch (Exception e) {
							logger.error("淘宝同业数据失败后重新统计，新数据更新有误！商品信息：" + title + ":" + shopName + e.getMessage(), e);
						}
					} else {
						// 更新ZDTBItemInfo表中老接口商品的数据
						try {
							String sql1 = "UPDATE ZDTBItemInfo SET IsNew = '0',ModifyDate = NOW() WHERE ItemId = ?";
							QueryBuilder qb1 = new QueryBuilder(sql1, itemId);
							qb1.executeNoQuery();
						} catch (Exception e) {
							logger.error("更新ZDTBItemInfo表中老接口商品异常，itemId：" + itemId + e.getMessage(), e);
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 获取险种名称
	 * @param insType
	 * @return
	 */
	private String getTBInsTypeName(String insType) {
		String insTypeName = "";
		switch (Integer.parseInt(insType)) {
		case 100:
			insTypeName = "意外险";
			break;
		case 200:
			insTypeName = "旅游险";
			break;
		case 300:
			insTypeName = "财产险";
			break;
		case 400:
			insTypeName = "健康险";
			break;
		case 500:
			insTypeName = "寿险";
			break;
		default:
			insTypeName = "其它";
			break;
		}
		return insTypeName;
	}
	
	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "淘宝同业数据监控定时任务";
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.sinosoft.datachannel.TBDataCountManager";
	}
	
}
