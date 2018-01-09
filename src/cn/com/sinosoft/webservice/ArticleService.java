package cn.com.sinosoft.webservice;

import cn.com.sinosoft.util.PropertiesConfig;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

public class ArticleService {
	private static final Logger logger = LoggerFactory.getLogger(ArticleService.class);

	private static final String webServiceUrl = PropertiesConfig.getProperty("cmswebservice");

	/**
	 * 发布一个文章
	 * 
	 * @param catalogID
	 * @param title
	 * @param author
	 * @param content
	 * @param publishDate
	 * @return
	 */
	public static long addArticle(long catalogID, String present_id) {
		Service servicemodel = new ObjectServiceFactory().create(CmsService.class);
		try {
			CmsService service = (CmsService) new XFireProxyFactory().create(servicemodel, webServiceUrl);
			return service.addArticle(catalogID, present_id);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			return -1;
		}
	}

	/**
	 * 查询一个文章
	 * 
	 * @param ArticleID
	 * @return
	 */
	public static String showArticleURL(long ArticleID) {
		Service servicemodel = new ObjectServiceFactory().create(CmsService.class);
		try {
			CmsService service = (CmsService) new XFireProxyFactory().create(servicemodel, webServiceUrl);
			return service.showArticleURL(ArticleID);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 删除一篇文章
	 * 
	 * @param articleID
	 * @return
	 */
	public static boolean deleteArticle(long articleID) {
		Service servicemodel = new ObjectServiceFactory().create(CmsService.class);
		try {
			CmsService service = (CmsService) new XFireProxyFactory().create(servicemodel, webServiceUrl);
			return service.deleteArticle(articleID);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 发布一个图片
	 * 
	 * @param ArticleID
	 * @return
	 */
	public static String publishArticleImage(long catalogID, String filePath, String ImageType) {
		Service servicemodel = new ObjectServiceFactory().create(CmsService.class);
		try {
			CmsService service = (CmsService) new XFireProxyFactory().create(servicemodel, webServiceUrl);
			return service.publishArticleImage(catalogID, filePath, ImageType);
		} catch (MalformedURLException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
}
