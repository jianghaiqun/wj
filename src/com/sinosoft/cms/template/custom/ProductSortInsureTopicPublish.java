package com.sinosoft.cms.template.custom;

import com.sinosoft.cms.document.Article;
import com.sinosoft.cms.template.PageGenerator;
import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.messages.LongTimeTask;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.webservice.ProductWebservice;
import com.sinosoft.webservice.productCode.ProductCodeWebServiceStub.ProductCodeResponse;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.FEMSearchProperties;
import com.sinosoft.webservice.searchInfo.SearchInfoServiceStub.SearchInfoResponse;
import com.tenpay.util.MD5Util;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ProductSortInsureTopicPublish extends Ajax
{
	private static final Logger logger = LoggerFactory.getLogger(ProductSortInsureTopicPublish.class);
	public static final String CompanyKey = "SupplierCode";
	private LongTimeTask ltt = null;
	
	private PageGenerator pg = null;
	
	private String b2TemplateName = null;
	
	private String b1NoProductTemplate = null;
	
	private String IDs;
	
	public void publish()
	{
		LongTimeTask ltt = new LongTimeTask()
		{
			public void execute()
			{
				publishB2Pages(this);
			}
		};
		this.ltt = ltt;
//		PageGenerator p = new PageGenerator(ltt);
//		if (p.staticSiteIndex(Application.getCurrentSiteID())) {
//			Deploy d = new Deploy();
//			d.addJobs(Application.getCurrentSiteID(), p.getFileList());
//		}
		ltt.setUser(User.getCurrent());
		pg = new PageGenerator(this.ltt);
		ltt.start();
		ltt.setCurrentInfo("开始发布......");
		$S("TaskID", "" + ltt.getTaskID());
	}
	
	private void publishB2Pages(LongTimeTask ltt)
	{
		String strCatalogId = Config.getValue("InsureCompanyTopicCatalog");
		String errMsg = null;
		if ((null == strCatalogId) || (!strCatalogId.matches("^[a-zA-Z\\d]+$")))
		{
			errMsg = "没有在系统配置项中设置保险公司专题栏目编码，未进行发布处理！";
			ltt.setCurrentInfo(errMsg);
			logger.error(errMsg);
			ltt.setPercent(0);
			return;
		}
		
		this.b2TemplateName = Config.getValue("InsureCompanyTopicB2Template");
		if (StringUtil.isEmpty(b2TemplateName))
		{
			errMsg = "没有在系统配置项中设置保险公司专题B2模板路径，未进行发布处理！";
			ltt.setCurrentInfo(errMsg);
			logger.error(errMsg);
			ltt.addError(errMsg);
			ltt.setPercent(0);
			return;
		}
		
		this.b1NoProductTemplate = Config.getValue("InsureCompanyTopicB1NoProduct");
		if (StringUtil.isEmpty(b1NoProductTemplate))
		{
			errMsg = "没有在系统配置项中设置保险公司专题B1无产品模板路径，未进行发布处理！";
			ltt.setCurrentInfo(errMsg);
			ltt.addError(errMsg);
			logger.error(errMsg);
			ltt.setPercent(0);
			return;
		}
		IDs = $V("IDs");

		//查询公司编码、公司文章id、公司名称
		String sql = "select z3.TextValue,z1.id,z1.title from zcarticle z1,zdcolumnvalue z3 where z3.ColumnCode='CompanyID' and z3.relaid=z1.id and z1.CatalogID=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.setSQL(sql);
		qb.add(strCatalogId);
		if (StringUtil.isNotEmpty(IDs)) {
			qb.append(" and z1.id in (" + IDs + ") ");
		}
		DataTable dtComCode = qb.executeDataTable();
		int iComCodeCount = 0;
		if ((null == dtComCode) || (0 == (iComCodeCount = dtComCode.getRowCount())))
		{
			errMsg = "系统配置项中设置的保险公司专题栏目对应保险公司为空，未进行发布处理！";
			ltt.setCurrentInfo(errMsg);
			logger.error(errMsg);
			ltt.addError(errMsg);
			ltt.setPercent(0);
			return;
		}
		
		sql = "select codevalue,codename from zdcode where codetype='ProductType' and parentcode='ProductType' order by codevalue LIMIT 21";
		qb.setSQL(sql);
		qb.getParams().clear();
		DataTable dtType = qb.executeDataTable();
		int iTypeCount = 0;
		if ((null == dtType) || (0 == (iTypeCount = dtType.getRowCount())))
		{
			errMsg = "未获取到产品类别列表，未进行发布处理！";
			ltt.setCurrentInfo(errMsg);
			logger.error(errMsg);
			ltt.addError(errMsg);
			ltt.setPercent(0);
			return;
		}
		
		sql = "update zcarticle set publishdate=?,FirstPublishDate=?,modifyuser=?,modifytime=?,status=? where id=?";
		QueryBuilder updateQB = new QueryBuilder(sql);
		updateQB.setBatchMode(true);
		String user = User.getUserName();
		int status = Article.STATUS_PUBLISHED; // 30 已发布
		
		QueryBuilder deleteQB = qb;
		sql = "delete from zdcode where codetype='ArticleRelaLink' and parentcode=?";
		deleteQB.setSQL(sql);
		deleteQB.setBatchMode(true);
		
		sql = "insert into zdcode(codetype, parentcode, codevalue, codename, codeorder,memo, addtime, addUser) values('ArticleRelaLink', ?, ?, ?, ?, ?,now(),'admin')";
		QueryBuilder insertQB = new QueryBuilder(sql);
		insertQB.setBatchMode(true);
		
		//循环保险公司和产品类别进行主类别发布
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("BU1", "N");
		String[] riskCodes = new String[1];
		int allCount = iComCodeCount*(iTypeCount + 1);
		int k = 0;
		for (int i=0; i<iComCodeCount; i++)
		{
			String comCode = dtComCode.getString(i, 0);
			String articleId = dtComCode.getString(i, 1);
			String compName = dtComCode.getString(i, 2);
			String parentcode = articleId + "_" + comCode;
			map.put(CompanyKey, comCode);
			map.remove("SubRiskTypeCode");

            deleteQB.add(parentcode);
            deleteQB.addBatch();

			k++;
			ltt.setCurrentInfo("当前(" + k + "/" + allCount + ")，正在发布" + compName + "的全部产品");
			
			ZCArticleSchema article = new ZCArticleSchema();
			article.setID(articleId);
			ZCArticleSet articleSet = article.query();
			if ((null == articleSet) || (0 == articleSet.size()))
			{
				errMsg = "查询对应文章失败！comCode=" + comCode + ", articledId=" + articleId;
				logger.error(errMsg);
				ltt.addError(errMsg);
				continue;
			}
			article = articleSet.get(0);
			boolean[] noProductFlag = new boolean[]{false}; // 判断是否有产品标识
			if (this.publishAllSort(map, comCode, article, noProductFlag))
			{
				//如果发布成功，则更新发布状态
				Date date = new Date();
				updateQB.add(date);
				Date firstPubDate = article.getFirstPublishDate();
				firstPubDate = (firstPubDate==null) ? date : firstPubDate;
				updateQB.add(firstPubDate);
				updateQB.add(user);
				updateQB.add(date);
				updateQB.add(status);
				updateQB.add(article.getID());
				updateQB.addBatch();
			}

			// 无产品
			if (noProductFlag[0])
			{
				allCount -= iTypeCount;
				
				//无产品b1页面发布
				pg.setDetailTemplate(this.b1NoProductTemplate);
				
				boolean publishStatus = pg.staticDoc("Article", article);
				
				pg.clearCustom();
				
				if (!publishStatus)
				{
					errMsg = "生成对应一级无产品静态页面失败！comCode=" + comCode + ", articledId=" + articleId + ", title=" + article.getTitle();
					logger.error(errMsg);
					ltt.addError(errMsg);
				}
				continue;
			}
			
			for (int j=0; j<iTypeCount; j++)
			{
				String strRiskType = dtType.getString(j, 0);
				String strRiskName = dtType.getString(j, 1);
				map.put("SubRiskTypeCode", strRiskType);
				
				insertQB.add(parentcode);
				insertQB.add(strRiskType);
				insertQB.add(compName + "的" + strRiskName);
				
				k++;
				ltt.setCurrentInfo("当前(" + k + "/" + allCount + ")，正在发布" + compName + "的" + strRiskName);
				
				int[] relaCount = new int[]{0};
				if (!this.getRiskCodes(map, strRiskName, riskCodes, relaCount))
				{
					insertQB.add(0);
					insertQB.add("");
					insertQB.addBatch();
					logger.info("********公司专题发布信息：comCode={}" + comCode + ", riskType={}, riskNum=0, 记录数=未查询。", comCode, strRiskType);
					continue;
				}

				String fileName = MD5Util.MD5Encode(comCode + "_" + strRiskType, "UTF-8") + ".shtml";
				
				int[] articleCount = new int[]{0};
				
				//发布本类别的静态页面
				this.publishSpecialSort(strRiskType, riskCodes[0], comCode, strRiskName, article, fileName, articleCount);
				ltt.setPercent(k*100/allCount);
				Object[] argArr = {comCode, strRiskType, relaCount[0], articleCount[0]};
				logger.info("********公司专题发布信息：comCode={}, riskType={}, riskNum={}, 记录数=", argArr);

				insertQB.add(articleCount[0]);
				insertQB.add(fileName);
				insertQB.addBatch();
			}
			
			logger.info("********公司专题发布信息：comCode={}, 发布完成，执行左侧产品分类数据sql更新。", comCode);
			updateQB.executeNoQuery();
			deleteQB.executeNoQuery();
			insertQB.executeNoQuery();
		}
		
		ltt.setCurrentInfo("保险公司专题二级静态页面发布成功完成！");
		ltt.setPercent(100);
	}
	
	private boolean publishAllSort(HashMap<String, Object> map, String comCode, ZCArticleSchema article, boolean[] noProductFlag)
	{
		String[] riskCodes = new String[1];
		int[] relaCount = new int[]{0};
		if (!this.getRiskCodes(map, "所有险种", riskCodes, relaCount))
		{
			noProductFlag[0] = true;
			logger.info("********公司专题发布信息：comCode={}, riskType=All, riskNum=0, 记录数=未查询。", comCode);
			return true;
		}
		
		//查询出对应的所有产品文章数据
		StringBuffer sb = new StringBuffer("select z1.* from zcarticle z1 where z1.cataloginnercode like '002313%' and  ");
		sb.append(" z1.status in (30,60) and (z1.refertarget!='' and z1.refertarget is not null) and z1.prop4 in(");
		sb.append(riskCodes[0]);
		sb.append(") and exists(select 1 from zccatalog z2 where z2.id=z1.catalogid and (z2.producttype is not null and z2.producttype<>''))");
		sb.append(" order by z1.topflag desc,z1.orderflag desc");
		
		String sql = sb.toString();
		
		DataTable dtProduct = (new QueryBuilder(sql.toString())).executeDataTable();
		int count = 0;
		if ((null == dtProduct) || (0 == (count = dtProduct.getRowCount())))
		{
			noProductFlag[0] = true;
			logger.info("********公司专题发布信息：comCode={}, riskType=All, riskNum=" + relaCount[0] + ", 记录数=0。", comCode);
			return true;
		}
		
		logger.info("********公司专题发布信息：comCode={}, riskType=All, riskNum=" + relaCount[0] + ", 记录数={}",comCode, count);
		
		pg.setCustomTable(dtProduct);
		
		boolean publishStatus = pg.staticDoc("Article", article);
		
		pg.clearCustom();
		
		if (!publishStatus)
		{
			logger.error("生成对应一级静态页面失败！comCode=" + comCode + ", articledId=" + article.getID()+", title=" + article.getTitle());
			return false;
		}
		
		return true;
	}
	
	private boolean publishSpecialSort(String strRiskType, String risks, String comCode,
			String riskTypeName, ZCArticleSchema article, String fileName, int[] articleCount)
	{
		//查询出对应的所有产品文章数据
		/*
		String sql = "select z1.* from zcarticle z1,zccatalog z2,ZDColumnValue z3 where z1.catalogid=z2.id and z2.producttype='"
			+ strRiskType + "' and z1.id=z3.relaid and z3.columncode='RiskCode' and z1.status in (30,60) and z3.textvalue in ("
			+ risks+ ") order by z1.topflag desc,z1.orderflag desc";
		*/
		String sql = "select z1.* from zcarticle z1,zccatalog z2 where z1.cataloginnercode like '002313%' and  z1.catalogid=z2.id and z1.status in (30,60) and z1.prop4 in ("
			+ risks+ ") and (z1.refertarget!='' and z1.refertarget is not null) order by z1.topflag desc,z1.orderflag desc";
		
		DataTable dtProduct = (new QueryBuilder(sql)).executeDataTable();
		if ((null == dtProduct) || (0 == (articleCount[0] = dtProduct.getRowCount())))
		{
			return false;
		}
		
		pg.addCustomGlobalData("ProductType", "Code", strRiskType);
		pg.addCustomGlobalData("ProductType", "Name", riskTypeName);
		
		//指定模板名、生成文件名、文章数据表
		pg.setCustomData(b2TemplateName, fileName, dtProduct);
		
		boolean publishStatus = pg.staticDoc("Article", article);
		
		pg.clearCustom();
		
		if (!publishStatus)
		{
			logger.error("生成对应二级静态页面失败！comCode=" + comCode + ", articledId=" + article.getID());
		}
		
		return true;
	}
	
	/**
	 * 根据传入的map（map中存了保险公司编码和险种类别，通过产品接口获取险种编码）
	 * @param map 条件map
	 * @param comCode 保险公司编码
	 * @param strRiskType 险种类别编码
	 * @param strRiskTypeName 险种类别名称
	 * @param riskCodes 返回的险种编码字符串（多个险种用半角逗号隔开）
	 * @return
	 */
	private boolean getRiskCodes(HashMap<String, Object> map, String strRiskTypeName, String[] riskCodes, int[] relaCount)
	{
		
		ProductCodeResponse productCode = null;
        List<ProductCodeResponse> productCodeResponseList = null;
		try
		{
			long start = System.currentTimeMillis();
			logger.info("获取产品信息开始：{}", start);
			map.put("ComCode_RiskType", "ComCode_RiskType");
			map.put("RiskProp","I");
            String comCodes = (String)map.get(CompanyKey);
            String[] arrayComCodes = comCodes.split(",");
            productCodeResponseList = new ArrayList<ProductCodeResponse>();
            for (int i = 0; i < arrayComCodes.length; i++) {
                map.put(CompanyKey, arrayComCodes[i]);
                productCode = ProductWebservice.ProductCodeServiceImpl(map);
                if (productCode != null) {
                    productCodeResponseList.add(productCode);
                }
            }
            // 重新把公司编号放回map中，防止后边调用出错
            map.put(CompanyKey, comCodes);
			long end = System.currentTimeMillis();
			logger.info("获取产品信息结束，共用时：{}", (end-start));
		}
		catch (Exception e)
		{
			String errMsg = "发布保险公司专题二级静态页面时获取产品编码列表失败！保险公司编码: "
							+ map.get(CompanyKey) + ", 产品类别："
							+ map.get("SubRiskTypeCode") + "--" + strRiskTypeName;
			logger.error(errMsg + e.getMessage(), e);
			return false;
		}
        if (productCodeResponseList == null || productCodeResponseList.size() == 0) {
            return false;
        }

        String[] allRiskcode = null;
        String[] riskcode = null;
        for (int j = 0; j < productCodeResponseList.size(); j++) {
            productCode = productCodeResponseList.get(j);
            if (productCode.getRiskCode() == null || productCode.getRiskCode().length == 0) {
                continue;
            }
            riskcode = productCode.getRiskCode();
            allRiskcode = (String[])ArrayUtils.addAll(allRiskcode, riskcode);
        }
		if (allRiskcode == null || allRiskcode.length == 0) {
			return false;
		}
		
		StringBuffer sb = new StringBuffer("");
		for (int k=0; k<allRiskcode.length; k++) {
			sb.append(",'").append(allRiskcode[k]).append("'");
		}

		relaCount[0] = allRiskcode.length;
		riskCodes[0] = sb.toString().substring(1);
		return true;
	}
	
	private boolean getSearchProperties(ArrayList<FEMSearchProperties[]> femSearchPropList, LongTimeTask ltt)
	{
		String sql = "select codevalue,codename from zdcode where codetype='ProductType' and parentcode='ProductType' order by codevalue LIMIT 21";
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dtType = qb.executeDataTable();
		int iTypeCount = 0;
		if ((null == dtType) || (0 == (iTypeCount = dtType.getRowCount())))
		{
			ltt.setCurrentInfo("未获取到产品类别列表，未进行发布处理！");
			ltt.setPercent(0);
			return false;
		}
		
		HashMap<String, Object> map = new HashMap<String, Object>();

		//先查询出所有类别并暂存起来
		for (int j=0; j<iTypeCount; j++)
		{
			try
			{
				map.put("ERiskSubType", dtType.getString(j, 0));
				SearchInfoResponse siInfo = ProductWebservice.SearchInfoServiceImpl(map, null);
				femSearchPropList.add(siInfo.getFEMSearchProperties());
			}
			catch (Exception e)
			{
				logger.error("产品筛选失败：" + e.getMessage(), e);
				continue;
			}
		}
		
		return true;
	}
	/**
	 * 保险公司信息查询
	 * 
	 * @param dga
	 */
	public static void dg1DataBind(DataGridAction dga) {
		String strCatalogId = Config.getValue("InsureCompanyTopicCatalog");
		String sql = "select z1.id as ID,z3.TextValue as ComCode,z1.title as ComName from zcarticle z1,zdcolumnvalue z3 where z3.ColumnCode='CompanyID' and z3.relaid=z1.id and z1.CatalogID=?";
		QueryBuilder qb = new QueryBuilder(sql);
		String ComName = (String) dga.getParams().get("ComName");
		qb.add(strCatalogId);
		if (StringUtil.isNotEmpty(ComName)) {
			qb.append(" and z1.title like '%" + ComName + "%'");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}
}
