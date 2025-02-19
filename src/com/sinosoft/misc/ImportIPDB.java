package com.sinosoft.misc;

import com.sinosoft.cms.stat.StatUtil;
import com.sinosoft.framework.data.DBConn;
import com.sinosoft.framework.data.DBConnPool;
import com.sinosoft.framework.data.DataAccess;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZDDistrictSchema;
import com.sinosoft.schema.ZDIPRangeSchema;
import com.sinosoft.schema.ZDIPRangeSet;
import com.sinosoft.schema.ZDIPSchema;
import com.sinosoft.schema.ZDIPSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public class ImportIPDB {
	private static final Logger logger = LoggerFactory.getLogger(ImportIPDB.class);
	public static void main(String[] args) {
		// importTXT();
		// fixAddress();
		// fixDistrictCode();
		// checkIP();
		// importIPRange();
		// verify();

		exportIPRangeAsSQL();

		// System.out.println(new
		// QueryBuilder("select Name from ZDDistrict where Code=?", StatUtil
		// .getDistrictCode("119.6.119.5")).executeString());
		// System.out.println(new
		// QueryBuilder("select Name from ZDDistrict where Code=?", StatUtil
		// .getDistrictCode("119.4.99.97")).executeString());
	}

	public static void exportIPRangeAsSQL() {
		ZDIPRangeSet set = new ZDIPRangeSchema().query();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from table ZDIPRange;\n");
		for (int i = 0; i < set.size(); i++) {
			ZDIPRangeSchema r = set.get(i);
			sb.append("insert into ZDIPRange (DistrictCode,IPRanges) values ('" + r.getDistrictCode() + "','"
					+ r.getIPRanges() + "');\n");
		}
		FileUtil.writeText("F:/IPRanges.sql", sb.toString(), "UTF-8");
		// System.out.println(sb);
	}

	public static void importTXT() {
		String[] pros = new String[] { "新疆", "青海", "宁夏", "内蒙", "黑龙江", "吉林", "辽宁", "河北", "天津", "北京", "山东", "安徽", "江苏",
				"浙江", "上海", "福建", "广东", "广西", "香港", "台湾", "澳门", "云南", "贵州", "西藏", "四川", "陕西", "山西", "河南", "湖北", "湖南",
				"重庆", "江西", "海南", "甘肃" };
		String fileName = "F:/qqwry/ip.txt";
		new QueryBuilder("truncate table ZDIP").executeNoQuery();
		DataTable dt = DataTableUtil.txtToDataTable(fileName, new String[] { "IP3", "IP4", "Address", "Memo" }, "\\s+");
		logger.info(dt.getDataRow(0).toString());
		logger.info(dt.getDataRow(1).toString());
		ZDIPSet set = new ZDIPSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZDIPSchema ip = new ZDIPSchema();
			String ip3 = dt.getString(i, "IP3");
			String ip4 = dt.getString(i, "IP4");
			String[] arr1 = ip3.split("\\.");
			String[] arr2 = ip4.split("\\.");
			long ip1 = Long.parseLong(arr1[0]) * 16777216L + Long.parseLong(arr1[1]) * 65536L + Long.parseLong(arr1[2])
					* 256L + Long.parseLong(arr1[3]) - 1L;
			long ip2 = Long.parseLong(arr2[0]) * 16777216 + Long.parseLong(arr2[1]) * 65536 + Long.parseLong(arr2[2])
					* 256 + Long.parseLong(arr2[3]) - 1;
			if (ip1 < 0) {
				ip1 = 0;
			}
			ip.setIP1(new Double(ip1).longValue());
			ip.setIP2(new Double(ip2).longValue());
			ip.setIP3(ip3);
			ip.setIP4(ip4);
			ip.setAddress(dt.getString(i, "Address"));
			ip.setMemo(dt.getString(i, "Memo"));
			if (ip.getMemo().length() > 60) {
				ip.setMemo(ip.getMemo().substring(0, 60));
			}
			boolean flag = false;
			for (int j = 0; j < pros.length; j++) {
				if (ip.getAddress().startsWith(pros[j])) {
					flag = true;
					break;
				}
			}
			if (!flag && ip.getAddress().indexOf("大学") > 0) {
				logger.info(ip.toString());
			}
			set.add(ip);
			if ((i % 5000 == 0 && i != 0) || i == dt.getRowCount() - 1) {
				set.insert();
				set.clear();
			}
		}
	}

	public static void fixAddress() {
		new QueryBuilder("update ZDIP set Address='' where Address='CZ88.NET'").executeNoQuery();
		new QueryBuilder("update ZDIP set Memo='' where Memo='CZ88.NET'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='内蒙古乌兰察布市' where Address='内蒙古乌兰察布盟'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='新疆伊犁哈萨克自治州' where Address='新疆伊犁州'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='新疆昌吉回族自治州' where Address='新疆昌吉州'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='新疆博尔塔拉蒙古自治州' where Address='新疆博尔塔拉州'").executeNoQuery();
		new QueryBuilder("update ZDIP set Address='内蒙古呼伦贝尔市' where Address='内蒙古呼伦贝尔盟'").executeNoQuery();

		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address='北京交通大学'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address='中央财经大学'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address='北京邮电大学'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市朝阳区',Memo=concat(Address,Memo) where Address like '对外经济贸易大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '清华大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖北省武汉市',Memo=concat(Address,Memo) where Address like '华中农业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='吉林省长春市',Memo=concat(Address,Memo) where Address like '吉林大学%'")
				.executeNoQuery();
		new QueryBuilder(
				"update ZDIP set Address='福建省漳州市',Memo=concat(Address,Memo) where Address='厦门大学' and Memo like '漳州校区%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='福建省厦门市',Memo=concat(Address,Memo) where Address like '厦门大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='福建省厦门市',Memo=concat(Address,Memo) where Address like '集美大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='上海市闵行区',Memo=concat(Address,Memo) where Address like '上海交通大学闵行校区%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='上海市',Memo=concat(Address,Memo) where Address like '上海交通大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北京科技大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北京航空航天大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖北省武汉市',Memo=concat(Address,Memo) where Address like '华中科技大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖北省武汉市',Memo=concat(Address,Memo) where Address like '武汉大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='山东省大连市',Memo=concat(Address,Memo) where Address like '大连理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北京大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='陕西省西安市',Memo=concat(Address,Memo) where Address like '西安交通大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北京交通大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='黑龙江省哈尔滨市',Memo=concat(Address,Memo) where Address like '哈尔滨工业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='黑龙江省哈尔滨市',Memo=concat(Address,Memo) where Address like '哈尔滨工程大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='黑龙江省哈尔滨市',Memo=concat(Address,Memo) where Address like '哈尔滨理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北方工业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '中国农业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江苏省南京市',Memo=concat(Address,Memo) where Address like '东南大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江苏省南京市',Memo=concat(Address,Memo) where Address like '南京大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖南省长沙市',Memo=concat(Address,Memo) where Address like '湖南师范大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江苏省南京市',Memo=concat(Address,Memo) where Address like '南京大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='吉林省长春市',Memo=concat(Address,Memo) where Address like '长春工业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='陕西省西安市',Memo=concat(Address,Memo) where Address like '西安石油大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='重庆市沙坪坝区',Memo=concat(Address,Memo) where Address like '重庆大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市朝阳区',Memo=concat(Address,Memo) where Address like '北京中医药大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北京理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北京林业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市朝阳区',Memo=concat(Address,Memo) where Address like '首都经贸大学东区%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市丰台区',Memo=concat(Address,Memo) where Address like '首都经贸大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北京师范大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='福建省福州市',Memo=concat(Address,Memo) where Address like '福建师范大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='福建省福州市',Memo=concat(Address,Memo) where Address like '福州大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江西省南昌市',Memo=concat(Address,Memo) where Address like '江西农业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='广东省广州市',Memo=concat(Address,Memo) where Address like '华南理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='四川省成都市',Memo=concat(Address,Memo) where Address like '四川大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='新疆乌鲁木齐市',Memo=concat(Address,Memo) where Address like '新疆大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖南省长沙市',Memo=concat(Address,Memo) where Address like '中南大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '北京西站%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='广东省广州市',Memo=concat(Address,Memo) where Address like '暨南大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='广东省广州市',Memo=concat(Address,Memo) where Address like '华南农业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市朝阳区',Memo=concat(Address,Memo) where Address like '北京化工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市石景山区',Memo=concat(Address,Memo) where Address like '北京石景山%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='天津市',Memo=concat(Address,Memo) where Address like '南开大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='河南省郑州市',Memo=concat(Address,Memo) where Address like '郑州大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市',Memo=concat(Address,Memo) where Address like '北京信息科技大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江苏省南京市',Memo=concat(Address,Memo) where Address like '南京理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='浙江省杭州市',Memo=concat(Address,Memo) where Address like '浙江工业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='浙江省杭州市',Memo=concat(Address,Memo) where Address like '浙江科技学院%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='青海省西宁市',Memo=concat(Address,Memo) where Address like '青海大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖南省长沙市',Memo=concat(Address,Memo) where Address like '湖南科技大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='浙江省宁波市',Memo=concat(Address,Memo) where Address like '宁波大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='上海市',Memo=concat(Address,Memo) where Address like '上海理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='上海市',Memo=concat(Address,Memo) where Address like '同济大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江苏省南京市',Memo=concat(Address,Memo) where Address like '南京工业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='四川省绵阳市',Memo=concat(Address,Memo) where Address like '四川西南科技大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='四川省成都市',Memo=concat(Address,Memo) where Address like '成都理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='黑龙江省哈尔滨市',Memo=concat(Address,Memo) where Address like '哈尔滨师范大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='山西省太原市',Memo=concat(Address,Memo) where Address like '太原科技大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='山西省太原市',Memo=concat(Address,Memo) where Address like '中北大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='山东省青岛市',Memo=concat(Address,Memo) where Address like '青岛大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='广东省广州市',Memo=concat(Address,Memo) where Address like '暨南大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江西省南昌市',Memo=concat(Address,Memo) where Address like '南昌大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖北省荆州市',Memo=concat(Address,Memo) where Address like '长江大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖北省武汉市',Memo=concat(Address,Memo) where Address like '湖北大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='四川省成都市',Memo=concat(Address,Memo) where Address like '四川师范大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='四川省成都市',Memo=concat(Address,Memo) where Address like '成都信息工程学院%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='四川省成都市',Memo=concat(Address,Memo) where Address like '西华大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='上海市',Memo=concat(Address,Memo) where Address like '华东师范大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江西省南昌市',Memo=concat(Address,Memo) where Address like '华东交通大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='上海市',Memo=concat(Address,Memo) where Address like '华东理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='黑龙江省哈尔滨市',Memo=concat(Address,Memo) where Address like '东北林业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='上海市',Memo=concat(Address,Memo) where Address like '东华大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='北京市海淀区',Memo=concat(Address,Memo) where Address like '首都师范大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='河南省郑州市',Memo=concat(Address,Memo) where Address like '黄河科技大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='辽宁省沈阳市',Memo=concat(Address,Memo) where Address like '东北大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='湖北省武汉市',Memo=concat(Address,Memo) where Address like '中南财经政法大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='黑龙江省哈尔滨市',Memo=concat(Address,Memo) where Address like '东北农业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='江西省抚州市',Memo=concat(Address,Memo) where Address like '东华理工大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='陕西省西安市',Memo=concat(Address,Memo) where Address like '西北工业大学%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='河北省三河市',Memo=concat(Address,Memo) where Address like '华北科技学院%'")
				.executeNoQuery();
		new QueryBuilder("update ZDIP set Address='河北省三河市',Memo=concat(Address,Memo) where Address like '华北科技学院%'")
				.executeNoQuery();
	}

	public static void fixDistrictCode() {
		DataTable dt = new QueryBuilder("select Address,IP1,IP2 from ZDIP").executeDataTable();
		DataTable dt2 = new QueryBuilder(
				"select Name,Code from ZDDistrict where code like '11%' or code like '12%' or code like '31%' "
						+ "or code like '50%' or treelevel in (0,1,2) order by code desc").executeDataTable();
		Mapx map = dt2.toMapx(0, 1);
		Mapx map2 = dt2.toMapx(1, 0);
		Object[] names = dt2.getColumnValues(0);
		QueryBuilder qb = new QueryBuilder("update ZDIP set DistrictCode=? where ip1=? and ip2=?");
		qb.setBatchMode(true);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String address = dt.getString(i, 0);
			for (int j = 0; j < names.length; j++) {
				String name = names[j].toString();
				String code = map.getString(name);
				String parentCode = code.substring(0, 4) + "00";
				if (parentCode.startsWith("11") || parentCode.startsWith("21") || parentCode.startsWith("31")
						|| parentCode.startsWith("50")) {
					parentCode = code.substring(0, 2) + "0000";
				}
				String parentName = map2.getString(parentCode);
				if (StringUtil.isNotEmpty(parentName) && !code.startsWith("00")) {
					if (address.indexOf(name) >= 0 && address.indexOf(parentName) >= 0) {
						qb.add(code);
						qb.add(dt.getString(i, "IP1"));
						qb.add(dt.getString(i, "IP2"));
						qb.addBatch();
						break;
					}
					if (address.indexOf(name.substring(0, 2)) >= 0 && address.indexOf(parentName.substring(0, 2)) >= 0) {
						qb.add(code);
						qb.add(dt.getString(i, "IP1"));
						qb.add(dt.getString(i, "IP2"));
						qb.addBatch();
						break;
					}
				}
			}
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			String address = dt.getString(i, 0);
			for (int j = 0; j < names.length; j++) {
				String name = names[j].toString();
				String code = map.getString(name);
				String parentCode = code.substring(0, 4) + "00";
				if (parentCode.startsWith("11") || parentCode.startsWith("21") || parentCode.startsWith("31")
						|| parentCode.startsWith("50")) {
					parentCode = code.substring(0, 2) + "0000";
				}
				String parentName = map2.getString(parentCode);
				if (StringUtil.isEmpty(parentName) || code.startsWith("00")) {
					if (address.indexOf(name) >= 0) {
						qb.add(code);
						qb.add(dt.getString(i, "IP1"));
						qb.add(dt.getString(i, "IP2"));
						qb.addBatch();
						break;
					}
					if (name.length() > 2) {
						if (address.indexOf(name.substring(0, 2)) >= 0) {
							qb.add(code);
							qb.add(dt.getString(i, "IP1"));
							qb.add(dt.getString(i, "IP2"));
							qb.addBatch();
							break;
						}
					}
				}
			}
		}
		qb.executeNoQuery();
	}

	public static void fixContry() {
		DBConn conn = DBConnPool.getConnection("DB_TY");
		DataAccess da = new DataAccess(conn);
		try {
			new QueryBuilder("delete from ZDDistrict where Code like '0%'").executeNoQuery();
			DataTable dt = da.executeDataTable(new QueryBuilder("select * from T_DM_GJDQ order by dmsx"));
			for (int i = 1; i <= dt.getRowCount(); i++) {
				ZDDistrictSchema d = new ZDDistrictSchema();
				d.setCode(StringUtil.leftPad(i + "", '0', 6));
				d.setCodeOrder(d.getCode());
				d.setName(dt.getString(i - 1, "MC"));
				d.setTreeLevel(0);
				d.setType("9");
				d.insert();
			}
			new QueryBuilder("update ZDDistrict set Code='000000',CodeOrder='000300' where Name = '中国'")
					.executeNoQuery();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public static void checkIP() {
		DataTable dt = new QueryBuilder("select IP1,IP2,DistrictCode from ZDIP order by ip1").executeDataTable();
		long last = 0;
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (last != 0) {
				if (Long.parseLong(dt.getString(i, 0)) - 1 != last) {
					logger.info(dt.getString(i, 0));
				}
			}
			last = Long.parseLong(dt.getString(i, 1));
		}
	}

	public static void importIPRange() {
		new QueryBuilder("delete from ZDIPRange").executeNoQuery();
		DataTable dt = new QueryBuilder("select IP1,IP2,DistrictCode from ZDIP order by DistrictCode,IP1")
				.executeDataTable();
		long lastIP2 = 0;
		String lastDistrictCode = null;
		ArrayList list = null;
		for (int i = 0; i < dt.getRowCount(); i++) {
			String DistrictCode = dt.getString(i, "DistrictCode");
			long IP1 = Long.parseLong(dt.getString(i, "IP1"));
			long IP2 = Long.parseLong(dt.getString(i, "IP2"));
			if (StringUtil.isEmpty(DistrictCode)) {
				continue;
			}
			if (!DistrictCode.equals(lastDistrictCode)) {
				if (list != null) {
					list.add(new Long(lastIP2));
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < list.size(); j += 2) {
						if (j != 0) {
							sb.append(",");
						}
						long t1 = ((Long) list.get(j)).longValue();
						long t2 = ((Long) list.get(j + 1)).longValue();
						sb.append(t1 + "+" + (t2 - t1));
					}
					ZDIPRangeSchema range = new ZDIPRangeSchema();
					range.setDistrictCode(lastDistrictCode);
					range.setIPRanges(sb.toString());
					range.insert();
				}
				list = new ArrayList();
				list.add(new Long(IP1));
				lastDistrictCode = DistrictCode;
			} else {
				if (IP1 != lastIP2 + 1) {
					list.add(new Long(lastIP2));
					list.add(new Long(IP1));
				}
			}
			lastIP2 = IP2;
		}

		// 加上最后一个
		list.add(new Long(lastIP2));
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < list.size(); j += 2) {
			if (j != 0) {
				sb.append(",");
			}
			long t1 = ((Long) list.get(j)).longValue();
			long t2 = ((Long) list.get(j + 1)).longValue();
			sb.append(t1 + "+" + (t2 - t1));
		}
		ZDIPRangeSchema range = new ZDIPRangeSchema();
		range.setDistrictCode(lastDistrictCode);
		range.setIPRanges(sb.toString());
		range.insert();
	}

	public static void verify() {
		DataTable dt = new QueryBuilder("select IP3,IP4,DistrictCode from ZDIP").executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String code = dt.getString(i, 2);
			String ip1 = dt.getString(i, 0);
			String ip2 = dt.getString(i, 1);
			String str = StatUtil.getDistrictCode(ip1);
			if (StringUtil.isNotEmpty(code) && !code.equals(str)) {
				logger.info(ip1 + "\t" + code + "\t" + str);
			}
			str = StatUtil.getDistrictCode(ip2);
			if (StringUtil.isNotEmpty(code) && !code.equals(str)) {
				logger.info(ip2 + "\t" + code + "\t" + str);
			}
		}

	}
}
