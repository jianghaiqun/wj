package com.sinosoft.cms.document;

import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.cms.template.ParserCache;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 单文章发布
 * 
 */
public class ArticlePublish extends Page {

	public void clear() {
		try {
			ParserCache.clear();

			Response.setStatus(0);
			Response.setMessage("清除缓存完成!");

		} catch (Exception e) {
			Response.setStatus(1);
			Response.setMessage("清除缓存异常!" + e.getMessage());
		}
	}
	
	public void syncPublish() {
		final  Map<String,String> map = new HashMap<String,String>();
		map.put("IDs", $V("IDs"));
		map.put("CatalogID", $V("CatalogID"));
		map.put("Keyword", $V("Keyword"));
		map.put("StartDate", $V("StartDate"));
		map.put("EndDate", $V("EndDate"));
		map.put("Type", $V("Type"));
		
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				ArticlePublish.publish(this, map);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		ltt.setCurrentInfo("开始同步......");
		$S("SignArticleTaskID", "" + ltt.getTaskID());
	}
	
	public static void publish(LongTimeTask lTT , Map<String,String> map) {
		long start = System.currentTimeMillis();
		try {
			String ids = map.get("IDs");
			String CatalogID = map.get("CatalogID");
			String Keyword = map.get("Keyword");
			String StartDate = map.get("StartDate");
			String EndDate = map.get("EndDate");
			String Type = map.get("Type");

			QueryBuilder qb = new QueryBuilder();
			if (StringUtil.isNotEmpty(ids)) {
				qb.append(" where id in (" + ids  + ") and status in (0,20,25,30,60)");

			} else {  
				qb.append(" where type = 1 "); 

				if ("TOPUBLISH".equals(Type)) {// 待发布的文档
					qb.append(" and status=?", Article.STATUS_TOPUBLISH);
				} else if ("PUBLISHED".equals(Type)) {// 已发布的文档
					qb.append(" and status=?", Article.STATUS_PUBLISHED);
				} else if ("ONPUBLISH".equals(Type)) {// 发布中
					qb.append(" and status=?", Article.STATUS_ONPUBLISH);
				} else if ("EDITING".equals(Type)) {
					qb.append(" and status=?", Article.STATUS_EDITING);// 重新编辑
				} else if ("ALL".equals(Type)) {
					qb.append(" and status in (0,20,25,30,60)");
				}

				if (StringUtil.isNotEmpty(CatalogID)) {
					qb.append(" and CatalogID  = ?");
					qb.add(CatalogID);
				}
				if (StringUtil.isNotEmpty(Keyword)) {
					qb.append("  and  title like ? ", "%" + Keyword.trim() + "%");
				}
				if (StringUtil.isNotEmpty(StartDate)) {
					StartDate += " 00:00:00";
					qb.append(" and publishdate >= ? ", StartDate);
				}
				if (StringUtil.isNotEmpty(EndDate)) {
					EndDate += " 23:59:59";
					qb.append(" and publishdate <= ? ", EndDate);
				}
			}
			if (lTT != null) {
				lTT.setCurrentInfo("数据查询完成......"  );
			}
			
			ZCArticleSchema tZCArticleSchema = new ZCArticleSchema();
			ZCArticleSet tZCArticleSet = tZCArticleSchema.query(qb);

			for (int i = 0; i < tZCArticleSet.size(); i++) {
				ZCArticleSchema tArticleSchema = tZCArticleSet.get(i);
				
				if (lTT != null) {
					lTT.setCurrentInfo("正在发布(" + (i + 1) + "/" + tZCArticleSet.size() + ")：" + tArticleSchema.getTitle());
					
				}
				
				boolean result = false;
				try {
					PageGenerator p = new PageGenerator();
					result = p.staticDoc("Article", tArticleSchema);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					result = false;
					lTT.setCurrentInfo("发布失败(" + (i + 1) + "/" + tZCArticleSet.size() + ")：" + tArticleSchema.getTitle());
					lTT.setPercent(Integer.valueOf((i + 1) * 100 / (tZCArticleSet.size() + 1)));

				} finally {
					if (lTT != null) {
						lTT.setPercent(Integer.valueOf((i + 1) * 100 / (tZCArticleSet.size() + 1)));
					}
					
					if (result) {
						tArticleSchema.setStatus(Article.STATUS_PUBLISHED);
						// 判断物理文件路径是否存在
						if (!validateFile(tArticleSchema.getURL())) {
							tArticleSchema.setStatus(Article.STATUS_ONPUBLISH);

						} else {
							tArticleSchema.setPublishDate(new Date());
						}
					} else {
						tArticleSchema.setStatus(Article.STATUS_ONPUBLISH);
					}

					tArticleSchema.update();
				}

			}
			
			if (lTT != null) {
				lTT.setPercent(100);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 断物理文件路径是否存在<br>
	 * 根据文件的最后修改时间判断是否为新生成，在一个小时以内为准
	 * 
	 * @param articleURL
	 * @return
	 */
	private static boolean validateFile(String articleURL) {
		String p_path = Config.getClassesPath().replace("WEB-INF", "wwwroot").replace("classes", "kxb") + articleURL;
		File f = new File(p_path);
		if (!f.exists()) {
			return false;
		}
		boolean b = validateDate(new Date(f.lastModified()), new Date());
		return b;

	}

	/**
	 * 时间校验
	 * 
	 * @param sDate
	 * @param eDate
	 * @return
	 */
	private static boolean validateDate(Date sDate, Date eDate) {
		long s = eDate.getTime() - sDate.getTime();
		double d = s / (1000 * 60);
		// 超过1小时
		if (d > 60) {
			return false;
		} else {
			return true;
		}
	}
	
}
