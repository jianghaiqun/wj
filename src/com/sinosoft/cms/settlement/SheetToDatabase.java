package com.sinosoft.cms.settlement;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFComment;

import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.schema.financialsettlementdetailsSchema;
import com.sinosoft.schema.financialsettlementdetailsSet;

/**
 *
 * Created by dongsheng on 2017/6/2.
 */
public class SheetToDatabase implements FullSheetContentHandler {

	private int minColumns;
	private int firstRow;
	private financialsettlementdetailsSchema defaultSchema;
	private int currentRow = -1;
	private int currentCol = -1;
	private Transaction transaction;
	private financialsettlementdetailsSchema schema;
	private financialsettlementdetailsSet set;
	private List<CoverageBean> cxCoverages;
	private List<CoverageBean> rsxCoverages;
	private List<String> appStatus;
	private List<String> type;
	private Mapx channelType;
	// private final static Map<String, String> branchs;
	private String errorMsg;

	public String getErrorMsg() {

		return errorMsg;
	}

	public SheetToDatabase setErrorMsg(String errorMsg) {

		this.errorMsg = errorMsg;
		return this;
	}

	public SheetToDatabase() {

		minColumns = 0;
		appStatus = new ArrayList<String>();
		appStatus.add("承保");
		appStatus.add("撤单");
		appStatus.add("退保");
		appStatus.add("续保");
		appStatus.add("退保(保险公司)");
		appStatus.add("承保(保险公司)");
		type = new ArrayList<String>();
		type.add("人身险");
		type.add("产险");
		channelType = HtmlUtil.codeToMapx("ChannelType");
	}

	public SheetToDatabase(int firstRow, financialsettlementdetailsSchema defaultSchema) {

		this();
		this.firstRow = firstRow;
		this.defaultSchema = defaultSchema;

		cxCoverages = new ArrayList<CoverageBean>();
		QueryBuilder qb = new QueryBuilder(new StringBuilder("SELECT")
				.append("	p.`name` pname,")
				.append("	p.`innercode` pcode,")
				.append("	c.`name` `name`,")
				.append("	c.`innercode` `code`")
				.append("   FROM")
				.append("	datainfo p,")
				.append("	datainfo c")
				.append("   WHERE")
				.append("	p.InnerCode = c.ParentInnerCode")
				.append("   AND p.Type=?")
				.append("   UNION")
				.append("	SELECT")
				.append("		`name` pname,")
				.append("		`innercode` pcode,")
				.append("		`name` `name`,")
				.append("		`innercode` `code`")
				.append("	FROM")
				.append("		datainfo")
				.append("	WHERE")
				.append("		prop2 = '1'")
				.append("       AND Type=?")
				.toString());
		qb.add("coverage_cx");
		qb.add("coverage_cx");
		DataTable rows = qb.executeDataTable();
		for (DataRow row : rows) {
			cxCoverages.add(new CoverageBean(row.getString(0), row.getString(1), row.getString(2), row.getString(3)));
		}
		rsxCoverages = new ArrayList<CoverageBean>();
		qb.set(0, "coverage_rsx");
		qb.set(1, "coverage_rsx");
		rows = qb.executeDataTable();
		for (DataRow row : rows) {
			rsxCoverages.add(new CoverageBean(row.getString(0), row.getString(1), row.getString(2), row.getString(3)));
		}
	}

	private boolean isReadableRow(int rowNum) {

		return rowNum >= firstRow - 1;
	}

	private boolean checkDefaultSchema() {

		return !StringUtils.isAnyEmpty(defaultSchema.getbranchcode(),
				defaultSchema.getbatchnumber(),
				defaultSchema.getcreateduser())
				&& defaultSchema.getinvoicedate() != null
				&& defaultSchema.getcreateddatetime() != null
				&& defaultSchema.getmodifieddatetime() != null;
	}

	@Override
	public void startRow(int rowNum) {

		// System.out.println("currentRowNum--start:" + rowNum);
		// Prepare for this row
		currentRow = rowNum;
		currentCol = -1;
		if (isReadableRow(rowNum)) {
			this.schema = new financialsettlementdetailsSchema();
			this.schema.setbranchcode(defaultSchema.getbranchcode());
			this.schema.setinvoicedate(defaultSchema.getinvoicedate());
			this.schema.setbatchnumber(defaultSchema.getbatchnumber());
			this.schema.setcreateddatetime(defaultSchema.getcreateddatetime());
			this.schema.setmodifieddatetime(defaultSchema.getmodifieddatetime());
			this.schema.setcreateduser(defaultSchema.getcreateduser());
		}
	}

	@Override
	public void endRow(int rowNum) {

		// System.out.println("currentRowNum--end:" + rowNum);
		if (!isReadableRow(rowNum))
			return;
		if (StringUtil.isEmpty(schema.getpolicyno())) {
			setErrorMsg("保单号码不能为空");
		}
		if (StringUtil.isEmpty(schema.getinsurancetype())) {
			setErrorMsg("保监险种类别不能为空");

		}
		if (StringUtil.isEmpty(schema.getinsurancesubtype())) {
			this.schema.setinsurancesubtype(schema.getinsurancetype());
		}
		if (!channelType.containsValue(schema.getchanneltype())) {
			setErrorMsg("渠道归属不是一个有效的值");
		}
		if (!appStatus.contains(schema.getappstatus())) {
			setErrorMsg("保单状态不是一个有效的值");
		}
		if (StringUtil.isEmpty(schema.getpremium1st())) {
			this.schema.setpremium1st(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getpremium2nd())) {
			this.schema.setpremium2nd(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getfee1st())) {
			this.schema.setfee1st(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getfee2nd())) {
			this.schema.setfee2nd(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getfeeratio1st())) {
			this.schema.setfeeratio1st(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getfeeratio2nd())) {
			this.schema.setfeeratio2nd(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getcoupondiscount())) {
			this.schema.setcoupondiscount(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getactivitydiscount())) {
			this.schema.setactivitydiscount(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getpointdiscount())) {
			this.schema.setpointdiscount(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.gettotaldiscount())) {
			this.schema.settotaldiscount(new BigDecimal(0));
		}
		if (StringUtil.isEmpty(schema.getinsurancecompanyname())) {
			setErrorMsg("保险公司名称不能为空");
		}
		if (!type.contains(schema.getinsurancecompanytype())) {
			setErrorMsg("保险公司性质不是一个合法的值");
		}

		if (type.get(0).equals(schema.getinsurancecompanytype())) {
			int i = rsxCoverages.indexOf(new CoverageBean(schema.getinsurancetype(), schema.getinsurancesubtype()));
			if (i >= 0) {
				CoverageBean bean = rsxCoverages.get(i);
				schema.setinsurancetype(bean.pCode);
				schema.setinsurancesubtype(bean.code);
			} else {
				setErrorMsg("无法找到人身险对应的保监险种类型。");
			}
		} else if (type.get(1).equals(schema.getinsurancecompanytype())) {
			int i = cxCoverages.indexOf(new CoverageBean(schema.getinsurancetype(), schema.getinsurancesubtype()));
			if (i >= 0) {
				CoverageBean bean = cxCoverages.get(i);
				schema.setinsurancetype(bean.pCode);
				schema.setinsurancesubtype(bean.code);
			} else {
				setErrorMsg("无法找到产险对应的保监险种类型。");
			}
		}

		if (StringUtils.isNotEmpty(getErrorMsg())) {
			throw new RuntimeException("当前操作第" + (rowNum + 1) + "行出现错误，错误：" + getErrorMsg());
		}

		if (set.size() == 10000) {
			transaction.add(set, Transaction.INSERT);
			if (!transaction.commitRoll(false)) {
				throw new RuntimeException("数据保存错误：" + transaction.getExceptionMessage());
			}
			set = new financialsettlementdetailsSet();
		}

		set.add(schema);
	}

	@Override
	public void cell(String cellReference, String formattedValue,
			XSSFComment comment) {

		if (!isReadableRow(currentRow)) {
			return;
		}
		if (cellReference == null) {
			cellReference = new CellAddress(currentRow, currentCol).formatAsString();
		}
		CellReference reference = new CellReference(cellReference);
		// System.out.println("当前单元格：" + reference.formatAsString());
		int thisCol = reference.getCol();
		currentCol = thisCol;
		formattedValue = formattedValue.trim();
		switch (thisCol) {
		case 0:
			this.schema.setpolicyno(formattedValue);
			break;
		case 1:
			this.schema.setinsurancetype(formattedValue);
			break;
		case 2:
			this.schema.setinsurancesubtype(formattedValue);
			break;
		case 3:
			this.schema.setchannel(formattedValue);
			break;
		case 4:
			this.schema.setsubchannel(formattedValue);
			break;
		case 5:
			this.schema.setchanneltype(formattedValue);
			break;
		case 6:
			// 生效时间起期止期,承保时间.三个时间可空.但是数据必须是有效的时间型.
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					Date date = parseDate(formattedValue);
					this.schema.setsvalidate(date);
				}
			} catch (ParseException e) {
				setErrorMsg("生效时间起期不是一个日期");
			}
			break;
		case 7:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					Date date = parseDate(formattedValue);
					this.schema.setevalidate(date);
				}
			} catch (ParseException e) {
				setErrorMsg("生效时间止期不是一个日期");
			}
			break;
		case 8:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					Date date = parseDate(formattedValue);
					this.schema.setinsuredate(date);
				}
			} catch (ParseException e) {
				setErrorMsg("承保日期不是一个日期");
			}
			break;

		case 9:
			this.schema.setappstatus(formattedValue);
			break;

		case 10:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setpremium1st(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("新单保费有误，不是一个数字");
			}
			break;
		case 11:

			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setpremium2nd(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("续期保费有误，不是一个数字");
			}
			break;
		case 12:

			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setfeeratio1st(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("新单手续费比率有误，不是一个数字");
			}
			break;
		case 13:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setfeeratio2nd(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("续期手续费比率有误，不是一个数字");
			}
			break;
		case 14:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setfee1st(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("新单手续费有误，不是一个数字");
			}
			break;
		case 15:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setfee2nd(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("续期手续费有误，不是一个数字");
			}
			break;
		case 16:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setcoupondiscount(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("保单优惠券有误，不是一个数字");
			}
			break;
		case 17:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setactivitydiscount(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("保单活动优惠有误，不是一个数字");
			}
			break;
		case 18:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.setpointdiscount(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("保单积分抵值有误，不是一个数字");
			}
			break;
		case 19:
			try {
				if (StringUtils.isNotEmpty(formattedValue)) {
					this.schema.settotaldiscount(new BigDecimal(formattedValue));
				}
			} catch (Exception e) {
				setErrorMsg("总优惠有误，不是一个数字");
			}
			break;
		case 20:
			this.schema.setpremiumbatchnumber(formattedValue);
			break;
		case 21:
			this.schema.setfeebatchnumber(formattedValue);
			break;
		case 22:
			this.schema.setinsurancecompanyname(formattedValue);
			break;
		case 23:
			this.schema.setinsurancecompanytype(formattedValue);
			break;
		}
	}

	@Override
	public void headerFooter(String text, boolean isHeader, String tagName) {

		// Skip, no headers or footers in CSV


	}

	@Override
	public void beginning() throws Exception {

		if (!checkDefaultSchema()) {
			throw new Exception("参数错误。默认参数不全或有误");
		}

		transaction = new Transaction();
		set = new financialsettlementdetailsSet(1000, 1000);
	}

	@Override
	public void ending() throws Exception {

		transaction.add(set, Transaction.INSERT);
		if (!transaction.commitRoll(false)) {
			throw new RuntimeException("数据保存错误：" + transaction.getExceptionMessage());
		}
	}

	private static class CoverageBean {

		String pName;
		String pCode;
		String name;
		String code;

		CoverageBean(String pName, String name) {

			this.pName = pName;
			this.name = name;
		}

		CoverageBean(String pName, String pCode, String name, String code) {

			this.pName = pName;
			this.pCode = pCode;
			this.name = name;
			this.code = code;
		}

		@Override
		public boolean equals(Object o) {

			if (this == o)
				return true;

			if (o == null || getClass() != o.getClass())
				return false;

			CoverageBean that = (CoverageBean) o;

			return new EqualsBuilder()
					.append(pName, that.pName)
					.append(name, that.name)
					.isEquals();
		}

		@Override
		public int hashCode() {

			return new HashCodeBuilder(17, 37)
					.append(pName)
					.append(name)
					.toHashCode();
		}
	}

	private Date parseDate(String dateStr) throws ParseException {

		return DateUtils.parseDate(dateStr, "yyyy-MM-dd", "yyyyMMdd", "yyyy-M-d", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd",
				"yyyy/M/d");
	}
}
