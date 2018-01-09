/**批量评论导入功能
 * <p>Date        :2012-07-01</p> 
 * <p>Module      :CMS </p> 
 * <p>Description :微信激活碼导入</p> 
 * <p>Remark      : </p> 
 * @author  wangcaiyun
 * @version 1.0 
 * <p>------------------------------------------------------------</p> 
 * <p>  修改历史</p> 
 * <p>  序号   日期                修改人     修改原因</p> 
 */
package com.sinosoft.framework.utility.weixin.common;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.WxActiveCodeInfoSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ActiviCodeUpLoad extends Page {
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

			Map<String,String> activeCodeMap = new HashMap<String,String>();
			WxActiveCodeInfoSchema tWxActiveCodeInfoSchema = null;
			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			if (sheet == null || sheet.getLastRowNum() < 8) {
				this.addError("Excel文件中没有数据!");
				return false;
			}
			if (sheet.getLastRowNum() > 10014) {
				this.addError("导入的数据最多为10000条!");
				return false;
			}
			// 取得条数
			this.setCount(sheet.getLastRowNum() - 7);
			String id;
			// 读取Excel
			for (int i = 8; i <= sheet.getLastRowNum(); i++) {
				tWxActiveCodeInfoSchema = new WxActiveCodeInfoSchema();
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					// 激活码
					if (row.getCell(1) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(1))))) {
						id = String.valueOf(getCellString(row.getCell(1))).trim();
						tWxActiveCodeInfoSchema.setActiveCode(id);
						if(tWxActiveCodeInfoSchema.fill() || activeCodeMap.containsKey(id)){
							this.addError("第" + (i + 1) + "行,第2列已经存在，不允许重复添加！");
							return false;
						}
						activeCodeMap.put(id, "Y");
						tWxActiveCodeInfoSchema.setCreateDate(DateUtil.parseDateTime(PubFun.getCurrentDate()));
						tWxActiveCodeInfoSchema.setModifyDate(DateUtil.parseDateTime(PubFun.getCurrentDate()));
					} else {
						this.addError("第" + (i + 1) + "行,第2列不能是空！");
						return false;
					}
					// 激活网站
					if (row.getCell(2) != null
							&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(2))))) {
						tWxActiveCodeInfoSchema.setReceiveCode(String.valueOf(getCellString(row.getCell(2))).trim());

						// 旅游景点编码
						if (row.getCell(3) != null
								&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(3))))) {
							String content = String.valueOf(getCellString(row.getCell(3))).trim();
							tWxActiveCodeInfoSchema.setDestinationCode(content);

							// 旅游景点
							if (row.getCell(4) != null
									&& !StringUtil.isEmpty(String.valueOf(getCellString(row.getCell(4))).trim())) {
								tWxActiveCodeInfoSchema
										.setDestinationText(String.valueOf(getCellString(row.getCell(4))).trim());
							}
						}
					}
					trans.add(tWxActiveCodeInfoSchema,
							Transaction.INSERT);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	public void dg1DataBind(DataGridAction dga) {

		StringBuffer sql = new StringBuffer(" SELECT ActiveCode,ReceiveCode,ReceiveFlag,OpenID FROM WxActiveCodeInfo where 1=1 ");
		String tActiveCode = (String) dga.getParams().get("ActiveCode");
		if(StringUtil.isNotEmpty(tActiveCode)){
			sql.append(" and ActiveCode='"+tActiveCode+"'");
		}
		sql.append(" order by createdate");
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
