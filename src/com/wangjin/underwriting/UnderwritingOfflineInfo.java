package com.wangjin.underwriting;

import cn.com.sinosoft.common.email.MessageConstant;
import cn.com.sinosoft.service.PayBaseService;
import cn.com.sinosoft.service.impl.PayBaseServiceImpl;
import com.sinosoft.aeonlife.utils.ZipUtils;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.Page;
import com.sinosoft.framework.User;
import com.sinosoft.framework.controls.DataGridAction;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.HtmlUtil;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.schema.UnderwritingOfflineInfoSchema;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import org.apache.commons.lang.ArrayUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 线下核保信息管理
 * @author guozc
 * @date 2017-06-14
 */
public class UnderwritingOfflineInfo extends Page {

	public static void dg1DataBind(DataGridAction dga) {
		// 产品名称
		String name = dga.getParam("name");
		// 手机号
		String mobile = dga.getParam("mobile");
		// 邮箱
		String email = dga.getParam("email");
		// 身份证号
		String applicantIdentityId = dga.getParam("applicantIdentityId");
		// 产品名称
		String productName = dga.getParam("productName");
		// 处理状态
		String dealStatus = dga.getParam("dealStatus");
		// 申请起始时间
		String createStartDate = dga.getParam("createStartDate");
		// 申请结束时间
		String createEndDate = dga.getParam("createEndDate");
		// 渠道选择
		String channelsn = dga.getParam("contant");
		
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.id,a.product_id as productId,a.product_name as productName,a.name,a.mobile,a.email,a.situation_desc as situationDesc,");
		listSql.append(" a.remark,(select codeName from zdcode where codetype='OfflineUW.DealStatus' and codevalue=a.deal_status) as dealStatus,a.deal_status,");
		listSql.append(" (select codeName from zdcode where codetype='OfflineUW.UnderWritingStatus' and codevalue=a.underwriting_status) as underwritingStatus,");
		listSql.append(" date_format(a.finish_time,'%Y-%m-%d') as finishTime,date_format(a.create_time,'%Y-%m-%d') as createTime,");
		listSql.append(" date_format(a.modify_time,'%Y-%m-%d') as modifyTime ,(SELECT clo.channelname FROM channelinfo clo  WHERE clo.channelcode=a.prop1)  as channelsn,uoh.idno  ");
		listSql.append(" ,( SELECT GROUP_CONCAT(remark) FROM sdremark WHERE prop1= CONCAT('offline_',a.id) ORDER BY OperateTime desc )   AS records ");
		listSql.append(" ,uoh.mainSymptoms, uoh.diseaseName");
		listSql.append(" from underwriting_offline_info a LEFT JOIN underwriting_offline_healthinfo uoh ON uoh.info_id=a.id  where 1=1 and a.prop1 != 'b2b_app' ");
		QueryBuilder qb = new QueryBuilder(listSql.toString());
		if (StringUtil.isNotEmpty(name)) {
			qb.append(" and a.name like '" + name + "%' ");
		}
		if (StringUtil.isNotEmpty(mobile)) {
			qb.append(" and a.mobile = ? ", mobile);
		}
		if (StringUtil.isNotEmpty(email)) {
			qb.append(" and a.email = ? ", email);
		}
		if (StringUtil.isNotEmpty(productName)) {
			qb.append(" and a.product_name like '%" + productName + "%' ");
		}
		if (StringUtil.isNotEmpty(dealStatus)) {
			qb.append(" and a.deal_status = ? ", dealStatus);
		}
		if (StringUtil.isNotEmpty(createStartDate)) {
			qb.append(" and a.create_time >= ? ", createStartDate + " 00:00:00");
		}
		if (StringUtil.isNotEmpty(createEndDate)) {
			qb.append(" and a.create_time <= ? ", createEndDate + " 23:59:59");
		}
		if (StringUtil.isNotEmpty(channelsn)) {
			channelsn = "'" + channelsn.replace(",","','") + "'";
			qb.append(" and a.prop1 in ("+channelsn+")");
		}
		if (StringUtil.isNotEmpty(applicantIdentityId)) {
			qb.append(" and uoh.idno = ? ",applicantIdentityId);
		}
		qb.append(" order by create_time desc ");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
		dga.bindData(dt);
	}

	public static Mapx<String, Object> init(Mapx<String, Object> params) {
		params.put("dealStatus", HtmlUtil.codeToOptions("OfflineUW.DealStatus", true));
		return params;
	}
	
	/**
	 * 审核处理页面初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx<String, Object> initDialog(Mapx<String, Object> params) {
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.product_name,a.name,a.mobile,a.email,a.situation_desc as situationDesc,a.remark,");
		listSql.append(" a.insure_email as companyEmail from underwriting_offline_info a where a.id = ? ");
		QueryBuilder qb = new QueryBuilder(listSql.toString(), params.getString("infoID"));
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			params.put("productName", dt.getString(0, "product_name"));
			params.put("name", dt.getString(0, "name"));
			params.put("mobile", dt.getString(0, "mobile"));
			params.put("email", dt.getString(0, "email"));
			params.put("situationDesc", dt.getString(0, "situationDesc"));
			params.put("remark", dt.getString(0, "remark"));
			params.put("companyEmail", dt.getString(0, "companyEmail"));
		}
		return params;
	}
	/**
	 * 审核处理页面初始化(百年康惠保)
	 *
	 * @param params
	 * @return
	 */
	public static Mapx<String, Object> initDialogForBaiNian(Mapx<String, Object> params) {
		StringBuffer listSql = new StringBuffer();
		listSql.append("SELECT a.product_name AS productName,a.name,b.info_id as infoId,b.sex,b.age,b.height,b.weight,b.IdType,b.IdNo,b.birthday,");
		listSql.append("b.firsOnsetTime,b.mainSymptoms,b.diseaseName,b.attackDate,b.attackFrequency,b.attackLastDate,b.isTreat,");
		listSql.append("b.stopTreat,b.treatSurgery,b.treatDrug,b.treatPhysical,b.treatOther,(SELECT c.codename FROM zdcode c WHERE c.CodeType = 'OfflineUW.TreatEffect' AND c.CodeValue = b.treatEffect) as treatEffect,b.otherSupplement ");
		listSql.append("FROM underwriting_offline_info a, underwriting_offline_healthinfo b WHERE a.id = b.info_id and a.id = ?");
		QueryBuilder qb = new QueryBuilder(listSql.toString(), params.getString("infoID"));
		DataTable dt = qb.executeDataTable();
		if (dt != null && dt.getRowCount() > 0) {
			params.put("productName", "康惠保、附加康惠保");
			params.put("name", dt.getString(0, "name"));
			params.put("infoID", dt.getString(0, "infoId"));
			String sex = dt.getString(0, "sex");
			if ("M".equals(sex)) {
				params.put("sex", "男");
			} else if ("F".equals(sex)) {
				params.put("sex", "女");
			}
			params.put("age", dt.getString(0, "age"));
			params.put("height", dt.getString(0, "height"));
			params.put("weight", dt.getString(0, "weight"));
			params.put("IdType", "身份证");
			params.put("IdNo", dt.getString(0, "IdNo"));
			params.put("birthday", dt.getString(0, "birthday"));
			params.put("firsOnsetTime", dt.getString(0, "firsOnsetTime"));
			params.put("mainSymptoms", dt.getString(0, "mainSymptoms"));
			params.put("diseaseName", dt.getString(0, "diseaseName"));
			params.put("attackDate", "(a)" + dt.getString(0, "attackDate") + "</br>");
			params.put("attackFrequency", "(b)" + dt.getString(0, "attackFrequency") + "</br>");
			params.put("attackLastDate", "(c)" + dt.getString(0, "attackLastDate") + "</br>");
			String isTreat = dt.getString(0, "isTreat");
			if (StringUtil.isNotEmpty(isTreat)) {
				if ("N".equals(isTreat) ){
					params.put("treat", "(a)否 ，请说明停止治疗的时间：" + dt.getString(0, "stopTreat") + "</br>");
				} else {
					params.put("treat", "(a)是" + "</br>");
				}
			}
			String treatSurgery = dt.getString(0, "treatSurgery");
			StringBuilder sb = new StringBuilder();
			if (StringUtil.isNotEmpty(treatSurgery)) {
				sb.append("手术治疗，手术名称：" + treatSurgery + "</br>");
			}
			String treatDrug = dt.getString(0, "treatDrug");
			if (StringUtil.isNotEmpty(treatDrug)) {
				sb.append("药物治疗，药物名称：" + treatDrug + "</br>");
			}
			String treatPhysical = dt.getString(0, "treatPhysical");
			if (StringUtil.isNotEmpty(treatPhysical)) {
				sb.append("物理治疗，物理名称：" + treatPhysical + "</br>");
			}
			String treatOther = dt.getString(0, "treatOther");
			if (StringUtil.isNotEmpty(treatOther)) {
				sb.append("其他，请具体说明：" + treatOther+ "</br>");
			}

			params.put("treatMathod", "(b)" + sb.toString());
			params.put("treatEffect", "(c)" + dt.getString(0, "treatEffect"));
			params.put("otherSupplement", dt.getString(0, "otherSupplement"));
		}
		return params;
	}

	/**
	 * 百年康惠宝线下核保生成 批量下载核保图片
	 */
	public void batchDownload () {
		try {
			DataTable dataTable = (DataTable) Request.get("tableData");
			if (dataTable == null || dataTable.getRowCount() == 0) {
				Response.setLogInfo(0, "批量下载失败！");
				return;
			}

			// 模板文件
			String modelFileName = Config.getContextRealPath() + "template/electronicPolicy/UnderWritingOfflinePdf.jasper";

			// 模板数据SQL
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("SELECT a.product_name AS productName,a.name,a.mobile,a.email,a.insure_email,a.create_time as createTime,b.id as id,b.sex,b.age,b.height,b.weight,b.IdType,b.IdNo,b.birthday,");
			stringBuilder.append("b.firsOnsetTime,b.mainSymptoms,b.diseaseName,b.attackDate,b.attackFrequency,b.attackLastDate,b.isTreat,");
			stringBuilder.append("b.stopTreat,b.treatSurgery,b.treatDrug,b.treatPhysical,b.treatOther,(SELECT c.codename FROM zdcode c WHERE c.CodeType = 'OfflineUW.TreatEffect' AND c.CodeValue = b.treatEffect) as treatEffect,b.otherSupplement ");
			stringBuilder.append("FROM underwriting_offline_info a, underwriting_offline_healthinfo b WHERE a.id = b.info_id and a.id = ?");

			String infoId = null;
			String productId = null;
			QueryBuilder qb = null;
			DataTable dt = null;
			HashMap<String, String> hm = null;
			String tempImgPath = Config.getValue("newPolicyPath") + "/offline/zipTemp/";

			// 如果路径不存在，创建
			File zipPath = new File(tempImgPath);
			if (!zipPath.exists()) {
				zipPath.mkdirs();
			}

			for (int i = 0; i < dataTable.getRowCount(); i++) {
				infoId = dataTable.getString(i, "id");
				productId = dataTable.getString(i, "productId");

				// 只使用于百年康惠保
				if ( !"224801001".equals(productId)) {
					continue;
				}

				qb = new QueryBuilder(stringBuilder.toString(), infoId);
				dt = qb.executeDataTable();
				hm = new HashMap<String, String>();
				if (dt != null && dt.getRowCount() > 0) {
					String outFilePath = tempImgPath + dt.getString(0, "name") + ".jpg";

					// 递归处理重复图片，重复图片后边依次加(1)(2)...
					File outFile = null;
					boolean flag = true;
					int a = 1;
					while (flag) {
						outFile = new File(outFilePath);
						if (outFile.exists()) {
							outFilePath = tempImgPath + dt.getString(0, "name") + "(" + a + ").jpg";
							a++;
						} else {
							flag = false;
						}
					}
					hm.put("dutyName", "康惠保、附加康惠保");
					hm.put("name", dt.getString(0, "name"));
					String sex = dt.getString(0, "sex");
					if ("M".equals(sex)) {
						hm.put("sex", "男");
					} else if ("F".equals(sex)) {
						hm.put("sex", "女");
					}
					hm.put("age", dt.getString(0, "age"));
					hm.put("height", dt.getString(0, "height") + "cm");
					hm.put("weight", dt.getString(0, "weight") + "kg");
					hm.put("IdType", "身份证");
					hm.put("IdNo", dt.getString(0, "IdNo"));
					hm.put("birthday", dt.getString(0, "birthday"));
					hm.put("firsOnsetTime", dt.getString(0, "firsOnsetTime"));
					hm.put("mainSymptoms", dt.getString(0, "mainSymptoms"));
					hm.put("diseaseName", dt.getString(0, "diseaseName"));
					hm.put("attackDate", dt.getString(0, "attackDate"));
					hm.put("attackFrequency", dt.getString(0, "attackFrequency"));
					hm.put("attackLastDate", dt.getString(0, "attackLastDate"));
					String isTreat = dt.getString(0, "isTreat");
					if (StringUtil.isNotEmpty(isTreat)) {
						if ("N".equals(isTreat)) {
							hm.put("treat", "(a)否 ，请说明停止治疗的时间：" + dt.getString(0, "stopTreat"));
						} else {
							hm.put("treat", "(a)是");
						}
					}
					hm.put("treatSurgery", "手术治疗，手术名称：" + dt.getString(0, "treatSurgery"));
					hm.put("treatDrug", "药物治疗，药物名称：" + dt.getString(0, "treatDrug"));
					hm.put("treatPhysical", "物理治疗，物理名称：" + dt.getString(0, "treatPhysical"));
					hm.put("treatOther", "其他，请具体说明：" + dt.getString(0, "treatOther"));
					hm.put("treatEffect", dt.getString(0, "treatEffect"));
					hm.put("otherSupplement", dt.getString(0, "otherSupplement"));
					hm.put("applyDate", dt.getString(0, "createTime"));

					JasperPrint print = JasperFillManager.fillReport(modelFileName, hm);

					// 生成图片
					JRGraphics2DExporter exporter = new JRGraphics2DExporter();
					BufferedImage bufferedImage = new BufferedImage(print.getPageWidth() * 4, print.getPageHeight() * 4, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
					exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, g);
					exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, Float.valueOf(4));
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

					exporter.exportReport();

					g.dispose();
					ImageIO.write(bufferedImage, "JPEG", new File(outFilePath));
				}
			}

			// 把图片zip包压缩
			File tempFile = new File(tempImgPath);
			String zipFileName = Config.getValue("newPolicyPath") + "/offline/imgTemp" + ".zip";
			ZipUtils.zipFiles(tempFile.listFiles(), new File(zipFileName));

			// 删除临时生成的图片
			FileUtil.delete(tempFile);
			
			// 更新审核状态
			UnderwritingOfflineInfoSchema info = null;
			for (int i = 0; i < dataTable.getRowCount(); i++) {
				// 只使用于百年康惠保
				if ( !"224801001".equals(productId)) {
					continue;
				}

				infoId = dataTable.getString(i, "id");
				info = new UnderwritingOfflineInfoSchema();
				info.setId(Integer.valueOf(infoId));
				if (info.fill()) {
					info.setDealStatus("1");
					info.setOperator(User.getUserName());
					info.setModifyTime(new Date());
					info.update();
				}
			}

			Response.setLogInfo(1, zipFileName);
			return;
		} catch (Exception ex) {
			Response.setLogInfo(0, "批量下载发生异常！");
			logger.error(ex.getMessage(), ex);
		}
	}
	/**
	 * 百年康惠宝线下核保生成pdf   （前端画面按钮的功能被弃用，所以此方法暂时没有被使用）
	 */
	public void createPdfFile () {
		try {
			String fileName = Config.getContextRealPath() + "template/electronicPolicy/UnderWritingOfflinePdf.jasper";

			HashMap<String, String> hm = new HashMap<String, String>();
			StringBuffer listSql = new StringBuffer();
			listSql.append("SELECT a.product_name AS productName,a.name,a.mobile,a.email,a.insure_email,a.create_time as createTime,b.id as id,b.sex,b.age,b.height,b.weight,b.IdType,b.IdNo,b.birthday,");
			listSql.append("b.firsOnsetTime,b.mainSymptoms,b.diseaseName,b.attackDate,b.attackFrequency,b.attackLastDate,b.isTreat,");
			listSql.append("b.stopTreat,b.treatSurgery,b.treatDrug,b.treatPhysical,b.treatOther,(SELECT c.codename FROM zdcode c WHERE c.CodeType = 'OfflineUW.TreatEffect' AND c.CodeValue = b.treatEffect) as treatEffect,b.otherSupplement ");
			listSql.append("FROM underwriting_offline_info a, underwriting_offline_healthinfo b WHERE a.id = b.info_id and a.id = ?");
			QueryBuilder qb = new QueryBuilder(listSql.toString(), $V("infoID"));
			DataTable dt = qb.executeDataTable();
			if (dt != null && dt.getRowCount() > 0) {
				String pdfFileName = "offline_" + dt.getString(0, "id") + ".jpg";
				String outFilePath = Config.getValue("newPolicyPath") + "/offline/" +  pdfFileName;
				hm.put("dutyName", "康惠保、附加康惠保");
				hm.put("name", dt.getString(0, "name"));
				String sex = dt.getString(0, "sex");
				if ("M".equals(sex)) {
					hm.put("sex", "男");
				} else if ("F".equals(sex)) {
					hm.put("sex", "女");
				}
				hm.put("age", dt.getString(0, "age"));
				hm.put("height", dt.getString(0, "height") + "cm");
				hm.put("weight", dt.getString(0, "weight") + "kg");
				hm.put("IdType", "身份证");
				hm.put("IdNo", dt.getString(0, "IdNo"));
				hm.put("birthday", dt.getString(0, "birthday"));
				hm.put("firsOnsetTime", dt.getString(0, "firsOnsetTime"));
				hm.put("mainSymptoms", dt.getString(0, "mainSymptoms"));
				hm.put("diseaseName", dt.getString(0, "diseaseName"));
				hm.put("attackDate", dt.getString(0, "attackDate"));
				hm.put("attackFrequency", dt.getString(0, "attackFrequency"));
				hm.put("attackLastDate", dt.getString(0, "attackLastDate"));
				String isTreat = dt.getString(0, "isTreat");
				if (StringUtil.isNotEmpty(isTreat)) {
					if ("N".equals(isTreat) ){
						hm.put("treat", "(a)否 ，请说明停止治疗的时间：" + dt.getString(0, "stopTreat"));
					} else {
						hm.put("treat", "(a)是");
					}
				}
				hm.put("treatSurgery", "手术治疗，手术名称：" + dt.getString(0, "treatSurgery"));
				hm.put("treatDrug", "药物治疗，药物名称：" + dt.getString(0, "treatDrug"));
				hm.put("treatPhysical", "物理治疗，物理名称：" + dt.getString(0, "treatPhysical"));
				hm.put("treatOther","其他，请具体说明：" +  dt.getString(0, "treatOther"));
				hm.put("treatEffect", dt.getString(0, "treatEffect"));
				hm.put("otherSupplement", dt.getString(0, "otherSupplement"));
				hm.put("applyDate",  dt.getString(0, "createTime"));

				JasperPrint print = JasperFillManager.fillReport(fileName, hm);
//
//				JRPdfExporter exporter = new JRPdfExporter();
//				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFilePath);
//				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
//
//				exporter.exportReport();

				// 生成图片
				JRGraphics2DExporter exporter = new JRGraphics2DExporter();
				BufferedImage bufferedImage = new BufferedImage(print.getPageWidth() * 4, print.getPageHeight() * 4, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
				exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, g);
				exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, Float.valueOf(4));
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

				exporter.exportReport();

				g.dispose();

				ImageIO.write(bufferedImage, "JPEG", new File(outFilePath));
				if( $V("download").equals("Y")){
					Response.setLogInfo(1, outFilePath);
					return;
				}
				// 给保险公司发送核保邮件
				// 姓名
				String name = dt.getString(0, "name");
				// 手机
				String mobile = dt.getString(0, "mobile");
				// 客户邮箱
				String email = dt.getString(0, "email");

				String mailText = "<font size =\"4\" face=\"微软雅黑\" >您好：<br>线下核保用户信息如下：<br>姓名："+name+"；手机："+mobile+"；邮箱："+email
						+"<br>核保pdf：<a href='"+ Config.getFrontServerContextPath() + "/offline/" + pdfFileName +"' target='_blank'>下载</a><br>"
						+ "<br>附件为核保pdf。如有问题及结果请回复至开心保客服“<b>webf_kf@kaixinbao.com</b>”<br>——开心保客服部</font>";

				List<Map<String,Object>> attachments = new ArrayList<Map<String,Object>>();
				Map<String,Object> attach = new HashMap<String,Object>();
				attach.put("name", pdfFileName);
				attach.put("path", outFilePath);
				attachments.add(attach);
				// 保险公司邮箱地址
				String companyEmail = dt.getString(0, "insure_email");
				String productName = $V("productName");
				boolean flag = ActionUtil.sendGeneralMail(companyEmail, "开心保主站核保咨询_" + productName, mailText, attachments);
				if (flag) {
					// 发送邮件成功后，更新审核状态
					UnderwritingOfflineInfoSchema info = new UnderwritingOfflineInfoSchema();
					info.setId(Integer.valueOf($V("infoID")));
					if (info.fill()) {
						info.setDealStatus("1");
						info.setOperator(User.getUserName());
						info.setModifyTime(new Date());
						info.update();
					}
					Response.setLogInfo(1, "发送成功！");
				} else {
					Response.setLogInfo(0, "发送失败！");
				}
			} else {
				Response.setLogInfo(0, "发送失败！");
			}

		} catch (Exception e) {
			Response.setLogInfo(0, "发送失败！");
			logger.error(e.getMessage(), e);
		}
	}
	/**
	 * 审核完结处理页初始化
	 * 
	 * @param params
	 * @return
	 */
	public static Mapx<String, Object> initDialog1(Mapx<String, Object> params) {
		StringBuffer listSql = new StringBuffer();
		listSql.append("select a.product_id,a.product_name,a.name,a.mobile,a.email,b.offlineCode,b.id from underwriting_offline_info a left join underwriting_offline_healthinfo b on a.id = b.info_id where a.id = ? ");
		QueryBuilder qb = new QueryBuilder(listSql.toString(), params.getString("infoID"));
		DataTable dt = qb.executeDataTable();
		String productId="",productName="",offlineCode="",userName="";
		if (dt != null && dt.getRowCount() > 0) {
			productId = dt.getString(0, "product_id");//产品id
			offlineCode = dt.getString(0, "offlineCode");//百年康惠宝线下核保编码
			productName = dt.getString(0, "product_name");//产品名称
			userName = dt.getString(0, "name");
			params.put("productId", productId);
			params.put("productName", productName);
			params.put("name", userName);
			params.put("mobile", dt.getString(0, "mobile"));
			params.put("email", dt.getString(0, "email"));

			// 百年康惠宝线下核保编码
			if ("224801001".equals(productId)) {
				if (StringUtil.isEmpty(offlineCode)) {
					String code = genCodes();
					offlineCode = code;
					params.put("offlineCode", code);
					String updateSql = "update underwriting_offline_healthinfo set offlineCode = ? where id = ?";
					qb = new QueryBuilder(updateSql, code, dt.getString(0, "id"));
					qb.executeNoQuery();
				} else {
					params.put("offlineCode", offlineCode);
				}
			}
		}
		params.put("UnderWritingStatus", HtmlUtil.codeToOptions("OfflineUW.UnderWritingStatus","1", true));
		
		params.put("MetaDescription_1", getMetaDescription_1(productId,offlineCode,productName,userName));
		params.put("MetaDescription_2", getMetaDescription_2(productId,offlineCode,productName,userName));

		return params;
	}
	
	/*
	 * 审核通过
	 * 根据保险公司获取邮件模板
	 * */
	public static String getMetaDescription_1(String productId,String offlineCode,String productName,String userName){//+ "<p></p>"
		String MetaDescription_1 = "";
		if("2248".equals(productId.substring(0,4))){//百年康惠宝
			MetaDescription_1="<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">核保结果：核保通过</span></p>"
					+ "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">核保编码："+offlineCode+" </span></p>"
					+ "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">被保人："+userName+" </span></p>"
					+ "<p></p> <p></p>"
					+ "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">温馨提示：请点击“"+productName+"”进行在线投保，在“填写健康告知信息”页面下方点击录入核保编码，按提示进行投保操作。</span></p>" ;
		}else if("1036".equals(productId.substring(0,4))){//瑞泰瑞和 //瑞泰瑞康
			MetaDescription_1 = "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">​核保结果：核保通过</span></p>"
					+ "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">姓名："+userName+"</span></p>"
					+ "<p></p> <p></p>"
					+ "<p><strong><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">温馨提示：</span></strong>"
					+ "<span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black;background:white\">核保通过后，</span>"
					+ "<strong><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:red;background: white\">请收到邮件后15天之内投保成功，</span></strong>"
					+ "<span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black;background:white\"><strong>投保时的手机号码要与核保时填写的号码一致。</strong>有任何疑问及建议请拨打4009-789-789联系我们。</span>"
					+ "</p>";
		}
		return MetaDescription_1;
	}
	/*
	 * 审核不通过
	 * 根据保险公司获取邮件模板
	 * */
	public static String getMetaDescription_2(String productId,String offlineCode,String productName,String userName){
		String MetaDescription_2 = "";
		if("2248".equals(productId.substring(0,4))){//百年康惠宝
			MetaDescription_2="<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">核保结果：未通过</span></p>"
					+ "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">被保人："+userName+" </span></p>"
					+ "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">原因：您的信息不满足核保条件 </span></p>"
					+ "<p></p> <p></p>"
					+ "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">温馨提示：很遗憾，您的核保请求未通过审核，不能继续投保"+productName+"了，您可以选择其他保险产品进行购买。</span></p>" ;
		}else if("1036".equals(productId.substring(0,4))){//瑞泰瑞和 //瑞泰瑞康
			MetaDescription_2 =  "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">​核保结果：未通过</span></p>"
					+ "<p><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">姓名："+userName+"</span></p>"
					+ "<p></p> <p></p>"
					+ "<p><strong><span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">温馨提示：</span></strong>"
					+ "<span style=\"font-size:16px;font-family:'微软雅黑','sans-serif';color:black\">很遗憾，您不能继续投保"+productName+"了！该核保结果不影响您继续选择其他保险产品！</span> </p>" ;
		}
		return MetaDescription_2;
	}
	/**
	 * 发送资料给保险公司
	 */
	public void sendEmailToCompany() {
		// 产品名称
		String productName = $V("productName");
		if (StringUtil.isEmpty(productName)) {
			Response.setLogInfo(0, "传入产品名称时发生错误！");
			return;
		}
		// 图片id
		String IDs = $V("IDs");
		if (!StringUtil.checkID(IDs)) {
			Response.setLogInfo(0, "传入图片ID时发生错误！");
			return;
		}
		// 保险公司邮箱
		String companyEmail = $V("companyEmail");
		if (StringUtil.isEmpty(companyEmail)) {
			Response.setLogInfo(0, "请填写保险公司邮箱！");
			return;
		}
		// 备注
		String remark = $V("remark");
		// 申请信息id
		String infoID = $V("infoID");
		if (StringUtil.isEmpty(infoID)) {
			Response.setLogInfo(0, "传入申请信息ID时发生错误！");
			return;
		}
		UnderwritingOfflineInfoSchema info = new UnderwritingOfflineInfoSchema();
		info.setId(Integer.valueOf(infoID));
		if (info.fill()) {
			info.setDealStatus("1");
			info.setOperator(User.getUserName());
    		info.setModifyTime(new Date());
    		if (!companyEmail.equals(info.getInsureEmail())) {
    			info.setInsureEmail(companyEmail);
    		}
    		if (!info.update()) {
    			Response.setLogInfo(0, "申请信息更新状态失败，请重试！");
    			return;
    		}
			// 查询图片
			DataTable dt = new QueryBuilder("select image_url,thum_url2 from underwriting_offline_images where id in ("+IDs+")").executeDataTable();
			if (dt != null) {
				int rowCount = dt.getRowCount();
				try {
					// 压缩的文件
					String fileName = "用户报告图片_"+infoID+".zip";
			    	String zipFilePath = Config.getValue("newPolicyPath")+"/EPolicy/UnderwritingOffline/"+fileName;   	
			    	File zipfile=new File(zipFilePath);
			    	int i = 0;
			    	byte[] buf=new byte[1024];
		            ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipfile));
		            File file;
			    	for (; i < rowCount; i++) {
			    		if (StringUtil.isNotEmpty(dt.getString(i, "thum_url2"))) {
			    			file = new File(dt.getString(i, "thum_url2"));
			    		} else {
			    			file = new File(dt.getString(i, "image_url"));
			    		}
			    		FileInputStream in = new FileInputStream(file);
			            out.putNextEntry(new ZipEntry(System.currentTimeMillis()+file.getName()));
			            int len;
			            while((len = in.read(buf)) > 0){
			                out.write(buf,0,len);
			            }
			            out.closeEntry();
			            in.close();
			    	}
			    	out.close();
			    	String mailText = "您好：<br>线下核保用户信息如下：<br>姓名："+info.getName()+"；手机："+info.getMobile()+"；邮箱："+info.getEmail()+
			    			"<br>情况说明："+info.getSituationDesc()+"<br>";
			    	
			    	
			    	boolean flag = true;
			    	zipfile=new File(zipFilePath);
			    	double resourcesize = zipfile.length() / 1024.0;
			    	if (resourcesize > 1000 && (resourcesize / 1024.0) > 10) {
			    		mailText += ("用户报告图片：<a href='"+zipFilePath.replace(Config.getValue("newPolicyPath"), Config.getFrontServerContextPath())+"' target='_blank'>下载</a><br>");
			    		if (StringUtil.isNotEmpty(remark)) {
				    		mailText = mailText + remark;
				    	}
			    		mailText += "<br>附件为用户报告图片。如有问题及结果请回复至开心保客服“webf_kf@kaixinbao.com”<br>——开心保客服部";
			    		ActionUtil.sendGeneralMail(companyEmail, "开心保_核保咨询_"+DateUtil.toString(info.getCreateTime(), "yyyy-MM-dd") + "_" + productName, mailText);
			    	} else {
			    		List<Map<String,Object>> attachments = new ArrayList<Map<String,Object>>();
			    		Map<String,Object> attach = new HashMap<String,Object>();
			    		attach.put("name", fileName);
			    		attach.put("path", zipFilePath);
			    		attachments.add(attach);
			    		if (StringUtil.isNotEmpty(remark)) {
				    		mailText = mailText + remark;
				    	}
			    		mailText += "<br>附件为用户报告图片。如有问题及结果请回复至开心保客服“webf_kf@kaixinbao.com”<br>——开心保客服部";
			    		flag = ActionUtil.sendGeneralMail(companyEmail, "开心保主站核保咨询 "+DateUtil.toString(info.getCreateTime(), "yyyyMMdd") + "_" + productName, mailText, attachments);
			    	}
			    	 
			    	if (flag) {
			    		Response.setLogInfo(1, "发送成功！");
			    	} else {
			    		Response.setLogInfo(0, "发送失败！");
			    	}
				}  catch(Exception e) {
					logger.error(e.getMessage(), e);
					Response.setLogInfo(0, "压缩图片异常！"+e.getMessage());
				}
			}
		} else {
			Response.setLogInfo(0, "未查找到申请信息！");
			return;
		}
	}
	
	/**
	 * 发送核保通知给客户
	 */
	public void sendUWResult() {
		// 申请信息ID
		String infoID = $V("infoID");
		if (StringUtil.isEmpty(infoID)) {
			Response.setLogInfo(0, "传入申请信息ID时发生错误！");
			return;
		}
		
		// 核保结果
		String underWritingStatus = $V("UnderWritingStatus");
		if (StringUtil.isEmpty(underWritingStatus)) {
			Response.setLogInfo(0, "请选择核保结果！");
			return;
		}
		
		// 邮件内容
		String metaDescription = $V("metaDescription");
		if (StringUtil.isEmpty(metaDescription)) {
			Response.setLogInfo(0, "请填写邮件内容！");
			return;
		}
		// 备注
		String remark = $V("remark");
		
		UnderwritingOfflineInfoSchema info = new UnderwritingOfflineInfoSchema();
		info.setId(Integer.valueOf(infoID));
		if (info.fill()) {
			info.setDealStatus("2");
			info.setUnderwritingStatus(underWritingStatus);
			info.setOperator(User.getUserName());
    		info.setModifyTime(new Date());
    		if (info.getFinishTime() == null) {
    			info.setFinishTime(new Date());
    		}
    		if (!info.update()) {
    			Response.setLogInfo(0, "申请信息更新状态失败，请重试！");
    			return;
    		}
    		
    		// 存在线下核保的合作方渠道数组 
    		String[] channelsnArr = Config.getValue("UnderwritingOfflineChannel").split(",");
    		if (ArrayUtils.contains(channelsnArr, info.getProp1())){
    			// 发送mq 发送核保结果 --发到消息队列
				try {
					// 发送核保结果
					logger.error("sendUnderwritingToReceiver-infoID：{}" , infoID);
					PayBaseService payService = new PayBaseServiceImpl();
					payService.sendUnderwritingToReceiver(infoID, StringUtil.delHTMLTag(metaDescription));
				} catch (Exception e) {
					logger.error("消息队列出现异常 ==== sendUnderwritingToReceiver-infoID：" + infoID + e.getMessage(), e);
				}
    		}
    		else {
    			// 发送邮件
        		Map<String, Object> data = new HashMap<String, Object>();
        		data.put("name", info.getName());
        		data.put("mobile", info.getMobile());
        		data.put("email", info.getEmail());
        		data.put("underWritingStatus", underWritingStatus);
        		data.put("metaDescription", metaDescription);
        		data.put("remark", remark);
        		data.put("productName", info.getProductName());
        		QueryBuilder qb = new QueryBuilder("select HtmlPath from sdproduct where ProductID = ?",
    					info.getProductId());
        		data.put("productUrl", qb.executeString());
        		data.put(MessageConstant.PARAM_SHOW_ACTIVITY_PRODUCT, "1");
        		ActionUtil.sendMail("wj01021", info.getEmail(), data);
    		}
    		
    		Response.setLogInfo(1, "数据状态更新成功，已发送邮件给客户！");
		} else {
			Response.setLogInfo(0, "未查找到申请信息！");
		}
	}
	
	/**
	 * 作废
	 *
	 * @return
	 */
	public void invalid() {
	String id = $V("id");
	String dealStatus = $V("dealStatus");//处理状态
	String underwritingStatus = $V("underwritingStatus");//核保状态
	if(!(StringUtil.isEmpty(underwritingStatus) && dealStatus.equals("0"))){
		Response.setLogInfo(0, "本条信息不符合作废条件！");
		return;
	}
	UnderwritingOfflineInfoSchema info = new UnderwritingOfflineInfoSchema();
	info.setId(Integer.valueOf(id));
	if (info.fill()) {
		info.setDealStatus("3");
		info.setOperator(User.getUserName());
		info.setModifyTime(new Date());
		info.setFinishTime(new Date());
		if (!info.update()) {
			Response.setLogInfo(0, "申请信息更新状态失败，请重试！");
			return;
		}
		if (!info.update()) {
			Response.setLogInfo(0, "操作失败，请重试！");
			return;
		} else {
			Response.setLogInfo(1, "操作成功！");
			return;
		}
	}else{
		Response.setLogInfo(0, "这条信息不存在！");
		}
	}
	
	public static void main(String[] args) {
//		String path = "D:/alidata/kxb/EPolicy/UnderwritingOffline";
//		File oldDir = new File(path);
//		File[] files = oldDir.listFiles();
//
//		for (File file : files) {
//			System.out.println(UploadCustomClaimsData.scaleRateImageFile(path+"/"+file.getName(), "1", 0.1));
//
//		}

	}

	/**
	 * 获取6位随机字符串
	 *
	 * @return
	 */
	private static String genCodes(){

		String val = "";

		Random random = new Random();
		for(int i = 0; i < 6; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if("char".equalsIgnoreCase(charOrNum)) // 字符串
			{
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			}
			else if("num".equalsIgnoreCase(charOrNum)) // 数字
			{
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val.toLowerCase();

	}
}