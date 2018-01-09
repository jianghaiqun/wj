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

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.schema.SDRemarkSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.util.Date;
import java.util.Random;

public class BaoquanInfoUpLoad extends Page {
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

			// 加保全记录
			SDRemarkSchema schema;

			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			if (sheet == null || sheet.getLastRowNum() < 1) {
				this.addError("Excel文件中没有数据!");
				return false;
			}
			if (sheet.getLastRowNum() > 301) {
				this.addError("导入的数据最多为300条!");
				return false;
			}
			// 取得条数
			this.setCount(sheet.getLastRowNum());
			String orderSn;
			String remark;
			String content = "";
			String sum = "";
			// 读取Excel
			int score = 0;
			Random rm = new Random();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				schema = new SDRemarkSchema();
				HSSFRow row = sheet.getRow(i);
				if (row != null) {
					score = 0;
					if (row.getCell(0) != null && !StringUtil.isEmpty(row.getCell(0).getStringCellValue())) {
						// id
						schema.setid(PubFun.GetNRemarkId());
						// 订单号
						orderSn = row.getCell(0).getStringCellValue().trim();
						schema.setorderSn(orderSn);
					} else {
						this.addError("第" + (i + 1) + "行,第1列不能是空！");
						return false;
					}
					// 内容
					if (row.getCell(1) != null && !StringUtil.isEmpty(row.getCell(1) .getStringCellValue())) {
						content = row.getCell(1).getStringCellValue().trim();
					}
					
					// 金额
					if (row.getCell(2) != null && !StringUtil.isEmpty(row.getCell(2) .getStringCellValue())) {
						sum = row.getCell(2).getStringCellValue().trim();
					}

					// remark
					remark = content + sum;
					schema.setremark(remark);

					schema.setid(PubFun.GetNRemarkId());
					schema.setOperateName(User.getUserName());
					schema.setOperateTime(new Date());
					schema.setOperateType("add");
					schema.setprop1("");
					schema.setprop2("");
					schema.setupperId("");

					trans.add(schema, Transaction.INSERT);
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
