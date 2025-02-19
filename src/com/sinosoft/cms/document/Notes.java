package com.sinosoft.cms.document;

import java.util.Calendar;
import java.util.Date;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCNotesSchema;
import com.sinosoft.schema.ZCNotesSet;

/**
 */

public class Notes extends Page {
	public static Mapx init(Mapx params) {
		String curDate = ""; // 当前时间
		int firstDayOnWeek = 0;// 每个月1号是星期几
		int dayInMonth = 0;// 得到一个月有多少天
		int countTR = 0; // 计算有几个tr

		Object o1 = params.get("cDate");
		Object o2 = params.get("Flag");
		if (o1 != null && o2 != null) {
			String Flag = o2.toString();
			String s = o1.toString();
			if ("-1".equals(Flag)) {// 往前一个月
				curDate = DateUtil.toString(DateUtil.addMonth(DateUtil.parse(s), -1));
			} else if ("0".equals(Flag)) { // 不变
				curDate = s;
			} else { // 往后一个月
				curDate = DateUtil.toString(DateUtil.addMonth(DateUtil.parse(s), 1));
			}
		} else {
			curDate = DateUtil.getCurrentDate();
		}

		Mapx map = new Mapx();
		StringBuffer sb = new StringBuffer();

		sb.append("<table width='100%' cellspacing='0' cellpadding='0' border='1' bordercolor='#aaccee'>");
		sb.append("<tr bgcolor='#DCF0FB' align='center'>");
		sb.append("<td width='15%' height='30'><font class='green'>星期日</font></td>");
		sb.append("<td width='14%'>星期一</td>");
		sb.append("<td width='14%'>星期二</td>");
		sb.append("<td width='14%'>星期三</td>");
		sb.append("<td width='14%'>星期四</td>");
		sb.append("<td width='14%'>星期五</td>");
		sb.append("<td width='15%'><font class='green'>星期六</font></td>");
		sb.append("</tr>");

		firstDayOnWeek = DateUtil.getDayOfWeek(DateUtil.getFirstDayOfMonth(curDate));
		dayInMonth = DateUtil.getMaxDayOfMonth(DateUtil.parse(curDate));
		countTR = (int) Math.ceil((dayInMonth + firstDayOnWeek - 1) / 7.0);
		int m = 1;
		DataTable dt = null;

		for (int n = 1; n <= countTR; n++) {
			sb.append("<tr valign='top'>");
			for (int i = 1; i <= 7; i++) {
				sb.append("<td>");
				if ((i < firstDayOnWeek && n == 1) || m > dayInMonth) {
					sb.append("&nbsp;");
				} else {
					sb.append("<table width='100%' border='0' cellspacing='0' cellpadding='0' ");
					if (!(i < firstDayOnWeek && n == 1) && m <= dayInMonth) {
						sb.append("onDblClick='add(" + m + ");'");
					}
					sb.append(">");
					sb.append("<tr>");
					sb
							.append("<td width='40' height='20' align='center' valign='middle' style='border-bottom:#aaccee 1px solid; border-right:#aaccee 1px solid; background-color:#DCF0FB'>");
					if ((i < firstDayOnWeek && n == 1) || m > dayInMonth) {
						sb.append("&nbsp;");
					} else {
						sb.append(m + "日");
						m++;
						sb.append("<br>");
					}
					sb.append("<td bgcolor='#E9F0F8'>&nbsp;</td>");
					sb.append("</tr>");
					sb.append("<tr valign='top'>");
					// 取值，tDate 当前格的日期
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateUtil.parse(curDate));
					cal.set(Calendar.DAY_OF_MONTH, m - 1);
					String tDate = DateUtil.toString(cal.getTime());
					QueryBuilder qb = new QueryBuilder("select title,id from zcnotes where notetime=? and adduser=?");
					if (Config.isDB2()) {
						qb.setSQL("select title,id from zcnotes where date(notetime)=? and adduser=?");
					}
					qb.add(tDate);
					qb.add(User.getUserName());
					dt = qb.executeDataTable();
					// 如果是当天的话，填充米黄色
					if (DateUtil.getCurrentDate().equals(tDate)) {
						sb
								.append("<td colspan='2' height='55' bgcolor='#FFFFCC' onmouseover=\"this.bgColor='#EDFBD2'\" onmouseout=\"this.bgColor='#FFFFCC'\">");
					} else {
						sb
								.append("<td colspan='2' height='55' onmouseover=\"this.bgColor='#EDFBD2'\" onmouseout=\"this.bgColor=''\">");
					}
					for (int j = 0; j < dt.getRowCount(); j++) {
						String str = dt.get(j, 0).toString();
						if (str.length() > 12) {
							str = str.substring(0, 12) + "...";
						}
						sb.append("<a herf='#;' style='cursor:pointer;' onClick='edit(" + (m - 1) + ","
								+ dt.get(j, 1).toString() + ");' onContextMenu='showMenu(event," + (m - 1) + ","
								+ dt.get(j, 1).toString() + ");'>" + str + "</a>");
						sb.append("<br>");
					}
					sb.append("</td>");
					sb.append("</tr>");
					sb.append("</table>");
				}
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");

		map.put("NoteContent", sb.toString());
		map.put("nowDate", DateUtil.toString(DateUtil.parse(curDate), "yyyy年MM月"));
		map.put("hnowDate", curDate);
		return map;
	}

	public static Mapx initDialog(Mapx params) {
		Object o = params.get("ID");
		Mapx map = new Mapx();
		if (o != null) {
			ZCNotesSchema notes = new ZCNotesSchema();
			notes.setID(Integer.parseInt(o.toString()));
			notes.query();
			ZCNotesSet set = notes.query();
			if (set.size() < 1) {
				return null;
			}
			notes = set.get(0);
			map.put("Title", notes.getTitle());
			map.put("Content", notes.getContent());
		}
		return map;
	}

	public void save() {
		ZCNotesSchema notes = new ZCNotesSchema();
		// 增加
		if ("".equals($V("ID"))) {
			notes.setID(NoUtil.getMaxID("NotesID"));
			notes.setTitle($V("Title"));
			notes.setContent($V("Content"));
			// 计算备忘时间
			String nowDate = $V("nowDate");
			String DateOfMonth = $V("DateOfMonth");
			Calendar cal = Calendar.getInstance();
			cal.setTime(DateUtil.parse(nowDate));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(DateOfMonth));
			Date tDate = cal.getTime();
			notes.setNoteTime(tDate);

			notes.setAddTime(new Date());
			notes.setAddUser(User.getUserName());
			if (notes.insert()) {
				Response.setStatus(1);
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		}
		// 修改
		else {
			notes.setID(Integer.parseInt($V("ID")));
			notes.fill();
			notes.setTitle($V("Title"));
			notes.setContent($V("Content"));
			notes.setModifyTime(new Date());
			if (notes.update()) {
				Response.setStatus(1);
			} else {
				Response.setStatus(0);
				Response.setMessage("发生错误!");
			}
		}
	}

	public void del() {
		String id = $V("ID");
		ZCNotesSchema notes = new ZCNotesSchema();
		notes.setID(Integer.parseInt(id));
		if (notes.delete()) {
			Response.setStatus(1);
		} else {
			Response.setStatus(0);
			Response.setMessage("发生错误!");
		}
	}
}
