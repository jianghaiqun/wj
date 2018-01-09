package com.wangjin.daren;

import cn.com.sinosoft.util.CommonUtil;
import cn.com.sinosoft.util.Constant;
import cn.com.sinosoft.util.ExcelReadUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.SDDREmailExtractRecodeSchema;
import com.sinosoft.schema.SDDREmailExtractSchema;
import com.sinosoft.schema.SDDREmailExtractSet;
import com.sinosoft.schema.SDDRLinkCheckResultSchema;
import com.sinosoft.schema.SDDRLinkInfoSchema;
import com.sinosoft.schema.SDDRLinkInfoSet;
import com.wangjin.daren.util.SimpleClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: DataMiningManage <br/>
 * Function: 达人数据挖掘. <br/>
 * date: 2016年7月5日 上午9:28:28 <br/>
 *
 * @author wwy
 * @version 
 */
public class DataMiningManage extends Page {

	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"); 
	private final static Pattern pt_iid = Pattern.compile("<" + "a" + "[^<>]*?\\s" + "data-iid" + "=['\"]?(.*?)['\"]?\\s.*?>");
	
	public static void main(String[] args) {
	}
	
	/**
	 * emailExtract:邮箱抓取. <br/>
	 *
	 * @author wwy
	 */
	public void emailExtract(){
		// 链接地址
		String link = $V("link");
		
		if (link.indexOf("www.mafengwo.cn") >= 0) {
			try {
				String url_page = "http://www.mafengwo.cn/ajax/ajax_fetch_pagelet.php?params={\"type\":0,\"objid\":0,\"page\":1,\"ajax\":1}&api=:mfw:pagelet:recommendGinfoApi";
				String s = SimpleClient.doGet(url_page, null);
				
				JSONObject a = new JSONObject(s);
				String n = String.valueOf(((JSONObject) a.get("data")).get("html"));
				
				String regexPage="共.*?页 /";
				Pattern ptpage = Pattern.compile(regexPage);

				Matcher mtPage = ptpage.matcher(n);
				String pageCount = "";
				while(mtPage.find()){
					pageCount = mtPage.group(0);
					pageCount = pageCount.replace("共", "");
					pageCount = pageCount.replace("页 /", "");
				}
				int pages = 83;
				try {
					pages = Integer.valueOf(pageCount);
				}catch (Exception e) {
				}
				
				//String regex_a = "<a.*?/a>";
				//Pattern pt_a = Pattern.compile(regex_a);
				//String regex_iid = "<" + "a" + "[^<>]*?\\s" + "data-iid" + "=['\"]?(.*?)['\"]?\\s.*?>";
				//Pattern pt_iid = Pattern.compile(regex_iid);
				QueryBuilder qb = new QueryBuilder("SELECT MAX(CONVERT(CASE WHEN Prop2 IS NULL THEN '1' ELSE Prop2 END, DECIMAL)) FROM SDDREmailExtract WHERE adduser = ? ", User.getRealName());
				String maxPage = qb.executeString();
				if (StringUtil.isEmpty(maxPage)) {
					maxPage = "1";
				}
				int pageCurrent = Integer.valueOf(maxPage);
				
				for (int i = pageCurrent; i < pages; i++) {
					String url = "http://www.mafengwo.cn/ajax/ajax_fetch_pagelet.php?params={\"type\":0,\"objid\":0,\"page\":" + i + ",\"ajax\":1}&api=:mfw:pagelet:recommendGinfoApi";
					//String url = "http://www.mafengwo.cn/ajax/ajax_fetch_pagelet.php";
					
					String content = SimpleClient.doGet(url, "utf-8");
					JSONObject json = new JSONObject(content);
					String html = String.valueOf(((JSONObject)json.get("data")).get("html"));
					Matcher mt_iid = pt_iid.matcher(html);
					
					String g_iid = "";
					while (mt_iid.find()) {
						g_iid = mt_iid.group(1);

						String pageUrl = "http://www.mafengwo.cn/i/" + g_iid + ".html";
						String pageComment = SimpleClient.doGet(pageUrl, "utf-8");
						Document jsoupPage = Jsoup.parse(pageComment);
						Element ePage = jsoupPage.getElementById("_j_sticky_relmdd");
						
						Elements es = ePage.getElementsByClass("_j_mdd_stas");
						String destination = "";
						if (null != es && es.size() > 0) {
							Element e = es.first();
							destination = e.text();
						}
						if (StringUtil.isEmpty(destination)) {
							Response.setStatus(0);
							Response.setMessage("获取旅游目的地出现异常！");
							return;
						}
						
						for (int j = 0; j < 100; j++) {
							
							// 评论内容
							String ajaxUrl = "http://www.mafengwo.cn/ajax/ajax_fetch_pagelet.php?params={\"iid\":\"" + g_iid + "\",\"page\":\"" + j + "\"}&api=:note:pagelet:bottomReplyApi";

							//Map<String ,String> param = new HashMap<String, String>();
							//param.put("params", "{\"type\":0,\"objid\":0,\"page\":" + i + ",\"ajax\":1}");
							//param.put("api", ":mfw:pagelet:recommendGinfoApi");
							
							String comment = SimpleClient.doGet(ajaxUrl, "utf-8");
							JSONObject jb = null;
							try {
								jb = new JSONObject(comment);
							} catch (Exception e) {
								logger.error("JSON解析报错！" + e.getMessage(), e);
								break;
							}
							
							String ht = String.valueOf(((JSONObject)jb.get("data")).get("html"));
							
							Document jsoupDoc = Jsoup.parse(ht);
							Element element = jsoupDoc.getElementById("_j_reply_list");
							
							if (null == element || element.childNodeSize() == 1) {
								break;
							}
							else {
								Matcher matchr = emailer.matcher(element.toString());
								while (matchr.find()) {
									String email = matchr.group(0);
									if (!email.endsWith("2x.png") && !email.endsWith("2x.gif") && !email.endsWith("2x.jpg")) {
										// j 帖子内页 i 首页进入的帖子页
										saveSDDREmailExtract(pageUrl, email, destination, String.valueOf(j), "蚂蜂窝", String.valueOf(i));
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				Response.setStatus(0);
				Response.setMessage("抓取异常！" + e.getMessage());
				return;
			}

		} 
		else if (link.indexOf(".qyer.com") >= 0) {
			try {
				qiongyouE();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				Response.setStatus(0);
				Response.setMessage("穷游抓取邮箱异常！");
				return;
			}
		}
		else {
			Response.setStatus(0);
			Response.setMessage("暂不支持该链接地址！");
			return;
		}
		Response.setStatus(1);
		Response.setMessage("邮箱抓取完成");
	} 
    
	/**
	 * linkCheck:帖子链接检查. <br/>
	 *
	 * @author wwy
	 */
	public void linkCheck(){
		// 平台
		String source = $V("source");
		// 链接地址
		String linkParam = $V("link");
		// 获取帖子链接地址
		SDDRLinkInfoSchema infoSchema = new SDDRLinkInfoSchema();
		String whereSql = "where 1=1";
		if (StringUtil.isNotEmpty(source)) {
			whereSql += " and Platform = '" + source + "'";
		}
		if (StringUtil.isNotEmpty(linkParam)) {
			whereSql += " and Link like '%" + linkParam + "%'";
		}
		SDDRLinkInfoSet infoSet = infoSchema.query(new QueryBuilder(whereSql));
		
		Transaction tr = new Transaction();
		if (infoSet.size() > 0) {
			QueryBuilder qb = new QueryBuilder("delete from SDDRLinkCheckResult where adduser = ?", User.getRealName());
			
			if (StringUtil.isNotEmpty(source)) {
				qb.append(" and Platform = ? ", source);
			}
			if (StringUtil.isNotEmpty(linkParam)) {
				qb.append(" and link like '%"+linkParam+"%' ");
			}
			tr.add(qb);
		}
		Map<String, Object> result = null;
		for (int i = 0; i < infoSet.size(); i++) {
			SDDRLinkInfoSchema info = infoSet.get(i);
			
			// 平台
			String platform = info.getPlatform();
			String link = info.getLink();
			if (StringUtil.isEmpty(link)) {
				Response.setStatus(0);
				Response.setMessage("帖子地址不能为空！新增帖子的备注为帖子的地址，需要提前设置。");
				return;
			}
			// 蚂蜂窝
			if (link.indexOf("www.mafengwo.cn") >= 0) {
				result = mafengwo(info);
			}
			// 穷游
			else if (link.indexOf("bbs.qyer.com") >= 0) {
				result = qiongyou(info);
			}
			// 穷游ask
			else if (link.indexOf("ask.qyer.com") >= 0) {
				result = qiongyou_ask(info);
			}
			// 百度旅游
			else if (link.indexOf("lvyou.baidu.com") >= 0) {
				result = baidulvyou(info);
			}
			// 携程
			else if (link.indexOf("you.ctrip.com") >= 0) {
				result = xiecheng(info);
			}
			// 蝉游记
			else if (link.indexOf("chanyouji.com") >= 0) {
				result = chanyouji(info);
			}
			// 岛多多
			else if (link.indexOf("www.daoduoduo.com") >= 0) {
				result = daoduoduo(info);
			}
			// 滴答留学
			else if (link.indexOf("bbs.tigtag.com") >= 0) {
				result = didaliuxue(info);
			}
			// 驴妈妈
			else if (link.indexOf("www.lvmama.com") >= 0) {
				result = lvmama(info);
			}
			// 面包旅行
			else if (link.indexOf("web.breadtrip.com") >= 0) {
				result = mianbaolvyou(info);
			}
			// 磨房
			else if (link.indexOf("www.doyouhike.net") >= 0) {
				result = mofang(info);
			}
			// 途牛
			else if (link.indexOf("www.tuniu.com") >= 0) {
				result = tuniu(info);
			}
			// 去哪儿
			else if (link.indexOf("travel.qunar.com") >= 0) {
				result = qunaer(info);
			}
			// 十六番
			else if (link.indexOf("bbs.16fan.com") >= 0) {
				result = shiliufan(info);
			}
			// 同程
			else if (link.indexOf("go.ly.com") >= 0) {
				result = tongcheng(info);
			} else{
				Response.setStatus(0);
				Response.setMessage("检查失败！未知平台：" + platform + " 帖子：" + info.getLink());
				return;
			}
			
			// 判断check结果
			if (null != result) {
				if (Constant.SUCCESS.equals(result.get(Constant.STATUS))) {
					if (null != result.get(Constant.DATA)) {
						SDDRLinkCheckResultSchema s = (SDDRLinkCheckResultSchema) result.get(Constant.DATA);
						tr.add(s, Transaction.INSERT);
					}
				}else{
					Response.setStatus(0);
					Response.setMessage((String) result.get(Constant.MSG));
					return;
				}
			}
		}
		if (tr.commit()) {
			Response.setStatus(1);
			Response.setMessage("检查完成");
		} else {
			Response.setStatus(0);
			Response.setMessage("检查失败，事务提交异常！");
		}
	}
	
	/**
	 * tuniu:途牛. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> tuniu(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = SimpleClient.pageContent_Get(link, "utf-8");
		if (StringUtil.isNotEmpty(pageContent)) {
			Map<String, Object> containResult = containstr(pageContent);
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * tongcheng:同程. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> tongcheng(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		try {
			String pageContent = SimpleClient.doGetBrowser(link, "utf-8", null);
			if (StringUtil.isNotEmpty(pageContent)) {
				Map<String, Object> containResult = containstr(pageContent);
				return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
			}
		}catch(Exception e){
			logger.error("同程获取页面内容异常！" + link + e.getMessage(), e);
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * mianbaolvyou:磨房. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> mofang(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = "";
		try {
			for (int i = 1; i < 5; i++) {
				
				link = link.substring(0, link.indexOf(".html") - 1) + i + ".html";
				pageContent = SimpleClient.doGet(link, null);
				
				if (StringUtil.isNotEmpty(pageContent)) {
					Map<String, Object> containResult = containstr(pageContent);
					if ("Y".equals((String) containResult.get("isExist"))) {
						return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "Y", String.valueOf(i), (String) containResult.get("keyword")));
					}
				}
			}
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "N", null, null));
			
		} catch (Exception e) {
			logger.error("磨房获取页面内容异常！" + link + e.getMessage(), e);
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * shiliufan:十六番. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> shiliufan(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = "";
		try {
			for (int i = 1; i < 5; i++) {
				if (link.indexOf("&page=") > 0) {
					link = link.substring(0, link.indexOf("&page=")) + "&page=" + i;
				} else if (link.indexOf(".html") > 0) {
					link = link.substring(0, link.indexOf(".html") - 3) + i + "-1.html";
				}
				pageContent = SimpleClient.doGetBrowser(link, null, null);

				if (StringUtil.isNotEmpty(pageContent)) {
					Map<String, Object> containResult = containstr(pageContent);
					if ("Y".equals((String) containResult.get("isExist"))) {
						return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "Y", String.valueOf(i), (String) containResult.get("keyword")));
					}
				}
			}
			
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "N", null, null));
			
		} catch (Exception e) {
			logger.error("十六番获取页面内容异常！" + link + e.getMessage(), e);
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * mianbaolvyou:面包旅游. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> mianbaolvyou(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = "";
		try {
			pageContent = SimpleClient.doGet(link, "utf-8");
		} catch (Exception e) {
			logger.error("面包旅游获取页面内容异常！" + link + e.getMessage(), e);
		}
		if (StringUtil.isNotEmpty(pageContent)) {
			Map<String, Object> containResult = containstr(pageContent);
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * lvmama:驴妈妈. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> lvmama(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = SimpleClient.pageContent_Get(link, null);
		if (StringUtil.isNotEmpty(pageContent)) {
			Map<String, Object> containResult = containstr(pageContent);
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * didaliuxue:滴答留学. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> didaliuxue(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = "";
		try {
			pageContent = SimpleClient.doGet(link, null);
		} catch (Exception e) {
			logger.error("滴答留学异常！" + link + e.getMessage(), e);
		}
		if (StringUtil.isNotEmpty(pageContent)) {
			Map<String, Object> containResult = containstr_didaliuxue(pageContent);
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * daoduoduo:岛多多. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> daoduoduo(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = "";
		
		String linkC = "";
		try {
			for (int i = 1; i < 5; i++) {
				if (link.indexOf(".html") > 0) {
					linkC = link.substring(0, link.indexOf(".html") - 1) + i + ".html";
				} else if (link.indexOf(".php?tid") > 0 && link.indexOf("&page=1") > 0) {
					linkC = link.replace("&page=1", "&page=" + i);
				} else {
					if (i == 2) break;
				}
				pageContent = SimpleClient.doGet(linkC, "gbk");

				if (StringUtil.isNotEmpty(pageContent)) {
					Map<String, Object> containResult = containstr(pageContent);
					if ("Y".equals((String) containResult.get("isExist"))) {
						return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "Y", String.valueOf(i), (String) containResult.get("keyword")));
					}
				}
			}
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "N", null, null));
		} catch (Exception e) {
			logger.error("岛多多检查异常!" + link + e.getMessage(), e);
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * chanyouji:蝉游记. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> chanyouji(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = SimpleClient.pageContent_Get(link, null);
		if (StringUtil.isNotEmpty(pageContent)) {
			Map<String, Object> containResult = containstr(pageContent);
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * qiongyou_ask:穷游ask检查. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> qiongyou_ask(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		try {
			String pageContent = SimpleClient.pageContent_Get(link, null);
			if (StringUtil.isNotEmpty(pageContent)) {
				Map<String, Object> containResult = containstr(pageContent);
				return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
			}
		} catch (Exception e) {
			logger.error("穷游检查异常！" + link + e.getMessage(), e);
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * qiongyou:穷游检查. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> qiongyou(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		// 分享的帖子，查询前三页是否包含关键字
		try {
			for (int i = 1; i < 4; i++) {
				if (link.indexOf(".html") > 0 && "-".equals(link.substring(link.indexOf(".html") - 2, link.indexOf(".html") - 1))) {
					link = link.substring(0, link.indexOf(".html") - 1) + i + ".html";
				} else {
					if (i == 2) break; 
				}
				
	 			String pageContent = SimpleClient.doGetBrowser(link, null, null);
				if (StringUtil.isNotEmpty(pageContent)) {
					Map<String, Object> containResult = containstr(pageContent);
					if ("Y".equals((String) containResult.get("isExist"))) {
						return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "Y", String.valueOf(i), (String) containResult.get("keyword")));
					}
				}
			}
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "N", "1", null));
		} catch (Exception e) {
			logger.error("穷游检查异常！" + link + e.getMessage(), e);
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * mafengwo:蚂蜂窝检查. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> mafengwo(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = "";
		try {
			pageContent = SimpleClient.doGet(link, "utf-8");
		} catch (Exception e) {
			logger.error("蚂蜂窝检查异常！" + link + e.getMessage(), e);
		}
		if (StringUtil.isNotEmpty(pageContent)) {
			Map<String, Object> containResult = containstr(pageContent);
			if ("Y".equals((String) containResult.get("isExist"))) {
				return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "Y", "1", (String) containResult.get("keyword")));
			} else {
				String iId = link.substring(link.lastIndexOf("/") + 1, link.indexOf(".html"));
				// 评论内容 评论内容不要了。。。。
				//for (int i = 1; i < 4; i++) {
				for (int i = 1; i < 1; i++) {
					// http://www.mafengwo.cn/ajax/ajax_fetch_pagelet.php?params={"iid":"5432145","page":"2"}&api=:note:pagelet:bottomReplyApi
					String ajaxUrl = "http://www.mafengwo.cn/ajax/ajax_fetch_pagelet.php?params={\"iid\":\"" + iId + "\",\"page\":\"" + i + "\"}&api=:note:pagelet:bottomReplyApi";
					String pageContentAjax = "";
					try {
						pageContentAjax = SimpleClient.doGetBrowser(ajaxUrl, null, null);
						JSONObject jb = new JSONObject(pageContentAjax);
						pageContentAjax = String.valueOf(((JSONObject)jb.get("data")).get("html"));
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
					if (StringUtil.isNotEmpty(pageContentAjax)) {
						Map<String, Object> containResultAjax = containstr(pageContentAjax);
						if ("Y".equals((String) containResultAjax.get("isExist"))) {
							return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "Y", String.valueOf(i), (String) containResult.get("keyword")));
						}
					}
				}
				return PubFun.sucMsg(getSDDRLinkCheckResult(cs, "N", null, null));
			}
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * baidulvyou:百度旅游检查. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> baidulvyou(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		try {
			link = link.replace("http://", "https://");
			String pageContent = SimpleClient.doGetBrowser(link, "utf-8", null);
			if (StringUtil.isNotEmpty(pageContent)) {
				Map<String, Object> containResult = containstr(pageContent);
				return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
			}
		} catch (Exception e) {
			logger.error("百度旅游检查异常！" + link + e.getMessage(), e);
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * qunaer:去哪儿网检查. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> qunaer(SDDRLinkInfoSchema cs){
		String link = cs.getLink();
		String pageContent = "";
		try {
			pageContent = SimpleClient.doGetBrowser(link, "UTF-8", null);
		} catch (Exception e) {
			logger.error("去哪儿网检查异常！" + link + e.getMessage(), e);
		}
		if (StringUtil.isNotEmpty(pageContent)) {
			Map<String, Object> containResult = containstr(pageContent);
			return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * xiecheng:携程检查. <br/>
	 *
	 * @author wwy
	 * @param cs
	 * @return
	 */
	private static Map<String, Object> xiecheng(SDDRLinkInfoSchema cs){
		
		String link = cs.getLink();
		try {
			String pageContent = SimpleClient.doGetBrowser(link, "UTF8", null);
			if (StringUtil.isNotEmpty(pageContent)) {
				Map<String, Object> containResult = containstr(pageContent);
				return PubFun.sucMsg(getSDDRLinkCheckResult(cs, (String) containResult.get("isExist"), "1", (String) containResult.get("keyword")));
			}
		} catch (Exception e) {
			logger.error("携程网检查异常！" + link + e.getMessage(), e);
		}
		
		return PubFun.sucMsg();
	}
	
	/**
	 * containstr:(是否包含指定字符串). <br/>
	 *
	 * @author wwy
	 * @param str
	 * @param s
	 * @return
	 */
	private static Map<String, Object> containstr(String str){
		Map<String, Object> result = new HashMap<String, Object>();
		String[] s = {"kaixinbao", "dwz.cn", "http://t.cn", "http:\\/\\/t.cn"};
		String flag = "N";
        for (int i = 0; i < s.length; i++) {
            if (str.contains(s[i])) {
            	flag = "Y";
                result.put("keyword", s[i]);
                break;
            }
        }
        result.put("isExist", flag);
        return result;
    }
	
	/**
	 * containstr:(是否包含指定字符串 滴答留学特殊). <br/>
	 *
	 * @author wwy
	 * @param str
	 * @param s
	 * @return
	 */
	private static Map<String, Object> containstr_didaliuxue(String str){
		Map<String, Object> result = new HashMap<String, Object>();
		String[] s = {"kaixinbao", "dwz.cn", "t.cn"};
		String flag = "N";
        for (int i = 0; i < s.length; i++) {
            if (str.contains(s[i])) {
            	flag = "Y";
                result.put("keyword", s[i]);
                break;
            }
        }
        result.put("isExist", flag);
        return result;
    }
	
	/**
	 * saveSDDREmailExtract:报错获取到的email信息. <br/>
	 *
	 * @author wwy
	 * @param link
	 * @param email
	 * @param destination
	 * @param pageNo
	 * @return
	 */
	private static boolean saveSDDREmailExtract(String link, String email, String destination, String pageNo, String prop1, String prop2) {
		SDDREmailExtractSchema s = new SDDREmailExtractSchema();
		
		s.setAddUser(User.getRealName());
		s.setEmail(email);
		s.setdestination(destination);
		SDDREmailExtractSet set = s.query();
		
		if (null != set && set.size() > 0) {
			return true;
		}
		
		s.setId(CommonUtil.getUUID());
		s.setEmail(email);
		s.setdestination(destination);
		s.setLink(link);
		s.setPageNo(pageNo);
		s.setAddTime(new Date());
		s.setAddUser(User.getRealName());
		s.setProp1(prop1);
		s.setProp2(prop2);
		return s.insert();
	}
	
	/**
	 * saveSDDRLinkCheckResult:获取保存数据库对象. <br/>
	 *
	 * @author wwy
	 * @return
	 */
	private static SDDRLinkCheckResultSchema getSDDRLinkCheckResult(SDDRLinkInfoSchema c, String isExist, String pageNo, String keyword) {
		SDDRLinkCheckResultSchema s = new SDDRLinkCheckResultSchema();
		
		s.setId(CommonUtil.getUUID());
		s.setLink(c.getLink());
		s.setPlatform(c.getPlatform());
		s.setPageNo(pageNo);
		s.setIsExist(isExist);
		s.setPageNo(pageNo);
		s.setKeyword(keyword);
		s.setCheckTime(new Date());
		s.setAddUser(User.getRealName());
		return s;
	}
	
	public static Mapx<String, String> init(Mapx<String, String> params) {
		// 来源
		params.put("source", HtmlUtil.codeToOptions("Author.source", true));
		params.put("YesOrNo", HtmlUtil.codeToOptions("YesOrNo", true));
		
		return params;
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		// 平台
		String source = dga.getParams().getString("source");
		// 是否存在关键字
		String isExist = dga.getParams().getString("isExist");
		// 链接地址
		String link = dga.getParams().getString("link");
		
		String sql = "select r.*, (case when IsExist = 'Y' then '是' else '否' end) as isExistName, i.Author, i.linkman from SDDRLinkCheckResult r, SDDRLinkInfo i where r.link = i.link and r.AddUser = ?";
		QueryBuilder qb = new QueryBuilder(sql, User.getRealName());
		if (StringUtil.isNotEmpty(source)) {
			qb.append(" and r.Platform = ? ", source);
		}
		if (StringUtil.isNotEmpty(isExist)) {
			qb.append(" and r.IsExist = ? ", isExist);
		}
		if (StringUtil.isNotEmpty(link)) {
			qb.append(" and r.link like '%"+link+"%' ");
		}
		qb.append(" order by r.CheckTime ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	public static void dg2DataBind(DataGridAction dga) {
		// 链接地址
		String destination = dga.getParams().getString("destination");
		// email
		String email = dga.getParams().getString("email");
		// source
		String source = dga.getParams().getString("source");
		
		String sql = "select * from SDDREmailExtract where AddUser = ?";
		QueryBuilder qb = new QueryBuilder(sql, User.getRealName());
		if (StringUtil.isNotEmpty(destination)) {
			qb.append(" and destination like '%" + destination + "%' ");
		}
		if (StringUtil.isNotEmpty(email)) {
			qb.append(" and email like '%" + email + "%' ");
		}
		if (StringUtil.isNotEmpty(source)) {
			qb.append(" and Prop1 = '" + source + "' ");
		}
		qb.append(" order by AddTime ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	/**
	 * 增加
	 */
	public void add() {
		
		String Link = (String) Request.get("Link");
		
		SDDRLinkInfoSchema schema = new SDDRLinkInfoSchema();
		schema.setLink(Link);
		SDDRLinkInfoSet set = schema.query();
		if (set.size() > 0) {
			Response.setLogInfo(0, "链接已经存在了!");
			return;
		}
		schema.setId(CommonUtil.getUUID());
		schema.setValue(Request);
		schema.setAddUser(User.getRealName());
		schema.setAddTime(new Date());
		
		if (schema.insert()) {
			Response.setLogInfo(1, "新建代码成功!");
		} else {
			Response.setLogInfo(0, "新建代码失败!");
		}
	}
	
	/**
	 * 删除 
	 */
	public void del() {
		String ids = $V("IDs");
		String[] idArr = ids.split(",");
		String sqlIds = "";
		for (int i = 0; i < idArr.length; i++) {
			sqlIds += "'" + idArr[i] + "',";
		}
		sqlIds = sqlIds.substring(0, sqlIds.length() - 1);
		
		Transaction trans = new Transaction();
		SDDRLinkInfoSchema schema = new SDDRLinkInfoSchema();
		SDDRLinkInfoSet set = schema.query(new QueryBuilder("where id in (" + sqlIds + ")"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		}
		else{
			Response.setLogInfo(0, "删除失败");
		}
	}
	
	public static void dg3DataBind(DataGridAction dga) {
		// 来源
		String source = dga.getParams().getString("source");
		// 作者
		String author = dga.getParams().getString("author");
		// 联系人
		String linkman = dga.getParams().getString("linkman");
		
		String sql = "select * from SDDRLinkInfo where 1=1";
		QueryBuilder qb = new QueryBuilder(sql);
		if (StringUtil.isNotEmpty(source)) {
			qb.append(" and Platform = '" + source + "' ");
		}
		if (StringUtil.isNotEmpty(author)) {
			qb.append(" and author like '%" + author + "%' ");
		}
		if (StringUtil.isNotEmpty(linkman)) {
			qb.append(" and linkman = '" + linkman + "' ");
		}
		qb.append(" order by Prop1 desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
	
	private static void qiongyouE() throws Exception{
		String pageUrl = "http://place.qyer.com/";
		String pageComment = SimpleClient.doGetBrowser(pageUrl, "UTF-8", null);
		Document html = Jsoup.parse(pageComment);
		Elements es = html.getElementsByClass("item");
		
		if (null != es && es.size() > 0) {
			for (int i = 0; i < es.size(); i++) {
				Element e = es.get(i);
				
				Node n = e.childNode(0);
				
				String placeUrl = "";
				String place = "";
				if ("a".equals(n.nodeName())) {
					placeUrl = n.attr("href");
					place = n.childNode(0).outerHtml();
				} else if ("p".equals(n.nodeName())) {
					Node san = n.childNode(0);
					placeUrl = san.attr("href");
					place = san.childNode(0).outerHtml();
				}
				
				if (StringUtil.isNotEmpty(placeUrl) && StringUtil.isNotEmpty(place)) {

					String url_n = placeUrl;
					String comment_n = SimpleClient.doGetBrowser(url_n, "UTF-8", null);

					Document doc_n = Jsoup.parse(comment_n);
					
					Elements country_a = doc_n.getElementsByAttributeValue("data-type", "country");
					String pid = "";
					if (country_a.size() > 0) {
						pid = country_a.first().attr("data-id");
					}
					
					if (StringUtil.isEmpty("pid")) {
						logger.error("穷游页面已经修改，获取不到Pid");
						break;
					}
					for (int m = 1; m < 50; m++) {
						String link = "http://place.qyer.com/city.php?action=ajaxrecommendthreads&page=" + m + "&type=country&pid=" + pid + "&sort=recommend";

						String result = SimpleClient.doGetBrowser(link, "UTF-8", null);
						if (StringUtil.isEmpty(result)) {
							break;
						}
						JSONObject json = null;
						try {
							json = new JSONObject(result);
						} catch (Exception e1) {
							logger.error(e1.getMessage(), e1);
							break;
						}
						JSONArray array = (JSONArray)((JSONObject) json.get("data")).get("list");
						if (array.length() > 0) {
							for (int o = 0; o < array.length(); o++) {
								JSONObject jo = (JSONObject) array.get(o);
								
								String thread = jo.getString("url");
								
								if (thread.indexOf("http://bbs.qyer.com/thread-") >= 0) {
									readPage(thread, place, o);
								}
							}
						} else {
							if (m == 1) {
								Element div = doc_n.getElementById("plcTravelLists");
								
								Elements as = div.getElementsByTag("a");
								for (int j = 0; j < as.size(); j++) {
									Element a = as.get(j);
									String href = a.attr("href");
									if (StringUtil.isNotEmpty(href)) {
										if (href.indexOf("http://bbs.qyer.com/thread-") >= 0) {
											readPage(href, place, j);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static void readPage (String href, String place, int j) throws Exception {
		QueryBuilder qb = new QueryBuilder("select count(1) from SDDREmailExtractRecode where link = ? and adduser = ?", href, User.getUserName());
		
		int count = qb.executeInt();
		if (count > 0) {
			return;
		}
		
		SDDREmailExtractRecodeSchema schema = new SDDREmailExtractRecodeSchema();
		schema.setId(CommonUtil.getUUID());
		schema.setLink(href);
		schema.setAddTime(new Date());
		schema.setAddUser(User.getRealName());
		schema.insert();
		
		for (int k = 1; k < 100; k++) {
			if (href.indexOf(".html") > 0 && "-".equals(href.substring(href.indexOf(".html") - 2, href.indexOf(".html") - 1))) {
				href = href.substring(0, href.indexOf(".html") - 1) + k + ".html";
			} else {
				break; 
			}
			
 			String pageContent = SimpleClient.doGetBrowser(href, "UTF-8", null);
			if (StringUtil.isNotEmpty(pageContent)) {
				
				Document doc = Jsoup.parse(pageContent);
				Elements d = doc.getElementsByClass("bbs_detail_list");
				
				if (d.get(0).childNodes().size() > 0) {
					Matcher matchr = emailer.matcher(d.get(0).toString());
					while (matchr.find()) {
						String email = matchr.group(0);
						saveSDDREmailExtract(href, email, place, String.valueOf(k), "穷游", String.valueOf(j));
					}
				} else {
					break;
				}
			}
		}
	}

	/**
	 * 导入帖子信息
	 */
	public void improt(){
		
		String FilePath = (String) Request.get("FilePath");
		if (StringUtil.isEmpty(FilePath)) {
			Response.setLogInfo(1, "文件上传失败！");
			return;
		}

		SDDRLinkInfoSet set = new SDDRLinkInfoSet();
		String errorMessage = "";
		String[] filePaths = FilePath.split(",");
		for (int i = 0; i < filePaths.length; i++) {
			File file = new File(Config.getContextRealPath() + "/temp/" + filePaths[i]);
			if (file.exists() && file.isFile()) {
				String ext = getExtension(filePaths[i]);
				if ("xls,xlsx".indexOf(ext) >= 0) {
				
					try {
						List<Map<String, String>> result = ExcelReadUtil.getData(file, 0);
						logger.info("excel解析完成。size:{}", result.size());
						if (null != result && result.size() > 1) {
							
							for (int j = 1; j < result.size(); j++) {
								Map<String, String> row = result.get(j);

								SDDRLinkInfoSchema schema = new SDDRLinkInfoSchema();
								schema.setId(CommonUtil.getUUID());
								schema.setPlatform(row.get("2"));
								schema.setLink(row.get("1"));
								schema.setAuthor(row.get("0"));
								schema.setlinkman(row.get("3"));
								schema.setAddTime(new Date());
								schema.setAddUser("admin");
								schema.setProp1(String.valueOf(System.currentTimeMillis()));
								
								set.add(schema);
							}
						}
					}catch (Exception e) {
						logger.error("文件解析异常!" + e.getMessage(), e);
						errorMessage = "文件解析异常！";
					}
				
				}else {
					Response.setLogInfo(1, "文件格式不正确！");
					errorMessage = "文件格式不正确！";
					break;
				}
			} else {
				Response.setLogInfo(1, "文件不存在！");
				errorMessage = "文件不存在！";
				break;
			}
		}
		// 删除文件
		for (int i = 0; i < filePaths.length; i++) {

			File file = new File(Config.getContextRealPath() + "/temp/" + filePaths[i]);
			if (file.exists() && file.isFile()) {
				FileUtil.delete(file);
			}
		}
		if (StringUtil.isNotEmpty(errorMessage)) {
			Response.setLogInfo(1, errorMessage);
		} else {
			Transaction tr = new Transaction();
			tr.add(new QueryBuilder(" delete from SDDRLinkInfo"));
			tr.add(set, Transaction.INSERT);
			if (tr.commit()) {
				Response.setLogInfo(0, "导入成功！");
			} else {
				Response.setLogInfo(0, "导入失败！");
			}
		}
	}

	/**
	 * 获取后缀名
	 * 
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}
