package com.sinosoft.cms.dataservice;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.ProductAnnouncementSchema;
import com.sinosoft.schema.ProductAnnouncementSet;

public class PublicInform extends Page {
	public static Mapx init(Mapx params) {
		params.put("ViewFlag",
				HtmlUtil.codeToOptions("PublicInfo.ViewFlag", false));
		params.put("Color", HtmlUtil.codeToOptions("PublicInfo.Color", true));
		return params;
	}
	public static Mapx MemberInfoinit(Mapx params) {
		String id = params.getString("id");
		if(!StringUtil.isEmpty(id)){
			ProductAnnouncementSchema schema = new ProductAnnouncementSchema();
			schema.setID(id);
			if(schema.fill()){
				String info = schema.getInfo();
				String subIndex = "";
					if (StringUtil.isNotEmpty(info)) {
						subIndex = "<li style='color:" +schema.getColor()
								+ "'>";
						if (info.startsWith(subIndex)) {
							info = info.substring(subIndex.length());
						}
						if (info.endsWith("</li>")) {
							info = info.substring(0, info.length() - 5);
						}
						schema.setInfo(info);
					}
				params = schema.toMapx();
			}
		}
		params.put("ViewFlags",
				HtmlUtil.codeToOptions("PublicInfo.ViewFlag", true));
		params.put("Colors", HtmlUtil.codeToOptions("PublicInfo.Color", true));
		return params;
	}

	/**
	 * 得到产品信息
	 * 
	 * @param dga
	 */
	public void dg1DataBind(DataGridAction dga) {
		// 得到查询SQL
		String querySql = "select s.ProductName, s.ProductID, '' as HasInfo "
				+ "from sdproduct s where 1=1 ";
		// 判断是否有公告的条件
		String whereSql = " and ViewFlag = '1' and UNIX_TIMESTAMP(DATE_FORMAT"
				+ "(StartDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(now()) "
				+ "and UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) "
				+ "> UNIX_TIMESTAMP(now()) ";

		// 查询条件
		StringBuffer wherePart = new StringBuffer();
		// 产品名称
		String productName = dga.getParams().getString("productName");
		if (StringUtil.isNotEmpty(productName)) {
			wherePart.append(" and s.ProductName like '%" + productName.trim()
					+ "%'");
		}
		// 产品编码
		String productCode = dga.getParams().getString("productCode");
		if (StringUtil.isNotEmpty(productCode)) {
			wherePart.append(" and s.ProductID like '%" + productCode.trim()
					+ "%'");
		}
		
		// 0:没有公告 1:有公告
		String hasInfo = dga.getParams().getString("hasInfo");
		if ("0".equals(hasInfo)) {
			wherePart.append(" and not exists(select id from "
					+ "ProductAnnouncement where ProductID = s.ProductID "
					+ whereSql + ")");
		} else if ("1".equals(hasInfo)) {
			wherePart.append(" and exists(select id from "
					+ "ProductAnnouncement where ProductID = s.ProductID "
					+ whereSql + ")");
		}
		if (StringUtils.isNotEmpty(wherePart.toString())) {
			querySql += wherePart.toString();
		}

		querySql += " order by ProductID asc ";
		// 取得结果
		QueryBuilder qb = new QueryBuilder(querySql);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			// 查询产品是否有正在启用的公告
			String sql = "select count(*) from ProductAnnouncement "
					+ "where ProductID = ? " + whereSql;
			QueryBuilder qbCount = null;
			for (int i = 0; i < dt.getRowCount(); i++) {
				qbCount = new QueryBuilder(sql, dt.get(i, 1));
				if (qbCount.executeInt() > 0) {
					dt.set(i, 2, "是");
				}
			}

		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 得到保险公司信息
	 * 
	 * @param dga
	 */
	public void dg2DataBind(DataGridAction dga) {
		// 得到查询SQL
		String sql = "select CodeValue as comCode, CodeName as comName, "
				+ "'' as HasInfo from zdcode a where CodeType = ? and ParentCode = ? ";
		// 判断是否有公告的条件
		String whereSql = " and ViewFlag = '1' and UNIX_TIMESTAMP(DATE_FORMAT"
				+ "(StartDate,'%Y-%m-%d %H:%i:%s')) <= UNIX_TIMESTAMP(now()) "
				+ "and UNIX_TIMESTAMP(DATE_FORMAT(EndDate,'%Y-%m-%d %H:%i:%s')) "
				+ "> UNIX_TIMESTAMP(now()) ";
		
		// 查询条件
		StringBuffer wherePart = new StringBuffer();
		// 产品名称
		String comName = dga.getParams().getString("comName");
		if (StringUtil.isNotEmpty(comName)) {
			wherePart.append(" and CodeName like '%" + comName.trim() + "%'");
		}
		// 产品编码
		String comCode = dga.getParams().getString("comCode");
		if (StringUtil.isNotEmpty(comCode)) {
			wherePart.append(" and CodeValue like '%" + comCode.trim() + "%'");
		}
		// 0:没有公告 1:有公告
		String hasInfo = dga.getParams().getString("hasInfo");
		if ("0".equals(hasInfo)) {
			wherePart.append(" and not exists(select id from "
					+ "ProductAnnouncement where ComCode = a.CodeValue "
					+ whereSql + ")");
		} else if ("1".equals(hasInfo)) {
			wherePart.append(" and exists(select id from "
					+ "ProductAnnouncement where ComCode = a.CodeValue "
					+ whereSql + ")");
		}
		
		if (StringUtils.isNotEmpty(wherePart.toString())) {
			sql += wherePart.toString();
		}
		sql += " order by CodeValue asc ";
		// 取得结果
		QueryBuilder qb = new QueryBuilder(sql, "SupplierCode", "SupplierCode");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			// 查询产品是否有正在启用的公告
			sql = "select count(*) from ProductAnnouncement "
					+ "where ComCode = ?" + whereSql;
			QueryBuilder qbCount = null;
			for (int i = 0; i < dt.getRowCount(); i++) {
				qbCount = new QueryBuilder(sql, dt.get(i, 0));
				if (qbCount.executeInt() > 0) {
					dt.set(i, 2, "是");
				}
			}
		}
		
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	/**
	 * 查询公告
	 * 
	 * @param dga
	 */
	public void Infodg1DataBind(DataGridAction dga) {
		String productID = (String) dga.getParams().get("productID");
		String comCode = (String) dga.getParams().get("comCode");
		if (StringUtil.isEmpty(productID) && StringUtil.isEmpty(comCode)) {
			return;
		}
		String sql = "select id, InfoOrder, StartDate, EndDate, ViewFlag, "
				+ "Color, Info, CreateDate, CreateUser, ModifyDate, ModifyUser "
				+ "from ProductAnnouncement where 1=1 ";
		QueryBuilder qb = new QueryBuilder(sql);
		if (StringUtil.isNotEmpty(productID)) {
			qb.append("and ProductID = '" + productID + "' ");
		}
		if (StringUtil.isNotEmpty(comCode)) {
			qb.append("and ComCode = '" + comCode + "' ");
		}
		qb.append("order by InfoOrder ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			String info = "";
			String subIndex = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				info = dt.getString(i, "Info");
				if (StringUtil.isNotEmpty(info)) {
					subIndex = "<li style='color:" + dt.getString(i, "Color")
							+ "'>";
					if (info.startsWith(subIndex)) {
						info = info.substring(subIndex.length());
					}
					if (info.endsWith("</li>")) {
						info = info.substring(0, info.length() - 5);
					}
					dt.set(i, "Info", info);
				}

			}
		}
		dga.bindData(dt);
	}

	/**
	 * 保存
	 */
	public void save() {
		String productID = $V("productID");
		String comCode = $V("comCode");
		DataTable dt = Request.getDataTable("Data");
		ProductAnnouncementSet set = new ProductAnnouncementSet();
		ProductAnnouncementSchema schema = new ProductAnnouncementSchema();
		boolean flag = true;
		Transaction trans = new Transaction();
		int b = dt.getRowCount();
		for (int i = 0; i < b; i++) {
			schema = new ProductAnnouncementSchema();
			Date date = new Date();
			DataRow dr = dt.getDataRow(i);
			String info = dr.getString("Info").trim();
			info = "<li style='color:" + dr.getString("Color") + "'>" + info
					+ "</li>";
			String id = dr.getString("id");
			if (dr.getDate("StartDate").compareTo(dr.getDate("EndDate")) >= 0) {
				Response.setStatus(0);
				Response.setMessage("结束时间必须大于开始时间！");
				return;
			}
			if (StringUtil.isEmpty(id)) {
				id = PubFun.getPubInfoID();
				schema.setCreateDate(date);
				schema.setCreateUser(User.getUserName());
				schema.setID(id);
				schema.setComCode(comCode);
				schema.setProductID(productID);
				schema.setStartDate(dr.getDate("StartDate"));
				schema.setEndDate(dr.getDate("EndDate"));
				schema.setInfoOrder(dr.getString("InfoOrder"));
				schema.setViewFlag(dr.getString("ViewFlag"));
				schema.setColor(dr.getString("Color"));
				schema.setInfo(info);
				set.add(schema);

			} else {
				schema.setID(id);
				if (schema.fill()) {
					boolean changeFlag = false;
					if (schema.getStartDate()
							.compareTo(dr.getDate("StartDate")) != 0) {
						schema.setStartDate(dr.getDate("StartDate"));
						changeFlag = true;
					}
					if (schema.getEndDate().compareTo(dr.getDate("EndDate")) != 0) {
						schema.setEndDate(dr.getDate("EndDate"));
						changeFlag = true;
					}
					if (schema.getInfoOrder() != dr.getInt("InfoOrder")) {
						schema.setInfoOrder(dr.getInt("InfoOrder"));
						changeFlag = true;
					}
					if (!schema.getViewFlag().equals(dr.getString("ViewFlag"))) {
						schema.setViewFlag(dr.getString("ViewFlag"));
						changeFlag = true;
					}
					if (!schema.getColor().equals(dr.getString("Color"))) {
						schema.setColor(dr.getString("Color"));
						changeFlag = true;
					}
					if (!schema.getInfo().equals(info)) {
						schema.setInfo(info);
						changeFlag = true;
					}
					if (changeFlag) {
						schema.setModifyDate(date);
						schema.setModifyUser(User.getUserName());
					}
					set.add(schema);
				}
			}
		}

		if (StringUtil.isNotEmpty(productID)) {
			trans.add(new QueryBuilder(
					"delete from ProductAnnouncement where ProductID=?",
					productID));
		}

		if (StringUtil.isNotEmpty(comCode)) {
			trans.add(new QueryBuilder(
					"delete from ProductAnnouncement where ComCode=?", comCode));
		}

		if (set != null) {
			trans.add(set, Transaction.DELETE_AND_INSERT);
		}
		if (!trans.commit()) {
			flag = false;
		}
		if (flag) {
			Response.setStatus(1);
			Response.setMessage("保存成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败，操作数据库时发生错误!");
		}
	}

	/**
	 * 会员中心-公告保存
	 */
	public void memberCenterInfoSave() {
		String StartDate = $V("StartDate");
		String EndDate = $V("EndDate");
		String ViewFlag = $V("ViewFlag");
		String Color = $V("Color");
		String Info = $V("Info");
		String id = $V("id");
		Date startDate = DateUtil.parse(StartDate);
		Date endDate = DateUtil.parse(EndDate);
		ProductAnnouncementSet set = new ProductAnnouncementSet();
		ProductAnnouncementSchema schema = new ProductAnnouncementSchema();
		boolean flag = true;
		Transaction trans = new Transaction();

		schema = new ProductAnnouncementSchema();
		Date date = new Date();
		Info = "<li style='color:" + Color + "'>" + Info + "</li>";
		if (StartDate.compareTo(EndDate) >= 0) {
			Response.setStatus(0);
			Response.setMessage("结束时间必须大于开始时间！");
			return;
		}
		if (StringUtil.isEmpty(id)) {
			id = PubFun.getPubInfoID();
			schema.setCreateDate(date);
			schema.setCreateUser(User.getUserName());
			schema.setID(id);
			schema.setStartDate(startDate);
			schema.setEndDate(endDate);
			schema.setInfoOrder("1");
			schema.setViewFlag(ViewFlag);
			schema.setColor(Color);
			schema.setInfo(Info);
			set.add(schema);

		} else {
			schema.setID(id);
			if (schema.fill()) {
				boolean changeFlag = false;
				if (schema.getStartDate().compareTo(startDate) != 0) {
					schema.setStartDate(startDate);
					changeFlag = true;
				}
				if (schema.getEndDate().compareTo(endDate) != 0) {
					schema.setEndDate(endDate);
					changeFlag = true;
				}
				if (!schema.getViewFlag().equals(ViewFlag)) {
					schema.setViewFlag(ViewFlag);
					changeFlag = true;
				}
				if (!schema.getColor().equals(Color)) {
					schema.setColor(Color);
					changeFlag = true;
				}
				if (!schema.getInfo().equals(Info)) {
					schema.setInfo(Info);
					changeFlag = true;
				}
				if (changeFlag) {
					schema.setModifyDate(date);
					schema.setModifyUser(User.getUserName());
				}
				set.add(schema);
			}
		}

		if (set != null) {
			trans.add(set, Transaction.DELETE_AND_INSERT);
		}
		if (!trans.commit()) {
			flag = false;
		}
		if (flag) {
			Response.setStatus(1);
			Response.setMessage("保存成功!");
		} else {
			Response.setStatus(0);
			Response.setMessage("保存失败，操作数据库时发生错误!");
		}
	}

	/**
	 * 会员中心-查询公告
	 * 
	 * @param dga
	 */
	public void memberCenterInfodg1DataBind(DataGridAction dga) {
		String sql = "select id, InfoOrder, StartDate, EndDate, ViewFlag, "
				+ "Color, Info, CreateDate, CreateUser, ModifyDate, ModifyUser "
				+ "from ProductAnnouncement where 1=1 ";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.append(" AND (ProductID='' OR ProductID IS NULL)");
		qb.append(" AND (ComCode='' OR ComCode IS NULL)");
		qb.append(" order by InfoOrder ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(),
				dga.getPageIndex());
		if (dt != null && dt.getRowCount() > 0) {
			String info = "";
			String subIndex = "";
			for (int i = 0; i < dt.getRowCount(); i++) {
				info = dt.getString(i, "Info");
				if (StringUtil.isNotEmpty(info)) {
					subIndex = "<li style='color:" + dt.getString(i, "Color")
							+ "'>";
					if (info.startsWith(subIndex)) {
						info = info.substring(subIndex.length());
					}
					if (info.endsWith("</li>")) {
						info = info.substring(0, info.length() - 5);
					}
					dt.set(i, "Info", info);
				}
				if("1".equals(dt.getString(i, "ViewFlag"))){
					dt.set(i, "ViewFlag","是");
				}else{
					dt.set(i, "ViewFlag","否");
				}

			}
		}
		dga.bindData(dt);
	}
	
	public void memberCenterInfoDel(){
		String id = $V("id");
		if (!StringUtil.isEmpty(id)) {
			ProductAnnouncementSchema schema = new ProductAnnouncementSchema();
			schema.setID(id);
			if(schema.fill()){
				if(schema.delete()){
					Response.setStatus(1);
					Response.setMessage("删除成功!");
				}else{
					Response.setStatus(0);
					Response.setMessage("删除失败!");
				}
			}else{
				Response.setStatus(0);
				Response.setMessage("传入ID出错!");
			}
			
		}
	}
}
