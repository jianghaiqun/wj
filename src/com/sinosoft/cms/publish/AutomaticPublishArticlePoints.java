package com.sinosoft.cms.publish;

import com.sinosoft.cms.dataservice.ColumnUtil;
import com.sinosoft.cms.document.Article;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.RequestImpl;
import com.sinosoft.framework.User;
import com.sinosoft.framework.User.UserData;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserList;
import com.sinosoft.schema.ZCCatalogSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class AutomaticPublishArticlePoints {
	private static final Logger logger = LoggerFactory.getLogger(AutomaticPublishArticlePoints.class);

	public long syncOneArticlegAttribute(String tCatalogID, String presentID) {
		try {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(tCatalogID);
			catalog.fill();
			if (User.getCurrent() == null) {
				User.setCurrent(new UserData());
				User.setRealName("系统管理员");
				User.setUserName(UserList.ADMINISTRATOR);
				User.setLogin(true);
				User.setManager(true);
				User.setBranchInnerCode("0001");
			}
			User.getCurrent().getMap().put("_CurrentSiteID", String.valueOf(catalog.getSiteID()));

			String sql = "select a.id,isHot,isNew,marketPrice,a.name ,point,metaDescription,imageaddress,presentArticleID,b.residue,actCode from present a, stock b  where a.stock_id=b.id";
			sql += " and a.id='" + presentID + "'";
			GetDBdata gDB = new GetDBdata();

			List<Map<String, Object>> list = gDB.queryObj(sql);
			if (list == null || list.size() != 1)
				return -1;

			Map<String, Object> preset = list.get(0);

			String articleID = "";
			String id = "" + preset.get("presentArticleID");
			if (id != null && !"".equals(id) && !"0".equals(id) && !"null".equals(id)) {
				articleID = String.valueOf(id);
			}
			Mapx mp = new Mapx();
			mp.put("ArticleID", articleID);
			mp.put("CatalogID", tCatalogID);

			RequestImpl request = new RequestImpl();
			request.putAll(Article.init(mp));
			request.put("ArticleID", String.valueOf(request.get("ID")));

			// 处理产品图片
			request.put("Logo", preset.get("imageaddress"));
			if (!setArticlegAttribute(request, preset)) {
				return -1;
			}

			Article article = new Article();
			article.setRequest(request);
			if (!article.save()) {
				return -1;
			}
			return Long.parseLong(String.valueOf(request.get("ID")));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return -1;
		}
	}

	@SuppressWarnings("unchecked")
	private boolean setArticlegAttribute(RequestImpl request, Map<String, Object> preset) {
		request.put("Type", "1");
		request.put("Title", preset.get("name") == null ? "未知" : preset.get("name"));
		request.put("ShortTitle", null);
		request.put("SubTitle", null);
		request.put("Content", preset.get("metaDescription"));
		request.put("Keyword", "");
		request.put("TopFlag", "0");
		request.put("TemplateFlag", "0");
		request.put("Template", "");
		String isHot = preset.get("isHot") + "";
		String isNew = preset.get("isNew") + "";
		String Attribute = "";
		if (StringUtil.isNotEmpty(isHot) && ("true".equals(isHot) || "1".equals(isHot))) {

			Attribute += "hot";

		}
		if (StringUtil.isNotEmpty(isNew) && ("true".equals(isNew) || "1".equals(isNew))) {
			Attribute += ",newRecommend";

		}

		request.put("Attribute", Attribute);
		request.put("ContentSign", "");
		request.put("SourceSign", "");
		request.put("ClusterTarget", "");
		request.put("ReferTarget", "");
		// ---------------------------------
		request.put(ColumnUtil.PREFIX + "kaixinguo", preset.get("point"));
		request.put(ColumnUtil.PREFIX + "referenceprice", preset.get("marketPrice"));
		request.put(ColumnUtil.PREFIX + "stock", preset.get("residue"));
		request.put(ColumnUtil.PREFIX + "presentID", preset.get("id"));
		request.put(ColumnUtil.PREFIX + "actCode", preset.get("actCode"));
		return true;
	}

}
