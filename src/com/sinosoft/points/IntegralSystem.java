package com.sinosoft.points;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDPointRuleSchema;
import com.sinosoft.schema.SDPointRuleSet;
import com.sinosoft.schema.SDProductPointRateSchema;

import java.util.Date;

@SuppressWarnings("unchecked")
public class IntegralSystem extends Page {

	public final static Mapx ACT_MAP = new Mapx();
	public final static Mapx POINTS_GIVE_MAP = new Mapx();

	static {
		// 会员行为
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_BUY, IntegralConstant.POINT_SOURCE_BUY_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_COMMENT, IntegralConstant.POINT_SOURCE_COMMENT_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_WORTH, IntegralConstant.POINT_SOURCE_WORTH_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_EXCHANGE, IntegralConstant.POINT_SOURCE_EXCHANGE_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_REGISTER, IntegralConstant.POINT_SOURCE_REGISTER_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_ANSWER, IntegralConstant.POINT_SOURCE_ANSWER_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_EMAIL, IntegralConstant.POINT_SOURCE_EMAIL_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MOBILE, IntegralConstant.POINT_SOURCE_MOBILE_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_NAME, IntegralConstant.POINT_SOURCE_MEM_NAME_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_SEX, IntegralConstant.POINT_SOURCE_MEM_SEX_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_BIRTHDAY, IntegralConstant.POINT_SOURCE_MEM_BIRTHDAY_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_IDENTITY_TYPE, IntegralConstant.POINT_SOURCE_MEM_IDENTITY_TYPE_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_AREA, IntegralConstant.POINT_SOURCE_MEM_AREA_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_ZIP, IntegralConstant.POINT_SOURCE_MEM_ZIP_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_MARRAY, IntegralConstant.POINT_SOURCE_MEM_MARRAY_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_TYPE, IntegralConstant.POINT_SOURCE_MEM_TYPE_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_OCCUP, IntegralConstant.POINT_SOURCE_MEM_OCCUP_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MEM_JOB, IntegralConstant.POINT_SOURCE_MEM_JOB_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_LOTTERY_ACTIVITY, IntegralConstant.POINT_SOURCE_LOTTERY_ACTIVITY_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_11, IntegralConstant.POINT_SOURCE_11_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER, IntegralConstant.POINT_SOURCE_RECOMMEND_REGISTER_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_RECOMMEND_BUY, IntegralConstant.POINT_SOURCE_RECOMMEND_BUY_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_OVER_TIME, IntegralConstant.POINT_SOURCE_OVER_TIME_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_OFFSET_POINT, IntegralConstant.POINT_SOURCE_OFFSET_POINT_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_CANCEL_FREEZE, IntegralConstant.POINT_SOURCE_CANCEL_FREEZE_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_MANUAL_HANDLING, IntegralConstant.POINT_SOURCE_MANUAL_HANDLING_DES);
		ACT_MAP.put(IntegralConstant.POINT_SOURCE_BIRTH_MONTH, IntegralConstant.POINT_SOURCE_BIRTH_MONTH_DES);
		
		// 积分赠送规则
		POINTS_GIVE_MAP.put(IntegralConstant.POINT_GIVE_01, IntegralConstant.POINT_GIVE_01_DES);
		POINTS_GIVE_MAP.put(IntegralConstant.POINT_GIVE_02, IntegralConstant.POINT_GIVE_02_DES);
		POINTS_GIVE_MAP.put(IntegralConstant.POINT_GIVE_03, IntegralConstant.POINT_GIVE_03_DES);
		POINTS_GIVE_MAP.put(IntegralConstant.POINT_GIVE_04, IntegralConstant.POINT_GIVE_04_DES);
	}

	/**
	 * 积分设置-页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initIntegralSystem(Mapx params) {

		params.put("MemberActList", HtmlUtil.mapxToOptions(ACT_MAP, true));

		return params;
	}

	/**
	 * 积分添加设置-页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initAddIntegral(Mapx params) {
		Mapx GRADE_MAP = new Mapx();
		DataTable dt = new QueryBuilder("select gradeCode, gradeName from MemberGrade ").executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			int count = dt.getRowCount();
			for (int i = 0; i < count; i++) {
				GRADE_MAP.put(dt.get(i, 0), dt.get(i, 1));
			}
		}
		params.put("MemberGradeList", HtmlUtil.mapxToOptions(GRADE_MAP, true));
		params.put("MemberActList", HtmlUtil.mapxToOptions(ACT_MAP, true));
		params.put("PointsGiveList", HtmlUtil.mapxToOptions(POINTS_GIVE_MAP, true));

		return params;
	}

	/**
	 * 积分修改设置-页面初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx initEditIntegral(Mapx params) {

		String id = params.get("Id") + "";
		if (StringUtil.isEmpty(id)) {
			return params;
		}
		SDPointRuleSchema pointRule = new SDPointRuleSchema();
		pointRule.setID(id);

		if (pointRule.fill()) {
			Mapx GRADE_MAP = new Mapx();
			DataTable dt = new QueryBuilder("select gradeCode, gradeName from MemberGrade ").executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				int count = dt.getRowCount();
				for (int i = 0; i < count; i++) {
					GRADE_MAP.put(dt.get(i, 0), dt.get(i, 1));
				}
			}
			params.put("type", pointRule.getPointsGive());
			params.put("MemberGradeList", HtmlUtil.mapxToOptions(GRADE_MAP, pointRule.getMemberGrade(), true));
			params.put("MemberActList", HtmlUtil.mapxToOptions(ACT_MAP, pointRule.getMemberAct(), false));
			params.put("PointsGiveList", HtmlUtil.mapxToOptions(POINTS_GIVE_MAP, pointRule.getPointsGive(), false));
			params.put("EnableList", HtmlUtil.codeToOptions("Enable", pointRule.getStatus(), false));
			params.put("PointsNum", pointRule.getPointsNum());
			params.put("PointDes", pointRule.getPointDes());

			if (!"1999-01-01".equals(pointRule.getStartDate())) {
				params.put("StartDate", pointRule.getStartDate());

			}
			if (!"2999-01-01".equals(pointRule.getStartDate())) {
				params.put("EndDate", pointRule.getEndDate());
			}
		}
		return params;
	}

	/**
	 * 新增
	 */
	public void save() {
		try {
			SDPointRuleSchema pointRule = new SDPointRuleSchema();
			String Id = $V("Id");
			pointRule.setID(Id);
			boolean flag = false;
			if (StringUtil.isNotEmpty(Id) && pointRule.fill()) {
				// update
				pointRule.setValue(Request);
				pointRule.setModifyDate(new Date());
				pointRule.setModifyUser(User.getUserName());

				if (StringUtil.isEmpty(pointRule.getStartDate())) {
					pointRule.setStartDate("1999-01-01");

				}

				if (StringUtil.isEmpty(pointRule.getEndDate())) {
					pointRule.setEndDate("2999-01-01");

				}

				flag = pointRule.update();

			} else {
				// add
				pointRule.setID(NoUtil.getMaxIDLocal("SDPointRuleID") + "");
				pointRule.setValue(Request);

				if (StringUtil.isEmpty(pointRule.getStartDate())) {
					pointRule.setStartDate("1999-01-01");

				}

				if (StringUtil.isEmpty(pointRule.getEndDate())) {
					pointRule.setEndDate("2999-01-01");

				}

				pointRule.setCreateDate(new Date());
				pointRule.setCreateUser(User.getUserName());
				flag = pointRule.insert();

			}

			if (flag) {
				Response.setStatus(1);
				Response.setMessage("保存成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("保存失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("保存失败! 异常原因：" + e.getMessage());
		}
	}

	/**
	 * 积分设置-数据查询
	 * 
	 * @param dga
	 */
	public static void IntegralSystemSearch(DataGridAction dga) {
		String sql = " select a.*,'' as MemberActName , '' as PointsGiveName, b.gradeName  from SDPointRule a left join MemberGrade b on a.MemberGrade = b.gradeCode  where 1=1 ";

		String MemberAct = (String) dga.getParams().get("MemberAct");
		String PointDes = (String) dga.getParams().get("PointDes");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(MemberAct)) {
			qb.append(" and MemberAct = ? ", MemberAct);
		}

		if (StringUtil.isNotEmpty(PointDes)) {
			qb.append(" and PointDes like ? ", "%" + PointDes + "%");
		}

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());

		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "MemberActName", ACT_MAP.get(dt.getString(i, "MemberAct")));
			dt.set(i, "PointsGiveName", POINTS_GIVE_MAP.get(dt.getString(i, "PointsGive")));
		}

		dga.bindData(dt);

	}

	/**
	 * 产品积分管理-页面初始化
	 * 
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initProductIntegral(Mapx params) {
		params.put("CompanyList", HtmlUtil.codeToOptions("SupplierCode", true));
		return params;
	}

	/**
	 * 产品积分管理-数据查询
	 * 
	 * @param dga
	 */
	public static void ProductIntegralSearch(DataGridAction dga) {
		String sql = " select s1.ProductID,s1.ProductName,s1.HtmlPath,s1.IsPublish,s2.GivePoints,s2.BuyPoints "
				+ " from sdproduct s1 left join SDProductPointRate s2 on (s1.ProductID = s2.ProductID) where 1=1 ";

		String CompanyNo = (String) dga.getParams().get("CompanyNo");
		String ProductName = (String) dga.getParams().get("ProductName");

		QueryBuilder qb = new QueryBuilder(sql);

		if (StringUtil.isNotEmpty(CompanyNo)) {
			qb.append(" and s1.remark6 = ? ", CompanyNo);
		}

		if (StringUtil.isNotEmpty(ProductName)) {
			qb.append(" and s1.ProductName like ? ", "%" + ProductName + "%");
		}

		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	/**
	 * 产品积分管理-数据保存
	 * 
	 */
	public void saveProductIntegral() {
		DataTable dt = (DataTable) Request.get("DT");

		if (dt == null || dt.getRowCount() == 0) {
			Response.setStatus(0);
			Response.setMessage("数据未修改!");
			return;
		}

		Transaction trans = new Transaction();
		String productids = "";
		for (int i = 0; i < dt.getRowCount(); i++) {
			productids += "'" + dt.getString(i, "productid") + "'";
			if (i != dt.getRowCount() - 1) {
				productids += ",";

			}
		}

		QueryBuilder del = new QueryBuilder("delete from SDProductPointRate where  productid in (" + productids + ")");
		trans.add(del);

		for (int i = 0; i < dt.getRowCount(); i++) {
			SDProductPointRateSchema tRateSchema = new SDProductPointRateSchema();
			tRateSchema.setProductID(dt.getString(i, "productid"));
			String GivePoints = dt.getString(i, "GivePoints");
			if (!checkNum(GivePoints)) {
				Response.setStatus(0);
				Response.setMessage("赠送积分数值为非数字!");
				return;
			} else if (Double.parseDouble(GivePoints) < 0 || Double.parseDouble(GivePoints) > 1) {
				Response.setStatus(0);
				Response.setMessage("抵值积分数值为非负小数!");
				return;
			}
			tRateSchema.setGivePoints(GivePoints);
			String BuyPoints = dt.getString(i, "BuyPoints");
			if (!checkNum(BuyPoints)) {
				Response.setStatus(0);
				Response.setMessage("抵值积分数值为非数字!");
				return;
			} else if (Double.parseDouble(BuyPoints) < 0 || Double.parseDouble(BuyPoints) > 1) {
				Response.setStatus(0);
				Response.setMessage("抵值积分数值为非负小数!");
				return;
			}
			tRateSchema.setBuyPoints(BuyPoints);
			tRateSchema.setMakeUser(User.getUserName());
			tRateSchema.setMakeDate(new Date());

			trans.add(tRateSchema, Transaction.INSERT);
		}

		if (trans.commit()) {
			Response.setStatus(1);
			Response.setMessage("保存成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败!");
		}
	}

	/**
	 * 
	 * @Title: checkNum
	 * @Description: TODO(校验是否为数字)
	 * @return boolean 返回类型
	 * @author
	 */
	private boolean checkNum(String num) {
		try {
			Double.parseDouble(num);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 元素节点删除
	 */
	public void del() {
		try {
			String ids = $V("IDs");
			if (StringUtil.isEmpty(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID不能为空!");
				return;
			}

			if (!StringUtil.checkID(ids)) {
				Response.setStatus(0);
				Response.setMessage("传入ID时发生错误!");
				return;
			}

			SDPointRuleSchema element = new SDPointRuleSchema();
			SDPointRuleSet element_set = element.query(new QueryBuilder("where id in (" + ids + ")"));
			if (element_set.delete()) {
				Response.setStatus(1);
				Response.setMessage("删除成功!");
			} else {
				Response.setStatus(0);
				Response.setMessage("删除失败，操作数据库时发生错误!");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Response.setStatus(0);
			Response.setMessage("删除失败! 异常原因：" + e.getMessage());
		}
	}
}
