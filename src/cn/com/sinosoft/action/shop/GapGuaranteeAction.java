 package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.Member;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 缺口保障处理类
 * ============================================================================
 * 
 * KEY:SINOSOFT66590FE3DF97C81824D62C168A48301D
 * ============================================================================
 */

@ParentPackage("member")
public class GapGuaranteeAction extends BaseShopAction {

	public String count;
	
	public Map<String, String> data = new HashMap<String, String>();
	
	public String getCount() {
	
		return count;
	}

	
	public void setCount(String count) {
		
		this.count = count;
	}
	
	
	public Map<String, String> getData() {
	
		return data;
	}


	
	public void setData(Map<String, String> data) {
	
		this.data = data;
	}
	
	/**
	 * 
	 * @Title: queryOrder
	 * @Description: TODO(缺口保障存档)
	 * @return String 返回类型
	 * @author
	 */
	public String Gapguarantee() {
		Member mem = getLoginMember();
		
		if (mem == null) {
			data.put("status", "N");
			data.put("message", "请登录！");
			return ajaxJson(data);	
		}
		String memberid = mem.getId(); 
		String sql = "select z1.CodeName as hometype1, z2.CodeName as liveprovince1, "
				+ "if(g.sex = 'M','男','女') as sex1 ,"
				+ "if(g.socialsecurity = 'Y','有','无') as socialsecurity1 ,"
				+ "if(g.supportparents = 'Y','是','否') as supportparents1 ,"
				+ "if(g.spousesicialsecurity = 'Y','是','否') as spousesicialsecurity1 ,"
				+ "g.* "
				+ "from gapguaranteearchive g,zdcode z1 ,zdcode z2 "
				+ "where memberid = '"
				+ memberid
				+ "' and z1.codetype = 'GapGuarantee.munu3' "
				+ "and z2.codetype = 'GapGuaranteeProvinceData' "
				+ "and z1.codevalue = g.hometype "
				+ "and z2.codevalue = g.liveprovince "
				+ "order by createddatetime desc";
				
		QueryBuilder qb = new QueryBuilder(sql);
		DataTable dt = qb.executeDataTable();
		
		Calendar now = Calendar.getInstance();  
		String birthday = dt.getString((Integer.parseInt(count) - 1), "birthday").substring(0, 4);
		String workstartdate = dt.getString((Integer.parseInt(count) - 1), "workstartdate").substring(0, 4);
		String spousesex;
		String copywritingman1 = "风华正茂男青年一枚，除了要在事业上打拼，家庭更是您必须承担的责任；";
		String copywritingman2 = "恭喜您迈入萌叔的行列，成为拥有美好家庭的人生赢家；";
		String copywritingman3 = "打拼多年，您或许已经功成身退，但仍需要在保卫家人的路上继续前行；";
		String copywritingwoman1 = "恭喜您晋级为辣妈一族，孩子是您幸福的源泉，家庭是您温暖的港湾；";
		String copywritingwoman2 = "打拼多年，您或许已经功成身退，但仍需要在保卫家人的路上继续前行；";
		
		data.put("hometype", dt.getString((Integer.parseInt(count) - 1), "hometype1"));
		data.put("sex", dt.getString((Integer.parseInt(count) - 1), "sex1"));
		if ("M".equals(dt.getString((Integer.parseInt(count) - 1), "sex"))) {
			spousesex = "女主人";
			if (now.get(Calendar.YEAR)-Integer.parseInt(birthday) < 35) {
				data.put("copywriting", copywritingman1);
			}else if (now.get(Calendar.YEAR)-Integer.parseInt(birthday) >= 35 && now.get(Calendar.YEAR)-Integer.parseInt(birthday) <= 50) {
				data.put("copywriting", copywritingman2);
			}else{
				data.put("copywriting", copywritingman3);
			}
		}else{
			spousesex = "男主人";
			if (now.get(Calendar.YEAR)-Integer.parseInt(birthday) <= 50) {
				data.put("copywriting", copywritingwoman1);
			}else{
				data.put("copywriting", copywritingwoman2);
			}
		}
		data.put("age",String.valueOf(now.get(Calendar.YEAR)-Integer.parseInt(birthday)));
		data.put("socialsecurity", dt.getString((Integer.parseInt(count) - 1), "socialsecurity1"));
		String annualearningsstr = dt.getString((Integer.parseInt(count) - 1), "annualearnings");
		data.put("annualearnings",annualearningsstr.substring(0,annualearningsstr.length()-2));
		data.put("liveprovince1", dt.getString((Integer.parseInt(count) - 1), "liveprovince1"));
		data.put("workstartdate", String.valueOf(now.get(Calendar.YEAR)-Integer.parseInt(workstartdate)));
		data.put("supportparents", dt.getString((Integer.parseInt(count) - 1), "supportparents1"));
		String alimonystr= dt.getString((Integer.parseInt(count) - 1), "alimony");
		data.put("alimony", alimonystr.substring(0,alimonystr.length()-2));
		String householdnetliabilitiesstr = dt.getString((Integer.parseInt(count) - 1), "householdnetliabilities");
		data.put("householdnetliabilities",householdnetliabilitiesstr.substring(0, householdnetliabilitiesstr.length()-2));
		data.put("guarantee", dt.getString((Integer.parseInt(count) - 1), "guarantee"));
		
		BigDecimal homegetinnum1= new BigDecimal(dt.getString((Integer.parseInt(count) - 1), "annualearnings"));
		
		BigDecimal homegetinnum2 = new BigDecimal(0);
		if("1".equals(dt.getString((Integer.parseInt(count) - 1), "hometype")) || "2".equals(dt.getString((Integer.parseInt(count) - 1), "hometype"))){
			String spousebirthday = dt.getString((Integer.parseInt(count) - 1), "spousebirthday").substring(0, 4);
			String spouseworkstartdate = dt.getString((Integer.parseInt(count) - 1), "spouseworkstartdate").substring(0, 4);
			data.put("spouseage",String.valueOf(now.get(Calendar.YEAR)-Integer.parseInt(spousebirthday)));
			data.put("spousesex",spousesex);
			data.put("spouseworkstartdate", String.valueOf(now.get(Calendar.YEAR)-Integer.parseInt(spouseworkstartdate)));
			data.put("spousesicialsecurity", dt.getString((Integer.parseInt(count) - 1), "spousesicialsecurity1"));
			String spouseannualearningsstr = dt.getString((Integer.parseInt(count) - 1), "spouseannualearnings");
			data.put("spouseannualearnings", spouseannualearningsstr.substring(0, spouseannualearningsstr.length()-2));
			homegetinnum2 = new BigDecimal(spouseannualearningsstr);
		}
		
		String province = dt.getString((Integer.parseInt(count) - 1), "liveprovince");
		String sqlprovince = "select * from zdcode where codetype = 'GapGuaranteeProvinceData' and codevalue = '" + province + "'";
		QueryBuilder qb1 = new QueryBuilder(sqlprovince);
		DataTable dt1 = qb1.executeDataTable();
		DecimalFormat df = new DecimalFormat("#.00");
		String prop1 = dt1.getString(0, "prop1");
		String prop2 = dt1.getString(0, "prop2");
		data.put("getin", df.format(new BigDecimal(dt1.getString(0, "prop1"))));
		data.put("putout", df.format(new BigDecimal(dt1.getString(0, "prop2"))));
		BigDecimal homegetinnum = homegetinnum1.add(homegetinnum2);
		BigDecimal homeputoutnum = homegetinnum.multiply(new BigDecimal(prop2)).divide(new BigDecimal(prop1), 2, RoundingMode.HALF_UP);
		data.put("homegetin", String.valueOf(df.format(homegetinnum)));
		data.put("homeputout", homeputoutnum.toString());
		
		long guarantee =  dt.getLong((Integer.parseInt(count) - 1), "guarantee");
		 short[] result;  
		 result = new short[4] ;  
		calculateLong2Primitive(guarantee,result);
		data.put("GapGuasrantee1", Short.toString(result[0]));
		data.put("GapGuasrantee2", Short.toString(result[1]));
		data.put("GapGuasrantee3", Short.toString(result[2]));
		data.put("GapGuasrantee4", Short.toString(result[3]));
		Map<String, String[]> documentMap = new HashMap<String, String[]>();
		documentMap = recommendInsurance((int)result[0],(int)result[1],(int)result[2],(int)result[3]);
		String[] firstText = documentMap.get("firstRecommend");
		String[] secondText = documentMap.get("secondRecommend");
		data.put("name1", firstText[0]);
		data.put("copywriting1", firstText[1]);
		data.put("copywriting2", firstText[2]);
		if (firstText.length >= 4) {
			data.put("copywriting3", firstText[3]);
		}
		data.put("name2", secondText[0]);
		data.put("copywriting4", secondText[1]);
		data.put("copywriting5", secondText[2]);
		if (secondText.length >= 4) {
			data.put("copywriting6", secondText[3]);
		}
		return "GapGuarantee";
	}
	
	protected void calculateLong2Primitive(long data, short[] results) {

		if (results == null || results.length == 0 || Long.SIZE / Short.SIZE < results.length) {
			return;
		}
		for (int i = 0; i < results.length; i++) {
			long l = (data >> (Short.SIZE * i)) & Short.MAX_VALUE;
			results[results.length - 1 - i] = (short) l;
		}
	}
	
	public Map<String, String[]> recommendInsurance(int life, int health, int education, int pension) {

		Map<String, String[]> documentMap = new HashMap<String, String[]>();
		documentMap.put("life", new String[] { "人寿险", "低保费，高保障", "家庭责任的经济保障", "保额和保障期间均可调整，灵活规划" });
		documentMap.put("health", new String[] { "健康险", "健康保险无需指定受益人，且被保险人和受益人常为同一个人",
				"除重大疾病等保险以外，绝大多数健康保险尤其是医疗费用保险常为一年期的短期合同" });
		documentMap.put("education", new String[] { "教育金", "教育金帐户要专款专用、长期储备、稳健增值", "保障灵活可调。平安幸福是父母对孩子最大的期望" });
		documentMap.put("pension", new String[] { "养老险", "能保障年老后基本生活，实现老有所养", "减轻子女赡养负担", "缴费基数越高，缴费年限越长，相应的待遇也就越高" });

		SortedMap<String, Integer> sortedMap = new TreeMap<String, Integer>();
		sortedMap.put("life", life);
		sortedMap.put("health", health);
		sortedMap.put("education", education);
		sortedMap.put("pension", pension);
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(sortedMap.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {

				return o2.getValue().compareTo(o1.getValue());
			}
		});
		String[] firstText = documentMap.get(list.get(0).getKey());
		String[] secondText = documentMap.get(list.get(1).getKey());
		documentMap = new HashMap<String, String[]>();
		documentMap.put("firstRecommend", firstText);
		documentMap.put("secondRecommend", secondText);
		return documentMap;
	}
	
}
