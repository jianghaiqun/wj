package cn.com.sinosoft.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelWriteUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelWriteUtil.class);
	/**
	 * 多行一起写入
	 * 
	 * @param fileName
	 * @param list
	 *            多行数据
	 */
	public static void writeLines(String fileName, List<List<String>> list) {
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("batchRefund_template");
			for (int i = 0; i < list.size(); i++) {
				List<String> rowList = list.get(i);
				HSSFRow row = sheet.createRow(i);
				for (int j = 0; j < rowList.size(); j++) {
					HSSFCell cell = row
							.createCell(j, HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(rowList.get(j));
				}
			}
			FileOutputStream fout = new FileOutputStream(fileName, true);
			wb.write(fout);
			fout.close();
		} catch (Exception e) {
			logger.error("多行一起写入工作目录失败！" + e.getMessage(), e);
		}
	}
}
