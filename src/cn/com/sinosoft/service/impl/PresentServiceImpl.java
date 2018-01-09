package cn.com.sinosoft.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import cn.com.sinosoft.bean.Pager;
import cn.com.sinosoft.bean.PresentImage;
import cn.com.sinosoft.dao.PresentDao;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.entity.Present;
import cn.com.sinosoft.entity.PresentCategory;
import cn.com.sinosoft.service.HtmlService;
import cn.com.sinosoft.service.PresentService;
import cn.com.sinosoft.util.PropertiesConfig;
import cn.com.sinosoft.webservice.ArticleService;

/**
 * Service实现类 - 商品
 * ============================================================================
 * KEY:SINOSOFT603F6052C7BD6E1C6113519369127127
 * ============================================================================
 */

@Service
public class PresentServiceImpl extends BaseServiceImpl<Present, String> implements PresentService {
	public static final String PRESENT_CONTENT = "presentContent";// 礼品内容
	@Resource
	private PresentDao presentDao;
	@Resource
	private HtmlService htmlService;

	@Resource
	public void setBaseDao(PresentDao presentDao) {
		super.setBaseDao(presentDao);
	}

	public List<Present> getPresentList(PresentCategory presentCategory) {
		return presentDao.getPresentList(presentCategory);
	}

	public List<Present> getPresentList(int firstResult, int maxResults) {
		return presentDao.getPresentList(firstResult, maxResults);
	}

	public List<Present> getPresentList(PresentCategory presentCategory, int firstResult, int maxResults) {
		return presentDao.getPresentList(presentCategory, firstResult, maxResults);
	}

	public Pager getPresentPager(PresentCategory presentCategory, Pager pager) {
		return presentDao.getPresentPager(presentCategory, pager);
	}

	// 首页展现产品
	public List<Present> getIndexPresentList(PresentCategory presentCategory) {
		return presentDao.getIndexPresentList(presentCategory);
	}

	// =============================首页产品中心==================================================
	public Pager getPresentHomePager(Pager pager) {
		return presentDao.getPresentHomePager(pager);
	}

	// =============================首页产品中心==================================================

	public List<Present> getBestPresentList(int maxResults) {
		return presentDao.getBestPresentList(maxResults);
	}

	public List<Present> getBestPresentList(PresentCategory presentCategory, int maxResults) {
		return presentDao.getBestPresentList(presentCategory, maxResults);
	}

	public List<Present> getHotPresentList(int maxResults) {
		return presentDao.getHotPresentList(maxResults);
	}

	public List<Present> getHotPresentList(PresentCategory presentCategory, int maxResults) {
		return presentDao.getHotPresentList(presentCategory, maxResults);
	}

	public List<Present> getNewPresentList(int maxResults) {
		return presentDao.getNewPresentList(maxResults);
	}
	public List<Present> getNewPresentList(PresentCategory presentCategory, int maxResults) {
		return presentDao.getNewPresentList(presentCategory, maxResults);
	}

	public List<Present> getPresentList(Date beginDate, Date endDate, int firstResult, int maxResults) {
		return presentDao.getPresentList(beginDate, endDate, firstResult, maxResults);
	}

//	public Pager search(Pager pager) {
//		Compass compass = compassTemplate.getCompass();
//		CompassSession compassSession = compass.openSession();
//		CompassQueryBuilder compassQueryBuilder = compassSession.queryBuilder();
//		CompassBooleanQueryBuilder compassBooleanQueryBuilder = compassQueryBuilder.bool();
//
//		CompassQuery compassQuery = compassBooleanQueryBuilder.addMust(compassQueryBuilder.term("isMarketable", true)).addMust(
//				compassQueryBuilder.queryString("name:*" + pager.getKeyword() + "*").toQuery()).toQuery();
//
//		if (StringUtils.isEmpty(pager.getOrderBy()) || pager.getOrderType() == null) {
//			compassQuery.addSort("isBest", SortPropertyType.STRING, SortDirection.REVERSE).addSort("isNew", SortPropertyType.STRING, SortDirection.REVERSE).addSort("isHot", SortPropertyType.STRING,
//					SortDirection.REVERSE);
//		} else {
//			if (pager.getOrderType() == OrderType.asc) {
//				if (StringUtils.equalsIgnoreCase(pager.getOrderBy(), "price")) {
//					compassQuery.addSort("price", SortPropertyType.FLOAT);
//				}
//			} else {
//				if (StringUtils.equalsIgnoreCase(pager.getOrderBy(), "price")) {
//					compassQuery.addSort("price", SortPropertyType.FLOAT, SortDirection.REVERSE);
//				}
//			}
//		}
//
//		CompassHits compassHits = compassQuery.hits();
//
//		List<Present> presentList = new ArrayList<Present>();
//
//		int firstResult = (pager.getPageNumber() - 1) * pager.getPageSize();
//		int maxReasults = pager.getPageSize();
//		int totalCount = compassHits.length();
//
//		int end = Math.min(totalCount, firstResult + maxReasults);
//		for (int i = firstResult; i < end; i++) {
//			Present present = (Present) compassHits.data(i);
//			presentList.add(present);
//		}
//		compassSession.close();
//		pager.setList(presentList);
//		pager.setTotalCount(totalCount);
//		return pager;
//	}

	public Pager getFavoritePresentPager(Member member, Pager pager) {
		return presentDao.getFavoritePresentPager(member, pager);
	}

	public Long getStoreAlertCount() {
		return presentDao.getStoreAlertCount();
	}

	public Long getMarketablePresentCount() {
		return presentDao.getMarketablePresentCount();
	}

	public Long getUnMarketablePresentCount() {
		return presentDao.getUnMarketablePresentCount();
	}

	// present属性获取测试
	public List<Present> getAllPresentAttr(String id) {
		return presentDao.getAllPresentAttr(id);
	}

	// present属性获取测试

	// 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
	@Override
	public void delete(Present present) {
		// File htmlFile = new
		// File(ServletActionContext.getServletContext().getRealPath(present.getHtmlFilePath()));
		// if (htmlFile.exists()) {
		// htmlFile.delete();
		// }

//		List<PresentImage> presentImageList = present.getPresentImageList();
//		if (presentImageList != null) {
//			for (PresentImage presentImage : presentImageList) {
//				File sourcePresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(presentImage.getSourcePresentImagePath()));
//				if (sourcePresentImageFile.exists()) {
//					sourcePresentImageFile.delete();
//				}
//				File bigPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(presentImage.getBigPresentImagePath()));
//				if (bigPresentImageFile.exists()) {
//					bigPresentImageFile.delete();
//				}
//				File smallPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(presentImage.getSmallPresentImagePath()));
//				if (smallPresentImageFile.exists()) {
//					smallPresentImageFile.delete();
//				}
//				File thumbnailPresentImageFile = new File(ServletActionContext.getServletContext().getRealPath(presentImage.getThumbnailPresentImagePath()));
//				if (thumbnailPresentImageFile.exists()) {
//					thumbnailPresentImageFile.delete();
//				}
//			}
//		}
//
//		ArticleService.deleteArticle(present.getPresentArticleID());

		presentDao.delete(present);
	}

	// 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
	@Override
	public void delete(String id) {
		Present present = presentDao.load(id);
		this.delete(present);
	}

	// 重写方法，删除对象的同时删除已生成的HTML静态文件、商品图片文件
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	// 重写方法，保存对象的同时处理价格精度并生成HTML静态文件
	@Override
	public String save(Present present) {
		String id = presentDao.save(present);
//		presentDao.flush();
//		presentDao.evict(present);
//		if (id != null) {
//			// 插入cms数据库zcarticle表内,并发布文章产生路径id;
//			Present present_update = presentDao.load(id);
//			long ArticleID = ArticleService.addArticle(Long.parseLong(present.getCatalog()), present_update.getId());
//			present_update.setPresentArticleID(ArticleID);
//			String URL = ArticleService.showArticleURL(ArticleID);
//			present_update.setHtmlFilePath("../" + URL);
//			String istcontent = htmlService.presentContentListBuildHtml(present_update);
//			present_update.setListcontent(istcontent);
//			presentDao.update(present_update);
//		}
		return id;
	}

	// 重写方法，更新对象的同时处理价格精度并重新生成HTML静态文件
	@Override
	public void update(Present present) {
		String id = present.getId();
		String istcontent = htmlService.presentContentListBuildHtml(present);
		present.setListcontent(istcontent);
		presentDao.update(present);
//		presentDao.flush();
//		presentDao.evict(present);

//		ArticleService.addArticle(Long.parseLong(present.getCatalog()), id);
	}

	public List getAlreadyExchangeList() {

		return null;
	}

	public List getNoExchangeList() {
		return null;
	}
	public List<Present> getNewPresentList() {
		return presentDao.getNewPresentList();
	}
	public Present getNewPresentInfo(String id) {
		return presentDao.getNewPresentInfo(id);
	}
	public List<Present> getMobilePresentInfo(String htmlFilePath,String actCode) {
		return presentDao.getMobilePresentInfo(htmlFilePath,actCode);
	}
}