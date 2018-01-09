/**
 * 
 */
package com.sinosoft.cms.document;

import com.sinosoft.framework.Page;
import com.sinosoft.framework.data.DataColumn;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.utility.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class ShoppingGuidePKManageNew extends Page {
	
	/**
	 * 文章险种不一样，头图也不一样
	 * @param articleId
	 * @return
	 */
	public String getTopPicture(String articleId,String baoxianfenlei,String ArticleContentTest,String size) {
		if(ArticleContentTest.length() >= Integer.parseInt(size)){
			ArticleContentTest=ArticleContentTest.substring(0,  Integer.parseInt(size)-1) + "......";
		}
		if("长险".equals(baoxianfenlei)){
			//暂无
		}else if("健康险".equals(baoxianfenlei)){
			return jiankangPicture(ArticleContentTest);
		}else if("旅游险".equals(baoxianfenlei)){
			return lvyouOryiwaiPicture(baoxianfenlei,ArticleContentTest);
		}else{//意外险
			return lvyouOryiwaiPicture(baoxianfenlei,ArticleContentTest);
		}
		return "";
	}
	
	/**
	 * 健康保险头图
	 */
	public String  jiankangPicture(String ArticleContentTest){
		String str = " <div class=\"guide_banner\">"
					+ "<div class=\"g-weaper\"> "
					+ "<div class=\"guide_banimg\"></div> "
					+ "<div class=\"guider_banner_des animated bounceInDown\"> "
					+ "<span class=\"guider_dz_icon1\"></span> "
					+ "<span class=\"guider_dz_icon2\"></span> "
					+ "<div class=\"guider_banner_p \" id =\"ArticleContentTest\" > "+ArticleContentTest+" </div> "
	                + "<div class=\"banner_icon1 animated slideInLeft\"></div> "
	                + "<div class=\"banner_icon2 animated slideInLeft\"></div> "
	                + "<div class=\"banner_icon3 animated rubberBand\"></div> "
	                + "<div class=\"banner_icon4 animated slideInRight\"></div> "
	                + "</div> "
	                + "</div> ";
		return str;
	}
	
	/**
	 * 旅游，意外保险头图
	 */
	public String  lvyouOryiwaiPicture(String  distinction ,String ArticleContentTest){
		String str = "";
		if("旅游险".equals(distinction)){
			str = " <div class=\"guide_banner guide_lv\"> ";   //旅游
		}else if("意外险".equals(distinction)){
			 str = " <div class=\"guide_banner guide_yw\"> " ;  		//意外
		}

		str = str +"<div class=\"g-weaper\"> "
				+ "<div class=\"guider_banner_des animated bounceInDown\"> "
				+ " <span class=\"guider_dz_icon1\"></span>  <span class=\"guider_dz_icon2\"></span> "
				+ " <div class=\"guider_banner_p\"  id =\"ArticleContentTest \"> "+ArticleContentTest+" </div> "
				+ "</div>"
				+ "</div>"
				+ "</div>";

		return str;
	}
	
	/**
	 * 取得文章拼装信息
	 * @param articleId
	 * @return
	 */
	public String getPKInfo(String articleId,String baoxianfenlei ) {
		String product1 ,product2;
		StringBuffer sb = new StringBuffer();
		Map<String, String> map = getPKProductInfo(articleId);
		sb.append(" <div class=\"g-weaper guide_con\">");
		
		// 拼装行程推荐html
		if (!map.isEmpty()) {
			sb.append(getRecommentSchedule(map, articleId,baoxianfenlei));
		}

		// 拼装PK保障信息
		sb.append(getPKDutyHtml(articleId,baoxianfenlei));
		
		// 拼装PK优势信息
		sb.append(getPKAdvantageInfo(articleId));
		
		try{
			product1 = map.get("ProductId1");
			product2 = map.get("ProductId2");
		}catch(Exception e) {
			product1 = "";
			product2 = "";
		}
		//拼装留言板
		sb.append(getmessageBoard(product1,product2));
		
		//拼装尾部重新测评
		sb.append(" <div class=\"guide_gobak\"> <div class=\"g-weaper\"> <div class=\"guide_gobank_img animated slideInLeft\"></div>   "
				+ "  <p class=\"animated swing\">没有您想要的？赶紧重新评测一下吧</p>"
				+ " <a href=\"http://www.kaixinbao.com/daogou/\" class=\"animated slideInRight\">重新评测</a> </div> </div> ");
		return sb.toString();
	}
	
	public Map<String, String> getPKProductInfo(String articleId) {
		DataTable dt = new QueryBuilder("select ProductId1, ProductName1, DiscountPrice1, Prem1, ProductUrl1, ProductPeriod1, ProductId2, ProductName2, DiscountPrice2, Prem2, ProductUrl2, ProductPeriod2, InitPeriod from PKProductInfo where ArticleId = ? limit 0,1", articleId).executeDataTable();
		Map<String, String> map = new HashMap<String, String>();
		if (dt != null && dt.getRowCount() > 0) {
			DataColumn[] colu = dt.getDataRow(0).getDataColumns();
			int len = colu.length;
			for (int i = 0; i < len; i++) {
				map.put(colu[i].getColumnName(), dt.getString(0, colu[i].getColumnName()));
			}
		}
		return map;
	}
	
	/**
	 * 拼装PK保障信息
	 * @param articleId
	 * @return PK保障信息
	 */
	private String getPKDutyHtml(String articleId,String distinction) {
		DataTable dt = new QueryBuilder("select DutyName, DutyAmnt1, Percent1, KeyTips1, DutyDesc1, DutyAmnt2, Percent2, KeyTips2, DutyDesc2 ,prop1,prop2,prop3,prop4 "
				+ " from PKProductDutyInfo where ArticleId = ? order by OrderFlag asc", articleId).executeDataTable();	//KeyTips重点提示 本次取消
		String changePlan = "";//健康险选计划
		if("健康险".equals(distinction)){
			String columncode="",recommendPlan1_1 = "",recommendPlan1_2 = "",recommendPlan1_3 = "",recommendPlan2_1 = "",recommendPlan2_2 = "",recommendPlan2_3 = "";
			int planNotEmptyLeft = 0,planNotEmptyRight = 0;
			DataTable dt_jk = new QueryBuilder("SELECT columncode,textvalue FROM zdcolumnvalue WHERE relaId=? AND columncode LIKE 'recommendPlan%' ", articleId).executeDataTable();
			if(dt_jk != null && dt_jk.getRowCount() > 0){
				for(int i = 0; i<dt_jk.getRowCount();i++){
					if(StringUtil.isNotEmpty(dt_jk.getString(i, "columncode"))){
						columncode = dt_jk.getString(i, "columncode");
						if("recommendPlan1_1".equals(columncode)){
							++planNotEmptyLeft;
							recommendPlan1_1= dt_jk.getString(i, "textvalue");
						}
						if("recommendPlan1_2".equals(columncode)){
							++planNotEmptyLeft;
							recommendPlan1_2= dt_jk.getString(i, "textvalue");
						}
						if("recommendPlan1_3".equals(columncode)){
							++planNotEmptyLeft;
							recommendPlan1_3= dt_jk.getString(i, "textvalue");
						}
						if("recommendPlan2_1".equals(columncode)){
							++planNotEmptyRight;
							recommendPlan2_1= dt_jk.getString(i, "textvalue");
						}
						if("recommendPlan2_2".equals(columncode)){
							++planNotEmptyRight;
							recommendPlan2_2= dt_jk.getString(i, "textvalue");
						}
						if("recommendPlan2_3".equals(columncode)){
							++planNotEmptyRight;
							recommendPlan2_3= dt_jk.getString(i, "textvalue");
						}

					}
				}
			}
			if(planNotEmptyLeft>=1&& planNotEmptyRight>=1&&(planNotEmptyRight+planNotEmptyLeft)>=3 ){
				changePlan =" <dl class=\"g-pk-plan\">"
						+ "<dd class=\"g-pk-planli g-pk-planli_left\">"
						+ "<span class=\"sel\" data-plan=\"cpl-plan1\">"+recommendPlan1_1+"</span>";
						if(StringUtil.isNotEmpty(recommendPlan1_2)){
							changePlan = changePlan + "<span data-plan=\"cpl-plan2\">"+recommendPlan1_2+"</span>";
						}
						if(StringUtil.isNotEmpty(recommendPlan1_3)){
							changePlan = changePlan + "<span data-plan=\"cpl-plan3\">"+recommendPlan1_3+"</span>";
						}
						changePlan = changePlan + "</dd>"
						+ "<dd class=\"g-pk-dt\">自选</dd>"
						+ "<dd class=\"g-pk-planli g-pk-planli_right\">"
						+ "<span class=\"sel\" data-plan=\"cpr-plan1\">"+recommendPlan2_1+"</span>";
						if(StringUtil.isNotEmpty(recommendPlan2_2)){
							changePlan = changePlan + "<span data-plan=\"cpr-plan2\">"+recommendPlan2_2+"</span>";
						}
						if(StringUtil.isNotEmpty(recommendPlan2_3)){
							changePlan = changePlan + "<span data-plan=\"cpr-plan3\">"+recommendPlan2_3+"</span>";
						}
						changePlan = changePlan + "</dd>" + "</dl>";
			}
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(" <h3 class=\"g-max-tit f_mi\"><span><em class=\"pk-max\"></em>保障对比</span></h3>");
		sb.append("  <div class=\"g-bzpk-con \"> ");
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				sb.append("<dl class=\"g-bzpk-dl\">"
						+ " <dd class=\"g-pk-left hovercs\"><span style=\"width:"+dt.getString(i, "Percent1")+"\">");
						if("健康险".equals(distinction)){
							sb.append("<em class=\"cpl-plan1\" >"+dt.getString(i, "DutyAmnt1")+"</em>"
							+ "<em class=\"cpl-plan2\" >"+dt.getString(i, "prop1")+"</em>"
							+ "<em class=\"cpl-plan3\" >"+dt.getString(i, "prop2")+"</em>");
						}else{
							sb.append("<em >"+dt.getString(i, "DutyAmnt1")+"</em>");
						}
						sb.append("</span><div class=\"clear\"></div> "
						+ " <div class=\"g-pk-desf\"><p>"+dt.getString(i, "DutyDesc1")+"</p></div> </dd>"
						+ " <dd class=\"g-pk-dt\">"+dt.getString(i, "DutyName")+"</dd>"
						+ " <dd class=\"g-pk-right hovercs\"><span style=\"width:"+dt.getString(i, "Percent2")+"\">");
						if("健康险".equals(distinction)){
							sb.append("<em  class=\"cpr-plan1\">"+dt.getString(i, "DutyAmnt2")+"</em>"
							+ "<em class=\"cpr-plan2\" >"+dt.getString(i, "prop3")+"</em>"
							+ "<em class=\"cpr-plan3\" >"+dt.getString(i, "prop4")+"</em>");
						}else{
							sb.append("<em >"+dt.getString(i, "DutyAmnt2")+"</em>");
						}
						sb.append("</span><div class=\"clear\"></div> "
						+ " <div class=\"g-pk-desf\" ><p>"+dt.getString(i, "DutyDesc2")+"</p></div> "
						+ "</dd>  </dl> " );
			}
		}
		
		  
		sb.append(changePlan);
		sb.append("</div>");
		return sb.toString();
	}
	
	/**
	 * 拼装PK优势信息
	 * @param articleId
	 * @return PK优势信息
	 */
	private String getPKAdvantageInfo(String articleId) {
		int Maxsize = 40;
		DataTable dt = new QueryBuilder("select OrderFlag, Info1, Info2 from PKProductAdvantageInfo where ArticleId = ? order by OrderFlag asc", articleId).executeDataTable();
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"gys-pk-con\"> ");
		sb.append(" <h3 class=\"g-max-tit f_mi\"><span><em class=\"pk-max pk-max1\"></em>优势PK</span></h3>");
		if (dt != null && dt.getRowCount() > 0) {
			int rowCount = dt.getRowCount();
			// 产品1优势信息html
			StringBuffer advantage1Sb = new StringBuffer();
			advantage1Sb.append(" <dl class=\"gys-pk-dl gys-pk-dll gys-pk-dd6 animated slideInLeft\" style=\"display: block;\">");
			// 产品2优势信息html
			StringBuffer advantage2Sb = new StringBuffer();
			advantage2Sb.append(" <dl class=\"gys-pk-dl gys-pk-dlr gys-pk-dd5 animated slideInRight\" style=\"display: block;\">");
			// 显示序号
			String ShowNo = "";
			// 产品1优势信息
			String info1 = "";
			String info1_1 = "";
			// 产品2优势信息
			String info2 = "";
			String info2_2 = "";
			for (int i = 0; i < rowCount; i++) {
				ShowNo = dt.getString(i, "OrderFlag");
				info1 = dt.getString(i, "Info1");
				if(info1.length()>=50){
					info1_1 = info1.substring(0, Maxsize-1)+"...";
				}else{
					info1_1 = info1;
				}
				info2 = dt.getString(i, "Info2");
				if(info2.length()>=50){
					info2_2 = info2.substring(0, Maxsize-1)+"...";
				}else{
					info2_2 = info2;
				}
				if (StringUtil.isNotEmpty(info1)) {
					advantage1Sb.append("<dd class=\"gys-dd"+ShowNo+" hovercs\"><em>"+ShowNo+"</em><span>"+info1_1+"</span>");
					advantage1Sb.append(" <div  class=\"g-pk-desd\"> <i class=\"g-pk-cficon\"></i> <p>"+info1+"</p> <i class=\"g-pk-jiao\"></i></div></dd> ");
 
				}
				if (StringUtil.isNotEmpty(info2)) {
					advantage2Sb.append("<dd class=\"gys2-dd"+ShowNo+" hovercs\"><em>"+ShowNo+"</em><span>"+info2_2+"</span>");
					advantage2Sb.append("<div  class=\"g-pk-desd\"><i class=\"g-pk-cficon\"></i><p>"+info2+"</p><i class=\"g-pk-jiao\"></i></div></dd>");
				}
			}
			
			advantage1Sb.append("</dl>");
			advantage2Sb.append("</dl>");
			sb.append(advantage1Sb);
			sb.append(advantage2Sb);
		}
		
		sb.append("<div class=\"clear\"></div></div>");
		
		return sb.toString();
	}
	
	/**
	 * 拼装行程推荐html
	 * @param map
	 * @param articleId
	 * @param baoxianfenlei
	 * @return 行程推荐html
	 */
	private String getRecommentSchedule(Map<String, String> map, String articleId ,String baoxianfenlei) {
		String columncode="",cpl_plan2_1left = "",cpl_plan2_2left = "",cpl_plan3_1left = "",cpl_plan3_2left = ""
				,cpl_plan2_1Right = "",cpl_plan2_2Right = "",cpl_plan3_1Right = "",cpl_plan3_2Right = "",textvalue="";
		//cpl_plan2_1= 计划2金额（现价），cpl_plan2_2=计划2金额（原价）
		if("健康险".equals(baoxianfenlei)){
			DataTable dt = new QueryBuilder("SELECT columncode,textvalue FROM zdcolumnvalue WHERE relaId=? AND columncode LIKE 'jihuajine%' ", articleId).executeDataTable();
			if(dt != null && dt.getRowCount() > 0){
				for(int i = 0; i<dt.getRowCount();i++){
					if(StringUtil.isNotEmpty(dt.getString(i, "columncode"))){
						columncode = dt.getString(i, "columncode");
						textvalue =  dt.getString(i, "textvalue");
						if(",".equals(textvalue)){
							continue;
						}
						if("jihuajine2_1".equals(columncode)){
							cpl_plan2_1left="￥"+ dt.getString(i, "textvalue").split(",")[0];
							if("￥".equals(cpl_plan2_1left)){
								cpl_plan2_1left="";
							}
							if(dt.getString(i, "textvalue").split(",").length>1){
								cpl_plan2_1Right="￥"+ dt.getString(i, "textvalue").split(",")[1];
							}
						}
						if("jihuajine2_2".equals(columncode)){
							cpl_plan2_2left="￥"+dt.getString(i, "textvalue").split(",")[0];
							if("￥".equals(cpl_plan2_2left)){
								cpl_plan2_2left="";
							}
							if(dt.getString(i, "textvalue").split(",").length>1){
								cpl_plan2_2Right= "￥"+dt.getString(i, "textvalue").split(",")[1]; 
							}
						}
						if("jihuajine3_1".equals(columncode)){
							cpl_plan3_1left="￥"+ dt.getString(i, "textvalue").split(",")[0];
							if("￥".equals(cpl_plan3_1left)){
								cpl_plan3_1left="";
							}
							if(dt.getString(i, "textvalue").split(",").length>1){
								cpl_plan3_1Right="￥"+ dt.getString(i, "textvalue").split(",")[1];
							}
						}
						if("jihuajine3_2".equals(columncode)){
							cpl_plan3_2left="￥"+dt.getString(i, "textvalue").split(",")[0];
							if("￥".equals(cpl_plan3_2left)){
								cpl_plan3_2left="";
							}
							if(dt.getString(i, "textvalue").split(",").length>1){
								cpl_plan3_2Right="￥"+ dt.getString(i, "textvalue").split(",")[1];
							}
						}
					}
				}
			}
		}
		
		String PkOrDaysStr = " <span class=\"g-pk-icon\"></span> ";
		if("旅游险".equals(baoxianfenlei)){
			PkOrDaysStr = PkOrDays(map,articleId);
		}
		String weixiProDetail = " exturl=\"http://www.kaixinbao.com/DGtlink\" vlpageid=\"DGtlink\" onclick=\"javascript:void(0);return(VL_FileDL(this));return false;\" ";
		StringBuffer sb = new StringBuffer();
		sb.append(" <div class=\"plan_title clearfix\"> "
				+ " <dl class=\"plan_pay\"> <dt><span>推荐一</span> <a href=\""+map.get("ProductUrl1")+"\" "+weixiProDetail+">"+map.get("ProductName1")+"</a></dt> ");
		if("健康险".equals(baoxianfenlei)){
			sb.append(" <dd  class=\"cpl-plan1\">");
		}else{
			sb.append(" <dd>");
		}
		sb.append( " <em name=\"R_Ajax_PKPrice_"+map.get("ProductId1")+"\" >￥"+map.get("DiscountPrice1")+"</em>"
		+ " <i name=\"Clear_Ajax_PKPrice_"+map.get("ProductId1")+"\">");
		if (!map.get("DiscountPrice1").equals(map.get("Prem1"))) {
			sb.append("￥" + map.get("Prem1"));
		}
		sb.append("</i><a href=\""+map.get("ProductUrl1")+"\"  "+weixiProDetail+">去看看</a></dd> ");
		if("健康险".equals(baoxianfenlei)){
			sb.append("  <dd class=\"cpl-plan2\"><em>"+cpl_plan2_1left+"</em><i>"+cpl_plan2_2left+"</i><a href=\""+map.get("ProductUrl1")+"\"  "+weixiProDetail+">去看看</a></dd>");
			sb.append("  <dd class=\"cpl-plan3\"><em>"+cpl_plan3_1left+"</em><i>"+cpl_plan3_2left+"</i><a href=\""+map.get("ProductUrl1")+"\"  "+weixiProDetail+">去看看</a></dd>");
		}
		sb.append("</dl> ");
		sb.append(PkOrDaysStr);
		sb.append(" <dl class=\"plan_pay plan_pay_right\"> <dt><span>推荐二</span> <a href=\""+map.get("ProductUrl2")+"\" "+weixiProDetail+">"+map.get("ProductName2")+"</a></dt> ");
				if("健康险".equals(baoxianfenlei)){
					sb.append(" <dd class=\"cpr-plan1\" >");
				}else{
					sb.append(" <dd>");
				}
		sb.append("<em name=\"R_Ajax_PKPrice_"+map.get("ProductId2")+"\" >￥"+map.get("DiscountPrice2")+"</em>"
				+ " <i name=\"Clear_Ajax_PKPrice_"+map.get("ProductId2")+"\">");
		if (!map.get("DiscountPrice2").equals(map.get("Prem2"))) {
			sb.append("￥" + map.get("Prem2"));
		}
		sb.append("</i><a href=\""+map.get("ProductUrl2")+"\"  "+weixiProDetail+">去看看</a></dd> ");
		if("健康险".equals(baoxianfenlei)){
			sb.append("  <dd class=\"cpr-plan2\"><em>"+cpl_plan2_1Right+"</em><i>"+cpl_plan2_2Right+"</i><a href=\""+map.get("ProductUrl1")+"\"  "+weixiProDetail+">去看看</a></dd>");
			sb.append("  <dd class=\"cpr-plan3\"><em>"+cpl_plan3_1Right+"</em><i>"+cpl_plan3_2Right+"</i><a href=\""+map.get("ProductUrl1")+"\"  "+weixiProDetail+">去看看</a></dd>");
		}
		sb.append("</dl>  </div> " );
		return sb.toString();
	}
	
	/**
	 * 推荐产品pk是否使用行程日历
	 * 当两款产品保障期限都只有一个并且相同的情况，不需要用户选择行程，不显示行程天数及选择行程的日期
	 * @return
	 */
	private String PkOrDays(Map<String, String> map,String articleId){
		String str ="";
		boolean flag = true;
		DataTable dt = new QueryBuilder("select FactoryValue from PKProductFactoryInfo where RiskCode in (?, ?) and FactoryType = 'Period'", map.get("ProductId1"), map.get("ProductId2")).executeDataTable();
		if(dt == null || dt.getRowCount()<=1){//没有年期，不可选择年期对比
			flag = false;
		}
		if (dt != null && dt.getRowCount() == 2) {
			if (dt.getString(0, 0).equals(dt.getString(1, 0))) {
				flag = false;
			} else {
				if (checkYearPeriod(dt.getString(0, 0)) && checkYearPeriod(dt.getString(1, 0))) {//一年期校验
					flag = false;
				}
			}
		}

		if(flag){
			str =" <div class=\"plan_days\"> "
            + "<p id=\"defaultPeriod\">" + map.get("InitPeriod").replace("D", "天") + "行程</p>"
            + "<a class=\"titcons\" href=\"javascript:void(0)\" exturl=\"http://www.kaixinbao.com/DGdate\" vlpageid=\"DGdate\" >自选行程 ></a> " 
            	+ "<div class=\"ga-again-time  tagshow cf\" style=\"\"><em class=\"ag-jiao-s\"></em> " 
            	+ "<table class=\"ga-again-table\"><tbody><tr><td class=\"ag-again-td\">出发日期：</td><td> " 
            	+ "<input onblur=\"isBirthDate('txtStartDay','DayE')\" onfocus=\"ErroClear('DayE')\" onclick=\"WdatePicker({skin:'whyGreen',minDate:'%y-%M-#{%d+1}'})\" "
            + "name=\"txtStartDay\" class=\"input_selectday\" id=\"txtStartDay\" type=\"text\"></td></tr><tr><td class=\"ag-again-td\">结束日期：</td><td> "
            + "<input onblur=\"isBirthDate('txtEndDay','DayE')\" onfocus=\"ErroClear('DayE')\" class=\"input_selectday\" onclick=\"WdatePicker({skin:'whyGreen',minDate:'%y-%M-{%d+1}'})\" "
           + " id=\"txtEndDay\" name=\"txtEndDay\" type=\"text\"></td></tr><tr><td colspan=\"2\"><div id=\"DayE\" style=\"display:none\" class=\"day-error\"></div> "
           + "<a class=\"g-again-ok\" href=\"javascript:void(0)\" exturl=\"http://www.kaixinbao.com/DGbutton\" vlpageid=\"DGbutton\"  "
        		   + " onclick=\"chooseSchedule('txtStartDay','txtEndDay','"+articleId+"');return(VL_FileDL(this));return false;\">确定</a><span class=\"g-again-no\">取消</span> "
        		   + " </td></tr></tbody></table></div> "
          + "</div>";
		}else{
			str=" <span class=\"g-pk-icon\"></span>";
		}
		
		return str;
	}
	
	/**
	 * 校验是否是一年期
	 * @param period
	 * @return
	 */
	private boolean checkYearPeriod(String period) {
		if (period.endsWith("D")) {
			if ("365".equals(period.substring(0, period.length()-1))) {
				return true;
			}
		} else if (period.endsWith("Y")) {
			if ("1".equals(period.substring(0, period.length()-1))) {
				return true;
			}
		} else if (period.endsWith("M")) {
			if ("12".equals(period.substring(0, period.length()-1))) {
				return true;
			}
		}
		return false;
	}

	
	/**
	 * 获取留言点评
	 * @param period
	 * @return
	 */
	private String getmessageBoard(String productid1,String productid2) {
		String AddUser1 = "", AddUser2 = "";
		String sql = "SELECT AddUser,DATE_FORMAT(ADDTIME,'%Y-%m-%d %H:%i') AS 'AddTime', content  , CASE a.grade WHEN 'k1' THEN 'vip_k1' WHEN 'k2' THEN 'vip_k2' WHEN 'vip' THEN 'vip_kvip'  ELSE 'vip_no' END AS grade  "
				+ "FROM zccomment LEFT JOIN member a ON zccomment.prop1=a.id  WHERE  verifyflag='Y' "
				+ "AND zccomment.relaid=( SELECT prop1 FROM sdsearchrelaproduct WHERE productid=?)";
		DataTable dt = new QueryBuilder(sql,productid1).executeDataTable();
		DataTable dt2 = new QueryBuilder(sql,productid2).executeDataTable();
		
		StringBuffer sb = new StringBuffer();
		sb.append("  <h3 class=\"g-max-tit f_mi\"><span><em class=\"pk-max pk-max2\"></em>用户点评</span></h3> ");
		sb.append("<div class=\"guide_message\"> ");
		
		StringBuffer Sb1 = new StringBuffer();
		Sb1.append(" <div class=\"guide_shop_mes guide_mes_left\"> <div class=\"slideTxtBox1\">  <div class=\"bd\"> <ul> ");
		StringBuffer Sb2 = new StringBuffer();
		Sb2.append("<div class=\"guide_shop_mes guide_mes_right\"> <div class=\"slideTxtBox2\"> <div class=\"bd\"> <ul> ");
		if(dt != null && dt.getRowCount() > 0){
			for (int i = 0; i < dt.getRowCount(); i++) {
				AddUser1 = dt.getString(i, "AddUser");
				if(AddUser1.length()>3){
					AddUser1=AddUser1.substring(0, 3)+"...";
				}
				Sb1.append(" <li> <dl> <dt> <i  class=\"vip_k vip_top vip_k1 vip_k_top "+dt.getString(i, "grade")+"\" title=\"\"></i>"
						+ " <span>"+AddUser1+"</span> <em>"+dt.getString(i, "AddTime")+"</em> </dt>"
						+ " <dd>"+dt.getString(i, "content")+"</dd> </dl> </li>");
			}
		}
		Sb1.append("</div></div></div>");
		if(dt2 != null && dt2.getRowCount() > 0){
			for (int i = 0; i < dt2.getRowCount(); i++) {
				AddUser2 = dt2.getString(i, "AddUser");
				if(AddUser2.length()>3){
					AddUser2=AddUser2.substring(0, 3)+"...";
				}
				Sb2.append("<li> <dl> <dt><i  class=\"vip_k vip_top vip_k1 vip_k_top "+dt2.getString(i, "grade")+"\" title=\"\"></i>"
						+ "<span>"+AddUser2+"</span><em>"+dt2.getString(i, "AddTime")+"</em></dt>"
						+ "<dd>"+dt2.getString(i, "content")+"</dd> </dl></li>" );
			}
		}
		Sb2.append("</div></div></div>");
		
		sb.append(Sb1);
		sb.append(Sb2);
		sb.append("</div></div>");
		return sb.toString();
	}
	
}