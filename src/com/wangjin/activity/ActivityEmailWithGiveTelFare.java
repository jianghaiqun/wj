package com.wangjin.activity;

import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.DataTableUtil;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.Mapx;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.ConfigEanbleTaskManager;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityEmailWithGiveTelFare extends ConfigEanbleTaskManager {

	public static final String CODE = "com.wangjin.activity.ActivityEmailWithGiveTelFare";

	public boolean isRunning(long id) {
		return false;
	}

	/**
	 * ClassName:ActivityEmailWithGiveTelFare <br/>
	 * Function:TODO 天猫自动充值系统定时任务. <br/>
	 * Date:2016年9月28日 上午8:59:16 <br/>
	 *
	 * @author:"guanyulong"
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public boolean deal() {
		String todayDate = PubFun.getCurrentDate();
		Date yesterday = new Date(new Date().getTime()-24*60*60*1000);
		SimpleDateFormat matter = new SimpleDateFormat("yyyy-MM-dd");
		String yesterdayDate = matter.format(yesterday);
		Map<String, Object> map = new HashMap<String, Object>();
		String toEmail = Config.getValue("ActivityEmailWithGiveTelFare");
		String sql= " SELECT GROUP_CONCAT(s.orderSn),s.AppntName,s.appntMobileNo,Sum(s.TradeSum - s.UseSum), "
				   + " IF(s0.accumulation='1', "
				   + " (SELECT  a.ActivityData FROM sdactivityrule a WHERE a.activitySn =s.activitySn AND a.StartAmount<=SUM(s.TradeSum - s.UseSum) AND a.endAmount>SUM(s.TradeSum - s.UseSum) ), "
				   + " ( SELECT FLOOR(Sum(s.TradeSum - s.UseSum)/(a.Startamount)) * a.activitydata FROM sdactivityrule a WHERE a.activitySn =s.activitySn )) AS Sumpay , "
				   + " s.channelsn , (SELECT productname FROM sdproduct p WHERE    t.riskcode=p.productid)" 
                   +" FROM SDTBTradeRecord s, sdinformationrisktype t , sdcouponactivityinfo s0 "
                   +" WHERE s.orderSn=t.orderSn  "
                   +" AND s.activitySn IS NOT NULL AND s.activitySn !='' AND t.appstatus IN ('1','3')  "
                   +" AND s.activitySn = s0.activitysn AND s0.TYPE ='10' "
                   +" AND s.channelsn!='tbsd'  AND t.svaliDate like  '" +yesterdayDate+"%'" //-- 生效时间  填入昨天日期
                   +" GROUP BY s.appntMobileNo, s.activitySn  ORDER BY s.appntMobileNo, s.activitySn";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		if (dt.getRowCount() > 0) {
			BigDecimal zero = new BigDecimal("0");
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (zero.compareTo(new BigDecimal(dt.getString(i, "Sumpay"))) >= 0) {
					dt.deleteRow(i);
					i--;
				}
			}
		}else{
			logger.info("{}没有客户参加活动喔!", yesterdayDate);
			if(StringUtil.isNotEmpty(toEmail)){
				map.put("Massage", "亲爱的管理员 ，您好！<br></br><dt></dt>昨天没有客户满足参加满送活动条件喔。<br></br>");
				if(!ActionUtil.sendMail("wj00410", toEmail, map)){
					logger.error("天猫自动充值系统email发送/定时任务!,邮件发送失败！");
				}
			}
			return false;
		}
		String path = Config.getContextRealPath();
		String tFolderOutPath = path + File.separator + "ActivityEmailWithGiveTelFare" + File.separator ;
		File newfilepatch = new File(tFolderOutPath);
		if (!newfilepatch.exists()) {
			newfilepatch.mkdirs();
		}
		
		String filepath = tFolderOutPath +PubFun.getCurrentDate()+".xls";
		DataTableUtil datatableutil = new DataTableUtil();
		try{
		datatableutil.dataTableToExcel(dt,filepath);
		File file=new File(filepath);
        updateExcel(file);
		}catch (Exception e) {
			logger.error("天猫自动充值系统email发送/定时任务!" + e.getMessage(), e);
		}	
		List<Map<String,Object>> attachments = new ArrayList<Map<String,Object>>();//附件
		Map<String,Object> attach = new HashMap<String,Object>();
		attach.put("name", "满送话费"+todayDate+".xls");
		attach.put("path", Config.getValue("ServerContext") + "/" + "ActivityEmailWithGiveTelFare/" + PubFun.getCurrentDate() + ".xls");
		attachments.add(attach);
		map.put("Massage","亲爱的管理员 ，您好！<br></br><dt></dt>本次发来天猫自动充值系统，发送满送话费活动者参加名单<br></br><dt></dt>详情见附件，请注意查收：<br></br>");
		if(StringUtil.isNotEmpty(toEmail)){
			if(!ActionUtil.sendMail("wj00410", toEmail, attachments, map)){ 
				logger.error("天猫自动充值系统email发送/定时任务!,邮件发送失败！");
				return false;
			}
		}
		return true;
		
	}

	/**
	 * 
	* @Title: execute 
	* @Description: TODO(定时任务调用主方法) 
	* @return void    返回类型 
	* @author cuishg
	 */
	public void execute(long id) {
		// 邮件发送
		if ("0".equals(id + "")) {
			if (deal()) {
				logger.info("天猫自动充值系统email发送/定时任务!");
			}
		}
	}

	@Override
	public Mapx<String, String> getConfigEnableTasks() {
		Mapx<String, String> map = new Mapx<String, String>();
		map.put("0", "天猫自动充值系统email发送/定时任务!");
		return map;
	}

	@Override
	public void execute(String paramString) {
		execute(Long.parseLong(paramString));
	}

	public String getCode() {
		return CODE;
	}

	public String getName() {
		return "天猫自动充值系统email发送/定时任务!";
	}

	@Override
	public boolean isRunning(String paramString) {
		return false;
	}

	@Override
	public String getID() {
		return "com.wangjin.activity.ActivityEmailWithGiveTelFare";
	}

	

	public static void updateExcel(File exlFile)throws Exception{
		//-- 保单号 ，投保人名 , 电话号  有效金额,应充值 ,渠道
        FileInputStream fis=new FileInputStream(exlFile);
        HSSFWorkbook workbook=new HSSFWorkbook(fis);
        HSSFSheet sheet=workbook.getSheetAt(0);
        HSSFRow ExcelRow=sheet.getRow(0);
        ExcelRow.getCell(0).setCellValue("订单号");
        ExcelRow.getCell(1).setCellValue("投保人名 ");
        ExcelRow.getCell(2).setCellValue("充值号码");
        ExcelRow.getCell(3).setCellValue("总金额");
        ExcelRow.getCell(4).setCellValue("充值话费");
        ExcelRow.getCell(5).setCellValue("渠道");
        ExcelRow.getCell(6).setCellValue("投保产品");
        fis.close();//关闭文件输入流
        FileOutputStream fos=new FileOutputStream(exlFile);
        workbook.write(fos);
        fos.close();//关闭文件输出流
    }

	
	public static void main(String[] args) {//test main
		ActivityEmailWithGiveTelFare AEWGT = new ActivityEmailWithGiveTelFare();
		AEWGT.execute(0);
	}
	
	
	
	
	
}
