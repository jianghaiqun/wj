package cn.com.sinosoft.util;

import com.sinosoft.framework.utility.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReadUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelReadUtil.class);

	public enum FileType {
		xls, xlsx
	}

	public static void main(String[] args) throws Exception {

		File file = new File("C:\\Users\\dongsheng\\Desktop\\新需求\\sss.xlsx");
		List<Map<String, String>> result = getData(file, 0);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < result.size(); i++) {
			Map<String, String> row = result.get(i);
			Map<String, Object> data =new HashMap<String, Object>();
			data.put("name",row.get("0"));
			data.put("glycemicIndex",Integer.valueOf(row.get("1")));
			data.put("calories",Integer.valueOf(row.get("2")));
			dataList.add(data);
		}
//		System.out.println(JSON.toJSON(dataList));

	}

	/**
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * 
	 * @param file
	 *            读取数据的源Excel
	 * @param ignoreRows
	 *            读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<Map<String, String>> getData(File file, int ignoreRows)
			throws FileNotFoundException, IOException {

		if (file == null) {
			return null;
		}
		String filePath = file.getPath();
		FileType fileType;
		if (filePath.endsWith("xls")) {
			fileType = FileType.xls;
		} else if (filePath.endsWith("xlsx")) {
			fileType = FileType.xlsx;
		} else {
			return null;
		}
		InputStream is = new FileInputStream(file);
		return getData(is, fileType, ignoreRows);
	}

	public static List<Map<String, String>> getData(InputStream is, ExcelReadUtil.FileType fileType, int ignoreRows)
			throws FileNotFoundException, IOException {

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		int rowSize = 0;
		if (FileType.xls.equals(fileType)) {
			BufferedInputStream in = new BufferedInputStream(is);

			// 打开HSSFWorkbook
			POIFSFileSystem fs = new POIFSFileSystem(in);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFCell cell = null;
			for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
				HSSFSheet st = wb.getSheetAt(sheetIndex);
				for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
					HSSFRow row = st.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					int tempRowSize = row.getLastCellNum() + 1;
					if (tempRowSize > rowSize) {
						rowSize = tempRowSize;
					}
					Map<String, String> values = new HashMap<String, String>();
					// Arrays.fill(values, "");
					boolean hasValue = false;
					for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
						String value = "";
						cell = row.getCell(columnIndex);
						if (cell != null) {
							// 注意：一定要设成这个，否则可能会出现乱码
							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									Date date = cell.getDateCellValue();
									if (date != null) {
										value = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
												.format(date);
									} else {
										value = "";
									}
								} else {
									value = new DecimalFormat("0.00").format(cell
											.getNumericCellValue());

									if (StringUtil.isNotEmpty(value) && value.indexOf(".00") >= 0) {
										value = value.replace(".00", "");
									}
								}
								break;
							case HSSFCell.CELL_TYPE_FORMULA:
								// 导入时如果为公式生成的数据则无值
								if (!cell.getStringCellValue().equals("")) {
									value = cell.getStringCellValue();
								} else {
									value = cell.getNumericCellValue() + "";
								}
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								value = "";
								break;
							case HSSFCell.CELL_TYPE_BOOLEAN:
								value = (cell.getBooleanCellValue() == true ? "Y"
										: "N");
								break;
							default:
								value = "";
							}
						}
						// if (columnIndex == 0 && value.trim().equals("")) {
						// break;
						// }
						values.put(String.valueOf(columnIndex), rightTrim(value));
						hasValue = true;
					}

					if (hasValue) {
						result.add(values);
					}
				}
			}

			in.close();
		} else if (FileType.xlsx.equals(fileType)) {
			XSSFWorkbook xwb = new XSSFWorkbook(is);
			XSSFCell cell = null;
			for (int sheetIndex = 0; sheetIndex < xwb.getNumberOfSheets(); sheetIndex++) {
				XSSFSheet st = xwb.getSheetAt(sheetIndex);
				for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
					XSSFRow row = st.getRow(rowIndex);
					if (row == null) {
						continue;
					}
					int tempRowSize = row.getLastCellNum() + 1;
					if (tempRowSize > rowSize) {
						rowSize = tempRowSize;
					}
					Map<String, String> values = new HashMap<String, String>();
					// Arrays.fill(values, "");
					boolean hasValue = false;
					for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
						String value = "";
						cell = row.getCell(columnIndex);
						if (cell != null) {
							// 注意：一定要设成这个，否则可能会出现乱码
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_STRING:
								value = cell.getStringCellValue();
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								if (HSSFDateUtil.isCellDateFormatted(cell)) {
									Date date = cell.getDateCellValue();
									if (date != null) {
										value = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
												.format(date);
									} else {
										value = "";
									}
								} else {
									value = new DecimalFormat("0.00").format(cell
											.getNumericCellValue());

									if (StringUtil.isNotEmpty(value) && value.indexOf(".00") >= 0) {
										value = value.replace(".00", "");
									}
								}
								break;
							case XSSFCell.CELL_TYPE_FORMULA:
								// 导入时如果为公式生成的数据则无值
								if (!cell.getStringCellValue().equals("")) {
									value = cell.getStringCellValue();
								} else {
									value = cell.getNumericCellValue() + "";
								}
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								break;
							case XSSFCell.CELL_TYPE_ERROR:
								value = "";
								break;
							case XSSFCell.CELL_TYPE_BOOLEAN:
								value = (cell.getBooleanCellValue() == true ? "Y"
										: "N");
								break;
							default:
								value = "";
							}
						}
						// if (columnIndex == 0 && value.trim().equals("")) {
						// break;
						// }
						values.put(String.valueOf(columnIndex), rightTrim(value));
						hasValue = true;
					}

					if (hasValue) {
						result.add(values);
					}
				}
			}

			is.close();
		}
		else {
			logger.error("未知文件！");
		}
		return result;
	}

	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {

		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
}