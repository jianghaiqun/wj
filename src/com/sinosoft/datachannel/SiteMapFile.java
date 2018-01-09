package com.sinosoft.datachannel;

import com.sinosoft.cms.datachannel.Deploy;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.jdt.InsureTransfer;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class SiteMapFile{
	private static final Logger logger = LoggerFactory.getLogger(SiteMapFile.class);

	private static final String strFILECOUNTFLAG = "files";
	private String strFileCount = "50000";
	private Element root;
	/**
	 * 
	 * 站点地图生成sitemap.xml文件方法
	 */
	public void proXML() {
		try {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String siteDate = dateFormatter.format(new Date());
			LinkedHashMap<String, String> Header_Map = new LinkedHashMap<String, String>();
			
			// 设置节点信息
			root = new Element("urlset");
			root.setAttribute(strFILECOUNTFLAG, "0");
			// 更新频率
			String strChangefreq = String.valueOf(new QueryBuilder("select memo from zdcode where codetype='sitemapchange' and parentcode = 'sitemapchange'").executeOneValue());
			strFileCount = String.valueOf(new QueryBuilder("select memo from zdcode where codetype='sitemapfilecount'and parentcode = 'sitemapfilecount'").executeOneValue());
			if (StringUtil.isEmpty(strFileCount)) {
				strFileCount = "50000";
			}
			//分发列表
			ArrayList<String> jobList = new ArrayList<String>();
		
			String sql1 = "";
			GetDBdata db = new GetDBdata();
			//查询不同环境对应不同的url值
			try {
				sql1 = db
						.getOneValue("select url  from zcsite where id='221'");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			Header_Map.put("loc", sql1+"/");
			Header_Map.put("lastmod", siteDate);
			Header_Map.put("changefreq", strChangefreq);
			Header_Map.put("priority", "1.0");
			root.addContent(splitEle("url", Header_Map,  jobList));
			

			ZCCatalogSchema tZCCatalogSchema = new ZCCatalogSchema();
			ZCCatalogSet tZCCatalogSet = tZCCatalogSchema.query(new QueryBuilder("where (issitemap is null or issitemap !='N') and type='1' order by orderflag"));
			for (int i = 0; i < tZCCatalogSet.size(); i++) {
				ZCCatalogSchema s = tZCCatalogSet.get(i);
				File a = new File(s.getURL());
				if (StringUtil.isEmpty(s.getURL())&& a.exists() ) {
					continue;	
				}
				
				// 判断网站优先等级,1为首页,0.8为二级页面,0.6为剩余部分
				if (!s.getURL().endsWith("/")) {
					s.setURL(s.getURL() + "/");
				}
				if(s.getURL() != null && s.getURL().indexOf("//") !=-1 ){
					s.setURL( s.getURL().replaceAll("//", "/"));
				}
				try {
					List<HashMap<String, String>> list = db
							.query("select URL,date_format(ModifyTime,'%Y-%m-%d') as Mdate "
									+ "from ZCArticle where type ='1' and CatalogID = "
									+ s.getID()
									+ " and url not like'%index.shtml'"
									+ " and Status in ('30','50','60')");
					for (HashMap<String, String> m : list) {
						Header_Map.put("loc", sql1 + "/" + m.get("URL"));
						Header_Map.put("lastmod", siteDate);
						Header_Map.put("changefreq", strChangefreq);
						Header_Map.put("priority", s.getURL().indexOf("/") == s.getURL()
								.lastIndexOf("/") ? "0.8" : "0.6");
						root.addContent(splitEle("url", Header_Map,  jobList));
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			// 获取tag词路径信息
			ZCTagSchema tZCTagSchema = new ZCTagSchema();
			ZCTagSet tZCTagSet = tZCTagSchema.query();
			for (int i = 0; i < tZCTagSet.size(); i++) {
				ZCTagSchema tZCTag = tZCTagSet.get(i);
				tZCTag.getLinkURL();
				Header_Map.put("loc", sql1 + tZCTag.getLinkURL());
				Header_Map.put("lastmod", siteDate);
				Header_Map.put("changefreq", strChangefreq);
				Header_Map.put("priority", "0.6");
				root.addContent(splitEle("url", Header_Map,  jobList));
			}

			// 获取已审核的关键词文件路径
//			String path1 = Config.getContextRealPath() + File.separator
//					+ "wwwroot" + File.separator + "kxb" + File.separator
//					+ "keylist" + File.separator;
//			File f = new File(path1);
//			if (f.exists() && f.isDirectory()) {
//				File[] f1 = f.listFiles();
//				if (f1 != null && f1.length > 0) {
//					for (int i = 0; i < f1.length; i++) {
//						if (f1[i].isFile()) {
//							if (f1[i].getName().endsWith("index.shtml")) {
//								continue;
//							}
//							Header_Map.put("loc",
//									sql1 + "/keylist/" + f1[i].getName());
//							Header_Map.put("lastmod", siteDate);
//							Header_Map.put("changefreq", strChangefreq);
//							Header_Map.put("priority", "0.6");
//							root.addContent(splitEle("url", Header_Map,  jobList));
//
//						}
//					}
//				}
//			}
			// 获取带分页的index.shtml及list.shtml文件
			String path2 = Config.getContextRealPath() + File.separator
					+ "wwwroot" + File.separator + "kxb" + File.separator;
			File e = new File(path2);
			File[] f1 = listFiles(e);
			if (f1 != null && f1.length > 0) {
				for (int i = 0; i < f1.length; i++) {
					String str = f1[i].getPath()
							.substring(e.getPath().length())
							.replaceAll("\\\\", "/");
					str = str.substring(0);
					if(!str.startsWith("/")){
					Header_Map.put("loc", sql1 + "/" + str);
					}else{
					Header_Map.put("loc", sql1 + str);
					}
					Header_Map.put("lastmod", siteDate);
					Header_Map.put("priority", str.indexOf("/") == str
							.lastIndexOf("/") ? "0.8" : "0.6");
					root.addContent(splitEle("url", Header_Map,  jobList));
				}
			}
			
			if (root.getContentSize() > 0) {
				String path = createSiteMap();
				if (StringUtil.isNotEmpty(path)) {
					jobList.add(path);
				}
			}
			Deploy d = new Deploy();
			d.addJobs(221, jobList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * wap站点地图生成sitemap.xml文件方法
	 */
	public void proWapXML() {
		try {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String siteDate = dateFormatter.format(new Date());
			LinkedHashMap<String, String> Header_Map = new LinkedHashMap<String, String>();
			
			// 设置节点信息
			root = new Element("urlset");
			root.setAttribute(strFILECOUNTFLAG, "0");
			// 更新频率
			String strChangefreq = String.valueOf(new QueryBuilder("select memo from zdcode where codetype='sitemapchange' and parentcode = 'sitemapchange'").executeOneValue());
			strFileCount = String.valueOf(new QueryBuilder("select memo from zdcode where codetype='sitemapfilecount'and parentcode = 'sitemapfilecount'").executeOneValue());
			if (StringUtil.isEmpty(strFileCount)) {
				strFileCount = "50000";
			}
			//分发列表
			ArrayList<String> jobList = new ArrayList<String>();
			
			// WAP站实现资讯类WAP页的新页面自动更新 （知识，资讯，百科） +++++++begin
			String siteurl = Config.getValue("WapServerContext");
			if (StringUtil.isEmpty(siteurl)) {
				siteurl = "http://wap.kaixinbao.com/";
			}
			
		 	String changefreq = "daily";
			String catalog = "select id from zccatalog where  innercode like '002306%' or  innercode like '002307%' or  innercode like '002347%' ";
			// 竞价页
			String wapsemInnercode = Config.getValue("wapsemInnercode");
			if (StringUtil.isNotEmpty(wapsemInnercode)) {
				catalog += " or innercode like '" + wapsemInnercode + "%' ";
			}
		 	QueryBuilder catalog_qb = new QueryBuilder(catalog);
		 	DataTable qb_dt = catalog_qb.executeDataTable();
		 	for (int i = 0; i < qb_dt.getRowCount(); i++) {
		 		String catalogid = qb_dt.get(i).getString("id");

		 		String article = "select URL,date_format(ModifyTime,'%Y-%m-%d') as Mdate from zcarticle where CatalogID = ?";
		 		QueryBuilder article_qb = new QueryBuilder(article);
		 		article_qb.add(catalogid);
		 		DataTable article_dt = article_qb.executeDataTable();

		 		for (int j = 0; j < article_dt.getRowCount(); j++) {
		 			String URL = article_dt.get(j).getString("URL");
		 			String Mdate = article_dt.get(j).getString("Mdate");

		 			Element detail_url = new Element("url");
		 			detail_url.addContent(new Element("loc").setText(siteurl + "/" + URL));
		 			detail_url.addContent(new Element("lastmod").setText(Mdate));
		 			detail_url.addContent(new Element("changefreq").setText(changefreq));
		 			detail_url.addContent(new Element("priority").setText("0.6"));

		 			root.addContent(detail_url);
		 		}
		 	}
		 	// +++++++++++++++++++++++++++++++++++++++++++++++++++++end
			
			if (root.getContentSize() > 0) {
				String path = createWapSiteMap();
				if (StringUtil.isNotEmpty(path)) {
					jobList.add(path);
				}
			}
			Deploy d = new Deploy();
			d.addJobs(221, jobList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 生成xml文件
	 * @param root
	 */
	private String createSiteMap() {
		try {
			// 设置xml文件的字符, 解决中文问题
			String iFiles = root.getAttributeValue(strFILECOUNTFLAG);
			root.removeAttribute(strFILECOUNTFLAG);
			Format format = Format.getPrettyFormat();
			format.setEncoding("UTF-8");
			XMLOutputter xmlout = new XMLOutputter(format);
			Document doc1 = new Document(root);
			ByteArrayOutputStream bo1 = new ByteArrayOutputStream();
				xmlout.output(doc1, new PrintWriter(bo1));
			// 生成xml到本地
			ByteArrayInputStream is = new ByteArrayInputStream(bo1.toString()
					.getBytes("UTF-8"));
			String fileName = Config.getContextRealPath() + File.separator
					+ "wwwroot" + File.separator + "kxb" + File.separator;
			if ("0".equals(iFiles)) {
				fileName += "sitemap";
			} else {
				fileName += "sitemap" + iFiles;
			}
			fileName += ".xml";
			logToFile(fileName, is);
			is.close();
			bo1.close();
			root.removeContent();
			root = new Element("urlset");
			root.setAttribute(strFILECOUNTFLAG, String.valueOf(Integer.parseInt(iFiles)+1));
			return fileName;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 生成xml文件
	 * @param root
	 */
	private String createWapSiteMap() {
		try {
			// 设置xml文件的字符, 解决中文问题
			String iFiles = root.getAttributeValue(strFILECOUNTFLAG);
			root.removeAttribute(strFILECOUNTFLAG);
			Format format = Format.getPrettyFormat();
			format.setEncoding("UTF-8");
			XMLOutputter xmlout = new XMLOutputter(format);
			Document doc1 = new Document(root);
			ByteArrayOutputStream bo1 = new ByteArrayOutputStream();
				xmlout.output(doc1, new PrintWriter(bo1));
			// 生成xml到本地
			ByteArrayInputStream is = new ByteArrayInputStream(bo1.toString()
					.getBytes("UTF-8"));
			String fileName = Config.getContextRealPath() + File.separator
					+ "wwwroot" + File.separator + "wap" +  File.separator + "ditu" + File.separator;
			if ("0".equals(iFiles)) {
				fileName += "sitemap";
			} else {
				fileName += "sitemap" + iFiles;
			}
			fileName += ".xml";
			logToFile(fileName, is);
			is.close();
			bo1.close();
			root.removeContent();
			root = new Element("urlset");
			root.setAttribute(strFILECOUNTFLAG, String.valueOf(Integer.parseInt(iFiles)+1));
			return fileName;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 判断路径是否存在,是否为文件夹
	 * 读取存在的文件夹中的全部符合要求的文件
	 * @param path
	 * @return
	 */
	private File[] listFiles(File path) {
		List<File> lFiles = new ArrayList<File>();
		if (path.exists() && path.isDirectory()) {
			SiteMapFilenameFilter filter = new SiteMapFilenameFilter();
			File[] files = path.listFiles(filter);
			for (File f : files) {
				if (f.isDirectory()) {
					lFiles.addAll(listFiles(filter, f));
				} else {
					lFiles.add(f);
				}
			}
		}
		if (lFiles.size() > 0) {
			return lFiles.toArray(new File[lFiles.size()]);
		}
		return null;
	}

	private List<File> listFiles(FilenameFilter filter, File path) {
		List<File> lFiles = new ArrayList<File>();
		File[] files = path.listFiles(filter);
		for (File f : files) {
			if (f.isDirectory()) {
				lFiles.addAll(listFiles(filter, f));
			} else {
				lFiles.add(f);
			}
		}
		return lFiles;
	}
	/**
	 * 判断文件夹下文件的扩展名结尾
	 * 是否是以/为结束
	 * @param path
	 * @param file
	 * @param in
	 */
	public void logToFile(String path, InputStream in) {
		try {
			File p = new File(path.substring(0, path.lastIndexOf(File.separator)));
			File f = new File(path);

			if (f.exists()) {
				f.delete();
			} else {
				p.mkdirs();
			}
			f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			InsureTransfer.copyStream(in, fos, 64 * 1024);
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 遍历map,将结果添加到root节点
	 * 
	 * @param root
	 * @param map
	 * @return
	 */
	private Element splitEle(String eleName,
			LinkedHashMap<String, String> map, List<String> job) {
		int iCount = Integer.parseInt(strFileCount), curCount = root.getContentSize();
		if (curCount >= iCount) {
			String path = createSiteMap();
			if (StringUtil.isNotEmpty(path)) {
				job.add(path);
			}
		}
		Element ele = new Element(eleName);
		Set<Map.Entry<String, String>> set = map.entrySet();
		for (Iterator<Map.Entry<String, String>> it = set.iterator(); it
				.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			ele.addContent(addEle(entry.getKey(), entry.getValue()));
		}
		return ele;
	}

	/**
	 * 组装element元素
	 * 
	 * @param ele
	 * @param value
	 * @return
	 */
	private Element addEle(String ele, String value) {
		Element temp = new Element(ele);
		temp.setText(value);
		return temp;
	}
	

}
