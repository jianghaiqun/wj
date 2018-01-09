/**
 * 
 */
package com.sinosoft.cms.resource;

import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.FileUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class TBDutyImageMake {
	private static final Logger logger = LoggerFactory.getLogger(TBDutyImageMake.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */ 
	public CErrors mErrors = new CErrors();
	
	private List<String> htmlList = new ArrayList<String>();
	
	private String path = "";
	
	/**
	 * 保存上传文件
	 * 
	 * @param upLoadFileName
	 *            上传文件
	 * @return
	 * 
	 */
	public String upLoadSave(String upLoadFileName) {
		String result = "Succ";
		// 解析Excel内容成功，保存html文件
		if (resolvingExcel(upLoadFileName)) {
			
			String path = Config.getValue("TBDutyImageHtmlPath");
			String fileName = File.separator + "EPolicy" + File.separator
					+ "TBDutyImageHtml" + File.separator + "TBDutyImageHtml"
					+ DateUtil.getCurrentDateTime("yyyyMMddHHmmss") + ".html";

			if (FileUtil.writeFile(path+fileName, htmlList)) {
				setPath(Config.getFrontServerContextPath()+fileName.replace("\\", "/"));
			} else {
				result = "Fail";
				this.addError("生成html文件失败！");
			}
			
		} else {
			result = "Fail";
		}

		return result;
	}
	
	public boolean resolvingExcel(String upLoadFileName) {
		try {
			// 取得并解析文件头部
			HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
					upLoadFileName));
			HSSFSheet sheet = workbook.getSheetAt(0);// 取头一个sheet
			if (sheet == null || sheet.getLastRowNum() < 12) {
				this.addError("Excel文件中没有数据!");
				return false;
			}
			
			htmlList.add("<!DOCTYPE html><html><head lang=\"en\"><title>淘宝产品图片生成器</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
			htmlList.add("<style>html,body,h1,h2,h3,h4,h5,h6,div,dl,dt,dd,ul,ol,li,p,blockquote,pre,hr,figure,table,caption,th,td,form,fieldset,legend,input,button,textarea,menu{margin:0;padding:0;}\n");
			htmlList.add("canvas { border: 1px solid black; }\n");
			htmlList.add("button { clear: both;display: block;}\n");
			htmlList.add("#content {background: #ffffff;padding: 0px 0px;}\n");
			htmlList.add(".but_con{position:fixed; right: 40px; top: 50%; top: 30%;}");
			htmlList.add("img{ display: block;}");
			htmlList.add(".taobao_table{border-collapse:collapse;}");
			htmlList.add(".taobao_table td{ background: #ffffff;}");
			htmlList.add(".taobao_table td.bg_js{ background: #FF5500;  }");
			htmlList.add(".taobao_table td.bg_hs{ background: #FFA202; }");
			htmlList.add(".bg_hs p{ font-size: 24px; text-align: center; font-family: 'Microsoft YaHei'; color: #fff;}");
		    htmlList.add(".tb_bor{ border: 1px solid #FF5500; text-align: center; color: #696969;  line-height: 1.6em;}");
			htmlList.add(".tb_des{ text-align: left;}");
		    htmlList.add(".tb_des p{ padding: 12px;  color: #696969; font-size: 13px;}");
		    htmlList.add(".tb_des b{ display: block; font-size: 14px;}");
		    htmlList.add(".taobao_table td.tb_jh{ background: #FF5500; color: #fff; font-size: 14px; height: 22px; line-height: 22px; padding-bottom: 2px; text-align: center; border-right: 1px solid #ffffff;}");
		    htmlList.add(".taobao_table td.tb_jh p{ height: 24px; line-height: 24px;}");
		    htmlList.add(".taobao_table td.clear_bor{ border-right: 1px solid #FF5500;} ");
		    htmlList.add(".bg_tis{ border-right: 1px solid #fff; border-bottom: 1px solid #FF5500;}");
		    htmlList.add(".one_bor{ border-top: none;}");
		    htmlList.add(".button{ display: block; border: none; width: 230px; height: 100px;  font-size: 1.6em; cursor: pointer; color: #fff; background: #8ED4DA; }");
		    htmlList.add(".td_bor_bs{ border-right: 1px solid #FF5500;}");
		    htmlList.add(".taobao_table td.bj_js1{ border-bottom:1px solid #FF5500;}");
		    htmlList.add(".taobao_table td.bj_js2{ border-bottom:1px solid #FFA202;}");
		    htmlList.add(".img_mar img{ margin-top: -1px;}");
		    htmlList.add(".color_li{ margin-top: 4px;}");
		    htmlList.add(".color_li li{ display: block; width: 25px; height: 25px; margin-right:4px; background: #FFA202; float: left; cursor: pointer;}");
		    htmlList.add(".color_li li.select{ background-image: url(images/icon_03.png); background-position:-1px 2px; background-repeat: no-repeat;}");
		    htmlList.add(".orange td.bg_js{ background:  #FF5500;}");
		    htmlList.add(".orange td.tb_jh{ background:  #FF5500;}");
		    htmlList.add(".orange td.bg_hs{ border-bottom: 1px solid #fff;  background: #FFA202;}");
		    htmlList.add(".orange td.clear_bor{  border-right: 1px solid   #FF5500;}");
		    htmlList.add(".orange .bg_tis{ border-bottom: 1px solid  #FF5500;}");
		    htmlList.add(".orange .tb_bor{border: 1px solid #FF5500;}");
		    htmlList.add(".orange .td_bor_bs{ border-right: 1px solid  #FF5500;}");
		    htmlList.add(".orange td.bj_js1{ border-bottom:1px solid  #FF5500;}");
		    htmlList.add(".orange td.bj_js2{ border-bottom:1px solid  #FF5500;}");
		    htmlList.add(".blue td.bg_js{background: #69B6E6;}");
		    htmlList.add(".blue td.tb_jh{ background: #69B6E6;}");
		    htmlList.add(".blue td.bg_hs{ border-bottom: 1px solid #fff;  background: #99CAE9;}");
		    htmlList.add(".blue td.clear_bor{  border-right: 1px solid  #69B6E6;}");
		    htmlList.add(".blue td.bg_tis{ border-bottom: 1px solid #B7B7B7; background: #83CFFF; }");
		    htmlList.add(".blue .tb_bor{border: 1px solid #B7B7B7;}");
		    htmlList.add(".blue .td_bor_bs{ border-right: 1px solid #B7B7B7;}");
		    htmlList.add(".blue td.bj_js1{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".blue td.bj_js2{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".red td.bg_js{background:#E97979;}");
		    htmlList.add(".red td.tb_jh{ background:#E97979;}");
		    htmlList.add(".red td.bg_hs{ border-bottom: 1px solid #fff;  background:#EBA4A4;}");
		    htmlList.add(".red td.clear_bor{  border-right: 1px solid #E97979;}");
		    htmlList.add(".red td.bg_tis{ border-bottom: 1px solid #B7B7B7; background: #FF9090; }");
		    htmlList.add(".red .tb_bor{border: 1px solid #B7B7B7;}");
		    htmlList.add(".red .td_bor_bs{ border-right: 1px solid #B7B7B7;}");
		    htmlList.add(".red td.bj_js1{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".red td.bj_js2{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".yellow td.bg_js{background:#E3AA56;}");
		    htmlList.add(".yellow td.tb_jh{ background:#E3AA56;}");
		    htmlList.add(".yellow td.bg_hs{ border-bottom: 1px solid #fff;  background:#E6C18C;}");
		    htmlList.add(".yellow td.clear_bor{  border-right: 1px solid #E3AA56;}");
		    htmlList.add(".yellow td.bg_tis{ border-bottom: 1px solid #B7B7B7; background:#FFC673; }");
		    htmlList.add(".yellow .tb_bor{border: 1px solid #B7B7B7;}");
		    htmlList.add(".yellow .td_bor_bs{ border-right: 1px solid #B7B7B7;}");
		    htmlList.add(".yellow td.bj_js1{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".yellow td.bj_js2{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".green td.bg_js{background:#75BD79;}");
		    htmlList.add(".green td.tb_jh{ background:#75BD79;}");
		    htmlList.add(".green td.bg_hs{ border-bottom: 1px solid #fff;  background:#A0CEA3;}");
		    htmlList.add(".green td.clear_bor{  border-right: 1px solid #75BD79;}");
		    htmlList.add(".green td.bg_tis{ border-bottom: 1px solid #B7B7B7; background:#93DA97; }");
		    htmlList.add(".green .tb_bor{border: 1px solid #B7B7B7;}");
		    htmlList.add(".green .td_bor_bs{ border-right: 1px solid #B7B7B7;}");
		    htmlList.add(".green td.bj_js1{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".green td.bj_js2{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".orangeq td.bg_js{background:#E6995B;}");
		    htmlList.add(".orangeq td.tb_jh{ background:#E6995B;}");
		    htmlList.add(".orangeq td.bg_hs{ border-bottom: 1px solid #fff;  background:#E8B890;  }");
		    htmlList.add(".orangeq td.clear_bor{  border-right: 1px solid #E6995B;}");
		    htmlList.add(".orangeq td.bg_tis{ border-bottom: 1px solid #B7B7B7; background:#FFB579; }");
		    htmlList.add(".orangeq .tb_bor{border: 1px solid #B7B7B7;}");
		    htmlList.add(".orangeq .td_bor_bs{ border-right: 1px solid #B7B7B7;}");
		    htmlList.add(".orangeq td.bj_js1{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".orangeq td.bj_js2{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".purple td.bg_js{background:#A49BE8;}");
		    htmlList.add(".purple td.tb_jh{ background:#A49BE8;}");
		    htmlList.add(".purple td.bg_hs{ border-bottom: 1px solid #fff;  background:#C1BBEB;}");
		    htmlList.add(".purple td.clear_bor{  border-right: 1px solid #A49BE8;}");
		    htmlList.add(".purple td.bg_tis{ border-bottom: 1px solid #B7B7B7; background:#B6ADF9; }");
		    htmlList.add(".purple .tb_bor{border: 1px solid #B7B7B7;}");
		    htmlList.add(".purple .td_bor_bs{ border-right: 1px solid #B7B7B7;}");
		    htmlList.add(".purple td.bj_js1{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".purple td.bj_js2{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add(".grey td.bg_js{background:#B8B8B8;}");
		    htmlList.add(".grey td.tb_jh{ background:#B8B8B8;}");
		    htmlList.add(".grey td.bg_hs{ border-bottom: 1px solid #fff;  background:#A2A2A2;}");
		    htmlList.add(".grey td.clear_bor{  border-right: 1px solid #B8B8B8;}");
		    htmlList.add(".grey td.bg_tis{ border-bottom: 1px solid #B7B7B7; background:#B9B9B9; }");
		    htmlList.add(".grey .tb_bor{border: 1px solid #B7B7B7;}");
		    htmlList.add(".grey .td_bor_bs{ border-right: 1px solid #B7B7B7;}");
		    htmlList.add(".grey td.bj_js1{ border-bottom:1px solid #B7B7B7;}");
		    htmlList.add("li.blue{ background: #3AC6E9;}");
		    htmlList.add("li.red{ background: #F72D2D;}");
		    htmlList.add("li.orange{ background: #EF600A;}");
		    htmlList.add("li.yellow{ background:#FFFF80;}");
		    htmlList.add("li.green{ background: #35CC0F;}");
		    htmlList.add("li.blueness{ background: #21C4D6;}");
		    htmlList.add("li.purple{ background:#B41DDA;}");
		    htmlList.add("li.grey{ background:#929A99;}");
			htmlList.add("</style></head>");
			htmlList.add("<body bgcolor=\"#FFFFFF\" leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">");
			htmlList.add("<div id=\"content\"><table class=\"taobao_table orange\" width=\"790\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >");
			
			HSSFRow row = sheet.getRow(11);
			int planCount = 0;
			List<String> plans = new ArrayList<String>();
			for (int i = 2; i < 6; i++) {
				if (row.getCell(i) != null && !StringUtil.isEmpty(row.getCell(i)
						.getStringCellValue())) {
					planCount += 1;
					plans.add(row.getCell(i).getStringCellValue());
				} else {
					break;
				}
			}
			int tdWidth = 709;
			int amntTDCount = planCount;
			String dutyNameClass = "tb_bor tb_des";
			String amntClass = "tb_bor";
			if (planCount == 0) {
				tdWidth = 604;
				amntTDCount = 1;
				dutyNameClass = "tb_bor tb_des one_bor";
				amntClass = "tb_bor one_bor";
				htmlList.add("<tr><td colspan=\"9\" height=\"9\" ></td></tr>");
				htmlList.add("<tr><td width=\"32\" height=\"67\"></td>");
				htmlList.add("<td class=\"bg_js bg_tis\"colspan=\"2\"> <img src=\"./images/taobao_orange.gif\" class=\"logo_img\" width=\"190\" height=\"63\" alt=\"\"></td>");
				htmlList.add("<td colspan=\""+(amntTDCount+1)+"\" class=\"bg_hs\" >");
				htmlList.add("<p>保额</p></td>");
				htmlList.add("<td width=\"32\" ></td></tr>");
				
			} else {
				tdWidth = tdWidth - planCount*105;
				htmlList.add("<tr><td colspan=\"9\" height=\"9\" ></td></tr>");
				htmlList.add("<tr><td rowspan=\"2\" width=\"32\"></td>");
				htmlList.add("<td rowspan=\"2\" colspan=\"2\" class=\"bg_js bg_tis\" > <img src=\"./images/taobao_orange.gif\" class=\"logo_img\" width=\"190\" height=\"63\" alt=\"\"></td>");
				htmlList.add("<td colspan=\""+(amntTDCount+1)+"\" class=\"bg_hs\" >");
				htmlList.add("<p>保额</p></td>");
				htmlList.add("<td width=\"32\" rowspan=\"2\" ></td></tr>");
				
			}
			if (planCount != 0) {
				htmlList.add("<tr>");
				for (int i = 1; i <= planCount; i++) {
					if (i == planCount) {
						htmlList.add("<td width=\"105\" class=\"tb_jh clear_bor\"><p>"+plans.get(i-1)+"</p></td>");
					} else {
						htmlList.add("<td width=\"105\" class=\"tb_jh\"><p>"+plans.get(i-1)+"</p></td>");
					}
				}
				htmlList.add("<td width=\"15\" height=\"21\"  class=\"bg_js\"></td></tr>");
			}
			// 读取Excel
			StringBuffer sb = new StringBuffer();
			for (int i = 12; i <= sheet.getLastRowNum(); i++) {
				row = sheet.getRow(i);
				if (row != null) {
					sb = new StringBuffer();
					sb.append("<tr><td width=\"32\"></td>");
					sb.append("<td class=\"td_bor_bs img_mar\" valign=\"top\">");
					if (i == 12) {
						sb.append("<img src=\"./images/j1_13.gif\" width=\"17\" height=\"16\" alt=\"\">");
					}
					sb.append("</td><td class=\""+dutyNameClass+"\"><p><b>");
					
					
					// 责任名称
					if (row.getCell(0) != null
							&& !StringUtil.isEmpty(row.getCell(0)
									.getStringCellValue())) {
						sb.append(row.getCell(0).getStringCellValue().trim()+"</b>");
						
					} else {
						this.addError("第" + (i + 1) + "行,第1列不能是空！");
						return false;
					}
					
					// 责任描述
					if (row.getCell(1) != null
							&& !StringUtil.isEmpty(row.getCell(1)
									.getStringCellValue())) {
						sb.append(row.getCell(1).getStringCellValue().trim());
						
					}
					sb.append("</p></td>");
					
					for (int j = 0; j < amntTDCount; j++) {
						sb.append("<td class=\""+amntClass+"\" ");
						if (planCount == 0) {
							sb.append("width=\"150px\"");
						} 
						sb.append("><p>");
						if (row.getCell(j + 2) != null
								&& !StringUtil.isEmpty(row.getCell(j + 2)
										.getStringCellValue())) {
							sb.append(row.getCell(j + 2).getStringCellValue().trim());
							
						} else {
							sb.append("-");
						}
						
						sb.append("</p></td>");
					}
					sb.append("<td valign=\"top\" class=\"img_mar\"> ");
					if (i == 12) {
						sb.append("<img src=\"./images/j2_19.gif\" width=\"15\" height=\"16\" alt=\"\">");
					}
					sb.append("</td><td width=\"32\"></td></tr>");
					
					htmlList.add(sb.toString());
				}
				
			}
			
			htmlList.add("</table></div><canvas width=\"790\" height=\"0\"></canvas>");
			htmlList.add("<script type=\"text/javascript\" src=\""+Config.getServerContext()+"/wwwroot/kxb/js/jquery-1.4.4.min.js\"></script>");
			htmlList.add("<script type=\"text/javascript\" src=\""+Config.getServerContext()+"/scripts/html2canvas.js\"></script>");
			htmlList.add("<div class=\"but_con\"><button class=\"button\">生成淘宝图片</button>");
			htmlList.add("<ul class=\"color_li\" id=\"color\"><li class=\"red\" data=\"red\"></li>");
			htmlList.add("<li class=\"select orange\" data=\"orange\"></li><li class=\"orangeq\" data=\"orangeq\"></li>");
			htmlList.add("<li class=\"yellow\" data=\"yellow\"></li><li class=\"green\" data=\"green\"></li>");
			htmlList.add("<li class=\"blue\" data=\"blue\" ></li><li class=\"purple\" data=\"purple\"></li>");
			htmlList.add("<li class=\"grey\" data=\"grey\"></li></ul></div>");
			htmlList.add("<script type=\"text/javascript\">var canvas = document.querySelector(\"canvas\");");
			htmlList.add("document.querySelector(\"button\").addEventListener(\"click\", function() {");
			htmlList.add("html2canvas(document.querySelector(\"#content\"), {canvas: canvas}).then(function(canvas) {});");
			htmlList.add("document.getElementsByTagName(\"canvas\")[0].height = document.body.offsetHeight;");
			htmlList.add("}, false);");
			htmlList.add("jQuery(\"#color li\").click(function(){var class_name = jQuery(this).attr(\"data\");");
			htmlList.add("jQuery(this).siblings().removeClass(\"select\").end().addClass(\"select\");");
			htmlList.add("jQuery(\"#content table\").removeClass().addClass(\"taobao_table \"+class_name);");
			htmlList.add("jQuery(\".logo_img\").attr(\"src\",'images/taobao_'+class_name+'.gif');})");
			htmlList.add("</script></body></html>");
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 添加错误信息
	 * 
	 * @param errorMessage
	 *            错误信息
	 */
	protected void addError(String errorMessage) {
		CError tError = new CError();
		tError.moduleName = this.getClass().getName();
		tError.errorMessage = errorMessage;
		this.mErrors.addOneError(tError);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
