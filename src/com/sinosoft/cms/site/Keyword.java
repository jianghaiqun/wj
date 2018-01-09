package com.sinosoft.cms.site;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCKeywordSchema;
import com.sinosoft.schema.ZCKeywordSet;
import com.sinosoft.schema.ZCKeywordTypeSchema;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 关键字管理
 */
public class Keyword extends Page {

	public static Mapx Type_KeyWordMap = new Mapx(1000);
	
	private HttpClient httpClient;
	
	private void init() {
		if (httpClient == null) {
			SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
			HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
			hcmp.setDefaultMaxConnectionsPerHost(5);
			hcmp.setConnectionTimeout(3000);// 三秒之内必须返回
			hcmp.setSoTimeout(5000);
			hcmp.setTcpNoDelay(true);
			cm.setParams(hcmp);
			httpClient = new HttpClient(cm);
		}
	}

	public static ZCKeywordSet getKeyWordSet(String typeID) {
		ZCKeywordSet set = (ZCKeywordSet) Type_KeyWordMap.get(typeID);
		if (set == null) {
			updateCache(typeID);
			set = (ZCKeywordSet) Type_KeyWordMap.get(typeID);
		}
		return set;
	}

	public static void updateCache(String typeID) {
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		QueryBuilder qb = new QueryBuilder();
		if (Config.isSQLServer() || Config.isSybase()) {
			qb.setSQL("where KeyWordType like ? order by prop1,addtime,len(KeyWord) desc");
		} else {
			qb.setSQL("where KeyWordType like ? order by prop1,addtime,length(KeyWord)  desc");
		}
		qb.add("%" + typeID + "%");
		ZCKeywordSet set = keyword.query(qb);
		if (set.size() == 0) {
			Type_KeyWordMap.remove(typeID);
		} else {
			Type_KeyWordMap.put(typeID, set);
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		
		String Link = dga.getParam("Link");
		String Category = dga.getParam("Category");
		String Employ = dga.getParam("Employ");
		String LinkFlag = dga.getParam("LinkFlag");
		String SearchCodeStart = dga.getParam("SearchCodeStart");
		String SearchCodeEnd = dga.getParam("SearchCodeEnd");
		String PriorityLevelStart = dga.getParam("PriorityLevelStart");
		String PriorityLevelEnd = dga.getParam("PriorityLevelEnd");
		
		String keywordTypeID = dga.getParam("id");
		String word = dga.getParam("Word");
		long siteID = Application.getCurrentSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select ID,Keyword,SearchCode,LinkURL,PriorityLevel,EmployFlag,BelongCategory,AddTime from ZCKeyword where 1=1 ");
		if (StringUtil.isNotEmpty(word)) {
			qb.append(" and Keyword like ? ", "%" + word.trim() + "%");
		}
		if(StringUtil.isNotEmpty(Link)){
			qb.append(" and LinkUrl like ? ", "%" + Link.trim() + "%");
		}
		if(StringUtil.isNotEmpty(Category)){
			qb.append(" and BelongCategory = ? ");
			qb.add(Category.trim());
		}
		if(StringUtil.isNotEmpty(Employ)){
			qb.append(" and EmployFlag = ? ");
			qb.add(Employ.trim());
		}
		if(StringUtil.isNotEmpty(LinkFlag)){
			if("02".equals(LinkFlag.trim())){
				qb.append(" and ( LinkUrl is null or LinkUrl = ? or LinkUrl = ? ) ");
				qb.add("");
				qb.add("http://");
			}else if ("01".equals(LinkFlag.trim())){
				qb.append(" and ( LinkUrl is not null and LinkUrl <> ? and LinkUrl <> ? ) ");
				qb.add("");
				qb.add("http://");
			}
		}
		if(StringUtil.isNotEmpty(SearchCodeStart)){
			qb.append(" and SearchCode >= ? ");
			qb.add(Long.parseLong(SearchCodeStart.trim()));
		}
		if(StringUtil.isNotEmpty(SearchCodeEnd)){
			qb.append(" and SearchCode <= ? ");
			qb.add(Long.parseLong(SearchCodeEnd.trim()));
		}
		if(StringUtil.isNotEmpty(PriorityLevelStart)){
			qb.append(" and PriorityLevel >= ? ");
			qb.add(Long.parseLong(PriorityLevelStart.trim()));
		}
		if(StringUtil.isNotEmpty(PriorityLevelEnd)){
			qb.append(" and PriorityLevel <= ? ");
			qb.add(Long.parseLong(PriorityLevelEnd.trim()));
		}
		
		qb.append(" and SiteId = ? ");
		qb.add(siteID);
		if (StringUtil.isNotEmpty(keywordTypeID) && !keywordTypeID.trim().equals("null")) {
			qb.append(" and KeywordType like ? ");
			qb.add("%," + keywordTypeID.trim() + ",%");
		}
		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(dga.getSortString());
		} else {
			qb.append(" order by id asc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),dga.getPageIndex());
		
		for (int i = 0; i < dt.getRowCount(); i++) {
			
			QueryBuilder qb_BelongCategory = new QueryBuilder("select CodeName from zdCode where CodeType = 'Keyword.belongCategory' and CodeValue = ? ");
			
			if("01".equals(dt.get(i, "EmployFlag"))){
				dt.set(i, "EmployFlag", "是");
			}else if("02".equals(dt.get(i, "EmployFlag"))){
				dt.set(i, "EmployFlag", "否");
			}else{
				dt.set(i, "EmployFlag", "");
			}
			qb_BelongCategory.add(dt.get(i, "BelongCategory"));
			dt.set(i, "BelongCategory", qb_BelongCategory.executeString());
		}
		dga.bindData(dt);
		
	}

/*	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZCKeywordSet set = new ZCKeywordSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCKeywordSchema keyword = new ZCKeywordSchema();
			keyword.setID(Integer.parseInt(dt.getString(i, "ID")));
			keyword.fill();
			keyword.setValue(dt.getDataRow(i));
			keyword.setModifyTime(new Date());
			keyword.setModifyUser(User.getUserName());

			set.add(keyword);
		}
		if (set.update()) {
			Type_KeyWordMap.clear();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}*/

	public static Mapx init(Mapx params) {
		Mapx map = new Mapx();
		map.put("employFlagInit", HtmlUtil.codeToOptions("Keyword.employFlag", true));
		map.put("belongCategoryInit", HtmlUtil.codeToOptions("Keyword.belongCategory", true));
		
		return map;
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		keyword.setID(ID);
		if (StringUtil.isNotEmpty(ID) && keyword.fill()) {
			params.putAll(keyword.toMapx());
		} else {
			params.put("LinkUrl", "http://");
			params.put("LinkTarget", "_blank");
		}
		
		//读取关键词画面的下拉框信息
		params.put("employFlagInit", HtmlUtil.codeToOptions("Keyword.employFlag", true));
		params.put("belongCategoryInit", HtmlUtil.codeToOptions("Keyword.belongCategory", true));
		
		return params;

	}

	public void add() {
		long siteID = Application.getCurrentSiteID();
		String KeyWord = $V("Keyword").trim();
		String LinkURL = StringUtil.noNull($V("LinkURL")).trim();
		String SearchCode = StringUtil.noNull($V("SearchCode")).trim();
		String PriorityLevel = StringUtil.noNull($V("PriorityLevel")).trim();
		String EmployFlag = $V("EmployFlag");
		if("".equals(SearchCode)){
			SearchCode = "0";
		}
		if("".equals(PriorityLevel)){
			PriorityLevel = "0";
		}
		
		//取出站点地址
		String siteUrl = new QueryBuilder("select Value from zdconfig where type='FrontServerContextPath'").executeString();
		
			int flag = new QueryBuilder("select count(*) from ZCKeyWord where SiteID=? and Keyword=? and KeywordType <> ',' ",siteID,KeyWord).executeInt();
			
			ZCKeywordSchema keyword = new ZCKeywordSchema();
			if (flag == 0) {
				
				if(StringUtil.isNotEmpty(LinkURL) && !"http://".equals(LinkURL)){
					if(LinkURL.indexOf(siteUrl) > -1){
						//地址有效性校验
						if(!check404(LinkURL)){
							return;
						}
					}else{
						Response.setStatus(0);
						Response.setMessage("着陆页地址错误，请输入开心保站内的有效地址!");
						return;
					}
				}else{
					//自动清空优先级、搜索值、是否收录
					PriorityLevel = "0";
					EmployFlag = "";
					SearchCode = "0";
					
				}
				
				if (StringUtil.isEmpty(LinkURL)) {
					keyword.setLinkUrl("http://");
				} else {
					keyword.setLinkUrl(LinkURL.trim());
				}
				
				keyword.setID(NoUtil.getMaxID("KeywordID"));
				keyword.setKeyword(KeyWord);
				keyword.setSiteId(siteID);
				keyword.setLinkTarget($V("LinkTarget"));
				keyword.setKeywordType("," + $V("selectdTypes") + ",");
				keyword.setLinkAlt($V("LinkAlt"));
				keyword.setAddTime(new Date());
				keyword.setAddUser(User.getUserName());
				keyword.setSearchCode(Long.parseLong(SearchCode));
				keyword.setPriorityLevel(Long.parseLong(PriorityLevel));
				keyword.setEmployFlag(EmployFlag);
				keyword.setBelongCategory($V("BelongCategory"));
				
				if (keyword.insert()) {
					Type_KeyWordMap.clear();
					Response.setStatus(1);
					Response.setMessage("新增成功！");
				} else {
					Response.setStatus(0);
					Response.setMessage("发生错误!");
				}
			} else {
				
				if(StringUtil.isNotEmpty(LinkURL) && !"http://".equals(LinkURL)){
					if(LinkURL.indexOf(siteUrl) > -1){
						//地址有效性校验
						if(!check404(LinkURL)){
							return;
						}
					}else{
						Response.setStatus(0);
						Response.setMessage("着陆页地址错误，请输入开心保站内的有效地址!");
						return;
					}
					
					QueryBuilder qb = new QueryBuilder("select count(*) from ZCKeyWord where LinkUrl=? and Keyword=? and SiteId = ?");
					qb.add(LinkURL);
					qb.add(KeyWord);
					qb.add(siteID);
					
					int flag1 = qb.executeInt();
					
					if(flag1 == 0){
						keyword.setLinkUrl(LinkURL);
					}else{
						Response.setStatus(0);
						Response.setMessage("此关键词已存在，着陆页地址重复,请重新输入着陆页!");
						return;
					}
				}else{
					Response.setStatus(0);
					Response.setMessage("此关键词已存在，着陆页不能为空!");
					return;
				}
				
				keyword.setID(NoUtil.getMaxID("KeywordID"));
				keyword.setKeyword(KeyWord);
				keyword.setSiteId(siteID);
				keyword.setLinkTarget($V("LinkTarget"));
				keyword.setKeywordType("," + $V("selectdTypes") + ",");
				keyword.setLinkAlt($V("LinkAlt"));
				keyword.setAddTime(new Date());
				keyword.setAddUser(User.getUserName());
				keyword.setSearchCode(Long.parseLong(SearchCode));
				keyword.setPriorityLevel(Long.parseLong(PriorityLevel));
				keyword.setEmployFlag($V("EmployFlag"));
				keyword.setBelongCategory($V("BelongCategory"));
				
				/*if (StringUtil.isNotEmpty($V("LinkAlt"))) {
					keyword.setLinkAlt($V("LinkAlt"));
				}
				keyword.setSearchCode(Long.parseLong(SearchCode));
				keyword.setPriorityLevel(Long.parseLong(PriorityLevel));
				keyword.setEmployFlag($V("EmployFlag"));
				keyword.setBelongCategory($V("BelongCategory"));
				
				keyword.setLinkTarget($V("LinkTarget"));
				
				String[] temp = $V("selectdTypes").split(","); 
				for (int i = 0; i < temp.length; i++) {
					if (keyword.getKeywordType().indexOf("," + temp[i] + ",") < 0) {
						keyword.setKeywordType(keyword.getKeywordType() + temp[i] + ",");
					}
				}*/

				if (keyword.insert()) {
					if (!StringUtil.checkID(keyword.getKeywordType())) {
						Response.setStatus(0);
						Response.setMessage("传入ID时发生错误!");
						return;
					}
					DataTable keywordTypeDT = new QueryBuilder("select * from ZCKeyWordType where SiteID=? and ID in ("
							+ keyword.getKeywordType().substring(1, keyword.getKeywordType().length() - 1) + ")", siteID)
							.executeDataTable();
					StringBuffer message = new StringBuffer();
					for (int i = 0; i < keywordTypeDT.getRowCount(); i++) {
						message.append(keywordTypeDT.get(i, "TypeName") + " ");
					}
					Response.setStatus(1);
					Response.setMessage("新增成功！此关键词同时存在于以下分类中：<br/>" + "<font color='red'>" + message + "</font>");
				} else {
					Response.setStatus(0);
					Response.setMessage("发生错误!");
				}
			}
	}
	
	public void edit() {
		long siteID = Application.getCurrentSiteID();
		String LinkURL = StringUtil.noNull($V("LinkURL")).trim();
		String SearchCode = StringUtil.noNull($V("SearchCode")).trim();
		String PriorityLevel = StringUtil.noNull($V("PriorityLevel")).trim();
		String EmployFlag = $V("EmployFlag");
		if("".equals(SearchCode)){
			SearchCode = "0";
		}
		if("".equals(PriorityLevel)){
			PriorityLevel = "0";
		}
		String siteUrl = new QueryBuilder("select Value from zdconfig where type='FrontServerContextPath'").executeString();
		
		String KeyWord = $V("Keyword").trim();
		String ID = $V("ID");
		
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		keyword.setID(ID);
		keyword.fill();
		
		QueryBuilder qb = new QueryBuilder("select count(*) from ZCKeyWord where ID != ? and SiteID=? and Keyword=?");
		qb.add(Long.parseLong(ID));
		qb.add(Application.getCurrentSiteID());
		qb.add(KeyWord);
		
		if (qb.executeInt() == 0) {
			
			if(StringUtil.isNotEmpty(LinkURL) && !"http://".equals(LinkURL)){
				if(LinkURL.indexOf(siteUrl) > -1){
					//地址有效性校验
					if(!check404(LinkURL)){
						return;
					}
				}else{
					Response.setStatus(0);
					Response.setMessage("着陆页地址错误，请输入开心保站内的有效地址!");
					return;
				}
			}else{
				//自动清空优先级、搜索值、是否收录
				PriorityLevel = "0";
				SearchCode = "0";
				EmployFlag = "";
			}
			
			keyword.setKeyword(KeyWord);
			keyword.setSiteId(siteID);
			keyword.setLinkUrl(LinkURL);
			keyword.setLinkTarget($V("LinkTarget"));
			keyword.setKeywordType("," + $V("selectdTypes") + ",");
			keyword.setLinkAlt($V("LinkAlt"));
			keyword.setModifyTime(new Date());
			keyword.setModifyUser(User.getUserName());
			keyword.setSearchCode(Long.parseLong(SearchCode));
			keyword.setPriorityLevel(Long.parseLong(PriorityLevel));
			keyword.setEmployFlag(EmployFlag);
			keyword.setBelongCategory($V("BelongCategory"));
			
			if (keyword.update()) {
				Type_KeyWordMap.clear();
				Response.setStatus(1);
				Response.setMessage("修改成功！");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			if(StringUtil.isNotEmpty(LinkURL) && !"http://".equals(LinkURL)){
				if(LinkURL.indexOf(siteUrl) > -1){
					//地址有效性校验
					if(!check404(LinkURL)){
						return;
					}
				}else{
					Response.setStatus(0);
					Response.setMessage("着陆页地址错误，请输入开心保站内的有效地址!");
					return;
				}
				
			}else{
				Response.setStatus(0);
				Response.setMessage("此关键词已存在，着陆页不能为空!");
				return;
			}
			
			keyword.setKeyword(KeyWord);
			keyword.setSiteId(siteID);
			keyword.setLinkUrl(LinkURL);
			keyword.setLinkTarget($V("LinkTarget"));
			keyword.setKeywordType("," + $V("selectdTypes") + ",");
			keyword.setLinkAlt($V("LinkAlt"));
			keyword.setModifyTime(new Date());
			keyword.setModifyUser(User.getUserName());
			keyword.setSearchCode(Long.parseLong(SearchCode));
			keyword.setPriorityLevel(Long.parseLong(PriorityLevel));
			keyword.setEmployFlag($V("EmployFlag"));
			keyword.setBelongCategory($V("BelongCategory"));

			if (keyword.update()) {
				if (!StringUtil.checkID(keyword.getKeywordType())) {
					Response.setStatus(0);
					Response.setMessage("传入ID时发生错误!");
					return;
				}
				DataTable keywordTypeDT = new QueryBuilder("select * from ZCKeyWordType where SiteID=? and ID in ("
						+ keyword.getKeywordType().substring(1, keyword.getKeywordType().length() - 1) + ")", siteID)
						.executeDataTable();
				StringBuffer message = new StringBuffer();
				for (int i = 0; i < keywordTypeDT.getRowCount(); i++) {
					message.append(keywordTypeDT.get(i, "TypeName") + " ");
				}
				Response.setStatus(1);
				Response.setMessage("修改成功！此关键词同时存在于以下分类中：<br/>" + "<font color='red'>" + message + "</font>");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		}
	}

	/*
	 * public void add() { ZCKeywordSchema keyword = new ZCKeywordSchema();
	 * keyword.setValue(Request); keyword.setAddTime(new Date());
	 * keyword.setAddUser(User.getUserName()); if (keyword.insert()) {
	 * Response.setStatus(1); Response.setMessage("新增成功！"); } else {
	 * Response.setStatus(0); Response.setMessage("发生错误!"); } }
	 */

	/**
	 * @Author 崔建成
	 * @Date 2010-1-28 关键词批量导入，支持txt、xls格式。
	 */
	public void importWords() {
		String FilePath = $V("FilePath");
		long SiteID = Application.getCurrentSiteID();
		Transaction trans = new Transaction();
		String Words = $V("KeyWords");
		String selectedCID = $V("selectedCID");
		if (StringUtil.isEmpty(selectedCID)) {
			selectedCID = "";
		}
		String wordsText = "";
		String[] keyWords = null;
		DataTable dt = null;
		if (StringUtil.isEmpty(FilePath)) {
			keyWords = Words.split("\n");
		} else if (FilePath.indexOf("txt") >= 0) {
			FilePath = FilePath.replaceAll("//", "/");
			wordsText = FileUtil.readText1(FilePath);
			keyWords = wordsText.split("\n");
		} else if (FilePath.indexOf("xls") >= 0) {
			try {
				FilePath = FilePath.replace("//", "/");
				dt = DataTableUtil.xlsToDataTable(FilePath);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			keyWords = new String[dt.getRowCount()];
			for (int i = 0; i < dt.getRowCount(); i++) {
				keyWords[i] = StringUtil.join(dt.getDataRow(i).toMapx().valueArray());				
			}
		}
		//优先级
		String PriorityLevel = "";
		//所属分类
		String EmployFlag = "";
		//搜索值
		String SearchCode = "";
		
		//取出站点地址
		String siteUrl = new QueryBuilder("select Value from zdconfig where type='FrontServerContextPath'").executeString();
		
		String temp = "";
		
		int errNum = 0;
		
		if(keyWords.length == 0){
			Response.setStatus(0);
			Response.setMessage("导入的数据为空，请严格按照要求编辑9项内容，txt格式以“,”隔开！");
			return ;
		}
		init();
		for (int i = 0; i < keyWords.length; i++) {
			
			try{
				
				errNum = i + 1;
				
				if (!(keyWords[i].equals("\r") || StringUtil.isEmpty(keyWords[i]))) {
					ZCKeywordSchema keyword = new ZCKeywordSchema();
					temp = keyWords[i];
					//xls模板中导入的列如果为空，用#代替
					temp = temp.trim().replaceAll("#", "");
					temp = temp.replaceAll("，", ",");
					String[] word = StringUtil.splitEx(temp, ",");
					/*if(keyWords.length==1){
						if (word.length != 9 || StringUtil.isEmpty(word[0])) {
							Response.setMessage("请严格按照示例格式编辑,9项内容以“，”隔开！");
							return ;
						}
					}*/
					if (word.length != 9) {
						Response.setStatus(0);
						Response.setMessage("第" + errNum + "条数据编辑错误，请严格按照要求编辑9项内容，txt格式以“,”隔开！");
						return ;
						
						//continue;
					} else {
						
						if(StringUtil.isEmpty(word[0]) || StringUtil.isEmpty(word[3]) || StringUtil.isEmpty(word[4])){
							Response.setStatus(0);
							Response.setMessage("第" + errNum + "条数据编辑错误，关键词、打开方式、内链分类为必录项，请重新编辑此条数据！");
							return ;
						}
						
						if(StringUtil.isNotEmpty(word[3])){
							if(!"1".equals(word[3].trim())&&!"2".equals(word[3].trim())&&!"3".equals(word[3].trim())){
								Response.setStatus(0);
								Response.setMessage("第" + errNum + "条数据编辑错误，打开方式录入错误，请参照页面提示合法录入此项数据！");
								return ;
							}
						}
						
						if(StringUtil.isNotEmpty(word[7])){
							if(!"01".equals(word[7].trim())&&!"02".equals(word[7].trim())){
								Response.setStatus(0);
								Response.setMessage("第" + errNum + "条数据编辑错误，是否收录录入错误，请参照页面提示合法录入此项数据！");
								return ;
							}
						}
						
						if(StringUtil.isNotEmpty(word[8])){
							Mapx bc = HtmlUtil.codeToMapx("Keyword.belongCategory");
							if(!bc.containsKey(word[8].trim())){
								Response.setStatus(0);
								Response.setMessage("第" + errNum + "条数据编辑错误，所属分类录入错误，请参照页面提示合法录入此项数据！");
								return ;
							}
						}
						
						boolean flag = false;
						if (new QueryBuilder("select count(*) from ZCKeyWord where KeyWord = ? and SiteID=? and KeywordType <> ','", word[0],
								SiteID).executeInt() > 0) {
							flag = true;
						} else {
							flag = false;
						}
						
						keyword.setID(NoUtil.getMaxID("KeywordID"));
						
						keyword.setKeyword(word[0]);
						keyword.setSiteId(SiteID);
						
						if (StringUtil.isNotEmpty(word[2])) {
							keyword.setLinkAlt(word[2].trim());
						}
					
						if (word[3].equals("1")) {
							keyword.setLinkTarget("_self");
						} else if (word[3].equals("2")) {
							keyword.setLinkTarget("_blank");
						} else {
							keyword.setLinkTarget("_parent");
						}
						
	
						String[] typeNames = word[4].split("/");
						for (int j = 0; j < typeNames.length; j++) {
							if (new QueryBuilder("select count(*) from ZCKeyWordType where TypeName = ?", typeNames[j])
									.executeInt() == 0) {
								ZCKeywordTypeSchema type = new ZCKeywordTypeSchema();
								type.setID(NoUtil.getMaxID("KeyWordTypeID"));
								type.setTypeName(typeNames[j].trim());
								type.setSiteID(Application.getCurrentSiteID());
								type.setAddTime(new Date());
								type.setAddUser(User.getUserName());
								type.insert();
							}
							typeNames[j] = "'" + typeNames[j].trim() + "'";
						}
						DataTable typeDT = new QueryBuilder(
								"select ID from ZCKeywordType where siteID = ? and TypeName in ("
										+ StringUtil.join(typeNames) + ")", SiteID).executeDataTable();
						for (int j = 0; j < typeDT.getRowCount(); j++) {
							if (keyword.getKeywordType() == null) {
								keyword.setKeywordType("," + typeDT.getString(j, 0));
							} else if (keyword.getKeywordType().indexOf(typeDT.getString(j, 0)) < 0) {
								keyword.setKeywordType(keyword.getKeywordType() + "," + typeDT.getString(j, 0));
							}
						}
						keyword.setKeywordType(keyword.getKeywordType() + ",");
						
						if (flag){
							if(StringUtil.isNotEmpty(word[1]) && !"http://".equals(word[1])){
								if(word[1].indexOf(siteUrl) > -1){
									//地址有效性校验
									if(!check404(word[1],errNum)){
										return;
									}
								}else{
									Response.setStatus(0);
									Response.setMessage("第" + errNum + "条数据着陆页地址错误，请输入开心保站内的有效地址!");
									return;
								}
								
								QueryBuilder qb = new QueryBuilder("select count(*) from ZCKeyWord where LinkUrl=? and Keyword=? and SiteId = ?");
								qb.add(word[1]);
								qb.add(word[0]);
								qb.add(SiteID);
								
								int flag1 = qb.executeInt();
								
								if(flag1 == 0){
									keyword.setLinkUrl(word[1]);
								}else{
									Response.setStatus(0);
									Response.setMessage("第" + errNum + "条数据的关键词已存在，着陆页地址重复,请重新输入着陆页!");
									return;
								}
							}else{
								Response.setStatus(0);
								Response.setMessage("第" + errNum + "条数据的关键词已存在，着陆页不能为空!");
								return;
							}
							
							SearchCode = word[5].trim();
							
							PriorityLevel = word[6].trim();
							
							EmployFlag = word[7].trim();
							
						}else{
							if(StringUtil.isNotEmpty(word[1]) && !"http://".equals(word[1])){
								
								if(word[1].indexOf(siteUrl) > -1){
									//地址有效性校验
									if(!check404(word[1],errNum)){
										return;
									}
								}else{
									Response.setStatus(0);
									Response.setMessage("第" + errNum + "条数据着陆页地址错误，请输入开心保站内的有效地址!");
									return;
								}
								
								keyword.setLinkUrl(word[1].trim());
								
								SearchCode = word[5].trim();
		
								PriorityLevel = word[6].trim();
								
								EmployFlag = word[7].trim();
								
							}else{
								keyword.setLinkUrl("http://");
								//自动清空优先级、搜索值、是否收录
								PriorityLevel = "0";
								SearchCode = "0";
								EmployFlag = "";
								
							}
						}
						
						//搜索值
						if(StringUtil.isEmpty(SearchCode)){
							SearchCode = "0";
						}
						keyword.setSearchCode(Long.parseLong(SearchCode));
						//优先级
						if(StringUtil.isEmpty(PriorityLevel)){
							PriorityLevel = "0";
						}
						keyword.setPriorityLevel(Long.parseLong(PriorityLevel));
						//是否收录
						if (StringUtil.isNotEmpty(EmployFlag)) {
							keyword.setEmployFlag(EmployFlag);
						}
						//所属分类
						if (StringUtil.isNotEmpty(word[8].trim())) {
							keyword.setBelongCategory(word[8].trim());
						}
						
						keyword.setAddTime(new Date());
						keyword.setAddUser(User.getUserName());
						trans.add(keyword, Transaction.INSERT);
						
					}
				}
			}catch(Exception e){
				Response.setStatus(0);
				Response.setMessage("第" + errNum + "条数据编辑错误，请严格按照要求编辑9项内容，txt格式以“,”隔开！");
				return ;
			}
		}

		if (trans.commit()) {
			Type_KeyWordMap.clear();
			Response.setLogInfo(1, "导入成功");
		} else {
			Response.setStatus(0);
			String errMsg = trans.getExceptionMessage();
			Response.setLogInfo(0, "导入失败:"+errMsg);
		}
		
		((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
	}
	
	/**
	 * @Author 王昌旸
	 * @Date 2013-08-09 搜索量批量导入，支持txt、xls格式。
	 */
	public void importSearchCodes() {
		String FilePath = $V("FilePath");
		long SiteID = Application.getCurrentSiteID();
		Transaction trans = new Transaction();
		String Codes = $V("SearchCodes");

		String wordsText = "";
		String[] SearchCodes = null;
		DataTable dt = null;
		if (StringUtil.isEmpty(FilePath)) {
			SearchCodes = Codes.split("\n");
		} else if (FilePath.indexOf("txt") >= 0) {
			FilePath = FilePath.replaceAll("//", "/");
			wordsText = FileUtil.readText1(FilePath);
			SearchCodes = wordsText.split("\n");
		} else if (FilePath.indexOf("xls") >= 0) {
			try {
				FilePath = FilePath.replace("//", "/");
				dt = DataTableUtil.xlsToDataTable(FilePath);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			SearchCodes = new String[dt.getRowCount()];
			for (int i = 0; i < dt.getRowCount(); i++) {
				SearchCodes[i] = StringUtil.join(dt.getDataRow(i).toMapx().valueArray());
				
//				if(keyWords[i].length()!=5){
//					Response.setMessage("请严格按照要求编辑5项内容，txt格式以“,”隔开！");
//					return ;
//				}				
			}
		}

		String temp = "";
		int errNum = 0;
		
		if(SearchCodes.length == 0){
			Response.setStatus(0);
			Response.setMessage("导入的数据为空，请严格按照要求编辑2项内容，txt格式以“,”隔开！");
			return ;
		}
		
		for (int i = 0; i < SearchCodes.length; i++) {
			
			errNum = i + 1;
			try{
				if (!(SearchCodes[i].equals("\r") || StringUtil.isEmpty(SearchCodes[i]))) {
					
					temp = SearchCodes[i];
					temp = temp.trim();
					temp = temp.replaceAll("，", ",");
					String[] code = StringUtil.splitEx(temp, ",");
					/*if(SearchCodes.length==1){
						if (code.length != 2 || StringUtil.isEmpty(code[0])) {
							Response.setMessage("第" + errNum + "条数据编辑错误，请严格按照示例格式编辑,2项内容以“，”隔开！");
							return ;
						}
					}*/
					if (code.length != 2) {
						Response.setStatus(0);
						Response.setMessage("第" + errNum + "条数据编辑错误，请严格按照要求编辑2项内容，txt格式以“,”隔开！");
						return ;
						
						//continue;
					} else {
						
						if(StringUtil.isEmpty(code[0])){
							Response.setStatus(0);
							Response.setMessage("第" + errNum + "条数据编辑错误，关键词为必录项，请重新编辑此条数据！");
							return ;
						}
						
						boolean flag = false;
						if (new QueryBuilder("select count(*) from ZCKeyWord where KeyWord = ? and SiteID=? ", code[0],
								SiteID).executeInt() > 0) {
							flag = true;
						} else {
							Response.setStatus(0);
							Response.setMessage("第" + errNum + "条数据关键词不存在，请严格按照要求编辑2项内容，txt格式以“,”隔开！");
							return ;
						}
						
						DataTable dtCode = new QueryBuilder("select ID from ZCKeyWord where KeyWord = ? and SiteID=? ",
								code[0].trim(), SiteID).executeDataTable();
						for (DataRow dr : dtCode) {
							
							ZCKeywordSchema keyword = new ZCKeywordSchema();
							
							keyword.setID(dr.getLong(0));
							keyword.fill();
						
							keyword.setKeyword(code[0]);
		
							if (StringUtil.isEmpty(code[1])) {
								long emptyCode = 0;
								keyword.setSearchCode(emptyCode);
							} else {
								keyword.setSearchCode(Long.parseLong(code[1]));
							}
		
							keyword.setModifyTime(new Date());
							keyword.setModifyUser(User.getUserName());
							trans.add(keyword, Transaction.UPDATE);
						
					    }
					}
				}
			}catch(Exception e){
				Response.setStatus(0);
				Response.setMessage("第" + errNum + "条数据编辑错误，请严格按照要求编辑2项内容，txt格式以“,”隔开！");
				return ;
			}
		}

		if (trans.commit()) {
			Type_KeyWordMap.clear();
			Response.setLogInfo(1, "导入成功");
		} else {
			Response.setStatus(0);
			String errMsg = trans.getExceptionMessage();
			Response.setLogInfo(0, "导入失败:"+errMsg);
		}

	}

	public void del() {
		String ids = $V("IDs");
		String selectedCID = $V("selectedCID");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		long SiteID = Application.getCurrentSiteID();
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		ZCKeywordSet set = keyword.query(new QueryBuilder("where SiteID=? and id in (" + ids + ")", SiteID));
		if (StringUtil.isEmpty(selectedCID)) {
			trans.add(set, Transaction.DELETE_AND_BACKUP);
		} else {
			for (int i = 0; i < set.size(); i++) {
				keyword = set.get(i);
				String keywordType = keyword.getKeywordType();
				if (keywordType.indexOf("," + selectedCID + ",") >= 0) {
					keyword.setKeywordType(keywordType.replaceAll("," + selectedCID + ",", ","));
				} else {
					ZCKeywordSchema keywordTemp = keyword;
					trans.add(keywordTemp, Transaction.DELETE_AND_BACKUP);
				}
			}
			trans.add(set, Transaction.UPDATE);
		}
		if (trans.commit()) {
			Type_KeyWordMap.clear();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		long siteID = Application.getCurrentSiteID();
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder("select ID,TypeName from ZCKeywordType Where SiteID=? order by ID", siteID);
		dt = qb.executeDataTable();
		ta.setRootText("关键词汇库");
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag"))) {
				item.setIcon("Icons/treeicon11.gif");
			}
		}
	}

	// public void sortKeyword() {
	// String target = $V("Target");
	// String orders = $V("Orders");
	// String type = $V("Type");
	// String typeID = $V("typeID");
	// boolean topFlag = "true".equals($V("TopFlag"));
	// if (!StringUtil.checkID(target) && !StringUtil.checkID(orders)) {
	// return;
	// }
	// Transaction tran = new Transaction();
	// if (topFlag) {
	// QueryBuilder qb = new
	// QueryBuilder("update ZCKeyword set TopFlag='1' where OrderFlag in (" +
	// orders + ")");
	// tran.add(qb);
	// } else {
	// QueryBuilder qb = new
	// QueryBuilder("update ZCKeyword set TopFlag='0' where OrderFlag in (" +
	// orders + ")");
	// tran.add(qb);
	// }
	// OrderUtil.updateOrder("ZCKeyword", "OrderFlag", type, target, orders,
	// null, tran);
	// if (tran.commit()) {
	// final String id = typeID;
	// LongTimeTask ltt = new LongTimeTask() {
	// public void execute() {
	// Publisher p = new Publisher();
	// p.publishCatalog(Long.parseLong(id), false, false, 3);
	// setPercent(100);
	// }
	// };
	// ltt.setUser(User.getCurrent());
	// ltt.start();
	//
	// Response.setMessage("操作成功");
	// } else {
	// Response.setError("操作失败");
	// }
	// }

	public void move() {
		String keywordIDs = $V("KeywordIDs");
		String tarTypeID = $V("TypeID");
		if (!StringUtil.checkID(keywordIDs)) {
			Response.setError("操作数据库时发生错误!");
			return;
		}
		if (!StringUtil.checkID(tarTypeID)) {
			Response.setError("传入TypeID时发生错误!");
			return;
		}

		Transaction trans = new Transaction();
		ZCKeywordSchema srcKeyword = new ZCKeywordSchema();
		ZCKeywordSet set = srcKeyword.query(new QueryBuilder("where id in (" + keywordIDs + ")"));

		for (int i = 0; i < set.size(); i++) {
			ZCKeywordSchema keyword = set.get(i);
			String keywordType = keyword.getKeywordType();
			if (keywordType.indexOf(tarTypeID) >= 0) {
				Response.setMessage("该分类下已经存在关键词\"" + keyword.getKeyword() + "\"");
				return;
			} else {
				keywordType += "," + tarTypeID;
				keyword.setKeywordType(keywordType);
			}

			trans.add(keyword, Transaction.UPDATE);
		}

		if (trans.commit()) {
			Response.setMessage("复制成功");
		} else {
			Response.setError("操作数据库时发生错误!");
		}
	}
	
	
	/**
	 * 模拟浏览器发送Http请求
	 * 
	 * @param url 验证地址
	 * 
	 * @return 验证结果
	 */
	@SuppressWarnings("finally")
	public synchronized boolean check404 (String url) {
		
		boolean result = false;
		
	    //创建HttpClient实例 
	    HttpClient client = new HttpClient(new HttpClientParams(),new SimpleHttpConnectionManager(true));
	    
	    //创建一个HttpMethod
	    HttpMethod method = new GetMethod();
	    
	    //执行方法  
	    try {
	    
		    //把参数按照UTF-8格式编码  
		    URI uri = new URI(url,false,"UTF-8");
		    url = uri.toString();
		    
		    method = new GetMethod(url);
		    
			int statusCode = client.executeMethod(method);  
			
			
			if (statusCode != HttpStatus.SC_OK) {
				if(statusCode == HttpStatus.SC_NOT_FOUND){
					Response.setStatus(0);
					Response.setMessage("着陆页地址404!");
				}else{
					Response.setStatus(0);
					Response.setMessage("着陆页地址错误，请输入开心保站内的有效地址!");
				}
			} else {
				result = true;
			}
	  
	    } catch (HttpException e) {
	    	Response.setStatus(0);
			Response.setMessage("网络验证异常，请联系管理员!");
	  
	    } catch (IOException e) {
	    	Response.setStatus(0);
			Response.setMessage("着陆页地址错误，请输入开心保站内的有效地址并检查网络是否畅通!");
	  
	    } finally {
	        //释放链接资源 
	    	method.releaseConnection();
	    	return result;
	  
	    }
	}
	
	/**
	 * 模拟浏览器发送Http请求,检查着陆页是否404
	 * 
	 * @param url 验证地址
	 * @param num 数据序号
	 * 
	 * @return 验证结果
	 */
	@SuppressWarnings("finally")
	public synchronized boolean check404 (String url,int num) {
		
		boolean result = false;
	      
	    //创建一个HttpMethod
	    HttpMethod method = new GetMethod();
	    
	    //执行方法  
	    try {
	    
		    //把参数按照UTF-8格式编码  
		    URI uri = new URI(url,false,"UTF-8");
		    url = uri.toString();
		    
		    method = new GetMethod(url);
		    
			int statusCode = httpClient.executeMethod(method);  
			
			if (statusCode != HttpStatus.SC_OK) {
				if(statusCode == HttpStatus.SC_NOT_FOUND){
					Response.setStatus(0);
					Response.setMessage("第" + num + "条数据着陆页地址404!");
				}else{
					Response.setStatus(0);
					Response.setMessage("第" + num + "条数据着陆页地址错误，请输入开心保站内的有效地址!");
				}
			} else {
				result = true;
			}
	  
	    } catch (HttpException e) {
	    	Response.setStatus(0);
			Response.setMessage("网络验证异常，请联系管理员!");
	  
	    } catch (IOException e) {
	    	Response.setStatus(0);
			Response.setMessage("第" + num + "条数据着陆页地址错误，请输入开心保站内的有效地址并检查网络是否畅通!");
	  
	    } finally {
	        //释放链接资源 
	    	method.releaseConnection();
	    	return result;
	  
	    }
	}
	
} 
