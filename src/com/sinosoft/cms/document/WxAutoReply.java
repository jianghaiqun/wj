package com.sinosoft.cms.document;
 
import java.util.Date;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.WxResponseInfoSchema;
import com.sinosoft.schema.WxResponseInfoSet;
import com.sinosoft.schema.ZDCodeSchema;
import com.sinosoft.schema.ZDCodeSet;

/**
 * 微信自动回复管理
 * 
 */
public class WxAutoReply extends Page {

	// 编辑器页面初始化
	public static Mapx initDialog(Mapx params) {
		Mapx map = new Mapx();
		String ID = (String) params.get("ID");
		QueryBuilder qb = new QueryBuilder(" SELECT * FROM WxResponseInfo WHERE id=? ");
		qb.add(ID);
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			map.put("ID", dt.getString(0, "ID"));
			map.put("MenuID", dt.getString(0, "MenuID"));
			map.put("MenuAttribute", dt.getString(0, "MenuAttribute"));
			map.put("MenuURL", dt.getString(0, "MenuURL"));
			map.put("ResponseType", dt.getString(0, "ResponseType"));
			map.put("TextContent", dt.getString(0, "TextContent"));
			map.put("PicTitle", dt.getString(0, "PicTitle"));
			map.put("PicDesc", dt.getString(0, "PicDesc"));
			map.put("PicURL", dt.getString(0, "PicURL"));
			map.put("piclinkurl", dt.getString(0, "piclinkurl"));
			map.put("MusicTitle", dt.getString(0, "MusicTitle"));
			map.put("MusicDesc", dt.getString(0, "MusicDesc"));
			map.put("MusicURL", dt.getString(0, "MusicURL"));
			map.put("HpMusicURL", dt.getString(0, "HpMusicURL"));
			map.put("TextOrder", dt.getString(0, "TextOrder"));
		} else {
			map.put("ResponseType", "text");
		}

		map.put("ResponseTypeList", HtmlUtil.codeToOptions("WX.ResoponseType", true));
		return map;
	}
	
	/**
	 * 保存
	 */
	public boolean save() {
		String ID= $V("ID");
		WxResponseInfoSchema info = new WxResponseInfoSchema();
		String MenuAttribute= $V("MenuAttribute");
		String ResponseType= $V("ResponseType");
		String TextContent = $V("TextContent");
		String PicTitle = $V("PicTitle");
		String PicDesc = $V("PicDesc");
		String PicURL = $V("PicURL");
		String piclinkurl = $V("piclinkurl");
		String TextOrder = $V("TextOrder");
		
		Transaction trans = new Transaction();
		info.setID(ID);
		if (StringUtil.isNotEmpty(ID)) {
			info.fill();
		}
		info.setMenuAttribute(MenuAttribute);
		info.setResponseType(ResponseType);
		info.setTextContent(TextContent);
		info.setPicTitle(PicTitle);
		info.setPicDesc(PicDesc);
		info.setPicURL(PicURL);
		info.setpiclinkurl(piclinkurl);
		if (StringUtil.isNotEmpty(TextOrder)) {
			info.setTextOrder(TextOrder);
		} else {
			info.setTextOrder("0");
		}
		
		if (StringUtil.isEmpty(ID)) {
			QueryBuilder qb = new QueryBuilder("SELECT MAX(CONVERT(ID, SIGNED)) FROM WxResponseInfo");
			Long maxId = qb.executeLong();
			ID = String.valueOf(maxId + 1);
			
			info.setID(ID);
			info.setMenuID(ID);
			info.setCreateDate(new Date());
			trans.add(info, Transaction.INSERT);
		}else {
			info.setModifyDate(new Date());
			trans.add(info, Transaction.UPDATE);
		}
		
		ZDCodeSchema code = new ZDCodeSchema();
		code.setCodeType("WeiXin.TextRes");
		code.setParentCode("WeiXin.TextRes");
		code.setCodeValue(MenuAttribute);
		code.setCodeName(MenuAttribute);
		ZDCodeSet codeSet = code.query();
		if (codeSet.size() > 0) {
			code = codeSet.get(0);
			code.setModifyTime(new Date());
			code.setModifyUser(User.getUserName());
			trans.add(code, Transaction.UPDATE);
		} else {
			code.setAddUser(User.getUserName());
			code.setAddTime(new Date());
			code.setCodeOrder(System.currentTimeMillis());
			trans.add(code, Transaction.INSERT);
		}
		
		if(trans.commit()){
			Response.setStatus(1);
			Response.setMessage("保存成功！");
		}else{
			Response.setStatus(0);
			Response.setMessage("发生错误：操作失败！"); 
			return false;
		}
		return true;
		
	}
	
	public static void dg1DataBind(DataGridAction dga) {
		String MenuAttribute = (String) dga.getParams().get("MenuAttribute");
		String PicTitle = (String) dga.getParams().get("PicTitle");
		QueryBuilder qb = new QueryBuilder(" SELECT i.ID, i.MenuID, i.MenuAttribute, i.ResponseType, "
				+ " (CASE WHEN i.ResponseType = 'text' THEN '文本' WHEN i.ResponseType = 'img' THEN '图片' ELSE '图文' END) AS ResponseTypeName "
				+ ", i.TextContent, i.PicTitle, i.PicDesc, i.PicURL, "
				+ " i.PicLinkURL, i.MusicTitle, i.MusicDesc, i.MusicURL, i.HpMusicURL FROM WxResponseInfo i "
				+ " WHERE i.MenuAttribute <> 'subscribeinfo'");
		
		if(StringUtil.isNotEmpty(MenuAttribute)){
			qb.append(" And i.MenuAttribute like ? ", "%" + MenuAttribute + "%");
		}
		if(StringUtil.isNotEmpty(PicTitle)){
			qb.append(" And i.PicTitle like ? ", "%" + PicTitle + "%");
		}
		
		int pageSize = dga.getPageSize();
		int pageIndex = dga.getPageIndex();
		qb.append(" ORDER BY createDate DESC, TextOrder ");
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dga.setTotal(qb);
		dga.bindData(dt);
	}
	
	public void del() {
		String ids = $V("IDs");
		Transaction trans = new Transaction();
		WxResponseInfoSchema info = new WxResponseInfoSchema();
		WxResponseInfoSet set = info.query(new QueryBuilder("where id in (" + ids + ")"));

		trans.add(set, Transaction.DELETE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("删除成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}	
	
	/**
	 * 首次关注页面初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initSubDialog(Mapx params) {
		Mapx map = new Mapx();
		QueryBuilder qb = new QueryBuilder(" SELECT * FROM WxResponseInfo WHERE MenuAttribute = ? ");
		qb.add("subscribeinfo");
		DataTable dt = qb.executeDataTable();
		if(dt!=null && dt.getRowCount()>=1){
			map.put("ID", dt.getString(0, "ID"));
			map.put("MenuAttribute", dt.getString(0, "MenuAttribute"));
			map.put("TextContent", dt.getString(0, "TextContent"));
		}
		return map;
	}
	
	/**
	 * 首次关注页面 保存
	 */
	public void subSave() {
		String ID = $V("ID");
		String textContent = $V("TextContent");
		WxResponseInfoSchema info = new WxResponseInfoSchema();
		info.setID(ID);
		info.fill();
		info.setTextContent(textContent);
		Transaction trans = new Transaction();
		trans.add(info, Transaction.UPDATE);

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("保存成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}	
}
