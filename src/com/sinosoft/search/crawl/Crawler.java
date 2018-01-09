package com.sinosoft.search.crawl;

import com.sinosoft.cms.api.ArticleAPI;
import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.site.Catalog;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.extend.ExtendManager;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.script.EvalException;
import com.sinosoft.framework.script.ScriptEngine;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Filter;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.NumberUtil;
import com.sinosoft.framework.utility.RegexParser;
import com.sinosoft.framework.utility.ServletUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.ImageUtilEx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCImageSet;
import com.sinosoft.search.DocumentList;
import com.sinosoft.search.SearchUtil;
import com.sinosoft.search.WebDocument;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
	private static final Logger logger = LoggerFactory.getLogger(Crawler.class);

	private CrawlConfig config;

	private DocumentList list;// 当前文档列表

	private int startLevel;

	private FileDownloader fileDownloader = new FileDownloader();

	private UrlExtracter extracter = new UrlExtracter();

	private int currentLevel;

	private int currentLevelCount;

	private int currentLevelDownloadedCount;

	private Filter retryFilter;

	public LongTimeTask task;

	public LongTimeTask getTask() {
		return task;
	}

	public Crawler() {
		this(null);
	}

	public Crawler(LongTimeTask ltt) {
		task = ltt;
		if (ltt == null) {
			task = LongTimeTask.createEmptyInstance();
		}
	}

	private void prepareList() {
		if (list == null) {
			String path = Config.getContextRealPath() + CrawlConfig.getWebGatherDir();
			if (!path.endsWith("/") && !path.endsWith("\\")) {
				path = path + "/";
			}
			path = path + config.getID() + "/";
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			list = new DocumentList(path);
		}
	}

	public DocumentList crawl() {
		prepareList();
		try {
			list.setCrawler(this);
			fileDownloader
					.setDenyExtension(".gif.jpg.jpeg.swf.bmp.png.js.wmv.css.ico.avi.mpg.mpeg.mp3.mp4.wma.rm.rmvb.exe.tar.gz.zip.rar");
			fileDownloader.setThreadCount(config.getThreadCount());
			fileDownloader.setTimeout(config.getTimeout() * 1000);

			if (config.isProxyFlag()) {
				fileDownloader.setProxyFlag(config.isProxyFlag());
				fileDownloader.setProxyHost(config.getProxyHost());
				fileDownloader.setProxyPassword(config.getProxyPassword());
				fileDownloader.setProxyUserName(config.getProxyUserName());
				fileDownloader.setProxyPort(config.getProxyPort());
			} else if ("Y".equalsIgnoreCase(Config.getValue("Proxy.IsUseProxy"))) { // 如果系统设置了，则默认为系统配置的代理
				fileDownloader.setProxyFlag(true);
				fileDownloader.setProxyHost(Config.getValue("Proxy.Host"));
				fileDownloader.setProxyPassword(Config.getValue("Proxy.Password"));
				fileDownloader.setProxyUserName(Config.getValue("Proxy.UserName"));
				fileDownloader.setProxyPort(Integer.parseInt(Config.getValue("Proxy.Port")));
			}

			prepareScript();
			for (int i = 0; i < config.getUrlLevels().length && i <= config.getMaxLevel(); i++) {
				try {
					if (i < startLevel && !task.checkStop()) {
						continue;
					}
					task.setCurrentInfo("正在处理第" + (i + 1) + "层级URL");
					currentLevel = i;
					dealOneLevel();
				} catch (Throwable e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (!task.checkStop()) {
				// 最后下载图片
				if (config.isCopyImageFlag()) {
					int maxPage = config.getMaxPageCount();
					config.setMaxPageCount(-1);
					fileDownloader
							.setDenyExtension(".html.htm.jsp.php.asp.shtml.swf.js.css.ico.avi.mpg.mpeg.mp3.mp4.wma.wmv.rm.rmvb.exe.tar.gz.zip.rar");
					currentLevel++;
					task.setCurrentInfo("正在处理第" + (currentLevel + 1) + "层级URL");
					String[] urls = config.getUrlLevels();
					urls = (String[]) ArrayUtils
							.add(urls,
									"${A}.gif\n${A}.jpg\n${A}.jpeg\n${A}.png\n${A}.bmp\n${A}.GIF\n${A}.JPG\n${A}.JPEG\n${A}.PNG\n${A}.BMP");
					config.setUrlLevels(urls);
					dealOneLevel();
					config.setMaxPageCount(maxPage);
					fileDownloader
							.setDenyExtension(".gif.jpg.jpeg.swf.bmp.png.js.wmv.css.ico.avi.mpg.mpeg.mp3.mp4.wma.rm.rmvb.exe.tar.gz.zip.rar");
				}
				retryWithFilter();
				writeArticle();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			list.save();
			list.close();
		}
		return list;
	}

	Pattern framePattern = Pattern.compile("<iframe.*?<\\/iframe>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	Pattern stylePattern = Pattern.compile("<style.*?><\\/style>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	Pattern scriptPattern = Pattern.compile("<script.*?<\\/script>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	Pattern linkPattern = Pattern.compile("<a .*?>(.*?)<\\/a>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	Pattern tagPattern = Pattern.compile("<.*?>", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	public void writeArticle() {
		prepareList();
		if (config.getType() == CrawlConfig.TYPE_DOCUMENT) {
			QueryBuilder imageQB = new QueryBuilder("select id from zccatalog where type=" + Catalog.IMAGELIB
					+ " and siteid=?", Long.parseLong(CatalogUtil.getSiteID(config.getCatalogID())));
			String imageCatalogID = imageQB.executeString();
			if (StringUtil.isEmpty(CatalogUtil.getSiteID(config.getCatalogID()))) {
				logger.warn("文档采集的目的栏目不存在：ID={}", config.getCatalogID());
				return;
			}
			String sitePath = SiteUtil.getAbsolutePath(CatalogUtil.getSiteID(config.getCatalogID()));
			String imagePath = "upload/Image/" + CatalogUtil.getAlias(imageCatalogID) + "/";

			RegexParser rp = config.getTemplate("Ref1");// 第一个,文档采集时只有第一个
			RegexParser[] filters = config.getFilterBlocks();
			list.moveFirst();
			WebDocument doc = null;
			int cSuccess = 0;// 成功数
			int cFailure = 0;// 失败数
			int cLost = 0;// 未匹配数

			boolean publishDateFlag = false;// 是否解析出了publishDate
			ZCArticleSet set = new ZCArticleSet();
			while ((doc = list.next()) != null) {
				if (task.checkStop()) {
					return;
				}
				if (doc.getLevel() != config.getUrlLevels().length - 1) {// 只有最后一级是详细页
					continue;
				}
				int percent = (100 - task.getPercent()) * (cSuccess + cFailure + cLost) / list.size();
				task.setPercent(task.getPercent() + percent);
				if (doc.isTextContent() && doc.getContent() != null) {
					String text = doc.getContentText();
					rp.setText(text);
					if (rp.match()) {
						Mapx map = rp.getMapx();
						Object[] ks = map.keyArray();
						Object[] vs = map.valueArray();
						for (int i = 0; i < map.size(); i++) {// 处理提取回来的数据，除内容外都要去掉标签并进行HTML解码
							String key = ks[i].toString();
							String value = vs[i].toString();
							if (!key.equalsIgnoreCase("Content")) {
								value = tagPattern.matcher(value).replaceAll("");
							}
							value = StringUtil.htmlDecode(value);
							value = value.trim();
							map.put(key, value);
						}
						String title = map.getString("Title");
						String content = map.getString("Content");
						String author = map.getString("Author");
						String source = map.getString("Source");
						String strDate = map.getString("PublishDate");
						Date publishDate = doc.getLastmodifiedDate();
						if (StringUtil.isNotEmpty(strDate) && StringUtil.isNotEmpty(config.getPublishDateFormat())) {
							try {
								// 先替换中文日期，如：二零零九年十一月一十一日，二○○九年
								strDate = DateUtil.convertChineseNumber(strDate);
								publishDate = DateUtil.parse(strDate, config.getPublishDateFormat());
							} catch (Exception e) {
								task.addError("日期" + strDate + "不符合指定格式" + doc.getUrl());
							}
							publishDateFlag = true;
						}
						if (publishDate.getTime() > System.currentTimeMillis()) {
							publishDate = new Date();// 不能晚于当前日期
						}
						ArticleAPI api = new ArticleAPI();
						try {
							ZCArticleSchema article = new ZCArticleSchema();
							if (StringUtil.isNotEmpty(title)) {
								article.setTitle(title);
							} else {
								cLost++;
								continue;
							}
							if (StringUtil.isNotEmpty(content)) {
								content = content.trim();
								while (rp.match()) {// 如果还有能匹配的，说明可能是文章内分页
									String html = rp.getMapx().getString("Content");
									content += html;
								}
								if (config.isCleanLinkFlag()) {
									content = framePattern.matcher(content).replaceAll("");
									content = stylePattern.matcher(content).replaceAll("");
									content = scriptPattern.matcher(content).replaceAll("");
									content = linkPattern.matcher(content).replaceAll("$1");
								}
								if (filters != null) {
									for (int k = 0; k < filters.length; k++) {
										content = filters[k].replace(content, "");
									}
								}
								// 需要处理图片
								String str = dealImage(content, doc.getUrl(), sitePath, imagePath, imageCatalogID);
								article.setContent(str);
							} else {
								cLost++;
								continue;
							}
							if (StringUtil.isNotEmpty(author)) {
								article.setAuthor(author);
							}
							if (StringUtil.isNotEmpty(source)) {
								article.setReferName(source);
							}
							article.setReferURL(doc.getUrl());
							article.setPublishDate(publishDate);
							article.setCatalogID(config.getCatalogID());
							article.setBranchInnerCode("0001");
							article.setProp2("FromWeb");// 标明是Web采集而来

							if (ExtendManager.hasAction("FromWeb.BeforeSave")) {
								ExtendManager.executeAll("FromWeb.BeforeSave", new Object[] { article });
							}

							Date date = (Date) new QueryBuilder(
									"select PublishDate from ZCArticle where ReferURL=? and CatalogID=?", doc.getUrl(),
									config.getCatalogID()).executeOneValue();
							if (date != null) {
								if (date.getTime() < doc.getLastDownloadTime()) {
									QueryBuilder qb = new QueryBuilder(
											"update ZCArticle set Title=?,Content=? where CatalogID=? and ReferURL=?");
									qb.add(article.getTitle());
									qb.add(article.getContent());
									qb.add(config.getCatalogID());
									qb.add(doc.getUrl());
									qb.executeNoQuery();
								}
								cSuccess++;
							} else {
								api.setSchema(article);
								set.add(article);
								if (api.insert() > 0) {
									cSuccess++;
								} else {
									cFailure++;
								}
							}
						} catch (Exception e) {
							cFailure++;
							logger.error(e.getMessage(), e);
						}
					} else {
						logger.warn("未能匹配{}", doc.getUrl());
						task.addError("未能匹配" + doc.getUrl());
						cLost++;
					}
					task.setCurrentInfo("正在转换文档, <font class='green'>" + cSuccess + "</font> 个成功, <font class='red'>"
							+ cFailure + "</font> 个失败, <font class='green'>" + cLost + "</font> 个未匹配");
				}
			}

			if (!publishDateFlag) {
				String[] lastURLs = config.getUrlLevels()[config.getUrlLevels().length - 1].split("\\\n", -1);
				if (lastURLs.length != 1) {// 只有一个才按URL排序
					return;
				}
				RegexParser rpUrl = new RegexParser(lastURLs[0]);
				boolean numberFlag = true;
				for (int i = 0; i < set.size(); i++) {
					String url = set.get(i).getReferURL();
					rpUrl.setText(url);
					if (rpUrl.match()) {
						String v = rpUrl.getMapx().getString("SortID");
						set.get(i).setProp2(v);
						if (!NumberUtil.isLong(v)) {
							numberFlag = false;
						}
					}
				}
				set.sort("Prop2", "asc", numberFlag);
				for (int i = set.size() - 1; i >= 0; i--) {
					set.get(i).setOrderFlag(OrderUtil.getDefaultOrder());
					set.get(i).setProp2(null);
				}
				set.update();
			}
		}
	}

	public String dealImage(String content, String baseUrl, String sitePath, String imagePath, String imageCatalogID) {
		Matcher m = SearchUtil.resourcePattern1.matcher(content);
		int lastEndIndex = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(lastEndIndex)) {
			String url = m.group(2);
			String ext = ServletUtil.getUrlExtension(url);
			if (SearchUtil.isRightUrl(url) && StringUtil.isNotEmpty(ext) && ".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0) {// 有引号，不需要判断是否是处于htmlEncode段中
				String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
				WebDocument tdoc = list.get(fullUrl);
				if (tdoc != null && !tdoc.isTextContent()) {
					byte[] data = tdoc.getContent();// 文件内容
					sb.append(content.substring(lastEndIndex, m.start()));
					String imageFilePath = saveImage(data, sitePath, imagePath, imageCatalogID, ext, fullUrl);
					sb.append(StringUtil.replaceEx(m.group(0), url, imageFilePath));
				} else {
					sb.append(content.substring(lastEndIndex, m.end()));
				}
			} else {
				sb.append(content.substring(lastEndIndex, m.end()));
			}
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();

		sb = new StringBuffer();
		m = SearchUtil.resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String url = m.group(3);
			String ext = ServletUtil.getUrlExtension(url);
			if (SearchUtil.isRightUrl2(url) && StringUtil.isNotEmpty(ext) && ".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0) {// 没引号，需要判断是否是处于htmlEncode段中
				String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
				WebDocument tdoc = list.get(fullUrl);
				if (tdoc != null && !tdoc.isTextContent()) {
					byte[] data = tdoc.getContent();// 文件内容
					sb.append(content.substring(lastEndIndex, m.start()));
					String imageFilePath = saveImage(data, sitePath, imagePath, imageCatalogID, ext, fullUrl);
					sb.append(StringUtil.replaceEx(m.group(0), url, imageFilePath));
				} else {
					sb.append(content.substring(lastEndIndex, m.end()));
				}
			} else {
				sb.append(content.substring(lastEndIndex, m.end()));
			}
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	/**
	 * 
	 * @param path1
	 * @param path2
	 * @param catalogID
	 * @param filename
	 * @return
	 */
	public static String saveImage(byte[] data, String path1, String path2, String catalogID, String ext,
			String imageURL) {
		ZCImageSchema image = new ZCImageSchema();
		image.setSourceURL(imageURL);
		boolean flag = true;
		ZCImageSet set = image.query();
		if (set.size() > 0) {
			image = set.get(0);
			File f = new File(path1 + path2 + image.getSrcFileName());
			if (f.exists()) {
				if (f.length() == data.length) {// 字节数相同而且文件相同的机率忽略不计
					flag = false;
				}
				FileUtil.writeByte(f, data);
			}
		} else {
			long imageID = NoUtil.getMaxID("DocID");
			int random = NumberUtil.getRandomInt(10000);
			String srcFileName = imageID + "" + random + ext;
			FileUtil.writeByte(path1 + path2 + srcFileName, data);
			image.setID(imageID);
			image.setCatalogID(catalogID);
			image.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			image.setName(imageID + "" + random);
			image.setOldName(imageID + "" + random);
			image.setSiteID(CatalogUtil.getSiteID(catalogID));
			image.setPath(path2);
			image.setFileName(imageID + "" + NumberUtil.getRandomInt(10000) + ".jpg");
			image.setSrcFileName(srcFileName);
			image.setSuffix(ext);
			image.setCount(1);
			image.setWidth(0);
			image.setHeight(0);
			try {
				BufferedImage img = ImageIO.read(new File(path1 + path2 + srcFileName));
				image.setWidth(img.getWidth());
				image.setHeight(img.getHeight());
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			image.setHitCount(0);
			image.setStickTime(0);
			image.setAuthor("Crawler");
			image.setSystem("CMS");
			image.setOrderFlag(OrderUtil.getDefaultOrder());
			image.setAddTime(new Date());
			image.setAddUser("SYS");
			image.setSiteID(CatalogUtil.getSiteID(image.getCatalogID()));
			image.insert();
		}
		if (flag) {
			ArrayList imageList = null;
			try {
				imageList = ImageUtilEx.afterUploadImage(image, path1 + path2);
			} catch (Throwable e) {
				logger.error(e.getMessage(), e);
				return "";
			}
			Deploy d = new Deploy();
			d.addJobs(image.getSiteID(), imageList);

			return (Config.getContextPath() + Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(CatalogUtil.getSiteID(catalogID)) + "/" + image.getPath() + "1_" + image
					.getFileName()).replaceAll("//", "/");
		}
		return (Config.getContextPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(CatalogUtil.getSiteID(catalogID)) + "/" + image.getPath() + image.getSrcFileName())
				.replaceAll("//", "/");
	}

	public void retryWithFilter() {
		if (this.retryFilter != null) {
			logger.info("Retry Downloading URL ........");
			WebDocument doc = list.next();
			while (doc != null) {
				if (retryFilter.filter(doc)) {
					FileDownloader.downloadOne(doc);
					if (list.hasContent(doc.getUrl())) {
						list.put(doc);
					}
				}
				doc = list.next();
			}
		}
	}

	private ScriptEngine se;

	private void prepareScript() throws EvalException {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isNotEmpty(config.getScript())) {
			se = new ScriptEngine(config.getLanguage());
			se.importPackage("com.sinosoft.framework.data");
			se.importPackage("com.sinosoft.framework.utility");
			if (config.getLanguage() == ScriptEngine.LANG_JAVA) {
				sb.append("StringBuffer _Result = new StringBuffer();\n");
				sb.append("void write(String str){_Result.append(str);}\n");
				sb.append("void writeln(String str){_Result.append(str+\"\\n\");}\n");
				sb.append(config.getScript());
				sb.append("\nreturn _Result.toString();\n");
			} else {
				sb.append("var _Result = [];\n");
				sb.append("function write(str){_Result.push(str);}\n");
				sb.append("function writeln(str){_Result.push(str+\"\\n\");}\n");
				sb.append(config.getScript());
				sb.append("\nreturn _Result.join('');\n");
			}
			se.compileFunction("_EvalScript", sb.toString());
		}
	}

	private double total = 0;

	public void executeScript(String when, CrawlScriptUtil util) {
		currentLevelDownloadedCount++;
		if (when.equalsIgnoreCase("after")) {
			task.setCurrentInfo("正在抓取" + util.getCurrentUrl());
		}
		if (total == 0) {
			for (int i = 0; i < config.getUrlLevels().length + 1; i++) {
				total += (i + 1) * (i + 1) * 400;
			}
		}
		double t = (currentLevel + 1) * (currentLevel + 1) * 400;
		t = t / total + (currentLevel + 1) * (currentLevel + 2) / total
				* (currentLevelDownloadedCount * 1.0 / currentLevelCount);
		int percent = new Double(t * 100).intValue();
		task.setPercent(percent);
		if (StringUtil.isNotEmpty(config.getScript())) {
			se.setVar("Util", util);
			se.setVar("When", when);
			se.setVar("Level", new Integer(currentLevel));
			try {
				se.executeFunction("_EvalScript");
			} catch (EvalException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private void dealOneLevel() {
		String[] arr = config.getUrlLevels()[currentLevel].trim().split("\n");
		task.setCurrentInfo("正在生成第" + (currentLevel + 1) + "层级URL");
		currentLevelCount = 0;
		if (currentLevel != 0) {
			extracter.extract(list, currentLevel, fileDownloader);
		} else {
			for (int i = 0; i < arr.length; i++) {
				String url = arr[i];
				if (StringUtil.isEmpty(url)) {
					continue;
				}
				if (!list.containsKey(url)) {
					WebDocument doc = new WebDocument();
					doc.setUrl(url);
					doc.setLevel(currentLevel);
					list.put(doc);
				}
			}
		}
		currentLevelCount = list.size();
		fileDownloader.downloadList(list, currentLevel);
	}

	public long getTaskID() {
		return config.getID();
	}

	public int getStartLevel() {
		return startLevel;
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public Filter getRetryFilter() {
		return retryFilter;
	}

	public void setRetryFilter(Filter retryFilter) {
		this.retryFilter = retryFilter;
	}

	public DocumentList getList() {
		return list;
	}

	public void setList(DocumentList list) {
		this.list = list;
	}

	public FileDownloader getFileDownloader() {
		return fileDownloader;
	}

	public CrawlConfig getConfig() {
		return config;
	}

	public void setConfig(CrawlConfig config) {
		this.config = config;
	}
}
