package cn.com.sinosoft.service.impl;

import cn.com.sinosoft.dao.InsuranceTestDao;
import cn.com.sinosoft.entity.InsuranceTest;
import cn.com.sinosoft.service.InsuranceTestService;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.utility.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InsuranceTestServiceImpl extends BaseServiceImpl<InsuranceTest, String> implements InsuranceTestService {
	@Resource
	private InsuranceTestDao insurancetestdao;

	@Resource
	public void setInsurancetestdao(InsuranceTestDao insurancetestdao) {
		super.setBaseDao(insurancetestdao);
	}

	public List<Map<String, String>> searchInsuranceTest(String userid) {
		// 查询获得保险需求结果列表
		InsuranceTest insurancetest = insurancetestdao.searchInsuranceTest(userid);
		List<Map<String, String>> list_result = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		// 子女
		if ("6".equals(insurancetest.getWho())) {
			map.put("childflag", "Y");
		} else {// 非子女
			int sex = Integer.parseInt(insurancetest.getSex());
			switch (sex) {
			case 1:
				map.put("sex", "男");
				break;
			case 2:
				map.put("sex", "女");
				break;
			}
			int work = Integer.parseInt(insurancetest.getWork());
			switch (work) {
			case 1:
				map.put("work", "公务员单位");
				break;
			case 2:
				map.put("work", "事业编制单位");
				break;
			case 3:
				map.put("work", "国有企业");
				break;
			case 4:
				map.put("work", "中外合资企业");
				break;
			case 5:
				map.put("work", "普通企业");
				break;
			case 6:
				map.put("work", "个体工商户");
				break;
			case 7:
				map.put("work", "无业或退休");
				break;
			case 8:
				map.put("work", "高危职业");
				break;
			}
			int travelnum = Integer.parseInt(insurancetest.getTravelnum());
			switch (travelnum) {
			case 1:
				map.put("travelnum", "无");
				break;
			case 2:
				map.put("travelnum", "10次以下");
				break;
			case 3:
				map.put("travelnum", "10次以上");
				break;
			}
			int loan = Integer.parseInt(insurancetest.getLoan());
			switch (loan) {
			case 1:
				map.put("loan", "无");
				break;
			case 2:
				map.put("loan", "有");
				break;
			case 3:
				map.put("loan", "10次以上");
				break;
			}
		}
		try {
			String age = getAge(DateUtil.parse(insurancetest.getBirthday(), "yyyy-MM-dd"));
			if (age.indexOf("D") != -1) {
				map.put("age", age.substring(0, age.indexOf("D")) + "天");
			} else {
				map.put("age", age.substring(0, age.indexOf("Y")) + "岁");
			}
			int yanglao = Integer.parseInt(insurancetest.getYanglaobaoxian());
			switch (yanglao) {
			case 1:
				map.put("yanglaobaoxian", "无养老保险");
				break;
			case 2:
				map.put("yanglaobaoxian", "社保养老");
				break;
			case 3:
				map.put("yanglaobaoxian", "商业养老保险");
				break;
			case 4:
				map.put("yanglaobaoxian", "社保&商业养老保险");
				break;
			}
			int yiliao = Integer.parseInt(insurancetest.getYiliaobaoxian());
			switch (yiliao) {
			case 1:
				map.put("yiliaobaoxian", "无医疗保险");
				break;
			case 2:
				map.put("yiliaobaoxian", "社保医疗");
				break;
			case 3:
				map.put("yiliaobaoxian", "商业医疗保险");
				break;
			case 4:
				map.put("yiliaobaoxian", "社保&商业医疗保险");
				break;
			}
			int healthstatus = Integer.parseInt(insurancetest.getHealthstatus());
			switch (healthstatus) {
			case 1:
				map.put("healthstatus", "很好");
				break;
			case 2:
				map.put("healthstatus", "正常");
				break;
			case 3:
				map.put("healthstatus", "一般，经常感冒");
				break;
			case 4:
				map.put("healthstatus", "不好，经常生病");
				break;
			}
			int salary = Integer.parseInt(insurancetest.getSalary());
			switch (salary) {
			case 1:
				map.put("salary", "5万以下");
				break;
			case 2:
				map.put("salary", "5万-10万");
				break;
			case 3:
				map.put("salary", "10万-20万");
				break;
			case 4:
				map.put("salary", "20万-50万");
				break;
			case 5:
				map.put("salary", "50万以上");
				break;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
		map.put("userid", insurancetest.getId());
		list_result.add(map);
		return list_result;
	}

	public List<Map<String, String>> searchInsuranceTestList(String pageIndex) {
		// 查询获得保险需求结果列表
		List<InsuranceTest> list = insurancetestdao.searchInsuranceTestList(pageIndex);
		List<Map<String, String>> list_result = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size(); i++) {
			InsuranceTest insurancetest = list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			// 子女
			if ("6".equals(insurancetest.getWho())) {
				map.put("childflag", "Y");
			} else {// 非子女
				int sex = Integer.parseInt(insurancetest.getSex());
				switch (sex) {
				case 1:
					map.put("sex", "男");
					break;
				case 2:
					map.put("sex", "女");
					break;
				}
				int work = Integer.parseInt(insurancetest.getWork());
				switch (work) {
				case 1:
					map.put("work", "公务员单位");
					break;
				case 2:
					map.put("work", "事业编制单位");
					break;
				case 3:
					map.put("work", "国有企业");
					break;
				case 4:
					map.put("work", "中外合资企业");
					break;
				case 5:
					map.put("work", "普通企业");
					break;
				case 6:
					map.put("work", "个体工商户");
					break;
				case 7:
					map.put("work", "无业或退休");
					break;
				case 8:
					map.put("work", "高危职业");
					break;
				}
				int travelnum = Integer.parseInt(insurancetest.getTravelnum());
				switch (travelnum) {
				case 1:
					map.put("travelnum", "无");
					break;
				case 2:
					map.put("travelnum", "10次以下");
					break;
				case 3:
					map.put("travelnum", "10次以上");
					break;
				}
			}
			try {
				int loan = Integer.parseInt(insurancetest.getLoan());
				switch (loan) {
				case 1:
					map.put("loan", "无");
					break;
				case 2:
					map.put("loan", "有");
					break;
				}
				String age = getAge(DateUtil.parse(insurancetest.getBirthday(), "yyyy-MM-dd"));
				if (age.indexOf("D") != -1) {
					map.put("age", age.substring(0, age.indexOf("D")) + "天");
				} else {
					map.put("age", age.substring(0, age.indexOf("Y")) + "岁");
				}
				int yanglao = Integer.parseInt(insurancetest.getYanglaobaoxian());
				switch (yanglao) {
				case 1:
					map.put("yanglaobaoxian", "无养老保险");
					break;
				case 2:
					map.put("yanglaobaoxian", "社保养老");
					break;
				case 3:
					map.put("yanglaobaoxian", "商业养老保险");
					break;
				case 4:
					map.put("yanglaobaoxian", "社保&商业养老保险");
					break;
				}
				int yiliao = Integer.parseInt(insurancetest.getYiliaobaoxian());
				switch (yiliao) {
				case 1:
					map.put("yiliaobaoxian", "无医疗保险");
					break;
				case 2:
					map.put("yiliaobaoxian", "社保医疗");
					break;
				case 3:
					map.put("yiliaobaoxian", "商业医疗保险");
					break;
				case 4:
					map.put("yiliaobaoxian", "社保&商业医疗保险");
					break;
				}
				int healthstatus = Integer.parseInt(insurancetest.getHealthstatus());
				switch (healthstatus) {
				case 1:
					map.put("healthstatus", "很好");
					break;
				case 2:
					map.put("healthstatus", "正常");
					break;
				case 3:
					map.put("healthstatus", "一般，经常感冒");
					break;
				case 4:
					map.put("healthstatus", "不好，经常生病");
					break;
				}
				int salary = Integer.parseInt(insurancetest.getSalary());
				switch (salary) {
				case 1:
					map.put("salary", "5万以下");
					break;
				case 2:
					map.put("salary", "5万-10万");
					break;
				case 3:
					map.put("salary", "10万-20万");
					break;
				case 4:
					map.put("salary", "20万-50万");
					break;
				case 5:
					map.put("salary", "50万以上");
					break;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			map.put("userid", insurancetest.getId());
			list_result.add(map);
		}
		return list_result;
	}

	/**
	 * 
	 * @Title: searchInsuranceTestListBy
	 * @Description: TODO(分页条件查询)
	 * @return List<Map<String,String>> 返回类型
	 * @author zhangjing
	 */
	public Map<String,String> searchInsuranceTestListBy(String pageIndex,String param) {
		String[] params=param.split(",");
		String wheresql="where 1=1 ";
		for (int i = 0; i < params.length; i++) {
			if(!("".equals(params[i])||params[i]==null)){
				switch (i) {
				case 0:
					if("3".equals(params[i])){
						wheresql=wheresql+" and who='6'";
					}
					else{
						wheresql=wheresql+" and sex='"+params[i]+"'";
					}
					break;
				case 1:
					Calendar cal = Calendar.getInstance();
					int yearNow = cal.get(Calendar.YEAR);
					int monthNow = cal.get(Calendar.MONTH) + 1;
					int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
					int min=(Integer.parseInt(params[i])-1)*10;
					int max=(Integer.parseInt(params[i]))*10;
					int minbirthday=yearNow-min;
					int maxbirthday=yearNow-max;
					wheresql=wheresql+" and DATE_FORMAT(birthday,'%Y-%m-%d')<="+"DATE_FORMAT('"+minbirthday+"-"+monthNow+"-"+dayOfMonthNow+"','%Y-%m-%d')";
					wheresql=wheresql+" and DATE_FORMAT(birthday,'%Y-%m-%d')>="+"DATE_FORMAT('"+maxbirthday+"-"+monthNow+"-"+dayOfMonthNow+"','%Y-%m-%d')";
					break;
				case 2:
					wheresql=wheresql+" and work='"+params[i]+"'";
					break;
				case 3:
					wheresql=wheresql+" and travelnum='"+params[i]+"'";
					break;
				case 4:
					wheresql=wheresql+" and yiliaobaoxian='"+params[i]+"'";
					break;
				case 5:
					wheresql=wheresql+" and yanglaobaoxian='"+params[i]+"'";
					break;
				case 6:
					wheresql=wheresql+" and loan='"+params[i]+"'";
					break;
				case 7:
					wheresql=wheresql+" and salary='"+params[i]+"'";
					break;
				}
			}
		}
		// 查询获得保险需求结果列表
		List<InsuranceTest> list = insurancetestdao.searchInsuranceTestListBy(pageIndex,wheresql);
		List<Map<String, String>> list_result = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list.size()-1; i++) {
			InsuranceTest insurancetest = list.get(i);
			Map<String, String> map = new HashMap<String, String>();
			// 子女
			if ("6".equals(insurancetest.getWho())) {
				map.put("childflag", "Y");
			} else {// 非子女
				int sex = Integer.parseInt(insurancetest.getSex());
				switch (sex) {
				case 1:
					map.put("sex", "男");
					break;
				case 2:
					map.put("sex", "女");
					break;
				}
				int work = Integer.parseInt(insurancetest.getWork());
				switch (work) {
				case 1:
					map.put("work", "公务员单位");
					break;
				case 2:
					map.put("work", "事业编制单位");
					break;
				case 3:
					map.put("work", "国有企业");
					break;
				case 4:
					map.put("work", "中外合资企业");
					break;
				case 5:
					map.put("work", "普通企业");
					break;
				case 6:
					map.put("work", "个体工商户");
					break;
				case 7:
					map.put("work", "无业或退休");
					break;
				case 8:
					map.put("work", "高危职业");
					break;
				}
				int travelnum = Integer.parseInt(insurancetest.getTravelnum());
				switch (travelnum) {
				case 1:
					map.put("travelnum", "无");
					break;
				case 2:
					map.put("travelnum", "10次以下");
					break;
				case 3:
					map.put("travelnum", "10次以上");
					break;
				}
			}
			try {
				int loan = Integer.parseInt(insurancetest.getLoan());
				switch (loan) {
				case 1:
					map.put("loan", "无");
					break;
				case 2:
					map.put("loan", "有");
					break;
				}
				String age = getAge(DateUtil.parse(insurancetest.getBirthday(), "yyyy-MM-dd"));
				if (age.indexOf("D") != -1) {
					map.put("age", age.substring(0, age.indexOf("D")) + "天");
				} else {
					map.put("age", age.substring(0, age.indexOf("Y")) + "岁");
				}
				int yanglao = Integer.parseInt(insurancetest.getYanglaobaoxian());
				switch (yanglao) {
				case 1:
					map.put("yanglaobaoxian", "无养老保险");
					break;
				case 2:
					map.put("yanglaobaoxian", "社保养老");
					break;
				case 3:
					map.put("yanglaobaoxian", "商业养老保险");
					break;
				case 4:
					map.put("yanglaobaoxian", "社保&商业养老保险");
					break;
				}
				int yiliao = Integer.parseInt(insurancetest.getYiliaobaoxian());
				switch (yiliao) {
				case 1:
					map.put("yiliaobaoxian", "无医疗保险");
					break;
				case 2:
					map.put("yiliaobaoxian", "社保医疗");
					break;
				case 3:
					map.put("yiliaobaoxian", "商业医疗保险");
					break;
				case 4:
					map.put("yiliaobaoxian", "社保&商业医疗保险");
					break;
				}
				int healthstatus = Integer.parseInt(insurancetest.getHealthstatus());
				switch (healthstatus) {
				case 1:
					map.put("healthstatus", "很好");
					break;
				case 2:
					map.put("healthstatus", "正常");
					break;
				case 3:
					map.put("healthstatus", "一般，经常感冒");
					break;
				case 4:
					map.put("healthstatus", "不好，经常生病");
					break;
				}
				int salary = Integer.parseInt(insurancetest.getSalary());
				switch (salary) {
				case 1:
					map.put("salary", "5万以下");
					break;
				case 2:
					map.put("salary", "5万-10万");
					break;
				case 3:
					map.put("salary", "10万-20万");
					break;
				case 4:
					map.put("salary", "20万-50万");
					break;
				case 5:
					map.put("salary", "50万以上");
					break;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);

			}
			map.put("userid", insurancetest.getId());
			list_result.add(map);
		}
		//列表html内容
		String html=getHtml(list_result);
		//分页foote的rhtml内容
		String footer=getPageFooter(Integer.parseInt(pageIndex),Integer.parseInt(list.get(list.size()-1).getMemo()),5);
		Map<String,String> map=new HashMap<String,String>();
		map.put("bodyhtml", html);
		map.put("footerhtml", footer);
		return map;
	}

	/**
	 * 
	 * @Title: getAge
	 * @Description: TODO(计算年龄,满一年返回年数,不满一年返回天数)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private String getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			logger.info("保险测试的年龄大于当前日期");
		}

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		if (age != 0) {
			return age + "Y";
		} else {
			return (dayOfMonthNow - dayOfMonthBirth) + "D";
		}
	}

	/**
	 * 
	 * @Title: getHtml
	 * @Description: TODO(获取分页后的html)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private String getHtml(List<Map<String,String>> list){
		StringBuffer html=new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			Map<String,String> map=list.get(i);
			html.append("<div class=\"test_des_box\">");
			if(!("".equals(map.get("childflag"))||map.get("childflag")==null)){
				html.append("<p class=\"test_des_tit\"><span>子女</span><span>"+map.get("age")+"</span><span>"+map.get("yanglaobaoxian")+"</span><span>"+map.get("yiliaobaoxian")+"</span></p>");
			}
			else{
				html.append("<p class=\"test_des_tit\"><span>"+map.get("sex")+"</span><span>"+map.get("age")+"</span><span>"+map.get("work")+"</span><span>"+map.get("yanglaobaoxian")+"</span><span>"+map.get("yiliaobaoxian")+"</span></p>");
			}
			if(!("".equals(map.get("healthstatus"))||map.get("healthstatus")==null)){
				html.append("<ul class=\"test_des_g\"><li><span class=\"test_des_gt\">健康状况：</span><span>"+map.get("healthstatus")+"</span></li><li>");
			}else{
				html.append("<ul class=\"test_des_g\"><li><span class=\"test_des_gt\">健康状况：</span><span>无</span></li><li>");
			}
			if(!("".equals(map.get("salary"))||map.get("salary")==null)){
				html.append("<span class=\"test_des_gt\">家庭收入：</span><span>"+map.get("salary")+"</span></li>");
			}else{
				html.append("<span class=\"test_des_gt\">家庭收入：</span><span>无</span></li>");
			}
			if(!("".equals(map.get("travelnum"))||map.get("travelnum")==null)){
				html.append("<div class=\"clear\"></div><li><span class=\"test_des_gt\">旅游次数/年：</span><span>"+map.get("travelnum")+"</span></li><li>");
			}else{
				html.append("<div class=\"clear\"></div><li><span class=\"test_des_gt\">旅游次数/年：</span><span>无</span></li><li>");
			}
			if(!("".equals(map.get("loan"))||map.get("loan")==null)){
				html.append("<span class=\"test_des_gt\">有无贷款：</span><span>"+map.get("loan")+"</span></li></ul>");
			}else{
				html.append("<span class=\"test_des_gt\">有无贷款：</span><span>无</span></li></ul>");
			}
			html.append("<a href=\""+Config.getValue("FrontServerContextPath")+"/wj/shop/insurance_test!searchInsuranceTestResultList.action?userid="+map.get("userid")+"\" class=\"go_to_testcon\" target=\"_blank\">查看</a></div>");
		}
		return html.toString();
	}
	/**
	 * 
	* @Title: getPageFooter 
	* @Description: TODO(分页footer) 
	* @return String    返回类型 
	* @author zhangjing
	 */
	private String getPageFooter(int pageIndex,int total,int onePageCount){
		int nextPage = pageIndex + 1;
		int pageCount = new Double(Math.ceil(total * 1.0 / onePageCount)).intValue();
		StringBuffer sb2 = new StringBuffer();
		sb2.append("<div class='plpage'>");
		sb2.append("<div class='plpagecont'>");
		if (pageIndex > 1) {
			sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='pageList(\"" + 1 + "\");'>首页</a></span>");
			sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='pageList(\"" + (pageIndex-1) + "\");'><</a></span>");
		} else {
			sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='pageList(\"" + 1 + "\");'>首页</a></span>");
			sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='pageList(\"" + 1 + "\");'><</a></span>");
		}
		int j = 1;  
		for( j = 1;j<=(total%onePageCount==0?total/onePageCount:(total/onePageCount+1));j++){    
			if(j==pageIndex){
				sb2.append("<span class='plpage04'><a href='javascript:void(0)' onClick='pageList(\"" + j + "\");'><font color = '#FFFFFF'> "+j+"</font></a></span>&nbsp;");
			}
			else if(pageCount<6){
				sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='pageList(\"" + j + "\");'>"+ j+"</a></span>&nbsp;");
			}
		}
		if (pageIndex  != pageCount && pageCount > 0) {
			sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='pageList(\"" + nextPage + "\");'>></a></span>");
			sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='pageList(\"" + pageCount + "\");'>尾页</a></span>");
		} else {
			sb2.append("<span class='plpage01'><a href='javascript:void(0)' onClick='pageList(\"" + pageCount + "\");'>></a></span>");
			sb2.append("<span class='plpage02'><a href='javascript:void(0)' onClick='pageList(\"" + pageCount + "\");'>尾页</a></span>");
		}

		sb2.append("&nbsp;&nbsp;共" + (total%onePageCount==0?total/onePageCount:(total/onePageCount+1)) + "页&nbsp;&nbsp;");
		sb2.append("&nbsp;&nbsp;转到第<input id='_PageBar_Index_' type='text' size='4' style='width:30px' ");
		sb2.append("style='' onKeyUp=\"value=value.replace(/\\D/g,'')\">页");

		sb2.append("&nbsp;&nbsp;<input type='button' onclick=\"if(/[^\\d]/.test(document.getElementById('_PageBar_Index_').value)){alert('错误的页码');$('_PageBar_Index_').focus();}else if(document.getElementById('_PageBar_Index_').value>"
						+ pageCount
						+ "){alert('错误的页码');document.getElementById('_PageBar_Index_').focus();}else{var PageIndex = (document.getElementById('_PageBar_Index_').value)>0?document.getElementById('_PageBar_Index_').value:1;if(PageIndex==1){pageList(PageIndex);}else{pageList(PageIndex);}}\" style='' value='跳转'></td>");
		
		sb2.append("</div>");
		sb2.append("</div>");
		return sb2.toString();
	}
}
