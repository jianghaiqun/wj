package com.sinosoft.cms.dataservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.lowagie.tools.concat_pdf;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;

/** 
 * @ClassName: MemberMarketing
 * @Description: TODO(会员营销)
 * @author CongZN
 * @date 2014-12-23 下午02:23:29
 * 
 *       <p>
 *       修改历史
 *       </p>
 *       <p>
 *       序号 日期 修改人 修改原因
 *       </p>
 */
public class MemberMarketing extends Page {

	/**
	 * @Title: dg1DataBind.
	 * @Description: TODO(回购率查询).
	 * @param dga
	 *            void.
	 * @author CongZN.
	 */
	public static void dg1DataBind(DataGridAction dga) {

		Map<String, Info> mMap = new HashMap<String, Info>();
		Map<String, Info> iMap = new HashMap<String, Info>();

		String beginDate = dga.getParams().getString("BeginDate");
		String endDate = dga.getParams().getString("EndDate");
		String email = dga.getParams().getString("Email");
		String mobileNo = dga.getParams().getString("MobileNo");
		String queryStatus = dga.getParams().getString("QueryStatus");
		String fromWap = dga.getParams().getString("fromWap");

		int MemberNumber = 0;
		int MemberActive = 0;
		int MemberPassive = 0;

		StringBuffer queryMainSql = new StringBuffer("");
		StringBuffer querySql_conditions = new StringBuffer("");

		QueryBuilder query_main = new QueryBuilder("");
		QueryBuilder query_number = new QueryBuilder("");
		QueryBuilder query_cancellations = new QueryBuilder("");

		queryMainSql.append(" select");
		queryMainSql.append(" s1.memberid as MemberID,");
		queryMainSql.append(" sum(s1.totalAmount) as sTotalAmount,");
		queryMainSql.append(" sum(s1.payPrice) as sPayAmunt,");
		queryMainSql.append(" count(s1.ordersn) as cOrders,");
		queryMainSql.append(" m1.username as mUserName,");
		queryMainSql.append(" m1.sex as mSex,");
		queryMainSql.append(" m1.birthday as mBirthday,");
		queryMainSql.append(" m1.IDNO as mIDNO,");
		queryMainSql.append(" m1.address as mAddress,");
		queryMainSql.append(" m1.mobileNO as mMobileNO,");
		queryMainSql.append(" m1.email as mEmail,");
		queryMainSql.append(" m1.createDate as mCreateDate,");
		queryMainSql.append(" if (m1.fromWap like 'tb%', '淘宝', '主站') as fromName,");
		queryMainSql
				.append(" (case when m1.registerType in('0','1') then '主动' else '被动' end)  as mType");
		queryMainSql.append(" from sdorders s1,member m1 ");

		// 回购次数
		StringBuffer queryMainSql_Number = new StringBuffer("");
		queryMainSql_Number
				.append(" select z.memberid as MemberID,count(1) as zCount,");
		queryMainSql_Number.append(" '' as sTotalAmount,");
		queryMainSql_Number.append(" '' as sPayAmunt,");
		queryMainSql_Number.append(" '' as cOrders,");
		queryMainSql_Number.append(" '' as cPrice,");
		queryMainSql_Number.append(" '' as cNumber,");
		queryMainSql_Number.append(" '' as mUserName,");
		queryMainSql_Number.append(" '' as mSex,");
		queryMainSql_Number.append(" '' as mBirthday,");
		queryMainSql_Number.append(" '' as mIDNO,");
		queryMainSql_Number.append(" '' as mAddress,");
		queryMainSql_Number.append(" '' as mMobileNO,");
		queryMainSql_Number.append(" '' as mEmail,");
		queryMainSql_Number.append(" '' as mType,");
		queryMainSql_Number.append(" '' as mCreateDate,");
		queryMainSql_Number.append(" '' as fromName");
		queryMainSql_Number.append(" from (select s1.memberid");
		queryMainSql_Number.append(" from sdorders s1,member m1");

		// 撤单金额、数量
		StringBuffer queryMainSql_Cancellations = new StringBuffer("");
		queryMainSql_Cancellations.append(" select s1.memberid as memberId,");
		queryMainSql_Cancellations
				.append(" (select sum(s3.timePrem) from sdinformationrisktype s3 where s1.orderSn = s3.orderSn group by s3.orderSn) as cancellationsPrice,");
		queryMainSql_Cancellations
				.append(" (select count(s3.ordersn) from sdinformationrisktype s3 where s1.orderSn = s3.orderSn group by s3.orderSn) as cancellationsCount");
		queryMainSql_Cancellations.append(" from sdorders s1,member m1");

		querySql_conditions.append(" where s1.memberid=m1.id");
		querySql_conditions
				.append(" and left(s1.modifyDate,10) >=DATE_ADD(left(m1.createDate,10),INTERVAL 1 DAY)");
		
		//增量查询注册 底部显示
		StringBuffer query_memberSql = new StringBuffer();

		if (StringUtil.isNotEmpty(beginDate)) {
			querySql_conditions.append(" and left(s1.modifyDate,10) >= '"
					+ beginDate.trim() + "'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			querySql_conditions.append(" and left(s1.modifyDate,10) <= '"
					+ endDate.trim() + "'");
		}
		if (StringUtil.isNotEmpty(email)) {
			querySql_conditions.append(" and m1.email like '%" + email.trim()
					+ "%'");
		}
		if (StringUtil.isNotEmpty(mobileNo)) {
			querySql_conditions.append(" and m1.mobileNO like '%"
					+ mobileNo.trim() + "%'");
		}
		if (StringUtil.isNotEmpty(fromWap)) {
			// 来源淘宝
			if ("1".equals(fromWap)) {
				querySql_conditions.append(" and m1.fromWap like 'tb%'");
			} else if ("2".equals(fromWap)) {// 主站除淘宝外
				querySql_conditions.append(" and (m1.fromWap not like 'tb%' or m1.fromWap is null or m1.fromWap = '')");
			}
		}
		
		if ("2".equals(queryStatus) && (StringUtil.isNotEmpty(beginDate) && StringUtil
						.isNotEmpty(endDate))) {
			querySql_conditions.append(" and left(m1.createDate,10) >= '" + beginDate + "'");
			querySql_conditions.append(" and left(m1.createDate,10) <= '" + endDate + "'");
			
			query_memberSql.append(" select m1.registerType,count(m1.id) as rCount from member m1 where 1 = 1 ");
			query_memberSql.append(" and left(m1.createDate,10) >= '" + beginDate + "'");
			query_memberSql.append(" and left(m1.createDate,10) <= '" + endDate + "'");
			query_memberSql.append(" group by m1.registerType ");
			query_memberSql.append(" union ");
			query_memberSql.append(" select '4',count(m1.id) from member m1 where 1 = 1 ");
			query_memberSql.append(" and m1.registerType is not null ");
			query_memberSql.append(" and left(m1.createDate,10) >= '" + beginDate + "'");
			query_memberSql.append(" and left(m1.createDate,10) <= '" + endDate + "'");
		}else if("1".equals(queryStatus) && StringUtil.isNotEmpty(endDate)){
			query_memberSql.append(" select m1.registerType,count(m1.id) as rCount from member m1 where 1 = 1 ");
			query_memberSql.append(" and left(m1.createDate,10) <= '" + endDate + "'");
			query_memberSql.append(" group by m1.registerType ");
			query_memberSql.append(" union ");
			query_memberSql.append(" select '4',count(m1.id) from member m1 where 1 = 1 ");
			query_memberSql.append(" and m1.registerType is not null ");
			query_memberSql.append(" and left(m1.createDate,10) <= '" + endDate + "'");
		}else{
			query_memberSql.append(" select m1.registerType,count(m1.id) as rCount from member m1 where 1 = 1 ");
			query_memberSql.append(" group by m1.registerType ");
			query_memberSql.append(" union ");
			query_memberSql.append(" select '4',count(m1.id) from member m1 where 1 = 1 ");
			query_memberSql.append(" and m1.registerType is not null ");
		}

		queryMainSql.append(querySql_conditions);
		queryMainSql_Number.append(querySql_conditions);
		queryMainSql_Cancellations.append(querySql_conditions);

		queryMainSql.append(" and s1.orderStatus in('7','10','14')");
		queryMainSql_Number.append(" and s1.orderStatus in('7','10','14')");
		queryMainSql_Cancellations.append(" and s1.orderStatus in('9','10')");

		queryMainSql.append(" group by s1.memberId order by s1.memberId desc");
		queryMainSql_Number
				.append(" group by s1.memberid,left(s1.modifyDate,10)) z group by z.memberid order by zCount desc");
		queryMainSql_Cancellations.append(" group by memberId");

		query_main.setSQL(queryMainSql.toString());
		DataTable dt_query_main = query_main.executeDataTable();

		query_number.setSQL(queryMainSql_Number.toString());
		DataTable dt_query_number = query_number.executePagedDataTable(dga
				.getPageSize(), dga.getPageIndex());

		// 撤单封装
		query_cancellations.setSQL(queryMainSql_Cancellations.toString());
		DataTable dt_query_cancellations = query_cancellations
				.executeDataTable();
		for (int i = 0; i < dt_query_cancellations.getRowCount(); i++) {
			Info mm = new MemberMarketing().new Info();
			mm.setCancellationsPrice(dt_query_cancellations.getString(i,
					"cancellationsPrice"));
			mm.setCancellationsCount(dt_query_cancellations.getString(i,
					"cancellationsCount"));
			iMap.put(dt_query_cancellations.getString(i, "memberId"), mm);
		}

		for (int i = 0; i < dt_query_main.getRowCount(); i++) {
			Info mm = new MemberMarketing().new Info();
			
			String temp_sTotalAmount = dt_query_main.getString(i, "sTotalAmount");
			String temp_sPayAmunt = dt_query_main.getString(i, "sPayAmunt");
			
			if(StringUtil.isEmpty(temp_sTotalAmount) || StringUtil.isEmpty(temp_sPayAmunt)){
				continue;
			}
			
			BigDecimal sTotalAmount = new BigDecimal(temp_sTotalAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
			BigDecimal sPayAmunt = new BigDecimal(temp_sPayAmunt).setScale(2,BigDecimal.ROUND_HALF_UP);
			
			mm.setSumTotalAmount(String.valueOf(sTotalAmount));
			mm.setSumPayAmunt(String.valueOf(sPayAmunt.toString()));
			
			mm.setCountOrders(dt_query_main.getString(i, "cOrders"));
			mm.setUserName(dt_query_main.getString(i, "mUserName"));
			mm.setSex(dt_query_main.getString(i, "mSex"));
			mm.setBirthday(dt_query_main.getString(i, "mBirthday"));
			mm.setIDNO(dt_query_main.getString(i, "mIDNO"));
			mm.setAddress(dt_query_main.getString(i, "mAddress"));
			mm.setMobileNO(dt_query_main.getString(i, "mMobileNO"));
			mm.setEmail(dt_query_main.getString(i, "mEmail"));
			mm.setType(dt_query_main.getString(i, "mType"));
			mm.setCreateDate(dt_query_main.getString(i, "mCreateDate"));
			mm.setFromName(dt_query_main.getString(i, "fromName"));
			mMap.put(dt_query_main.getString(i, "MemberID"), mm);
		}

		for (int i = 0; i < dt_query_number.getRowCount(); i++) {
			String tempMemberId = dt_query_number.getString(i, "MemberID");
			Info info = mMap.get(tempMemberId);
			Info info_c = iMap.get(tempMemberId);
			if(info==null || info==null){
				continue;
			}
			dt_query_number.set(i, "sTotalAmount", info.getSumTotalAmount());
			dt_query_number.set(i, "sPayAmunt", info.getSumPayAmunt());
			dt_query_number.set(i, "cOrders", info.getCountOrders());

			if (info_c != null) {
				dt_query_number
						.set(i, "cPrice", info_c.getCancellationsPrice());
				dt_query_number.set(i, "cNumber", info_c
						.getCancellationsCount());
			} else {
				dt_query_number.set(i, "cPrice", "0");
				dt_query_number.set(i, "cNumber", "0");
			}

			dt_query_number.set(i, "mUserName", info.getUserName());
			dt_query_number.set(i, "mSex", info.getSex());
			dt_query_number.set(i, "mBirthday", info.getBirthday());
			dt_query_number.set(i, "mIDNO", info.getIDNO());
			dt_query_number.set(i, "mAddress", info.getAddress());
			dt_query_number.set(i, "mMobileNO", info.getMobileNO());
			dt_query_number.set(i, "mEmail", info.getEmail());
			dt_query_number.set(i, "mType", info.getType());
			dt_query_number.set(i, "mCreateDate", info.getCreateDate());
			dt_query_number.set(i, "fromName", info.getFromName());

		}
		
		dt_query_number.decodeColumn("mSex", HtmlUtil.codeToMapx("Sex"));
		
			
		if(dt_query_number.getRowCount() > 0){
			
			QueryBuilder query_member = new QueryBuilder(query_memberSql.toString());
			DataTable dt = query_member.executeDataTable();
			
			dt_query_number.insertColumn("memberNumber", MemberNumber);
			dt_query_number.insertColumn("memberActive", MemberActive);
			dt_query_number.insertColumn("memberActive1", MemberActive);
			dt_query_number.insertColumn("memberPassive", MemberPassive);
			
			for (int i = 0; i < dt.getRowCount(); i++) {
				if ("0".equals(dt.getString(i, "registerType"))) {
					dt_query_number.set(0, "MemberActive",  dt.getString(i, "rCount"));
				} else if ("1".equals(dt.getString(i, "registerType"))) {
					dt_query_number.set(0, "MemberActive1",  dt.getString(i, "rCount"));
				} else if ("2".equals(dt.getString(i, "registerType"))) {
					dt_query_number.set(0, "MemberPassive", dt.getString(i, "rCount"));
				} else if ("4".equals(dt.getString(i, "registerType"))) {
					dt_query_number.set(0, "MemberNumber", dt.getString(i, "rCount"));
				}
			}
		}

		dga.setTotal(query_number);
		dga.bindData(dt_query_number);
	}

	/**
	 * @Title: detail.
	 * @Description: TODO(明细).
	 * @param dga
	 *            void.
	 * @author CongZN.
	 */
	public static void detail(DataGridAction dga) {
		String memberID = dga.getParams().getString("MemberID");
		String beginDate = dga.getParams().getString("BeginDate");
		String endDate = dga.getParams().getString("EndDate");

		QueryBuilder query_main = new QueryBuilder("");
		StringBuffer queryMainSql = new StringBuffer("");
		queryMainSql.append(" select m1.UserName,");
		queryMainSql.append(" s1.OrderSn,");
		queryMainSql.append(" (select productName from sdinformation  s2 where s2.ordersn = s1.ordersn) as ProductName,");
		queryMainSql.append(" s1.totalAmount,");
		queryMainSql.append(" s1.payPrice,");
		queryMainSql.append(" s1.orderStatus,");
		queryMainSql.append(" (select ModifyDate from tradeinformation t1 where t1.ordid = s1.ordersn) as tModifyDate");
		queryMainSql.append(" from sdorders s1,member m1");
		queryMainSql.append(" where s1.memberid=m1.id");
		queryMainSql
				.append(" and left(s1.modifyDate,10) >=DATE_ADD(left(m1.createDate,10),INTERVAL 1 DAY)");

		if (StringUtil.isNotEmpty(beginDate)) {
			queryMainSql.append(" and left(s1.modifyDate,10) >= '"
					+ beginDate.trim() + "'");
		}
		if (StringUtil.isNotEmpty(endDate)) {
			queryMainSql.append(" and left(s1.modifyDate,10) <= '"
					+ endDate.trim() + "'");
		}

		queryMainSql
				.append(" and s1.orderStatus in('7','9','10','13','14') and s1.memberid = '"
						+ memberID + "'");
		queryMainSql.append(" order by s1.orderStatus");

		query_main.setSQL(queryMainSql.toString());
		DataTable dt_query_main = query_main.executePagedDataTable(dga
				.getPageSize(), dga.getPageIndex());
		dt_query_main.decodeColumn("orderStatus", HtmlUtil
				.codeToMapx("orderstatus"));
		dga.setTotal(query_main);
		dga.bindData(dt_query_main);
	}

	/**
	 * @Title: initMember.
	 * @Description: TODO(会员数统计.).
	 * @param params
	 * @return Mapx.
	 * @author CongZN.
	 */
	@SuppressWarnings("unchecked")
	public static Mapx initMember(Mapx params) {

		QueryBuilder query_member = new QueryBuilder(
				"select registerType,count(id) as rCount from member group by registerType union "
						+ "select '4',count(id) from member where registerType is not null ");
		DataTable dt = query_member.executeDataTable();

		for (int i = 0; i < dt.getRowCount(); i++) {

			if ("0".equals(dt.getString(i, "registerType"))) {
				params.put("result_zMember", dt.getString(i, "rCount"));
			} else if ("1".equals(dt.getString(i, "registerType"))) {
				params.put("result_z1Member", dt.getString(i, "rCount"));
			} else if ("2".equals(dt.getString(i, "registerType"))) {
				params.put("result_bMember", dt.getString(i, "rCount"));
			} else if ("4".equals(dt.getString(i, "registerType"))) {
				params.put("result_qMember", dt.getString(i, "rCount"));
			}
		}

		return params;
	}

	/**
	 * @ClassName: Info
	 * @Description: TODO(属性)
	 * @author CongZN
	 * @date 2014-12-1 下午02:22:54
	 */
	protected class Info {

		// 订单总金额
		private String SumTotalAmount;
		// 总支付金额
		private String SumPayAmunt;
		// 回购订单数
		private String CountOrders;
		// 会员名称
		private String UserName;
		// 性别
		private String Sex;
		// 生日
		private String Birthday;
		// 身份证
		private String IDNO;
		// 地址
		private String Address;
		// 手机号码
		private String MobileNO;
		// 邮箱
		private String Email;
		// 注册时间
		private String CreateDate;
		// 会员类型 1 2 主动 2 被动
		private String Type;
		// 撤单金额
		private String CancellationsPrice;
		// 查单数量
		private String CancellationsCount;
		// 会员来源
		private String fromName;

		public String getSumTotalAmount() {
			return SumTotalAmount;
		}

		public void setSumTotalAmount(String sumTotalAmount) {
			SumTotalAmount = sumTotalAmount;
		}

		public String getSumPayAmunt() {
			return SumPayAmunt;
		}

		public void setSumPayAmunt(String sumPayAmunt) {
			SumPayAmunt = sumPayAmunt;
		}

		public String getCountOrders() {
			return CountOrders;
		}

		public void setCountOrders(String countOrders) {
			CountOrders = countOrders;
		}

		public String getUserName() {
			return UserName;
		}

		public void setUserName(String userName) {
			UserName = userName;
		}

		public String getSex() {
			return Sex;
		}

		public void setSex(String sex) {
			Sex = sex;
		}

		public String getBirthday() {
			return Birthday;
		}

		public void setBirthday(String birthday) {
			Birthday = birthday;
		}

		public String getIDNO() {
			return IDNO;
		}

		public void setIDNO(String iDNO) {
			IDNO = iDNO;
		}

		public String getAddress() {
			return Address;
		}

		public void setAddress(String address) {
			Address = address;
		}

		public String getEmail() {
			return Email;
		}

		public void setEmail(String email) {
			Email = email;
		}

		public String getCreateDate() {
			return CreateDate;
		}

		public void setCreateDate(String createDate) {
			CreateDate = createDate;
		}

		public String getType() {
			return Type;
		}

		public void setType(String type) {
			Type = type;
		}

		public String getMobileNO() {
			return MobileNO;
		}

		public void setMobileNO(String mobileNO) {
			MobileNO = mobileNO;
		}

		public String getCancellationsPrice() {
			return CancellationsPrice;
		}

		public void setCancellationsPrice(String cancellationsPrice) {
			CancellationsPrice = cancellationsPrice;
		}

		public String getCancellationsCount() {
			return CancellationsCount;
		}

		public void setCancellationsCount(String cancellationsCount) {
			CancellationsCount = cancellationsCount;
		}

		public String getFromName() {
			return fromName;
		}

		public void setFromName(String fromName) {
			this.fromName = fromName;
		}
		
	}

}