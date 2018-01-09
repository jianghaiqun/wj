/**批量问答导入功能
 * <p>Date        :2012-06-26</p> 
 * <p>Module      :CMS </p> 
 * <p>Description :问答导入</p> 
 * <p>Remark      : </p> 
 * @author  wangcaiyun
 * @version 1.0 
 * <p>------------------------------------------------------------</p> 
 * <p>  修改历史</p> 
 * <p>  序号   日期                修改人     修改原因</p> 
 */
package com.sinosoft.cms.document;

import com.sinosoft.cms.pub.CatalogUtil;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.cms.site.CatalogQuestionAsk;
import com.sinosoft.cms.site.Tag;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.TreeAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.Application;
import com.sinosoft.platform.Priv;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.platform.pub.OrderUtil;
import com.sinosoft.schema.ZCArticleLogSchema;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCTagSchema;
import com.sinosoft.schema.ZCTagSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.workflow.Context;
import com.sinosoft.workflow.WorkflowUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
 
/**
 * 问答导入处理
 * 
 * @author wangcaiyun
 * 
 */
public class QuestionAskUpLoad extends Page {

	private static final String empty = "";
	private static final String sql = "select CodeValue from ZDCode where CodeType = ? and ParentCode = ? and CodeName = ? ";
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private Transaction trans;
	private String catalogID;
	/** 处理条数 */
	private Integer count;

	/**
	 * 向外提供显示栏目树
	 * 
	 * @param ta
	 *            TreeAction
	 */
	public static void treeDataBind(TreeAction ta) {
		CatalogQuestionAsk.treeDataBind(ta);
	}

	/**
	 * 保存上传文件
	 * 
	 * @param upLoadFileName
	 *            上传文件
	 * @return
	 */
	public String upLoadSave(String upLoadFileName) {
		String result = "Succ";
		// 解析Excel内容成功，保存数据
		if (resolvingExcel(upLoadFileName)) {
			if (!trans.commit()) {
				this.addError("执行插入语句失败！");
				result = "Fail";
			}
		} else {
			result = "Fail";
		}

		return result;
	}

	/**
	 * 解析Excel内容
	 * 
	 * @param upLoadFileName
	 *            上传文件
	 * @return 解析是否成功
	 */
	public boolean resolvingExcel(String upLoadFileName) {
		try {
			trans = new Transaction();
			ZCArticleSchema zCArticleSchema = null;
			QueryBuilder queryBuilder;
			Mapx<String, String> request;

			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			if (sheet == null || sheet.getLastRowNum() < 11) {
				this.addError("Excel文件中没有数据!");
				return false;
			}
			if (sheet.getLastRowNum() > 310) {
				this.addError("导入的数据最多为300条!");
				return false;
			}
			// 取得条数
			this.setCount(sheet.getLastRowNum() - 10);
			// 读取Excel
			for (int i = 11; i <= sheet.getLastRowNum(); i++) {
				zCArticleSchema = new ZCArticleSchema();
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					// 标题
					if (row.getCell(1) != null
							&& !StringUtil.isEmpty(row.getCell(1)
									.getStringCellValue())) {
						zCArticleSchema.setTitle(row.getCell(1)
								.getStringCellValue().trim());
					} else {
						this.addError("第" + (i + 1) + "行,第2列不能是空！");
						return false;
					}

					// 短标题
					if (row.getCell(2) != null
							&& !StringUtil.isEmpty(row.getCell(2)
									.getStringCellValue())) {
						zCArticleSchema.setShortTitle(row.getCell(2)
								.getStringCellValue().trim());
					}

					// 副标题
					if (row.getCell(3) != null
							&& !StringUtil.isEmpty(row.getCell(3)
									.getStringCellValue())) {
						zCArticleSchema.setSubTitle(row.getCell(3)
								.getStringCellValue().trim());
					}

					// 作者
					if (row.getCell(4) != null
							&& !StringUtil.isEmpty(row.getCell(4)
									.getStringCellValue())) {
						zCArticleSchema.setAuthor(row.getCell(4)
								.getStringCellValue().trim());
					} else {
						this.addError("第" + (i + 1) + "行,第5列不能是空！");
						return false;
					}

					// Tag
					if (row.getCell(5) != null
							&& !StringUtil.isEmpty(row.getCell(5)
									.getStringCellValue())) {
						zCArticleSchema.setTag(row.getCell(5)
								.getStringCellValue().trim());
					} else {
						this.addError("第" + (i + 1) + "行,第6列不能是空！");
						return false;
					}

					// 文章来源
					if (row.getCell(6) != null
							&& !StringUtil.isEmpty(row.getCell(6)
									.getStringCellValue())) {
						String sourceSign = row.getCell(6).getStringCellValue()
								.trim();
						queryBuilder = new QueryBuilder(sql);
						queryBuilder.add("SourceSign");
						queryBuilder.add("SourceSign");
						queryBuilder.add(sourceSign);
						DataTable dataTable = queryBuilder.executeDataTable();
						if (dataTable != null && dataTable.getRowCount() > 0) {
							sourceSign = dataTable.getString(0, 0);
						} else {
							this.addError("第" + (i + 1) + "行,第7列值错误！");
							return false;
						}
						zCArticleSchema.setSourceSign(sourceSign);

					} else {
						this.addError("第" + (i + 1) + "行,第7列不能是空！");
						return false;
					}

					// 内容标记
					if (row.getCell(7) != null
							&& !StringUtil.isEmpty(row.getCell(7)
									.getStringCellValue())) {
						String contentSign = row.getCell(7)
								.getStringCellValue().trim();
						queryBuilder = new QueryBuilder(sql);
						queryBuilder.add("ContentSign");
						queryBuilder.add("ContentSign");
						queryBuilder.add(contentSign);
						DataTable dataTable = queryBuilder.executeDataTable();
						if (dataTable != null && dataTable.getRowCount() > 0) {
							contentSign = dataTable.getString(0, 0);
						} else {
							this.addError("第" + (i + 1) + "行,第8列值错误！");
							return false;
						}
						zCArticleSchema.setContentSign(contentSign);

					} else {
						this.addError("第" + (i + 1) + "行,第8列不能是空！");
						return false;
					}

					// 分类属性
					if (row.getCell(8) != null
							&& !StringUtil.isEmpty(row.getCell(8)
									.getStringCellValue())) {
						String attribute = row.getCell(8).getStringCellValue()
								.trim();
						queryBuilder = new QueryBuilder(sql);
						queryBuilder.add("ArticleAttribute");
						queryBuilder.add("ArticleAttribute");
						queryBuilder.add(attribute);
						DataTable dataTable = queryBuilder.executeDataTable();
						if (dataTable != null && dataTable.getRowCount() > 0) {
							attribute = dataTable.getString(0, 0);
						} else {
							this.addError("第" + (i + 1) + "行,第9列值错误！");
							return false;
						}
						zCArticleSchema.setAttribute(attribute);
					}

					request = new Mapx<String, String>();
					// 问题补充说明
					if (row.getCell(9) != null
							&& !StringUtil.isEmpty(row.getCell(9)
									.getStringCellValue())) {
						request.put("_C_cainadaan", row.getCell(9)
								.getStringCellValue().trim());
					} else {
						this.addError("第" + (i + 1) + "行,第10列不能是空！");
						return false;
					}

					// 是否解决
					if (row.getCell(10) != null
							&& !StringUtil.isEmpty(row.getCell(10)
									.getStringCellValue())) {
						request.put("_C_isOk", row.getCell(10)
								.getStringCellValue().trim());
					} else {
						this.addError("第" + (i + 1) + "行,第11列不能是空！");
						return false;
					}

					// 回答人
					if (row.getCell(11) != null
							&& !StringUtil.isEmpty(row.getCell(11)
									.getStringCellValue())) {
						request.put("_C_ReplyUser", row.getCell(11)
								.getStringCellValue().trim());
					} else {
						this.addError("第" + (i + 1) + "行,第12列不能是空！");
						return false;
					}

					// 回答时间
					if (row.getCell(12) != null
							&& !StringUtil.isEmpty(row.getCell(12)
									.getStringCellValue())) {
						if (!DateUtil.isLegalDate(row.getCell(12)
								.getStringCellValue().trim().replace("/", "-"))) {
							this.addError("第" + (i + 1) + "行,第13列值错误！");
							return false;
						}
						request.put("_C_ReplyTime", row.getCell(12)
								.getStringCellValue().trim().replace("/", "-"));
					} else {
						this.addError("第" + (i + 1) + "行,第13列不能是空！");
						return false;
					}

					// 提问时间
					if (row.getCell(13) != null
							&& !StringUtil.isEmpty(row.getCell(13)
									.getStringCellValue())) {
						if (!DateUtil.isLegalDate(row.getCell(13)
								.getStringCellValue().trim().replace("/", "-"))) {
							this.addError("第" + (i + 1) + "行,第14列值错误！");
							return false;
						}
						request.put("_C_AskTime", row.getCell(13)
								.getStringCellValue().trim().replace("/", "-"));
					} else {
						this.addError("第" + (i + 1) + "行,第14列不能是空！");
						return false;
					}

					// 采纳答案
					String content = empty;
					if (row.getCell(14) != null
							&& !StringUtil.isEmpty(row.getCell(14)
									.getStringCellValue())) {
						content = row.getCell(14).getStringCellValue().trim();
					} else {
						this.addError("第" + (i + 1) + "行,第15列不能是空！");
						return false;
					}
					zCArticleSchema.setContent(content);
					zCArticleSchema.setCopyImageFlag("Y");

					// 摘要
					if (row.getCell(15) != null
							&& !StringUtil.isEmpty(row.getCell(15)
									.getStringCellValue())) {
						zCArticleSchema.setSummary(row.getCell(15)
								.getStringCellValue().trim());
					}

					// Title
					if (row.getCell(16) != null
							&& !StringUtil.isEmpty(row.getCell(16)
									.getStringCellValue())) {
						zCArticleSchema.setMetaTitle(row.getCell(16)
								.getStringCellValue().trim());
					}

					// Keyword
					if (row.getCell(17) != null
							&& !StringUtil.isEmpty(row.getCell(17)
									.getStringCellValue())) {
						zCArticleSchema.setMetaKeywords(row.getCell(17)
								.getStringCellValue().trim());
					}

					// Description
					if (row.getCell(18) != null
							&& !StringUtil.isEmpty(row.getCell(18)
									.getStringCellValue())) {
						zCArticleSchema.setMetaDescription(row.getCell(18)
								.getStringCellValue().trim());
					}

					// 设置文章的基本信息
					setZCArticleInfo(zCArticleSchema, request);
					
					// 险种
					if (row.getCell(19) != null
							&& !StringUtil.isEmpty(row.getCell(19)
									.getStringCellValue())) {
						String riskType = row.getCell(19).getStringCellValue()
								.trim();
						queryBuilder = new QueryBuilder(sql);
						queryBuilder.add("RiskType");
						queryBuilder.add("RiskType");
						queryBuilder.add(riskType);
						DataTable dataTable = queryBuilder.executeDataTable();
						if (dataTable != null && dataTable.getRowCount() > 0) {
							riskType = dataTable.getString(0, 0);
						} else {
							this.addError("第" + (i + 1) + "行,第20列值错误！");
							return false;
						}
						zCArticleSchema.setRiskType(riskType);

					}
					
					// 保险公司
					if (row.getCell(20) != null
							&& !StringUtil.isEmpty(row.getCell(20)
									.getStringCellValue())) {
						String supplierCode = row.getCell(20).getStringCellValue()
								.trim();
						queryBuilder = new QueryBuilder(sql);
						queryBuilder.add("SupplierCode");
						queryBuilder.add("SupplierCode");
						queryBuilder.add(supplierCode);
						DataTable dataTable = queryBuilder.executeDataTable();
						if (dataTable != null && dataTable.getRowCount() > 0) {
							supplierCode = dataTable.getString(0, 0);
						} else {
							this.addError("第" + (i + 1) + "行,第21列值错误！");
							return false;
						}
						zCArticleSchema.setRiskCompany(supplierCode);
					} else {
						zCArticleSchema.setRiskCompany("");
					}
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 设置文章的基本信息
	 * 
	 * @param zCArticleSchema
	 * @param request
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	private void setZCArticleInfo(ZCArticleSchema zCArticleSchema,
			Mapx<String, String> request) throws NumberFormatException,
			Exception {
		long articleID = NoUtil.getMaxID("DocID");
		zCArticleSchema.setID(articleID);

		long catalogID = Long.parseLong(this.getCatalogID());
		zCArticleSchema.setCatalogID(catalogID);

		String siteID = CatalogUtil.getSiteID(catalogID);
		zCArticleSchema.setSiteID(siteID);

		String innerCode = CatalogUtil.getInnerCode(catalogID);
		zCArticleSchema.setCatalogInnerCode(innerCode);

		// 处理自定义字段
		Article.saveCustomColumn(trans, request, catalogID, articleID, true);

		if ("".equals(zCArticleSchema.getFirstPublishDate())
				|| zCArticleSchema.getFirstPublishDate() == null) {
			zCArticleSchema.setFirstPublishDate(new Date());
		}

		Date tDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String temp1 = formatter.format(tDate);
		temp1 = temp1.substring(0, 10).replace("-", "");
		if (zCArticleSchema.getEveryDayNo() == 0) {
			DataTable nextDT = new QueryBuilder(
					"select max(EveryDayNo)as aNo from zcarticle where  date_format(addtime,'%Y%m%d') =?",
					temp1).executePagedDataTable(1, 0);
			if ("".equals(nextDT.getString(0, "aNo"))) {
				zCArticleSchema.setEveryDayNo(1);

			} else {
				zCArticleSchema.setEveryDayNo(Long.parseLong(nextDT.getString(
						0, "aNo")) + 1);
			}
		}

		// 下线时间
		zCArticleSchema.setDownlineDate(DateUtil.parse("2099-12-31 23:59:59",
				DateUtil.Format_DateTime));

		zCArticleSchema.setURL(PubFun.getArticleURL(zCArticleSchema));

		if (Priv.getPriv(Priv.ARTICLE, innerCode, Priv.ARTICLE_MODIFY)) {
			// 处理关键词
			Article.dealKeyword(trans, zCArticleSchema, empty);

			// 处理Tag内容
			dealTag(trans, zCArticleSchema);

			// 处理关联图片
			Article.dealRelaImage(trans, zCArticleSchema);

			// 处理关联附件
			Article.dealRelaAttach(trans, zCArticleSchema);

			// 处理关联视频
			Article.dealRelaVideo(trans, zCArticleSchema);
		}

		// 获取URL
		if (StringUtil.isEmpty(zCArticleSchema.getURL())) {
			zCArticleSchema.setURL(PubFun.getArticleURL(zCArticleSchema));
		}

		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(articleID);

		// 获取工作流
		String workflowID = CatalogUtil.getWorkflow(catalogID);
		Context context = null;

		// 处理投票
		Article.dealVote(zCArticleSchema, trans);

		zCArticleSchema.setType("1");
		zCArticleSchema.setTopFlag("0");
		zCArticleSchema.setTemplateFlag("0");
		zCArticleSchema.setPublishFlag("1");
		zCArticleSchema.setCommentFlag("1");
		zCArticleSchema.setOrderFlag(OrderUtil.getDefaultOrder());
		zCArticleSchema.setHitCount(0);
		zCArticleSchema.setStickTime(0);
		zCArticleSchema.setStatus(Article.STATUS_DRAFT);
		zCArticleSchema.setAddTime(new Date(zCArticleSchema.getOrderFlag()));
		zCArticleSchema.setModifyTime(new Date());

		articleLog.setAction("INSERT");
		articleLog.setActionDetail("新建文章");

		zCArticleSchema.setAddUser(User.getUserName());
		zCArticleSchema.setBranchInnerCode(User.getBranchInnerCode());
		if (StringUtil.isNotEmpty(workflowID)) {
			// 加入工作流
			context = WorkflowUtil.createInstance(trans,
					Long.parseLong(workflowID), "CMS",
					"" + zCArticleSchema.getID(), "0");
			zCArticleSchema.setWorkFlowID(context.getInstance().getID());
		}

		trans.add(zCArticleSchema, Transaction.INSERT);
		String sqlArticleCount = "update zccatalog set total = total+1,isdirty=1 where id=?";
		trans.add(new QueryBuilder(sqlArticleCount, catalogID));

		articleLog.setAddUser(User.getRealName());
		articleLog.setAddTime(new Date());
		// 有工作流时不需要，工作流的Adapter会加处理历史记录
		if (StringUtil.isEmpty(workflowID)) {
			trans.add(articleLog, Transaction.INSERT);
		}
	}

	/**
	 * 处理Tag内容
	 * 
	 * @param trans
	 * @param article
	 */
	public void dealTag(Transaction trans, ZCArticleSchema article) {
		String tagword = article.getTag();
		if (StringUtil.isNotEmpty(tagword)) {
			tagword = tagword.trim().replaceAll("\\s+", " ");
			tagword = tagword.replaceAll("，", " ");
			tagword = tagword.replaceAll("；", " ");
			tagword = tagword.replaceAll(";", " ");
			tagword = tagword.replaceAll("\\,+", " ");
			if (",".equals(tagword) || StringUtil.isEmpty(tagword)) {
				tagword = "";
			} else {
				if (tagword.indexOf(",") == 0) {
					tagword = tagword.substring(1);
				}
				if (tagword.lastIndexOf(",") == tagword.length() - 1) {
					tagword = tagword.substring(0, tagword.length() - 1);
				}
			}
			if (StringUtil.isNotEmpty(tagword)) {
				QueryBuilder qb = new QueryBuilder(
						"select value from zdconfig where  type = 'defaultmemo' ");
				Object defaultmemo = qb.executeOneValue();
				String[] tagwords = tagword.split(" ");
				for (int i = 0; i < tagwords.length; i++) {
					ZCTagSet tZCTagSet = Tag.checkTagWord(
							Application.getCurrentSiteID(), tagwords[i]);
					if (tZCTagSet.size() == 0) {
						ZCTagSchema tag = new ZCTagSchema();
						tag.setID(NoUtil.getMaxNo("TagID", 6));
						tag.setTag(tagwords[i]);
						tag.setAddTime(new Date());
						tag.setAddUser(User.getUserName());
						tag.setUsedCount(1);
						tag.setSiteID(Application.getCurrentSiteID());
						String tagId = tag.getID() + "";
						if (tagId.length() < 6) {
							tagId = StringUtil.leftPad(tagId, '0', 6);
						}
						String tagHtml = "/tag/" + tagId + "000001-"
								+ defaultmemo + ".html";
						tag.setLinkURL(tagHtml);
						trans.add(tag, Transaction.INSERT);
					} else {// 如果重复 被引用次数+1
						tZCTagSet.get(0).setUsedCount(
								tZCTagSet.get(0).getUsedCount() + 1);
						trans.add(tZCTagSet, Transaction.UPDATE);
					}
				}
			}
		}
	}

	/**
	 * 添加错误信息
	 * 
	 * @param errorMessage
	 *            错误信息
	 */
	protected void addError(String errorMessage) {
		CError tError = new CError();
		tError.moduleName = this.getClass().getName();
		tError.errorMessage = errorMessage;
		this.mErrors.addOneError(tError);
	}

	public String getCatalogID() {
		return catalogID;
	}

	public void setCatalogID(String catalogID) {
		this.catalogID = catalogID;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
