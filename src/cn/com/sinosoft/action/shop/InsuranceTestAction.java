package cn.com.sinosoft.action.shop;

import cn.com.sinosoft.entity.InsuranceTest;
import cn.com.sinosoft.entity.InsuranceTestResult;
import cn.com.sinosoft.entity.Member;
import cn.com.sinosoft.service.InsuranceTestResultService;
import cn.com.sinosoft.service.InsuranceTestService;
import cn.com.sinosoft.service.MemberService;
import com.sinosoft.cms.pub.PubFun;
import com.sinosoft.framework.Config;
import com.sinosoft.framework.GetDBdata;
import com.sinosoft.framework.data.DataTable;
import com.sinosoft.framework.data.QueryBuilder;
import com.sinosoft.framework.data.Transaction;
import com.sinosoft.framework.utility.DateUtil;
import com.sinosoft.framework.utility.LogUtil;
import com.sinosoft.framework.utility.StringUtil;
import com.sinosoft.inter.ActionUtil;
import com.sinosoft.platform.pub.NoUtil;
import com.sinosoft.schema.SDCouponInfoSchema;
import com.sinosoft.schema.SDCouponProvideLogSchema;
import com.sinosoft.schema.ZDConfigSchema;
import com.sinosoft.schema.memberSchema;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/***
 * 
 * @ClassName: InsuranceTestAction
 * @Description: TODO(保险需求测试)
 * @author zhangjing
 * @date 2014-3-3
 * 
 */
@ParentPackage("shop")
public class InsuranceTestAction extends BaseShopAction {
	SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private InsuranceTest insurance;
	private List<Map<String, String>> list_ly = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_yw = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_rs = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_jk = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_jy = new ArrayList<Map<String, String>>();
	// 推荐产品列表
	private List<Map<String, String>> list_one = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_two = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_three = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_four = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_five = new ArrayList<Map<String, String>>();
	private List<Map<String, String>> list_six = new ArrayList<Map<String, String>>();
	// 险种描述
	private String content_one = "";
	private String content_two = "";
	private String content_three = "";
	private String content_four = "";
	private String content_five = "";
	// 险种标题
	private String title_one = "";
	private String title_two = "";
	private String title_three = "";
	private String title_four = "";
	private String title_five = "";
	// 险种星级
	private String start_one = "";
	private String start_two = "";
	private String start_three = "";
	private String start_four = "";
	private String start_five = "";
	// 投保提醒
	private String warn_one = "";
	private String warn_two = "";
	private String warn_three = "";
	private String warn_four = "";
	private String warn_five = "";
	// 列表页
	private String product_list_one = "";
	private String product_list_two = "";
	private String product_list_three = "";
	private String product_list_four = "";
	private String product_list_five = "";
	private String product_list_six = "";

	// 邮件险种星级
	private String mail_start_one = "";
	private String mail_start_two = "";
	private String mail_start_three = "";
	private String mail_start_four = "";
	private String mail_start_five = "";
	// 邮件投保提醒
	private String warn_one_mail = "";
	private String warn_two_mail = "";
	private String warn_three_mail = "";
	private String warn_four_mail = "";
	private String warn_five_mail = "";
	// 邮件星级图片
	private String[] mail_start = { "xing1_09.gif", "xing2_09.gif", "xing3_09.gif", "xing4_09.gif", "xing5_09.gif" };

	// 产品列表页
	private List<String> list_productid = new ArrayList<String>();
	// 测试结果列表
	private List<Map<String, String>> test_list = new ArrayList<Map<String, String>>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 703746079859585888L;
	// 分别为旅游险、意外险、人寿险、健康险、教育险 的文字描述
	private static String[] title = { "旅游险", "意外险", "人寿险", "健康险", "教育险" };
	private static String[] contentArr = { "出门在外，给自己一份保障，给予全面的一路护航，为让您在出行时有更安心，根据您的出行次数及特点，我们竭诚为您推荐以下两款旅游保险产品！", "防止意外，让自己出行无忧，让家人放心安心，根据您的工作类型及户外保障情况，我们竭诚为您推荐以下两款意外保险产品！",
			"在遭遇意外时，给家人提供一定的经济支撑，减轻家庭负债压力，更能保全家人生活质量。自身责任越大，负债越重，保额需求越大！", "在生病或住院时，支付一定的医药费用，减轻经济负担，不至于因病返贫。事业起步期和家庭建设期的人士，身体是拼搏的本钱，需要重点规划！", "有计划的为子女准备一份教育保险，通过强制储蓄的性质，让您的孩子教育有保障。越早规划，负担越轻！" };
	private static String[] warn = { "需求程度紧迫，建议立即投保！", "需求程度较高，建议您尽快投保！", "需求程度一般，建议您选择性适当投保！" };
	// 车险
	private static String[] chexian_productname = { "太平洋车险", "平安车险", "阳光车险", "大地车险" };
	private static String[] chexian_url = { "/chexian/taipingyang.shtml", "/chexian/pingan.shtml", "/chexian/yangguang.shtml", "/chexian/dadi.shtml" };
	private static String[] class_image = { "/images/max_cpic_logo_03.gif", "/images/max_logo_03.gif", "/images/max_logo_03-11.gif", "/images/max_logo_03-17.gif" };

	@Resource
	private InsuranceTestService insurancetestservice;
	@Resource
	private InsuranceTestResultService insurancetestresultservice;
	@Resource
	private MemberService memberService;
	private Member member = new Member();;

	public void setInsurancetestservice(InsuranceTestService insurancetestservice) {
		this.insurancetestservice = insurancetestservice;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setInsurancetestresultservice(InsuranceTestResultService insurancetestresultservice) {
		this.insurancetestresultservice = insurancetestresultservice;
	}

	/**
	 * 
	 * @Title: searchInsuranceTestList
	 * @Description: TODO(查询保险测试需求列表)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	public String searchInsuranceTestList() {
		String pageIndex = "1";
		test_list = this.insurancetestservice.searchInsuranceTestList(pageIndex);
		return "list";

	}

	/**
	 * 
	 * @Title: searchInsuranceTestListBy
	 * @Description: TODO(分页条件查询)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	public String searchInsuranceTestListBy() {
		String pageIndex = getParameter("page");
		String param = getParameter("param");
		Map<String, String> map = this.insurancetestservice.searchInsuranceTestListBy(pageIndex, param);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put("bodyhtml", map.get("bodyhtml"));
		jsonMap.put("footerhtml", map.get("footerhtml"));
		return ajaxJson(jsonMap);

	}

	/**
	 * 
	 * @Title: searchInsuranceTestResultList
	 * @Description: TODO(根据保险测试需求列表查询结果)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	public String searchInsuranceTestResultList() {
		String userid = getParameter("userid");
		test_list = insurancetestservice.searchInsuranceTest(userid);
		List<List<Map<String, String>>> list = this.insurancetestresultservice.searchInsuranceTestResultList(userid);
		list_one = list.get(0);
		list_two = list.get(1);
		list_three = list.get(2);
		list_four = list.get(3);
		list_five = list.get(4);
		// 险种描述
		if (!"0".equals(list_one.get(0).get("level"))) {
			content_one = contentArr[0];
		}
		if (!"0".equals(list_two.get(0).get("level"))) {
			content_two = contentArr[1];
		}
		if (!"0".equals(list_three.get(0).get("level"))) {
			content_three = contentArr[2];
		}
		if (!"0".equals(list_four.get(0).get("level"))) {
			content_four = contentArr[3];
		}
		if (!"0".equals(list_five.get(0).get("level"))) {
			content_five = contentArr[4];
		}
		// 险种标题
		title_one = title[0];
		title_two = title[1];
		title_three = title[2];
		title_four = title[3];
		title_five = title[4];
		// 险种星级
		start_one = "need_name need_grade" + list_one.get(0).get("level");
		start_two = "need_name need_grade" + list_two.get(0).get("level");
		start_three = "need_name need_grade" + list_three.get(0).get("level");
		start_four = "need_name need_grade" + list_four.get(0).get("level");
		start_five = "need_name need_grade" + list_five.get(0).get("level");
		// 投保提醒
		if (0 < Integer.parseInt(list_one.get(0).get("level")) && Integer.parseInt(list_one.get(0).get("level")) < 3 && !"0".equals(list_one.get(0).get("level"))) {
			warn_one = warn[2];
		} else if (Integer.parseInt(list_one.get(0).get("level")) == 3) {
			warn_one = warn[1];
		} else if (3 < Integer.parseInt(list_one.get(0).get("level"))) {
			warn_one = warn[0];
		}
		if (0 < Integer.parseInt(list_two.get(0).get("level")) && Integer.parseInt(list_two.get(0).get("level")) < 3 && !"0".equals(list_two.get(0).get("level"))) {
			warn_two = warn[2];
		} else if (Integer.parseInt(list_two.get(0).get("level")) == 3) {
			warn_two = warn[1];
		} else if (3 < Integer.parseInt(list_two.get(0).get("level"))) {
			warn_two = warn[0];
		}
		if (0 < Integer.parseInt(list_three.get(0).get("level")) && Integer.parseInt(list_three.get(0).get("level")) < 3 && !"0".equals(list_three.get(0).get("level"))) {
			warn_three = warn[2];
		} else if (Integer.parseInt(list_three.get(0).get("level")) == 3) {
			warn_three = warn[1];
		} else if (3 < Integer.parseInt(list_three.get(0).get("level"))) {
			warn_three = warn[0];
		}
		if (0 < Integer.parseInt(list_four.get(0).get("level")) && Integer.parseInt(list_four.get(0).get("level")) < 3 && !"0".equals(list_four.get(0).get("level"))) {
			warn_four = warn[2];
		} else if (Integer.parseInt(list_four.get(0).get("level")) == 3) {
			warn_four = warn[1];
		} else if (3 < Integer.parseInt(list_four.get(0).get("level"))) {
			warn_four = warn[0];
		}
		if (0 < Integer.parseInt(list_five.get(0).get("level")) && Integer.parseInt(list_five.get(0).get("level")) < 3 && !"0".equals(list_five.get(0).get("level"))) {
			warn_five = warn[2];
		} else if (Integer.parseInt(list_five.get(0).get("level")) == 3) {
			warn_five = warn[1];
		} else if (3 < Integer.parseInt(list_five.get(0).get("level"))) {
			warn_five = warn[0];
		}
		// 列表页
		String sql = "SELECT z2.URL  FROM zcarticle z1,zccatalog z2 ,sdsearchrelaproduct z3 WHERE z1.id=z3.prop1 AND z1.catalogid=z2.id  AND z3.productid=?";
		if (!"0".equals(list_one.get(0).get("level"))) {
			QueryBuilder qb_list = new QueryBuilder(sql);
			qb_list.add(list_one.get(0).get("productid"));
			DataTable dt = qb_list.executeDataTable();
			if (dt.getRowCount() > 0) {
				product_list_one = Config.getValue("FrontServerContextPath") + "/" + dt.getString(0, 0);
			} else {
				product_list_one = Config.getValue("FrontServerContextPath");
			}
		}
		if (!"0".equals(list_two.get(0).get("level"))) {
			QueryBuilder qb_list = new QueryBuilder(sql);
			qb_list.add(list_two.get(0).get("productid"));
			DataTable dt = qb_list.executeDataTable();
			if (dt.getRowCount() > 0) {
				product_list_two = Config.getValue("FrontServerContextPath") + "/" + dt.getString(0, 0);
			} else {
				product_list_two = Config.getValue("FrontServerContextPath");
			}
		}
		if (!"0".equals(list_three.get(0).get("level"))) {
			QueryBuilder qb_list = new QueryBuilder(sql);
			qb_list.add(list_three.get(0).get("productid"));
			DataTable dt = qb_list.executeDataTable();
			if (dt.getRowCount() > 0) {
				product_list_three = Config.getValue("FrontServerContextPath") + "/" + dt.getString(0, 0);
			} else {
				product_list_three = Config.getValue("FrontServerContextPath");
			}
		}
		if (!"0".equals(list_four.get(0).get("level"))) {
			QueryBuilder qb_list = new QueryBuilder(sql);
			qb_list.add(list_four.get(0).get("productid"));
			DataTable dt = qb_list.executeDataTable();
			if (dt.getRowCount() > 0) {
				product_list_four = Config.getValue("FrontServerContextPath") + "/" + dt.getString(0, 0);
			} else {
				product_list_four = Config.getValue("FrontServerContextPath");
			}
		}
		if (!"0".equals(list_five.get(0).get("level"))) {
			QueryBuilder qb_list = new QueryBuilder(sql);
			qb_list.add(list_five.get(0).get("productid"));
			DataTable dt = qb_list.executeDataTable();
			if (dt.getRowCount() > 0) {
				product_list_five = Config.getValue("FrontServerContextPath") + "/" + dt.getString(0, 0);
			} else {
				product_list_five = Config.getValue("FrontServerContextPath");
			}
		}
		product_list_six = Config.getValue("FrontServerContextPath") + "/chexian/";
		QueryBuilder qb = new QueryBuilder("select car from insurancetest where id=?", userid);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() == 1 && StringUtil.isNotEmpty(String.valueOf(dt.get(0, 0)))) {
			if ("2".equals(dt.get(0, 0))) {
				Map<String, String> map = new HashMap<String, String>();
				int index = new Random().nextInt(3);
				map.put("productname", chexian_productname[index]);
				map.put("url", Config.getValue("FrontServerContextPath") + chexian_url[index]);
				map.put("logolink", Config.getValue("FrontServerContextPath") + class_image[index]);
				list_six.add(map);
				Map<String, String> map_pa = new HashMap<String, String>();
				map_pa.put("productname", "平安驾驶无忧驾驶员人身意外保险");
				map_pa.put("url", Config.getValue("FrontServerContextPath") + "/yiwai-baoxian/251340.shtml");
				map_pa.put("logolink", Config.getValue("FrontServerContextPath") + "/images/max_logo_03.gif");
				product_list_six = Config.getValue("FrontServerContextPath") + "/chexian/";
				list_six.add(map_pa);
			}
		}

		return "listresult";

	}

	/**
	 * 
	 * @Title: insuranceTest
	 * @Description: TODO(保险测试开始页)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	public String insuranceTest() {
		return "index";
	}

	/**
	 * 
	 * @Title: saveInsuranceTestResult
	 * @Description: TODO(保存保险测试结果)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	public String saveInsuranceTestResult() {
		String id = this.insurancetestservice.save(insurance);
		// 分别为旅游险A、意外险B、人寿险E、健康险D、教育险E04
		String riskcode[] = { "A00", "B00", "E00", "D00", "H00" };
		String star = getParameter("star");
		String[] st = star.split(",");
		int[] nums = new int[st.length];
		for (int i = 0; i < st.length; i++) {
			nums[i] = Integer.parseInt(st[i]);
		}
		member.setEmail(insurance.getMail());
		List<InsuranceTestResult> list = new ArrayList<InsuranceTestResult>();
		String birthday = insurance.getBirthday();
		for (int i = 0; i < nums.length; i++) {
			String productId1 = "";
			String productId2 = "";
			switch (i) {
			case 0:
				String destination = insurance.getDestination();
				if (StringUtil.isEmpty(insurance.getDestination())) {
					nums[i] = 0;
					destination = "1";
				}
				// 针对旅游险的条件
				String sql_where = "";
				// 旅游
				switch (Integer.parseInt(destination)) {
				case 1:
					// zccatalog表 A01国内旅游保险
					QueryBuilder qb_gn = new QueryBuilder(" SELECT a.productid,a.productname,a.initprem,a.url,a.logolink FROM  (SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A01' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30' ORDER BY CONVERT(salesvolume,SIGNED)  DESC  LIMIT 10 ) AS a ORDER BY  RAND() LIMIT 2 ");
					DataTable dt = qb_gn.executeDataTable();
					if (dt.getRowCount() > 1) {
						for (int j = 0; j < dt.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt.get(j, 0), DateUtil.getCurrentDate());
							// if (dt.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_ly.size() == 2) {
								break;
							}
//							boolean flag = checkProduct((String) dt.get(j, 0));
//							if (flag) {
//								continue;
//							}
							list_productid.add((String) dt.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt.get(j, 0));
							map.put("productname", (String) dt.get(j, 1));
							map.put("initprem", (String) dt.get(j, 2));
							map.put("url", (String) dt.get(j, 3));
							map.put("logolink", (String) dt.get(j, 4));
							list_ly.add(map);

							if (list_ly.size() == 1) {
								productId1 = list_ly.get(0).get("productid");
							} else if (list_ly.size() == 2) {
								productId2 = list_ly.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							if (j == (dt.getRowCount() - 1) && list_ly.size() < 2) {
								for (int x = 0; x < dt.getRowCount(); x++) {
									if (list_ly.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt.get(x, 0));
										maps.put("productname", (String) dt.get(x, 1));
										maps.put("initprem", (String) dt.get(x, 2));
										maps.put("url", (String) dt.get(x, 3));
										maps.put("logolink", (String) dt.get(x, 4));
										list_ly.add(maps);
										if (list_ly.size() == 1) {
											productId1 = list_ly.get(0).get("productid");
										} else if (list_ly.size() == 2) {
											productId2 = list_ly.get(1).get("productid");
										}
									}
								}
							}
						}
					} else {
						logger.info("保险测试A01国内旅游险的记录不足两条");
					}
					break;
				case 2:
					// A02 境外旅游险
					if ("1".equals(insurance.getSalary())) {
						if ("1".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >0  and  initprem < 50";
						} else if ("2".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >0  and  initprem < 50";
						} else if ("3".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >50 and  initprem < 200";
						} else if ("4".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >500 and  initprem < 1000";
						}

					} else if ("2".equals(insurance.getSalary())) {
						if ("1".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >50 and  initprem < 200";
						} else if ("2".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >50 and  initprem < 200";
						} else if ("3".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >200 and  initprem < 500";
						} else if ("4".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >500 and  initprem < 1000";
						}
					} else if ("3".equals(insurance.getSalary())) {
						if ("1".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >200 and  initprem < 500";
						} else if ("2".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >200 and  initprem < 500";
						} else if ("3".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >500 and  initprem < 1000";
						} else if ("4".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >1000 ";
						}
					} else if ("4".equals(insurance.getSalary())) {
						if ("1".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >200 and  initprem < 500";
						} else if ("2".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >200 and  initprem < 500";
						} else if ("3".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >500 and  initprem < 1000";
						} else if ("4".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >1000 ";
						}
					} else if ("5".equals(insurance.getSalary())) {
						if ("1".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >200 and  initprem < 500";
						} else if ("2".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >200 and  initprem < 500";
						} else if ("3".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >500 and  initprem < 1000";
						} else if ("4".equals(insurance.getTravelnum())) {
							sql_where = "and initprem >1000 ";
						}
					}
					QueryBuilder qb_jw = new QueryBuilder(
							"SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A02' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30'  "
									+ sql_where + "  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_jw = qb_jw.executeDataTable();
					if (dt_jw.getRowCount() > 0) {
						for (int j = 0; j < dt_jw.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_jw.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_jw.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_ly.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_jw.get(j, 0));
							if (flag) {
								continue;
							}
							boolean flag_company = checkCompany((String) dt_jw.get(j, 0));
							if (flag_company) {
								continue;
							}
							list_productid.add((String) dt_jw.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_jw.get(j, 0));
							map.put("productname", (String) dt_jw.get(j, 1));
							map.put("initprem", (String) dt_jw.get(j, 2));
							map.put("url", (String) dt_jw.get(j, 3));
							map.put("logolink", (String) dt_jw.get(j, 4));
							list_ly.add(map);
							if (list_ly.size() == 1) {
								productId1 = list_ly.get(0).get("productid");
							} else if (list_ly.size() == 2) {
								productId2 = list_ly.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							QueryBuilder qb_jw_other = new QueryBuilder(
									" SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A02' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30' ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
							DataTable dt_jw_other = qb_jw_other.executeDataTable();

							if (j == (dt_jw.getRowCount() - 1) && list_ly.size() < 2) {
								for (int x = 0; x < dt_jw_other.getRowCount(); x++) {
									if (list_ly.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_jw_other.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_jw_other.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_jw_other.get(x, 0));
										maps.put("productname", (String) dt_jw_other.get(x, 1));
										maps.put("initprem", (String) dt_jw_other.get(x, 2));
										maps.put("url", (String) dt_jw_other.get(x, 3));
										maps.put("logolink", (String) dt_jw_other.get(x, 4));
										list_ly.add(maps);
										if (list_ly.size() == 1) {
											productId1 = list_ly.get(0).get("productid");
										} else if (list_ly.size() == 2) {
											productId2 = list_ly.get(1).get("productid");
										}
									}
								}
							}
						}
						if (list_ly.size() < 2) {
							logger.info("保险测试A02境外旅游险的记录不足2条");
						}
					} else {
						logger.info("保险测试A02境外旅游险的记录不足1条");
						// 条数不够两条时，取销量最好的两个
						QueryBuilder qb_jw_other = new QueryBuilder(
								" SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A02' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30' ORDER BY CONVERT(salesvolume,SIGNED)  DESC  ");
						DataTable dt_jw_other = qb_jw_other.executeDataTable();
						for (int x = 0; x < dt_jw_other.getRowCount(); x++) {
							if (list_ly.size() > 1) {
								break;
							}
							list_productid.add((String) dt_jw_other.get(x, 0));
							Map<String, String> maps = new HashMap<String, String>();
							maps.put("productid", (String) dt_jw_other.get(x, 0));
							maps.put("productname", (String) dt_jw_other.get(x, 1));
							maps.put("initprem", (String) dt_jw_other.get(x, 2));
							maps.put("url", (String) dt_jw_other.get(x, 3));
							maps.put("logolink", (String) dt_jw_other.get(x, 4));
							list_ly.add(maps);
							if (list_ly.size() == 1) {
								productId1 = list_ly.get(0).get("productid");
							} else if (list_ly.size() == 2) {
								productId2 = list_ly.get(1).get("productid");
							}
						}
					}
					break;
				case 3:
					// A03 申根签证险
					if ("1".equals(insurance.getSalary())) {
						sql_where = "and initprem >0  and  initprem < 50";
					} else if ("2".equals(insurance.getSalary())) {
						sql_where = "and initprem >50  and  initprem < 200";
					} else if ("3".equals(insurance.getSalary())) {
						sql_where = "and initprem >500  and  initprem < 1000";
					} else if ("4".equals(insurance.getSalary())) {
						sql_where = "and initprem >500  and  initprem < 1000";
					} else if ("5".equals(insurance.getSalary())) {
						sql_where = "and initprem >500  and  initprem < 1000";
					}
					QueryBuilder qb_sg = new QueryBuilder(
							"SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A03' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30' "
									+ sql_where + "    ORDER BY salesvolume");
					DataTable dt_sg = qb_sg.executeDataTable();
					if (dt_sg.getRowCount() > 0) {
						for (int j = 0; j < dt_sg.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_sg.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_sg.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_ly.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_sg.get(j, 0));
							if (flag) {
								continue;
							}
							boolean flag_company = checkCompany((String) dt_sg.get(j, 0));
							if (flag_company) {
								continue;
							}
							list_productid.add((String) dt_sg.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_sg.get(j, 0));
							map.put("productname", (String) dt_sg.get(j, 1));
							map.put("initprem", (String) dt_sg.get(j, 2));
							map.put("url", (String) dt_sg.get(j, 3));
							map.put("logolink", (String) dt_sg.get(j, 4));
							list_ly.add(map);
							if (list_ly.size() == 1) {
								productId1 = list_ly.get(0).get("productid");
							} else if (list_ly.size() == 2) {
								productId2 = list_ly.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							QueryBuilder qb_sg_other = new QueryBuilder(
									"SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A03' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30' ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
							DataTable dt_sg_other = qb_sg_other.executeDataTable();

							if (j == (dt_sg.getRowCount() - 1) && list_ly.size() < 2) {
								for (int x = 0; x < dt_sg_other.getRowCount(); x++) {
									if (list_ly.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_sg_other.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_sg_other.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_sg_other.get(x, 0));
										maps.put("productname", (String) dt_sg_other.get(x, 1));
										maps.put("initprem", (String) dt_sg_other.get(x, 2));
										maps.put("url", (String) dt_sg_other.get(x, 3));
										maps.put("logolink", (String) dt_sg_other.get(x, 4));
										list_ly.add(maps);
										if (list_ly.size() == 1) {
											productId1 = list_ly.get(0).get("productid");
										} else if (list_ly.size() == 2) {
											productId2 = list_ly.get(1).get("productid");
										}
									}
								}
							}
						}
						if (list_ly.size() < 2) {
							logger.info("保险测试A03申根签证险的记录不足2条");
						}
					} else {
						logger.info("保险测试A03申根签证险 的记录不足1条");
						// 条数不够两条时，取销量最好的两个
						QueryBuilder qb_sg_other = new QueryBuilder(
								"SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A03' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30' ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
						DataTable dt_sg_other = qb_sg_other.executeDataTable();

						for (int x = 0; x < dt_sg_other.getRowCount(); x++) {
							if (list_ly.size() > 1) {
								break;
							}
							list_productid.add((String) dt_sg_other.get(x, 0));
							Map<String, String> maps = new HashMap<String, String>();
							maps.put("productid", (String) dt_sg_other.get(x, 0));
							maps.put("productname", (String) dt_sg_other.get(x, 1));
							maps.put("initprem", (String) dt_sg_other.get(x, 2));
							maps.put("url", (String) dt_sg_other.get(x, 3));
							maps.put("logolink", (String) dt_sg_other.get(x, 4));
							list_ly.add(maps);
							if (list_ly.size() == 1) {
								productId1 = list_ly.get(0).get("productid");
							} else if (list_ly.size() == 2) {
								productId2 = list_ly.get(1).get("productid");
							}
						}

					}
					break;
				case 4:
					// 港澳台旅游保险
					if ("1".equals(insurance.getSalary())) {
						sql_where = "and initprem >0  and  initprem < 50";
					} else if ("2".equals(insurance.getSalary())) {
						sql_where = "and initprem >50  and  initprem < 200";
					} else if ("3".equals(insurance.getSalary())) {
						sql_where = "and initprem >500  and  initprem < 1000";
					} else if ("4".equals(insurance.getSalary())) {
						sql_where = "and initprem >500  and  initprem < 1000";
					} else if ("5".equals(insurance.getSalary())) {
						sql_where = "and initprem >500  and  initprem < 1000";
					}
					QueryBuilder qb_ga = new QueryBuilder(
							"SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A01' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30'  "
									+ sql_where + "   ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_ga = qb_ga.executeDataTable();
					if (dt_ga.getRowCount() > 0) {
						for (int j = 0; j < dt_ga.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_ga.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_ga.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_ly.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_ga.get(j, 0));
							if (flag) {
								continue;
							}
							boolean flag_company = checkCompany((String) dt_ga.get(j, 0));
							if (flag_company) {
								continue;
							}
							list_productid.add((String) dt_ga.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_ga.get(j, 0));
							map.put("productname", (String) dt_ga.get(j, 1));
							map.put("initprem", (String) dt_ga.get(j, 2));
							map.put("url", (String) dt_ga.get(j, 3));
							map.put("logolink", (String) dt_ga.get(j, 4));
							list_ly.add(map);
							if (list_ly.size() == 1) {
								productId1 = list_ly.get(0).get("productid");
							} else if (list_ly.size() == 2) {
								productId2 = list_ly.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							QueryBuilder qb_ga_other = new QueryBuilder(
									"SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A01' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30' ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
							DataTable dt_ga_other = qb_ga_other.executeDataTable();
							if (j == (dt_ga.getRowCount() - 1) && list_ly.size() < 2) {
								for (int x = 0; x < dt_ga_other.getRowCount(); x++) {
									if (list_ly.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_ga_other.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_ga_other.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_ga_other.get(x, 0));
										maps.put("productname", (String) dt_ga_other.get(x, 1));
										maps.put("initprem", (String) dt_ga_other.get(x, 2));
										maps.put("url", (String) dt_ga_other.get(x, 3));
										maps.put("logolink", (String) dt_ga_other.get(x, 4));
										list_ly.add(maps);
										if (list_ly.size() == 1) {
											productId1 = list_ly.get(0).get("productid");
										} else if (list_ly.size() == 2) {
											productId2 = list_ly.get(1).get("productid");
										}
									}
								}
							}
						}
						if (list_ly.size() < 2) {
							logger.info("保险测试A01港澳台险的记录不足2条");
						}
					} else {
						logger.info("保险测试A01港澳台险 的记录不足1条");
						// 条数不够两条时，取销量最好的两个
						QueryBuilder qb_ga_other = new QueryBuilder(
								"SELECT z5.productid,z5.productname,z5.initprem,z5.url,z5.logolink FROM zccatalog z1,zcarticle z2  LEFT JOIN zdcolumnvalue z3 ON ( z3.columncode='RiskCode' AND  z3.relaid=z2.id),sdsearchrelaproduct z5 WHERE z1.producttype='A01' AND z1.id=z2.catalogid  AND  z5.ProductID =z3.textvalue  AND z2.status='30' ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
						DataTable dt_ga_other = qb_ga_other.executeDataTable();

						for (int x = 0; x < dt_ga_other.getRowCount(); x++) {
							if (list_ly.size() > 1) {
								break;
							}
							boolean f = checkProduct((String) dt_ga_other.get(x, 0));
							if (f) {
								continue;
							} else {
								list_productid.add((String) dt_ga_other.get(x, 0));
								Map<String, String> maps = new HashMap<String, String>();
								maps.put("productid", (String) dt_ga_other.get(x, 0));
								maps.put("productname", (String) dt_ga_other.get(x, 1));
								maps.put("initprem", (String) dt_ga_other.get(x, 2));
								maps.put("url", (String) dt_ga_other.get(x, 3));
								maps.put("logolink", (String) dt_ga_other.get(x, 4));
								list_ly.add(maps);
								if (list_ly.size() == 1) {
									productId1 = list_ly.get(0).get("productid");
								} else if (list_ly.size() == 2) {
									productId2 = list_ly.get(1).get("productid");
								}
							}
						}

					}
					break;
				}
				break;
			case 1:
				// 意外保险
				if ("4".equals(insurance.getSalary())) {
					QueryBuilder qb_yw = new QueryBuilder(
							"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p  WHERE s.ProductID=p.ProductID AND p.IsPublish='Y' AND  f.riskcode=s.productid AND belongflag LIKE 'B%' AND initprem >200  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_yw = qb_yw.executeDataTable();
					if (dt_yw.getRowCount() > 1) {
						for (int j = 0; j < dt_yw.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_yw.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_yw.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_yw.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_yw.get(j, 0));
							if (flag) {
								continue;
							}
							list_productid.add((String) dt_yw.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_yw.get(j, 0));
							map.put("productname", (String) dt_yw.get(j, 1));
							map.put("initprem", (String) dt_yw.get(j, 2));
							map.put("url", (String) dt_yw.get(j, 3));
							map.put("logolink", (String) dt_yw.get(j, 4));
							list_yw.add(map);
							if (list_yw.size() == 1) {
								productId1 = list_yw.get(0).get("productid");
							} else if (list_yw.size() == 2) {
								productId2 = list_yw.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							QueryBuilder qb_yw_other = new QueryBuilder(
									"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p  WHERE s.ProductID=p.ProductID AND p.IsPublish='Y' AND  f.riskcode=s.productid AND belongflag LIKE 'B%'   ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
							DataTable dt_yw_other = qb_yw_other.executeDataTable();
							if (j == (dt_yw.getRowCount() - 1) && list_yw.size() < 2) {
								for (int x = 0; x < dt_yw_other.getRowCount(); x++) {
									if (list_yw.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_yw_other.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_yw_other.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_yw_other.get(x, 0));
										maps.put("productname", (String) dt_yw_other.get(x, 1));
										maps.put("initprem", (String) dt_yw_other.get(x, 2));
										maps.put("url", (String) dt_yw_other.get(x, 3));
										maps.put("logolink", (String) dt_yw_other.get(x, 4));
										list_yw.add(maps);
										if (list_yw.size() == 1) {
											productId1 = list_yw.get(0).get("productid");
										} else if (list_yw.size() == 2) {
											productId2 = list_yw.get(1).get("productid");
										}
									}
								}
							}
						}
					} else {
						logger.info("保险测试意外保险大于200元的记录不足两条");
					}
				} else if ("5".equals(insurance.getSalary())) {
					QueryBuilder qb_yw = new QueryBuilder(
							"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s ,sdproduct p  WHERE f.riskcode=s.productid and  p.IsPublish='Y' AND  s.ProductID=p.ProductID AND belongflag LIKE 'B%' AND initprem >500  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_yw = qb_yw.executeDataTable();
					if (dt_yw.getRowCount() > 1) {
						for (int j = 0; j < dt_yw.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_yw.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_yw.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_yw.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_yw.get(j, 0));
							if (flag) {
								continue;
							}
							list_productid.add((String) dt_yw.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_yw.get(j, 0));
							map.put("productname", (String) dt_yw.get(j, 1));
							map.put("initprem", (String) dt_yw.get(j, 2));
							map.put("url", (String) dt_yw.get(j, 3));
							map.put("logolink", (String) dt_yw.get(j, 4));
							list_yw.add(map);
							if (list_yw.size() == 1) {
								productId1 = list_yw.get(0).get("productid");
							} else if (list_yw.size() == 2) {
								productId2 = list_yw.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							QueryBuilder qb_yw_other = new QueryBuilder(
									"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p  WHERE s.ProductID=p.ProductID AND p.IsPublish='Y' AND  f.riskcode=s.productid AND belongflag LIKE 'B%'   ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
							DataTable dt_yw_other = qb_yw_other.executeDataTable();
							if (j == (dt_yw.getRowCount() - 1) && list_yw.size() < 2) {
								for (int x = 0; x < dt_yw_other.getRowCount(); x++) {
									if (list_yw.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_yw_other.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_yw_other.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_yw_other.get(x, 0));
										maps.put("productname", (String) dt_yw_other.get(x, 1));
										maps.put("initprem", (String) dt_yw_other.get(x, 2));
										maps.put("url", (String) dt_yw_other.get(x, 3));
										maps.put("logolink", (String) dt_yw_other.get(x, 4));
										list_yw.add(maps);
										if (list_yw.size() == 1) {
											productId1 = list_yw.get(0).get("productid");
										} else if (list_yw.size() == 2) {
											productId2 = list_yw.get(1).get("productid");
										}
									}
								}
							}
						}
					} else {
						logger.info("保险测试意外保险大于500元的记录不足两条");
					}
				} else if ("2".equals(insurance.getSalary())) {
					QueryBuilder qb_yw = new QueryBuilder(
							"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s ,sdproduct p  WHERE  p.IsPublish='Y' AND f.riskcode=s.productid AND s.ProductID=p.ProductID AND belongflag = 'B03'  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_yw = qb_yw.executeDataTable();
					if (dt_yw.getRowCount() > 1) {
						for (int j = 0; j < dt_yw.getRowCount(); j++) {
							boolean flag_age = validateAge(birthday, (String) dt_yw.get(j, 0), DateUtil.getCurrentDate());
							if (dt_yw.getRowCount() == 2) {
								flag_age = true;
							}
							if (!flag_age) {
								continue;
							}
							if (list_yw.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_yw.get(j, 0));
							if (flag) {
								continue;
							}
							list_productid.add((String) dt_yw.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_yw.get(j, 0));
							map.put("productname", (String) dt_yw.get(j, 1));
							map.put("initprem", (String) dt_yw.get(j, 2));
							map.put("url", (String) dt_yw.get(j, 3));
							map.put("logolink", (String) dt_yw.get(j, 4));
							list_yw.add(map);
							if (list_yw.size() == 1) {
								productId1 = list_yw.get(0).get("productid");
							} else if (list_yw.size() == 2) {
								productId2 = list_yw.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							QueryBuilder qb_yw_other = new QueryBuilder(
									"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p  WHERE s.ProductID=p.ProductID AND p.IsPublish='Y' AND  f.riskcode=s.productid AND belongflag LIKE 'B%'   ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
							DataTable dt_yw_other = qb_yw_other.executeDataTable();
							if (j == (dt_yw.getRowCount() - 1) && list_yw.size() < 2) {
								for (int x = 0; x < dt_yw_other.getRowCount(); x++) {
									if (list_yw.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_yw_other.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_yw_other.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_yw_other.get(x, 0));
										maps.put("productname", (String) dt_yw_other.get(x, 1));
										maps.put("initprem", (String) dt_yw_other.get(x, 2));
										maps.put("url", (String) dt_yw_other.get(x, 3));
										maps.put("logolink", (String) dt_yw_other.get(x, 4));
										list_yw.add(maps);
										if (list_yw.size() == 1) {
											productId1 = list_yw.get(0).get("productid");
										} else if (list_yw.size() == 2) {
											productId2 = list_yw.get(1).get("productid");
										}
									}
								}
							}
						}
					} else {
						logger.info("保险测试意外保险航空险的记录不足两条");
					}
				} else if ("3".equals(insurance.getSalary())) {
					QueryBuilder qb_yw = new QueryBuilder(
							"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s ,sdproduct p  WHERE p.IsPublish='Y' AND s.ProductID=p.ProductID and f.riskcode=s.productid AND belongflag = 'B02'  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_yw = qb_yw.executeDataTable();
					if (dt_yw.getRowCount() > 1) {
						for (int j = 0; j < dt_yw.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_yw.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_yw.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_yw.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_yw.get(j, 0));
							if (flag) {
								continue;
							}
							list_productid.add((String) dt_yw.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_yw.get(j, 0));
							map.put("productname", (String) dt_yw.get(j, 1));
							map.put("initprem", (String) dt_yw.get(j, 2));
							map.put("url", (String) dt_yw.get(j, 3));
							map.put("logolink", (String) dt_yw.get(j, 4));
							list_yw.add(map);
							if (list_yw.size() == 1) {
								productId1 = list_yw.get(0).get("productid");
							} else if (list_yw.size() == 2) {
								productId2 = list_yw.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							QueryBuilder qb_yw_other = new QueryBuilder(
									"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p  WHERE s.ProductID=p.ProductID AND p.IsPublish='Y' AND  f.riskcode=s.productid AND belongflag LIKE 'B%'   ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
							DataTable dt_yw_other = qb_yw_other.executeDataTable();
							if (j == (dt_yw.getRowCount() - 1) && list_yw.size() < 2) {
								for (int x = 0; x < dt_yw_other.getRowCount(); x++) {
									if (list_yw.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_yw_other.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_yw_other.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_yw_other.get(x, 0));
										maps.put("productname", (String) dt_yw_other.get(x, 1));
										maps.put("initprem", (String) dt_yw_other.get(x, 2));
										maps.put("url", (String) dt_yw_other.get(x, 3));
										maps.put("logolink", (String) dt_yw_other.get(x, 4));
										list_yw.add(maps);
										if (list_yw.size() == 1) {
											productId1 = list_yw.get(0).get("productid");
										} else if (list_yw.size() == 2) {
											productId2 = list_yw.get(1).get("productid");
										}
									}
								}
							}
						}
					} else {
						logger.info("保险测试意外保险交通险的记录不足两条");
					}
				} else {
					QueryBuilder qb_yw = new QueryBuilder(
							"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s ,sdproduct p  WHERE p.IsPublish='Y'  and  s.ProductID=p.ProductID AND f.riskcode=s.productid AND belongflag like 'B%'  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_yw = qb_yw.executeDataTable();
					if (dt_yw.getRowCount() > 1) {
						for (int j = 0; j < dt_yw.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_yw.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_yw.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_yw.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_yw.get(j, 0));
							if (flag) {
								continue;
							}
							list_productid.add((String) dt_yw.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_yw.get(j, 0));
							map.put("productname", (String) dt_yw.get(j, 1));
							map.put("initprem", (String) dt_yw.get(j, 2));
							map.put("url", (String) dt_yw.get(j, 3));
							map.put("logolink", (String) dt_yw.get(j, 4));
							list_yw.add(map);
							if (list_yw.size() == 1) {
								productId1 = list_yw.get(0).get("productid");
							} else if (list_yw.size() == 2) {
								productId2 = list_yw.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							QueryBuilder qb_yw_other = new QueryBuilder(
									"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p  WHERE s.ProductID=p.ProductID AND p.IsPublish='Y' AND  f.riskcode=s.productid AND belongflag LIKE 'B%'   ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
							DataTable dt_yw_other = qb_yw_other.executeDataTable();
							if (j == (dt_yw.getRowCount() - 1) && list_yw.size() < 2) {
								for (int x = 0; x < dt_yw_other.getRowCount(); x++) {
									if (list_yw.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_yw_other.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_yw_other.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_yw_other.get(x, 0));
										maps.put("productname", (String) dt_yw_other.get(x, 1));
										maps.put("initprem", (String) dt_yw_other.get(x, 2));
										maps.put("url", (String) dt_yw_other.get(x, 3));
										maps.put("logolink", (String) dt_yw_other.get(x, 4));
										list_yw.add(maps);
										if (list_yw.size() == 1) {
											productId1 = list_yw.get(0).get("productid");
										} else if (list_yw.size() == 2) {
											productId2 = list_yw.get(1).get("productid");
										}
									}
								}
							}
						}
					} else {
						logger.info("保险测试意外保险的记录不足两条");
					}
				}
				break;
			case 2:
				if ("1".equals(insurance.getLoan())) {// 理财
					QueryBuilder qb_rs = new QueryBuilder(
							"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p  WHERE  p.IsPublish='Y' AND s.ProductID=p.ProductID and f.riskcode=s.productid AND belongflag LIKE  'H%'  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_rs = qb_rs.executeDataTable();
					if (dt_rs.getRowCount() > 1) {
						for (int j = 0; j < dt_rs.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_rs.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_rs.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_rs.size() == 2) {
								break;
							}
							// boolean flag = checkProduct((String) dt_rs.get(j,
							// 0));
							// if (flag) {
							// continue;
							// }
							list_productid.add((String) dt_rs.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_rs.get(j, 0));
							map.put("productname", (String) dt_rs.get(j, 1));
							map.put("initprem", (String) dt_rs.get(j, 2));
							map.put("url", (String) dt_rs.get(j, 3));
							map.put("logolink", (String) dt_rs.get(j, 4));
							list_rs.add(map);
							if (list_rs.size() == 1) {
								productId1 = list_rs.get(0).get("productid");
							} else if (list_rs.size() == 2) {
								productId2 = list_rs.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							if (j == (dt_rs.getRowCount() - 1) && list_rs.size() < 2) {
								for (int x = 0; x < dt_rs.getRowCount(); x++) {
									if (list_rs.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_rs.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_rs.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_rs.get(x, 0));
										maps.put("productname", (String) dt_rs.get(x, 1));
										maps.put("initprem", (String) dt_rs.get(x, 2));
										maps.put("url", (String) dt_rs.get(x, 3));
										maps.put("logolink", (String) dt_rs.get(x, 4));
										list_rs.add(maps);
										if (list_rs.size() == 1) {
											productId1 = list_rs.get(0).get("productid");
										} else if (list_rs.size() == 2) {
											productId2 = list_rs.get(1).get("productid");
										}
									}
								}
							}
						}
					} else {
						logger.info("保险测试理财险的记录不足两条");
					}
				} else if ("2".equals(insurance.getLoan())) {// 销量最好的寿险
					QueryBuilder qb_rs = new QueryBuilder(
							"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p WHERE  p.IsPublish='Y' AND s.ProductID=p.ProductID and f.riskcode=s.productid AND belongflag  LIKE 'E%'  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_rs = qb_rs.executeDataTable();
					if (dt_rs.getRowCount() > 1) {
						for (int j = 0; j < dt_rs.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_rs.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_rs.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_rs.size() == 2) {
								break;
							}
							// boolean flag = checkProduct((String) dt_rs.get(j,
							// 0));
							// if (flag) {
							// continue;
							// }
							list_productid.add((String) dt_rs.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_rs.get(j, 0));
							map.put("productname", (String) dt_rs.get(j, 1));
							map.put("initprem", (String) dt_rs.get(j, 2));
							map.put("url", (String) dt_rs.get(j, 3));
							map.put("logolink", (String) dt_rs.get(j, 4));
							list_rs.add(map);
							if (list_rs.size() == 1) {
								productId1 = list_rs.get(0).get("productid");
							} else if (list_rs.size() == 2) {
								productId2 = list_rs.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							if (j == (dt_rs.getRowCount() - 1) && list_rs.size() < 2) {
								for (int x = 0; x < dt_rs.getRowCount(); x++) {
									if (list_rs.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_rs.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_rs.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_rs.get(x, 0));
										maps.put("productname", (String) dt_rs.get(x, 1));
										maps.put("initprem", (String) dt_rs.get(x, 2));
										maps.put("url", (String) dt_rs.get(x, 3));
										maps.put("logolink", (String) dt_rs.get(x, 4));
										list_rs.add(maps);
										if (list_rs.size() == 1) {
											productId1 = list_rs.get(0).get("productid");
										} else if (list_rs.size() == 2) {
											productId2 = list_rs.get(1).get("productid");
										}
									}
								}
							}
						}
					} else {
						logger.info("保险测试人寿险的记录不足两条");
					}
					QueryBuilder qb_rs_two = new QueryBuilder(
							"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM FEMRiskType a ,sdsearchrelaproduct s,sdproduct p WHERE  p.IsPublish='Y' AND s.ProductID=p.ProductID AND a.riskcode=s.productid AND SubRiskTypeCode LIKE 'E%' ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
					DataTable dt_rs_two = qb_rs_two.executeDataTable();
					if (dt_rs_two.getRowCount() > 1) {
						for (int j = 0; j < dt_rs_two.getRowCount(); j++) {
							// boolean flag_age = validateAge(birthday, (String)
							// dt_rs_two.get(j, 0), DateUtil.getCurrentDate());
							// if (dt_rs_two.getRowCount() == 2) {
							// flag_age = true;
							// }
							// if (!flag_age) {
							// continue;
							// }
							if (list_rs.size() == 2) {
								break;
							}
							boolean flag = checkProduct((String) dt_rs_two.get(j, 0));
							if (flag) {
								continue;
							}
							list_productid.add((String) dt_rs_two.get(j, 0));
							Map<String, String> map = new HashMap<String, String>();
							map.put("productid", (String) dt_rs_two.get(j, 0));
							map.put("productname", (String) dt_rs_two.get(j, 1));
							map.put("initprem", (String) dt_rs_two.get(j, 2));
							map.put("url", (String) dt_rs_two.get(j, 3));
							map.put("logolink", (String) dt_rs_two.get(j, 4));
							list_rs.add(map);
							if (list_rs.size() == 1) {
								productId1 = list_rs.get(0).get("productid");
							} else if (list_rs.size() == 2) {
								productId2 = list_rs.get(1).get("productid");
							}
							// 条数不够两条时，取销量最好的两个
							if (j == (dt_rs_two.getRowCount() - 1) && list_rs.size() < 2) {
								for (int x = 0; x < dt_rs_two.getRowCount(); x++) {
									if (list_rs.size() > 1) {
										break;
									}
									boolean f = checkProduct((String) dt_rs_two.get(x, 0));
									if (f) {
										continue;
									} else {
										list_productid.add((String) dt_rs_two.get(x, 0));
										Map<String, String> maps = new HashMap<String, String>();
										maps.put("productid", (String) dt_rs_two.get(x, 0));
										maps.put("productname", (String) dt_rs_two.get(x, 1));
										maps.put("initprem", (String) dt_rs_two.get(x, 2));
										maps.put("url", (String) dt_rs_two.get(x, 3));
										maps.put("logolink", (String) dt_rs_two.get(x, 4));
										list_rs.add(maps);
										if (list_rs.size() == 1) {
											productId1 = list_rs.get(0).get("productid");
										} else if (list_rs.size() == 2) {
											productId2 = list_rs.get(1).get("productid");
										}
									}

								}
							}
						}
					} else {
						logger.info("保险测试人寿险的记录不足两条");
					}
				}
				break;
			case 3:
				String sex = insurance.getSex();
				String sexsql = "";
				if (!("".equals(sex) || sex == null)) {
					if ("1".equals(sex)) {
						sexsql = "  AND s.productname NOT LIKE '%女%' ";
					} else {
						sexsql = "  AND s.productname NOT LIKE '%男%' ";
					}
				}
				// String age = "";
				// try {
				// if (birthday.indexOf(":") != -1) {
				// age = getAge(DateUtil.parse(birthday, "yyyy-MM-dd"));
				// } else {
				// age = getAge(DateUtil.parse(birthday,
				// "yyyy-MM-dd HH:mm:ss"));
				// }
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				QueryBuilder qb_jk = new QueryBuilder(
						"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink,sectionage FROM fmrisk f ,sdsearchrelaproduct s ,sdproduct p WHERE f.riskcode=s.productid AND p.IsPublish='Y' AND s.ProductID=p.ProductID AND belongflag LIKE 'D%' "
								+ sexsql + " ORDER BY CONVERT(salesvolume,SIGNED)  ");
				DataTable dt_jk = qb_jk.executeDataTable();
				if (dt_jk.getRowCount() > 1) {
					for (int j = 0; j < dt_jk.getRowCount(); j++) {
						boolean flag_age = validateAge(birthday, (String) dt_jk.get(j, 0), DateUtil.getCurrentDate());
						if (dt_jk.getRowCount() == 2) {
							flag_age = true;
						}
						if (!flag_age) {
							continue;
						}
						if (list_jk.size() == 2) {
							break;
						}
						boolean flag = checkProduct((String) dt_jk.get(j, 0));
						if (flag) {
							continue;
						}
						list_productid.add((String) dt_jk.get(j, 0));
						Map<String, String> map = new HashMap<String, String>();
						map.put("productid", (String) dt_jk.get(j, 0));
						map.put("productname", (String) dt_jk.get(j, 1));
						map.put("initprem", (String) dt_jk.get(j, 2));
						map.put("url", (String) dt_jk.get(j, 3));
						map.put("logolink", (String) dt_jk.get(j, 4));
						list_jk.add(map);
						if (list_jk.size() == 1) {
							productId1 = list_jk.get(0).get("productid");
						} else if (list_jk.size() == 2) {
							productId2 = list_jk.get(1).get("productid");
						}
						// 条数不够两条时，取销量最好的两个
						QueryBuilder qb_jk_other = new QueryBuilder(
								"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink,sectionage FROM fmrisk f ,sdsearchrelaproduct s ,sdproduct p WHERE f.riskcode=s.productid AND p.IsPublish='Y' AND s.ProductID=p.ProductID AND belongflag LIKE 'D%' ORDER BY CONVERT(salesvolume,SIGNED)  ");
						DataTable dt_jk_other = qb_jk_other.executeDataTable();
						if (j == (dt_jk.getRowCount() - 1) && list_jk.size() < 2) {
							for (int x = 0; x < dt_jk_other.getRowCount(); x++) {
								if (list_jk.size() > 1) {
									break;
								}
								boolean f = checkProduct((String) dt_jk_other.get(x, 0));
								if (f) {
									continue;
								} else {
									list_productid.add((String) dt_jk_other.get(x, 0));
									Map<String, String> maps = new HashMap<String, String>();
									maps.put("productid", (String) dt_jk_other.get(x, 0));
									maps.put("productname", (String) dt_jk_other.get(x, 1));
									maps.put("initprem", (String) dt_jk_other.get(x, 2));
									maps.put("url", (String) dt_jk_other.get(x, 3));
									maps.put("logolink", (String) dt_jk_other.get(x, 4));
									list_jk.add(maps);
									if (list_jk.size() == 1) {
										productId1 = list_jk.get(0).get("productid");
									} else if (list_jk.size() == 2) {
										productId2 = list_jk.get(1).get("productid");
									}
								}
							}
						}
					}
				} else {
					logger.info("保险测试健康险的记录不足两条");
				}
				break;
			case 4:
				QueryBuilder qb_jy = new QueryBuilder(
						"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p  WHERE p.IsPublish='Y' AND s.ProductID=p.ProductID and f.riskcode=s.productid AND s.productid in ('109101001','109101002')");
				DataTable dt_jy = qb_jy.executeDataTable();
				if (dt_jy.getRowCount() == 2) {
					for (int j = 0; j < dt_jy.getRowCount(); j++) {
						// boolean flag_age = validateAge( birthday,(String)
						// dt_jy.get(j, 0),
						// DateUtil.getCurrentDate());
						// if (!flag_age) {
						// continue;
						// }
						// if(list_ly.size()==2){
						// break;
						// }
						// boolean flag=checkProduct((String)
						// dt_jy.get(j, 0));
						// if(flag){
						// continue;
						// }
						list_productid.add((String) dt_jy.get(j, 0));
						Map<String, String> map = new HashMap<String, String>();
						map.put("productid", (String) dt_jy.get(j, 0));
						map.put("productname", (String) dt_jy.get(j, 1));
						map.put("initprem", (String) dt_jy.get(j, 2));
						map.put("url", (String) dt_jy.get(j, 3));
						map.put("logolink", (String) dt_jy.get(j, 4));
						list_jy.add(map);
						if (list_jy.size() == 1) {
							productId1 = list_jy.get(0).get("productid");
						} else if (list_jy.size() == 2) {
							productId2 = list_jy.get(1).get("productid");
						}
						// 条数不够两条时，取销量最好的两个
						QueryBuilder qb_jy_other = new QueryBuilder(
								"SELECT s.productid,s.productname,s.initprem,s.url,s.logolink FROM fmrisk f ,sdsearchrelaproduct s,sdproduct p WHERE  p.IsPublish='Y' AND s.ProductID=p.ProductID and f.riskcode=s.productid AND belongflag  LIKE 'E%'  ORDER BY CONVERT(salesvolume,SIGNED)  DESC ");
						DataTable dt_jy_other = qb_jy_other.executeDataTable();
						if (j == (dt_jy.getRowCount() - 1) && list_jy.size() < 2) {
							for (int x = 0; x < dt_jy_other.getRowCount(); x++) {
								if (list_jy.size() > 1) {
									break;
								}
								list_productid.add((String) dt_jy_other.get(x, 0));
								Map<String, String> maps = new HashMap<String, String>();
								maps.put("productid", (String) dt_jy_other.get(x, 0));
								maps.put("productname", (String) dt_jy_other.get(x, 1));
								maps.put("initprem", (String) dt_jy_other.get(x, 2));
								maps.put("url", (String) dt_jy_other.get(x, 3));
								maps.put("logolink", (String) dt_jy_other.get(x, 4));
								list_jy.add(maps);
								if (list_jy.size() == 1) {
									productId1 = list_jy.get(0).get("productid");
								} else if (list_jy.size() == 2) {
									productId2 = list_jy.get(1).get("productid");
								}
							}
						}
					}
				} else {
					logger.info("保险测试教育险的记录不是两条");
				}
				break;
			}
			InsuranceTestResult insurancetestresult = new InsuranceTestResult();
			insurancetestresult.setUserid(id);
			insurancetestresult.setType(riskcode[i]);
			insurancetestresult.setLevel(String.valueOf(nums[i]));
			insurancetestresult.setProduct_id1(productId1);
			insurancetestresult.setProduct_id2(productId2);
			this.insurancetestresultservice.save(insurancetestresult);
			list.add(insurancetestresult);
		}
		this.sort(st);
		for (int j = 0; j < st.length; j++) {
			for (int j2 = 0; j2 < list.size(); j2++) {
				String level = list.get(j2).getLevel();
				if (st[j].equals(level)) {
					List<Map<String, String>> li = null;
					switch (j2) {
					case 0:
						li = list_ly;
						break;
					case 1:
						li = list_yw;
						break;
					case 2:
						li = list_rs;
						break;
					case 3:
						li = list_jk;
						break;
					case 4:
						li = list_jy;
						break;
					}
					switch (j) {
					case 0:
						if (li == null || li.size() == 0) {
							level = "0";
						}
						list_one = li;
						for (int k = 0; k < list_one.size(); k++) {
							list_one.get(k).put("level", level + "");
						}
						list.get(j2).setLevel("10");
						content_one = contentArr[j2];
						title_one = title[j2];
						start_one = "need_name need_grade" + level;
						if (Integer.parseInt(level) != 0) {
							mail_start_one = mail_start[Integer.parseInt(level) - 1];
						}
						if (0 < Integer.parseInt(level) && Integer.parseInt(level) < 3) {
							warn_one = warn[2];
							warn_one_mail = "<font size=\"2\" color=\"grean\">" + warn_one + "</font>";
						} else if (Integer.parseInt(level) == 3) {
							warn_one = warn[1];
							warn_one_mail = "<font size=\"2\" color=\"orange\">" + warn_one + "</font>";
						} else if (3 < Integer.parseInt(level)) {
							warn_one = warn[0];
							warn_one_mail = "<font size=\"2\" color=\"red\">" + warn_one + "</font>";
						}
						if ("0".equals(level)) {
							break;
						}
						QueryBuilder qb_one = new QueryBuilder(
								"SELECT z2.URL  FROM zcarticle z1,zccatalog z2 ,sdsearchrelaproduct z3 WHERE z1.id=z3.prop1 AND z1.catalogid=z2.id  AND z3.productid=?  LIMIT 0,1", list_one.get(0)
										.get("productid"));
						if (qb_one.executeDataTable().getRowCount() > 0) {
							product_list_one = Config.getValue("FrontServerContextPath") + "/" + qb_one.executeString();
						} else {
							product_list_one = Config.getValue("FrontServerContextPath");
						}
						break;
					case 1:
						if (li == null || li.size() == 0) {
							level = "0";
						}
						list_two = li;
						for (int k = 0; k < list_two.size(); k++) {
							list_two.get(k).put("level", level + "");
						}
						list.get(j2).setLevel("10");
						content_two = contentArr[j2];
						title_two = title[j2];
						start_two = "need_name need_grade" + level;
						if (Integer.parseInt(level) != 0) {
							mail_start_two = mail_start[Integer.parseInt(level) - 1];
						}
						if (0 < Integer.parseInt(level) && Integer.parseInt(level) < 3) {
							warn_two = warn[2];
							warn_two_mail = "<font size=\"2\" color=\"grean\">" + warn_two + "</font>";
						} else if (Integer.parseInt(level) == 3) {
							warn_two = warn[1];
							warn_two_mail = "<font size=\"2\" color=\"orange\">" + warn_two + "</font>";
						} else if (3 < Integer.parseInt(level)) {
							warn_two = warn[0];
							warn_two_mail = "<font size=\"2\" color=\"red\">" + warn_two + "</font>";
						}
						if ("0".equals(level)) {
							break;
						}
						QueryBuilder qb_two = new QueryBuilder(
								"SELECT z2.URL  FROM zcarticle z1,zccatalog z2 ,sdsearchrelaproduct z3 WHERE z1.id=z3.prop1 AND z1.catalogid=z2.id  AND z3.productid=?  LIMIT 0,1", list_two.get(0)
										.get("productid"));
						if (qb_two.executeDataTable().getRowCount() > 0) {
							product_list_two = Config.getValue("FrontServerContextPath") + "/" + qb_two.executeString();
						} else {
							product_list_two = Config.getValue("FrontServerContextPath");
						}
						break;
					case 2:
						if (li == null || li.size() == 0) {
							level = "0";
						}
						list_three = li;
						for (int k = 0; k < list_three.size(); k++) {
							list_three.get(k).put("level", level + "");
						}
						list.get(j2).setLevel("10");
						content_three = contentArr[j2];
						title_three = title[j2];
						start_three = "need_name need_grade" + level;
						if (Integer.parseInt(level) != 0) {
							mail_start_three = mail_start[Integer.parseInt(level) - 1];
						}
						if (0 < Integer.parseInt(level) && Integer.parseInt(level) < 3) {
							warn_three = warn[2];
							warn_three_mail = "<font size=\"2\" color=\"grean\">" + warn_three + "</font>";
						} else if (Integer.parseInt(level) == 3) {
							warn_three = warn[1];
							warn_three_mail = "<font size=\"2\" color=\"orange\">" + warn_three + "</font>";
						} else if (3 < Integer.parseInt(level)) {
							warn_three = warn[0];
							warn_three_mail = "<font size=\"2\" color=\"red\">" + warn_three + "</font>";
						}
						if ("0".equals(level)) {
							break;
						}
						QueryBuilder qb_three = new QueryBuilder(
								"SELECT z2.URL  FROM zcarticle z1,zccatalog z2 ,sdsearchrelaproduct z3 WHERE z1.id=z3.prop1 AND z1.catalogid=z2.id  AND z3.productid=?  LIMIT 0,1", list_three.get(0)
										.get("productid"));
						if (qb_three.executeDataTable().getRowCount() > 0) {
							product_list_three = Config.getValue("FrontServerContextPath") + "/" + qb_three.executeString();
						} else {
							product_list_three = Config.getValue("FrontServerContextPath");
						}
						break;
					case 3:
						if (li == null || li.size() == 0) {
							level = "0";
						}
						list_four = li;
						for (int k = 0; k < list_four.size(); k++) {
							list_four.get(k).put("level", level + "");
						}
						list.get(j2).setLevel("10");
						content_four = contentArr[j2];
						title_four = title[j2];
						start_four = "need_name need_grade" + level;
						if (Integer.parseInt(level) != 0) {
							mail_start_four = mail_start[Integer.parseInt(level) - 1];
						}
						if (0 < Integer.parseInt(level) && Integer.parseInt(level) < 3) {
							warn_four = warn[2];
							warn_four_mail = "<font size=\"2\" color=\"grean\">" + warn_four + "</font>";
						} else if (Integer.parseInt(level) == 3) {
							warn_four = warn[1];
							warn_four_mail = "<font size=\"2\" color=\"orange\">" + warn_four + "</font>";
						} else if (3 < Integer.parseInt(level)) {
							warn_four = warn[0];
							warn_four_mail = "<font size=\"2\" color=\"red\">" + warn_four + "</font>";
						}
						if ("0".equals(level)) {
							break;
						}
						QueryBuilder qb_four = new QueryBuilder(
								"SELECT z2.URL  FROM zcarticle z1,zccatalog z2 ,sdsearchrelaproduct z3 WHERE z1.id=z3.prop1 AND z1.catalogid=z2.id  AND z3.productid=?  LIMIT 0,1", list_four.get(0)
										.get("productid"));
						if (qb_four.executeDataTable().getRowCount() > 0) {
							product_list_four = Config.getValue("FrontServerContextPath") + "/" + qb_four.executeString();
						} else {
							product_list_four = Config.getValue("FrontServerContextPath");
						}
						break;
					case 4:
						if (li == null || li.size() == 0) {
							level = "0";
						}
						list_five = li;
						for (int k = 0; k < list_five.size(); k++) {
							list_five.get(k).put("level", level + "");
						}
						list.get(j2).setLevel("10");
						content_five = contentArr[j2];
						title_five = title[j2];
						start_five = "need_name need_grade" + level;
						if (Integer.parseInt(level) != 0) {
							mail_start_five = mail_start[Integer.parseInt(level) - 1];
						}
						if (0 < Integer.parseInt(level) && Integer.parseInt(level) < 3) {
							warn_five = warn[2];
							warn_five_mail = "<font size=\"2\" color=\"grean\">" + warn_five + "</font>";
						} else if (Integer.parseInt(level) == 3) {
							warn_five = warn[1];
							warn_five_mail = "<font size=\"2\" color=\"orange\">" + warn_five + "</font>";
						} else if (3 < Integer.parseInt(level)) {
							warn_five = warn[0];
							warn_five_mail = "<font size=\"2\" color=\"red\">" + warn_five + "</font>";
						}
						if ("0".equals(level)) {
							break;
						}
						QueryBuilder qb_five = new QueryBuilder(
								"SELECT z2.URL  FROM zcarticle z1,zccatalog z2 ,sdsearchrelaproduct z3 WHERE z1.id=z3.prop1 AND z1.catalogid=z2.id  AND z3.productid=?  LIMIT 0,1", list_five.get(0)
										.get("productid"));
						if (qb_five.executeDataTable().getRowCount() > 0) {
							product_list_five = Config.getValue("FrontServerContextPath") + "/" + qb_five.executeString();
						} else {
							product_list_five = Config.getValue("FrontServerContextPath");
						}
						break;
					}
					break;
				}
			}
		}
		if ("2".equals(insurance.getCar())) {
			Map<String, String> map = new HashMap<String, String>();
			int index = new Random().nextInt(3);
			map.put("productname", chexian_productname[index]);
			map.put("url", Config.getValue("FrontServerContextPath") + chexian_url[index]);
			map.put("logolink", Config.getValue("FrontServerContextPath") + class_image[index]);
			list_six.add(map);
			Map<String, String> map_pa = new HashMap<String, String>();
			map_pa.put("productname", "平安驾驶无忧驾驶员人身意外保险");
			map_pa.put("url", Config.getValue("FrontServerContextPath") + "/yiwai-baoxian/251340.shtml");
			map_pa.put("logolink", Config.getValue("FrontServerContextPath") + "/images/max_logo_03.gif");
			product_list_six = Config.getValue("FrontServerContextPath") + "/chexian/";
			list_six.add(map_pa);

		}

		// 保存是否成功生成id
		if (!("".equals(id) || id == null)) {
			try {
				// 邮箱是否被注册
				if ("true".equals(checkEmail(insurance.getMail()))) {
					// 直接注册并发送邮件
					this.newRegister(insurance.getMail(), StringUtil.noNull(insurance.getPhone()), id);
				} else {
					// 已注册，发送邮件
					Map<String, Object> map_mail = new HashMap<String, Object>();
					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
					int rigisterDate = Integer.parseInt(sdf1.format(new Date()));
					map_mail.put("rigisterDate", rigisterDate);
					map_mail.put("Member", member);
					Map<String, Object> map = new HashMap<String, Object>();
					Member member = new Member();
					member.setEmail(insurance.getMail());

					map.put("username", insurance.getMail());
					map.put("password", "0");
					map.put("serverpath", Config.getValue("FrontServerContextPath") + "/wj/wwwroot/kxb/");
					map.put("searchpath", Config.getValue("FrontServerContextPath") + "/wj/shop/insurance_test!searchInsuranceTestResultList.action?userid=" + id);
					scoreMailInformation(map);
					map.put("Member", member);
					try {
						ActionUtil.sendMail("wj00093", insurance.getMail(), map);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
				//校验是否发送优惠券
				QueryBuilder qb=new QueryBuilder("select couponsn from  sdcouponprovidelog where type='4' and mail =?",insurance.getMail());
				DataTable dt=qb.executeDataTable();
				if(dt.getRowCount()==0){
					//触发保险测试送券活动
					QueryBuilder qb_activity=new QueryBuilder("SELECT id from sdcouponactivityinfo  WHERE TYPE='4' AND STATUS='3' ");
					DataTable qt_activity=qb_activity.executeDataTable();
					if(qt_activity.getRowCount()>0){
						//发放优惠券
						String mail=insurance.getMail();
						provideCoupon(mail);
					}else if(qt_activity.getRowCount()>1){
						logger.info("保险测试活动数量多于一个！");
					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		} else {
			logger.error("保险测试结果保存失败!");
		}
		return "result";
	}

	/***
	 * 判断邮箱是否注册过
	 * 
	 * @return
	 */
	public String checkEmail(String email) {
		if (StringUtil.isMail(email)) {
			if (memberService.isExistByMailbox(email)) {
				return "false";// 邮箱被注册
			} else {
				return "true";
			}
		}
		return null;
	}

	/**
	 * 
	 * @Title: scoreMailInformation
	 * @Description: TODO(准备邮件相关参数)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	private void scoreMailInformation(Map<String, Object> map) {
		if (list_one.size() != 0 && list_one != null) {
			map.put("htmlOne", changeToHtml(list_one));
		}
		if (list_two.size() != 0 && list_two != null) {
			map.put("htmlTwo", changeToHtml(list_two));
		}
		if (list_three.size() != 0 && list_three != null) {
			map.put("htmlThree", changeToHtml(list_three));
		}
		if (list_four.size() != 0 && list_four != null) {
			map.put("htmlFour", changeToHtml(list_four));
		}
		if (list_five.size() != 0 && list_five != null) {
			map.put("htmlFive", changeToHtml(list_five));
		}
		if (list_six.size() != 0 && list_six != null) {
			map.put("htmlSix", changeToHtmlCar(list_six));
		}
		map.put("warn_one", warn_one_mail);
		map.put("warn_two", warn_two_mail);
		map.put("warn_three", warn_three_mail);
		map.put("warn_four", warn_four_mail);
		map.put("warn_five", warn_five_mail);

		map.put("title_one", title_one);
		map.put("title_two", title_two);
		map.put("title_three", title_three);
		map.put("title_four", title_four);
		map.put("title_five", title_five);

		map.put("product_list_one", product_list_one);
		map.put("product_list_two", product_list_two);
		map.put("product_list_three", product_list_three);
		map.put("product_list_four", product_list_four);
		map.put("product_list_five", product_list_five);
		map.put("product_list_six", product_list_six);

		map.put("content_one", content_one);
		map.put("content_two", content_two);
		map.put("content_three", content_three);
		map.put("content_four", content_four);
		map.put("content_five", content_five);

		map.put("mail_start_one", Config.getValue("FrontServerContextPath") + "/wj/wwwroot/kxb/images/" + mail_start_one);
		map.put("mail_start_two", Config.getValue("FrontServerContextPath") + "/wj/wwwroot/kxb/images/" + mail_start_two);
		map.put("mail_start_three", Config.getValue("FrontServerContextPath") + "/wj/wwwroot/kxb/images/" + mail_start_three);
		map.put("mail_start_four", Config.getValue("FrontServerContextPath") + "/wj/wwwroot/kxb/images/" + mail_start_four);
		map.put("mail_start_five", Config.getValue("FrontServerContextPath") + "/wj/wwwroot/kxb/images/" + mail_start_five);
	}

	/**
	 * 
	 * @Title: newRegister
	 * @Description: TODO(发送注册邮件)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	private void newRegister(String email, String mobileNo, String userid) {
		Transaction trans = new Transaction();
		memberSchema members = new memberSchema();
		double a = (2 / 20.0) * 100;
		DecimalFormat myformat = new DecimalFormat("#####0");
		String fullDegree = myformat.format(a) + "%";
		long menberID = NoUtil.getMaxID("MemberID");
		members.setid(menberID + "");
		members.setcreateDate(new Date());
		members.setmodifyDate(new Date());
		members.setfullDegree(fullDegree);
		String password = randomPW();
		members.setpassword(DigestUtils.md5Hex(password));
		members.setVIPFrom("0");
		members.setsafeQuestion(null);
		members.setsafeAnswer(null);
		// CMS报表设定RegisterType的值为2时认为是自动注册。
		members.setregisterType("2");
		members.setmemberRank_id(getDefaultMemberRank());
		members.setisEmailBinding("Y");
		members.setisMobileNOBinding("Y");
		members.setpoint(0);
		members.setdeposit(new BigDecimal("0"));
		members.setisAccountEnabled("Y");
		members.setisAccountLocked("N");
		members.setloginFailureCount(0);
		members.setpasswordRecoverKey(null);
		members.setlockedDate(null);
		members.setregisterIp(getRequest().getRemoteAddr());
		members.setloginIp(getRequest().getRemoteAddr());
		members.setusedPoint(0);
		members.setcurrentValidatePoint(0);
		members.setexpiricalValue(0);
		members.setemail(email);
		members.setmobileNO(mobileNo);
		members.setusername(email);
		members.setverifyEmailSendDate(new Date());
		members.setFromWap("wj");
		members.setgrade("K0");
		members.setvipFlag("N");
		
		trans.add(members, Transaction.INSERT);
		if (trans.commit()) {
			try {
				ZDConfigSchema tZDConfigSchema = new ZDConfigSchema();
				tZDConfigSchema.setType("ServerContext");
				tZDConfigSchema.fill();
				Map<String, Object> map = new HashMap<String, Object>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				int rigisterDate = Integer.parseInt(sdf.format(new Date()));
				Member member = new Member();
				member.setEmail(email);

				map.put("username", email);
				map.put("password", password);
				map.put("serverpath", Config.getValue("FrontServerContextPath") + "/wj/wwwroot/kxb/");
				map.put("searchpath", Config.getValue("FrontServerContextPath") + "/wj/shop/insurance_test!searchInsuranceTestResultList.action?userid=" + userid);
				map.put("rigisterDate", rigisterDate);
				// 封装邮件参数
				scoreMailInformation(map);
				map.put("Member", member);
				ActionUtil.sendMail("wj00093", email, map);
			} catch (Exception e) {
				logger.error("保险测试的用户名：" + email + "发送自动注册用户信息邮件没有成功！" + e.getMessage(), e);
			}
		} else {
			logger.info("保险测试的用户名：{}自动注册用户信息没有注册成功！", email);
		}
	}

	/**
	 * 
	 * @Title: age
	 * @Description: TODO(判断是否符合产品的年龄段)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	private boolean validateAge(String BirthdayId, String productId, String effDate) {
		Boolean tFlag = false;
		try {
			GetDBdata db = new GetDBdata();
			Date start, end = null;
			String sql_sectionage = "select SectionAge,Remark6 from sdproduct where productid=?";
			String[] sql_sectionageTemp = { productId };
			List<HashMap<String, String>> a = db.query(sql_sectionage, sql_sectionageTemp);
			HashMap<String, String> b = a.get(0);
			String sectionage = b.get("SectionAge");
			String conmancom = b.get("Remark6");
			if (conmancom.equals("1087") || conmancom.equals("1001") || conmancom.equals("1004") || conmancom.equals("2043")) {
				effDate = PubFun.getCurrentDate();
			}
			if (StringUtil.isEmpty(sectionage)) {
				return true;
			} else {
				// 多个断段间
				if (sectionage.indexOf("|") != -1) {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {

						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", sdf_1.parse(effDate));
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								if (compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId)) {
									tFlag = true;
								}
							}
						} else {

						}
					}
				} else {
					String[] op = sectionage.split("\\|");
					for (int i = 0; i < op.length; i++) {
						if (op[i].indexOf("-") != -1) {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							}
						} else {
							String[] age = op[i].split("-");
							if (age[0].endsWith("D") && age[1].endsWith("D")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("D", "")) - 1, "D", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("D") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("D", "")), "D", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("M")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("M", "")) - 1, "M", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("M") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("M", "")), "M", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							} else if (age[0].endsWith("Y") && age[1].endsWith("Y")) {
								start = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[0].replaceAll("Y", "")), "Y", sdf_1.parse(effDate));
								end = com.sinosoft.sms.messageinterface.pubfun.PubFun.calDate(sdf_1.parse(effDate), -Integer.parseInt(age[1].replaceAll("Y", "")) - 1, "Y", sdf_1.parse(effDate));
								return compare_date(sdf_1.format(start), sdf_1.format(end), BirthdayId);
							}
						}
					}

				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return tFlag;
	}

	/**
	 * 
	 * @Title: changeToHtml
	 * @Description: TODO(拼接推荐商品html代码)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private String changeToHtml(List<Map<String, String>> list) {
		String returnHtml = "";
		for (int i = 0; i < list.size(); i++) {
			StringBuffer html = new StringBuffer("<td style=\"padding:22px 0px 22px 30px;\">");
			html.append("<table  width=\"190px\"  border=\"0\">");
			html.append("<tr>");
			html.append("<td colspan=\"2\" align=\"center\"><a href=\"" + list.get(i).get("url") + "\" target=\"_blank\"><img src=\"" + list.get(i).get("logolink")
					+ "\"  style=\"border:1px solid #DCDCDC;\" width=\"190\" height=\"190\"  alt=\"\"/></a></td>");
			html.append("</tr>");
			html.append("<tr>");
			html.append("<td colspan=\"2\"  align=\"left\" ><a style=\"display:block; height:20px; padding-top:4px;  overflow: hidden; \" href=\"" + list.get(i).get("url")
					+ "\" style=\"font-size:14px; color:#6D6D6D; text-decoration:none;\">" + list.get(i).get("productname") + "</a></td>");
			html.append("</tr>");
			html.append("<tr>");
			html.append("<td width=\"110px\"><span style=\"font-family:'Microsoft YaHei'; color:red; font-weight:bold;\">￥" + list.get(i).get("initprem") + "</span></td>");
			html.append("<td><a href=\"" + list.get(i).get("url")
					+ "\" style=\"display:block; width:80px; height:28px; line-height:28px; color:#fff; background:red; text-align:center; text-decoration:none; float:right; \">去看看>></a></td>");
			html.append("</tr>");
			html.append("</table>");
			html.append("</td>");
			returnHtml = returnHtml + html;
		}
		return returnHtml;
	}

	/**
	 * 
	 * @Title: changeToHtmlCar
	 * @Description: TODO(针对车险的html转换)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private String changeToHtmlCar(List<Map<String, String>> list) {
		String returnHtml = "";
		for (int i = 0; i < list.size(); i++) {
			StringBuffer html = new StringBuffer("<td style=\"padding:22px 0px 22px 30px;\">");
			html.append("<table  width=\"225px\"  border=\"0\">");
			html.append("<tr>");
			html.append("<td colspan=\"2\" align=\"center\"><a href=\"" + list.get(i).get("url") + "\" target=\"_blank\"><img src=\"" + list.get(i).get("logolink")
					+ "\"  style=\"border:1px solid #DCDCDC;\" width=\"203\" height=\"98\"  alt=\"\"/></a></td>");
			html.append("</tr>");
			html.append("<tr>");
			html.append("<td colspan=\"2\"  align=\"left\" ><a href=\"" + list.get(i).get("url") + "\" style=\"font-size:14px; color:#6D6D6D; text-decoration:none;\">"
					+ list.get(i).get("productname") + "</a></td>");
			html.append("</tr>");
			html.append("<tr>");
			html.append("<td width=\"110px\"><span style=\"font-family:'Microsoft YaHei'; color:red; font-weight:bold;\"></span></td>");
			html.append("<td><a href=\"" + list.get(i).get("url")
					+ "\" style=\"display:block; width:80px; height:28px; line-height:28px; color:#fff; background:red; text-align:center; text-decoration:none; float:right; \">去看看>></a></td>");
			html.append("</tr>");
			html.append("</table>");
			html.append("</td>");
			returnHtml = returnHtml + html;
		}
		return returnHtml;
	}

	/**
	 * 
	 * @Title: compare_date
	 * @Description: TODO(比较时间)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	private boolean compare_date(String startdate, String enddate, String birthday) {
		try {
			Date start = sdf_1.parse(startdate);
			Date end = sdf_1.parse(enddate);
			Date Dbirthday = sdf_1.parse(birthday);
			if (Dbirthday.getTime() <= start.getTime() && Dbirthday.getTime() > end.getTime()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 
	 * @Title: randomPW
	 * @Description: TODO(获取随机密码)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private String randomPW() {
		Random r = new Random();
		int i = 0;
		while (i < 100000) {
			i = r.nextInt(1000000);
		}
		return String.valueOf(i);
	}

	/***
	 * 
	 * @Title: getDefaultMemberRank
	 * @Description: TODO(查询会员级别)
	 * @return String 返回类型
	 * @author zhangjing
	 */
	private String getDefaultMemberRank() {
		String defaultMemberRank_id = "";
		String sql1 = "select * from MemberRank as memberRank where memberRank.isDefault = ?";
		String sql2 = "select * from MemberRank as memberRank order by memberRank.createDate asc";
		QueryBuilder qb = new QueryBuilder(sql1, "0");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			defaultMemberRank_id = dt.getString(0, "id");
		} else {
			qb = new QueryBuilder(sql2);
			dt = qb.executeDataTable();
			defaultMemberRank_id = dt.getString(0, "id");
		}

		return defaultMemberRank_id;
	}

	/**
	 * 
	 * @Title: sort
	 * @Description: TODO(降序排列)
	 * @return void 返回类型
	 * @author zhangjing
	 */
	private void sort(String[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (Integer.parseInt(array[j]) > Integer.parseInt(array[i])) {
					int t = Integer.parseInt(array[j]);
					array[j] = array[i];
					array[i] = t + "";
				}
			}
		}
	}

	/**
	 * 
	 * @Title: checkProduct
	 * @Description: TODO(检查商品不重复)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	private boolean checkProduct(String productid) {
		// 两款固定的教育险除外
		if ((("109101001").equals(productid)) || (("109101002").equals(productid))) {
			return true;
		} else if (list_productid.contains(productid.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @Title: checkCompany
	 * @Description: TODO(检查公司（品牌）不重复)
	 * @return boolean 返回类型
	 * @author zhangjing
	 */
	private boolean checkCompany(String productid) {
		if (StringUtil.isNotEmpty(productid)) {
			for (int i = 0; i < list_productid.size(); i++) {
				if (list_productid.get(i).indexOf(productid) != -1) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
	* @Title: provideCoupon 
	* @Description: TODO(保险测试送优惠券) 
	* @return void    返回类型 
	* @author zhangjing
	 */
	private void provideCoupon(String mail) {
		// 公共参数
		Map<String, Object> data = new HashMap<String, Object>();
		SDCouponInfoSchema sdcouponschema = new SDCouponInfoSchema();
		QueryBuilder qb_coupon = new QueryBuilder(
				" SELECT cou.id AS id ,cou.status as status FROM couponinfo cou,sdcouponactivityinfo sdac WHERE sdac.TYPE='4' AND sdac.STATUS='3'  AND cou.batch=sdac.batch order by cou.status ");
		DataTable qt = qb_coupon.executeDataTable();
		// id值
		String coupon_id = qt.getString(0, 0);
		// 状态
		String status = qt.getString(0, 1);
		sdcouponschema.setId(coupon_id);
		Transaction trans = new Transaction();
		// 查询
		if (sdcouponschema.fill()) {
			if (StringUtil.isMail(mail)) {
				if (!("0".equals(status))) {
					// 生成优惠劵ID
					String year = DateUtil.getCurrentDateTime("yyyy");
					String id = year + NoUtil.getMaxNo("CouponSn", 12);
					// 优惠券ID值
					sdcouponschema.setId(id);
					// 优惠券号
					sdcouponschema.setCouponSn(DigestUtils.md5Hex(id));
					// 创建时间
					sdcouponschema.setCreateDate(new Date());
					// 创建者
					sdcouponschema.setCreateUser("admin");
				}
				// 状态2已发放
				sdcouponschema.setStatus("2");
				// 发放时间
				sdcouponschema.setProp2(DateUtil.getCurrentDateTime());
				// 将会员id关联到优惠券表中
				sdcouponschema.setMemberId(member.getId());
				sdcouponschema.setMail(mail);
				// 更改人和更改时间
//				String now = DateUtil.getCurrentDateTime();
				if (!("0".equals(status))) {
					trans.add(sdcouponschema, Transaction.INSERT);
				} else {
					trans.add(sdcouponschema, Transaction.UPDATE);
				}
				if (trans.commit()) {
					// 发送邮件
					Member member = new Member();
					data.put("Member", member);
					// 优惠券说明
					try {
						data.put("direction", java.net.URLDecoder.decode(sdcouponschema.getDirection(), "utf-8"));
					} catch (UnsupportedEncodingException e) {
						logger.error(e.getMessage(), e);
					}
					// 优惠金额
					data.put("parValue", sdcouponschema.getParValue());
					// 优惠券开始时间
					String starttime = sdcouponschema.getStartTime().toString();
					// 优惠券结束时间
					String endtime = sdcouponschema.getEndTime().toString();

					// 开始时间
					data.put("starttime", starttime.substring(0, 10));
					// 结束时间
					data.put("endtime", endtime.substring(0, 10));
					// 优惠券编号
					data.put("couponsn", sdcouponschema.getCouponSn());
					data.put("url", Config.getValue("FrontServerContextPath") + "/wj/shop/coupon!queryCoupon.action");
					// 邮箱地址
					member.setEmail(mail);

					// 如果需要信息提醒
					if ("Y".equals(sdcouponschema.getRemindFlag())) {
						// 非折扣券
						if (StringUtil.isEmpty(sdcouponschema.getProp3()) || "01".equals(sdcouponschema.getProp3())) {
							// 优惠金额
							data.put("parValueShow", sdcouponschema.getParValue() + "元");

							if (ActionUtil.sendMail("wj00088", mail,data)) {
								//发放记录
								writeEmailLog(sdcouponschema.getCouponSn(),mail);
								logger.info("保险测试送优惠券活动，发送邮件成功");
							} else {
								logger.error("保险测试送优惠券活动，发送邮件失败");
							}
						} else { // 折扣券
							// 优惠金额
							data.put("parValueShow", sdcouponschema.getProp4() + "折");

							if (ActionUtil.sendMail("wj00113",mail, data)) {
								//发放记录
								writeEmailLog(sdcouponschema.getCouponSn(),mail);
								logger.info("保险测试送优惠券活动，发送邮件成功");
							} else {
								logger.error("保险测试送优惠券活动，发送邮件失败");
							}
						}
					}
				} else {
					logger.error("保险测试送优惠券活动，更改优惠券状态错误");
				}
			} else {
				logger.error("保险测试送优惠券活动邮箱错误！");
			}
		}
	}
	/**
	 * 
	* @Title: writeEmailLog 
	* @Description: TODO(记录保险测试送优惠券日志表) 
	* @return void    返回类型 
	* @author zhangjing
	 */
	private void writeEmailLog(String couponsn,String mail){
		SDCouponProvideLogSchema sdcouponprovidelogschema = new SDCouponProvideLogSchema();
		sdcouponprovidelogschema.setcouponsn(couponsn);
		sdcouponprovidelogschema.setmail(mail);
		sdcouponprovidelogschema.settype("4");
		sdcouponprovidelogschema.setdescribe("保险测试送优惠券");
		sdcouponprovidelogschema.setcreateDate(PubFun.getCurrent());
		sdcouponprovidelogschema.insert();
	}
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public InsuranceTest getInsurance() {
		return insurance;
	}

	public void setInsurance(InsuranceTest insurance) {
		this.insurance = insurance;
	}

	public List<Map<String, String>> getList_ly() {
		return list_ly;
	}

	public void setList_ly(List<Map<String, String>> listLy) {
		list_ly = listLy;
	}

	public List<Map<String, String>> getList_yw() {
		return list_yw;
	}

	public void setList_yw(List<Map<String, String>> listYw) {
		list_yw = listYw;
	}

	public List<Map<String, String>> getList_rs() {
		return list_rs;
	}

	public void setList_rs(List<Map<String, String>> listRs) {
		list_rs = listRs;
	}

	public List<Map<String, String>> getList_jk() {
		return list_jk;
	}

	public void setList_jk(List<Map<String, String>> listJk) {
		list_jk = listJk;
	}

	public List<Map<String, String>> getList_jy() {
		return list_jy;
	}

	public void setList_jy(List<Map<String, String>> listJy) {
		list_jy = listJy;
	}

	public String getContent_one() {
		return content_one;
	}

	public void setContent_one(String contentOne) {
		content_one = contentOne;
	}

	public String getContent_two() {
		return content_two;
	}

	public void setContent_two(String contentTwo) {
		content_two = contentTwo;
	}

	public String getContent_three() {
		return content_three;
	}

	public void setContent_three(String contentThree) {
		content_three = contentThree;
	}

	public String getContent_four() {
		return content_four;
	}

	public void setContent_four(String contentFour) {
		content_four = contentFour;
	}

	public String getContent_five() {
		return content_five;
	}

	public void setContent_five(String contentFive) {
		content_five = contentFive;
	}

	public List<Map<String, String>> getList_one() {
		return list_one;
	}

	public void setList_one(List<Map<String, String>> listOne) {
		list_one = listOne;
	}

	public List<Map<String, String>> getList_two() {
		return list_two;
	}

	public void setList_two(List<Map<String, String>> listTwo) {
		list_two = listTwo;
	}

	public List<Map<String, String>> getList_three() {
		return list_three;
	}

	public void setList_three(List<Map<String, String>> listThree) {
		list_three = listThree;
	}

	public List<Map<String, String>> getList_four() {
		return list_four;
	}

	public void setList_four(List<Map<String, String>> listFour) {
		list_four = listFour;
	}

	public List<Map<String, String>> getList_five() {
		return list_five;
	}

	public void setList_five(List<Map<String, String>> listFive) {
		list_five = listFive;
	}

	public String getTitle_one() {
		return title_one;
	}

	public void setTitle_one(String titleOne) {
		title_one = titleOne;
	}

	public String getTitle_two() {
		return title_two;
	}

	public void setTitle_two(String titleTwo) {
		title_two = titleTwo;
	}

	public String getTitle_three() {
		return title_three;
	}

	public void setTitle_three(String titleThree) {
		title_three = titleThree;
	}

	public String getTitle_four() {
		return title_four;
	}

	public void setTitle_four(String titleFour) {
		title_four = titleFour;
	}

	public String getTitle_five() {
		return title_five;
	}

	public void setTitle_five(String titleFive) {
		title_five = titleFive;
	}

	public String getStart_one() {
		return start_one;
	}

	public void setStart_one(String startOne) {
		start_one = startOne;
	}

	public String getStart_two() {
		return start_two;
	}

	public void setStart_two(String startTwo) {
		start_two = startTwo;
	}

	public String getStart_three() {
		return start_three;
	}

	public void setStart_three(String startThree) {
		start_three = startThree;
	}

	public String getStart_four() {
		return start_four;
	}

	public void setStart_four(String startFour) {
		start_four = startFour;
	}

	public String getStart_five() {
		return start_five;
	}

	public void setStart_five(String startFive) {
		start_five = startFive;
	}

	public String getWarn_one() {
		return warn_one;
	}

	public void setWarn_one(String warnOne) {
		warn_one = warnOne;
	}

	public String getWarn_two() {
		return warn_two;
	}

	public void setWarn_two(String warnTwo) {
		warn_two = warnTwo;
	}

	public String getWarn_three() {
		return warn_three;
	}

	public void setWarn_three(String warnThree) {
		warn_three = warnThree;
	}

	public String getWarn_four() {
		return warn_four;
	}

	public void setWarn_four(String warnFour) {
		warn_four = warnFour;
	}

	public String getWarn_five() {
		return warn_five;
	}

	public void setWarn_five(String warnFive) {
		warn_five = warnFive;
	}

	public String getProduct_list_one() {
		return product_list_one;
	}

	public void setProduct_list_one(String productListOne) {
		product_list_one = productListOne;
	}

	public String getProduct_list_two() {
		return product_list_two;
	}

	public void setProduct_list_two(String productListTwo) {
		product_list_two = productListTwo;
	}

	public String getProduct_list_three() {
		return product_list_three;
	}

	public void setProduct_list_three(String productListThree) {
		product_list_three = productListThree;
	}

	public String getProduct_list_four() {
		return product_list_four;
	}

	public void setProduct_list_four(String productListFour) {
		product_list_four = productListFour;
	}

	public String getProduct_list_five() {
		return product_list_five;
	}

	public void setProduct_list_five(String productListFive) {
		product_list_five = productListFive;
	}

	public List<Map<String, String>> getList_six() {
		return list_six;
	}

	public void setList_six(List<Map<String, String>> listSix) {
		list_six = listSix;
	}

	public String getMail_start_one() {
		return mail_start_one;
	}

	public void setMail_start_one(String mailStartOne) {
		mail_start_one = mailStartOne;
	}

	public String getMail_start_two() {
		return mail_start_two;
	}

	public void setMail_start_two(String mailStartTwo) {
		mail_start_two = mailStartTwo;
	}

	public String getMail_start_three() {
		return mail_start_three;
	}

	public void setMail_start_three(String mailStartThree) {
		mail_start_three = mailStartThree;
	}

	public String getMail_start_four() {
		return mail_start_four;
	}

	public void setMail_start_four(String mailStartFour) {
		mail_start_four = mailStartFour;
	}

	public String getMail_start_five() {
		return mail_start_five;
	}

	public void setMail_start_five(String mailStartFive) {
		mail_start_five = mailStartFive;
	}

	public String getProduct_list_six() {
		return product_list_six;
	}

	public void setProduct_list_six(String productListSix) {
		product_list_six = productListSix;
	}

	public List<Map<String, String>> getTest_list() {
		return test_list;
	}

	public void setTest_list(List<Map<String, String>> testList) {
		test_list = testList;
	}
}
