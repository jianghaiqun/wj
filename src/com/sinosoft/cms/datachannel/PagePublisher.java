package com.sinosoft.cms.datachannel;

import java.util.ArrayList;
import java.util.Date;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.pub.SiteUtil;
import com.sinosoft.cms.publish.AutomaticPublishCatalog;
import com.sinosoft.cms.resource.ConfigImageLib;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.orm.Schema;
import com.sinosoft.framework.orm.SchemaSet;
import com.sinosoft.framework.utility.Errorx;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.UserList;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCAttachmentSchema;
import com.sinosoft.schema.ZCAudioSchema;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCImageSchema;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;
import com.sinosoft.schema.ZCVideoSchema;
import com.sinosoft.schema.ZSGoodsSchema;
import com.sinosoft.schema.ZSGoodsSet;

public class PagePublisher {
	

	/**
	 * 根据配置的字段生成投保录入页
	 *  
	 * @param siteID
	 * @return
	 */
	/*
	 * 根据配置字段生成投保人信息模块
	 */
	public boolean publishApp(){
		
		return true;
	}
	
	/*
	 * 根据配置字段生成被保人信息模块
	 */
     public boolean publishIns(){
		
		return true;
	}
    /*
     * 根据配置字段生成受益人信息模块
     */
	public boolean publishBnf(){
		
		return true;
	}
	/*
	 * 根据配置字段生成投保要素信息模块
	 */
	public boolean publishInsElement(){
		
		return true;
	}
	//根据配置字段生成保障计划信息模块
	public boolean publishGua(){
		
		return true;
	}
	public static void main(String[] args){
		
	}
}
