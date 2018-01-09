package com.sinosoft.cms.settlement;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 *
 * Created by dongsheng on 2017/6/2.
 */
public class ExcelBigSheetProcessor {
	private static final Logger logger = LoggerFactory.getLogger(ExcelBigSheetProcessor.class);
	private final OPCPackage xlsxPackage;
	private final FullSheetContentHandler handler;

	public ExcelBigSheetProcessor(OPCPackage pkg, FullSheetContentHandler handler) {

		this.xlsxPackage = pkg;
		this.handler = handler;
	}

	/**
	 * Parses and shows the content of one sheet using the specified styles and
	 * shared-strings tables.
	 *
	 * @param styles
	 *            The table of styles that may be referenced by cells in the
	 *            sheet
	 * @param strings
	 *            The table of strings that may be referenced by cells in the
	 *            sheet
	 * @param sheetInputStream
	 *            The stream to read the sheet-data from.
	 * 
	 * @exception java.io.IOException
	 *                An IO exception from the parser, possibly from a byte
	 *                stream or character stream supplied by the application.
	 * @throws SAXException
	 *             if parsing the XML data fails.
	 */
	public void processSheet(
			StylesTable styles,
			ReadOnlySharedStringsTable strings,
			XSSFSheetXMLHandler.SheetContentsHandler sheetHandler,
			InputStream sheetInputStream) {

		DataFormatter formatter = new DataFormatter(Locale.CHINA);
		InputSource sheetSource = new InputSource(sheetInputStream);
		try {
			XMLReader sheetParser = SAXHelper.newXMLReader();
			ContentHandler handler = new XSSFSheetXMLHandler(
					styles, null, strings, sheetHandler, formatter, false);
			sheetParser.setContentHandler(handler);
			sheetParser.parse(sheetSource);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
		} catch (SAXException e) {
			logger.error("Excel解析错误。" + e.getMessage(), e);
			throw new RuntimeException("Excel解析错误");
		} catch (IOException e) {
			logger.error("文件操作错误。" + e.getMessage(), e);
			throw new RuntimeException("文件操作错误。");
		}
	}

	/**
	 * Initiates the processing of the XLS workbook file to CSV.
	 *
	 * @throws IOException
	 *             If reading the data from the package fails.
	 * @throws SAXException
	 *             if parsing the XML data fails.
	 */
	public void process() {

		ReadOnlySharedStringsTable strings;
		XSSFReader xssfReader;
		StylesTable styles;
		XSSFReader.SheetIterator iter;
		try {
			strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
			xssfReader = new XSSFReader(this.xlsxPackage);
			styles = xssfReader.getStylesTable();
			styles.putNumberFormat((short) 0xe, "yyyy-MM-dd");
			styles.putNumberFormat((short) 0x16, "yyyy-MM-dd HH:mm:ss");
			iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		} catch (IOException e) {
			logger.error("ReadOnlySharedStringsTable文件读取异常" + e.getMessage(), e);
			throw new RuntimeException("文件读取异常。");
		} catch (SAXException e) {
			logger.error("ReadOnlySharedStringsTable解析excel错误。" + e.getMessage(), e);
			throw new RuntimeException("解析excel错误。");
		} catch (OpenXML4JException e) {
			logger.error("ReadOnlySharedStringsTable解析excel错误。" + e.getMessage(), e);
			throw new RuntimeException("解析excel错误。");
		}

		int index = 0;
		try {
			while (iter.hasNext()) {
				InputStream stream = iter.next();
				// String sheetName = iter.getSheetName();
				// System.out.println(sheetName + " [index=" + index + "]:");
				this.handler.beginning();
				processSheet(styles, strings, this.handler, stream);
				stream.close();
				this.handler.ending();
				++index;
			}
		} catch (Exception e) {
			logger.error("解析excel错误。" + e.getMessage(), e);
			throw new RuntimeException(e.getMessage());
		}

	}

}
