package cn.com.sinosoft.util;

import com.Ostermiller.util.CSVParser;
import com.sinosoft.framework.utility.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvFileParser2 {
 
	private CSVParser csvParser;
	private int currLineNum = -1;
	private String[] currLine = null;

	public CsvFileParser2() {

	}

	public CsvFileParser2(InputStreamReader in) throws IOException {

		csvParser = new CSVParser(in);
		currLineNum = csvParser.getLastLineNumber();
	}

	public CSVParser getCsvParser() {

		return csvParser;
	}

	public void setCsvParser(CSVParser csvParser) {

		this.csvParser = csvParser;
	}

	public boolean hasMore() throws IOException {
		currLine = csvParser.getLine();
		currLineNum = csvParser.getLastLineNumber();
		if (null == currLine)
			return false;
		return true;
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
//			List<Map<String, String>> list = new CsvFileParser2()
//					.ReadFileToList(new File("D:\\微信.txt"), "gbk");
//
////			System.out.println(list.size());
//			//System.out.println(list.get(0).get("0"));
//			//System.out.println(list.get(0).get("23"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public List<Map<String, String>> ReadFileToList(File file, String encoding) throws Exception {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> mapStr;
		InputStream in = new FileInputStream(file);
		InputStreamReader inReader = null;
		if (StringUtil.isEmpty(encoding)) {
			inReader = new InputStreamReader(in);
		} else {
			inReader = new InputStreamReader(in, encoding);
		}
		CsvFileParser2 parser = new CsvFileParser2(inReader);
		while (parser.hasMore()) {
			String[] currentRow = parser.currLine;
			mapStr = new HashMap<String, String>();
			for (int i = 0; i < currentRow.length; i++) {
				mapStr.put(String.valueOf(i), currentRow[i]);
			}
			list.add(mapStr);
		}
		parser.close();
		return list;
	}
}