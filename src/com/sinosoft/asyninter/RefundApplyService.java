/**
 * Project Name:wj
 * File Name:InsureApplyDocumentService.java
 * Package Name:com.sinosoft.asyninter
 * Date:2016年9月8日下午3:14:07
 * Copyright (c) 2016, www.kaixinbao.com All Rights Reserved.
 *
 */

package com.sinosoft.asyninter;

import cn.com.sinosoft.service.impl.BaseServiceImpl;
import cn.com.sinosoft.util.CommonUtil;
import com.sinosoft.aeonlife.utils.SftpCommon;
import com.sinosoft.aeonlife.utils.ZipUtils;
import com.sinosoft.framework.data.DataRow;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.schema.PartnerInfoSchema;
import com.sinosoft.schema.PartnerInfoSet;
import com.sinosoft.schema.PartnerRefundApplySchema;
import com.sinosoft.schema.SDOrdersSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ClassName:RefundApplyService <br/>
 * Function: 处理合作方退保申请文件. <br/>
 * Date:2016年9月8日 下午3:14:07 <br/>
 *
 * @author:liuhognyu
 */
public class RefundApplyService extends BaseServiceImpl<SDOrdersSet, String> {

	private final static String CLASS_NAME = RefundApplyService.class.getName();

	private static final Logger logger = LoggerFactory.getLogger(RefundApplyService.class);
	public void execute() {

		RefundApplyService service = new RefundApplyService();
		try {
			service.getRefunApplyFromPartner();
		} catch (IOException e) {
			logger.error("从合作方获取退保申请文件异常");
		}
	}
	public static void main(String[] args) {

		RefundApplyService service = new RefundApplyService();
		try {
			service.getRefunApplyFromPartner();
		} catch (IOException e) {
			logger.error("从合作方获取退保申请文件异常" + e.getMessage(), e);
		}
	}

	/**
	 * getInsureResultFromAeonlife:从合作方获取退保申请文件. <br/>
	 *
	 * @author liuhongyu
	 * @throws IOException
	 */
	private void getRefunApplyFromPartner() throws IOException {

		PartnerInfoSet set = getPartnerInfoSet();
		// upZipFiles()方法的参数是文件所在的目录.注意结尾须是"/".
		if (set == null) {
			logger.error("数据错误,没找到合作方!");
			return;
		}
		for (int i = 0; i < set.size(); i++) {
			PartnerInfoSchema info = set.get(i);
			String path = info.getFtpPath() + cfp(SftpCommon.DOWNLOAD, SftpCommon.PATH_REFUNDAPPLY);
			String newpath = info.getFtpPath() + cfp(SftpCommon.DOWNLOAD_BAK_WJ, SftpCommon.PATH_REFUNDAPPLY);
			try {
				ZipUtils.unZipFilesToNewPlace(path, newpath);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				ActionUtil.sendAlarmMail("理财险解压退保申请异常，需要重新处理请重视，" + e.getMessage());
			}
			// 遍历每个合作方对应的文件中的csv文件.
			// loadCSV()方法的参数是文件所在的目录.而不是精确到某个文件.
			List<List<String>> rows = CSVUtils.readCSV(newpath);
			if (rows == null) {
				logger.error("{}文件没有内容!", info.getFtpPath());
				continue;
			}
			// 一个供应商一个处理
			for (List<String> row : rows) {

				Transaction transaction = new Transaction();
				// 创建合作方退保申请
				PartnerRefundApplySchema ras = createPartnerPolicyReq(row, info);

				if (null != ras) {
					transaction.add(ras, Transaction.DELETE_AND_INSERT);
					// 退保 资产表状态设置为2 腿保中
					if (!"0".equals(ras.getCancelResult())) {
						String updateSql = createUpdateFinancingInfo(row, info.getchannelSn());
						QueryBuilder qb = new QueryBuilder(updateSql);
						transaction.add(qb);
					}
					if (!transaction.commit()) {
						logger.error("保存退保数据错误，投保单号：{}", row.get(0));
					}
				}

			}
		}

	}

	/**
	 * getPartnerInfoSet:获取合作方. <br/>
	 * 
	 * @author liuhongyu
	 * @param loadType
	 * @param dateStr
	 * @param strType
	 * @return
	 */
	protected PartnerInfoSet getPartnerInfoSet() {

		String sql = "where type='asyn'";
		QueryBuilder queryBuilder = new QueryBuilder(sql);
		PartnerInfoSchema parterInfo = new PartnerInfoSchema();
		PartnerInfoSet set = parterInfo.query(queryBuilder);
		return set;
	}

	/**
	 * createPartnerPolicyReq:创建合作方退保申请. <br/>
	 *
	 * @author liuhongyu
	 * @param row
	 * @param partnerInfo
	 * @return
	 */
	private PartnerRefundApplySchema createPartnerPolicyReq(List<String> row, PartnerInfoSchema partnerInfo) {

		String pOrderSn = row.get(0);
		String serialNo = row.get(1);
		String riskCode = row.get(2);
		String applyType = row.get(3);
		String applyDate = row.get(4);
		// 数据校验
		// 1：成功 0：失败
		String result = "1";
		// 失败原因
		String msg = "";

		PartnerRefundApplySchema refundApplySchema = null;

		if (StringUtils.isEmpty(pOrderSn) || pOrderSn.length() > 32) {
			logger.info("投保订单号不合法{}", pOrderSn);
			msg += "投保订单号不合法,";
			result = "0";
		}
		if (serialNo.length() > 32) {
			logger.info("退保申请流水号不合法{}",serialNo);
			msg += "退保申请流水号不合法,";
			result = "0";
		}
		if (!"1".equals(applyType) && !"2".equals(applyType)) {
			logger.info("申请类型不合法{}",applyType);
			msg += "申请类型不合法,";
			result = "0";
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		try {
			format.setLenient(false);
			format.parse(applyDate);
		} catch (ParseException e) {
			logger.error("申请时间不合法" + applyDate + e.getMessage(), e);
			msg += "申请时间不合法,";
			result = "0";
		}
		// 修改
		Date lastDate = DateUtil.addDay(new Date(), -1);
		String lastDateStr = DateUtil.toString(lastDate);
		String applyDateStr = DateUtil.toString(DateUtil.parse(row.get(4), "yyyyMMdd HH:mm:ss"), "yyyy-MM-dd");

		if (!lastDateStr.equals(applyDateStr)) {
			msg += "申请时间错误,不是昨日退保,";
			result = "0";
		}

		QueryBuilder qb = new QueryBuilder(
				"SELECT f.* FROM FinancingInfo f, sdorders o WHERE f.ordersn = o.ordersn and o.paySn = ?", pOrderSn);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() <= 0) {
			msg += "未承保，请确认投保结果,";
			result = "0";
			refundApplySchema = new PartnerRefundApplySchema();
			// 保存到临时表中.
			refundApplySchema.setid(CommonUtil.getUUID());
			refundApplySchema.setPOrderSn(pOrderSn);
			refundApplySchema.setSerialNo(serialNo);
			refundApplySchema.setRiskCode(riskCode);
			refundApplySchema.setApplyType(applyType);
			refundApplySchema.setApplyDate(DateUtil.parse(applyDate, "yyyyMMdd HH:mm:ss"));
			refundApplySchema.setCancelResult(result);
			refundApplySchema.setCancelFailReason(msg);
			refundApplySchema.setCreateDate(DateUtil.parse(format.format(new Date()), "yyyyMMdd HH:mm:ss"));
			refundApplySchema.setProp1(partnerInfo.getchannelSn());
			logger.info("未承保数据：生成对象成功：{}", pOrderSn);
		} else {
			DataRow datarow = dt.get(0);
			String insStatus = datarow.getString("InsStatus");
			// 修改
			if ("3".equals(insStatus)) {
				msg += "已退保，请不要重复提交退保,";
				result = "0";
			}
			// 退保中 不存
			if ("2".equals(insStatus)) {
				logger.info("订单已存在：{}", pOrderSn);
			} else {
				refundApplySchema = new PartnerRefundApplySchema();
				// 保存到临时表中.
				refundApplySchema.setid(CommonUtil.getUUID());
				refundApplySchema.setPOrderSn(pOrderSn);
				refundApplySchema.setSerialNo(serialNo);
				refundApplySchema.setRiskCode(riskCode);
				refundApplySchema.setApplyType(applyType);
				refundApplySchema.setApplyDate(DateUtil.parse(applyDate, "yyyyMMdd HH:mm:ss"));
				refundApplySchema.setCancelResult(result);
				refundApplySchema.setCancelFailReason(msg);
				refundApplySchema.setCreateDate(DateUtil.parse(format.format(new Date()), "yyyyMMdd HH:mm:ss"));
				refundApplySchema.setProp1(partnerInfo.getchannelSn());
				logger.info("退保申请：生成对象成功：{}", pOrderSn);
			}
		}
		return refundApplySchema;

	}

	/**
	 * createUpdateFinancingInfo:创建变更资产信息语句. <br/>
	 *
	 * @author liuhongyu
	 * @param row
	 * @param channelsn
	 * @return
	 */
	private String createUpdateFinancingInfo(List<String> row, String channelsn) {

		String cancelDate = DateUtil.toString(DateUtil.parse(row.get(4), "yyyyMMdd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append("	UPDATE 	 financinginfo 	");
		sb.append("	SET	  insstatus = 2, cancelDate = '").append(cancelDate).append("',ModifyDate = sysdate()");
		sb.append("	WHERE 	");
		sb.append("	  ordersn = ");
		sb.append("	  (SELECT ordersn  FROM	sdorders 	");
		sb.append("	  WHERE channelsn = '" + channelsn + "'");
		sb.append("	  AND   paysn = '" + row.get(0) + "' )	");

		return sb.toString();
	}

	/**
	 * cfp:获取文件路径. <br/>
	 *
	 * @author liuhongyu
	 * @param root
	 * @param buis
	 * @return
	 */
	private static String cfp(String root, String buis) {

		StringBuffer sb = new StringBuffer();
		sb.append(root);
		sb.append(DateUtil.getCurrentDate(SftpCommon.FORMAT_YMD));
		sb.append(buis);
		return sb.toString();
	}
}
