package com.sinosoft.cms.dataservice;

import java.util.Date;

import com.sinosoft.framework.Ajax;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.ZCApplySchema;

public class Apply extends Ajax {

	public static final String Gender_F = "F";

	public static final String Gender_M = "M";

	public final static Mapx Gender_MAP = new Mapx();

	static {
		Gender_MAP.put(Gender_M, "男");
		Gender_MAP.put(Gender_F, "女");
	}

	public static final String EduLevel_Uni = "05";

	public static final String EduLevel_Jun = "04";

	public static final String EduLevel_Mas = "06";

	public final static Mapx EduLevel_MAP = new Mapx();

	static {
		EduLevel_MAP.put(EduLevel_Uni, "本科");
		EduLevel_MAP.put(EduLevel_Jun, "专科");
		EduLevel_MAP.put(EduLevel_Mas, "硕士");
	}

	public static final String Political_Pol = "01";

	public static final String Political_Pro = "02";

	public static final String Political_Lea = "03";

	public static final String Political_Peo = "04";

	public static final String Political_Oth = "05";

	public final static Mapx Political_MAP = new Mapx();

	static {
		Political_MAP.put(Political_Pol, "中共党员");
		Political_MAP.put(Political_Pro, "预备党员");
		Political_MAP.put(Political_Lea, "共青团员");
		Political_MAP.put(Political_Peo, "群众");
		Political_MAP.put(Political_Oth, "其他党派人士");
	}

	public static Mapx init(Mapx params) {
		String position = params.getString("Position");
		params.put("Political", HtmlUtil.mapxToOptions(Political_MAP, true));
		params.put("EduLevel", HtmlUtil.mapxToOptions(EduLevel_MAP, true));
		params.put("Gender", HtmlUtil.mapxToRadios("Gender", Gender_MAP, Gender_M));
		params.put("Ethnicity", HtmlUtil.codeToOptions("Ethnicity"));
		params.put("District", "");
		params.put("Position", position);

		Mapx district = new QueryBuilder("select code,name from zddistrict where treelevel=1").executeDataTable().toMapx(0, 1);
		params.put("NativePlace", HtmlUtil.mapxToOptions(district));
		return params;
	}

	public void add() {
		ZCApplySchema apply = new ZCApplySchema();
		apply.setAddTime(new Date());
		apply.setID(NoUtil.getMaxID("ID"));
		apply.setValue(Request);
		apply.setAddUser("");
		apply.setAddTime(new Date());
		
		if (apply.insert()) {
			Response.setLogInfo(1, "新增成功");
		} else {
			Response.setLogInfo(0, "新增" + apply.getName() + "失败!");
		}
	}
}
