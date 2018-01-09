package com.sinosoft.cms.webservice;

import com.sinosoft.cms.api.ArticleAPI;
import com.sinosoft.cms.api.CatalogAPI;
import com.sinosoft.cms.api.MemberAPI;
import com.sinosoft.cms.api.UserAPI;
import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.publish.AutomaticPublishArticle;
import com.sinosoft.cms.publish.AutomaticPublishArticlePoints;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCSiteSchema;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.FMRisk;
import com.sinosoft.webservice.productInfo.ProductInfoWebServiceStub.ProductInfoResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * webservice接口实现
 * 
 * @author lanjun
 * 
 */

public class CmsServiceImpl implements CmsService {

	private static final Logger logger = LoggerFactory.getLogger(CmsServiceImpl.class);
	/**
	 * 新建文章
	 * 
	 * @param catalogID
	 *            栏目id
	 * @param title
	 *            标题
	 * @param author
	 *            作者
	 * @param content
	 *            文章内容
	 * @param publishDate
	 *            发布日期
	 * @return
	 */

	public long addArticle(long catalogID, String present_id) {
		// String sql =
		// "select isbest,ishot,isnew,marketprice,a.name title,point,metadescription,imageaddress from present a, stock b  where a.stock_id=b.id";
		// sql += " and a.id='" + present_id + "'";
		// GetDBdata gDB = new GetDBdata();
		// try {
		// java.util.List<HashMap<String, String>> list = gDB.query(sql);
		// if (list == null || list.size() != 1)
		// return -1;
		// HashMap<String, String> preset = list.get(0);
		//
		// ArticleAPI p = new ArticleAPI();
		// ZCArticleSchema article = new ZCArticleSchema();
		// article.setCatalogID(catalogID);
		// article.setTitle(preset.get("title"));
		// article.setAuthor(User.getUserName());
		// article.setContent(preset.get("metadescription"));
		// article.setPublishDate(new Date());
		// article.setLogo(preset.get("imageaddress"));
		// p.setSchema(article);
		// if (p.insert() > 0) {
		// return article.getID();
		// } else {
		// return -1;
		// }
		// } catch (Exception e) {
		// logger.error(e.getMessage(), e);
		// return -1;
		// }

		try {
			AutomaticPublishArticlePoints auto = new AutomaticPublishArticlePoints();
			return auto.syncOneArticlegAttribute(catalogID + "", present_id);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * 获取文章详细信息
	 * 
	 * @param articleID
	 * @return
	 */
	public String showArticleURL(long articleID) {
		try {
			ZCArticleSchema article = new ZCArticleSchema();
			ZCArticleSet set = article.query(new QueryBuilder("where id =? or id in(select id from zcarticle where refersourceid=? )", articleID, articleID));
			if (set.size() > 0) {
				return set.get(0).getURL();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 新建栏目
	 * 
	 * @param parentID
	 *            上级栏目ID
	 * @param name
	 *            栏目名称
	 * @param type
	 *            栏目类型
	 * @param alias
	 *            别名
	 * @return
	 */
	public long addCatalog(long parentID, String name, int type, String alias) {
		Mapx params = new Mapx();
		params.put("ParentID", "" + parentID);
		params.put("Name", name);
		params.put("Type", "" + type);
		params.put("Alias", alias);
		CatalogAPI c = new CatalogAPI();
		c.setParams(params);

		if (c.insert() > 0) {
			return 1;
		} else
			return -1;
	}

	/**
	 * 发布文章引导图片
	 * 
	 * @param ID
	 *            栏目ID
	 * @return
	 */
	public String publishArticleImage(long catalogID, String filePath, String imageType) {
		ZCCatalogSchema zccSchema = new ZCCatalogSchema();
		zccSchema.setID(catalogID);
		try {
			if (zccSchema.fill()) {
				ZCSiteSchema zcssSchema = new ZCSiteSchema();
				zcssSchema.setID(zccSchema.getSiteID());
				if (zcssSchema.fill()) {
					CmsServiceImpl csi = new CmsServiceImpl();
					String url = csi.getClassPath() + "" + zcssSchema.getAlias() + "/";
					if (url.endsWith("/")) {
						url += "upload/Image/mrtp/";
					} else {
						url += "/upload/Image/mrtp/";
					}
					int lastindex = filePath.lastIndexOf("\\");
					if (lastindex == -1) {
						lastindex = filePath.lastIndexOf("/");
					}
					String filename = filePath.substring(lastindex + 1, filePath.lastIndexOf("."));
					File f1 = new File(filePath);
					File f2 = new File(url + "" + filename + "." + imageType);
					FileUtils.copyFile(f1, f2);
					return "upload/Image/mrtp/" + filename + "." + imageType;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public String getClassPath() throws Exception {
		try {
			String strClassName = getClass().getName();
			String strPackageName = "";
			if (getClass().getPackage() != null) {
				strPackageName = getClass().getPackage().getName();
			}
			String strClassFileName = "";
			if (!"".equals(strPackageName)) {
				strClassFileName = strClassName.substring(strPackageName.length() + 1, strClassName.length());
			} else {
				strClassFileName = strClassName;
			}
			URL url = null;
			url = getClass().getResource(strClassFileName + ".class");
			String strURL = url.toString();
			Properties prop = System.getProperties();
			String os = prop.getProperty("os.name");

			if (os.startsWith("win") || os.startsWith("Win")) {
				strURL = strURL.substring(strURL.indexOf('/') + 1, strURL.lastIndexOf('/'));
			} else {
				strURL = strURL.substring(strURL.indexOf('/'), strURL.lastIndexOf('/'));
			}
			// 返回当前类的路径，并且处理路径中的空格，因为在路径中出现的空格如果不处理的话，
			// 在访问时就会从空格处断开，那么也就取不到完整的信息了，这个问题在web开发中尤其要注意
			return strURL.replaceAll("%20", " ").replace("/WEB-INF/classes/com/sinosoft/cms/webservice", "/wwwroot/");
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw ex;
		}
	}

	public static void main(String[] args) {
		try {
			File f1 = new File("D:\\我的酷盘\\kaixinbao\\preview\\7-19\\首页\\Kaixinbao_home_v5_首页_未登录.jpg");
			File f2 = new File("D:/workspace/cms/WebContent/wwwroot/test/upload/Image/mrtp/Kaixinbao_home_v5_首页_未登录.jpg");
			FileUtils.copyFile(f1, f2);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	/**
	 * 新建用户
	 * 
	 * @param userName
	 *            用户名
	 * @param realName
	 *            真实姓名
	 * @param password
	 *            密码
	 * @param md5pass
	 *            是否需要加密
	 * @param departID
	 *            机构编码
	 * @param email
	 *            邮箱
	 * 
	 * @return -1 失败 0 用户已存在 1 成功
	 */
	public long addUser(String userName, String realName, String password, String departID, String email) {
		Mapx params = new Mapx();
		params.put("Username", "" + userName);
		params.put("RealName", realName);
		params.put("Password", "" + password);
		params.put("Email", email);
		params.put("BranchInnerCode", departID);
		if (StringUtil.isEmpty(departID)) {
			params.put("BranchInnerCode", "0001");
		}
		UserAPI u = new UserAPI();
		u.setParams(params);
		return u.insert();

	}

	/**
	 * 删除文章
	 * 
	 * @param articleID
	 *            文章ID
	 * @return
	 */
	public boolean deleteArticle(long articleID) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(articleID);
		if (article.fill()) {
			ArticleAPI p = new ArticleAPI();
			p.setSchema(article);
			return p.delete();
		} else {
			return false;
		}
	}

	/**
	 * 删除栏目
	 * 
	 * @param ID
	 *            栏目ID
	 * @return
	 */
	public boolean deleteCatalog(long ID) {
		Mapx params = new Mapx();
		params.put("CatalogID", "" + ID);
		CatalogAPI c = new CatalogAPI();
		c.setParams(params);
		return c.delete();
	}

	/**
	 * 删除用户
	 * 
	 * @param userName
	 * @return
	 */
	public boolean deleteUser(String userName) {
		Mapx params = new Mapx();
		params.put("Username", userName);
		UserAPI u = new UserAPI();
		u.setParams(params);
		return u.delete();
	}

	/**
	 * 编辑文章
	 * 
	 * @param articleID
	 *            文章ID
	 * @param title
	 *            文章标题
	 * @param author
	 *            作者
	 * @param content
	 *            文章内容
	 * @param publishDate
	 *            发布日期
	 * @return
	 */
	public boolean editArticle(long articleID, String title, String author, String content, String publishDate) {
		Mapx params = new Mapx();
		params.put("DocID", "" + articleID);
		params.put("Title", title);
		params.put("Author", author);
		params.put("Cotent", content);
		params.put("PublishDate", publishDate);
		ArticleAPI a = new ArticleAPI();
		a.setParams(params);
		return a.update();
	}

	/**
	 * 修改栏目
	 * 
	 * @param ID
	 *            栏目ID
	 * @param name
	 *            栏目名称
	 * @param alias
	 *            栏目别名
	 * @return
	 */
	public boolean editCatalog(long ID, String name, String alias) {
		Mapx params = new Mapx();
		params.put("CatalogID", "" + ID);
		params.put("Name", name);
		params.put("Alias", alias);
		CatalogAPI c = new CatalogAPI();
		c.setParams(params);
		return c.update();

	}

	/**
	 * 编辑用户
	 * 
	 * @param userName
	 *            用户名
	 * @param realName
	 *            真实姓名
	 * @param password
	 *            密码
	 * @param md5pass
	 *            是否需要加密
	 * @param departID
	 *            机构编码
	 * @param email
	 *            邮箱
	 * 
	 * @return true 成功 false 失败
	 */
	public boolean editUser(String userName, String realName, String password, String departID, String email) {
		Mapx params = new Mapx();
		params.put("Username", "" + userName);
		params.put("RealName", realName);
		params.put("Password", "" + password);
		params.put("BranchInnerCode", departID);
		if (StringUtil.isEmpty(departID)) {
			params.put("BranchInnerCode", "0001");
		}
		params.put("Email", email);
		UserAPI u = new UserAPI();
		u.setParams(params);
		return u.update();
	}

	/**
	 * 发布文章
	 * 
	 * @param articleID
	 *            文章ID
	 * @return
	 */
	public boolean publishArticle(long articleID) {

		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder("where id =? or id in(select id from zcarticle where refersourceid=? )", articleID, articleID));
		if (set.size() > 0) {
			Publisher p = new Publisher();
			return p.publishArticle(set);
		}
		return false;
	}

	/**
	 * 发布栏目
	 * 
	 * @param ID
	 *            栏目ID
	 * @return
	 */
	public boolean publishCatalog(final long ID) {
		try {
			LongTimeTask ltt = new LongTimeTask() {
				public void execute() {
					Publisher p = new Publisher();
					p.publishCatalog(ID, true, true, true ,this);
					setPercent(100);
				}
			};
			ltt.start();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public long addMember(String UserName, String RealName, String PassWord, String Email) {
		Mapx params = new Mapx();
		params.put("UserName", "" + UserName);
		params.put("PassWord", PassWord);
		params.put("RealName", RealName);
		params.put("Email", "" + Email);
		MemberAPI m = new MemberAPI();
		m.setParams(params);
		return m.insert();
	}

	public boolean deleteMember(String UserName) {
		Mapx params = new Mapx();
		params.put("UserName", UserName);
		MemberAPI m = new MemberAPI();
		m.setParams(params);
		return m.delete();
	}

	public boolean editMember(String UserName, String RealName, String PassWord, String Email) {
		Mapx params = new Mapx();
		params.put("UserName", "" + UserName);
		params.put("RealName", "" + RealName);
		params.put("PassWord", PassWord);
		params.put("Email", "" + Email);
		MemberAPI m = new MemberAPI();
		m.setParams(params);
		return m.update();
	}

	@Override
	public boolean publishArticle(String riskcode, String catalogID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("RiskCode", new String[] { riskcode });
		try {
			ProductInfoResponse productInfo = ProductWebservice.ProductInfoSereviceImpl(map, null);
			FMRisk[] list = productInfo.getFMRisk();
			if (list != null && list.length > 0 && list[0] != null) {
				if (!AutomaticPublishArticle.publishing(list[0], catalogID)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			logger.error("同步产品数据失败：" + e.getMessage(), e);
		}
		return false;
	}

}
