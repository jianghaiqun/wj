package com.sinosoft.cms.dataservice;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.cache.CacheManager;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ZCApplySchema;

public class ApplyAdmin extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String searchGender = dga.getParams().getString("searchGender");
		String searchEduLevel = dga.getParams().getString("searchEduLevel");
		QueryBuilder qb = new QueryBuilder("select * from  ZCApply where 1=1 ");
		if (StringUtil.isNotEmpty(searchGender)) {
			qb.append(" and Gender=?", searchGender);
		}
		if (StringUtil.isNotEmpty(searchEduLevel)) {
			qb.append(" and EduLevel=?", searchEduLevel);
		}
		qb.append(" order by ID desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dt.decodeColumn("Gender", Apply.Gender_MAP);
		dt.decodeColumn("EduLevel", Apply.EduLevel_MAP);
		dga.bindData(dt);
	}

	public static Mapx initSearch(Mapx params) {
		Mapx genderMap = new Mapx();
		genderMap.put("", "所有");
		genderMap.putAll(Apply.Gender_MAP);
		params.put("Gender", HtmlUtil.mapxToOptions(genderMap, ""));
		Mapx eduLevelMap = new Mapx();
		eduLevelMap.put("", "所有");
		eduLevelMap.putAll(Apply.EduLevel_MAP);
		params.put("EduLevel", HtmlUtil.mapxToOptions(eduLevelMap, ""));
		return params;
	}

	public void del() {
		String IDs = $V("IDs");
		String[] ids = IDs.split(",");
		Transaction trans = new Transaction();
		ZCApplySchema applicant = new ZCApplySchema();
		for (int i = 0; i < ids.length; i++) {
			trans.add(applicant.query(new QueryBuilder(" where ID = ?", ids[i])), Transaction.DELETE);
		}
		if (trans.commit()) {
			Response.setLogInfo(1, "删除成功");
		} else {
			Response.setLogInfo(0, "删除失败");
		}
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		if (StringUtil.isNotEmpty(ID)) {
			ZCApplySchema applicant = new ZCApplySchema();
			applicant.setID(ID);
			applicant.fill();
			Mapx map = applicant.toMapx();
			map.put("Gender", Apply.Gender_MAP.get(applicant.getGender()));
			map.put("EduLevel", Apply.EduLevel_MAP.get(applicant.getEduLevel()));
			map.put("Ethnicity", CacheManager.getMapx("Code", "Ethnicity").get(applicant.getEthnicity()));
			map.put("Political", Apply.Political_MAP.get(applicant.getPolitical()));
			map.put("PictureFile", (Config.getContextPath() + Config.getValue("UploadDir")/*
																						 * "/"+
																						 * Application
																						 * .
																						 * getCurrentSiteAlias
																						 * (
																						 * )
																						 */+ "/" + applicant
					.getPicture()).replaceAll("//", "/"));
			return map;
		} else {
			return params;
		}
	}
}
