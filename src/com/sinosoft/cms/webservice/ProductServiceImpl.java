package com.sinosoft.cms.webservice;

import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.utility.DataCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.baifendian.UploadRmItem;

public class ProductServiceImpl implements ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	/**
	 * 发布产品
	 * 
	 * @param productID
	 *            产品ID
	 * @return
	 */
	public boolean publishProduct(String productID) {
		logger.info("发布产品===================={}", productID);
		if (StringUtil.isEmpty(productID)) {
			return false;
		}
		DataCache.cleanOneProductInfo(productID);
		try {
			CmsServiceImpl csi = new CmsServiceImpl();
			return csi.publishArticle(productID, "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 下架产品
	 * 
	 * @param productID
	 *            产品ID
	 * @return
	 */
	public boolean downProduct(String productID) {
		logger.info("下架产品===================={}", productID);
		if (StringUtil.isEmpty(productID)) {
			return false;
		}
		try {
			GetDBdata db = new GetDBdata();
//			String result = db.getOneValue("select productid from sdproduct where productid='" + productID + "'");
//			if (StringUtil.isEmpty(result)) {
//				LogUtil.info("下架产品失败，产品不存在" + productID);
//				return false;
//			}
			//try {//百分点合作结束，百分点核心代码暂存
				DataCache.cleanOneProductInfo(productID);
				
				// 百分点产品下架接口
/*				UploadRmItem.downProduct(productID);
			} catch (Exception e) {
				Log.error("百分点产品下架接口异常");
				e.printStackTrace();
			}*/
			return db.execUpdateSQL("update sdproduct set ispublish='N' where productid='" + productID + "'", null);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	public static void main(String[] args) {
		ProductService tProductService=new ProductServiceImpl();
		tProductService.publishProduct("000501003");
		
	}
}
