package cn.com.sinosoft.util;

import com.Ostermiller.util.ExcelCSVParser;
import com.Ostermiller.util.LabeledCSVParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvFileParser {

	private LabeledCSVParser csvParser;
	private int currLineNum = -1;
	private String[] currLine = null;

	public CsvFileParser() {

	}

	public CsvFileParser(InputStreamReader in) throws IOException {

		csvParser = new LabeledCSVParser(new ExcelCSVParser(in));
		currLineNum = csvParser.getLastLineNumber();
	}

	public LabeledCSVParser getCsvParser() {

		return csvParser;
	}

	public void setCsvParser(LabeledCSVParser csvParser) {

		this.csvParser = csvParser;
	}

	public boolean hasMore() throws IOException {

		currLine = csvParser.getLine();
		currLineNum = csvParser.getLastLineNumber();
		if (null == currLine)
			return false;
		return true;
	}

	public String getByFieldName(String fieldName) {

		return csvParser.getValueByLabel(fieldName);
	}

	public void close() throws IOException {

		csvParser.close();
	}

	public String[] readLine() throws IOException {

		currLine = csvParser.getLine();
		currLineNum = csvParser.getLastLineNumber();
		return currLine;
	}

	public int getCurrLineNum() {

		return currLineNum;
	}

//	public static void main(String[] args) {
//		try {
//			List<Map<String, String>> list = new CsvFileParser()
//					.ReadFileToList(new File("D:\\微信.txt"));
//
////			System.out.println(list.size());
//			//System.out.println(list.get(0).get("0"));
//			//System.out.println(list.get(0).get("23"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public List<Map<String, String>> ReadFileToList(File file) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> mapStr;
		InputStream in = new FileInputStream(file);
		InputStreamReader inReader = new InputStreamReader(in, "gbk");
		CsvFileParser parser = new CsvFileParser(inReader);
		String[] lables = parser.getCsvParser().getLabels();
		while (parser.hasMore()) {
			mapStr = new HashMap<String, String>();
			for (int i = 0; i < lables.length; i++) {
				mapStr.put(String.valueOf(i), parser.getByFieldName(lables[i]));
			}
			list.add(mapStr);
		}
		parser.close();
		return list;
	}
}