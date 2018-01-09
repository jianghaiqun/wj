/**批量评论导入功能
 * <p>Date        :2012-07-01</p> 
 * <p>Module      :CMS </p> 
 * <p>Description :评论导入</p> 
 * <p>Remark      : </p> 
 * @author  wangcaiyun
 * @version 1.0 
 * <p>------------------------------------------------------------</p> 
 * <p>  修改历史</p> 
 * <p>  序号   日期                修改人     修改原因</p> 
 */
package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCArticleSchema;
import com.sinosoft.schema.ZCArticleSet;
import com.sinosoft.schema.ZCCatalogSchema;
import com.sinosoft.schema.ZCCatalogSet;
import com.sinosoft.schema.ZCCommentSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CommentUpLoad extends Page {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */ 
	public CErrors mErrors = new CErrors();
	private static final String sql = "select CodeValue from ZDCode where CodeType = ? and ParentCode = ? and CodeName = ? ";
	private Transaction trans;

	/** 处理条数 */
	private Integer count;

	/**
	 * 更新产品ID数据功能
	 * 
	 * @return
	 */
	public void updateProduct() {
		try { 
			// 新建
			HSSFWorkbook workbook = new HSSFWorkbook();

			// Title字体样式
			HSSFFont fontBold = workbook.createFont();
			fontBold.setFontHeightInPoints((short) 10);
			fontBold.setFontName("宋体");
			fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 内容字体样式
			HSSFFont fontNormal = workbook.createFont();
			fontNormal.setFontHeightInPoints((short) 10);
			fontNormal.setFontName("宋体");
			fontNormal.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			// Title单元格样式
			HSSFCellStyle styleBorderBold = workbook.createCellStyle();
			styleBorderBold.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleBorderBold.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleBorderBold.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			styleBorderBold.setWrapText(true);
			styleBorderBold.setFont(fontBold);
			// 内容单元格样式
			HSSFCellStyle styleBorderNormal = workbook.createCellStyle();
			styleBorderNormal.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderRight(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal.setBorderTop(HSSFCellStyle.BORDER_THIN);
			styleBorderNormal
					.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			styleBorderNormal.setFont(fontNormal);
			// 新建Sheet
			HSSFSheet sheet = workbook.createSheet("产品ID");
			// 设置列宽
			sheet.setColumnWidth(0, 10 * 256);
			sheet.setColumnWidth(1, 65 * 256);
			sheet.setColumnWidth(2, 10 * 256);
			// 创建行
			HSSFRow row = sheet.createRow(0);
			// 创建列
			HSSFCell cell = row.createCell(0);
			// 设置Title
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(styleBorderBold);
			cell.setCellValue("关联ID");
			HSSFCell cell1 = row.createCell(1);
			cell1.setCellValue("产品名称");
			cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell1.setCellStyle(styleBorderBold);
			HSSFCell cell2 = row.createCell(2);
			cell2.setCellValue("险种分类");
			cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell2.setCellStyle(styleBorderBold);

			// 取得产品ID数据
			DataTable dt = new QueryBuilder(
					"select ID,Title,ContentSign from zcarticle where type=1 "
							+ "and cataloginnercode LIKE '002313%' and status=30 order by title desc")
					.executeDataTable();
			
			// 把取得的数据放入Sheet页
			if (dt != null && dt.getRowCount() > 0) {
				DataTable typeDt = new QueryBuilder("SELECT CodeValue, CodeName FROM zdcode WHERE codetype='ContentSign' AND parentcode='ContentSign' ").executeDataTable();
				Map<String, String> riskType = new HashMap<String, String>();
				if (typeDt != null && typeDt.getRowCount() > 0) {
					int rowcount = typeDt.getRowCount();
					for (int i = 0; i < rowcount; i++) {
						riskType.put(typeDt.getString(i, 0), typeDt.getString(i, 1));
					}
				}
				for (int i = 0; i < dt.getRowCount(); i++) {
					row = sheet.createRow(i + 1);
					dt.set(i, 2, riskType.get(dt.getString(i, 2)));
					for (int j = 0; j < dt.get(i).getColumnCount(); j++) {
						cell = row.createCell(j);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(styleBorderNormal);
						cell.setCellValue(dt.get(i).getString(j));
					}
				}
			}

			// 读取模板
			File file = new File(Config.getContextRealPath() + File.separatorChar
					+ "DataService" + File.separatorChar + "ADTemplate"
					+ File.separatorChar + "ProductRelaId.xls");
			
			// 模板存在则删除
			if (file.exists()) {
				file.delete();
			}
			
			// 写文件
			FileOutputStream fileOut = new FileOutputStream(file);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			
			// 返回更新成功信息
			Response.setStatus(0);
			Response.setMessage("更新成功！");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
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
		List<Map<String,Object>> relaIDs = new ArrayList<Map<String,Object>>();
		if (resolvingExcel(upLoadFileName,relaIDs)) {
			if (!trans.commit()) {
				this.addError("执行插入语句失败！");
				result = "Fail";
			}else{
				// 更新reis中产品评论数量
				Comment comment = new Comment();
				comment.updateCommentCountInRedis(relaIDs);
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
	public boolean resolvingExcel(String upLoadFileName,List<Map<String,Object>> relaIDs) {
		try {
			trans = new Transaction();
			ZCArticleSchema article = null;
			ZCCommentSchema zcComment = null;
			ZCCatalogSchema zcCatalog = null;
			ZCCatalogSet zcCatalogSet;
			ZCArticleSet zcArticleSet;
			QueryBuilder queryBuilder;

			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			if (sheet == null || sheet.getLastRowNum() < 15) {
				this.addError("Excel文件中没有数据!");
				return false;
			}
			if (sheet.getLastRowNum() > 314) {
				this.addError("导入的数据最多为300条!");
				return false;
			}
			// 取得条数
			this.setCount(sheet.getLastRowNum() - 14);
			String id;
			// 读取Excel
			int score = 0;
			Random rm = new Random();
			for (int i = 15; i <= sheet.getLastRowNum(); i++) {
				article = new ZCArticleSchema();
				zcComment = new ZCCommentSchema();
				zcCatalog = new ZCCatalogSchema();
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					score = 0;
					// 关联ID
					if (row.getCell(1) != null
							&& !StringUtil.isEmpty(row.getCell(1)
									.getStringCellValue())) {
						id = row.getCell(1).getStringCellValue().trim();
						article.setID(id);
						zcArticleSet = article.query();
						if (zcArticleSet.size() < 1) {
							this.addError("第" + (i + 1)
									+ "行,第2列值错误！请按照产品ID数据表填写！");
							return false;
						}
						article = zcArticleSet.get(0);
						zcComment.setRelaID(id);
					} else {
						this.addError("第" + (i + 1) + "行,第2列不能是空！");
						return false;
					}

					// 站点ID
					zcComment.setSiteID(article.getSiteID());
					zcComment.setCatalogID(article.getCatalogID());
					zcCatalog.setID(article.getCatalogID());
					zcCatalogSet = zcCatalog.query();
					if (zcCatalogSet.size() < 1) {
						this.addError("第" + (i + 1) + "行,第2列值错误！请按照产品ID数据表填写！");
						return false;
					}
					zcCatalog = zcCatalogSet.get(0);
					zcComment.setCatalogType(zcCatalog.getType());

					// 内容
					if (row.getCell(2) != null
							&& !StringUtil.isEmpty(row.getCell(2)
									.getStringCellValue())) {
						String content = row.getCell(2).getStringCellValue().trim();
						if (StringUtil.getByteLength(content) < 20)
						{
							this.addError("第" + (i + 1) + "行,第3列需要填写10个以上的字！");
							return false;
						}
						
						if (StringUtil.getByteLength(content) > 1000)
						{
							this.addError("第" + (i + 1) + "行,第3列只能写500个字以内！");
							return false;
						}
						zcComment.setContent(content);
					} else {
						this.addError("第" + (i + 1) + "行,第3列不能是空！");
						return false;
					}
					
					// 评论回复内容
					if (row.getCell(3) != null
							&& !StringUtil.isEmpty(row.getCell(3)
									.getStringCellValue())) {
						String content = row.getCell(3).getStringCellValue().trim();
						if (StringUtil.getByteLength(content) < 20)
						{
							this.addError("第" + (i + 1) + "行,第4列需要填写10个以上的字！");
							return false;
						}
						
						if (StringUtil.getByteLength(content) > 1000)
						{
							this.addError("第" + (i + 1) + "行,第4列只能写500个字以内！");
							return false;
						}
						zcComment.setReplyContent(content);
					} 
					
					// 评论者
					if (row.getCell(4) != null
							&& !StringUtil.isEmpty(row.getCell(4)
									.getStringCellValue())) {
						zcComment.setAddUser(row.getCell(4)
								.getStringCellValue().trim());
					} else {
						this.addError("第" + (i + 1) + "行,第5列不能是空！");
						return false;
					}
					
					// 留言时间
					if (row.getCell(5) != null
							&& !StringUtil.isEmpty(row.getCell(5)
									.getStringCellValue())) {
						// 时间不合法时，提示错误信息
						if (!DateUtil.isLegalDate(row.getCell(5)
								.getStringCellValue().trim())) {
							this.addError("第" + (i + 1) + "行,第6列值错误！");
							return false;
						}
						String addtime = row.getCell(5).getStringCellValue().trim();
						if (addtime.length() == 10) {
							int hour = rm.nextInt(24);
							int min = rm.nextInt(60);
							int sec = rm.nextInt(60);
							if (hour < 10) {
								addtime += (" 0" + hour);
							} else {
								addtime += (" " + hour);
							}
							addtime += ":";
							if (min < 10) {
								addtime += ("0" + min);
							} else {
								addtime += min;
							}
							addtime += ":";
							if (sec < 10) {
								addtime += ("0" + sec);
							} else {
								addtime += sec;
							}
						}
						zcComment.setAddTime(DateUtil.parseDateTime(addtime));
						
					} else {
						zcComment.setAddTime(new Date());
					}

					// 投保目的
					if (row.getCell(6) != null
							&& !StringUtil.isEmpty(row.getCell(6)
									.getStringCellValue())) {
						zcComment.setPurpose(row.getCell(6)
								.getStringCellValue().trim());
					}
					
					// 审核状态
					if (row.getCell(7) != null
							&& !StringUtil.isEmpty(row.getCell(7)
									.getStringCellValue())) {
						String verifyFlagName = row.getCell(7)
								.getStringCellValue().trim();
						String verifyFlag = "";
						queryBuilder = new QueryBuilder(sql);
						queryBuilder.add("Comment.Status");
						queryBuilder.add("Comment.Status");
						queryBuilder.add(verifyFlagName);
						DataTable dataTable = queryBuilder.executeDataTable();
						if (dataTable != null && dataTable.getRowCount() > 0) {
							verifyFlag = dataTable.getString(0, 0);
						} else {
							this.addError("第" + (i + 1) + "行,第8列值错误！");
							return false;
						}
						zcComment.setVerifyFlag(verifyFlag);
						
						if("Y".equals(verifyFlag)){
							Map<String,Object> relaId = new HashMap<String,Object>();
							relaId.put("id", zcComment.getRelaID());
							relaId.put("type", "Pass");
							relaIDs.add(relaId);
						}

					} else {
						this.addError("第" + (i + 1) + "行,第8列不能是空！");
						return false;
					}

					boolean buyFlag = false;
					// 是否购买
					if (row.getCell(8) != null
							&& !StringUtil.isEmpty(row.getCell(8)
									.getStringCellValue())) {
						String isBuy = row.getCell(8)
								.getStringCellValue().trim();
						if ("是".equals(isBuy)) {
							zcComment.setIsBuy("1");
							buyFlag= true;
							
						} else if ("否".equals(isBuy)) {
							zcComment.setIsBuy("0");
							
						} else {
							this.addError("第" + (i + 1) + "行,第9列值非法，请选择下拉框里的内容！");
							return false;
						}
						
					} else {
						this.addError("第" + (i + 1) + "行,第9列不能是空！");
						return false;
					}
					
					// 保障范围
					if (row.getCell(9) != null && !StringUtil.isEmpty(row.getCell(9)
							.getStringCellValue())) {
						int coverageScore = 0;
						
						if (!buyFlag) {
							this.addError("第" + (i + 1) + "行,第10列保障范围评分不能输入！（未购买的产品不能评分）");
							return false;
						}
						
						try {
							coverageScore = Integer.valueOf(row.getCell(9).getStringCellValue());
							score += coverageScore;
						} catch(Exception e) {
							this.addError("第" + (i + 1) + "行,第10列保障范围评分必须是整数！");
							return false;
						}
						
						if (coverageScore >= 1 && coverageScore <= 5) {
							zcComment.setCoverageScore(String.valueOf(coverageScore));
							
						} else {
							this.addError("第" + (i + 1) + "行,第10列保障范围评分不合法，必须在1-5之间！");
							return false;
						} 
							
					} else if (buyFlag) {
						this.addError("第" + (i + 1) + "行,第10列保障范围不能是空！（已购买的产品必须评分）");
						return false;
					}
					
					// 保障程度
					if (row.getCell(10) != null && !StringUtil.isEmpty(row.getCell(10)
							.getStringCellValue())) {
						int describeScore = 0;
						
						if (!buyFlag) {
							this.addError("第" + (i + 1) + "行,第11列保障程度评分不能输入！（未购买的产品不能评分）");
							return false;
						}
						
						try {
							describeScore = Integer.valueOf(row.getCell(10).getStringCellValue());
							score += describeScore;
						} catch(Exception e) {
							this.addError("第" + (i + 1) + "行,第11列保障程度评分必须是整数！");
							return false;
						}
						
						if (describeScore >= 1 && describeScore <= 5) {
							zcComment.setDescribeScore(String.valueOf(describeScore));
							
						} else {
							this.addError("第" + (i + 1) + "行,第11列保障程度评分不合法，必须在1-5之间！");
							return false;
						}                                                                                                                    
						
					} else if (buyFlag) {
						this.addError("第" + (i + 1) + "行,第11列保障程度不能是空！（已购买的产品必须评分）");
						return false;
					}
						
					// 保障价格
					if (row.getCell(11) != null && !StringUtil.isEmpty(row.getCell(11)
							.getStringCellValue())) {
						int policyScore = 0;
						
						if (!buyFlag) {
							this.addError("第" + (i + 1) + "行,第12列保障价格评分不能输入！（未购买的产品不能评分）");
							return false;
						}
						
						try {
							policyScore = Integer.valueOf(row.getCell(11).getStringCellValue());
							score += policyScore;
						} catch(Exception e) {
							this.addError("第" + (i + 1) + "行,第12列保障价格评分必须是整数！");
							return false;
						}
						
						if (policyScore >= 1 && policyScore <= 5) {
							zcComment.setPolicyScore(String.valueOf(policyScore));
							
						} else {
							this.addError("第" + (i + 1) + "行,第12列保障价格评分不合法，必须在1-5之间！");
							return false;
						}                                                                                                                    
						
					} else if (buyFlag) {
						this.addError("第" + (i + 1) + "行,第12列保障价格不能是空！（已购买的产品必须评分）");
						return false;
					}
					
					// 售后服务
					if (row.getCell(12) != null && !StringUtil.isEmpty(row.getCell(12)
							.getStringCellValue())) {
						int clientScore = 0;
						
						if (!buyFlag) {
							this.addError("第" + (i + 1) + "行,第13列售后服务评分不能输入！（未购买的产品不能评分）");
							return false;
						}
						
						try {
							clientScore = Integer.valueOf(row.getCell(12).getStringCellValue());
							score += clientScore;
						} catch(Exception e) {
							this.addError("第" + (i + 1) + "行,第13列售后服务评分必须是整数！");
							return false;
						}
						
						if (clientScore >= 1 && clientScore <= 5) {
							zcComment.setClientScore(String.valueOf(clientScore));
						} else {
							this.addError("第" + (i + 1) + "行,第13列售后服务评分不合法，必须在1-5之间！");
							return false;
						}                                                                                                                    
						
					} else if (buyFlag) {
						this.addError("第" + (i + 1) + "行,第13列售后服务不能是空！（已购买的产品必须评分）");
						return false;
					}
					
					if (buyFlag) {
						// 计算评分满意程度=（保障范围评分+售后服务评分+保障程度评分+保障价格评分）除4 四舍五入取整
						zcComment.setScore(new BigDecimal(score).divide(new BigDecimal(4), 0, BigDecimal.ROUND_HALF_UP).toString());
					}
					
					// 会员等级
					if (row.getCell(13) != null
							&& !StringUtil.isEmpty(row.getCell(13)
									.getStringCellValue())) {
						zcComment.setmemGrade(row.getCell(13).getStringCellValue()
								.trim());
					} else {
						zcComment.setmemGrade("K1");
					}
					
					// 评论标题
					if (row.getCell(14) != null
							&& !StringUtil.isEmpty(row.getCell(14)
									.getStringCellValue())) {
						zcComment.setTitle(row.getCell(14).getStringCellValue()
								.trim());
					} else {
						zcComment.setTitle("无");
					}
					
					// IP
					if (row.getCell(15) != null
							&& !StringUtil.isEmpty(row.getCell(15)
									.getStringCellValue())) {
						zcComment.setAddUserIP(row.getCell(15)
								.getStringCellValue().trim());
					}
					
					// ID
					zcComment.setID(NoUtil.getMaxID("CommentID"));
					zcComment.setpraisedNum(0);
					trans.add(zcComment, Transaction.INSERT);
				}
			}

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
