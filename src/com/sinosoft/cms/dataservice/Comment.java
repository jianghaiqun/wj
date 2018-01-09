/**简短介绍 
 * <p>Date        :</p> 
 * <p>Module      :评论管理 </p> 
 * <p>Description: 描述信息</p> 
 * <p>Remark      : </p> 
 * @author XXX
 * @version  
 * <p>------------------------------------------------------------</p> 
 * <p>  修改历史</p> 
 * <p>   序号            日期                                 修改人                                      修改原因</p> 
 * <p>    1   2014-07-09   jiaomengying     req20140702001-cms评论管理     </p> 
 * <p>    2   2014-12-08   jiaomengying     req20141205003-cms后台评论关联订单     </p>
 */

package com.sinosoft.cms.dataservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finance.util.JedisCommonUtil;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Constant;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.controls.DataListAction;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.controls.TreeItem;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.CommentOperationInfoSchema;
import com.sinosoft.schema.ZCCommentSchema;
import com.sinosoft.schema.ZCCommentSet;

import cn.com.sinosoft.util.RedisConsts;


public class Comment extends Page {

	public static Mapx initDetail(Mapx params) {
		String id = params.getString("ID");
		if (StringUtil.isNotEmpty(id)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(id);
			if (comment.fill()) {
				params.putAll(comment.toMapx());
				params.put("VerifyFlag", CacheManager.getMapx("Code", "Comment.Status").get(params.get("VerifyFlag")));
				String addTimeStr = params.getString("AddTime");
				params.put("AddTime", addTimeStr.substring(0, addTimeStr.lastIndexOf(".")));
			}
		}
		return params;
	}
	
	/**
	 * 保存客服回复
	 */
	public void save() {
		Transaction trans=new Transaction(); 
		ZCCommentSchema comment = new ZCCommentSchema();
		String id =	$V("ID");
		String replyContent = $V("ReplyContent");
		comment.setID(id);
		if (comment.fill()) {
			comment.setReplyContent(replyContent);
			trans.add(comment, Transaction.UPDATE);
		} else {
			Response.setLogInfo(1, "此评论不存在！");
			return;
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "回复成功！");
		} else {
			Response.setLogInfo(0, "保存发生错误!");
		}
	}
	
	public static Mapx initReplyContent(Mapx params) {
		String id = params.getString("ID");
		if (StringUtil.isNotEmpty(id)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(id);
			if (comment.fill()) {
				params.put("ReplyContent", comment.getReplyContent());
			}
		}
		return params;
	}
	
	public static void treeDataBind(TreeAction ta) {
		long SiteID = Application.getCurrentSiteID();
		DataTable dt = null;
		Mapx params = ta.getParams();
		// 是否是评测评论管理
		int evalFlag = 0;
		String catalogId = "";
		if (params.containsKey("evalFlag")){
			evalFlag = params.getInt("evalFlag");
			// 获取保险测评Id
			QueryBuilder qb = new QueryBuilder("select id from ZCCatalog where Name = '"+ Constant.EVALUATING_NAME + "'");
			dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				catalogId = dt.getString(0,0);
			}
		}
		String evalSql = "";
		if (!"".equals(catalogId)) {
			evalSql = " and (parentid = '"+ catalogId +"' OR (parentid = '0' AND id ='"+ catalogId +"')) ";
		}


		long CatalogType = params.getLong("Type");
		int parentLevel = params.getInt("ParentLevel");
		String parentID = (String) params.get("ParentID");
		String rootText = "";
		if (CatalogType == 1) {
			rootText = "文档库";
		} else if (CatalogType == 4) {
			rootText = "图片库";
		} else if (CatalogType == 5) {
			rootText = "视频库";
		} else if (CatalogType == 6) {
			rootText = "音频库";
		} else if (CatalogType == 7) {
			rootText = "附件库";
		}
		String sql = "";
		if (ta.isLazyLoad()) {
			sql = "select ID,ParentID,TreeLevel,Name,SingleFlag,prop1 from ZCCatalog Where Type=? and SiteID=? and TreeLevel>? and innerCode like ? order by orderflag";
			if (evalFlag == 1) {
				sql = "select ID,ParentID,TreeLevel,Name,SingleFlag,prop1 from ZCCatalog Where Type=? and SiteID=? "+ evalSql + "and TreeLevel>? and innerCode like ? order by orderflag";
			}
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(CatalogType);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			sql = "select ID,ParentID,TreeLevel,Name,SingleFlag,prop1 from ZCCatalog Where Type=? and SiteID=? and TreeLevel-1<=? order by orderflag";
			if (evalFlag == 1) {
				sql = "select ID,ParentID,TreeLevel,Name,SingleFlag,prop1 from ZCCatalog Where Type=? and SiteID=? " + evalSql + "and TreeLevel-1<=? order by orderflag";
			}
			QueryBuilder qb = new QueryBuilder(sql);
			qb.add(CatalogType);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}
		ta.setRootText(rootText);
		dt.setWebMode(false);
		ta.bindData(dt);
		if (CatalogType == 1) {
			List items = ta.getItemList();
			for (int i = 1; i < items.size(); i++) {
				TreeItem item = (TreeItem) items.get(i);
				if ("Y".equals(item.getData().getString("SingleFlag"))) {
					item.setIcon("Icons/treeicon11.gif");
				}
			}
		}
	}
	/**
	 * 问答栏目-选择树
	 * 
	 * @param ta
	 */
	public static void treeDataBindQ(TreeAction ta) {
		long SiteID = Application.getCurrentSiteID();
		DataTable dt = null;
		Mapx params = ta.getParams();
		long CatalogType = params.getLong("Type");
		int parentLevel = params.getInt("ParentLevel");
		int tSign = params.getInt("Sign");
		String parentID = (String) params.get("ParentID");
		String rootText = "";
		if (CatalogType == 1) {
			rootText = "文档库";
		} else if (CatalogType == 4) {
			rootText = "图片库";
		} else if (CatalogType == 5) {
			rootText = "视频库";
		} else if (CatalogType == 6) {
			rootText = "音频库";
		} else if (CatalogType == 7) {
			rootText = "附件库";
		}
		String str="";
		if(tSign==1){
			str="and CatalogType='02'";
		}else if(tSign==2){
			str="and CatalogType='03'";
		}
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,prop1 from ZCCatalog Where Type=? and SiteID=? and TreeLevel>? and innerCode like ? "+str+"order by orderflag");
			qb.add(CatalogType);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,prop1 from ZCCatalog Where Type=? and SiteID=? and TreeLevel-1<=? "+str+"order by orderflag");
			qb.add(CatalogType);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}
		ta.setRootText(rootText);
		dt.setWebMode(false);
		ta.bindData(dt);
		if (CatalogType == 1) {
			List items = ta.getItemList();
			for (int i = 1; i < items.size(); i++) {
				TreeItem item = (TreeItem) items.get(i);
				if ("Y".equals(item.getData().getString("SingleFlag"))) {
					item.setIcon("Icons/treeicon11.gif");
				}
			}
		}
	}
	public static void dg1DataBind(DataListAction dla) {
		String CatalogID = dla.getParam("CatalogID");
		String CatalogType = dla.getParam("CatalogType");
		String VerifyStatus = dla.getParam("VerifyStatus");
		String isBuy = dla.getParam("isBuy");
		String productName = dla.getParam("productName");
		String uploadStatus = dla.getParam("uploadStatus");
		QueryBuilder qb = new QueryBuilder("select b.ordersn,c.receiveDate,ZCComment.*,(select Name from zccatalog "
				+ "where zccatalog.ID=ZCComment.CatalogID) as CatalogName,");
		qb.append("if((ReplyContent is not null && ReplyContent != ''),'','none') as replayStyle ,");
		if (StringUtil.isEmpty(CatalogType)) {
			CatalogType = "1";
		}
		if (StringUtil.isEmpty(VerifyStatus)) {
			VerifyStatus = "X";
		}
		if (CatalogType.equals("1")) {
			qb.append("(select Title from ZCArticle where ZCArticle.ID=ZCComment.RelaID) as Name");
		} else if (CatalogType.equals("4")) {
			qb.append("(select Name from ZCImage where ZCImage.ID=ZCComment.RelaID) as Name");
		} else if (CatalogType.equals("5")) {
			qb.append("(select Name from ZCVideo where ZCVideo.ID=ZCComment.RelaID) as Name");
		} else if (CatalogType.equals("6")) {
			qb.append("(select Name from ZCAudio where ZCAudio.ID=ZCComment.RelaID) as Name");
		} else if (CatalogType.equals("7")) {
			qb.append("(select Name from ZCAttachment where ZCAttachment.ID=ZCComment.RelaID) as Name");
		}
		qb.append(" from ZCComment LEFT JOIN sdorders b ON b.commentid = ZCComment.id  LEFT JOIN tradeinformation c ON b.ordersn=c.ordid where SiteID=?", Application.getCurrentSiteID());
		qb.append(" and isBuy=?", isBuy);
		qb.append(" and CatalogType=?", Long.parseLong(CatalogType));
		if (StringUtil.isNotEmpty(CatalogID)) {
			qb.append(" and CatalogID=?", Long.parseLong(CatalogID));
		}
		if (!"All".equals(VerifyStatus)) {
			qb.append(" and VerifyFlag=?", VerifyStatus);
		}
		if (StringUtil.isNotEmpty(productName)) {
			qb.append(" and (select Title from ZCArticle where ZCArticle.ID=ZCComment.RelaID) like '%"+productName+"%'");
		}
		if (StringUtil.isNotEmpty(uploadStatus)) {
			if("Y".equals(uploadStatus)){
				qb.append(" and b.ordersn is null or b.ordersn=''");
			}else if("N".equals(uploadStatus)){
				qb.append(" and b.ordersn is not null and b.ordersn <> ''");
			}
		}
		if (StringUtil.isNotEmpty(productName)) {
			qb.append(" order by RelaID asc, sortNum asc, praisedNum desc, ID desc");
		} else {
			qb.append(" order by VerifyFlag asc, ID desc");
		}
		
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("PreLink");
		dt.insertColumn("FlagColor");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "CatalogType").equals("1")) {
				dt.set(i, "PreLink", "../Document/Preview.jsp?ArticleID=" + dt.getString(i, "RelaID"));
			} else {
				dt.set(i, "PreLink", "#;");
			}
			if (dt.getString(i, "VerifyFlag").equals("Y")) {
				dt.set(i, "FlagColor", "#00ff00");
			} else if (dt.getString(i, "VerifyFlag").equals("X")) {
				dt.set(i, "FlagColor", "#ff7700");
			} else {
				dt.set(i, "FlagColor", "#ff0000");
			}
		}
		dt.decodeColumn("VerifyFlag", CacheManager.getMapx("Code", "Comment.Status"));
		if (dt.getRowCount() == 0) {
			dt.insertRow((Object[]) null);
			dt.set(0, "ID", "0");
		}
		dla.bindData(dt);
	}

	/**
	 * 获取评测评论数据
	 *
	 * @param dla
	 */
	public static void EvalComDataBind(DataListAction dla) {
		String CatalogID = dla.getParam("CatalogID");
		String VerifyStatus = dla.getParam("VerifyStatus");
		String commentTitle = dla.getParam("commentTile");
		QueryBuilder qb = new QueryBuilder("select ZCComment.*,(select Name from zccatalog where zccatalog.ID=ZCComment.CatalogID) as CatalogName,");
		qb.append("if((ReplyContent is not null && ReplyContent != ''),'','none') as replayStyle ");
		if (StringUtil.isEmpty(VerifyStatus)) {
			VerifyStatus = "X";
		}
		qb.append(" from ZCComment where SiteID=?", Application.getCurrentSiteID());
		qb.append(" and CatalogType=1");
		if (!"All".equals(VerifyStatus)) {
			qb.append(" and VerifyFlag=?", VerifyStatus);
		}
		if (StringUtil.isNotEmpty(CatalogID)) {
			qb.append(" and CatalogID=?", Long.parseLong(CatalogID));
		} else {
			// 获取保险测评Id
			QueryBuilder qb1 = new QueryBuilder("select id from ZCCatalog where Name = '"+ Constant.EVALUATING_NAME + "'");
			DataTable dt1 = qb1.executeDataTable();
			String catalogId = "";
			if (dt1 != null && dt1.getRowCount() > 0) {
				catalogId = dt1.getString(0,0);
			}

			// 获得所有评测的栏目ID，包括子栏目
			qb1 = new QueryBuilder(" select id from ZCCatalog where (parentid = '"+ catalogId +"' OR (parentid = '0' AND id ='"+ catalogId +"')) ");
			dt1 = qb1.executeDataTable();
			String sqlParam = "";
			for (int i = 0; i < dt1.getRowCount(); i++) {
				sqlParam += ",'" + dt1.getString(i,0) +"'";
			}
			qb.append(" and CatalogID in(" + sqlParam.substring(1) + ")");
		}
		if (StringUtil.isNotEmpty(commentTitle)) {
			qb.append(" and title like '%"+commentTitle+"%'");
		}
		if (StringUtil.isNotEmpty(commentTitle)) {
			qb.append(" order by RelaID asc, sortNum asc, praisedNum desc, ID desc");
		} else {
			qb.append(" order by VerifyFlag asc, ID desc");
		}

		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dt.insertColumn("PreLink");
		dt.insertColumn("FlagColor");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.getString(i, "CatalogType").equals("1")) {
				dt.set(i, "PreLink", "../Document/Preview.jsp?ArticleID=" + dt.getString(i, "RelaID"));
			} else {
				dt.set(i, "PreLink", "#;");
			}
			if (dt.getString(i, "VerifyFlag").equals("Y")) {
				dt.set(i, "FlagColor", "#00ff00");
			} else if (dt.getString(i, "VerifyFlag").equals("X")) {
				dt.set(i, "FlagColor", "#ff7700");
			} else {
				dt.set(i, "FlagColor", "#ff0000");
			}
		}
		dt.decodeColumn("VerifyFlag", CacheManager.getMapx("Code", "Comment.Status"));
		if (dt.getRowCount() == 0) {
			dt.insertRow((Object[]) null);
			dt.set(0, "ID", "0");
		}
		dla.bindData(dt);
	}

	public void Verify() {
		String ID = $V("ID");
		String Type = $V("Type");
		String IDs = $V("IDs");
		List<Map<String,Object>> relaIDs = new ArrayList<Map<String,Object>>();
		if (StringUtil.isNotEmpty(ID) && StringUtil.isEmpty(IDs)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			comment.setID(ID);
			comment.fill();
			Map<String,Object> relaId = new HashMap<String,Object>();
			relaId.put("id", comment.getRelaID());
			if (Type.equals("Pass")) {
				// 由状态是非审核通过更新为审核通过后增加redis中产品评论数
				if(!"Y".equals(comment.getVerifyFlag())){
					relaId.put("type", "Pass");
					relaIDs.add(relaId);
				}
				comment.setVerifyFlag("Y");
			} else if (Type.equals("NoPass")) {
				// 由状态是审核通过更新为审核不通过后减少redis中产品评论数
				if("Y".equals(comment.getVerifyFlag())){
					relaId.put("type", "NoPass");
					relaIDs.add(relaId);
				}
				comment.setVerifyFlag("N");
			}
			comment.setVerifyUser(User.getUserName());
			comment.setVerifyTime(new Date());
			if (comment.update()) {
				updateCommentCountInRedis(relaIDs);
				Response.setLogInfo(1, "审核成功");
			} else {
				Response.setLogInfo(0, "审核失败");
			}
		} else if (StringUtil.isNotEmpty(IDs) && StringUtil.isEmpty(ID)) {
			ZCCommentSchema comment = new ZCCommentSchema();
			ZCCommentSet set = comment.query(new QueryBuilder("where ID in (" + IDs + ")"));
			Transaction trans = new Transaction();
			for (int i = 0; i < set.size(); i++) {
				comment = set.get(i);
				Map<String,Object> relaId = new HashMap<String,Object>();
				relaId.put("id", comment.getRelaID());
				if (Type.equals("Pass")) {
					if(!"Y".equals(comment.getVerifyFlag())){
						relaId.put("type", "Pass");
						relaIDs.add(relaId);
					}
					comment.setVerifyFlag("Y");
				} else if (Type.equals("NoPass")) {
					if("Y".equals(comment.getVerifyFlag())){
						relaId.put("type", "NoPass");
						relaIDs.add(relaId);
					}
					comment.setVerifyFlag("N");
				}
				comment.setVerifyUser(User.getUserName());
				comment.setVerifyTime(new Date());
				trans.add(comment, Transaction.UPDATE);
			}
			if (trans.commit()) {
				updateCommentCountInRedis(relaIDs);
				Response.setLogInfo(1, "审核成功");
			} else {
				Response.setLogInfo(0, "审核失败");
			}
		}
	}
	
	/**
	 * updateCommentCount:(更新redis中产品评论数量). <br/>
	 * @author guozc
	 * @param relaIDs
	 */
	public void updateCommentCountInRedis(List<Map<String,Object>> relaIDs){
		if(relaIDs != null && relaIDs.size() > 0){
			Map<Long,String> relaIDToProductID = new HashMap<Long,String>();
			String productId = null;
			Long relaId = null;
			String type = null;
			for(Map<String,Object> relaID : relaIDs){
				relaId = (Long)relaID.get("id");
				type = (String)relaID.get("type");
				productId = relaIDToProductID.get(relaId);
				if(productId == null){
					QueryBuilder qb = new QueryBuilder("SELECT a.productid FROM sdsearchrelaproduct a"
							+ ",zcarticle b WHERE a.prop1 = b.id AND b.id = ?",relaId);
					productId = qb.executeString();
					if(StringUtil.isEmpty(productId)){
						continue;
					}
					relaIDToProductID.put(relaId, productId);
				}
				String cacheKey = RedisConsts.KEY_PREFIX_PRODUCTATTR + productId;//key
				String mapKey = RedisConsts.MAPKEY_PRODUCTATTR_COMMENTCOUNT;//map key名
				int dbIndex = RedisConsts.DB_PRODUCT_ATTR;
				// 缓存中有相应key才操作!!
				if(JedisCommonUtil.hExists(dbIndex, cacheKey, mapKey)){
					if("Pass".equals(type)){
						logger.info("审核通过：增加redis产品-{}评论数量", productId);
						JedisCommonUtil.hincrBy(dbIndex, cacheKey, mapKey, 1);
					}else if("NoPass".equals(type)){
						logger.info("审核不通过：减少redis产品-{}评论数量", productId);
						JedisCommonUtil.hincrBy(dbIndex, cacheKey, mapKey, -1);
					}else if("Delete".equals(type)){
						logger.info("删除状态是审核通过的评论：减少redis产品-{}评论数量", productId);
						JedisCommonUtil.hincrBy(dbIndex, cacheKey, mapKey, -1);
					}
				}
			}
		}
	}

	public void del() {
		String ids = $V("IDs");
		String type = $V("type");
		if (ids.indexOf("\"") >= 0 || ids.indexOf("\'") >= 0) {
			Response.setLogInfo(0, "传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();
		ZCCommentSet set = task.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);
		
		// 删除状态是审核通过的评论后减少redis中评论数量
		List<Map<String,Object>> relaIDs = new ArrayList<Map<String,Object>>();
		ZCCommentSchema comment = null;
		for (int i = 0; i < set.size(); i++) {
			comment = set.get(i);
			if("Y".equals(comment.getVerifyFlag())){
				Map<String,Object> relaId = new HashMap<String,Object>();
				relaId.put("id", comment.getRelaID());
				relaId.put("type", "Delete");
				relaIDs.add(relaId);
			}
		}
		
		if (trans.commit()) {
			Response.setLogInfo(1, "删除" + type + "成功!");
			updateCommentCountInRedis(relaIDs);
		} else {
			Response.setLogInfo(0, "删除" + type + "失败!");
		}
	}

	public void addSupporterCount() {
		String ip = Request.getClientIP();
		String id = $V("ID");

		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();

		task.setID(id);
		task.fill();
		String supportAntiIP = task.getSupportAntiIP();
		if (StringUtil.isNotEmpty(supportAntiIP) && supportAntiIP.indexOf(ip) >= 0) {
			Response.setMessage("您已经评论过，谢谢支持！");
			Response.put("count", task.getSupporterCount());
			return;
		}

		long count = task.getSupporterCount();

		task.setSupporterCount(count + 1);
		task.setSupportAntiIP((StringUtil.isEmpty(supportAntiIP) ? "" : supportAntiIP) + ip);
		trans.add(task, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("您的评论提交成功！");
			Response.put("count", count + 1);
		} else {
			Response.setLogInfo(0, "审核失败");
		}
	}

	public void addAntiCount() {
		String ip = Request.getClientIP();
		String id = $V("ID");

		Transaction trans = new Transaction();
		ZCCommentSchema task = new ZCCommentSchema();

		task.setID(id);
		task.fill();
		String supportAntiIP = task.getSupportAntiIP();
		if (StringUtil.isNotEmpty(supportAntiIP) && supportAntiIP.indexOf(ip) >= 0) {
			Response.setMessage("您已经评论过，谢谢支持！");
			Response.put("count", task.getAntiCount());
			return;
		}
		long count = task.getAntiCount();

		task.setAntiCount(count + 1);
		task.setSupportAntiIP((StringUtil.isEmpty(supportAntiIP) ? "" : supportAntiIP) + ip);
		trans.add(task, Transaction.UPDATE);
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("您的评论提交成功！");
			Response.put("count", count + 1);
		} else {
			Response.setLogInfo(0, "审核失败");
		}

	}

	/**
	 * 修改评论时间--保存
	 * 
	 * 2014-07-09 jiaomengying
	 */
	public void saveCommTime() {
		String ID = $V("CID");
		String time = $V("time");
		Date date = DateUtil.parse(time, "yyyy-MM-dd HH:mm:ss");
		Transaction transaction = new Transaction();
		ZCCommentSchema ZCComment = new ZCCommentSchema();
		ZCComment.setID(ID);
		if (ZCComment.fill()) {
			ZCComment.setAddTime(date);
			transaction.add(ZCComment, Transaction.UPDATE);
		}
		if (transaction.commit()) {
			Response.setLogInfo(1, "保存成功");
		} else {
			Response.setLogInfo(0, "保存失败");
		}

	}
	
	/**
	 * 保存排序数、点赞数
	 */
	public void saveCommSort() {
		String ID = $V("CID");
		String sortNum = $V("sortNum").trim();
		String praisedNum = $V("praisedNum").trim();
		if (StringUtil.isEmpty(sortNum)) {
			sortNum = "0";
		}
		if (StringUtil.isEmpty(praisedNum)) {
			praisedNum = "0";
		}
		Transaction transaction = new Transaction();
		ZCCommentSchema ZCComment = new ZCCommentSchema();
		ZCComment.setID(ID);
		if (ZCComment.fill()) {
			int sort = ZCComment.getsortNum();
			int praNum = ZCComment.getpraisedNum();
			if (!String.valueOf(sort).equals(sortNum) || !String.valueOf(praNum).equals(praisedNum)) {
				ZCComment.setsortNum(sortNum);
				ZCComment.setpraisedNum(praisedNum);
				CommentOperationInfoSchema sche = new CommentOperationInfoSchema();
				sche.setid(NoUtil.getMaxNo("CommentOperationID", 15));
				sche.setcommentId(ID);
				sche.setprevSortNum(String.valueOf(sort));
				sche.setprevPraisedNum(String.valueOf(praNum));
				sche.setsortNum(sortNum);
				sche.setpraisedNum(praisedNum);
				sche.setMakeDate(new Date());
				sche.setMakeUser(User.getUserName());
				transaction.add(ZCComment, Transaction.UPDATE);
				transaction.add(sche, Transaction.INSERT);
				
				if (transaction.commit()) {
					Response.setLogInfo(1, "保存成功");
				} else {
					Response.setLogInfo(0, "保存失败");
				}
				
			} else {
				Response.setLogInfo(0, "排序数、点赞数未改变！");
			}
			
		} else {
			Response.setLogInfo(0, "未查询到该评论！");
		}
		
	}
	
	public static void dg2DataBind(DataGridAction dla) {
		String userIP = dla.getParam("userIP");
		String relaID = dla.getParam("relaID");
		String productName = dla.getParam("productName");
		QueryBuilder qb = new QueryBuilder("select p.userIP,p.praisedDate,p.RelaID,p.productName,p.orderSn,c.Content from CommentPraisedInfo p, zccomment c where p.isPraised = 'Y' and p.commentId = c.ID");
		if (StringUtil.isNotEmpty(userIP)) {
			qb.append(" and p.userIP like '%"+userIP+"%' ");
		}
		if (StringUtil.isNotEmpty(relaID)) {
			qb.append(" and p.RelaID = '"+relaID+"' ");
		}
		if (StringUtil.isNotEmpty(productName)) {
			qb.append(" and p.productName like '%"+productName+"%' ");
		}
		
		qb.append(" order by p.praisedDate desc ");
		dla.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
		dla.bindData(dt);
	}
	
	public static void dg3DataBind(DataGridAction da) {
		String commentId = da.getParam("ID");
		QueryBuilder qb = new QueryBuilder("select prevSortNum, prevPraisedNum, sortNum, praisedNum, MakeUser, MakeDate from CommentOperationInfo where commentId = ? order by MakeDate desc ", commentId);
		da.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(da.getPageSize(), da.getPageIndex());
		da.bindData(dt);
	}
}
