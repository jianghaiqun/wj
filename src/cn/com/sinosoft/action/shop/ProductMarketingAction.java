package cn.com.sinosoft.action.shop;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.resource.uploader.OSSUploadFile;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.SDMarketCommentSchema;
import com.sinosoft.schema.SDMarketNavSchema;
import com.sinosoft.schema.SDMarketProductSchema;
import com.sinosoft.schema.SDMarketingSchema;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCSiteSchema;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@ParentPackage("shop") 
public class ProductMarketingAction extends BaseShopAction {
	private static final long serialVersionUID = 1L;
	private String articleId;
	private String catalogId;
	private String textList;
	private String bgsColor;
	private String rightWords;
	private String rightLink;
	private String moduleID;
	private String txtHtml;
	private String products;
	private String editPro;
	private String navHtml;
	private String linkTxt;
	private String wordTxt;
	private String wordColor;
	private String wordBackColor;
	private String imageSrc;
	private String hide;
	private String commentContent;
	private String roundDate;
	private String commentId;
	private String editType;
	
	
	/**
	 * 文件.
	 */
	private List<File> uploads;
	/**
	 * 文件名称.
	 */
	private List<String> uploadsFileName;
	
	
	public String addArticle(){
		if(StringUtil.isEmpty(articleId)){
			Transaction trans = new Transaction();
			ZCArticleSchema article = new ZCArticleSchema();
			long articleID = NoUtil.getMaxID("DocID");
			articleId = String.valueOf(articleID);
			article.setID(articleID);
			if (StringUtil.isEmpty(catalogId)) {
				return "";
			}
			article.setCatalogID(catalogId);
			article.setSiteID(CatalogUtil.getSiteID(catalogId));

			String innerCode = CatalogUtil.getInnerCode(catalogId);
			article.setCatalogInnerCode(innerCode);
			if (article.getType() == null) {
				article.setType("1");
			}

			if (article.getTopFlag() == null) {
				article.setTopFlag("0");
			}
			if (article.getTitle() == null) {
				article.setTitle("新建文档");
			}

			if (article.getCommentFlag() == null) {
				article.setCommentFlag("1");
			}

			if (article.getContent() == null) {
				article.setContent("");
			}

			article.setStickTime(0);

			article.setPriority("1");
			article.setTemplateFlag("0");

			article.setPublishFlag("1");
			article.setAddUser(User.getUserName());

			if (article.getOrderFlag() == 0) {
				if (article.getPublishDate() != null) {
					article.setOrderFlag(article.getPublishDate().getTime());
				} else {
					article.setOrderFlag(OrderUtil.getDefaultOrder());
				}
			}
			article.setHitCount(0);
			if (article.getStatus() == 0) {
				article.setStatus(Article.STATUS_DRAFT);
			}
			article.setAddTime(new Date(article.getOrderFlag()));

			if (article.getAddUser() == null) {
				article.setAddUser("admin");
			}

			if (article.getFirstPublishDate() == null) {
				article.setFirstPublishDate(new Date());
			}
			long catalogID = Long.parseLong(catalogId);
			String temp = "";
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(catalogID);
			catalog.fill();
			ZCSiteSchema tZCSiteSchema = new ZCSiteSchema();
			tZCSiteSchema.setID(catalog.getSiteID());
			tZCSiteSchema.fill();
			String tSiteName = tZCSiteSchema.getName();
			temp = catalog.getName();
			long tParentID = catalog.getParentID();
			while (tParentID != 0) {
				catalog = new ZCCatalogSchema();
				catalog.setID(tParentID);
				catalog.fill();
				temp = temp + "-" + catalog.getName();
				tParentID = catalog.getParentID();
			}
			String tMetaTitle = article.getTitle() + "-" + temp + "-" + tSiteName;
			article.setMetaTitle(tMetaTitle);
			Date tDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String temp1 = formatter.format(tDate);
			temp1 = temp1.substring(0, 10).replace("-", "");
			if (article.getEveryDayNo() == 0) {
				DataTable nextDT = new QueryBuilder("select max(EveryDayNo)as aNo from zcarticle where  date_format(addtime,'%Y%m%d') =?", temp1).executePagedDataTable(1, 0);

				if ("".equals(nextDT.getString(0, "aNo"))) {
					article.setEveryDayNo(1);

				} else {
					article.setEveryDayNo(Long.parseLong(nextDT.getString(0, "aNo")) + 1);
				}

			}
			article.setURL(PubFun.getArticleURL(article));
			trans.add(article, Transaction.INSERT);

			String sqlArticleCount = "update zccatalog set total = total+1,isdirty=1 where id=?";
			trans.add(new QueryBuilder(sqlArticleCount, article.getCatalogID()));

			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
			articleLog.setAction("INSERT");
			articleLog.setActionDetail("添加新文章");
			articleLog.setArticleID(articleID);
			// 2011-1-14 处理历史显示真实姓名
			articleLog.setAddUser("admin");
			articleLog.setAddTime(new Date());
			trans.add(articleLog, Transaction.INSERT);
			trans.commit();
		}
		return articleId;
		
	}
	
	public String addNav(){
		articleId = addArticle();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(!StringUtil.isEmpty(textList)){
			try {
				textList = java.net.URLDecoder.decode(textList, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
			SDMarketNavSchema tMarketNav = new SDMarketNavSchema();
			Transaction trans = new Transaction();
			tMarketNav.setMarketID(articleId);
			if(tMarketNav.fill()){
				trans.add(tMarketNav,Transaction.DELETE);
			}
			tMarketNav = new SDMarketNavSchema();
			Date date = new Date();
			tMarketNav.setMarketID(articleId);
			tMarketNav.setNavDetail(textList);
			tMarketNav.setModifyDate(date);
			tMarketNav.setCreateDate(date);
			tMarketNav.setOperater(User.getUserName());
			trans.add(tMarketNav,Transaction.INSERT);
			if(trans.commit()){
				map.put("status", 1);
				map.put("message", textList);
			}else{
				map.put("status", 0);
			}
			
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String saveColor(){
		articleId = addArticle();
		Map<String, Object> map = new HashMap<String, Object>();
		Transaction trans = new Transaction();
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		Date date = new Date();
		tMarketing.setId(articleId);
		if(tMarketing.fill()){
			tMarketing.setColor(bgsColor);
			tMarketing.setModifyDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.UPDATE);
		}else{
			tMarketing.setColor(bgsColor);
			tMarketing.setModifyDate(date);
			tMarketing.setCreateDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.INSERT);
		}
		if(trans.commit()){
			map.put("status", 1);
			map.put("message", bgsColor);
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String onUploadPhoto(){
		Map<String, Object> map = new HashMap<String, Object>();
		String PhotoPatch = "";
		String partParth = "";
		if (!(StringUtil.isEmpty(uploads))) {
			if(uploads.get(0).length()>(1024*1024*10)){
				map.put("status", 2);
				return ajax(JSONObject.fromObject(map).toString(),"text/html");
			}
			PhotoPatch = uploads.get(0).getAbsolutePath();
			String path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/"
					+ Application.getCurrentSiteAlias()+"/upload/Image/tpbf/"+uploadsFileName.get(0);
			partParth = "/upload/Image/tpbf/"+uploadsFileName.get(0);
			File file = new File(PhotoPatch);
			InputStream in;
			try {
				in = new FileInputStream(file);
				OSSUploadFile.upload(in, path);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				map.put("status", 0);
			}
		}
		map.put("status", 1);
		map.put("imagsrc", partParth);
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String saveBanner(){
		articleId = addArticle();
		Map<String, Object> map = new HashMap<String, Object>();
		Transaction trans = new Transaction();
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		Date date = new Date();
		tMarketing.setId(articleId);
		if(tMarketing.fill()){
			tMarketing.setWordLeft(wordTxt);
			tMarketing.setPictrueLink(linkTxt);
			tMarketing.setWordLeftColor(wordColor);
			tMarketing.setWordLeftBack(wordBackColor);
			tMarketing.setPictruePath(imageSrc);
			tMarketing.setModifyDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.UPDATE);
		}else{
			tMarketing.setWordLeft(wordTxt);
			tMarketing.setPictrueLink(linkTxt);
			tMarketing.setWordLeftColor(wordColor);
			tMarketing.setWordLeftBack(wordBackColor);
			tMarketing.setPictruePath(imageSrc);
			tMarketing.setModifyDate(date);
			tMarketing.setCreateDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.INSERT);
		}
		if(trans.commit()){
			map.put("status", 1);
			map.put("wordTxt", wordTxt);
			map.put("linkTxt", linkTxt);
			map.put("wordColor", wordColor);
			map.put("wordBackColor", wordBackColor);
			map.put("imageSrc", imageSrc);
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String savePrePicture(){
		articleId = addArticle();
		Map<String, Object> map = new HashMap<String, Object>();
		Transaction trans = new Transaction();
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		Date date = new Date();
		tMarketing.setId(articleId);
		if(tMarketing.fill()){
			tMarketing.setPrePicPath(imageSrc);
			tMarketing.setModifyDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.UPDATE);
		}else{
			tMarketing.setPrePicPath(imageSrc);
			tMarketing.setModifyDate(date);
			tMarketing.setCreateDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.INSERT);
		}
		if(trans.commit()){
			map.put("status", 1);
			map.put("imageSrc", imageSrc);
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String saveRightWords(){
		articleId = addArticle();
		Map<String, Object> map = new HashMap<String, Object>();
		Transaction trans = new Transaction();
		try {
			rightWords = java.net.URLDecoder.decode(rightWords, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		Date date = new Date();
		tMarketing.setId(articleId);
		if(tMarketing.fill()){
			tMarketing.setWordRight(rightWords);
			tMarketing.setWordRightLink(rightLink);
			tMarketing.setModifyDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.UPDATE);
		}else{
			tMarketing.setWordRight(rightWords);
			tMarketing.setWordRightLink(rightLink);
			tMarketing.setModifyDate(date);
			tMarketing.setCreateDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.INSERT);
		}
		if(trans.commit()){
			map.put("status", 1);
			map.put("rightWords", rightWords);
			map.put("rightLink", rightLink);
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String saveInfo(){
		articleId = addArticle();
		Map<String, Object> map = new HashMap<String, Object>();
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		Transaction trans = new Transaction();
		Date date = new Date();
		try {
			if(StringUtil.isNotEmpty(txtHtml)){
				txtHtml = java.net.URLDecoder.decode(txtHtml, "UTF-8");
				navHtml = java.net.URLDecoder.decode(navHtml, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		tMarketing.setId(articleId);
		if(tMarketing.fill()){
			tMarketing.setTxtHtml(txtHtml);
			tMarketing.setNavHtml(navHtml);
			tMarketing.setModifyDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.UPDATE);
		}else{
			tMarketing.setTxtHtml(txtHtml);
			tMarketing.setNavHtml(navHtml);
			tMarketing.setModifyDate(date);
			tMarketing.setCreateDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.INSERT);
		}
		if(trans.commit()){
			map.put("status", 1);
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String savePro(){
		String strList[] = products.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		SDMarketProductSchema tMarketProduct = new SDMarketProductSchema();
		String productDetail = "";
		String productName = "";
		for(int i=0;i<strList.length;i++){
			tMarketProduct = new SDMarketProductSchema();
			tMarketProduct.setProductID(strList[i]);
			tMarketProduct.fill();
			productDetail += tMarketProduct.getProDetailHtml();
			String str = (String)new QueryBuilder("select ProductName from sdproduct where ProductID='"+strList[i]+"' and ispublish ='Y'").executeOneValue();
			if(i==0){
				productName = str;
			}else{
				productName += ","+str;
			}
		}
		map.put("productDetail", productDetail);
		map.put("productName", productName);
		map.put("editPro", editPro);
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String hidPre(){
		articleId = addArticle();
		Map<String, Object> map = new HashMap<String, Object>();
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		Transaction trans = new Transaction();
		Date date = new Date();
		tMarketing.setId(articleId);
		if(tMarketing.fill()){
			tMarketing.setPrePicHid(hide);
			tMarketing.setModifyDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.UPDATE);
		}else{
			tMarketing.setPrePicHid(hide);
			tMarketing.setModifyDate(date);
			tMarketing.setCreateDate(date);
			tMarketing.setOperater(User.getUserName());
			trans.add(tMarketing,Transaction.INSERT);
		}
		if(trans.commit()){
			map.put("status", 1);
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String saveCom(){
		articleId = addArticle();
		Map<String, Object> map = new HashMap<String, Object>();
		Transaction trans = new Transaction();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String name = getSeed();
		String commentId = NoUtil.getMaxNo("MarketCommentId");
		String comment = "<li><p class=\"img\"><img src=\""+Config.getValue("StaticResourcePath")+"/images/img_avatar_01.gif\" alt=\"\" />"+
				"<span class=\"name\">"+name+"</span></p><p class=\"txt\"><span class=\"cmt\" id=\""+commentId+"\">"+
				commentContent+"</span>"+
				"<span class=\"date\">"+roundDate+"</span></p><p class=\"operate\">"+
				"<a class=\"editCmt\" href=\"#\">编辑</a><a href=\"#\" class=\"smt\" onclick=\"editComm('"+commentId+"','edit')\">确定</a>"+
				"<a class=\"delCmt\" href=\"#\" onclick=\"editComm('"+commentId+"','del')\">删除</a></p></li>";
		SDMarketCommentSchema tMarketComment = new SDMarketCommentSchema();
		tMarketComment.setID(commentId);
		tMarketComment.setCommentContent(comment);
		tMarketComment.setMarketID(articleId);
		tMarketComment.setModuleId(editPro);
		Date d = new Date();
		try {
			d = formatter.parse(roundDate);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		tMarketComment.setRoundDate(d);
		trans.add(tMarketComment,Transaction.INSERT);
		if(trans.commit()){
			String sql = "SELECT CommentContent FROM SDMarketComment WHERE ModuleId = '"+editPro+"' AND MarketID='"+articleId+"' ORDER BY RoundDate desc";
			DataTable dt = new QueryBuilder(sql).executeDataTable();
			if(dt.getRowCount()>0){
				commentContent = "";
				for(int i=0;i<dt.getRowCount();i++){
					commentContent += dt.getString(i, 0);
				}
			}
			map.put("status", 1);
			map.put("editPro", editPro);
			map.put("commentContent", commentContent);
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String editComm(){
		Transaction trans = new Transaction();
		Map<String, Object> map = new HashMap<String, Object>();
		SDMarketCommentSchema tMarketComment = new SDMarketCommentSchema();
		tMarketComment.setID(commentId);
		if(tMarketComment.fill()){
			if("edit".equals(editType)){
				tMarketComment.setCommentContent(commentContent);
				trans.add(tMarketComment,Transaction.UPDATE);
			}else if("del".equals(editType)){
				trans.add(tMarketComment,Transaction.DELETE);
			}else{
				map.put("status", 0);
			}
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public String deleteModule(){
		Transaction trans = new Transaction();
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = "delete from SDMarketComment where MarketID='"+articleId+"' and ModuleId='"+editPro+"'";
		trans.add(new QueryBuilder(sql));
		if(trans.commit()){
			sql = "UPDATE SDMarketComment  SET ModuleId = (CONCAT('pro',(REPLACE(ModuleId,'pro','')-1))) " +
					"WHERE ModuleId > '"+editPro+"' and MarketID='"+articleId+"'";
			trans.add(new QueryBuilder(sql));
			if(!trans.commit()){
				map.put("status", 0);
			}
		}else{
			map.put("status", 0);
		}
		return ajax(JSONObject.fromObject(map).toString(),"text/html");
	}
	
	public static String getSeed() {
		char[] arr = "123456789abcdefghijklmnopqrstxyzABCDEFGHIJKLMNOPQRSTXYZ".toCharArray();
		Random rand = new Random();
		StringBuffer sb = new StringBuffer(4);

		// int max = 15;
		// int min = 8;
		// int tLength = rand.nextInt(max) % (max - min + 1) + min;

		for (int i = 0; i < 4; i++) {
			sb.append(arr[rand.nextInt(arr.length)]);
		}
		return sb.toString() + "**";
	}
	
	public String getBodyColor(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String color = "";
		if(tMarketing.fill()){
			color = tMarketing.getColor();
			if(StringUtil.isEmpty(color)){
				color = "";
			}
		}
		return color;
	}
	
	public String getMenuCont(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String navHtml = "";
		if(tMarketing.fill()){
			navHtml = tMarketing.getNavHtml();
			if(StringUtil.isEmpty(navHtml)){
				navHtml = "";
			}
		}
		return navHtml;
	}
	
	public String getBannerLink(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String bannerLink = "";
		if(tMarketing.fill()){
			bannerLink = tMarketing.getPictrueLink();
			if(StringUtil.isEmpty(bannerLink)){
				bannerLink = "";
			}
		}
		return bannerLink;
	}
	public String getBannerPath(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String bannerPath = "";
		if(tMarketing.fill()){
			bannerPath = tMarketing.getPictruePath();
			if(StringUtil.isEmpty(bannerPath)){
				bannerPath = "";
			}
		}
		return bannerPath;
	}
	
	public String getLeftWords(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String leftWords = "";
		if(tMarketing.fill()){
			leftWords = tMarketing.getWordLeft();
			if(StringUtil.isEmpty(leftWords)){
				leftWords = "";
			}
		}
		return leftWords;
	}
	
	public String getLeftColor(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String leftColor = "";
		if(tMarketing.fill()){
			leftColor = tMarketing.getWordLeftColor();
			if(StringUtil.isEmpty(leftColor)){
				leftColor = "";
			}
		}
		return leftColor;
	}
	
	public String getLeftWordBackColor(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String leftWordBackColor = "";
		if(tMarketing.fill()){
			leftWordBackColor = tMarketing.getWordLeftBack();
			if(StringUtil.isEmpty(leftWordBackColor)){
				leftWordBackColor = "";
			}
		}
		return leftWordBackColor;
	}
	
	public String getRightWords(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String rightWords = "";
		if(tMarketing.fill()){
			rightWords = tMarketing.getWordRight();
			if(StringUtil.isEmpty(rightWords)){
				rightWords = "";
			}
		}
		return rightWords;
	}
	
	public String getRightWordLink(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String rightWordLink = "";
		if(tMarketing.fill()){
			rightWordLink = tMarketing.getWordRightLink();
			if(StringUtil.isEmpty(rightWordLink)){
				rightWordLink = "";
			}
		}
		return rightWordLink;
	}
	
	public String getTxtHtml(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String txtHtml = "";
		if(tMarketing.fill()){
			txtHtml = tMarketing.getTxtHtml();
			if(StringUtil.isEmpty(txtHtml)){
				txtHtml = "";
			}else{
				txtHtml = txtHtml.replace("editSec", "");
			}
		}
		return txtHtml;
	}
	
	public String getPreHid(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String txtHtml = "";
		if(tMarketing.fill()){
			txtHtml = tMarketing.getPrePicHid();
			if(StringUtil.isEmpty(txtHtml)){
				txtHtml = "";
			}
		}
		return txtHtml;
	}
	
	public String getPrePic(String articleId) {
		SDMarketingSchema tMarketing = new SDMarketingSchema();
		tMarketing.setId(articleId);
		String prePic = "";
		if(tMarketing.fill()){
			prePic = tMarketing.getPrePicPath();
			if(StringUtil.isEmpty(prePic)){
				prePic = "";
			}
		}
		return prePic;
	}
	public String getNavHtml(String articleId){
		StringBuffer navHTML = new StringBuffer();
		DataTable dt = new QueryBuilder("select NavDetail from SDMarketNav where MarketID ='"+articleId+"'").executeDataTable();
		if(dt.getRowCount()>0){
			navHTML.append(dt.getString(0, 0));
			if(StringUtil.isEmpty(navHTML)){
				navHTML = new StringBuffer();
			}
		}
		return navHTML.toString();
	}
	
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public String getTextList() {
		return textList;
	}

	public void setTextList(String textList) {
		this.textList = textList;
	}
	
	public String getBgsColor() {
		return bgsColor;
	}

	public void setBgsColor(String bgsColor) {
		this.bgsColor = bgsColor;
	}

	public List<File> getUploads() {
		return uploads;
	}

	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}

	public String getRightWords() {
		return rightWords;
	}

	public void setRightWords(String rightWords) {
		this.rightWords = rightWords;
	}

	public String getRightLink() {
		return rightLink;
	}

	public void setRightLink(String rightLink) {
		this.rightLink = rightLink;
	}

	public String getModuleID() {
		return moduleID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public String getTxtHtml() {
		return txtHtml;
	}

	public void setTxtHtml(String txtHtml) {
		this.txtHtml = txtHtml;
	}

	public String getEditPro() {
		return editPro;
	}

	public void setEditPro(String editPro) {
		this.editPro = editPro;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getNavHtml() {
		return navHtml;
	}

	public void setNavHtml(String navHtml) {
		this.navHtml = navHtml;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public List<String> getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(List<String> uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public String getLinkTxt() {
		return linkTxt;
	}

	public void setLinkTxt(String linkTxt) {
		this.linkTxt = linkTxt;
	}

	public String getWordTxt() {
		return wordTxt;
	}

	public void setWordTxt(String wordTxt) {
		this.wordTxt = wordTxt;
	}

	public String getWordColor() {
		return wordColor;
	}

	public void setWordColor(String wordColor) {
		this.wordColor = wordColor;
	}

	public String getWordBackColor() {
		return wordBackColor;
	}

	public void setWordBackColor(String wordBackColor) {
		this.wordBackColor = wordBackColor;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public String getHide() {
		return hide;
	}

	public void setHide(String hide) {
		this.hide = hide;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getRoundDate() {
		return roundDate;
	}

	public void setRoundDate(String roundDate) {
		this.roundDate = roundDate;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}

}
