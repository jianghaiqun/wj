package com.sinosoft.cms.site;

import java.util.Date;
import java.util.Iterator;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCBadWordSchema;
import com.sinosoft.schema.ZCBadWordSet;

/**
 * 关键字管理
 * 
 */
public class BadWord extends Page {

	public static final String TREELEVEL_1 = "1"; // 一般

	public static final String TREELEVEL_2 = "2"; // 比较敏感

	public static final String TREELEVEL_3 = "3"; // 非常敏感

	public final static Mapx TREELEVEL_MAP = new Mapx();

	static {
		TREELEVEL_MAP.put(TREELEVEL_1, "一般");
		TREELEVEL_MAP.put(TREELEVEL_2, "比较敏感");
		TREELEVEL_MAP.put(TREELEVEL_3, "非常敏感");
	}

	private static Mapx BadWordMap = new Mapx();

	static {
		BadWordMap.put(TREELEVEL_1, new Mapx());
		BadWordMap.put(TREELEVEL_2, new Mapx());
		BadWordMap.put(TREELEVEL_3, new Mapx());
		updateCache();
	}

	private static void updateCache() {
		((Mapx) BadWordMap.get(TREELEVEL_1)).clear();
		((Mapx) BadWordMap.get(TREELEVEL_2)).clear();
		((Mapx) BadWordMap.get(TREELEVEL_3)).clear();
		DataTable dt = new QueryBuilder("select TreeLevel,Word,ReplaceWord from ZCBadWord order by ID desc").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String TreeLevel = dt.getString(i, "TreeLevel");
			if (TREELEVEL_3.compareTo(TreeLevel) <= 0) {
				((Mapx) BadWordMap.get(TREELEVEL_2)).put(dt.getString(i, "Word"), dt.getString(i, "ReplaceWord"));
				((Mapx) BadWordMap.get(TREELEVEL_3)).put(dt.getString(i, "Word"), dt.getString(i, "ReplaceWord"));
			} else if (TREELEVEL_2.compareTo(TreeLevel) <= 0) {
				((Mapx) BadWordMap.get(TREELEVEL_2)).put(dt.getString(i, "Word"), dt.getString(i, "ReplaceWord"));
			}
			((Mapx) BadWordMap.get(TREELEVEL_1)).put(dt.getString(i, "Word"), dt.getString(i, "ReplaceWord"));
		}
	}

	public static String checkBadWord(String content) {
		return checkBadWord(TREELEVEL_1);
	}

	public static String checkBadWord(String content, String priority) {
		Mapx map = (Mapx) BadWordMap.get(priority);
		String badwords = "";
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			String word = (String) iter.next();
			if (StringUtil.isNotEmpty(content) && !"null".equals(content) && content.indexOf(word) != -1) {
				badwords += " " + word;
			}
		}
		return badwords;
	}

	public static String filterBadWord(String content) {
		return filterBadWord(content, TREELEVEL_1);
	}

	public static String filterBadWord(String content, String priority) {
		Mapx map = (Mapx) BadWordMap.get(priority);
		String word = null;
		String replaceWord = null;
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			word = null;
			replaceWord = null;
			word = (String) iter.next();
			replaceWord = (String) map.get(word);
			if (StringUtil.isNotEmpty(word)) {
				content = StringUtil.replaceAllIgnoreCase(content, word, replaceWord);
			}
		}
		return content;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String word = dga.getParam("Word");
		QueryBuilder qb = new QueryBuilder("select ID,Word,TreeLevel,ReplaceWord,AddUser,AddTime from ZCBadWord ");
		if (StringUtil.isNotEmpty(word)) {
			qb.append("where word like ?", "%" + word.trim() + "%");
		}
		if (StringUtil.isNotEmpty(dga.getSortString())) {
			qb.append(dga.getSortString());
		} else {
			qb.append(" order by ID desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("TreeLevel", TREELEVEL_MAP);
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) Request.get("DT");
		ZCBadWordSet set = new ZCBadWordSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCBadWordSchema badWord = new ZCBadWordSchema();
			badWord.setID(Integer.parseInt(dt.getString(i, "ID")));
			badWord.fill();
			// badWord.setValue(dt.getDataRow(i));
			badWord.setTreeLevel(dt.getString(i, "TreeLevel"));
			badWord.setWord(dt.getString(i, "Word"));
			badWord.setReplaceWord(dt.getString(i, "ReplaceWord"));
			badWord.setModifyTime(new Date());
			badWord.setModifyUser(User.getUserName());

			set.add(badWord);
		}
		if (set.update()) {
			updateCache();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		return null;
	}

	public void add() {
		String Word = $V("BadWord").trim();
		if (new QueryBuilder("select count(*) from ZCBadWord where Word = ?", Word).executeInt() == 0) {
			ZCBadWordSchema badWord = new ZCBadWordSchema();
			badWord.setID(NoUtil.getMaxID("BadWordID"));
			badWord.setWord(Word);
			badWord.setTreeLevel(Integer.parseInt($V("Level")));
			badWord.setReplaceWord($V("ReplaceWord"));
			badWord.setAddTime(new Date());
			badWord.setAddUser(User.getUserName());
			if (badWord.insert()) {
				updateCache();
				Response.setStatus(1);
				Response.setMessage("新增成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		} else {
			Response.setStatus(0);
			Response.setMessage("已经存在的敏感词!");
		}
	}

	public void importWords() {
		String FilePath = $V("FilePath");
		String Words = $V("BadWords");
		String wordsText = "";
		if (StringUtil.isNotEmpty(FilePath)) {
			FilePath = FilePath.replaceAll("//", "/");
			wordsText = FileUtil.readText(FilePath);
		} else {
			wordsText = Words;
		}
		String[] badWords = wordsText.split("\n");
		String temp = "";
		Transaction trans = new Transaction();
		ZCBadWordSchema badword = new ZCBadWordSchema();
		for (int i = 0; i < badWords.length; i++) {
			if (!(badWords[i].equals("\r") || StringUtil.isEmpty(badWords[i]))) {
				temp = badWords[i];
				temp = temp.trim().replaceAll("\\s+", ",");
				temp = temp.replaceAll("，", ",");
				String[] word = StringUtil.splitEx(temp, ",");
				if (word.length == 0 || word.length > 3 || StringUtil.isEmpty(word[0])) {
					continue;
				} else {
					boolean flag = false;
					if (new QueryBuilder("select count(*) from ZCBadWord where Word = ?", word[0]).executeInt() > 0) {
						flag = true;
					} else {
						flag = false;
					}
					badword = new ZCBadWordSchema();
					if (flag) {
						String WordID = new QueryBuilder("select ID from ZCBadWord where Word = ?", word[0]).executeOneValue().toString();
						badword.setID(WordID);
						badword.fill();
						badword.setWord(word[0]);
						if (word.length == 1) {
							badword.setReplaceWord("");
							badword.setTreeLevel(1);
						} else if (word.length == 2) {
							badword.setReplaceWord(word[1]);
							badword.setTreeLevel(1);
						} else if (word.length == 3) {
							badword.setReplaceWord(word[1]);
							if (StringUtil.isDigit(word[2]) && 0 < Integer.parseInt(word[2]) && Integer.parseInt(word[2]) < 4) {
								badword.setTreeLevel(word[2]);
							} else {
								badword.setTreeLevel(1);
							}
						}
					} else {
						badword.setID(NoUtil.getMaxID("BadWordID"));
						badword.setWord(word[0]);
						if (word.length == 1) {
							badword.setReplaceWord("");
							badword.setTreeLevel(1);
						} else if (word.length == 2) {
							badword.setReplaceWord(word[1]);
							badword.setTreeLevel(1);
						} else if (word.length == 3) {
							badword.setReplaceWord(word[1]);
							if (StringUtil.isDigit(word[2]) && 0 < Integer.parseInt(word[2]) && Integer.parseInt(word[2]) < 4) {
								badword.setTreeLevel(word[2]);
							} else {
								badword.setTreeLevel(1);
							}
						}
					}
					if (flag) {
						badword.setModifyTime(new Date());
						badword.setModifyUser(User.getUserName());
						trans.add(badword, Transaction.UPDATE);
					} else {
						badword.setAddTime(new Date());
						badword.setAddUser(User.getUserName());
						trans.add(badword, Transaction.INSERT);
					}
				}
			}
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "导入成功");
		} else {
			Response.setLogInfo(0, "导入失败");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!StringUtil.checkID(ids)) {
			Response.setStatus(0);
			Response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCBadWordSchema badWord = new ZCBadWordSchema();
		ZCBadWordSet set = badWord.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, Transaction.DELETE_AND_BACKUP);

		if (trans.commit()) {
			updateCache();
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("操作数据库时发生错误!");
		}
	}
}
