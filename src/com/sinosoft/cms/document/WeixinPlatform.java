package com.sinosoft.cms.document;

import java.util.Date;

import cn.com.sinosoft.util.CommonUtil;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserLog;
import com.sinosoft.schema.WeixinPlatformSchema;
import com.sinosoft.schema.WeixinPlatformSet;
import com.sinosoft.schema.WeixinPlatformUserSchema;
import com.sinosoft.weixin.WeiXinCommon;


public class WeixinPlatform extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from WeixinPlatform ");
		qb.append(" where 1=1 ");
		String sceneId = dga.getParam("sceneId");
		if (StringUtil.isNotEmpty(sceneId)) {
			qb.append(" and sceneId = ? ", sceneId);
		}
		qb.append(" ORDER BY LPAD(sceneId, 4, 0) DESC ");

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
	}

	public void add() {
		String sceneId = $V("sceneId");
		QueryBuilder qb = new QueryBuilder(
				"select id, sceneId, sceneName, ticket, AddTime, AddUser from WeixinPlatform where 1 = 1 ");
		qb.append(" and sceneId = ?", sceneId);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			Response.setStatus(0);
			Response.setMessage("该场景值已存在!");
			return;
		}
		
		String token = WeiXinCommon.ajaxtoken();
		String ticket = WeiXinCommon.ajaxticket(token, sceneId);
				
		WeixinPlatformSchema schema = new WeixinPlatformSchema();
		schema.setid(CommonUtil.getUUID());
		schema.setValue(Request);
		schema.setuseFlag("Y");
		schema.setticket("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
		schema.setAddTime(new Date());
		schema.setAddUser(User.getUserName());
		if (schema.insert()) {
			Response.setStatus(1);
			Response.setMessage("新增场景成功！");
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		ids = ids.replaceAll(",", "','");
		Transaction trans = new Transaction();
		WeixinPlatformSchema schema = new WeixinPlatformSchema();
		WeixinPlatformSet set = schema.query(new QueryBuilder("where id in ('" + ids + "')"));
		trans.add(set, Transaction.DELETE);
		if (trans.commit()) {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCONFIG, ids + "场景删除成功", Request.getClientIP());
			Response.setStatus(1);
			Response.setMessage("删除成功！");
		} else {
			UserLog.log(UserLog.SYSTEM, UserLog.SYSTEM_DELCONFIG, ids + "场景删除失败", Request.getClientIP());
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}

	public void startUse() {
		String id = $V("id");
		String useFlag = $V("useFlag");
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder("update ZDConfig set useFlag=? where id=?");
		qb.add(useFlag);
		qb.add(id);
		trans.add(qb);
		
		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("修改成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("修改失败!");
		}
	}
	
	public static void dg2DataBind(DataGridAction dga) {
		
		QueryBuilder qb = new QueryBuilder(
				"SELECT id,sceneId,CASE WHEN STATUS = '0' THEN '未关注用户扫描' ELSE '已关注用户扫描' END AS STATUS,CreateTime,FromUserName,ToUserName, AddTime,"
				+ " u.nickname, CASE u.sex WHEN '1' THEN '男' WHEN '2' THEN '女' ELSE '其他' END AS sex, u.city, u.province "
				+ "  FROM WeixinPlatformRecord left join WeixinPlatformUser u on u.openid = FromUserName ");
		
		qb.append(" where 1=1 ");
		String sceneId = dga.getParam("sceneId");
		if (StringUtil.isNotEmpty(sceneId)) {
			qb.append(" and sceneId = ? ", sceneId);
		}
		String status = dga.getParam("Status");
		if (StringUtil.isNotEmpty(status)) {
			qb.append(" and STATUS = ? ", status);
		}
		String createStartDate = (String) dga.getParams().get("createStartDate");
		String createEndDate = (String) dga.getParams().get("createEndDate");

		if (StringUtil.isNotEmpty(createStartDate)) {
			createStartDate += " 00:00:00";
			qb.append(" and CreateTime >= ? ", createStartDate);
		}
		if (StringUtil.isNotEmpty(createEndDate)) {
			createEndDate += " 23:59:59";
			qb.append(" and CreateTime <= ? ", createEndDate);
		}
		
		String openid = dga.getParam("openid");
		if (StringUtil.isNotEmpty(openid)) {
			qb.append(" and FromUserName = ? ", openid);
		}
		
		String nickname = dga.getParam("nickname");
		if (StringUtil.isNotEmpty(nickname)) {
			qb.append(" and u.nickname like ?", "%" + nickname + "%");
		}
		
		String sex = dga.getParam("sex");
		if (StringUtil.isNotEmpty(sex)) {
			qb.append(" and u.sex = ? ", sex);
		}
		
		String city = dga.getParam("city");
		if (StringUtil.isNotEmpty(city)) {
			qb.append(" and u.city = ? ", city);
		}
		
		String province = dga.getParam("province");
		if (StringUtil.isNotEmpty(province)) {
			qb.append(" and u.province = ? ", province);
		}
		
		qb.append(" order by CreateTime desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		dga.bindData(dt);
	}
	
	/**
	 * 初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initUserShowDialog(Mapx params) {
		String openid = params.getString("openid");
		WeixinPlatformUserSchema user = new WeixinPlatformUserSchema();
		user.setopenid(openid);
		if (user.fill()){
			params.put("openid", user.getopenid());
			params.put("nickname", user.getnickname());
			if ("1".equals(user.getsex())) {
				params.put("sex", "男");
			} else if ("2".equals(user.getsex())) {
				params.put("sex", "女");
			} else {
				params.put("sex", "未知");
			}
			params.put("language", user.getlanguage());
			params.put("city", user.getcity());
			params.put("province", user.getprovince());
			params.put("country", user.getcountry());
			params.put("headimgurl", user.getheadimgurl());
			params.put("subscribe_time", user.getsubscribe_time());
			params.put("remark", user.getremark());
		}
		
		return params;
	}
}
