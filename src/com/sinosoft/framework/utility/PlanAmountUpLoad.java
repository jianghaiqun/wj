/**批量评论导入功能
 * <p>Date        :2012-07-01</p> 
 * <p>Module      :CMS </p> 
 * <p>Description :计划保费导入功能</p> 
 * <p>Remark      : </p> 
 * @version 1.0 
 * <p>------------------------------------------------------------</p> 
 * <p>  修改历史</p> 
 * <p>  序号   日期                修改人     修改原因</p> 
 */
package com.sinosoft.framework.utility;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.schema.PlanAmountInfoSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;

public class PlanAmountUpLoad extends Page {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private Transaction trans;

	/** 处理条数 */
	private Integer count;

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

			PlanAmountInfoSchema tPlanAmountInfoSchema = null;
			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			//取的年份
			String planyear = String.valueOf(sheet.getRow(1).getCell(1));
			if (StringUtil.isEmpty(planyear)) {
				this.addError("导入年份不能为空!");
				return false;
			}
			// 取得条数
			this.setCount(sheet.getLastRowNum() - 3);
			// 读取Excel
			for (int i = 4; i <= sheet.getLastRowNum(); i++) {
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					tPlanAmountInfoSchema = new PlanAmountInfoSchema();
					tPlanAmountInfoSchema.setPlanYear(planyear);
					// 类别
					if (row.getCell(1) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(1))))) {
						String risktype = String.valueOf(getCellString(row.getCell(1))).trim();
						tPlanAmountInfoSchema.setRiskType(risktype);
						tPlanAmountInfoSchema.setCreateDate(DateUtil.parseDateTime(PubFun.getCurrent()));
						
					} else {
						this.addError("第" + (i + 1) + "行,第2列(类别编码)不能是空！");
						return false;
					}
					// 类别名称
					if (row.getCell(2) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(2))))) {
						tPlanAmountInfoSchema.setRiskTypeName(String.valueOf(getCellString(row.getCell(2))));
					}
					int method = Transaction.INSERT;
					if(tPlanAmountInfoSchema.fill()){
						method = Transaction.UPDATE;
					}
					//1月
					if (row.getCell(3) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(3))))) {
						tPlanAmountInfoSchema.setMonP1(String.valueOf(getCellString(row.getCell(3))));
					}
					if (row.getCell(4) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(4))))) {
						tPlanAmountInfoSchema.setMonF1(String.valueOf(getCellString(row.getCell(4))));
					}
					if (row.getCell(5) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(5))))) {
						tPlanAmountInfoSchema.setMonP2(String.valueOf(getCellString(row.getCell(5))));
					}
					if (row.getCell(6) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(6))))) {
						tPlanAmountInfoSchema.setMonF2(String.valueOf(getCellString(row.getCell(6))));
					}
					if (row.getCell(7) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(7))))) {
						tPlanAmountInfoSchema.setMonP3(String.valueOf(getCellString(row.getCell(7))));
					}
					if (row.getCell(8) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(8))))) {
						tPlanAmountInfoSchema.setMonF3(String.valueOf(getCellString(row.getCell(8))));
					}
					if (row.getCell(9) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(9))))) {
						tPlanAmountInfoSchema.setMonP4(String.valueOf(getCellString(row.getCell(9))));
					}
					if (row.getCell(10) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(10))))) {
						tPlanAmountInfoSchema.setMonF4(String.valueOf(getCellString(row.getCell(10))));
					}
					if (row.getCell(11) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(11))))) {
						tPlanAmountInfoSchema.setMonP5(String.valueOf(getCellString(row.getCell(11))));
					}
					if (row.getCell(12) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(12))))) {
						tPlanAmountInfoSchema.setMonF5(String.valueOf(getCellString(row.getCell(12))));
					}
					if (row.getCell(13) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(13))))) {
						tPlanAmountInfoSchema.setMonP6(String.valueOf(getCellString(row.getCell(13))));
					}
					if (row.getCell(14) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(14))))) {
						tPlanAmountInfoSchema.setMonF6(String.valueOf(getCellString(row.getCell(14))));
					}
					if (row.getCell(15) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(15))))) {
						tPlanAmountInfoSchema.setMonP7(String.valueOf(getCellString(row.getCell(15))));
					}
					if (row.getCell(16) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(16))))) {
						tPlanAmountInfoSchema.setMonF7(String.valueOf(getCellString(row.getCell(16))));
					}
					if (row.getCell(17) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(17))))) {
						tPlanAmountInfoSchema.setMonP8(String.valueOf(getCellString(row.getCell(17))));
					}
					if (row.getCell(18) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(18))))) {
						tPlanAmountInfoSchema.setMonF8(String.valueOf(getCellString(row.getCell(18))));
					}
					if (row.getCell(19) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(19))))) {
						tPlanAmountInfoSchema.setMonP9(String.valueOf(getCellString(row.getCell(19))));
					}
					if (row.getCell(20) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(20))))) {
						tPlanAmountInfoSchema.setMonF9(String.valueOf(getCellString(row.getCell(20))));
					}
					if (row.getCell(21) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(21))))) {
						tPlanAmountInfoSchema.setMonP10(String.valueOf(getCellString(row.getCell(21))));
					}
					if (row.getCell(22) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(22))))) {
						tPlanAmountInfoSchema.setMonF10(String.valueOf(getCellString(row.getCell(22))));
					}
					if (row.getCell(23) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(23))))) {
						tPlanAmountInfoSchema.setMonP11(String.valueOf(getCellString(row.getCell(23))));
					}
					if (row.getCell(24) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(24))))) {
						tPlanAmountInfoSchema.setMonF11(String.valueOf(getCellString(row.getCell(24))));
					}
					if (row.getCell(25) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(25))))) {
						tPlanAmountInfoSchema.setMonP12(String.valueOf(getCellString(row.getCell(25))));
					}
					if (row.getCell(26) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(26))))) {
						tPlanAmountInfoSchema.setMonF12(String.valueOf(getCellString(row.getCell(26))));
					}
					if (row.getCell(27) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(27))))) {
						tPlanAmountInfoSchema.setAllYearP(String.valueOf(getCellString(row.getCell(27))));
					}
					if (row.getCell(28) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(28))))) {
						tPlanAmountInfoSchema.setAllYearF(String.valueOf(getCellString(row.getCell(28))));
					}
					trans.add(tPlanAmountInfoSchema, method);
				}
			}
			if(!trans.commit()){
				this.addError("提交数据库失败！");
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	public void dg1DataBind(DataGridAction dga) {

		StringBuffer sql = new StringBuffer(" SELECT * FROM PlanAmountInfo where 1=1 ");
		String tActiveCode = (String) dga.getParams().get("ActiveCode");
		if(StringUtil.isNotEmpty(tActiveCode)){
			sql.append(" and PlanYear='"+tActiveCode+"'");
		}
		sql.append(" order by CreateDate");
		QueryBuilder qb = new QueryBuilder(sql.toString());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.setTotal(qb);	
		dga.bindData(dt);
		
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
	private Object getCellString(Cell cell) {
		  // TODO Auto-generated method stub
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Object result = null;
		  if(cell != null){
		    //单元格类型：Numeric:0,String:1,Formula:2,Blank:3,Boolean:4,Error:5
		    int cellType = cell.getCellType();
		    switch (cellType) {
		    case Cell.CELL_TYPE_STRING:
		      result = cell.getRichStringCellValue().getString();
		      break;
		    case Cell.CELL_TYPE_NUMERIC:
		    	if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
			    	  result = sdf.format(cell.getDateCellValue());
		    	}else{
		    		 result = cell.getNumericCellValue();
		    	}
		      break;
		    case Cell.CELL_TYPE_FORMULA:
		      
		      cell.getCellFormula();
		      try{
		    	  if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
			    	  result = sdf.format(cell.getDateCellValue());
			      }else{
			    	  result = cell.getStringCellValue();
			      }
		      }catch(Exception e){
		    	  result = cell.getStringCellValue();
		      }
		      break;
		    case Cell.CELL_TYPE_BOOLEAN:
		      result = cell.getBooleanCellValue();
		      break;
		    case Cell.CELL_TYPE_BLANK:
		      result = null;
		      break;
		    case Cell.CELL_TYPE_ERROR:
		      result = null;
		      break;
		    default:
		      break;
		    }
		  }
		  return result;
		}
}
