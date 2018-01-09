package com.sinosoft.cms.site;

import com.sinosoft.cms.datachannel.Publisher;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.CookieImpl;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.schema.ZCSiteSchema;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 站点属性
 * 
 */

public class CatalogSite extends Page {
	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		site.fill();
		Mapx map = site.toMapx();
		if (StringUtil.isEmpty(site.getIndexTemplate())) {
			map.put("edit", "<a id='editLink' href='javascript:void(0);' onclick=\"openEditor('')\">" +
					"<img src='../Platform/Images/icon_edit.gif' width='14' height='14' style='display:none'></a>");
		} else {
			String s1 =
					"<a id='editLink' href='javascript:void(0);' onclick=\"openEditor('"+site.getIndexTemplate()
					+"')\"><img src='../Platform/Images/icon_edit.gif' width='14' height='14'></a>";
			map.put("edit", s1);
		}
		params.putAll(map);
		return params;
	}

	// 发布站点首页
	public void publishIndex() {
		long id = publishTask(Application.getCurrentSiteID());
		Response.setStatus(1);
		$S("TaskID", "" + id);
	}
	
	private long publishTask(final long siteID) {
		LongTimeTask ltt = new LongTimeTask() {
			public void execute() {
				Publisher p = new Publisher();
				p.publishIndex(siteID,this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}
	
	/**
	 * 更换站点模板
	 *
	 */
	public void changeTemplate(){
		ZCSiteSchema site=new ZCSiteSchema();
		site.setID(Application.getCurrentSiteID());
		if(!site.fill()){
			Response.setLogInfo(0, "更新首页模板失败!");
			return;
		}
		String indexTemplate=$V("IndexTemplate");
		if(indexTemplate==null){
			Response.setLogInfo(0, "请选择模板!");
			return;
		}
		String fileName=Config.getContextRealPath()+
						Config.getValue("Statical.TemplateDir")+
						"/"+Application.getCurrentSiteAlias()+
						indexTemplate;
		fileName=fileName.replaceAll("///", "/");
		fileName=fileName.replaceAll("//", "/");
		File file=new File(fileName);
		if(!file.exists()){
			Response.setLogInfo(0, "该模板文件不存在!");
			return;
		}
		site.setIndexTemplate(indexTemplate);
		Transaction trans=new Transaction();
		trans.add(site, Transaction.UPDATE);
		if(trans.commit()){
			Response.setLogInfo(1, "更新首页模板成功!");
		}else{
			Response.setLogInfo(0, "更新首页模板失败!");
		}
	}

	/**
	 * 根据Cookie自动重定向到相应栏目的页面
	 */
	public static void onRequestBegin(HttpServletRequest request, HttpServletResponse response) {
		String type = request.getParameter("Type");
		if (StringUtil.isEmpty(type)) {
			CookieImpl ci = new CookieImpl(request);
			String id = ci.getCookie("DocList.LastCatalog");
			if (StringUtil.isNotEmpty(id)) {
				String siteID = Application.getCurrentSiteID()+"";
				if(siteID.equals(CatalogUtil.getSiteID(id))){
					try {
						request.getRequestDispatcher("CatalogBasic.jsp?CatalogID=" + id).forward(request, response);
					} catch (ServletException e) {
						logger.error(e.getMessage(), e);
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
	}
}
