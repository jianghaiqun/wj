package com.sinosoft.platform;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDTbsdInsuredSchema;
import com.sinosoft.schema.SDTbsdInsuredSet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TBSDImportInsuredBatch extends Page {
	public void getexcel() {
		try {
			String fileaddress = $V("fileaddress");
			int sum = 0;

			FileInputStream finput = null;
			try {
				finput = new FileInputStream(fileaddress);
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}

			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(finput);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFWorkbook wb = null;
			try {
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFSheet sheet = wb.getSheetAt(0);
			try {
				finput.close();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
				this.Response.setError("导入失败！请选择正确模板");
			}
			HSSFRow row = null;

			short i = 0;

			int maxRow = sheet.getLastRowNum();
			

			if (maxRow <= 0) {
				this.Response.setError("导入失败！导入的文件中不包含有效数据！");
				return;
			}
			
			SDTbsdInsuredSet tSDTbsdInsuredSet = new SDTbsdInsuredSet();
			
			QueryBuilder qb = new QueryBuilder("select max(InsertBatch) from SDTbsdInsured");
			long insertBatch = qb.executeLong() + 1;

			for (i = 10; i <= maxRow; i = (short) (i + 1)) {
				SDTbsdInsuredSchema tSDTbsdInsuredSchema = new SDTbsdInsuredSchema();
				row = sheet.getRow(i);
				

				if (((row.getCell((short) 1) == null) || (row.getCell((short) 1).getStringCellValue().equals("")))
						&& ((row.getCell((short) 2) == null) || (row.getCell((short) 2).getStringCellValue().equals("")))){
					break;
				}

				String recName = "";
				String passportId = "";
				String mobileNO = "";
				String email = "";
				try {
					for (int g = 1; g <4; g++) {
						HSSFCell readCell = row.getCell((short) g);

						if (readCell != null) {
							// 字符串类型
							if (readCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
								String temp = readCell.getStringCellValue();
								String regex = "prompt|script|iframe|<|\u003c|window.a";
								Pattern p = Pattern.compile(regex);
								Matcher m = p.matcher(temp.toLowerCase());
								if (m.find()) {
									this.Response.setError("有非法字符请重新导入");
									return;
								}
							}
						}
					}

					recName = row.getCell((short) 1).getStringCellValue().trim();
					if (!checkImportInfo(recName, "姓名", "recName", i)) {
						return;
					}

					passportId = String.valueOf(row.getCell((short) 2).getStringCellValue()).trim();
					if (!checkImportInfo(passportId, "证件号码", "passportId", i))
						return;
					for (short j = (short) (i + 1); j <= maxRow; j = (short) (j + 1)) {
						HSSFRow rowj = sheet.getRow(j);

						if (((rowj.getCell((short) 1) == null) || (rowj.getCell((short) 1).getStringCellValue().equals("")))
								&& ((rowj.getCell((short) 2) == null) || (rowj.getCell((short) 2).getStringCellValue().equals("")))) {
							break;
						}
						
						String passportId_j = String.valueOf(rowj.getCell((short) 4).getStringCellValue());
						if (!checkImportInfo(passportId, "证件号码", "passportId", j))
							return;
						if (passportId.equals(passportId_j)) {
							this.Response.setError("导入失败！第" + (i + 1) + "行与第" + (j + 1) + "行被保人护照号相同，请检查！");
							return;
						}

					}

					mobileNO = row.getCell((short) 3).getStringCellValue();
					if (!checkImportInfo(mobileNO, "手机号码", "mobileNO", i)) {
						return;
					}

					if (row.getCell((short) 4) != null) {
						email = row.getCell((short) 4).getStringCellValue();
						if (!checkImportInfo(email, "邮箱", "email", i)) {
							return;
						}
					}

					tSDTbsdInsuredSchema.setID(NoUtil.getMaxNo("TBSD"));
					tSDTbsdInsuredSchema.setInsertBatch(insertBatch);
					tSDTbsdInsuredSchema.setName(recName);
					tSDTbsdInsuredSchema.setPassportId(passportId);
					tSDTbsdInsuredSchema.setMobile(mobileNO);
					tSDTbsdInsuredSchema.setEmail(email);
					tSDTbsdInsuredSchema.setCreateDate(new Date());
					tSDTbsdInsuredSchema.setCreateUser(User.getUserName());

					tSDTbsdInsuredSet.add(tSDTbsdInsuredSchema);
				} catch (Exception e) {
					this.Response.setError("导入失败！请选择正确模板");
					logger.error(e.getMessage(), e);
					return;
				}
				sum++;
			}

			this.Response.setStatus(1);
			this.Response.setMessage("操作成功！共导入" + sum + "个被保人！");
			this.Response.put("Count", sum);

			Transaction trans = new Transaction();
			trans.add(tSDTbsdInsuredSet, 1);
			trans.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			this.Response.setError("导入异常！请选择正确模板！");
		}
	}

	private boolean checkImportInfo(String item, String itemName, String itemType, short i) {
		if ((itemType != null) && (!"email".equals(itemType)) && (!"mobileNO".equals(itemType)) && (StringUtil.isEmpty(item))) {
			this.Response.setError("导入失败！第" + (i + 1) + "行被保人" + itemName + "为空，请检查！");
			return false;
		}
		if (itemType.equals("mobileNO") && StringUtil.isNotEmpty(item) && !StringUtil.isMobileNO(item)) {
			this.Response.setError("导入失败，第" + (i + 1) + "行的被保人手机号输入有误，请检查！");
			return false;
		} else if (itemType.equals("email") && StringUtil.isNotEmpty(item) && !StringUtil.isMail(item)) {
			this.Response.setError("导入失败，第" + (i + 1) + "行的被保人邮箱输入有误，请检查！");
			return false;
		}

		return true;
	}
}
