package com.sinosoft.cms.settlement;

import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by dongsheng on 2017/5/26.
 */
public class FinancialSettlementStatisticsExport {
	private static final Logger logger = LoggerFactory.getLogger(FinancialSettlementStatisticsExport.class);

	private List<SheetBean> sheets;
	private OutputStream out;

	public FinancialSettlementStatisticsExport(OutputStream out) {

		this.out = out;
	}

	public FinancialSettlementStatisticsExport(List<SheetBean> sheets, OutputStream out) {

		this.sheets = sheets;
		this.out = out;
	}

	private void createSheetRSX(Workbook wb, SheetBean bean, Map<String, CellStyle> styles) {

		Sheet sheet = wb.createSheet(bean.getSheetName());
		sheet.setDefaultColumnWidth(15);
		Map<String, String> header = bean.getHeader();
		DataTable dataTable = bean.getDataTable();
		Row titleRow = sheet.createRow(0);
		for (int i = 0; i <= 6; i++) {
			Cell cell = createCell(titleRow, (short) i, styles.get("header"));
			if (i == 0) {
				cell.setCellValue("保险代理机构业务报表—人身险公司业务（单位：万元）");
			}
		}
		Row headRow1 = sheet.createRow(1);
		Row headRow2 = sheet.createRow(2);
		for (int i = 0; i <= 14; i++) {
			Cell cell1 = createCell(headRow1, (short) i, styles.get("title"));
			Cell cell2 = createCell(headRow2, (short) i, styles.get("title"));
			switch (i) {
			case 1:
				cell1.setCellValue("行号");
				break;
			case 2:
				cell1.setCellValue("新单保费金额");
				cell2.setCellValue("累计");
				break;
			case 3:
				cell1.setCellValue("续期保单金额");
				cell2.setCellValue("累计");
				break;
			case 4:
				cell1.setCellValue("应付保费");
				cell2.setCellValue("累计");
				break;
			case 5:
				cell1.setCellValue("新单代理佣金");
				cell2.setCellValue("累计");
				break;
			case 6:
				cell1.setCellValue("续期代理佣金");
				cell2.setCellValue("累计");
				break;
			case 7:
				cell1.setCellValue("自营网络平台渠道");
				cell2.setCellValue("新单保费金额");
				break;
			case 8:
				cell2.setCellValue("续期保单金额");
				break;
			case 9:
				cell2.setCellValue("新单代理佣金");
				break;
			case 10:
				cell2.setCellValue("续期代理佣金");
				break;
			case 11:
				cell1.setCellValue("第三方网络平台渠道");
				cell2.setCellValue("新单保费金额");
				break;
			case 12:
				cell2.setCellValue("续期保单金额");
				break;
			case 13:
				cell2.setCellValue("新单代理佣金");
				break;
			case 14:
				cell2.setCellValue("续期代理佣金");
				break;
			}
		}
		int rowNum = 3, dataRowNum = 1;
		for (DataRow dataRow : dataTable) {
			Row row = sheet.createRow(rowNum);
			int treeLevel = dataRow.getInt("treeLevel");
			String type = dataRow.getString("type");
			switch (treeLevel) {
			case 1:
				createCell(row, (short) 0, styles.get("level1")).setCellValue(type);
				break;
			case 2:
				createCell(row, (short) 0, styles.get("level2")).setCellValue("   ".concat(type));
				break;
			case 3:
				createCell(row, (short) 0, styles.get("level3")).setCellValue("      ".concat(type));
				break;
			}
			createCell(row, (short) 1, styles.get("number")).setCellValue(dataRowNum);
			createCell(row, (short) 2, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sump1")) );
			createCell(row, (short) 3, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sump2")) );
			createCell(row, (short) 4, styles.get("money"));
			createCell(row, (short) 5, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sumf1")) );
			createCell(row, (short) 6, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sumf2")) );
			createCell(row, (short) 7, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sump1z")) );
			createCell(row, (short) 8, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sump2z")) );
			createCell(row, (short) 9, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sumf1z")) );
			createCell(row, (short) 10, styles.get("money"))
					.setCellValue(string2doubleOfTenThousand(dataRow.getString("sumf2z")) );
			createCell(row, (short) 11, styles.get("money"))
					.setCellValue(string2doubleOfTenThousand(dataRow.getString("sump1d")) );
			createCell(row, (short) 12, styles.get("money"))
					.setCellValue(string2doubleOfTenThousand(dataRow.getString("sump2d")) );
			createCell(row, (short) 13, styles.get("money"))
					.setCellValue(string2doubleOfTenThousand(dataRow.getString("sumf1d")) );
			createCell(row, (short) 14, styles.get("money"))
					.setCellValue(string2doubleOfTenThousand(dataRow.getString("sumf2d")) );
			rowNum++;
			dataRowNum++;
		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 10));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 11, 14));
	}

	private void createSheetCX(Workbook wb, SheetBean bean, Map<String, CellStyle> styles) {

		Sheet sheet = wb.createSheet(bean.getSheetName());
		sheet.setDefaultColumnWidth(15);
		Map<String, String> header = bean.getHeader();
		DataTable dataTable = bean.getDataTable();
		Row titleRow = sheet.createRow(0);
		for (int i = 0; i <= 4; i++) {
			Cell cell = createCell(titleRow, (short) i, styles.get("header"));
			if (i == 0) {
				cell.setCellValue("保险代理机构业务报表—产险公司业务 （单位：万元）");
			}
		}

		Row headRow1 = sheet.createRow(1);
		Row headRow2 = sheet.createRow(2);
		for (int i = 0; i <= 8; i++) {
			Cell cell1 = createCell(headRow1, (short) i, styles.get("title"));
			Cell cell2 = createCell(headRow2, (short) i, styles.get("title"));
			switch (i) {
			case 1:
				cell1.setCellValue("行号");
				break;
			case 2:
				cell1.setCellValue("保费金额");
				cell2.setCellValue("累计");
				break;
			case 3:
				cell1.setCellValue("应付保费");
				cell2.setCellValue("累计");
				break;
			case 4:
				cell1.setCellValue("代理佣金");
				cell2.setCellValue("累计");
				break;
			case 5:
				cell1.setCellValue("自营网络平台渠道");
				cell2.setCellValue("保费金额");
				break;
			case 6:
				cell2.setCellValue("代理佣金");
				break;
			case 7:
				cell1.setCellValue("第三方网络平台渠道");
				cell2.setCellValue("保费金额");
				break;
			case 8:
				cell2.setCellValue("代理佣金");
				break;
			}
		}
		int rowNum = 3, dataRowNum = 1;
		for (DataRow dataRow : dataTable) {
			Row row = sheet.createRow(rowNum);
			int treeLevel = dataRow.getInt("treeLevel");
			String type = dataRow.getString("type");
			switch (treeLevel) {
			case 1:
				createCell(row, (short) 0, styles.get("level1")).setCellValue(type);
				break;
			case 2:
				createCell(row, (short) 0, styles.get("level2")).setCellValue("   ".concat(type));
				break;
			case 3:
				createCell(row, (short) 0, styles.get("level3")).setCellValue("      ".concat(type));
				break;
			}
			createCell(row, (short) 1, styles.get("number")).setCellValue(dataRowNum);
			createCell(row, (short) 2, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sump")) );
			createCell(row, (short) 3, styles.get("money"));
			createCell(row, (short) 4, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sumf")) );
			createCell(row, (short) 5, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sumpz")));
			createCell(row, (short) 6, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sumfz")));
			createCell(row, (short) 7, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sumpd")));
			createCell(row, (short) 8, styles.get("money")).setCellValue(string2doubleOfTenThousand(dataRow.getString("sumfd")));
			rowNum++;
			dataRowNum++;
		}
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 5, 6));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 8));
	}

	void exportExcel(SheetBean rsxBean, SheetBean cxBean) {

		Workbook wb = new XSSFWorkbook();
		Map<String, CellStyle> styles = createStyles(wb);
		// declare a row object reference
		Row r;
		// declare a cell object reference
		Cell c;
		// create a new sheet
		createSheetRSX(wb, rsxBean, styles);
		createSheetCX(wb, cxBean, styles);
		try {
			wb.write(getOut());
			wb.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 使用银行家舍入算法,保留两位小数.
	 * 
	 * @param numericStr
	 * @return
	 */
	private double string2double(String numericStr) {

		if (StringUtils.isEmpty(numericStr))
			return 0;
		try {
			BigDecimal bigDecimal = new BigDecimal(numericStr);
			return bigDecimal.setScale(2, RoundingMode.HALF_EVEN).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 变换为万元.使用银行家舍入算法,保留两位小数.
	 * 
	 * @param numericStr
	 * @return
	 */
	private double string2doubleOfTenThousand(String numericStr) {

		if (StringUtils.isEmpty(numericStr))
			return 0;
		try {
			BigDecimal bigDecimal = new BigDecimal(numericStr);
			return bigDecimal.divide(new BigDecimal(10000), 2, RoundingMode.HALF_EVEN).doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}

	private Cell createCell(Row row, short column, CellStyle style) {

		Cell cell = row.createCell(column);
		cell.setCellStyle(style);
		return cell;
	}

	private CellStyle createBorderedStyle(Workbook wb) {

		BorderStyle thin = BorderStyle.THIN;
		short black = IndexedColors.BLACK.getIndex();

		CellStyle style = wb.createCellStyle();
		style.setBorderRight(thin);
		style.setRightBorderColor(black);
		style.setBorderBottom(thin);
		style.setBottomBorderColor(black);
		style.setBorderLeft(thin);
		style.setLeftBorderColor(black);
		style.setBorderTop(thin);
		style.setTopBorderColor(black);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		return style;
	}

	/**
	 * create a library of cell styles
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {

		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		DataFormat df = wb.createDataFormat();
		CellStyle style;
		Font headerFont = wb.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBold(true);
		style = createBorderedStyle(wb);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(headerFont);
		styles.put("header", style);

		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 12);
		titleFont.setBold(false);
		style = createBorderedStyle(wb);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFont(titleFont);
		styles.put("title", style);

		Font level1Font = wb.createFont();
		level1Font.setBold(true);
		level1Font.setColor(IndexedColors.BLACK.getIndex());
		style = createBorderedStyle(wb);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setDataFormat(df.getFormat("text"));
		style.setFont(level1Font);
		styles.put("level1", style);

		Font level2Font = wb.createFont();
		level2Font.setColor(IndexedColors.BLACK.getIndex());
		style = createBorderedStyle(wb);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setDataFormat(df.getFormat("text"));
		style.setFont(level2Font);
		styles.put("level2", style);

		Font level3Font = wb.createFont();
		level3Font.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style = createBorderedStyle(wb);
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setDataFormat(df.getFormat("text"));
		style.setFont(level3Font);
		styles.put("level3", style);

		style = createBorderedStyle(wb);
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setDataFormat(df.getFormat("0"));
		styles.put("number", style);

		style = createBorderedStyle(wb);
		style.setAlignment(HorizontalAlignment.RIGHT);
		style.setDataFormat(df.getFormat("#,##0.00"));
		styles.put("money", style);
		return styles;
	}

	static class SheetBean {

		LinkedHashMap<String, String> header;
		DataTable dataTable;
		String sheetName;

		public SheetBean() {

		}

		SheetBean(String sheetName) {

			this.sheetName = sheetName;
		}

		public SheetBean(LinkedHashMap<String, String> header, DataTable dataTable, String sheetName) {

			this.header = header;
			this.dataTable = dataTable;
			this.sheetName = sheetName;
		}

		public LinkedHashMap<String, String> getHeader() {

			return header;
		}

		public SheetBean setHeader(LinkedHashMap<String, String> header) {

			this.header = header;
			return this;
		}

		public DataTable getDataTable() {

			return dataTable;
		}

		public SheetBean setDataTable(DataTable dataTable) {

			this.dataTable = dataTable;
			return this;
		}

		public String getSheetName() {

			return sheetName;
		}

		public SheetBean setSheetName(String sheetName) {

			this.sheetName = sheetName;
			return this;
		}
	}

	public List<SheetBean> getSheets() {

		return sheets;
	}

	public FinancialSettlementStatisticsExport setSheets(List<SheetBean> sheets) {

		this.sheets = sheets;
		return this;
	}

	public OutputStream getOut() {

		return out;
	}

	public FinancialSettlementStatisticsExport setOut(OutputStream out) {

		this.out = out;
		return this;
	}
}
